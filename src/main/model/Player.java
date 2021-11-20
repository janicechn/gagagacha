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
    private EventLog eventLog;            // event log of player's events

    /*
     * EFFECTS: notebook is initially an empty list;
     *          if initialBalance >= 0 then player's coin balance is set to
     *          initialBalance, otherwise balance is zero.
     */
    public Player(String name, int initialBalance) {
        this.name = name;
        notebook = new ArrayList<>();
        balance = initialBalance;
        eventLog = eventLog.getInstance();
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
    // EFFECTS: removes a note entry in the player's notebook at position i and records event of it
    public void removeNote(int i) {
        notebook.remove(i);

        Event removeNoteEvent = new Event("note at index i is removed from notebook list");
        eventLog.logEvent(removeNoteEvent);
    }

    // MODIFIES: this
    // EFFECTS: removes all note entries in the player's notebook and records event of it
    public void removeAllNotes() {
        notebook = new ArrayList<>();

        Event removeAllNotesEvent = new Event("notebook list is cleared with no notes in the list left");
        eventLog.logEvent(removeAllNotesEvent);
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

    // EFFECTS: creates JSONObject from player
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

    // EFFECTS: prints events in player's event log
    public void playerEventLog(EventLog el) {
        for (Event next : el) { //https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
            System.out.println(next.toString() + "\n");
        }
    }
}
