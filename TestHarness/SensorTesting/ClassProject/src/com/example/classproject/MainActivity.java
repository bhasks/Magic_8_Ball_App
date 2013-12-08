package com.example.classproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import android.hardware.Sensor;
import android.content.Context;
import android.util.FloatMath;


public class MainActivity extends Activity {
	
	
    private SensorManager mSensorManager;
    //private final Sensor mAccelerometer;
    //linear acceleration triggered by linear shake
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    //device has accelerometer sensor
    private boolean hasAccelerometer = false;
    
    private static final int ACCELERATION_THRESHOLD = 15000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAccel = 0.0f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
		enableAcceleromterListening();
		if(hasAccelerometer)
		{
			String yes = "YES";
			Toast.makeText(getApplicationContext(), 
					"Is there accelerometer on device?: " + yes, 
					Toast.LENGTH_SHORT).show();		
		}
		else
		{
			String no = "NO";
			Toast.makeText(getApplicationContext(), 
					"Is there accelerometer on device?: " + no, 
					Toast.LENGTH_SHORT).show();		
			
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		disableAcceleromterListening();
	}
	
	protected void onResume() {
		super.onResume();
		enableAcceleromterListening();
	}
	
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	private void enableAcceleromterListening()
	{
		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		hasAccelerometer = mSensorManager.registerListener(sensorEventListener, 
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);			
	}
	
	private void disableAcceleromterListening()
	{
		if(mSensorManager != null)
		{
		
			mSensorManager.unregisterListener(sensorEventListener,
					
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		}
							
	}
	
	private SensorEventListener sensorEventListener = new SensorEventListener()
	{		
		@Override
		public void onSensorChanged(SensorEvent event)
		{
			
			
			
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			/*
			Toast.makeText(getApplicationContext(), "x :" + x + "y: " + y + "z: " + z, 
					Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "acceleration:" + mAccel, 
					Toast.LENGTH_SHORT).show();
					*/
			
			/*
			//method based on High filter
			float lastX = 0;
			float lastY = 0;
			float lastZ = 0;
			
			float deltaX = Math.abs(lastX - x);
			float deltaY = Math.abs(lastY - y);
			float deltaZ = Math.abs(lastZ - z);
			
			float threshold = 0.9f; 
			if( (deltaX > threshold) && (deltaY > threshold)
				|| (deltaX > threshold) && (deltaZ > threshold)
					|| (deltaY > threshold) && (deltaZ > threshold))
			{
				Toast.makeText(getApplicationContext(), 
				"High Pass Filter Shake:  " +
				"Threshold = 0.9 triggered by\n  DeltaX: " + deltaX + " :Delta Y:" + deltaY + ":Delta Z:" + deltaZ, 
						Toast.LENGTH_SHORT).show();
			}
			//End Method High pass filter (too much noise)
			 
			 */
			
			
			/*Shake triggered by linear acceleration threshold*/
			
			//float x = event.values[0];
			//float y = event.values[1];
			//float z = event.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = x*x + y*y + z*z;
			mAccel = mAccelCurrent* (mAccelCurrent - mAccelLast);
			
			
					
			
			
			if(mAccel > ACCELERATION_THRESHOLD)
			{
				{
					Toast.makeText(getApplicationContext(), 
							"Shake triggered with acceleration threshold  of 15000\n  Acceleration  recorded = " + mAccel, 
							Toast.LENGTH_SHORT).show();
								
				}
			}
			//end method linear 
			
			
			/*
			//gravity method
			//mAccelCurrent = earth's gravity
			//gX, gY and gZ  is the gForce along X, Y and Z axis
			float gX = x / mAccelCurrent;
			float gY = y /mAccelCurrent;
			float gZ = z / mAccelCurrent;
			
			float gAccel =   FloatMath.sqrt(gX * gX + gY * gY + gZ * gZ);
			if(gAccel > 1.5)
			{
				Toast.makeText(getApplicationContext(), 
					"Shake triggered with gForce threshold  of 1.5\n  GForce  recorded = " + gAccel, 
					Toast.LENGTH_SHORT).show();
			}
			//End method GForce
			 * 		
			 */
		}
	
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy)
		{
			//ignore
		}

};
}
