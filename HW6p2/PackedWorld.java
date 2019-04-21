import java.io.IOException;

public class PackedWorld extends World {

    private long world;

    public PackedWorld(String parameters) throws Exception {
        super(parameters);
        if (getWidth() * getHeight() > 64) {
            throw new IOException("Can't have bigger than 8X8 board");
        }
        getPattern().initialise(this);
    }

    public PackedWorld(Pattern pt) throws PatternFormatException {
        super(pt);

        getPattern().initialise(this);
    }

    public PackedWorld(PackedWorld pack) {
        super(pack);
        world = 0L;
        world = pack.world;
    }

    @Override
    public PackedWorld clone() throws CloneNotSupportedException {
        PackedWorld cloned = (PackedWorld) super.clone();
        cloned.world = 0;
        for (int i = 0; i < cloned.getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                cloned.setCell(i, j, true);
            }
        }
        return cloned;
    }

    public boolean getCell(int row, int col) {
        if (row < 0 || row >= pattern.getHeight()) {
            return false;
        }
        if (col < 0 || col >= pattern.getWidth()) {
            return false;

        }
        if (((world >>> (col * pattern.getHeight() + row)) & 1L) == 1L)
            return true;
        else
            return false;
    }

    public void setCell(int i, int j, boolean value) {
        if (i < 0 || j < 0) {
            return;
        }
        if (value) {
            world = 1 << (i * pattern.getWidth() + j) | world;
        } else {
            world = (~(1 << (i * pattern.getHeight() + j)) & world);
        }


    }

    public void nextGenerationImpl() {
        long newWorld = 0L;
        for (int i = 0; i < pattern.getHeight(); i++) {
            for (int j = 0; j < pattern.getWidth(); j++) {
                if (computeCell(j, i)) {
                    newWorld = 1L << (j * pattern.getWidth() + i) | newWorld;
                }
            }
        }
        world = newWorld;

    }


}
