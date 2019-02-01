package program.classes;

import java.util.ArrayList;
import java.util.List;

public class Question {
	String _title;
	List<Answer> _answers;
	
	public Question() {
		this._title = "";
		this._answers = new ArrayList<>();
	}
	public Question(String title) {
		this._title = title;
		this._answers = new ArrayList<>();
	}
	public Question(String title, List<Answer> answers) {
		this._title = title;
		this._answers = new ArrayList<>(answers);
	}
	public void setTitle(String title) {
		this._title = title;
	}
	public String getTitle() {
		return this._title;
	}
	public void addAnswer(Answer answer) {
		if(this._answers.size() < 4) {
			this._answers.add(answer);
		}
	}
	public void removeAnswer(Answer answer) {
		this._answers.remove(answer);
	}
	public List<Answer> getAnswers(){
		return this._answers;
	}
	public void addAnswers(List<Answer> answersList) {
		this._answers = new ArrayList<>(answersList);
		
	}
}
