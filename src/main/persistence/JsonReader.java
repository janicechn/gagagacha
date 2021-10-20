package persistence;

import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads player (data including name, balance, notebook) from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads player from file and returns it; throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        StringBuilder contentBuilder = new StringBuilder();
        //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString(); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        String name = jsonObject.getString("name");
        int balance = jsonObject.getInt("balance");
        Player player = new Player(name, balance);
        // parse notes and add to player notebook
        JSONArray jsonArray = jsonObject.getJSONArray("notebook");
        for (Object json : jsonArray) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            player.addNotebook(json.toString());
        }
        return player;
    }
}