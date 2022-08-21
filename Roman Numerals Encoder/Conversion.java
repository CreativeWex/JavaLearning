public class Conversion {
    static final String ONE = "I";
    static final String FIVE = "V";
    static final String TEN = "X";
    static final String FIFTY = "L";
    static final String HUNDRED = "C";
    static final String FIVE_HUNDRED = "D";
    static final String THOUSAND = "M";
    public static String digitConverter(int digit) {
        String result = "";
        if (0 < digit && digit <= 3) {
            result = new String(new char[digit]).replace("\0", ONE);
        } else if (digit == 4) {
            return ONE + FIVE;
        } else if (digit >=5 && digit < 9) {
            result = FIVE + new String(new char[digit - 5]).replace("\0", ONE);
        } else if (digit == 9) {
            result = ONE + TEN;
        }
        return result;
    }
    public static String dozensConverter(int dozens) {
        String result = "";
        if (dozens <= 3) {
            result = new String(new char[dozens]).replace("\0", TEN);
        } else if (dozens == 4) {
            return TEN + FIFTY;
        } else if (dozens >= 5 && dozens < 9) {
            result = FIFTY + new String(new char[dozens - 5]).replace("\0", TEN);
        } else if (dozens == 9) {
            result = TEN + HUNDRED;
        }
        return result;
    }
    public static String hundredConverter(int hundred) {
        String result = "";
        if (hundred <= 3) {
            result = new String(new char[hundred]).replace("\0", HUNDRED);
        } else if (hundred == 4) {
            return HUNDRED + FIVE_HUNDRED;
        } else if (hundred >= 5 && hundred < 9) {
            result = FIVE_HUNDRED + new String(new char[hundred - 5]).replace("\0", HUNDRED);
        } else if (hundred == 9) {
            result = HUNDRED + THOUSAND;
        }
        return result;
    }
    public String solution(Integer n) {
        String result = "";
        if (n >= 1000) {
            Integer Mcount = n / 1000;
            result += new String(new char[Mcount]).replace("\0", THOUSAND);
            n %= 1000;
        }
        if (n >= 100) {
            Integer hundreds = n / 100;
            result += Conversion.hundredConverter(hundreds);
            n %= 100;
        }
        if (n >= 10) {
            Integer dozens = n / 10;
            result += Conversion.dozensConverter(dozens);
            n %= 10;
        }
        result += Conversion.digitConverter(n);
        return result;
    }
}