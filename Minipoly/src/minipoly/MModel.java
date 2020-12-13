package minipoly;

import java.util.Observable;

public class MModel extends Observable {
    private final Player[] players = new Player[2];
    private static final Tile[] board = new Tile[40];
    private int dice1;
    private int dice2;
    private int playerTurn = 0; 
    private int gameState;
    private int opponent = 1;
    private int winner;
    
    
    public MModel() {
        //creates players and adds them to players array
        players[0] = new Player(1);
        players[1] = new Player(2);
        
        gameState = 0; // starting game state, only roll and cheat available 
        
        winner = 0;
        
        //Creating all property tiles for the board and putting them in their appropriate sets
        Tile a1 = new Tile("A1", 50, 2);
        Tile a2 = new Tile("A2", 50, 2);
        Tile a3 = new Tile("A3", 70, 2);
        a1.setSets(a2, a3);
        Tile b1 = new Tile("B1", 100, 2);
        Tile b2 = new Tile("B2", 100, 2);
        Tile b3 = new Tile("B3", 120, 2);
        b1.setSets(b2, b3);
        Tile c1 = new Tile("C1", 150, 2);
        Tile c2 = new Tile("C2", 150, 2);
        Tile c3 = new Tile("C3", 170, 2);
        c1.setSets(c2, c3);
        Tile d1 = new Tile("D1", 200, 2);
        Tile d2 = new Tile("D2", 200, 2);
        Tile d3 = new Tile("D3", 220, 2);
        d1.setSets(d2, d3);
        Tile e1 = new Tile("E1", 250, 2);
        Tile e2 = new Tile("E2", 250, 2);
        Tile e3 = new Tile("E3", 270, 2);
        e1.setSets(e2, e3);
        Tile f1 = new Tile("F1", 300, 2);
        Tile f2 = new Tile("F2", 300, 2);
        Tile f3 = new Tile("F3", 320, 2);
        f1.setSets(f2, f3);
        Tile g1 = new Tile("G1", 350, 2);
        Tile g2 = new Tile("G2", 350, 2);
        Tile g3 = new Tile("G3", 370, 2);
        g1.setSets(g2, g3);
        Tile h1 = new Tile("H1", 400, 2);
        Tile h2 = new Tile("H2", 400, 2);
        Tile h3 = new Tile("H3", 420, 2);
        h1.setSets(h2, h3);
        
        //adding all tiles to the board, setting owner to 3 will keep it out of situations where it needs to act like property
        
        board[0] = new Tile("GO"); // adds/creates GO tile
        board[1] = a1;
        board[2] = new Tile(); // adds/creates blank tile
        board[3] = a2;
        board[4] = a3;
        board[5] = new Tile();
        board[6] = b1;
        board[7] = new Tile();
        board[8] = b2;
        board[9] = b3;
        board[10] = new Tile();
        board[11] = c1;
        board[12] = new Tile();
        board[13] = c2;
        board[14] = c3;
        board[15] = new Tile();
        board[16] = d1;
        board[17] = new Tile();
        board[18] = d2;
        board[19] = d3;
        board[20] = new Tile(); 
        board[21] = e1;
        board[22] = new Tile();
        board[23] = e2;
        board[24] = e3;
        board[25] = new Tile();
        board[26] = f1;
        board[27] = new Tile();
        board[28] = f2;
        board[29] = f3;
        board[30] = new Tile();
        board[31] = g1;
        board[32] = new Tile();
        board[33] = g2;
        board[34] = g3;
        board[35] = new Tile();
        board[36] = h1;
        board[37] = new Tile();
        board[38] = h2;
        board[39] = h3;
        
        setChanged();
        notifyObservers();
        
        assert gameState >= 0;
        assert gameState <= 6;
        assert playerTurn == 0 || playerTurn == 1;
        assert opponent == 0 || opponent ==1;
        assert opponent != playerTurn;
        
    } 
    
    public boolean invariant() {
        return gameState >= 0 && gameState <= 6 && (playerTurn == 0 || playerTurn == 1) && (opponent == 0 || opponent == 1) && opponent != playerTurn;
    }
    
