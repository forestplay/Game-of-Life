package life;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int universeSize = scanner.nextInt();
        long seed = System.currentTimeMillis();             // scanner.nextLong();
        int generations = 100;                              // scanner.nextInt();
        boolean withAnnotation = false;
        boolean showNeighbors = false;
        boolean showEachGeneration = true;

        Universe universe = new Universe(universeSize, seed);
        if (showEachGeneration) {
            clearConsole();
            universe.print(withAnnotation);
        }

        for (int g = 1; g <= generations; g++) {
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            universe = Generation.next(universe);
            if (showEachGeneration) {
                clearConsole();
                System.out.println("Generation #" + (g));
                System.out.println("Alive: " + universe.livingCells());
                universe.print(withAnnotation);
            }
            if (showNeighbors) {
                System.out.println();
                universe.printNeighbors(withAnnotation);
                System.out.println();
            }

        }
    }

    public static void clearConsole() {
//        try {
//            if (System.getProperty("os.name").contains("Windows"))
//                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
//            else
//                Runtime.getRuntime().exec("clear");
//        }
//        catch (IOException | InterruptedException e) {}
    }

}

