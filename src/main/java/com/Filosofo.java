package com;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Filosofo extends Thread {

    private final int indice;
    private final Semaphore[] semaforoPalillo;
    private final int[][] palillosFilosofo;
    private final Random random = new Random();



    //Constructor
    public Filosofo(int miIndice, Semaphore[] semaforoPalillo, int[][] palillosFilosofo) {
        this.indice = miIndice;
        this.semaforoPalillo = semaforoPalillo;
        this.palillosFilosofo = palillosFilosofo;
    }

    /**
     * Metodo que representa el comportamiento del programa: pensar -> comer -> pensar...
     */
    @Override
    public void run() {
        while (true) {
            pensar();
            comer();
        }
    }

    /**
     * Metodo que muestra un mensaje y duerme el hilo un tiempo aleatorio para simular la acción
     */
    public void pensar() {
        System.out.println("Filósofo " + indice + " está pensando");
        try {
            Thread.sleep(random.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método en el que el filósofo índice que tiene hambre, intenta coger los palillos y si no puede se bloquea con
     * un semaphore. Después come y libera los palillos
     * Cada palillo tiene un semáforo con un solo permiso y garantiza que solo un filósofo puede usar un palillo a la
     * vez
     */
    public void comer() {
        int palilloIzq = palillosFilosofo[indice][0];
        int palilloDer = palillosFilosofo[indice][1];

        System.out.println("Filósofo " + indice + " está hambriento");

        try {
            semaforoPalillo[palilloIzq].acquire();
            semaforoPalillo[palilloDer].acquire();

            System.out.println("Filósofo " + indice + " está comiendo "
                    + "(palillos " + palilloIzq + " y " + palilloDer + ")");

            Thread.sleep(random.nextInt(1000) + 500);

            System.out.println("Filósofo " + indice + " ha terminado de comer "
                    + "(libera palillos " + palilloIzq + " y " + palilloDer + ")");

            semaforoPalillo[palilloIzq].release();
            semaforoPalillo[palilloDer].release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}