package chess.moveCalculators;

import chess.*;

import java.util.Collection;

public class PawnMoveCalculator extends MoveCalculator
{
	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		//Determine color and invert all offsets if black.
		int invert = 1;
		if(board.getPiece(start).getTeamColor() == ChessGame.TeamColor.BLACK)
		{
			invert = -1;
		}

		ChessMove forward = checkPieceMove(invert, 0, board, start);
		ChessMove positiveDiagonal = checkPieceMove(invert, 1, board, start);
		ChessMove negativeDiagonal = checkPieceMove(invert, -1, board, start);
		ChessMove doubleFoward = checkPieceMove(2 * invert, 0, board, start);

		pieceMoves.add(forward);
		pieceMoves.add(positiveDiagonal);
		pieceMoves.add(negativeDiagonal);
		pieceMoves.add(doubleFoward);

		for(int i = pieceMoves.size() - 1; i > -1; i--)
		{
			ChessMove move = pieceMoves.get(i);
			if(move == null)
			{
				pieceMoves.remove(move);
			}
			else if(move.getEndPosition().getRow() == 8 || move.getEndPosition().getRow() == 1)
			{
				pieceMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.QUEEN));
				pieceMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.BISHOP));
				pieceMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.KNIGHT));
				pieceMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.ROOK));
				pieceMoves.remove(move);
			}
		}
		return this.pieceMoves;
	}

	@Override
	protected ChessMove checkPieceMove(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
	{
		ChessPosition current = start.offset(rowOffset, colOffset);
		ChessMove move = new ChessMove(start, current, null);

		if(!current.inBounds())
		{
			return null;
		}
		else if(colOffset == 0 && !board.occupied(start.offset((int) Math.signum(rowOffset), colOffset)))
		{
			if(rowOffset == 2 && start.getRow() == 2 && !board.occupied(current))
			{
				return move;
			}
			else if(rowOffset == -2 && start.getRow() == 7 && !board.occupied(current))
			{
				return move;
			}
			else if(rowOffset == 1 || rowOffset == -1)
			{
				return move;
			}
		}
		else if(colOffset != 0 && board.containsEnemy(current, board.getPiece(start)))
		{
			return move;
		}
		return null;
	}
}
