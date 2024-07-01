package ru.savkaev;

/**
 * Массив с динамическим размером и индексацией. Изначально список содержит 10 элементов, при превышении этого
 * размера, он увеличивается в 2 раза.
 * @param <E>
 */

public class CustomArrayList<E>{
    private static final int INITIAL_CAP = 10;
    private Object[] elements;
    private int size;

    public CustomArrayList() {
        elements = new Object[INITIAL_CAP];
        size = 0;
    }

    /**
     * Увеличение размера массива при переполнении.
     */

    private void increaseCap() {
        int newCap = elements.length * 2;
        elements = new Object[newCap];
    }

    /**
     * Добавление элемента в конец списка.
     * @param element добавляемый элемент
     */
    public void add(E element){
        if(size == elements.length) {
            increaseCap();
        }
        elements[size++] = element;
    }

    /**
     * Добавление элемента в список по индексу. Для добавления нового элемента в массив, часть массива сдвигается в
     * в сторону, начиная от индекса для вставки. После этого добавляется элемент и увеличивается размер
     * @param element добавляемый элемент
     * @param index индекс добавляемого элемента
     */

    public void add(E element, int index){
        if(size == elements.length) {
            increaseCap();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Получить элемент по индексу
     * @param index индекс искомого элемента
     * @return искомый элемент
     */
    public E get(int index){
        checkIndexBounds(index);
        try {
            return (E) elements[index];
        } catch (ClassCastException e) {
            System.err.println("Ошибка приведения типов: " + e.getMessage());
            return null;
        }
    }

    /**
     * Удаление элемента массива по индексу. Сдвигает элементы массива влево, начиная от взятого индекса.
     * @param index
     */
    public void remove(int index) {
        checkIndexBounds(index);
        int toRemove = size - index - 1;
        if(toRemove > 0) {
            System.arraycopy(elements, index + 1, elements, index, toRemove);
        }
        elements[--size] = null;
    }

    /**
     * Очистить массив. Все элементы удаляются с помощью цикла for.
     */

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Сортировка массива. Если размер массива превышает 100, то используется быстрая сортировка, иначе - пузырьковая.
     */

    public void sort(){
        if (size > 100){
            quickSort(0, size - 1);
            System.out.println("Quick sort done");
        } else {
            bubbleSort(0, size - 1);
            System.out.println("Bubble sort done");
        }
    }

    /**
     * Быстрая сортировка. Массив делится на части и сортируется рекурсивно
     * @param low
     * @param high
     */

    private void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    /**
     * Метод для сортировки.
     * @param low
     * @param high
     * @return
     */

    private int partition(int low, int high) {
        Object pivot = elements[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (((Comparable<Object>) elements[j]).compareTo(pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Метод для обмена двух элементов массива местами.
     * @param i первый элемент
     * @param j второй элемент
     */
    private void swap(int i, int j) {
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    /**
     * Пузырьковая сортировка. Элементы поочередно сравниваются с соседями и меняются местами в соответствии с критериями
     * сравнения. Повторяется до тех пор, пока все элементы не будут отсортированы.
     * @param low начало последовательности
     * @param high конец последовательности
     */

    private void bubbleSort(int low, int high){
        for(int i = low; i < high; i++){
            for(int j = low; j < high; j++){
                if(((Comparable) elements[j]).compareTo(elements[j + 1]) > 0){
                    Object temp = elements[j];
                    elements[j] = elements[j + 1];
                    elements[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Проверка индекса на выход за пределы массива.
     * @param index
     */


    private void checkIndexBounds(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Получить размер массива
     * @return int размер
     */
    public int size(){
        return size;
    }

    /**
     * Удалить все нулевые элементы массива
     *
     */
    public void trim() {
        for (int i = size - 1; i >= 0; i--) {
            if (this.get(i) == null) {
                this.remove(i);
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }


}
