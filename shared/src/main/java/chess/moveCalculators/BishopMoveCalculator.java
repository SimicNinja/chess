package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator extends MoveCalculator
{
	private Collection<ChessMove> validMoves = new ArrayList<>();

	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		validMoves.addAll(checkDirection(1, 1, board, start));
		validMoves.addAll(checkDirection(1, -1, board, start));
		validMoves.addAll(checkDirection(-1, 1, board, start));
		validMoves.addAll(checkDirection(-1, -1, board, start));

		return validMoves;
	}
}