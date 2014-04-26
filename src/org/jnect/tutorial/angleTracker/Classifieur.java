package org.jnect.tutorial.angleTracker;

public class Classifieur {

	private static final int width=80;
	private float[] classifieur;
	
	public Classifieur(){
		this.classifieur = new float[width];
	}
	
	public void setAngle( int i, float a ){
		this.classifieur[i]=a;
	}

	public float getAngle ( int i )	{
		return this.classifieur[i];
	}
	
}
