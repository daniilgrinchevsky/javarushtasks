package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }
    @Override
    public int compareTo(MoveEfficiency o) {
        if(this == o)
            return 0;
        int difTiles = this.numberOfEmptyTiles - o.numberOfEmptyTiles;
        int difScores = this.score - o.score;
        if(difTiles > 0)
            return 1;
        if(difTiles < 0)
            return -1;
        if(difTiles == 0){
           if(difScores > 0)
               return 1;
           if(difScores < 0)
               return -1;
        }
        return 0;
    }
}
