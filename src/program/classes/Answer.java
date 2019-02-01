package program.classes;

public class Answer {
	private String _title;
	private boolean _isTrue;
	
	public Answer() {
		this._title = "";
		this._isTrue = false;
	}
	
	public Answer(String title, boolean isTrue) {
		this._title = title;
		this._isTrue = isTrue;
	}
	
	public void setTitle(String title) {
		this._title = title;
	}
	
	public String getTitle() {
		return this._title;
	}
	
	public boolean isTrue() {
		return this._isTrue;
	}
	
	public void setIsTrue(boolean isTrue) {
		this._isTrue = isTrue;
	}
	
}
