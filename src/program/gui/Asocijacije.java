package program.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import program.classes.Association;
import program.classes.AssociationColumn;
import program.classes.Player;
import program.util.JsonUtil;
import program.util.Utilities;

public class Asocijacije extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AsocijacijeComponent associationsDrawContent;
	private JFrame parentWindow;
	private JFrame window;
	private Player player;
	private ImageIcon buttonBackground;
	private ImageIcon buttonPressedBackground;
	private ImageIcon buttonCorrectBackground;
	private ImageIcon buttonMissedBackground;
	private List<Association> associations;
	private List<JButton>columnTermButtons;
	private BufferedImage frameBackground;
	private List<JTextField> columnSolutionFields;
	private boolean gameOver;
	private boolean termOpened;
	private JTextField solutionField;
	private Association currentAssociation;
	private JButton back;
	private JButton surrender;
	private boolean finalScoreGiven;
    
	public Asocijacije(JFrame parent) {
		window = new JFrame("Asocijacije");
		setBackground( Color.LIGHT_GRAY );
	    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    setName("Asocijacije-Game");
	    parentWindow = parent;
	    
	    buttonBackground = new ImageIcon("resources/images/button.png");
	    Image buttonBackgroundContent = buttonBackground.getImage();
		Image resizedButtonBackgroundContent = buttonBackgroundContent.getScaledInstance(100, 25,  java.awt.Image.SCALE_SMOOTH);  
		buttonBackground.setImage(resizedButtonBackgroundContent);

		buttonPressedBackground = new ImageIcon("resources/images/button-pressed.png");
	    Image buttonPressedBackgroundContent = buttonPressedBackground.getImage();
		Image resizedPressedButtonBackgroundContent = buttonPressedBackgroundContent.getScaledInstance(100, 25,  java.awt.Image.SCALE_SMOOTH);  
		buttonPressedBackground.setImage(resizedPressedButtonBackgroundContent);

		buttonCorrectBackground = new ImageIcon("resources/images/button-correct.png");
	    Image buttonCorrectBackgroundContent = buttonCorrectBackground.getImage();
		Image resizedCorrectButtonBackgroundContent = buttonCorrectBackgroundContent.getScaledInstance(100, 25,  java.awt.Image.SCALE_SMOOTH);  
		buttonCorrectBackground.setImage(resizedCorrectButtonBackgroundContent);
		
		buttonMissedBackground = new ImageIcon("resources/images/button-missed.png");
	    Image buttonMissedBackgroundContent = buttonMissedBackground.getImage();
		Image resizedMissedButtonBackgroundContent = buttonMissedBackgroundContent.getScaledInstance(100, 25,  java.awt.Image.SCALE_SMOOTH);  
		buttonMissedBackground.setImage(resizedMissedButtonBackgroundContent);
		
		termOpened = false;
		player = ((IgreComponent)parent.getContentPane()).getPlayer();
		player.setPlayerAssociationScore(0);
		
	    try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/asocijacije-bg.jpg"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
	    try {
			window.setIconImage(new ImageIcon("resources/images/asocijacije.png").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
	    
	    associationsDrawContent = new AsocijacijeComponent(frameBackground,player);
	    associationsDrawContent.setLayout(null);
	    
	    associations = new JsonUtil().readAssociations();
		
	    columnTermButtons = new ArrayList<>();
	    columnSolutionFields = new ArrayList<>();
	    int number = 1;
	    int letterCounter = 1;
	    String letter = "A";
	    int buttonCounter = 0;
	    int fieldCounter = 0;
	    gameOver =  false;
	    
	    int randomIndex = new Utilities().getRandomInteger(0, associations.size());
	    currentAssociation = associations.get(randomIndex);
	    
	    for(AssociationColumn a : currentAssociation.getColumns()) {
	    	for(String term : a.getTerms()) {
	    		JButton termButton = new JButton(buttonBackground);
	    		termButton.setVerticalTextPosition(SwingConstants.CENTER);
	    		termButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    		termButton.setText(letter+number);
	    		termButton.setName(term+"<>"+buttonCounter+"<>"+letter);
	    	    termButton.setBorder(BorderFactory.createLineBorder(new Color(25,25,112)));
	    		termButton.setForeground(new Color(240,248,255));
	    		termButton.setPressedIcon(buttonPressedBackground);
	    		termButton.setDisabledIcon(buttonPressedBackground);
	    		termButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						// TODO Auto-generated method stub
						changeButtonText(event);
					}});
	    		if(number == 4) {
	    			number = 1;
	    			letterCounter ++;
	    		}
	    		else {
	    			number++;	    			
	    		}
	    		letter = new Utilities().getLetter(letterCounter);
	    	    columnTermButtons.add(termButton);
	    	    buttonCounter++;
	    	}
	    	JTextField field = new JTextField();
	    	field.setName(a.getSolution() +"<>"+ fieldCounter);
	    	field.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent event) {
					// TODO Auto-generated method stub			
					if(termOpened) {
						checkColumn(event);
					}
					else {
						if(event.getKeyCode() == KeyEvent.VK_ENTER){
							JOptionPane.showMessageDialog(null, "Please open at least one term for this column to proceed!","Operation not allowed", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}});
    	    

    	    columnSolutionFields.add(field);
    	    fieldCounter ++;

    	    solutionField = new JTextField();
    	    solutionField.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent event) {
					// TODO Auto-generated method stub
					if(termOpened) {
						checkAssociation(event);
					}
					else {
						if(event.getKeyCode() == KeyEvent.VK_ENTER){
							JOptionPane.showMessageDialog(null, "Please open at least one term for this column to proceed!","Operation not allowed", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}});
	    }
	    
	    int j = 0, k = 0;
	    for(int i = 0; i< columnTermButtons.size(); i++) {
	    	if(i%4 == 0) {
	    		if(i != 0) {
	    			k++;
	    		}
	    		j = 0;
	    		
	    	}
	    	else {
	    		j++;
	    	}
	    	JButton termButton = columnTermButtons.get(i);
    	    termButton.setContentAreaFilled(false);
    	    termButton.setFocusPainted(false);
    	    termButton.setBounds(90+(k*100) - k, 150+(j*25) -j, 100, 25);
    	    termButton.setFont(new Font("Arial", Font.PLAIN, 8));
    	    
    	    associationsDrawContent.add(termButton);

    	    JTextField columnSolutionField = columnSolutionFields.get(k);
    	    columnSolutionField.setBounds(90+(k*100) - k, 174+(j*25) -j, 100, 25);
    	    columnSolutionField.setBorder(BorderFactory.createLineBorder(new Color(25,25,112)));
    	    columnSolutionField.setBackground(new Color(207,232,255));
    	    columnSolutionField.setForeground(new Color(30,144,255));
    	    columnSolutionField.setHorizontalAlignment(JTextField.CENTER);
    	    
    	    associationsDrawContent.add(columnSolutionField);
    	    
    	    if(i == columnTermButtons.size()-1) {
	    	    solutionField.setBounds(90, 198+(j*25) -j, 397, 25);
	    	    solutionField.setHorizontalAlignment(JTextField.CENTER);
	    	    solutionField.setBorder(BorderFactory.createLineBorder(new Color(25,25,112)));
	    	    solutionField.setBackground(new Color(240,248,255));
	    	    solutionField.setForeground(new Color(30,144,255));
	    	    solutionField.setName(currentAssociation.getFinalSolution());
	    	    
	    	    associationsDrawContent.add(solutionField);
    	    }
	    }
	    
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
	    associationsDrawContent.add(back);
	    
	    surrender = new JButton("End this game");
	    surrender.setBounds(200, 320, 180, 20);
	    surrender.setContentAreaFilled(false);
	    surrender.setBorder(null);
	    surrender.setName("surrender");
	    surrender.setForeground(new Color(30,144,255));
	    surrender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "Are you sure that you want to end this game?", "Confirm end", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
		    		endGame();
		    		associationsDrawContent.repaint();
					new JsonUtil(player).writeResults("associations");
		    		int result1 = JOptionPane.showConfirmDialog(null, "Your score is "+player.getPlayerAssociationsScore()+" points. Do you want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION);
		    		player.setPlayerAssociationScore(0);
					if(result1 == JOptionPane.YES_OPTION) {
						exitProcedure();
					}
				}
			}
	    	
	    });
	   
	    associationsDrawContent.add(surrender);
	    
	    associationsDrawContent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    window.setContentPane(associationsDrawContent);
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
	public List<JButton> getColumnTermButtons() {
		return this.columnTermButtons;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setAscojacijeScore(int score) {
		this.player.setPlayerAssociationScore(score);
	}
	
	public void openButtonTerm(JButton pressedButton) {
		String buttonTerm = pressedButton.getName().split("<>")[0];
		pressedButton.setText(buttonTerm.toUpperCase());
		pressedButton.setIcon(buttonPressedBackground);
		pressedButton.setForeground(Color.BLACK);
		pressedButton.setFocusable(false);
		pressedButton.setEnabled(false);
		UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));
	}
	
	public boolean isButtonOpen(JButton pressedButton) {
		if(pressedButton.getIcon().toString().equals(buttonPressedBackground.toString())){
			return true;
		}
		return false;
	}
	
	public void changeButtonText(ActionEvent event) {
		JButton pressedButton = (JButton)event.getSource();
		openButtonTerm(pressedButton);
		if(!termOpened) {
			termOpened = true;
		}
	}
	
	public void checkAssociation(KeyEvent e) {
		JTextField field = (JTextField)e.getSource();
		String solution = field.getName().split("<>")[0];
		int score = 0;
		int associationScore = player.getPlayerAssociationsScore();
		if(!finalScoreGiven) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(solution != null && field.getText() != null) {
					if(solution.equals(field.getText().trim().toLowerCase())){
						for(int i = 0; i < columnTermButtons.size(); i++) {
							if(!isButtonOpen(columnTermButtons.get(i))) {
								if(i % 4 == 0) {
									score += 10;
								}
								score += 5;
							}
							openButtonTerm(columnTermButtons.get(i));
							columnTermButtons.get(i).setPressedIcon(buttonCorrectBackground);
							columnTermButtons.get(i).setDisabledIcon(buttonCorrectBackground);
						}
						
						for(int i = 0; i < columnSolutionFields.size();i++) {
							columnSolutionFields.get(i).setText(currentAssociation.getColumns().get(i).getSolution().toUpperCase());
							columnSolutionFields.get(i).setBackground(new Color(144,238,144));
							columnSolutionFields.get(i).setForeground(new Color(0,0,0));
							columnSolutionFields.get(i).setEditable(false);
							columnSolutionFields.get(i).setFocusable(false);
						}
						field.setText(solution.toUpperCase());
						field.setForeground(new Color(255,255,255));
						field.setBackground(new Color(60,179,113));
						field.setEditable(false);
						field.setFocusable(false);
						associationsDrawContent.repaint();
				
						associationScore += score;
						gameOver = true;
						finalScoreGiven = true;
						player.setPlayerAssociationScore(associationScore);
						associationsDrawContent.setPlayer(player);
						this.associationsDrawContent.repaint();
						score += 10;
						new JsonUtil(player).writeResults("associations");
						
						JOptionPane.showMessageDialog(null, "Congratulations! Your score is " +associationScore +" points.", "Victory!", JOptionPane.INFORMATION_MESSAGE);
						exitProcedure();
					}
				}
			}
		}
	}
	
	public void endGame() {
		for(int i = 0; i < columnTermButtons.size(); i++) {
			if(!isButtonOpen(columnTermButtons.get(i))) {
				openButtonTerm(columnTermButtons.get(i));
				UIManager.put("Button.disabledText", new Color(204,0,0));
				columnTermButtons.get(i).setPressedIcon(buttonMissedBackground);
				columnTermButtons.get(i).setDisabledIcon(buttonMissedBackground);
			}
		}
		
		for(int i = 0; i < columnSolutionFields.size(); i++) {
			String solution = columnSolutionFields.get(i).getName().split("<>")[0];
			if(columnSolutionFields.get(i).getText().trim().length() == 0) {
				columnSolutionFields.get(i).setText(solution.toUpperCase());
				columnSolutionFields.get(i).setBackground(new Color(255,178,178));
				columnSolutionFields.get(i).setDisabledTextColor(new Color(204,0,0));
				columnSolutionFields.get(i).setFocusable(false);
				columnSolutionFields.get(i).setEnabled(false);
			}
		}
		
		String solution = solutionField.getName().split("<>")[0];
		solutionField.setText(solution.toUpperCase());
		solutionField.setBackground(new Color(255,178,178));
		solutionField.setDisabledTextColor(new Color(204,0,0));
		solutionField.setFocusable(false);
		solutionField.setEnabled(false);
	}
	
	public void checkColumn(KeyEvent e) {	
		JTextField field = (JTextField)e.getSource();
		String solution = field.getName().split("<>")[0];
		int associationScore = player.getPlayerAssociationsScore();
		int fieldNumber = Integer.parseInt(field.getName().split("<>")[1]);
		int score = 0;
		boolean columnAReady = false;
		boolean columnBReady = false;
		boolean columnCReady = false;
		boolean columnDReady = false;
		for(int i = fieldNumber*4; i < fieldNumber*4 + 4; i++) {
			if(isButtonOpen(columnTermButtons.get(i))) {
				if (fieldNumber == 0) {
					columnAReady = true;
					break;
				}
				else if(fieldNumber == 1) {
					columnBReady = true;
					break;
				}
				else if(fieldNumber == 2) {
					columnCReady = true;
					break;
				}
				else if(fieldNumber == 3) {
					columnDReady = true;
					break;
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(solution != null && field.getText() != null) {
				if((fieldNumber == 0 && !columnAReady) || (fieldNumber == 1 && !columnBReady) || (fieldNumber == 2 && !columnCReady) || (fieldNumber == 3 && !columnDReady)) {
					JOptionPane.showMessageDialog(null, "Please open at least one term for this column to proceed!","Operation not allowed", JOptionPane.INFORMATION_MESSAGE);
				}
				
				if(solution.equals(field.getText().trim().toLowerCase())){
					if((fieldNumber == 0 && columnAReady) || (fieldNumber == 1 && columnBReady) || (fieldNumber == 2 && columnCReady) || (fieldNumber == 3 && columnDReady)) {
							
						for(int i = fieldNumber*4; i < fieldNumber*4 + 4; i++) {
							if(!isButtonOpen(columnTermButtons.get(i))) {
								score += 2;
							}
							openButtonTerm(columnTermButtons.get(i));
							columnTermButtons.get(i).setPressedIcon(buttonCorrectBackground);
							columnTermButtons.get(i).setDisabledIcon(buttonCorrectBackground);
						}
						
						score += 3;
						field.setText(solution.toUpperCase());
						field.setBackground(new Color(144,238,144));
						field.setForeground(new Color(0,0,0));
						field.setEditable(false);
						field.setFocusable(false);
					}
					
					if(!gameOver) {
						associationScore += score;					
						player.setPlayerAssociationScore(associationScore);
						associationsDrawContent.setPlayer(player);
					}
					associationsDrawContent.repaint();
				}
			}
		}
	}
}
