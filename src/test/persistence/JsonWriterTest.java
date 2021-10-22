package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            // pass
        }
    }

    @Test
    void testWriterPlayer() {
        try {
            Player player = new Player("name", 10);
            player.addNotebook("message");
            player.addNotebook("fortune");
            player.removeBalance(7);
            player.addBalance(1);
            //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            JsonWriter writer = new JsonWriter("./data/testWriterPlayer.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPlayer.json");
            player = reader.read();
            assertEquals("name", player.getName());
            assertEquals(2, player.getNotebook().size());
            assertEquals("message", player.getNotebook().get(0));
            assertEquals("fortune", player.getNotebook().get(1));
            assertEquals(4, player.getBalance());
        } catch (IOException e) {
            //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            fail("Exception should not have been thrown");
        }
    }
}