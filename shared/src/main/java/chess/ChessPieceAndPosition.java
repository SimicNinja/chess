package chess;

public class ChessPieceAndPosition
{
	private final ChessPiece piece;
	private final ChessPosition position;

	public ChessPieceAndPosition(ChessPiece piece, ChessPosition position)
	{
		this.piece = piece;
		this.position = position;
	}

	public ChessPiece getPiece()
	{
		return piece;
	}

	public ChessPosition getPosition()
	{
		return position;
	}
}
