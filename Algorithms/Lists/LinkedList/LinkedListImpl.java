package Algorithms.Lists.LinkedList;

public class LinkedListImpl implements LinkedList{
    Node head;
    Integer amount = 0;
    public LinkedListImpl() {
        head = null;
    }

    @Override
    public void display() {
        Node currentNode = head;
        if (head != null) {
            System.out.println(head.data);
        }
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            System.out.println(currentNode.data);
        }
    }

    @Override
    public void add(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node currentNode = head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
            newNode.previous = currentNode;
        }
        amount++;
    }

    @Override
    public void add(int index, Object data) {
        if (index > amount) {
            throw new RuntimeException("Index out of list");
        }
        int i = 0;

    }

    @Override
    public void remove(Object data) {
        Node currentNode = head;
        Node previousNode = null;
        while (currentNode.next != null) {
            if (currentNode.data == data) {
                if (currentNode == head) {
                    head = currentNode.next;
                } else {
                    previousNode.next = currentNode.next;
                }
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void set(int index, Object element) {

    }

    private static class Node {
        private Object data;
        private Node next;
        public Node(Object data) {
            this.data = data;
            next = null;
        }

        public Object getData() {
            return data;
        }
        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
        public void setData(Object data) {
            this.data = data;
        }
    }

}
