package ru.savkaev;

/**
 * Двусвязный список. Содержит в себе узлы, которые хранят ссылки на своих соседей.
 * Первый и последний узлы называются "головой" и "хвостом", соответственно. Если список состоит из одного элемента -
 * он является "головой" и "хвостом".
 * @param <T>
 */

public class CustomLinkedList<T> {
    /**
     * Узел списка "голова" - является началом списка, если список больше 1 элемента,
     * содержит ссылку на следующий элемент.
     */
    Node<T> head;
    /**
     * Узел списка "хвост" - является концом списка, хранит ссылку на предидущий узел
     */
    private Node<T> tail;
    /**
     * Размер списка.
     */
    private int size;

    /**
     * Добавить элемент в конец списка. Если список пуст - добавить первый элемент. В противном случае -
     * добавить элемент
     * в конец списка, прописав его следующим у старого хвоста, а у нового - прописать предидущий хвост.
     * Увеличить размер списка на 1.
     * @param value - добавляемое значение
     */

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Добавить элемент в список по индексу. Если список пустой - добавить первый элемент. В противном случае -
     * добавление происходит в начало списка, делая его новой головой, а старая голова становится следующим узлом. Если
     * вставка происходит в середину списка - у соседей нового элемента значения голвы и хвоста переписываются на
     * новый элемент. Так же у нового элемента прописываются соседи как следующий и предидущий.
     * @param index индекс добавляемого элемента
     * @param value значение добавляемого элемента
     */

    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    /**
     * Получить элемент по индексу. Выбрасывается исключение, если запрашиваемый индекс находится за пределами списка.
     *
     * @param index индекс запрашиваемого элемента
     * @return запрашиваемый элемент
     */

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNode(index).value;
    }

    /**
     * Удалить элемент из списка по индексу. Если индекс выходит за пределы списка - выбрасывается исключение.
     * У удаляемого элемента стираются ссылки на соседей, а у них ссылки переписываются на друг друга. Узел перед
     * удаляемым узлом становится предидущим у следующего узла, а следующий за удаляемым, становится следующим у предидущего.
     * @param index индекс удаляемого элемента
     * @return удаляемый элемент
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T value = getNode(index).value;
        if (index == 0) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> current = getNode(index);
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return value;
    }

    /**
     * Очистить список. У коллекции стираются ссылки на голову и хвост, а размер устанавливается на 0.
     */

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Сортировка пузырьком. Сортируются элементы списка в порядке возрастания. Сравниваются соседние элементы и
     * меняются местами, если один больше другого, проходя
     * каждый раз по списку, до тех пор, пока все элементы не будут стоять в порядке возрастания.
     */

    public void sort() {
        if (size < 2) {
            return;
        }

        Node<T> current = head.next;

        while (current != null) {
            Node<T> temp = current;
            T key = temp.value;
            Node<T> prev = temp.prev;

            while (prev != null && ((Comparable<T>) prev.value).compareTo(key) > 0) {
                temp.value = prev.value;
                temp = prev;
                prev = temp.prev;
            }

            temp.value = key;
            current = current.next;
        }
    }

    /**
     * Проверка на заполненность списка
     * @return
     */

    private boolean isEmpty() {
        return size == 0;
    }

    /**
     * Поиск запрашиваемого узла. Если индекс запрашиваемого угла меньше половины списка, тогда курсор устанавливается
     * на голову списка, если индекс больше или равен половины списка, то курсор устанавливается на хвост списка.
     * Затем, путем сравнения индексов искомого и того, на котором курсор, находится искомый элемент, двигаясь в
     * противоположном направлении.
     * @param index
     * @return
     */

    private Node<T> getNode(int index) {
        Node<T> current = index < size / 2 ? head : tail;
        int i = index < size / 2 ? 0 : size - 1;
        while (i != index) {
            current = index < i ? current.next : current.prev;
            i = index < i ? i + 1 : i - 1;
        }
        return current;
    }

    /**
     * Подкласс для узла списка. Узел содержит значение и указатели на предыдущий и следующий узлы("голова" и "хвост").
     * @param <T>
     */

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
    public int size() {
        return size;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}