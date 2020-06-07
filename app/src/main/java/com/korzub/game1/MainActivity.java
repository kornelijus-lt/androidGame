package com.korzub.game1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    DatabaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // retrieve the position of the DecorView
        Rect visibleFrame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleFrame);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        // check if the DecorView takes the whole screen vertically or horizontally
        boolean isRightOfContent = dm.heightPixels == visibleFrame.bottom;
        boolean isBelowContent   = dm.widthPixels  == visibleFrame.right;

        setTopText("Simple Battleships");


        myDb=new DatabaseHandler(this);


        setupMainMenu();
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

    protected void setupMainMenu(){

        int buttonCount=5;

        LinearLayout menuLayout=findViewById(R.id.menu_layout);

        //Play button
        Button playButton=findViewById(R.id.play_btn);


        LinearLayout.LayoutParams buttonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT+700,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(0,10,0,0);

        playButton.setLayoutParams(buttonParams);
        playButton.setBackground(getResources().getDrawable(R.drawable.mybutton));


        //Settings button

        Button settingsButton=findViewById(R.id.settings_btn);


        settingsButton.setLayoutParams(buttonParams);
        settingsButton.setBackground(getResources().getDrawable(R.drawable.mybutton));

        //About button
/*
        Button aboutButton=findViewById(R.id.about_btn);


        aboutButton.setLayoutParams(buttonParams);
        aboutButton.setBackground(getResources().getDrawable(R.drawable.mybutton));
*/
        //Statistics button

        Button statisticsButton=findViewById(R.id.statistics_btn);


        statisticsButton.setLayoutParams(buttonParams);
        statisticsButton.setBackground(getResources().getDrawable(R.drawable.mybutton));


        //Profile button

        Button profileButton=findViewById(R.id.profile_btn);


        profileButton.setLayoutParams(buttonParams);
        profileButton.setBackground(getResources().getDrawable(R.drawable.mybutton));







        //playButton.setBackgroundColor(getResources().getColor(R.color.orange));

        /*
        LinearLayout gameLayout=findViewById(R.id.menu_layout);

        //Play button

        Button playButton = new Button(this);

        LinearLayout.LayoutParams playButtonParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)


        playButton.setText("Žaisti");
        playButton.setLayoutParams(playButtonParams);

        playButton.setOnClickListener(gameLayout);*/


    }

    protected void onClick(View view){
        if(view.getId()==R.id.play_btn){
            Intent intent=new Intent(this,PlayActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.settings_btn){
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        /*if(view.getId()==R.id.about_btn){
            Intent intent=new Intent(this,AboutActivity.class);
            startActivity(intent);
        }*/
        if(view.getId()==R.id.profile_btn){
            Intent intent=new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.statistics_btn){
            Intent intent=new Intent(this,StatisticsActivity.class);
            startActivity(intent);
        }

    }
}
