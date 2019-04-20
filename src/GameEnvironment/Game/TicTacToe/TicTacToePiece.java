package GameEnvironment.Game.TicTacToe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Piece;

/* Piece object for TicTacToe
 * Build the TicTacToe piece logic
 */
public class TicTacToePiece extends Piece{
	private final String GAME_FOLDER = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";  //Folder of TicTacToe assets
	private final String[] PIECE_ICON = {this.GAME_FOLDER + "X.png", this.GAME_FOLDER + "O.png"};  //Assets image for each pieces
	
	/* Constructor for Piece
	 * @param name of piece (used for debugging), player the piece belong to
	 */
	TicTacToePiece(String pieceName, int player) {
		super(pieceName, player);
	}
	
	/* A HashMap(row, col) of all available moves that the Piece can make; used for nextMove() in Board 
	 * @return available moves; if Piece have no predetermined moves, return empty ArrayList if all are allowed
	 */
	@Override
	public List<Point> getMoves() {
		return new ArrayList<Point>();
	}
	
	/* Get the Piece Icon from the pieceIcon string
	 * @param the player that own the piece
	 * @return the path of the icon
	 */
	@Override
	public String getIcons(int player) {
		return this.PIECE_ICON[player - 1];
	}	
}

