package voxspell;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import voxspell.data.Data;
import voxspell.gui.Login;

/**
 * 
 * The Class that contains the main method
 * Starts the JFrame and adds the Login panel
 * 
 * @author alya691
 *
 */
@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	
	/**
	 * Instance of the MainGUI
	 */
	private static MainGUI _instance;
	
	/**
	 * Used for Singleton pattern
	 * Only one instance can be created
	 * @return
	 */
	public static MainGUI getInstance() {
		if (_instance == null) {
			_instance = new MainGUI();
		}
		return _instance;
	}
	
	/**
	 * Constructor that runs for the JFrame
	 * Makes data save on closing the JFrame
	 */
	private MainGUI() {
		super("VOXSPELL");
		setSize(400, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Data.getInstance().saveData();
				System.exit(0);
			}
		});
	}

	/**
	 * Main Method
	 * Starts the JFrame
	 * @param args
	 */
	public static void main(String[] args) {
				
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainGUI frame = getInstance();
				frame.setSize(new Dimension(800,700));
				frame.setResizable(false);
				frame.add(new Login());
				frame.setVisible(true);
			}
		});
		
	}
	

}
