public class Calc {

    public Number add(Number n1, Number n2) {
        if (n1.getNegative() == n2.getNegative()) {
            return addUnsigned(n1, n2);
        } else if (n1.getNegative()) { // && !n2.getNegative()
            return subtractUnsignedExtended(n2, n1.getSwitchedSign());
        } else { // n2.getNegative() && !n1.getNegative()
            return subtractUnsignedExtended(n1, n2.getSwitchedSign());
        }
    }

    public Number subtract(Number n1, Number n2) {
        if (n1.getNegative() == n2.getNegative()) {
            return subtractUnsignedExtended(n1, n2);
        } else if (n1.getNegative()) { // && !n2.getNegative()
            // n1 - n2 == -((-n1) + n2)
            // Since n1 is negative will -n1 be positive. In addition to the fact that n1 is positive can
            // unsigned addition be applied.
            return addUnsigned(n1.getSwitchedSign(), n2).getSwitchedSign();
        } else { // n2.getNegative() && !n1.getNegative()
            // n1 - n2 == n1 + (-n2)
            // Since n2 is negative will -n2 be positive. In addition to the fact that n1 is positive can
            // unsigned addition be applied.
            return addUnsigned(n1, n2.getSwitchedSign());
        }
    }

    /**
     * Adds two numbers to each other according to algorithm 1.1 of the lecture notes
     * @param n1    The first number
     * @param n2    The second number
     * @pre {@code n1 != null && n2 != null && !(n1.getNegative() ^ n2.getNegative())}    // Only add two of the same negative sign
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1+n2} without leading zeros.
     */
    private Number addUnsigned(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if(n1==null || n2==null){   // Preconditions
            throw new NullPointerException("Can not add to null");
        } else if(n1.getNegative() != n2.getNegative()){
            throw new IllegalArgumentException("Different sign");
        }

        int base = n1.getBase();
        int length = Math.max(n1.getLength(), n2.getLength()); // The maximum length of the two numbers.
        int[] result = new int[length + 1]; // Array to write the result to, has one extra digit to prevent overflow.

        int carry = 0; // Keeps track of the carry.
        for(int i = 0; i < length; i++){
            int res = carry;

            // Add the value of each digit, if it exists
            if (i < n1.getLength()) {
                res += n1.getDigit(i);
            }
            if (i < n2.getLength()) {
                res += n2.getDigit(i);
            }

            // res = n1.getDigit(i) + n2.getDigit(i) + carry

            if(res >= base){
                res = res - base;
                carry = 1;
            } else {
                carry = 0;
            }
            result[i] = res;
        }

        // Remove leading zeros, first detect the most significant digit which isn't zero.
        int i;
        for (i = result.length - 1; i > 0; i--) {
            if (result[i] != 0) {
                break;
            }
        }

        // Copy all non-leading zero digits.
        int[] endResult = new int[i + 1];
        System.arraycopy(result, 0, endResult, 0, endResult.length);

        // Create a new number object with the results and return this.
        return new Number(endResult, base, n1.getNegative());
    }

    /**
     * Subtracts two numbers, extends the implementation of algorithm 1.2 of the lecture notes with support for
     * larger {@code n2} than {@code n1}.
     * @param n1    The first number
     * @param n2    The second number
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1-n2}
     */
    private Number subtractUnsignedExtended(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if (n1 == null || n2 == null) {
            throw new NullPointerException("Numbers may not be null");
        } else if (n1.getNegative()^n2.getNegative()) {
            throw new IllegalArgumentException("Both numbers of subtractUnsigned should have the same sign.");
        }

        // @TODO CORRECT
        // Check if abs(n2) > abs(n1), since subtractUnsigned does not support this the operation n1 - n2 is
        // rewritten to the equivalent operation -(n2 - n1) which will satisfy the pre condition of subtractUnsigned.
        if (n1.getNegative() == n1.compareTo(n2) <= 0) {
            return subtractUnsigned(n1, n2);
        } else {
            Number invertedResult = subtractUnsigned(n2, n1); // Compute n2 - n1

            return invertedResult.getSwitchedSign(); // Give the resulting -(n2 - n1).
        }
    }

