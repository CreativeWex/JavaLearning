public class Kata {
    public static int[] sortArray(int[] array) {
        int temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = array.length - 1; j > i; j--) {
                if (array[i] % 2 != 0 && array[j] % 2 != 0 && array[i] > array[j]) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    System.out.println(array[i] + " ");
                }
            }
        }
        return array;
    }
}