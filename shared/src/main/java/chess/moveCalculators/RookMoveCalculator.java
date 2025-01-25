package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator extends MoveCalculator
{
	private Collection<ChessMove> validMoves = new ArrayList<>();

	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		ChessPosition test = start.offset(0, 1);

		validMoves.addAll(checkDirection(0, 1, board, start));
		validMoves.addAll(checkDirection(0, -1, board, start));
		validMoves.addAll(checkDirection(1, 0, board, start));
		validMoves.addAll(checkDirection(-1, 0, board, start));

		return validMoves;
	}
}
