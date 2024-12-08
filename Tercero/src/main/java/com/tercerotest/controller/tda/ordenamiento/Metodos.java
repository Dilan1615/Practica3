package com.tercerotest.controller.tda.ordenamiento;

import java.util.Random;
import java.util.Arrays;

public class Metodos {
    public static void main(String[] args) {        
        int[] vecShell = generarVectorAleatorio(20000);
        int[] vecMerge = Arrays.copyOf(vecShell, vecShell.length);
        int[] vecQuick = Arrays.copyOf(vecShell, vecShell.length);

        // Shell Sort
        System.out.println("Metodo Shell Sort (Primeros 100 Datos):");
        imprimirPrimeros(vecShell, 100);

        long inicioShell = System.nanoTime();
        vecShell = ordenacionShell(vecShell);
        long finShell = System.nanoTime();

        System.out.println("\nOrdenados (Shell Sort):");
        imprimirPrimeros(vecShell, 100);
        System.out.println("\nTiempo de ejecución (Shell Sort): " + (finShell - inicioShell) / 1_000_000_000.0 + " segundos");

        // Merge Sort
        System.out.println("\nMetodo Merge Sort (Primeros 100 Datos):");
        imprimirPrimeros(vecMerge, 100);

        long inicioMerge = System.nanoTime();
        ordenacionMergeSort(vecMerge);
        long finMerge = System.nanoTime();

        System.out.println("\nOrdenados (Merge Sort):");
        imprimirPrimeros(vecMerge, 100);
        System.out.println("\nTiempo de ejecución (Merge Sort): " + (finMerge - inicioMerge) / 1_000_000_000.0 + " segundos");

        // Quick Sort
        System.out.println("\nMetodo Quick Sort (Primeros 100 Datos):");
        imprimirPrimeros(vecQuick, 100);

        long inicioQuick = System.nanoTime();
        ordenacionRapida(vecQuick);
        long finQuick = System.nanoTime();

        System.out.println("\nOrdenados (Quick Sort):");
        imprimirPrimeros(vecQuick, 100);
        System.out.println("\nTiempo de ejecución (Quick Sort): " + (finQuick - inicioQuick) / 1_000_000_000.0 + " segundos");
    }

    public static int[] generarVectorAleatorio(int tamanio) {
        Random random = new Random();
        int[] vec = new int[tamanio];
        for (int i = 0; i < tamanio; i++) {
            vec[i] = random.nextInt(20000);
        }
        return vec;
    }

    public static int[] ordenacionShell(int vec[]) {
        final int N = vec.length;
        int k = N / 2;
        while (k >= 1) {
            for (int i = 0; i < k; i++) {
                for (int j = k + i; j < N; j += k) {
                    int v = vec[j];
                    int n = j - k;
                    while (n >= 0 && vec[n] > v) {
                        vec[n + k] = vec[n];
                        n -= k;
                    }
                    vec[n + k] = v;
                }
            }
            k /= 2;
        }
        return vec;
    }

    public static void ordenacionMergeSort(int vec[]) {
        if (vec.length <= 1) return;
        int mitad = vec.length / 2;
        int izq[] = Arrays.copyOfRange(vec, 0, mitad);
        int der[] = Arrays.copyOfRange(vec, mitad, vec.length);
        ordenacionMergeSort(izq);
        ordenacionMergeSort(der);
        combinarVector(vec, izq, der);
    }

    public static void combinarVector(int v[], int izq[], int der[]) {
        int i = 0, j = 0;
        for (int k = 0; k < v.length; k++) {
            if (i >= izq.length) {
                v[k] = der[j++];
                continue;
            }
            if (j >= der.length) {
                v[k] = izq[i++];
                continue;
            }
            if (izq[i] < der[j]) {
                v[k] = izq[i++];
            } else {
                v[k] = der[j++];
            }
        }
    }

    public static void ordenacionRapida(int vec[]) {
        final int N = vec.length;
        quickSort(vec, 0, N - 1);
    }

    public static void quickSort(int vec[], int inicio, int fin) {
        if (inicio >= fin) return;
        int pivote = vec[inicio];
        int elemIzq = inicio + 1;
        int elemDer = fin;
        while (elemIzq <= elemDer) {
            while (elemIzq <= fin && vec[elemIzq] < pivote) {
                elemIzq++;
            }
            while (elemDer > inicio && vec[elemDer] >= pivote) {
                elemDer--;
            }
            if (elemIzq < elemDer) {
                int temp = vec[elemIzq];
                vec[elemIzq] = vec[elemDer];
                vec[elemDer] = temp;
            }
        }
        if (elemDer > inicio) {
            int temp = vec[inicio];
            vec[inicio] = vec[elemDer];
            vec[elemDer] = temp;
        }
        quickSort(vec, inicio, elemDer - 1);
        quickSort(vec, elemDer + 1, fin);
    }

    public static void imprimirPrimeros(int vec[], int cantidad) {
        for (int i = 0; i < Math.min(cantidad, vec.length); i++) {
            System.out.print(vec[i] + " ");
        }
        System.out.println();
    }
}
