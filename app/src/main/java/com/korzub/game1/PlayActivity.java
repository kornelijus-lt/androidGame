package com.korzub.game1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlayActivity extends AppCompatActivity {

    Board playerBoard;
    Board enemyBoard;

    LinearLayout gameLayout;
    LinearLayout playerLayout;
    LinearLayout enemyLayout;

    int[] lastTouchDownXY;
    int[] lastTouchUpXY;
    int[] lastTouchMoveXY;


    int[] enemyLastTouchDownXY;
    int[] enemyLastTouchUpXY;
    int[] enemyLastTouchMoveXY;

    //Moves: player - 1, enemy - 2
    int move;

    boolean started;

    //easy-1 medium-2 hard-3
    int dificulty;
    int turns;
    String dificultyString;

    DatabaseHandler myDb;


    static int dim=10;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        myDb=new DatabaseHandler(this);

        this.dificulty=myDb.getDifficulty(1);
        this.turns=0;

        if(this.dificulty==0)
        {
            this.dificultyString="Easy";
        }
        else if(this.dificulty==1)
        {
            this.dificultyString="Normal";
        }
        else if(this.dificulty==2)
        {
            this.dificultyString="Hard";
        }


        initializeGameLayout();
    }




    View.OnTouchListener playerTouchListener=new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event){

            if(started==false) {

                //gameLayout.addView(enemyLayout);
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

                    int X = (int) event.getX() / (v.getWidth() / dim);
                    int Y = (int) event.getY() / (v.getHeight() / dim);

                    lastTouchDownXY[0] = Y;
                    lastTouchDownXY[1] = X;

                    if (X < dim && Y < dim) {
                        Log.i("TAG", "X:" + X + " Y:" + Y);
                    }
                }
                if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {

                    int X = (int) event.getX() / (v.getWidth() / dim);
                    int Y = (int) event.getY() / (v.getHeight() / dim);

                    lastTouchMoveXY[0] = Y;
                    lastTouchMoveXY[1] = X;
                    if (X < dim && Y < dim) {
                        //Log.i("TAG","X:"+X+" Y:"+Y);
                    }

                }
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {

                    int X = (int) event.getX() / (v.getWidth() / dim);
                    int Y = (int) event.getY() / (v.getHeight() / dim);

                    lastTouchUpXY[0] = Y;
                    lastTouchUpXY[1] = X;

                    if (lastTouchUpXY[0] < dim && lastTouchUpXY[1] < dim) {


                        //See if attempt to TURN
                        if (lastTouchUpXY[0] == lastTouchDownXY[0] && lastTouchUpXY[1] == lastTouchDownXY[1]) {
                            Log.i("TAG", "Y:" + lastTouchUpXY[0] + " X:" + lastTouchUpXY[1]);
                            int identity = playerBoard.getMatrixElement(lastTouchUpXY[0], lastTouchUpXY[1]);


                            if (identity > 0) {
                                ArrayList<int[]> coordinates = playerBoard.getShipCoordinates(Y, X,identity);
                                ArrayList<int[]> newCoordinates = playerBoard.getTurnCoordinates(coordinates, identity);

                                if (newCoordinates.size() != 0) {

                                    ArrayList<Integer> oldColors=new ArrayList<>();
                                    ArrayList<Integer> newColors=new ArrayList<>();

                                    for(int i=0;i<newCoordinates.size();i++)
                                    {
                                        oldColors.add(0);
                                        newColors.add(1);
                                    }


                                    playerBoard.writeNewCoordinates(coordinates, 0,oldColors);
                                    playerBoard.writeNewCoordinates(newCoordinates, identity,newColors);
                                    playerBoard.invalidate();
                                }


                            }
                        }

                        //See if atempt to MOVE
                        if (lastTouchUpXY[0] != lastTouchDownXY[0] || lastTouchUpXY[1] != lastTouchDownXY[1]) {
                            int identity = playerBoard.getMatrixElement(lastTouchDownXY[0], lastTouchDownXY[1]);

                            if (identity > 0) {

                                ArrayList<int[]> originalCoordinates = playerBoard.getShipCoordinates(lastTouchDownXY[0], lastTouchDownXY[1],identity);
                                int moveY = lastTouchDownXY[0] - lastTouchUpXY[0];
                                int moveX = lastTouchDownXY[1] - lastTouchUpXY[1];

                                ArrayList<int[]> newCoordinates = new ArrayList<int[]>();


                                for (int i = 0; i < originalCoordinates.size(); i++) {


                                    int[] coordinate = {originalCoordinates.get(i)[0] - moveY, originalCoordinates.get(i)[1] - moveX};
                                    newCoordinates.add(coordinate);
                                    if (coordinate[0] < 0 || coordinate[0] >= dim || coordinate[1] < 0 || coordinate[1] >= dim) {

                                        newCoordinates.clear();
                                        break;
                                    }
                                }
                                if (newCoordinates.size() != 0) {
                                    if (playerBoard.isBlocked(newCoordinates, identity) == false) {


                                        ArrayList<Integer> oldColors=new ArrayList<>();
                                        ArrayList<Integer> newColors=new ArrayList<>();

                                        for(int i=0;i<newCoordinates.size();i++)
                                        {
                                            oldColors.add(0);
                                            newColors.add(1);
                                        }

                                        playerBoard.writeNewCoordinates(originalCoordinates, 0,oldColors);
                                        playerBoard.writeNewCoordinates(newCoordinates, identity,newColors);
                                        playerBoard.invalidate();
                                    }
                                }
                            }

                        }

                    }

                }

            }

            return false;
        }
    };


    View.OnClickListener playerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };


    View.OnTouchListener enemyTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Log.i("View id: "," "+v.getId());

            if(started==true && move==1) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

                    int X = (int) event.getX() / (v.getWidth() / dim);
                    int Y = (int) event.getY() / (v.getHeight() / dim);

                    lastTouchDownXY[0] = Y;
                    lastTouchDownXY[1] = X;

                    /*
                    if (X < dim && Y < dim && X >= 0 && Y >= 0) {
                        Log.i("TAG", "X:" + X + " Y:" + Y);
                    }*/



                }




                if (event.getActionMasked() == MotionEvent.ACTION_UP) {

                    int X = (int) event.getX() / (v.getWidth() / dim);
                    int Y = (int) event.getY() / (v.getHeight() / dim);

                    lastTouchUpXY[0] = Y;
                    lastTouchUpXY[1] = X;

                    /*if (X < dim && Y < dim && X >= 0 && Y >= 0) {
                        Log.i("TAG", "X:" + X + " Y:" + Y);
                    }*/
                }
            }
            return false;
        }


    };

    View.OnClickListener enemyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(started==true && move==1)
            {
                if(lastTouchDownXY[0]==lastTouchUpXY[0]&&lastTouchDownXY[1]==lastTouchUpXY[1])
                {
                    Log.i("TAG", "Y:" + lastTouchDownXY[0] + " X:" + lastTouchDownXY[1]);
                    int Y=lastTouchUpXY[0];
                    int X=lastTouchUpXY[1];

                    if(enemyBoard.getBoardElement(Y,X).getColor()==0)
                    {
                        turns=turns+1;

                        if(enemyBoard.successfulHit(Y,X)==true)
                        {

                            int identity=enemyBoard.getMatrixElement(Y,X);
                            enemyBoard.registerHit(Y,X,identity);
                            enemyBoard.invalidate();

                            if(gameOver(enemyBoard)==true)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(PlayActivity.this);
                                builder.setCancelable(true);
                                builder.setMessage("You win!");

                                myDb.addWin(1);
                                myDb.saveGameResult(turns,dificultyString,"Win");


                                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                AlertDialog dialog=builder.show();
                                TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
                                messageView.setGravity(Gravity.CENTER);
                                move=3;
                            }

                        }
                        else {
                            move=2;

                            int identity=enemyBoard.getMatrixElement(Y,X);
                            enemyBoard.registerMiss(Y,X,identity);
                            enemyBoard.invalidate();

                            enemyAction();

                            move=1;
                            if(gameOver(playerBoard)==true)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(PlayActivity.this);
                                builder.setCancelable(true);
                                builder.setMessage("You lose!");

                                myDb.addLoss(1);
                                myDb.saveGameResult(turns,dificultyString,"Loss");

                                builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                AlertDialog dialog=builder.show();
                                TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
                                messageView.setGravity(Gravity.CENTER);
                                move=3;
                                move=3;
                            }
                        }
                        //move=2;
                    }
                }
            }
        }
    };
    View.OnClickListener startBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(started==false)
            {
                ViewGroup parentGroup= (ViewGroup)v.getParent();
                parentGroup.removeAllViews();
                parentGroup.addView(enemyBoard);
                started=true;
            }

        }
    };

    public void enemyAction(){

        int Y,X;

        int times=1;

        if(dificulty==0)
        {
            times=1;
        }
        else if(dificulty==1)
        {
            times=3;
        }
        if(dificulty==2)
        {
            times=6;
        }

        while(true){
            Y = ThreadLocalRandom.current().nextInt(0, 10);
            X= ThreadLocalRandom.current().nextInt(0, 10);

            for(int i=0;i<times;i++)
            {
                if(playerBoard.successfulHit(Y,X)==true){
                    break;
                }
                else
                    {
                    Y = ThreadLocalRandom.current().nextInt(0, dim);
                    X= ThreadLocalRandom.current().nextInt(0, dim);
                }
            }
            if(playerBoard.successfulHit(Y,X)==true)
            {
                int identity=playerBoard.getMatrixElement(Y,X);
                playerBoard.registerHit(Y,X,identity);
                playerBoard.invalidate();
            }
            else
            {
                int identity=playerBoard.getMatrixElement(Y,X);
                playerBoard.registerMiss(Y,X,identity);
                playerBoard.invalidate();
                return;
            }

        }
    }

    private boolean gameOver(Board board)
    {
        int alive=0;
        for(int i=0;i<dim;i++){
            for(int j=0;j<dim;j++){
                if((board.getBoardElement(i,j).getColor()==0 || board.getBoardElement(i,j).getColor()==1) &&board.getMatrixElement(i,j)>0){
                    alive++;
                }
            }
        }
        if(alive==0)
        {
            return true;
        }
        return false;
    }

    private void initializeGameLayout(){


        //Start of name adding

        LinearLayout namesLayout=findViewById(R.id.names_layout);

        //----

        LinearLayout playerNameLayout=new LinearLayout(this);
        LinearLayout.LayoutParams playerNameLayoutParams= new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);


        TextView playerName=new TextView(this);

        ArrayList<String> profileData=myDb.getProfileData(1);




        playerName.setText(profileData.get(0));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        playerName.setTextColor(getResources().getColor(R.color.yellow));
        playerName.setTextSize(height/50);


        playerName.setShadowLayer((height+width)/240,width/120,height/120,getResources().getColor(R.color.black));


        playerNameLayout.addView(playerName);



        //------------------------------------------------------------------------------------------

        LinearLayout enemyNameLayout=new LinearLayout(this);
        LinearLayout.LayoutParams enemyNameLayoutParams= new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);

        TextView enemyName=new TextView(this);
        enemyName.setText("Computer");
        enemyName.setTextColor(getResources().getColor(R.color.yellow));
        enemyName.setTextSize(height/50);


        enemyName.setShadowLayer((height+width)/240,width/120,height/120,getResources().getColor(R.color.black));
        enemyNameLayout.addView(enemyName);

        //----
        namesLayout.addView(playerNameLayout,playerNameLayoutParams);
        namesLayout.addView(enemyNameLayout,enemyNameLayoutParams);

        //End of name adding



        gameLayout=findViewById(R.id.game_layout);

        //Player
        playerLayout=new LinearLayout(this);
        int playerId=1;
        playerLayout.setId(playerId);

        playerLayout.setClipChildren(Boolean.TRUE);


        //Player layout click and touch events
        lastTouchDownXY=new int[2];
        lastTouchMoveXY=new int[2];
        lastTouchUpXY=new int[2];
        playerLayout.setOnTouchListener(playerTouchListener);
        playerLayout.setOnClickListener(playerClickListener);


        playerLayout.setClickable(Boolean.TRUE);

        LinearLayout.LayoutParams playerLayoutParams= new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);

        playerBoard=new Board(this,dim,playerLayout,1);


        playerLayout.addView(playerBoard);

        //------------------------------------------------------------------------------------------
        //AI
        enemyLayout=new LinearLayout(this);
        int enemyId=2;
        enemyLayout.setId(enemyId);

        enemyLayout.setClipChildren(Boolean.TRUE);

        //SETUP CLICK LATER --------!

        enemyLastTouchDownXY=new int[2];
        enemyLastTouchMoveXY=new int[2];
        enemyLastTouchUpXY=new int[2];

        enemyLayout.setOnTouchListener(enemyTouchListener);
        enemyLayout.setOnClickListener(enemyClickListener);

        enemyLayout.setClickable(Boolean.TRUE);
        enemyLayout.setClipChildren(true);

        LinearLayout.LayoutParams enemyLayoutParams= new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);


        enemyBoard=new Board(this,dim,enemyLayout,2);



        //Add start button

        Button startBtn=new Button(this);
        startBtn.setBackgroundResource(R.drawable.mybutton);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
        btnParams.gravity=Gravity.CENTER;
        startBtn.setLayoutParams(btnParams);
        startBtn.setText("Start game");
        startBtn.setClickable(true);
        startBtn.setOnClickListener(startBtnListener);
        startBtn.setId(View.generateViewId());


        enemyLayout.addView(startBtn);

        //Add layouts to game layout

        gameLayout.addView(playerLayout,playerLayoutParams);
        gameLayout.addView(enemyLayout,enemyLayoutParams);


        //enemyLayout.setLayoutParams(enemyLayoutParams);
        //enemyLayout.addView(enemyBoard);


        this.move=1;
        this.started=false;
    }


}
