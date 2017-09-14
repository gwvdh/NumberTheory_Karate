import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Stefan on 7-9-2017.
 */

public class IOHandler {

    public boolean HasNext;
    public String operation;
    public Number x;
    public Number y;
    int base;

    File file;
    Scanner sc;


    public IOHandler() {

        HasNext = true;
        //TODO: rename to "input.txt"
        file = new File("example.txt");

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
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
    /** <p> determine word size, store word size in variable
     *  read words individually
     *  get the base 10 representation
     *  add number to array
     *  create new Number with that word size and the array
     *  </p>
     *
     */
    Number initNumber (Number a) {

        boolean negative;
        int[] numArray;
        char charWord;

        //check whether the word is negative
        int i = 0;
        int correction = 0;

        String num = sc.next();

        if(num.charAt(i) == '-') {

            numArray = new int[num.length() - 1];
            negative = true;
            correction = 1;
            i++;

        } else {

            numArray = new int[num.length()];
            negative = false;

        }

        //fill the digits in the array
        for(; i < numArray.length; i++) {

            charWord = num.charAt(i);
            String word = Character.toString(charWord);
            numArray[i - correction] = determineNumber(word);
        }


        //use the array to create a new number
        a = new Number(numArray, base, negative);
        return a;

    }

    /**
     * @param  p word
     * @return n == integer representation of p
     */
    int determineNumber(String p) {

        int n = Integer.parseInt(p, base);
        return n;

    }

    String getString(Number a, int index) {
        return Integer.toString(a.getDigit(index), a.getBase());
    }

    /**
     * prints a in normal Number representation in System.out
     * @param a Number
     *
     */
    public void print(Number a) {
        String out = "[answer] ";

        //add the minus sign if negative
        if (a.getNegative()) {
            out += "-";
        }

        //continuously get the string of some number at index i and append
        for (int i = 0; i < a.getLength(); i++) {
            out += getString(a, i);
        }

        System.out.println(out);
    }
}