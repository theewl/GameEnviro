package GameEnvironment.Game.Checkers;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Piece;

public class Checkers implements GameFactory{
	public Checkers() {
		buildGame(this.getClass().getSimpleName());
	}

	@Override
	public void buildGame(String gameName) {
		new GameGUI(buildBoard(), gameName);
		
	}

	@Override
	public Board buildBoard() {
		return new CheckersBoard(8, 8, 2, Interaction.BOARD, buildPieces());
	}

	@Override
	public Piece buildPieces() {
		return null;
	}
}
