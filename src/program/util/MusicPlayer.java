package program.util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class MusicPlayer {
	
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}
	public static enum Playback {
		READY, STOPPED, PAUSED, PLAYING
	}
	private Clip _clip;
	public Volume _volume;
	public Playback _playback;
	
	public MusicPlayer(String name,String type){
		
		AudioInputStream audioIn = null;
	            
	    try {
	    	String path="resources/sound/";
	    	if(type.equals("effect")){
	    		path+="effects/";
	    	}
	    	else{
	    		path+="music/";
	    	}
	    	path+=name;
			audioIn = AudioSystem . getAudioInputStream ( new File ( path ) ) ;
		
			_clip = AudioSystem.getClip();
			if(audioIn!=null) {
		    	_clip.open(audioIn);
		    	_volume = Volume.MEDIUM;
		    	_playback = Playback.READY;
		    	_clip.addLineListener(e -> {
		    	    if (e.getType() == LineEvent.Type.STOP) {
		    	      _playback = Playback.STOPPED;
		    	    }
		    	  }
		    	);
		    }
	    } catch (LineUnavailableException e) {
			e.printStackTrace();
		}catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	public void replay(boolean loop){
		
		this.stop();		
		this.play(loop);
	}	
	public void play(boolean loop){

		if(_playback != Playback.PLAYING) {
			_playback = Playback.PLAYING;
			if(loop) {
				this.getClip().loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				this.getClip().start();
			}
		}
	}
	
	public void mute() {
		if (_volume != Volume.MUTE) {
			this.setClipVolume(0);
			_volume = Volume.MUTE;
		}
	}
	
	private void setVolumeEnum(double amount) {
		if(amount == 0) {
    		_volume = Volume.MUTE;
    	}
    	else if(amount > 0 && amount <= 0.5) {
    		_volume = Volume.LOW;
    	}
    	else if(amount > 0.5 && amount <= 1) {
    		_volume = Volume.MEDIUM;
    	}
    	else {
    		_volume = Volume.HIGH;
    	}
	}
	
	private float getDBFromLinear(double volume) {
		float dB;
        if (volume != 0) {
            dB = 20.0f *(float) Math.log10(volume);
        }
        else {
            dB = -80.0f;
        }
        return dB;
	}
	
	public void setClipVolume(double amount) {
		setVolumeEnum(amount);
        FloatControl ctrl = this.getClipControl(FloatControl.Type.MASTER_GAIN);
        try{
        	
        	ctrl.setValue((float)this.getDBFromLinear(amount));
        	
        }catch(IllegalArgumentException e){
        	JOptionPane.showMessageDialog(null, "Volume is too high! Instead it will be set to normal volume!", "Error! ", JOptionPane.ERROR_MESSAGE);
        	ctrl.setValue((float)this.getDBFromLinear(1));
        	_volume = Volume.MEDIUM;
        }
	}
	
	public Clip getClip() {
		return this._clip;
	}
	
	public Clip setClip(Clip clip) {
		return this._clip = clip;
	}
	
	public FloatControl getClipControl(Control.Type control) {
		return  (FloatControl) this.getClip().getControl(control);
	}
	
	public void pause() {
		if(_playback == Playback.PLAYING) {
			if (this.isRunning()) {
				this.getClip().stop();
			}
			_playback = Playback.PAUSED;
		}
	}
	
	public void stop() {
		
		if(this.isRunning()) {
			if(_playback != Playback.STOPPED && _playback != Playback.READY) {
				this.getClip().stop();
				_playback = Playback.STOPPED;
			}
		}
		this.getClip().setFramePosition(0);
		_playback = Playback.READY;
	}
	
	public boolean isRunning() {
		return this.getClip().isRunning();
	}
	public void close() {
		this.getClip().close();
	}
}
