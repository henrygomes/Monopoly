
/**
 * Write a description of class MonopolyGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
public class MonopolyGame implements Game
{
    public static boolean gameEnd = false;
    private static int currentPlayerInt = 0;//Early versions used this as an indication of whos turn it was until I figured out you could pass Objects as variables and it would change the variable
    private static Player currentPlayer;
    public static Player[] players = new Player[0];//declared as 0 just for safety
    public static Space[] board = boardCreation();//Inializing the board
    public MonopolyGame()
    {
        while (true)//why would anyone what to ever stop playing our wonderful game?
        {
            Scanner scan = new Scanner(System.in);//initializing input scanner, hopefully will be using a different method in the GUI
            System.out.println ("num of players: ");//the System.out.println (); and the input to be moved to the GUI
            int playerNum = scan.nextInt();//moved to GUI
            scan.nextLine();//Moved to GUI
            players = new Player[playerNum];//actually created player list
            for (int i = 0; i< playerNum; i++)
            {
                System.out.println ("name: ");
                String name = scan.nextLine();//input to be moved to GUI
                players[i] = new Player (name, i);//(name, money)
            }
            Player winner = getWinner();
            while(gameEnd == false)
            {
                displayInfo(players[currentPlayerInt], board);
                if (players[currentPlayerInt].getHasLost())//game can still run if 1 or more people have lost
                {
                    currentPlayer = increasePlayer (currentPlayer, playerNum);
                }
                else if (turn(players[currentPlayerInt]))
                {
                    currentPlayer = increasePlayer (currentPlayer, playerNum);
                }

                if (winner !=null)
                    gameEnd = true;
            }   
            System.out.println (winner.getName() + "Won the Game!!");
        }
    }

    public static boolean turn (Player player)
    {
        boolean doubles= false;
        int roll1 = roll();
        int roll2 = roll();
        if (roll1 == roll2)
        {
            doubles = true;
            player.newDoubles(player.getDoubles() + 1);
        }
        else 
        {
            doubles = false;
            player.newDoubles (0);
        }
        if (player.getDoubles () >=3)
        {
            //send to jail
            player.newDoubles (0);
            return true;
        }
        int moves = roll1+roll2;
        System.out.println ("Player rolled a " + roll1 + " and a " + roll2 + " (Moved " + moves + ")");
        int playerLoc = player.move(moves);
        System.out.println (player.getName() + " landed on: " + board[playerLoc].getName());
        land (playerLoc, player, -1);
        /*
        if (board[playerLoc] instanceof Property)
        {
            if (((Property)board[playerLoc]).getPlayer()!=null && ((Property)board[playerLoc]).getColour() != 'u')
            {
                pay(player, ((Property)board[playerLoc]).getPlayer(), (((Property)board[playerLoc]).getRent()) * -1);

            }
            else if (((Property)board[playerLoc]).getPlayer()!=null && ((Property)board[playerLoc]).getColour() == 'u')
            {
                pay(player, ((Property)board[playerLoc]).getPlayer(), ((Property)board[playerLoc]).getRent(moves));
            }
            else
            {
                buyProperty(player, ((Property)board[playerLoc]));
            }
        }
        else
        {
            if (Math.abs(((OtherSpace)board[playerLoc]).getTax()) > 0)
            {
                pay(player, ((OtherSpace)board[playerLoc]).getTax());
            }
            else if (((OtherSpace)board[playerLoc]).getCardValue()>0)
            {
                //give card
            }
        }
        */
        //do you want to add houses, mortgage, un-mortgage, trading anything, bankrupcy. 
        //ask for houses, mortgage property. trades
        if (hasPlayerLost(player))
            player.hasLost();
        if (doubles && !turn (player))
            return false;
        return true;
    }
    public static void land (int playerLoc, Player player, int utilityMultiplier)//utilityMultiplier is for the multiplier for utilities
    {
        if (utilityMultiplier <0)
            utilityMultiplier = 4;
        if (board[playerLoc] instanceof Property)
        {
            if (((Property)board[playerLoc]).getPlayer()!=null && ((Property)board[playerLoc]).getColour() != 'u')
            {
                pay(player, ((Property)board[playerLoc]).getPlayer(), (((Property)board[playerLoc]).getRent()) * -1);

            }
            else if (((Property)board[playerLoc]).getPlayer()!=null && ((Property)board[playerLoc]).getColour() == 'u')
            {
                pay(player, ((Property)board[playerLoc]).getPlayer(), ((Property)board[playerLoc]).getRent(roll()*utilityMultiplier));
            }
            else
            {
                buyProperty(player, ((Property)board[playerLoc]));
            }
        }
        else
        {
            if (Math.abs(((OtherSpace)board[playerLoc]).getTax()) > 0)
            {
                pay(player, ((OtherSpace)board[playerLoc]).getTax());
            }
            else if (((OtherSpace)board[playerLoc]).getCardValue()>0)
            {
                //give card
            }
        }
    }
    public static boolean hasPlayerLost (Player player)
    {
        if (player.getMoney() <=0 && player.getProperties().isEmpty())
            return true;
        else
            return false;
    }

    public static Player increasePlayer (Player currentPlayer, int numOfPlayers)
    {
        int currentPlayerNum = currentPlayer.getPlayerNum();
        currentPlayerNum = (currentPlayerNum + 1) % numOfPlayers;
        currentPlayer = players[currentPlayerNum];

        return currentPlayer;
    }

    public static void displayInfo(Player player, Space[] board)
    {
        System.out.println ("Player: " + player.getName());
        System.out.println ("Money: $" + player.getMoney());
        System.out.println ("On space: " + board[player.getLocation()].getName ());
    }
    
    public static void buyProperty (Player player, Property property)
    {
        System.out.print ("do you want to buy: " + property.getName() + "? (yes, or no)");
        Scanner scan = new Scanner(System.in);
        String yesOrNo = scan.next();
        if (yesOrNo.equals ("yes") && pay (player, property.getPrice()))
        {
            property.newOwner(player);
            player.addNewProperty (property);
        }
        else
        {
            System.out.println ("Did not buy " + property.getName());
        }
    }

    public static int roll()
    {
        //using rand, possibly two rands to generate similar # to real game\
        Random rand = new Random();
        return(rand.nextInt(6)+1);
    }

    public static boolean pay (Player fromPlayer, double amount)
    {
        if (fromPlayer.transaction(amount))
        {
            return true;
        }
        amount -= fromPlayer.getMoney();
        pay (fromPlayer, fromPlayer.getMoney());
        if (bankrupcy (fromPlayer, amount))
        {
            pay (fromPlayer, amount);
            return true;
        }
        return false;
    }

    public static boolean pay (Player fromPlayer, Player toPlayer, double amount)
    {
        if (fromPlayer.transaction(amount)) 
        {
            toPlayer.transaction(amount);
            return true;
        }   
        amount -= fromPlayer.getMoney();
        pay (fromPlayer, toPlayer, fromPlayer.getMoney());
        if (bankrupcy(fromPlayer, amount))
        {
            pay(fromPlayer, toPlayer, amount);
            return true;
        }
        return false;
    }
    
    public static void mortgageProperty(Player currentPlayer, Property property)
    {
       currentPlayer.addMoney(property.getMortgage());
       property.isMortgaged();
    }
    
    public static void unMortgageProperty(Player currentPlayer, Property property)
    {
        pay(currentPlayer, property.getMortgage());
        property.unMortgage();
    }
    
    /**
     * This method creates the option for players to trade properties and money between eachother. 
     */
    public static void trade(Player fromPlayer, Player toPlayer, ArrayList<Property> fromPlayerProperties, ArrayList<Property> toPlayerProperties)
    {
        for(int i = 0; i < fromPlayerProperties.size(); i++)
        {
            fromPlayerProperties.get(i).newOwner(toPlayer);
            toPlayerProperties.add(fromPlayerProperties.get(i));
            fromPlayerProperties.remove(i);
        }
        for(int i = 0; i < toPlayerProperties.size(); i++)
        {
            toPlayerProperties.get(i).newOwner(toPlayer);
            fromPlayerProperties.add(toPlayerProperties.get(i));
            toPlayerProperties.remove(i);
        }
    }

    public static boolean bankrupcy (Player player, double amount)//returns true if debt is paid back, false if it is not
    {
        double valueOfPlayer = player.getValueOfPlayer();
        if (valueOfPlayer >= amount)
        {
            return true;
            //if you sell some things you can pay the debt
        }
        else
            return false;
        //amount is what player owes
        //asks if player wants to sell stuff

    }

    public static Player getWinner()//checks if each player has 
    {
        //work in progress]
        int numOfPlayersLost = 0;
        Player winner = null;
        for (int i = 0; i < players.length; i++)
        {   
            if (players[i].getHasLost())
                numOfPlayersLost++;
            else 
                winner = players[i]; 
        }
        if (numOfPlayersLost+1 >= players.length)
        {
            return winner;
        }
        return null;
    }

    public static Space[] boardCreation()
    {
        //creating the board, properies, etc.
        try {
            File file = new File ("BoardConfig.txt");
            Scanner scanFile = new Scanner (file);
            String firstLine = scanFile.nextLine();
            Scanner scan = new Scanner (firstLine);
            int numOfSpaces = scan.nextInt();
            Space[] board = new Space[numOfSpaces];
            for (int i = 0; i <numOfSpaces; i ++)
            {
                String type = scanFile.next();
                if (type.equals ("Property"))
                {
                    String name = scanFile.nextLine();
                    int position = scanFile.nextInt();
                    double cost = scanFile.nextInt();
                    double rent = scanFile.nextInt();

                    String colorString = scanFile.next();
                    char color = colorString.charAt (0);
                    board[i] = new Property(name, position, cost, rent, color);
                }
                else if (type.equals("OtherSpace"))
                {
                    String name = scanFile.nextLine();
                    int location = scanFile.nextInt();
                    int typeOfCard = scanFile.nextInt();
                    double tax = scanFile.nextDouble();

                    board[i] = new OtherSpace(name, typeOfCard, tax, location);
                }
            }
            /*
            board[0]= new OtherSpace("Start", 0, -200);
            board[1]= new Property("Mediterranean Avenue", 1, 60, 2, 'b');
            board[2]= new OtherSpace("Community Chest", 1, 0);//(name, pick up community chest card, give 0 $$)  
            board[3]= new Property("Baltic Avenue", 3, 60, 4, 'b');
            board[4]= new OtherSpace("Tax", 0, 200); //(name, pick up no card, give 200)
            board[5]= new Property("Reading Railroad", 4, 200, 25, 's');
            board[6]= new Property("Oriental Avenue", 6, 100, 6, 'l');
            board[7]= new OtherSpace("Chance", 2, 0);//(name, pick up chance card, give 0 $$)
            board[8]= new Property("Vermont Avenue", 8, 100, 6, 'l');
            board[9]= new Property("Connecticut Avenue", 9, 120, 8, 'l');

            board[10]= new OtherSpace("Jail", 0, 0);
            board[11]= new Property("St. Charles Place", 11, 140, 10, 'p');
            board[12]= new Property("Electric Company", 12, 150,  9999, 'u');
            board[13]= new Property("States Avenue", 13, 140, 10, 'p');
            board[14]= new Property("Virginia Avenue", 14, 160, 12,  'p');
            board[15]= new Property("Pennsylvania Railroad", 15, 200, 25, 's');
            board[16]= new Property("St. James Place", 16, 180, 14, 'o');
            board[17]= new OtherSpace("Community Chest", 1, 0);
            board[18]= new Property("Tenessee Avenue", 18, 180, 14, 'o');
            board[19]= new Property("New York Avenue", 19, 200, 16, 'o');

            board[20]= new OtherSpace("Free Parking", 0, 0);
            board[21]= new Property("Kentucky Avenue", 21, 220, 18, 'r');
            board[22]= new OtherSpace("Chance", 2, 0);
            board[23]= new Property("Indiana Avenue", 23, 220, 18, 'r');
            board[24]= new Property("Illinois Avenue", 24, 240, 18,  'r');
            board[25]= new Property("B. & O. Railroad", 25, 200, 25,  's');
            board[26]= new Property("Atlantic Avenue", 26, 260, 22, 'y');
            board[27]= new Property("Ventnor", 27, 260, 22, 'y');
            board[28]= new Property("Water Works", 28, 150, 999999999, 'u');
            board[29]= new Property("Marvin Gardens", 29, 280, 24, 'y');

            board[30]= new OtherSpace("Go To Jail", 0, 0);
            board[31]= new Property("Pacific Avenue", 31, 300, 26, 'g');
            board[32]= new Property("North Carolina Avenue", 32, 300, 26, 'g');
            board[33]= new OtherSpace("Community Chest", 1, 0);
            board[34]= new Property("Pennsylvania Avenue", 34, 320, 28, 'g');
            board[35]= new Property("Short Line", 35, 200, 25, 's');
            board[36]= new OtherSpace("Chance", 2, 0);
            board[37]= new Property("Park Place", 37, 350, 35, 'd');
            board[38]= new OtherSpace("Tax", 0, 200);
            board[39]= new Property("Boardwalk", 39, 400, 50, 'd');
             */
            return board;
        }
        catch(IOException e)
        {
            System.err.println ("ERROR: " + e);
        }
        return null;
    }    
}
