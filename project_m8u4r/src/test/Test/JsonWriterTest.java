package Test;

import model.Cell;
import model.SetOfCell;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            SetOfCell sc = new SetOfCell();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBoard() {
        JsonReader reader = new JsonReader("./data/testReaderNoActiveCell.json");
        try {
            SetOfCell sc = new SetOfCell();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBoard.json");
            writer.open();
            writer.write(sc);
            writer.close();

            sc = reader.read();
            assertEquals(0, sc.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBoard() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBoard.json");
        try {
            SetOfCell sc = new SetOfCell();
            sc.addCell(3,3);
            sc.addCell(3,4);
            sc.addCell(3,5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBoard.json");
            writer.open();
            writer.write(sc);
            writer.close();

            sc = reader.read();
            assertEquals(3, sc.length());
            assertTrue(sc.contains(3,3));
            assertTrue(sc.contains(3,4));
            assertTrue(sc.contains(3,5));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
