package com.korzub.game1;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameResultAdapter extends RecyclerView.Adapter<GameResultAdapter.GameResultVIewHolder> {


    private ArrayList<GameResult> gameResultArrayList;

    public static class GameResultVIewHolder extends RecyclerView.ViewHolder
    {
        public TextView turnsTextView;
        public TextView difficultyTextView;
        public TextView outcomeTextView;

        public GameResultVIewHolder(View itemView)
        {
            super(itemView);
            turnsTextView=itemView.findViewById(R.id.turnsText);
            difficultyTextView=itemView.findViewById(R.id.difficultyText);
            outcomeTextView=itemView.findViewById(R.id.outcomeText);

        }
    }

    public GameResultAdapter(ArrayList<GameResult> gameResultArrayList)
    {
        this.gameResultArrayList=gameResultArrayList;
    }

    @Override
    public GameResultVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(
                parent.getContext()).inflate(R.layout.record,parent,false);

        GameResultVIewHolder gameResultVIewHolder=new GameResultVIewHolder(v);

        return gameResultVIewHolder;
    }

    @Override
    public void onBindViewHolder(GameResultVIewHolder holder, int position)
    {
        Log.d("i","ASD"+position);
        GameResult currentResult=gameResultArrayList.get(position);

        LinearLayout holderLayout=(LinearLayout)holder.turnsTextView.getParent();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)holderLayout.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width=displayMetrics.widthPixels;

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height/6);
        ((LinearLayoutCompat)holderLayout.getParent()).setLayoutParams(params);


        Context context= ((Activity)holderLayout.getContext());

        holder.turnsTextView.setText("Turns: "+currentResult.getTurns());
        holder.turnsTextView.setTextColor(context.getResources().getColor(R.color.orange));
        holder.turnsTextView.setTextSize(height/50);

        holder.turnsTextView.setShadowLayer((height+width)/240,
                width/120,height/120,context.getResources().getColor(R.color.black));


        holder.difficultyTextView.setText("Difficulty: "+ currentResult.getDifficulty());
        holder.difficultyTextView.setTextColor(context.getResources().getColor(R.color.orange));
        holder.difficultyTextView.setTextSize(height/50);

        holder.difficultyTextView.setShadowLayer((height+width)/240,
                width/120,height/120,context.getResources().getColor(R.color.black));

        holder.outcomeTextView.setText("Outcome: "+currentResult.getOutcome());
        holder.outcomeTextView.setTextColor(context.getResources().getColor(R.color.orange));
        holder.outcomeTextView.setTextSize(height/50);

        holder.outcomeTextView.setShadowLayer((height+width)/240,
                width/120,height/120,context.getResources().getColor(R.color.black));
    }

    @Override
    public int getItemCount() {
        return gameResultArrayList.size();
    }
}
