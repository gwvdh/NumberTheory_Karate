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
        Number numberResult = new Number("", base, n1.getNegative()/* ^ n2.getNegative()*/, k); //@TODO Does not handle the negative correctly
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
        Number numberResult = new Number("", base, n1.getNegative()/* ^ n2.getNegative()*/, k); //@TODO Does not handle the negative correctly
        for(int i=1; i<k-1; i++){
            numberResult.num[i-1] = result[k-i];
        }
        return numberResult;
    }

    public Number multiply(Number n1, Number n2) {
        return null; // @TODO Implement
    }

    public Number karatsuba(Number n1, Number n2) {
        return null; // @TODO Implement
    }

    // @TODO Implement other functions.
}
