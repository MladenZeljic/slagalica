package program.gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import program.classes.Player;

public class AsocijacijeComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image image;
	private Player _player;
			
	public AsocijacijeComponent(Image image,Player player) {
		this.image = image;
	    this._player = player;
	}
		
	public void setPlayer(Player player) {
		this._player = player;
	}
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		    
		Font font = new Font ("Courier New", 1, 20);
	    g.setFont (font);
	    if (image != null) {
	    	g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	    }
	    g.setColor(new Color(30,144,255));
	    g.drawString(this._player.getPlayerName()+": "+this._player.getPlayerAssociationsScore(), 100, 60);
	}
}