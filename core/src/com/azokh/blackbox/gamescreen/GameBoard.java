package com.azokh.blackbox.gamescreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.gamescreen.elements.BoardCell;
import com.azokh.blackbox.gamescreen.elements.EmptyBoardCell;
import com.azokh.blackbox.gamescreen.elements.HiddenBoardCell;
import com.azokh.blackbox.gamescreen.elements.InputBoardCell;
import com.azokh.blackbox.gamescreen.elements.InputCornerCell;
import com.azokh.blackbox.gamescreen.elements.TimerElement;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class GameBoard implements Element, InputProcessor {

    int cellSpacing = 2;

    BoardCell[][] board_elements;
    int boardSize;
    float cellSize;
    float yOffset;
    GameLogic gameLogic;
    int[][] board_raw;

    boolean gameStarted = false;

    boolean endGame = false;

    TimerElement gameTimer;

    public GameBoard(int size, TimerElement gameTimer) {
        this.gameTimer = gameTimer;
        this.boardSize = size+2;
        this.cellSize = Gdx.graphics.getWidth()/(float)(size+3);
        this.board_elements = new BoardCell[boardSize][boardSize];
        this.yOffset = Gdx.graphics.getHeight()/2.0f - cellSize*(boardSize)/2;
        this.board_raw = new int[size][size];
        generateBoard();
        this.gameLogic = new GameLogic(this);

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {


                if (x == 0 || x==boardSize-1 || y == 0 || y==boardSize-1) {
                    if (x==y || x+y == boardSize-1) {
                        this.board_elements[y][x] = new InputCornerCell(new Rectangle(cellSize * (x + 0.5f) + cellSpacing, yOffset + cellSize * y + cellSpacing, cellSize-2*cellSpacing, cellSize-2*cellSpacing), InputBoardCell.Direction.getCornerDirection(y == 0, x == 0));
                    } else {
                        this.board_elements[y][x] = new InputBoardCell(new Rectangle(cellSize * (x + 0.5f) + cellSpacing, yOffset + cellSize * y + cellSpacing, cellSize-2*cellSpacing, cellSize-2*cellSpacing), InputBoardCell.Direction.getDirection(x == 0 || y == 0, x == 0 || x == boardSize-1));

                        //this.board_elements[y][x] = new InputBoardCell(y==0 || y==boardSize-1, y==boardSize-1 || x==boardSize-1, new Rectangle(cellSize * (1+x) + 2, yOffset + cellSize * y + 2, cellSize-4, cellSize-4));
                    }
                } else if (x == 1 || x==boardSize-2 || y == 1 || y==boardSize-2) {
                    this.board_elements[y][x] = new EmptyBoardCell(new Rectangle(cellSize * (x+0.5f) + 2*cellSpacing, yOffset + cellSize * y + 2*cellSpacing, cellSize-4*cellSpacing, cellSize-4*cellSpacing));
                } else {
                    this.board_elements[y][x] = new HiddenBoardCell(new Rectangle(cellSize * (x+0.5f) + 2*cellSpacing, yOffset + cellSize * y + 2*cellSpacing, cellSize-4*cellSpacing, cellSize-4*cellSpacing));
                }
            }
        }
    }

    public void generateBoard() {
        Random rand = new Random();
        for (int count = 0; count < (int)(boardSize*boardSize*3.0f/32.0f) - 1 + rand.nextInt(2); ) {
            int x = rand.nextInt(boardSize-4) + 1;
            int y = rand.nextInt(boardSize-4) + 1;
            if (this.board_raw[y][x] == 0) {
                this.board_raw[y][x] = 1;
                count++;
            }
        }
        System.out.println("BOARD");
        for (int i = boardSize-4; i > 0; i--) {
            for (int j = 0; j < boardSize-3; j++) {
                System.out.print(board_raw[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void processBeamCreation(int x, int y, boolean horizontal, boolean flip) {
        gameLogic.start(new int[]{x-1, y-1}, horizontal, flip);
    }

    @Override
    public void render() {
        Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Resources.shapeRenderer.setColor(Resources.board);
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                this.board_elements[y][x].render();
            }
        }
        Resources.shapeRenderer.end();
    }

    static final float STEP_CONSTANT = 0.2f;
    float stepTime = 0.0f;

    @Override
    public void update(float delta) {
        if (!gameLogic.stop) {
            gameLogic.updateInputTimings(delta);

            stepTime += delta;
            if (stepTime >= STEP_CONSTANT) {
                stepTime = 0;
                gameLogic.step();
            }

        }

    }

    @Override
    public void dispose() {

    }

    public boolean isGameOver() {

        for (int i = 0; i < boardSize-2; i++) {
            for (int j = 0; j < boardSize-2; j++) {
                if (this.board_raw[j][i] == 1) {
                    if (!this.board_elements[j+1][i+1].isActive()) {
                        return false;
                    }
                } else {
                    if (this.board_elements[j+1][i+1].isActive()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean hasGameStarted() {
        return this.gameStarted;
    }

    public boolean getEndGame() { return endGame; }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (this.board_elements[y][x].getBounds().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    if (!this.gameStarted) {
                        this.gameStarted = true;
                        gameTimer.resumeTimer();
                    }
                    if (x == 0 || x==boardSize-1 || y == 0 || y==boardSize-1) {
                        if (x!=y && x+y != boardSize-1 && gameLogic.stop) {
                            processBeamCreation(x, y, y==0 || y==boardSize-1, y==boardSize-1 || x==boardSize-1);
                            this.board_elements[y][x].execute();
                        } else {
                            return false;
                        }
                    }

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                    if (this.board_elements[y][x].getBounds().contains(screenX, Gdx.graphics.getHeight() - screenY))
                    {
                        if (x == 0 || x == boardSize - 1 || y == 0 || y == boardSize - 1) {
                            if (this.board_elements[y][x].isActive()) {
                                this.board_elements[y][x].execute();
                                gameLogic.inputStopped = true;
                            }
                            return false;
                        }
                        this.board_elements[y][x].execute();
                        if (isGameOver()) {
                            endGame = true;
                        }
                    }

            }
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
