package com.example.animation_test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import android.view.View.OnClickListener;
import android.content.res.AssetManager;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

public class AnimationTestActivity extends Activity {
	
	private static final String TAG = "Magic8Ball Activity";
	private List<String> fileNameList; //image names
	private Random random;
	private Handler handler;
	private boolean isAnimationEnded;
	
	private ImageView answerImageView; //displays the oracle
	//private Animation oracleAnimation;
	
	
	private int numberOfAnswers; //20 possible answers

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_test);
		
		fileNameList = new ArrayList<String>(); 
		random = new Random();
		handler = new Handler();
		
		answerImageView = (ImageView)findViewById(R.id.answerImageView);
		Button animateButton = (Button) findViewById(R.id.btn_animate);
		animateButton.setOnClickListener(animateButtonListener);
		
		/*
		AssetManager am = getAssets();
		try
		{
		InputStream is = am.open("Images\\answer.png");
		Drawable answer = Drawable.createFromStream(is, null);
		answerImageView.setImageDrawable(answer);
		Toast.makeText(getApplicationContext(), 
				"Images folder has: " + numberOfAnswers + " images", 
				Toast.LENGTH_SHORT).show();	
		}
		catch(IOException e)
		{
			Log.e(TAG, "Error opening image file names", e);
			
		}
		
		//loadStartImage();
		
		//resetMagic8Ball();
		 * 
		 */
	}
	
	public OnClickListener animateButtonListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			animateImageView();
		}
	};
	
	private void animateImageView()
	{
		ImageView iv = (ImageView)this.findViewById(R.id.answerImageView);
        //magicBallAnimation oracleAnimation = new magicBallAnimation(0.0f, 0,0f);
		BallAnimationListener listener = new BallAnimationListener();
		
		iv.startAnimation(new magicBallAnimation(0.0f, 0.0f));
		isAnimationEnded = listener.getisAnimationEnded();
		
	}
	}
	
	
	
/*	
	private void resetMagic8Ball()
	{
		AssetManager assets = getAssets();
		try {
			String[] paths = assets.list("Images");
			fileNameList.clear();
			for(String path: paths)
				fileNameList.add(path);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.e(TAG, "Error loading image file names", e);
			
			int numberOfAnswers = fileNameList.size();
			Toast.makeText(getApplicationContext(), 
					"Images folder has: " + numberOfAnswers + " images", 
					Toast.LENGTH_SHORT).show();	
			loadStartImage();
			
		}
	}
	*/
	/*
	private void loadStartImage()
	{
		AssetManager assets = getAssets();
		try {
			String[] paths = assets.list("Images");
			
			//fileNameList.clear();
			for(String path: paths)
			{
				fileNameList.add(path);
				Toast.makeText(getApplicationContext(), 
						"Images folder has: " + path + " as imageName", 
						Toast.LENGTH_SHORT).show();
				
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.e(TAG, "Error loading image file names", e);
			
		}
			
			int numberOfAnswers = fileNameList.size();
			Toast.makeText(getApplicationContext(), 
					"Images folder has: " + numberOfAnswers + " images", 
					Toast.LENGTH_SHORT).show();
		String imageName = fileNameList.remove(0);
		
		//answerImageView = (ImageView)findViewById(R.id.answerImageView);
		//AssetManager assets = getAssets();
		InputStream stream;
		try
		{
			stream = assets.open(imageName + "/" + ".png");
			Drawable answer = Drawable.createFromStream(stream, imageName);
			answerImageView.setImageDrawable(answer);			
		}
		catch(IOException e)
		{
			Log.e(TAG, "Error loading " + imageName);
		}
		answerImageView.setVisibility(View.VISIBLE);
		Toast.makeText(getApplicationContext(), 
				"Can you see image?: " , 
				Toast.LENGTH_SHORT).show();	
	}
	*/

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.animation_test, menu);
		return true;
	}
	*/

