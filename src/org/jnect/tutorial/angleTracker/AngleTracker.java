package org.jnect.tutorial.angleTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.jnect.bodymodel.Body;
import org.jnect.core.KinectManager;

public class AngleTracker implements Runnable {
	public static AngleTracker INSTANCE = new AngleTracker();

	int repet=0;
	boolean block=true;
	//private Classifieur classif;
	private float[] vector = new float[120];
	PrintWriter pw;
	boolean isAlive = true;
	int i = -100; // on s'assure que les 100 premières mesures ne sont pas dans le classifieur.
				// Car elles correspondent à la mise en place du sportif devant la caméra.

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
		
		                                    // au delà de 360 pour etre sur que Adbbis différent
		float Adjbis = 400;		            // de Adb au départ
		
		long totalTime = 0;
		
		while (isAlive) { // permettra de sortir de la boucle en mettant isAlive a false.

			long startTime = System.currentTimeMillis(); // pour calculer le temps entre deux prints
			
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
				
	/****************** angle entre la jambe droite et le bras droit *******************************/
				
				float produitScalaireD = (Xrknee - Xrhip) * (Xrh - Xrs) + (Yrknee - Yrhip)
						* (Yrh - Yrs);
				float produitNormeD = normeRj * normeR;
				float AdD;
				
				AdD = (float) (Math.acos(produitScalaireD / produitNormeD) * 180 / Math.PI);
				
	/****************** angle entre la jambe gauche et le bras gauche ******************************/
				
				float produitScalaireG = (Xlknee - Xlhip) * (Xlh - Xls) + (Ylknee - Ylhip)
						* (Ylh - Yls);
				float produitNormeG = normeLj * normeL;
				float AdL;
				
				AdL = (float) (Math.acos(produitScalaireG / produitNormeG) * 180 / Math.PI);
				
	/**************** calcul du temps entre deux impressions ***************************************/
				
				long endIntermediateTime = System.currentTimeMillis(); // on introduit ces temps intermediaire  car seul le temps entre deux print nous interesse
				long totalIntermediateTime = endIntermediateTime - startTime;
				totalTime = totalTime + totalIntermediateTime;
				
   /***********************************     print  et setAngle    ****************************/
				if ( (Adj != Adjbis ) && (Ychip != 0) ) { // on evite de répéter les mêmes angles 
					Adjbis = Adj;
					
				/********************** setAngle ******************/
				
				/*if ( i>=0 && i<=19 ){ //19 pour avoir 20 prises de positions pour chaque angle.
					this.classif.setAngle(i,Adb);
					this.classif.setAngle(i+20,Adj);
					this.classif.setAngle(i+40,AdD);
					this.classif.setAngle(i+60,AdL);
				}
			*/
					if ( i>=0 && i<=29 ){ //19 pour avoir 20 prises de positions pour chaque angle.
						this.vector[i]=Adb;
						this.vector[i+30]=Adj;
						this.vector[i+60]=AdD;
						this.vector[i+90]=AdL;
					}
				i=i+1;
				/*********************  print *********************/
				 
				
				
				System.out.println("angle entre les deux bras: " + Adb 
						+ " angle entre les deux jambes: " + Adj
						+ "angle entre le bras droit et la jambe droite: " + AdD
						+ "angle entre le bras gauche et la jambe gauche: " + AdL
						+ " temps de l'iteration: " + totalTime
						+ "repetition: " + repet);
				System.out.println();

				/* try {

					pw.append( "angle entre les deux bras: "
							+ Adb + System.getProperty("line.separator")
							+ "angle entre les deux jambes: "
							+ Adj + System.getProperty("line.separator")
							+ "angle entre le bras droit et la jambe droite: "
							+ AdD + System.getProperty("line.separator")
							+ "angle entre le bras gauche et la jambe gauche: "
							+ AdL + System.getProperty("line.separator")
							+ "temps de l'itération: "
							+ totalTime + System.getProperty("line.separator")
							+ System.getProperty("line.separator"));
					pw.flush();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 

					
				}
				*/
				
				if(block==true && Adb >= 300){
					repet=repet+1;
					block=false;
				}
				
				if (block==false && Adb <= 50){
					block=true;
				}
				
			totalTime = 0;
			if(i==29){
				try {

					for ( int j=0;j<=119;j++){
						pw.append(this.vector[j]+";");	
					}
						//pw.flush();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 

					
				}	
			}
			
			if(repet>=5){
			pw.append(System.getProperty("line.separator")+repet);
			}
			
			pw.flush();
			
		}
				
				
		}
		
		KinectManager.INSTANCE.stopKinect();
	}

	/*public final void saveClassifToTextFile(){
		
		try {

			for ( int j=0;j<=19;j++){
				pw.append(this.classif.getAngle(j)+";");
			}
			
			pw.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 

			
		}	
	}
	*/
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
		
		//saveClassifToTextFile();
	}

}
