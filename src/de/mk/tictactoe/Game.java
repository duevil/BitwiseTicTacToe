package de.mk.tictactoe;

import java.io.CharArrayWriter;

/**
 * Represents a game of Tic Tac Toe using a single integer as state
 *
 * @author Malte Kasolowsky
 */
public class Game {
    /**
     * The game's state
     * <p>
     * The last (19th) bit defines the current player: 1 for Cross and 0 for Circle
     * <p>
     * The first 18 bits define the 9 board's cells, where each cell consists out of two bits;
     * the first bit is set, when the field is taken by the player Cross,
     * the second bit is set, when the field is taken by Circle
     */
    private int state = 0b1___00_00_00__00_00_00__00_00_00;

    /**
     * Searches the board for free cells which where not yet taken by any player
     *
     * @return An integer containing all free cell as bits;
     * The cells are stored as a bitwise representation,
     * meaning e.g. the first bit is set when the top-left cell has not been taken;
     * the return value will be 0 when no moves are possible
     */
    public int getFreeCells() {
        int freeCells = 0b000_000_000;
        for (int i = 0; i <= 8; i++) {
            if ((getCellVal(i) & 0b11) == 0) freeCells ^= 1 << i;
        }
        return freeCells;
    }

    /**
     * Makes a move by the current player;
     * A move will only be performed if there are possible moves left
     * and the specified cell is not yet taken
     *
     * @param cell The cell which should be taken by the player whose turn is performed
     * @return true, when the move could be successfully performed
     */
    public boolean makeMove(int cell) {
        if ((getCellVal(cell) & 0b11) == 0) {
            state ^= (isCrossTurn() ? 0b01 : 0b10) << (cell * 2);
            if (isRunning()) state ^= 1 << 18;
            return true;
        }
        return false;
    }

    /**
     * Analyzed the game board for any three adjacent taken and equal cells
     *
     * @return When any three cells taken by the same player where found
     */
    @SuppressWarnings({"java:S3776", "java:S1941", "java:S1142", "java:S1541", "java:S1871"})
    public boolean isWon() {
        int center = getCellVal(4);
        int topLeft = getCellVal(0);
        int bottomRight = getCellVal(8);

        if ((center & 0b11) > 0) { // if center is taken
            if (center == getCellVal(0) && center == getCellVal(8)) { // and left diagonal
                return true;
            } else if (center == getCellVal(2) && center == getCellVal(6)) { // or right diagonal
                return true;
            } else if (center == getCellVal(1) && center == getCellVal(7)) { // or mid-vertical
                return true;
            } else if (center == getCellVal(3) && center == getCellVal(5)) { // or mid-horizontal
                return true;
            }
        }

        if ((topLeft & 0b11) > 0) { // if top-left is taken
            if (topLeft == getCellVal(1) && topLeft == getCellVal(2)) { // and top-horizontal
                return true;
            } else if (topLeft == getCellVal(3) && topLeft == getCellVal(6)) { // or left-vertical
                return true;
            }
        }

        if ((bottomRight & 0b11) > 0) { // if bottom-right is taken
            if (bottomRight == getCellVal(2) && bottomRight == getCellVal(5)) { // and right-vertical
                return true;
            } else if (bottomRight == getCellVal(6) && bottomRight == getCellVal(7)) { // or bottom-horizontal
                return true;
            }
        }

        return false;
    }

    /**
     * Return whether the game is running or was completed;
     * A game is running if no player has won and there are possible moves left
     *
     * @return true, when the game is still running
     */
    public boolean isRunning() {
        return !isWon() && getFreeCells() > 0;
    }

    /**
     * Return whether it's the player Cross' turn or the player Circle's turn
     *
     * @return true, when it's Cross' turn or Circle's turn
     */
    public boolean isCrossTurn() {
        return (state & (1 << 18)) != 0;
    }

    /**
     * Copies the current game
     *
     * @return A new instance with the current state of this instance
     */
    public Game copy() {
        var copyInstance = new Game();
        copyInstance.state = this.state;
        return copyInstance;
    }

    /**
     * Returns a string representation of the game board;
     * The string will be formatted as a 3x3 grid, where each cell is represented by 'x', 'o' or '.'
     *
     * @return A string representation of the game board
     */
    @Override
    public String toString() {
        var buf = new CharArrayWriter();
        for (int i = 0; i <= 8; i++) {
            buf.append(switch (getCellVal(i)) {
                case 0b01 -> 'x';
                case 0b10 -> 'o';
                default -> '.';
            });
            if (i < 8) switch (i) {
                case 2, 5 -> buf.append(System.lineSeparator());
                default -> buf.append(' ');
            }
        }
        return buf.toString();
    }

    /**
     * Reads only the value of a specific cell of the board
     *
     * @param cell The cell of which the value should be read
     * @return The two-bit vale of the specified cell
     */
    private int getCellVal(int cell) {
        return (state >> (cell * 2)) & 0b11;
    }
}
