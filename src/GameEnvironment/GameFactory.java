package GameEnvironment;

/* Interface GameFactory.java class for: 
 *   - Factory for building game
 *   - Create Board object
 *   - Create Piece object
 *   - Call GameGUI to make GUI
 */
public interface GameFactory {
	
	/* Create the game
	 * Call buildBoard(), call buildPieces()
	 * Create the GUI: instantiate GameGUI(buildBoard())
	 */
	public void buildGame(String gameName);	
	Board buildBoard();	
	Piece buildPieces();
}
