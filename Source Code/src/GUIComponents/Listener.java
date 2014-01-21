package GUIComponents;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
/**
 * A listener class which listens all the JButtons in the gam. It decides which action to take by the buttons text.
 * It implements ActionListener
 * @author:  Yunus Nedim Mehel
 *
 */
public class Listener implements ActionListener{

    private String butText;
    private MainGameFrame mgf;


    public Listener(MainGameFrame mgf1){

        //A mainGameFrame object is taken as a parameter, otherwise it wouldnt have been possible to reach the JPanels in the mgf.
        mgf = mgf1;

    }

    public void actionPerformed(ActionEvent e) {

        CardLayout cl = (CardLayout)(mgf.getPH().getLayout());
        CardLayout c2 = (CardLayout)(mgf.getCanvas().getLayout());
        //ButtonText is needed to decide, which action to take
        butText = e.getActionCommand();

        if(butText.equals("Settings")){

            System.out.println("Go To Settings");
            cl.show(mgf.getPH(),"Settings"); //getPH, getMP and such methods can be found in mgf

        }

        else if (butText.equals("Back") ){ //This JButton will be deleted. The MainPanel class will also be deleted

            System.out.println("Go To Main Menu");

            cl.show(mgf.getPH(),"Main");
        }

        else if (butText.equals("About") ){

            System.out.println("Go To About");
            cl.show(mgf.getPH(),"About");
        }

        else if (butText.equals("Credits") ){

            System.out.println("Go To Credits");
            cl.show(mgf.getPH(),"Credits");

        }

         else if (butText.equals("High Scores")){

            System.out.println("Go To High Scores");
            try {
				mgf.getHP().refresh();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            cl.show(mgf.getPH(), "High Scores");

         }

        else if (butText.equals("New Game")){

            System.out.println("New Game ");

            cl.show(mgf.getPH(), "Credits");
            c2.show(mgf.getCanvas(), "Game Manager");
            mgf.getGM().requestFocus();
            mgf.initGameManagerGame();

            //Aynı şekilde oyundan ana menu ye dondugumuzde de focusu ayarlamamız gerekeblir

        } //This button starst the game
        else if (butText.equals("Quit"))System.exit(0); //This button quits the game.


        else {System.out.println("Button text cannot be found");}

    }

}

