package GameEnvironment.Game.Reversi;

import java.awt.Color;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;
/* Game factory of Reversi
 * Implements GameEnviroment.GameFactory
 */
public class Reversi implements GameFactory{

	/* Constructor for Reversi game factory
	 * Call by GameGUI when game is selected
	 * Call buildGame to construct Board and Piece
	 */
	public Reversi() {
		buildGame(this.getClass().getSimpleName());	
	}
	
	/* Create the Reversi GameGUI
	 * Force user to create a board
	 * @param name of the class, also the name of the game
	 */
	@Override
	public void buildGame(String gameName) {
		new GameGUI(buildBoard(), gameName);		
	}
	
	/* Create the Reversi object
	 * Currently its: row, col, number of players, board interaction, and force creating Piece object
	 * @return a Board object
	 */
	@Override
	public Board buildBoard() {
		return new ReversiBoard(8, 8, 2, Interaction.BOARD, buildPieces(), Pattern.BLANK, Color.GREEN);
	}
	
	/* Create a Reversi object
	 * @return a Reversi object
	 */
	@Override
	public Piece buildPieces() {
		return new ReversiPiece("", 1);
	}
}
