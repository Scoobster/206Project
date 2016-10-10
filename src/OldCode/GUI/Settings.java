package OldCode.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import GUI.Data.DataStore;

public class Settings extends GUIElement {
	
	private JLabel _title = new JLabel("SETTINGS");
	private JComboBox<String> _voices = new JComboBox<String>(new String[] {"American", "British", "New Zealander"});
	private JButton _test = new JButton();
	private JButton _selectList = new JButton("Select A New List");
	private JButton _addWord = new JButton("Add/Remove Word");
	private JButton _clear = new JButton("Clear Data");
	private JButton _delete = new JButton("Delete User");
	private JButton _return = new JButton("Return to Main Menu");
	
	private JFileChooser _fileChoose = new JFileChooser();

	protected Settings(JFrame frame, DataStore data) {
		super(frame, data);
				
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(_title, c);
		
		c.gridwidth = 1;
		c.gridy = 1;
		_voices.addActionListener(this);
		add(_voices, c);
		
		c.gridx = 1;
		_test.setPreferredSize(new Dimension(50,50));
		ImageIcon ico = new ImageIcon("res/SpeakerIcon.png");
		_test.setIcon(ico);
		_test.addActionListener(this);
		add(_test, c);
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		_selectList.addActionListener(this);
		add(_selectList, c);
		
		c.gridy = 3;
		_addWord.addActionListener(this);
		add(_addWord, c);
		
		c.gridy = 4;
		_clear.addActionListener(this);
		add(_clear, c);
		
		c.gridy = 5;
		_delete.addActionListener(this);
		add(_delete, c);
		
		c.gridy = 6;
		_return.addActionListener(this);
		add(_return, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(_clear)) {
			clearData();
		} else if (e.getSource().equals(_test)) {
			testVoice();
		} else if (e.getSource().equals(_voices)) {
			System.out.println("Voice Selected:" + _voices.getSelectedItem());
			_data.setVoice((String) _voices.getSelectedItem());
		} else if (e.getSource().equals(_selectList)) {
			selectAList();
		} else if (e.getSource().equals(_delete)) {
			deleteAUser();
		} else if (e.getSource().equals(_addWord)) {
			_GUI.getContentPane().add(new AddRemoveWord(_GUI, _data, this));
			_GUI.revalidate();
			_GUI.repaint();
		} else if (e.getSource().equals(_return)) {
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new MainMenu(_GUI, _data));
			_GUI.revalidate();
			_GUI.repaint();
		} 
		
	}
	
	private void clearData() {
		int reply = JOptionPane.showConfirmDialog(_GUI, "Are you sure you wish to clear all data?\nYour statistics will not be able to be recovered.");
		if (reply == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(_GUI, "Your data has been reset.");
			_data.overrideAll();
		}
	}
	
	private void testVoice() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			protected Void doInBackground() throws Exception {

				ProcessBuilder builder = new ProcessBuilder("/usr/bin/festival", _data.getVoice(), "(SayText \"Do you like this voice?\")");

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
	
	private void selectAList() {
		
		SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				
				JFrame frame = new JFrame("Choose A List:");
				frame.setVisible(true);
				frame.setSize(500, 400);
				_fileChoose.addActionListener( new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						int reply = _fileChoose.showOpenDialog(_fileChoose);
						if (reply == JFileChooser.APPROVE_OPTION) {
							int replyTwo = JOptionPane.showConfirmDialog(_GUI, "Are you sure you wish to load a new spelling list?\n"
									+ "This will clear all previous data!");
							if (replyTwo == JOptionPane.YES_OPTION) {
								_data.setList(_fileChoose.getSelectedFile().getAbsolutePath());
							}
						}
						frame.dispose();
					}
					
				});
				frame.add(_fileChoose);
								
				return null;
			}
			
			
		};
		worker.execute();
		
	}
	
	private void deleteAUser() {
		
		String user = (String) JOptionPane.showInputDialog(_GUI, 
		        "Select A User To Delete", "User",
		        JOptionPane.QUESTION_MESSAGE, 
		        null, 
		        _data.getUsers().toArray(), 
		        _data.getUsers().toArray()[0]);
		
		int reply;
		if (user.equals("Select User:")) {
			JOptionPane.showMessageDialog(_GUI, "Not A Valid User!");
			return;
		} else if (user.equals(_data.getCurrentUser())) {
			reply = JOptionPane.showConfirmDialog(_GUI, "Are You Sure You Wish To Delete Current User?");
		} else {
			reply = JOptionPane.showConfirmDialog(_GUI, "Are You Sure You Wish To Delete User: " + user + "?");
		}
		
		if (reply == JOptionPane.YES_OPTION) {
			_data.deleteUser(user);
			if (user.equals(_data.getCurrentUser())) {
				JOptionPane.showMessageDialog(_GUI, "User Deleted\nYou Will Be Redirected Back To The Login Screen");
				_GUI.getContentPane().removeAll();
				_GUI.getContentPane().add(new Login(_GUI, _data));
				_GUI.revalidate();
				_GUI.repaint();
			} else {
				JOptionPane.showMessageDialog(_GUI, "User Deleted");
			}
		}
		
	}

}
