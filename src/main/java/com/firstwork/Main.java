package com.firstwork;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            runGame(sc);
        }
    }

    public static void runGame(Scanner sc) {
        String caracter = null;
        int intentos = 6;
        String wordHint = "";
        char[] useChars = new char[33];
        int useCharsCounter = 0;
        boolean ejecutarFuncion = false;

        String[] diccionario = {"casa", "perro", "jardin", "viaje", "computadora", "musica", "gato", "chocolate", "felicidad", "avion", "montaña"};
        
        Random random = new Random();
        String word = diccionario[random.nextInt(diccionario.length)];
        char[] userWord = new char[word.length()];

        for (int i = 0; i < word.length(); ++i) {
            wordHint = MessageFormat.format("{0}_ ", wordHint);
        }

        while (true) {
            clearConsole();

            System.out.println("Juego de ahorcado:\n");

            if (ejecutarFuncion) {
                for (int i = 0; i < word.length(); ++i) {
                    if (word.charAt(i) == userWord[i]) {
                        char[] wordHintArray = wordHint.toCharArray();
                        wordHintArray[i * 2] = userWord[i];
                        wordHint = new String(wordHintArray);
                    }
                }
            }

            System.out.println(wordHint);
            System.out.println("\nIngrese un caracter:");
            caracter = sc.nextLine();

            String useCharsString = new String(useChars);

            if (caracter.length() != 1) {
                ejecutarFuncion = false;
                clearConsole();
                System.out.println("Debe ingresar exactamente un caracter.");
                esperarTecla(sc);
                continue;
            } else if (!caracter.matches("[a-zñáéíóúü\\s]*")) {
                ejecutarFuncion = false;
                clearConsole();
                System.out.println("Debe ingresar un caracter del alfabeto");
                esperarTecla(sc);
                continue;
            } else if (useCharsString.contains(caracter)) {
                ejecutarFuncion = false;
                clearConsole();
                System.out.println("esta string ya se encuentra en uso");
                esperarTecla(sc);
                continue;
            } else {
                caracter = caracter.toLowerCase();
                useChars[useCharsCounter] = caracter.charAt(0);
                ++useCharsCounter;
            }

            if (word.contains(caracter)) {
                for (int i = 0; i < word.length(); ++i) {
                    String currentChar = word.substring(i, i + 1);

                    if (currentChar.equals(caracter)) {
                        userWord[i] = caracter.charAt(0);
                    }
                }
                ejecutarFuncion = true;
            } else {
                ejecutarFuncion = false;
                --intentos;
                clearConsole();
                System.out.println(MessageFormat.format("te quedan {0} intentos", intentos));
                esperarTecla(sc);
                continue;
            }

            String userWordAsString = new String(userWord);
            if (userWordAsString.equals(word)) {
                clearConsole();
                System.out.println("felicidades has ganado \nten una rosa 🌹");
                esperarTecla(sc);
                break;
            } else if (intentos == 0) {
                clearConsole();
                System.out.println("el juego se ha acabado \ntremendo manco :,v \n la palabra era " + word);
                esperarTecla(sc);
                break;
            }
        }
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void esperarTecla(Scanner scanner) {
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }
}