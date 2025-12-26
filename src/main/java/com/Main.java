package com;

import java.util.concurrent.Semaphore;

/**
 * Clase principal del programa. Crea los palillos, los asocia con un filósofo y arranca los hilos
 */
public class Main {

    public static void main(String[] args) {

        final int NUM_FILOSOFOS = 5;

        // Se crea un array de semáforos donde cada posición del array representa un palillo, de momento 5.
        Semaphore[] palillos = new Semaphore[NUM_FILOSOFOS];

        // Se crean los semáforos. Como -> new Semaphore(1) significa que el palillo solo puede usarse por un
        // filosofo a la vez y que si otro filosofo lo intenta coger se bloquea

        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            palillos[i] = new Semaphore(1);
        }

        /* Se crea una matriz donde dice que palillos necesita cada filosofo. es como si fuese una tabla:

                | Filósofo | Palillo izquierdo | Palillo derecho |
                | -------- | ----------------- | --------------- |
                | 0        | 0                 | 4               |
                | 1        | 1                 | 0               |
                | 2        | 2                 | 1               |
                | 3        | 3                 | 2               |
                | 4        | 4                 | 3               |
        */
        int[][] palillosFilosofo = {
                {0, 4},
                {1, 0},
                {2, 1},
                {3, 2},
                {4, 3}
        };

        /* Este bucle hace lo siguiente en cada repeticion:
            - Crea un filósofo
            - le da su indice, el array de los palillos y la matriz que necesita
            - Se inicia el hilo con start(), que crea el hilo, a la vez que el metodo run() se ejecuta en paralelo
         */
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            new Filosofo(i, palillos, palillosFilosofo).start();
        }
    }
}