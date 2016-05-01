/**
 * 
 */
package rotationTest;

import static org.junit.Assert.*;

import org.junit.Test;

import rotation.Angle;
import rotation.Principle;

/**
 * Test rotation.Angle
 * Adapter class for angle measure:
 * 	<p>-- positive and negative units.
 *  <p>-- signed measures.
 *  <p>-- stores more than span of revolution.
 *  
 * @author mike
 *
 */
public class AngleTest {

//	/**
//	 * Protected Constructor:
//	 * Test method for {@link rotation.Angle#Angle(double)}.
//	 */
//	@Test
//	public void testAngleDouble() {
//		double a = 30;
//		Angle b = Angle.inDegrees(a);
////		System.out.println( a.getDegrees() + " degrees" );
////		System.out.println(b.getDegrees() + " degrees" );
//		assertEquals("Compare double constructor: ",
//				b.getDegrees(),30.0,1e-14);
////		fail("Not yet implemented"); // TODO
//	}

	
	/**
	 * Test method for {@link rotation.Angle#Angle(rotation.Angle)}.
	 * Test method for {@link rotation.Angle#put(rotation.Angle)}.
	 * Test method for {@link rotation.Angle#bisectorPrincipleAngle()}.
	 * 
	 */
//	@Test
//	public void testAngleAngle() {
//	public void testSet() {
////	public void testHalf() {
//	public void testAngleSetHalfAngle() {
//		Angle a = Angle.inDegrees(30);
//		Angle b = Angle.inDegrees(60);
//		b = new Angle(a);
//		assertEquals(b.getDegrees(),30,1e-14);
//		a.set(b);
//		assertEquals(a.getDegrees(),30,1e-14);
//		a.set(new Angle(b).bisectorPrincipleAngle());
//		assertEquals(a.getDegrees(),15,1e-14);
//		assertEquals(b.getDegrees(),30,1e-14);
//		
//		a = Angle.inDegrees(120);
//		b.set(new Angle(a).bisectorPrincipleAngle());
//		assertEquals(b.getDegrees(),60,1e-14);
//
//		a = Angle.inDegrees(240);
//		b.set(new Angle(a).bisectorPrincipleAngle());
//		//System.out.println(b.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
//		assertEquals(b.getDegrees(),-60,1e-13);
//		
//		a = Angle.inDegrees(380);
//		assertEquals(a.getDegrees(),380,1e-14);
//		b.set(new Angle(a).bisectorPrincipleAngle());
//		assertEquals(b.getDegrees(),10,1e-14);
//		
//		a = Angle.inDegrees(-120);
//		b.set(new Angle(a).bisectorPrincipleAngle());
//		assertEquals(b.getDegrees(),-60,1e-14);
//
//		
//	}

	/**
	 * Test method for {@link rotation.Angle#inRadians(double)}.
	 * Test method for {@link rotation.Angle#putRadians(double)}.
	 * Test method for {@link rotation.Angle#getRadians()}.
	 */
//  public void testGetRadians() {
//	public void testSetRadians() {
//	public void testInRadians() {
	@Test
	public void testGetSetInRadians() {
		Angle a = Angle.inRadians(1.0);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),1.0,0);
		
		a.setRadians(.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),.2,1e-14);
		
		a.setRadians(-.2);
//		System.out.println(a.getRadians()+" degrees"+(-StrictMath.nextUp(StrictMath.abs(a.getRadians()))));
		assertEquals(a.getRadians(),-.2,1e-14);
		
		a.setRadians(2.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextAfter(a.getRadians(),1));
		assertEquals(a.getRadians(),2.2,1e-14);
		
		a.setRadians(-2.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians())); 
		assertEquals(a.getRadians(),-2.2,1e-14);

		a.setRadians(5.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),5.2,1e-14);
		
		a.setRadians(-5.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),-5.2,1e-14);
		
		a.setRadians(50.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),50.2,1e-14);	

		a.setRadians(-50.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),-50.2,1e-14);	

		a = Angle.inRadians(1.0);
//		System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getRadians(),1.0,0);
		
		a = Angle.inRadians(.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),.2,1e-14);
		
		a = Angle.inRadians(-.2);
