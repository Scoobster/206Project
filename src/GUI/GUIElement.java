package GUI;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Data.Data;



@SuppressWarnings("serial")
public abstract class GUIElement extends JPanel implements ActionListener {

	protected JFrame _GUI;
	protected Data _data;
	
	protected GUIElement(JFrame frame, Data data) {
		super();
		_GUI = frame;
		_data = data;
	}
	
	protected void changeScreen(GUIElement other) {
		_GUI.getContentPane().removeAll();
		_GUI.getContentPane().add(other);
		_GUI.revalidate();
		_GUI.repaint();
	}
	

}
