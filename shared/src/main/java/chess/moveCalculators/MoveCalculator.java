package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public abstract class MoveCalculator
{
	private Collection<ChessMove> validMoves;

	public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start);

	protected Collection<ChessMove> checkDirection(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
	{
		Collection<ChessMove> moves = new ArrayList<>();

		ChessPosition current = start.offset(rowOffset, colOffset);

		while(current.inBounds())
		{
			moves.add(new ChessMove(start, current, null));
			current = current.offset(rowOffset, colOffset);
		}

		return moves;
	}
}