package program.gui;


import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import program.util.MusicPlayer;


public class SlagalicaMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private BufferedImage frameBackground;
	private JFrame window;
	private JButton sound;
	private JButton play;
	private JButton settings;
	private JButton score;
	private JButton exit;
	private SlagalicaMainComponent content;
	
	public SlagalicaMain() {
		
		window = new JFrame("Mini slagalica - Main menu");
	
		try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/main.png"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			window.setIconImage(new ImageIcon("resources/images/game.png").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		setBackground( Color.LIGHT_GRAY );
	    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    
	    content = new SlagalicaMainComponent(frameBackground);
	    content.setLayout(null);
    	
	    // sound icon
	    ImageIcon soundIcon = new ImageIcon("resources/images/VolumeNormal.png");
    	Image soundIconContent = soundIcon.getImage();  
        Image resizedSoundIconContent = soundIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        soundIcon.setImage(resizedSoundIconContent);
        
        sound = new JButton(soundIcon);
        //sound.addActionListener(new ButtonListener());
	    
        sound.setContentAreaFilled( false );
        sound.setBorder( null );
        sound.setBounds(30, 80, 40, 40);
        sound.setName("Sound");
        sound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volumeControl(e);
			}});
        content.add(sound);
	    
        // play icon
	    ImageIcon playIcon = new ImageIcon("resources/images/play.png");
    	Image playIconContent = playIcon.getImage();  
        Image resizedPlayIconContent = playIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        playIcon.setImage(resizedPlayIconContent);
        
        play = new JButton(playIcon);
	    //play.addActionListener(new ButtonListener());
	    
	    play.setContentAreaFilled( false );
	    play.setBorder( null );
	    play.setBounds(220, 170, 40, 40);
	    play.setName("Play");
	    play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Igre(window);
				window.setVisible(false);
				window.dispose();
			}});
	    
	    // score icon
	    ImageIcon scoreIcon = new ImageIcon("resources/images/score.png");
    	Image scoreIconContent = scoreIcon.getImage();  
        Image resizedScoreIconContent = scoreIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        scoreIcon.setImage(resizedScoreIconContent);
        
        score = new JButton(scoreIcon);
	    score.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Rezultati(window);
				window.setVisible(false);
				window.dispose();
			}});
	    
	    score.setContentAreaFilled( false );
	    score.setBorder( null );
	    score.setBounds(330, 170, 40, 40);
	    score.setName("Scores");
        
	    // settings
	    ImageIcon settingsIcon = new ImageIcon("resources/images/settings.png");
    	Image settingsIconContent = settingsIcon.getImage();  
        Image resizedSettingsIconContent = settingsIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        settingsIcon.setImage(resizedSettingsIconContent);
        
        settings = new JButton(settingsIcon);
	    settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new OpcijeMain(window);
				window.setVisible(false);
				window.dispose();
			}});
	    
	    settings.setContentAreaFilled( false );
	    settings.setBorder( null );
	    settings.setBounds(220, 280, 40, 40);
	    settings.setName("Settings");
	    
	    // exit
	    ImageIcon exitIcon = new ImageIcon("resources/images/quit.png");
    	Image exitIconContent = exitIcon.getImage();  
        Image resizedExitIconContent = exitIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        exitIcon.setImage(resizedExitIconContent);
        
	    exit = new JButton(exitIcon);
	    exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				window.setVisible(false);
				window.dispose();
		    	System.exit(0);
			}
	    	
	    });
	    exit.setContentAreaFilled( false );
	    exit.setBorder( null );
	    exit.setBounds(330, 280, 40, 40);
	    exit.setName("Exit");
	    
	    window.setSize(600, 500);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    
	    content.add(play);
	    content.add(score);
	    content.add(settings);
	    content.add(exit);
	    content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		   
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setContentPane(content);
	    window.setVisible(true);
	}
	
	public void volumeControl(ActionEvent e) {
		
		Object source = e.getSource();
        
    	JButton pressedButton = (JButton) source;
		
		String volumeImagePath = "resources/";
		
		content.getGameSettings().incrementImageCount();
		int imageCount = content.getGameSettings().getImageCount();
		if(imageCount == 1) {
			volumeImagePath += "images/VolumeNormal.png";
			content.getGameSettings().setSoundLevel(0.8);
			content.getMusicPlayer().setClipVolume(0.8);
			content.getMusicPlayer()._volume = MusicPlayer.Volume.MEDIUM;
		}
		else if(imageCount == 2) {
			volumeImagePath += "images/VolumeLow.png";
			content.getGameSettings().setSoundLevel(0.2);
			content.getMusicPlayer().setClipVolume(0.2);
			content.getMusicPlayer()._volume = MusicPlayer.Volume.LOW;
		}
		else if(imageCount == 3) {
			volumeImagePath += "images/VolumeMuted.png";
			content.getGameSettings().setSoundLevel(0);
			content.getMusicPlayer().mute();
		}
		else {
			content.getMusicPlayer().setClipVolume(1.5);
			content.getGameSettings().setSoundLevel(1.5);
			volumeImagePath += "images/VolumeHigh.png";
			content.getMusicPlayer()._volume = MusicPlayer.Volume.HIGH;
		}
		if(content.getMusicPlayer()._playback == MusicPlayer.Playback.PAUSED) {
			content.getMusicPlayer().play(true);
		}
			try {
				pressedButton.setIcon(new ImageIcon(ImageIO.read(new File(volumeImagePath)).getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH)));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
}