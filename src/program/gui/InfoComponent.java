package program.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import program.util.Utilities;


public class InfoComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	 public InfoComponent() {
		 this.setBounds(0,0, this.getWidth(), this.getHeight());
	 }
   
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Graphics2D g2 = (Graphics2D) g.create();
	     howToPlay(g2);
		 
	 }
	 
	 public void howToPlay(Graphics2D g2) {
		 Font font = new Font ("Courier New", 1, 25);
		 g2.setFont (font);
		 
		 String title = "How to play?";
		 FontMetrics fm = g2.getFontMetrics();
	     int x = (getWidth() - fm.stringWidth(title)) / 2;
	     int y = ((getHeight() + fm.getHeight()) / 2) + fm.getAscent();
	        
	     g2.drawString(title, x, y-250);
		    
		 font = new Font ("Courier New", 1, 15);
		 g2.setFont (font);
		 String howToPlay =   "This game consists of three separate games,called Mastermind, "
		 		          	+ "\nQuiz  and  Asociations.  While  playing the first  game,  the "
		 		          	+ "\nplayer  must guess one randomly choosen combination  of  four "
		 		          	+ "\nsymbols. If  that  guess is correct, the players  score  will "
		 		          	+ "\nbe increased by 20, 10 or 5 points, which solely  depends  on "
		 		          	+ "\nhis/hers  number  of  guess. Also note that you can guess one "
		 		          	+ "\ncombination  only  seven  times.  Afterwards the game will be "
		 		          	+ "\nover, and your mastermind score will be set to 0. The quiz is "
		 		          	+ "\nthe  game  where the player can answer up to 5  questions. If "
		    				+ "\nthat  answer  is  true,  the  player  will be awarded with 10 "
		    				+ "\npoints, or else with 5  negative  points. The  third  game is "
		    				+ "\ncalled  'Associations'  and  it can  be  played  by  guessing "
		    				+ "\nthe association terms for which the player  is  awarded  with "
		    				+ "\n10, 5, 3 or 2 points according  to his/hers guess. The player "
		    				+ "\nmay in the  process gain  some  additional points. The player "
		    				+ "\ncannot  make  a  guess if neither of the association terms is "
		    				+ "\nopened, and in order to do so,  the player must open at least "
		    				+ "\none  of  them.  Each result of these games is saved, and then "
		    				+ "\ndisplayed,   when  needed  in  Highscores  table,  which  the "
		    				+ "\nplayer can view or clear. Also, be warned.Your previous score "
		    				+ "\nmight be overwritten!";
			new Utilities().drawMultilineString(g2,howToPlay, 10, 105); 
			
			font = new Font("Arial",Font.ITALIC, 11);
			g2.setFont(font);
							
			String footer = "This  game  has  been  made  as  a  final  project  from  'Software  engineering'   course  studied  as   a  part   of "
					+ "\ncomputer  science  master  studies  at  University of Tuzla  (Faculty of Electrical Engineering)  and  attended  in "
				    + "\n2017/18 year. ";
					
			new Utilities().drawMultilineString(g2,footer,10,520);
			
			
			g2.drawString("Developed by: Mladen Zeljiæ", 10, 580);
		}
}
