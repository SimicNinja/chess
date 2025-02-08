package chess;

import chess.moveCalculators.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece
{
	private final ChessGame.TeamColor color;
	private final ChessPiece.PieceType type;
	private final MoveCalculator calculator;

	public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type)
	{
		this.color = pieceColor;
		this.type = type;
		this.calculator = createCalculator();
	}

	private MoveCalculator createCalculator()
	{
		switch(this.type)
		{
			case KING: return new KingMoveCalculator();
			case QUEEN: return new QueenMoveCalculator();
			case BISHOP: return new BishopMoveCalculator();
			case KNIGHT: return new KnightMoveCalculator();
			case ROOK: return new RookMoveCalculator();
			case PAWN: return new PawnMoveCalculator();
			default: throw new RuntimeException("Piece doesn't have a valid type!");
		}
	}

	/**
	 * The various different chess piece options
	 */
	public enum PieceType
	{
		KING,
		QUEEN,
		BISHOP,
		KNIGHT,
		ROOK,
		PAWN
	}

	/**
	 * @return Which team this chess piece belongs to
	 */
	public ChessGame.TeamColor getTeamColor()
	{
		return this.color;
	}

	/**
	 * @return which type of chess piece this piece is
	 */
	public PieceType getPieceType()
	{
		return this.type;
	}

	/**
	 * Calculates all the positions a chess piece can move to
	 * Does not take into account moves that are illegal due to leaving the king in
	 * danger
	 *
	 * @return Collection of valid moves
	 */
	public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
	{
		return calculator.pieceMoves(board, myPosition);
	}

	public Collection<ChessMove> pieceCapture(ChessBoard board, ChessPosition position)
	{
		return calculator.checkCaptures(board, position);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		ChessPiece that = (ChessPiece) o;
		return color == that.color && type == that.type;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(color, type);
	}

	@Override
	public String toString()
	{
		if(this.color == ChessGame.TeamColor.WHITE)
		{
			switch(this.type)
			{
				case KING: return "K";
				case QUEEN: return "Q";
				case BISHOP: return "B";
				case KNIGHT: return "N";
				case ROOK: return "R";
				case PAWN: return "P";
				default: throw new RuntimeException("Piece doesn't have a valid type!");
			}
		}
		else
		{
			switch(this.type)
			{
				case KING: return "k";
				case QUEEN: return "q";
				case BISHOP: return "b";
				case KNIGHT: return "n";
				case ROOK: return "r";
				case PAWN: return "p";
				default: throw new RuntimeException("Piece doesn't have a valid type!");
			}
		}
	}
}
