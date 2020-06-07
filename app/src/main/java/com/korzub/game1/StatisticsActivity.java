package com.korzub.game1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class StatisticsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter gameResultAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<GameResult> gameResultArrayList;

    DatabaseHandler myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setTopText("Game history");

        myDb=new DatabaseHandler(this);

        this.gameResultArrayList=myDb.getAllGameResults();

        setupStatistics();


    }

    protected void setTopText(String text){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height/6);

        TextView textView= findViewById(R.id.top_text);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.orange));
        textView.setTextSize(height/30);


        textView.setShadowLayer((height+width)/240,width/120,height/120,getResources().getColor(R.color.black));
    }

    protected void setupStatistics()
    {

        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        gameResultAdapter = new GameResultAdapter(gameResultArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gameResultAdapter);

    }

}