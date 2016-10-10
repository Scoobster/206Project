package OldCode.GUI.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * 
 * @author ngav018
 *
 */

public class DataStore {

	private String _currentUser = null;
	private int _score = 0;
	private String _listPath = "NZCER-spelling-lists.txt";
	
	private String _voice = "(voice_kal_diphone)";
	private ArrayList<String> users = new ArrayList<String>();
	
	private String _currentLevel = "Level 1";
	private ArrayList<WordList> _wordLists = new ArrayList<WordList>();
	private WordList _mistakes = new WordList("mistakes");
	
	/**
	 * Constructor, setups up the WordList for testing and the users list
	 */
	public DataStore() {
		setupLists();
		generateUsers();
	}
	
	/**
	 * Sets up the current list of words
	 */
	public void setupLists() {
		
		_wordLists = new ArrayList<WordList>();

		File levels = new File(_listPath);
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
			
			saveUsers();

			String fileName = "res/." + _currentUser + ".data";

			addToFile("" + _score, fileName);
			addToFile(getVoiceName(), fileName);

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
	 * This method adds a string on a new line to a file
	 */
	private void addToFile(String word, String fileName) {
		String newLine = System.getProperty("line.separator");
		String line = word + newLine;

		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(fileName, true));
			file.write(line);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/** 
	 * This method sets the value of _currentLevel 
	 */
	public void setCurrentLevel(String level) {
		_currentLevel = level;
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
	 * Method that sets the festival voice
	 * @param voice
	 */
	public void setVoice(String voice) {
		
		if (voice.equals("American")) {
			_voice = "(voice_kal_diphone)";
		} else if (voice.equals("British")) {
			_voice = "(voice_rab_diphone)";
		} else if (voice.equals("New Zealander")) {
			_voice = "(voice_akl_nz_jdt_diphone)";
		}
		
	}
	
	/**
	 * Returns the festival voice command to change the voice
	 * @return
	 */
	public String getVoice() {
		return _voice;
	}
	
	/**
	 * Returns the name of the set voice on festival
	 * @return
	 */
	private String getVoiceName() {
		if (_voice.equals("(voice_kal_diphone)")) {
			return "American";
		} else if (_voice.equals("(voice_rab_diphone)")) {
			return "British";
		} else if (_voice.equals("(voice_akl_nz_jdt_diphone)")) {
			return "New Zealander";
		} else {
			return "American";
		}
	}
	
	/**
	 * Sets the current user and imports their data
	 * @param user
	 */
	public void setUser(String user) {
		_currentUser = user;
		readUserFile();
	}
	
	/**
	 * Creates a new user and sets them as the current user
	 * @param user
	 */
	public void createUser(String user) {
		users.add(user);
		saveUsers();
		setUser(user);
	}
	
	/**
	 * Method that deletes a user
	 * @param user
	 */
	public void deleteUser(String user) {
		
		if (!users.contains(user)) {
			return;
		}
		
		if (user.equals(_currentUser)) {
			_currentLevel = "Level 1";
			_wordLists = new ArrayList<WordList>();
			_mistakes = new WordList("mistakes");
			setupLists();
		}
		
		users.remove(user);
		saveUsers();
		
		File file = new File("res/." + user + ".data");
		file.delete();
		
	}
	
	/**
	 * Method that reads the file listing the users to generate it's list of users
	 */
	private void generateUsers() {

		users.add("Select User:");
		File file = new File("res/.users");
		if (file.exists()) {
			System.out.println(file.getAbsolutePath());
			Scanner scan = null;
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.getStackTrace();
			}

			while (scan.hasNextLine()) {
				String line = scan .nextLine();
				users.add(line);
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
	 * Method that saves the current list of users to the save file
	 */
	private void saveUsers() {

		File file = new File("res/.users");

		file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.getStackTrace();
		}

		for (String user : users) {
			if (!user.equals("Select User:")) {
				addToFile(user, file.getAbsolutePath());
			}
		}

	}
	
	/**
	 * Returns a list of all the users
	 * @return
	 */
	public ArrayList<String> getUsers() {
		return users;
	}
	
	/**
	 * Returns the name of the current user
	 * @return
	 */
	public String getCurrentUser() {
		return _currentUser;
	}
	
	/**
	 * Method that sets the current spelling word list and updates the word list
	 * @throws FileNotFoundException 
	 */
	public void setList(String filePath) {
		_listPath = filePath;
		overrideAll();
		setupLists();
	}
	
	/**
	 * Returns the current score
	 * @return
	 */
	private int getScore() {
		return _score;
	}
		
	/*
	
	private void setupVariables() {
		
		for (WordList list : _wordLists) {
			int scoreRequired = ((list.getSize()/10)*9*90);
			if (_score < scoreRequired) {
				break;
			}
		}
		
	}
	
	private int getTotalScore() {
		int totalScore = 0;
		for (WordList list : _wordLists) {
			totalScore += ((list.getSize()/10)*9*90);
		}
		return totalScore;
	}
	
	*/
}
