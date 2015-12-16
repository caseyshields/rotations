package tspi.model;

import rotation.Angle;
import rotation.Operator;
import rotation.Principle;
import rotation.Quaternion;
import rotation.Vector3;

//import org.apache.commons.math3.linear.*;

/**  */
public class Pedestal {
	
	protected String systemId; // system identifier
	protected final GeodeticLocation wgs84; // geodetic position: lat, lon angles, h meters ellipsoid coordinates
	protected final Vector3 efg; // geocentric position vector: EFG
	protected final ApertureOrientation aer; //Aperture position: az, el angle r meters 
	//axial operators...
	protected final Operator q_NG; //navigation North East Down {NED} to geocentric {EFG}: N-->G
	protected final Operator q_AN; //aperture range, horz-right, vert-down  {RHV} to navigation {NED}: A-->N
	protected final Operator q_AG; //aperture range, horz-right, vert-down  {RHV} to geocentric {EFG}: A-->G
	
	//TODO Matrix error;// error model 
	//TODO SolidAngle mask;//angularBounds

	public Pedestal( String id, double lat, double lon, double h) {
		this.systemId = id;
		
		/* Pedestal: has location... in EFG with geodetic orientation */
		
		//Pedestal location (WGS84) in ellipsoid coordinates: Latitude, Longitude, height
		this.wgs84 = new GeodeticLocation(Double.NaN);
		this.wgs84.set(Angle.inDegrees(lat),Angle.inDegrees(lon), h );
		
		//Pedestal location (WGS84) in geocentric Cartesian frame coordinates (WGS84): E,F,G [a.k.a. ECEF X,Y,Z]
		this.efg = wgs84.geocentric();
		
		//axial operator transforms from local navigation alignment to Geocentric: {NED}-->{XYZ}  
		this.q_NG = wgs84.axialOperator_NG();
		
		
		/* Pedestal: position aperture class variable PLACE HOLDERS... */

		//Pedestal aperture position in local (AER) coordinates: Azimuth, Elevation, Range
		this.aer = new ApertureOrientation(Double.NaN); //Angle.inDegrees(Double.NaN),Angle.inDegrees(Double.NaN),Double.NaN);
	
		//axial operator from local pedestal aperture to local navigation: {RHV} --> {NED:
		this.q_AN = new Operator(Quaternion.NAN); //aperture.op_AN();
				
		//Pedestal: position aperture... in EFG orientation
		this.q_AG = new Operator(Quaternion.NAN); //aperture.op_AN();
		// q_AG = q_NG.euler_kj(paz,el);
		
	}
	
	public String getSystemId() { return this.systemId; }
	public GeodeticLocation getEllipsoidalCoordinates() { return this.wgs84; }
	public double getLatitude() { return this.wgs84.getNorthLatitude().getDegrees(); }
	public double getLongitude() { return this.wgs84.getEastLongitude().getDegrees(); }
	public double getHeight() { return this.wgs84.getEllipsoidHeight(); }
	public Vector3 getGeocentricCoordinates() { return this.efg; }	//this.wgs84.getGeocentricCoordinates();	//slower 
	public double getE() { return this.efg.getX(); } //this.wgs84.geocentric().getX(); //slower
	public double getF() { return this.efg.getY(); }
	public double getG() { return this.efg.getZ(); }
		
	// the following getters may return null to denote there is no current need for a heading
	
	public Double getAzimuth() {	
		return this.aer.getAzimuth().getDegrees();	
	}
	public Double getElevation() {
		return this.aer.getElevation().getDegrees();
	}
	public Double getRange() { 
		return this.aer.getRange(); 
	}
	
	public void setSystemId(String id) { this.systemId = id; }
	
	public void setLatitude(double lat) {
		this.wgs84.setNorthLatitude(Angle.inDegrees(lat) );//put(lat, wgs84.getY(), wgs84.getZ());
		this.efg.put(this.wgs84.geocentric());
		this.q_NG.put(this.wgs84.axialOperator_NG());//local Nav to Geo axial rotation.
	}
	
	public void setLongitude(double lon) {
		this.wgs84.setEastLongitude( Angle.inDegrees(lon) );//.put(wgs84.getX(), lon, wgs84.getZ());
		this.efg.put(this.wgs84.geocentric());
		this.q_NG.put(this.wgs84.axialOperator_NG());//local Nav to Geo axial rotation.
	}
	
	/**
	 * Get Location by Op q_NG (default WGS84._HEIGHT height):
	 */
	protected GeodeticLocation getLocationFromOp() { // Operator q_NG84,double height84){
		double dump = q_NG.getEuler_i_kji().tanHalf();
		Principle pLat = q_NG.getEuler_j_kji().addRight().negate();
		Principle pLon = q_NG.getEuler_k_kji();
		GeodeticLocation extracted = new GeodeticLocation(Double.NaN); 
		if (Double.isNaN(dump)) { // Northern hemisphere
			extracted.set(pLat.negate().signedAngle(), pLon.addStraight().negate().unsignedAngle(),
					wgs84._height);
		} else if (dump == 0d) { // Southern hemisphere
			extracted.set(pLat.signedAngle(), pLon.unsignedAngle(), wgs84._height);
		} else {
		System.out.println("ERROR: Not wgs84 geolocation Location! -- NOT VALID q_NG ROTATION OP.");
		}
		return  extracted;
	}
	
