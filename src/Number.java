public class Number implements Comparable<Number> {
    int base;
    boolean negative;
    int[] num;

    public Number(int[] value, int base, boolean negative) {
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

        boolean thisNegative = getNegative();
        boolean otherNegative = o.getNegative();

        int maxLength = Math.max(otherLength, thisLength);

        // Loop through all digits from the most import digit to the least important digit.
        for (int i = maxLength - 1; i >= 0; i--) {
            int difference = 0;

            // Check if the index is within the length of this number.
            if (i < thisLength) {
                if (thisNegative) {
                    difference -= getDigit(i);
                } else {
                    difference += getDigit(i);
                }
            }

            // Check if the index is within the length of the other number.
            if (i < otherLength) {
                if (otherNegative) {
                    difference += o.getDigit(i);
                } else {
                    difference -= o.getDigit(i);
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
