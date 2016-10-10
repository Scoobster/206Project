package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import GUI.Data.Data;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class LevelSelect extends GUIElement {
	
	private JLabel _label = new JLabel("LEVEL SELECT");
	private JButton _play = new JButton("Play!");
	private JButton _view = new JButton("View Statistics");
	private JButton _return = new JButton("Return to Main Menu");
	private JComboBox<String> _selector;
	
	protected LevelSelect(JFrame frame, Data data) {
		
		super(frame, data);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
				
		c.gridx = 0;
		c.gridy = 0;
		add(_label, c);
		
		c.gridy = 1;
		String[] levels = new String[_data.getNamesOfLists().length + 1];
		levels[0] = "Continue";
		int i = 1;
		for (String str : _data.getNamesOfLists()) {
			levels[i] = _data.getNamesOfLists()[i - 1];
			i++;
		}
		_selector = new JComboBox<String>(levels);
		add(_selector, c);
		
		c.gridy = 2;
		_play.addActionListener(this);
		_play.setPreferredSize(new Dimension(100,50));
		add(_play, c);
		
		c.gridy = 3;
		_view.addActionListener(this);
		_view.setPreferredSize(new Dimension(150,50));
		add(_view, c);
		
		c.gridy = 4;
		_return.addActionListener(this);
		_return.setPreferredSize(new Dimension(200,50));
		add(_return, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(_play)) {
		
			String item = _selector.getSelectedItem().toString();
			if (item.equals("Continue")) {
				_GUI.getContentPane().removeAll();
				_GUI.getContentPane().add(new SpellingTest(_GUI, _data, _data.getList(_data.getCurrentLevelName()).getRandomWords(), false));
			} else {
				_data.setCurrentLevel(item);
				_GUI.getContentPane().removeAll();
				_GUI.getContentPane().add(new SpellingTest(_GUI, _data, _data.getList(item).getRandomWords(), false));
			}
			
			
		} else if (e.getSource().equals(_view)) {
			_GUI.getContentPane().add(new ViewStats(_GUI,_data, this));
		} else if (e.getSource().equals(_return)) {
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new MainMenu(_GUI, _data));
		}
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}
	
}
