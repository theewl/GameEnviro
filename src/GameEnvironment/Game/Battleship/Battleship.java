package GameEnvironment.Game.Battleship;

import java.awt.Color;

import javax.swing.JFrame;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

/* Game factory of Battleship
 * Implements GameEnviroment.GameFactory
 */
public class Battleship implements GameFactory {
	
	static JFrame stage1, stage2;  //Keep track of the two JFrame for battleship staging
	static BattleshipStageBoard stageBoard1, stageBoard2;  //Keep track of the batttleship staging board
	final static int MAX_GRID_SIZE = 10;  //Maximum size of board
	static String battleship;  //Name of game
	
	/* Constructor, call buildGame to build the battleship staging GUI
	 */
	public Battleship() {
		Battleship.battleship = this.getClass().getSimpleName(); //Assigned name of game based on this class name
		buildGame(battleship); 
	}
	
	/* Build the battleship staging GUI
	 * Force users to implement buildBoard() and BuildPieces()
	 */
	@Override
	public void buildGame(String gameName) {
		//If there isn't any battleship staging GUI open; run only one instance of the game
		if ((Battleship.stage1 == null && Battleship.stage2 == null) || 
		   (!Battleship.stage1.isDisplayable() && !Battleship.stage2.isDisplayable())) {
			
			//Null JFrame and BattleshipStagingBoard to ensure fresh start
			Battleship.stageBoard1 = null; 
			Battleship.stageBoard2 = null;
			Battleship.stage1 = null;
			Battleship.stage2 = null;			
			
			//Instantiate BattleshipStagingBoard and GameGUI of that board
			Battleship.stageBoard1 = (BattleshipStageBoard) buildBoard();
			Battleship.stageBoard2 = (BattleshipStageBoard) buildBoard();
			Battleship.stage1 = new GameGUI(stageBoard1, gameName + " Staging: Player 1").getFrame();
			Battleship.stage2 = new GameGUI(stageBoard2, gameName + " Staging: Player 2").getFrame();
		}
	}
	
	/* Build a BattleshipStagingBoard to allow users to input their ships
	 * @return a battleship staging board
	 */
	@Override
	public Board buildBoard() {
		return new BattleshipStageBoard(Battleship.MAX_GRID_SIZE, Battleship.MAX_GRID_SIZE, 1, Interaction.BOARD, buildPieces(), Pattern.BLANK, Color.BLUE);		
	}
	
	/* Build a BattleshipPiece for the board to use as reference
	 * @return a battleship piece
	 */
	@Override
	public Piece buildPieces() {
		return new BattleshipPiece("", 1);
	}	
}


