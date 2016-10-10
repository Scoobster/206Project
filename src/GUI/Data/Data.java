package GUI.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
	
	private String _currentUser = null;
	private List<String> _users;
	
	public Data() {
		generateUsers();
		/*
		 * TO BE IMPLEMENTED
		 */
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
	 * Method that saves all relevant data to the user's save file
	 */
	public void saveData() {
		saveUsers();
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
	 * Method that adds a new user
	 */
	public void addNewUser(String user) {
		/*
		 * TO BE IMPLEMENTED
		 */
	}
	
	/**
	 * Method that returns the list of users
	 * @return
	 */
	public List<String> getUserList() {
		return _users;
	}
	
}