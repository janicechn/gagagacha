package persistence;

import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Player player = reader.read();
            fail("IOException expected"); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        } catch (IOException e) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            // pass
        }
    }

    @Test
    void testReaderPlayer() {
        Player samplePlayer = new Player("sample name", 10);
        samplePlayer.addNotebook("message");
        samplePlayer.addNotebook("fortune");
        samplePlayer.removeBalance(7);
        samplePlayer.addBalance(1);
        //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        JsonWriter writer = new JsonWriter("./data/testReaderPlayer.json");
        try {
            writer.open();
            writer.write(samplePlayer);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Couldn't find file");
        }

        JsonReader reader = new JsonReader("./data/testReaderPlayer.json");
        try {
            Player player = reader.read();
            assertEquals("sample name", player.getName());
            assertEquals(2, player.getNotebook().size());
            assertEquals("message", player.getNotebook().get(0));
            assertEquals("fortune", player.getNotebook().get(1));
            assertEquals(4, player.getBalance());
        } catch (IOException e) {
            fail("Couldn't read from file"); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        }
    }
}
