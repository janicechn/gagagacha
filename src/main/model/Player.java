package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Player is the class that contains the player's balance and notebook and methods to add/remove to them
public class Player implements Writable {
    private String name;                  // the player's name
    private List<String> notebook;        // the player's collection
    private int balance;                  // the current coin balance of the player

    /*
     * EFFECTS: notebook is initially an empty list;
     *          if initialBalance >= 0 then player's coin balance is set to
     *          initialBalance, otherwise balance is zero.
     */
    public Player(String name, int initialBalance) {
        this.name = name;
        notebook = new ArrayList<>();
        balance = initialBalance;
    }

    // EFFECTS: returns player's name
    public String getName() {
        return name;
    }

    // EFFECTS: returns player's notebook
    public List<String> getNotebook() {
        return notebook;
    }

    // EFFECTS: returns player's coin balance
    public int getBalance() {
        return balance;
    }

    // REQUIRES: i > 0
    // MODIFIES: this
    // EFFECTS: adds coins in player's balance
    public void addBalance(int i) {
        balance = balance + i;
    }

    // REQUIRES: balance >= i, i > 0
    // MODIFIES: this
    // EFFECTS: removes coins from player's balance
    public void removeBalance(int i) {
        balance = balance - i;
    }

    // MODIFIES: this
    // EFFECTS: adds a note (String s) entry into the player's notebook
    public void addNotebook(String s) {
        notebook.add(s);
    }

    // MODIFIES: this
    // EFFECTS: adds to player's notebook if not already there; otherwise do not add
    public void addNotebookWithoutReplicates(String s) {
        if (!notebook.contains(s)) {
            notebook.add(s);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        json.put("balance", balance);
        json.put("notebook", notesToJson());
        return json;
    }

    // EFFECTS: returns notes in player as a JSON array
    private JSONArray notesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String note : notebook) {
            jsonArray.put(note); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        }

        return jsonArray; //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    }
}
