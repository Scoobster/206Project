package voxspell.gui;

import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;

/**
 * 
 * Class that is the Main Menu screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class MainMenu extends GUIElement {
	
	private JButton _quiz;
	private JButton _stats;
	private JButton _rewards;
	private JButton _settings;
	private JButton _logout;

	/**
	 * Create the panel.
	 */
	public MainMenu() {
		super();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{250, 300, 250, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 25, 0, 25, 0, 25, 0, 25, 0, 25, 0, 25, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMainMenu = new JLabel("MAIN MENU");
		lblMainMenu.setFont(new Font("Dialog", Font.BOLD, 40));
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblMainMenu = new GridBagConstraints();
		gbc_lblMainMenu.gridwidth = 3;
		gbc_lblMainMenu.insets = new Insets(0, 0, 5, 0);
		gbc_lblMainMenu.gridx = 0;
		gbc_lblMainMenu.gridy = 1;
		add(lblMainMenu, gbc_lblMainMenu);
		
		_quiz = getButton("Play Quiz");
		_quiz.setToolTipText("Go To The Quiz Select Screen To Play The Quiz");
		GridBagConstraints gbc__quiz = new GridBagConstraints();
		gbc__quiz.fill = GridBagConstraints.BOTH;
		gbc__quiz.insets = new Insets(0, 0, 5, 5);
		gbc__quiz.gridx = 1;
		gbc__quiz.gridy = 4;
		add(_quiz, gbc__quiz);
		
		_quiz.addActionListener(this);
		
		_stats = getButton("Statistics");
		_stats.setToolTipText("Press To View Your Overall Statistics");
		GridBagConstraints gbc__stats = new GridBagConstraints();
		gbc__stats.fill = GridBagConstraints.BOTH;
		gbc__stats.insets = new Insets(0, 0, 5, 5);
		gbc__stats.gridx = 1;
		gbc__stats.gridy = 6;
		add(_stats, gbc__stats);
		
		_rewards = getButton("Rewards");
		_rewards.setToolTipText("Press To View Your Rewards");
		GridBagConstraints gbc__rewards = new GridBagConstraints();
		gbc__rewards.fill = GridBagConstraints.BOTH;
		gbc__rewards.insets = new Insets(0, 0, 5, 5);
		gbc__rewards.gridx = 1;
		gbc__rewards.gridy = 8;
		add(_rewards, gbc__rewards);
		
		_settings = getButton("Settings");
		_settings.setToolTipText("Press To Go To The Settings Menu");
		GridBagConstraints gbc__settings = new GridBagConstraints();
		gbc__settings.fill = GridBagConstraints.BOTH;
		gbc__settings.insets = new Insets(0, 0, 5, 5);
		gbc__settings.gridx = 1;
		gbc__settings.gridy = 10;
		add(_settings, gbc__settings);
		_settings.addActionListener(this);
		
		_logout = getButton("Logout");
		_logout.setToolTipText("Press To Logout Of Current User And Return To Login Screen");
		GridBagConstraints gbc__logout = new GridBagConstraints();
		gbc__logout.insets = new Insets(0, 0, 5, 0);
		gbc__logout.gridx = 2;
		gbc__logout.gridy = 12;
		add(_logout, gbc__logout);
		_stats.addActionListener(this);
		_rewards.addActionListener(this);
		_logout.addActionListener(this);

	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_quiz)) {
			changeScreen(new QuizSelect());
		} else if (src.equals(_rewards)) {
			changeScreen(new Rewards());
		} else if (src.equals(_stats)) {
			changeScreen(new ViewStats(this));
		} else if (src.equals(_settings)) {
			changeScreen(new Settings());
		} else if (src.equals(_logout)) {
			_data.logout();
			changeScreen(new Login());
		}
		
	}

}
