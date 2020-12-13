package minipoly;

public class MController {
    private MModel model;
    private MView view;

    MController(MModel model) {
        this.model = model;
    }
    
    public void setView(MView view) {
        this.view = view;
    }


    public void roll() {
        int turn = model.getPlayerTurn();
        //removes token current position
        view.getTiles()[model.getPlayerPos(turn)].getChildren().remove(view.getToken(turn));
        //rolls the dice
        int roll = model.roll();
        //adds token to its new position
        view.getTiles()[model.getPlayerPos(turn)].getChildren().add(view.getToken(turn));
        //sends string describing the roll to game info
        int i = turn +1;
        String s = "Player " + i + " rolled " + roll;
        setInfo(s);
        configureButtons();
    }

    public void purchaseProperty() {
        //purchases current property and updates player info
        model.purchase();
        view.setPlayerInfo(model.getPlayerTurn());
        //passes on string describing purchase
        int i = model.getPlayerTurn() + 1;
        String prop = model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName();
        String s = "Player " + i + " purchased " + prop;
        setInfo(s);
        configureButtons();
    }

    public void purchaseHouse() {
        //purchases house and updates player info
        model.buyHouse();
        view.setPlayerInfo(model.getPlayerTurn());
        //sends string describing the purchase
        int i = model.getPlayerTurn() + 1;
        String prop = model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName();
        String s = "Player " + i + " purchased house on " + prop;
        setInfo(s);
        configureButtons();
    }

    public void purchaseHotel() {
        //buys hotel and updates player's info
        model.buyHotel();
        view.setPlayerInfo(model.getPlayerTurn());
        //sends string describing hotel purchase to game info
        int i = model.getPlayerTurn() + 1;
        String prop = model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getName();
        String s = "Player " + i + " purchased hotel on " + prop;
        setInfo(s);
        configureButtons();
    }

    public void endTurn() {
        model.endTurn();
        
        setInfo("");
        configureButtons();
    }

    public String getPlayerInfo(int i) {
        String info;
        int j = i +1;
        //prints out player number and balance
        info = "Player " + j;
        info += ":\nBalance: £" + model.getPlayers()[i].getBalance() +"\n" + "Properties Houses Hotels\n";
        //checks if they have any properties and if so ands that info too
        if (!model.getPlayers()[i].getProperties().isEmpty()) {
            for (Tile p : model.getPlayers()[i].getProperties()) {
                info += p.getName() + "              " + p.getNoHouses() + "          ";
                if (p.hasHotel()) { // checks hotel boolean
                    info += "1\n";
                } else {
                    info += "0\n";
                }
            }
        }
        return info;
    }

    public void submit(int i) {
        int turn = model.getPlayerTurn();
        //edits token position based on cheat roll
        view.getTiles()[model.getPlayerPos(turn)].getChildren().remove(view.getToken(turn));
        model.cheatRoll(i);
        view.getTiles()[model.getPlayerPos(turn)].getChildren().add(view.getToken(turn));
        
        configureButtons();
    }

    private void payRent() {
        //pays rent and updates both player information
        model.payRent();
        view.setPlayerInfo(0);
        view.setPlayerInfo(1);
        //sends string to update game info about rent payed
        String s = "Player " + (model.getPlayerTurn()+1) + " payed £" + model.getBoard()[model.getPlayers()[model.getPlayerTurn()].getPosition()].getRent() + " in rent.";
        
        setInfo(s);
        configureButtons();
    }
    
    private void configureButtons() {
        int state = model.getGameState();
        switch(state) {
            case 0: //new turn, can only roll
                view.setEnableRoll(false);
                view.setEnableCheat(false);
                view.setEnableEnd(true);
                view.setEnableHotel(true);
                view.setEnableHouse(true);
                view.setEnableProp(true);
                break;
            case 1: //can buy houses
                view.setEnableRoll(true);
                view.setEnableCheat(true);
                view.setEnableEnd(false);
                view.setEnableHotel(true);
                view.setEnableHouse(false);
                view.setEnableProp(true);
                break;
            case 2: // can buy hotels
                view.setEnableRoll(true);
                view.setEnableCheat(true);
                view.setEnableEnd(false);
                view.setEnableHotel(false);
                view.setEnableHouse(true);
                view.setEnableProp(true);
                break;
            case 3: // player pays rent
                payRent();
                break;
            case 4: //can purchase property
                view.setEnableRoll(true);
                view.setEnableCheat(true);
                view.setEnableEnd(false);
                view.setEnableHotel(true);
                view.setEnableHouse(true);
                view.setEnableProp(false);
                break;
            case 5: // can only end turn
                view.setEnableRoll(true);
                view.setEnableCheat(true);
                view.setEnableEnd(false);
                view.setEnableHotel(true);
                view.setEnableHouse(true);
                view.setEnableProp(true);
                break;
            case 6: // game over, there's a winner
                view.setEnableRoll(true);
                view.setEnableCheat(true);
                view.setEnableEnd(true);
                view.setEnableHotel(true);
                view.setEnableHouse(true);
                view.setEnableProp(true);
                //sends string announcing winner to game info
                String s = "Player " + model.getWinner() + " wins!";
                view.setGameInfo(s);
            default: break;
        }
    }
    
    private void setInfo (String s) {
        // announces player turn and describes last move in center of the board
        int i = model.getPlayerTurn() + 1;
        String str = "Player " + i + "'s Turn:\n";
        str += s;
        view.setGameInfo(str);
    }
}