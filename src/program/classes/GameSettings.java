package program.classes;

public class GameSettings {
	private Player _player;
	private int _imageCount;
	private double _soundLevel;
	
	public GameSettings() {
		_player = new Player();
		_imageCount = 1;
		_soundLevel = 0.8;
	}
	public GameSettings(Player player) {
		_player = player;
		_imageCount = 1;
		_soundLevel = 0.8;
	}
	public void incrementImageCount() {
		if(_imageCount == 4) {
			_imageCount = 1;
		}
		else {
			_imageCount++;
		}
	}
	public int getImageCount() {
		return _imageCount;
	}
	public double getSoundLevel() {
		return this._soundLevel;
	}
	public void setSoundLevel(double soundLevel) {
		this._soundLevel = soundLevel;
	}
	public void setPlayer(Player player) {
		_player = player;
	}
	public Player getPlayer() {
		return this._player;
	}

}
