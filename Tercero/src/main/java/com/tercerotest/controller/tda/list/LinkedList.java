package com.tercerotest.controller.tda.list;

import java.lang.reflect.Method;

import com.tercerotest.controller.excepcion.ListEmptyException;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {

        addLast(info);
    }

    // ********** GET */
    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }

    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {

            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {

            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }

    public LinkedList<E> order(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++) {
                    E aux = lista[i]; // valor a ordenar
                    int j = i - 1; // índice anterior
                    while (j >= 0 && atrribute_compare(attribute, lista[j], aux, type)) {
                        lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                    }
                    lista[j + 1] = aux; // inserta el valor en su posición correcta
                }
                this.toList(lista);
            }
        }
        return this;
    }

    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
            default:
                // mayor a menor
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
        }

    }

    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {
            return method.invoke(a);
        }

        return null;
    }

    // METODO QUICKSORT
    public LinkedList<E> QuickSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray(); // Convertimos la lista en un arreglo
            quickSort(lista, 0, lista.length - 1, attribute, type); // Llamamos al QuickSort
            this.toList(lista); // Convertimos el arreglo de nuevo a la lista
        }
        return this;
    }

    private void quickSort(E[] vec, int inicio, int fin, String attribute, Integer type) throws Exception {
        if (inicio < fin) {
            // Realizamos la partición y obtenemos el índice del pivote
            int indicePivote = partition(vec, inicio, fin, attribute, type);

            quickSort(vec, inicio, indicePivote - 1, attribute, type);
            quickSort(vec, indicePivote + 1, fin, attribute, type);
        }
    }

    private int partition(E[] vec, int inicio, int fin, String attribute, Integer type) throws Exception {
        E pivote = vec[fin]; // se usa el ultimo elemento como pivote
        int i = inicio - 1;

        // Recorrido de la lista para reordenar elementos menores al pivote a la
        // izquierda
        for (int j = inicio; j < fin; j++) {
            if (atrribute_compare(attribute, vec[j], pivote, type)) {
                i++;

                E temp = vec[i];
                vec[i] = vec[j];
                vec[j] = temp;
            }
        }

        E temp = vec[i + 1];
        vec[i + 1] = vec[fin];
        vec[fin] = temp;

        return i + 1;
    }

    // METODO DE MERGESORT
    public LinkedList<E> mergeSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();// convierte la lista en arreglo
            mergeSortRecursive(lista, 0, lista.length - 1, attribute, type);
            this.toList(lista); // Actualiza la lista ya ordenada
        }
        return this;
    }

    private void mergeSortRecursive(E[] arr, int left, int right, String attribute, Integer type) throws Exception {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSortRecursive(arr, left, middle, attribute, type);// izquierda
            mergeSortRecursive(arr, middle + 1, right, attribute, type);// derecha

            merge(arr, left, middle, right, attribute, type);// las une cuando ya acabo de ordenar
        }
    }

    private void merge(E[] arr, int left, int middle, int right, String attribute, Integer type) throws Exception {
        // tamanio de los subarrays
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // subarrays temporales
        E[] leftArray = (E[]) new Object[n1];
        E[] rightArray = (E[]) new Object[n2];

        // copia los datos en subarrays temporales
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[middle + j + 1];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (!atrribute_compare(attribute, leftArray[i], rightArray[j], type)) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;

        }

        // Copiar los elementos restantes
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
        }
        k++;
    }

    public LinkedList<E> shellSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray();
            final int n = lista.length;
            int k = n / 2; // se encarga del tamanio de los subvectores

            // Ciclo principal del algoritmo ShellSort
            while (k >= 1) {
                for (int i = 0; i < k; i++) {
                    for (int j = k + i; j < n; j += k) {
                        // j= 3;j<6;j =3+3
                        E v = lista[j];// como es 3 
                        int N = j - k;
                        while (N >= 0 && atrribute_compare(attribute, lista[N], v, type)) {
                            lista[N + k] = lista[N];// desplaza el valor hacia adelante
                            N -= k;// desplaza hacia atras
                        }
                        lista[N + k] = v;// nueva posicion de v
                    }
                }
                k /= 2;
            }
            this.toList(lista);// se convierte denuevo en lista 
        }
        return this; // Retornar la lista ordenada
    }

}
