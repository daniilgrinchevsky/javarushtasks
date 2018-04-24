package com.javarush.task.task35.task3513;


import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int score;
    int maxTile;
    Stack<Tile[][]> previousStates = new Stack();
    Stack<Integer> previousScores = new Stack();
    boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
        score = 0;
        maxTile = 2;
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++)
                if (gameTiles[i][j].isEmpty())
                    emptyTiles.add(gameTiles[i][j]);
        }
        return emptyTiles;
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if(!emptyTiles.isEmpty()) {
            Tile tile = emptyTiles.get((int) (emptyTiles.size() * Math.random()));
            tile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    void resetGameTiles() {
        previousStates.clear();
        previousScores.clear();
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++)
                gameTiles[i][j] = new Tile();
        }
        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isChanged = false;
        Tile tmp;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[j].value == 0 && tiles[j + 1].value != 0) {
                    tmp = tiles[j];
                    tiles[j] = tiles[j + 1];
                    tiles[j + 1] = tmp;
                    isChanged = true;
                }
            }
        }
        return isChanged;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isChanged = false;
        for (int j = 0; j < 3; j++) {
            if (tiles[j].value != 0 && (tiles[j].value == tiles[j + 1].value)) {
                tiles[j].value = tiles[j].value * 2;
                tiles[j + 1].value = 0;
                if (tiles[j].value > maxTile)
                    maxTile = tiles[j].value;
                score += tiles[j].value;
                isChanged = true;
                compressTiles(tiles);
            }
        }
        return isChanged;
    }
    public void left(){
        if(isSaveNeeded)
            saveState(gameTiles);
        boolean isChanged = false;
        for(int i = 0; i < FIELD_WIDTH; i++){
           if(compressTiles(gameTiles[i])|mergeTiles(gameTiles[i]))
               isChanged = true;
        }
        if(isChanged)
            addTile();
        isSaveNeeded = true;
    }
    public void right(){
        saveState(gameTiles);
        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }
    public void up(){
        saveState(gameTiles);
        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }
    public void down(){
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }
    public void rotate() {
        for (int k = 0; k < 2; k++) {
            for (int j = k; j < 3 - k; j++) {
                Tile tmp = gameTiles[k][j];
                gameTiles[k][j] = gameTiles[j][3 - k];
                gameTiles[j][3 - k] = gameTiles[3 - k][3 - j];
                gameTiles[3 - k][3 - j] = gameTiles[3 - j][k];
                gameTiles[3 - j][k] = tmp;
            }
        }
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }
    public boolean canMove(){
        if(!getEmptyTiles().isEmpty())
            return true;
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH - 1; j++){
                if(gameTiles[i][j].value == gameTiles[i][j+1].value)
                    return true;
            }
        }
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH - 1; j++){
                if(gameTiles[j][i].value == gameTiles[j+1][i].value)
                    return true;
            }
        }
        return false;
    }
    private void saveState(Tile[][] tiles){
        Tile[][] savedTile = new Tile[tiles.length][tiles[0].length];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                savedTile[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(savedTile);
        previousScores.push(score);
        isSaveNeeded = false;
    }
    public void rollback(){
        if(!previousScores.isEmpty()&&!previousStates.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }
    void randomMove(){
        int  n = ((int) (Math.random() * 100)) % 4;
        switch (n){
            case 0 : left(); break;
            case 1 : right(); break;
            case 2 : down(); break;
            case 3 : up(); break;
        }
    }
    boolean hasBoardChanged(){
        int sum1 = 0;
        int sum2 = 0;
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH; j++){
                 sum1 = sum1 + gameTiles[i][j].value;
            }
        }
        for(int i = 0; i < FIELD_WIDTH; i++){
            for(int j = 0; j < FIELD_WIDTH; j++){
                sum2 = sum2 + previousStates.peek()[i][j].value;
            }
        }
        if(sum1 != sum2)
            return true;
        return false;
    }
    MoveEfficiency getMoveEfficiency(Move move){
        MoveEfficiency moveEfficiency;
        move.move();
        if(hasBoardChanged())
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        else
            moveEfficiency = new MoveEfficiency(-1, 0, move);
        rollback();
        return moveEfficiency;
    }
    void autoMove(){
        PriorityQueue<MoveEfficiency> queue = new PriorityQueue<>(4,Collections.reverseOrder());
        queue.offer(getMoveEfficiency(this::left));
        queue.offer(getMoveEfficiency(this::right));
        queue.offer(getMoveEfficiency(this::up));
        queue.offer(getMoveEfficiency(this::down));

        queue.peek().getMove().move();

    }
}
