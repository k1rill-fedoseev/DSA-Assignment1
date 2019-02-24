import java.util.Comparator;

class Node<E> {
    Node<E> prev, next;
    E value;

    Node(E value, Node<E> prev, Node<E> next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}

class DoublyLinkedList<E> extends List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    DoublyLinkedList() {
        size = 0;
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);
        head.next = tail;
    }

    @Override
    int size() {
        return size;
    }

    @Override
    void add(int index, E element) throws IndexOutOfBoundsException {
        checkBounds(index, size());

        Node<E> node = find(index);

        Node<E> newNode = new Node<>(element, node.prev, node);
        node.prev.next = newNode;
        node.prev = newNode;

        size++;
    }

    @Override
    void addLast(E element) {
        Node<E> newNode = new Node<>(element, tail.prev, tail);
        tail.prev.next = newNode;
        tail.prev = newNode;

        size++;
    }

    @Override
    boolean delete(E element) {
        Node<E> cur = head.next;

        // Find element equals the given one
        while (cur != tail && !cur.value.equals(element))
            cur = cur.next;

        if (cur == tail)
            return false;

        removeNode(cur);
        size--;

        return true;
    }

    @Override
    E delete(int index) throws IndexOutOfBoundsException {
        checkBounds(index);

        Node<E> node = find(index);

        removeNode(node);
        size--;

        return node.value;
    }

    @Override
    E deleteLast() {
        E value = tail.prev.value;

        removeNode(tail.prev);
        size--;

        return value;
    }

    @Override
    E set(int index, E element) throws IndexOutOfBoundsException {
        checkBounds(index);

        Node<E> node = find(index);
        E value = node.value;

        node.value = element;

        return value;
    }

    @Override
    E get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);

        return find(index).value;
    }

    /**
     * Optimized version of sort, <code>O(n<sup>2</sup>)</code>
     */
    @Override
    void sort(Comparator<E> comparator) {
        for (Node<E> i = head.next; i != tail.prev; i = i.next) {
            Node<E> min = i;
            E minValue = min.value;

            for (Node<E> j = i.next; j != tail; j = j.next) {
                if (comparator.compare(minValue, j.value) > 0) {
                    min = j;
                    minValue = min.value;
                }
            }

            min.value = i.value;
            i.value = minValue;
        }
    }

    /**
     * Rearranges links, in order to remove specified <code>Node</code>
     *
     * @param node Removed <code>Node</code>
     */
    private void removeNode(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * Finds <code>Node</code>
     *
     * @param index element position
     * @return <code>Node</code> at <code>index</code> position
     */
    private Node<E> find(int index) {
        Node<E> cur = head.next;
        for (int i = 0; i < index; i++)
            cur = cur.next;

        return cur;
    }
}