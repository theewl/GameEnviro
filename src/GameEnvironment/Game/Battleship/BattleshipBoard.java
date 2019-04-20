package GameEnvironment.Game.Battleship;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

/* Battleship board for the main game
 * Battleship game logic
 * Extends from GameEnvironment.Board
 */
class BattleshipBoard extends Board {
	
	BattleshipPiece[][] shipBoard;  //Store the Pieces placed by the users in the battleship staging
	private int[] playerScore;  //Current score of each player
	private final int MAX_SCORE = 17;  //Maximum score each player can achieve
	
	/* Constructor for battleship board with default background
	 * Call parent constructor, instantiate shipBoard and playerScore
	 */
	protected BattleshipBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
		this.shipBoard = new BattleshipPiece[rows][cols];
		this.playerScore = new int[maxPlayer];
	}
	
	/* Constructor for battleship board with changes to background
	 * Call parent constructor, instantiate shipBoard and playerScore
	 */
	protected BattleshipBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		super(rows, cols, maxPlayer, interaction, currentPiece, pattern, color);
		this.shipBoard = new BattleshipPiece[rows][cols];
		this.playerScore = new int[maxPlayer];
	}
	
	/* Start game state
	 * Select who goes first
	 */
	@Override
	public void startGame(){
		super.currentPlayer = 1;
	}
	
	/* Check if current input is a valid move
	 * If it is, add a hit or miss piece to the gridPiece array
	 * @param row and col selected
	 * @return if the move is valid
	 */
	@Override
	public boolean isMoveValid(int row, int col) {
		if (isValidSide(row, col)) {
			addMissileResult(row, col);
			return true;
		}
		return false;
	}
	
	/* Helper function to determine if move is allowed
	 * @param row and col selected
	 * @return if move is allowed
	 */
	private boolean isValidSide(int row, int col) {
		return (col >= (super.currentPlayer % 2) * 10 && col < ((super.currentPlayer % 2) + 1) * 10 &&
				super.getGridPieces()[row][col] == null);
	}
	
	/* Helper function to determine which piece to add to the gridPiece
	 * @param row and col selected
	 */
	private void addMissileResult(int row, int col) {
		BattleshipPiece temp = new BattleshipPiece("", super.currentPlayer);
		
		//Determine if player hit or miss a ship, checked the store array
		if (this.shipBoard[row][col] != null) { 
			temp.missileHit = true;
			this.playerScore[super.currentPlayer - 1]++;
		}
		else
			temp.missileHit = false;			
		
		//Add piece
		super.updateGrid(row, col, temp);		
	}
	
	/* Available next moves
	 * @return a list of available moves; return an empty arraylist if all moves are available
	 */
	@Override
	public List<Point> getAvailableMoves() {
		return new ArrayList<Point>();
	}
	
	/* Determine end game state
	 * Whenever any of the player scored the MAX_SCORE
	 * @return if a player won
	 */
	@Override
	public boolean endGame() {	
		if (playerScore[0] == MAX_SCORE || playerScore[1] == MAX_SCORE) return true;
		return false;
	}
	
	/* Calculate score for the winner, difference between the two players
	 * @return the calculated score
	 */
	@Override
	protected int calculateScore() {
		return Math.abs(playerScore[0] - playerScore[1]);
	}
	
	/* Determine if game ended in a draw
	 * @return if game is a tie; game can never be tied
	 */
	@Override
	public boolean isGameTied() {	
		return false;
	}	
	
	/* Helper function to print out the stored piece array
	 */
	public void printShipBoard() {
		System.out.println(Arrays.deepToString(this.shipBoard));
	}
}

