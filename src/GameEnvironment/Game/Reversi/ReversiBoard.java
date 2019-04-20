package GameEnvironment.Game.Reversi;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Point;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

public class ReversiBoard extends Board{
	
	private List<Integer> direction;
	private Boolean lock, update, tiedGame;
	private int blackScore, whiteScore, scoreToReturn;

	protected ReversiBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);		
	}
	
	/* Constructor for board with change in appearance
	 * @param row, col, number of player, board interaction, TicTacToePIece, select board pattern, and board color
	 */
	protected ReversiBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		super(rows, cols, maxPlayer, interaction, currentPiece, pattern, color);
	}
	
	/*initialize game board  with 2 black and 2 white pieces
	 * @see GameEnvironment.Board#startGame()
	 */
	@Override
	public void startGame() {
		super.updateGrid(3, 3, new ReversiPiece("",2));
		super.updateGrid(3, 4, new ReversiPiece("",1));
		super.updateGrid(4, 3, new ReversiPiece("",1));
		super.updateGrid(4, 4, new ReversiPiece("",2));
		super.currentPlayer = 1;
		lock = false;
		tiedGame = false;
		blackScore = 0;
		whiteScore = 0;
		scoreToReturn = 0;
		direction = new ArrayList<Integer>();
		return;
		
	}
	
	/*
	 * Checks to see if row and col is a right move for the current player
	 * @see GameEnvironment.Board#isMoveValid(int, int)
	 */
	@Override
	public boolean isMoveValid(int row, int col) {
		if(getGridPieces()[row][col] != null)
			return false;
		
		if(leftDirection(row,col)== 3){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(3);
		}
		
		resetLock();
		if(rightDirection(row,col) == 4){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(4);
		}
		
		resetLock();
		if(upDirection(row,col) == 5){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(5);
		}
		
		resetLock();
		if(downDirection(row,col) == 6){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(6);
		}
		
		resetLock();
		if(leftUpDirection(row,col)== 7){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(7);
		}
		
		resetLock();
		if(rightUpDirection(row,col)== 8){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(8);
		}
		
		resetLock();
		if(leftDownDirection(row,col) == 9){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(9);
		}
		
		resetLock();
		if(rightDownDirection(row,col) == 10){
			if (this.update)
				updateGrid(row, col, new ReversiPiece("",currentPlayer));
			direction.add(10);
		}
		
		resetLock();
		/*
		 * switch statement that calls functions that flip tiles on a specific direction
		 */
		if(direction.size() >0){
			for(int i = 0; i<direction.size(); i++){
				switch(direction.get(i)){
				case 3: flipLeftDirection(row,col);
						break;
				case 4: flipRightDirection(row,col);
						break;
				case 5: flipUpDirection(row,col);
						break;
				case 6: flipDownDirection(row,col);
						break;
				case 7: flipLeftUpDirection(row,col);
						break;
				case 8: flipRightUpDirection(row,col);
						break;
				case 9: flipLeftDownDirection(row,col);
						break;
				case 10: flipRightDownDirection(row,col);
						break;
				}
			}
			direction.clear();
			return true;
		}
		return false;
	}
	
	/*
	 * gets all the available moves for the current player at the current state of the board
	 * @see GameEnvironment.Board#getAvailableMoves()
	 */
	@Override
	public List<Point> getAvailableMoves() {
		this.update = false;
		List<Point> allValidMoves = new ArrayList<Point>();
		for(int row = 0; row<8; row++){
			for(int col = 0; col<8; col++){
				if(super.getGridPieces()[row][col] == null){
					if(isMoveValid(row, col)){
						allValidMoves.add(new Point(row, col));
					}
				}
			}
		}
		this.update = true;
		return allValidMoves;
	}
	
	/*
	 * Override next player
	 * first in switches to the next player
	 * then it checks for all available moves for the new current player
	 * if no moves are found for current player, current player skips a turn switches to next avaiable player
	 */
	@Override
	protected void nextPlayer() {
		
		if(++this.currentPlayer > Board.maxPlayer)
			this.currentPlayer =1;
		
		
		if (getAvailableMoves().isEmpty()){
			if(currentPlayer == 1){
				this.currentPlayer = 2;
			}
			else{
				this.currentPlayer = 1;
			}
		}
		
	}
	
	/*
	 * checks to see if the game has ended by checking if there are no available moves for either player, if this is the case function returns true
	 * it also calculates score
	 * whoever has the most scores wins
	 * whoever has the most scores becomes current player, which is used to display winner in the GUI
	 * if it ends in a tie, returns false, and sets tiedGame flag to true, this variable will take care of the tiedgame  
	 * @see GameEnvironment.Board#endGame()
	 */
	@Override
	public boolean endGame() {
		if(++this.currentPlayer > Board.maxPlayer) //switch to the next player
			this.currentPlayer = 1;
		if(getAvailableMoves().isEmpty())
		{
			if(--this.currentPlayer < 1) //switch back to the original player in turn
				this.currentPlayer = Board.maxPlayer;
			if(getAvailableMoves().isEmpty()){
				totalScores();
				if(blackScore > whiteScore){
					scoreToReturn = blackScore;
					this.currentPlayer = 1;
				}
				else if (whiteScore > blackScore){
					scoreToReturn = whiteScore;
					this.currentPlayer = 2;
				}
				else if( whiteScore == blackScore){
					tiedGame = true;
					return false;
				}
				return true;
			}
		}
		if(--this.currentPlayer < 1)
			this.currentPlayer = Board.maxPlayer;
		return false;
	}

	@Override
	protected int calculateScore() {
		return scoreToReturn;
		
	}
	
	/*
	 * helper funtion to calculate scores
	 */	
	private void totalScores(){
		for(int row = 0; row<8; row++){
			for(int col = 0; col < 8; col++){
				if(getGridPieces()[row][col] != null){
					if(getGridPieces()[row][col].playerNumber() == 1)
						blackScore++;
					else if(getGridPieces()[row][col].playerNumber() == 2)
						whiteScore++;
				}
			}
		}
	}
	
	
	/*
	 * all the functions below are helper functions that check if move is valid at a specific direction
	 */
	private int leftDirection(int r, int c){
		int i = c-1;
		if(i < 0)
			return 0;
		for(; i >= 0; i--){
			if(super.getGridPieces()[r][i] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[r][i].playerNumber()== currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[r][i].playerNumber() == currentPlayer)
					return 3;
		}
		return 0;
	}
	
	private int rightDirection(int r, int c){
		int i = c+1;
		if(i > 7)
			return 0;
		for(; i <= 7; i ++){
			if(getGridPieces()[r][i] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[r][i].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[r][i].playerNumber() == currentPlayer)
					return 4;
		}
		return 0;
	}
	
	private int upDirection(int r, int c){
		int i = r-1;
		if(i < 0)
			return 0;
		for(; i >= 0; i--){
			if(getGridPieces()[i][c] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 5;
		}
		return 0;
	}
	
	private int downDirection(int r, int c){
		int i = r+1;
		if(i > 7)
			return 0;
		for(; i <=7; i++){
			if(getGridPieces()[i][c] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][c].playerNumber() == currentPlayer)
					return 6;
		}
		return 0;
	}
	
	private int leftUpDirection(int r, int c){
		int i = r-1;
		int j = c-1;
		if(i < 0 )
			return 0;
		if(j < 0)
			return 0;
		while(i >= 0 && j>= 0){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 7;
			i--;
			j--;
		}
		return 0;
	}
	
	private int rightUpDirection(int r, int c){
		int i = r-1;
		int j = c+1;
		
		if(i < 0)
			return 0;
		if( j > 7)
			return 0;
		while(i >= 0 && j <= 7){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 8;
			i--;
			j++;
		}
		return 0;
	}
	
	private int leftDownDirection(int r, int c){
		int i = r+1;
		int j = c-1;
		
		if(i > 7)
			return 0;
		if(j < 0)
			return 0;
		while(i <= 7 && j >= 0){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 9;
			i++;
			j--;
		}
		return 0;
	}
	
	private int rightDownDirection(int r, int c){
		int i = r+1;
		int j = c+1;
		
		if(i > 7)
			return 0;
		if(j > 7)
			return 0;
		while(i <=7 && j <= 7){
			if(getGridPieces()[i][j] == null)
				return 0;
			if(!getLock()){
				if(getGridPieces()[i][j].playerNumber() == currentPlayer )
					return 0;
				else
					setLock(true);
			}
			else
				if(getGridPieces()[i][j].playerNumber() == currentPlayer)
					return 10;
			i++;
			j++;
		}
		return 0;
	}
	
	private void flipLeftDirection(int r, int c){
		int i = c-1;
		while(getGridPieces()[r][i].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(r, i, new ReversiPiece("",currentPlayer));
				i--;			
		}			
	}
	
	private void flipRightDirection(int r, int c){
		int i = c+1;
		while(getGridPieces()[r][i].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(r, i, new ReversiPiece("", currentPlayer));
				i++;			
		}
	}
	
	private void flipUpDirection(int r, int c){
		int i = r-1;
		while(getGridPieces()[i][c].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(i, c, new ReversiPiece("",currentPlayer));
				i--;			
		}
	}
	
	private void flipDownDirection(int r, int c){
		int i = r+1;
		while(getGridPieces()[i][c].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(i, c, new ReversiPiece("", currentPlayer));
				i++;			
		}
	}
	
	private void flipLeftUpDirection(int r, int c){
		int i = r-1;
		int j = c-1;
		while(getGridPieces()[i][j].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(i,j, new ReversiPiece("", currentPlayer));
				i--;
				j--;			
		}
	}
	
	private void flipRightUpDirection(int r, int c){
		int i = r-1;
		int j = c+1;
		while(getGridPieces()[i][j].playerNumber()!= currentPlayer){
			if (this.update) 
				updateGrid(i,j, new ReversiPiece("", currentPlayer));
				i--;
				j++;			
		}
	}
	
	private void flipLeftDownDirection(int r, int c){
		int i = r+1;
		int j = c-1;
		while(getGridPieces()[i][j].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(i,j, new ReversiPiece("", currentPlayer));
				i++;
				j--;			
		}
	}
	
	private void flipRightDownDirection(int r, int c){
		int i = r+1;
		int j = c+1;
		while(getGridPieces()[i][j].playerNumber() != currentPlayer){
			if (this.update) 
				updateGrid(i,j,new ReversiPiece("", currentPlayer));
				i++;
				j++;			
		}
	}
	
	private void setLock(boolean status){
		lock = status;
	}
	
	private boolean getLock(){
		return lock;
	}
	
	private void resetLock(){
		lock = false;
	}
	/*
	 * this ends all of the helper functions for all valid moves to specific directions.
	 */	
	
	/*
	 * if tiedGame is true then it return true to the game tied.
	 * @see GameEnvironment.Board#isGameTied()
	 */
	@Override
	public boolean isGameTied() {
		if(tiedGame)
			return true;
		return false;
	}
}
