public class Number {
    int base;
    boolean negative;
    int[] num;

    public Number(int[] value, int base, boolean negative, int arrayLength) {
        // @TODO add to local file representation
        this.base = base;
        this.negative = negative;
        this.num = new int[arrayLength];
    }

    public int getLength() {
        return 0; // @TODO implement
    }

    public boolean getNegative() {
        return negative;
    }

    public int getDigit(int index) {
        return num[index]; // @TODO implement
    }
}
