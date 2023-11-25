package ui;

import Responses.*;
import ServerModels.GameModel;
import chess.Game;

import java.util.Collection;
import java.util.Scanner;

public class client {
    public static String authToken = null;
    public static ServerFacade myServer = new ServerFacade();
    public static String loString = "[LOGGED_OUT] >>> ";
    public static String liString = "[LOGGED_IN] >>> ";
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
                            output += "\u001b[39;49;1mWelcome "+login.getUsername()+"!\n\n"+liString;
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
                        int range = inputs.length-1;
                        for(int i=1; i<range; i++){
                            gamename.append(inputs[i]).append(" ");
                        }
                        CreateGameResponse create = myServer.createGame(gamename.toString(),authToken);
                        if(create.getMessage() == null){
                            output += "Your new game's ID is "+create.getGameID()+"\n\n" + liString;
                        }else{
                            output += create.getMessage()+"\n\n"+ liString;
                        }
                        break;
                    case "list":
                        ListGamesResponse listGames = myServer.listGames(authToken);
                        if(listGames.getMessage()==null){
                            Collection<GameModel> list = listGames.getGames();
                            for(GameModel game: list){
                                output += "ID: \u001b[36;49;1m"+game.getGameID()+"\u001b[39;49;0m Name: \u001b[36;49;1m"+game.getGameName()+"\u001b[39;49;0m\n";
                            }
                            output += "\n\n" + liString;
                        }else{
                            output += listGames.getMessage()+"\n"+ liString;
                        }
                        break;
                    case "join":
                        output += myServer.joinGame(inputs[1],inputs[2],authToken) + "\n" + liString;
                        break;
                    case "observe":
                        output += myServer.joinObserver(inputs[1],authToken) + "\n" + liString;
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
}
