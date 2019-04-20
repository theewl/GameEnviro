package GameEnvironment.Game.Battleship;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Piece;

/* Battleship pieces for the board game
 * Logic for the pieces
 * Extends from GameEnvironment.Piece
 */
public class BattleshipPiece extends Piece{
	private final String GAME_FOLDER = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";  //Folder of TicTacToe assets
	private final String[] PIECE_ICON = {this.GAME_FOLDER + "Ship.png", this.GAME_FOLDER + "Miss.png", this.GAME_FOLDER + "Hit.png"};  //Assets image for each pieces
	boolean missileHit;  //Did the missile hit a ship
	
	/* Constructor for the battleship pieces
	 * Call parent constructor
	 */
	BattleshipPiece(String pieceName, int player) {
		super(pieceName, player);
	}
	
	/* Determine what move the piece can make
	 * @return an array of piece movements; return empty array if all move are allowed
	 */
	@Override
	public List<Point> getMoves() {
		return new ArrayList<Point>();
	}
	
	/* Determine the image to be printed
	 * @return path of where image is stored
	 */
	@Override
	public String getIcons(int player) {
		
		//For battleship staging board
		if (Battleship.stageBoard1 != null && Battleship.stageBoard2 != null)
			return this.PIECE_ICON[0];
		
		//If player miss in battleship board
		else if (!this.missileHit)
			return this.PIECE_ICON[1];
		
		//If player hit in battleship board
		else if (this.missileHit)
			return this.PIECE_ICON[2];
		return "";
	}	
}

