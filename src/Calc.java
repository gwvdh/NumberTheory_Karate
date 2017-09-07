public class Calc {
    public Number add(Number n1, Number n2) {
        int base = n1.base;
        int length = Math.max(n1.getLength(), n2.getLength());
        int[] result = new int[length];
        //Number result = new Number("", base, n1.getNegative() ^ n2.getNegative(), n1.getLength()+1); //@TODO This array length is the maximum. Might be too long.
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
        Number numberResult = new Number("", base, n1.getNegative() ^ n2.getNegative(), k);
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
