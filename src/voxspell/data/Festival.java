package voxspell.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingWorker;

import voxspell.gui.GUIElement;

/**
 * Class that controls all functionality related to Festival/Text-To-Speech
 * @author scoobster
 *
 */
public class Festival{
	
	
	private static Festival _instance = null;
		
	private GUIElement _screen;
	
	private String _festivalPath = "/usr/bin/festival";

	private String _currentVoice;
	private Map<String,String> _voiceCodes;
	
	private List<String> _correctFeedback;
	private List<String> _incorrectFeedback;

	private String _line;
	
	/**
	 * Constructor sets up relevant fields
	 */
	private Festival() {
		_currentVoice = "American";
		_voiceCodes = new HashMap<String,String>();
		_voiceCodes.put("American", "(voice_kal_diphone)");
		_voiceCodes.put("British", "(voice_rab_diphone)");
		_voiceCodes.put("New Zealander", "(voice_akl_nz_jdt_diphone)");
		setupFeedback();
	}
	
	/**
	 * Returns the one instance of Festival
	 * @return
	 */
	public static Festival getInstance() {
		if (_instance == null) {
			_instance = new Festival();
		}
		return _instance;
	}
	
	/**
	 * Sets up the Feedback variables
	 */
	private void setupFeedback() {
		_correctFeedback = new ArrayList<String>();
		_correctFeedback.add("Good Job!");
		_correctFeedback.add("Correct!");
		_correctFeedback.add("Awesome!");
		
		_incorrectFeedback = new ArrayList<String>();
		_incorrectFeedback.add("Nice try");
		_incorrectFeedback.add("Bad luck");
		_incorrectFeedback.add("Next time");
	}
	
	/**
	 * Method that will make festival speak a given string and will make the relevant GUIElement buttons inaccessible
	 * @param line
	 */
	private void speakLine(String line) {

		if (_screen != null) {
			_screen.enableButtons(false);
		}
		_line = line;
		new FestivalCall().execute();

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
	private String getCode() {
		return _voiceCodes.get(_currentVoice);
	}
	
	/**
	 * Method that sets a new current festival voice
	 * @param name
	 */
	public void setVoice(String name) {
		if (_voiceCodes.containsKey(name)) {
			_currentVoice = name;
		}
	}
	
	/**
	 * Method that returns an array of the voice names
	 * @return
	 */
	public Set<String> getVoicesSet() {
		return _voiceCodes.keySet();
	}
	
	/**
	 * Method that says a given word
	 * @param word
	 */
	public void sayWord(String word) {
		speakLine(word);
	}
	
	/**
	 * Method that says the word for the first time (so has an additional things said)
	 */
	public void sayFirstWord(String word) {
		speakLine("Please spell the word.. " + word);
	}
	
	/**
	 * Method that spells the word for the user
	 */
	public void spellWord(String word) {
		String line = "Here is the spelling of the word... ";
		for (int i = 0; i < word.length(); i++) {
			line = line + word.charAt(i) + "... ";
		}
		speakLine(line);
	}
	
	/**
	 * Method congratulates user for getting the word right
	 */
	public void sayCorrectFeedback() {
		Collections.shuffle(_correctFeedback);
		speakLine(_correctFeedback.get(0));
	}
	
	/**
	 * Method  user for getting the word right
	 */
	public void sayIncorrectFeedback() {
		Collections.shuffle(_incorrectFeedback);
		speakLine(_incorrectFeedback.get(0));
	}
	
	/**
	 * Method that sets the GUIElement the code is running from
	 * @param screen
	 */
	public void setScreen(GUIElement screen) {
		_screen = screen;
	}
	
	/**
	 * 
	 * Inner class that extends SwingWorker for making the Festival calls on a separate thread
	 * 
	 * @author scoobster
	 *
	 */
	private class FestivalCall  extends SwingWorker<Void,Void> {

		/**
		 * Makes the Festival call
		 */
		@Override
		protected Void doInBackground() throws Exception {
			
			ProcessBuilder builder = new ProcessBuilder(_festivalPath, getCode(), "(SayText \"" + _line + "\")", "(quit)");

			try {
				Process process = builder.start();
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * Makes the button on the GUIElement accessible again
		 */
		@Override
		protected void done() {
			if (_screen != null) {
				_screen.enableButtons(true);
			}
		}
		
	}
	
}
