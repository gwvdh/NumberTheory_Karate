public class Calc {

    /**
     * Adds two numbers to each other according to algorithm 1.1 of the lecture notes
     * @Param n1    The first number
     * @Param n2    The second number
     * @Pre {@code n1 != null && n2 != null && !(n1.getNegative() ^ n2.getNegative())}    // Only add two of the same negative sign
     * @Throws NullPointerException if {@code n1 == null || n2 == null}
     * @Return Number numberResult where {@code numberResult = n1+n2}
     *
     */
    public Number add(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if(n1==null || n2==null){   // Preconditions
            throw new NullPointerException("Can not add to null");
        } else if(n1.getNegative()^n2.getNegative()){
            throw new IllegalArgumentException("Different sign");
        }
        //@TODO Handle numbers of different lengths (add leading zeroes to the smaller number)
        int base = n1.base;
        int length = Math.max(n1.getLength(), n2.getLength());
        int[] result = new int[length];
        int carry=0;
        for(int i=0; i>=length; i++){
            int res = n1.getDigit(i)+n2.getDigit(i)+carry; //No check if a next carry is necessary
            if(res >= base){
                res = res - base;
                carry = 1;
            } else {
                carry = 0;
            }
            result[i] = res;
        }
        int k; //Length of the result Number
        if(carry == 1){
            k = length+1;
            result[k-1] = 1;
        } else {
            k = length;
        }
        Number numberResult = new Number(new int[k], base, n1.getNegative()/* ^ n2.getNegative()*/); //@TODO Does not handle the negative correctly
        for(int i=1; i<k-1; i++){
            numberResult.num[i-1] = result[k-i];
        }
        return numberResult;
    }

    /**
     * Subtracts two numbers from each other according to algorithm 1.2 of the lecture notes
     * @Param n1    The first number
     * @Param n2    The second number
     * @Pre {@code n1 != null && n2 != null && !(n1 ^ n2) && n1>n2}    // Only subtract two of the same negative sign
     * @Throws NullPointerException if {@code n1 == null || n2 == null}
     * @Return Number numberResult where {@code numberResult = n1-n2}
     *
     */
    public Number subtract(Number n1, Number n2) throws NullPointerException, IllegalArgumentException {
        if(n1==null || n2==null){
            throw new NullPointerException("Can not add to null");
        } else if(n1.getNegative()^n2.getNegative()){
            throw new IllegalArgumentException("Different sign");
        } else if(n1.getLength()<n2.getLength()){ //@TODO Not entirely robust
            throw new IllegalArgumentException("x<y");
        }

        int base = n1.base;
        int carry = 0;
        int length = n1.getLength();
        int[] result = new int[length];
        //@TODO Add leading zeroes to the smaller number (n2)
        for(int i=0; i<length; i++){
            result[i] = n1.num[i]-n2.num[i]-carry; //@TODO Wrong numbers are being subtracted
            if(result[i]<0){
                result[i]=result[i]+base;
                carry = 1;
            } else {
                carry = 0;
            }
        }
        int k = n1.getLength();
        while(k>=2 && result[k-1]==0){
            k = k-1;
        }
        Number numberResult = new Number(new int[k], base, n1.getNegative()/* ^ n2.getNegative()*/); //@TODO Does not handle the negative correctly
        for(int i=1; i<k-1; i++){
            numberResult.num[i-1] = result[k-i];
        }
        return numberResult;
    }

    /**
     * Multiplies two numbers to each other according to algorithm 1.3 of the lecture notes (Naive multiplication)
     * @Param n1    The first number
     * @Param n2    The second number
     * @Pre {@code n1 != null && n2 != null}
     * @Throws NullPointerException if {@code n1 == null || n2 == null}
     * @Return Number numberResult where {@code numberResult = n1*n2}
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
        int base = n1.base;
        int carry = 0;
        for(int i=0; i<m; i++){ //Get elements from one number, multiply with numbers from the other
            for(int j=0; j<n; j++){
                int t = result[i+j]+n1.num[i]*n2.num[j]+carry;
                carry = t/base;
                result[i+j] = t-carry*base;
            }
            result[i+n] = carry;
        }
        int k;
        if(result[m+n-1] == 0){
            k = m+n-2;
        } else {
            k = m+n-1;
        }
        Number numberResult = new Number(new int[k], base, n1.getNegative() ^ n2.getNegative());
        for(int i=1; i<k-1; i++){
            numberResult.num[i-1] = result[k-i];
        }
        return numberResult;
    }

    /**
     * Multiplies two numbers to each other (Karatsuba)
     * @Param n1    The first number
     * @Param n2    The second number
     * @Pre {@code n1 != null && n2 != null}
     * @Throws NullPointerException if {@code n1 == null || n2 == null}
     * @Return Number numberResult where {@code numberResult = n1*n2}
     *
     */
    public Number karatsuba(Number n1, Number n2) {
        int base = n1.base;
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
        Number newMid = subtract(subtract(karatsuba(add(n1_lo,n1_hi),add(n2_lo,n2_hi)),newLo),newHi);
        Number result = new Number(new int[n], base, n1.getNegative() ^ n2.getNegative()); //change length

        return null; // @TODO Implement
    }

    // @TODO Implement other functions.
}
