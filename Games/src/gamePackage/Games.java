package gamePackage;

import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Games {
    /*  dice game accepts a bet amount and an outcome (e.g i bet 5 points on rolling a 6).
        multiple interactions project requirement:
        the dice game offers a "double or nothing" option on a win.
        the game returns an integer of how much the player won/lost
        double or nothing variable decides how game logic should run on first/second go
     */
    public static int DiceGame(int betAmount, boolean doubleOrNothing)
    {
        if(doubleOrNothing == false)
        {
            System.out.println("★★★★★☆☆☆☆☆DICE☆☆☆☆☆★★★★★");
            System.out.println("Welcome to dice rolling!");
        }
        System.out.println("Pick your bet on the roll outcome (1 to 6)");
        int outcome = Validation.Validate(6);
        if(doubleOrNothing == false)
        {
            System.out.println("You bet " + betAmount + " on rolling a " + outcome);
        }
        System.out.println("Let's roll the die. Best of luck!");
        Random diceRes = new Random();
        int diceResult = diceRes.nextInt(5) + 1; //random from 0 to 5, plus 1

        System.out.println("The result of the roll is....");
        System.out.println(diceResult + "!");

        if(diceResult == outcome && doubleOrNothing == false) //first play win; no DoN
        {
            System.out.println("Congratulations! You win!");
            System.out.println("Would you like to go double or nothing? Roll again, and double your earnings if you win; but risk losing it all if you lose!");
            System.out.println("What'll it be? Enter 1 for yes, 2 for no");

            //to do: catch errors in user input for scanners
            int userInput = Validation.Validate(2);
            if(userInput==1) //if user decides to go for double or nothing:
            {
                DiceGame(betAmount, true);
            }else{ //if user leaves after first victory
                betAmount = 2 * betAmount; //double the user's bet amount
                System.out.println("Ok, enjoy your earnings then!");
                System.out.println("You won " + betAmount + " points.");
            }
        }else if(diceResult == outcome && doubleOrNothing){ //user wins DoN
            betAmount = 4 * betAmount; //quadruple users' bet amount
            System.out.println("Jackpot! Double-or-nothing victory! Congratulations!");
            System.out.println("You won " + betAmount + " points.");
        }else if(doubleOrNothing){ //user loses DoN
            betAmount = -1 * betAmount;
            System.out.println("Ouch, lost it all! Too bad, try again next time.");
            System.out.println("You lost " + betAmount * -1 + " points.");
        }else{ //user loses on first roll
            betAmount = -1 * betAmount;
            System.out.println("Aw, better luck next time :)");
            System.out.println("You lost " + betAmount * -1 + " points.");
        }
        return betAmount;
    }

    /*
        The famous Monty Hall problem, recreated here in Java in full goaty glory. Project requirements for
        multiple interactions: the monty hall problem is inherently interactive, as the user is presented a choice
        halfway through, regarding whether they wish to change their initial answer.
    */
    public static int MontyHall(int betAmount)
    {
        //define our doors, and shuffle array for each instance
        String[] doors = {"Goat","Goat","Car"};
        List<String> doorList = Arrays.asList(doors);
        Collections.shuffle(doorList);
        doorList.toArray(doors);

        System.out.println("★★★★★☆☆☆☆☆MONTY HALL☆☆☆☆☆★★★★★");
        System.out.println("Welcome to the Monty Hall game!");
        System.out.println("There are three doors you can choose from. Behind one door is the prize; behind the other two doors are goats.");
        System.out.println("The doors are 1, 2, and 3. Please pick a door to open.");

        //user enters "3"; in array index that is 3-1 = 2 for third door in array
        int userInput = Validation.Validate(3) - 1;
        System.out.println("You've picked door " + (userInput + 1) + ".");
        System.out.println("I'm now going to select a different door and open it, and show you what's behind it.");
        System.out.println("Note: I can only select a door to show you if it has a goat behind it; I know what each door holds.");

        //find locations in shuffled doors array where goats are; add to goat array
        int[] goatLoc = new int[2];
        int j =0;
        for(int i=0; i < 3; i++)
        {
            if(doors[i].equals("Goat"))
            {
                goatLoc[j] = i;
                j++;
            }
        }
        //initialise door result to impossible value for a later while loop that re-assigns this value
        int doorResult=-5;
        //if user picks car, host can pick either of the goat doors to show
        if(doors[userInput].equals("Car"))
        {
            Random doorRes = new Random();
            int randGoatDoor = doorRes.nextInt(1) + 1;
            //doorResult = doorRes.nextInt(1) + 1; //random from 0 to 1, plus 1 (1-2)
            doorResult = goatLoc[randGoatDoor];
        }//if user picks goat, host must reveal the *remaining* goat door
        else if(doors[userInput].equals("Goat"))
        {
            /*
            repeatedly generate random number until one that is *not* equal to user's guess is obtained
             n.b: this random number generation is actually a better fit for the statistics/probability
             nature of this game than it may seem,as the game can be played just as easily
             with 300 goats as 3; in which case all you need to do is adjust random number selection bound below.
            */
            Random doorRes = new Random();
            int randAttempt;
            while(doorResult==-5)
            {
                randAttempt = doorRes.nextInt(3); //from 0 to 2 in index
                //if random number is *not* equal to user input, and its value at door array is goat, then select it
                if(randAttempt!=userInput && doors[randAttempt].equals("Goat")){
                    doorResult=randAttempt;
                }
            }
        }
        System.out.println("I'm going to open door " + (doorResult + 1) + ".\n-------------------------");
        //print out a pretty little door opening text set :)
        for (int i = 0; i < 3; i++) {
            if(i==doorResult) {
                System.out.println("Door " + (i + 1) +": \uD83D\uDC10 Goat");
            }else if(i==userInput) {
                System.out.println("Door " + (i + 1) +": ???? ← Your choice");
            }
            else { System.out.println("Door " + (i + 1) +": ????"); }
        }
        System.out.println("-------------------------\nAs you can see, there's a goat behind it.");
        System.out.println("Now, do you want to change your door choice to the remaining closed door, or stay on your first choice?");
        System.out.println("Enter 1 to change door choice to the remaining door; enter 2 to stay on your first door choice.");
        //to do: catch errors in user input for scanners
        int doorChange = Validation.Validate(2);
        if(doorChange == 1)
        {
            int availDoor = 0;
            //loop through array, select door that does not match userInput or goatLoc
            for (int i = 0; i <2 ; i++) {
                //if the doors value is *not* equal to user input, and is not equal to door from goat result, it is last
                //available door
                if(!(doors[i].equals(doors[userInput])) || !(doors[i].equals(doors[doorResult])))
                {
                    availDoor = i;
                }
            }
            userInput = availDoor;
        }

        System.out.println("Your final choice was: Door " + (userInput + 1));
        System.out.println("Let's see whether you guessed right...\n-------------------------");
        for (int i = 0; i < 3; i++) {
            //ternary operator to print user's final door choice with an arrow
            String doorOutput = i==userInput ? ("Door " + (i + 1) + ": " + doors[i]+ " ← Final choice") : ("Door " + (i + 1) + ": " + doors[i] );
            System.out.println(doorOutput);
        }
        System.out.println("-------------------------");
        if(doors[userInput].equals("Car"))
        {
            System.out.println("Congratulations! You picked right!");
            betAmount = 2 * betAmount; //double the user's bet amount
            System.out.println("You won " + betAmount + " points.");
        }else
        {
            betAmount = -1 * betAmount;
            System.out.println("Darn, unlucky. Try again next time!");
            System.out.println("You lost " + betAmount * -1 + " points.");
        }
        return betAmount;
    }

    /*
    A simple game of rock-paper-scissors. Multiple interaction requirement: This is a best-of-three game, meaning
    players always play three times
     */
    public static int RockPaperScissors(int betAmount)
    {
        System.out.println("★★★☆☆☆ROCK, PAPER, SCISSORS!☆☆☆★★★");
        System.out.println("Welcome to Rock, Paper, Scissors!");
        System.out.println("This is a best of three game.");

        int gameCounter = 0; //zeroth game is the first one
        int gameLimit = 3; //best of 3
        int userScore = 0;
        int computerScore = 0;
        while(gameCounter<gameLimit)
        {
            Random computerRan = new Random();
            int computerResult = computerRan.nextInt(3) + 1; //random from 1 to 3
            System.out.println("------------------------\nNew Round:");
            System.out.println("Enter 1 to pick Rock\nEnter 2 to pick Paper \nEnter 3 to pick Scissors");
            int userInput = Validation.Validate(3);
            System.out.println("Game " + (gameCounter + 1) + " of " + gameLimit + " outcome:");
            if(userInput==1 && computerResult==2) //user has rock; computer has paper; user loses
            {
                System.out.println("You picked Rock; Computer picked Paper. You lose this round.");
                computerScore ++;gameCounter++;
            }else if(userInput==1 && computerResult==3) //user:rock, comp:scissors, user wins
            {
                System.out.println("You picked Rock; Computer picked Scissors. You win this round.");
                userScore ++;gameCounter++;
            }else if(userInput==1 && computerResult==1)
            {
                System.out.println("You picked Rock; Computer picked Rock. Draw! Play again.");
            }else if(userInput==2 && computerResult==1)//user:Paper, comp: Rock, user wins
            {
                System.out.println("You picked Paper; Computer picked Rock. You win this round.");
                userScore ++;gameCounter++;
            }else if(userInput==2 && computerResult==3) //user:paper, comp:scissors, user loses
            {
                System.out.println("You picked Paper; Computer picked Scissors. You lose this round.");
                computerScore ++;gameCounter++;
            }else if(userInput==2 && computerResult==2) //draw
            {
                System.out.println("You picked Paper; Computer picked Paper. Draw! Play again.");
            }else if(userInput==3 && computerResult==1) //user:scissors, comp: rock, user loses
            {
                System.out.println("You picked Scissors; Computer picked Rock. You lose this round.");
                computerScore ++;gameCounter++;
            }else if(userInput==3 && computerResult==2) //user:scissors, comp: paper, user wins
            {
                System.out.println("You picked Scissors; Computer picked Paper. You win this round.");
                userScore ++;gameCounter++;
            }else
            {
                System.out.println("You picked Scissors; Computer picked Scissors. Draw! Play again.");
            }
        }
        System.out.println("------------------------\nFinal score:\nYou: " + userScore +"\nComputer: " + computerScore+"\n------------------------");
        if(userScore>computerScore)
        {
            betAmount = 2 * betAmount;
            System.out.println("Congratulations, you win "+ betAmount +" points!");
        }else
        {
            betAmount = -1 * betAmount;
            System.out.println("You lose. Better luck next time! You lost " + betAmount * -1 + " points");
        }
        return betAmount;
    }
}
