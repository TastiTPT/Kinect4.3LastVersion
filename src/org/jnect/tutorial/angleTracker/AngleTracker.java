package org.jnect.tutorial.angleTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.jnect.bodymodel.Body;
import org.jnect.core.KinectManager;

public class AngleTracker implements Runnable {
	public static AngleTracker INSTANCE = new AngleTracker();

	PrintWriter pw;
	boolean isAlive = true;

	private AngleTracker() {
		File file = new File(System.getProperty("user.dir") + File.separator
				+ "data" + File.separator + "track.txt");
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	public void printPosition() throws InterruptedException {
		KinectManager.INSTANCE.startKinect();
		KinectManager.INSTANCE.startSkeletonTracking();
		
		float Aarbis = 400; // au delà de 360 pour etre sur que Aarbis différent
							// de AAr au départ

		while (isAlive) { // permettra de sortir de la boucle en mettant isAlive a false.

			Body body = KinectManager.INSTANCE.getSkeletonModel();

			float Xrh = (float) body.getRightHand().getX();
			float Yrh = (float) body.getRightHand().getY();
			float Xlh = (float) body.getLeftHand().getX();
			float Ylh = (float) body.getLeftHand().getY();
			float Xch = (float) body.getCenterHip().getX();
			float Ych = (float) body.getCenterHip().getY();
			float Xrs = (float) body.getRightShoulder().getX();
			float Yrs = (float) body.getRightShoulder().getY();
			float Xls = (float) body.getLeftShoulder().getX();
			float Yls = (float) body.getRightShoulder().getY();

			float produitScalaire = (Xrh - Xrs) * (Xlh - Xls) + (Yrh - Yrs)
					* (Ylh - Yls);
			float normeR = (float) Math.sqrt((Xrh - Xrs) * (Xrh - Xrs)
					+ (Yrh - Yrs) * (Yrh - Yrs));
			float normeL = (float) Math.sqrt((Xlh - Xls) * (Xlh - Xls)
					+ (Ylh - Yls) * (Ylh - Yls));
			float produitNorme = normeR * normeL;
			float Aar;
			
			if(Yrs >= Yrh){ // Pour pouvoir avoir des angles de 0 à 360
			Aar = (float) (Math.acos(produitScalaire / produitNorme) * 180 / Math.PI);
			}
			else { Aar = 360 - (float) (Math.acos(produitScalaire / produitNorme) * 180 / Math.PI);
			}
			
			if (Aar != Aarbis && Ych != 0) {
				Aarbis = Aar;

				System.out.println("leftHand x: " + body.getLeftHand().getX()
						+ "|leftHand y: " + body.getLeftHand().getY()
						+ "|leftHand z: " + body.getLeftHand().getZ()
						+ "|rightHand x: " + body.getRightHand().getX()
						+ "|rightHand y: " + body.getRightHand().getY()
						+ "|rightHand z: " + body.getRightHand().getZ()
						+ "|centerHip x: " + body.getCenterHip().getX()
						+ "|centerHip y: " + body.getCenterHip().getY()
						+ "|centerHip z: " + body.getCenterHip().getZ()
						+ "|head x: " + body.getHead().getX() + "|Head y: "
						+ body.getHead().getY() + "|head z: "
						+ body.getHead().getZ() + "angle " + Aar);
				System.out.println();

				try {

					pw.append("leftHand x: " + body.getLeftHand().getX()
							+ " leftHand y: " + body.getLeftHand().getY()
							+ " leftHand z: " + body.getLeftHand().getZ()
							+ System.getProperty("line.separator")
							+ "rightHand x: " + body.getRightHand().getX()
							+ " rightHand y: " + body.getRightHand().getY()
							+ " rightHand z: " + body.getRightHand().getZ()
							+ System.getProperty("line.separator")
							+ "centerHip x: " + body.getCenterHip().getX()
							+ " centerHip y: " + body.getCenterHip().getY()
							+ " centerHip z: " + body.getCenterHip().getZ()
							+ System.getProperty("line.separator") + "head x: "
							+ body.getHead().getX() + " Head y: "
							+ body.getHead().getY() + " head z: "
							+ body.getHead().getZ()
							+ System.getProperty("line.separator") + "angle "
							+ Aar + System.getProperty("line.separator")
							+ System.getProperty("line.separator"));
					pw.flush();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 

					
				}
			}
		}

		KinectManager.INSTANCE.stopKinect();
	}

	
	public void stop() {
		isAlive = false;

	}

	@Override
	public void run() {
		try {
			printPosition() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
