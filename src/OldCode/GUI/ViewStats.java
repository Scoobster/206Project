package OldCode.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import GUI.Data.DataStore;
import OldCode.GUI.Data.Word;
import OldCode.GUI.Data.WordList;

public class ViewStats extends GUIElement {
	
	private JPanel _previousScreen;
	private JButton _goBack = new JButton("Go Back");
	private JComboBox<String> _selectInfo;
	private JTable _table;
	private JLabel _title = new JLabel("Current Statistics");
	private JScrollPane _scroll;
	private JPanel _south = new JPanel();
	private JPanel _tableArea = new JPanel();
	
	private Object[][] _overall;
	private Object[][] _specificLevel;
	private String[] _overallColumnHeads = {"Word List", "Average Score out of 10", "Previous Score out of 10", "Progression"};
	private String[] _levelColumnHeads = {"Word", "Times mastered", "Times faulted", "Times failed"};
	

	public ViewStats(JFrame frame, DataStore data, JPanel previousScreen) {
		super(frame, data);
		_previousScreen = previousScreen;
		_previousScreen.setVisible(false);
		
		
		setLayout(new BorderLayout());
		
		add(_title, BorderLayout.NORTH);
		
		_south.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 0.5;
		c.weighty = 0.5;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		String[] nameList = _data.getNamesOfLists();
		String[] nameList1 = new String[nameList.length + 1];
		nameList1[0] = "Overall";
		for (int i = 1; i < nameList1.length; i++) {
			nameList1[i] = nameList[i-1];
		}
		
		_selectInfo = new JComboBox<String>(nameList1);
		_selectInfo.addActionListener(this);
		
		_south.add(_selectInfo, c);
		
		c.gridx = 3;
		c.gridwidth = 1;
		
		_goBack.setPreferredSize(new Dimension(100, 50));
		_goBack.addActionListener(this);
		
		_south.add(_goBack, c);
		
		add(_south, BorderLayout.SOUTH);
		
		_tableArea.setLayout(new BorderLayout());
		
		fillOverallArray();
		
		_table = new JTable(_overall, _overallColumnHeads);
		_table.setFillsViewportHeight(true);
		_table.setAutoCreateRowSorter(true);
		
		_scroll = new JScrollPane(_table);
		_tableArea.add(_scroll, BorderLayout.CENTER);
		
		add(_tableArea, BorderLayout.CENTER);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _goBack) {
			_GUI.remove(this);
			_previousScreen.setVisible(true);
			
		} else if (e.getSource() == _selectInfo) {
			_tableArea.remove(_scroll);
			remove(_tableArea);
			String selected = _selectInfo.getSelectedItem().toString();
			if (selected.equals("Overall")) {
				_table = new JTable(_overall, _overallColumnHeads);
			} else {
				fillLevelSpecificArray(selected);
				_table = new JTable(_specificLevel, _levelColumnHeads);
			}
			_table.setFillsViewportHeight(true);
			_table.setAutoCreateRowSorter(true);
			_scroll = new JScrollPane(_table);
			_tableArea.add(_scroll, BorderLayout.CENTER);
			add(_tableArea, BorderLayout.CENTER);
		}
		_GUI.revalidate();
		_GUI.repaint();
		
		
	}

	private void fillOverallArray() {
		String[] names = _data.getNamesOfLists();
		
		_overall = new Object[names.length][4];
		
		for (int i = 0; i < names.length; i++) {
			WordList var = _data.getList(names[i]);
			_overall[i][0] = names[i];
			_overall[i][1] = new Double(var.getOverallScore());
			_overall[i][2] = new Double(var.getPreviousScore());
			
			double progress = var.getProgression();
			String out = "";
			if (progress >= 0) {
				out = "+" + progress*100 + "%";
			} else {
				out = progress*100 + "%";
			}
			_overall[i][3] = out;
		}
	}
	
	private void fillLevelSpecificArray(String levelName) {
		ArrayList<Word> list = _data.getList(levelName).returnCopyOfList();
		_specificLevel = new Object[list.size()][4];
		
		int i = 0;
		
		for (Word var : list) {
			for (int j = 0; j < 4; j++) {
				_specificLevel[i][j] = var.getValueAt(j);
			}	
			i++;
		}
	}
	
}
