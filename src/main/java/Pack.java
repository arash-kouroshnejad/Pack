import java.util.*;
import java.util.function.Predicate;

public class Pack<T> implements Iterable<T>{
    private Node<T> head;
    private Node<T> tail;

    public Pack() {
        head = new Node<>();
        /*head.setNext(head);
        head.setPrev(head);*/
        tail = head;
    }

    private int size = 0;

    public boolean add(T data) {
        if (size == 0) {
            head.setVal(data);
            size = 1;
        }
        else {
            Node<T> node = new Node<>();
            node.setVal(data);
            addAfter(node, tail);
            tail = node;
        }
        return true;
    }

    private void addAfter(Node<T> toBeAdded, Node<T> targetNode) {
        toBeAdded.setIndex(targetNode.getIndex() + 1);
        toBeAdded.setPrev(targetNode);
        targetNode.setNext(toBeAdded);
        size++;
    }

    private  void insertNode(Node<T> toBeAdded, Node<T> targetNode) {
        toBeAdded.setIndex(targetNode.getIndex());
        toBeAdded.setPrev(targetNode.getPrev());
        toBeAdded.setNext(targetNode);
        if (targetNode.getPrev() != null) {
            targetNode.getPrev().setNext(toBeAdded);
        }
        targetNode.setPrev(toBeAdded);
        targetNode.setIndex(targetNode.getIndex() + 1);
        if (targetNode == head) {
            head = toBeAdded;
        }
        size++;
    }

    public void add(int index, T data) {
        if (index < 0) {
            throw new IllegalArgumentException("index " + index + " is invalid!");
        }
        else if (index > size) {
            throw new IndexOutOfBoundsException("index " + index + " is out of bounds for Pack size " + size);
        }
        else if (size == 0) {
            head.setVal(data);
        }
        else {
            Node<T> node = new Node<>();
            node.setVal(data);
            int current = 0;
            for (Node<T> n = head; n != null; n = n.getNext(), current++) {
                if (current == index) {
                    insertNode(node, n);
                }
                else if (current > index) {
                    n.setIndex(current + 1);
                }
            }
        }
    }

    private void removeNode(Node<T> node) {
        if (node.getPrev() != null) {
            if (node.getNext() != null) {
                // mid node
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
            }
            else {
                // tail node
                node.getPrev().setNext(null);
                tail = node.getPrev();
            }
        }
        else if (node.getNext() != null) {
            // head node
            node.getNext().setPrev(null);
            head = node.getNext();
        }
        else {
            // head node
            head.setVal(null);
        }
        size--;
    }

    public T remove(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index " + index + " is invalid!");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("index " + index + " is out of bounds for Pack size " + size);
        }
        int current = 0;
        Node<T> n = null;
        for (Node<T> node = head; node != null; node = node.getNext(), current++) {
            if (current == index) {
                removeNode(node);
                n = node;
            }
            else if (current > index) {
                node.setIndex(current - 1);
            }
        }
        assert n != null;
        return n.getVal();
    }

    private Node<T> findNode(Object data) {
        for (Node<T> n = head; n != null; n = n.getNext()) {
            if (n.getVal().equals(data)) {
                return n;
            }
        }
        return null;
    }

    private Node<T> findNode(int index) { // TODO : should it update a variable instead ?
        for (Node<T> node = head; node != null; node = node.getNext())
            if (node.getIndex() == index)
                return node;
        return null;
    }

    public boolean remove(Object data) {
        Node<T> node = findNode(data);
        if (node != null) {
            removeNode(node);
            return true;
        }
        return false;
    }

    public T get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index " + index + " is invalid!");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("index " + index + " is out of bounds for Pack size " + size);
        }
        for (Node<T> node = head; node != null; node = node.getNext()) {
            if (node.getIndex() == index) {
                return node.getVal();
            }
        }
        return null;
    }

    public int indexOf(Object data) {
        Node<T> node = findNode(data);
        if (node != null) {
            return node.getIndex();
        }
        return -1;
    }

    public boolean contains(Object data) {
        return findNode(data) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void removeIf(Predicate<? super T> predicate) {
        for (Node<T> node = head; node != null; node = node.getNext()) {
            if (predicate.test(node.getVal())) {
                removeNode(node);
            }
        }
    }

    public void addAll(List<? extends T> list) {
        for (T data : list) {
            add(data);
        }
    }

    public void addAll(Pack<? extends T> pack) {
        for (T t : pack) {
            add(t);
        }
    }

    private void swapNodes(Node<T> node1, Node<T> node2) {
        T val = node2.getVal();
        node2.setVal(node1.getVal());
        node1.setVal(val);
    }

    public void sort(Comparator<? super T> comparator) {
        for (Node<T> node1 = head; node1 != null; node1 = node1.getNext()) {
            for (Node<T> node2 = head; node2 != null; node2 = node2.getNext()) {
                if (comparator.compare(node1.getVal(), node2.getVal()) < 0) {
                    swapNodes(node1, node2);
                }
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new itr();
    }

    public Iterator<T> iterator(int n) {
        return new itr(n);
    }

    public ListIterator<T> listIterator() {return new ListItr();}

    private class itr implements Iterator<T>{
        Node<T> node = new Node<>();

        public itr() {
            node.setNext(Pack.this.head);
        }

        public itr(int n) {
            if (n < 0)
                throw new IllegalArgumentException("Index " + n + " is invalid");
            else
                node.setNext(findNode(n));
        }

        protected void validate() {
            if (node == null) {
                throw new IndexOutOfBoundsException("Pack is Empty!");
            }
        }

        @Override
        public boolean hasNext() {
            validate();
            return node.getNext() != null && node.getNext().getVal() != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                node = node.getNext();
                return node.getVal();
            }
            return null;
        }
    }

    private class ListItr extends itr implements ListIterator<T> {
        boolean locked;

        @Override
        public boolean hasPrevious() {
            validate();
            return node.getPrev() != null;
        }

        @Override
        public T next() {
            T next = super.next();
            if (next!= null) {
                locked = false;
                return next;
            }
            return null;
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                locked = false;
                node = node.getPrev();
                return node.getVal();
            }
            return null;
        }

        @Override
        public int nextIndex() {
            if (hasNext()) {
                return node.getNext().getIndex();
            }
            return -1;
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) {
                return node.getPrev().getIndex();
            }
            return -1;
        }

        private void cleanIndices(int delta) {
            int index = node.getIndex() + delta;
            for (Node<T> node = this.node.getNext(); node != null; node = node.getNext()) {
                node.setIndex(index++);
            }
        }

        @Override
        public void remove() {
            if (!locked) {
                if (size != 0) {
                    removeNode(node);
                    cleanIndices(0);
                    if (size == 0) {
                        node = null;
                    }
                }
                else {
                    throw new IndexOutOfBoundsException("Pack is Empty!");
                }
            }
            locked = true;
        }

        @Override
        public void set(T t) {
            validate();
            node.setVal(t);
        }

        @Override
        public void add(T t) {
            if (!locked) {
                if (size == 0) {
                    head.setVal(t);
                    this.node = head;
                    head.setIndex(0);
                }
                else {
                    Node<T> node = new Node<>();
                    node.setVal(t);
                    insertNode(node, this.node);
                    this.node = node;
                    cleanIndices(1);
                }
            }
            locked = true;
        }
    }
}
