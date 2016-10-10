package GUI.Data;

/**
 * this class represents a word. It contains the word, as a string, 
 * and the amount of times the word has been mastered, faulted and failed, as ints.
 * 
 * @author mvan439
 *
 */
public class Word {

	private String _word;
	private int _mastered;
	private int _faulted;
	private int _failed;
	
	/**
	 * Constructor for Word
	 * @param word
	 */
	public Word(String word) {
		_word = word.trim();
	}
	
	/**
	 * Constructor for word with stats
	 * @param word
	 * @param mastered
	 * @param faulted
	 * @param failed
	 */
	public Word(String word, int mastered, int faulted, int failed) {
		_word = word.trim();
		_mastered = mastered;
		_faulted = faulted;
		_failed = failed;
	}
	
	/**
	 * This method increases a stat by one depending on its input
	 * @param value
	 */
	public void increment(int value){
		if(value == 0){
			_mastered += 1;
		} else if(value == 1){
			_faulted += 1;
		} else if(value == 2){
			_failed += 1;
		}
	}
	
	/**
	 * Resets the statistics for the word
	 */
	public void reset(){
		_mastered = 0;
		_faulted = 0;
		_failed = 0;
	}

	/**
	 * Method used by TableValues, to get values for the stats table
	 * @param columnIndex
	 * @return
	 */
	public Object getValueAt(int columnIndex) {
		switch(columnIndex){
		case 0:
			return _word;
		case 1:
			return _mastered;
		case 2:
			return _faulted;
		case 3:
			return _failed;
		}
		return 0;
	}
	
	/**
	 * Method returns a string of the word and its stats for saving to file
	 * @return
	 */
	public String getStats(){
		return _word + " " + _mastered + " " + _faulted + " " + _failed;
	}
	
	/**
	 * Overriding the Object#toString() method for returning a string of the word
	 */
	@Override
	public String toString(){
		return _word;
	}
	
	/**
	 * Overriding the Object#compareTo(Object obj) method for comparing word to another
	 * @param word
	 * @return
	 */
	public int compareTo(Word word) {
		return _word.compareTo(word.toString());
	}

	/**
	 * Overriding equals ensuring the equals only compares the actual word
	 */
	@Override
	public boolean equals(Object object){
		return _word.equals(object.toString());
	}
	
	/**
	 * Method for setting statistics to word
	 * @param mastered
	 * @param faulted
	 * @param failed
	 */
	public void setStats(int mastered, int faulted, int failed) {
		_mastered = mastered;
		_faulted = faulted;
		_failed = failed;
	}
	
	/**
	 * Method for adding statistics to word
	 * @param mastered
	 * @param faulted
	 * @param failed
	 */
	public void addStats(int mastered, int faulted, int failed) {
		_mastered += mastered;
		_faulted += faulted;
		_failed += failed;
	}
}