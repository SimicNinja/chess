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
		ChessPosition test = start.offset(0, 1);

		checkDirection(0, 1, board, start);
		checkDirection(0, -1, board, start);
		checkDirection(1, 0, board, start);
		checkDirection(-1, 0, board, start);

		return validMoves;
	}

	private void checkDirection(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
	{
		ChessPosition current = start.offset(rowOffset, colOffset);

		while(current.inBounds())
		{
			validMoves.add(new ChessMove(start, current, null));
			current = current.offset(rowOffset, colOffset);
		}
	}
}
