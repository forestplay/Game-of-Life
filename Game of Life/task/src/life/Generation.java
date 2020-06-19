package life;

import java.util.Random;

public class Generation {
    public static Universe randomNext(Universe firstGen) {
        Random random = new Random(System.currentTimeMillis());
        return new Universe(firstGen.getSize(), random.nextLong());
    }

    public static Universe next(Universe firstGen) {
        Universe nextGen = new Universe(firstGen.getSize());

        for (int x = 0; x < firstGen.getSize(); x++) {
            for (int y = 0; y < firstGen.getSize(); y++) {
                int neighbors = firstGen.countNeighbors(x, y);
                if (neighbors == 2 && firstGen.getCell(x, y).equals(Universe.ALIVE)) {
                    nextGen.setCell(x, y, Universe.ALIVE);
                } else if (neighbors == 3) {
                    nextGen.setCell(x, y, Universe.ALIVE);
                } else if (neighbors < 2 || neighbors > 3) {
                    nextGen.setCell(x, y, Universe.DEAD);
                }
            }
        }

        return nextGen;
    }

}
