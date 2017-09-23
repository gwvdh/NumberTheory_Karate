/**
 * Author:
 *
 * Stefan Tanja - 0955022
 *
 * Main sequence of instructions
 */
public class Main {
    Calc op;
    IOHandler IO;

    public Main(boolean checkAnswers) {
        op = new Calc();
        IO = new IOHandler(checkAnswers);
    }


    void run() {
        System.out.println("Starting...");
        Number c = null;
        int count = 1;
        while(IO.HasNext) {

            IO.readNext();

            switch (IO.operation) {
                case "[add]":
                    c = op.add(IO.getX(), IO.getY());
                    break;
                case "[subtract]":
                    c = op.subtract(IO.getX(), IO.getY());
                    break;
                case "[multiply]":
                    c = op.multiply(IO.getX(), IO.getY());
                    break;
                case "[karatsuba]":
                    c = op.karatsuba(IO.getX(), IO.getY());
                    break;
                default:
                    break;

            }
            //test for correctness with a known answer
            if(IO.getZ() != null) {
                System.out.println(c.compareTo(IO.getZ()) == 0);
            }

            //keep track of which input it was
            IO.print(c, count);
            count++;
        }
        IO.close();
        System.out.println("Finished");
    }

    public static void main(String [] args) {
        Main program;


        if (args.length == 0) {

            program = new Main(false);
            program.run();

        } else if (args[0].equals("false")) {

            program = new Main(false);
            program.run();

        } else if (args[0].equals("true")) {

            program = new Main(true);
            program.run();

        } else {
            System.out.println("wrong input parameters, either use false or true");
        }




    }


}