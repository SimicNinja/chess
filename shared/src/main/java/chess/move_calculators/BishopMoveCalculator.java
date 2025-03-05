package chess.move_calculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class BishopMoveCalculator extends MoveCalculator
{
	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		pieceMoves.addAll(checkDirection(1, 1, board, start));
		pieceMoves.addAll(checkDirection(1, -1, board, start));
		pieceMoves.addAll(checkDirection(-1, 1, board, start));
		pieceMoves.addAll(checkDirection(-1, -1, board, start));

		return pieceMoves;
	}
}