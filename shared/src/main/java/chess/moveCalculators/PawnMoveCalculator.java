package chess.moveCalculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator extends MoveCalculator
{
	private ArrayList<ChessMove> validMoves = new ArrayList<>();

	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		//Determine color and invert all offsets if black.
		int invert = 1;
		if(board.getPiece(start).getTeamColor() == ChessGame.TeamColor.BLACK)
		{
			invert = -1;
		}

		ChessMove forward = checkMove(invert, 0, board, start);
		ChessMove positiveDiagonal = checkMove(invert, 1, board, start);
		ChessMove negativeDiagonal = checkMove(invert, -1, board, start);
		ChessMove doubleFoward = checkMove(2 * invert, 0, board, start);

		validMoves.add(forward);
		validMoves.add(positiveDiagonal);
		validMoves.add(negativeDiagonal);
		validMoves.add(doubleFoward);

		for(int i = validMoves.size() - 1; i > -1; i--)
		{
			ChessMove move = validMoves.get(i);
			if(move == null)
			{
				validMoves.remove(move);
			}
			else if(move.getEndPosition().getRow() == 8 || move.getEndPosition().getRow() == 1)
			{
				validMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.QUEEN));
				validMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.BISHOP));
				validMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.KNIGHT));
				validMoves.add(new ChessMove(move.getStartPosition(), move.getEndPosition(), ChessPiece.PieceType.ROOK));
				validMoves.remove(move);
			}
		}
		return this.validMoves;
	}

	@Override
	protected ChessMove checkMove(int rowOffset, int colOffset, ChessBoard board, ChessPosition start)
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
