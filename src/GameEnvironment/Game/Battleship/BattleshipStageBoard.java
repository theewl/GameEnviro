package GameEnvironment.Game.Battleship;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;
import javafx.util.Pair;

/* Game board logic for battleship staging board
 * Extends from GameEnvironment.Board
 */
public class BattleshipStageBoard extends Board{
	
	private boolean firstSelect;  //First selection before final placement of ship
	private Pair<Integer, Integer> piecePoint;  //Current piece point on the grid (key = row, value = col)
	private final int[] ships = {1, 2, 2, 3, 4};  //Length of ship away from the center point
	private int currentShip = 0;  //Current ship being placed
	private List<Point> availableMoves;   //Array list of all available placement spaces for the current ship
	private BattleshipBoard battleshipBoard;  //Resulting Battleship Board built from the two battleship staging boards
	
	/* Constructor for board with default background
	 * Call parent constructor
	 */
	protected BattleshipStageBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
	}
	
	/* Constructor for board with predetermined background
	 * Call parent constructor
	 */
	protected BattleshipStageBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		super(rows, cols, maxPlayer, interaction, currentPiece, pattern, color);
	}
	
	/* Start state for the game
	 * Currently only one player, so nothing to add
	 */
	@Override
	public void startGame() {
	}
	
	/* Determine if selection is valid
	 * @param row and col selected
	 * @return if move is valid
	 */
	@Override
	public boolean isMoveValid(int row, int col) {	
		
		//If player placed all 5 ships, return false
		if (this.currentShip == 5) return false;
		
		//If space is allowed and not first selection
		//Add ship to board
		//Reset all counter and go to next ship; return true
		if (isInAvailableMoves(row, col) && this.firstSelect) {
			fillShip(row, col);
			this.firstSelect = false;
			this.piecePoint = null;
			this.currentShip++;
			return true;
		}
		
		//If space is empty and this is your first selection
		//Make selection true and mark the point selected
		//Add temporary piece to grid
		else if (super.getGridPieces()[row][col] == null && !this.firstSelect) {
			this.firstSelect = true;
			this.piecePoint = new Pair<Integer, Integer>(row, col);
			super.updateGrid(row, col, new BattleshipPiece("", super.currentPlayer));
			return true;
		}		
		
		//If first or second selection is invalid, remove piece and return false
		removeInvalid();
		return false;
	}
	
	/* Helper function to determine if move is in availableMoves array
	 * @param row and col selected
	 * @return if move is in the array 
	 */
	private boolean isInAvailableMoves(int row, int col) {
		if (this.availableMoves != null)
			for (Point coord: this.availableMoves) 
				if ((int)coord.getX() == row && (int)coord.getY() == col)
					return true;
		return false;
	}
	
	/* Helper functio to fill the ship from the first selected point to the final selected point
	 * @param row and col selected
	 */
	private void fillShip(int row, int col) {
		//Filled horizontal placement
		for (int i = Math.min(row, this.piecePoint.getKey()); i <= Math.max(row, this.piecePoint.getKey()); ++i)
			super.updateGrid(i, col, new BattleshipPiece("", super.currentPlayer));
		
		//Fill vertical placement
		for (int i = Math.min(col, this.piecePoint.getValue()); i <= Math.max(col, this.piecePoint.getValue()); ++i)
			super.updateGrid(row, i, new BattleshipPiece("", super.currentPlayer));
	}
	
	/* Helper function to remove first placement of ship if second placement is invalid 
	 * Reset all counters
	 */
	private void removeInvalid() {
		super.getGridPieces()[this.piecePoint.getKey()][this.piecePoint.getValue()] = null;	
		this.availableMoves = null;
		this.piecePoint = null;
		this.firstSelect = false;
	}
	
	/* Determine all available moves at the current stage
	 * @return an array of available moves
	 */
	@Override
	public List<Point> getAvailableMoves() {
		int tempX, tempY;
		this.availableMoves = new ArrayList<Point>();
		
		//If first section is made
		if(this.piecePoint != null) {
			
			//Check north of selection
			tempX = (this.piecePoint.getKey() - ships[this.currentShip]);
			tempY = this.piecePoint.getValue();
			if (tempX > -1 && isSpaceAvailable(tempX, tempY))				
				this.availableMoves.add(new Point(tempX, tempY));
			
			//Check south of selection
			tempX = (this.piecePoint.getKey() + ships[this.currentShip]);
			tempY = this.piecePoint.getValue();
			if (tempX < super.getRows() && isSpaceAvailable(tempX, tempY))				
				this.availableMoves.add(new Point(tempX, tempY));
			
			//Check west of selection
			tempX = this.piecePoint.getKey();
			tempY = (this.piecePoint.getValue() - ships[this.currentShip]);
			if (tempY > -1 && isSpaceAvailable(tempX, tempY))				
				this.availableMoves.add(new Point(tempX, tempY));
			
			//Check east of selection
			tempX = this.piecePoint.getKey();
			tempY = (this.piecePoint.getValue() + ships[this.currentShip]);
			if (tempY < super.getCols() && isSpaceAvailable(tempX, tempY))				
				this.availableMoves.add(new Point(tempX, tempY));
		}
			
		return this.availableMoves;
	}
	
	/* Helper function to determine if space around the first selection is available
	 * Including previously placed ships
	 * @param row and col of first selection
	 * @return if space is available
	 */
	private boolean isSpaceAvailable(int row, int col) {
		int rowCount = 0, colCount = 0, i = 0, j = 0;
		
		//Check north of selection
		if (row < this.piecePoint.getKey())
			for (i = rowCount = row; i < this.piecePoint.getKey(); ++i) 
				if (super.getGridPieces()[i][col] == null)
					++rowCount;
		
		//Check south of selection
		if (row > this.piecePoint.getKey())
			for (i = rowCount = row; i > this.piecePoint.getKey(); --i) 
				if (super.getGridPieces()[i][col] == null)
					--rowCount;
		
		//Check west of selection
		if (col < this.piecePoint.getValue())
			for (j = colCount = col; j < this.piecePoint.getValue(); ++j) 
				if (super.getGridPieces()[row][j] == null)
					++colCount;
		
		//Check east of selection
		if (col > this.piecePoint.getValue())
			for (j = colCount = col; j > this.piecePoint.getValue(); --j) 
				if (super.getGridPieces()[row][j] == null)
					--colCount;
		
		return (rowCount == i && colCount == j) ? true : false;
	}
	
	/* Determine if game ended; game never end really
	 * Used to determine how to handle the two battleship staging board
	 * @return false always
	 */
	@Override
	public boolean endGame() {		
		
		//If player one staging board is closed and there is not all 5 pieces placed, bring it back
		if (!Battleship.stage1.isDisplayable() && Battleship.stageBoard1.getCurrentShip() < 5) Battleship.stage1.setVisible(true);
		
		//If player two staging board is closed and there is not all 5 pieces placed, bring it back
		else if (!Battleship.stage2.isDisplayable() && Battleship.stageBoard2.getCurrentShip() < 5) Battleship.stage2.setVisible(true);
		
		//If both players finish placing their pieces, create the GameGUI for the BattleshipBoard
		//Move the both battleship staging board into the final battleship game board 
		//Close all the staging boards and make it null
		else if (Battleship.stageBoard1.getCurrentShip() == 5 && Battleship.stageBoard2.getCurrentShip() == 5) {
			
			this.battleshipBoard = new BattleshipBoard(Battleship.MAX_GRID_SIZE, Battleship.MAX_GRID_SIZE * 2, 2, Interaction.BOARD, new BattleshipPiece("", 1), Pattern.BLANKED_LINE, Color.BLUE);
			fillBattleshipBoard();
			new GameGUI(this.battleshipBoard, Battleship.battleship);
			
			Battleship.stageBoard1 = null;
			Battleship.stageBoard2 = null;			
			
			Battleship.stage1.dispose();
			Battleship.stage1 = null;			
			Battleship.stage2.dispose();	
			Battleship.stage2 = null;
		}
		return false;
	}
	
	/* Helper function to fill the battleship game board with the input in the battleship staging board
	 */
	private void fillBattleshipBoard() {
		
		//Fill battleship game board left side with the player 1 staging board input
		for (int row = 0; row < Battleship.stageBoard1.getRows(); ++row)
			for (int col = 0; col < Battleship.stageBoard1.getCols(); ++col)
				if (Battleship.stageBoard1.getGridPieces()[row][col] != null)
					this.battleshipBoard.shipBoard[row][col] = (BattleshipPiece) Battleship.stageBoard1.getGridPieces()[row][col];
		
		//Fill battleship game board right side with the player 2 staging board input
		for (int row = 0; row < Battleship.stageBoard2.getRows(); ++row)
			for (int col = 0; col < Battleship.stageBoard2.getCols(); ++col)
				if (Battleship.stageBoard2.getGridPieces()[row][col] != null)
					this.battleshipBoard.shipBoard[row][col + Battleship.MAX_GRID_SIZE] = (BattleshipPiece) Battleship.stageBoard2.getGridPieces()[row][col];
	}
	
	/* Determine if game ended in a tied; never use
	 * @return false always
	 */
	@Override
	public boolean isGameTied() {
		return false;
	}
	
	/* Calculate ending score; never used
	 * @return 0 always;
	 */
	@Override
	protected int calculateScore() {
		return 0;
	}
	
	/* Determine status to input in the staging JFrame
	 * @return current staging status
	 */
	@Override
	protected String getStatus() {
		if (this.currentShip == 0) return "Instruction: Place Battleship of two(2) spaces.";
		else if (this.currentShip == 1) return "Instruction: Place Battleship of three(3) spaces.";
		else if (this.currentShip == 2) return "Instruction: Place Battleship of three(3) spaces.";
		else if (this.currentShip == 3) return "Instruction: Place Battleship of four(4) spaces.";
		else if (this.currentShip == 4) return "Instruction: Place Battleship of five(5) spaces.";
		else return "Instruction: All Battleships are placed. Wait for the other player.";
	}
	
	/* Helper function to get current ship being placed
	 * @return the current ship being placed
	 */
	private int getCurrentShip() {
		return this.currentShip;
	}

}
