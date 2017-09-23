import com.sun.org.apache.xpath.internal.operations.Bool;

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
    public Number z;

    private int base;
    private File file;
    private File output;
    private FileOutputStream out;
    private Scanner sc;
    private Boolean EnableCheck;
    private String ScannerString;

    public IOHandler(boolean CheckAnswer) {

        HasNext = true;
        try {
            file = new File("input.txt");
            output = new File("output.txt");

            //check existence, overwrite if it does exist
            if(!output.exists()) {
                output.createNewFile();
            }
            out =  new FileOutputStream(output);


            sc = new Scanner(file);
        } catch (FileNotFoundException e) {

            System.out.println("input.txt was not found, aborting");
            HasNext = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
        EnableCheck = CheckAnswer;
        ScannerString = sc.next();
    }
    /**
     * Reads the next input block from the input file
     */
    public void readNext() {

        base = 1;

        //loop until there is no more input or until the numbers are found

        while(sc.hasNext()) {

            switch (ScannerString) {
                case "[radix]":
                    base = sc.nextInt();
                    //System.out.println(base);
                    break;
                case "[add]":
                case "[subtract]":
                case "[multiply]":
                case "[karatsuba]":
                    operation = ScannerString;
                    break;
                case "[x]":
                    x = initNumber(x);
                    break;
                case "[y]":
                    y = initNumber(y);
                    break;
                case "[answer]":
                    if(EnableCheck) {
                        z = initNumber(z);
                    }
                    break;
                default:
                    sc.nextLine();
                    break;
            }
            //look until another [radix] header has been found - the start of the next input
            //break if it is found
            //if not found:
            //set HasNext to false so that main will stop probing the IOHandler for input
            //break the loop as there will be no more input
            if (sc.hasNext()) {
                ScannerString = sc.next();

                if (ScannerString.equals("[radix]")) {
                    break;
                }
            } else {
                HasNext = false;
                break;
            }

        }



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

        //fill the digits in the array such that the end result is [a_{k}a_{k-1}...a_0] instead of [a_{0}a_{1}...a_k]
        for(; i >= end; i--) {

            charWord = num.charAt(i);
            String word = Character.toString(charWord);
            numArray[c] = determineNumber(word);
            c++;
        }


        //use the array to create a new number
        a = new Number(numArray, base, negative);
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