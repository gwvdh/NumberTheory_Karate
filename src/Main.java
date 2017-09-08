/**
 * Created by s152124 on 7-9-2017.
 */
public class Main {

    Number x;
    Number y;
    IOHandler IO = new IOHandler(x, y);

    void run() {

        IO.readNext();
        IO.readNext();
        IO.readNext();
        IO.readNext();

    }

    public static void main(String [] args) {

        Main program = new Main();
        program.run();

    }


}
