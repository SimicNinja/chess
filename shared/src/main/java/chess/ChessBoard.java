package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard
{
	private ChessPiece[][] boardState = new ChessPiece[8][8];

	public ChessBoard() {}

	/**
	 * Adds a chess piece to the chessboard
	 *
	 * @param position where to add the piece to
	 * @param piece    the piece to add
	 */
	public void addPiece(ChessPosition position, ChessPiece piece)
	{
		boardState[position.getRow() - 1][position.getColumn() - 1] = piece;
	}

	/**
	 * Gets a chess piece on the chessboard
	 *
	 * @param position The position to get the piece from
	 * @return Either the piece at the position, or null if no piece is at that
	 * position
	 */
	public ChessPiece getPiece(ChessPosition position)
	{
 		return boardState[position.getRow() - 1][position.getColumn() - 1];
	}

	/**
	 * Sets the board to the default starting board
	 * (How the game of chess normally starts)
	 */
	public void resetBoard()
	{
		powerRow(8);
		pawnRow(7);
		pawnRow(2);
		powerRow(1);
	}

	public boolean occupied(ChessPosition position)
	{
		return boardState[position.getRow() - 1][position.getColumn() - 1] != null;
	}

	public boolean containsAlly(ChessPosition position, ChessPiece piece)
	{
		return this.occupied(position) && getPiece(position).getTeamColor() == piece.getTeamColor();
	}

	public boolean containsEnemy(ChessPosition position, ChessPiece piece)
	{
		return this.occupied(position) && getPiece(position).getTeamColor() != piece.getTeamColor();
	}

	private int symmetry(int col)
	{
		return 9 - col;
	}

	private ChessGame.TeamColor determineColor(int row)
	{
		if(row > 4)
		{
			return ChessGame.TeamColor.BLACK;
		}
		return ChessGame.TeamColor.WHITE;
	}

	private void pawnRow(int row)
	{
		for(int col = 1; col < 9; col++)
		{
			addPiece(new ChessPosition(row, col), new ChessPiece(determineColor(row), ChessPiece.PieceType.PAWN));
		}
	}

	private void powerRow(int row)
	{
		for(int col = 1; col < 9; col++)
		{
			switch(col)
			{
				case 1:
					addPiece(new ChessPosition(row, col), new ChessPiece(determineColor(row), ChessPiece.PieceType.ROOK));
					addPiece(new ChessPosition(row, symmetry(col)), new ChessPiece(determineColor(row), ChessPiece.PieceType.ROOK));
					break;
				case 2:
					addPiece(new ChessPosition(row, col), new ChessPiece(determineColor(row), ChessPiece.PieceType.KNIGHT));
					addPiece(new ChessPosition(row, symmetry(col)), new ChessPiece(determineColor(row), ChessPiece.PieceType.KNIGHT));
					break;
				case 3:
					addPiece(new ChessPosition(row, col), new ChessPiece(determineColor(row), ChessPiece.PieceType.BISHOP));
					addPiece(new ChessPosition(row, symmetry(col)), new ChessPiece(determineColor(row), ChessPiece.PieceType.BISHOP));
					break;
				case 4:
					addPiece(new ChessPosition(row, col), new ChessPiece(determineColor(row), ChessPiece.PieceType.QUEEN));
					addPiece(new ChessPosition(row, symmetry(col)), new ChessPiece(determineColor(row), ChessPiece.PieceType.KING));
					break;
			}
		}
	}

	@Override
	public String toString()
	{
		String out = "\n";
		for(int row = 8; row > 0; row--)
		{
			for(int col = 1; col < 9; col++)
			{
				ChessPiece piece = getPiece(new ChessPosition(row, col));
				if(piece == null)
				{
					out = out + "| ";
				}
				else
				{
					out = out + "|" + piece.toString();
				}
			}
			out = out + "|\n";
		}
		return out;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		ChessBoard that = (ChessBoard) o;
		return Objects.deepEquals(boardState, that.boardState);
	}

	@Override
	public int hashCode()
	{
		return Arrays.deepHashCode(boardState);
	}
}
