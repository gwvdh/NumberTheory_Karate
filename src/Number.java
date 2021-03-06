/**
 * Authors:
 * Adriaan Knapen - 0963981
 *
 *
 * Represents large signed integer numbers.
 */
public class Number implements Comparable<Number> {
    private int base;
    private boolean negative;
    private int[] num;
    private int multiplyCount;
    private int addCount;

    /**
     * Creates a new {@code Number} object.
     * @pre {@code {\forall i; value.has(i); value[i] >= 0} && value != null && base > 0}
     * @param value The number represented as an array of integers, with index 0 representing the least important digit
     *              and becoming increasingly important with increasing index. Every number in this array should be >0.
     * @param base The base (radix) of the number, should be positive and non zero.
     * @param negative True if the number should be negative, false otherwise.
     */
    public Number(int[] value, int base, boolean negative) {
        // Check if value is not null
        if (value == null) {
            throw new IllegalArgumentException("Value may not be null");
        }

        // Check if all numbers in value are at least positive.
        for(int i = value.length - 1; i >= 0; i--) {
            if (value[i] < 0) {
                throw new IllegalArgumentException("The value of the integer on index " + i +
                        " should be larger or equal to 0, is " + value[i]);
            }
        }

        // Check if the base is positive.
        if (base <= 0) {
            throw new IllegalArgumentException("Base should be positive and non zero, is " + base);
        }

        this.addCount = 0; //Initial
        this.multiplyCount = 0; //Initial
        this.base = base;
        this.negative = negative;
        this.num = value.clone();
        //this.multiplyCount = mult;
        //this.addCount = add;
    }

    /**
     * Gives the amount of digits the number consists of, including leading zeros.
     * @return The amount of digits the number consists of, including leading zeros.
     */
    public int getLength() {
        return num.length;
    }

    /**
     * Gives if the number is positive or not.
     * @return True if the number is negative, false if not.
     */
    public boolean getNegative() {
        return negative;
    }

    /**
     * Getter for the base of this number.
     * @return The base of this number.
     */
    public int getBase() {
        return base;
    }

    /**
     * Gives access to one of the digits of the number.
     * @param index The index of the number to be retrieved. {@code 0} indicates the least important digit, {@code getLength()} the
     *              most important digit.
     * @return The digit present at the digit indicated by {@code index}.
     */
    public int getDigit(int index) {
        if(index < num.length){
            return num[index];
        } else{
            return 0;
        }

    }
    /**
     * Getter for the number of additions was performed to get this number.
     * @return The number of additions was performed to get this number.
     */
    public int getAddCount() {
        return addCount;
    }

    /**
     * Getter for the number of multiplications was performed to get this number.
     * @return The number of multiplications was performed to get this number.
     */
    public int getMultiplyCount() {
        return multiplyCount;
    }

    /**
     * Setter of  many additions were needed to get this number after multiply or karatsuba.
     * @param k how many additions were needed to get this number after multiply or karatsuba.
     */
    public void setAddCount(int k) { addCount = k;}

    /**
     * Setter of  many multiplications were needed to get this number after multiply or karatsuba.
     * @param k how many multiplications were needed to get this number after multiply or karatsuba.
     */
    public void setMultiplyCount(int k) { multiplyCount = k;}

    /**
     * Generates an inversed sign version of the current object.
     * @return Duplicate of the current object, but with changed sign.
     */
    public Number getSwitchedSign() {
        return new Number(num, base, !getNegative());
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

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        // Add the minus sign, if required.
        if (negative) {
            output.append("-");
        }

        // Add all the digits.
        for (int i = num.length - 1; i >= 0; i--) {
            if (num[i] < 10) {
                output.append(num[i]);
            } else {
                output.append(Integer.toString(num[i], getBase()));
            }
        }

        return output.toString();
    }
}