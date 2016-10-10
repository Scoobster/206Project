import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.Data.Data;
import GUI.Login;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	
	private Data _data;
	
	public MainGUI() {
		super("Spelling Aid");
		
		
		_data = new Data();
			
		setSize(400, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	
		add(new Login(this, _data));
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				_data.saveData();
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
