package Test;

import model.SetOfCell;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SetOfCell sc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReader reader = new JsonReader("./data/testReaderNoActiveCell.json");
        try {
            SetOfCell sc = reader.read();
            assertEquals(0, sc.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBoard() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBoard.json");
        try {
            SetOfCell sc = reader.read();
            assertEquals(3, sc.length());
            assertTrue(sc.contains(3,3));
            assertTrue(sc.contains(3,4));
            assertTrue(sc.contains(3,5));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
