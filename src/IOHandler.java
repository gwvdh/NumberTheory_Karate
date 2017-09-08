import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Stefan on 7-9-2017.
 */
public class IOHandler {

    File file;
    Scanner sc;
    Number x;
    Number y;
    int base;

    public IOHandler(Number a, Number b) {


        this.x = a;
        this.y = b;
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

        //loop until there is no more input left
        while(sc.hasNext() && !gotNumbers) {

            String s = sc.next();
            switch (s) {
                case "[radix]":
                    base = sc.nextInt();
                    System.out.println(base);
                    break;
                case "[add]":
                    //notify
                    System.out.println(s + " = add");
                    break;
                case "[subtract]":
                    System.out.println(s + " = subtract");
                    break;
                case "[multiply]":
                    System.out.println(s + " = multiply");
                    break;
                case "[karatsuba]":
                    System.out.println(s + " = karatsuba");
                    break;
                case "[x]":
                    System.out.println("got x");
                    break;
                case "[y]":
                    System.out.println("got y and x");
                    gotNumbers = true;
                    break;
                case "[answer]":
                    break;
                default:
                    sc.nextLine();
                    break;
            }

            if (gotNumbers) {


                //TODO: perform the specified operation on the given integers


                //gotNumbers = false;
            }
        }




    }
    /*  determine word size, store word size in variable
    *  read words individually
    *  get the base 10 representation
    *  add number to array
    *  create new Number with that word size and the array
    *
     */
    void initNumber (Number a) {



    }
    /**
     * @param p word
     * @post return p in base 10 representation
     */
    int determineNumber(String p) {

        int n = Integer.parseInt(p, base);
        return n;

    }


}
