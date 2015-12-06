/**
 * 
 */
package tspi.model;

import junit.framework.TestCase;
import rotation.Angle;
import rotation.BasisUnit;
import rotation.Operator;
import rotation.Principle;
import rotation.Quaternion;
import rotation.QuaternionMath;
import rotation.Vector3;

/**
 * @author mike
 *
 */
public class testPedestal extends TestCase {
	public void testSetWGS84() {
		//Location temp = new Location(Angle.inDegrees(-30),Angle.inDegrees(-91),3000);		
		//Vector3 result = temp.getGeocentric();
		//temp.set(result);
		Location temp = new Location(Angle.inDegrees(-30),Angle.inDegrees(-89),3000);		
		  System.out.println("lat: "+temp.getNorthLatitude().getDegrees());
		  System.out.println("lon: "+temp.getEastLongitude().getDegrees());
		Angle theta = temp.getNorthLatitude().negate().subtract(Angle.QUARTER_REVOLUTION);
//		  Principle ptheta =new Principle(theta);
//		  System.out.println("ptheta: "+ptheta.signedAngle().getDegrees());	
		  
		Operator q =QuaternionMath.eulerRotate_kj(temp.getLongitude(), theta.getPrinciple());

//		Principle pDump = q.getEuler_i_kji();
//		System.out.println("is dump twist: "+pDump.signedAngle().getDegrees());
		
//		Angle aTheta = q.getEuler_j_kji().signedAngle();
//		Angle aLat = new Angle(aTheta).add(Angle.QUARTER_REVOLUTION).negate();
//		Angle aLon = q.getEuler_k_kji().unsignedAngle();
//		if (pDump.signedAngle().getDegrees() == 180.0) { //southern hemisphere
//			System.out.println("Northern Hemisphere");
//			aLat.negate();
//			aLon.add(Angle.HALF_REVOLUTION).unsignedPrincipleAngle();
//			aTheta.add(Angle.HALF_REVOLUTION).negate();
//		}
//		System.out.println("get Op lat: " + aLat.getDegrees());
//		System.out.println("get Op lon: " + aLon.getDegrees());
//		System.out.println("get Op theta: " + aTheta.getDegrees());
//		;		
		
		//Principle pTheta = q.getEuler_j_kji();
		Principle pLat = q.getEuler_j_kji().addRight().negate();
		Principle pLon = q.getEuler_k_kji();
		System.out.println("twistDump: " + q.getEuler_i_kji().tanHalf());
		double dump = q.getEuler_i_kji().tanHalf();
		
		boolean northernHemisphere = Double.isNaN(dump);
		if (northernHemisphere) { //Northern hemisphere 
			pLat.negate();			
			pLon.addStraight().negate();
			//pTheta.addStraight();
		} 
		boolean southernHemisphere  = (dump==0d);
		if(!southernHemisphere&&!northernHemisphere) {System.out.println("get geodetic horizontal position from Op failed: " + dump);}
		System.out.println("get Op lat: " + pLat.signedAngle().getDegrees());
		System.out.println("get Op lon: " + pLon.unsignedAngle().getDegrees());
		//System.out.println("get Op theta: " + pTheta.signedAngle().getDegrees());
				
		
		
//		System.out.println(q.unit().toString());	
//		q = new Operator(1,0,0,0).exp_k(temp.getLongitude()).exp_j(ptheta);
//		System.out.println(q.unit().toString());
//		System.out.println("get Op theta: "+q.getEuler_j_kji().signedAngle().getDegrees());
//		System.out.println("get Op lon: "+q.getEuler_k_kji().unsignedAngle().getDegrees());
//		Angle pthetaTest =  q.getEuler_j_kji().signedAngle().add(Angle.QUARTER_REVOLUTION).negate();	
//		System.out.println("get Op lat: "+pthetaTest.getDegrees());
		
		//if(pthetaTest.getPiRadians()<-.5d) pthetaTest.add(Angle.HALF_REVOLUTION);
//		System.out.println("get Op lat: "+pthetaTest.getPiRadians());
//		System.out.println("get Op lat: "+pthetaTest.getDegrees());
//		System.out.println("q = "+q.toString(10));
//		System.out.println("twist     q = "+q.getEuler_i_kji().signedAngle().getDegrees());
//		System.out.println("elevation q = "+q.getEuler_j_kji().signedAngle().getDegrees());
//		System.out.println("azimuth   q = "+q.getEuler_k_kji().signedAngle().getDegrees());
//		System.out.println("A.i to N = "+ q.getImage_i().toString(15));	
//		System.out.println("A.j to N = "+ q.getImage_j().toString(15));	
//		System.out.println("A.k to N = "+ q.getImage_k().toString(15));	
//		
		
		
		
		
			//initial Euler angles definition!
			Angle az = Angle.inDegrees(120);
			Angle el = Angle.inDegrees(60);
			Angle tw = Angle.inDegrees(0);
			
			//initial principle Euler angles definition!
			Principle paz = az.getPrinciple();
			Principle pel = el.getPrinciple();
			Principle ptw = tw.getPrinciple();
			
			az = Angle.inDegrees(45);
			el = Angle.inDegrees(30);
			tw = Angle.inDegrees(0);			
			System.out.println("Euler Angles {A,E,T}={ "+ az.getDegrees()+", "+ el.getDegrees()+", "+ tw.getDegrees()+" }");		
			
			//Operator q_AN = (Operator) QuaternionMath.exp_i(ptw.put(tw)).leftMultExpJ(pel.put(el)).leftMultExpK(paz.put(az));
			//Operator q_AN = (Operator) QuaternionMath.exp_k(paz.put(az)).rightMultExpJ(pel.put(el)).rightMultExpI(ptw.put(tw));
			//Operator q_AN = (Operator) QuaternionMath.exp_k(paz.put(az)).rightMultExpJ(pel.put(el));
			
			//Operator q_AN = QuaternionMath.exp_k(paz.put(az)).exp_j(pel.put(el));
			Operator q_AN = QuaternionMath.eulerRotate_kj(paz.put(az), pel.put(el));
			
			System.out.println("q_AN = "+q_AN.toString(10));
			System.out.println("twist     q_AN = "+q_AN.getEuler_i_kji().signedAngle().getDegrees());
			System.out.println("elevation q_AN = "+q_AN.getEuler_j_kji().signedAngle().getDegrees());
			System.out.println("azimuth   q_AN = "+q_AN.getEuler_k_kji().signedAngle().getDegrees());
			System.out.println("A.i to N = "+ q_AN.getImage_i().toString(15));	
			System.out.println("A.j to N = "+ q_AN.getImage_j().toString(15));	
			System.out.println("A.k to N = "+ q_AN.getImage_k().toString(15));	
			
			
			//do rotation to get direction!
//			System.out.println("tmp = "+new Quaternion(q_AN).rightMultiply(Vector3.UNIT_I).rightMultiply(QuaternionMath.conjugate(q_AN)).unit().toString(15));
//			System.out.println("dir = "+new Vector3( paz.cos()*pel.cos(), paz.sin()*pel.cos(),-pel.sin() ).toString(15));	
			
			
//			paz.put(az.putDegrees(120.0));
//			pel.put(el.putDegrees(60));
//			ptw.put(tw.putDegrees(0));
//			
//			q_AN = (Operator) QuaternionMath.exp_k(paz).rightMultExpJ(pel).rightMultExpI(ptw);
//			System.out.println("q_AN = "+q_AN.toString(10));
//			System.out.println("twist     q_AN = "+q_AN.getEuler_i_kji().signedAngle().getDegrees());
//			System.out.println("elevation q_AN = "+q_AN.getEuler_j_kji().signedAngle().getDegrees());
//			System.out.println("azimuth   q_AN = "+q_AN.getEuler_k_kji().signedAngle().getDegrees());
			
			paz = new Principle(Angle.inDegrees(30));
			pel = new Principle(Angle.inDegrees(30));
			q_AN = QuaternionMath.eulerRotate_kj(paz, pel);
			
			Vector3 direction = new Vector3(pel.cos() * paz.cos(), pel.cos() * paz.sin(),-pel.sin());
			
			Vector3 rgNormal = q_AN.getImage_i();
			Vector3 azNormal = q_AN.getImage_j();
			Vector3 elNormal = q_AN.getImage_k();
//			System.out.println("**************************************");
//			System.out.println("Az:"+paz.unsignedAngle().getDegrees());
//			System.out.println("El:"+pel.signedAngle().getDegrees());
//			System.out.println("Direction: "+direction.toString(5));
//			System.out.println("**************************************");
//			System.out.println("rgNormal: "+rgNormal.toString(5));
//			System.out.println("azNormal: "+azNormal.toString(5));
//			System.out.println("elNormal: "+elNormal.toString(5));
//			System.out.println("**************************************");
//			System.out.println("rgNormal: "+rgNormal.unit().toString(5)); //unit is a mutator!!!
//			System.out.println("azNormal: "+azNormal.toString(5));
//			System.out.println("elNormal: "+elNormal.toString(5));
//			System.out.println("**************************************");
			
//			Operator q_NG = new Operator(Quaternion.NAN); 	
//			Angle lat = Angle.inDegrees(60);
//			Angle lon = Angle.inDegrees(30);
//			Angle theta = Angle.inRadians(Angle.PI_2).subtract(lat);
//			Principle plon = lon.getPrinciple();
			
//			atheta = tst.getEuler_j_kji().signedAngle().add(Angle.QUARTER_REVOLUTION);
//			plon.put( tst.getEuler_k_kji());					
//			if (phi >= 0){ //test northern hemisphere!!!
//				qlat = atheta.getDegrees();
//				qlon = plon.signedAngle().add(Angle.HALF_REVOLUTION).getDegrees();
//			} else {
//				qlat = atheta.negate().getDegrees();
//				qlon = plon.unsignedAngle().getDegrees();						
//			}

			//Principle plat = lat.getPrinciple();
//			ptheta = (Angle.inRadians(Angle.QUARTER_REVOLUTION).add(lat)).negate().getPrinciple();
//			ptheta = Angle.inRadians(Angle.PI_2).subtract(lat).getPrinciple();
//		    q_NG.putRightJ(QuaternionMath.eulerRotate_kj(plon,
//				Angle.inRadians(Angle.QUARTER_REVOLUTION).subtract(lat).getPrinciple())).unit();

//			q_NG.putRightJ(QuaternionMath.eulerRotate_kj(plon,ptheta)).unit();
//			Vector3 north = q_NG.getImage_i();
//			Vector3 east = q_NG.getImage_j();
//			Vector3 down = q_NG.getImage_k();
			
//			Principle pplon = q_NG.getEuler_k_kji();
//			Principle pptheta = q_NG.getEuler_j_kji();
//			Principle pplat = pptheta.signedAngle().subtract(Angle.inRadians(Angle.PI_2)).getPrinciple();

			//q_NG.putRightJ(QuaternionMath.eulerRotate_kj(plon,	
			
			paz = new Principle(Angle.inDegrees(150));
			pel = new Principle(Angle.inDegrees(30.0));
			q_AN.put(QuaternionMath.eulerRotate_kj(paz, pel).unit());
//			System.out.println("**************input*******************");
//			System.out.println("yaw|bearing: "+paz.unsignedAngle().getDegrees());
//			System.out.println("pitch|el   : "+pel.signedAngle().getDegrees()); //unit is a mutator!!!
//			System.out.println("q_AN : "+q_AN.toString(5)); 
//			System.out.println("**************************************");
			
			Vector3 d_AN = q_AN.getImage_i(); 

			Principle yaw = q_AN.getEuler_k_kji();
			Principle pitch = q_AN.getEuler_j_kji();
			Principle twi = q_AN.getEuler_i_kji();
			
//			System.out.println("*************Rank-3*******************");
//			System.out.println("az : "+yaw.unsignedAngle().getDegrees()); 
//			System.out.println("el : "+pitch.signedAngle().getDegrees());
//			System.out.println("tw : "+twi.signedAngle().getDegrees());
//			System.out.println("d_AN : "+d_AN.toString(5)); 
//			System.out.println("**************************************");
//			System.out.println("*************Rank-2*******************");
			
			Operator q_ANx = new Operator(Quaternion.NAN);
			q_ANx.put(QuaternionMath.foldoverI(d_AN).conjugate());
//			q_ANw.putRightTiltI(d_AN).conjugate();
//			q_ANw.put( new Quaternion(Quaternion.IDENTITY).rightMultiplyTiltI(d_AN).conjugate());
//			q_ANw.put( new Quaternion(Quaternion.IDENTITY).leftMultiplyTiltI(d_AN).conjugate());
			yaw = q_ANx.getEuler_k_kji();
			pitch = q_ANx.getEuler_j_kji();
			twi = q_ANx.getEuler_i_kji();
			q_ANx.exp_i(twi.negate());
			twi = q_ANx.getEuler_i_kji();
//			System.out.println("az : "+yaw.unsignedAngle().getDegrees()); 
//			System.out.println("el : "+pitch.signedAngle().getDegrees());
//			System.out.println("tw : "+twi.signedAngle().getDegrees());
//			System.out.println("**************************************");
		
	}
		
	}	
