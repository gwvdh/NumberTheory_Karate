import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* Authors:
 * Adriaan Knapen - 0963981
 *
 */

class NumberTest {
    @org.junit.jupiter.api.Test
    void getLength() {
        testLength(new Number(new int[] {0}, 5, false), 1);
        testLength(new Number(new int[] {0,1}, 5, false), 2);
        testLength(new Number(new int[] {0,1,2}, 5, false), 3);
        testLength(new Number(new int[] {0,1,2,3}, 5, false), 4);
        testLength(new Number(new int[] {1,2,3,0}, 5, false), 4);
    }

    void testLength(Number n, int expected) {
        assertEquals(n.getLength(), expected);
    }

    @org.junit.jupiter.api.Test
    void getNegative() {
        testNegative(new Number(new int[] {0}, 5, false), false);
        testNegative(new Number(new int[] {0,1}, 5, false), false);
        testNegative(new Number(new int[] {0,1,2}, 5, false), false);
        testNegative(new Number(new int[] {0,1,2,3}, 5, false), false);
        testNegative(new Number(new int[] {1,2,3,0}, 5, false), false);

        testNegative(new Number(new int[] {0}, 5, true), true);
        testNegative(new Number(new int[] {0,1}, 5, true), true);
        testNegative(new Number(new int[] {0,1,2}, 5, true), true);
        testNegative(new Number(new int[] {0,1,2,3}, 5, true), true);
        testNegative(new Number(new int[] {1,2,3,0}, 5, true), true);
    }

    void testNegative(Number n, boolean expected) {
        assertEquals(n.getNegative(), expected);
    }

    @org.junit.jupiter.api.Test
    void getDigit() {
        testDigit(new Number(new int[] {0}, 5, false), 0, 0);
        testDigit(new Number(new int[] {0,1}, 5, true), 1, 1);
        testDigit(new Number(new int[] {0,1,2}, 5, false), 2, 2);
        testDigit(new Number(new int[] {0,1,2,3}, 5, true), 3, 3);
        testDigit(new Number(new int[] {1,2,3,0}, 5, false), 3, 0);
    }

    void testDigit(Number n, int index, int expected) {
        assertEquals(n.getDigit(index), expected);
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
        Number p0 = new Number(new int[] {0}, 5, false);
        Number p1 = new Number(new int[] {1}, 5, false);
        Number p04 = new Number(new int[] {0,4}, 5, false);
        Number p0512494 = new Number(new int[] {0,5,1,2,4,9,4}, 5, false);

        Number n0 = new Number(new int[] {0}, 5, true);
        Number n1 = new Number(new int[] {1}, 5, true);
        Number n04 = new Number(new int[] {0,4}, 5, true);
        Number n0512494 = new Number(new int[] {0,5,1,2,4,9,4}, 5, true);

        // Test equivalence of both positive and negative version of zero.
        compareTest(p0, p0, 0);
        compareTest(p0, n0, 0);
        compareTest(n0, p0, 0);
        compareTest(n0, n0, 0);

        // Test equivalence of two large numbers.
        compareTest(p0512494, p0512494, 0);
        compareTest(n0512494, n0512494, 0);

        // Test comparison of positive numbers


        // Test comparison of negative numbers

        // Test comparison of mixed positive and negative numbers
        compareTest(p0512494, n0512494, 1);
        compareTest(n0512494, p0512494, -1);
        compareTest(p0512494, p0, 1);
        compareTest(p0512494, n0, 1);
        compareTest(n0512494, p0, -1);
        compareTest(n0512494, n0, -1);
        compareTest(p0512494, n04, 1);
        compareTest(p0512494, p04, 1);
        compareTest(n0512494, n04, -1);
        compareTest(n0512494, p04, -1);
        compareTest(n04, n0512494, 1);
    }

    void compareTest(Number n1, Number n2, int expected) {
        int actual = n1.compareTo(n2);

        if (expected > 0) {
            assertTrue(actual > 0, "Compare test expected a positive number, got " + actual);
        } else if (expected < 0) {
            assertTrue(actual < 0, "Compare test expected a negative number, got " + actual);
        } else {
            assertTrue(actual == 0, "Compare test expected 0, got " + actual);
        }
    }


    @Test
    void testToString() {
        // Test positive numbers
        toStringTest(new Number(new int[] {0}, 5, false), "0");
        toStringTest(new Number(new int[] {1}, 5, false), "1");
        toStringTest(new Number(new int[] {0,4}, 5, false), "40");
        toStringTest(new Number(new int[] {0,5,1,2,4,9,4}, 5, false), "4942150");
        toStringTest(new Number(new int[] {11,15}, 16, false), "fb"); // Hexadecimal

        // Test negative numbers
        toStringTest(new Number(new int[] {0}, 5, true), "-0");
        toStringTest(new Number(new int[] {1}, 5, true), "-1");
        toStringTest(new Number(new int[] {0,4}, 5, true), "-40");
        toStringTest(new Number(new int[] {0,5,1,2,4,9,4}, 5, true), "-4942150");
        toStringTest(new Number(new int[] {11,15}, 16, true), "-fb"); // Hexadecimal
    }

    void toStringTest(Number n, String expected) {
        assertEquals(n.toString(), expected);
    }
}