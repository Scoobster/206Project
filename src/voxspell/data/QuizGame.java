package voxspell.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Class that contains the logic for running a quiz game
 * 
 * @author scoobster
 *
 */
public class QuizGame {
	
	private WordList _wordlist;
	private List<Word> _list;
	private Word _currentWord;
	private Festival _festival;
	
	private int _currentScore;
	
	private Map<Word,ArrayList<String>> _previousAttempts = new HashMap<Word,ArrayList<String>>();
	private Map<Word,Boolean> _isLocked = new HashMap<Word,Boolean>();
	private Map<Word,Integer> _numAttempts = new HashMap<Word,Integer>();
	private Map<Word,Integer> _scores = new HashMap<Word,Integer>();
	
	/**
	 * Constructor that sets up the relevant fields
	 * @param list
	 */
	public QuizGame(WordList list) {
		
		_wordlist = list;
		_list = _wordlist.getRandomWords();
		_currentWord = _list.get(0);
		_festival = Festival.getInstance();
		_currentScore = 0;
		
		for (Word word : _list) {
			_isLocked.put(word, false);
			_previousAttempts.put(word, new ArrayList<String>());
			_numAttempts.put(word, 0);
			_scores.put(word, 100);
		}
		
	}
	
	/**
	 * Changes the current word and calls to festival to say new word
	 * @param number
	 * @return
	 */
	public boolean loadWord(int number) {
		
		_currentWord = _list.get(number);
		
		if (_isLocked.get(_currentWord)) {
			_festival.sayWord("You have gotten this word correct already");
		} else {
			_festival.sayFirstWord(_currentWord.toString());
		}
		return _isLocked.get(_currentWord);
		
	}
	
	/**
	 * Method that takes an attempt from the user, adds that attempt, changes the score as necessary and makes Festival give feedback
	 * @param attempt
	 * @return a boolean stating whether the guess was correct or not
	 */
	public boolean update(String attempt) {
		
		_previousAttempts.get(_currentWord).add(attempt);
		int num = _numAttempts.get(_currentWord) + 1;
		_numAttempts.replace(_currentWord, num);
		
		if (_currentWord.equals(attempt.trim())) {
			
			_currentWord.addAttempt(true);
			_isLocked.replace(_currentWord, true);
			_festival.sayCorrectFeedback();
			_currentScore += _scores.get(_currentWord);
			return true;
			
		} else {
			
			_currentWord.addAttempt(false);
			decreaseScore();
			_festival.sayIncorrectFeedback();
			_festival.sayFirstWord(_currentWord.toString());
			return false;
			
		}
		
	}
	
	/**
	 * Method that decreases the score by the required amount based on attempts made
	 */
	private void decreaseScore() {
		
		int attempts = _numAttempts.get(_currentWord);
		int score = 100;
		if (attempts > 15 || attempts == 0) {
			
		} else if (attempts <= 5) {
			score -= (11 - attempts);
		} else {
			score -= 5;
		}
		
		_scores.replace(_currentWord, score);
		
	}
	
	/**
	 * Returns the current score of only the quiz
	 * @return
	 */
	public int getScore() {
		return _currentScore;
	}
	
	/**
	 * Returns a string with all the previous attempts on the current word separated by a new line
	 * @return
	 */
	public String getPreviousAttempts() {
		
		List<String> list = _previousAttempts.get(_currentWord);
		String attemptsString = "";
		for (String str : list) {
			attemptsString += str + "\n";
		}
		return attemptsString;
		
	}
	
	/**
	 * Calls to festival to say the current word
	 */
	public void repeatWord() {
		_festival.sayWord(_currentWord.toString());
	}
	
	/**
	 * Calls to festival that makes it say the spelling of the word
	 */
	public void spellWord() {
		_isLocked.replace(_currentWord, true);
		_festival.spellWord(getWord());
	}
	
	/**
	 * Returns the number of the current qord in the quiz
	 * @return
	 */
	public int getWordNumber() {
		return _list.indexOf(_currentWord) + 1;
	}
	
	/**
	 * Returns the size of the quiz
	 * @return
	 */
	public int listSize() {
		return _list.size();
	}
	
	/**
	 * Returns whether the word can be attempted or not
	 * @return
	 */
	public boolean isLocked() {
		return _isLocked.get(_currentWord);
	}
	
	/**
	 * Returns the current word as String
	 * @return
	 */
	public String getWord() {
		return _currentWord.toString();
	}
	
	/**
	 * Returns the WordList from which the quiz was made
	 * @return
	 */
	public WordList getWordList() {
		return _wordlist;
	}

}
