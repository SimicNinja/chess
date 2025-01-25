package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import jdk.jshell.spi.ExecutionControl;

import java.util.Collection;

public class BishopMoveCalculator implements MoveCalculator
{
	private Collection<ChessMove> validMoves;

	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
	{
		throw new RuntimeException("Not Implemented");
	}
}
