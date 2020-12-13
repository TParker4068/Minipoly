package minipoly;

public class Tile {
    private final String name;
    private final int cost;
    private double rent;
    private int noHouses;
    private int noHotel;
    private int owner;
    private Tile[] set;
        
    //constructor for property tile
    public Tile(String name, int x, int y) {
        this.name = name;
        this.set = new Tile[2];
        this.cost = x;
        this.noHouses = 0;
        this.noHotel = 0;
        this.owner = y;
    }
    //constructor for blank tile
    public Tile() {
        this.name = "Blank";
        this.cost = 0;
        this.noHouses = 0;
        this.noHotel = 0;
        //owner = 3 means it will never be asked to act like a property
        this.owner = 3;
    }
    //constructor for the go tile 
    public Tile(String name) {
        this.name = name;
        this.set = new Tile[2];
        this.cost = 0;
        this.noHouses = 0;
        this.noHotel = 0;
        this.owner = 3;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public void setOwner(int playerNo) {
        this.owner = playerNo;
    }
    
    public Tile[] getSet() {
        return this.set;
    }
    
    //puts all 3 tiles in each others set arrays
    public void setSets(Tile tile1, Tile tile2) {
        set[0] = tile1;
        set[1] = tile2;
        tile1.getSet()[0] = this;
        tile1.getSet()[1] = tile2;
        tile2.getSet()[0] = this;
        tile2.getSet()[1] = tile1;
    }
    
    public int getNoHouses(){
        return this.noHouses;
    }
    
    public int getOwner() {
        return this.owner;
    }
    
    public boolean hasHotel() {
        return noHotel == 1;
    }
    
    public double getRent() {
        return this.rent;
    }
    
    public void setRent() {
        if (!this.checkSet()) { // checks for set 
            rent = (int) (cost * 0.1);
        } else {
            this.rent = (cost*0.2);// calculates new rent after purchase of house/hotel;
            this.rent +=  0.1 * (noHouses*0.5*cost);
            if (this.hasHotel()) {
                this.rent += 0.1 * (cost*0.8);
            }
        }
    }
    
    public void addHouse() {
        this.noHouses += 1;
    }
    
    public void addHotel() {
        this.noHotel = 1;
    }
    //checks if all properties in a set have the same owner
    public boolean checkSet() {
        boolean owned = true;
        for (Tile p : this.set) {
            if (this.owner != p.owner) {
                owned = false;
            }
        }
        return owned;
    }
}
