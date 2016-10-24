package voxspell.gui;

import java.util.Arrays;
import java.util.List;

import voxspell.data.QuizGame;
import voxspell.data.WordList;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * Class that is the Quiz Game screen
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public class QuizScreen extends GUIElement implements KeyListener {
	
	private QuizGame _quiz;
	
	private JLabel _wordNum;
	private JTextField _input;
	private JButton _speak;
	private JButton _submit;
	private JButton _giveUp;
	private JButton _next;
	private JTextArea _printAttempts;
	
	private JPanel _navPane;
	private List<JButton> _buttonArray;
	private JLabel[] _labelArray = new JLabel[] {
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-"),
			new JLabel("-")
	};
	private JButton _finish = new JButton("Finish Quiz");
	private JLabel _scoreLabel;
	private JLabel lblQuestions;
	private JLabel _feedback;
	
	/**
	 * Create the panel.
	 */
	public QuizScreen(WordList list) {
		super();
		_quiz = new QuizGame(list);
		_festival.setScreen(this);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 150, 50, 25, 100, 100};
		gridBagLayout.rowHeights = new int[]{75, 10, 35, 15, 50, 50, 50, 50, 15, 150};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		setLayout(gridBagLayout);
		
		JLabel lblQuiz = new JLabel("QUIZ");
		lblQuiz.setFont(new Font("Dialog", Font.BOLD, 40));
		GridBagConstraints gbc_lblQuiz = new GridBagConstraints();
		gbc_lblQuiz.gridwidth = 6;
		gbc_lblQuiz.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuiz.gridx = 0;
		gbc_lblQuiz.gridy = 0;
		add(lblQuiz, gbc_lblQuiz);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 6;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);
		
		_wordNum = new JLabel("Word " + _quiz.getWordNumber() + " of " + _quiz.listSize());
		_wordNum.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblWordXOf = new GridBagConstraints();
		gbc_lblWordXOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblWordXOf.gridwidth = 3;
		gbc_lblWordXOf.gridx = 0;
		gbc_lblWordXOf.gridy = 2;
		add(_wordNum, gbc_lblWordXOf);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBackground(Color.WHITE);
		separator_1.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.VERTICAL;
		gbc_separator_1.gridheight = 8;
		gbc_separator_1.insets = new Insets(0, 0, 0, 5);
		gbc_separator_1.gridx = 3;
		gbc_separator_1.gridy = 2;
		add(separator_1, gbc_separator_1);
		
		lblQuestions = new JLabel("Words:");
		lblQuestions.setFont(new Font("Dialog", Font.BOLD, 20));
		GridBagConstraints gbc_lblQuestions = new GridBagConstraints();
		gbc_lblQuestions.gridwidth = 2;
		gbc_lblQuestions.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuestions.gridx = 4;
		gbc_lblQuestions.gridy = 2;
		add(lblQuestions, gbc_lblQuestions);
		
		_navPane = new JPanel();
		_navPane.setBackground(new Color(204, 255, 204));
		GridBagConstraints gbc__navPane = new GridBagConstraints();
		gbc__navPane.gridheight = 7;
		gbc__navPane.gridwidth = 2;
		gbc__navPane.fill = GridBagConstraints.BOTH;
		gbc__navPane.gridx = 4;
		gbc__navPane.gridy = 3;
		add(_navPane, gbc__navPane);
		
		JLabel lblSpellTheWord = new JLabel("Spell The Word:");
		lblSpellTheWord.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSpellTheWord.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblSpellTheWord = new GridBagConstraints();
		gbc_lblSpellTheWord.anchor = GridBagConstraints.WEST;
		gbc_lblSpellTheWord.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpellTheWord.gridx = 0;
		gbc_lblSpellTheWord.gridy = 3;
		add(lblSpellTheWord, gbc_lblSpellTheWord);
		
		_scoreLabel = new JLabel("Current Score: " + _quiz.getScore());
		_scoreLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc__scoreLabel = new GridBagConstraints();
		gbc__scoreLabel.anchor = GridBagConstraints.EAST;
		gbc__scoreLabel.gridwidth = 2;
		gbc__scoreLabel.insets = new Insets(0, 0, 5, 5);
		gbc__scoreLabel.gridx = 1;
		gbc__scoreLabel.gridy = 3;
		add(_scoreLabel, gbc__scoreLabel);
		
		_input = new JTextField();
		_input.setToolTipText("Press 'Enter' To Submit Your Attempt");
		_input.setFont(new Font("Dialog", Font.BOLD, 25));
		_input.setPreferredSize(new Dimension(350,50));
		GridBagConstraints gbc__input = new GridBagConstraints();
		gbc__input.insets = new Insets(0, 0, 5, 5);
		gbc__input.gridwidth = 2;
		gbc__input.fill = GridBagConstraints.HORIZONTAL;
		gbc__input.gridx = 0;
		gbc__input.gridy = 4;
		add(_input, gbc__input);
		_input.setColumns(10);
		
		_speak = new JButton("");
		ImageIcon ico = new ImageIcon("res/SpeakerIcon.png");
		_speak.setIcon(ico);
		_speak.setPreferredSize(new Dimension(50,50));
		_speak.setToolTipText("This will repeat the word");
		GridBagConstraints gbc__speak = new GridBagConstraints();
		gbc__speak.insets = new Insets(0, 0, 5, 5);
		gbc__speak.gridx = 2;
		gbc__speak.gridy = 4;
		add(_speak, gbc__speak);
		
		_giveUp = new JButton("Give Up");
		_giveUp.setToolTipText("Press To Here The Spelling Of The Word - Cannot Attempt Word After");
		_giveUp.setPreferredSize(new Dimension(200, 50));
		_giveUp.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc__giveUp = new GridBagConstraints();
		gbc__giveUp.insets = new Insets(0, 0, 5, 5);
		gbc__giveUp.gridx = 0;
		gbc__giveUp.gridy = 5;
		add(_giveUp, gbc__giveUp);
		_giveUp.addActionListener(this);
		
		_submit = new JButton("Submit");
		_submit.setToolTipText("Press To Submit Your Attempt");
		_submit.setPreferredSize(new Dimension(200,50));
		_submit.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc__submit = new GridBagConstraints();
		gbc__submit.gridwidth = 2;
		gbc__submit.insets = new Insets(0, 0, 5, 5);
		gbc__submit.gridx = 1;
		gbc__submit.gridy = 5;
		add(_submit, gbc__submit);
		_submit.addActionListener(this);
		
		_next = new JButton("Next Word");
		_next.setToolTipText("Press To Continue To The Next Word");
		_next.setPreferredSize(new Dimension(200,50));
		_next.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc__next = new GridBagConstraints();
		gbc__next.gridwidth = 3;
		gbc__next.insets = new Insets(0, 0, 5, 5);
		gbc__next.gridx = 0;
		gbc__next.gridy = 6;
		add(_next, gbc__next);
		_next.addActionListener(this);
		_next.addKeyListener(this);
		
		_feedback = new JLabel("Please Attempt Word");
		_feedback.setFont(new Font("Dialog", Font.BOLD, 30));
		GridBagConstraints gbc__feedback = new GridBagConstraints();
		gbc__feedback.gridwidth = 3;
		gbc__feedback.insets = new Insets(0, 0, 5, 5);
		gbc__feedback.gridx = 0;
		gbc__feedback.gridy = 7;
		add(_feedback, gbc__feedback);
		
		JLabel lblPreviousAttempts = new JLabel("Previous Attempts:");
		lblPreviousAttempts.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc_lblPreviousAttempts = new GridBagConstraints();
		gbc_lblPreviousAttempts.anchor = GridBagConstraints.WEST;
		gbc_lblPreviousAttempts.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreviousAttempts.gridx = 0;
		gbc_lblPreviousAttempts.gridy = 8;
		add(lblPreviousAttempts, gbc_lblPreviousAttempts);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 9;
		add(scrollPane, gbc_scrollPane);
		
		_printAttempts = new JTextArea();
		_printAttempts.setFont(new Font("Dialog", Font.ITALIC, 20));
		scrollPane.setViewportView(_printAttempts);
		
		_speak.addActionListener(this);

		setupNavPane();
		_buttonArray.get(0).setEnabled(false);
		
		_quiz.loadWord(0);
		_input.requestFocusInWindow();
		
		_input.addKeyListener(this);
		_finish.setToolTipText("Press To Submit Your Quiz");
		_finish.addKeyListener(this);
		
	}
	
	/**
	 * Method that sets up the navigation buttons by adding as many buttons as there are words in the quiz
	 */
	private void setupNavPane() {
		
		JButton[] bArray = new JButton[] {
				new JButton("One"),
				new JButton("Two"),
				new JButton("Three"),
				new JButton("Four"),
				new JButton("Five"),
				new JButton("Six"),
				new JButton("Seven"),
				new JButton("Eight"),
				new JButton("Nine"),
				new JButton("Ten")
		};
		
		_buttonArray = Arrays.asList(bArray);
		
		_navPane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.75;
		c.weighty = 0.75;
		c.gridwidth = 1;
		for (int i = 0; i < _quiz.listSize(); i++) {
			c.gridy = i;
			
			c.gridx = 0;
			_buttonArray.get(i).setFont(new Font("Dialog", Font.BOLD, 15));
			_buttonArray.get(i).setPreferredSize(new Dimension(100,50));
			_buttonArray.get(i).addActionListener(this);
			_navPane.add(_buttonArray.get(i), c);
			
			c.gridx = 1;
			_labelArray[i].setFont(new Font("Dialog", Font.BOLD, 15));
			_navPane.add(_labelArray[i], c);
		}
		
		c.gridwidth = 2;
		c.gridy = _quiz.listSize();
		c.gridx = 0;
		_finish.setFont(new Font("Dialog", Font.BOLD, 20));
		_finish.addActionListener(this);
		_finish.setPreferredSize(new Dimension(200,50));
		_navPane.add(_finish, c);
		
	}
	
	/**
	 * Method that checks the source of the action performed and acts accordingly
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_speak)) {
			_quiz.repeatWord();			
		} else if (src.equals(_input) || src.equals(_submit)) {
			attempt();
		} else if (src.equals(_next)) {
			changeWord(_quiz.getWordNumber());
		} else if (_buttonArray.contains(src)) {
			changeWord(_buttonArray.indexOf(src));
		} else if (src.equals(_giveUp)) {
			giveUp();
		} else if (src.equals(_finish)) {
			finishQuiz();
		}
			
		
	}
	
	/**
	 * Method for if a user gives up on a word
	 * Locks the input, makes festival spell the word
	 */
	private void giveUp() {
		_buttonArray.get(_quiz.getWordNumber()-1).setEnabled(false);
		_labelArray[_quiz.getWordNumber()-1].setText("Gave Up");
		_quiz.spellWord();
		_feedback.setText("The word was: " + _quiz.getWord());
		lockInput(false);
	}
	
	/**
	 * Method that gets called if the user makes an attempt at the word
	 * Will update all components on screen as required and calls to QuizGame
	 */
	private void attempt() {
		
		String attempt = _input.getText().trim();
		attempt.toLowerCase();
		
		boolean isCorrect = _quiz.update(attempt.toLowerCase());
		
		if (isCorrect) {
			
			_next.requestFocusInWindow();
			if (_quiz.getWordNumber() == _quiz.listSize()) {
				_finish.requestFocusInWindow();
			}
			
			_scoreLabel.setText("Current Score: " + _quiz.getScore());
			_feedback.setText("CORRECT!");
			
			lockInput(false);
			lockWord(_quiz.getWordNumber()-1);
			
		} else {
			
			_printAttempts.setText(_quiz.getPreviousAttempts());
			_feedback.setText("Incorrect");
			_input.requestFocusInWindow();
			
		}
	}
	
	/**
	 * Method that makes the quiz finish
	 * Adds the quiz score to the overall score and navigates the user to the completion screen
	 */
	private void finishQuiz() {
		int nextLevel = _data.scoreToNextLevel();
		int nextReward = _data.scoreToNextReward();
		int scoreBefore = _data.getScore();
		_data.addScore(_quiz.getScore());
		changeScreen(new CompletionScreen(_quiz));
		
		if (_data.getScore() >= _data.getScoreToPassAll() && scoreBefore < _data.getScoreToPassAll()) {
			JOptionPane.showMessageDialog(this, "CONGRADULATIONS! You Passsed All Levels");
		} else if (_data.getScore() > nextLevel && scoreBefore < nextLevel) {
			JOptionPane.showMessageDialog(this, "CONGRADULATIONS! You Advance To The Next Level");
		}
		
		if (_data.getScore() >= nextReward && scoreBefore < nextReward) {
			JOptionPane.showMessageDialog(this, "CONGRADULATIONS! You Unlocked A New Reward");
		}
	}
	
	/**
	 * Method that overrides the same method in GUIElement
	 * Makes the buttons relevant to festival enable or disable
	 */
	@Override
	public void enableButtons(boolean enable) {
		_submit.setEnabled(enable);
		_giveUp.setEnabled(enable);
		_speak.setEnabled(enable);
		_next.setEnabled(enable);
		if (_quiz.isLocked()) {
			_submit.setEnabled(false);
			_giveUp.setEnabled(false);
			_speak.setEnabled(false);
		}
	}
	
	/**
	 * Makes it so that all components related to input or changing word can be enabled or disabled
	 * @param enable
	 */
	private void lockInput(boolean enable) {
		_submit.setEnabled(enable);
		_giveUp.setEnabled(enable);
		_input.setEnabled(enable);
		_speak.setEnabled(enable);
	}
	
	/**
	 * Makes it so that all components related to the current word can be enabled or disabled
	 * @param enable
	 */
	private void lockWord(int number) {
		_buttonArray.get(number).setEnabled(false);
		_labelArray[number].setText("Correct");
	}
	
	/**
	 * Method that updates the GUI and Quiz Game when the user navigates to the next word
	 */
	private void changeWord(int number) {
		
		if (!_quiz.isLocked()) {
			_buttonArray.get(_quiz.getWordNumber()-1).setEnabled(true);
		}
		
		lockInput(true);
		_quiz.loadWord(number);
		
		_buttonArray.get(number).setEnabled(false);
		_feedback.setText("Please Attempt Word");
		_printAttempts.setText(_quiz.getPreviousAttempts());
		_input.setText("");
		_input.requestFocusInWindow();
		
		if (_quiz.isLocked()) {
			lockInput(false);
			_input.setText(_quiz.getWord());
			_feedback.setText("CORRECT!");
			_next.requestFocusInWindow();
			if (_quiz.getWordNumber() == _quiz.listSize()) {
				_finish.requestFocusInWindow();
			}
		}
		
		if (_quiz.getWordNumber() == _quiz.listSize()) {
			_next.setEnabled(false);
		}
		
		_wordNum.setText("Word " + _quiz.getWordNumber() + " of " + _quiz.listSize());
		
	}

	/**
	 * Required for interface (not used)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Method that calls the relevant method when 'Enter' is pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (_input.isEnabled()) {
				attempt();
			} else if (_next.isEnabled()) {
				changeWord(_quiz.getWordNumber());
			} else {
				finishQuiz();
			}
		}
	}

	/**
	 * Required for interface (not used)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
