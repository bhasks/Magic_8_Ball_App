package com.example.randomtest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.Context;
import android.util.FloatMath;

public class RandomTestActivity extends Activity {
	
	private static final String TAG = "Magic8Ball Activity";
	private List<String> fileNameList; //image names
	private Random random;
	private boolean isAnimationEnded;
	
	private ImageView answerImageView; //displays the oracle
	private RelativeLayout relativeLayout;
	private int numberOfAnswers; //20 possible answers
	
	//For Shake handler
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    //device has accelerometer sensor
    private boolean hasAccelerometer = false;    
    private static final int ACCELERATION_THRESHOLD = 15000;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_test);
		
		relativeLayout = (RelativeLayout)this.findViewById(R.id.relativeLayout);
		Resources res = getResources(); //resource handle
		
		//Set the background to the start template
		try
		{
			Drawable drawable = res.getDrawable(R.drawable.hdpi_main_template);
			//setBackgroundDrawable is deprecated as of Android v18	   
			relativeLayout.setBackground(drawable); 
		}
		catch(Exception e)
		{
			Log.e(TAG, "Error opening image file name", e);
			
		}
		
		//The placeholder for the image to be animated depending on random number
		answerImageView = (ImageView)findViewById(R.id.answerImageView);
		//Set the image to invisible 
		//This view is invisible, but it still takes up space for layout purposes
		answerImageView.setVisibility(View.INVISIBLE);
		
		//buttons
		Button answerButton = (Button) findViewById(R.id.btn_answer);
		answerButton.setOnClickListener(answerButtonListener);
		Button startButton = (Button) findViewById(R.id.btn_start);
		startButton.setOnClickListener(startButtonListener);
		Button helpButton = (Button) findViewById(R.id.btn_help);
		helpButton.setOnClickListener(helpButtonListener);
		Button quitButton = (Button)findViewById(R.id.btn_quit);
		quitButton.setOnClickListener(quitButtonListener);
		
		//accelerometer sensor stuff
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
	
	public OnClickListener answerButtonListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			//First make sure to change bg from 8 ball to oracle viewport
			resetOracleViewPort();
			int answerNum = generateRandomAnswer();
			Toast.makeText(getApplicationContext(), 
					"Random number is: " + answerNum,
					Toast.LENGTH_SHORT).show();	
			animateImageView(answerNum);
		}
	};
	
	public void resetOracleViewPort()
	{
		try
		{
			//relativeLayout = (RelativeLayout)this.findViewById(R.id.relativeLayout);
			Resources res = getResources(); //resource handle
			//Need to change this from the 8 ball to the blue side

			Drawable drawable = res.getDrawable(R.drawable.hdpi_template_1); 
			relativeLayout.setBackground(drawable);	
		}
		catch(Exception e)
		{
			Log.e(TAG, "Error retrieving resource", e);
			
		}		
	}
	
	
	public OnClickListener startButtonListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			try
			{
			//relativeLayout = (RelativeLayout)this.findViewById(R.id.relativeLayout);
			Resources res = getResources(); //resource handle
			//Need to change this to the start template image

			Drawable drawable = res.getDrawable(R.drawable.hdpi_template_1); 
			relativeLayout.setBackground(drawable);
			}
			catch(Exception e)
			{
				Log.e(TAG, "Error retrieving resource", e);
				
			}			
		}
	};
	
	public OnClickListener quitButtonListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Toast.makeText(getApplicationContext(), 
					"Touch your device's menu button to quit",
					Toast.LENGTH_LONG).show();	
		}
	};
	
	//TODO: Depending on accelerometer detected or not send 
	//Toast message to shake or\not or press answer button
	public OnClickListener helpButtonListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if(hasAccelerometer)
			{
				Toast.makeText(getApplicationContext(), 
					"Shake your phone or tablet\n or else touch the answer button to see into your future!.",
					Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), 
						"Touch the answer button to see into your future!\n " +
						"If you have accelerometer sensor, enabled, you can also shake your phone\n" +
								"to peek into your future!",				
						Toast.LENGTH_LONG).show();				
			}
		}
	};
	
	
	public int generateRandomAnswer()
	{
		
		int randomindexnum = (int)(Math.random() *20);
		return randomindexnum;
	}
	
	public void  animateImageView(int oracle)
	{
		BallAnimationListener listener = new BallAnimationListener();
		boolean isAnimationRunning = listener.getIsAnimationRunning();
	   if(!isAnimationRunning)
		{
		   
			   ImageView iv = (ImageView)this.findViewById(R.id.answerImageView);
			 //To prevent race conditions from crashing the app
			   try
			   {
			   //Set the image depending on the answer
			   Resources res = getResources(); //resource handle
		
			   int resid = res.getIdentifier("answer" + Integer.toString(oracle), "drawable", getPackageName() );
			   Drawable drawable = res.getDrawable( resid );
			   Toast.makeText(getApplicationContext(), 
				"Image Resource Id is" + resid,
				Toast.LENGTH_SHORT).show();	
			   iv.setImageDrawable(drawable);
		   }
		   catch(Exception e)
		   {
			 Log.e(TAG, "Error retrieving resouce " + e);  
		   }
		   
		iv.setVisibility(View.VISIBLE);
		Toast.makeText(getApplicationContext(), 
				"Image loaded. Can you see it?",
				Toast.LENGTH_SHORT).show();	
			iv.startAnimation(new magicBallAnimation(0.0f, 0.0f));
		}
		//TODO; set the clock and wait for 2500mseconds
	   //calling Reset from animation ended listener instead
		/*
	   isAnimationEnded = listener.getisAnimationEnded();
		if(isAnimationEnded)
		{
			
			resetMagic8Ball();
		}
		*/		
		
	}
	
	public void resetMagic8Ball()
	{	
		resetOracleViewPort();
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		disableAcceleromterListening();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		enableAcceleromterListening();
	}
	
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
						
			/*Shake triggered by linear acceleration threshold*/			
			mAccelLast = mAccelCurrent;
			mAccelCurrent = x*x + y*y + z*z;
			mAccel = mAccelCurrent* (mAccelCurrent - mAccelLast);			
			
			if(mAccel > ACCELERATION_THRESHOLD)
			{
				{
					resetOracleViewPort();
					Toast.makeText(getApplicationContext(), 
							"Shake triggered with acceleration threshold  of 15000\n  Acceleration  recorded = " + mAccel, 
							Toast.LENGTH_SHORT).show();
					int answerNum = generateRandomAnswer();
					Toast.makeText(getApplicationContext(), 
							"Random number is: " + answerNum,
							Toast.LENGTH_SHORT).show();	
					animateImageView(answerNum);								
				}
			}

		}
			
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy)
		{
			//ignore
		}

};


	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_test, menu);
		return true;
	}
	*/

}
