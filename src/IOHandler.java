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

    File file;
    Scanner sc;
    int base;

    public IOHandler() {

        file = new File("example.txt");

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    /*
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
                    //notify
                    operation = s;
                    System.out.println(s + " = add");
                    break;
                case "[subtract]":
                    operation = s;
                    System.out.println(s + " = subtract");
                    break;
                case "[multiply]":
                    operation = s;
                    System.out.println(s + " = multiply");
                    break;
                case "[karatsuba]":
                    operation = s;
                    System.out.println(s + " = karatsuba");
                    break;
                case "[x]":
                    System.out.println("got x");
                    x = initNumber(x);
                    break;
                case "[y]":
                    System.out.println("got y and x");
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
    /*  determine word size, store word size in variable
    *  read words individually
    *  get the base 10 representation
    *  add number to array
    *  create new Number with that word size and the array
    *
     */
    Number initNumber (Number a) {

        a = new Number("", base, false, 0);
        return a;


    }
    /**
     * @param p word
     * @post return p in base 10 representation
     */
    int determineNumber(String p) {

        int n = Integer.parseInt(p, base);
        return n;

    }
    public void print(Number a) {
        System.out.println("print");
    }
}