    /**@pre gameState == 0
     *      players[playerTurn].getPosition() >= 0 && players[playerTurn].getPosition() <= 39
     * @post gameState != 0 && gameState != 6
     *       sum >= 2 && sum <= 12 
     *       players[playerTurn].getPosition() >= 0 && players[playerTurn].getPosition() <= 39
     *       players[playerTurn].getPosition() == (prePos + sum) % 40
     */ 
    public int roll() {
        assert gameState == 0: "You should not be able to roll at this Game State";
        assert players[playerTurn].getPosition() >=0 && players[playerTurn].getPosition() <= 39: "Player must be at valid position";
        assert invariant(): "Invariant must be maintained";
        int prePos = players[playerTurn].getPosition();
        
        dice1 = (int) (Math.random() * 6 + 1); // Rolls two 2d6 and adds their value
        dice2 = (int) (Math.random() * 6 + 1);
        int sum = dice1 + dice2;
        if (players[playerTurn].getPosition() + sum >= 40) { // checks if player has finished a loop of the board then changes players current position
            players[playerTurn].setPosition((players[playerTurn].getPosition() + sum)% 40);
        } else {
            players[playerTurn].setPosition(players[playerTurn].getPosition()+sum);
        }
        if(players[playerTurn].getPosition() == 20) { // if player has landed on the square opposite GO it gets teleported to GO or Board[0]
            players[playerTurn].setPosition(0);
        }
        
        setGameState();
        setChanged();
        notifyObservers();
        
        assert gameState != 0 && gameState != 6: "Game has entered incorrect game state";
        assert sum >= 2 && sum <=12: "Dice didn't roll real value";
        assert players[playerTurn].getPosition() == (prePos + sum) % 40;
        assert players[playerTurn].getPosition() >=0 && players[playerTurn].getPosition() <= 39: "Player must be at valid position";
        assert invariant(): "Invariant must be maintained";
        
        return sum;
    }
    
    /**@pre i >= 0
     *      gameState == 0
     *      players[playerTurn].getPosition() >= 0 && players[playerTurn].getPosition() <= 39
     * @post gameState != 0 && gameState != 6 
     *       players[playerTurn].getPosition() >= 0 && players[playerTurn].getPosition() <= 39
     *       players[playerTurn].getPosition() == (prePos + i) % 40
     */
    public void cheatRoll(int i) {
        assert i >= 0: "Cheat roll value must be greater than 0";
        assert gameState == 0: "You should not be able to roll at this Game State";
        assert players[playerTurn].getPosition() >=0 && players[playerTurn].getPosition() <= 39: "Player must be at valid position";
        assert invariant(): "Invariant must be maintained";
        int prePos = players[playerTurn].getPosition();
        
        if (players[playerTurn].getPosition() + i >= 40) { // checks if player has finished a loop of the board then changes players current position
            players[playerTurn].setPosition((players[playerTurn].getPosition() + i)% 40);
        } else {
            players[playerTurn].setPosition(players[playerTurn].getPosition()+ i);
        }
        if(players[playerTurn].getPosition() == 20) { // if player has landed on the square opposite GO it gets teleported to GO or Board[0]
            players[playerTurn].setPosition(0);
        }
        
        setGameState();
        setChanged();
        notifyObservers();
        
        assert gameState != 0 && gameState != 6: "Game has entered incorrect game state";
        assert players[playerTurn].getPosition() >=0 && players[playerTurn].getPosition() <= 39: "Player must be at valid position";
        assert players[playerTurn].getPosition() == (prePos + i) % 40;
        assert invariant(): "Invariant must be maintained";
    }
    
