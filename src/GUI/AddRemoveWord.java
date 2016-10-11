package GUI;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import GUI.Data.Data;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddRemoveWord extends GUIElement {
	
	private JLabel _title;
	private JComboBox _operation;
	private JPanel _empty;
	private JButton _submit;
	private JButton _return;
	
	private JPanel _addPanel;
	private JPanel _removePanel;
	
	private JComboBox _addLevel;
	private JTextField _addInput;
	private JButton _addSubmit;
	
	private JComboBox _removeLevel;
	private JComboBox _removeInput;
	private JButton _removeSubmit;

	/**
	 * Create the panel.
	 */
	public AddRemoveWord(JFrame frame, Data data) {
		super(frame, data);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 20, 25, 0, 0, 75, 0, 25, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		_title = new JLabel("ADD WORDS");
		_title.setFont(new Font("Dialog", Font.BOLD, 25));
		GridBagConstraints gbc_lblAddWords = new GridBagConstraints();
		gbc_lblAddWords.insets = new Insets(0, 0, 5, 0);
		gbc_lblAddWords.gridx = 0;
		gbc_lblAddWords.gridy = 0;
		add(_title, gbc_lblAddWords);
		
		JLabel lblSelectAnOperation = new JLabel("Select An Operation:");
		GridBagConstraints gbc_lblSelectAnOperation = new GridBagConstraints();
		gbc_lblSelectAnOperation.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectAnOperation.gridx = 0;
		gbc_lblSelectAnOperation.gridy = 2;
		add(lblSelectAnOperation, gbc_lblSelectAnOperation);
		
		_operation = new JComboBox();
		_operation.setFont(new Font("Dialog", Font.BOLD, 15));
		GridBagConstraints gbc__operation = new GridBagConstraints();
		gbc__operation.insets = new Insets(0, 0, 5, 0);
		gbc__operation.fill = GridBagConstraints.HORIZONTAL;
		gbc__operation.gridx = 0;
		gbc__operation.gridy = 3;
		add(_operation, gbc__operation);
		
		_empty = new JPanel();
		GridBagConstraints gbc__empty = new GridBagConstraints();
		gbc__empty.insets = new Insets(0, 0, 5, 0);
		gbc__empty.fill = GridBagConstraints.BOTH;
		gbc__empty.gridx = 0;
		gbc__empty.gridy = 5;
		add(_empty, gbc__empty);
		
		_return = new JButton("Return");
		GridBagConstraints gbc__return = new GridBagConstraints();
		gbc__return.insets = new Insets(0, 0, 5, 0);
		gbc__return.gridx = 0;
		gbc__return.gridy = 8;
		add(_return, gbc__return);
		
		_submit = new JButton("Submit");
		GridBagConstraints gbc__submit = new GridBagConstraints();
		gbc__submit.gridx = 0;
		gbc__submit.gridy = 7;
		add(_submit, gbc__submit);
		
		_operation.addActionListener(this);
		_submit.addActionListener(this);
		_return.addActionListener(this);
		
		setupAddPanel();
		
	}
	
	private void setupAddPanel() {
		
		_addPanel = new JPanel();
		_addPanel.setLayout(new GridBagLayout());
		
		List<String> levels = _data.getUserList();
		levels.add("INSERT NEW LEVEL");
		_addLevel = new JComboBox(levels.toArray());
		_addLevel.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc__addLevel = new GridBagConstraints();
		gbc__addLevel.insets = new Insets(0, 0, 5, 0);
		gbc__addLevel.gridx = 0;
		gbc__addLevel.gridy = 0;
		_addPanel.add(_addLevel, gbc__addLevel);
		
		_addInput = new JTextField();
		_addInput.setFont(new Font("Dialog", Font.PLAIN, 25));
		GridBagConstraints gbc__addInput = new GridBagConstraints();
		gbc__addInput.insets = new Insets(0, 0, 5, 0);
		gbc__addInput.gridx = 0;
		gbc__addInput.gridy = 2;
		_addPanel.add(_addInput, gbc__addInput);
		
		_addSubmit = new JButton("Submit");
		GridBagConstraints gbc__addSubmit = new GridBagConstraints();
		gbc__addSubmit.insets = new Insets(0, 0, 5, 0);
		gbc__addSubmit.gridx = 0;
		gbc__addSubmit.gridy = 2;
		_addPanel.add(_addSubmit, gbc__addSubmit);
		
		_addSubmit.addActionListener(this);
		
		_title.setText("ADD WORD");
		
		_empty.removeAll();
		_empty.add(_addPanel);
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}
	
	private void setupRemovePanel() {
		
		_removePanel = new JPanel();
		_removePanel.setLayout(new GridBagLayout());
		
		_removeLevel = new JComboBox(_data.getUserList().toArray());
		_removeLevel.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc__removeLevel = new GridBagConstraints();
		gbc__removeLevel.insets = new Insets(0, 0, 5, 0);
		gbc__removeLevel.gridx = 0;
		gbc__removeLevel.gridy = 0;
		_removePanel.add(_removeLevel, gbc__removeLevel);
		
		_removeInput = new JComboBox(_data.getUserList().toArray());
		_removeInput.setFont(new Font("Dialog", Font.PLAIN, 20));
		GridBagConstraints gbc__removeInput = new GridBagConstraints();
		gbc__removeInput.insets = new Insets(0, 0, 5, 0);
		gbc__removeInput.gridx = 0;
		gbc__removeInput.gridy = 2;
		_removePanel.add(_removeInput, gbc__removeInput);
		
		_removeSubmit = new JButton("Submit");
		GridBagConstraints gbc__removeSubmit = new GridBagConstraints();
		gbc__removeSubmit.insets = new Insets(0, 0, 5, 0);
		gbc__removeSubmit.gridx = 0;
		gbc__removeSubmit.gridy = 2;
		_removePanel.add(_removeSubmit, gbc__removeSubmit);
		
		_removeSubmit.addActionListener(this);
		
		_title.setText("Remove Word");
		
		_empty.removeAll();
		_empty.add(_removePanel);
		
		_GUI.revalidate();
		_GUI.repaint();
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src.equals(_operation)) {
			if (_operation.getSelectedItem().equals("ADD WORD")) {
				setupAddPanel();
			} else {
				setupRemovePanel();
			}
		} else if (src.equals(_addSubmit)) {
			
			if (_addLevel.getSelectedItem().equals("INSERT NEW LEVEL")) {
				String listName = JOptionPane.showInputDialog(_GUI, "Please enter a level name:");
				/*
				 * CHECK IF LIST EXISTS!
				 */
				if (listName == null || listName.isEmpty()) {
					JOptionPane.showMessageDialog(_GUI, "Invalid List Name");
					return;
				}
				_data.addList(listName);
			}
			
			/*
			 * CHECK WORD DOES NOT EXIST IN LIST THEN ADD WORD
			 */
			
		}
		
	}

}
