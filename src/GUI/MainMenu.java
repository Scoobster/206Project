package GUI;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Data.Data;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;

public class MainMenu extends GUIElement {
	
	private JButton _quiz;
	private JButton _review;
	private JButton _stats;
	private JButton _rewards;
	private JButton _settings;
	private JButton _logout;

	/**
	 * Create the panel.
	 */
	public MainMenu(JFrame frame, Data data) {
		super(frame, data);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{75, 150, 75, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 25, 0, 25, 0, 25, 0, 25, 0, 25, 0, 25, 25, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMainMenu = new JLabel("MAIN MENU");
		lblMainMenu.setFont(new Font("Dialog", Font.BOLD, 25));
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblMainMenu = new GridBagConstraints();
		gbc_lblMainMenu.gridwidth = 3;
		gbc_lblMainMenu.insets = new Insets(0, 0, 5, 0);
		gbc_lblMainMenu.gridx = 0;
		gbc_lblMainMenu.gridy = 0;
		add(lblMainMenu, gbc_lblMainMenu);
		
		_quiz = new JButton("Play Quiz");
		GridBagConstraints gbc__quiz = new GridBagConstraints();
		gbc__quiz.fill = GridBagConstraints.BOTH;
		gbc__quiz.insets = new Insets(0, 0, 5, 5);
		gbc__quiz.gridx = 1;
		gbc__quiz.gridy = 2;
		add(_quiz, gbc__quiz);
		
		_review = new JButton("Review Quiz");
		GridBagConstraints gbc__review = new GridBagConstraints();
		gbc__review.fill = GridBagConstraints.BOTH;
		gbc__review.insets = new Insets(0, 0, 5, 5);
		gbc__review.gridx = 1;
		gbc__review.gridy = 4;
		add(_review, gbc__review);
		
		_stats = new JButton("Statistics");
		GridBagConstraints gbc__stats = new GridBagConstraints();
		gbc__stats.fill = GridBagConstraints.BOTH;
		gbc__stats.insets = new Insets(0, 0, 5, 5);
		gbc__stats.gridx = 1;
		gbc__stats.gridy = 6;
		add(_stats, gbc__stats);
		
		_rewards = new JButton("Rewards");
		GridBagConstraints gbc__rewards = new GridBagConstraints();
		gbc__rewards.fill = GridBagConstraints.BOTH;
		gbc__rewards.insets = new Insets(0, 0, 5, 5);
		gbc__rewards.gridx = 1;
		gbc__rewards.gridy = 8;
		add(_rewards, gbc__rewards);
		
		_settings = new JButton("Settings");
		GridBagConstraints gbc__settings = new GridBagConstraints();
		gbc__settings.fill = GridBagConstraints.BOTH;
		gbc__settings.insets = new Insets(0, 0, 5, 5);
		gbc__settings.gridx = 1;
		gbc__settings.gridy = 10;
		add(_settings, gbc__settings);
		
		_logout = new JButton("Logout");
		GridBagConstraints gbc__logout = new GridBagConstraints();
		gbc__logout.anchor = GridBagConstraints.EAST;
		gbc__logout.gridwidth = 2;
		gbc__logout.insets = new Insets(0, 0, 5, 0);
		gbc__logout.gridx = 1;
		gbc__logout.gridy = 12;
		add(_logout, gbc__logout);
		
		_quiz.addActionListener(this);
		_review.addActionListener(this);
		_stats.addActionListener(this);
		_rewards.addActionListener(this);
		_settings.addActionListener(this);
		_logout.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		/*
		if (src.equals(_quiz)) {
			changeScreen(new LevelSelect(_GUI, _data));
		} else if (src.equals(_review)) {
			/*
			 * Will need to be changed
			 *
			changeScreen(new QuizScreen(_GUI, _data));
		} else if (src.equals(_rewards)) {
			changeScreen(new RewardsScreen(_GUI, _data));
		} else if (src.equals(_stats)) {
			changeScreen(new ViewStats(_GUI, _data));
		} else */if (src.equals(_settings)) {
			changeScreen(new Settings(_GUI, _data));
		} else if (src.equals(_logout)) {
			_data.logout();
			changeScreen(new Login(_GUI, _data));
		}
		
	}

}
