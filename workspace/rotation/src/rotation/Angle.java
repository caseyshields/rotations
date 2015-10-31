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
	public final static double REVOLUTION_DEGREES      = 360;
	public final static double REVOLUTION_ARCMINUTES   = REVOLUTION_DEGREES*60;
	public final static double REVOLUTION_ARCSECONDS   = REVOLUTION_DEGREES*3600;
	public final static double REVOLUTION_PI_RADIANS   = 2;
	public final static double REVOLUTION_RADIANS      = StrictMath.PI*2;
	public final static double REVOLUTION_MILLIRADIANS = StrictMath.PI*2000;
	public final static double REVOLUTION_MILS         = 6000;	

	protected final static double QUARTER_PI_RADIANS   = StrictMath.scalb(StrictMath.PI,-2);//=StrictMath.atan(1);
	protected final static double HALF_PI_RADIANS      = StrictMath.scalb(StrictMath.PI,-1);
	
	private double angle; //internally stores radians...

	/** Protected constructor: Internals assume radians Angle measure... */
	protected Angle(double a) { angle = a; }
	
	/** Copy constructor. */
	public Angle(Angle a) { angle = a.angle; }

    /** Angle Static Factory -- Produce <b><i>Angle</i></b> of <b>r</b> radians.*/
	public static Angle inRadians(double r){ return new Angle(r); }
	
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> of <b>d</b> degrees. */
	public static Angle inDegrees(double d){ return new Angle(StrictMath.toRadians(d)); }
		
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> measured. */
	public static Angle inMeasure(double unitsInAngle, double unitsInRevolution)
	{ return new Angle(QUARTER_PI_RADIANS*( unitsInAngle/StrictMath.scalb(unitsInRevolution,-3) )); }
	
	/** Angle Static Factory -- Produce <b><i>Angle</i></b> from binary representation of <b>numBits</b>. */
	public static Angle inBinary(int binaryAngle, byte numBits){
	  int signed=-(numBits-3); double cast = binaryAngle;
	  return new Angle(QUARTER_PI_RADIANS*( StrictMath.scalb(cast,signed) ));  }

	/** Angle Static Factory -- Produce <b><i>Angle</i></b> from binary representation of <b>numBits</b>. */
	public static Angle inBinary(long binaryAngle, byte numBits){
	  int signed=-(numBits-3); double cast = binaryAngle;
	  return new Angle(QUARTER_PI_RADIANS*( StrictMath.scalb(cast,signed) ));  }

	/** Mutator: Return <b>halved<i>this</i></b> set equal to <b>copy</b>. */
	public Angle half() { Angle half = new Angle(this); half.angle /= 2; return half; }
	
	/** Mutator: Return <b><i>this</i></b> set equal to <b>copy</b>. */
	public Angle put(Angle copy) { angle = copy.getRadians(); return this; }
	
	/** Mutator: Return <b><i>this</i></b> set equal to <b>r</b> radians.*/ 
	public Angle putRadians(double r){ angle = r; return this; }	

	/** Mutator -- Return <b><i>this</i></b> set equal to <b>d</b> degrees. */ 
	public Angle putDegrees(double d){ angle = StrictMath.toRadians(d); return this;}

    /** Mutator: Return <b><i>this</i></b> set equal to measured angle of units per revolution specified.*/ 
	public Angle put(double unitsInAngle, double unitsInRevolution)
	{ angle=QUARTER_PI_RADIANS*( unitsInAngle/StrictMath.scalb(unitsInRevolution,-3) ); return this; }

   /** Mutator: Return <b><i>this</i></b> set to <b>binaryAngle</b> of modulus <b>angleBits</b>.*/ 
	public Angle putBinary(int binaryAngle, byte angleBits) 
	{
		int signed=-(angleBits-3); 	
		double cast = binaryAngle; 
		angle=QUARTER_PI_RADIANS*( StrictMath.scalb(cast,signed) ); 
		return this; 
	}

    /** Mutator: Return <b><i>this</i></b> set to <b>binaryAngle</b> of modulus <b>angleBits</b>.*/ 
	public Angle putModuloBinary(int binaryAngle, byte angleBits) {
		int signed=-(angleBits-3); 	
		// mod in cast prevents impossible binary inputs being accepted.
		double cast = binaryAngle % StrictMath.scalb(2, (int) (angleBits) - 1); 
		angle=QUARTER_PI_RADIANS*( StrictMath.scalb(cast,signed) ); //double cast = binaryAngle;
		return this; 
	}

	/** Get <i>double</i>: radians measure of <b><i>Angle</b></i>. */
	public double getRadians(){ return angle; }
	
	/** Get <i>double</i>: degrees measure of <b><i>Angle</b></i>. */
	public double getDegrees(){ return StrictMath.toDegrees(angle); }

	/** Get <i>double</i>: <i>units measure</i> of <b><i>Angle</b></i>. 
	 * @param double <b>units_in_revolution</b>.
	 * */
	public double getMeasure(double units_in_revolution)
	{ return angle*(StrictMath.scalb(units_in_revolution,-3)/QUARTER_PI_RADIANS); }	

	/** Get <i>int</i>: <i>binary</i> angle representation of <b><i>Angle</b></i>. 
	 * @param byte <b>number_bits</b> coded in angle of revolution.
	 */
	public int getBinary(byte number_bits)
	{ return (int) StrictMath.rint(StrictMath.scalb(angle/QUARTER_PI_RADIANS,number_bits-3)); }

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

	/** 
	 * Get <i>double</i>: <i>coded</i> <b><i>Principle</b> Angle</i>. 
	 */
	protected double getCodedPrinciple()
	{ 
		double r = StrictMath.scalb(angle ,-1) % StrictMath.PI;
		if (StrictMath.abs(r)>HALF_PI_RADIANS) 
			r -= StrictMath.copySign(StrictMath.PI, r); 
		return StrictMath.tan(r); 
	}

	
}
