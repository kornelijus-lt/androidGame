package com.korzub.game1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    DatabaseHandler myDb;
    int difficulty;

    LinearLayout difficultyLabelLayout;
    TextView difficultyLabel;
    TextView difficultyIndicator;

    LinearLayout difficultyLayout;

    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        myDb=new DatabaseHandler(this);
        this.difficulty=myDb.getDifficulty(1);
        setupSettings();

        setTopText("Settings");
    }

    protected void setTopText(String text){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.heightPixels;

        TextView textView= findViewById(R.id.top_text);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.orange));
        textView.setTextSize(height/30);


        textView.setShadowLayer((height+width)/240,width/120,height/120,getResources().getColor(R.color.black));
    }

    protected void setupSettings()
    {
        LinearLayout mainLayout=(LinearLayout)findViewById(R.id.menu_layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        //----------------------------------------------------------------------------------------
        //Dificulty label
        LinearLayout.LayoutParams difficultyLabelLayoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,3);
        difficultyLabelLayoutParams.setMargins(width/6,height/6,width/6,0);

        difficultyLabelLayout=new LinearLayout(this);
        difficultyLabelLayout.setLayoutParams(difficultyLabelLayoutParams);

        //Label itself
        LinearLayout.LayoutParams difficultyLabelParams=new LinearLayout.LayoutParams(
                width/2,
                height/8);


        difficultyLabel=new TextView(this);
        difficultyLabel.setLayoutParams(difficultyLabelParams);
        difficultyLabel.setText("Difficulty: ");

        difficultyLabel.setTextColor(getResources().getColor(R.color.orange));
        difficultyLabel.setTextSize(height/50);


        difficultyLabel.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));

        //Indicator
        LinearLayout.LayoutParams difficultyIndexParams=new LinearLayout.LayoutParams(
                width/2,
                height/8);

        difficultyIndicator=new TextView(this);
        difficultyIndicator.setLayoutParams(difficultyIndexParams);

        difficultyIndicator.setTextColor(getResources().getColor(R.color.yellow));
        difficultyIndicator.setTextSize(height/50);


        difficultyIndicator.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));


        difficultyLabelLayout.addView(difficultyLabel);
        difficultyLabelLayout.addView(difficultyIndicator);

        mainLayout.addView(difficultyLabelLayout);

        //----------------------------------------------------------------------------------------
        //Difficulkty seekBar

        LinearLayout.LayoutParams difficultyLayoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,2);
        difficultyLayoutParams.setMargins(0,0,0,0);


        difficultyLayout=new LinearLayout(this);
        difficultyLayout.setLayoutParams(difficultyLayoutParams);



        LinearLayout.LayoutParams seekBarParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        seekBarParams.setMargins(width/6,0,width/6,height/2);

        seekBar=new SeekBar(this);
        seekBar.setMax(2);
        seekBar.setLayoutParams(seekBarParams);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);


        difficultyLayout.addView(seekBar);



        //difficulty indicator text selection
        seekBar.setProgress(difficulty);

        if(this.difficulty==0)
        {
            difficultyIndicator.setText("Easy");
        }
        else if (this.difficulty==1)
        {
            difficultyIndicator.setText("Normal");
        }
        else if(this.difficulty==1)
        {
            difficultyIndicator.setText("Hard");
        }


        /*
        LinearLayout.LayoutParams difficultySpinnerParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        String[] options=new String[]{"Easy","Normal","Hard"};
        Spinner difficultySpinner=new Spinner(this);
        difficultySpinner.setLayoutParams(difficultySpinnerParams);
        ArrayAdapter<String> myAdaper=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,options);

        difficultySpinner.setAdapter(myAdaper);

        difficultyLayout.addView(difficultySpinner);*/



        mainLayout.addView(difficultyLayout);

    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            Log.d("i","i: "+i);
            difficulty=i;
            if(i==0)
            {
                difficultyIndicator.setText("Easy");
            }
            else if (difficulty==1)
            {
                difficultyIndicator.setText("Normal");
            }
            else if(difficulty==2)
            {
                difficultyIndicator.setText("Hard");
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
                myDb.saveDifficulty(1,difficulty);
        }
    };
}