public class Program {
    public static void main(String[] args) {
        System.out.println("Score for [5,1,3,4,1] must be 250:" + Greed.greedy(new int[]{5,1,3,4,1}));
        System.out.println("Score for [1,1,1,3,1] must be 1100:" + Greed.greedy(new int[]{1,1,1,3,1}));
        System.out.println("Score for [2,4,4,5,4] must be 450:" + Greed.greedy(new int[]{2,4,4,5,4}));
    }
}
