package rotation;
/**
 * Adapter class for angle measure:
 * 	<p>-- positive and negative units.
 *  <p>-- signed measures.
 *  <p>-- stores more than span of revolution.
 */
public class Angle //adapter class -- Angle
{
	//Units:
	public final static double MILS_PIRADIAN         = 3000d;	
	public final static double DEGREES_PIRADIAN      = 180;
	public final static double DEGREES_REVOLUTION    = DEGREES_PIRADIAN*2;
	public final static double ARCMINUTES_REVOLUTION = DEGREES_PIRADIAN*60*2;
	public final static double ARCMINUTES_PIRADIAN   = DEGREES_PIRADIAN*60;
	public final static double ARCSECONDS_PIRADIAN   = DEGREES_PIRADIAN*3600;
	public final static double PI     = StrictMath.PI;
	public final static double PI_2   = StrictMath.scalb(StrictMath.PI,-1);
	public final static double PI_4   = StrictMath.scalb(StrictMath.PI,-2);//=StrictMath.atan(1);
	public final static double MILLIRADIANS_PIRADIAN = PI*1000;	
	public final static Angle REVOLUTION         = new Angle(2d);
	public final static Angle HALF_REVOLUTION    = new Angle(1d);
	public final static Angle QUARTER_REVOLUTION = new Angle(1d/2);
	public final static Angle EIGHTH_REVOLUTION  = new Angle(1d/4);
	
	private double _piRadAngle; //internally stores PiRadian measure...

	/** Protected constructor: Internals assume PiRadians measure Angle... */
	protected Angle(double p) { _piRadAngle = p; }
	
	/** Copy constructor. */
	public Angle(Angle p) { _piRadAngle = p._piRadAngle; }

	/** Angle Static Factory -- Produce <b><i>Angle</i></b> of <b>p</b> PiRadians.*/
	public static Angle inPiRadians(double p){ return new Angle(p); }
 
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> of <b>r</b> radians.*/
	public static Angle inRadians(double r){ return new Angle(r/PI); }
	
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> of <b>d</b> degrees. */
	public static Angle inDegrees(double d){ return new Angle(d/DEGREES_PIRADIAN); }
		
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> measured. */
	public static Angle inMeasure(double unitsAngle, double unitsRevolution)
	{ return new Angle(( unitsAngle/StrictMath.scalb(unitsRevolution,-1) )); }
	
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> from integer representation of <b>numBits</b>. */
	public static Angle inBinary(int binaryAngle, byte numBitsRevolution){
	  int signed=-(numBitsRevolution-1); double cast = binaryAngle;
	  return new Angle(( StrictMath.scalb(cast,signed) ));  }

	/** Angle Static Factory -- Produce <b><i>Angle</i></b> from long representation of <b>numBits</b>. */
	public static Angle inBinary(long binaryAngle, byte numBitsRevolution){
	  int signed=-(numBitsRevolution-1); double cast = binaryAngle;
	  return new Angle(( StrictMath.scalb(cast,signed) ));  }

	/** Mutator. Returns sum with Angle addend. Method is unaware of Angle wrapping.*/
	public Angle add(Angle addend){
		this._piRadAngle += addend._piRadAngle;
		return this;
	}
	
	/** Mutator. Returns difference from subtrahend. Method is unaware of Angle wrapping.*/
	public Angle subtract(Angle subtrahend){
		this._piRadAngle -= subtrahend._piRadAngle;
		return this;
	}
	
	/** Mutator. Returns positive Angle MAGNITUDE. Method is unaware of angle wrapping.*/
	public Angle abs(){
		_piRadAngle = StrictMath.abs(_piRadAngle);
		return this;
	}
	
	/** Mutator. Returns this Angle negated. Method is unaware of angle wrapping.*/
	public Angle negate(){
		_piRadAngle = -_piRadAngle;
		return this;
	}
	
