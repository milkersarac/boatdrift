//Author:  İlker Saraç

package GameComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PausePanelGM extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//private JLabel label;
	private JButton buton;

	public PausePanelGM(){
		setLayout(new BorderLayout());
		buton = new JButton("pause");


		setPreferredSize(new Dimension(300, 300));
		setLocation(0, 0);
		setFocusable(true);
		setBackground(Color.GREEN);
		setVisible(false);


		this.add(buton);
		buton.addActionListener(this);
		buton.revalidate();
		buton.repaint();

		this.revalidate();
		this.repaint();


	}

//	public void setPausePanelVisible(boolean newVisible){
//		visible = newVisible;
//	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

}
