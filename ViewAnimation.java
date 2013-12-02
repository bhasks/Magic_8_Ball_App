package com.example.magic8ballshake;
import android.graphics.Matrix;
import android.graphics.Camera;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;


public class ViewAnimation extends Animation {
	
	float centerX, centerY;
	Camera camera = new Camera();
	public ViewAnimation(float cx, float cy)
	{
		
		centerX = cx;
		centerY = cy;
	}
	
	public ViewAnimation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(int width, int height, 
			int parentWidth,
			int parentHeight )
	{
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(2500);
		setFillAfter(true);
		setInterpolator(new LinearInterpolator());	
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t)
	{
		applyTransformationNew(interpolatedTime, t);
		
	}
	protected void applyTransformationNew(float interpolatedTime, Transformation t)
	{
		final Matrix matrix = t.getMatrix();
		camera.translate(0.0f, 0.0f, (1300 - 1300.0f * interpolatedTime));
		camera.rotateY(360 * interpolatedTime);
		camera.getMatrix(matrix);
		
		matrix.setScale(interpolatedTime, interpolatedTime);
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
		camera.restore();
		
	}
	}
	
	

