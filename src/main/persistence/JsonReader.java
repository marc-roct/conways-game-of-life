package persistence;

import model.Cell;
import model.SetOfCell;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SetOfCell read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private SetOfCell parseWorkRoom(JSONObject jsonObject) {
        SetOfCell sc = new SetOfCell();
        addJsonCells(sc, jsonObject);
        return sc;
    }

    // MODIFIES: wr

    // EFFECTS: parses cells from JSON object and adds them to listOfCell
    private void addJsonCells(SetOfCell sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cell");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addJsonCell(sc, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses cell from JSON object and adds it to listOfCell
    private void addJsonCell(SetOfCell sc, JSONObject jsonObject) {
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        sc.addCell(x,y);

    }
}
