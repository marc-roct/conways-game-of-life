package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

public class Cell implements Writable {
    //A cell representing an active cell with position x and y
    private int xpos;
    private int ypos;
    //Requires:
    //Modifies:
    //Effects:

    //Effects: creates a live cell
    public Cell(Integer xcoord, Integer ycoord) {
        this.xpos = xcoord;
        this.ypos = ycoord;
    }

    //Effects: returns x position of cell
    public int getXPos() {
        return xpos;
    }

    //Effects: returns y position of cell
    public int getYPos() {
        return ypos;
    }

    //Effects: cells are now equal if their x and y positions are the same
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return xpos == cell.getXPos() && ypos == cell.getYPos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(xpos, ypos);
    }

    //Effects: returns cells as json object with x position and y position
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", xpos);
        json.put("y", ypos);
        return json;
    }
}
