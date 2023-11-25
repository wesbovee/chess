package ui;

import Requests.*;
import Responses.*;
import chess.ChessGame;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

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

        try(OutputStream requestBody = connection.getOutputStream();) {
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
        connection.setDoOutput(true);

        connection.addRequestProperty("Authorization", token);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            ListGamesRequest client_request = new ListGamesRequest();
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            ListGamesResponse responseBody;

            try(InputStream resBody = connection.getInputStream()){
                InputStreamReader inputStreamReader = new InputStreamReader(resBody);
                responseBody = new Gson().fromJson(inputStreamReader, ListGamesResponse.class);
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
    public String joinGame(String ID,String teamColor, String token) throws Exception{
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        ChessGame.TeamColor tc = null;

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
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");
            //InputStream responseBody = connection.getInputStream();
            return "success";

            // Read response body from InputStream ...
        }
        else {
            // SERVER RETURNED AN HTTP ERROR

            //InputStream responseBody = connection.getErrorStream();
            //String error_message = responseBody.
            // Read and process error response body from InputStream ...
            return "error";
        }
    }
    public String joinObserver(String ID, String token) throws Exception{
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            ListGamesRequest client_request = new ListGamesRequest();
            requestBody.write(new Gson().toJson(client_request).getBytes());
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");
            //InputStream responseBody = connection.getInputStream();
            return "success";

            // Read response body from InputStream ...
        }
        else {
            // SERVER RETURNED AN HTTP ERROR

            //InputStream responseBody = connection.getErrorStream();
            //String error_message = responseBody.
            // Read and process error response body from InputStream ...
            return "error";
        }
    }
}
