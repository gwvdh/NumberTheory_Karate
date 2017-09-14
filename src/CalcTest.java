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

        addTest(p0, n0, n0);
        addTest(p1, n1, n0);
        addTest(p1, p1, new Number(new int[] {2}, 5, false));
        addTest(p04, p1, new Number(new int[] {1,4}, 5, false));
        addTest(p04, n1, new Number(new int[] {4,3}, 5, false));

    }

    void addTest(Number n1, Number n2, Number expected) {
        Number result = c.add(n1, n2);

        assertEquals(result.compareTo(expected), 0);
    }

}