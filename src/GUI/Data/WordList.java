package GUI.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordList {

	private String _name;
	private double _recentScore;
	private ArrayList<Double> _scores = new ArrayList<Double>();
	private ArrayList<Word> _list = new ArrayList<Word>();

	public WordList(String name) {
		_name = name;
	}
	
	public void add(Word word) {
		if (!_list.contains(word)) {
			_list.add(word);
		}
	}
	
	public void remove(Word word) {
		_list.remove(word);
	}
	
	public String getName() {
		return _name;
	}
	
	public int getListLength() {
		return _list.size();
	}
	
	public List<Word> getRandomTen() {
		Collections.shuffle(_list);
		if (_list.size() < 10) {
			return _list;
		} else {
			return _list.subList(0, 10);
		}
	}
	
	public ArrayList<Word> returnCopyOfList() {
		ArrayList<Word> list = new ArrayList<Word>();
		list.addAll(_list);
		return list;
	}
	
	public boolean containsWord(String word) {
		for (Word var : _list) {
			if (var.getWord().equals(word)) {
				return true;
			}
		}
		return false;
	}
	
	public void addToScore(int score) {
		_scores.add(new Double(_recentScore));
		_recentScore = (double)score;
	}

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
	
	public int getSize() {
		return _list.size();
	}
}




