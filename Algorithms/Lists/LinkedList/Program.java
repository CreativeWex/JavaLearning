package Lists.LinkedList;

public class Program {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedListImpl();
        linkedList.add(5);
        linkedList.add(5);
        linkedList.add(2);
        linkedList.add(3);

        linkedList.display();
    }
}
