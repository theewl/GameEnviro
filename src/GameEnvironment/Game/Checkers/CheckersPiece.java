package GameEnvironment.Game.Checkers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Piece;

public class CheckersPiece extends Piece{
	private final String GAME_FOLDER = System.getProperty("user.dir") + "/src/GameEnvironment/Game/Checkers/";
	private final String[] PIECE_ICON = {GAME_FOLDER + "black.png", GAME_FOLDER + "blackKing.png", GAME_FOLDER + "red.png", GAME_FOLDER + "redKing.png"};
	
	private boolean king;
	private List<Point> moves;

	protected CheckersPiece(String pieceName, int player) {
		super(pieceName, player);
		moves =  new ArrayList<Point>();
		if(super.playerNumber() == 1) {
			this.moves.add(new Point(-1, -1));
			this.moves.add(new Point(-1, 1));				
		}
		if(super.playerNumber() == 2) {
			this.moves.add(new Point(1, 1));
			this.moves.add(new Point(1, -1));
		}
		this.king = false;
	}

	@Override
	public String getIcons(int player) {
		if(this.king) {
			return PIECE_ICON[(2 * (player-1)) + 1];
		} 
		return PIECE_ICON[2*(player-1)];
	}
	
	public void promote() {
		if(super.playerNumber() == 1) {
			this.moves.add(new Point(1, 1));
			this.moves.add(new Point(1, -1));
		}
		if(super.playerNumber() == 2) {
			this.moves.add(new Point(-1, -1));
			this.moves.add(new Point(-1, 1));
		}
		this.king = true;
		return;
	}
	
	public boolean isKing() {
		return this.king;
	}
	
	@Override
	public List<Point> getMoves() {
		return moves;
	}
}
