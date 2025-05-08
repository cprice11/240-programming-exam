package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor color;
    private final ChessPiece.PieceType type;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
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
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        return switch (piece.getPieceType()) {
            case KING -> kingMoves(board, myPosition, piece);
            case QUEEN -> queenMoves(board, myPosition, piece);
            case BISHOP -> bishopMoves(board, myPosition, piece);
            case KNIGHT -> knightMoves(board, myPosition, piece);
            case ROOK -> rookMoves(board, myPosition, piece);
            case PAWN -> pawnMoves(board, myPosition, piece);
        };
    }

    /**
     * Calculates all the positions a king piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition start, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a queen piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition start, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a bishop piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition start, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a knight piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition start, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a rook piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition start, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a pawn piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition start, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can slide to in a given direction
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    private Collection<ChessMove> slidingMoves(ChessBoard board, ChessPosition start, ChessPiece piece, int rankIncrement, int fileIncrement) {
        HashSet<ChessMove> moves = new HashSet<>();
        ChessGame.TeamColor color = piece.getTeamColor();
        int startRank = start.getRank();
        int startFile = start.getFile();
        for (int i = 1; i <= ChessBoard.BOARD_SIZE; i++) {
            ChessPosition nextSpace = new ChessPosition(startRank + i * rankIncrement, startFile + i * fileIncrement);
            if (!board.isOnBoard(nextSpace)) break;
            ChessPiece nextPiece = board.getPiece(nextSpace);
            if (nextPiece != null) {
                if (nextPiece.getTeamColor() == piece.getTeamColor()) {
                    ChessMove capture = new ChessMove(start, nextSpace, null);
                    capture.setIsCapture(true);
                    moves.add(capture);
                }
                break;
            }
            ChessMove slide = new ChessMove(start, nextSpace, null);
            moves.add(slide);
        }
        return moves;
    }

    private Collection<ChessMove> hoppingMoves(ChessBoard board, ChessPosition start, ChessPiece piece, Collection<ChessPosition> hops) {
        HashSet<ChessMove> moves = new HashSet<>();
        ChessGame.TeamColor color = piece.getTeamColor();
        hops.forEach(hop -> {
            if (!board.isOnBoard(hop)) return;
            ChessPiece targetPiece = board.getPiece(hop);
            ChessMove hopMove = new ChessMove(start, hop, null);
            if (targetPiece != null) {
                if (targetPiece.getTeamColor() == color) return;
                hopMove.setIsCapture(true);
            }
            moves.add(hopMove);
        });
        return moves;
    }

    @Override
    public String toString() {
        String pieceChar = switch (type) {
            case KING -> "K";
            case QUEEN -> "Q";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case ROOK -> "R";
            case PAWN -> "P";
        };
        return color == ChessGame.TeamColor.WHITE ? pieceChar : pieceChar.toLowerCase();
    }
}
