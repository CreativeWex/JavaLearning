package Algorithms.Lists.LinkedList;

public interface LinkedList {
    public void display();
    public void add (Object data);
    public void add(int index, Object data);
    public void remove(Object data);
    public void remove(int index);
    public void set(int index, Object element);
}
