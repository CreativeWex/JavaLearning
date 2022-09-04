package Singleton;

public class Singleton {
    private static Singleton instanse = null; // Статический объект
    private Singleton() {}
    public static Singleton getInstance() {
        if (instanse == null) {
            instanse = new Singleton();
        }
        return instanse;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance(); // Получение объекта через геттер
    }
}