	/** Mutator. Returns this Angle with sign of that. */
	public Angle copySign(Angle that){
		_piRadAngle = StrictMath.copySign(_piRadAngle, that._piRadAngle);
		return this;
	}
	

	/** Mutator: Return <b><i>this</i></b> set equal to <b>copy</b>. */
	public Angle put(Angle copy) { _piRadAngle = copy._piRadAngle; return this; }
	
	/** Mutator: Return <b><i>this</i></b> set equal to <b>r</b> radians.*/ 
	public Angle putRadians(double r){ _piRadAngle = r/PI; return this; }	

	/** Mutator -- Return <b><i>this</i></b> set equal to <b>d</b> degrees. */ 
	public Angle putDegrees(double d){ _piRadAngle = d/DEGREES_PIRADIAN; return this;}

    /** Mutator: Return <b><i>this</i></b> set equal to measured angle of units per revolution specified.*/ 
	public Angle put(double unitsInAngle, double unitsInRevolution)
	{ _piRadAngle=( unitsInAngle/StrictMath.scalb(unitsInRevolution,-1) ); return this; }

   /** Mutator: Return <b><i>this</i></b> set to <b>binaryAngle</b> of modulus <b>angleBits</b>.*/ 
	public Angle putBinary(int binaryAngle, byte angleBits) 
	{
		int signed=-(angleBits-1); 	
		double cast = binaryAngle; 
		_piRadAngle= StrictMath.scalb(cast,signed); 
		return this; 
	}

    /** Mutator: Return <b><i>this</i></b> set to <b>binaryAngle</b> of modulus <b>angleBits</b>.*/ 
	public Angle putModuloBinary(int binaryAngle, byte angleBits) {
		int signed=-(angleBits-1); 	
		// mod in cast prevents impossible binary inputs being accepted.
		double cast = binaryAngle % StrictMath.scalb(2, (int) (angleBits) - 1); 
		_piRadAngle= StrictMath.scalb(cast,signed); //double cast = binaryAngle;
		return this; 
	}

	/** Get <i>double</i>: radians measure of <b><i>Angle</b></i>. */
	public double getRadians(){ return _piRadAngle*PI; }
	

	/** Get <i>double</i>: PiRadians measure of <b><i>Angle</b></i>. */
	public double getPiRadians(){ return _piRadAngle; }
	
	/** Get <i>double</i>: degrees measure of <b><i>Angle</b></i>. */
	public double getDegrees(){ return _piRadAngle*DEGREES_PIRADIAN; }

	/** Get <i>double</i>: <i>units measure</i> of <b><i>Angle</b></i>. 
	 * @param double <b>units_in_revolution</b>.
	 * */
	public double getMeasure(double units_in_revolution)
	{ return _piRadAngle*(StrictMath.scalb(units_in_revolution,-1)); }	

	/** Get <i>int</i>: <i>binary</i> angle representation of <b><i>Angle</b></i>. 
	 * @param byte <b>number_bits</b> coded in angle of revolution.
	 */
	public int getBinary(byte number_bits)
	{ return (int) StrictMath.rint(StrictMath.scalb(_piRadAngle,number_bits-1)); }

	
	// This should only be done with signed angles [?]... confusing what happens for angles greater than 180...	
	/** Factory: Return <b>halved copy</b> of <i>this</i>. */
	public Angle halfSignedPrincipleAngle() {
		Angle half = this.signedPrincipleAngle(); 	//create signed principle angle.
		half._piRadAngle /= 2.d;					//make half.
		return half;								//return half signed principle angle.
	}
	
