package gamePackage;

import java.util.Scanner;
import java.io.IOException;

//this file runs the actual game
public class GameFile {
    //IOexception handling for write to leaderboard method call in leaderboard.java
    public static void main(String[] args) throws IOException {
        System.out.println("★★★Hello, welcome to DanGames v1.0!★★★");
        while(true){
            System.out.println("\n• To start a game as a new player, please enter 1");
            System.out.println("• To quit, please enter 2");

            //initialise scanner
            Scanner scanner = new Scanner(System.in);
            int firstMenuInput = Validation.Validate(2);

            if(firstMenuInput==1)
            {
                System.out.println(" ");
            }else
            {
                System.out.printf("║▓▓▓▓▒▒▒▒░░░░%5s░░░░▒▒▒▒▓▓▓▓║%n","Leaderboard");
                System.out.printf("║%-25s%10s║%n","Player name","Score");
                System.out.printf("║%-35s║%n","-----------------------------------");
                Leaderboard.ReturnLeaderboard();
                System.exit(0);
            }

            // #################### player creation ########################
            System.out.println("Please enter your name. Spaces & special characters are permitted. ");
            String newUserName = "";
            while(newUserName.length() < 3)
            {
                System.out.println("Enter username: minimum 3 characters: ");
                newUserName = scanner.nextLine();
            }
            System.out.println("Hello " + newUserName +", please select your player account type: \n");
            System.out.println("• 1 |Normal account. No restrictions or benefits, starts with 15 credits");
            System.out.println("• 2 |VIP account. No restrictions, starts with 30 credits and gets fancy leaderboard entry");
            System.out.println("• 3 |Limited account. Can only wager 5 credits at a time, starts with 15 credits\n");
            System.out.println("Please enter the number corresponding to the account type you desire (1,2 or 3):");

            int newUserAccType = Validation.Validate(3);
            
            Player currentUser = new Player(newUserAccType,newUserName); //create instance of Player class

            // #################### gameplay ########################
            while (true)
            {
                System.out.println("░░▒▒▒▒║☆☆☆☆☆║GAME SELECTION║☆☆☆☆☆║▒▒▒▒░░");
                System.out.println(currentUser.getName() + ", please choose a game from the list below; or enter -1 to quit");
                System.out.println("◄▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬►");
                System.out.println("1. Dice wager\n2. Monty Hall\n3. Rock, Paper, Scissors");
                System.out.println("◄▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬►");

                //can't use Validation.validate here, as -1 is used to quit
                int userGame = 0;int userInput;
                try{
                    while (true)
                    {
                        System.out.println("Enter value from 1 to 3; or -1 to quit");
                        userInput = scanner.nextInt();
                        if(userInput==1 || userInput==2 || userInput ==3 || userInput==-1){
                            userGame = userInput;
                            break;
                        }
                    }
                }catch (java.util.InputMismatchException inputError){
                    System.out.println("Error: Non-int value entered. Terminating..");
                    System.exit(1);
                }

                switch (userGame){
                    case 1:
                        System.out.println("You've picked: Dice roll!");
                        break;
                    case 2:
                        System.out.println("You've picked: Monty Hall!");
                        break;
                    case 3:
                        System.out.println("You've picked: Rock, Paper, Scissors!");
                        break;
                }

                if(userGame==-1){
                    System.out.println("Thanks for playing!");
                    Leaderboard.WriteToLeaderboard(currentUser.getName(),currentUser.getCredits());
                    break;
                }
                int userWager = 0;
                //encapsulate in a while loop to pester player until they enter acceptable wager
                boolean wagerSetup = true;
                while(wagerSetup)
                {
                    System.out.println("Please enter your desired wager for this game: ");
                    userWager = scanner.nextInt();
                    if(userWager < 1) //check min bet
                    {
                        System.out.println("Error: Wager must be greater than or equal to 1!");
                    }
                    else if(currentUser.getRestricted() && userWager>5) //check user restrictions
                    {
                        System.out.println("Error: you are a restricted user, and can only wager up to 5 points");
                    }else if(currentUser.getCredits() < userWager) //check bet against user balance
                    {
                        System.out.println("Error: you do not have enough points for that wager.");
                    }else
                    {
                        wagerSetup = false;
                    }
                }
                int gameResult;
                //this switch plays the actual game selected, from the Games.java file.
                switch (userGame){
                    case 1: //dice
                        gameResult = Games.DiceGame(userWager,false);
                        currentUser.addCredits(gameResult);
                        break;
                    case 2: //monty hall
                        gameResult = Games.MontyHall(userWager);
                        currentUser.addCredits(gameResult);
                        break;
                    case 3: //rock paper scissors
                        gameResult = Games.RockPaperScissors(userWager);
                        currentUser.addCredits(gameResult);
                        break;
                }
                System.out.println("Current user balance: " + currentUser.getCredits() +"\n");
            }
        }

    }
}
