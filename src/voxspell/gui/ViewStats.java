package voxspell.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import voxspell.data.Word;
import voxspell.data.WordList;

/**
 * 
 * Class that is the Statistics screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class ViewStats extends GUIElement {
	
	private GUIElement _previousScreen;
	private JButton _goBack = new JButton("Go Back");
	private JComboBox<String> _selectInfo;
	private JTable _table;
	private JLabel _title = new JLabel("Current Statistics");
	private JScrollPane _scroll;
	private JPanel _south = new JPanel();
	private JPanel _tableArea = new JPanel();
	private JPanel _overview = new JPanel();
	
	private Object[][] _specificLevel;
	private String[] _levelColumnHeads = {"Word", "Attempts Made", "Times Correct"};
	

	/**
	 * Constructor that sets up the JPanel
	 * @param previousScreen
	 */
	public ViewStats(GUIElement previousScreen) {
		super();
		_previousScreen = previousScreen;
		
		setLayout(new BorderLayout());
		
		add(_title, BorderLayout.NORTH);
		
		_south.setLayout(new GridBagLayout());
		_south.setBackground(new Color(204, 255, 204));
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 0.5;
		c.weighty = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		WordList[] nameList = _data.getAvailableLists();
		String[] nameList1 = new String[nameList.length + 1];
		nameList1[0] = "Overview";
		for (int i = 1; i < nameList1.length; i++) {
			nameList1[i] = nameList[i-1].getName();
		}
		
		_selectInfo = new JComboBox<String>(nameList1);
		_selectInfo.addActionListener(this);
		
		_south.add(_selectInfo, c);
		
		c.gridx = 3;
		c.gridwidth = 1;
		
		_goBack.setPreferredSize(new Dimension(100, 50));
		_goBack.addActionListener(this);
		
		_south.add(_goBack, c);
		
		add(_south, BorderLayout.SOUTH);
		
		_tableArea.setLayout(new BorderLayout());
		
		setupOverviewPanel();
		
		_table = new JTable();
		_table.setFillsViewportHeight(true);
		_table.setAutoCreateRowSorter(true);
		
		_scroll = new JScrollPane(_table);
		_tableArea.add(_overview, BorderLayout.CENTER);
		
		add(_tableArea, BorderLayout.CENTER);
		
	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _goBack) {
			changeScreen(_previousScreen);
			
		} else if (e.getSource() == _selectInfo) {
			_tableArea.removeAll();
			remove(_tableArea);
			String selected = _selectInfo.getSelectedItem().toString();
			if (selected.equals("Overview")) {
				_tableArea.add(_overview, BorderLayout.CENTER);
			} else {
				fillLevelSpecificArray(selected);
				_table = new JTable(_specificLevel, _levelColumnHeads);
				_table.setFillsViewportHeight(true);
				_table.setAutoCreateRowSorter(true);
				_scroll = new JScrollPane(_table);
				_tableArea.add(_scroll, BorderLayout.CENTER);
			}
			add(_tableArea, BorderLayout.CENTER);
			_GUI.revalidate();
			_GUI.repaint();
		}		
	}
	
	/**
	 * Method that setups up the overview panel
	 */
	private void setupOverviewPanel() {
		
		_overview.setBackground(new Color(204, 255, 204));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{395, 10, 395};
		gridBagLayout.rowHeights = new int[]{40, 30, 30, 30, 30, 50, 50, 50, 50, 50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		_overview.setLayout(gridBagLayout);
		
		JLabel lblOverviewStatistics = new JLabel("Overview Statistics");
		lblOverviewStatistics.setFont(new Font("Dialog", Font.BOLD, 30));
		GridBagConstraints gbc_lblOverviewStatistics = new GridBagConstraints();
		gbc_lblOverviewStatistics.gridwidth = 3;
		gbc_lblOverviewStatistics.insets = new Insets(0, 0, 5, 0);
		gbc_lblOverviewStatistics.gridx = 0;
		gbc_lblOverviewStatistics.gridy = 0;
		_overview.add(lblOverviewStatistics, gbc_lblOverviewStatistics);
		
		JLabel lblYourScore = new JLabel("Your Score: ");
		lblYourScore.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblYourScore = new GridBagConstraints();
		gbc_lblYourScore.anchor = GridBagConstraints.EAST;
		gbc_lblYourScore.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourScore.gridx = 0;
		gbc_lblYourScore.gridy = 2;
		_overview.add(lblYourScore, gbc_lblYourScore);
		
		JLabel label_4 = new JLabel(" " + _data.getScore());
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.insets = new Insets(0, 0, 5, 0);
		gbc_label_4.gridx = 2;
		gbc_label_4.gridy = 2;
		_overview.add(label_4, gbc_label_4);
		
		JLabel lblYourCurrentLevel = new JLabel("Your Current Level: ");
		lblYourCurrentLevel.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblYourCurrentLevel = new GridBagConstraints();
		gbc_lblYourCurrentLevel.anchor = GridBagConstraints.EAST;
		gbc_lblYourCurrentLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourCurrentLevel.gridx = 0;
		gbc_lblYourCurrentLevel.gridy = 3;
		_overview.add(lblYourCurrentLevel, gbc_lblYourCurrentLevel);
		
		JLabel lblNewLabel_1 = new JLabel(" " + _data.getCurrentLevel().getName());
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 3;
		_overview.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblProgressToNext = new JLabel("Progress To Next Level");
		lblProgressToNext.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblProgressToNext = new GridBagConstraints();
		gbc_lblProgressToNext.anchor = GridBagConstraints.SOUTH;
		gbc_lblProgressToNext.insets = new Insets(0, 0, 5, 5);
		gbc_lblProgressToNext.gridx = 0;
		gbc_lblProgressToNext.gridy = 5;
		_overview.add(lblProgressToNext, gbc_lblProgressToNext);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 0, 5);
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.gridheight = 8;
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 5;
		_overview.add(separator, gbc_separator);
		
		JLabel lblNewLabel = new JLabel("Best Word:");
		lblNewLabel.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 5;
		_overview.add(lblNewLabel, gbc_lblNewLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setToolTipText("Score Required To Unlock Next Level: " + (_data.scoreToNextLevel() - _data.getScore()));
		progressBar.setBackground(new Color(238, 238, 238));
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setMinimum(_data.scoreToCurrentLevel());
		progressBar.setMaximum(_data.scoreToNextLevel());
		progressBar.setValue(_data.getScore());
		progressBar.setPreferredSize(new Dimension(300,50));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 6;
		_overview.add(progressBar, gbc_progressBar);
		
		JLabel label = new JLabel(" " + _data.getBestWord());
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 2;
		gbc_label.gridy = 6;
		_overview.add(label, gbc_label);
		
		JLabel lblWorstWord = new JLabel("Worst Word:");
		lblWorstWord.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblWorstWord = new GridBagConstraints();
		gbc_lblWorstWord.insets = new Insets(0, 0, 5, 0);
		gbc_lblWorstWord.gridx = 2;
		gbc_lblWorstWord.gridy = 7;
		_overview.add(lblWorstWord, gbc_lblWorstWord);
		
		JLabel lblNumberOfRewards = new JLabel("Number of Rewards Unlocked:");
		lblNumberOfRewards.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblNumberOfRewards = new GridBagConstraints();
		gbc_lblNumberOfRewards.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfRewards.gridx = 0;
		gbc_lblNumberOfRewards.gridy = 8;
		_overview.add(lblNumberOfRewards, gbc_lblNumberOfRewards);
		
		JLabel label_1 = new JLabel(" " + _data.getWorstWord());
		label_1.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 8;
		_overview.add(label_1, gbc_label_1);
		
		JLabel lblRewards = new JLabel("# Rewards");
		lblRewards.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc_lblRewards = new GridBagConstraints();
		gbc_lblRewards.insets = new Insets(0, 0, 5, 5);
		gbc_lblRewards.gridx = 0;
		gbc_lblRewards.gridy = 9;
		_overview.add(lblRewards, gbc_lblRewards);
		
		JLabel lblMostAttempts = new JLabel("Most Attempts:");
		lblMostAttempts.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblMostAttempts = new GridBagConstraints();
		gbc_lblMostAttempts.insets = new Insets(0, 0, 5, 0);
		gbc_lblMostAttempts.gridx = 2;
		gbc_lblMostAttempts.gridy = 9;
		_overview.add(lblMostAttempts, gbc_lblMostAttempts);
		
		Word word = _data.getMostAttempts();
		JLabel label_2 = new JLabel(word.toString() + " - " + word.getAttempts() + " attempts");
		label_2.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 10;
		_overview.add(label_2, gbc_label_2);
		
		JLabel lblProgressToNext_1 = new JLabel("Progress To Next Reward:");
		lblProgressToNext_1.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblProgressToNext_1 = new GridBagConstraints();
		gbc_lblProgressToNext_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblProgressToNext_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblProgressToNext_1.gridx = 0;
		gbc_lblProgressToNext_1.gridy = 11;
		_overview.add(lblProgressToNext_1, gbc_lblProgressToNext_1);
		
		JLabel lblMostTimesCorrect = new JLabel("Most Times Correct:");
		lblMostTimesCorrect.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblMostTimesCorrect = new GridBagConstraints();
		gbc_lblMostTimesCorrect.insets = new Insets(0, 0, 5, 0);
		gbc_lblMostTimesCorrect.gridx = 2;
		gbc_lblMostTimesCorrect.gridy = 11;
		_overview.add(lblMostTimesCorrect, gbc_lblMostTimesCorrect);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setPreferredSize(new Dimension(300,50));
		progressBar_1.setBackground(new Color(238, 238, 238));
		progressBar_1.setForeground(new Color(50, 205, 50));
		GridBagConstraints gbc_progressBar_1 = new GridBagConstraints();
		gbc_progressBar_1.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar_1.gridx = 0;
		gbc_progressBar_1.gridy = 12;
		
		int score = _data.getScoreToPassAll();
		if (_data.getScore() < score*0.1) {
			
			lblRewards.setText("0 Rewards");
			progressBar_1.setMaximum((int) (score*0.1));
			progressBar_1.setMinimum(0);
			progressBar_1.setValue(_data.getScore());
			progressBar_1.setToolTipText("Increase Your Score By " + (score*0.1 - _data.getScore()) + " To View The Next Reward");
			
		} else if (_data.getScore() < score*0.25) {
			
			lblRewards.setText("1 Reward");
			progressBar_1.setMaximum((int) (score*0.25));
			progressBar_1.setMinimum((int) (score*0.1));
			progressBar_1.setValue(_data.getScore());
			progressBar_1.setToolTipText("Increase Your Score By " + (score*0.25 - _data.getScore()) + " To View The Next Reward");
			
		} else if (_data.getScore() < score*0.45) {
			
			lblRewards.setText("2 Rewards");
			progressBar_1.setMaximum((int) (score*0.45));
			progressBar_1.setMinimum((int) (score*0.25));
			progressBar_1.setValue(_data.getScore());
			progressBar_1.setToolTipText("Increase Your Score By " + (score*0.45 - _data.getScore()) + " To View The Next Reward");
			
		} else if (_data.getScore() < score*0.7) {
			
			lblRewards.setText("3 Rewards");
			progressBar_1.setMaximum((int) (score*0.7));
			progressBar_1.setMinimum((int) (score*0.45));
			progressBar_1.setValue(_data.getScore());
			progressBar_1.setToolTipText("Increase Your Score By " + (score*0.7 - _data.getScore()) + " To View The Next Reward");
			
		} else if (_data.getScore() < score) {
			
			lblRewards.setText("4 Rewards");
			progressBar_1.setMaximum((int) (score));
			progressBar_1.setMinimum((int) (score*0.7));
			progressBar_1.setValue(_data.getScore());
			progressBar_1.setToolTipText("Increase Your Score By " + (score - _data.getScore()) + " To View The Next Reward");
			
		} else {
			
			lblRewards.setText("All 5 Rewards");
			progressBar_1.setMaximum((int) (score));
			progressBar_1.setMinimum((int) (score*0.7));
			progressBar_1.setValue(_data.getScore());
			progressBar_1.setToolTipText("You Have All The Rewards!");
			
		}
		
		_overview.add(progressBar_1, gbc_progressBar_1);
		
		word = _data.getMostCorrect();
		JLabel label_3 = new JLabel(word.toString() + " - " + word.getCorrect() + " times");
		label_3.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 12;
		_overview.add(label_3, gbc_label_3);
		
	}
	
	/**
	 * Method that populates the JTable of a given level
	 * @param levelName
	 */
	private void fillLevelSpecificArray(String levelName) {
		WordList list = _data.getList(levelName);
		_specificLevel = new Object[list.size()][3];
				
		for (int i = 0; i < list.size(); i++) {
			Word var = list.getAtIndex(i);
			_specificLevel[i][0] = var.toString();
			_specificLevel[i][1] = var.getAttempts();
			_specificLevel[i][2] = var.getCorrect();
		}
	}
	
}
