

public class Program {
    public static int MIN = 100;
    public static int MAX = 1257;
    public static int STEP = 56;
    public static void main(String[] args) {
        Conversion conversion = new Conversion();
        for (int i = 95; i < MAX; i += STEP) {
            System.out.println(i + " = " + conversion.solution(i));
        }
//        System.out.println(conversion.solution(323));
    }
}