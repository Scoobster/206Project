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
public class ResetData extends GUIElement {

	private JLabel _RUSure = new JLabel("Are you sure you wish to reset all data?");
	private  JButton _yes = new JButton("Yes");
	private JButton _no = new JButton("No");
	
	
	public ResetData(JFrame frame, DataStore data) {
		super(frame, data);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(_RUSure, c);
		
		_yes.setPreferredSize(new Dimension(100,50));
		_no.setPreferredSize(new Dimension(100,50));
		
		_yes.addActionListener(this);
		_no.addActionListener(this);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		add(_yes, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		add(_no, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _yes) {
			_data.overrideAll();
		}
		_GUI.getContentPane().removeAll();
		_GUI.add(new MainMenu(_GUI, _data));
		_GUI.revalidate();
		_GUI.repaint();
	}

}
