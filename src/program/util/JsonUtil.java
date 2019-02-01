package program.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import program.classes.Answer;
import program.classes.Association;
import program.classes.AssociationColumn;
import program.classes.Player;
import program.classes.Question;

public class JsonUtil 
{
	private Player _player;
	
	public JsonUtil(Player player) {
		_player = player;
	}
	
	public JsonUtil() {}
	
	@SuppressWarnings("unchecked")
	public JSONArray readResults() {
		JSONParser parser = new JSONParser();
		try {
			BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream("resources/json/results.json"), StandardCharsets.UTF_8));
			 Object obj = parser.parse(fReader); 
			 if(obj != null) { 
				 JSONObject jsonObject = (JSONObject) obj;
				 JSONArray jsonArray = (JSONArray) jsonObject.get("result");
				 // sorting array by greater score
				 jsonArray.sort(new Comparator<JSONObject>() {
					 
					 public int compare(JSONObject a, JSONObject b){
						 
						 int valA=  Integer.parseInt(String.valueOf(((JSONObject)((JSONArray)a.get("scores")).get(3)).get("total")));
						 int valB = Integer.parseInt(String.valueOf(((JSONObject)((JSONArray)b.get("scores")).get(3)).get("total")));
						 return valB - valA;
					 }
				 });
				 fReader.close();
				 return jsonArray;
			 }
			 else 
				 fReader.close();
				 return new JSONArray();
		}
		catch (IOException | ParseException e) {
			e.printStackTrace();
        }
		return new JSONArray();
		
	}
	
	public List<Association>readAssociations(){
		List<Association> associations = new ArrayList<>();
		 
		JSONParser parser = new JSONParser();
		try {
			BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream("resources/json/associations.json"), "UTF-8"));
			
			 Object obj = parser.parse(fReader); 
			 if(obj != null) { 
				 JSONObject jsonObject = (JSONObject) obj;
				 JSONArray jsonArray = (JSONArray) jsonObject.get("associations");
				 JSONObject jsonAssociation = new JSONObject();
				 Collection <?> jsonAssociationValues = new ArrayList<>();
				 String finalSolution = "";
				 JSONArray jsonAssociationArray = new JSONArray();
				 JSONArray columns = new JSONArray();
				 JSONObject column = new JSONObject();
				 Collection<?> columnValues = new ArrayList<>();
				 JSONArray fields = new JSONArray();
				 Collection<?> fieldValues = new ArrayList<>();
				 int termNumber = 0;
				 
				 for(Object o1: jsonArray) {
					 Association association = new Association();
					 jsonAssociation = (JSONObject)o1;
					 jsonAssociationValues = jsonAssociation.values();
					 Iterator<?> ita = jsonAssociationValues.iterator();
					 
					 while(ita.hasNext()) {

						 jsonAssociationArray = (JSONArray)ita.next();
						 columns = (JSONArray)((JSONObject)jsonAssociationArray.get(0)).get("columns");
						 finalSolution = (String)((JSONObject)jsonAssociationArray.get(1)).get("finalSolution");
						 association.setFinalSolution(finalSolution);
						 for(Object o2: columns) {

							 AssociationColumn col = new AssociationColumn();
							 
							 column = (JSONObject)o2;
							 columnValues = column.values();
							 for(Object o3 : columnValues) {
								 fields = (JSONArray)o3;
								 for(Object o4 : fields) {
									 fieldValues = ((JSONObject)o4).values();
									 Iterator<?> itf = fieldValues.iterator();
									 while(itf.hasNext()) {
										 String term = (String)itf.next();
										 if(termNumber < 4) {
											 col.addTerm(term);
											 termNumber++;
										 }
										 else {
											 termNumber=0;
											 col.setSolution(term);
										 }
									 }
								 }
								 
							 }
							 Collections.shuffle(col.getTerms());
							 association.addColumn(col);

						 }

						 
					 }
					 Collections.shuffle(association.getColumns());
					 associations.add(association);
				 }
				 
			 }
		}catch (IOException | ParseException e) {
			e.printStackTrace();
        }
		
		return associations;
	}
	
	public List<Question> readQuestions() {
		JSONParser parser = new JSONParser();
		List<Question> questions = new ArrayList<>();
		List<Answer> answersList = new ArrayList<>();
		try {
			BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream("resources/json/questions.json"), "UTF-8"));
			
			 Object obj = parser.parse(fReader); 
			 if(obj != null) { 
				 JSONObject jsonObject = (JSONObject) obj;
				 JSONArray jsonArray = (JSONArray) jsonObject.get("questions");
				 JSONArray answers = new JSONArray();
				 
				 for(Object o : jsonArray) {
					 JSONObject object = (JSONObject)o;
					 Collection<?> questionValues = object.values();
					 Iterator<?> it = object.keySet().iterator();
					 while(it.hasNext()) {
						String keyValue = (String)it.next();
						Question q = new Question(keyValue);
						questions.add(q);
					 }
					 Iterator<?> itq = questionValues.iterator();
					 int i = 0;
					 while(itq.hasNext()) {
						 Object o2 = itq.next();
						 answers = (JSONArray)o2;
						 for(Object o3: answers) {
							 JSONObject answersObject = (JSONObject)o3;
							 Collection<?> answersValues = answersObject.values();
							 for(Object o4 : answersValues) {
								 JSONArray answerValues = (JSONArray)o4;
								 
								 

									 String answerTitle = (String)((JSONObject)answerValues.get(0)).get("title");
									 String answerStatus = (String)((JSONObject)answerValues.get(1)).get("status");
									 Answer a = new Answer();
									 a.setTitle(answerTitle);
									 if(answerStatus != null) {
										 if(answerStatus.equals("true")) {
											 a.setIsTrue(true);
										 }
										 else {
											 a.setIsTrue(false);
										 }
									 }
									 answersList.add(a);
									 
								 

							 }
							 
						}
					 Collections.shuffle(answersList);
					 questions.get(i).addAnswers(answersList);
					 answersList.clear();
					 i++;
					 }
				 
									 
					
				} 
			 }

			 fReader.close();
		}
		
		catch (IOException | ParseException e) {
			e.printStackTrace();
        }

		return questions;
	}
	
	@SuppressWarnings("unchecked")
	public void writeResults(String gameName) {
		JSONObject jsonObject = new JSONObject();
		JSONArray scores = readResults();
		JSONObject playerMastermindScore = new JSONObject();
		JSONObject playerQuizScore = new JSONObject();
		JSONObject playerAssociationsScore = new JSONObject();
		JSONObject playerTotalScore = new JSONObject();
		JSONObject playerScore = new JSONObject();
		JSONArray playerScores = new JSONArray();
		BufferedWriter fWriter;
		
		try {
			fWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resources/json/results.json"), StandardCharsets.UTF_8));
			int arrayIndex = 0;
			int scoreToWrite = 0;
			if(gameName != null) {
				if(gameName.toUpperCase().equals("MASTERMIND")) {
					arrayIndex = 0;
					scoreToWrite = this._player.getPlayerMastermindScore();
				}
				else if(gameName.toUpperCase().equals("QUIZ")) {
					arrayIndex = 1;
					scoreToWrite = this._player.getPlayerQuizScore();
				}
				else {
					if(gameName.toUpperCase().equals("ASSOCIATIONS")) {
						arrayIndex = 2;
						scoreToWrite = this._player.getPlayerAssociationsScore();
					}
				}
			}
			
			int scoresIndex = 0;
			boolean insertNew = false;
			for(Object o : scores) {
				
				JSONObject obj = (JSONObject)o;
				String playerName = obj.get("player").toString(); 
				JSONObject objectToReplace = (((JSONObject)((JSONArray)obj.get("scores")).get(arrayIndex)));
				JSONObject totalScore = (((JSONObject)((JSONArray)obj.get("scores")).get(3)));
				int totalScoreValue = Integer.parseInt(String.valueOf(totalScore.get("total")));
				
				if(playerName != null && playerName.equals(this._player.getPlayerName())) {				
					int fileScore = Integer.parseInt(String.valueOf(objectToReplace.get(gameName.toLowerCase())));
					objectToReplace.put(gameName.toLowerCase(), scoreToWrite);
					totalScoreValue = totalScoreValue - fileScore + scoreToWrite;
					totalScore.put("total", totalScoreValue);
					insertNew = false;
					jsonObject.put("result",scores);
	
					break;
				}
				scoresIndex ++;
				
			}
			if(scoresIndex == scores.size() || scores.size() == 0) {
				insertNew = true;
			}
			if(insertNew) {
					playerMastermindScore.put("mastermind", this._player.getPlayerMastermindScore());
					playerQuizScore.put("quiz", this._player.getPlayerQuizScore());
					playerAssociationsScore.put("associations", this._player.getPlayerAssociationsScore());
					playerTotalScore.put("total", this._player.getPlayerMastermindScore() + this._player.getPlayerAssociationsScore() + this._player.getPlayerQuizScore());
					playerScores.add(playerMastermindScore);
					playerScores.add(playerQuizScore);
					playerScores.add(playerAssociationsScore);
					playerScores.add(playerTotalScore);
					playerScore.put("player", this._player.getPlayerName());
					playerScore.put("scores",playerScores);
					scores.add(playerScore);
					
					jsonObject.put("result",scores);
				
			}
			fWriter.write(jsonObject.toString());
			fWriter.flush();
			fWriter.close();
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void clearScores() {
		JSONObject jsonObject = new JSONObject();
		JSONArray scores = new JSONArray();

		try {
			
			jsonObject.put("result",scores);
			FileWriter fWriter = new FileWriter("resources/json/results.json");
			fWriter.write(jsonObject.toString());
			fWriter.flush();
			fWriter.close();
		}
	    catch (IOException e) {
	    	e.printStackTrace();
		}


	}
}
