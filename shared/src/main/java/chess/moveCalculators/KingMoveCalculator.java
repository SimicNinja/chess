package chess.moveCalculators;

import chess.*;

import java.util.Collection;

public class KingMoveCalculator extends MoveCalculator
{
	@Override
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition start)
	{
		int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		ChessMove current;

		for (int i = 0; i < directions.length; i++)
		{
			current = checkPieceMove(directions[i][0], directions[i][1], board, start);
			if (current != null)
			{
				pieceMoves.add(current);
			}
		}

		ChessPiece king = board.getPiece(start);

		if (!king.getHasMoved() && board.inStartingPosition(king, start))
		{
			//Variables for readability; 1 & 8 for what the starting column should be.
			ChessPiece rook1 = board.getPiece(start.offset(0, -4));
			ChessPiece rook8 = board.getPiece(start.offset(0, 3));

			if (eligibleRook(rook1) && isPathClear(board, start, -1) && isPathSafe(board, start, -1))
			{
				pieceMoves.add(new ChessMove(start, start.offset(0, -2), null, true));
			}
			if (eligibleRook(rook8) && isPathSafe(board, start, 1) && isPathSafe(board, start, 1))
			{
				pieceMoves.add(new ChessMove(start, start.offset(0, 2), null, true));
			}
		}

		return pieceMoves;
	}

	private boolean eligibleRook(ChessPiece rook)
	{
		return rook != null && rook.getPieceType() == ChessPiece.PieceType.ROOK && !rook.getHasMoved();
	}

	private boolean isPathClear(ChessBoard board, ChessPosition kingStart, int colDirection)
	{
		ChessPosition temp = kingStart.offset(0, colDirection);

		while (temp.inBounds())
		{
			if (board.occupied(temp) && board.getPiece(temp).getPieceType() != ChessPiece.PieceType.ROOK)
			{
				return false;
			}
			temp = temp.offset(0, colDirection);
		}

		return true;
	}

	private boolean isPathSafe(ChessBoard board, ChessPosition kingStart, int colDirection)
	{
		ChessPiece king = board.getPiece(kingStart);
		ChessGame.TeamColor color = king.getTeamColor();
		ChessGame game = new ChessGame(board);
		ChessMove move1 = new ChessMove(kingStart, kingStart.offset(0, colDirection), null);
		ChessMove move2 = new ChessMove(kingStart, kingStart.offset(0, colDirection * 2), null);

		return !game.isInCheck(color) && game.testMove(move1, board) && game.testMove(move2, board);
	}
}
