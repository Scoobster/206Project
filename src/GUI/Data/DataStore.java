package GUI.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class DataStore {


	private String _currentLevel = "Level 1";
	private ArrayList<WordList> _wordLists = new ArrayList<WordList>();
	private WordList _mistakes = new WordList("mistakes");
	private String _festivalPath;

	public DataStore() {
		readFromFile();
		locateFestival();
	}
	
	private void readFromFile() {
		File file = new File(".Data.txt");
		if (file.isFile()) {
			Scanner scanFile = null;
			try {
				scanFile = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (scanFile.hasNextLine()) {
				
				_currentLevel = scanFile.nextLine();
				
				WordList var = new WordList(scanFile.nextLine().substring(1));

				while (scanFile.hasNextLine()) {
					String line = scanFile.nextLine();
					if (line.equals("%mistakes")) {
						var = _mistakes;
					} else if (line.startsWith("%")) {
						var = new WordList(line.substring(1));
						if (!var.getName().equals("mistakes")) {
							_wordLists.add(var);
						}
					} else {
						addWordFromString(var, line);
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

		File levels = new File("NZCER-spelling-lists.txt");
		Scanner levelsFile = null;
		try {
			levelsFile = new Scanner(levels);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		WordList var = null;
		
		String line = levelsFile.nextLine();
		if (doesListExist(line.substring(1))) {
			var = getList(line.substring(1));
		} else {
			var = new WordList(line.substring(1));
			_wordLists.add(var);
		}
		
		while (levelsFile.hasNextLine()) {
			line = levelsFile.nextLine();
			if (line.startsWith("%")) {
				if (doesListExist(line.substring(1))) {
					var = getList(line.substring(1));
				} else {
					var = new WordList(line.substring(1));
					_wordLists.add(var);
				}
			} else {
				if (!var.containsWord(line)) {
					var.add(new Word(line));
				}
			}
		}
		
		levelsFile.close();
		
		overrideFile();
	}
	
	/** This method converts a line from a file into a format suitable
	 *  for creating a word object, then adds it to a wordlist.
	 */
	private void addWordFromString(WordList var, String line) {
		String[] wordInfo = line.split("\\s+");	
		if (wordInfo.length > 4) {
			String word = "";
			for (int i = 0; i < wordInfo.length - 3; i++) {
				word = word + " " + wordInfo[i];
			}
			String[] twoWords = {word, wordInfo[wordInfo.length - 3], wordInfo[wordInfo.length - 2], wordInfo[wordInfo.length - 1]};
			wordInfo = twoWords;
		}
		
		
		var.add(new Word(wordInfo[0], 
				Integer.parseInt(wordInfo[1]), 
				Integer.parseInt(wordInfo[2]), 
				Integer.parseInt(wordInfo[3])));
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
		File data = new File(".Data.txt");
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
		readFromFile();
	}

	public void overrideAll() {
		overrideFile();
		overrideData();
	}
	
	/** This method writes the data stored in this object to the file .Data.txt
	 */
	public void writeDataToFile() {

		String fileName = ".Data.txt";

		if (_currentLevel == null) {
			_currentLevel = "Level 1";
		}
		
		addToFile(_currentLevel, fileName);

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
	
	private void locateFestival() {
			
		File file = new File(".save");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c","which festival > '" + file.getAbsolutePath() + "'");
		try {
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = scan.nextLine();
		_festivalPath = line;
		
		scan.close();
		file.delete();
		
	}
	
	public String getFestival() {
		return _festivalPath;
	}
	
	
	
}
