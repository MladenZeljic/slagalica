package program.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class OpcijeMain extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JFrame parentWindow;
	private BufferedImage frameBackground;
	private JFrame window;
	private JTabbedPane tabs;
	private JButton backBtn;
	
	public OpcijeMain(JFrame parent){
		this.parentWindow = parent;
    	parentWindow.setVisible(false);
		
    	window = new JFrame("Mini slagalica - Settings");
		try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/options.jpg"));
			frameBackground = toBufferedImage(frameBackground.getScaledInstance(595, 200,  java.awt.Image.SCALE_SMOOTH));
		
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			window.setIconImage(new ImageIcon("resources/images/settings.png").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		setBackground( Color.LIGHT_GRAY );

	    
		window.setSize(600, 500);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    
	    OpcijeComponent content = new OpcijeComponent(parentWindow,frameBackground);
	    Color backgroundColor = Color.decode("#f1b15a");
	    content.setBackground(backgroundColor);
	    content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    
	    back(content);
	    addTabs(content);

    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	content.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
 	   

		window.setContentPane(content);
	    window.setVisible(true);
		
	}
	public JFrame getMyParent() {
		return this.parentWindow;
	}
	public void addTabs(OpcijeComponent content) 
	{
		content.setLayout(null);
		tabs = new JTabbedPane();
		tabs.setBounds(0, 20, this.getWidth(), this.getHeight());

		InfoComponent infoPanel = new InfoComponent();
		infoPanel.setBackground(Color.WHITE);
		
		Opcije opcije = new Opcije(parentWindow);
		opcije.setBackground(Color.WHITE);
		opcije.setLayout(null);
		JScrollPane scroll = new JScrollPane();
		infoPanel.setPreferredSize(new Dimension(595,600));
		scroll.setViewportView(infoPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension (200,100));
		tabs.add("Game Info", scroll);
		tabs.add("Player settings", opcije);
		tabs.setBounds(2, 179, 590, 290);
		
		content.add(tabs);
		
	}
	
	public void back(OpcijeComponent content) 
	{
	    ImageIcon backIcon = new ImageIcon("resources/images/back.png");
		Image backIconContent = backIcon.getImage();  
	    Image resizedBackIconContent = backIconContent.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
	    backIcon.setImage(resizedBackIconContent);
	    
	    backBtn = new JButton(backIcon);

	    backBtn.setBounds(20, 30, 20, 20);
	    backBtn.setContentAreaFilled(false);
	    backBtn.setBorder(null);
	    backBtn.setName("BackSettings");
	    backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				exitProcedure();
			}
	    	
	    });
	    backBtn.setFocusPainted(false);
	    content.add(backBtn);
    }
	
    public static BufferedImage toBufferedImage(Image img){
    	
    	if (img instanceof BufferedImage){
    		
    	        return (BufferedImage) img;
    	}

    	BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    	Graphics2D bGr = bimage.createGraphics();
    	bGr.drawImage(img, 0, 0, null);
    	bGr.dispose();

    	return bimage;
    	
    }
	
	public void exitProcedure() {
		window.setVisible(false);
		parentWindow.setVisible(true);
		window.dispose();
	}
	public void showWindow() {
		window.setVisible(true);
		
	}
	public void hideWindow() {
		window.setVisible(false);
		
	}
	
}
