package org.jnect.tutorial.angleTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.jnect.bodymodel.RightHand;
import org.jnect.core.KinectManager;

public class TheTracker {          
	public static TheTracker INSTANCE = new TheTracker();
	
	PrintWriter pw;
	
	private TheTracker()
	{
		File file = new File(System.getProperty("user.dir") + File.separator + 
	            "data" + File.separator + "track.txt") ;
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    };
		
		public void printRightHandPosition() {
			KinectManager.INSTANCE.startKinect(); 
			KinectManager.INSTANCE.startSkeletonTracking();
			final RightHand rightHand = KinectManager.INSTANCE.getSkeletonModel().getRightHand();
			
			rightHand.eAdapters().add(new Adapter() {
				@Override          
				public void notifyChanged(Notification notification) { 
					System.out.println("rightHand x: " + rightHand.getX() + "|rightHand y: "                                
				+ rightHand.getY() + "|rightHand z: " + rightHand.getZ());
					
					try {
						pw.append("rightHand x: " + rightHand.getX() + "|rightHand y: "                                
								+ rightHand.getY() + "|rightHand z: " + rightHand.getZ() + System.getProperty("line.separator"));
						pw.flush();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
	
				
				@Override       
				public Notifier getTarget() {  
					return rightHand;                    
					}                  
				@Override        
				public void setTarget(Notifier newTarget) {  
					// TODO Auto-generated method stub  
					}          
				@Override         
				public boolean isAdapterForType(Object type) {
					// TODO Auto-generated method stub 
					return false;  
					}
			
		});
}
		public void stop() {
			KinectManager.INSTANCE.stopKinect(); 
			}
		
	}