    /**@pre players[playerTurn].getBalance() > board[players[playerTurn].getPosition()].getCost()
     *      board[players[playerTurn].getPosition()].getOwner() == 0
     *      gameState == 4
     * @post preBalance == players[playerTurn].getBalance() + board[players[playerTurn].getPosition().getCost()
     *       players[playerTurn].getProperties().contains(board[players[playerTurn].getPosition()])
     *       rent == (cost*0.2) || rent == cost*0.1
     *       gameState != 4 && gameState != 0 && gameState != 6 && gameState != 3
     *       players[playerTurn].getBalance() > 0
     */
    public void purchase() {
        assert players[playerTurn].getBalance() > board[players[playerTurn].getPosition()].getCost(): "Player must be able to afford property";
        assert board[players[playerTurn].getPosition()].getOwner() == 2: "This property should not be purchasable";
        assert gameState == 4: "You should not be able to purchase property at this game state";
        assert invariant(): "Invariant must be maintained";
        double preBalance = players[playerTurn].getBalance();
        
        //adds tile to player's array, sets owner of the tile, updates player balance and sets rent on the property 
        players[playerTurn].addProperty(board[players[playerTurn].getPosition()]); 
        board[players[playerTurn].getPosition()].setOwner(playerTurn);
        players[playerTurn].substractBalance(board[players[playerTurn].getPosition()].getCost());
        board[players[playerTurn].getPosition()].setRent();      
        
        // checks if set is now owned and updates rent for other properties if so
        if (board[players[playerTurn].getPosition()].checkSet()) { 
            for (Tile p: board[players[playerTurn].getPosition()].getSet()) {
                p.setRent();
            }
        }
        
        setGameState();
        setChanged();
        notifyObservers();
        
        double rent = board[players[playerTurn].getPosition()].getRent();
        int cost = board[players[playerTurn].getPosition()].getCost();
        assert preBalance == players[playerTurn].getBalance() + cost: "Player did not pay the correct amount";
        assert players[playerTurn].getProperties().contains(board[players[playerTurn].getPosition()]): "Player must recieve property";
        assert rent == cost * 0.1 || rent == cost * 0.2: "Rent is not the correct amount";
        assert gameState !=4 && gameState != 0 && gameState != 6 && gameState != 3: "Game has entered incorrect game state";
        assert players[playerTurn].getBalance() > 0: "player must not be made bankrupt by purchase";
        assert invariant(): "Invariant must be maintained";
    }
    
    /**@pre gameState == 3 && board[players[playerTurn].getPosition()].getOwner() == opponent
     * @post gameState == 5 || gameState == 6
     *       players[playerTurn].getBalance() = playPreBal - rent
     *       players[opponent].getBalance = oppPreBal + rent
     *       winner >=0 && winner <= 2
     */
    public void payRent() {
        assert gameState == 3: "You should not pay rent at this game state";
        assert board[players[playerTurn].getPosition()].getOwner() == opponent: "The opponent must own the property";
        assert invariant(): "Invariant must be maintained";
        double playPreBal = players[playerTurn].getBalance();
        double oppPreBal = players[opponent].getBalance();
        
        players[playerTurn].substractBalance(board[players[playerTurn].getPosition()].getRent()); // lowers balance of current player
        players[opponent].addBalance(board[players[playerTurn].getPosition()].getRent()); // increase balance of opponent
        if (players[playerTurn].getBalance() < 0) { // checks win condition
            gameState = 6; // game ends game state
            if (playerTurn == 0) {
                winner = 2;
            } else {
                winner = 1;
            }
        } else {
            gameState = 5;
        }
        setChanged();
        notifyObservers();
        
        double rent = board[players[playerTurn].getPosition()].getRent();
        assert gameState == 5 || gameState == 6: "Game has entered incorrect game state";
        assert players[playerTurn].getBalance() == playPreBal - rent: "Current player's balance is incorrect";
        assert players[opponent].getBalance() == oppPreBal + rent: "Opponent's balance is incoorect";
        assert invariant(): "Invariant must be maintained";
    }
    
