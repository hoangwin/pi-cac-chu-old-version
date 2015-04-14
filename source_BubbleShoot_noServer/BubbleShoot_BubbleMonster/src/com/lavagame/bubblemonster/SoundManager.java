package com.lavagame.bubblemonster;
import com.lavagame.bubblemonster.R;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.net.MailTo;
import android.test.IsolatedContext; 

public class SoundManager {
 
	static private SoundManager _instance;
	private static SoundPool mSoundPool;
	public static MediaPlayer mMediaPlayer[];

	private static HashMap<Integer, Integer> mSoundPoolMap;
	private static AudioManager  mAudioManager;
	private static Context mContext;
	 public static int SOUND_TITLE = 0;
	 public static int SOUND_SELECT = 1;
	 public static int SOUND_INGAME_BG = 2;
	 public static int SOUND_CLICK_CARD = 3;
	 public static int SOUND_CANON_SHOOT = 4;
	 public static int SOUND_SHOOT_MATCH = 5;
	 public static int SOUND_WIN = 6;
	 public static int SOUND_LOSE = 7;
	 public static int SOUND_BACK = 8;
	 public static int SOUND_SPECIAL_EFFECT_LOST = 9;
	 public static int SOUND_SHOOT_BOUND = 10;

	 public static int SOUND_START = 11;
	private SoundManager() 
	{
	}
 
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 *
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance()
	{
	    if (_instance == null)
	      _instance = new SoundManager();
	    return _instance;
	 }
 
	/**
	 * Initialises the storage for the sounds
	 *
	 * @param theContext The Application context
	 */
	public static  void initSounds(Context theContext)
	{
		 mContext = theContext;
	     mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	    // mMediaPlayer = new MediaPlayer();	     
	     mSoundPoolMap = new HashMap<Integer, Integer>();
	     mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
	     
	     try {
	    	 mMediaPlayer = new MediaPlayer[2];
	    	 mMediaPlayer[0] = MediaPlayer.create(MainActivity.context,R.raw.bg_music);
	    	 mMediaPlayer[0].setVolume(0.4f, 0.4f);
	    	// mMediaPlayer[1] = MediaPlayer.create(Diamond.context, R.raw.game_background);
	          
	        } catch (Exception exception) {
	        }
	     
	} 
	public static void playsoundLoop(int index,boolean loop)
	{
		if(!MainActivity.isEnableSound)
			return;
		if(mMediaPlayer != null)
			if(mMediaPlayer.length >index)
				if(mMediaPlayer[index] != null) 
				{
					mMediaPlayer[index].start();
					mMediaPlayer[index].setLooping(loop);					
					mMediaPlayer[index].setOnCompletionListener(new OnCompletionListener() {					
			              public void onCompletion(MediaPlayer mp) {
			                  mp.release();
			                  mp = null;			
			              }
			          });   
				}	 
	}
	public static void pausesoundLoop(int index)
	{
		try{
			if(mMediaPlayer != null)
				if(index <mMediaPlayer.length)
					if(mMediaPlayer[index] != null)
						if(mMediaPlayer[index].isPlaying())			
							mMediaPlayer[index].pause();
		}catch(Exception e)
		{
				
		}
	}
	/**
	 * Add a new Sound to the SoundPool
	 *
	 * @param Index - The Sound Index for Retrieval
	 * @param SoundID - The Android ID for the Sound asset.
	 */
	public static void addSound(int Index,int SoundID)
	{
		mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));

	} 
	/**
	 * Loads the various sound assets
	 * Currently hardcoded but could easily be changed to be flexible.
	 */
	public static void loadSounds()
	{	
		mSoundPoolMap.put(SOUND_SELECT, mSoundPool.load(mContext, R.raw.select, 1));	
		mSoundPoolMap.put(SOUND_CLICK_CARD, mSoundPool.load(mContext, R.raw.button_click, 1));
		mSoundPoolMap.put(SOUND_CANON_SHOOT, mSoundPool.load(mContext,R.raw.cannonshoot, 1));
		mSoundPoolMap.put(SOUND_SHOOT_MATCH, mSoundPool.load(mContext, R.raw.match, 1));
		
		mSoundPoolMap.put(SOUND_WIN, mSoundPool.load(mContext, R.raw.game_win, 1));
		mSoundPoolMap.put(SOUND_LOSE, mSoundPool.load(mContext, R.raw.game_over, 1));		
		mSoundPoolMap.put(SOUND_BACK, mSoundPool.load(mContext, R.raw.select, 1));
		mSoundPoolMap.put(SOUND_SPECIAL_EFFECT_LOST, mSoundPool.load(mContext, R.raw.special_effect_lose, 1));
		mSoundPoolMap.put(SOUND_SHOOT_BOUND, mSoundPool.load(mContext, R.raw.rebound, 1));
		mSoundPoolMap.put(SOUND_START, mSoundPool.load(mContext, R.raw.start, 1));
		
		
		
	}
 
	/**
	 * Plays a Sound
	 *
	 * @param index - The Index of the Sound to be played
	 * @param speed - The Speed to play not, not currently used but included for compatibility
	 */
	public static void playSound(int index,float speed)
	{
		if(!MainActivity.isEnableSound)
			return;
		     float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		     streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		  int i =    mSoundPool.play((Integer) mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
		 
	}
	
	
	/**
	 * Stop a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void stopSound(int index)
	{
		mSoundPool.stop((Integer) mSoundPoolMap.get(index));
	}
 
	/**
	 * Deallocates the resources and Instance of SoundManager
	 */
	public static void cleanup()
	{
		mSoundPool.release();
		mSoundPool = null;
	    mSoundPoolMap.clear();
	    mAudioManager.unloadSoundEffects();
	    _instance = null;
 
	}
 
}