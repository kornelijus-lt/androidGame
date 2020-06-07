package com.korzub.game1;

import android.content.Context;
import android.graphics.Canvas;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Board extends View{

    Context context;

    View currentView;


    protected int boardDim;

    protected Boolean isNewGame;

    protected Square board[][];
    protected int matrix[][];

    protected int ships[];
    protected int identities[];


    //Player-1 or Enemy-2
    protected int boardType;


    public Board(Context context,int boardDim,View currentView,int boardType){

        super(context);
        this.context=context;

        this.boardDim=boardDim;

        this.currentView=currentView;

        this.board=new Square[boardDim][boardDim];
        this.matrix=new int[boardDim][boardDim];

        this.isNewGame=true;

        this.boardType=boardType;

        initializeMatrix();

    }

    public boolean areSidesOccupied(int y,int x,int identity) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if ((i * i + j * j) != 0) {

                    if (y + i < boardDim && y + i >= 0 && x + j < boardDim && x + j >= 0) {

                        if (this.matrix[y + i][x + j] != 0) {
                            if(this.matrix[y + i][x + j] != identity)
                            {
                                return true;
                            }
                        }

                    }
                }
            }

        }
        return false;
    }

    public boolean isOccupied(int[][] ship,int identity){

        for(int i=0;i<ship.length;i++)
        {
            if(this.matrix[ship[i][0]][ship[i][1]]!=0 || areSidesOccupied(ship[i][0],ship[i][1],identity)==true){
                return true;
            }
        }

        return false;
    }

    public boolean isBlocked(ArrayList<int[]> coordinates,int identity){



        for(int i=0;i<coordinates.size();i++){
            if(matrix[coordinates.get(i)[0]][coordinates.get(i)[1]]!=0)
            {
                if(matrix[coordinates.get(i)[0]][coordinates.get(i)[1]]!=identity)
                {
                    return true;
                }

            }
            if(areSidesOccupied(coordinates.get(i)[0],coordinates.get(i)[1],identity)==true){
                return true;
            }
        }
        return false;
    }




    public ArrayList<int[]> getShipCoordinates(int startY,int startX,int identity){
        ArrayList<int[]> coordinates=new ArrayList<int[]>();


        for (int i=0;i<boardDim;i++)
        {
            for (int j=0;j<boardDim;j++){
                if(matrix[i][j]==identity){
                    int[] coordinate={i,j};
                    coordinates.add(coordinate);
                }
            }
        }
        return coordinates;
    }

    public ArrayList<int[]> getTurnCoordinates(ArrayList<int[]> coordinates,int identity){
       // ArrayList<int[]> coordinates=getShipCoordinates(startY,startX);

        ArrayList<int[]> newCoordinates=new ArrayList<int[]>();

        if(coordinates.size()==1)
        {
            int startY=coordinates.get(0)[0];
            int startX=coordinates.get(0)[1];
            int[] element= {startY,startX};
            newCoordinates.add(element);
            return newCoordinates;
        }

        if(coordinates.get(0)[0]==coordinates.get(1)[0]){
            if(coordinates.get(coordinates.size()-1)[0]+coordinates.size()-1<boardDim)
            {
                int newFirstX=coordinates.get(0)[1];
                int start=coordinates.get(coordinates.size()-1)[0];
                int end=coordinates.get(coordinates.size()-1)[0]+coordinates.size();
                for(int i=start;i<end;i++)
                {
                    int[] element={i,newFirstX};
                    newCoordinates.add(element);
                }

                if(isBlocked(newCoordinates,identity)==true){
                    newCoordinates.clear();
                    return newCoordinates;
                }
            }
            else{
                return newCoordinates;
            }
        }
        else if(coordinates.get(0)[1]==coordinates.get(1)[1]){
            if(coordinates.get(coordinates.size()-1)[1]+coordinates.size()-1<boardDim)
            {



                int newFirstY=coordinates.get(0)[0];
                int start=coordinates.get(coordinates.size()-1)[1];
                int end=coordinates.get(coordinates.size()-1)[1]+coordinates.size();

                for(int i=start;i<end;i++)
                {
                    int[] element={newFirstY,i};
                    newCoordinates.add(element);
                }

                if(isBlocked(newCoordinates,identity)==true){
                    newCoordinates.clear();
                    return newCoordinates;
                }

            }
            else{
                return newCoordinates;
            }
        }

        return newCoordinates;
    }

    public void writeNewCoordinates(ArrayList<int[]> coordinates,int identity, ArrayList<Integer> colors){

        for(int i=0;i<coordinates.size();i++){
            matrix[coordinates.get(i)[0]][coordinates.get(i)[1]]=identity;
            board[coordinates.get(i)[0]][coordinates.get(i)[1]].setIdentity(identity);
            board[coordinates.get(i)[0]][coordinates.get(i)[1]].setColor(colors.get(i));
        }
    }

    public void initializeMatrix()
    {
        for(int i=0;i<boardDim;i++){
            for(int j=0;j<boardDim;j++){
                this.matrix[i][j]=0;
            }
        }

        ships=new int[]{4,3,3,2,2,2,1,1,1,1};
        identities=new int[ships.length];
        for(int i=1;i<=ships.length;i++){
            identities[i-1]=i;
        }

        ShipPlacer placer=new ShipPlacer();
        for(int i=0;i<ships.length;i++){
            List<int[][]> locations=placer.getShipLocations(ships[i],boardDim);
           // Log.i("TAG", "onLongClick: x = " + "ASD+ , y = " );
            boolean shipPlaced=false;
            int identity=identities[i];

            while(shipPlaced==false){
                int randomNum = ThreadLocalRandom.current().nextInt(0, locations.size()-1);
                if(isOccupied(locations.get(randomNum),identity)==false){

                    for(int j=0;j<locations.get(randomNum).length;j++){
                        this.matrix[locations.get(randomNum)[j][0]][locations.get(randomNum)[j][1]]=identity;
                    }

                    shipPlaced=true;
                }
            }


        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(int i=0;i<boardDim;i++){
            for(int j=0;j<boardDim;j++) {
                if(isNewGame==true) {

                    if (this.boardType == 1) {
                        if (this.matrix[i][j] == 0) {
                            Square square = new Square(this.context, 0, i, j, this.boardDim, currentView, canvas, this.matrix[i][j]);
                            this.board[i][j] = square;
                        } else if (this.matrix[i][j] > 0 && this.matrix[i][j] <= this.identities[this.identities.length - 1]) {
                            Square square = new Square(this.context, 1, i, j, this.boardDim, currentView, canvas, this.matrix[i][j]);
                            this.board[i][j] = square;
                        }
                    }
                    else if (this.boardType == 2) {
                        if (this.matrix[i][j] == 0) {
                            Square square = new Square(this.context, 0, i, j, this.boardDim, currentView, canvas, this.matrix[i][j]);
                            this.board[i][j] = square;
                        } else if (this.matrix[i][j] > 0 && this.matrix[i][j] <= this.identities[this.identities.length - 1]) {
                            Square square = new Square(this.context, 0, i, j, this.boardDim, currentView, canvas, this.matrix[i][j]);
                            this.board[i][j] = square;
                        }
                    }
                }
                else if(isNewGame==false)
                {
                    this.board[i][j].paintSquare(canvas);
                }

            }
        }

        isNewGame=false;
    }

    public void registerHit(int y,int x,int identity)
    {
        ArrayList<int[]> coordinates = new ArrayList<>();
        int[] coordinate=new int[]{y,x};
        coordinates.add(coordinate);

        ArrayList<Integer> colors=new ArrayList<>();
        //add "Damaged" color
        colors.add(3);
        writeNewCoordinates(coordinates,identity,colors);

        //check if ship is dead
        coordinates.clear();
        coordinates=getShipCoordinates(y,x,identity);

        int shipPartsLeft=0;
        for(int i=0;i<coordinates.size();i++){
            if(boardType==1){
                if(this.board[coordinates.get(i)[0]][coordinates.get(i)[1]].getColor()==1){
                    shipPartsLeft=shipPartsLeft+1;
                }
            }
            else if(boardType==2){
                if(this.board[coordinates.get(i)[0]][coordinates.get(i)[1]].getColor()==0){
                    shipPartsLeft=shipPartsLeft+1;
                }
            }

        }
        if(shipPartsLeft==0)
        {
            colors.clear();
            for(int i=0;i<coordinates.size();i++)
            {
                //set ship colors to red
                colors.add(2);
            }
            writeNewCoordinates(coordinates,identity,colors);
        }


    }

    public void registerMiss(int y,int x,int identity) {
        ArrayList<int[]> coordinates = new ArrayList<>();
        int[] coordinate = new int[]{y, x};
        coordinates.add(coordinate);

        ArrayList<Integer> colors = new ArrayList<>();
        //add "miss" color
        colors.add(4);
        writeNewCoordinates(coordinates, identity, colors);
    }

    public boolean successfulHit(int y,int x)
    {
        if (this.matrix[y][x]>0){
            return true;
        }
        return false;
    }

    public int getMatrixElement(int y,int x){

        return this.matrix[y][x];
    }

    public Square getBoardElement(int y,int x){
        return this.board[y][x];

    }
}
