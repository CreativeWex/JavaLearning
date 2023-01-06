import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution
{
    public static int findMissing(int[] numbers)
    {
        Set<Integer> distinct = Arrays.stream(numbers).boxed().collect(Collectors.toSet());
        if (distinct.size() == 1) { // Массив состоит из одинаковых элементов
            return numbers[0];
        }
        int step = (numbers[numbers.length - 1] - numbers[0]) / (numbers.length);
        int progressionMember = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != progressionMember) {
                return  progressionMember;
            }
            progressionMember += step;
        }
        return 0;
    }
}
