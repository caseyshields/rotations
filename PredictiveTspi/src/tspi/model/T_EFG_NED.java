package tspi.model;
import rotation.Angle;
//import rotation.BasisUnit;
import rotation.Rotator;
import rotation.Principle;
import rotation.QuaternionMath;
import rotation.Vector3;

/**
 * Stores local geodetic [WGS84] definition:<p>
 * <p> Topocentric coordinate frame
 * <br>-- (Operator) Geocentric rotator to local horizontal navigation {N,E,D}
 * <br>-- (double) Helmert height above ellipsoid from which to calculate gravity potentials and elevations 
 *
 * <p> Class includes methods for generating: 
 * <br>-- (Vector3) Cartesian geocentric coordinates {E,F,G}
 * <br>-- (Ellipsoid) geodetic coordinates {latitude,longitude,height}
 *                          
 * @author mike
 *
 */
public class T_EFG_NED {	
	
	//Definition WGS84 ellipsoid.
	public static final double _a = 6378137.0d; //WGS84 semi-major axis radius
	public static final double _f = 0.00335281068118d; //WGS84 flattening

	//Conversion algorithm constants.
	protected static final double ZERO = 0d;
	private static final double NEGATIVE_ZERO = -ZERO;
	
	protected static final double ONE = 1d;
	private static final double NEGATIVE_ONE = -ONE;
	
	protected static final double TWO = 2d;
	protected static final double THREE = 3d;
	protected static final double ONE_THIRD = ONE/THREE;
	protected static final double FOUR_THIRDS = 4d/THREE;
	
	private static final double MINOR_MAJOR = ONE - _f; // = _b/_a; 
    protected static final double FUNSQ= (MINOR_MAJOR)*(MINOR_MAJOR); // = (_b/_a)*(_b/_a)
    protected static final double DIFFERENCE_AXES_RATIOS = (ONE - FUNSQ)/MINOR_MAJOR; //= (_a/_b) - (_b/_a);	
    protected static final double FLATFN= (TWO - _f)*_f; //== 1-((1-f)^2)

    protected static final double _b = _a * (MINOR_MAJOR); //WGS84 semi-minor axis radius
    	
	//minimalist geodetic OP holding:
	protected final Rotator _horizontal; //rotater
	protected double _vertical;  //translater		

	/**
	 * Geodetic local copy-constructor:
	 */
	public T_EFG_NED(T_EFG_NED wgs84){
		_vertical = wgs84._vertical;
		_horizontal = new Rotator(wgs84._horizontal);
	}
	
	/**
	 * Setter: by Ellipsoid coordinates.
	 * @param coordinates
	 */
	public void set(Ellipsoid coordinates){
		_vertical = coordinates._height; //pass thru...
		_horizontal.set(localHorizontal(coordinates._latitude,coordinates._longitude));
	}
	
	/**
	 * Constructor: initialize 'Empty'.
	 */
	public T_EFG_NED(){
		_vertical = Double.NaN;
		_horizontal = new Rotator(Rotator.EMPTY);
	}	
	
	/**
	 * @return meters vertical (Helmert height) above WGS 84 ellipsoid
	 */
	public double getLocalVertical() {
		return _vertical;
	}
	
	public void setLocalVertical(double heightHelmert){
		_vertical = heightHelmert;
	}

	
	
