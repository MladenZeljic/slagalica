package program.classes;

import java.util.ArrayList;
import java.util.List;

public class AssociationColumn {
	private List<String> _terms;
	private String _solution;
	
	public AssociationColumn() {
		this._terms = new ArrayList<>();
		this._solution = "";
	}
	
	public AssociationColumn(List<String> terms, String solution) {
		this._terms = new ArrayList<>(terms);
		this._solution = solution;
	}
	
	public void setTerms(List<String> terms) {
		this._terms = terms;
	}
	
	public void addTerm(String term) {
		this._terms.add(term);
	}
	
	public void removeTerm(String term) {
		this._terms.remove(term);
	}
	
	public void setSolution(String solution) {
		this._solution = solution;
	}
	
	public List<String> getTerms() {
		return this._terms;
	}
		
	public String getSolution() {
		return this._solution;
	}
}
