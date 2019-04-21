public abstract class World {
    public int generation;
    protected Pattern pattern;

    public World(String format) throws PatternFormatException {
        pattern = new Pattern(format);
    }

    public World(Pattern pt) throws PatternFormatException {
        pattern = pt;
    }

    public World(World copyWorld) {
        generation = copyWorld.generation;
        pattern = copyWorld.pattern;
    }

    @Override
    public World clone() throws CloneNotSupportedException {
        World cloned = (World) super.clone();
        cloned.generation = generation;
        cloned.pattern = pattern;
        return cloned;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getWidth() {

        return pattern.getWidth();
    }

    public int getHeight() {

        return pattern.getHeight();
    }


    public int getGenerationCount() {
        return generation;
    }

    public void incrementGenerationCount() {
        generation++;

    }

    public void nextGeneration() {
        nextGenerationImpl();
        incrementGenerationCount();
    }

    public abstract void nextGenerationImpl();

    public abstract boolean getCell(int col, int row);

    public abstract void setCell(int col, int row, boolean value);

    public int countNeighbours(int col, int row) {
        int count = 0;
        int[][] neighbours = {
                {row - 1, col - 1},
                {row, col - 1},
                {row - 1, col},
                {row + 1, col},
                {row, col + 1},
                {row + 1, col + 1},
                {row - 1, col + 1},
                {row + 1, col - 1}
        };

        for (int i = 0; i < neighbours.length; i++) {
            if (row - 1 < 0 || col - 1 < 0) {
                continue;
            }
            if (getCell(neighbours[i][1], neighbours[i][0])) {
                count++;
            }

        }
        return count;
    }

    public boolean computeCell(int col, int row) {
        int countElements = this.countNeighbours(row, col);
        if (getCell(row, col)) {
            if (countElements < 2) {
                return false;

            }
            if (countElements == 2 || countElements == 3) {
                return true;

            }
            return false; // countElements > 3
        }

        if (countElements == 3) {
            return true;
        }
        return false;

    }


}

