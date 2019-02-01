package program.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import program.classes.Answer;
import program.classes.Player;
import program.classes.Question;
import program.util.JsonUtil;
import program.util.Utilities;

public class Kviz extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame window;
	private List<Question> questions;
	private Question currentQuestion;
	private Player player;
	private Timer timer;
	private BufferedImage frameBackground;
	private ImageIcon buttonBackground;
	private ImageIcon buttonPressedBackground;
	private KvizComponent quizDrawContent;
	private List<JButton> answerButtons;
	private JTextArea questionLabel;
	private List<Answer>answers;
	private int timeLeft;
	private int numberOfQuestions;
	private JFrame parentWindow;
	private JButton back;
	
	public Kviz(JFrame parent) {
		window = new JFrame("Kviz");
		parentWindow = parent;
		setBackground( Color.LIGHT_GRAY );
	    setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    setName("Kviz-Game");
	    buttonBackground = new ImageIcon("resources/images/button.png");
	    Image buttonBackgroundContent = buttonBackground.getImage();
		Image resizedButtonBackgroundContent = buttonBackgroundContent.getScaledInstance(200, 55,  java.awt.Image.SCALE_SMOOTH);  
		buttonBackground.setImage(resizedButtonBackgroundContent);

		buttonPressedBackground = new ImageIcon("resources/images/button-pressed.png");
	    Image buttonPressedBackgroundContent = buttonPressedBackground.getImage();
		Image resizedPressedButtonBackgroundContent = buttonPressedBackgroundContent.getScaledInstance(200, 55,  java.awt.Image.SCALE_SMOOTH);  
		buttonPressedBackground.setImage(resizedPressedButtonBackgroundContent);
    	
		timeLeft = 10;
		numberOfQuestions = 5;
    	
	    player = ((IgreComponent)parent.getContentPane()).getPlayer();
	    try {
			frameBackground = ImageIO.read(new File("resources/images/backgrounds/kviz-br.jpg"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Background could not be set! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
	    
	    try {
			window.setIconImage(new ImageIcon("resources/images/kviz.png").getImage());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Icon could not be found! ", "Error! ", JOptionPane.ERROR_MESSAGE);
		}
	    
	    
	    
	    quizDrawContent = new KvizComponent(frameBackground,player);
	    quizDrawContent.setLayout(null);
	    
	    timer = new Timer(1000,new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    		countTimeout(event);
	    	}
	    });
	    timer.start();
	    answerButtons = new ArrayList<>();
	    questions = new ArrayList<>();
	    List<Question> questionList = new JsonUtil().readQuestions();
	    for(int i = 0; i < numberOfQuestions; i++) {
	    	int randomIndex = new Utilities().getRandomInteger(0, questionList.size());
	    	Question q = questionList.get(randomIndex);
	    	questions.add(q);
	    	questionList.remove(q);
	    }
	    Collections.shuffle(questions);
	    
	    initQuiz();	    
	    
	    window.setContentPane(quizDrawContent);
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
	
	public void checkAnswer(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		int score = player.getPlayerQuizScore();
		if(this.questions.size() > 0) {
			if(Boolean.parseBoolean(button.getName().split("<>")[1])) {
				score += 10;
			}
			else {
				score += -5;
			}
	
			player.setPlayerQuizScore(score);
			this.timeLeft = 10;
			this.quizDrawContent.setTime(timeLeft);
			this.quizDrawContent.setPlayer(player);
			this.quizDrawContent.repaint();
		}
	}
	public void replaceQuestion(Question oldQuestion) {
		this.answers.clear();
		this.questions.remove(oldQuestion);
		
		if(this.questions.size() > 0) {
			Utilities util = new Utilities();
			int questionIndex = util.getRandomInteger(0, questions.size());
			currentQuestion = questions.get(questionIndex);
			questionLabel.setText(currentQuestion.getTitle());
			answers = currentQuestion.getAnswers();
		    for(int i = 0; i < answerButtons.size(); i++) {
		    	JButton answerButton = answerButtons.get(i);
		    	answerButton.setIcon(buttonBackground);
		    	answerButton.setText(answers.get(i).getTitle());
		    	answerButton.setName(answers.get(i).getTitle()+"<>"+answers.get(i).isTrue());
		    }
		}
	}
	
	public void initQuiz() {
		Utilities util = new Utilities();
		int questionIndex = util.getRandomInteger(0, questions.size());
		currentQuestion = questions.get(questionIndex);
		
	    questionLabel = new JTextArea(currentQuestion.getTitle(),2, 400);
	    questionLabel.setEditable(false);
	    questionLabel.setOpaque(false);
	    questionLabel.setFocusable(false);
	    questionLabel.setLineWrap(true);
	    questionLabel.setBounds(110, 140, 400, 80);
	    questionLabel.setForeground(new Color(30,144,255));
    	
	    quizDrawContent.add(questionLabel);
	    answers = currentQuestion.getAnswers();
	    int j = 0;
	    for(int i = 0; i < answers.size(); i++) {
	    	JButton answerButton = new JButton(buttonBackground);
	    	answerButton.setVerticalTextPosition(SwingConstants.CENTER);
	    	answerButton.setHorizontalTextPosition(SwingConstants.CENTER);
	    	answerButton.setText(answers.get(i).getTitle());
	    	answerButton.setName(answers.get(i).getTitle()+"<>"+answers.get(i).isTrue());
	    	answerButton.setForeground(new Color(240,248,255));
	    	answerButton.setPressedIcon(buttonPressedBackground);
	    	answerButton.setContentAreaFilled(false);
	    	answerButton.setFocusPainted(false);
	    	answerButton.setBorderPainted(false);
	    	if(i >= 0 && i < answers.size()/2) {
	    		j = i;
	    		answerButton.setBounds(110+((j*205)), 220, 200, 50);
	    	}
	    	else {
	    		j = i - 2;
	    		answerButton.setBounds(110+((j*205)), 279, 200, 50);
	    	}
	    	answerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					checkAnswer(e);
					replaceQuestion(currentQuestion);
				}
	    		
	    	});
	    	answerButtons.add(answerButton);
	    	quizDrawContent.add(answerButton);
	    	
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
				public void actionPerformed(ActionEvent event) {
					// TODO Auto-generated method stub
					timer.stop();
					exitProcedure();
				}
		    	
		    });
		    back.setFocusPainted(false);
		    quizDrawContent.add(back);

	    }
	    quizDrawContent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

	}
	
	public void countTimeout(ActionEvent event) {
		quizDrawContent.setTime(timeLeft);
		quizDrawContent.repaint();
		if(questions.size() >0) {
    		if(timeLeft > 0) {
    			timeLeft--;
    		}
    		else {
    			int score = player.getPlayerQuizScore();
    			score += 0;
    			player.setPlayerQuizScore(score);
    			replaceQuestion(currentQuestion);
    			timeLeft = 10;
    		}
		
		}
		else {
			JOptionPane.showMessageDialog(null, "Congratulations! Your score is " +player.getPlayerQuizScore() +" points.", "Game over! ", JOptionPane.INFORMATION_MESSAGE);
			new JsonUtil(player).writeResults("quiz");
			player.setPlayerQuizScore(0);
			((Timer)event.getSource()).stop();
			exitProcedure();
		}
	}
}