	/** 
	 * @param geocentricEFG earth-centered, earth-fixed Cartesian position
	 */
	public void set(Vector3 geocentricEFG){	
		
	    double x= geocentricEFG.getX(); //E
	    double y= geocentricEFG.getY(); //F
	    double z= geocentricEFG.getZ(); //G   
	
	    /* 2.0 compute intermediate values for latitude */
	    double r= StrictMath.hypot(x, y); //.sqrt( x*x + y*y );
	    double e = (StrictMath.abs(z) / _a - DIFFERENCE_AXES_RATIOS) / (r / _b);
		double f = (StrictMath.abs(z) / _a + DIFFERENCE_AXES_RATIOS) / (r / _b);
	    
	    /* 3.0 Find solution to: t^4 + 2*E*t^3 + 2*F*t - 1 = 0  */
	    double p= FOUR_THIRDS * (e*f + ONE);
	    double q= TWO * (e*e - f*f);
	    
	    double d = p*p*p + q*q;
	    double v;
	    if( d >= ZERO ) {
	            v= StrictMath.pow( (StrictMath.sqrt( d ) - q), ONE_THIRD )
	             - StrictMath.pow( (StrictMath.sqrt( d ) + q), ONE_THIRD );
	    } else {
	            v= TWO * StrictMath.sqrt( -p )
	             * StrictMath.cos( StrictMath.acos( q/(p * StrictMath.sqrt( -p )) ) / THREE );
	    }
	    
	    /* 4.0 Improve v. NOTE: not really necessary unless point is near pole */
	    if( v*v < StrictMath.abs(p) ) {
	            v= -(v*v*v + TWO*q) / (THREE*p);
	    }
	    double g = (StrictMath.sqrt( e*e + v ) + e) / TWO;
	    double t = StrictMath.sqrt( g*g  + (f - v*g)/(TWO*g - e) ) - g;
			
		double kSinLat = ONE - t*t;
		double kCosLat = TWO*t*StrictMath.copySign(MINOR_MAJOR,z);
		double k = StrictMath.hypot(kSinLat, kCosLat);
		
		/* 5.0 Calculate ellipsoid height WGS84 */	    
		double B = (z<ZERO)?-_b:_b;	    
		_vertical = ((r - _a * t) * kCosLat + (z - B) * kSinLat)/k;

		
		/* 6.0 Perform direct Principle computations for ellipsoid WGS84 coordinates */
		
		double cosLon = x/r; // /r always positive.
		Principle plon = Principle.arcTanHalfAngle(StrictMath.copySign(StrictMath.sqrt((ONE-cosLon)/(ONE+cosLon)),y));
		Angle theta = Angle.inRadians(StrictMath.atan(kSinLat/kCosLat)).add(Angle.QUARTER_REVOLUTION).negate(); //codes as cotangent of half-latitude
		Principle ptheta = theta.getPrinciple();

		//_localHorizontal 	
		if (plon.isAcute()) {
			if (ptheta.isAcute()) { //acute lon, acute theta
				if (ptheta.isZero()) {
					_horizontal.set(ONE, ZERO, ZERO, plon.tanHalf());
					//_localHorizontal.unit();
					return;
				}
				_horizontal.set(ONE, -ptheta.tanHalf() * plon.tanHalf(), ptheta.tanHalf(), plon.tanHalf());
				//_localHorizontal.unit();
				return;
			} //acute lon, obtuse theta
			if (ptheta.isStraight()) {
				_horizontal.set(0, -plon.tanHalf(), ONE, ZERO);
				//_localHorizontal.unit();
				return;
			}
			_horizontal.set(ONE / ptheta.tanHalf(), -plon.tanHalf(), ONE, plon.tanHalf() / ptheta.tanHalf());
			//_localHorizontal.unit();
			return;
		} //obtuse lon, acute theta
		if (ptheta.isAcute()) {
			if (ptheta.isZero()) {
				_horizontal.set(ONE / plon.tanHalf(), ZERO, ZERO, ONE);
				//_localHorizontal.unit();
				return;
			}
			_horizontal.set(ONE / plon.tanHalf(), -ptheta.tanHalf(), ptheta.tanHalf() / plon.tanHalf(), ONE);
			//_localHorizontal.unit();
			return;
		} //obtuse lon, obtuse theta
		if (ptheta.isStraight()) {
			_horizontal.set(ZERO, NEGATIVE_ONE, ONE / plon.tanHalf(), ZERO);
			//_localHorizontal.unit();
			return;
		}
		double cotHalfTheta = ONE / ptheta.tanHalf();
		_horizontal.set(cotHalfTheta / plon.tanHalf(), NEGATIVE_ONE, ONE / plon.tanHalf(), cotHalfTheta);
		//_localHorizontal.unit();
		return;
	}

