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
        player = new Player(3);
    }

    @Test
    void testNotebook() {
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