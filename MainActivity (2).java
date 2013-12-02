package com.example.magic8ballshake;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.View;
import android.view.animation.*;
import android.widget.*;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorEvent;
 


public class MainActivity extends Activity implements SensorEventListener  {

	private Button b;
	private org.openintents.sensorsimulator.hardware.SensorManagerSimulator mSensorManager;
    private final org.openintents.sensorsimulator.hardware.Sensor mAccelerometer;
    //linear acceleration triggered by linear shake
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    //device has accelerometer sensor
    private boolean hasAccelerometer;
    //to count direction changes while shaking within shake duration
    private long startTime = 0;
    private int  moveCount = 0;
	//private final SensorEventListener shakeListener;
    
    public MainActivity() {
        //mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    	mSensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
    	mSensorManager.connectSimulator();
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); 
    }
    
    /*
     * Note : Whenever you are not connected to the simulator, you will get real device sensor data: 
     * the org.openintents.hardware.SensorManagerSimulator class transparently calls the SensorManager 
     * returned by the system service in this case.
     */

      	
		public void onSensorChanged(SensorEvent e)
		{
			/*
			 * "Sensor values are acceleration in the X, Y and Z axis, 
			 * where the X axis has positive direction toward the right side of the device, 
			 * the Y axis has positive direction toward the top of the device and 
			 * the Z axis has positive direction toward the front of the device. 
			 * The direction of the force of gravity is indicated by acceleration values in the X, Y and Z axes. 
			 * The typical case where the device is flat relative to the surface of the Earth appears as 
			 * -STANDARD_GRAVITY in the Z axis and X and Y values close to zero. 
			 * Acceleration values are given in SI units (m/s^2)."  
			 */
			
			//Select one of these three methods
			//depending on testing choose the one that
			//offers the best results
			// Parameters to consider:
			//shakeDuration ( currently 1 second)
			//number of direction changes ( currently 2)
			//shake with rotation? try getting gForce
			//Minimum  force threshold to register a shake (currently linearAcceleration > 2 or gForce > 2.5
			
		    // This uses the low-pass filter to filter out the gravity affects to get linear acceleration 
			//if linearAcceleration > 2  register a shake
			float  linearAcceleration = GetLinearShake(e);
			
			//overloaded method: pass minimum number of movements to register a shake
			//also pass maximum duration of shake = 1 second
			//and linearAcceleration > 2 to register a shake
			linearAcceleration = GetLinearShake(e, 2, 1000, 2);
			
			//Uses gravity : the gForce necessary to trigger shake event
			//gForce of 1 is no shake; if gForce > 2.5 register a shake
			//this will be useful if shake involves rotation
			float gForce = GetGForceShake(e);
			
			if(linearAcceleration > 2 )
			{
				Toast.makeText(getApplicationContext(), "Shake triggered with linear shake of magnitude" + linearAcceleration, 
						Toast.LENGTH_SHORT).show();
							
			}
			
			if(gForce > 2.5 )
			{
				Toast.makeText(getApplicationContext(), "Shake triggered with gForce shake of magnitude" + gForce, 
						Toast.LENGTH_SHORT).show();
							
			}
			
			
			
			/*
			float x = e.values[0];
			float y = e.values[1];
			float z = e.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel*0.9f + delta;
			*/
			//Send message that shake happened if mAccel > 2
		}
		

		public void onAccuracyChanged(Sensor sensor, int accuracy){
			
		//Ignore	
		}
		


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.SetupButton();
		
		/*
		mSensorManager = SensorManagerSimulator.getSystemService(this, Context.SENSOR_SERVICE);
		if(mSensorManager != null)
		{
			hasAccelerometer = mSensorManager.registerListener(this, mAccelerometer, SensorManagerSimulator.SENSOR_DELAY_UI);
					
			if(!hasAccelerometer)
			{
				//Send message that no accelerometer found
				Toast.makeText(getApplicationContext(), "accelerometer on device?:" + hasAccelerometer, Toast.LENGTH_SHORT).show();
				
			}
			} */
		enableAcceleromterListening();
			
			//For linear acceleration method
			mAccel = 0.0f;
			mAccelCurrent = SensorManager.GRAVITY_EARTH;
			mAccelLast = SensorManager.GRAVITY_EARTH;
		}
		



	@Override
	
	protected void onPause(){
		mSensorManager.unregisterListener(this);
		super.onPause();
	}
	
	@Override
	
	protected void onResume(){
		//mSensorManager.unregisterListener(shakeListener);
		super.onResume();
		
		mSensorManager.registerListener(this, mAccelerometer, SensorManagerSimulator.SENSOR_DELAY_UI);
	}
	
	
	private void enableAcceleromterListening()
	{
		//initialize the sensor manager
		mSensorManager = SensorManagerSimulator.getSystemService(this, Context.SENSOR_SERVICE);
		if(mSensorManager != null)
		{
			hasAccelerometer = mSensorManager.registerListener(this, mAccelerometer, SensorManagerSimulator.SENSOR_DELAY_UI);
					
			if(!hasAccelerometer)
			{
				//Send message that no accelerometer found
				Toast.makeText(getApplicationContext(), "accelerometer on device?:" + hasAccelerometer, Toast.LENGTH_SHORT).show();
				
			}
		}				
	}
	
	private void SetupButton()
	{
		b = (Button) this.findViewById(R.id.btn_animate);
		b.setOnClickListener(
				new Button.OnClickListener(){
					public void onClick(View v)
					{
						animateImageView();
					}
				});
				
		
	}
	
	private void animateImageView()
	{
		ImageView iv = (ImageView)this.findViewById(R.id.imageView1);
		iv.startAnimation(new ViewAnimation(0.0f, 0.0f));
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	//if mAccel > 2, trigger a shake
	public float GetLinearShake(SensorEvent e)
	{		
		float x = e.values[0];
		float y = e.values[1];
		float z = e.values[2];
		mAccelLast = mAccelCurrent;
		mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
		float delta = mAccelCurrent - mAccelLast;
		mAccel = mAccel*0.9f + delta;		
		return mAccel;
				
	}
	
	//e is the accelerometer sensor reading
	//minShakes is the minimum number of direction changes to trigger a shake
	//maxShakeDuration is the maximum duration of the shake
	//shakeTrigger is the minimum linear acceleration for each movement for it to count as a shake element
	public float GetLinearShake( SensorEvent e, int minShakes, int maxShakeDuration, float shakeTrigger )
	{
		float acceleration = GetLinearShake(e);
		if(acceleration > shakeTrigger )
		{
			long now = System.currentTimeMillis();
			
			if(startTime == 0)
			{
				startTime = now;
				
			}
			
			long elapsedTime = now - startTime;
			
			if(elapsedTime > maxShakeDuration)
			{
				//exceeds the shake duration -- start over again for next shake event
				ResetShakeDetection();
			}
			else{
				
				//Count the number of shakes
				moveCount++;
				if (moveCount > minShakes){					
					ResetShakeDetection();
					//return acceleration;
				}				
			}
		}
		if(acceleration > 2 && moveCount >= 2)
			return acceleration;
		else
			return 0.0f;
	}
	
	//Overall GForce  will be 1 for no movement
	//Should be 2.5 to register a shake
	public float GetGForceShake(SensorEvent e)
	{
		float x = e.values[0];
		float y = e.values[1];
		float z = e.values[2];
		
		//mAccelCurrent = earth's gravity
		//gX, gY and Gz  is the gForce along X, Y and Z axis
		float gX = x / mAccelCurrent;
		float gY = y /mAccelCurrent;
		float gZ = z / mAccelCurrent;
		
		return FloatMath.sqrt(gX * gX + gY * gY + gZ * gZ); 				
	}
	
	private void ResetShakeDetection() {
		
		startTime = 0;
		moveCount = 0;	
	}
	//}
	
	
}