//		System.out.println(a.getRadians()+" degrees"+(-StrictMath.nextUp(StrictMath.abs(a.getRadians()))));
		assertEquals(a.getRadians(),-.2,1e-14);
		
		a = Angle.inRadians(2.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextAfter(a.getRadians(),1));
		assertEquals(a.getRadians(),2.2,1e-14);
		
		a = Angle.inRadians(-2.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians())); 
		assertEquals(a.getRadians(),-2.2,1e-14);

		a = Angle.inRadians(5.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),5.2,1e-14);
		
		a = Angle.inRadians(-5.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),-5.2,1e-14);	
		
		a = Angle.inRadians(50.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),50.2,1e-14);	

		a = Angle.inRadians(-50.2);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getRadians(),-50.2,1e-14);	

		a = Angle.inRadians(StrictMath.PI/6);
//		System.out.println(a.getRadians()+" degrees"+StrictMath.nextUp(a.getRadians()));
		assertEquals(a.getDegrees(),30,1e-14);	
		
	//	fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link rotation.Angle#inDegrees(double)}.
	 * Test method for {@link rotation.Angle#getDegrees()}.
	 * Test method for {@link rotation.Angle#setDegrees(double)}.
	 */
//	public void testSetDegrees() {
//	public void testGetDegrees() {
//	public void testInDegrees() {
	@Test
	public void testGetSetInDegrees() {
		
		Angle a = Angle.inDegrees(45);
//		System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),45.0,0);
		
		a.setDegrees(30);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),30.0,1e-14);
		
		a.setDegrees(-30);
//		System.out.println(a.getDegrees()+" degrees"+(-StrictMath.nextUp(StrictMath.abs(a.getDegrees()))));
		assertEquals(a.getDegrees(),-30.0,1e-14);
		
		a.setDegrees(150);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextAfter(a.getDegrees(),1));
		assertEquals(a.getDegrees(),150.0,1e-13);
		
		a.setDegrees(-150);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees())); 
		assertEquals(a.getDegrees(),-150.0,1e-13);

		a.setDegrees(330);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),330.0,1e-13);
		
		a.setDegrees(-330);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),-330.0,1e-13);

		a.setDegrees(530);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),530.0,1e-13);
		
		a.setDegrees(-530);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),-530.0,1e-13);

		a = Angle.inDegrees(45);
//		System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),45.0,0);
		
		a = Angle.inDegrees(30);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),30.0,1e-14);
		
		a = Angle.inDegrees(-30);
//		System.out.println(a.getDegrees()+" degrees"+(-StrictMath.nextUp(StrictMath.abs(a.getDegrees()))));
		assertEquals(a.getDegrees(),-30.0,1e-14);
		
		a = Angle.inDegrees(150);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextAfter(a.getDegrees(),1));
		assertEquals(a.getDegrees(),150.0,1e-13);
		
		a = Angle.inDegrees(-150);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees())); 
		assertEquals(a.getDegrees(),-150.0,1e-13);

		a = Angle.inDegrees(330);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),330.0,1e-13);
		
		a = Angle.inDegrees(-330);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),-330.0,1e-13);
		

		a = Angle.inDegrees(530);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),530.0,1e-13);
		
		a = Angle.inDegrees(-530);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),-530.0,1e-13);
		
		a = Angle.inDegrees(-530.3);
//		System.out.println(a.getDegrees()+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getDegrees(),-530.3,1e-13);
		
	}

	/**
	 * Test method for {@link rotation.Angle#inMeasure(double, double)}.
	 * Test method for {@link rotation.Angle#set(double, double)}.
	 * Test method for {@link rotation.Angle#getMeasure(double)}.
	 */
