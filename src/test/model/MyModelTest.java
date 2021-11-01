package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void runBefore() {
        player = new Player("name", 3);
    }

    @Test
    void testRemoveNote() {
        List<String> notebook = new ArrayList<>();
        assertEquals(notebook, player.getNotebook());
        notebook.add("message");
        player.addNotebook("message");
        assertEquals(notebook, player.getNotebook());
        notebook.add("advice");
        player.addNotebook("advice");
        assertEquals(notebook, player.getNotebook());
        player.removeNote(0);
        notebook.remove(0);
        assertEquals(notebook, player.getNotebook());
        notebook.add("fortune");
        player.addNotebook("fortune");
        assertEquals(notebook, player.getNotebook());
        player.removeAllNotes();
        assertEquals(0, player.getNotebook().size());
    }

    @Test
    void testAddNotebook() {
        List<String> notebook = new ArrayList<>();
        assertEquals(notebook, player.getNotebook());
        notebook.add("message");
        player.addNotebook("message");
        assertEquals(notebook, player.getNotebook());
        notebook.add("advice");
        player.addNotebook("advice");
        assertEquals(notebook, player.getNotebook());
    }

    @Test
    void testAddNotebookWithoutReplicates() {
        List<String> notebook = new ArrayList<>();
        assertEquals(notebook, player.getNotebook());
        player.addNotebookWithoutReplicates("message");
        notebook.add("message");
        assertEquals(notebook, player.getNotebook());
        player.addNotebookWithoutReplicates("message");
        assertEquals(notebook, player.getNotebook());
        player.addNotebookWithoutReplicates("advice");
        notebook.add("advice");
        assertEquals(notebook, player.getNotebook());
        player.addNotebookWithoutReplicates("message");
        assertEquals(notebook, player.getNotebook());
        player.addNotebookWithoutReplicates("advice");
        assertEquals(notebook, player.getNotebook());
        player.addNotebookWithoutReplicates("fortune");
        notebook.add("fortune");
        assertEquals(notebook, player.getNotebook());
    }

    @Test
    void testBalance() {
        assertEquals(3, player.getBalance());
        player.addBalance(5);
        assertEquals(8, player.getBalance());
        player.removeBalance(1);
        assertEquals(7, player.getBalance());
        player.removeBalance(7);
        assertEquals(0, player.getBalance());
    }
}