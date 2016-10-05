import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.QuizScreen;
import GUI.Login;
import GUI.Data.DataStore;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	
	private DataStore _data;
	
	public MainGUI() {
		super("Spelling Aid");
		
		
		_data = new DataStore();
			
		setSize(400, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	
		add(new Login(this, _data));
		//add(new QuizScreen(this, _data, null));
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				_data.overrideFile();
				_data.writeDataToFile();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		
		
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainGUI frame = new MainGUI();
				frame.setVisible(true);
			}
		});
		
		
		
	}

}
