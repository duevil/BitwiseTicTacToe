package de.mk.tictactoe;

import java.util.Scanner;

/**
 * Represents the game loop of Tic Tac Toe
 *
 * @author Malte Kasolowsky
 */
public class GameLoop {
    private final Game game = new Game();
    private final Scanner scanner = new Scanner(System.in);
    private int playerCross = 0;
    private int playerCircle = 1;

    /**
     * Sets up the game by asking the user for the players;
     * a player can either be a human or a computer
     */
    public void setup() {
        System.out.println("Welcome to Tic Tac Toe!");
        do {
            System.out.print("Select player 'Cross' [0=Human, 1=Computer]: ");
            playerCross = scanner.nextInt();
        } while (playerCross != 0 && playerCross != 1);
        do {
            System.out.print("Select player 'Circle' [0=Human, 1=Computer]: ");
            playerCircle = scanner.nextInt();
        } while (playerCircle != 0 && playerCircle != 1);
    }

    /**
     * Starts the game loop;
     * starts a new game of Tic Tac Toe and by turns asks the user and the computer for their moves
     * until the game is either won or tied
     */
    public void run() {
        setup();
        while (game.isRunning()) {
            System.out.printf("Turn for '%s' [0-8]: ", game.isCrossTurn() ? "Cross" : "Circle");
            makeMove();
            System.out.println(game);
        }
        System.out.println("Game over!");
        if (game.isWon()) System.out.printf("Player '%s' won%n%n", game.isCrossTurn() ? "Cross" : "Circle");
        else System.out.println("The game is tied");
    }

    /**
     * Makes a move by the current player;
     * if the current player is a human, the user will be asked for a move,
     * otherwise the computer will make a move
     */
    private void makeMove() {
        if ((game.isCrossTurn() && playerCross == 0) || (!game.isCrossTurn() && playerCircle == 0)) {
            while (!game.makeMove(scanner.nextInt())) {
                System.out.print("Invalid move! Try again [0-8]: ");
            }
        } else {
            int comMove = Negamax.getBestMove(game);
            System.out.println(comMove);
            game.makeMove(comMove);
        }
    }
}
