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
public class MainMenu extends GUIElement {
	
	private JLabel _title = new JLabel("MAIN MENU");
	private JButton _quiz = new JButton("New Spelling Quiz");
	private JButton _review = new JButton("Review Quiz");
	private JButton _stats = new JButton("View Statistics");
	private JButton _clear = new JButton("Clear Data");
	

	public MainMenu(JFrame frame, DataStore data) {
		
		super(frame, data);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		add(_title, c);
		
		JButton[] buttons = new JButton[] {_quiz, _review, _stats, _clear};
		for (int i = 0; i < 4; i++) {
			JButton current = buttons[i];
			c.gridy = i + 1;
			current.addActionListener(this);
			current.setPreferredSize(new Dimension(200,50));
			add(current,c);
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		
		if (source.equals(_quiz)) {
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new LevelSelect(_GUI, _data));
		} else if (source.equals(_review)) {
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new SpellingTest(_GUI, _data, _data.getMistakes().getRandomTen(), true));
		} else if (source.equals(_stats)) {
			_GUI.getContentPane().add(new ViewStats(_GUI, _data, this));
		} else if (source.equals(_clear)) {
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new ResetData(_GUI, _data));
		}
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}

}
