package com.example.animation_test;
import android.view.animation.Animation;
import android.util.Log;

public class BallAnimationListener implements Animation.AnimationListener{
	
    private boolean isAnimationStarted;
    private boolean isAnimationRunning;
    private boolean isAnimationEnded;
	
	public BallAnimationListener(){
		isAnimationStarted = false;
		isAnimationRunning = false;
		isAnimationEnded = true;
	
	}
	
	public boolean getIsAnimationStarted()
	{
		return isAnimationStarted;
	}
	
	public boolean getIsAnimationRunning()
	{
		return isAnimationStarted;
	}
	
	public boolean getisAnimationEnded()
	{
		return isAnimationEnded;
	}
	
	
	public void onAnimationStart(Animation animation)
	{
		Log.d("Ball Animation Listener", "onAnimationStart");
		isAnimationStarted = true;
		isAnimationRunning = true;
		
	}
	
	public void onAnimationRepeat(Animation animation)
	{
		//do nothing -needed to implent this interface
	}
	
	
	public void onAnimationEnd(Animation animation)
	{
		Log.d("Ball Animation Listener", "onAnimationStart");
		isAnimationStarted = false;
		isAnimationRunning = false;
		isAnimationEnded = true;
		
	}	

}
 