class DynamicArrayList<E> extends List<E> {
    private static final int DEFAULT_CAPACITY = 32;

    private int size;
    private E[] array;

    DynamicArrayList() {
        this(DEFAULT_CAPACITY);
    }

    DynamicArrayList(int capacity) {
        size = 0;
        array = (E[]) new Object[capacity];
    }

    @Override
    int size() {
        return size;
    }

    @Override
    void add(int index, E element) throws IndexOutOfBoundsException {
        checkBounds(index, size());

        // If array is full
        if (size() == array.length) {
            E[] prev = array;

            // Double array size
            array = (E[]) new Object[array.length * 2];

            for (int i = size; i >= 0; i--) {
                if (i > index)
                    array[i] = prev[i - 1];
                else if (i == index)
                    array[i] = element;
                else
                    array[i] = prev[i];
            }
        } else {
            for (int i = size; i > index; i--) {
                array[i] = array[i - 1];
            }
            array[index] = element;
        }
        size++;
    }

    @Override
    boolean delete(E element) {
        int i = 0;
        // Find index of first element equals to the given one
        while (i < size() && !array[i].equals(element))
            i++;

        // No such element
        if (i == size())
            return false;

        // Rearrange all elements after the found one
        while (i < size() - 1) {
            array[i] = array[i + 1];
            i++;
        }

        size--;

        return true;
    }

    @Override
    E delete(int index) throws IndexOutOfBoundsException {
        checkBounds(index);

        E value = array[index];

        for (int i = index; i < size(); i++)
            array[i] = array[i + 1];

        size--;

        return value;
    }

    @Override
    E set(int index, E element) throws IndexOutOfBoundsException {
        checkBounds(index);

        E value = array[index];
        array[index] = element;

        return value;
    }

    @Override
    E get(int index) throws IndexOutOfBoundsException {
        checkBounds(index);

        return array[index];
    }
}