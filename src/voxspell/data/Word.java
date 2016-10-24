package voxspell.data;

/**
 * this class represents a word. It contains the word, as a string, 
 * and the amount of times the word has been attempted and gotten correct, as ints.
 * 
 * @author mvan439
 *
 */
public class Word {

	private String _word;
	private int _numCorrect;
	private int _numAttempt;
	
	/**
	 * Constructor for Word
	 * @param word
	 */
	public Word(String word) {
		_word = word.trim();
		_numCorrect = 0;
		_numAttempt = 0;
	}
	
	/**
	 * Constructor for word with stats
	 * @param word
	 * @param correct
	 * @param attempts
	 */
	public Word(String word, int attempts, int correct) {
		_word = word.trim();
		_numCorrect = correct;
		_numAttempt = attempts;
	}
	
	/**
	 * Method that adds an attempt made on the word and if it was correct it increases the number of correct
	 * @param isCorrect
	 */
	public void addAttempt(boolean isCorrect) {
		_numAttempt++;
		if (isCorrect) {
			_numCorrect++;
		}
	}
	
	/**
	 * Overrides the toString method that returns a string of the word
	 */
	@Override
	public String toString() {
		return _word;
	}
	
	/**
	 * Method returns a string of the word and the number of attempts and times correct for saving data
	 * @return
	 */
	public String print() {
		return _word + " " + _numAttempt + " " + _numCorrect;
	}
	
	/**
	 * Returns the number of attempts
	 * @return
	 */
	public int getAttempts() {
		return _numAttempt;
	}
	
	/**
	 * Returns the number of times word has been spelt correctly
	 * @return
	 */
	public int getCorrect() {
		return _numCorrect;
	}
	
	/**
	 * Overrides the equals method for compare if two words the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this.toString().toLowerCase().equals(obj.toString().trim().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method that resets all the stats with the word
	 */
	public void resetStats() {
		_numCorrect = 0;
		_numAttempt = 0;
	}
	
	/**
	 * Method sets the statistics of the word
	 * @param attempts
	 * @param correct
	 */
	public void setStats(int attempts, int correct) {
		_numAttempt = attempts;
		_numCorrect = correct;
	}
	
}
