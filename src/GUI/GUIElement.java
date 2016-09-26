package GUI;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Data.DataStore;



@SuppressWarnings("serial")
public abstract class GUIElement extends JPanel implements ActionListener {

	protected JFrame _GUI;
	protected DataStore _data;
	
	protected GUIElement(JFrame frame, DataStore data) {
		
		super();
		_GUI = frame;
		_data = data;
			
	}
	

}
