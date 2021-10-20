
package gamePackage;
//this package contains the code/classes to run the game
public class Player {
    private int Credits = 15; //default player credit start
    private String Name = "Player"; //default player name
    private boolean Restricted = false; //default players are not limited/restricted

    //constructor to create new Player account
    public Player(int Type, String newName)
    {
        switch (Type)
        {
            case 1: //1 = normal player
                this.Name = newName;
                break;
            case 2: //2 = VIP player
                this.Credits = 30; //VIPS get extra credits
                this.Name = "(VIP) " + newName;
                break;
            case 3: //3 = restricted player
                this.Restricted = true; //restricted player class gets their restricted bool attribute updated
                this.Name = newName;
        }
    }
    //get player name
    public String getName()
    {
        return Name;
    }
    //get player credits
    public int getCredits()
    {
        return Credits;
    }
    //get player restriction status
    public Boolean getRestricted()
    {
        return Restricted;
    }

    //set player name (likely not necessary, but may be useful?)
    public void setName(String newName)
    {
        this.Name = newName;
    }
    //set player credits to a given amount
    public void setCredits(int newAmount)
    {
        this.Credits = newAmount;
    }
    //set player restriction status
    public void setRestricted(boolean status)
    {
        this.Restricted = status;
    }
    //add credits to players account
    public void addCredits(int creditAmount) {this.Credits = this.getCredits() + creditAmount;}
}
