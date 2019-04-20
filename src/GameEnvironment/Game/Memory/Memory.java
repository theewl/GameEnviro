package GameEnvironment.Game.Memory;

import java.awt.Color;

import GameEnvironment.Board;
import GameEnvironment.GameFactory;
import GameEnvironment.GameGUI;
import GameEnvironment.Interaction;
import GameEnvironment.Pattern;
import GameEnvironment.Piece;

public class Memory implements GameFactory {

	public Memory () {
		buildGame(this.getClass().getSimpleName());	
	}
	
	@Override
	public void buildGame(String gameName) 
	{
		new GameGUI(buildBoard(), gameName);
	}

	@Override
	public Board buildBoard() 
	{
		return new MemoryBoard(4, 4, 2, Interaction.BOARD, buildPieces(), Pattern.BLANK, Color.WHITE);
	}

	@Override
	public Piece buildPieces() 
	{
		return null;
	}
}
