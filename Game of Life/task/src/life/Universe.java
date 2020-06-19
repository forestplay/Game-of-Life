package life;

import java.util.ArrayList;
import java.util.Random;

public class Universe {
    public static final String DEAD = " ";
    public static final String ALIVE = "O";

    private final ArrayList<ArrayList<String>> data;
    private final int size;

    public Universe(int size, long seed) {
        this.size = size;
        this.data = new ArrayList<>();
        Random random = new Random(seed);

        for (int x = 0; x < size; x++) {
            ArrayList<String> row = new ArrayList<>();
            for (int y = 0; y < size; y++) {
                row.add((random.nextInt(2) == 0) ? DEAD : ALIVE);
            }
            data.add(row);
        }
    }

    public Universe(int size) {
        this.size = size;
        this.data = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            ArrayList<String> row = new ArrayList<>();
            for (int y = 0; y < size; y++) {
                row.add(DEAD);
            }
            data.add(row);
        }
    }

    public Universe(String initialState) {
        this.size = (int) Math.sqrt(initialState.length());
        if (this.size * this.size != initialState.length())
            System.err.println("Error with init string!");
        this.data = new ArrayList<>();

        for (int x = 0; x < size; x++) {
            ArrayList<String> row = new ArrayList<>();
            for (int y = 0; y < size; y++) {
                if (initialState.charAt(x * this.size + y) == 'O') {
                    row.add(ALIVE);
                } else row.add(DEAD);
            }
            data.add(row);
        }
    }

    public void print(boolean withAnnotation) {
        if (withAnnotation) annotationHeader();
        for (int x = 0; x < this.size; x++) {
            if (withAnnotation) System.out.print(x + ".");
            for (int y = 0; y < this.size; y++) {
                System.out.print(data.get(x).get(y));
            }
            System.out.println();
        }
    }

    public void printNeighbors(boolean withAnnotation) {
        if (withAnnotation) annotationHeader();
        for (int x = 0; x < size; x++) {
            if (withAnnotation) System.out.print(x + ".");
            for (int y = 0; y < size; y++) {
                System.out.print(this.countNeighbors(x, y));
            }
            System.out.println();
        }
    }

    private void annotationHeader() {
        System.out.print("  ");
        for (int y = 0; y < size; y++) System.out.print(y);
        System.out.print("\n  ");
        for (int y = 0; y < size; y++) System.out.print(".");
        System.out.println();
    }

    public int getSize() {
        return this.size;
    }

    public String getCell(int x, int y) {
        return data.get(x).get(y);
    }

    public void setCell(int x, int y, String value) {
        data.get(x).set(y, value);
    }

    public int countNeighbors(int i, int j) {
        if (this.size == 1) {
            if (data.get(0).get(0).equals(DEAD)) return 0;  // dead means no neighbors
            return 8;                                       // alive means many neighbors
        }

        int boundary = this.size - 1;   // max index for x & y of this.data

        // Create array of neighbor's coordinates
        int[][] neighbors;
        // Check the corners then edges else body
        if (i == 0 && j == 0) {  // NW corner
            neighbors = new int[][]{
                    {boundary, boundary}, {boundary, 0}, {boundary, 1},
                    {0, boundary},                       {0, 1},
                    {1, boundary},        {1, 0},        {1, 1}};
        } else if (i == 0 && j == boundary) {  // NE corner
            neighbors = new int[][]{
                    {boundary, j - 1}, {boundary, j}, {boundary, 0},
                    {0, j - 1},                       {0, 0},
                    {1, j - 1},        {1, j},        {1, 0}};
        } else if (i == (data.size() - 1) && j == boundary) {  // SE corner
            neighbors = new int[][]{
                    {i - 1, j - 1}, {i - 1, j}, {i - 1, 0},
                    {i, j - 1},                 {i, 0},
                    {0, j - 1},     {0, j},     {0, 0}};
        } else if (i == boundary && j == 0) {  // SW corner
            neighbors = new int[][]{
                    {i - 1, boundary}, {i - 1, 0}, {i - 1, 1},
                    {i, boundary},                 {i, 1},
                    {0, boundary},     {0, 0},     {0, 1}};
        } else if (i == 0) {  // north edge
            neighbors = new int[][]{
                    {boundary, j - 1}, {boundary, j}, {boundary, j + 1},
                    {0, j - 1},        {0, j + 1},
                    {1, j - 1},        {1, j},        {1, j + 1}};
        } else if (j == boundary) { // east edge
            neighbors = new int[][]{
                    {i - 1, j - 1}, {i - 1, j}, {i - 1, 0},
                    {i, j - 1},                 {i, 0},
                    {i + 1, j - 1}, {i + 1, j}, {i + 1, 0}};
        } else if (i == boundary) {  // south edge
            neighbors = new int[][]{
                    {i - 1, j - 1}, {i - 1, j}, {i - 1, j + 1},
                    {i, j - 1},                 {i, j + 1},
                    {0, j - 1},     {0, j},     {0, j + 1}};
        } else if (j == 0) {  // west edge
            neighbors = new int[][]{
                    {i - 1, boundary}, {i - 1, 0}, {i - 1, 1},
                    {i, boundary},                 {i, j + 1},
                    {i + 1, boundary}, {i + 1, 0}, {i + 1, 1}};
        } else { // location in body of matrix
            neighbors = new int[][]{
                    {i - 1, j - 1}, {i - 1, j}, {i - 1, j + 1},
                    {i, j - 1},                 {i, j + 1},
                    {i + 1, j - 1}, {i + 1, j}, {i + 1, j + 1}};
        }

        int count = 0;
        for (int x = 0; x < 8; x++) {
            if (getCell(neighbors[x][0], neighbors[x][1]).equals(ALIVE)) count++;
        }
        return count;
    }

    public int livingCells() {
        int count = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (getCell(x, y).equals(ALIVE)) count++;
            }
        }
        return count;
    }

}
