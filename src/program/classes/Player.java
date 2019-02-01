package program.classes;

public class Player {
	private String _playerName;
    private int _playerMastermindScore;
    private int _playerQuizScore;
    private int _playerAssociationsScore;
    
    public Player() {
    	this._playerName = "Player";
    }
    public Player(String playerName) {
    	this._playerName = playerName;
    }
    
    public void setPlayerName(String playerName) {
    	this._playerName = playerName;
    }
    
    public void setPlayerMastermindScore(int playerMastermindScore) {
    	this._playerMastermindScore = playerMastermindScore;
    	
    }
    public void setPlayerQuizScore(int playerQuizScore) {
    	this._playerQuizScore = playerQuizScore;
    }
    
    public void setPlayerAssociationScore(int playerAssociationsScore) {
    	this._playerAssociationsScore = playerAssociationsScore;
    }
    public String getPlayerName() {
    	return this._playerName;
    }
    public int getPlayerAssociationsScore() {
    	return this._playerAssociationsScore;
    }
    public int getPlayerMastermindScore() {
    	return this._playerMastermindScore;
    }
    public int getPlayerQuizScore() {
    	return this._playerQuizScore;
    }
}
