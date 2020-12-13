package minipoly;

import java.util.ArrayList;

public class Player {
    private ArrayList<Tile> properties;
    private int playerNo;
    private double balance;
    private int position;
    
    public Player(int i) {
        this.playerNo = i;
        this.balance = 2000;
        this.properties = new ArrayList<>();
    }
    
    public int getPlayerNo() {
        return this.playerNo;
    }
    
    public double getBalance() {
        return this.balance;
    }
    
    public ArrayList<Tile> getProperties() {
        return this.properties;
    }
    //adds to player balance
    public void addBalance(double i) {
        this.balance += i;
    }
    //takes away from player balance
    public void substractBalance(double i) {
        this.balance -= i;
    }
    //adds a property to array list and sets the player as its owner
    public void addProperty(Tile property) {
        this.properties.add(property);
        property.setOwner(this.playerNo);
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public int getPosition() {
        return this.position;        
    }

}
