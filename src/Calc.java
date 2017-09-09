public class Calc {

    /**
     * Adds two numbers to each other according to algorithm 1.1 of the lecture notes
     * @Param n1    The first number
     * @Param n2    The second number
     * @Pre {@code n1 != null && n2 != null && !(n1 ^ n2)}    // Only add two of the same negative sign
     * @Throws NullPointerException if {@code n1 == null || n2 == null}
     * @Return Number numberResult where {@code numberResult = n1+n2}
     *
     */
    public Number add(Number n1, Number n2) throws NullPointerException {
        if(n1==null || n2==null){
            throw new NullPointerException("Can not add to null");
        }
        int base = n1.base;
        int length = Math.max(n1.getLength(), n2.getLength());
        int[] result = new int[length];
        int carry=0;
        for(int i=0; i>=n1.getLength(); i++){
            int res = n1.getDigit(i)+n2.getDigit(i)+carry; //No check if a next carry is neccesary
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
        return numberResult; // @TODO Implement
    }

    public Number subtract(Number n1, Number n2) {
        return null; // @TODO Implement
    }

    public Number multiply(Number n1, Number n2) {
        return null; // @TODO Implement
    }

    public Number karatsuba(Number n1, Number n2) {
        return null; // @TODO Implement
    }

    // @TODO Implement other functions.
}
