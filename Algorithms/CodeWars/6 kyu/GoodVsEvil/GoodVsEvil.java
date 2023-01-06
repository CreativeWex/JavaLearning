public class GoodVsEvil {
    static final int[] GOOD_ARMY = new int[] {1, 2, 3, 3, 4, 10};
    static final int[] EVIL_ARMY = new int[] {1, 2, 2, 2, 3, 5, 10};
    public static String battle(String goodAmounts, String evilAmounts) {

        int goodPower = 0;
        int evilPower = 0;
        String[] goodCounter = goodAmounts.split(" ");
        String[] evilCounter = evilAmounts.split(" ");

        for (int i = 0; i < goodCounter.length; i++) {
            goodPower += Integer.parseInt(goodCounter[i]) * GOOD_ARMY[i];
        }
        for (int i = 0; i < evilCounter.length; i++) {
            evilPower += Integer.parseInt(evilCounter[i]) * EVIL_ARMY[i];
        }
        if (goodPower > evilPower) {
            return "Battle Result: Good triumphs over Evil";
        } else if (goodPower < evilPower) {
            return "Battle Result: Evil eradicates all trace of Good";
        }
        return "Battle Result: No victor on this battle field";
    }
}