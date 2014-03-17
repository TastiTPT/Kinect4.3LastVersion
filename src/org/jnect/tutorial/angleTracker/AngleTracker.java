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
		
		float Adbbis = 400; // au delà de 360 pour etre sur que Adbbis différent
		float Adjbis = 400;		            // de Adb au départ

		while (isAlive) { // permettra de sortir de la boucle en mettant isAlive a false.

			Body body = KinectManager.INSTANCE.getSkeletonModel();

			/****************GETTER DE TOUS LES MEMBRES*****************/
			
			/************* Hand *************************/
			
			float Xrh = (float) body.getRightHand().getX();
			float Yrh = (float) body.getRightHand().getY();
			
			float Xlh = (float) body.getLeftHand().getX();
			float Ylh = (float) body.getLeftHand().getY();
			
			/************** Hip *************************/
			
			float Xchip = (float) body.getCenterHip().getX();
			float Ychip = (float) body.getCenterHip().getY();
			
			float Xrhip = (float) body.getRightHip().getX();
			float Yrhip = (float) body.getRightHip().getY();
			
			float Xlhip = (float) body.getLeftHip().getX();
			float Ylhip = (float) body.getLeftHip().getY();
			
			/**************** Shoulder *******************/
			
			float Xrs = (float) body.getRightShoulder().getX();
			float Yrs = (float) body.getRightShoulder().getY();
			
			float Xls = (float) body.getLeftShoulder().getX();
			float Yls = (float) body.getRightShoulder().getY();
			
			/*float Xcs = (float) body.getCenterShoulder().getX();
			float Ycs = (float) body.getCenterShoulder().getY();
			*/
			
			/***************** Head ***********************/
			
			/*
			float Xhead = (float) body.getHead().getX();
			float Yhead = (float) body.getHead().getY();
			*/
			
			/**************** Ankle **********************/
			
			/*
			 
			float Xrankle = (float) body.getRightAnkle().getX();
			float Yrankle = (float) body.getRightAnkle().getY();
			
			float Xlankle = (float) body.getLeftAnkle().getX();
			float Ylankle = (float) body.getLeftAnkle().getY();
			
			*/
			
			/***************** Elbow *********************/
			/*
			 
			float Xrelbow = (float) body.getRightElbow().getX();
			float Yrelbow = (float) body.getRightElbow().getY();
			
			float Xlelbow = (float) body.getLeftElbow().getX();
			float Ylelbow = (float) body.getLeftElbow().getY();
			
			*/
			
			/***************** Foot *********************/
			
			/*
			 
			float Xrfoot = (float) body.getRightFoot().getX();
			float Yrfoot = (float) body.getRightFoot().getY();
			
			float Xlfoot = (float) body.getLeftFoot().getX();
			float Ylfoot = (float) body.getLeftFoot().getY();
			
			*/
			
			/****************** Knee *******************/
			
			float Xrknee = (float) body.getRightKnee().getX();
			float Yrknee = (float) body.getRightKnee().getY();
			
			float Xlknee = (float) body.getLeftKnee().getX();
			float Ylknee = (float) body.getLeftKnee().getX();
			
			/***************** Wrist ********************/
			
			/*
			
			float Xrw = (float) body.getRightWrist().getX();
			float Yrw = (float) body.getRightWrist().getY();
			
			float Xlw = (float) body.getLeftWrist().getX();
			float Ylw = (float) body.getLeftWrist().getY();
			
			*/
			
			/***************** Spine *******************/
			
			/*
			float Xspine = (float) body.getSpine().getX();
			*/
			
			
			/******************CALCUL DES ANGLES UTILES************************/
			
			/****Angle entre les 2 Bras Adb*****************/
			
			float produitScalaire = (Xrh - Xrs) * (Xlh - Xls) + (Yrh - Yrs)
					* (Ylh - Yls);
			float normeR = (float) Math.sqrt((Xrh - Xrs) * (Xrh - Xrs)
					+ (Yrh - Yrs) * (Yrh - Yrs));
			float normeL = (float) Math.sqrt((Xlh - Xls) * (Xlh - Xls)
					+ (Ylh - Yls) * (Ylh - Yls));
			float produitNorme = normeR * normeL;
			float Adb;
			
			if(Yrs >= Yrh){ // Pour pouvoir avoir des angles de 0 à 360
			Adb = (float) (Math.acos(produitScalaire / produitNorme) * 180 / Math.PI);
			}
			else { Adb = 360 - (float) (Math.acos(produitScalaire / produitNorme) * 180 / Math.PI);
			}
			
			if (Adb != Adbbis && Ychip != 0) {
				Adbbis = Adb;
			}
				
	/*********Angle entre les 2 jambes Adj *********************/
				
				float produitScalairej = (Xrknee - Xrhip) * (Xlknee - Xlhip) + (Yrknee - Yrhip)
						* (Ylknee - Ylhip);
				float normeRj = (float) Math.sqrt((Xrknee - Xrhip) * (Xrknee - Xrhip)
						+ (Yrknee - Yrhip) * (Yrknee - Yrhip));
				float normeLj = (float) Math.sqrt((Xlknee - Xlhip) * (Xlknee - Xlhip)
						+ (Ylknee - Ylhip) * (Ylknee - Ylhip));
				float produitNormej = normeRj * normeLj;
				float Adj;
				
				
				Adj = (float) (Math.acos(produitScalairej / produitNormej) * 180 / Math.PI);
				
				if (Adj != Adjbis && Ychip != 0) {
					Adjbis = Adj;
					
   /***********************************     Print      ****************************/
				System.out.println("angle entre les deux bras: " + Adb 
						+ "angle entre les deux jambes: " + Adj );
				System.out.println();

				try {

					pw.append( "angle entre les deux bras: "
							+ Adb + System.getProperty("line.separator")
							+ "angle entre les deux jambes: "
							+ Adj + System.getProperty("line.separator")
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