    /**
     * Subtracts two numbers from each other according to algorithm 1.2 of the lecture notes
     * @param n1    The first number
     * @param n2    The second number
     * @pre {@code n1 != null && n2 != null && !(n1.getNegative() ^ n2.getNegative()) && abs(n1)>=abs(n2)}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1-n2}
     *
     */
    private Number subtractUnsigned(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if (n1 == null || n2 == null) {
            throw new NullPointerException("Numbers may not be null");
        } else if (n1.getNegative()^n2.getNegative()) {
            throw new IllegalArgumentException("Both numbers of subtractUnsigned should have the same sign.");
        } else if (n1.getNegative() == n1.compareTo(n2) > 0 && n1.compareTo(n2) != 0) { // n1.getNegative() <=> n1 > n2
            throw new IllegalArgumentException("The absolute values of the first number of subtractUnsigned should be larger than the second.");
        }

        int base = n1.getBase();
        int length = n1.getLength();
        int carry = 0;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            // Assume the digit of n2 to be zero if the current index exceeds n2s length.
            if (i < n2.getLength()) {
                result[i] = n1.getDigit(i) - n2.getDigit(i) - carry;
            } else {
                result[i] = n1.getDigit(i) - carry;
            }

            // Check if a carry (borrow) is required.
            if ( result[i] < 0){
                result[i] = result[i] + base;
                carry = 1;
            } else {
                carry = 0;
            }
        }

        // Remove leading zeros, first detect the most significant digit which isn't zero.
        int i;
        for (i = result.length - 1; i > 0; i--) {
            if (result[i] != 0) {
                break;
            }
        }

        // Copy all non-leading zero digits.
        int[] endResult = new int[i + 1];
        System.arraycopy(result, 0, endResult, 0, endResult.length);

        // Create a new number object with the results and return this.
        return new Number(endResult, base, n1.getNegative());
    }

    /**
     * Multiplies two numbers to each other according to algorithm 1.3 of the lecture notes (Naive multiplication)
     * @param n1    The first number
     * @param n2    The second number
     * @pre {@code n1 != null && n2 != null}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1*n2}
     *
     */
    public Number multiply(Number n1, Number n2) throws NullPointerException{
        if(n1==null || n2==null){   // Preconditions
            throw new NullPointerException("Can not add to null");
        }
        int m = n1.getLength();
        int n = n2.getLength();

        int[] result = new int[n1.getLength()+n2.getLength()-1]; // In algorithm denoted as z
        for(int i : result){ //Set all elements in the result array to 0
            result[i] = 0;
        }
        int base = n1.getBase();
        int carry = 0;
        for(int i=0; i<m; i++){ //Get elements from one number, multiply with numbers from the other
            for(int j=0; j<n; j++){
                int t = result[i+j]+n1.getDigit(i) * n2.getDigit(j) + carry;
                carry = t/base;
                result[i+j] = t-carry*base;
            }
            result[i+n] = carry;
        }

        // Remove leading zeros, first detect the most significant digit which isn't zero.
        int i;
        for (i = result.length - 1; i > 0; i--) {
            if (result[i] != 0) {
                break;
            }
        }

        // Copy all non-leading zero digits.
        int[] endResult = new int[i + 1];
        System.arraycopy(result, 0, endResult, 0, endResult.length);

        // Create a new number object with the results and return this.
        return new Number(endResult, base, n1.getNegative() == n2.getNegative());
    }

    /**
     * Multiplies two numbers to each other (Karatsuba)
     * @param n1    The first number
     * @param n2    The second number
     * @pre {@code n1 != null && n2 != null}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1*n2}
     *
     */
    public Number karatsuba(Number n1, Number n2) {
        int base = n1.getBase();
        int m = n1.getLength();
        int n = n2.getLength();

        //Split numbers if one is larger than 2
        int lengthOfSplit;
        if(m>=n){
            lengthOfSplit = m/2+m%2; //Is modulo allowed?
        } else {
            lengthOfSplit = n2.getLength()/2+n2.getLength()%2; //Is modulo allowed?
        }
        //Split numbers in half
        Number n1_lo = new Number(new int[lengthOfSplit], base, n1.getNegative()); //Does not work for odd length numbers
        Number n1_hi = new Number(new int[lengthOfSplit], base, n1.getNegative()); //Does not work for odd length numbers
        Number n2_lo = new Number(new int[lengthOfSplit], base, n2.getNegative()); //Does not work for odd length numbers
        Number n2_hi = new Number(new int[lengthOfSplit], base, n2.getNegative()); //Does not work for odd length numbers

        Number newLo = karatsuba(n1_lo,n2_lo);
        Number newHi = karatsuba(n1_hi,n2_hi);
        Number newMid = subtractUnsigned(subtractUnsigned(karatsuba(add(n1_lo,n1_hi),add(n2_lo,n2_hi)),newLo),newHi);
        Number result = new Number(new int[n], base, n1.getNegative() ^ n2.getNegative()); //change length

        return null; // @TODO Implement
    }

    // @TODO Implement other functions.
}
