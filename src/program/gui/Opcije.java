package program.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import program.classes.Player;

public class Opcije extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private Player playerObject;
	private JButton set;
	private JFrame mainWindow;
		
	 public Opcije(JFrame ancestor) {
		 mainWindow = ancestor;
		 playerObject = new Player();
		 this.setBounds(0,0, this.getWidth(), this.getHeight());
		 form();
	 }
	 
		
	public void form() {
		//name
		nameLabel = new JLabel("Player Name");
		nameLabel.setBounds(20,  45, 150,50);
		nameLabel.setFont(new Font("Courier New", 1, 15));
		this.add(nameLabel);
			
		nameTextField = new JTextField();
		nameTextField.setBounds(180, 58, 320, 60);
		nameTextField.setSize(350, 30);
		nameTextField.setText(((SlagalicaMainComponent)mainWindow.getContentPane()).getGameSettings().getPlayer().getPlayerName());
		this.add(nameTextField);
						
		// set button
		set = new JButton("Set");
		set.setBackground(Color.WHITE);
		set.setBounds(450,140,350,160);
		set.setSize(80,30);
	
		set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String player = "";
				if(nameTextField.getText() != null) {
					if(nameTextField.getText().trim().equals("")) {
						player = "Player";
					}
					else {
						player = nameTextField.getText();
					}
				}
				
				else {
					player = "Player";
				}
				playerObject.setPlayerName(player);
				(((SlagalicaMainComponent)mainWindow.getContentPane()).getGameSettings()).getPlayer().setPlayerName(player);
			    }
			}
		);
		this.add(set);
	}
}


