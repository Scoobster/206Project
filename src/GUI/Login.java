package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.Data.DataStore;

public class Login extends GUIElement {
		
	private JLabel title = new JLabel("VOXSPELL");
	private JLabel loginLabel = new JLabel("Login");
	private JComboBox<String> userSelect;
	private JButton submit = new JButton("Login");
	private JButton newUser = new JButton("New User");

	public Login(JFrame frame, DataStore data) {
		super(frame, data);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		
		c.gridy = 0;
		c.gridx = 0;
		add(title, c);
		
		c.gridy = 1;
		add(loginLabel, c);
		
		c.gridy = 2;
		userSelect = new JComboBox<String>(new DefaultComboBoxModel(_data.getUsers().toArray()));
		add(userSelect, c);
		
		c.gridy = 3;
		submit.addActionListener(this);
		add(submit, c);
		
		c.gridy = 4;
		newUser.addActionListener(this);
		add(newUser, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(submit)) {
			String user = (String) userSelect.getSelectedItem();
			if (user.equals("Select User:")) {
				JOptionPane.showMessageDialog(_GUI, "Please Select A User");
				return;
			} else {
				_data.setUser(user);
			}
		} else if (e.getSource().equals(newUser)) {
			
			String user = JOptionPane.showInputDialog("Please Enter A Username:");
			if (_data.getUsers().contains(user)) {
				JOptionPane.showMessageDialog(_GUI, "User Already Exists!");
				return;
			}
			_data.createUser(user);
		}
		
		_GUI.getContentPane().removeAll();
		_GUI.getContentPane().add(new MainMenu(_GUI, _data));
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}

}
