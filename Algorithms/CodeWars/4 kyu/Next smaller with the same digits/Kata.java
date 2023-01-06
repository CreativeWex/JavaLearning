import java.util.Arrays;
public class Kata
{
    public static long nextBiggerNumber(long n)
    {
        char [] s = String.valueOf(n).toCharArray(); // Возвращает строковое представление аргумента
//        for(int i = s.length - 2; i >= 0; i--){
//            for (int j = s.length-1; j > i; j--){
//                if(s[i] < s[j]){
//                    char tmp = s[i];
//                    s[i] = s[j];
//                    s[j] = tmp;
//                    Arrays.sort(s, i+1, s.length);
//                    return Long.valueOf(String.valueOf(s));
//                }
//            }
//        }
        for (int i = 0; i < s.length - 2; i++) {
            for (int j = 1; i)
        }
        return -1;
    }
}