package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.Data.Data;
import GUI.Data.Word;

public class AddRemoveWord extends GUIElement {
	
	private JPanel _previousScreen;

	private JLabel _title = new JLabel("Add A Word");
	private JLabel _operationLabel = new JLabel("Select An Operation:");
	private JComboBox<String> _selectOperation = new JComboBox<String>(new String[] {"Add Word", "Remove Word"});
	private JPanel _emptyPanel = new JPanel();
	private JPanel _addWordPanel = new JPanel();
	private JPanel _removeWordPanel = new JPanel();
	private JButton _return = new JButton("Cancel");
	
	private JComboBox<String> _addLevels;
	private JTextField _addInput = new JTextField();
	private JButton _addSubmit = new JButton("Add");
	
	private JComboBox<String> _removeLevels = new JComboBox<String>(_data.getNamesOfLists());
	private JComboBox<Word> _removeInput = new JComboBox<Word>(_data.getList(_data.getNamesOfLists()[0]).getAsArray());
	private JButton _removeSubmit = new JButton("Remove");

	protected AddRemoveWord(JFrame frame, Data data, JPanel previous) {
		super(frame, data);
		_previousScreen = previous;
		_previousScreen.setVisible(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(_title, c);
		
		c.gridwidth = 1;
		c.gridy = 1;
		add(_operationLabel, c);
		
		c.gridx = 1;
		_selectOperation.addActionListener(this);
		add(_selectOperation, c);
		
		setupAddPanel();
		setupRemovePanel();
		
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		add(_emptyPanel, c);
		
		_emptyPanel.add(_addWordPanel);
		
		
		c.gridy = 3;
		_return.addActionListener(this);
		add(_return, c);
		
	}
	
	private void setupAddPanel() {
		
		_addWordPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		
		String[] displayLevels = new String[_data.getNamesOfLists().length + 1];
		for (int i = 0; i < _data.getNamesOfLists().length; i++) {
			displayLevels[i] = _data.getNamesOfLists()[i];
		}
		displayLevels[_data.getNamesOfLists().length] = "INSERT NEW LEVEL";
		_addLevels = new JComboBox<String>(displayLevels);
		
		c.gridx = 0;
		c.gridy = 0;
		_addWordPanel.add(_addLevels, c);
		
		c.gridy = 1;
		_addInput.setPreferredSize(new Dimension(150,25));
		_addWordPanel.add(_addInput, c);
		
		c.gridy = 2;
		_addSubmit.addActionListener(this);
		_addWordPanel.add(_addSubmit, c);
		
	}
	
	private void setupRemovePanel() {
		
		_removeWordPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		
		_removeLevels = new JComboBox<String>(_data.getNamesOfLists());
		
		c.gridx = 0;
		c.gridy = 0;
		_removeLevels.addActionListener(this);
		_removeWordPanel.add(_removeLevels, c);
		
		c.gridy = 1;
		_removeWordPanel.add(_removeInput, c);
		
		c.gridy = 2;
		_removeSubmit.addActionListener(this);
		_removeWordPanel.add(_removeSubmit, c);
	
		
	}
	
	private void updateWords() {
		
		_removeInput.removeAllItems();
		ArrayList<Word> list = _data.getList((String) _removeLevels.getSelectedItem()).returnCopyOfList();
		
		for (Word word : list) {
			_removeInput.addItem(word);
		}
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if (source.equals(_selectOperation)) {
			
			if (_selectOperation.getSelectedItem().equals("Remove Word")) { 
				_emptyPanel.remove(_addWordPanel);
				_emptyPanel.add(_removeWordPanel);
			} else if (_selectOperation.getSelectedItem().equals("Add Word")) {
				_emptyPanel.add(_addWordPanel);
				_emptyPanel.remove(_removeWordPanel);
			}
			
			_GUI.revalidate();
			_GUI.repaint();
			
		} else if (source.equals(_removeLevels)) {
			
			updateWords();
			
		} else if (source.equals(_removeSubmit)) {
			
			_data.getList((String) _removeLevels.getSelectedItem()).remove((Word) _removeInput.getSelectedItem());
			updateWords();
			/*
			 * NEED TO WRITE TO THE FILE!!!
			 */
			
		} else if (source.equals(_return)) {
			
			changeScreen(new Settings(_GUI,_data));
			
		} else if (source.equals(_addSubmit)) {
			
			if (_addInput.getText().equals("INSERT NEW LEVEL")) {
				JOptionPane.showMessageDialog(_GUI, "YET TO BE IMPLEMENTED");
				return;
			}
			_data.getList((String) _addLevels.getSelectedItem()).add(new Word(_addInput.getText()));
			/*
			 * NEED TO WRITE TO FILE!!!
			 */
			
		}
		
	}
	
}
