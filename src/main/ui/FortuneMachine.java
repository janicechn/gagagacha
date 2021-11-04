package ui;

import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * FortuneMachine is a class that plays a GachaMachine with the price and fortune notes pertaining to the
 * FortuneMachine.
 */
public class FortuneMachine extends GachaMachine {
    public static final int COST_TO_PLAY = 5;  // Cost to play machine
    public static final String welcome = "\nWelcome to the Fortune Gacha Machine!"
            + "\nGet your fortune told!";      // Initial message introducing machine
    public static final List<String> fortune;  // Possible note outcomes from machine

    static {
        fortune = Collections.unmodifiableList(
                new ArrayList<String>() {{
                    add("When one door closes, another one opens.");
                    add("The love of your life is right in front of your eyes.");
                    add("You have a secret admirer.");
                    add("A dubious friend may be an enemy in camouflage.");
                    add("A golden egg of opportunity falls into your lap this month.");
                    add("A good time to finish up old tasks.");
                    add("A smooth long journey! Great expectations.");
                    add("A lifetime of happiness lies ahead of you.");
                    add("A pleasant surprise is waiting for you.");
                    add("All the troubles you have will pass away very quickly.");
                    add("Be careful or you could fall for some tricks today.");
                    add("Don’t let your limitations overshadow your talents.");
                }
                });
    }

    // MODIFIES: player
    // EFFECTS: lets player play gacha machine with note possibilities being specific to the fortune machine;
    //          all notes added to player's notebook regardless if it already exists there
    public FortuneMachine(Player player) {
        super(welcome, player, COST_TO_PLAY, fortune, true);
    }
}