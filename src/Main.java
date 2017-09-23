/**
 * Created by s152124 on 7-9-2017.
 */
public class Main {

    //Calc op = new Calc();
    IOHandler IO = new IOHandler();

    void run() {

        int count = 1;
        while(IO.HasNext) {
            Number c = null;
            IO.readNext();
            // IO.x is a number, same for IO.y
            switch (IO.operation) {
                case "[add]":
                    // TODO: set c to result of add(IO.x, IO.y)
                    break;
                case "[subtractUnsigned]":
                    // TODO: set c to result of sub(IO.x, IO.y)
                    break;
                case "[multiply]":
                    // TODO: set c to result of mul(IO.x, IO.y)
                    break;
                case "[karatsuba]":
                    // TODO: set c to result of kar(IO.x, IO.y)
                    break;
                default:
                    break;

            }

            IO.print(IO.x, count);
            count++;
        }
        IO.close();
    }

    public static void main(String [] args) {

        Main program = new Main();
        program.run();


    }


}
