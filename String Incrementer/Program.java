public class Program {
    public static void main(String[] args) {
        System.out.println("Before foobar000 " + "After " + Kata.incrementString("foobar000"));
        System.out.println("Before foo " + "After " + Kata.incrementString("foo"));
        System.out.println("Before foobar001 " + "After " + Kata.incrementString("foobar001"));
        System.out.println("Before foobar99 " + "After " + Kata.incrementString("foobar99"));
        System.out.println("Before foobar0099 " + "After " + Kata.incrementString("foobar0099"));
        System.out.println("Before " + "After " + Kata.incrementString(""));
    }
}
