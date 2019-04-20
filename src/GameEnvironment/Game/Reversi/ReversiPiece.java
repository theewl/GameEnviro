package GameEnvironment.Game.Reversi;

import java.awt.Point;
import java.util.List;

import GameEnvironment.Piece;

public class ReversiPiece extends Piece{
	
	private final String GAME_FOLDER = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";
	private final String[] PIECE_ICON = {GAME_FOLDER + "blackPiece.png", GAME_FOLDER +"whitePiece.png"};
	
	/* Constructor for Piece
	 * @param name of piece (used for debugging), player the piece belong to
	 */
	ReversiPiece(String pieceName, int player) {
		super(pieceName, player);
	}

	@Override
	public List<Point> getMoves() {
		return null;
	}
	/* Get the Piece Icon from the pieceIcon string
	 * @param the player that own the piece
	 * @return the path of the icon
	 */
	@Override
	public String getIcons(int player) {
		return PIECE_ICON[player-1];
	}

	

}
