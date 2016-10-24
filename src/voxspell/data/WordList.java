package voxspell.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Class that represents a "Level" or list of words
 * 
 * @author scoobster
 *
 */
public class WordList {
	
	private List<Word> _wordlist = new ArrayList<Word>();
	private String _name;
	private int _score;
	
	/**
	 * Constructor
	 * @param name
	 */
	public WordList(String name) {
		_name = name;
	}
	
	/**
	 * Method that adds a word to the wordlist if it is already in the list it does nothing
	 * @param word
	 */
	public void add(Word word) {
		if (!_wordlist.contains(word)) {
			_wordlist.add(word);
		}
	}
	
	/**
	 * Returns the word at given index
	 * @param index
	 * @return
	 */
	public Word getAtIndex(int index) {
		return _wordlist.get(index);
	}
	
	/**
	 * Method that removes a word from the wordlist
	 * @param word
	 */
	public void remove(Word word) {
		_wordlist.add(word);
	}
	
	/**
	 * Method that checks if the word already exists in the wordlist
	 * @param word
	 * @return
	 */
	public boolean contains(String word) {
		for (Word w : _wordlist) {
			if (word.trim().equals(w.toString())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Methods that adds to the score of the list
	 * @param score
	 */
	public void addToScore(int score) {
		_score += score;
	}
	
	/**
	 * Method returns the name of the list
	 * @return
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Returns the score on the list
	 * @return
	 */
	public int getScore() {
		return _score;
	}
	
	/**
	 * Method that returns the size of the word list (number of words in the list)
	 * @return
	 */
	public int size() {
		return _wordlist.size();
	}
	
	/**
	 * Method that returns if the level has been passed or not
	 * @return
	 */
	public boolean hasPassed() {
		int scoreToPass = size() * (9/10) * 90;
		if (_score >= scoreToPass) {
			return true;
		} else {
			return false;
		}
	}
	
	public int scoreToPass() {
		return (int) (size() * (0.9) * 90);
	}
	
	/**
	 * Returns a string of all the words contained in the list and their stats for saving
	 * @return
	 */
	public String print() {
		if (size() == 0) {
			return "";
		}
		String newLine = System.getProperty("line.separator");
		String printString = "";
		for (Word word : _wordlist) {
			if (_wordlist.indexOf(word) == size() - 1) {
				printString += word.print();
			} else {
				printString += word.print() + newLine;
			}
		}
		return printString;
	}
	
	/**
	 * Method that returns a list of up to 10 random words
	 * @return
	 */
	public List<Word> getRandomWords() {
		Collections.shuffle(_wordlist);
		if (size() <= 10) {
			return _wordlist;
		} else {
			return _wordlist.subList(0, 10);
		}
	}
	
	/**
	 * Method that returns an instance a word
	 * @param word
	 * @return
	 */
	protected Word getWord(String word) {
		for (Word w : _wordlist) {
			if (word.trim().equals(w.toString())) {
				return w;
			}
		}
		return null;
	}
	
	/**
	 * Methods that overrides toString() that returns the name of the list
	 */
	@Override
	public String toString() {
		return _name;
	}
	
	
}
