/**
 * Created by s152124 on 7-9-2017.
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
            // IO.x is a number, same for IO.y
            switch (IO.operation) {
                case "[add]":
                   c = op.add(IO.x, IO.y);
                    break;
                case "[subtract]":
                    c = op.subtract(IO.x, IO.y);
                    break;
                case "[multiply]":
                    c = op.multiply(IO.x, IO.y);
                    break;
                case "[karatsuba]":
                    c = op.karatsuba(IO.x, IO.y);
                    break;
                default:
                    break;

            }
            //test correctness
            if(IO.z != null) {
                System.out.println(c.compareTo(IO.z) == 0);
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
