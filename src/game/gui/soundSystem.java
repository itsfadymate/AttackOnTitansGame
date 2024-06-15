package game.gui;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class soundSystem {
    private static MediaPlayer backgroundMusicPlayer;
    private static MediaPlayer soundEffectsPlayer;
    private static double SFXVolume;
    private static double BackgroundVolume;

    public static double getSFXVolume() {
		return SFXVolume;
	}

	public static void setSFXVolume(double sFXVolume) {
		SFXVolume = sFXVolume;
		soundEffectsPlayer.setVolume(sFXVolume);
	}

	public static double getBackgroundVolume() {
		return BackgroundVolume;
	}

	public static void setBackgroundVolume(double backgroundVolume) {
		BackgroundVolume = backgroundVolume;
		backgroundMusicPlayer.setVolume(backgroundVolume);
	}

	static {
		try {
        Media backgroundMusic = new Media(new File("sound/Call-of-Silence-Lots-of-bla.mp3").toURI().toString());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        soundEffectsPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.play();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

    }

    public static void muteBackground() {
    	backgroundMusicPlayer.setMute(true);
    }

    public static void playBackgroundMusic() {
        backgroundMusicPlayer.play();
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

//    public void pauseBackgroundMusic() {
//        backgroundMusicPlayer.pause();
//    }

    //the weapons sound effect

    public static void playSoundEffectFromURL(String url) {
        Media soundEffect = new Media(new File(url).toURI().toString());
        soundEffectsPlayer = new MediaPlayer(soundEffect);
        soundEffectsPlayer.play();
    }

    public static void playCannonEffect() {
    	playSoundEffectFromURL("sound/cannon-firing.mp3"); // add the file path
    }

    public static void playSniperEffect() {
    	playSoundEffectFromURL("sound/sniper-rifle-firing-shot.mp3"); // add the file path
    }

    public static void playMortarEffect() {
    	Media soundEffect = new Media(new File("sound/mortar-shots.mp3").toURI().toString());
        soundEffectsPlayer = new MediaPlayer(soundEffect);
        soundEffectsPlayer.setCycleCount(2);
        soundEffectsPlayer.setRate(2.8);
        soundEffectsPlayer.play();
    }

    public static void playWallTrapEffect() {
    	playSoundEffectFromURL("sound/Wall-trap.mp3"); // add the file path
    }

    //here are the titans attacking sound effect

    public static void playClossalEffect() {
    	playSoundEffectFromURL(null); // add the file path
    }

    public static void playPureEffect() {
    	playSoundEffectFromURL(null); // add the file path
    }

    public static void playArmouredEffect() {
    	playSoundEffectFromURL(null); // add the file path
    }

    public static void playAbnormalEffect() {
    	playSoundEffectFromURL(null); // add the file path
    }


    //sound effect player controls

    public static void mutesoundEffects() {
    	soundEffectsPlayer.setMute(true);
    }

    public static void playSoundEffect() {
        soundEffectsPlayer.play();
    }

//    public void pauseSoundEffect() {
//        soundEffectsPlayer.pause();
//    }
    public static void main(String[] args) {
		playCannonEffect();
	}
}

