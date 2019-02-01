package program.classes;

import javax.swing.ImageIcon;

public class Tile {
	ImageIcon _image;
	String _type;
	int _place;
	boolean _compared;
	
	public Tile() {
		_image = new ImageIcon();
		_type = "";
		_place = 0;
		_compared = false;
	}
	public Tile(String imageName, String type, int place) {
		_image = new ImageIcon("resources/images/"+ imageName);
		_type = type;
		_place = place;
		_compared = false;
	}
	public ImageIcon getImage() {
		return this._image;
	}
	public void setImage (ImageIcon image) {
		this._image.setImage(image.getImage());
	}
	public String getType() {
		return this._type;
	}
	public void setType(String type) {
		this._type = type;
	}
	public int getPlace() {
		return this._place;
	}
	public void setPlace(int place) {
		this._place = place;
	}
	public boolean getCompared() {
		return this._compared;
	}
	public void setCompared(boolean compared) {
		this._compared = compared;
	}
	@Override
	public boolean equals(Object o) {
		Tile other = (Tile)o;
		if(o != null) {
			if(this._type.equals(other._type)) {
				return true;
			}
		}
	
		return false;
	}
}
