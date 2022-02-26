package Test;

import model.Cell;
import model.SetOfCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private Cell testCell;

    @BeforeEach
    public void runBefore() {
        testCell = new Cell(0,0);
    }

    @Test
    public void getXTest() {
        assertEquals(0, testCell.getXPos());
    }

    @Test
    public void getYTest() {
        assertEquals(0, testCell.getYPos());
    }

    @Test
    public void equalsTestXAndY() {
        Cell testCell2 = new Cell(0,0);
        assertEquals(testCell2, testCell);
    }

    @Test
    public void equalsTestSameObject() {
        assertEquals(testCell, testCell);
    }

    @Test
    public void equalsTestDifferentClass() {
        SetOfCell testSet = new SetOfCell();
        assertNotEquals(testCell, testSet);
    }

    @Test
    public void equalsTestNull() {
        SetOfCell testSet = new SetOfCell();
        assertNotEquals(testCell, null);
    }
}
