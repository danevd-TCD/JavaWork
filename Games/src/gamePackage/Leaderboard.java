package gamePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//these classes handle leaderboard writing/reading
public class Leaderboard {
    public static void WriteToLeaderboard(String playerName, int playerScore) throws IOException {
        //create file
        try {
            File leaderboard = new File("leaderboard.txt");
            if (leaderboard.createNewFile()) {
                System.out.println("Creating and updating leaderboard with your score...");
            } else {
                System.out.println("Updating leaderboard with your score...");
            }
        }catch (IOException IOerror){
            System.out.println("Leaderboard file creation error: Printing stacktrace...");
            IOerror.printStackTrace();
        }
        //write to file
        try{
            FileWriter leaderboardWriter = new FileWriter("leaderboard.txt", true); //append=true
            leaderboardWriter.write(String.format("║%-25s%010d║%n",playerName,playerScore));
            leaderboardWriter.close();
        }catch (IOException IOerror){
            System.out.println("Leaderboard file writing error: Printing stacktrace...");
            IOerror.printStackTrace();
        }
    }

    public static void ReturnLeaderboard() //print leaderboard
    {
        try{
            File read_lboard = new File("leaderboard.txt");

            Scanner fileReader = new Scanner(read_lboard);
            ArrayList<String> lineStrings = new ArrayList<String>(); //empty string arrayList
            while(fileReader.hasNextLine())
            {
                String currentLine = fileReader.nextLine();
                lineStrings.add(currentLine);
            }

            //collections.sort comparator operation on last 9 characters of score (there are up to 9 trailing zeroes)
            Collections.sort(lineStrings, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    //if a is "║dan                              15║", then o1LastNum points
                    //here -------------------------------------^
                    int o1LastNum = o1.lastIndexOf(" ");
                    int o2LastNum = o2.lastIndexOf(" ");
                    //return o1.substring(o1LastNum-9).compareTo(o2.substring(o2LastNum-9));
                    return  o2.substring(o2LastNum-9).compareTo(o1.substring(o1LastNum-9));
                }
            });

            //write sorted leaderboard to file
            try{
                FileWriter leaderboardWriter = new FileWriter("leaderboard.txt", false); //append=false, to overwrite
                for (String name: lineStrings) {
                    leaderboardWriter.write(name + "\n");
                }
                leaderboardWriter.close();
            }catch (IOException IOerror){
                System.out.println("Leaderboard file writing error: Printing stacktrace...");
                IOerror.printStackTrace();
            }
            fileReader.close();

            Scanner newReader = new Scanner(read_lboard);
            while(newReader.hasNextLine()) //print output
            {
                String currentLine = newReader.nextLine();
                System.out.println(currentLine);
            }

        }catch (FileNotFoundException missingFile)
        {
            System.out.println("Error: Leaderboard file not found! Printing stacktrace..");
            missingFile.printStackTrace();
        }
    }
}
