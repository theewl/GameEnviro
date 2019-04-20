package GameEnvironment;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

/* Abstract Board.java class for: 
 *   - Creating a board object
 *   - Hold a matrix of the pieces on the board
 *   - Hold the starting board state
 *   - Determine if move is valid
 *   - Store next available moves
 *   - End game state
 */
public abstract class Board {
	private int rows; //Number of rows for the board 
	private int cols; //Number of cols for the board
	private Piece[][] gridPieces; //Matrix for the pieces
	protected Piece currentPiece; //Single instance of a piece
	private Interaction interaction; //Board interaction
	protected Pattern pattern;
	private Color color;
	protected static int maxPlayer; //Max number of players
	protected int currentPlayer; //Current player number
	
	/* Constructor without changes to background
	/* User must supply the size of the board, maximum number of player, interaction of board, and a piece of the board
	 * Rows and cols used to instantiate gridPieces;
	 */
	protected Board(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		this.rows = rows;
		this.cols = cols;
		this.interaction = interaction;
		Board.maxPlayer = maxPlayer;
		this.currentPiece = currentPiece;
		this.gridPieces = new Piece[this.rows][this.cols];
		this.pattern = Pattern.CHECKERED;
		this.color = Color.WHITE;
	}
	
	/* Constructor with changes to background
	/* User must supply the size of the board, maximum number of player, interaction of board, a piece of the board, board pattern, and board color
	 * Rows and cols used to instantiate gridPieces;
	 */
	protected Board(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		this(rows, cols, maxPlayer, interaction, currentPiece);
		this.pattern = pattern;
		this.color = color;
	}
	
	/* Set currentPlayer to the starting player number; should correspond with the Piece object
	 * Add all starting Pieces to gridPieces; if there are no starting pieces, do not do this part
	 */
	public abstract void startGame(); 
	
	/* Loop through gridPieces and determine if position clicked is a valid move
	 * Call Piece move method to get current move and check with the gridPieces to determine if move is valid
	 * @return if move is valid
	 */
	public abstract boolean isMoveValid(int row, int col);
	
	/* Determine what are the available next move for the pieces
	 * Call Piece move method to get current move and check with the gridPieces to determine if next move is available
	 * if valid, call nextPlayer method;
	 * @return a HashMap of all available next move; else return null;
	 */
	public abstract List<Point> getAvailableMoves(); 
	
	/* End game state, determine if the game is completed
	 * Loop through the gridPieces to determine so
	 * call calculateScore()
	 * @return if game has ended
	 */
	public abstract boolean endGame();
	
	/* Tied game state, determine if the game is completed and no winner
	 * Loop through the gridPieces to determine so
	 * @return if game has ended in a tie
	 */
	public abstract boolean isGameTied();
	
	/* Add new pieces to gridPieces, if validMove function is true 
	 */
	public void updateGrid(int row, int col, Piece piece) {
		this.gridPieces[row][col] = piece;
	}	
	
	/* Calculate scores for the winner and loser
	 * Add game score to savedScores HashMap, in Main.java using the playerOne and playerTwo String, in Main.java 
	 */
	protected abstract int calculateScore();
	
	/* Helper function to determine next player, cycle through all the players
	 */	
	protected void nextPlayer() {
		if(++this.currentPlayer > Board.maxPlayer)
			this.currentPlayer = 1;
	}
	
	/* Get row index
	 * @return number of row in board
	 */
	public int getRows() {
		return this.rows;
	}
	
	/* Get cols index
	 * @return number of cols in board
	 */
	public int getCols() {
		return this.cols;
	}	
	
	/* Get the gridPieces
	 * @return gridPieces matrix
	 */
	public Piece[][] getGridPieces() {
		return this.gridPieces;
	}

	/* Get board pattern
	 * @return pattern of board
	 */
	public Interaction getInteraction() {
		return this.interaction;
	}
	
	/* Get index of current player
	 * @return index of current player
	 */
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	/* Get index of current piece
	 * @return current piece
	 */
	public Piece getCurrentPiece() {
		return this.currentPiece;
	}
	
	/* Get board pattern
	 * @return current board pattern
	 */
	public Pattern getPattern() {
		return this.pattern;
	}
	
	/* Get name of board color
	 * @return board color
	 */
	public Color getColor() {
		return this.color;
	}
	
	/* Get current status of board
	 * @return status string
	 */
	protected String getStatus() {
		return "Status: Currently Player " + this.currentPlayer + " turn...";
	}
}
