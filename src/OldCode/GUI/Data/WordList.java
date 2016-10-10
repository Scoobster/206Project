package OldCode.GUI.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordList {

	private String _name;
	private double _recentScore;
	private ArrayList<Double> _scores = new ArrayList<Double>();
	private ArrayList<Word> _list = new ArrayList<Word>();

	/**
	 * Constructor for WordList, String required for name
	 * @param name
	 */
	public WordList(String name) {
		_name = name;
	}
	
	/**
	 * Adds word to the list
	 * @param word
	 */
	public void add(Word word) {
		if (!_list.contains(word)) {
			_list.add(word);
		}
	}
	
	/**
	 * Adds word from a String from the save file
	 * @param line
	 */
	public void add(String line) {
		 String[] lineParts = getWordInfo(line);
		if (containsWord(lineParts[0])) {
			getWord(lineParts[0]).addStats(Integer.parseInt(lineParts[1]), Integer.parseInt(lineParts[2]), Integer.parseInt(lineParts[3]));
		} else {
			add(new Word(lineParts[0], Integer.parseInt(lineParts[1]), Integer.parseInt(lineParts[2]), Integer.parseInt(lineParts[3])));
		}
	}
	
	/**
	 * Splits a line from the save file and splits it into an appropriate array
	 * @param line
	 * @return
	 */
	private String[] getWordInfo(String line) {
		
		String[] wordInfo = line.split("\\s+");	
		if (wordInfo.length > 4) {
			String word = "";
			for (int i = 0; i < wordInfo.length - 3; i++) {
				word = word + " " + wordInfo[i];
			}
			String[] twoWords = {word, wordInfo[wordInfo.length - 3], wordInfo[wordInfo.length - 2], wordInfo[wordInfo.length - 1]};
			wordInfo = twoWords;
		}
		return wordInfo;
		
	}
	
	/**
	 * Removes word from list
	 * @param word
	 */
	public void remove(Word word) {
		_list.remove(word);
	}
	
	/**
	 * Returns the name of the wordlist
	 * @return
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Returns the size of the list
	 * @return
	 */
	public int getListLength() {
		return _list.size();
	}
	
	/**
	 * Shuffles the wordlist then returns the first 10 words in the list
	 * @return
	 */
	public List<Word> getRandomWords() {
		Collections.shuffle(_list);
		if (_list.size() < 10) {
			return _list;
		} else {
			return _list.subList(0, 10);
		}
	}
	
	/**
	 * Returns a copy of the WordList as an ArrayList<Word>
	 * @return
	 */
	public ArrayList<Word> returnCopyOfList() {
		ArrayList<Word> list = new ArrayList<Word>();
		list.addAll(_list);
		return list;
	}
	
	/**
	 * Returns a boolean if the word is in the wordlist
	 * @param word
	 * @return
	 */
	public boolean containsWord(String word) {
		for (Word var : _list) {
			if (var.toString().equals(word)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the instance of the word if its in the list or null if it isn't
	 * @param word
	 * @return
	 */
	public Word getWord(String word) {
		for (Word var : _list) {
			if (var.toString().equals(word)) {
				return var;
			}
		}
		return null;
	}
	
	/**
	 * Method to add to the score scored on the list in the quiz
	 * @param score
	 */
	public void addToScore(int score) {
		_scores.add(new Double(_recentScore));
		_recentScore = (double)score;
	}

	/**
	 * Returns the previous score
	 * @return
	 */
	public double getPreviousScore() {
		double total = 0;
		double amount = 0;
		
		if (_scores.isEmpty()) {
			return 0;
		}
		
		for (Double var : _scores) {
			amount++;
			total = total + var.doubleValue();
		}
		
		return total/amount;
	}
	
	/**
	 * Return the total score
	 * @return
	 */
	public double getOverallScore() {
		double total = _recentScore;
		double amount = 1;
		
		if (_scores.isEmpty()) {
			return 0;
		}
		
		for (Double var : _scores) {
			amount++;
			total = total + var.doubleValue();
		}
		return total/amount;
	}
	
	/**
	 * Returns the percentage increase in the score from the previous score
	 * @return
	 */
	public double getProgression() {
		double overall = getOverallScore();
		double previous = getPreviousScore();
		
		if (overall == 0 && previous == 0) {
			return 0;
		} else if (previous == 0) {
			return 1;
		}
		
		return (overall/previous) -1;
	}
	
	/**
	 * Returns the size of the wordlist
	 * @return
	 */
	public int size() {
		return _list.size();
	}
	
	/**
	 * Returns the wordlist as an array of Words (sorted)
	 * @return
	 */
	public Word[] getAsArray() {
		List<Word> sorted = new ArrayList<Word>();
		for (Word var : _list) {
			int j = 0;
			while (j < sorted.size()) {
				if (var.compareTo(sorted.get(j)) <= 0) {
					break;
				}
				j++;
			}
			sorted.add(var);
		}
		
		Word[] words = new Word[sorted.size()];
		int i = 0;
		for (Word word : sorted) {
			words[i] = word;
			i++;
		}
		return words;
	}
	
}




