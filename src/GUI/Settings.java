package GUI;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Data.Data;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class Settings extends GUIElement {
	private JButton _test;
	private JButton _selectList;
	private JButton _addRemove;
	private JButton _clear;
	private JButton _delete;
	private JButton _return;
	private JComboBox _voiceSelect;
	
	

	/**
	 * Create the panel.
	 */
	public Settings(JFrame frame, Data data) {
		super(frame, data);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 25, 25};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 25, 0, 25, 25, 0, 25, 25, 0, 25, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSettings = new JLabel("SETTINGS");
		lblSettings.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.gridwidth = 3;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 0);
		gbc_lblSettings.gridx = 0;
		gbc_lblSettings.gridy = 0;
		add(lblSettings, gbc_lblSettings);
		
		JLabel lblSelectAVoice = new JLabel("Select A Voice:");
		GridBagConstraints gbc_lblSelectAVoice = new GridBagConstraints();
		gbc_lblSelectAVoice.anchor = GridBagConstraints.WEST;
		gbc_lblSelectAVoice.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectAVoice.gridx = 0;
		gbc_lblSelectAVoice.gridy = 2;
		add(lblSelectAVoice, gbc_lblSelectAVoice);
		
		_voiceSelect = new JComboBox();
		GridBagConstraints gbc__voiceSelect = new GridBagConstraints();
		gbc__voiceSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc__voiceSelect.gridwidth = 2;
		gbc__voiceSelect.insets = new Insets(0, 0, 5, 5);
		gbc__voiceSelect.gridx = 0;
		gbc__voiceSelect.gridy = 3;
		_voiceSelect.setPreferredSize(new Dimension(150,50));
		add(_voiceSelect, gbc__voiceSelect);
		
		_test = new JButton("");
		GridBagConstraints gbc__test = new GridBagConstraints();
		gbc__test.insets = new Insets(0, 0, 5, 0);
		gbc__test.gridx = 2;
		gbc__test.gridy = 3;
		
		_test.setPreferredSize(new Dimension(50,50));
		ImageIcon ico = new ImageIcon("res/SpeakerIcon.png");
		_test.setIcon(ico);
		add(_test, gbc__test);
		
		_selectList = new JButton("Select A New Spelling List");
		GridBagConstraints gbc__selectList = new GridBagConstraints();
		gbc__selectList.fill = GridBagConstraints.BOTH;
		gbc__selectList.gridwidth = 3;
		gbc__selectList.insets = new Insets(0, 0, 5, 0);
		gbc__selectList.gridx = 0;
		gbc__selectList.gridy = 5;
		add(_selectList, gbc__selectList);
		
		_addRemove = new JButton("Add/Remove Word");
		GridBagConstraints gbc__addRemove = new GridBagConstraints();
		gbc__addRemove.fill = GridBagConstraints.BOTH;
		gbc__addRemove.gridwidth = 3;
		gbc__addRemove.insets = new Insets(0, 0, 5, 0);
		gbc__addRemove.gridx = 0;
		gbc__addRemove.gridy = 6;
		add(_addRemove, gbc__addRemove);
		
		_clear = new JButton("Clear Data");
		GridBagConstraints gbc__clear = new GridBagConstraints();
		gbc__clear.fill = GridBagConstraints.BOTH;
		gbc__clear.gridwidth = 3;
		gbc__clear.insets = new Insets(0, 0, 5, 0);
		gbc__clear.gridx = 0;
		gbc__clear.gridy = 8;
		add(_clear, gbc__clear);
		
		_delete = new JButton("Delete User");
		GridBagConstraints gbc__delete = new GridBagConstraints();
		gbc__delete.fill = GridBagConstraints.BOTH;
		gbc__delete.gridwidth = 3;
		gbc__delete.insets = new Insets(0, 0, 5, 0);
		gbc__delete.gridx = 0;
		gbc__delete.gridy = 9;
		add(_delete, gbc__delete);
		
		_return = new JButton("Return to Main Menu");
		GridBagConstraints gbc__return = new GridBagConstraints();
		gbc__return.fill = GridBagConstraints.BOTH;
		gbc__return.gridwidth = 3;
		gbc__return.gridx = 0;
		gbc__return.gridy = 11;
		add(_return, gbc__return);
		
		_voiceSelect.addActionListener(this);
		_test.addActionListener(this);
		_selectList.addActionListener(this);
		_addRemove.addActionListener(this);
		_clear.addActionListener(this);
		_delete.addActionListener(this);
		_return.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_voiceSelect)) {
			_data.setVoice((String) _voiceSelect.getSelectedItem());
		} else if (src.equals(_test)) {
			testVoice();
		} else if (src.equals(_selectList)) {
			
		}
		
	}
	
	/**
	 * Method that makes a call to festival to say a sentence to test out the different voices
	 */
	private void testVoice() {
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			protected Void doInBackground() throws Exception {

				ProcessBuilder builder = new ProcessBuilder("/usr/bin/festival", _data.getVoiceCode(_data.getVoice()), "(SayText \"Do you like this voice?\")");

				try {
					builder.start();
				} catch (IOException e) {
					e.printStackTrace();
				}				

				return null;
			}
		};
		worker.execute();
	}
}