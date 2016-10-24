package voxspell.gui;

import java.awt.event.ActionEvent;

import voxspell.data.Word;
import voxspell.data.WordList;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * 
 * Class that is the Add Word screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class AddWord extends GUIElement {
	
	private JLabel _title;
	private JComboBox<WordList> _addLevel;
	private JTextField _addInput;
	private JButton _submit;
	private JButton _return;
	private JLabel lblSelectALevel;
	private JLabel lblPleaseEnterThe;

	/**
	 * Create the panel.
	 */
	public AddWord() {
		super();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 20, 0, 0, 25, 0, 0, 0, 25, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
				
		_title = new JLabel("ADD WORDS");
		_title.setFont(new Font("Dialog", Font.BOLD, 40));
		GridBagConstraints gbc_lblAddWords = new GridBagConstraints();
		gbc_lblAddWords.insets = new Insets(0, 0, 5, 0);
		gbc_lblAddWords.gridx = 0;
		gbc_lblAddWords.gridy = 1;
		add(_title, gbc_lblAddWords);
		
		lblSelectALevel = new JLabel("Select A Level:");
		lblSelectALevel.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblSelectALevel = new GridBagConstraints();
		gbc_lblSelectALevel.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectALevel.gridx = 0;
		gbc_lblSelectALevel.gridy = 3;
		add(lblSelectALevel, gbc_lblSelectALevel);
		
		_addLevel = new JComboBox<WordList>(_data.getAllLists());
		_addLevel.setToolTipText("Select A Level To Add The Word To");
		_addLevel.setPreferredSize(new Dimension(300,50));
		_addLevel.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc__addLevel = new GridBagConstraints();
		gbc__addLevel.insets = new Insets(0, 0, 5, 0);
		gbc__addLevel.gridx = 0;
		gbc__addLevel.gridy = 4;
		add(_addLevel, gbc__addLevel);
		
		lblPleaseEnterThe = new JLabel("Please Enter The Word:");
		lblPleaseEnterThe.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblPleaseEnterThe = new GridBagConstraints();
		gbc_lblPleaseEnterThe.insets = new Insets(0, 0, 5, 0);
		gbc_lblPleaseEnterThe.gridx = 0;
		gbc_lblPleaseEnterThe.gridy = 6;
		add(lblPleaseEnterThe, gbc_lblPleaseEnterThe);
		
		_addInput = new JTextField();
		_addInput.setToolTipText("Enter The Word You Wish To Add");
		_addInput.setPreferredSize(new Dimension(300,50));
		_addInput.setFont(new Font("Dialog", Font.PLAIN, 25));
		GridBagConstraints gbc__addInput = new GridBagConstraints();
		gbc__addInput.insets = new Insets(0, 0, 5, 0);
		gbc__addInput.gridx = 0;
		gbc__addInput.gridy = 7;
		add(_addInput, gbc__addInput);
		
		_submit = getButton("Submit");
		_submit.setToolTipText("Press To Add The Word");
		GridBagConstraints gbc__submit = new GridBagConstraints();
		gbc__submit.insets = new Insets(0, 0, 5, 0);
		gbc__submit.gridx = 0;
		gbc__submit.gridy = 9;
		add(_submit, gbc__submit);
		
		_submit.addActionListener(this);
				
		_return = getButton("Return");
		_return.setToolTipText("Return To The Settings Menu");
		GridBagConstraints gbc__return = new GridBagConstraints();
		gbc__return.insets = new Insets(0, 0, 5, 0);
		gbc__return.gridx = 0;
		gbc__return.gridy = 11;
		add(_return, gbc__return);
		_return.addActionListener(this);
				
	}
		
	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_submit)) {
			
			String listName = _addLevel.getSelectedItem().toString();
			
			if (_data.getList(listName).contains(_addInput.getText().trim())) {
				JOptionPane.showMessageDialog(_GUI, "Sorry That Word Already Exists");
				return;
			}
			
			_data.getList(listName).add(new Word(_addInput.getText().trim()));
			
			JOptionPane.showMessageDialog(this, "The Word \"" + _addInput.getText() + "\" Has Been Added");
			
		} else if (src.equals(_return)) {
			changeScreen(new Settings());
		}

	}
	
}