	public void setHeight(double meters) {
		this.wgs84.setEllipsoidHeight( meters );//.put(wgs84.getX(), wgs84.getY(), meters);
		this.efg.put(this.wgs84.geocentric());
		this.q_NG.put(this.wgs84.axialOperator_NG());//local Nav to Geo axial rotation.
	}
	
	public void setE(double E) {
		this.efg.put(E, efg.getY(), efg.getZ());
		this.wgs84.set(this.efg);
		this.q_NG.put(this.wgs84.axialOperator_NG());//local Nav to Geo axial rotation.
	}
	
	public void setF(double F) {
		this.efg.put(efg.getX(), F, efg.getZ());
		this.wgs84.set(this.efg);
		this.q_NG.put(this.wgs84.axialOperator_NG());//local Nav to Geo axial rotation.
	}
	
	public void setG(double G) {
		this.efg.put(efg.getX(), efg.getY(), G);
		this.wgs84.set(this.efg);
		this.q_NG.put(this.wgs84.axialOperator_NG());//local Nav to Geo axial rotation.
	}
	
	public void setAzimuth(double degrees) {
		this.aer.setAzimuth(Angle.inDegrees(degrees));
		this.q_AN.put(this.aer.op_AN());
	}
	
	public void setElevation(double degrees) {
		this.aer.setElevation( Angle.inDegrees(degrees));
		this.q_AN.put(this.aer.op_AN());
	}
	
	public void setAperturePosition(double az, double el) {
		this.aer.setAzimuth(Angle.inDegrees(az));
		this.aer.setElevation( Angle.inDegrees(el));
		this.q_AN.put(this.aer.op_AN());
	}
	
	public void setRange( double meters ) {
		this.aer.setRange(meters);
	}
	
	// pedestal.point = f(direct) 
	/** Updates the pedestal state to point to the given target geocentric coordinates.
	 * Actually, aligns given geocentric to pedestal's aperture coordinate frame as positioned on line-of-sight to target.
	 * Assumes no atmosphere and WGS84 earth model in positioing.
	 * @param targetEFG Position of the taget in geocentric coordinates.  */
	public void point(Vector3 targetEFG) {
		//Define geocentric vector from ped to target -- obtain pedestal.aperture range to target.
		Vector3 r_PT_G = new Vector3(targetEFG).subtract(efg);
		aer.setRange(r_PT_G.getAbs());
		
		//Make initial (rank-2) 'put' of q_AN -- returns pedestal.aperture azimuth & elevation direction (line-of-sight) to target.
		this.q_AN.put(this.q_NG).leftMultiplyTiltI(r_PT_G).conjugate();
		aer.setAzimuth(this.q_AN.getEuler_k_kji().unsignedAngle());		
		aer.setElevation( this.q_AN.getEuler_j_kji().signedAngle());

		//Refine 'put': Repair third rank of q_AN* (Annihilate Euler i-twist in field-of-view to match pedestal.aperture definition.)
		//-- makes q_AN rank-3 axial Operator between pedestal.aperture and pedestal.location coordinate frame definitions.
		Principle gTwist = q_AN.getEuler_i_kji();
		this.q_AN.rightMultExpI(gTwist.negate()); 
		
		//Assign local aperture directions in local navigation frame coordinates -- from final Operator q_AN:
//		r_N.put(this.q_AN.getImage_i()); //out pedestal.aperture (unit i) .. necessary?
//		Vector3 vertical.put(this.q_AN.getImage_j()); //right (across) pedestal.aperture (unit j)
//		Vector3 horizontal.put(this.q_AN.getImage_k()); //downward (across) pedestal.aperture (unit k)
		
		// //OP aligns geocentric to pedestal.aperture axial coordinates if perfectly located, aligned pedestal and no atmosphere:
		//System.out.println("post-twist annihilation:"+ this.q_AN.getImage_i().toString(5));		
		//System.out.println("q_AN twisted aftr repair:"+ q_AN.getEuler_i_kji().signedAngle().getDegrees());
		//System.out.println("Geocentric direction:"+ r_PT_G.divide(this.aperture.getRange()).toString(7));
		//System.out.println("Op Geocentric direction match:"+ new Operator(new Quaternion(q_NG).rightMultiply(q_AN)).getImage_i().unit().toString(7));				
	}

	public String toString() { 
		return this.systemId 
				+ "("+ this.wgs84.getNorthLatitude().toDegrees(7) +", "+ this.wgs84.getEastLongitude().toDegrees(7)+", "+this.getHeight()+")"
				+"("+aer.getAzimuth().toDegrees(4)+", "+aer.getElevation().toDegrees(4)+")";
	}

}
