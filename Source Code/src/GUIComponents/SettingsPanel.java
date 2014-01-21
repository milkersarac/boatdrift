package GUIComponents;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * The settings window of the game. It carriest lots of JComponent and an "Apply" button to save the changes.
 * Author:  Yunus Nedim Mehel
 */
public class SettingsPanel extends JPanel implements ActionListener, ChangeListener{
	/**
	 *
	 */
    private static final long serialVersionUID = 1L;

    static int i = 0;

    MainGameFrame mgf;

    //This group changes a color for the boat via a combobox. The result is shown in the imageIcon nearby.
    private JComboBox color;
    private JButton apply;
    private String[] colors;
    private JPanel colorChooser;
    private JPanel soundAndApply;
    private ImageIcon image;
    private JLabel imageLabel;
    private Color sandColor;

    //There are two radioButtonGroups (4 RadioButtons) for difficulty and controls selection. They are carried on radioButtons JPanel
    private JRadioButton directionKeys;
    private JRadioButton WASDKeys;
    private JRadioButton easy;
    private JRadioButton hard;
    private JPanel radioButtons;
    private JPanel emptyPanel;

    private ButtonGroup controls;
    private ButtonGroup difficulty;

    //A checkbox for the sound
    private JCheckBox sound ;

    /**
 * Constructor for the SettingsPanel class. Takes a MainGameFrame object in order to reach the Listener class
 */
    public SettingsPanel(MainGameFrame mgf1){

        mgf = mgf1;
        sandColor = new Color(250, 250, 165)  ;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        image = new ImageIcon("resources/boat_red.jpg");
        imageLabel = new JLabel();
        imageLabel.setIcon(image);

        sound = new JCheckBox("Sound");
        sound.addChangeListener(this);
        sound.setSelected(  mgf.isSoundOn());
        apply = new JButton("Apply");
        apply.addActionListener(this);
        soundAndApply = new JPanel();
        soundAndApply.setLayout( new FlowLayout() );
        soundAndApply.add(sound);
        soundAndApply.add(apply);
        soundAndApply.setBackground(sandColor);
        sound.setBackground(sandColor );


        //Initialization of the radioButtons
        directionKeys = new JRadioButton("Direction Keys");
        WASDKeys = new JRadioButton("A-D Keys");
        easy = new JRadioButton("Easy");
        hard = new JRadioButton("Hard");

        directionKeys.setBackground(sandColor ); //sandColor new Color(250, 250, 195)  to check
        WASDKeys.setBackground(sandColor);
        easy.setBackground(sandColor);
        hard.setBackground(sandColor);

        directionKeys.setPreferredSize( new Dimension(160,30));
        WASDKeys.setPreferredSize(new Dimension(160,30));
        easy.setPreferredSize(new Dimension(160,30));
        hard.setPreferredSize(new Dimension(160,30));

        directionKeys.addActionListener(this );
        WASDKeys.addActionListener(this );
        easy.addActionListener(this );
        hard.addActionListener(this );



        if ( mgf.getControls() ){

        	WASDKeys.setSelected( true);
        }

        else {

        	directionKeys.setSelected( true);
        }

        if ( mgf.getDifficulty() ){

        	hard.setSelected( true);
        }

        else {

        	easy.setSelected( true);
        }


        controls = new ButtonGroup();
        difficulty = new ButtonGroup();

        controls.add(directionKeys);
        controls.add(WASDKeys);

        difficulty.add(easy);
        difficulty.add(hard);

        //Colorchooser combobox
        colors = new String[3];
        colors[0] = "Red";
        colors[1] = "Green";
        colors[2] = "Yellow";

        color = new JComboBox(colors);
        color.addActionListener( this );

        colorChooser = new JPanel();
        colorChooser.add(color);
        colorChooser.add(imageLabel);
        colorChooser.setBackground(sandColor );

        radioButtons = new JPanel();
        radioButtons.setBackground(sandColor );
        radioButtons.setLayout(new BoxLayout(radioButtons,1));

        radioButtons.add(directionKeys);
        radioButtons.add(WASDKeys);

        emptyPanel = new JPanel();
        emptyPanel.setBackground(sandColor );
        radioButtons.add(emptyPanel); // To leave a space between the button groups

        radioButtons.add(easy);
        radioButtons.add(hard);

        easy.setAlignmentX(easy.CENTER_ALIGNMENT);
        hard.setAlignmentX(easy.CENTER_ALIGNMENT);
        directionKeys.setAlignmentX(easy.CENTER_ALIGNMENT);
        WASDKeys.setAlignmentX(easy.CENTER_ALIGNMENT);


        add(Box.createRigidArea(new Dimension(300,50)));
        add(colorChooser);
        add(Box.createRigidArea(new Dimension(300,50)));
        add(radioButtons);
        add(Box.createRigidArea(new Dimension(300,20)));
        add(soundAndApply);


    }


	@Override
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
		String comboText = color.getSelectedItem().toString();


		if(comboText.equals("Red")){
			imageLabel.setIcon( new ImageIcon ("resources/boat_red.jpg"));
			mgf.setBoatColor("resources/boat_red.png");

		}
		else if (comboText.equals("Yellow") ){

			imageLabel.setIcon( new ImageIcon ("resources/boat_yellow.jpg"));
			mgf.setBoatColor("resources/boat_yellow.png");
		}

		else if ( comboText.equals("Green") ){ //Combotext is green
			imageLabel.setIcon( new ImageIcon ("resources/boat_green.jpg"));
			mgf.setBoatColor("resources/boat_green.png");
		}
		else
			System.out.println("e.getSource() cannot be found: " + comboText );



		if ( action.equals("A-D Keys" )){

			System.out.println("settings: ad keys selected");
			mgf.setControls( true );
		}
		else if ( action.equals("Direction Keys" ) ){

			System.out.println("settings: dir keys selected");
			mgf.setControls( false );
		}

		else if ( action.equals("Easy" ) ){

			System.out.println("settings: easy selected");
			mgf.setDifficulty( false );
		}

		else if ( action.equals("Hard" ) ){

			System.out.println("settings: hard selected");
			mgf.setDifficulty( true );
		}

		else if (action.equals("Apply" ) ){

//			System.out.println("Apply button is pressed: ");
//			System.out.println("Diff: " + mgf.getDifficulty( ) );
//			System.out.println("Controls: " + mgf.getControls( ));
//			System.out.println("Sound: " + mgf.isSoundOn( ) );

			System.out.println("Boat Color comboBox: " + mgf.getBoatColor());

		}

		else System.out.println( "RadioButton text cannot be found ");


	}



   /**
 * Listenes the sound JCheckBox
 */
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		mgf.setSoundOn(sound.isSelected());

		System.out.println(i++ + "-->" + sound.isSelected());
	}


}
