package com.example.animation_test;

import android.view.animation.Animation;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Camera;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.AnimationUtils;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;
import android.util.Log;

public class magicBallAnimation extends Animation{
	private static final String TAG = "Magic8Ball Activity";
	
	AnimationTestActivity activitytest = new AnimationTestActivity();
	float centerX, centerY;
	Camera camera = new Camera();
	public magicBallAnimation(float cx, float cy)
	{
		
		centerX = cx;
		centerY = cy;
		Log.d(TAG, "Animation: cx, cy :" + cx + "," + cy);
	}
	
	@Override
	public void initialize(int width, int height, 
			int parentWidth,
			int parentHeight )
	{
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(2500);
		setFillAfter(true);
		AccelerateInterpolator i = new AccelerateInterpolator();
		setInterpolator(i); 
	}
			

	
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t)
	{
		applyTransformationNew(interpolatedTime, t);
		Log.d(TAG, "ONE:Reached apply transformation: interpolated Time: " + interpolatedTime + "Transform: " + t);
		
	}
	
	protected void applyTransformationNew(float interpolatedTime, Transformation t)
	{
		Log.d(TAG, "TWO:Interpolated time, transform" + interpolatedTime + "," + t);
		t.setAlpha(interpolatedTime);
		final Matrix matrix = t.getMatrix();
		camera.save();
		camera.translate(0.0f, 0.0f, (2600 - 2600.0f * interpolatedTime));
		camera.rotate(INFINITE, INFINITE, INFINITE);
		//camera.rotate(360 * interpolatedTime, 360 * interpolatedTime, 360 * interpolatedTime);
		Log.d(TAG, "THREE: Reached apply to camera: Infinite rotation/XYZ rotation? " );
		camera.getMatrix(matrix);
		
		
		matrix.preTranslate((3*centerX)/4, centerY);
		matrix.postTranslate(centerX, centerY);		
		camera.restore();
		
	}

}