//	public void testGetMeasure() {
//	public void testSetMeasure() {
//	public void testInMeasure() {
	@Test
	public void testGetSetInMeasure() {
		
		Angle a = Angle.inMeasure(45,Angle.DEGREES_REVOLUTION);
		
		//System.out.println(a.getMeasure(360) + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),45.0,0);
		
		a.set(30,Angle.DEGREES_REVOLUTION);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getMeasure(Angle.DEGREES_REVOLUTION),30.0,1e-14);
		
		a.set(-30*60, Angle.ARCMINUTES_REVOLUTION);
		//System.out.println(a.getMeasure(360)+" degrees"+(-StrictMath.nextUp(StrictMath.abs(a.getDegrees()))));
		assertEquals(a.getMeasure(Angle.DEGREES_REVOLUTION),-30.0,1e-14);
		
		a.set(150,360);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextAfter(a.getDegrees(),1));
		assertEquals(a.getMeasure(360),150.0,1e-13);
		
		a.set(-150,360);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees())); 
		assertEquals(a.getMeasure(360),-150.0,1e-13);

		a.set(330,360);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getMeasure(360),330.0,1e-13);
		
		a.set(-330,360);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getMeasure(360),-330.0,1e-13);

		a.set(530,360);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getMeasure(360),530.0,1e-13);
		
		a.set(-530,360);
		//System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(a.getMeasure(360),-530.0,1e-13);

		a = Angle.inMeasure(60,360);
		//System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),60,1e-14);
		
		a = Angle.inMeasure(60,-360);
		//System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),-60,1e-14);
		
		a = Angle.inMeasure(-60,-360);
		//System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),60,1e-14);
		
		a = Angle.inMeasure(-60,360);
		//System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),-60,1e-14);
		
		a = Angle.inMeasure(-560,-360);
		//System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),560,1e-14);
		
		a = Angle.inMeasure(-560,360);
		//System.out.println(a.getDegrees() + " degrees" + a.getDegrees() );
		assertEquals(a.getDegrees(),-560,1e-14);
		
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link rotation.Angle#inBinary(int, byte)}.
	 * Test method for {@link rotation.Angle#setModuloBinary(int, byte)}.
	 * Test method for {@link rotation.Angle#getBinary(byte)}.
	 */
	@Test
//	public void testGetBinary() {
//	public void testSetBinary() {
//	public void testInBinary() {
	public void testGetSetInBinary() {
		
		Angle a = Angle.inMeasure(45,360);		

		byte w=16;
		int m = 8192; //=2^
		long n = Angle.inBinary(m,w).getBinary(w);
		//System.out.println( n + " parts of revolution.");
		assertEquals(n,8192);		
		
		a.setModuloBinary(m, w);
		//System.out.println(a.getMeasure(360)+" degrees");
		assertEquals(a.getMeasure(360),45,1e-14);
		a.setModuloBinary(-m, w);
		//System.out.println(a.getMeasure(360)+" degrees");
		assertEquals(a.getMeasure(360),-45,1e-14);
		
		a.setModuloBinary(-9*m, w);
	    //System.out.println(a.getDegrees()+" degrees");
		assertEquals(a.getMeasure(360),-45,1e-12);
			
		a.setModuloBinary(3*m, w);
		//System.out.println(a.getMeasure(360)+" degrees");
		assertEquals(a.getMeasure(360),135,1e-14);
		a.setModuloBinary(-3*m, w);
		//System.out.println(a.getMeasure(360)+" degrees");
		assertEquals(a.getMeasure(360),-135,1e-14);
		
        w=8; //bits in revolution representation
        m=32;
		a.setModuloBinary(m, w);
		//System.out.println(a.getMeasure(360)+" degrees");
		assertEquals(a.getMeasure(360),45,1e-14);

		w=3;
        m=1;
		a.setModuloBinary(m, w);// 1/8
		long k=a.getBinary(w);
		////System.out.println(a.getMeasure(360)+" degrees"+StrictMath.nextUp(a.getDegrees()));
		//System.out.println(a.getMeasure(360)+" degrees");
		assertEquals(a.getMeasure(360),45,1e-14);
	    //System.out.println("for 3 bit machine...= "+k);
		assertEquals(k,1);

		
		w=8;
		k=a.getBinary(w);
		//System.out.println("for 8 bit machine...= "+k);
		assertEquals(k,32);
		
		a = Angle.inBinary(8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),45,1e-14);
		a = Angle.inBinary(2*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),90,1e-14);
		a = Angle.inBinary(3*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),135,1e-14);
		a = Angle.inBinary(4*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),180,1e-14);
		a = Angle.inBinary(5*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),225,1e-14);
		a = Angle.inBinary(6*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),270,1e-14);
		a = Angle.inBinary(7*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),315,1e-14);
		a = Angle.inBinary(8*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),360,1e-14);
		a = Angle.inBinary(9*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),405,1e-13);
		
		a = Angle.inBinary(-8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-45,1e-14);
		a = Angle.inBinary(-2*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-90,1e-14);
		a = Angle.inBinary(-3*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-135,1e-14);
		a = Angle.inBinary(-4*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-180,1e-14);
		a = Angle.inBinary(-5*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-225,1e-14);
		a = Angle.inBinary(-6*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-270,1e-14);
		a = Angle.inBinary(-7*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-315,1e-14);
		a = Angle.inBinary(-8*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-360,1e-14);
		a = Angle.inBinary(-9*8192,(byte) 16);
		//System.out.println(a.getDegrees() + " degrees");
		assertEquals(a.getDegrees(),-405,1e-13);
		
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link rotation.Angle#getCodedPrinciple()}.
	 */
	@Test
	public void testGetCodedPrinciple() {
		
		Angle a = Angle.inDegrees(390);
		Angle b = new Angle(a);
		
		
		Principle t = new Principle( Angle.inDegrees(0) ); //principle angle by constructor.
		//System.out.println("Principle: "+t.signedAngle().getDegrees()+"  degrees Angle:"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(t.signedAngle().getDegrees(),0,1e-13);

		t = new Principle( a ); //principle angle by constructor.
		//System.out.println("Principle: "+t.signedAngle().getDegrees()+"  degrees Angle:"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(t.signedAngle().getDegrees(),30,1e-13);
		
		b.set(t.signedAngle()); //convert principle angle to angle...signed and unsigned.
		
		Angle c = Angle.inRadians( t.signedAngle().getRadians() );

		//System.out.println("Principle: "+t.getDegrees()+"  degrees Angle:"+StrictMath.nextUp(a.getDegrees()));
		//System.out.println("Principle: "+b.getDegrees()+"  degrees Angle:"+StrictMath.nextUp(a.getDegrees()));
		assertEquals(b.getDegrees(),30,1e-13);
		assertEquals(c.getDegrees(),30,1e-13);
		assertEquals(a.getDegrees(),390,1e-13);
		
		c.set(t.unsignedAngle());
		assertEquals(c.getDegrees(),30,1e-13);
		a.setDegrees(-721);
		t.set(a);
		//System.out.println( "Principle: " + t.signedAngle().getDegrees() + "  degrees. " );
//		assertEquals("Ugh: ",t.signedAngle().getDegrees(),-1.0d,1e-13);

		//		fail("Not yet implemented"); // TODO
		boolean testZero;
//		testZero = (-0 == 0)?true:false;
//		System.out.println("neg zero equals pos zero:"+testZero);
//		testZero = (-0 < 0)?true:false;
//		System.out.println("neg less than pos zero:"+testZero);
//		testZero = (-0 < 0)?true:false;
		Double num = 0d;
		testZero = (num.equals(-0d))?true:false;
		System.out.println("object zero same negZero:"+testZero);
		System.out.println("object zero same negZero:"+testZero+" "+num.compareTo(0d));
		testZero = (num.compareTo(-0d)>0)?true:false;
		System.out.println("object zero compares greater than negZero:"+testZero+" "+num.compareTo(-0d));
		num=-0d;
		System.out.println("object negZero to one:"+num.compareTo(1d));
		System.out.println("object negZero to zero:"+num.compareTo(0d));
		System.out.println("object negZero to negZero:"+num.compareTo(-0d));
		System.out.println("object negZero to negOne:"+num.compareTo(-1d));
		num=0d;
		System.out.println("object zero to one:"+num.compareTo(1d));
		System.out.println("object zero to zero:"+num.compareTo(0d));
		System.out.println("object zero to negZero:"+num.compareTo(-0d));
		System.out.println("object zero to negOne:"+num.compareTo(-1d));
		
		double i;
		a = Angle.inDegrees(Double.NaN);
		for (int h = -32; h <= 32; h++) {
			i=h*22.5;
			a = Angle.inDegrees(i);
			System.out.println("("+i+") Unsigned Angle = "+a.unsignedPrincipleAngle().getDegrees()
					+"    Signed Angle = "+a.signedPrincipleAngle().getDegrees()
					);			
		}

	}

}