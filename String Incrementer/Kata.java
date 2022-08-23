public class Kata {
    private static int countZeros(String number) {
        int zeroCounter = 0;
        for (int i = 0; i < number.length() - 1; i++) {
            if (number.charAt(i) != '0') {
                if (number.charAt(i + 1) == '9') {
                    zeroCounter -= 1;
                    if (zeroCounter < 0) {
                        zeroCounter = 0;
                    }
                }
                break;
            }
            zeroCounter++;
        }
        return zeroCounter;
    }
    public static String incrementString(String str) {
        if (str.matches("[a-z]+[^\\d]+") || str.matches("")) {
            return str += "1";
        } else {
            String stringNumber = (str.replaceAll("[^0-9]", ""));
            Integer zeroCounter = countZeros(stringNumber);
            int value = Integer.parseInt(str.replaceAll("[^0-9]", "")) + 1;
            return str.replaceAll("[0-9]", "") + new String(new char[zeroCounter]).replace("\0", "0") + value;
        }
    }
}