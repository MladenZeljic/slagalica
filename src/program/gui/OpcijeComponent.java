package program.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

import program.util.Utilities;


public class OpcijeComponent extends JPanel {
			
			private static final long serialVersionUID = 1L;
			private Image image;
			private String title = "Game Info & Settings";
			private String text = " Here you can read \nabout game rules and\n change your name!";
			private JFrame mainWindow;
			
			public OpcijeComponent(JFrame main,Image image)
			{
		    	this.image = image;
		    	mainWindow = main;
		    }
			
		    @Override
		    protected void paintComponent(Graphics g)
		    {
		        super.paintComponent(g);
		        
		        Graphics2D g2 = (Graphics2D) g.create();
		        Font font = new Font ("Courier New", 1, 25);
		        g2.setFont (font);
		        FontMetrics fm = g2.getFontMetrics();
		        int x = (getWidth() - fm.stringWidth(title)) / 2;
		        int y = ((getHeight() + fm.getHeight()) / 2) + fm.getAscent();
		        
		        
		        if (image != null) {
		        	g2.drawImage(image, 0, 0, this.getWidth(), 200, null);
		        }
		        
		        g2.setColor(Color.WHITE);
		        g2.drawString (title, x, y-180); 
		        
		        Font f = new Font ("Courier New", 1, 15);
		        g2.setFont (f);
		        x = (getWidth() - fm.stringWidth(text)) / 2;
		        y = ((getHeight() + fm.getHeight()) / 2) + fm.getAscent();
		        new Utilities().drawMultilineString(g2, text, x+340, y-150);
	      }
		    
		public JFrame getMainWindow() {
			return this.mainWindow;
		}		
}
