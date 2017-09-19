import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalcTest {
    Calc c;

    @BeforeEach
    void setUp() {
        c = new Calc();
    }

    @Test
    void add() {
        Number p0 = new Number(new int[] {0}, 5, false);
        Number p1 = new Number(new int[] {1}, 5, false);
        Number p04 = new Number(new int[] {0,4}, 5, false);
        Number p0512494 = new Number(new int[] {0,5,1,2,4,9,4}, 5, false);

        Number n0 = new Number(new int[] {0}, 5, true);
        Number n1 = new Number(new int[] {1}, 5, true);
        Number n04 = new Number(new int[] {0,4}, 5, true);
        Number n0512494 = new Number(new int[] {0,5,1,2,4,9,4}, 5, true);

        // Add positive numbers
        addTest(p0, n0, n0);
        addTest(p1, n1, n0);
        addTest(p1, p1, new Number(new int[] {2}, 5, false));
        addTest(p04, p1, new Number(new int[] {1,4}, 5, false));

        // Add negative number to positive number, positive result
        addTest(p04, n1, new Number(new int[] {4,3}, 5, false));
        addTest(n1, p04, new Number(new int[] {4,3}, 5, false));

        // Add negative number to positive number, negative result.
        addTest(p1, n04, new Number(new int[] {4,3}, 5, true));
        addTest(p1, n0512494, new Number(new int[] {4,4,1,2,4,9,4}, 5, true));
        addTest(p04, n0512494, new Number(new int[] {0,1,1,2,4,9,4}, 5, true));

        // Add negative numbers to positive numbers, zero result.
        addTest(p0512494, n0512494, n0);
        addTest(p0512494, n0512494, p0);
        addTest(n0512494, p0512494, n0);
        addTest(n0512494, p0512494, p0);

        // Add negative number to negative number, negative result.
        addTest(n1, n1, new Number(new int[] {2}, 5, true));
        addTest(n04, n1, new Number(new int[] {1,4}, 5, true));
    }

    void addTest(Number n1, Number n2, Number expected) {
        Number result = c.add(n1, n2);

        assertEquals(result.compareTo(expected), 0);
    }

}