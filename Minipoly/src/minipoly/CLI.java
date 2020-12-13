package minipoly;

import java.util.Scanner;

public class CLI {
    private static MModel model;
    
    private static void displayPlayerInfo(int i) {
        //prints out requested player info
        System.out.println("Player " + (i+1) + ":");
        System.out.println("Balance: £" + model.getPlayers()[0].getBalance());
        System.out.println("Properties Houses Hotels");
        String properties = "\n";
        if (!model.getPlayers()[i].getProperties().isEmpty()) {
            for (Tile p : model.getPlayers()[i].getProperties()) {
                properties += p.getName() + "        " + p.getNoHouses() + "     ";
                if (p.hasHotel()) {
                    properties += "1";
                } else {
                    properties += "0";
                }
            }
        }
        System.out.println(properties);
        
    }
    
    private static void newTurn() {
        //tells the player which tile they're on
        System.out.println("Player " + (model.getPlayerTurn()+1) + "'s Turn:");
        System.out.println("You are on " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
        //tells player the next tile for a sense of position
        if (model.getPlayers()[model.getPlayerTurn()].getPosition() == 39) {
            System.out.println("The next tile is GO"); //if they are on last tile of the board
        } else {
            System.out.println("The next tile is " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition() + 1].getName());
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command;
        model = new MModel();
        int winner = 0;
        boolean isWinner = false;
        //introduces game
        System.out.println("Welcome to Minipoly!");
        System.out.println("Enter 'display' at any point to see your player info");
        newTurn();
        
        while (!isWinner) { 
            
            switch(model.getGameState()) {
                case 0: // player can roll
                    System.out.println("Please enter 'roll' to roll the dice or 'cheat' to enter your own roll value:");
                    command = sc.nextLine();
                    sc.nextLine(); //clears repition due to '\n' left in command
                    if (command.equalsIgnoreCase("roll")) {
                        int i = model.roll();
                        System.out.println("You rolled " + i);
                        System.out.println("You are on " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
                        if (model.getPlayers()[model.getPlayerTurn()].getPosition() == 39) {
                            System.out.println("The next tile is GO");
                        } else {
                           System.out.println("The next tile is " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition() + 1].getName()); 
                        }
                    } else if (command.equalsIgnoreCase("cheat")) {
                        System.out.println("Please enter your roll");
                        int i = sc.nextInt();
                        model.cheatRoll(i);
                        System.out.println("You rolled " + i);
                        System.out.println("You are on " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
                        if (model.getPlayers()[model.getPlayerTurn()].getPosition() == 39) {
                            System.out.println("The next tile is GO");
                        } else {
                           System.out.println("The next tile is " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition() + 1].getName()); 
                        }
                        
                    } else if (command.equalsIgnoreCase("display")) { //can displayer their player info at any point
                        displayPlayerInfo(model.getPlayerTurn());
                    } else {
                       System.out.println("The command you entered was invalid, please try again!"); 
                    }
                    break;
                case 1: // can purchase houses
                    System.out.println("You can purchase houses, enter 'house' to do so or 'end' to end your turn");
                    command = sc.nextLine();
                    sc.nextLine(); //clears repition due to '\n' left in command
                    if (command.equalsIgnoreCase("house")) {
                        model.buyHouse();
                        System.out.println("You purchased a house on " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
                        System.out.println("You now have " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getNoHouses() + " house(s) on " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
                    } else if (command.equalsIgnoreCase("end")) {
                        System.out.println("Player " + (model.getPlayerTurn() + 1) + " has ended their turn");
                        model.endTurn();
                        newTurn();
                    } else if (command.equalsIgnoreCase("display")) {
                        displayPlayerInfo(model.getPlayerTurn());
                    } else {
                       System.out.println("The command you entered was invalid, please try again!"); 
                    }
                    break;
                case 2: //can purchase hotels
                    System.out.println("You can purchase hotels, enter 'hotel' to do so or 'end' to end your turn");
                    command = sc.nextLine();
                    sc.nextLine(); //clears repition due to '\n' left in command
                    if (command.equalsIgnoreCase("hotel")) {
                        model.buyHotel();
                        System.out.println("Congrats, you've purchased a hotel on " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
                    } else if (command.equalsIgnoreCase("end")) {
                        System.out.println("Player " + (model.getPlayerTurn() + 1) + " has ended their turn");
                        model.endTurn();
                        newTurn();
                    } else if (command.equalsIgnoreCase("display")) {
                        displayPlayerInfo(model.getPlayerTurn());
                    } else {
                       System.out.println("The command you entered was invalid, please try again!"); 
                    }
                    break;
                case 3: // pays rent
                    System.out.println("OH NO! Your opponent owns this property! Pay £" + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getRent());
                    model.payRent();
                    break;
                case 4: // can purchase property
                    System.out.println("You can purchase this property, enter 'purchase' to do so or 'end' to end your turn");
                    command = sc.nextLine();
                    sc.nextLine(); //clears repition due to '\n' left in command
                    if (command.equalsIgnoreCase("purchase")) {
                        model.purchase();
                        System.out.println("You have purchased " + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName());
                    } else if (command.equalsIgnoreCase("end")) {
                        System.out.println("Player " + (model.getPlayerTurn() + 1) + " has ended their turn");
                        model.endTurn();
                        newTurn();
                    } else if (command.equalsIgnoreCase("display")) {
                        displayPlayerInfo(model.getPlayerTurn());
                    } else {
                       System.out.println("The command you entered was invalid, please try again!"); 
                    }
                    break;
                case 5: //end turn
                    System.out.println("You have no moves, please enter 'end' to end your turn");
                    command = sc.nextLine();
                    sc.nextLine(); //clears repition due to '\n' left in command
                    if (command.equalsIgnoreCase("end")) {
                        model.endTurn();
                        newTurn();
                    } else if (command.equalsIgnoreCase("display")) {
                        displayPlayerInfo(model.getPlayerTurn());
                    } else {
                       System.out.println("The command you entered was invalid, please try again!"); 
                    }
                    break;
                case 6: // there is a winner, breaks while loop
                    winner = model.getWinner();
                    isWinner = true;
                    break;
                            
            }
        }
        
        System.out.println("The winner is player " + winner + "!");
        System.out.println("Congratulations!");
    }
}
