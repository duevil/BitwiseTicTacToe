# Bitwise TicTacToe

This is a small java program implementing a simple Game of TicTacToe written in IntelliJ and having two main features:

- The usage of only one integer to store the whole games state, using bitwise operations to perform moves
- The implementation of the Negamax algorithm for a computer player

These two features were the main reason why I have written this program.

## How to play

The game itself runs entirely on the console. 

At the beginning of the game the user can choose for each player (cross and circle) wether the player is controled by a human or by the computer.

After the setup, the player take turns. A computer makes his move automatically while a human player is asked for his move to make:
The user must input the cell to take, the top-left being 0 and the bottom-right being 8.

The game runs until either a player has won or the game was tied.
