package ui;

import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * MessageMachine is a class that plays a GachaMachine with the price and message notes pertaining to the
 * MessageMachine. Note is not added if it already exists in the player's notebook.
 */
public class MessageMachine extends GachaMachine {
    public static final int COST_TO_PLAY = 5;      // Cost to play machine
    public static final String welcome = "\nWelcome to the Message Gacha Machine!"
            + "\nGet a message just for you!";     // Initial message introducing machine
    public static final List<String> message;      // Possible note outcomes from machine

    static {
        message = Collections.unmodifiableList(
                new ArrayList<String>() {{
                    add("I see your beauty inwards shining outwards.");
                    add("You are loved.");
                    add("I love your smile.");
                    add("You make a difference.");
                    add("I'm proud of you.");
                    add("You're doing great.");
                    add("You inspire me.");
                    add("I'm happy you exist.");
                    add("You're empowering.");
                    add("Everything will be okay.");
                    add("You make me feel like nothing is impossible.");
                    add("You got this!");
                }
                });
    }

    // MODIFIES: player
    // EFFECTS: lets player play gacha machine with note possibilities being specific to the message machine
    public MessageMachine(Player player) {
        super(welcome, player, COST_TO_PLAY, message);
        playMachine(player);
    }

    // MODIFIES: player
    // EFFECTS: message machine is played and replicated draws are not added into the notebook
    void playMachine(Player player) {
        if (player.getBalance() >= COST_TO_PLAY) {
            player.removeBalance(COST_TO_PLAY);
            System.out.println("Spinning gacha machine...");
            System.out.println("A capsule came out! Read your note:");
            Random rand = new Random();
            // https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
            String note = message.get(rand.nextInt(message.size()));
            System.out.println("\n\t" + note);
            player.addNotebookWithoutReplicates(note);
        } else {
            System.out.println("Sorry, you don't have enough coins to play! Go earn some coins playing mini-games!");
        }
    }
}