	/**
	 * [Recovery...]
	 * Sets geodetic north latitude and geodetic east longitude as extracted from q_NG.
	 * @param q_NG rotator defining local geodetic frame alignment
	 */
//	public void setEllipsoidHorizontal(Operator q_NG) { 
	public Ellipsoid getEllipsoid(){ //Operator q_NG) { 
		double dump = _horizontal.getEuler_i_kji().tanHalf();
//		Angle latitude=Angle.REVOLUTION;
//		Angle longitude=Angle.REVOLUTION;
		Principle pLat = _horizontal.getEuler_j_kji().addRight().negate();
		Principle pLon = _horizontal.getEuler_k_kji();
		Ellipsoid ellipsoid = new Ellipsoid();
		Double tst2 = ZERO; //+0.0d
		if (Double.isInfinite(dump)) { // Northern hemisphere 
			ellipsoid.setNorthLatitude(pLat.negate().signedAngle());
			ellipsoid.setEastLongitude(pLon.addStraight().negate().unsignedAngle());
			if (ellipsoid.getEastLongitude().getPiRadians() == ZERO) {
				if (tst2.equals(ellipsoid.getEastLongitude().getPiRadians())) { //-0.0d
					ellipsoid.setEastLongitude(Angle.inPiRadians(ONE));
				} else {
					ellipsoid.setEastLongitude(Angle.inPiRadians(ZERO));
				}
			}
		} else if (dump == ZERO) { // Southern hemisphere
			ellipsoid.setNorthLatitude(pLat.signedAngle());
			ellipsoid.setEastLongitude(pLon.unsignedAngle());
			Double lonD = ellipsoid.getEastLongitude().getPiRadians();
			if (lonD.equals(NEGATIVE_ZERO)){//ugly... but sentinel trap maps south pole.
				ellipsoid.setEastLongitude(Angle.HALF_REVOLUTION);
			}
		} else { //Error: Twist contamination in Euler k,j coordinate representation! 
			if (Double.isNaN(dump)) {
				//should rais exception.
				System.out.println("***NaN: Empty GEODETIC Angle!");
				ellipsoid.setNorthLatitude(pLat.negate().signedAngle());
				ellipsoid.setEastLongitude(pLon.addStraight().negate().unsignedAngle());
			} else {
				//should raise exception.
				System.out.println("Null pointer exception ERROR: Not wgs84 geo-location! -- NOT VALID q_NG Operator.");
				ellipsoid.setNorthLatitude(null);
				ellipsoid.setEastLongitude(null);
			}
		}
		ellipsoid.setEllipsoidHeight(_vertical);
		return ellipsoid;
	}
		
//	/**
//	 * @param meters above WGS reference ellipsoid.
//	 */
//	public void setEllipsoidHeight(double meters) {
//		this._height = meters;
//	}
//
//	/** 
//	 * @param latitude defined positive in northern hemisphere
//	 */
//	public void setNorthLatitude(Angle latitude) {
//		_latitude.set(latitude);
//		
//	}

		
//		/** Setter:
//		 * @param Location East longitude
//		 */
//		public void setEastLongitude(Angle eastLongitude) {
//			_longitude.set(eastLongitude);
//		}

			
		/** 
		 * Clears this Geodetic location -- re-initializes as empty.
		 */
		public void clear(){			
			_vertical = Double.NaN;
			_horizontal.set(Rotator.EMPTY);
		}

	/**
	 * Factory: Rotator from local navigation coordinate frame {N,E,D}
	 * to geocentric frame {E,F,G}. 
	 * 
	 * @return Operator quaternion {w,xI,yJ,zK}
	 */
	public Rotator getLocalHorizontal(){
//		//System.out.println("inputs axial OP: "+_latitude.getDegrees()+"  "+_longitude.getDegrees());
//		Angle theta = new Angle(_latitude).add(Angle.QUARTER_REVOLUTION).negate();
//		return QuaternionMath.eulerRotate_kj(_longitude.getPrinciple(),theta.getPrinciple());
		
		return new Rotator(_horizontal);
		
	}
	
	public void setLocalHorizontal(Rotator geocentric_to_topocentric){
		_horizontal.set(geocentric_to_topocentric);
	}
	
	/**
	 * Factory: Cartesian position coordinates of this GeodeticLocation, earth-centered and earth-fixed. 
	 * @return Vector3 geocentric {E,F,G}
	 */
	public Vector3 getGeocentric(){
		
		//trig unnecessary[?]
		
		//double sin_lat = getEast
		Ellipsoid local = getEllipsoid();
		return local.getGeocentric();
//		double latitudeRadians = local.getNorthLatitude().getRadians();
//		
//		
//		double sin_lat = StrictMath.sin(latitudeRadians);
//		
//		double rPE = _a / StrictMath.sqrt(ONE - FLATFN * sin_lat * sin_lat);
//		double x = (rPE + local.getEllipsoidHeight()) * StrictMath.cos(latitudeRadians);
//		double longitudeRadians = local.getEastLongitude().getRadians();
//		return new Vector3(
//				x * StrictMath.cos(longitudeRadians), 
//				x * StrictMath.sin(longitudeRadians),
//				(rPE * FUNSQ + local.getEllipsoidHeight()) * sin_lat
//		);
	}	
	
	public static Rotator localHorizontal(Angle latitude, Angle longitude){
		Angle theta = new Angle(latitude).add(Angle.QUARTER_REVOLUTION).negate();
		return QuaternionMath.eulerRotate_kj(longitude.getPrinciple(),theta.getPrinciple());		
	}
	
}
