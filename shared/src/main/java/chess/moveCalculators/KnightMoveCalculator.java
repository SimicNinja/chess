package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class KnightMoveCalculator extends MoveCalculator
{
	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		int[][] directions = {{2,1}, {1,2}, {-1,2}, {-2,1}, {-2,-1}, {-1,-2}, {1,-2}, {2,-1}};
		ChessMove current;

		for(int[] direction : directions)
		{
			current = checkMove(direction[0], direction[1], board, start);
			if (current != null)
			{
				pieceMoves.add(current);
			}
		}

		return pieceMoves;
	}
}
