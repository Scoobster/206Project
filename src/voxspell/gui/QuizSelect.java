package voxspell.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.SwingConstants;

import voxspell.data.WordList;

import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JComboBox;

/**
 * 
 * Class that is the Quiz Select screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class QuizSelect extends GUIElement {
	
	private JButton _continue;
	private JComboBox<WordList> _comboBox;
	private JButton _play;
	private JButton _return;

	/**
	 * Create the panel.
	 */
	public QuizSelect() {
		super();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel txtQuizSelect = new JLabel();
		txtQuizSelect.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuizSelect.setFont(new Font("Dialog", Font.BOLD, 40));
		txtQuizSelect.setText("QUIZ SELECT");
		GridBagConstraints gbc_txtQuizSelect = new GridBagConstraints();
		gbc_txtQuizSelect.insets = new Insets(0, 0, 5, 5);
		gbc_txtQuizSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtQuizSelect.gridx = 0;
		gbc_txtQuizSelect.gridy = 1;
		add(txtQuizSelect, gbc_txtQuizSelect);
		
		_continue = getButton("Continue");
		_continue.setToolTipText("Press Here To Launch A Quiz From Your Current Level");
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.insets = new Insets(0, 0, 5, 0);
		gbc_btnContinue.gridx = 0;
		gbc_btnContinue.gridy = 3;
		add(_continue, gbc_btnContinue);
		
		JLabel lblYourCurrentLevel = new JLabel("Your Current Level Is: " + _data.getCurrentLevel().getName());
		lblYourCurrentLevel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
		GridBagConstraints gbc_lblYourCurrentLevel = new GridBagConstraints();
		gbc_lblYourCurrentLevel.anchor = GridBagConstraints.SOUTH;
		gbc_lblYourCurrentLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourCurrentLevel.gridx = 0;
		gbc_lblYourCurrentLevel.gridy = 2;
		add(lblYourCurrentLevel, gbc_lblYourCurrentLevel);
		
		JLabel lblPleaseSelectA = new JLabel("Please Select A Word List:");
		lblPleaseSelectA.setFont(new Font("Dialog", Font.ITALIC, 20));
		GridBagConstraints gbc_lblPleaseSelectA = new GridBagConstraints();
		gbc_lblPleaseSelectA.anchor = GridBagConstraints.SOUTH;
		gbc_lblPleaseSelectA.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseSelectA.gridx = 0;
		gbc_lblPleaseSelectA.gridy = 5;
		add(lblPleaseSelectA, gbc_lblPleaseSelectA);
		
		_comboBox = new JComboBox<WordList>(_data.getAvailableLists());
		_comboBox.setToolTipText("Select A Word List or \"Level\" Here");
		_comboBox.setFont(new Font("Dialog", Font.BOLD, 20));
		_comboBox.setPreferredSize(new Dimension(400,50));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 6;
		add(_comboBox, gbc_comboBox);
		
		_play = getButton("PLAY!");
		_play.setToolTipText("Press Here To Launch A Quiz From The Selected Level");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.insets = new Insets(0, 0, 5, 0);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 8;
		add(_play, gbc_btnPlay);
		
		_return = getButton("Return");
		_return.setToolTipText("Return To The Main Menu");
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.gridx = 0;
		gbc_btnReturn.gridy = 10;
		add(_return, gbc_btnReturn);
		
		_continue.addActionListener(this);
		_play.addActionListener(this);
		_return.addActionListener(this);

	}

	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_continue)) {
			changeScreen(new QuizScreen(_data.getCurrentLevel()));
		} else if (src.equals(_play)) {
			changeScreen(new QuizScreen( (WordList) _comboBox.getSelectedItem()));
		} else if (src.equals(_return)) {
			changeScreen(new MainMenu());
		}
		
	}

}
