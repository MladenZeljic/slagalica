package program.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import program.util.JsonUtil;

public class Rezultati extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JFrame parentWindow;
	private BufferedImage frameBackground;
	private JFrame window;
	private RezultatiComponent content;
	private JsonUtil jsonClass;
	private JSONArray highScores;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton backBtn;
	private JButton clearScores;
	private JTable table;
	
	public Rezultati(JFrame parent)
	{
		this.parentWindow = parent;
    	parentWindow.setVisible(false);
    	jsonClass = new JsonUtil();
    	highScores = jsonClass.readResults();
    	
    	window = new JFrame("Mini slagalica - High scores");
		try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/scores.jpg"));
			frameBackground = toBufferedImage(frameBackground.getScaledInstance(595, 200,  java.awt.Image.SCALE_SMOOTH));
		
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			window.setIconImage(new ImageIcon("resources/images/score.png").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
		
		setBackground( Color.LIGHT_GRAY );
		
		window.setSize(600, 500);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    
	    content = new RezultatiComponent(parentWindow, frameBackground);
        Color backgroundColor = Color.decode("#02A14D");
        content.setBackground(backgroundColor);
        this.back(content);
        this.clear(content);
    	this.addScoreTable();	    	
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	window.setVisible(true);

	    content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	    window.setContentPane(content);
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
	
    public void addScoreTable() 
    {
    	DefaultTableModel model = new DefaultTableModel();
    	DefaultTableCellRenderer dtcr= new DefaultTableCellRenderer();
    	
    	table = new JTable(model);
    	model.addColumn("Rank");
    	model.addColumn("Player name");
    	model.addColumn("Mastermind");
    	model.addColumn("Quiz");
    	model.addColumn("Associations");
    	model.addColumn("Total score");
    	
    	addTableData(model);    	
    	
    	// centering columns text and set row count 
    	dtcr.setHorizontalAlignment(SwingConstants.CENTER);  
    	table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
    	table.getColumnModel().getColumn(1).setCellRenderer(dtcr);
    	table.getColumnModel().getColumn(2).setCellRenderer(dtcr);
    	table.getColumnModel().getColumn(3).setCellRenderer(dtcr);
    	table.getColumnModel().getColumn(4).setCellRenderer(dtcr);
    	table.getColumnModel().getColumn(5).setCellRenderer(dtcr);
    	
    	model.setRowCount(5);
    	table.setRowHeight(50);
    
    	content.setLayout(null);
    	panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    	Color backgroundColor = Color.decode("#02A14D");
    	panel.setBackground(backgroundColor);
    	panel.setBounds(2,200,590,269);
    	
    	scrollPane = new JScrollPane(table);
    	scrollPane.setBounds(0,0,590,269);
    	scrollPane.setPreferredSize(new Dimension(595,275));
    	
    	panel.add(scrollPane);
    	content.add(panel);
    }
    
    public void addTableData(DefaultTableModel model)
    {
    	for(int i=0; i < this.highScores.size(); i++) {
    		JSONObject item =  (JSONObject) this.highScores.get(i);
    		String rank = i+1+".";
    		String playerName = (String) item.get("player");
    		JSONArray scores = (JSONArray) item.get("scores");
    		String mastermindScore = ((JSONObject)(scores.get(0))).get("mastermind").toString();
    		String quizScore = ((JSONObject)(scores.get(1))).get("quiz").toString();
    		String associationsScore = ((JSONObject)(scores.get(2))).get("associations").toString();
    		String totalScore = ((JSONObject)(scores.get(3))).get("total").toString();
    		model.addRow(new Object[] {rank, playerName, mastermindScore,quizScore,associationsScore,totalScore});
    	}
    }
    
    public void clear(RezultatiComponent content) 
	{
	    ImageIcon backIcon = new ImageIcon("resources/images/clear.png");
		Image backIconContent = backIcon.getImage();  
	    Image resizedBackIconContent = backIconContent.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
	    backIcon.setImage(resizedBackIconContent);
	    
	    clearScores = new JButton(backIcon);
	    clearScores.setBounds(560, 30, 20, 20);
	    clearScores.setContentAreaFilled(false);
	    clearScores.setBorder(null);
	    Rezultati panel = this;
		
	    clearScores.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		int result = JOptionPane.showConfirmDialog(null, "Are you sure that you want to clear your scores?", "Confirm clear", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
		    		new JsonUtil().clearScores();
			    	highScores = jsonClass.readResults();
			    	DefaultTableModel model = (DefaultTableModel) table.getModel();
			    	model.setRowCount(0);
			    	model.setRowCount(5);
					panel.repaint();
				}
			}
	    	
	    });
	    clearScores.setFocusPainted(false);
	    content.add(clearScores);
    }
    
    public void back(RezultatiComponent content) 
	{
	    ImageIcon backIcon = new ImageIcon("resources/images/back.png");
		Image backIconContent = backIcon.getImage();  
	    Image resizedBackIconContent = backIconContent.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);  
	    backIcon.setImage(resizedBackIconContent);
	    
	    backBtn = new JButton(backIcon);
	    backBtn.setBounds(20, 30, 20, 20);
	    backBtn.setContentAreaFilled(false);
	    backBtn.setBorder(null);
	    backBtn.setName("BackScores");
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
}
