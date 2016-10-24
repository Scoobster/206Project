package voxspell.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import voxspell.MainGUI;

/**
 * Class that holds all data 
 * @author alya691
 *
 */
public class Data {
	
	private static Data _instance;
	
	private String _currentUser = null;
	private List<String> _users;
	
	private List<WordList> _wordlists;
	
	private String _currentFile = "NZCER-spelling-lists.txt";
	
	private int _score = 0;
	private int _numLevels = 1;
	
	/**
	 * Constructor
	 */
	private Data() {
		generateUsers();
	}
	
	/**
	 * Returns the one instance of the data class
	 * @return
	 */
	public static Data getInstance() {
		if (_instance == null) {
			_instance = new Data();
		}
		return _instance;
	}
	
	/**
	 * This method writes a given String to a new line of a file
	 */
	private void addToFile(String word, String filePath) {
		
		String newLine = System.getProperty("line.separator");
		String line = word + newLine;

		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(filePath, true));
			file.write(line);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Method that resets all data stored in this instance
	 */
	private void overrideData() {
		_currentFile = "NZCER-spelling-lists.txt";
		_score = 0;
		_numLevels = 1;
		_wordlists = new ArrayList<WordList>();
	}
	
	/**
	 * Method that erases the data stored in the users data file
	 */
	private void overrideFile() {
		File file = new File("res/." + _currentUser + ".data");
		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that saves all relevant data to the user's save file
	 */
	public void saveData() {
		if (_currentUser != null) {
			
			saveUsers();
			overrideFile();
			
			String filePath = "res/." + _currentUser + ".data";
			addToFile("" + _score, filePath);
			addToFile("" + _numLevels, filePath);
			addToFile(_currentFile, filePath);
			addToFile(Festival.getInstance().getVoice(), filePath);
			
			for (WordList var : _wordlists) {
				addToFile("%" + var.getName(), filePath);
				addToFile(var.print(), filePath);
			}
			
		}
	}
	
	/**
	 * Method that reads the current users data file and loads all their previous data
	 */
	private void loadData() {
		
		File file = new File("res/." + _currentUser + ".data");
		if (file.exists()) {
		
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		_score = Integer.parseInt(scan.nextLine());
		_numLevels = Integer.parseInt(scan.nextLine());
		_currentFile = scan.nextLine().trim();
		Festival.getInstance().setVoice(scan.nextLine().trim());
		
		String line = scan.nextLine();
		WordList list = getList(line.substring(1));
		if (list == null) {
			list = new WordList(line.substring(1));
			_wordlists.add(list);
		}
		
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			if (line.startsWith("%")) {
				
				list = getList(line.substring(1));
				if (list == null) {
					list = new WordList(line.substring(1));
					_wordlists.add(list);
				}
				
			} else {
				
				String[] lineParts = line.split(" ");
				String word = "";
				for (int i = 0; i < lineParts.length - 2; i++) {
					word += lineParts[i] + " ";
				}
				
				int attempt = Integer.parseInt(lineParts[lineParts.length - 2]);
				int correct = Integer.parseInt(lineParts[lineParts.length - 1]);
				
				if (list.contains(word)) {
					list.getWord(word).setStats(attempt, correct);
				} else {
					list.add(new Word(word, attempt, correct));
				}
				
			}
		}
		
		scan.close();
		
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Method that reads in the word from the current spelling list file selected
	 */
	private void setupList() {
		
		File file = new File(_currentFile);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = scan.nextLine();
		
		if (!line.startsWith("%")) {
			JOptionPane.showMessageDialog(MainGUI.getInstance(), "Error Reading Text File!\nEnsure It Is In Correct Format");
			return;
		}
		
		WordList list = getList(line.substring(1));
		if (list == null) {
			list = new WordList(line.substring(1));
			_wordlists.add(list);
		}
		
		while (scan.hasNextLine()) {
			line = scan.nextLine();

			if (line.startsWith("%")) {
				list = getList(line.substring(1));
				if (list == null) {
					list = new WordList(line.substring(1));
					_wordlists.add(list);
				}
			} else {
				list.add(new Word(line));
			}
		}
		scan.close();
	}
	
	/**
	 * Method that clears all data held in this object and deletes and recreates the save files
	 */
	public void clearData() {
		overrideFile();
		overrideData();
		setupList();
		saveData();
	}
	
	/**
	 * This method reads the users available and adds them to a list
	 */
	private void generateUsers() {
		
		_users = new ArrayList<String>();
		
		File file = new File("res/.users");
		if (file.exists()) {
			
			Scanner scan = null;
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.getStackTrace();
			}

			while (scan.hasNextLine()) {
				String line = scan .nextLine();
				_users.add(line);
			}

			scan.close();

		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
		
	}
	
	/**
	 * This methods writes over the current users save file with the current list of users
	 */
	private void saveUsers() {
		
		File file = new File("res/.users");

		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.getStackTrace();
		}

		for (String user : _users) {
			if (!user.equals("Select User:")) {
				addToFile(user, file.getAbsolutePath());
			}
		}
		
	}
	
	/**
	 * Method that sets the current user, it will also load their data
	 * @param user
	 */
	public void setUser(String user) {
		overrideData();
		_currentUser = user;
		loadData();
		setupList();
	}
	
	/**
	 * Method that returns the current user
	 * @return
	 */
	public String getUser() {
		return _currentUser;
	}
	
	/**
	 * Method that adds a new user
	 */
	public void addNewUser(String user) {
		_users.add(user);
		saveUsers();
		setUser(user);
	}
	
	/**
	 * Method that saves the user's statistics and resets the data stored in this instance
	 */
	public void logout() {
		saveData();
		_currentUser = null;
		overrideData();
	}
	
	/**
	 * Method that returns the list of users
	 * @return
	 */
	public List<String> getUserList() {
		return _users;
	}
	
	/**
	 * Method that deletes a given user and if its the current user will clear the data in the Data instance
	 * @param user
	 */
	public void deleteUser(String user) {
		if (!_users.contains(user)) {
			return;
		}
		
		if (user.equals(_currentUser)) {
			logout();
		}
		
		_users.remove(user);
		saveUsers();
		
		File file = new File("res/." + user + ".data");
		file.delete();
	}
	
	/**
	 * Method that sets a new list
	 * @param filePath
	 */
	public void setFile(String filePath) {
		overrideFile();
		overrideData();
		_currentFile = filePath;
		setupList();
	}
	
	/**
	 * Method that adds a new WordList
	 * @param name
	 */
	public void addList(String name) {
		_wordlists.add(new WordList(name));
	}

	/**
	 * Method that returns true if there is a list with the same name or false
	 * @param name
	 * @return
	 */
	public WordList getList(String name) {
		for (WordList list : _wordlists) {
			if (name.equals(list.getName())) {
				return list;
			}
		}
		return null;
	}
	
	/**
	 * Method that returns the wordlists the user has unlocked
	 * @return
	 */
	public WordList[] getAvailableLists() {
		WordList[] array = new WordList[_numLevels];
		for (int i = 0; i < _numLevels; i++) {
			array[i] = _wordlists.get(i);
		}
		return array;
	}
	
	/**
	 * Method that returns all of the wordlists
	 * @return
	 */
	public WordList[] getAllLists() {
		WordList[] array = new WordList[_wordlists.size()];
		for (int i = 0; i < _wordlists.size(); i++) {
			array[i] = _wordlists.get(i);
		}
		return array;
	}
	
	/**
	 * Returns the score required to get to the next level
	 * @return
	 */
	public int scoreToNextLevel() {
		int num = 0;
		for (int i = 0; i < _numLevels; i++) {
			num += _wordlists.get(i).scoreToPass();
		}
		return num;
	}
	
	/**
	 * Returns the score that was required to get to the current level
	 * @return
	 */
	public int scoreToCurrentLevel() {
		int num = 0;
		for (int i = 0; i < _numLevels - 1; i++) {
			num += _wordlists.get(i).scoreToPass();
		}
		return num;
	}
	
	/**
	 * Return the users' current score
	 * @return
	 */
	public int getScore() {
		return _score;
	}
	
	/**
	 * Method that adds to the users current score and adjusts the current level appropriately
	 * @param score
	 */
	public void addScore(int score) {
		_score += score;
		if (_score >= scoreToNextLevel()) {
			if (_numLevels != _wordlists.size()) {
				_numLevels++;
			}
		}
	}
	
	/**
	 * Method that returns the WordList that is the current level
	 * @return
	 */
	public WordList getCurrentLevel() {
		return _wordlists.get(_numLevels - 1);
	}
	
	/**
	 * Method that calculates and returns the score required to pass all level
	 * @return
	 */
	public int getScoreToPassAll() {
		int total = 0;
		for (WordList list : _wordlists) {
			total += list.scoreToPass();
		}
		return total;
	}
	
	/**
	 * Method that calculates the score required to unlock the next reward
	 * @return
	 */
	public int scoreToNextReward() {
		int total = 0;
		for (WordList list : _wordlists) {
			total += list.scoreToPass();
		}
		
		if (_score >= total) {
			return 0;
		} else if (_score >= total*0.7) {
			return (int) (total);
		} else if (_score >= total*0.45) {
			return (int) (total*0.7);
		} else if (_score >= total*0.25) {
			return (int) (total*0.45);
		} else if (_score >= total*0.1) {
			return (int) (total*0.25);
		} else {
			return (int) (total*0.1);
		}
	}
	
	/**
	 * Method that returns the word with the highest correct number to attempts made ratio
	 * @return
	 */
	public Word getBestWord() {
		Word best = getAvailableLists()[0].getAtIndex(0);
		double score = 0;
		for (WordList list : getAvailableLists()) {
			for (int i = 0; i < list.size(); i++) {
				Word word = list.getAtIndex(i);
				if (word.getAttempts() != 0) {
					if ((word.getCorrect()/word.getAttempts()) >= score) {
						score = word.getCorrect()/word.getAttempts();
						best = word;
					}
				}
			}
		}
		return best;
	}
	
	/**
	 * Method that returns the word with the lowest correct number to attempts made ratio
	 * @return
	 */
	public Word getWorstWord() {
		Word worst = getAvailableLists()[0].getAtIndex(0);
		double score = 999999999;
		for (WordList list : getAvailableLists()) {
			for (int i = 0; i < list.size(); i++) {
				Word word = list.getAtIndex(i);
				if (word.getAttempts() != 0) {
					if ((word.getCorrect()/word.getAttempts()) < score) {
						score = word.getCorrect()/word.getAttempts();
						worst = word;
					}
				}
			}
		}
		return worst;
	}
	
	/**
	 * Returns the word with the most attempts made
	 * @return
	 */
	public Word getMostAttempts() {
		Word best = getAvailableLists()[0].getAtIndex(0);
		double score = 0;
		for (WordList list : getAvailableLists()) {
			for (int i = 0; i < list.size(); i++) {
				Word word = list.getAtIndex(i);
				if (word.getAttempts() > score) {
					score = word.getAttempts();
					best = word;
				}
			}
		}
		return best;
	}
	
	/**
	 * Returns the word with the most number of correct attempts
	 * @return
	 */
	public Word getMostCorrect() {
		Word best = getAvailableLists()[0].getAtIndex(0);
		double score = 0;
		for (WordList list : getAvailableLists()) {
			for (int i = 0; i < list.size(); i++) {
				Word word = list.getAtIndex(i);
				if (word.getCorrect() > score) {
					score = word.getCorrect();
					best = word;
				}
			}
		}
		return best;
	}
			
}