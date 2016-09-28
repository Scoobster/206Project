package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import GUI.Data.DataStore;
import GUI.Data.Word;

public class SpellingTest extends GUIElement {

	private List<Word> _wordlist;
	private boolean _isReview;

	private JLabel _label = new JLabel("New Spelling Quiz");
	private JLabel _levelLabel = new JLabel(_data.getCurrentLevelName());
	private JLabel _wordLabel = new JLabel();
	private JTextField _input = new JTextField("Press enter to submit word");
	private JButton _speak = new JButton();
	private JLabel _labelOfStuff = new JLabel("");
	private JLabel _voiceLabel = new JLabel("Select a voice:");
	private JComboBox<String> _voiceSelect = new JComboBox<String>(new String[] {"American", "British", "New Zealander"});
	private JButton _view = new JButton("View Statistics");

	private int _wordNum = 0;
	private int _attemptCount = 0;
	private int _correctCount;
	private int _maxWords;

	private ArrayList<Word> _toRemove = new ArrayList<Word>();

	public SpellingTest(JFrame frame, DataStore data, List<Word> list, boolean isReview) {

		super(frame, data);
		_wordlist = list;
		_isReview = isReview;
		_correctCount = 0;
		_maxWords = _wordlist.size();
		if (_maxWords == 0) {
			JOptionPane.showMessageDialog(this, "You have NO mistakes! Returning to Main Menu...");
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new MainMenu(_GUI, _data));
			_GUI.revalidate();
			_GUI.repaint();
			return;
		}

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		if (isReview) {
			_label.setText("Review Mistakes Quiz");
		}
		add(_label, c);

		c.gridy = 1;
		add(_levelLabel, c);

		c.gridy = 2;
		_wordLabel.setText("Word 1 of " + _maxWords);
		add(_wordLabel, c);

		_input.setPreferredSize(new Dimension(300,50));
		_input.addActionListener(this);
		_input.addFocusListener(new FocusListener() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (_input.getText().equals("Press enter to submit word")) {
					_input.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
				if (_input.getText().equals("")) {
					_input.setText("Press enter to submit word");
				}
			}
		});

		c.gridy = 3;
		c.gridwidth = 2;
		add(_input, c);

		ImageIcon ico = new ImageIcon("res/SpeakerIcon.png");
		_speak.setIcon(ico);
		_speak.addActionListener(this);
		_speak.setPreferredSize(new Dimension(50,50));

		c.gridwidth = 1;
		c.gridx = 2;
		add(_speak, c);

		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = 1;
		add(_voiceLabel, c);

		c.gridwidth = 2;
		c.gridx = 1;
		_voiceSelect.setPreferredSize(new Dimension(150,25));
		add(_voiceSelect, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 3;
		add(_labelOfStuff, c);

		c.gridy = 6;
		_view.addActionListener(this);
		_view.setPreferredSize(new Dimension(150,50));
		add(_view,c);

		speakWord(_wordlist.get(_wordNum).getWord());

	}

	private void speakWord(final String word) {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			protected Void doInBackground() throws Exception {

				ProcessBuilder builder = new ProcessBuilder("usr/bin/festival", _data.getVoice(), "(SayText \"" + word + "\")");

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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(_speak)) {
			speakWord(_wordlist.get(_wordNum).getWord());
		} else if (e.getSource().equals(_view)) {
			_GUI.getContentPane().add(new ViewStats(_GUI, _data, this));
			_GUI.revalidate();
			_GUI.repaint();
		} else if (e.getSource().equals(_input)) {

			String input = _input.getText().trim();
			input = input.toLowerCase();
			if (!isAlpha(input)) {
				JOptionPane.showMessageDialog(this, "Please only enter characters between a-z.");
				return;
			}
			String word = _wordlist.get(_wordNum).getWord().toLowerCase();
			_labelOfStuff.setText("");

			if (_attemptCount == 0) {

				if (input.equals(word)) {

					_wordlist.get(_wordNum).increment(0);
					_labelOfStuff.setText("CORRECT!");
					_correctCount++;
					nextWord();

				} else {

					_attemptCount++;
					_labelOfStuff.setText("Incorrect, try again..");

				}

			} else {

				_attemptCount++;
				if (input.equals(word)) {

					_wordlist.get(_wordNum).increment(1);
					_labelOfStuff.setText("CORRECT!");
					_correctCount++;
					nextWord();
					
				} else {

					_wordlist.get(_wordNum).increment(2);
					_labelOfStuff.setText("Incorrect :(");
					nextWord();

				}

			}

			if (_wordNum == _maxWords) {

				for( Word wordToRemove:_toRemove){
					_data.getMistakes().remove(wordToRemove);
				}

				_GUI.getContentPane().removeAll();
				_GUI.getContentPane().add(new CompletionScreen(_GUI, _data, _correctCount, _isReview));

				_GUI.revalidate();
				_GUI.repaint();

				return;

			}

			speakWord(_wordlist.get(_wordNum).getWord());

		}

	}
	
	private void nextWord() {
		
		if (!_isReview) {
			_data.getMistakes().add(_wordlist.get(_wordNum));
		}

		_wordNum++;
		_attemptCount = 0;

		_input.setText("");
		int num = _wordNum + 1;
		_wordLabel.setText("Word " + num + " of " + _maxWords);
		
	}
	
	private boolean isAlpha(String word) {
		char[] chars = word.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

}
