package OldCode.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import GUI.Data.DataStore;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * This class creates a new JFrame and plays the amount of video the user is allowed to see, 
 * depending on current level. 
 * 
 * @author mvan439
 *
 */
@SuppressWarnings("serial")
public class Video extends GUIElement {
	private JFrame _videoFrame = new JFrame("Video player");
	
	private JButton _pauseBtn;
	private JButton _muteBtn;
	private JButton _continueBtn;
	
	private EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
	private String _filename = "res/big_buck_bunny_1_minute.avi";
	
	private double _videoLength;
	private double _units;
	private double _currentLevel;
	private double _stopTime;
	private boolean _isLastLevel;
	
	public Video(JFrame frame, DataStore data, boolean isLastLevel) {
		super(frame, data);
		_isLastLevel = isLastLevel;
		
		NativeLibrary.addSearchPath(
	            RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
	        );
	        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	        
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	createAndShowVideo();
	            }
	        });
	        

	}
	
	private void createAndShowVideo(){
    
 


    mediaPlayerComponent = new EmbeddedMediaPlayerComponent(){
    	public void finished(MediaPlayer mediaPlayer){
    	}
    };


    final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(mediaPlayerComponent, BorderLayout.CENTER);
    
    _videoFrame.setContentPane(panel);
    
    
    //create buttons and buttonpanel, adding actionlisteners
    JPanel btnPanel = new JPanel(new FlowLayout());
    
    final ImageIcon mute = new ImageIcon("res/SpeakerIcon.png");
    final ImageIcon muted = new ImageIcon("res/muted.png");
    _muteBtn = new JButton(mute);
    _muteBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			video.mute();
			if(_muteBtn.getIcon().equals(mute)){
				_muteBtn.setIcon(muted);
			} else {
				_muteBtn.setIcon(mute);
			}
		}
	});
    
    final ImageIcon pause = new ImageIcon("res/pause.jpg");
    final ImageIcon play = new ImageIcon("res/play.jpg");
    _pauseBtn = new JButton(pause);
    _pauseBtn.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			video.pause();
			if(_pauseBtn.getIcon().equals(pause)){
				_pauseBtn.setIcon(play);
			} else {
				_pauseBtn.setIcon(pause);
			}
		}
    });
    
    _continueBtn = new JButton("Continue from where you left off");
    _continueBtn.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			//calculate time diference between now and the point in vid.
			long continueTime = (long) ((_currentLevel - 1) * (_videoLength / _units) * 1000);
			video.setTime(continueTime);
		}
    });

    btnPanel.add(_continueBtn);
    btnPanel.add(_pauseBtn);
    btnPanel.add(_muteBtn);
    

    //creating and adding the utilityPanel, which contains all the information about the video including the buttons
    JLabel amountToSee = new JLabel();
    
    final JLabel labelTime = new JLabel("0 seconds", SwingConstants.RIGHT);
    
    JPanel utilityPanel = new JPanel(new BorderLayout());
    utilityPanel.add(labelTime, BorderLayout.NORTH);
    utilityPanel.add(amountToSee, BorderLayout.EAST);
    utilityPanel.add(btnPanel, BorderLayout.CENTER);
    
    panel.add(utilityPanel, BorderLayout.SOUTH);

    Timer timer = new Timer(50, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			long time = (long)(video.getTime()/1000.0);
			labelTime.setText(String.valueOf(time));
		}
	});
    
    timer.start();
    
    //setting the videoFrame settings
    _videoFrame.setLocation(100, 100);
    _videoFrame.setSize(1050, 600);
    _videoFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    _videoFrame.setVisible(true);

    _videoFrame.addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent e) {
    		video.stop();
    		_videoFrame.dispose();
    	}
    });
    
    //logic to change the amount of video the user gets to see
    //BGTask and filereading to determine length of video
    BackGroundTask bgTask = new BackGroundTask();
    bgTask.execute();

    //read number from file
    File file = new File("length.txt");
    if(file.isFile()){
    	Scanner scanFile = null;
    	try {
    		scanFile = new Scanner(file);
    	} catch (FileNotFoundException e){
    		e.printStackTrace();
    	}
    	
    	if(scanFile.hasNextDouble()){
    		_videoLength = scanFile.nextDouble();
    	}
    }
    
    
//retrieve the current wordlist, and the amount of wordlists
    String[] names = _data.getNamesOfLists();
    String listTitle = _data.getCurrentLevelName();
    _currentLevel = 0;
    _units = names.length;

    //figuring out which one the user is up to
    for(int i = 0; i < _units; i++){
    	if(names[i].equals(listTitle)){
    		break;
    	}
    	_currentLevel++;
    }
    
    if (_isLastLevel) {
    	_currentLevel++;
    }
    //the amount of video in seconds available at this point in time
    double percentToPlay = (100 * _currentLevel)/(_units);
    if(percentToPlay > 100){
    	percentToPlay = 100;
    }
    
    double _stopTime = _videoLength * ( percentToPlay/100);
    
    amountToSee.setText("You can see: " + (int)percentToPlay + "%");
    
    /*
     * print outputs for testing purposes
    System.out.println(count);
    System.out.println(names.length);
    System.out.println(length);
    System.out.println(percentToPlay);
    System.out.println(stopTime);

*/
    video.playMedia(_filename, ":stop-time=" + _stopTime);
    
	}

	private class BackGroundTask extends SwingWorker<List<String>, String>{

		@Override
		protected List<String> doInBackground() throws Exception {
			try {
				String cmd = "ffprobe -i " + _filename + " -show_entries format=duration -v quiet -of csv=\"p=0\" >> length.txt";
				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);

				builder.start();

			} catch(Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
