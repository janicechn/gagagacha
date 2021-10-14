package ui;

import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * AdviceMachine is a class that plays a GachaMachine with the price and advice notes pertaining to the
 * AdviceMachine.
 */
public class AdviceMachine extends GachaMachine {
    public static final int COST_TO_PLAY = 3;                                    // Cost to play machine
    public static final String welcome = "\nWelcome to the Advice Gacha Machine! "
            + "\nThink of a yes/no question and spin the machine for advice!";   // Initial message introducing machine
    public static final List<String> advice;                                     // Possible note outcomes from machine

    static {
        advice = Collections.unmodifiableList(
                new ArrayList<String>() {{
                    add("It is certain.");
                    add("Signs point to yes.");
                    add("Without a doubt.");
                    add("Yes - definitely.");
                    add("Most likely.");
                    add("Ask again later.");
                    add("Better not tell you now.");
                    add("Cannot predict now.");
                    add("Don't count on it.");
                    add("My sources say no.");
                    add("Very doubtful.");
                    add("Outlook is not so good.");
                }
                });
    }

    // MODIFIES: player
    // EFFECTS: lets player play gacha machine with note possibilities being specific to the advice machine
    public AdviceMachine(Player player) {
        super(welcome, player, COST_TO_PLAY, advice);
    }
}
