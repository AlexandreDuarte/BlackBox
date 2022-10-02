package com.azokh.blackbox.gamescreen;

public class GameLogic {


    int[][] board_raw;
    GameBoard board;
    boolean running;
    int[] previous_position, position;
    boolean inputStopped;
    float inputElapsedTime;
    float outputElapsedTime;
    boolean outputStarted;

    boolean stop = true;

    public GameLogic(GameBoard board) {
        this.board = board;
        this.board_raw = board.board_raw.clone();
        this.running = false;
    }

    public void start(int[] startPosition, boolean horizontal, boolean flip) {
        if (!stop) return;

        inputElapsedTime = 0.0f;
        outputElapsedTime = 0.0f;
        inputStopped = false;
        outputStarted = false;

        running = true;
        stop = false;

        int[] initial_bias = new int[2];

        if (horizontal) {
            if (flip) {
                initial_bias[1] = -1;
            } else {
                initial_bias[1] = 1;
            }

        } else {
            if (flip) {
                initial_bias[0] = -1;
            } else {
                initial_bias[0] = 1;
            }
        }

        previous_position = new int[] {startPosition[0], startPosition[1]};
        position = new int[]{startPosition[0] + initial_bias[0], startPosition[1] + initial_bias[1]};
    }


    public void updateInputTimings(float delta) {
        if (!inputStopped) {
            inputElapsedTime+=delta;
        }

        if (outputStarted) {
            outputElapsedTime+=delta;
        }

        if (outputElapsedTime > inputElapsedTime) {
            stop = true;
            board.board_elements[position[1]+1][position[0]+1].setActive(false);
        }
    }

    public void step() {


        if (!running) return;

        int[] new_position = nextPosition(previous_position, position);
        previous_position = position;
        position = new_position;
        if (position[0] >= board.boardSize-2 || position[0] < 0) {
            board.board_elements[position[1]+1][position[0]+1].setActive(true);
            outputStarted = true;
            running = false;
        }else if (position[1] >= board.boardSize-2 || position[1] < 0) {
            board.board_elements[position[1]+1][position[0]+1].setActive(true);
            outputStarted = true;
            running = false;
        }
    }

    public int[] nextPosition(int[] previous_position, int[] position) {

        int[] result = new int [] {position[0], position[1]};

        int[] bias = {0, 0};

        if (board_raw[position[1]][position[0]]==1) {
            return new int[] {previous_position[0], previous_position[1]};
        }

        bias[0] = position[0] - previous_position[0];
        bias[1] = position[1] - previous_position[1];

        int x = position[0];
        int y = position[1];

        int boxed_in_x = 0;
        int boxed_in_y = 0;



        if (x > 0) {
            if (board_raw[y][x-1] == 1) {
                boxed_in_x++;
                bias[0] += 1;
            }
            if (y > 0) {
                if (board_raw[y-1][x-1] == 1) {
                    bias[0] += 1;
                    bias[1] += 1;
                }
            }
            if (y<board.boardSize-3) {
                if (board_raw[y+1][x-1] == 1) {
                    bias[0] += 1;
                    bias[1] -= 1;
                }
            }
        }

        if (x < board.boardSize-3) {
            if (board_raw[y][x+1] == 1) {
                boxed_in_x++;
                bias[0] -= 1;
            }
            if (y > 0) {
                if (board_raw[y-1][x+1] == 1) {
                    bias[0] -= 1;
                    bias[1] += 1;
                }
            }
            if (y<board.boardSize-3) {
                if (board_raw[y+1][x+1] == 1) {
                    bias[0] -= 1;
                    bias[1] -= 1;
                }
            }
        }

        if (y > 0) {
            if (board_raw[y-1][x] == 1) {
                bias[1] += 1;
                boxed_in_y++;
            }
        }
        if (y<board.boardSize-3) {
            if (board_raw[y+1][x] == 1) {
                bias[1] -= 1;
                boxed_in_y++;
            }
        }

        if(boxed_in_y==2) {
            bias[0] += position[0] - previous_position[0];
        }

        if(boxed_in_x==2) {
            bias[1] += position[1] - previous_position[1];
        }


        if (bias[0] == 0 && bias[1] == 0) {
            return new int[] {previous_position[0], previous_position[1]};
        }

        if (bias[0] > 0) {
            result[0] += 1;
        }

        if (bias[0] < 0) {
            result[0] -= 1;
        }

        if (bias[1] > 0) {
            result[1] += 1;
        }

        if (bias[1] < 0) {
            result[1] -= 1;
        }


        return result;
    }

}
