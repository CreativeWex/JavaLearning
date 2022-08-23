public class Greed {

    public static int greedy(int[] dice) {
        int[] dieCount = new int[7];
        for (int die : dice) {
            dieCount[die]++;
        }
        return dieCount[1]/3*1000 + dieCount[1]%3*100 + dieCount[2]/3*200 + dieCount[3]/3*300 + dieCount[4]/3*400 + dieCount[5]/3*500 + dieCount[5]%3*50 + dieCount[6]/3*600;
    }
}