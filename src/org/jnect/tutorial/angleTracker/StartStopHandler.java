package org.jnect.tutorial.angleTracker;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.jnect.core.KinectManager;
import org.jnect.tutorial.angleTracker.TheTracker;

public class StartStopHandler extends AbstractHandler { 
	
	private static boolean STARTED = false;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (!STARTED) {  

			Thread thread = new Thread(AngleTracker.INSTANCE) ;
			
			thread.start();
			
			STARTED = true;
			} 
		else {
			AngleTracker.INSTANCE.stop();
			STARTED = false;  
			}  
		return null; 
		}
	}
