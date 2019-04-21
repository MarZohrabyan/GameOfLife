import java.lang.reflect.Array;

public class ArrayWorld extends World {

    private boolean[][] world;


    public ArrayWorld(String parameters) throws Exception {
        super(parameters);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
    }

    public ArrayWorld(ArrayWorld arr) throws Exception {
        super(arr);
        world = new boolean[getHeight()][getWidth()];
        world = arr.world;
    }

    @Override
    public ArrayWorld clone() throws CloneNotSupportedException {
        ArrayWorld cloned = (ArrayWorld) super.clone();
        cloned.world = new boolean[getHeight()][getWidth()];

        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                cloned.world[i][j] = this.world[i][j];
            }
        }
        return cloned;
    }

    public ArrayWorld(Pattern pt) throws Exception {
        super(pt);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
    }
    public void optimize(){
        boolean[] deadCells = new boolean[getWidth()];
        for(int i = 0; i < getHeight();i++){
            boolean isRowCellsDead = true;
            for(int j = 0; j < getWidth(); j++){
                if(world[i][j]){
                    isRowCellsDead = false;
                    break;
                }
            }
            if (isRowCellsDead){
                world[i] = deadCells;
            }
        }
    }

    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight()) {
            return false;
        }
        if (col < 0 || col >= getWidth()) {
            return false;
        }
        return world[row][col];

    }


    public void setCell(int col, int row, boolean value) {
        if (row < 0 || row >= pattern.getHeight()) {
            return;
        }
        if (col < 0 || col >= pattern.getWidth()) {
            return;
        }
        world[row][col] = value;
    }

    public void nextGenerationImpl() {
        boolean[][] newWorld = new boolean[pattern.getWidth()][pattern.getHeight()];
        for (int i = 0; i < newWorld.length; i++) {
            for (int j = 0; j < newWorld[i].length; j++) {
                newWorld[i][j] = computeCell(j, i);
            }
        }
        world = newWorld;

    }


}
