package program.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import program.classes.Player;
import program.classes.Tile;
import program.util.JsonUtil;
import program.util.Utilities;

public class Mastermind extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame window;
	private BufferedImage frameBackground;
	private MastermindComponent mastermindDrawContent;
	private List<JButton> gameButtons;
	private List<JLabel> gameLabels;
	private List<JLabel> hitsLabels;
	private List<Tile> tiles;
	private List<Tile> choices;
	private JButton confirmButton;
	private JButton deleteButton;
	private int inPlaceHits = 0;
	private int outOfPlaceHits = 0;
	private int currentLabelIndexX = 1;
	private int currentLabelIndexY = 1;
	private int currentHitIndexX = 1;
	private ImageIcon inPlaceHit;
	private ImageIcon outOfPlaceHit;
	private Player player;
	private static int choicePlace = 1;
	private JFrame parentWindow;
	private JButton back;
	
	public Mastermind(JFrame parent) {
		window = new JFrame("Mastermind");
		parentWindow = parent;
		setBackground( Color.LIGHT_GRAY );
	    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    setName("Skocko-Game");
	    
	    inPlaceHit = new ImageIcon("resources/images/inPlaceHit.png");
		Image inPlaceHitIconContent = inPlaceHit.getImage();
		Image resizedInPlaceHitIconContent = inPlaceHitIconContent.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH);  
        inPlaceHit.setImage(resizedInPlaceHitIconContent);
		
		outOfPlaceHit = new ImageIcon("resources/images/outOfPlaceHit.png");
		Image outOfPlaceHitIconContent = outOfPlaceHit.getImage();
		Image resizedOutOfPlaceHitIconContent = outOfPlaceHitIconContent.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        outOfPlaceHit.setImage(resizedOutOfPlaceHitIconContent);
		
        try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/skocko-br.jpg"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
        try {
			window.setIconImage(new ImageIcon("resources/images/skocko.jpg").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
        
        player = ((IgreComponent)parent.getContentPane()).getPlayer();
        gameLabels = new ArrayList<>();
		hitsLabels = new ArrayList<>();
		mastermindDrawContent = new MastermindComponent(frameBackground);
		mastermindDrawContent.setLayout(null);
	    
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 4; j++) {
				JLabel gameLabel = new JLabel();
				gameLabel.setBounds(120+((j*40)-(j*2)), 80+((i*40) - (i*2)), 40, 40);
				gameLabel.setBorder(border);
				gameLabel.setName("Skocko"+(i+1)+""+(j+1));
				gameLabels.add(gameLabel);
				mastermindDrawContent.add(gameLabel);
			}
		}
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 4; j++) {
				JLabel hitLabel = new JLabel();
				hitLabel.setBounds(320+((j*40)-(j*2)), 80+((i*40) - (i*2)), 40, 40);
				hitLabel.setBorder(border);
				hitLabel.setName("Hit"+(i+1)+""+(j+1));
				hitsLabels.add(hitLabel);
				mastermindDrawContent.add(hitLabel);
			}
		}
		
		choices = new ArrayList<>();
		gameButtons = new ArrayList<>();
		Utilities util = new Utilities();
		List<Tile> buttonTiles = util.getMastermindTiles();
		for(int i = 0; i < buttonTiles.size(); i++) {
			JButton gameButton = new JButton(buttonTiles.get(i).getImage());
			gameButton.setName(buttonTiles.get(i).getType());
			gameButton.setBounds(185+((i*40)-(i*2)), 360, 40, 40);
			gameButton.setContentAreaFilled( false );
			gameButton.setBorder(null);
			gameButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					gameButtonClickAction(e);
				}
			});
	        gameButtons.add(gameButton);
	        mastermindDrawContent.add(gameButton);
		}
		

	    tiles = util.getRandomMastermind();
	    /*for(Tile t : tiles) {
	    	System.out.println(t.getType());
	    }*/
		
	    deleteButton = new JButton("Remove last");
	    deleteButton.setForeground(Color.BLACK);
	    deleteButton.setBounds(210, 400, 120, 40);
	    deleteButton.setContentAreaFilled( false );
	    deleteButton.setBorder(null);
	    deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				deleteLastChoiceAction();
			}
	    	
	    });
	    mastermindDrawContent.add(deleteButton);
	    
		confirmButton = new JButton("Enter");
		confirmButton.setForeground(Color.BLACK);
		confirmButton.setBounds(335, 400, 60, 40);
		confirmButton.setContentAreaFilled( false );
		confirmButton.setBorder(null);
		confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				confirmAction(e,tiles);
			}			
		});
		mastermindDrawContent.add(confirmButton);
		
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
	    mastermindDrawContent.add(back);
	    
	    mastermindDrawContent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    window.setContentPane(mastermindDrawContent);
	    window.setVisible(true);
		window.setSize(600, 500);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
	   
	}
	private void exitProcedure() {
		window.setVisible(false);
		parentWindow.setVisible(true);
		window.dispose();
	}
	public void setCurrentLabelIndexX(int labelIndexX) {
		currentLabelIndexX = labelIndexX;
	}
	public void setCurrentLabelIndexY(int labelIndexY) {
		currentLabelIndexY = labelIndexY;
	}
	public List<Tile> getChoices(){
		return this.choices;
	}

	public void deleteLastChoiceAction() {
		for(JLabel gameLabel : gameLabels) {
			if(gameLabel.getName()!= null && gameLabel.getName().equals("Skocko"+currentLabelIndexX+""+(currentLabelIndexY-1))) {
				if(choices.size() > 0) {
				gameLabel.setIcon(null);
				choices.remove(choices.size()-1);
				currentLabelIndexY --;
				choicePlace --;
				gameLabel.repaint();
				}
			}
		}
	}
	
	public void confirmAction(ActionEvent e, List<Tile> tiles) {
		
		int score = 0;
		if(choices.size() == 4) {
			if(currentLabelIndexX <= 9) {
				
				currentLabelIndexX ++;
				currentLabelIndexY = 1;
							
			}
			int currentRow = currentLabelIndexX - 1;
			if(currentRow <= 7) {
				if(inPlaceHits < 4) {
					
			        inPlaceHits = 0;
					outOfPlaceHits = 0;
					List<Tile> choiceList = new ArrayList<>(choices);
					List<Tile> tilesList = new ArrayList<>(tiles);
					Iterator <Tile> it1 = tilesList.iterator();
					Iterator <Tile> it2 = choiceList.iterator();
					
					
					while (it1.hasNext() && it2.hasNext()) {
						Tile tile = it1.next();
						Tile choice = it2.next();
						if(choice.getType().equals(tile.getType())) {
							if(choice.getPlace() == tile.getPlace()) {
								it1.remove();
								it2.remove(); 
								inPlaceHits ++;
							}
						}
					}
					
					it1 = tilesList.iterator();
					it2 = choiceList.iterator();
					
					while(it1.hasNext()) {
						Tile tile = it1.next();
						if(choiceList.contains(tile)) {
							choiceList.remove(tile);
							//System.out.println("----");
							it1.remove();
							outOfPlaceHits ++;
						}
					}

					for(int i = (currentHitIndexX-1)*4; i < (4*(currentHitIndexX-1))+inPlaceHits+outOfPlaceHits; i++) {
						hitsLabels.get(i).setIcon(inPlaceHit);
						
					}
					
					for(int i = (currentHitIndexX-1)*4 + inPlaceHits; i < (4*(currentHitIndexX-1))+ inPlaceHits + outOfPlaceHits; i++) {
						hitsLabels.get(i).setIcon(outOfPlaceHit);
					}
					currentHitIndexX++;
					
				}
				
				if(inPlaceHits == 4) {
					if(currentRow >= 1 && currentRow < 3) {
						score = 20;
					}
					else if(currentRow >= 3 && currentRow <= 6) {
						score = 10;
					}
					else {
						score = 5;
					}
					for(JButton button : gameButtons) {
						button.setEnabled(false);
						button.setDisabledIcon(button.getIcon());
						button.setFocusable(false);
						button.setFocusPainted(false);
					}
					confirmButton.setEnabled(false);
					confirmButton.setFocusable(false);
					confirmButton.setFocusPainted(false);
					
					deleteButton.setEnabled(false);
					deleteButton.setFocusable(false);
					deleteButton.setFocusPainted(false);
					
					UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));
				
					JOptionPane.showMessageDialog(null, "Congratulations! Your score is " +score +" points.", "Victory! :-) ", JOptionPane.INFORMATION_MESSAGE);

					exitProcedure();
				}
				
			}
			if(currentRow == 7){
				if(inPlaceHits < 4) {
					score = 0;
					String correctCombination = "";
					for(int i = 0; i < tiles.size(); i++) {
						Tile tile = tiles.get(i);
						correctCombination += tile.getType();
						if(i < tiles.size()-1) {
							correctCombination += ", ";
						}
					}
					for(JButton button : gameButtons) {
						button.setEnabled(false);
						button.setDisabledIcon(button.getIcon());
						button.setFocusable(false);
						button.setFocusPainted(false);
					}
					confirmButton.setEnabled(false);
					confirmButton.setFocusable(false);
					confirmButton.setFocusPainted(false);
					
					deleteButton.setEnabled(false);
					deleteButton.setFocusable(false);
					deleteButton.setFocusPainted(false);
					
					UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));
				
					JOptionPane.showMessageDialog(null, "Sorry, but you have lost this game. The correct combination was: "+correctCombination+". Your score is "+ score +" points.", "Defeat!  :-( ", JOptionPane.INFORMATION_MESSAGE);
					exitProcedure();
				}
				
			}

			player.setPlayerMastermindScore(score);
			new JsonUtil(player).writeResults("mastermind");
			player.setPlayerMastermindScore(0);
			
			choices.clear();
			choicePlace = 1;
		}
		else {
			JOptionPane.showMessageDialog(null, "Please enter a complete combination!","Invalid move", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	
	public void gameButtonClickAction(ActionEvent e) {
		JButton clickedButton = (JButton)e.getSource();
		for(JLabel gameLabel : gameLabels) {
			if(gameLabel.getName()!= null && gameLabel.getName().equals("Skocko"+currentLabelIndexX+""+currentLabelIndexY)) {
				gameLabel.setIcon(clickedButton.getIcon());
				
				Tile tile = new Tile();
				tile.setImage(((ImageIcon)clickedButton.getIcon()));
				tile.setType(clickedButton.getName());
				
				tile.setPlace(choicePlace);
				choicePlace++;
				
				choices.add(tile);
				
				if(currentLabelIndexY <= 4) {
					currentLabelIndexY ++;
				}
				break;
			}
		}
	}
}
