package voxspell.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

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

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * This class creates a new JFrame and plays the video or audio file with the required filter 
 * as determined by the reward number
 * 
 * @author mvan439
 *
 */
@SuppressWarnings("serial")
public class Video extends GUIElement {
	private JFrame _videoFrame = new JFrame("Video player");

	private JButton _pauseBtn;
	private JButton _muteBtn;

	private EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private String _filename;
	private String _audioname;
	private String _filter2Name;
	private String _filter3Name;
	private String _filter4Name;
	private String _cmd = "";

	/**
	 * Constructor - starts the JFrame
	 * @param filter
	 */
	public Video(int filter) {
		super();
		File f = new File("res");
		_filename = f.getAbsolutePath() + "/big_buck_bunny_1_minute.avi";
		_audioname = f.getAbsolutePath() + "/Broke_For_Free_-_01_-_Night_Owl.mp3";
		_filter2Name = f.getAbsolutePath() + "/negativeVideo.avi";
		_filter3Name = f.getAbsolutePath() + "/reverseAudio.mp3";
		_filter4Name = f.getAbsolutePath() + "/shadedVideo.avi";

		NativeLibrary.addSearchPath(
				RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
			);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowVideo(filter);
			}
		});


	}

	/**
	 * Method that initialises everything, adds the required content to the JFrame and starts the video or audio
	 * @param filter
	 */
	private void createAndShowVideo(int filter){
		
		if (filter == 2 && !(new File(_filter2Name)).exists()) {
			_cmd = "ffmpeg -i " + _filename + " -vf lutrgb=\"r=negval:g=negval:b=negval\" " + _filter2Name;
		} else if (filter == 4 && !(new File(_filter4Name)).exists()) {
			_cmd = "ffmpeg -i " + _filename + " -vf lutrgb=\"r=val+100\" " + _filter4Name;
		} else if (filter == 3 && !(new File(_filter3Name)).exists()) {
			_cmd = "ffmpeg -i " + _audioname + " -af atempo=2 " + _filter3Name;
		}
		
		BackGroundTask bgTask = new BackGroundTask();
		bgTask.execute();

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent(){
			public void finished(MediaPlayer mediaPlayer){ }
		};


		final EmbeddedMediaPlayer video = mediaPlayerComponent.getMediaPlayer();
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(204, 255, 204));
		panel.add(mediaPlayerComponent, BorderLayout.CENTER);

		_videoFrame.setContentPane(panel);


		//create buttons and buttonpanel, adding actionlisteners
		JPanel btnPanel = new JPanel(new FlowLayout());
		btnPanel.setBackground(new Color(204, 255, 204));

		final ImageIcon mute = new ImageIcon("res/SpeakerIcon.png");
		final ImageIcon muted = new ImageIcon("res/muted.png");
		_muteBtn = new JButton(mute);
		_muteBtn.setPreferredSize(new Dimension(50,50));
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
		_pauseBtn.setPreferredSize(new Dimension(50,50));
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

		btnPanel.add(_pauseBtn);
		btnPanel.add(_muteBtn);

		//creating and adding the utilityPanel, which contains all the information about the video including the buttons
		JLabel amountToSee = new JLabel();

		final JLabel labelTime = new JLabel("0 seconds", SwingConstants.RIGHT);

		JPanel utilityPanel = new JPanel(new BorderLayout());
		utilityPanel.add(labelTime, BorderLayout.NORTH);
		utilityPanel.add(amountToSee, BorderLayout.EAST);
		utilityPanel.add(btnPanel, BorderLayout.CENTER);
		utilityPanel.setBackground(new Color(204, 255, 204));

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

		if (filter == 0) {
			video.playMedia(_filename);
		} else if (filter == 2) {
			while(!bgTask.isDone()){}
			video.playMedia(_filter2Name);
		} else if (filter == 4) {
			while(!bgTask.isDone()){}
			video.playMedia(_filter4Name);
		} else if (filter == 1) {
			video.playMedia(_audioname);
		} else if (filter == 3) {
			while(!bgTask.isDone()){}
			video.playMedia(_filter3Name);
		}

	}

	/**
	 * 
	 * This is an inner class that extends SwingWorker
	 * Purpose is to run the BASH command in the background
	 * 
	 * @author scoobster
	 *
	 */
	private class BackGroundTask extends SwingWorker<List<String>, String>{

		@Override
		protected List<String> doInBackground() throws Exception {
			try {
				
				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", _cmd);
				Process p = builder.start();
				p.waitFor();

			} catch(Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Not used - both buttons have their own action listeners
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
