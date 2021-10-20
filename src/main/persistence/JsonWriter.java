package persistence;

import model.Player;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of player (data including name, balance, notebook) to file
public class JsonWriter {
    private PrintWriter writer; //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private String destination; //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination; //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throwing FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of player to file
    public void write(Player player) {
        JSONObject json = player.toJson(); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        saveToFile(json.toString(4)); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close(); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }
}
