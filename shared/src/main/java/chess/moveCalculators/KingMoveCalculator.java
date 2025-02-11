package chess.moveCalculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
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

//		if(!board.getPiece(start).getHasMoved())
//		{
//			//Variables for readability; 1 & 8 for what the starting column should be.
//			ChessPiece rook1 = board.getPiece(start.offset(0, 3));
//			ChessPiece rook8 = board.getPiece(start.offset(0, -4));
//			if(rook1.getPieceType() == ChessPiece.PieceType.ROOK && rook1.getHasMoved() == false)
//			{
//				pieceMoves.add(new ChessMove(start, start.offset(0, -2), null));
//			}
//			if(rook8.getPieceType() == ChessPiece.PieceType.ROOK && rook8.getHasMoved() == false)
//			{
//				pieceMoves.add(new ChessMove(start, start.offset(0, 2), null));
//			}
//		}

		return pieceMoves;
	}
}
