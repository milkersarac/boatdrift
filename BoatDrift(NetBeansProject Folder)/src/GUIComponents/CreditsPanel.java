package GUIComponents;

/*
 *  This JPanel holds information about the creators of the game.
 *  Author:  İpek Aktaş
 */

import javax.swing.*;

public class CreditsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    ImageIcon credits;
    JLabel creditsLabel;
    MainGameFrame mgf;

    /**
     * Constructor for the CreditsPanel class. Takes a MainGameFrame object in order to reach the Listener class
    */
    public CreditsPanel(MainGameFrame mgf1 ){

        mgf = mgf1;
        //Color is set by the mgf class
        credits = new ImageIcon("resources/creditsInfo.jpg");
        creditsLabel = new JLabel();
        creditsLabel.setIcon(credits);
        add(creditsLabel);
    }

}
