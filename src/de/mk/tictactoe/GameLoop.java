package de.mk.tictactoe;

import java.util.Scanner;

/**
 * Represents the game loop of Tic Tac Toe
 *
 * @author Malte Kasolowsky
 */
public class GameLoop {

    /**
     * Starts the game loop;
     * starts a new game of Tic Tac Toe and by turns asks the user and the computer for their moves
     * until the game is either won or tied
     */
    public void run() {
        final var game = new Game();
        final var scanner = new Scanner(System.in);
        while (game.isRunning()) {
            System.out.printf("Turn for '%s' [0-8]: ", game.isCrossTurn() ? "Cross" : "Circle");
            if (!game.isCrossTurn()) {
                while (!game.makeMove(scanner.nextInt())) {
                    System.out.print("Invalid move! Try again [0-8]: ");
                }
            } else {
                int comMove = Negamax.getBestMove(game);
                System.out.println(comMove);
                game.makeMove(comMove);
            }
            System.out.println(game);
        }
        System.out.println("Game over!");
        if (game.isWon()) System.out.printf("Player '%s' won%n%n", game.isCrossTurn() ? "Cross" : "Circle");
        else System.out.println("The game is tied");
    }
}
