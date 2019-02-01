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


public class Igre extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage frameBackground;
	private JFrame window;
	private JButton mastermind;
	private JButton kviz;
	private JButton asocijacije;
	private JButton back;
	private IgreComponent content;
	private JFrame parentWindow;
	
	public Igre(JFrame parent) {
		window = new JFrame("Mini slagalica - Game selection");
		
		try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/menu.png"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			window.setIconImage(new ImageIcon("resources/images/play.png").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		parentWindow = parent;
		parentWindow.setVisible(false);
		
		setBackground( Color.LIGHT_GRAY );
	    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    
	    content = new IgreComponent(frameBackground,parent);
	    content.setLayout(null);
	   
	    window.setSize(600, 500);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    
	    // mastermind icon
	    ImageIcon mastermindIcon = new ImageIcon("resources/images/skocko.jpg");
    	Image mastermindIconContent = mastermindIcon.getImage();  
        Image resizedMastermindIconContent = mastermindIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        mastermindIcon.setImage(resizedMastermindIconContent);
        
	    
	    mastermind = new JButton(mastermindIcon);
	    
	    mastermind.setContentAreaFilled( false );
	    mastermind.setBorder( null );
	    mastermind.setBounds(150, 220, 40, 40);
	    mastermind.setName("Mastermind");
	    mastermind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Mastermind(window);
				window.setVisible(false);
				window.dispose();
			}});
	    
	    content.add(mastermind);
	    
	    // quiz icon
	    ImageIcon quizIcon = new ImageIcon("resources/images/kviz.png");
    	Image quizIconContent = quizIcon.getImage();  
        Image resizedQuizIconContent = quizIconContent.getScaledInstance(50, 40,  java.awt.Image.SCALE_SMOOTH);  
        quizIcon.setImage(resizedQuizIconContent);
        
	    
	    kviz = new JButton(quizIcon);
	    
	    kviz.setContentAreaFilled( false );
	    kviz.setBorder( null );
	    kviz.setBounds(280, 220, 50, 40);
	    kviz.setName("Kviz");
	    kviz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Kviz(window);
				window.setVisible(false);
				window.dispose();
			}});
	    
	    content.add(kviz);
	    
	    // association icon
	    ImageIcon associationIcon = new ImageIcon("resources/images/asocijacije.png");
    	Image associationIconContent = associationIcon.getImage();  
        Image resizedAssociationIconContent = associationIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        associationIcon.setImage(resizedAssociationIconContent);
        
	    
	    asocijacije = new JButton(associationIcon);
	    
	    asocijacije.setContentAreaFilled( false );
	    asocijacije.setBorder( null );
	    asocijacije.setBounds(410, 220, 40, 40);
	    asocijacije.setName("Asocijacije");
	    asocijacije.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Asocijacije(window);
				window.setVisible(false);
				window.dispose();
			}});
	    
	    ImageIcon backIcon = new ImageIcon("resources/images/back.png");
		Image backIconContent = backIcon.getImage();  
	    Image resizedBackIconContent = backIconContent.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
	    backIcon.setImage(resizedBackIconContent);
	    
	    back = new JButton(backIcon);
	    back.setBounds(20, 30, 20, 20);
	    back.setContentAreaFilled(false);
	    back.setBorder(null);
	    back.setName("backGames");
	    back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				exitProcedure();
			}
	    	
	    });
	    back.setFocusPainted(false);
	    content.add(back);
	    
	    content.add(asocijacije);
	    content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setContentPane(content);
	    window.setVisible(true);
	    
	}
	private void exitProcedure() {
		window.setVisible(false);
		parentWindow.setVisible(true);
		window.dispose();
	}
	
}
