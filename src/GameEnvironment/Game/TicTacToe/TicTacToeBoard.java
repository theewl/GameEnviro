package GameEnvironment.Game.TicTacToe;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

/* Board object for TicTacToe
 * Build the TicTacToe board logic
 */
class TicTacToeBoard extends Board {
	/* Constructor for board using default appearance (Pattern.CHECKERED, Color.WHITE)
	 * @param row, col, number of player, board interaction, and TicTacToePiece
	 */
	protected TicTacToeBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
	}
	
	/* Constructor for board with change in appearance
	 * @param row, col, number of player, board interaction, TicTacToePIece, select board pattern, and board color
	 */
	protected TicTacToeBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		super(rows, cols, maxPlayer, interaction, currentPiece, pattern, color);
	}
	
	/* Starting state of the game
	 * Select which player to start with
	 */
	@Override
	public void startGame(){
		super.currentPlayer = 1;
	}
	
	/* Determine if player's move is valid
	 * @param row and col selected
	 * @return if move is valid
	 */
	@Override
	public boolean isMoveValid(int row, int col) {
		
		//Player selected an empty space on the grid, add the piece to the Board gridPiece matrix, return true
		if (super.getGridPieces()[row][col] == null) {
			super.updateGrid(row, col, new TicTacToePiece("", super.currentPlayer));			
			return true;
		}
		return false;
	}
	
	/* Return current player's next available move if there is a predefined set of moves
	 * @return HashMap of row and col coordinates; return empty ArrayList if all are allowed
	 */
	@Override
	public List<Point> getAvailableMoves() {
		return new ArrayList<Point>();
	}
	
	/* Determine end game condition
	 * @return if game is to be ended
	 */
	@Override
	public boolean endGame() {		
		//Loop through entire Board gridPiece matrix and see if there is a winner	
		for (int row = 0; row < super.getRows(); ++row)
			for(int col = 0; col < super.getCols(); ++col) {				
				try {
					//Check rows for 3 matching
					if(getPlayerIndex(row, col) == getPlayerIndex((row), (col + 1)) && getPlayerIndex(row, col) == getPlayerIndex((row),( col + 2)))
						return true;
				} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {/*do nothing*/}
				
				try {
					//Check forward slash diagonal for 3 matching
					if(getPlayerIndex(row, col) == getPlayerIndex((row + 1), (col + 1)) && getPlayerIndex(row, col) == getPlayerIndex((row + 2),( col + 2)))
						return true;
				} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {/*do nothing*/}	
				
				try {
					//Check columns for 3 matching
					if(getPlayerIndex(row, col) == getPlayerIndex((row + 1), (col)) && getPlayerIndex(row, col) == getPlayerIndex((row + 2),( col)))
						return true;
				} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {/*do nothing*/}	
				
				try {
					//Check backward slash diagonal for 3 matching
					if(getPlayerIndex(row, col) == getPlayerIndex((row + 1), (col - 1)) && getPlayerIndex(row, col) == getPlayerIndex((row + 2),( col - 2)))
						return true;
				} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {/*do nothing*/}
			}
		
		return false;
	}
	
	/* Helper function to get the player index of each Piece 
	 * @return the player index that own the piece
	 */
	private int getPlayerIndex(int row, int col) {
		return super.getGridPieces()[row][col].playerNumber();
	}

	/* Calculate the winner score, increment winner score by the amount of empty cells
	 * @return the calculated score
	 */
	@Override
	protected int calculateScore() {
		int emptyCells = 0;
		
		//Loop through entire Board gridPiece matrix and see cell is empty
		for (int row = 0; row < super.getRows(); ++row)
			for(int col = 0; col < super.getCols(); ++col) 
				if (super.getGridPieces()[row][col] == null)
					++emptyCells;
		
		return (emptyCells == 0) ? 1 : emptyCells;
	}
	
	/* Determine if game ended in a tie; no more empty cells without a winner 
	 * @return if game ended in a tie
	 */
	@Override
	public boolean isGameTied() {
		for (int row = 0; row < super.getRows(); ++row)
			for (int col = 0; col < super.getCols(); ++col)
				if (super.getGridPieces()[row][col] == null)
					return false;
		return true;
	}	
}