	/** Factory.*/
	public Angle signedPrincipleAngle(){
		
		double p=StrictMath.IEEEremainder(_piRadAngle, 2d);
        
		return Angle.inPiRadians(p);

//		double p=StrictMath.IEEEremainder(_piRadAngle, 2d);
//		double a = StrictMath.rint(p);
//		if (a==_piRadAngle);	
//		if(StrictMath.abs(p)==1d){
//			return Angle.inPiRadians(1d);
//		}
//		if(StrictMath.abs(p)==-1d){
//			return Angle.inPiRadians(-1d);
//		}		
//		if(_piRadAngle<0){
//			return Angle.inPiRadians(-p);
//		}
//        
//		return Angle.inPiRadians(p);
	}

	/** Factory.*/
	public Angle unsignedPrincipleAngle(){
		Double p = this.signedPrincipleAngle().getPiRadians();
//		if(p.equals(-0d)){
//			return Angle.inPiRadians(0d);
//		}
		if(p<0){
		       return Angle.inPiRadians(2d+p);
		}
		return Angle.inPiRadians(p);	

	}
	
	/** 
	 * Get <i>double</i>: <i>coded</i> <b><i>Principle</b> Angle</i>. 
	 */
	protected double getCodedPrinciple()
	{ 
		double r = this.halfSignedPrincipleAngle().getPiRadians();
		if(r==0) return r;		
		if(r==.5) return Double.POSITIVE_INFINITY;
		if(r==-.5) return Double.NEGATIVE_INFINITY;
		return StrictMath.tan(r*PI);
		
	}
	
	/** 
	 * Factory. 
	 * Get <b><i>Principle</b> Angle</i>. 
	 */
	public Principle getPrinciple() {
		return Principle.arcTanHalfAngle(this.getCodedPrinciple());	
	}
	
	
	/*return tangent of one-half of the signed _angle defined in interval: (-PI..PI] */
//	
//	if(_angle==0){
//		//trap zero...fast return...
//		//System.out.print("trapZZZZZ");
//		return StrictMath.copySign(0,_angle);
//	}
//
//	double r = StrictMath.IEEEremainder(StrictMath.scalb(_angle,-1),StrictMath.PI);	
//	//double r = StrictMath.scalb(_angle ,-1) % StrictMath.PI; //alternative development
//	
//	
//	if (StrictMath.abs(r)>=PI_2) { //center the residual half angle
//		r -= StrictMath.copySign(StrictMath.PI, r); 	
//	}
//
//	if (StrictMath.abs(r)==PI_2) { //trap residual right angle...fast return
//		//System.out.print("trapInfiny");
//		return StrictMath.copySign(Double.POSITIVE_INFINITY, -r);									
//	}
//	
//	//System.out.print("default");
//	return StrictMath.tan(r);

	
	
//	/**
//	 * Get <i>int</i>: <i>nearest full revolution count</i> of stored
//	 * <b>Angle</b>.
//	 */
//	public int getNearestRevolution() {
//		return (int) StrictMath.round(angle / REVOLUTION_RADIANS);
//	}
//	/**
//	 * Get <i>double</i>: Principle <i>radians</i> of stored
//	 * <b>Angle</b>.
//	 */
//	public double getSignedPrincipleRadians() {
//		return StrictMath.IEEEremainder(angle , REVOLUTION_RADIANS);
//		//returns [-PI..+PI] revolution!
//	}


//	/** Get <i>int</i>: <i>binary</i> angle representation of <b><i>Angle</b></i>. 
//	 * @param byte <b>number_bits</b> coded in angle of revolution.
//	 */
//	public long getLongBinary(byte number_bits)
//	{ return (long) StrictMath.rint(StrictMath.scalb(angle/QUARTER_PI_RADIANS,number_bits-3)); }

	
	
//	/**
//	 * 
//	 * Sum factory
//	 * @param radians
//	 * @return
//	 */
//	protected Angle getSum(double radians){
//	    return inRadians(_angle+radians);	
//	}
//	/**
//	 * 
//	 * Sum factory
//	 * @param radians
//	 * @return
//	 */
//	protected Angle getDifference(double radians){
//	    return inRadians(radians - _angle);	
//	}
	
	
}
