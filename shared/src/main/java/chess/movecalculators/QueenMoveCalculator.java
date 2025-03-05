package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class QueenMoveCalculator extends MoveCalculator
{
	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		//Horizontal & Vertical
		pieceMoves.addAll(checkDirection(0, 1, board, start));
		pieceMoves.addAll(checkDirection(0, -1, board, start));
		pieceMoves.addAll(checkDirection(1, 0, board, start));
		pieceMoves.addAll(checkDirection(-1, 0, board, start));

		//Diagonals
		pieceMoves.addAll(checkDirection(1, 1, board, start));
		pieceMoves.addAll(checkDirection(1, -1, board, start));
		pieceMoves.addAll(checkDirection(-1, 1, board, start));
		pieceMoves.addAll(checkDirection(-1, -1, board, start));

		return pieceMoves;
	}
}
