package program.classes;

import java.util.ArrayList;
import java.util.List;

public class Association {
	private List<AssociationColumn> _columns;
	private String _finalSolution;
	
	public Association() {
		this._columns = new ArrayList<>();
		this._finalSolution = "";
	}
	
	public Association(List<AssociationColumn> columns,String finalSolution) {
		this._columns = new ArrayList<>(columns);
		this._finalSolution = "";
	}
	
	public void setColumns(List<AssociationColumn> columns) {
		this._columns = columns;
	}
	
	public void addColumn(AssociationColumn column) {
		this._columns.add(column);
	}
	
	public void removeColumn(AssociationColumn column) {
		this._columns.remove(column);
	}
	
	public void setFinalSolution(String finalSolution) {
		this._finalSolution = finalSolution;
	}
	
	public List<AssociationColumn> getColumns() {
		return this._columns;
	}
	public String getFinalSolution() {
		return this._finalSolution;
	}
	
}
