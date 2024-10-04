package game2048;

import java.util.Formatter;
import java.util.Observable;
import java.util.ArrayList;


/** The state of a game of 2048.
 *  @author mamaruo
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }



    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        board.setViewingPerspective(Side.NORTH);

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        int size = board.size();
        for (int col = 0; col < size; col++) {
            ArrayList<Tile> merged = new ArrayList<>(); // 根据规则，在一次操作中若一个方块是由合并产生，无法再次发生合并，记录之
            for (int row = size - 2 ; row >= 0; row--) { // 从上至下遍历当前列作为待移动方块

                Tile t = tile(col, row); // 当前方块，待移动
                int cur_value;
                if (t != null) cur_value = t.value(); // 若当前方块为空则跳过
                else continue;

                for (int i = row + 1; i < size; i++){ // 从下至上寻找合适的移动目标
                    Tile target = tile(col, i); // 目标方块
                    if (target != null) {
                        if (target.value() == cur_value) { // 目标不为空且值等于当前方块且
                            if (merged.contains(target)) { //   目标是本次 tilt 的结果
                                if (tile(col, i - 1) != null) break; //   则检查其下方一格是否为空，若
                                else {
                                    board.move(col, i - 1, t); //   为空则移动之
                                    changed = true;
                                }
                            } else { // 若1. 目标不为空 2. 值等于当前方块 3. 目标不是本次tilt的结果。将当前方块合并到目标位置
                                board.move(col, i, t);
                                merged.add(tile(col, i));
                                score += tile(col, i).value(); // 合并则加分
                                changed = true;
                            }
                        } else { // 目标不为空且值**不等于**当前方块，则移动到目标下方
                            if (tile(col, i - 1) != null) break;
                            else {
                                board.move(col, i - 1, t);
                                changed = true;
                            }
                        }
                    } else { // 若目标位置为空且是边缘，则移动到目标位置
                        if (i == size - 1){
                            board.move(col, size - 1, t);
                            changed = true;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }





        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (Tile t: b){
            if (t == null) return true;
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (Tile t: b){
            if (t != null && t.value() == MAX_PIECE) return true;
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // 遍历检查是否有空块。Check if there is an empty space.
        for (Tile t: b){
            if (t == null) return true;
        }

        // Check if two adjacent tiles has the same value.
        int boardSize = b.size();
        for (int i = 0; i < boardSize - 1; i++){
            for (int j = 0; j < boardSize - 1; j++){
                Tile curr, right, bottom;
                curr = b.tile(i, j); right = b.tile(i + 1 , j); bottom = b.tile(i, j + 1);

                if (curr == null) continue;
                if (right != null && curr.value() == right.value()) return true;
                if (bottom != null && curr.value() == bottom.value()) return true;
            }
        }

        Tile lowerRight = b.tile(boardSize - 1, boardSize - 1);
        Tile top = b.tile(boardSize - 1, boardSize - 2);
        Tile left = b.tile(boardSize - 2, boardSize - 1);
        if (lowerRight != null){
            if (top != null && top.value() == lowerRight.value()) return true;
            if (left != null && left.value() == lowerRight.value()) return true;
        }

        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
