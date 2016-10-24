package voxspell.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import voxspell.data.Data;
import voxspell.data.Festival;
import voxspell.MainGUI;

/**
 * 
 * Abstract class that all screens extend
 * 
 * @author scoobster
 *
 */
@SuppressWarnings("serial")
public abstract class GUIElement extends JPanel implements ActionListener {

	protected JFrame _GUI;
	protected Data _data;
	protected Festival _festival;
	
	/**
	 * Constructor
	 * Initialises variables and sets the size and background colour of the screens
	 */
	protected GUIElement() {
		super();
		setBackground(new Color(204, 255, 204));
		setPreferredSize(new Dimension(800,600));
		_GUI = MainGUI.getInstance();
		_data = Data.getInstance();
		_festival = Festival.getInstance();
	}
	
	/**
	 * Method that changes the screen shown on the GUI to a new screen
	 * @param other
	 */
	protected void changeScreen(GUIElement other) {
		_GUI.getContentPane().removeAll();
		_GUI.getContentPane().add(other);
		_GUI.revalidate();
		_GUI.repaint();
	}
	
	/**
	 * Returns a button of a specific type so that all buttons are the same
	 * @param text
	 * @return
	 */
	protected JButton getButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Dialog", Font.PLAIN, 20));
		button.setPreferredSize(new Dimension(300,50));
		return button;
	}
	
	/**
	 * Method that festival may require
	 * Only particular screens need it implement so will be overriden on those screens
	 * @param bool
	 */
	public void enableButtons(boolean bool) { }

}
