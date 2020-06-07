package com.korzub.game1;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ShipPlacer {

    public ShipPlacer(){
    }

    public List<int[][]> getShipLocations(int length,int boardDim){
        List<int[][]> locations=new ArrayList<int[][]>();

        for (int i=0;i<=boardDim-length;i++){
            for (int j=0;j<=boardDim-length;j++){
                int[][] shipY=new int[length][];
                int[][] shipX=new int[length][];

                int move=0;

                for(int k=0;k<length;k++){
                    int[] locationY=new int[2];
                    int[] locationX=new int[2];

                    locationY[0]=i+move;
                    locationY[1]=j;

                    locationX[0]=i;
                    locationX[1]=j+move;

                    shipY[k]=locationY;
                    shipX[k]=locationX;
                    move++;

                }
                locations.add(shipY);
                locations.add(shipX);
            }
        }



        for(int i=0;i<locations.size();i++){

            for(int j=0;j<locations.get(i).length;j++){
                String y=Integer.toString(locations.get(i)[j][0]);
                String x=Integer.toString(locations.get(i)[j][1]);
            }

        }
        return locations;
    }
}
