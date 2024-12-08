package com.tercerotest.controller.tda.busqueda;
import java.util.Arrays;
import java.util.Random;

public class LinealBinaria {
    public static void main(String[] args) {
        // Generar un arreglo de 10,000 números aleatorios
        int[] numeros = generarArregloAleatorio(25000);
        int[] numerosSinOrdenar = numeros.clone(); // Crear una copia sin ordenar para la búsqueda lineal

        // Ordenar el arreglo para aplicar la búsqueda binaria
        Arrays.sort(numeros);

        // Mostrar los primeros 100 números del arreglo ordenado
        System.out.println("Primeros 1000 números ordenados:");
        imprimirNumeros(numeros, 1000);

        // Número a buscar (puedes cambiarlo o generarlo aleatoriamente)
        int numeroABuscar = numeros[new Random().nextInt(25000)]; // Elegir un número al azar del arreglo

        // Realizar la búsqueda binaria
        System.out.println("\nBuscando el número (Búsqueda Binaria): " + numeroABuscar);
        long inicioBinaria = System.nanoTime();
        int posicionBinaria = busquedaBinaria(numeros, numeroABuscar);
        long finBinaria = System.nanoTime();

        // Mostrar el resultado de la búsqueda binaria
        if (posicionBinaria != -1) {
            System.out.println("Número encontrado en la posición: " + posicionBinaria);
        } else {
            System.out.println("Número no encontrado.");
        }

        // Tiempo de ejecución de la búsqueda binaria
        System.out.println("Tiempo de ejecución de Búsqueda Binaria: " + (finBinaria - inicioBinaria) / 1_000_000.0 + " ms");

        // metodo de busqueda lineal 
        System.out.println("\nBuscando el número (Búsqueda Lineal): " + numeroABuscar);
        long inicioLineal = System.nanoTime();
        int posicionLineal = busquedaLineal(numerosSinOrdenar, numeroABuscar);
        long finLineal = System.nanoTime();
        
        if (posicionLineal != -1) {
            System.out.println("Número encontrado en la posición: " + posicionLineal);
        } 

        
        System.out.println("Tiempo de ejecución de Búsqueda Lineal: " + (finLineal - inicioLineal) / 1_000_000.0 + " ms");
    }

    // Generar un arreglo de números aleatorios
    public static int[] generarArregloAleatorio(int tamanio) {
        Random random = new Random();
        int[] arreglo = new int[tamanio];
        for (int i = 0; i < tamanio; i++) {
            arreglo[i] = random.nextInt(20000); // generador aleatoreo de numero 
        }
        return arreglo;
    }

    // BUSQUEDA BINARIA 
    public static int busquedaBinaria(int[] arreglo, int objetivo) {
        int inicio = 0;
        int fin = arreglo.length - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;

            if (arreglo[medio] == objetivo) {
                return medio; // 
            } else if (arreglo[medio] < objetivo) {
                inicio = medio + 1; // 
            } else {
                fin = medio - 1; // 
            }
        }

        return -1; // Número no encontrado
    }

    //BUSQUEDA LINEAL
    public static int busquedaLineal(int[] arreglo, int objetivo) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == objetivo) {
                return i; // devulve el numero encontrado
            }
        }
        return -1; // si no encuentra 
    }

    // IMPRIME LOS NUMEROS
    public static void imprimirNumeros(int[] arreglo, int cantidad) {
        for (int i = 0; i < Math.min(cantidad, arreglo.length); i++) {
            System.out.print(arreglo[i] + " ");
        }
        System.out.println();
    }
}
