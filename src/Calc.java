public class Calc {

    /**
     * Adds the value of two number objects.
     * @param n1    The first number
     * @param n2    The second number
     * @pre     {@code n1 != null && n2 != null && n1.base == n2.base}
     * @return Number numberResult where {@code numberResult = n1 + n2} without leading zeros.
     */
    public Number add(Number n1, Number n2) {
        // Assert preconditions
        if(n1==null || n2==null){
            throw new NullPointerException("Can not add to null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        }

        // Delegate the addition of signed numbers to implementations on unsigned numbers.
        if (n1.getNegative() == n2.getNegative()) {
            return addUnsigned(n1, n2);
        } else if (n1.getNegative()) { // && !n2.getNegative()
            return subtractUnsignedExtended(n2, n1.getSwitchedSign());
        } else { // n2.getNegative() && !n1.getNegative()
            return subtractUnsignedExtended(n1, n2.getSwitchedSign());
        }
    }

    /**
     * Subtracts the value of two number objects.
     * @param n1    The first number
     * @param n2    The second number
     * @pre     {@code n1 != null && n2 != null && n1.base == n2.base}
     * @return Number numberResult where {@code numberResult = n1 - n2} without leading zeros.
     */
    public Number subtract(Number n1, Number n2) {
        // Assert preconditions
        if(n1==null || n2==null){
            throw new NullPointerException("Can not add to null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        }

        // Delegate the addition of signed numbers to implementations on unsigned numbers.
        if (n1.getNegative() == n2.getNegative()) {
            return subtractUnsignedExtended(n1, n2);
        } else if (n1.getNegative()) { // && !n2.getNegative()
            // n1 - n2 <=> -((-n1) + n2)
            // Since n1 is negative will -n1 be positive. In addition to the fact that n1 is positive can
            // unsigned addition be applied.
            return addUnsigned(n1.getSwitchedSign(), n2).getSwitchedSign();
        } else { // n2.getNegative() && !n1.getNegative()
            // n1 - n2 <=> n1 + (-n2)
            // Since n2 is negative will -n2 be positive. In addition to the fact that n1 is positive can
            // unsigned addition be applied.
            return addUnsigned(n1, n2.getSwitchedSign());
        }
    }

    /**
     * Adds two numbers to each other according to algorithm 1.1 of the lecture notes
     * @param n1    The first number
     * @param n2    The second number
     * @pre {@code n1 != null && n2 != null && !(n1.getNegative() ^ n2.getNegative()) && n1.base == n2.base}    // Only add two of the same negative sign
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1+  n2} without leading zeros.
     */
    private Number addUnsigned(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        // Assert preconditions
        if(n1==null || n2==null){
            throw new NullPointerException("Can not add to null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        } else if(n1.getNegative() != n2.getNegative()){
            throw new IllegalArgumentException("Different sign");
        }

        // Loop through every index of both numbers and add their corresponding values.
        int base = n1.getBase(); // The base of both numbers.
        int length = Math.max(n1.getLength(), n2.getLength()); // The maximum length of the two numbers.
        int[] result = new int[length + 1]; // Array to write the result to, has one extra digit to prevent overflow.
        int carry = 0; // Keeps track of the carry.
        for(int i = 0; i < result.length; i++){
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
     * @pre {@code n1 != null && n2 != null && !(n1.getNegative() ^ n2.getNegative()) && n1.base == n2.base}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1 - n2}
     */
    private Number subtractUnsignedExtended(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if (n1 == null || n2 == null) {
            throw new NullPointerException("Numbers may not be null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        } else if (n1.getNegative()^n2.getNegative()) {
            throw new IllegalArgumentException("Both numbers of subtractUnsigned should have the same sign.");
        }

        // Check if abs(n2) > abs(n1), since this is a prerequisite of subtractUnsigned, hence in such case is n1 - n2
        // rewritten to its equivalent operation -(n2 - n1).
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
     * @pre {@code n1 != null && n2 != null && !(n1.getNegative() ^ n2.getNegative()) && abs(n1)>=abs(n2)
     *      && n1.base == n2.base}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1 - n2}
     */
    private Number subtractUnsigned(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if (n1 == null || n2 == null) {
            throw new NullPointerException("Numbers may not be null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        } else if (n1.getNegative() ^ n2.getNegative()) {
            throw new IllegalArgumentException("Both numbers of subtractUnsigned should have the same sign.");
        } else if (n1.getNegative() == n1.compareTo(n2) > 0 && n1.compareTo(n2) != 0) { // n1.getNegative() <=> n1 > n2
            throw new IllegalArgumentException("The absolute values of the first number of subtractUnsigned should be larger than the second.");
        }

        // Loop through every index of both numbers and subtract their corresponding values.
        int base = n1.getBase();
        int length = n1.getLength();
        int carry = 0;
        int[] result = new int[length]; // Contains the value of the subtracted digits.
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
     * @pre {@code n1 != null && n2 != null && n1.base == n2.base}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1 * n2}
     */
    public Number multiply(Number n1, Number n2) throws NullPointerException{
        // Assert preconditions
        if (n1 == null || n2 == null){
            throw new NullPointerException("Can not add to null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        }

        int m = n1.getLength();
        int n = n2.getLength();

        int[] result = new int[m+n]; // In algorithm denoted as z
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
                /*System.out.printf("n: ");
                for(int k=0; k<result.length; k++){
                    System.out.printf("%d",result[k]);
                }
                System.out.printf("\n");*/
            }
            result[i+n] = carry;
            carry = 0;
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
        return new Number(endResult, base, n1.getNegative() ^ n2.getNegative());
    }

    /**
     * Multiplies two numbers to each other (Karatsuba)
     * @param n1    The first number
     * @param n2    The second number
     * @pre {@code n1 != null && n2 != null && n1.base == n2.base}
     * @throws NullPointerException if {@code n1 == null || n2 == null}
     * @return Number numberResult where {@code numberResult = n1*n2}
     *
     */
    public Number karatsuba(Number n1, Number n2) throws NullPointerException {
        if (n1 == null || n2 == null) {
            throw new NullPointerException("Can not add to null");
        } else if (n1.getBase() != n2.getBase()) {
            throw new IllegalArgumentException("Both numbers should have the same base");
        }

        int base = n1.getBase();
        int m = n1.getLength();
        int n = n2.getLength();

        //Split numbers if one is larger than 2
        int lengthOfSplit;
        if(m>=n){
            lengthOfSplit = m/2;
        } else {
            lengthOfSplit = n/2;
        }
        //Base
        if(lengthOfSplit <= 0){
            return multiply(n1,n2);
        }
        //Split numbers in half
        int[] num_n1_lo = new int[lengthOfSplit];
        int[] num_n1_hi = new int[lengthOfSplit];
        int[] num_n2_lo = new int[lengthOfSplit];
        int[] num_n2_hi = new int[lengthOfSplit];
        //n1 split
        for(int i=0; i<lengthOfSplit*2; i++) {
            if(i<lengthOfSplit) {
                num_n1_lo[i] = n1.getDigit(i);
            } else {
                num_n1_hi[i%lengthOfSplit] = n1.getDigit(i);
            }
        }
        //n2 split
        for(int i=0; i<lengthOfSplit*2; i++) {
            if(i<lengthOfSplit) {
                num_n2_lo[i] = n2.getDigit(i);
            } else {
                num_n2_hi[i%lengthOfSplit] = n2.getDigit(i);
            }
        }
        Number n1_lo = new Number(num_n1_lo, base, n1.getNegative());
        Number n1_hi = new Number(num_n1_hi, base, n1.getNegative());
        Number n2_lo = new Number(num_n2_lo, base, n2.getNegative());
        Number n2_hi = new Number(num_n2_hi, base, n2.getNegative());

        Number newLo = karatsuba(n1_lo,n2_lo);
        Number newHi = karatsuba(n1_hi,n2_hi);
        Number newMid = subtract(subtract(karatsuba(add(n1_lo,n1_hi),add(n2_lo,n2_hi)),newLo),newHi);
        int[] result = new int[n1.getLength()+n2.getLength()];

        //Number result = new Number(new int[n], base, n1.getNegative() ^ n2.getNegative()); //change length

        for(int i=0; i<result.length; i++){
            if(i<newLo.getLength() && i>=lengthOfSplit*0){
                int t = result[i] + newLo.getDigit(i);
                int carry = t/newLo.getBase();
                result[i] = t%newLo.getBase();
                if(i+1<result.length) {
                    result[i + 1] += carry;
                }
            }
            if(i-lengthOfSplit*1<newMid.getLength() && i>=lengthOfSplit*1){
                int t = result[i] + newMid.getDigit(i-lengthOfSplit*1);
                int carry = t/newMid.getBase();
                result[i] = t%newMid.getBase();
                if(i+1<result.length) {
                    result[i + 1] += carry;
                }
            }
            if(i-lengthOfSplit*2<newHi.getLength() && i>=lengthOfSplit*2){
                int t = result[i] + newHi.getDigit(i-lengthOfSplit*2);
                int carry = t/newHi.getBase();
                result[i] = t%newHi.getBase();
                if(i+1<result.length) {
                    result[i + 1] += carry;
                }
            }
        }
        Number finalResult = new Number(result, base, n1.getNegative() ^ n2.getNegative());
        return finalResult;
    }

    // @TODO Implement other functions.
}
