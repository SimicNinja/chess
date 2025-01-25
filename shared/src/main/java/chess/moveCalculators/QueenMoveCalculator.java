package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator extends MoveCalculator
{
	private Collection<ChessMove> validMoves = new ArrayList<>();

	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		//Horizontal & Vertical
		validMoves.addAll(checkDirection(0, 1, board, start));
		validMoves.addAll(checkDirection(0, -1, board, start));
		validMoves.addAll(checkDirection(1, 0, board, start));
		validMoves.addAll(checkDirection(-1, 0, board, start));

		//Diagonals
		validMoves.addAll(checkDirection(1, 1, board, start));
		validMoves.addAll(checkDirection(1, -1, board, start));
		validMoves.addAll(checkDirection(-1, 1, board, start));
		validMoves.addAll(checkDirection(-1, -1, board, start));

		return validMoves;
	}
}
