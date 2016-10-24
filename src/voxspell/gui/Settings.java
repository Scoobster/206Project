package voxspell.gui;

import java.awt.event.ActionEvent;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * 
 * Class that is the Settings screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class Settings extends GUIElement {
	private JButton _test;
	private JButton _selectList;
	private JButton _add;
	private JButton _clear;
	private JButton _delete;
	private JButton _return;
	private JComboBox<String> _voiceSelect;
	
	private JFileChooser _fileChoose = new JFileChooser();
	
	/**
	 * Create the panel.
	 */
	public Settings() {
		super();
		setBackground(new Color(204, 255, 204));
		_festival.setScreen(this);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{250, 300, 250};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 25, 0, 25, 25, 0, 25, 25, 0, 25, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSettings = new JLabel("SETTINGS");
		lblSettings.setFont(new Font("Dialog", Font.BOLD, 40));
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.gridwidth = 3;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 0);
		gbc_lblSettings.gridx = 0;
		gbc_lblSettings.gridy = 0;
		add(lblSettings, gbc_lblSettings);
		
		JLabel lblSelectAVoice = new JLabel("Select A Voice:");
		lblSelectAVoice.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAVoice.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblSelectAVoice = new GridBagConstraints();
		gbc_lblSelectAVoice.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectAVoice.gridx = 1;
		gbc_lblSelectAVoice.gridy = 2;
		add(lblSelectAVoice, gbc_lblSelectAVoice);
		
		_voiceSelect = new JComboBox<String>();
		_voiceSelect.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc__voiceSelect = new GridBagConstraints();
		gbc__voiceSelect.anchor = GridBagConstraints.EAST;
		gbc__voiceSelect.gridwidth = 2;
		gbc__voiceSelect.insets = new Insets(0, 0, 5, 5);
		gbc__voiceSelect.gridx = 0;
		gbc__voiceSelect.gridy = 3;
		_voiceSelect.setPreferredSize(new Dimension(350,50));
		add(_voiceSelect, gbc__voiceSelect);
		
		_test = new JButton("");
		GridBagConstraints gbc__test = new GridBagConstraints();
		gbc__test.anchor = GridBagConstraints.WEST;
		gbc__test.insets = new Insets(0, 0, 5, 0);
		gbc__test.gridx = 2;
		gbc__test.gridy = 3;
		
		_test.setPreferredSize(new Dimension(50,50));
		ImageIcon ico = new ImageIcon("res/SpeakerIcon.png");
		_test.setIcon(ico);
		add(_test, gbc__test);
		
		_selectList = new JButton("Select A New Spelling List");
		_selectList.setFont(new Font("Dialog", Font.PLAIN, 20));
		_selectList.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc__selectList = new GridBagConstraints();
		gbc__selectList.insets = new Insets(0, 0, 5, 0);
		gbc__selectList.gridx = 1;
		gbc__selectList.gridy = 5;
		add(_selectList, gbc__selectList);
		
		_add = new JButton("Add Word");
		_add.setFont(new Font("Dialog", Font.PLAIN, 20));
		_add.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc__addRemove = new GridBagConstraints();
		gbc__addRemove.insets = new Insets(0, 0, 5, 0);
		gbc__addRemove.gridx = 1;
		gbc__addRemove.gridy = 6;
		add(_add, gbc__addRemove);
		
		_clear = new JButton("Clear Data");
		_clear.setFont(new Font("Dialog", Font.PLAIN, 20));
		_clear.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc__clear = new GridBagConstraints();
		gbc__clear.insets = new Insets(0, 0, 5, 0);
		gbc__clear.gridx = 1;
		gbc__clear.gridy = 8;
		add(_clear, gbc__clear);
		
		_delete = new JButton("Delete User");
		_delete.setFont(new Font("Dialog", Font.PLAIN, 20));
		_delete.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc__delete = new GridBagConstraints();
		gbc__delete.insets = new Insets(0, 0, 5, 0);
		gbc__delete.gridx = 1;
		gbc__delete.gridy = 9;
		add(_delete, gbc__delete);
		
		_return = new JButton("Return to Main Menu");
		_return.setFont(new Font("Dialog", Font.PLAIN, 20));
		_return.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc__return = new GridBagConstraints();
		gbc__return.gridx = 1;
		gbc__return.gridy = 11;
		add(_return, gbc__return);
		
		for (String voice : _festival.getVoicesSet()) {
			_voiceSelect.addItem(voice);
		}
		_voiceSelect.setSelectedItem(_festival.getVoice());
		
		_voiceSelect.addActionListener(this);
		_test.addActionListener(this);
		_selectList.addActionListener(this);
		_add.addActionListener(this);
		_clear.addActionListener(this);
		_delete.addActionListener(this);
		_return.addActionListener(this);
		
	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_voiceSelect)) {
			_festival.setVoice(_voiceSelect.getSelectedItem().toString());
		} else if (src.equals(_test)) {
			testVoice();
		} else if (src.equals(_selectList)) {
			selectAFile();
		} else if (src.equals(_add)) {
			changeScreen(new AddWord());
		} else if (src.equals(_clear)) {
			clearData();
		} else if (src.equals(_delete)) {
			deleteAUser();
		} else if (src.equals(_return)) {
			changeScreen(new MainMenu());
		} 
		
	}
	
	/**
	 * Method that makes a call to festival to say a sentence to test out the different voices
	 */
	private void testVoice() {
		enableButtons(false);
		_festival.sayWord("Do you like this voice?");
	}

	/**
	 * Method that makes the user select
	 */
	private void selectAFile() {
		int reply = _fileChoose.showOpenDialog(_GUI);
		if (reply == JFileChooser.APPROVE_OPTION) {
			int replyTwo = JOptionPane.showConfirmDialog(_GUI, "Are you sure you want to load a new spelling list?\nWarning: This WILL clear ALL your data!");
			if (replyTwo == JOptionPane.YES_OPTION) {
				_data.setFile(_fileChoose.getSelectedFile().getAbsolutePath());
			}
		}
	}

	/**
	 * Method that makes a confirm dialog that if the user clicks a confirm, then will call clearData on the Data instance
	 */
	private void clearData() {
		int reply = JOptionPane.showConfirmDialog(_GUI, "Are you sure you want to clear the data?\nWarning: This can NOT be recovered!");
		if (reply == JOptionPane.YES_OPTION) {
			_data.clearData();
		}
	}
	
	/**
	 * Method that asks the user to choose a user and then deletes that user
	 */
	private void deleteAUser() {
		
		String user = (String) JOptionPane.showInputDialog(_GUI, 
		        "Select A User To Delete", "User",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        _data.getUserList().toArray(), 
		        _data.getUserList().toArray()[0]);
		
		int reply;
		if (user.equals(_data.getUser())) {
			reply = JOptionPane.showConfirmDialog(_GUI, "Are You Sure You Wish To Delete The Current User: " + user + "?");
		} else {
			reply = JOptionPane.showConfirmDialog(_GUI, "Are You Sure You Wish To Delete User: " + user + "?");
		}
		
		if (reply == JOptionPane.YES_OPTION) {
			if (user.equals(_data.getUser())) {
				_data.deleteUser(user);
				JOptionPane.showMessageDialog(_GUI, "User Deleted\nYou Will Be Redirected Back To The Login Screen");
				changeScreen(new Login());
			} else {
				_data.deleteUser(user);
				JOptionPane.showMessageDialog(_GUI, "User Deleted");
			}
		}
		
	}
	
	/**
	 * Method that overrides the same method in GUIElement
	 * Makes the buttons relevant to festival enable or disable
	 */
	@Override
	public void enableButtons(boolean enable) {
		_test.setEnabled(enable);
	}
	
}
