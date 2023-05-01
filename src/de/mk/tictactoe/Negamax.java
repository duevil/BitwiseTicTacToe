package de.mk.tictactoe;

/**
 * Implements a negamax algorithm to find the best move for the current player in a game of Tic Tac Toe
 *
 * @author Malte Kasolowksy
 */
public final class Negamax {

    /**
     * Private constructor to prevent instantiation
     */
    private Negamax() {
    }

    /**
     * Searches the best move for the current player in the specified game
     *
     * @param game The game to search the best move for
     * @return The best move for the current player
     */
    public static int getBestMove(Game game) {
        return negamax(game, 0).move;
    }

    /**
     * Recursively searches the best move for the current player in the specified game
     *
     * @param game The game to search the best move for
     * @param currentDepth The current depth of the recursion
     * @return The best move for the current player and the score of the game after that move
     */
    private static Evaluation negamax(final Game game, int currentDepth) {
        if (!game.isRunning()) return new Evaluation(-1, game.isWon() ? (-1000 / Math.max(currentDepth, 1)) : 0);

        int bestMove = -1;
        int bestScore = -1000 / Math.max(currentDepth, 1);

        for (int i = 0; i <= 8; i++) {
            var gameCopy = game.copy();
            if (gameCopy.makeMove(i)) {
                var recursedEvaluation = negamax(gameCopy, currentDepth + 1);
                int currentScore = -recursedEvaluation.score;
                if (currentScore > bestScore) {
                    bestMove = i;
                    bestScore = currentScore;
                }
            }
        }

        if (bestMove < 0) throw new IllegalStateException("negamax has not found any move");
        return new Evaluation(bestMove, bestScore);
    }

    /**
     * Data class to store a move and the score of the game after that move
     *
     * @param move The move
     * @param score The score of the game after that move
     */
    private record Evaluation(int move, int score) {}
}
