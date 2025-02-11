package chess;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
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

	public ChessBoard(ChessBoard original)
	{
		this.boardState = new ChessPiece[8][8];

		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				ChessPiece piece = original.boardState[row][col];

				if(piece != null)
				{
					this.boardState[row][col] = new ChessPiece(piece.getTeamColor(), piece.getPieceType());
				}
			}
		}
	}

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
	 * Applies ChessMove to the board. Assumes that the move is valid as logic is found in makeMove() in ChessGame.
	 */
	public void movePiece(ChessMove move)
	{
		//Readability variables
		ChessPiece piece = getPiece(move.getStartPosition());
		ChessGame.TeamColor color = piece.getTeamColor();
		ChessPosition end = move.getEndPosition();

		switch(move.getPromotionPiece())
		{
			case null:
				this.addPiece(end, new ChessPiece(color, piece.getPieceType(), true));
				break;
			case QUEEN:
				this.addPiece(end, new ChessPiece(color, ChessPiece.PieceType.QUEEN, true));
				break;
			case KNIGHT:
				this.addPiece(end, new ChessPiece(color, ChessPiece.PieceType.KNIGHT, true));
				break;
			case BISHOP:
				this.addPiece(end, new ChessPiece(color, ChessPiece.PieceType.BISHOP, true));
				break;
			case ROOK:
				this.addPiece(end, new ChessPiece(color, ChessPiece.PieceType.ROOK, true));
				break;
			case KING:
				break;
			case PAWN:
				break;
		}

		this.removePiece(move.getStartPosition());
	}

	private void removePiece(ChessPosition position)
	{
		boardState[position.getRow() - 1][position.getColumn() - 1] = null;
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

	/**
	 * Checks is an enemy is located at the designate positon
	 *
	 * @param position The position that may contain an enemy
	 * @param piece The piece you are moving
	 * @return True if enemy piece is at position, otherwise false
	 */
	public boolean containsEnemy(ChessPosition position, ChessPiece piece)
	{
		return this.occupied(position) && getPiece(position).getTeamColor() != piece.getTeamColor();
	}


	/**
	 * Implemented to make for each loops usable in ChessGame
	 *
	 * @param color The color of the team you are looking for
	 * @return An iterator used to find all the pieces on the board of a given team/color
	 */
	public Iterable<ChessPieceAndPosition> getTeamPieces(ChessGame.TeamColor color)
	{
		return () -> new ChessPieceIterator(color);
	}

	private class ChessPieceIterator implements Iterator<ChessPieceAndPosition>
	{
		private final ChessGame.TeamColor targetTeam;
		private int row = 0;
		private int col = 0;
		private boolean hasNext = false;

		public ChessPieceIterator(ChessGame.TeamColor color)
		{
			targetTeam = color;
			findNext();
		}

		@Override
		public boolean hasNext()
		{
			return hasNext;
		}

		@Override
		public ChessPieceAndPosition next()
		{
			if(!hasNext())
			{
				throw new NoSuchElementException("No more chess pieces");
			}

			ChessPiece current = boardState[row][col];
			ChessPosition position = new ChessPosition(row + 1, col + 1);

			col++;
			if(col > 7)
			{
				col = 0;
				row++;
			}
			findNext();

			return new ChessPieceAndPosition(current, position);
		}

		private void findNext()
		{
			hasNext = false;
			while (row < 8)
			{
				while (col < 8)
				{
					ChessPiece piece = boardState[row][col];

					if (piece != null && piece.getTeamColor() == targetTeam)
					{
						hasNext = true;
						return;
					}

					col++;
				}

				col = 0;
				row++;
			}
		}
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
