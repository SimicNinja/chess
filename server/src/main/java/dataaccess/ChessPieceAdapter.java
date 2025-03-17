package dataaccess;

import chess.ChessGame;
import chess.ChessPiece;
import chess.movecalculators.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece>
{
	@Override
	public ChessPiece deserialize(JsonElement json,
		Type type, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject pieceObject = json.getAsJsonObject();
		ChessGame.TeamColor color = context.deserialize(pieceObject.get("color"), ChessGame.TeamColor.class);
		ChessPiece.PieceType pieceType = context.deserialize(pieceObject.get("type"), ChessPiece.PieceType.class);
		boolean hasMoved = pieceObject.get("hasMoved").getAsBoolean();
//		MoveCalculator calculator;

//		switch(pieceType)
//		{
//			case KING:
//				calculator = new KingMoveCalculator();
//				break;
//			case QUEEN:
//				calculator = new QueenMoveCalculator();
//				break;
//			case BISHOP:
//				calculator = new BishopMoveCalculator();
//				break;
//			case KNIGHT:
//				calculator = new KnightMoveCalculator();
//				break;
//			case ROOK:
//				calculator = new RookMoveCalculator();
//				break;
//			case PAWN:
//				calculator = new PawnMoveCalculator();
//				break;
//			default:
//				throw new JsonParseException("Unknown PieceType: " + pieceType);
//		}

		return new ChessPiece(color, pieceType, hasMoved);
	}

//	{"color":"WHITE","board":{"boardState":[[
//	{"color":"WHITE","type":"ROOK","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"KNIGHT","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"BISHOP","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"QUEEN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"KING","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"BISHOP","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"KNIGHT","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"ROOK","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false}],
//
//	[{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"WHITE","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false}],
//
//	[null,null,null,null,null,null,null,null],
//	[null,null,null,null,null,null,null,null],
//	[null,null,null,null,null,null,null,null],
//	[null,null,null,null,null,null,null,null],
//
//	[{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"PAWN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false}],
//
//	[{"color":"BLACK","type":"ROOK","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"KNIGHT","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"BISHOP","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"QUEEN","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"KING","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"BISHOP","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"KNIGHT","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false},
//	{"color":"BLACK","type":"ROOK","calculator":{"pieceMoves":[],"captureMoves":[]},"hasMoved":false}]]}}
}
