package GUIComponents;

/*
 * MainGameFrame class holds all the required JPanels for the GUI of the program
 * Also called mgf in some places
 * Author:  Yunus Nedim Mehel
 */

import GameComponents.GameManager;
import GameComponents.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 *
 * @author yunus
 */
public class MainGameFrame extends JFrame {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//JFrame's own components: banner(title), left-side panel and layout manager
    private JPanel backgroundPanel;
    private JPanel canvas;
    private ImageIcon title;
    private JLabel imageLabel;
    private JPanel panelHolder;
    private GameManager gm;
    private Color sandColor;
    private Listener buttonListener;

    //The pages of the game as JPanels
    private SettingsPanel sp;
    private AboutPanel ap;
    private CreditsPanel cp;
    private HighScoresPanel hp;

    //The menu buttons on the left side of the game. buttonsPanel holds them
    private JButton newGame;
    private JButton about;
    private JButton credits;
    private JButton highScores;
    private JButton settings;
    private JButton quit;
    private JPanel buttonsPanel;

    //Game settings initializations
    private boolean difficult;//0-> easy, 1-> hard
    private boolean directionControls;//0->for direction keys, 1->for 'A' and 'D'
    private boolean sound;
    private String boatColor;


    /**
     * Contsructor for the MainGameFrame
     */
    public MainGameFrame(){
    	//Game initializations
    	difficult = false;
    	directionControls = false;
    	sound = false;
    	boatColor = "resources//boat_red.png";

    	//Frame initializations
        sandColor = new Color(250, 250, 165);
        setSize(720, 720);
        setResizable(false);
        setTitle("Boat Drift");
        setLayout(new BorderLayout()); //Banner on top, menu on the WEST, panels in the middle

        //A background panel, which holds basically everything in the MainGameFrame
        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.BLUE);
        backgroundPanel.setLayout(new BorderLayout() );

        title = new ImageIcon("resources/boatDrift.jpg");
        imageLabel = new JLabel(title);

        backgroundPanel.add(imageLabel,BorderLayout.NORTH);//The banner is added to the top of the page

        //The left buttons are held on buttonsPanel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout( new FlowLayout());
        buttonsPanel.setPreferredSize(new Dimension(130,0 ));
        buttonsPanel.setBackground( new Color ( 255, 245, 90)  );
        buttonsPanel.setBorder (BorderFactory.createRaisedBevelBorder() );
        //Spring Layout?

        //Menu buttons are initialized
        newGame = new JButton ("New Game");
        about = new JButton("About");
        credits= new JButton("Credits");
        highScores = new JButton("High Scores");
        settings= new JButton("Settings");
        quit = new JButton("Quit");

        //A class named Listener does the action listening for all JButtons in the game
        buttonListener = new Listener (this);
        newGame.addActionListener( buttonListener);
        about.addActionListener( buttonListener );
        credits.addActionListener(buttonListener );
        highScores.addActionListener( buttonListener );
        settings.addActionListener(buttonListener );
        quit.addActionListener( buttonListener );

        //Buttons size may not work, since the LayoutManager dominates such settings
        newGame.setPreferredSize(new Dimension(120, 45));
        about.setPreferredSize(new Dimension(120, 45));
        credits.setPreferredSize ( new Dimension ( 120, 45));
        highScores.setPreferredSize ( new Dimension ( 120, 45));
        settings.setPreferredSize ( new Dimension ( 120, 45));
        quit.setPreferredSize ( new Dimension ( 120, 45));

        //ButtonsPanel now carries six buttons
        buttonsPanel.add( newGame);
        buttonsPanel.add( highScores);
        buttonsPanel.add( settings);
        buttonsPanel.add( about);
        buttonsPanel.add( credits);
        buttonsPanel.add( quit);

        backgroundPanel.add(buttonsPanel, BorderLayout.WEST);

        ap = new AboutPanel(this);
        sp = new SettingsPanel(this);
        cp = new CreditsPanel(this);
        try {
			hp = new HighScoresPanel(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ap.setBackground( sandColor );
        sp.setBackground( sandColor);
        cp.setBackground( sandColor  );
        hp.setBackground( sandColor );

        //Java's CardLayout carries 6 JPanels as a stack, it can make one of them visible and it automatically calls validate() function
        //It is very useful for such GUI structure
        panelHolder = new JPanel ( new CardLayout() );

        //Panel holder holds the menu panels on the right side of the screen.
        panelHolder.add(cp, "Credits"); //CardLayout makes the first component active(visible in the beginning of the program)
        panelHolder.add(ap, "About");
        panelHolder.add(hp, "High Scores");
        panelHolder.add(sp, "Settings" );

        backgroundPanel.add(panelHolder, BorderLayout.CENTER);

        //Canvas JPanel holds the background Panel (GUI) and the board Panel, where the game is played
        canvas = new JPanel();
        canvas.setLayout( new CardLayout() );


        gm = new GameManager(this);
        canvas.add(backgroundPanel, "GUI");
        canvas.add(gm, "Game Manager");

        getContentPane().add(canvas);

        this.setVisible(true);
    }
    //Game attributes get-set
     /**
     * Returns the boat color
     */
    public String getBoatColor(){
    	return boatColor ;
    }
     /**
     * Changes to boat color into one of the 3 colors
     */
    public void setBoatColor ( String s){
    	boatColor = s;
    }
     /**
     * Returns the difficulty for the game ( false = easy, true = difficult)
     */
    public boolean getDifficulty(){
    	return difficult;
    }
    /**
     * Sets the difficulty ( false = easy, true = difficult)
     */
    public void setDifficulty(boolean newDifficulty){

    	difficult = newDifficulty;
    	System.out.println("new diff: " + difficult);
    }

    /**
     * Returns the controls for the game ( true = directional keys, false= A-D)
     */
    public boolean getControls(){
    	return directionControls;
    }

    /**
     * Sets the controls for the game ( true = directional keys, false= A-D)
     */
    public void setControls(boolean newControls){
    	directionControls = newControls;
    	System.out.println("dirControls: " + directionControls);
    }
    public boolean isSoundOn(){
    	return sound;
    }
    public void setSoundOn(boolean newSound){
    	sound = newSound;
    }


    //The listener class uses this functions to make them visible
    public SettingsPanel getSP(){ return sp; }
    public CreditsPanel getCP(){ return cp; }
    public AboutPanel getAP(){ return ap; }
    public HighScoresPanel getHP(){ return hp; }
    public JPanel getPH(){ return panelHolder; }
    public JPanel getCanvas() { return canvas; }
    public GameManager getGM() { return gm; }

    /**
     * Resets the game by invoking GameManager's initGame method
     */
    public void initGameManagerGame(){
    	gm.initGame();
    }

    public static void main(String[] args) {

        new MainGameFrame();
        // TODO code application logic here
    }

}
