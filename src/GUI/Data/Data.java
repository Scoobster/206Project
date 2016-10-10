package GUI.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Data {
	
	private String _currentUser = null;
	private List<String> _users;
	
	private String _currentVoice = "American";
	private Map<String,String> _voices;
	
	private String _currentList = "NZCER-spelling-lists.txt";
	
	private int _score = 0;
	
	
	/*
	public Data() {
		generateUsers();
		initialiseVoices();
		
		// TO BE IMPLEMENTED
		
	}
	*/
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
	 * Method that saves all relevant data to the user's save file
	 */
	public void saveData() {
		saveUsers();
		writeDataToFile();
		/*
		 * TO BE IMPLEMENTED
		 */
	}
	
	/**
	 * Method that reads the current users data file and loads all their previous data
	 */
	private void loadData() {
		/*
		 * TO BE IMPLEMENTED
		 */
		readUserFile();
	}
	
	/**
	 * Method that clears all data held in this object and deletes and recreates the save files
	 */
	public void clearData() {
		/*
		 * TO BE IMPLEMENTED
		 */
		overrideAll();
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
		_currentUser = user;
		loadData();
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
		/*
		 * TO BE IMPLEMENTED
		 */
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
		_wordLists = new ArrayList<WordList>();
		setupLists();
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
			_score = 0;
			_wordLists = new ArrayList<WordList>();
			_mistakes = new WordList("mistakes");
			setupLists();
		}
		
		_users.remove(user);
		saveUsers();
		
		File file = new File("res/." + user + ".data");
		file.delete();
	}
	
	/**
	 * Method that initialises the variables associated to the festival voices
	 */
	private void initialiseVoices() {
		_currentVoice = "American";
		_voices = new HashMap<String,String>();
		_voices.put("American", "(voice_kal_diphone)");
		_voices.put("British", "(voice_rab_diphone)");
		_voices.put("New Zealander", "(voice_akl_nz_jdt_diphone)");
	}
	
	/**
	 * Returns the name of the festival voice set as the current voice
	 * @return
	 */
	public String getVoice() {
		return _currentVoice;
	}
	
	/**
	 * Method that returns the code required to set the voice on festival, if name is not a voice method returns null
	 * @param name
	 * @return
	 */
	public String getVoiceCode(String name) {
		if (_voices.containsKey(name)) {
			return _voices.get(name);
		}
		return null;
	}
	
	/**
	 * Method that sets a new current festival voice
	 * @param name
	 */
	public void setVoice(String name) {
		if (_voices.containsKey(name)) {
			_currentVoice = name;
		}
	}
	
	/**
	 * Method that returns an array of the voice names
	 * @return
	 */
	public Set<String> getVoicesSet() {
		return _voices.keySet();
	}
	
	/**
	 * Method that sets a new list
	 * @param filePath
	 */
	public void setList(String filePath) {
		_currentList = filePath;
		_wordLists = new ArrayList<WordList>();
		setupLists();
	}
	
	
	/*
	 * 
	 * 
	 * THINGS ADDED FOR BETA SUBMISSION!
	 * 
	 * 
	 */
	
	private String _currentLevel = "Level 1";
	private ArrayList<WordList> _wordLists = new ArrayList<WordList>();
	private WordList _mistakes = new WordList("mistakes");
	
	/**
	 * Constructor, setups up the WordList for testing and the users list
	 */
	public Data() {
		setupLists();
		initialiseVoices();
		generateUsers();
	}
	
	/**
	 * Sets up the current list of words
	 */
	public void setupLists() {
		
		_wordLists = new ArrayList<WordList>();

		File levels = new File(_currentList);
		Scanner levelsFile = null;
		try {
			levelsFile = new Scanner(levels);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WordList var = new WordList(levelsFile.nextLine().substring(1));
		_wordLists.add(var);

		while (levelsFile.hasNextLine()) {
			String line = levelsFile.nextLine();
			if (line.equals("%mistakes")) {
				var = _mistakes;
			} else if (line.startsWith("%")) {
				var = new WordList(line.substring(1));
				if (!var.getName().equals("mistakes")) {
					_wordLists.add(var);
				}
			} else {
				var.add(new Word(line));
			}
		}
		
		levelsFile.close();
		
	}
	
	/**
	 * Getting the saved data for the current user
	 */
	private void readUserFile() {
		
		File file = new File("res/." + _currentUser + ".data");
		
		if (file.isFile()) {
			
			Scanner scanFile = null;
			try {
				scanFile = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			if (scanFile.hasNextLine()) {
				
				_score = Integer.parseInt(scanFile.nextLine());
				
				setVoice(scanFile.nextLine());
				
				WordList var = null;
				
				String line = scanFile.nextLine();
				if (doesListExist(line.substring(1))) {
					var = getList(line.substring(1));
				} else {
					var = new WordList(line.substring(1));
					_wordLists.add(var);
				}
				
				while (scanFile.hasNextLine()) {
					line = scanFile.nextLine();
					if (line.startsWith("%")) {
						if (doesListExist(line.substring(1))) {
							var = getList(line.substring(1));
						} else {
							var = new WordList(line.substring(1));
							_wordLists.add(var);
						}
					} else {
						var.add(line);
					}
				}
				
			}
			scanFile.close();
		} else {
			try {
				file.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 
	 * Checks if a list with the same name already exists 
	 */
	private boolean doesListExist(String listName) {
		for (WordList var : _wordLists) {
			if (var.getName().equals(listName)) {
				return true;
			}
		}
		return false;
	}

	/** 
	 * Returns WordList of the same name, or null if list does not exist
	 */
	public WordList getList(String listName) {
		for (WordList var : _wordLists) {
			if (var.getName().equals(listName)) {
				return var;
			}
		}
		return null;
	}

	/** 
	 * This method returns an array of Strings representing the level names 
	 */
	public String[] getNamesOfLists() {
		String[] names = new String[_wordLists.size()];
		int i = 0;
		for (WordList var : _wordLists) {
			names[i] = var.getName();
			i++;
		}
		return names;
	}

	/** 
	 * This method gets the Wordlist representing the words failed
	 */
	public WordList getMistakes() {
		return _mistakes;
	}

	/** 
	 * This method overwrites the data stored in the file users data file by deleting the file and creating a new one. 
	 */
	public void overrideFile() {
		File data = new File("res/." + _currentUser + ".data");
		data.delete();
		try {
			data.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Overrides the data currently in the DataStore object
	 */
	public void overrideData() {
		_currentLevel = "Level 1";
		_score = 0;
		_wordLists = new ArrayList<WordList>();
		_mistakes = new WordList("mistakes");
		setupLists();
		readUserFile();
	}

	/**
	 * Calls both overrideFile() and overrideData() methods
	 */
	public void overrideAll() {
		overrideFile();
		overrideData();
	}
	
	/** 
	 * This method writes the data stored in this object to the current user's data file
	 */
	public void writeDataToFile() {

		if (_currentUser != null) {
			
			overrideFile();
			saveUsers();

			String fileName = "res/." + _currentUser + ".data";

			addToFile("" + _score, fileName);
			addToFile(getVoice(), fileName);

			addToFile("%" + _mistakes.getName(), fileName);
			for (Word var : _mistakes.returnCopyOfList()) {
				addToFile(var.getStats(), fileName);
			}

			for (WordList var : _wordLists) {
				addToFile("%" + var.getName(), fileName);
				for (Word var1 : var.returnCopyOfList()) {
					addToFile(var1.getStats(), fileName);
				}
			}

		}

	}

	/**
	 * This method returns the name of the current level
	 */
	public String getCurrentLevelName() {
		return _currentLevel;
	}


	/** 
	 * This method returns the name of the level above current level.
	 */
	public String getNextLevelName() throws ArrayIndexOutOfBoundsException {
		String[] names = getNamesOfLists();
		for (int i = 0; i < names.length; i++) {
			if (i == 11) {
				throw new ArrayIndexOutOfBoundsException();
			}
			if (names[i].equals(_currentLevel)) {
				return names[i+1];
			}
		}
		return names[0];
	}
	
	/**
	 * This method sets the value of _currentLevel 
	 */
	public void setCurrentLevel(String level) {
		_currentLevel = level;
	}

	
}