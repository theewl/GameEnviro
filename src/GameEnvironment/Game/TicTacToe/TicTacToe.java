package GameEnvironment.Game.TicTacToe;

import java.awt.Color;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

/* Game factory of TicTacToe
 * Implements GameEnviroment.GameFactory
 */
public class TicTacToe implements GameFactory {
	
	/* Constructor for TicTacToe game factory
	 * Call by GameGUI when game is selected
	 * Call buildGame to construct Board and Piece
	 */
	public TicTacToe() {
		buildGame(this.getClass().getSimpleName());	
	}
	
	/* Create the TicTacToe GameGUI
	 * Force user to create a board
	 * @param name of the class, also the name of the game
	 */
	@Override
	public void buildGame(String gameName) {
		new GameGUI(buildBoard(), gameName);
	}
	
	/* Create the TicTacToeBoard object
	 * Currently its: row, col, number of players, board interaction, and force creating Piece object
	 * @return a Board object
	 */
	@Override
	public Board buildBoard() {
		return new TicTacToeBoard(3, 3, 2, Interaction.BOARD, buildPieces());
	}
	
	/* Create a TicTacToePiece object
	 * @return a TicTacToePiece object
	 */
	@Override
	public Piece buildPieces() {
		return new TicTacToePiece("", 1);
	}	
}


