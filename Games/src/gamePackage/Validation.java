package gamePackage;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

//validation class: using sets for membership testing with .contains() method
//validates inputs from 1 to argument "numRange", e.g "select number from 1 to 3:"
public class Validation {
    public static int Validate(int numRange)
    {
        Set numSet = new LinkedHashSet();
        for (int i = 1; i < numRange+1; i++) {
            numSet.add(i);
        }
        Scanner ValidateScanner = new Scanner(System.in);
        int validateInput = 0;
        try{
            while (true)
            {
                System.out.println("Enter value from 1 to " + numRange + ":");
                validateInput = ValidateScanner.nextInt();
                if(numSet.contains(validateInput))
                {
                    break;
                }
            }
        }catch (java.util.InputMismatchException inputError){
            System.out.println("Error: Non-int value entered. Terminating..");
            System.exit(1);
        }
        return validateInput;
    }
}
