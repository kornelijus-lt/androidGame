package com.korzub.game1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {

    DatabaseHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setTopText("Profile");

        myDb=new DatabaseHandler(this);

        setupProfile();

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


        textView.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));
    }

    protected void setupProfile()
    {
        LinearLayout mainLayout=(LinearLayout)findViewById(R.id.menu_layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        ArrayList<String> profileData= myDb.getProfileData(1);

        //Name linear layout for label and textEdit
        LinearLayout.LayoutParams nameLayoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,2);
        nameLayoutParams.setMargins(0,height/12,0,0);




        LinearLayout nameLayout=new LinearLayout(this);
        nameLayout.setLayoutParams(nameLayoutParams);

        //Name edit text label
        LinearLayout.LayoutParams editParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        TextView nameEditLabel=new TextView(this);
        nameEditLabel.setText("Player name:  ");
        nameEditLabel.setLayoutParams(editParams);



        nameEditLabel.setTextColor(getResources().getColor(R.color.orange));
        nameEditLabel.setTextSize(height/50);

        nameEditLabel.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));





        //Name edit text
        EditText nameEdit=new EditText(this);
        nameEdit.setId(0);
        nameEdit.setEms(11);
        nameEdit.setMaxEms(11);
        nameEdit.setMinEms(11);

        int maxLength = 20;
        nameEdit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        String name=profileData.get(0);
        nameEdit.setText(name);






        nameEdit.setLayoutParams(editParams);


        nameEdit.setTextColor(getResources().getColor(R.color.yellow));
        nameEdit.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));
        nameEdit.setTextSize((height+width)/100);

        //first row finisher
        nameLayout.addView(nameEditLabel);
        nameLayout.addView(nameEdit);
        //------------------------------------------------------------------
        //Win/Loss linear layout for labels
        LinearLayout.LayoutParams statsParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout statsLayout=new LinearLayout(this);
        statsLayout.setLayoutParams(statsParams);
        statsLayout.setWeightSum(2);
        String wins=profileData.get(1);
        String losses=profileData.get(2);

        //Win text view



        LinearLayout.LayoutParams ratioParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,2);




        TextView winText=new TextView(this);
        winText.setLayoutParams(ratioParams);
        winText.setText("Wins :  "+wins);
        winText.setTextColor(getResources().getColor(R.color.orange));
        winText.setTextSize(height/50);
        winText.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));
        winText.setGravity(1);
        winText.setWidth(width/2);

        TextView lossText=new TextView(this);
        lossText.setLayoutParams(ratioParams);
        lossText.setText("Losses :  "+losses);
        lossText.setTextColor(getResources().getColor(R.color.orange));
        lossText.setTextSize(height/50);
        lossText.setShadowLayer((height+width)/240,
                width/120,height/120,getResources().getColor(R.color.black));
        lossText.setGravity(1);
        lossText.setWidth(width/2);

        //second row finisher

        //statsLayout.addView(winText);
        //statsLayout.addView(lossText);

        statsLayout.addView(winText);
        statsLayout.addView(lossText);

        //-----------------------------------------------------------
        //Save button

        LinearLayout.LayoutParams buttonLayoutParams=new LinearLayout.LayoutParams(
                width/2,
                height/8,2);
        buttonLayoutParams.setMargins(0,height/8,0,0);

        LinearLayout buttonLayout=new LinearLayout(this);
        buttonLayout.setLayoutParams(buttonLayoutParams);
        buttonLayout.setWeightSum(2);


        LinearLayout.LayoutParams buttonParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,2);

        Button saveButton=new Button(this);
        saveButton.setLayoutParams(buttonParams);
        saveButton.setText("Save player name");
        saveButton.setBackground(getResources().getDrawable(R.drawable.mybutton));

        buttonLayout.addView(saveButton);



        //final
        mainLayout.addView(nameLayout);
        mainLayout.addView(statsLayout);
        mainLayout.addView(buttonLayout);



        saveButton.setOnClickListener(saveButtonClick);

        //TextView nameInput=new TextView()
    }

    View.OnClickListener saveButtonClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            EditText editText=(EditText)findViewById(0);
            String username=editText.getText().toString();
            myDb.updateProfileName(username);
        }
    };



}