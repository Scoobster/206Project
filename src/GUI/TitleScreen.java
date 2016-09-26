package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import GUI.Data.DataStore;

@SuppressWarnings("serial")
public class TitleScreen extends GUIElement {
	
	private JLabel _title = new JLabel("VOXSPELL");
	private JButton _beginButton = new JButton("Begin!");

	public TitleScreen(JFrame frame, DataStore data) {
		
		super(frame, data);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		add(_title,c);
		
		c.gridy = 1;
		_beginButton.addActionListener(this);
		_beginButton.setPreferredSize(new Dimension(100,50));
		add(_beginButton,c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		_GUI.getContentPane().remove(this);
		_GUI.getContentPane().add(new MainMenu(_GUI, _data));
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}

}
