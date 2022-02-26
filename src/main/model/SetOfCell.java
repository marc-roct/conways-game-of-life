package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class SetOfCell implements Writable {
    //A set where we store all active cells
    private int xdimension = 20;
    private int ydimension = 20;
    private Set<Cell> setOfCell;
    private List<Cell> listOfNeighbours;
    private Map<Cell, Integer> countMap;

    public SetOfCell() {
        setOfCell = new HashSet<>();
        listOfNeighbours = new LinkedList<>();
        countMap = new HashMap<>();
    }

    //Effects: returns the size of the list
    public int length() {
        return setOfCell.size();
    }

    public List<Cell> getListOfNeighbours() {
        return listOfNeighbours;
    }

    public Map<Cell, Integer> getCountMap() {
        return countMap;
    }

    //Modifies: this
    //Effects: adds an alive cell to the set and removes cell if it is already in set
    public void addCell(int x, int y) {
        EventLog.getInstance().logEvent(
                new Event("Added cell at position " + x + "," + y + " to the board"));
        Cell cell = new Cell(x, y);
        if (setOfCell.contains(cell)) {
            removeCell(x, y);
        } else {
            setOfCell.add(cell);
        }
    }

    public void setXdimension(int xdimension) {
        this.xdimension = xdimension;
    }

    public void setYdimension(int ydimension) {
        this.ydimension = ydimension;
    }

    public int getXdimension() {
        return xdimension;
    }

    public int getYdimension() {
        return ydimension;
    }

    //Modifies: this
    //Effects: adds a cell to the list without removing cell if it is already in
    public void addCellFunk(int x, int y) {
        EventLog.getInstance().logEvent(
                new Event("Added cell at position " + x + "," + y + " to the board"));
        Cell cell = new Cell(x, y);
        setOfCell.add(cell);

    }

    //Modifies: this
    //Effects: removes the specified cell from the list
    public void removeCell(int x, int y) {
        EventLog.getInstance().logEvent(
                new Event("Removed cell at position " + x + "," + y + " to the board"));
        Cell cell = new Cell(x, y);
        setOfCell.remove(cell);
        listOfNeighbours.remove(cell);
    }

    //Effects: returns a coordinate value that wraps around the board if at an edge
    private int wrapAround(int coordinate, int max) {
        return ((coordinate % max) + max) % max;
    }

    //Effects: returns true if alive or dead cell with same coordinates exists in list
    public boolean contains(int x, int y) {
        Cell cell = new Cell(x, y);
        return setOfCell.contains(cell);
    }

    //Modifies: this
    //Effects: adds all 8 neighbouring cells into listOfNeighbours
    public void addNeighboursForCell(Cell cell) {
        int x = cell.getXPos();
        int y = cell.getYPos();
        int xminusone = wrapAround((x - 1), xdimension);
        int xplusone = wrapAround((x + 1), xdimension);
        int yminusone = wrapAround((y - 1), ydimension);
        int yplusone = wrapAround((y + 1), ydimension);
        Cell deadCell = new Cell(xminusone, yminusone);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(x, yminusone);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(xplusone, yminusone);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(xminusone, y);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(xplusone, y);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(xminusone, yplusone);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(x, yplusone);
        listOfNeighbours.add(deadCell);
        deadCell = new Cell(xplusone, yplusone);
        listOfNeighbours.add(deadCell);

    }

    //Modifies: this
    //Effects: produces the next list of cells after 1 step has passed
    public void nextCells() {
        EventLog.getInstance().logEvent(
                new Event("Generated next board of cells."));
        addNeighbours();
        countToMap();
        setOfCell = generateNextCells();
        listOfNeighbours = new LinkedList<>();
        countMap = new HashMap<>();
    }

    //Modifies: this
    //Effects: adds all neighbours for all active cells
    public void addNeighbours() {
        for (Cell tempCell: setOfCell) {
            addNeighboursForCell(tempCell);
        }
    }

    //Modifies: this
    //Effects: adds cells from listOfNeighbours to a map as a key with their value, an int, being the number of times
    //         they appeared in the list
    public void countToMap() {
        for (Cell cellToCount: listOfNeighbours) {
            countMap.merge(cellToCount, 1, Integer::sum);
            // taken from https://stackoverflow.com/questions/81346/most-efficient-way-to-increment-a-map-value-in-java
        }
    }

    //Modifies: this
    //Effects: generates the next iteration of cells pertaining to the rules of Conway's Game Of Life
    public Set<Cell> generateNextCells() {
        Set<Cell> newSet = new HashSet<>();
        for (Map.Entry<Cell, Integer> entry: countMap.entrySet()) {
            Cell cell = entry.getKey();
            if (setOfCell.contains(cell)) {
                if (entry.getValue() == 2 || entry.getValue() == 3) {
                    newSet.add(cell);
                }
            } else {
                if (entry.getValue() == 3) {
                    newSet.add(cell);
                }
            }
        }
        return newSet;
    }

    //Effects: creates a json object with key "cell" and "value" jsonArray
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cell", cellsToJson());
        return json;
    }

    // EFFECTS: returns Cells in this ListOfCell as a JSON array
    private JSONArray cellsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cell c : setOfCell) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

}
