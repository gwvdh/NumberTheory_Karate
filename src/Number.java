public class Number implements Comparable<Number> {
    int base;
    boolean negative;
    int[] num;

    public Number(int[] value, int base, boolean negative) {
        // @TODO add to local file representation
        this.base = base;
        this.negative = negative;
        this.num = value.clone();
    }

    public int getLength() {
        return num.length;

    }

    public boolean getNegative() {
        return negative;
    }

    public int getDigit(int index) {
        return num[index];
    }

    @Override
    public int compareTo(Number o) {
        int otherLength = o.getLength();
        int thisLength = getLength();

        int maxLength = Math.max(otherLength, thisLength);

        for (int i = maxLength - 1; i >= 0; i--) {
            int difference = 0;

            // Within the length of a normal digit.
            if (i < otherLength) {
                if (o.getNegative()) {
                    difference -= o.getDigit(i);
                } else {
                    difference += o.getDigit(i);
                }
            }

            if (i < thisLength) {
                if (getNegative()) {
                    difference -= getDigit(i);
                } else {
                    difference += getDigit(i);
                }
            }

            if (difference != 0) {
                return difference;
            } // else both digits were equal, proceed to check the next digit.
        }

        // If every digit on each index where equal, return
        return 0;
    }

}
