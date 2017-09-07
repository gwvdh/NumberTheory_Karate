public class Number {
    int base;
    boolean negative;

    public Number(String value, int base, boolean negative) {
        // @TODO add to local file representation
        this.base = base;
        this.negative = negative;
    }

    public int getLength() {
        return 0; // @TODO implement
    }

    public boolean getNegative() {
        return negative;
    }

    public int getDigit(int index) {
        return 0; // @TODO implement
    }
}
