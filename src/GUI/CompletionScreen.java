package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.Data.DataStore;

@SuppressWarnings("serial")
public class CompletionScreen extends GUIElement {
	
	private int _numCorrect;
	
	private JLabel _passText = new JLabel();
	private JLabel _correctLabel = new JLabel();
	private JButton _playAgain = new JButton("Go To Level Select");
	private JButton _video = new JButton("Watch Video Reward");
	private JButton _tryAgain = new JButton("Try Level Again");
	private JButton _view = new JButton("View Statistics");
	private JButton _return = new JButton("Return to Main Menu");
	private JButton _review = new JButton("Review More Mistakes");
	
	private boolean _isLastlevel = false;

	public CompletionScreen(JFrame frame, DataStore data, int correctWords, boolean fromReview) {
		
		super(frame, data);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 0.5;
		c.gridx = 0;
		
		_numCorrect = correctWords;
		_data.getList(_data.getCurrentLevelName()).addToScore(correctWords);
		
		if (fromReview) {
			
			_passText.setText("You Reviewed Mistakes");
			
			c.gridy = 3;
			_review.setPreferredSize(new Dimension(200,50));
			_review.addActionListener(this);
			add(_review, c);
			
		} else if (_numCorrect < 9) {
			
			_passText.setText("Try Again");

			c.gridy = 3;
			_tryAgain.setPreferredSize(new Dimension(200,50));
			_tryAgain.addActionListener(this);
			add(_tryAgain, c);
		
		} else {
			
			_passText.setText("You Passed!");
			
			try {
				_data.setCurrentLevel(_data.getNextLevelName());
			} catch (ArrayIndexOutOfBoundsException e) {
				_isLastlevel = true;
				JOptionPane.showMessageDialog(this, "CONGRATULATIONS, You have completed all levels!");
			}
			
			c.gridy = 3;
			_video.setPreferredSize(new Dimension(200,50));
			_video.addActionListener(this);
			add(_video, c);
			
		}
		
		c.gridy = 0;
		add(_passText, c);
		
		c.gridy = 1;
		_correctLabel.setText("You got " + _numCorrect + " words correct.");
		add(_correctLabel, c);
		
		c.gridy = 2;
		_playAgain.setPreferredSize(new Dimension(200,50));
		_playAgain.addActionListener(this);
		add(_playAgain, c);
		
		c.gridy = 4;
		_view.setPreferredSize(new Dimension(200,50));
		_view.addActionListener(this);
		add(_view, c);
		
		c.gridy = 5;
		_return.setPreferredSize(new Dimension(200,50));
		_return.addActionListener(this);
		add(_return, c);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if (source.equals(_view)) {
			_GUI.getContentPane().add(new ViewStats(_GUI,_data, this));
			_GUI.revalidate();
			_GUI.repaint();
			return;
		} else if (source.equals(_video)) {
			new Video(_GUI, _data, _isLastlevel);
			_GUI.revalidate();
			_GUI.repaint();
			return;
		}
		
		_GUI.getContentPane().removeAll();
		
		if (source.equals(_playAgain)) {
			_GUI.getContentPane().add(new LevelSelect(_GUI, _data));
		} else if (source.equals(_tryAgain)) {
			_GUI.getContentPane().add(new SpellingTest(_GUI, _data, _data.getList(_data.getCurrentLevelName()).getRandomTen(), false));
		} else if (source.equals(_return)) {
			_GUI.getContentPane().add(new MainMenu(_GUI, _data));
		} else if (source.equals(_review)) {
			_GUI.getContentPane().add(new SpellingTest(_GUI, _data, _data.getMistakes().getRandomTen(), true));
		}
		
		_GUI.revalidate();
		_GUI.repaint();
	}
}
