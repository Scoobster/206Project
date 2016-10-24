package voxspell.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import voxspell.data.QuizGame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;

/**
 * 
 * Class that is the Completion screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class CompletionScreen extends GUIElement {
	
	private QuizGame _quiz;
	
	private JLabel _scoreLabel;
	private JProgressBar _levelProgress;
	private JLabel _levelLabel;
	private JButton _playAgain;
	private JButton _new;
	private JButton _viewStats;
	private JButton _return;
	private JSeparator separator_1;
	private JSeparator separator;
	private JLabel lblNewLabel;
	private JButton _rewards;
	private JLabel lblNewLabel_1;
	private JLabel lblYourOverallScore;
	private JLabel _overallScore;

	/**
	 * Create the panel.
	 */
	public CompletionScreen(QuizGame previousGame) {
		super();
		_quiz = previousGame;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 240, 0, 250, 0};
		gridBagLayout.rowHeights = new int[]{50, 10, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel lblSomeTitle = new JLabel("You Completed The Quiz!");
		lblSomeTitle.setFont(new Font("Dialog", Font.BOLD, 40));
		GridBagConstraints gbc_lblSomeTitle = new GridBagConstraints();
		gbc_lblSomeTitle.gridwidth = 4;
		gbc_lblSomeTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblSomeTitle.gridx = 0;
		gbc_lblSomeTitle.gridy = 0;
		add(lblSomeTitle, gbc_lblSomeTitle);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.gridwidth = 4;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 1;
		add(separator_1, gbc_separator_1);
		
		JLabel lblYouScored = new JLabel("You Scored: ");
		lblYouScored.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouScored.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc_lblYouScored = new GridBagConstraints();
		gbc_lblYouScored.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblYouScored.insets = new Insets(0, 0, 5, 5);
		gbc_lblYouScored.gridx = 0;
		gbc_lblYouScored.gridy = 2;
		add(lblYouScored, gbc_lblYouScored);
		
		_scoreLabel = new JLabel("" + _quiz.getScore());
		_scoreLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc__scoreLabel = new GridBagConstraints();
		gbc__scoreLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc__scoreLabel.insets = new Insets(0, 0, 5, 5);
		gbc__scoreLabel.gridx = 1;
		gbc__scoreLabel.gridy = 2;
		add(_scoreLabel, gbc__scoreLabel);
		
		separator = new JSeparator();
		separator.setForeground(new Color(0, 100, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridheight = 9;
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.insets = new Insets(0, 0, 0, 5);
		gbc_separator.gridx = 2;
		gbc_separator.gridy = 3;
		add(separator, gbc_separator);
		
		lblYourOverallScore = new JLabel("Your Overall Score: ");
		lblYourOverallScore.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc_lblYourOverallScore = new GridBagConstraints();
		gbc_lblYourOverallScore.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblYourOverallScore.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourOverallScore.gridx = 0;
		gbc_lblYourOverallScore.gridy = 3;
		add(lblYourOverallScore, gbc_lblYourOverallScore);
		
		_overallScore = new JLabel("" + _data.getScore());
		_overallScore.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc__overallScore = new GridBagConstraints();
		gbc__overallScore.anchor = GridBagConstraints.NORTHWEST;
		gbc__overallScore.insets = new Insets(0, 0, 5, 5);
		gbc__overallScore.gridx = 1;
		gbc__overallScore.gridy = 3;
		add(_overallScore, gbc__overallScore);
		
		JLabel lblYourCurrentLevel = new JLabel("Your Current Level: ");
		lblYourCurrentLevel.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc_lblYourCurrentLevel = new GridBagConstraints();
		gbc_lblYourCurrentLevel.gridwidth = 2;
		gbc_lblYourCurrentLevel.anchor = GridBagConstraints.SOUTH;
		gbc_lblYourCurrentLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourCurrentLevel.gridx = 0;
		gbc_lblYourCurrentLevel.gridy = 4;
		add(lblYourCurrentLevel, gbc_lblYourCurrentLevel);
		
		_playAgain = new JButton("Continue");
		_playAgain.setToolTipText("Play The Quiz Again At Your Current Level");
		_playAgain.setFont(new Font("Dialog", Font.BOLD, 15));
		_playAgain.setPreferredSize(new Dimension(200,50));
		GridBagConstraints gbc__playAgain = new GridBagConstraints();
		gbc__playAgain.insets = new Insets(0, 0, 5, 0);
		gbc__playAgain.gridx = 3;
		gbc__playAgain.gridy = 4;
		add(_playAgain, gbc__playAgain);
		
		_playAgain.addActionListener(this);
		
		_levelLabel = new JLabel(_data.getCurrentLevel().getName());
		_levelLabel.setVerticalAlignment(SwingConstants.TOP);
		_levelLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc__levelLabel = new GridBagConstraints();
		gbc__levelLabel.anchor = GridBagConstraints.NORTH;
		gbc__levelLabel.gridwidth = 2;
		gbc__levelLabel.insets = new Insets(0, 0, 5, 5);
		gbc__levelLabel.gridx = 0;
		gbc__levelLabel.gridy = 5;
		add(_levelLabel, gbc__levelLabel);
		
		lblNewLabel = new JLabel("Progress To Next Level");
		lblNewLabel.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 6;
		add(lblNewLabel, gbc_lblNewLabel);
		
		_new = new JButton("New Quiz");
		_new.setToolTipText("Go Back To The Quiz Select Screen");
		_new.setFont(new Font("Dialog", Font.BOLD, 15));
		_new.setPreferredSize(new Dimension(200,50));
		GridBagConstraints gbc__new = new GridBagConstraints();
		gbc__new.insets = new Insets(0, 0, 5, 0);
		gbc__new.gridx = 3;
		gbc__new.gridy = 6;
		add(_new, gbc__new);
		_new.addActionListener(this);
		
		_levelProgress = new JProgressBar();
		_levelProgress.setToolTipText("Score Required To Unlock Next Level: " + (_data.scoreToNextLevel() - _data.getScore()));
		_levelProgress.setBackground(new Color(238, 238, 238));
		_levelProgress.setForeground(new Color(50, 205, 50));
		_levelProgress.setPreferredSize(new Dimension(500, 50));
		_levelProgress.setMinimum(_data.scoreToCurrentLevel());
		_levelProgress.setMaximum(_data.scoreToNextLevel());
		_levelProgress.setValue(_data.getScore());
		GridBagConstraints gbc__levelProgress = new GridBagConstraints();
		gbc__levelProgress.gridwidth = 2;
		gbc__levelProgress.insets = new Insets(0, 0, 5, 5);
		gbc__levelProgress.gridx = 0;
		gbc__levelProgress.gridy = 7;
		add(_levelProgress, gbc__levelProgress);
		
		_viewStats = new JButton("Overall Statistics");
		_viewStats.setToolTipText("View The Overall Statistics");
		_viewStats.setFont(new Font("Dialog", Font.BOLD, 15));
		_viewStats.setPreferredSize(new Dimension(200,50));
		GridBagConstraints gbc__viewStats = new GridBagConstraints();
		gbc__viewStats.insets = new Insets(0, 0, 5, 0);
		gbc__viewStats.gridx = 3;
		gbc__viewStats.gridy = 8;
		add(_viewStats, gbc__viewStats);
		_viewStats.addActionListener(this);
		
		lblNewLabel_1 = new JLabel("Click Here To View Your Rewards");
		lblNewLabel_1.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 9;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		_rewards = new JButton("Rewards");
		_rewards.setToolTipText("Go To The Rewards Screen");
		_rewards.setFont(new Font("Dialog", Font.PLAIN, 20));
		_rewards.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc__rewards = new GridBagConstraints();
		gbc__rewards.gridwidth = 2;
		gbc__rewards.insets = new Insets(0, 0, 5, 5);
		gbc__rewards.gridx = 0;
		gbc__rewards.gridy = 10;
		add(_rewards, gbc__rewards);
		_rewards.addActionListener(this);
		
		_return = new JButton("Return");
		_return.setToolTipText("Return To The Main Menu");
		_return.setFont(new Font("Dialog", Font.BOLD, 15));
		_return.setPreferredSize(new Dimension(200,50));
		GridBagConstraints gbc__return = new GridBagConstraints();
		gbc__return.insets = new Insets(0, 0, 5, 0);
		gbc__return.gridx = 3;
		gbc__return.gridy = 10;
		add(_return, gbc__return);
		_return.addActionListener(this);

	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_playAgain)) {
			changeScreen(new QuizScreen(_data.getCurrentLevel()));
		} else if (src.equals(_new)) {
			changeScreen(new QuizSelect());
		} else if (src.equals(_viewStats)) {
			changeScreen(new ViewStats(this));
		} else if (src.equals(_return)) {
			changeScreen(new MainMenu());
		} else if (src.equals(_rewards)) {
			changeScreen(new Rewards());
		}
		
	}
}
