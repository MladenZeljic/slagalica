package program.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class MastermindComponent extends JPanel{
		
	private static final long serialVersionUID = 1L;
	private Image image;
		
	public MastermindComponent(Image image) {
		this.image = image;
	    	
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
	}
}
