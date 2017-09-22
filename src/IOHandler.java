import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Stefan on 7-9-2017.
 */

public class IOHandler {

    public boolean HasNext;
    public String operation;
    public Number x;
    public Number y;

    private int base;
    private File file;
    private File output;
    private FileOutputStream out;
    private Scanner sc;


    public IOHandler() {

        HasNext = true;
        try {
            file = new File("example.txt");
            output = new File("output.txt");

            //check existence, overwrite if it does exist
            if(!output.exists()) {
                output.createNewFile();
            }
            out =  new FileOutputStream(output);


            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
    /**
     * Reads the next input block from the input file
     */
    public void readNext() {

        base = 1;
        boolean gotNumbers = false;

        //loop until there is no more input or until the numbers are found
        while(sc.hasNext() && !gotNumbers) {

            String s = sc.next();
            switch (s) {
                case "[radix]":
                    base = sc.nextInt();
                    System.out.println(base);
                    break;
                case "[add]":
                case "[subtractUnsigned]":
                case "[multiply]":
                case "[karatsuba]":
                    operation = s;
                    break;
                case "[x]":
                    x = initNumber(x);
                    break;
                case "[y]":
                    y = initNumber(y);
                    gotNumbers = true;
                    break;
                case "[answer]":
                    System.out.println("comparing answers");
                    break;
                default:
                    sc.nextLine();
                    break;
            }

        }

        HasNext = sc.hasNext();

    }

    Number initNumber (Number a) {

        boolean negative;
        int[] numArray;
        char charWord;

        //check whether the word is negative

        int end = 0;
        String num = sc.next();

        if (num.charAt(0) == '-') {
            //negative
            negative = true;
            numArray = new int[num.length() - 1];
            end = 1;

        } else {
            //not negative
            negative = false;
            numArray = new int[num.length()];
        }
        //account for minus sign
        int i = numArray.length - 1 + end;
        int c = 0;
        //fill the digits in the array
        for(; i >= end; i--) {

            charWord = num.charAt(i);
            String word = Character.toString(charWord);
            numArray[c] = determineNumber(word);
            c++;
        }


        //use the array to create a new number
        a = new Number(numArray, base, negative, 0, 0);
        return a;

    }

    /**
     * @param  p word
     * @return Integer representation of {@code p}
     */
    int determineNumber(String p) {

        return Integer.parseInt(p, base);

    }

    /**
     * prints a in normal Number representation in the output file
     * @param a Number
     * @param count the number representing which calculation was performed
     */
    public void print(Number a, int count) {
        StringBuilder outString = new StringBuilder();

        //create the string
        outString.append("[input ");
        outString.append(count);
        outString.append("]");
        outString.append("\r\n");

        outString.append("[radix] ");
        outString.append(this.base);
        outString.append("\r\n");

        outString.append(this.operation);
        outString.append("\r\n");

        if (this.operation.equals("[multiply]") || this.operation.equals("[karatsuba]")) {
            outString.append("[additions] ");
            outString.append(a.getAddCount());
            outString.append("\r\n");

            outString.append("[multiplications] ");
            outString.append(a.getMultiplyCount());
            outString.append("\r\n");

        }
        outString.append("[answer] ");
        outString.append(a.toString());
        outString.append("\r\n");
        outString.append("\r\n");
        //try to write
        try {
            out.write(outString.toString().getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    //flush and close the output stream
    public void close () {
        try {
            if(out != null) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}