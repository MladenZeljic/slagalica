package program.util;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import program.classes.Tile;

public class Utilities {

	private String _imageTypes [] = {"pik","herc","karo","tref","as","dzoker"};
	
	public int getRandomInteger(int min, int max) {
		Random r = new Random();
		return r.nextInt(max-min) + min;
	}
	public void drawMultilineString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	public List<Tile> getRandomMastermind(){
		List <Tile> tiles = new ArrayList<>();
		int randomTypeIndex = 0;
		int scaleX = 40;
		int scaleY = 40;
		for(int i = 1 ; i <= 4; i++) {
			randomTypeIndex = this.getRandomInteger(0, _imageTypes.length);
			String imageType = _imageTypes[randomTypeIndex];
			Tile tile = new Tile(imageType+".png",imageType,i);
			Image tileIconContent = tile.getImage().getImage();  
			if(_imageTypes[i]!= null && (_imageTypes[i].equals("tref") || _imageTypes[i].equals("dzoker"))) {
				scaleX = 35;
				scaleY = 35;
			}
	        Image resizedTileIconContent = tileIconContent.getScaledInstance(scaleX, scaleY,  java.awt.Image.SCALE_SMOOTH);  
	        tile.getImage().setImage(resizedTileIconContent);
			tiles.add(tile);
		}
		return tiles;
	}
	public List<Tile> getMastermindTiles(){

		List <Tile> images = new ArrayList<>();
		int scaleX = 40;
		int scaleY = 40;
		for(int i = 0; i < _imageTypes.length; i++) {
			ImageIcon image = new ImageIcon("resources/images/"+ _imageTypes[i]+ ".png");
			Image imageIconContent = image.getImage();
			if(_imageTypes[i]!= null && (_imageTypes[i].equals("pik") || _imageTypes[i].equals("tref") || _imageTypes[i].equals("dzoker"))) {
				scaleX = 35;
				scaleY = 35;
			}
	        Image resizedImageIconContent = imageIconContent.getScaledInstance(scaleX, scaleY,  java.awt.Image.SCALE_SMOOTH);  
	        image.setImage(resizedImageIconContent);
	        Tile tile = new Tile();
	        tile.setImage(image);
	        tile.setType(_imageTypes[i]);
	        images.add(tile);
		}
		return images;
	}
	
	public String getLetter(int letterCounter) {
		// TODO Auto-generated method stub
		String letter = "A";
		switch (letterCounter) {
		case 1 : 
			letter = "A";
			break;
		case 2 : 
			letter = "B";
			break;
		case 3 : 
			letter = "C";
			break;
		case 4 : 
			letter = "D";
			break;
		default : 
			letter = "A";
		}
		
		return letter;
	}
}
