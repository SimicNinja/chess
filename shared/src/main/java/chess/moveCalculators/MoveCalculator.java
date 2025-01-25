package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public interface MoveCalculator
{
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
}