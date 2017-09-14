import static org.junit.jupiter.api.Assertions.*;

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
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
    }

}