    /**@pre gameState == 1
     *      board[players[playerTurn].getPosition()].checkSet()
     *      board[players[playerTurn].getPosition()].getNoHouses() < 4
     *      players[playerTurn].getBalance() > board[players[playerTurn].getPosition()].getCost() * 0.5
     * @post gameState != 0 || gameState != 3 || gameState != 4 || gameState != 6 
     *       players[playerTurn].getBalance() == preBalance - cost
     *       noHouses == preNoHouses + 1
     *       rent == (cost*0.2) + (0.1 * ((noHouses*cost))
     *       players[playerTurn].getBalance() > 0
     */
    public void buyHouse() {
        assert gameState == 1: "Game is in incorrect game state";
        assert board[players[playerTurn].getPosition()].checkSet(): "player must own the set";
        assert board[players[playerTurn].getPosition()].getNoHouses() < 4: "Player must not own 4 houses here already";
        double preBalance = players[playerTurn].getBalance();
        double cost = board[players[playerTurn].getPosition()].getCost() * 0.5;
        assert preBalance > cost: "Player cannot afford to buy a house";
        assert invariant(): "Invariant must be mantained";
        int preNoHouses = board[players[playerTurn].getPosition()].getNoHouses();
        
        // subtracts cost of house from player, adds house to the tile, updates the rent 
        players[playerTurn].substractBalance(board[players[playerTurn].getPosition()].getCost() * 0.5);
        board[players[playerTurn].getPosition()].addHouse();
        board[players[playerTurn].getPosition()].setRent();
        
        setGameState();
        setChanged();
        notifyObservers();
        
        double rent = board[players[playerTurn].getPosition()].getRent();
        double balance = players[playerTurn].getBalance();
        int noHouses = board[players[playerTurn].getPosition()].getNoHouses();
        assert gameState != 0 || gameState != 3 || gameState != 4 || gameState !=6: "Game has entered incorrect gameState";
        assert balance == preBalance - cost: "Player balance is incorrect";
        assert noHouses == preNoHouses + 1: "Number of houses is incorrect";
        assert rent == (2*cost*0.2) + (0.1 * (noHouses*cost)): "Rent has inccorect value";
        assert players[playerTurn].getBalance() > 0: "Player must not be made bankrupt by purchase";
        assert invariant(): "invariant must be maintained";
    }
    
    /**@pre gameState == 2
     *      board[players[playerTurn].getPosition()].checkSet()
     *      !board[players[playerTurn].getPosition()].hasHotel()
     *      players[playerTurn].getBalance() > board[players[playerTurn].getPosition()].getCost() * 0.8
     * @post gameState == 5
     *       board[players[playerTurn].getPosition()].hasHotel()
     *       rent = (cost*0.2) + (0.1 * ((noHouses*0.5*cost) + (0.8*cost)))
     *       players[playerTurn].getBalance() == preBalance - (cost*0.8)
     *       players[playerTurn].getBalance() > 0
     */
    public void buyHotel() {
        assert gameState == 2: "game is in incorrect state";
        assert board[players[playerTurn].getPosition()].checkSet(): "Player must own the entire set";
        assert !board[players[playerTurn].getPosition()].hasHotel(): "Hotel must not already be bought";
        double preBalance = players[playerTurn].getBalance();
        double cost = board[players[playerTurn].getPosition()].getCost();
        assert preBalance > (cost*0.8): "Player must be able to afford Hotel";
        assert invariant(): "Invariant must be maintained";
        
        // substracts hotel cost from balance, adds hotel to tile and updates rent
        players[playerTurn].substractBalance(board[players[playerTurn].getPosition()].getCost() * 0.8);
        board[players[playerTurn].getPosition()].addHotel();
        board[players[playerTurn].getPosition()].setRent();
        
        setGameState();
        setChanged();
        notifyObservers();
        
        double rent = board[players[playerTurn].getPosition()].getRent();
        double balance = players[playerTurn].getBalance();
        assert gameState == 5: "game is in incorrect state";
        assert board[players[playerTurn].getPosition()].hasHotel(): "Player must own Hotel";
        assert rent == (cost*0.2) + (0.1 * ((4*0.5*cost) + (0.8*cost))): "Rent has become incorrect";
        assert balance == preBalance - (cost*0.8): "Balance has becoem incorrect";
        assert balance > 0: "Player must not be bankrupt from purchase";
        assert invariant(): "Invariant must be maintained";
    }

