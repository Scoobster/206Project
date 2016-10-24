package voxspell.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.JButton;

/**
 * 
 * Class that is the Rewards screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class Rewards extends GUIElement {
	private JButton _rewardOne;
	private JButton _rewardTwo;
	private JButton _rewardThree;
	private JButton _rewardFive;
	private JButton _rewardFour;
	private ArrayList<JButton> _rewards;
	
	private JProgressBar _progress;
	private JButton _return;
	
	private int[] _scoresRequired = new int[5];

	/**
	 * Create the panel.
	 */
	public Rewards() {
		super();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{50, 116, 116, 116, 116, 116, 116, 50};
		gridBagLayout.rowHeights = new int[]{60, 60, 60, 60, 60, 60, 60, 60, 60};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0};
		setLayout(gridBagLayout);
		
		_scoresRequired[4] = _data.getScoreToPassAll();
		_scoresRequired[3] = (int) (_scoresRequired[4]*0.7);
		_scoresRequired[2] = (int) (_scoresRequired[4]*0.45);
		_scoresRequired[1] = (int) (_scoresRequired[4]*0.25);
		_scoresRequired[0] = (int) (_scoresRequired[4]*0.1);
		
		JLabel lblRewards = new JLabel("REWARDS");
		lblRewards.setFont(new Font("Dialog", Font.BOLD, 40));
		GridBagConstraints gbc_lblRewards = new GridBagConstraints();
		gbc_lblRewards.gridwidth = 8;
		gbc_lblRewards.insets = new Insets(0, 0, 5, 0);
		gbc_lblRewards.gridx = 0;
		gbc_lblRewards.gridy = 0;
		add(lblRewards, gbc_lblRewards);
		
		JLabel lblNewLabel = new JLabel("Progress To Next Reward");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.gridwidth = 8;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		_progress = new JProgressBar();
		_progress.setPreferredSize(new Dimension(500,50));
		_progress.setBackground(new Color(238, 238, 238));
		_progress.setForeground(new Color(50, 205, 50));
		GridBagConstraints gbc__progress = new GridBagConstraints();
		gbc__progress.gridwidth = 8;
		gbc__progress.insets = new Insets(0, 0, 5, 0);
		gbc__progress.gridx = 0;
		gbc__progress.gridy = 3;
		
		boolean setProgress = false;
		
		_rewardOne = new JButton("Reward #1");
		_rewardOne.setFont(new Font("Dialog", Font.PLAIN, 20));
		_rewardOne.setPreferredSize(new Dimension(200,50));
		_rewardOne.setToolTipText("Press Here To View The Reward");
		
		if (_data.getScore() < _scoresRequired[0]) {
			_rewardOne.setEnabled(false);
			if (!setProgress) {
				_progress.setMaximum(_scoresRequired[0]);
				_progress.setMinimum(0);
				_progress.setValue(_data.getScore());
				_progress.setToolTipText("Increase Your Score By " + (_scoresRequired[0] - _data.getScore()) + " To View The Next Reward");
				setProgress = true;
			}
		}
		
		GridBagConstraints gbc__rewardOne = new GridBagConstraints();
		gbc__rewardOne.gridwidth = 2;
		gbc__rewardOne.insets = new Insets(0, 0, 5, 5);
		gbc__rewardOne.gridx = 1;
		gbc__rewardOne.gridy = 5;
		add(_rewardOne, gbc__rewardOne);
		
		_rewardTwo = new JButton("Reward #2");
		_rewardTwo.setFont(new Font("Dialog", Font.PLAIN, 20));
		_rewardTwo.setPreferredSize(new Dimension(200,50));
		_rewardTwo.setToolTipText("Press Here To View The Reward");
		
		if (_data.getScore() < _scoresRequired[1]) {
			_rewardTwo.setEnabled(false);
			if (!setProgress) {
				_progress.setMaximum(_scoresRequired[1]);
				_progress.setMinimum(_scoresRequired[0]);
				_progress.setValue(_data.getScore());
				_progress.setToolTipText("Increase Your Score By " + (_scoresRequired[1] - _data.getScore()) + " To View The Next Reward");
				setProgress = true;
			}
		}
		
		GridBagConstraints gbc__rewardTwo = new GridBagConstraints();
		gbc__rewardTwo.gridwidth = 2;
		gbc__rewardTwo.insets = new Insets(0, 0, 5, 5);
		gbc__rewardTwo.gridx = 3;
		gbc__rewardTwo.gridy = 5;
		add(_rewardTwo, gbc__rewardTwo);
		
		_rewardThree = new JButton("Reward #3");
		_rewardThree.setFont(new Font("Dialog", Font.PLAIN, 20));
		_rewardThree.setPreferredSize(new Dimension(200,50));
		_rewardThree.setToolTipText("Press Here To View The Reward");
		
		if (_data.getScore() < _scoresRequired[2]) {
			_rewardThree.setEnabled(false);
			if (!setProgress) {
				_progress.setMaximum(_scoresRequired[2]);
				_progress.setMinimum(_scoresRequired[1]);
				_progress.setValue(_data.getScore());
				_progress.setToolTipText("Increase Your Score By " + (_scoresRequired[2] - _data.getScore()) + " To View The Next Reward");
				setProgress = true;
			}
		}
		
		GridBagConstraints gbc__rewardThree = new GridBagConstraints();
		gbc__rewardThree.gridwidth = 2;
		gbc__rewardThree.insets = new Insets(0, 0, 5, 5);
		gbc__rewardThree.gridx = 5;
		gbc__rewardThree.gridy = 5;
		add(_rewardThree, gbc__rewardThree);
		
		_rewardFour = new JButton("Reward #4");
		_rewardFour.setFont(new Font("Dialog", Font.PLAIN, 20));
		_rewardFour.setPreferredSize(new Dimension(200,50));
		_rewardFour.setToolTipText("Press Here To View The Reward");
		
		if (_data.getScore() < _scoresRequired[3]) {
			_rewardFour.setEnabled(false);
			if (!setProgress) {
				_progress.setMaximum(_scoresRequired[3]);
				_progress.setMinimum(_scoresRequired[2]);
				_progress.setValue(_data.getScore());
				_progress.setToolTipText("Increase Your Score By " + (_scoresRequired[3] - _data.getScore()) + " To View The Next Reward");
				setProgress = true;
			}
		}
		
		GridBagConstraints gbc__rewardFour = new GridBagConstraints();
		gbc__rewardFour.gridwidth = 2;
		gbc__rewardFour.insets = new Insets(0, 0, 5, 5);
		gbc__rewardFour.gridx = 2;
		gbc__rewardFour.gridy = 6;
		add(_rewardFour, gbc__rewardFour);
		
		_rewardFive = new JButton("Reward #5");
		_rewardFive.setFont(new Font("Dialog", Font.PLAIN, 20));
		_rewardFive.setPreferredSize(new Dimension(200,50));
		_rewardFive.setToolTipText("Press Here To View The Reward");
		
		if (_data.getScore() < _scoresRequired[4]) {
			_rewardFive.setEnabled(false);
			if (!setProgress) {
				_progress.setMaximum(_scoresRequired[4]);
				_progress.setMinimum(_scoresRequired[3]);
				_progress.setValue(_data.getScore());
				_progress.setToolTipText("You Have All Of The Rewards!");
				setProgress = true;
			}
		}
		
		GridBagConstraints gbc__rewardFive = new GridBagConstraints();
		gbc__rewardFive.gridwidth = 2;
		gbc__rewardFive.insets = new Insets(0, 0, 5, 5);
		gbc__rewardFive.gridx = 4;
		gbc__rewardFive.gridy = 6;
		add(_rewardFive, gbc__rewardFive);
		
		_return = new JButton("Return");
		_return.setFont(new Font("Dialog", Font.PLAIN, 20));
		_return.setPreferredSize(new Dimension(200,50));
		_return.setToolTipText("Return To The Main Menu");
		GridBagConstraints gbc__return = new GridBagConstraints();
		gbc__return.gridwidth = 2;
		gbc__return.insets = new Insets(0, 0, 0, 5);
		gbc__return.gridx = 5;
		gbc__return.gridy = 8;
		add(_return, gbc__return);
		
		add(_progress, gbc__progress);
		
		_return.addActionListener(this);
		
		_rewards = new ArrayList<JButton>();
		_rewards.add(_rewardOne);
		_rewards.add(_rewardTwo);
		_rewards.add(_rewardThree);
		_rewards.add(_rewardFour);
		_rewards.add(_rewardFive);
		
		for (JButton btn : _rewards) {
			btn.addActionListener(this);
		}

	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_return)) {
			changeScreen(new MainMenu());
		} else if (_rewards.contains(src)) {
			new Video(_rewards.indexOf(src));
			_GUI.revalidate();
			_GUI.repaint();
		}
		
	}

}
