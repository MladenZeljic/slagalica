package program.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import program.classes.Player;


public class IgreComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image image;
	private Player player;
	
	public IgreComponent(Image image,JFrame parent) {
    	this.image = image;
    	player = (((SlagalicaMainComponent)parent.getContentPane()).getGameSettings()).getPlayer();
    }
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	    Font font = new Font ("Courier New", 1, 25);
	    g.setFont (font);
	    
	    if (image != null) {
	    	g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	    }
	    g.setColor(Color.WHITE);
	    String headline = "Please, choose one game to play";
	    FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(headline)) / 2;
        //int y = ((getHeight() + fm.getHeight()) / 2) + fm.getAscent();
        
	    g.drawString(headline, x, 120);
        font = new Font ("Courier New", 1, 17);
        g.setFont (font);
	    g.drawString("Mastermind", 120, 290);
	    g.drawString("Quiz", 285, 290);
	    g.drawString("Associations", 370, 290);
	}

	public Player getPlayer() {
		return this.player;
	}
}
