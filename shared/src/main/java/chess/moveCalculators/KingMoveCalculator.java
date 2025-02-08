package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public class KingMoveCalculator extends MoveCalculator
{
	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
		ChessMove current;

		for(int i = 0; i < directions.length; i++)
		{
			current = checkMove(directions[i][0], directions[i][1], board, start);
			if(current != null)
			{
				pieceMoves.add(current);
			}
		}

		return pieceMoves;
	}
}
