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

	protected ChessMove checkMove(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
	{
		ChessPosition current = start.offset(rowOffset, colOffset);

		if(current.inBounds() && !board.containsAlly(current, board.getPiece(start)))
		{
			return new ChessMove(start, current, null);
		}
		return null;
	}

	protected Collection<ChessMove> checkDirection(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
	{
		Collection<ChessMove> moves = new ArrayList<>();
		int rowMove = rowOffset;
		int colMove = colOffset;
		ChessMove current = checkMove(rowMove, colMove, board, start);

		while(current != null)
		{
			moves.add(current);
			if(board.containsEnemy(current.getEndPosition(), board.getPiece(start)))
			{
				break;
			}
			rowMove += rowOffset;
			colMove += colOffset;
			current = checkMove(rowMove, colMove, board, start);
		}

		return moves;
	}
}