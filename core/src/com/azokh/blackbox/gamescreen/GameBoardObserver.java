package com.azokh.blackbox.gamescreen;

import com.azokh.blackbox.Resources;
import com.azokh.blackbox.gamescreen.elements.ObserverBoardCell;
import com.azokh.blackbox.gamescreen.elements.StaticBoardCell;
import com.azokh.blackbox.ui.element.Element;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameBoardObserver implements Element {

    StaticBoardCell[][] board_elements;
    int boardSize;

    public GameBoardObserver(GameBoard gameBoard) {

        this.boardSize = gameBoard.boardSize;
        this.board_elements = new StaticBoardCell[boardSize][boardSize];

        for (int i = 0; i < boardSize-2; i++) {
            for (int j = 0; j < boardSize-2; j++) {
                if (gameBoard.board_raw[j][i] == 1) {
                    this.board_elements[j+1][i+1] = new ObserverBoardCell(gameBoard.board_elements[j+1][i+1].getBounds(), true);
                } else {
                    this.board_elements[j+1][i+1] = new ObserverBoardCell(gameBoard.board_elements[j+1][i+1].getBounds(), false);
                }
            }
        }
    }

    @Override
    public void render() {
        Resources.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Resources.shapeRenderer.setColor(Resources.titleColor);
        for (int x = 1; x < boardSize-1; x++) {
            for (int y = 1; y < boardSize-1; y++) {
                this.board_elements[y][x].render();
            }
        }
        Resources.shapeRenderer.end();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }


}