    /**@pre gameState != 0 && gameState != 3 && gameState != 6
     *      
     * @post gameState == 0
     *       opponent == prePlayer
     *       playerTurn == preOpponent
     */
    public void endTurn() {
        assert gameState != 0 && gameState != 3 && gameState != 6: "game is in incorrect state";
        assert invariant(): "Invariant must be maintained";
        int prePlayer = playerTurn;
        int preOpponent = opponent;
        
        // checks whose turn it currently is then switches values
        if (playerTurn == 0) { 
            playerTurn = 1;
            opponent = 0;
        } else if (playerTurn == 1) {
            playerTurn = 0;
            opponent = 1;
        }
        
        gameState = 0; // sets only roll button enabled
        setChanged();
        notifyObservers();
        
        assert gameState == 0: "game is in incorrect state";
        assert playerTurn == preOpponent: "player turn must change";
        assert opponent == prePlayer: "Opponent must change";
        assert invariant(): "Invariant must be maintained";
    }

    /**@pre gameState != 6
     *      playerPos >= 0 && playerPos <= 39
     * @post gameState!= 6
     */
    private void setGameState() {
        assert gameState != 6: "game is in an incorrect state";
        int playerPos = players[playerTurn].getPosition();
        assert playerPos >=0 && playerPos <= 39: "Player must be in a valid position";
        assert invariant(): "Invariant must be maintained";
        
        if (board[playerPos].getOwner() == 3) {
            gameState = 5;
        } else if (board[playerPos].getOwner() == playerTurn && board[playerPos].checkSet() && (board[playerPos].getCost() *0.5) < players[playerTurn].getBalance() && board[playerPos].getNoHouses() != 4) {
            gameState = 1; //game state 1 means player can buy houses on property
        } else if (board[playerPos].getOwner() == playerTurn && board[playerPos].checkSet() && (board[playerPos].getCost() *0.8) < players[playerTurn].getBalance() && !board[playerPos].hasHotel() && board[playerPos].getNoHouses() == 4) {
            gameState = 2; // player can buy hotel
        } else if (board[playerPos].getOwner() == opponent) {
            gameState = 3; //player must pay rent
        } else if (board[playerPos].getOwner() == 2 && board[playerPos].getCost() < players[playerTurn].getBalance()) {
            gameState = 4; // can purchase property
        } else {
            gameState = 5; // only end turn
        }
        
        assert gameState != 6: "game is in an incorrect state";
        assert invariant(): "Invariant must be maintained";
    }

    // these are a series of getters used by the controller class
    
    public int getPlayerTurn() {
        assert invariant();
        return playerTurn;
    }

    /**@pre turn == 0 || turn == 1
     *      players[turn].getPosition() >= 0 && players[turn].getPosition() <= 39
     * 
     */
    public int getPlayerPos(int turn) {
        assert turn == 0 || turn == 1: "Turn must be 0 or 1";
        assert players[turn].getPosition() >= 0 && players[turn].getPosition() <= 39: "Player is in an invalid position";
        return players[turn].getPosition();
    }

    /**@pre players != null
     *  
     */
    public Player[] getPlayers() {
        assert players != null: "players must exist";
        return players;
    }
    
    /**@pre gameState >=0 && gameState <= 6
     * 
     */
    public int getGameState() {
        assert gameState >=0 && gameState <= 6: "game is in an invalid state";
        return this.gameState;
    }

    /**@pre board != null
     * 
     */
    public Tile[] getBoard() {
        assert board != null: "board must exist";
        return MModel.board;
    }
    
    /**@pre winner >= 0 && winner <= 2
     * 
     */
    public int getWinner() {
        assert winner >= 0 && winner <= 2: "winner must be 0, 1 or 2";
        return this.winner;
    }
    
    
    public int getOpponent() {
        assert invariant();
        return this.opponent;
    }
}