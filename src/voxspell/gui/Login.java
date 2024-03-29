package voxspell.gui;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Dimension;

/**
 * 
 * Class that is the Login screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class Login extends GUIElement {
	
	private JComboBox<String> _userSelect;
	private JButton _login;
	private JButton _new;

	/**
	 * Create the panel.
	 */
	public Login() {
		super();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{30, 40, 0, 25, 25, 0, 25, 25, 0, 20, 25, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblWelcomeToVoxspell = new JLabel("WELCOME TO");
		lblWelcomeToVoxspell.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToVoxspell.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc_lblWelcomeToVoxspell = new GridBagConstraints();
		gbc_lblWelcomeToVoxspell.insets = new Insets(0, 0, 5, 0);
		gbc_lblWelcomeToVoxspell.gridx = 0;
		gbc_lblWelcomeToVoxspell.gridy = 0;
		add(lblWelcomeToVoxspell, gbc_lblWelcomeToVoxspell);
		
		JLabel lblVoxspell = new JLabel("VOXSPELL");
		lblVoxspell.setFont(new Font("Dialog", Font.BOLD, 50));
		lblVoxspell.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVoxspell = new GridBagConstraints();
		gbc_lblVoxspell.insets = new Insets(0, 0, 5, 0);
		gbc_lblVoxspell.gridx = 0;
		gbc_lblVoxspell.gridy = 1;
		add(lblVoxspell, gbc_lblVoxspell);
		
		JLabel lblUserToLogin = new JLabel("Please Select A User to Login:");
		lblUserToLogin.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserToLogin.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc_lblUserToLogin = new GridBagConstraints();
		gbc_lblUserToLogin.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserToLogin.gridx = 0;
		gbc_lblUserToLogin.gridy = 5;
		add(lblUserToLogin, gbc_lblUserToLogin);
		
		_userSelect = new JComboBox<String>();
		_userSelect.setFont(new Font("Dialog", Font.BOLD, 20));
		_userSelect.setToolTipText("Please Select A User:");
		_userSelect.setPreferredSize(new Dimension(400,50));
		GridBagConstraints gbc__userSelect = new GridBagConstraints();
		gbc__userSelect.insets = new Insets(0, 0, 5, 0);
		gbc__userSelect.gridx = 0;
		gbc__userSelect.gridy = 6;
		add(_userSelect, gbc__userSelect);
		
		_login = getButton("Login");
		_login.setToolTipText("Press To Login In To Selected User");
		GridBagConstraints gbc__login = new GridBagConstraints();
		gbc__login.insets = new Insets(0, 0, 5, 0);
		gbc__login.gridx = 0;
		gbc__login.gridy = 7;
		add(_login, gbc__login);
		
		JLabel lblCreateANew = new JLabel("Create A New Account:");
		lblCreateANew.setFont(new Font("Dialog", Font.ITALIC | Font.BOLD, 15));
		GridBagConstraints gbc_lblCreateANew = new GridBagConstraints();
		gbc_lblCreateANew.anchor = GridBagConstraints.SOUTH;
		gbc_lblCreateANew.insets = new Insets(0, 0, 5, 0);
		gbc_lblCreateANew.gridx = 0;
		gbc_lblCreateANew.gridy = 9;
		add(lblCreateANew, gbc_lblCreateANew);
		
		_new = getButton("New Account");
		_new.setToolTipText("Press To Create A New User");
		GridBagConstraints gbc__new = new GridBagConstraints();
		gbc__new.insets = new Insets(0, 0, 5, 0);
		gbc__new.gridx = 0;
		gbc__new.gridy = 10;
		add(_new, gbc__new);
		
		for (String str : _data.getUserList()) {
			_userSelect.addItem(str);
		}
		
		_login.addActionListener(this);
		_new.addActionListener(this);

	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(_login)) {
			String user = (String) _userSelect.getSelectedItem();
			if (user == null || user.isEmpty()) {
				JOptionPane.showMessageDialog(this, "ERROR: That user is invalid");
				return;
			}
			_data.setUser(user);
		} else if (source.equals(_new)) {
			String newUser = JOptionPane.showInputDialog("Please enter a user name:");
			if (newUser == null ) {
				return;
			} else if (_data.getUserList().contains(newUser)) {
				JOptionPane.showMessageDialog(this, "ERROR: Sorry that user name is already taken");
				return;
			}
			_data.addNewUser(newUser.trim());
		}
		
		changeScreen(new MainMenu());
		
	}

}
