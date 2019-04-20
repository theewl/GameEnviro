package GameEnvironment.Game.Memory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Piece;

class MemoryPiece extends Piece 
{
	private boolean flipped = false;
	private int iconIndex; 
	private final String GAME_FOLDER = System.getProperty("user.dir") + "/src/" + new Object(){}.getClass().getPackage().getName().replace(".", "/") + "/";
	private final String[] PIECE_ICON = {GAME_FOLDER + "1.png", GAME_FOLDER + "2.png", GAME_FOLDER + "3.png", GAME_FOLDER + "4.png",
										 GAME_FOLDER + "5.png", GAME_FOLDER + "6.png", GAME_FOLDER + "A.png", GAME_FOLDER + "B.png",
										 GAME_FOLDER + "C.png", GAME_FOLDER + "D.png", GAME_FOLDER + "E.png", GAME_FOLDER + "F.png",
										 GAME_FOLDER + "G.png", GAME_FOLDER + "H.png", GAME_FOLDER + "I.png", GAME_FOLDER + "J.png",
										 GAME_FOLDER + "K.png", GAME_FOLDER + "L.png", GAME_FOLDER + "M.png", GAME_FOLDER + "N.png",
										 GAME_FOLDER + "O.png", GAME_FOLDER + "P.png", GAME_FOLDER + "Q.png", GAME_FOLDER + "R.png",
										 GAME_FOLDER + "S.png", GAME_FOLDER + "T.png", GAME_FOLDER + "U.png", GAME_FOLDER + "V.png",
										 GAME_FOLDER + "W.png", GAME_FOLDER + "X.png", GAME_FOLDER + "Y.png", GAME_FOLDER + "Z.png"};
	
	protected MemoryPiece(String pieceName, int player) 
	{
		super(pieceName, player);
		this.iconIndex = player;
	}

	@Override
	public List<Point> getMoves() {
		return new ArrayList<Point>();
	}

	@Override
	public String getIcons(int player) 
	{
		return PIECE_ICON[iconIndex];
	}
	
	public int getIconIndex() {
		return this.iconIndex;
	}
	
	public boolean isFlipped() {
		return this.flipped;
	}
	
	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}
}
