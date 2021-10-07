package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Player is the class that contains the player's balance and notebook and methods to add/remove to them
 */
public class Player {
    private List<String> notebook;        // the player's collection
    private int balance;                  // the current coin balance of the player

    /*
     * EFFECTS: notebook is initially an empty list;
     *          if initialBalance >= 0 then player's coin balance is set to
     *          initialBalance, otherwise balance is zero.
     */
    public Player(int initialBalance) {
        notebook = new ArrayList<>();
        balance = initialBalance;
    }

    // EFFECTS: returns player's notebook
    public List<String> getNotebook() {
        return notebook;
    }

    // EFFECTS: returns player's coin balance
    public int getBalance() {
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: adds coins in player's balance
    public void addBalance(int i) {
        balance = balance + i;
    }

    // REQUIRES: balance >= i
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
}
