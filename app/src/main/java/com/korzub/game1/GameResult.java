package com.korzub.game1;

public class GameResult
{

    private String turns;
    private String difficulty;
    private String outcome;

    public GameResult(String turns,String difficulty,String outcome)
    {
        this.turns=turns;
        this.difficulty=difficulty;
        this.outcome=outcome;
    }

    public String getTurns() {
        return turns;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getOutcome() {
        return outcome;
    }
}
