package program.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import program.classes.Player;

public class KvizComponent extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Image image;
	private Player _player;
	private int _timeLeft;
		
	public KvizComponent(Image image, Player player) {
		this.image = image;
	    this._player = player;
	    this._timeLeft = 10;
	}
	
	public void setPlayer(Player player) {
		this._player = player;
	}
	
	public int getTime() {
		return this._timeLeft;
	}
	
	public void setTime(int timeLeft) {
		this._timeLeft = timeLeft;
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
	    g.drawString(this._player.getPlayerName()+": "+this._player.getPlayerQuizScore(), 100, 60);
	    g.drawString("Time: "+ this._timeLeft, 410, 60);
	}
}
