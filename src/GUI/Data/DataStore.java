package GUI.Data;

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

	private String _currentLevel = "Level 1";
	private ArrayList<WordList> _wordLists = new ArrayList<WordList>();
	private WordList _mistakes = new WordList("mistakes");
	
	public DataStore() {
		setupLists();
		generateUsers();
	}
	
	/*
	 * 
	 * CHECK THIS SHIT!
	 * 
	 */
	
	private void readUserFile() {
		
		File file = new File("res/." + _currentUser + ".data");
		
		if (file.isFile()) {
			
			Scanner scanFile = null;
			try {
				scanFile = new Scanner(file);
			} catch (FileNotFoundException e) {
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
	/*
	 * CHECK THIS SHIT!
	 */
	
	public void setupLists() {

		File levels = new File("NZCER-spelling-lists.txt");
		Scanner levelsFile = null;
		try {
			levelsFile = new Scanner(levels);
		} catch (FileNotFoundException e) {
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

	/** This method checks whether there is already a WordList object
	 *  in the _wordlists field with a name matching the String parameter. 
	 */
	private boolean doesListExist(String listName) {
		for (WordList var : _wordLists) {
			if (var.getName().equals(listName)) {
				return true;
			}
		}
		return false;
	}

	/** This method gets a WordList object matching the name of the 
	 *  String parameter.
	 *  returns null if no list is stored.
	 */
	public WordList getList(String listName) {
		for (WordList var : _wordLists) {
			if (var.getName().equals(listName)) {
				return var;
			}
		}
		return null;
	}

	/** This method returns an array of Strings representing the level names 
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

	/** This method gets the Wordlist representing the words failed
	 */
	public WordList getMistakes() {
		return _mistakes;
	}

	/** This method overwrites the data stored in the file .Data.txt by deleting
	 *  the file and creating a new one. 
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
	
	
	public void overrideData() {
		_currentLevel = "Level 1";
		_wordLists = new ArrayList<WordList>();
		_mistakes = new WordList("mistakes");
		setupLists();
		readUserFile();
	}

	public void overrideAll() {
		overrideFile();
		overrideData();
	}
	
	/** This method writes the data stored in this object to the file .Data.txt
	 */
	public void writeDataToFile() {

		if (_currentUser != null) {
			
			saveUsers();

			String fileName = "res/." + _currentUser + ".data";

			addToFile("" + _score, fileName);
			addToFile(getVoiceName(), fileName);

			addToFile("%" + _mistakes.getName(), fileName);
			for (Word var : _mistakes.returnCopyOfList()) {
				addToFile(var.toString(), fileName);
			}

			for (WordList var : _wordLists) {
				addToFile("%" + var.getName(), fileName);
				for (Word var1 : var.returnCopyOfList()) {
					addToFile(var1.toString(), fileName);
				}
			}

		}

	}

	/**This method adds a string on a new line to a file
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
	
	/** This method sets the value of _currentLevel 
	 */
	public void setCurrentLevel(String level) {
		_currentLevel = level;
	}

	/** This method returns the WordList of _currentLevel 
	 */
	public String getCurrentLevelName() {
		return _currentLevel;
	}


	/** This method returns the name of the level above 
	 *  current level.
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
	 * Andrew Additions!!!!!!!!!!!!!!!!!!!
	 */
	
	private String _voice = "(voice_kal_diphone)";
	private ArrayList<String> users = new ArrayList<String>();
	
	public void setVoice(String voice) {
		
		if (voice.equals("American")) {
			_voice = "(voice_kal_diphone)";
		} else if (voice.equals("British")) {
			_voice = "(voice_rab_diphone)";
		} else if (voice.equals("New Zealander")) {
			_voice = "(voice_akl_nz_jdt_diphone)";
		}
		
	}
	
	public String getVoice() {
		return _voice;
	}
	
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
	
	public void setUser(String user) {
		
		_currentUser = user;
		readUserFile();
		
	}
	
	public void createUser(String user) {
		
		_currentUser = user;
		users.add(user);
		saveUsers();
		readUserFile();
		
	}
	
	public void deleteUser(String user) {
		
		
		
	}
	
	private void generateUsers() {

		users.add("Select User:");
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
	
	public ArrayList<String> getUsers() {
		return users;
	}
	
	/*
	 * 
	 * Do Something!
	 * 
	 */
	
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
	
	
}
