package GUIComponents;

/*
 * This JPanel holds information about the game. (aka. How to play)
 * Author:  İpek Aktaş
 */


import javax.swing.*;
/**
 *
 */
public class AboutPanel  extends JPanel {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	MainGameFrame mgf;
    ImageIcon about;
    JLabel aboutLabel;

    /**
     * Constructor for the AboutPanel class. Takes a MainGameFrame object in order to reach the Listener class
     */
    public AboutPanel(MainGameFrame mgf1){

        mgf = mgf1;
        //Background color is changed in mgf
        about = new ImageIcon("resources/aboutInfo.jpg"); //the text may need to be revised
        aboutLabel = new JLabel();
        aboutLabel.setIcon (about );
        add(aboutLabel);
    }

}
