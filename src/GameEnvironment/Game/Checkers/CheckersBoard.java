package GameEnvironment.Game.Checkers;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import GameEnvironment.Board;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

public class CheckersBoard extends Board{
	
	private Point currentPieceLocation;
	private boolean pieceSelected;
	private boolean combo;
	
	protected CheckersBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece) {
		super(rows, cols, maxPlayer, interaction, currentPiece);
		this.currentPieceLocation = null;
		this.pieceSelected = false;
		this.combo = false;
	}
	
	protected CheckersBoard(int rows, int cols, int maxPlayer, Interaction interaction, Piece currentPiece, Pattern pattern, Color color) {
		super(rows, cols, maxPlayer, interaction, currentPiece, pattern, color);
		this.currentPieceLocation = null;
		this.pieceSelected = false;
		this.combo = false;
	}

	@Override
	public void startGame() {
		for(int i=0; i < getRows(); ++i) {
			if (i != 3 && i != 4) {
				for(int j=0; j < getCols(); ++j) {
					CheckersPiece piece = new CheckersPiece(i<3?"black":"red",i<3?2:1);
					if((i%2 == 0 && j%2 == 0) || (i%2 != 0 && j%2 != 0))
						this.updateGrid(i, j, piece);
				}
			}
		}
		super.currentPlayer = 1;
	}

	@Override
	public boolean endGame() {
		boolean tmp[] = new boolean[] {true, true};
		for(int x = 0; x < getRows(); x++) {
			for(int y = 0; y < getCols(); y++) {
				Piece p = getGridPieces()[x][y];
				if (p != null)
					tmp[p.playerNumber() - 1] = false;	
			}
		}
		return tmp[0] || tmp[1];
	}

	@Override
	protected int calculateScore() {
		int tmp = 0;
		for(int x = 0; x < getRows(); x++) {
			for(int y = 0; y < getCols(); y++) {
				Piece p = getGridPieces()[x][y];
				if (p != null) {
					tmp += p.playerNumber() > 1 ? 1 : -1;
				}			
			}
		}
		return Math.abs(tmp);
	}
	
	@Override
	protected void nextPlayer() {
		if(!this.pieceSelected) {
			if(++this.currentPlayer > 2)
				this.currentPlayer = 1;
		}
	}		

	@Override
	public boolean isMoveValid(int row, int col) {
		List<Point> availableMoves = getAvailableMoves();
		Point move =  new Point(row,col);
		if(availableMoves.contains(move)) {
			if(this.pieceSelected) {
				List<Point> c = getCaptures(this.currentPieceLocation.x, this.currentPieceLocation.y, this.currentPiece);
				updateGrid(row, col, this.currentPiece);
				updateGrid(currentPieceLocation.x, currentPieceLocation.y, null);
				promotePiece(row, (CheckersPiece) this.currentPiece);
				this.pieceSelected = false;
				this.combo = false;
				if(c.contains(move)) {					
					int x;
					int y;
					List<Point> pMoves = currentPiece.getMoves();
					for(int i = 0; i < pMoves.size(); i++) {
						x = currentPieceLocation.x + pMoves.get(i).x;
						y = currentPieceLocation.y + pMoves.get(i).y;
						if((x + pMoves.get(i).x) == row && (y + pMoves.get(i).y) == col) {
							//subtract points for other player here
							//check the piece's .playerNumber() and subtract their score.
							updateGrid(x, y, null);
						}
					}
					if(!(getCaptures(row, col, this.currentPiece).isEmpty())) {
						this.combo = true;
						this.pieceSelected = true;
						this.currentPiece = getGridPieces()[row][col];
						this.currentPieceLocation = new Point(row, col);
					}
				}
				return true;
			}
			if(!this.pieceSelected) {
				this.pieceSelected = true;
				this.currentPiece = getGridPieces()[row][col];
				this.currentPieceLocation = new Point(row, col);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Point> getAvailableMoves() {
		List<Point> availableMoves = new ArrayList<Point>();
		if(!this.pieceSelected) {
			for(int x = 0; x < getRows(); x++) {
				for(int y = 0; y < getCols(); y++) {
					if(getGridPieces()[x][y] != null && 
					   getGridPieces()[x][y].playerNumber() == this.currentPlayer &&
					   hasMoves(x, y, getGridPieces()[x][y])){
						availableMoves.add(new Point(x,y));
					}			
				}
			} 
		}
		
		if(this.pieceSelected && !combo) {
			availableMoves.addAll(getCaptures(this.currentPieceLocation.x, this.currentPieceLocation.y, this.currentPiece));
			availableMoves.addAll(getMoves(this.currentPieceLocation.x, this.currentPieceLocation.y, this.currentPiece));
		}
		if(combo) {
			availableMoves.addAll(getCaptures(this.currentPieceLocation.x, this.currentPieceLocation.y, this.currentPiece));
		}
		
		return availableMoves;	
	}


	@Override
	public boolean isGameTied() {
		return false;
	}
	
	private boolean hasMoves(int row, int col, Piece p) {
		return (!(getCaptures(row, col, p).isEmpty()) || !(getMoves(row, col, p).isEmpty()));
	}
	
	private List<Point> getCaptures(int row, int col, Piece p){
		List<Point> captures = new ArrayList<Point>();
		int x;
		int y;
		List<Point> pMoves = p.getMoves();
		for(int i = 0; i < pMoves.size(); i++) {
			x = row + pMoves.get(i).x;
			y = col + pMoves.get(i).y;
			if(x >= getRows() || x < 0 ||
			   y >= getCols() || y < 0) {
				continue;
			}
			if(getGridPieces()[x][y] != null && getGridPieces()[x][y].playerNumber() != this.currentPlayer) {
				x += pMoves.get(i).x;
				y += pMoves.get(i).y;
				if(x >= getRows() || x < 0 ||
				   y >= getCols() || y < 0) {
					continue;
				}
				if(getGridPieces()[x][y] == null) {
					captures.add(new Point(x,y));
				}
			}		
		}
		
		return captures;
	}
	
	private List<Point> getMoves(int row, int col, Piece p){
		List<Point> moves = new ArrayList<Point>();
		int x;
		int y;
		List<Point> pMoves = p.getMoves();
		for(int i = 0; i < pMoves.size(); i++) {
			x = row + pMoves.get(i).x;
			y = col + pMoves.get(i).y;
			if(x >= getRows() || x < 0 ||
			   y >= getCols() || y < 0) {
				continue;
			}
			if(getGridPieces()[x][y] == null) {
				moves.add(new Point(x, y));
			}
		}
		return moves;
	}
	
	private void promotePiece(int row, CheckersPiece p) {
		if(this.currentPlayer == 1) {
			if(row == 0)
				p.promote();
		}
		if(this.currentPlayer == 2) {
			if(row == 7)
				p.promote();
		}
	}
}
