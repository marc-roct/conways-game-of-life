package Test;

import model.Cell;
import model.SetOfCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SetOfCellTest {
    private SetOfCell testSet;

    @BeforeEach
    public void runBefore() {
        testSet = new SetOfCell();
    }

    @Test
    public void addCellTestToEmptyList() {
        testSet.addCell(0,0);
        assertEquals(testSet.length(), 1);
    }

    @Test
    public void getXTest() {
        testSet.setXdimension(10);
        assertEquals(10, testSet.getXdimension());
    }

    @Test
    public void getYTest() {
        testSet.setYdimension(10);
        assertEquals(10, testSet.getYdimension());
    }

    @Test
    public void addCellFunkTestAlreadyInSet() {
        testSet.addCellFunk(0,0);
        assertTrue(testSet.contains(0,0));
        testSet.addCellFunk(0,0);
        assertTrue(testSet.contains(0,0));
    }

    @Test
    public void addCellFunkTestTwoAdjacentCells() {
        testSet.addCellFunk(0,0);
        testSet.addCellFunk(0,1);
        assertEquals(testSet.length(), 2);
    }

    @Test
    public void addCellTestAlreadyInSet() {
        testSet.addCell(0,0);
        assertTrue(testSet.contains(0,0));
        testSet.addCell(0,0);
        assertFalse(testSet.contains(0,0));
    }

    @Test
    public void addCellTestTwoAdjacentCells() {
        testSet.addCell(0,0);
        testSet.addCell(0,1);
        assertEquals(testSet.length(), 2);
    }

    @Test
    public void removeCellTest() {
        for (int i=0; i<10; i++) {
            testSet.addCell(0,i);
        }
        testSet.removeCell(0,0);
        assertEquals(testSet.length(), 9);
    }

    @Test
    public void addDeadCellTest() {
        testSet.addCell(0,0);
        testSet.addNeighbours();
        assertEquals(testSet.getListOfNeighbours().size(), 8);
    }

    @Test
    public void containsTest() {
        testSet.addCell(0,0);
        assertTrue(testSet.contains(0,0));
    }

    @Test
    public void nextCellsTestCellDeath() {
        testSet.addCell(0, 0);
        testSet.addCell(0,1);
        testSet.nextCells();
        assertEquals(testSet.length(),0);
    }

    @Test
    public void nextCellsTestCellSurvivesSize() {
        testSet.addCell(0,0);
        testSet.addCell(0,1);
        testSet.addCell(1,0);
        testSet.addCell(1,1);
        testSet.nextCells();
        assertEquals(testSet.length(), 4);
    }

    @Test
    public void nextCellsTestCellSurvivesInList() {
        testSet.addCell(0,0);
        testSet.addCell(0,1);
        testSet.addCell(1,0);
        testSet.addCell(1,1);
        testSet.nextCells();
        assertTrue(testSet.contains(0,0));
        assertTrue(testSet.contains(0,1));
        assertTrue(testSet.contains(1,0));
        assertTrue(testSet.contains(1,1));
    }

    @Test
    public void nextCellsTestRepopulationSize() {
        testSet.addCell(0,0);
        testSet.addCell(0,1);
        testSet.addCell(1,0);
        testSet.nextCells();
        assertEquals(testSet.length(), 4);
    }

    @Test
    public void nextCellsTestRepopulationInList() {
        testSet.addCell(0,0);
        testSet.addCell(0,1);
        testSet.addCell(1,0);
        testSet.nextCells();
        assertTrue(testSet.contains(0,0));
        assertTrue(testSet.contains(0,1));
        assertTrue(testSet.contains(1,0));
        assertTrue(testSet.contains(1,1));
    }

    @Test
    public void countToMapTest() {
        testSet.addCell(5,5);
        testSet.addNeighbours();
        testSet.countToMap();
        assertEquals(testSet.getCountMap().size(), 8);
        for (Map.Entry<Cell, Integer> entry: testSet.getCountMap().entrySet()) {
            assertEquals(entry.getValue(), 1);
        }
    }

    @Test
    public void countToMapTestTwoCellsOneApart() {
        testSet.addCell(5,5);
        testSet.addCell(5,7);
        testSet.addNeighbours();
        testSet.countToMap();
        assertEquals(testSet.getCountMap().size(), 13);
        Cell cell46 = new Cell(4,6);
        Cell cell56 = new Cell(5,6);
        Cell cell66 = new Cell(6,6);
        for (Map.Entry<Cell, Integer> entry: testSet.getCountMap().entrySet()) {
            if (entry.getKey().equals(cell46) || entry.getKey().equals(cell56) || entry.getKey().equals(cell66)) {
                assertEquals(entry.getValue(), 2);
            } else {
                assertEquals(entry.getValue(), 1);
            }
        }
    }

    @Test
    public void generateNextCellsTest() {
        testSet.addCell(5,5);
        testSet.addCell(5,7);
        testSet.addNeighbours();
        testSet.countToMap();
        assertEquals(testSet.generateNextCells().size(), 0);
    }
}









