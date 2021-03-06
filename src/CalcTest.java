import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* Authors:
 * Wessel van der Heijden - 0951686
 * Adriaan Knapen - 0963981
 *
 */

class CalcTest {
    Calc c;
    final Number p0 = new Number(new int[] {0}, 5, false);
    final Number p1 = new Number(new int[] {1}, 5, false);
    final Number p04 = new Number(new int[] {0,4}, 5, false);
    final Number p0512494 = new Number(new int[] {0,5,1,2,4,9,4}, 5, false);

    final Number n0 = new Number(new int[] {0}, 5, true);
    final Number n1 = new Number(new int[] {1}, 5, true);
    final Number n04 = new Number(new int[] {0,4}, 5, true);
    final Number n0512494 = new Number(new int[] {0,5,1,2,4,9,4}, 5, true);

    @BeforeEach
    void setUp() {
        c = new Calc();
    }

    @Test
    void add() {
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

    @Test
    void multiply() {
        Number p4444 = new Number(new int[] {4,4,4,4}, 5, false);

        Number n4444 = new Number(new int[] {4,4,4,4}, 5, true);

        // Multiply positive numbers
        multiplyTest(p0, n0, n0);
        multiplyTest(p1, n1, n1);
        multiplyTest(p1, n04, new Number(new int[] {0,4}, 5, true));
        multiplyTest(p04, p1, new Number(new int[] {0,4}, 5, false));

        // Multiply maximum numbers
        multiplyTest(n4444, p4444, new Number(new int[] {1,0,0,0,3,4,4,4}, 5, true));
        multiplyTest(n4444, n4444, new Number(new int[] {1,0,0,0,3,4,4,4}, 5, false));
    }

    void multiplyTest(Number n1, Number n2, Number expected) {
        Number result = c.multiply(n1,n2);

        assertEquals(result.compareTo(expected), 0);
    }

    @Test
    void karatsuba() {
        Number p4444 = new Number(new int[] {4,4,4,4}, 5, false);
        Number p3 = new Number(new int[] {3}, 10, false);
        Number p341 = new Number(new int[] {3,4,1}, 5, false);

        Number n4444 = new Number(new int[] {4,4,4,4}, 5, true);

        // Multiply positive numbers
        karatsubaTest(p0, n0, n0);
        karatsubaTest(p1, n1, n1);
        karatsubaTest(p1, n04, new Number(new int[] {0,4}, 5, true));
        karatsubaTest(p04, p1, new Number(new int[] {0,4}, 5, false));

        // Multiply maximum numbers
        karatsubaTest(p341,p341,new Number(new int[] {4,0,2,3,3}, 5, false));//143*143=33204
        karatsubaTest(n4444, p4444, new Number(new int[] {1,0,0,0,3,4,4,4}, 5, true));
        karatsubaTest(n4444, n4444, new Number(new int[] {1,0,0,0,3,4,4,4}, 5, false));
        karatsubaTest(p3, p3, new Number(new int[] {9}, 10, false));
    }
    void karatsubaTest(Number n1, Number n2, Number expected) {
        Number result = c.karatsuba(n1, n2);

        assertEquals(result.compareTo(expected), 0);
    }

    @Test
    void sub() {
        // sub positive numbers
        subTest(p0, n0, n0);
        subTest(p0, p0, n0);
        subTest(n0, n0, n0);
        subTest(n0, p0, n0);
        subTest(p1, p1, n0);
        subTest(p1, n1, new Number(new int[] {2}, 5, false));
        subTest(p04, n1, new Number(new int[] {1,4}, 5, false));

        // sub negative number to positive number, positive result
        subTest(p04, p1, new Number(new int[] {4,3}, 5, false));
        subTest(n1, n04, new Number(new int[] {4,3}, 5, false));

        // sub negative number to positive number, negative result.
        subTest(p1, p04, new Number(new int[] {4,3}, 5, true));
        subTest(p1, p0512494, new Number(new int[] {4,4,1,2,4,9,4}, 5, true));
        subTest(p04, p0512494, new Number(new int[] {0,1,1,2,4,9,4}, 5, true));

        // sub negative numbers to positive numbers, zero result.
        subTest(p0512494, p0512494, n0);
        subTest(p0512494, p0512494, p0);
        subTest(n0512494, n0512494, n0);
        subTest(n0512494, n0512494, p0);

        // sub negative number to negative number, negative result.
        subTest(n1, p1, new Number(new int[] {2}, 5, true));
        subTest(n04, p1, new Number(new int[] {1,4}, 5, true));
    }

    void subTest(Number n1, Number n2, Number expected) {
        Number result = c.subtract(n1, n2);

        assertEquals(result.compareTo(expected), 0);
    }
}