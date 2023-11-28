package ui;

import Responses.*;
import ServerModels.GameModel;
import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class client {
    public static String authToken = null;

    public static ChessGame testGame = new Game();
    public static ServerFacade myServer = new ServerFacade();
    public static String loString = "[LOGGED_OUT] >>> ";
    public static String liString = "[LOGGED_IN] >>> ";
    public static HashMap<Integer, GameModel> g_list = new HashMap<>();
    public static void main(String[] args) throws Exception{
        boolean active = true;
        System.out.printf("Welcome, I am Chesslee. Type help to get started. \n \n[LOGGED_OUT] >>> ");
        while (active){
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var inputs = line.split(" ");
            String output ="";
            if (authToken == null){
                switch (inputs[0].toLowerCase()){
                    case "register":
                        RegisterResponse register = myServer.pli_register(inputs[1],inputs[2],inputs[3]);
                        if (register.getAuthToken()!= null){
                            authToken = register.getAuthToken();
                            output += "\u001b[39;49;1mWelcome "+register.getUsername()+"!\n\n"+ liString;
                        }
                        else{
                            output+= register.getMessage() +"\n\n"+loString;
                        }
                        break;
                    case "login":
                        LoginResponse login = myServer.pli_login(inputs[1],inputs[2]);
                        if(login.getAuthToken() != null){
                            authToken = login.getAuthToken();
                            output += "\u001b[39;49;1mWelcome "+login.getUsername()+"!\u001b[39;49;0m\n\n"+liString;
                        }
                        else{
                            output += login.getMessage()+"\n\n"+ loString;
                        }
                        break;
                    case "quit":
                        active = myServer.quit();
                        break;
                    case "help":
                        output += myServer.pli_help() + "\n" + loString;
                        break;
                    default:
                        output+="\u001b[36;49;1m Type \"help\" to see your options.\u001b[39;49;0m \n"+ loString;
                }
            }else{
                switch (inputs[0].toLowerCase()){
                    case "create":
                        StringBuilder gamename = new StringBuilder();
                        int range = inputs.length;
                        for(int i=1; i<range; i++){
                            gamename.append(inputs[i]).append(" ");
                        }
                        CreateGameResponse create = myServer.createGame(gamename.toString(),authToken);
                        if(create.getMessage() == null){
                            output += "Your new game's ID is \u001b[36;49;1m"+create.getGameID()+"\u001b[39;49;0m\n\n" + liString;
                        }else{
                            output += create.getMessage()+"\n\n"+ liString;
                        }
                        break;
                    case "list":
                        ListGamesResponse listGames = myServer.listGames(authToken);
                        if(listGames.getMessage()==null){
                            g_list.clear();
                            Collection<GameModel> list = listGames.getGames();
                            for(GameModel game: list){
                                g_list.put(game.getGameID(),game);
                                output += "ID: \u001b[36;49;1m"+game.getGameID()+"\u001b[39;49;0m Name: \u001b[36;49;1m"+game.getGameName()+"\u001b[39;49;0m WHITE: \u001b[36;49;1m"+game.getWhiteUsername()+"\u001b[39;49;0m BLACK: \u001b[36;49;1m"+game.getBlackUsername()+"\u001b[39;49;0m\n";
                            }
                            output += "\n\n" + liString;
                        }else{
                            output += listGames.getMessage()+"\n"+ liString;
                        }
                        break;
                    case "join":
                        JoinGameResponse play = myServer.joinGame(inputs[1],inputs[2],authToken);
                        if(play.getMessage() == null){
                            GameModel game = g_list.get(Integer.parseInt(inputs[1]));
                            output += "You are playing in game \u001b[36;49;1m"+inputs[1]+"\u001b[39;49;0m on team \u001b[36;49;1m"+inputs[2];
                            output += printGame(game.getGame());
                            output += "\u001b[39;49;0m\n\n" + liString;
                        }else{
                            output += play.getMessage()+"\n\n"+ liString;
                        }
//                        output += "You are playing in game \u001b[36;49;1m"+inputs[1]+"\u001b[39;49;0m on team \u001b[36;49;1m"+inputs[2];
//                        output += printGame(testGame);
//                        output += "\u001b[39;49;0m\n\n" + liString;
                        break;
                    case "observe":
                        JoinGameResponse observe = myServer.joinObserver(inputs[1],authToken);
                        if(observe.getMessage() == null){
                            GameModel game = g_list.get(Integer.parseInt(inputs[1]));
                            output += "You are watching game \u001b[36;49;1m"+inputs[1];
                            output += printGame(game.getGame());
                            output += "\u001b[39;49;0m\n\n" + liString;
                        }else{
                            output += observe.getMessage()+"\n\n"+ liString;
                        }
//                        output += "You are playing in game \u001b[36;49;1m"+inputs[1]+"\u001b[39;49;0m on team \u001b[36;49;1m"+inputs[2];
//                        output += printGame(testGame);
//                        output += "\u001b[39;49;0m\n\n" + liString;
                        break;
                    case "logout":
                        LogoutResponse logout = myServer.logout(authToken);
                        if(logout.getMessage()==null){
                            authToken = null;
                            output += "\n" + loString;
                        }else{
                            output += logout.getMessage()+"\n"+ liString;
                        }
                        break;
                    case "quit":
                        LogoutResponse quit = myServer.logout(authToken);
                        if(quit.getMessage()==null){
                            active = myServer.quit();
                        }else{
                            output += quit.getMessage()+"\n";
                        }
                        break;
                    case "help":
                        output += myServer.help()+ "\n" + liString;
                        break;
                    default:
                        output+="\u001b[36;49;1m Type \"help\" to see your options.\u001b[39;49;0m\n"+ liString;
                }
            }
            System.out.print(output);
        }
    }
    public static String printGameWhiteTop(ChessGame game){
        String out="\n\u001b[30;47;1m    h  g  f  e  d  c  b  a    \u001b[39;47;0m\n";
        ChessBoard board = game.getBoard();
        for (int i=1; i<9; i++){
            out += "\u001b[30;47;1m "+ i +" ";
            for (int j=1; j<9; j++){
                ChessPiece currentPiece = board.getPiece(new Position(j,i));
                if (i%2 + j%2 == 0 || i%2 + j%2 == 2 ){
                    if (currentPiece == null){
                        out += "\u001b[31;107;1m"+ pieceRepresentation(currentPiece);
                    }else if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out += "\u001b[31;107;1m"+ pieceRepresentation(currentPiece);
                    }else{
                        out += "\u001b[34;107;1m"+ pieceRepresentation(currentPiece);
                    }
                }else{
                    if (currentPiece == null){
                        out += "\u001b[31;40;1m"+ pieceRepresentation(currentPiece);
                    }else if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out += "\u001b[31;40;1m"+ pieceRepresentation(currentPiece);
                    }else{
                        out += "\u001b[34;40;1m"+ pieceRepresentation(currentPiece);
                    }
                }
            }
            out += "\u001b[30;47;1m "+ i +" \u001b[39;47;0m\n";
        }
        out +="\u001b[30;47;1m    h  g  f  e  d  c  b  a    \u001b[39;47;0m\n";
        return out;
    }
    public static String printGameBlackTop(ChessGame game){
        String out="\n\u001b[30;47;1m    a  b  c  d  e  f  g  h    \u001b[39;47;0m\n";
        ChessBoard board = game.getBoard();
        for (int i=8; i>0; i--){
            out += "\u001b[30;47;1m "+ i +" ";
            for (int j=8; j>0; j--){
                ChessPiece currentPiece = board.getPiece(new Position(j,i));
                if (i%2 + j%2 == 0 || i%2 + j%2 == 2 ){
                    if (currentPiece == null){
                        out += "\u001b[31;107;1m"+ pieceRepresentation(currentPiece);
                    }else if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out += "\u001b[31;107;1m"+ pieceRepresentation(currentPiece);
                    }else{
                        out += "\u001b[34;107;1m"+ pieceRepresentation(currentPiece);
                    }
                }else{
                    if (currentPiece == null){
                        out += "\u001b[31;40;1m"+ pieceRepresentation(currentPiece);
                    }else if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out += "\u001b[31;40;1m"+ pieceRepresentation(currentPiece);
                    }else{
                        out += "\u001b[34;40;1m"+ pieceRepresentation(currentPiece);
                    }
                }
            }
            out += "\u001b[30;47;1m "+ i +" \u001b[39;47;0m\n";
        }
        out +="\u001b[30;47;1m    a  b  c  d  e  f  g  h    \u001b[39;47;0m\n";
        return out;
    }
    public static String printGame(ChessGame game){
        return printGameWhiteTop(game) + printGameBlackTop(game);
    }
    public static String pieceRepresentation(ChessPiece piece){
        String pieceString="";
        if(piece == null){
            pieceString += "   ";
        }else if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            switch (piece.getPieceType()){
                case PAWN -> pieceString = WHITE_PAWN;
                case ROOK -> pieceString = WHITE_ROOK;
                case KNIGHT -> pieceString = WHITE_KNIGHT;
                case BISHOP -> pieceString = WHITE_BISHOP;
                case QUEEN -> pieceString = WHITE_QUEEN;
                case KING -> pieceString = WHITE_KING;
            }
        }else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
            switch (piece.getPieceType()){
                case PAWN -> pieceString = BLACK_PAWN;
                case ROOK -> pieceString = BLACK_ROOK;
                case KNIGHT -> pieceString = BLACK_KNIGHT;
                case BISHOP -> pieceString = BLACK_BISHOP;
                case QUEEN -> pieceString = BLACK_QUEEN;
                case KING -> pieceString = BLACK_KING;
            }
        }
        return pieceString;
    }
}
