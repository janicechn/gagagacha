package ui.gui;

import ui.AdviceMachine;
import ui.FortuneMachine;
import ui.MessageMachine;

import javax.swing.*;
import java.util.List;
import java.util.Random;

import static ui.gui.StartScreen.player;

// Represents gacha machine gui for player to play with the different machines
public class PlayGachaMachine {

    // EFFECTS: prompts player to choose a machine to play
    public PlayGachaMachine() {
        Object[] options = { "Advice (cost: " + AdviceMachine.COST_TO_PLAY + " coins)",
                "Fortune (cost: " + FortuneMachine.COST_TO_PLAY + " coins)",
                "Message (cost: " + MessageMachine.COST_TO_PLAY + " coins)"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose which gacha machine type you want to play!"));
        int result = JOptionPane.showOptionDialog(null, panel, "Gacha machines!",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        selectedMachine(result);
    }

    // EFFECTS: plays chosen machine if player has enough coins to play; shows error message if player has insufficient
    //          coins to play
    private void selectedMachine(int result) {
        if (result == JOptionPane.YES_OPTION) {
            if (player.getBalance() < AdviceMachine.COST_TO_PLAY) {
                insufficientCoins();
            } else {
                playMachine(AdviceMachine.advice, AdviceMachine.COST_TO_PLAY);
            }
        }
        if (result == JOptionPane.NO_OPTION) {
            if (player.getBalance() < FortuneMachine.COST_TO_PLAY) {
                insufficientCoins();
            } else {
                playMachine(FortuneMachine.fortune, FortuneMachine.COST_TO_PLAY);
            }
        }
        if (result == JOptionPane.CANCEL_OPTION) {
            if (player.getBalance() < AdviceMachine.COST_TO_PLAY) {
                insufficientCoins();
            } else {
                playMachine(MessageMachine.message, MessageMachine.COST_TO_PLAY);
            }
        }
    }

    // EFFECTS: displays message indicating player has insufficient coins to play a gacha machine
    private void insufficientCoins() {
        JOptionPane.showMessageDialog(null,
                "Oops! You don't have enough coin to play this gacha machine!"
                        + "\nPlay mini-games to earn more coins to play!",
                "Insufficient coins.",
                JOptionPane.ERROR_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: gets random note based on machine and presents it, adding to player's notebook if it is not a repeated
    //          note from the message machine
    private void playMachine(List<String> notes, int cost) {
        player.removeBalance(cost);
        Random rand = new Random();
        String note = notes.get(rand.nextInt(notes.size()));
        ImageIcon icon = new ImageIcon("data/capsule.png"); //image made by me using Paint and Paint3D

        if (notes == MessageMachine.message && player.getNotebook().contains(note)) {
            JOptionPane.showMessageDialog(null,
                    "You won a message!\n\n\t" + "\"" + note + "\""
                            + "\n\nYou already collected this message, so it's not added to your notebook.",
                    "Gacha Capsule", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {
            player.addNotebook(note);
            JOptionPane.showMessageDialog(null,
                    "You won a note!\n\n\t" + "\"" + note + "\""
                            + "\n\nYour note was added to your notebook.", "Gacha Capsule",
                    JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
}
