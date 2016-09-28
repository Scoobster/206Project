package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import GUI.Data.DataStore;
import GUI.Data.Word;

@SuppressWarnings("serial")
public class QuizScreen extends GUIElement {
	
	private List<Word> _wordlist;
	
	private JSeparator _vertical = new JSeparator(SwingConstants.VERTICAL);
	private JSeparator _horizontal = new JSeparator(SwingConstants.HORIZONTAL);
	
	private JLabel _title = new JLabel("Quiz");
	private JTextField _input = new JTextField();
	private JButton _speech = new JButton();
	private JButton _submit = new JButton("Submit Word");
	private JButton _skip = new JButton("Skip Word");
	private JButton _giveUp = new JButton("Give Up");
	private JButton _defButton = new JButton("Click here\nfor definition!");
	private JLabel _feedback = new JLabel();
	private JTextArea _previousAttempts = new JTextArea("PREVIOUS ATTEMPTS:\n");
	
	private JPanel _navigation = new JPanel();
	private JButton[] _navButtons;
	private JLabel[] _navLabels;
	
	private JLabel _questionLabel = new JLabel("Questions:");
	private JButton _One = new JButton("ONE");
	private JButton _Two = new JButton("TWO");
	private JButton _Three = new JButton("THREE");
	private JButton _Four = new JButton("FOUR");
	private JButton _Five = new JButton("FIVE");
	private JButton _Six = new JButton("SIX");
	private JButton _Seven = new JButton("SEVEN");
	private JButton _Eight = new JButton("EIGHT");
	private JButton _Nine = new JButton("NINE");
	private JButton _Ten = new JButton("TEN");
	private JLabel _OneLabel = new JLabel();
	private JLabel _TwoLabel = new JLabel();
	private JLabel _ThreeLabel = new JLabel();
	private JLabel _FourLabel = new JLabel();
	private JLabel _FiveLabel = new JLabel();
	private JLabel _SixLabel = new JLabel();
	private JLabel _SevenLabel = new JLabel();
	private JLabel _EightLabel = new JLabel();
	private JLabel _NineLabel = new JLabel();
	private JLabel _TenLabel = new JLabel();
	private JButton _finish = new JButton("Finish Quiz");

	private int _currentWord = 1;
	private int _maxWords = 10;

	public QuizScreen(JFrame frame, DataStore data, List<Word> wordlist) {
		
		super(frame, data);
		_wordlist = wordlist;
		
		//_maxWords = _wordlist.size();
		
		checkList();
		
		_navButtons = new JButton[] {_One, _Two, _Three, _Four, _Five, _Six, _Seven, _Eight, _Nine, _Ten};
		_navLabels = new JLabel[] {_OneLabel, _TwoLabel, _ThreeLabel, _FourLabel, _FiveLabel, _SixLabel, _SevenLabel, _EightLabel, _NineLabel, _TenLabel};
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6; 
		c.gridheight = 1;
		add(_title, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6; 
		c.gridheight = 1;
		add(_horizontal, c);
		
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 12;
		add(_vertical, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2; 
		c.gridheight = 2;
		add(_input, c);
		
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1; 
		c.gridheight = 2;
		add(_speech, c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1; 
		c.gridheight = 2;
		add(_submit, c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1; 
		c.gridheight = 2;
		add(_skip, c);
		
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1; 
		c.gridheight = 2;
		add(_giveUp, c);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 3; 
		c.gridheight = 2;
		add(_defButton, c);
		
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 3; 
		c.gridheight = 1;
		add(_feedback, c);
		
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 3; 
		c.gridheight = 5;
		add(_previousAttempts, c);
		
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 2; 
		c.gridheight = 12;
		add(_navigation, c);
		
		setupNavigation();
		
	}
	
	private void checkList() {
		
		if (_maxWords == 0) {
			
			JOptionPane.showMessageDialog(this, "You have NO mistakes! Returning to Main Menu...");
			
			_GUI.getContentPane().removeAll();
			_GUI.getContentPane().add(new MainMenu(_GUI, _data));
			
			_GUI.revalidate();
			_GUI.repaint();
			
			return;
		}
	}
	
	private void setupNavigation() {
		
		_navigation.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
		_navigation.add(_questionLabel, c);
		
		c.gridwidth = 1;
		for (int i = 0; i < _maxWords; i++) {
			
			c.gridy = i + 1;
			
			c.gridx = 0;
			_navigation.add(_navButtons[i], c);
			
			c.gridx = 1;
			_navLabels[i].setText("Correct");
			_navigation.add(_navLabels[i], c);
			
		}
		
		c.gridx = 0;
		c.gridy = _maxWords + 1;
		c.gridheight = 1;
		c.gridwidth = 2;
		_navigation.add(_finish, c);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
