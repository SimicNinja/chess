package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public abstract class MoveCalculator
{
	protected ArrayList<ChessMove> pieceMoves = new ArrayList<>();
	protected ArrayList<ChessMove> captureMoves = new ArrayList<>();

	public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start);

	public Collection<ChessMove> checkCaptures(ChessBoard board, ChessPosition start)
	{
		pieceMoves(board, start);

		for (ChessMove move : pieceMoves)
		{
			if (board.containsEnemy(move.getEndPosition(), board.getPiece(start)))
			{
				captureMoves.add(move);
			}
		}

		return captureMoves;
	}

	protected ChessMove checkPieceMove(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
	{
		ChessPosition current = start.offset(rowOffset, colOffset);

		if (current.inBounds() && !board.containsAlly(current, board.getPiece(start)))
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
		ChessMove current = checkPieceMove(rowMove, colMove, board, start);

		while (current != null)
		{
			moves.add(current);
			if (board.containsEnemy(current.getEndPosition(), board.getPiece(start)))
			{
				break;
			}
			rowMove += rowOffset;
			colMove += colOffset;
			current = checkPieceMove(rowMove, colMove, board, start);
		}

		return moves;
	}
}