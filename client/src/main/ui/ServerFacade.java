package ui;

import Requests.*;
import Responses.*;
import chess.*;
import com.google.gson.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;

public class ServerFacade {

//prelogin -------------------------------------------------------------------------
    public String pli_help(){
        return "\u001b[36;49;1m   register <USERNAME> <PASSWORD> <EMAIL>" +"\u001b[39;49;0m - to create an account\n" +
            "\u001b[36;49;1m   login <USERNAME> <PASSWORD>"+"\u001b[39;49;0m - to play chess\n" +
            "\u001b[36;49;1m   quit" + "\u001b[39;49;0m - playing chess\n" +
            "\u001b[36;49;1m   help" + "\u001b[39;49;0m - with possible commands\n";}
    public Boolean quit(){return false;}
    public LoginResponse pli_login(String username, String password) throws Exception {
        URI uri = new URI("http://localhost:8080/session");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            LoginRequest client_request = new LoginRequest();
            client_request.setUsername(username);
            client_request.setPassword(password);
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            LoginResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, LoginResponse.class);
            }
            return responseBody;
        }
        else {
            LoginResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, LoginResponse.class);
            }
            return errorMessage;
        }
    }
    public RegisterResponse pli_register(String username, String password, String email) throws Exception {
        URI uri = new URI("http://localhost:8080/user");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            RegisterRequest client_request = new RegisterRequest(username,password,email);
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            RegisterResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, RegisterResponse.class);
            }
            return responseBody;
        }
        else{
            RegisterResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, RegisterResponse.class);
            }
            return errorMessage;
        }
    }
//Postlogin--------------------------------------------------------------------------
    public String help(){
        return "\u001b[36;49;1m   create <NAME>" +"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   list"+"\u001b[39;49;0m - games\n" +
                "\u001b[36;49;1m   join <ID> [WHITE|BLACK|<empty>]"+"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   observe <ID>"+"\u001b[39;49;0m - a game\n" +
                "\u001b[36;49;1m   logout"+"\u001b[39;49;0m - when you are done\n" +
                "\u001b[36;49;1m   quit" + "\u001b[39;49;0m - playing chess\n" +
                "\u001b[36;49;1m   help" + "\u001b[39;49;0m - with possible commands\n";
    }
    public LogoutResponse logout(String token) throws Exception{
        URI uri = new URI("http://localhost:8080/session");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        //Set HTTP request headers, if necessary
        connection.addRequestProperty("Authorization", token);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            LogoutRequest client_request = new LogoutRequest();
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            LogoutResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, LogoutResponse.class);
            }
            return responseBody;
        }
        else {
            LogoutResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, LogoutResponse.class);
            }
            return errorMessage;
        }
    }
    public CreateGameResponse createGame(String gameName,String token) throws Exception{
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.addRequestProperty("Authorization", token);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream()) {
            CreateGameRequest client_request = new CreateGameRequest();
            client_request.setGameName(gameName);
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            CreateGameResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, CreateGameResponse.class);
            }
            return responseBody;
        }
        else {
            CreateGameResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, CreateGameResponse.class);
            }
            return errorMessage;
        }
    }
    public ListGamesResponse listGames(String token) throws Exception{
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        connection.addRequestProperty("Authorization", token);

        connection.connect();


        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            ListGamesResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = deserialize().fromJson(inputStreamReader, ListGamesResponse.class);
            }
            return responseBody;
        }
        else {
            ListGamesResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, ListGamesResponse.class);
            }
            return errorMessage;
        }
    }
    public JoinGameResponse joinGame(String ID,String teamColor, String token) throws Exception{
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        ChessGame.TeamColor tc = null;

        connection.addRequestProperty("Authorization", token);

        connection.connect();

        switch (teamColor) {
            case "BLACK" -> tc = ChessGame.TeamColor.BLACK;
            case "WHITE" -> tc = ChessGame.TeamColor.WHITE;
        }

        try(OutputStream requestBody = connection.getOutputStream();) {
            JoinGameRequest client_request = new JoinGameRequest();
            client_request.setGameID(Integer.parseInt(ID));
            client_request.setPlayerColor(tc);
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            JoinGameResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, JoinGameResponse.class);
            }
            return responseBody;
        }
        else {
            JoinGameResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, JoinGameResponse.class);
            }
            return errorMessage;
        }
    }
    public JoinGameResponse joinObserver(String ID, String token) throws Exception{
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        connection.addRequestProperty("Authorization", token);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            JoinGameRequest client_request = new JoinGameRequest();
            client_request.setGameID(Integer.parseInt(ID));
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            JoinGameResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, JoinGameResponse.class);
            }
            return responseBody;
        }
        else {
            JoinGameResponse errorMessage;

            try(InputStream resBody = connection.getErrorStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                errorMessage = new Gson().fromJson(inputStreamReader, JoinGameResponse.class);
            }
            return errorMessage;
        }
    }
    public Gson deserialize (){
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessGame.class, new GameAdapter())
                .registerTypeAdapter(ChessBoard.class,new BoardAdapter())
                .registerTypeAdapter(ChessPiece.class,new PieceAdapter());
        return builder.create();
    }

    class GameAdapter implements JsonDeserializer<ChessGame> {
        public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Game.class);
        }
    }
    class BoardAdapter implements JsonDeserializer<ChessBoard> {
        public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Board.class);
        }
    }
    class PieceAdapter implements JsonDeserializer<ChessPiece> {
        public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            ChessPiece piece = null;
            String pieceType = el.getAsJsonObject().get("type").getAsString();
            switch (pieceType) {
                case "KING" -> piece = new Gson().fromJson(el, King.class);
                case "QUEEN" -> piece = new Gson().fromJson(el, Queen.class);
                case "BISHOP" -> piece = new Gson().fromJson(el, Bishop.class);
                case "KNIGHT" -> piece = new Gson().fromJson(el, Knight.class);
                case "ROOK" -> piece = new Gson().fromJson(el, Rook.class);
                case "PAWN" -> piece = new Gson().fromJson(el, Pawn.class);
            }
            return piece;
        }
    }
}
