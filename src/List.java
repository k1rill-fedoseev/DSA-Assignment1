import java.util.Comparator;

abstract class List<E> {

    /**
     * @return Size of the list
     */
    abstract int size();


    /**
     * @return <code>true</code> if list is empty, <code>false</code> otherwise
     */
    boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Inserts element <code>element</code> at position <code>index</code>
     *
     * @param index   Index, at which element should be after function call
     * @param element New element
     * @throws IndexOutOfBoundsException
     */
    abstract void add(int index, E element) throws IndexOutOfBoundsException;


    /**
     * Inserts element  <code>element</code> at the start of the list
     *
     * @param element New element
     */
    void addFirst(E element) {
        add(0, element);
    }

    /**
     * Inserts element  <code>element</code> at the start of the list
     *
     * @param element New element
     */
    void addLast(E element) {
        add(size(), element);
    }

    /**
     * Removes <code>element</code> out of the list, if such element exists
     *
     * @param element Removing element
     * @return <code>true</code> if element was removed, <code>false</code> otherwise
     */
    abstract boolean delete(E element);

    /**
     * Removes element at position <code>index</code>
     *
     * @param index Index of the removing element
     * @return Removed element
     * @throws IndexOutOfBoundsException
     */
    abstract E delete(int index) throws IndexOutOfBoundsException;


    /**
     * Removes first element of the list
     *
     * @return Removed element
     */
    E deleteFirst() {
        return delete(0);
    }

    /**
     * Removes last element of the list
     *
     * @return Removed element
     */
    E deleteLast() {
        return delete(size() - 1);
    }

    /**
     * Sets element at the specified position to a new one
     *
     * @param index   Element index
     * @param element New element
     * @return Replaced element
     * @throws IndexOutOfBoundsException
     */
    abstract E set(int index, E element) throws IndexOutOfBoundsException;

    /**
     * Gets element at the specified position
     *
     * @param index Element index
     * @return Retrieved element
     * @throws IndexOutOfBoundsException
     */
    abstract E get(int index) throws IndexOutOfBoundsException;

    /**
     * Sorts list using selection sort in increasing order, <code>O(n<sup>2</sup>)</code> if <code>get</code> is <code>O(1)</code>, <code>O(n<sup>3</sup>)</code> if <code>get</code> is <code>O(n)</code>
     */
    void sort(Comparator<E> comparator) {
        for (int i = 0; i < size() - 1; i++) {
            int minIndex = i;
            E min = get(i);
            for (int j = i + 1; j < size(); j++) {
                if (comparator.compare(min, get(j)) > 0) {
                    min = get(j);
                    minIndex = j;
                }
            }
            set(minIndex, get(i));
            set(i, min);
        }
    }

    /**
     * Checks if specified index lies inside lists bounds
     *
     * @param index Checked index
     * @throws IndexOutOfBoundsException
     */
    protected void checkBounds(int index) throws IndexOutOfBoundsException {
        checkBounds(index, size() - 1);
    }

    /**
     * Checks if specified index lies inside bound <code>0..maxIndex</code>
     *
     * @param index    Checked index
     * @param maxIndex Maximum valid index
     * @throws IndexOutOfBoundsException
     */
    protected void checkBounds(int index, int maxIndex) throws IndexOutOfBoundsException {
        if (index < 0 || index > maxIndex)
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound %d..%d", index, 0, maxIndex));
    }
}
