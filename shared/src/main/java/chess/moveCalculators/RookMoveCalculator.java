package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator implements MoveCalculator
{
	private Collection<ChessMove> validMoves = new ArrayList<>();

	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		ChessMove test = new ChessMove(start, new ChessPosition(start.getRow(), start.getColumn() + 1), null);

		while(test.getEndPosition().inBounds())
		{
			validMoves.add(test);
			test = new ChessMove(start, test.getEndPosition().offset(0, 1), null);
		}

		return validMoves;
	}
}
