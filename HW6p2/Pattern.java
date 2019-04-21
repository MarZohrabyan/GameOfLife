import java.io.*;

public class Pattern implements Comparable<Pattern> {

    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;


    public Pattern(String format) throws PatternFormatException {
        String[] stringSplit = format.split(":");


        if (format.equals("")) {
            throw new PatternFormatException("Please specify a pattern. ");
        } else if (stringSplit.length != 7) {
            throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found "
                    + stringSplit.length + ").");
        }
        name = stringSplit[0];
        author = stringSplit[1];
        try {
            width = Integer.parseInt(stringSplit[2]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number ("
                    + stringSplit[2] + " given).");
        }
        try {
            height = Integer.parseInt(stringSplit[3]);
        } catch (Exception e) {
            throw new PatternFormatException(
                    "Invalid pattern format: Could not interpret the height field as a number (" + stringSplit[3]
                            + " given).");
        }
        try {
            startCol = Integer.parseInt(stringSplit[4]);
        } catch (Exception e) {
            throw new PatternFormatException(
                    "Invalid pattern format: Could not interpret the startCol field as a number (" + stringSplit[4]
                            + " given).");
        }
        try {
            startRow = Integer.parseInt(stringSplit[5]);
        } catch (Exception e) {
            throw new PatternFormatException(
                    "Invalid pattern format: Could not interpret the startRow field as a number (" + stringSplit[5]
                            + " given).");
        }
        cells = stringSplit[6];
    }

    public String toString(){
        this.name = name;
        this.author = author;
        return name+(author);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public String getCells() {
        return cells;
    }

    public void setCells(String cells) {
        this.cells = cells;
    }

    public int compareTo(Pattern o) {
        return this.name.compareTo(o.name);
//            if (this.name.compareTo(o.name) > 1)
//                return 1;//this.name precedes o.name
//            if (this.name.compareTo(o.name) == 0)
//                return 0;//they are equal
//
//        return -1; //o.name precedes this.name
    }

    public void initialise(World world) throws PatternFormatException {
        String[] cellParts = this.cells.split(" ");
        for (int i = 0; i < cellParts.length; i++) {
            for (int l = 0; l < cellParts[i].length(); l++) {
                if (((!(cellParts[i].charAt(l) == '1'))) && (!(cellParts[i].charAt(l) == '0'))) {
                    throw new PatternFormatException("Invalid pattern format: Malformed pattern.");
                }
                if (cellParts[i].charAt(l) == '1') {
                    world.setCell(i + startRow, l + startCol, true);
                } else {
                    world.setCell(i + startRow, l + startCol, false);
                }

            }
        }
    }
}


