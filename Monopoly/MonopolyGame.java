
/**
 * Write a description of class MonopolyGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MonopolyGame implements Game
{
    public MonopolyGame()
    {
        Space[] board = boardCreation();
        
        //how many players?
        //initializing players
        //starting game
    }
    
    public static void trade()
    {
        
    }
    
    public static void turn (int numOfPlayers)
    {
        //loop for turn
    }

    public static int roll()
    {
        //using rand, possibly two rands to generate similar # to real game
        return 0; 
    }

    public static void land (int space)
    {

        //when player lands: do you want to buy property?, do you have to pay someone?       
    }

    public static Space[] boardCreation()
    {
        Space[] board = new Space[40];
        //creating the board, properies, etc.
        board[0]= new OtherSpace("Start", 0, -200);
        board[1]= new Property("Mediterranean Avenue", 1, 60, 2, 30, 'b');
        board[2]= new OtherSpace("Community Chest", 1, 0);//(name, pick up community chest card, give 0 $$)  
        board[3]= new Property("Baltic Avenue", 3, 60, 4, 30, 'b');
        board[4]= new OtherSpace("Tax", 0, 200); //(name, pick up no card, give 200)
        board[5]= new Property("Reading Railroad", 4, 200, 25, 100, 's');
        board[6]= new Property("Oriental Avenue", 6, 100, 6, 50, 'l');
        board[7]= new OtherSpace("Chance", 2, 0);//(name, pick up chance card, give 0 $$)
        board[8]= new Property("Vermont Avenue", 8, 100, 6, 50, 'l');
        board[9]= new Property("Connecticut Avenue", 9, 120, 8, 60, 'l');
        
        board[10]= new OtherSpace("Jail", 0, 0);
        board[11]= new Property("St. Charles Place", 11, 140, 10, 70, 'p');
        board[12]= new Property("Electric Company", 12, 150,  9999, 75, 'u');
        board[13]= new Property("States Avenue", 13, 140, 10, 70, 'p');
        board[14]= new Property("Virginia Avenue", 14, 160, 12, 80, 'p');
        board[15]= new Property("Pennsylvania Railroad", 15, 200, 25, 100, 's');
        board[16]= new Property("St. James Place", 16, 180, 14, 90, 'o');
        board[17]= new OtherSpace("Community Chest", 1, 0);
        board[18]= new Property("Tenessee Avenue", 18, 180, 14, 90, 'o');
        board[19]= new Property("New York Avenue", 19, 200, 16, 100, 'o');
        
        board[20]= new OtherSpace("Free Parking", 0, 0);
        board[21]= new Property("Kentucky Avenue", 21, 220, 18, 110, 'r');
        board[22]= new OtherSpace("Chance", 2, 0);
        board[23]= new Property("Indiana Avenue", 23, 220, 18, 110, 'r');
        board[24]= new Property("Illinois Avenue", 24, 240, 18, 120, 'r');
        board[25]= new Property("B. & O. Railroad", 25, 200, 25, 100, 's');
        board[26]= new Property("Atlantic Avenue", 26, 260, 22, 130, 'y');
        board[27]= new Property("Ventnor", 27, 260, 22, 130, 'y');
        board[28]= new Property("Water Works", 28, 150, 999999999, 75, 'u');
        board[29]= new Property("Marvin Gardens", 29, 280, 24, 140, 'y');
        
        board[30]= new OtherSpace("Go To Jail", 0, 0);
        board[31]= new Property("Pacific Avenue", 31, 300, 26, 150, 'g');
        board[32]= new Property("North Carolina Avenue", 32, 300, 26, 150, 'g');
        board[33]= new OtherSpace("Community Chest", 1, 0);
        board[34]= new Property("Pennsylvania Avenue", 34, 320, 28, 160, 'g');
        board[35]= new Property("Short Line", 35, 200, 25, 100, 's');
        board[36]= new OtherSpace("Chance", 2, 0);
        board[37]= new Property("Park Place", 37, 350, 35, 175, 'd');
        board[38]= new OtherSpace("Tax", 0, 200);
        board[39]= new Property("Boardwlak", 39, 400, 50, 200, 'd');
        return board;
    }    

    public static void print (Space[] board)
    {
        
    }
}