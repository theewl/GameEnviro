package GameEnvironment.Game.Memory;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;
import javafx.util.Pair;

class MemoryBoard extends Board 
{
	private MemoryPiece[][] gridPieces; //matrix for holding randomized pieces
	private Pair<Integer, Integer> lastp1Coord;
	private Pair<Integer, Integer> lastp2Coord;
	
	private int p1score = 0;
	private int p2score = 0;
	private boolean firstSelection;
	private boolean matched = true;
	
	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) 
	{		
		super(rows, cols, maxPlayer, interaction, currentPiece);
		this.gridPieces = new MemoryPiece[rows][cols];
	}
	
	protected MemoryBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		super(rows, cols, maxPlayer, interaction, currentPiece, pattern, color);
		this.gridPieces = new MemoryPiece[rows][cols];
	}
	
	@Override
	public void startGame() 
	{
		ArrayList<Integer> firstHalf = new ArrayList<Integer>();
		ArrayList<Integer> secondHalf = new ArrayList<Integer>();
		
		int items = (super.getRows() * super.getCols() / 2) - 1;
		for (int i = 0; i <= items; ++i) {
			firstHalf.add(i);
			secondHalf.add(i);
		}
		
		Collections.shuffle(firstHalf);
		Collections.shuffle(secondHalf);			
		
		int halfSelection = 0;
		int firstCounter = items;
		int secondCounter = items;
		
		for (int row = 0; row < super.getRows(); ++row) {
			for (int col = 0; col < super.getCols(); ++col) {
				halfSelection = (Math.random() <= 0.5) ? 1 : 2;
				
				if (halfSelection == 1) {
					if (firstCounter >= 0) 
						gridPieces[row][col] =  new MemoryPiece("", firstHalf.get(firstCounter--));
					else
						gridPieces[row][col] =  new MemoryPiece("", firstHalf.get(secondCounter--));
					}
		
				else
					if (secondCounter >= 0) 
						gridPieces[row][col] =  new MemoryPiece("", firstHalf.get(secondCounter--));
					else
						gridPieces[row][col] =  new MemoryPiece("", firstHalf.get(firstCounter--));							
			}
		}
		
//		for (int i = 0; i < super.getRows(); ++i) {
//			System.out.println();
//			for (int j = 0; j < super.getCols(); ++j) {
//				System.out.print(gridPieces[i][j].getPiece() + " ");
//			}
//		}
		
		super.currentPlayer = 1;
	}


	@Override
	public boolean endGame() 
	{
		int counter = super.getCols() * super.getRows();
		
		for (int row = 0; row < super.getRows(); ++row) 
			for (int col = 0; col < super.getCols(); ++col) 
				if (this.gridPieces[row][col].isFlipped()) 
					--counter;
		
		return (counter == 0 && !isGameTied()) ? true : false;
	}

	@Override
	protected int calculateScore() 
	{
		return Math.max(p1score, p2score);
	}


	@Override
	public boolean isMoveValid(int row, int col)
	{		
	    // Piece that was selected on THIS method call
		MemoryPiece current = this.gridPieces[row][col];
		
		if (current.isFlipped()) {
			return false;
		}
		
		if (this.lastp1Coord != null)
			if (row == this.lastp1Coord.getKey() && col == this.lastp1Coord.getValue())
				return false;
		
		if (!firstSelection && !this.matched) {
			super.updateGrid(this.lastp1Coord.getKey(), this.lastp1Coord.getValue(), null);
			super.updateGrid(this.lastp2Coord.getKey(), this.lastp2Coord.getValue(), null);
		}			
		
		if (!firstSelection) {
			this.firstSelection = true;
			super.updateGrid(row, col, current);
			this.lastp1Coord = new Pair<Integer, Integer>(row, col);
			return true;
		} else {
			this.firstSelection = false;
			super.updateGrid(row, col, current);
			this.lastp2Coord = new Pair<Integer, Integer>(row, col);
			if (this.gridPieces[this.lastp1Coord.getKey()][this.lastp1Coord.getValue()].getIconIndex() == current.getIconIndex()) {
				this.matched = true;
				current.setFlipped(true);
				this.gridPieces[this.lastp1Coord.getKey()][this.lastp1Coord.getValue()].setFlipped(true);
				
				if (this.currentPlayer == 1)
					this.p1score++;
				else
					this.p2score++;
			}
			else {
				this.matched = false;				
			}
			return true;				
		}		
	}

	@Override
	public List<Point> getAvailableMoves() 
	{
		return new ArrayList<Point>();
	}

	@Override
	public boolean isGameTied() 
	{
		return (this.p1score == super.getCols() && this.p2score == super.getCols()) ? true: false;
	}
	
	@Override
	protected void nextPlayer() {
		if(!firstSelection)
			if(++this.currentPlayer > Board.maxPlayer)
				this.currentPlayer = 1;
	}
}
