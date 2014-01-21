package GUIComponents;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/*
 * This panel will be carrying a high score table, hopefully...
 * Author:  İpek Aktaş
 */
public class HighScoresPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    MainGameFrame mgf;
    private JLabel title;
    private JLabel scores;
    private JPanel titlePanel;
    private JPanel scoresPanel;
    private Color sandColor;
    private JButton clearList;

    public HighScoresPanel(MainGameFrame mgf1) throws IOException{

        mgf = mgf1;
        sandColor = new Color(250, 250, 165);
        setLayout(new BoxLayout (this, BoxLayout.Y_AXIS) ); //BoxLayout can put the components in the middle


        title = new JLabel("High Scores", SwingConstants.CENTER);
        title.setFont( new Font ( "Halvetica", Font.BOLD, 20 ));
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension (50 , 60));
        titlePanel.setMaximumSize(new Dimension (1000 , 300));
        titlePanel.setBackground(sandColor);

        titlePanel.add(title);
        title.setAlignmentX(title.CENTER_ALIGNMENT);


        scores = new JLabel ( getTableContents() ,SwingConstants.CENTER );
        scores.setFont( new Font ( "Halvetica", Font.BOLD, 16 ));
        scoresPanel = new JPanel();
        scoresPanel.setBackground ( new Color (255, 255, 225));
        scoresPanel.setPreferredSize(new Dimension (50 , 1000));
        scoresPanel.setMaximumSize(new Dimension (500 , 1000));
        scoresPanel.setBorder (BorderFactory.createLoweredBevelBorder() );
        scoresPanel.setAlignmentX(CENTER_ALIGNMENT);
        scoresPanel.add(scores);

        clearList = new JButton ("Delete High Scores");
        clearList.addActionListener( new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent arg0) {
            //Clear the high scores
            //Refresh the panel

            try {
                    BufferedWriter bwr = new BufferedWriter ( new FileWriter ("resources/highScores.txt"));
                    //This can emptly the file, but method invokation may be needed
                    bwr.write("");


                    bwr.close();
                    refresh();


            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }



        }


        });
        //clearList.setVerticalAlignment(SwingConstants.CENTER);


        add(titlePanel);

        add(scoresPanel);
        add(Box.createRigidArea(new Dimension(000,100)));
        add(clearList);
        add(Box.createRigidArea(new Dimension(10000,100)));



    }

    /**
     * Refreshes the scores JPanel by invoking the getTableContents and repainting&revalidating the panel
     *
     * @throws IOException
     */
    public void refresh() throws IOException{

    	scores.setText(getTableContents() );
    	scoresPanel.repaint();
    	scoresPanel.revalidate();

    }

    /**
     * Reads the high scores contents and returns as a string. The structure of the string is made by HTML tags
     *
     * @throws IOException
     */
    private String getTableContents() throws IOException{

    	BufferedReader br;

        br = new BufferedReader(new FileReader("resources/highScores.txt"));

    	String scoresRead = "<html><br>";
    	int i = 1;
    	while ( br.ready() && i < 11){

            scoresRead += i + ". " + br.readLine() + "<br>";
            i++;

    	}
    	scoresRead += "</html>";

    	br.close();
    	return scoresRead;


    }




}
