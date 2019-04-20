package GameEnvironment;

import java.awt.Point;
import java.util.List;

/* Abstract Piece.java class for: 
 *   - Creating individual game piece
 *   - Determine piece movements
 *   - Store String[] of piece icon for each player
 */
public abstract class Piece { 
	protected String pieceName; //Used for debugging purposes
	protected int player; //Piece belong with which player
	
	//Requires the pieceName (could be left as blank) and which player does the piece belong to
	protected Piece(String pieceName, int player) {
		this.pieceName = pieceName;
		this.player = validPlayer(player);
	};	
	
	/* What are the movement of the pieces
	 * @return a HashMap of all available moves
	 */
	public abstract List<Point> getMoves();
	
	/* A list of string for the piece icons
	 * @param the player number of which the piece belong to
	 * @return the string for location of icon	
	 */
	public abstract String getIcons(int player);
	
	/* Used to determine what player does the piece belong to
	 * @return the player number
	 */
	public int playerNumber() {
		return this.player;
	}
	
	/* Helper function to validate player input in constructor
	 * @param the player number to be validated
	 * @return if player input is valid; else return 1
	 */
	private int validPlayer(int player) {
		if (player > 0 && player <= Board.maxPlayer)
			return player;
		return 1;	
	}	
}
