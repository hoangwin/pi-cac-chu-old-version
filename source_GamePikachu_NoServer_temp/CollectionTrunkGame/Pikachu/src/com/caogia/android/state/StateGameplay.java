package com.caogia.android.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import com.caogia.android.BitmapFont;
import com.caogia.android.Dialog;
import com.caogia.android.GameLayer;
import com.caogia.android.GameLib;
import com.caogia.android.GameThread;
import com.caogia.android.IConstant;
import com.caogia.android.Map;
import com.caogia.android.MainActivityHD;
import com.caogia.android.SoundManager;
import com.caogia.android.Sprite;

import resolution.DEF;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;

public class StateGameplay extends MainActivityHD
{




	public static Bitmap backgroundImage;
//	public static Bitmap characterImage;
	public static Sprite spriteDPad = null;
	public static Sprite spriteTileBoard = null;
	public static boolean isIngame = false;
	public static long timepause;
	static Map map;

	public static synchronized void SendMessage(int type)
	{
	
		switch (type)
		{
		case MESSAGE_CTOR:
			DEF.LABEL_LEVEL_X = DEF.LABEL_SCORE_X =  SCREEN_WIDTH + 5 - fontsmall_Yellow.stringWidth("Level : 9999");
			//if(characterImage != null)
		//		characterImage.recycle();
			//characterImage = null;
			//characterImage = loadImageFromAsset("image/character/character_" +( mcurrentlevel + 1)+ ".png");			
			//image/character/character_1.png
			isIngame = true;
	
			if(backgroundImage!=null)
				backgroundImage.recycle();
			backgroundImage = null;
			Random a = new Random();			
			int r = a.nextInt(3) + 1; 
			a = null;
			Log.d("aaaaaaaaaaaaaaaaaaa","  "+r);
			backgroundImage = loadImageFromAsset("image/screen/screen" + r +".jpg");
			backgroundImage = Bitmap.createScaledBitmap(backgroundImage, SCREEN_WIDTH, SCREEN_HEIGHT, true);
			map = new Map();
			
			map.Init();
			map.NewGame();
			DEF.LABEL_SCORE_Y =fontsmall_Yellow.getFontHeight();
			
			//SoundManager.stopSound(SoundManager.SOUND_TITLE);
			//SoundManager.mMediaPlayer
			SoundManager.playsoundLoop(1,true);
			//clean wall in map
			//GameLayer.arrayListDataMap.get(0)[6][6] = -1;
			//GameLayer.arrayListDataMap.get(0)[7][6] = -1;
	
			//Log.d("map.doorExit_Row"," " + map.doorExit_Row);
			//Log.d("map.doorExit_Col"," " + map.doorExit_Col);
	
			break;
		case MESSAGE_UPDATE:
			//cheat
			if(isKeyReleased(KeyEvent.KEYCODE_9))
			{
				StateWinLose.isWin = true;
				MainActivityHD.changeState(IConstant.STATE_WINLOSE);
										
			}
			//end cheat
			if (Dialog.isShowDialog)
				return;
			updatetouch(); 
			Map.mTimerCount += GameThread.timeCurrent - GameThread.timePrev2;
			if (MainActivityHD.isTouchReleaseScreen()) {
				map.CardClick(MainActivityHD.arraytouchPosX[0], MainActivityHD.arraytouchPosY[0]);
			} else if (Map.mTimerCount > Map.mTimerDecrease) {
				Map.mTimerCount = Map.mTimerDecrease = 0;
				
				//Map.mTimerDecrease = Map.mTimerDecrease - (GameThread.timeCurrent - Map.mTimerCount);
				StateWinLose.isWin = false;
				MainActivityHD.changeState(IConstant.STATE_WINLOSE);
				SoundManager.pausesoundLoop(1);
				SoundManager.playSound(SoundManager.SOUND_LOSE, 1);
			}
			break;
	
		case MESSAGE_PAINT:
			matrix.reset();
			float scale_x = SCREEN_WIDTH * 1f / 1280;
			float scale_y = SCREEN_HEIGHT * 1f / 800;
			
	
			matrix.setScale(scale_x, scale_y);
			matrix.postTranslate((SCREEN_WIDTH - backgroundImage.getWidth() * SCREEN_WIDTH / 1280) / 2, (SCREEN_HEIGHT - backgroundImage.getHeight() * SCREEN_HEIGHT / 800) / 2);
			mainPaint.setAntiAlias(true);
			mainPaint.setFilterBitmap(true);
			mainPaint.setDither(true);
			if (backgroundImage != null)
				mainCanvas.drawBitmap(backgroundImage, matrix, mainPaint);
			
			bitmapScreenBuffer.eraseColor(Color.TRANSPARENT);			
			//MainActivityHD.mainPaint.setStyle(Style.FILL);
			//MainActivityHD.mainPaint.setARGB(130, 0, 0, 0);
			//MainActivityHD.mainCanvas.drawRect(0, 0, MainActivityHD.SCREEN_WIDTH, MainActivityHD.SCREEN_HEIGHT, MainActivityHD.mainPaint);
			//if(characterImage!=null)
			//{
			//	MainActivityHD.mainPaint.setARGB(255, 0, 0, 0);
			//	matrix.postTranslate(scale_x*(1280 -characterImage.getWidth())/2 , scale_y*(800 -characterImage.getHeight())/2);
			//	mainCanvas.drawBitmap(characterImage, matrix, mainPaint);
			//}
			//	Map.drawMap(canvasScreenBuffer);
			Map.DrawGame(canvasScreenBuffer);
			MainActivityHD.matrix.reset();
			//PikachuActivity.matrix.postScale(SCREEN_WIDTH / 1024f, SCREEN_HEIGHT / 600f);//dua vap file map	
			mainCanvas.drawBitmap(bitmapScreenBuffer, 0, 0, mainPaint);
			drawHUD(mainCanvas);
			break;
		case MESSAGE_DTOR:
			isIngame = false;
			break;
		}
	}

	public static void drawHUD(Canvas c)
	{
		//score
		//spriteDPad.drawAFrame(PikachuActivity.mainCanvas, DEF.FRAME_SCORE, 0, 0);
		mainPaint.setStyle(Style.FILL);
		mainPaint.setColor(0xFF2b8fbe);
	//	c.drawRect(0, 0, SCREEN_WIDTH, DEF.BUTTON_TIMER_BAR_H, mainPaint);

		fontsmall_White.drawString(mainCanvas, "Level: " + MainActivityHD.mcurrentlevel, DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		fontsmall_White.drawString(mainCanvas, "Score: " + Map.mAllScore, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);
		//	if (Dialog.isShowDialog || mCurrentState != STATE_GAMEPLAY)
		//		return;
		//draw pause button
		if (isTouchDrapInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H))
			spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_PAUSE_HIGHTLIGHT, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		else
			spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_PAUSE_NORMAL, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		//hint
		if (isTouchDrapInRect(DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y, DEF.BUTTON_HINT_W, DEF.BUTTON_HINT_H))
			spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_HINT_HIGHTLIGHT, DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y);
		else
			spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_HINT, DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y);
		//fontsmall_White.drawString(mainCanvas, "Hint", DEF.BUTTON_HINT_X + DEF.BUTTON_HINT_W / 2, DEF.BUTTON_HINT_Y + DEF.BUTTON_HINT_H/2 - fontsmall_White.getFontHeight() - 2, BitmapFont.ALIGN_CENTER);
		fontsmall_Yellow.drawString(mainCanvas, "(" + Map.countHint + ")", DEF.BUTTON_HINT_X + DEF.BUTTON_HINT_W / 2, DEF.BUTTON_HINT_Y + DEF.BUTTON_HINT_H/2 - fontsmall_White.getFontHeight()/4, BitmapFont.ALIGN_CENTER);
		// change title

		if (isTouchDrapInRect(DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y, DEF.BUTTON_CHANGE_TILE_W, DEF.BUTTON_CHANGE_TILE_H))
			spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_SORT_HIGHTLIGHT, DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y);
		else
			spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_SORT, DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y);
		//fontsmall_White.drawString(mainCanvas, "Sort", DEF.BUTTON_CHANGE_TILE_X + DEF.BUTTON_CHANGE_TILE_W / 2, DEF.BUTTON_CHANGE_TILE_Y + DEF.BUTTON_HINT_H/2 - fontsmall_White.getFontHeight(), BitmapFont.ALIGN_CENTER);
		fontsmall_Yellow.drawString(mainCanvas, "(" + Map.countAutoSort + ")", DEF.BUTTON_CHANGE_TILE_X + DEF.BUTTON_CHANGE_TILE_W / 2, DEF.BUTTON_CHANGE_TILE_Y  + DEF.BUTTON_HINT_H/2 - fontsmall_White.getFontHeight()/4, BitmapFont.ALIGN_CENTER);

		//timer bar
		spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_TIMER_BAR_0, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);

		double width = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_1);
		width = width - width * (Map.mTimerCount * 1.0) / Map.mTimerDecrease;	
		
		
		MainActivityHD.mainCanvas.save();
		MainActivityHD.mainCanvas.clipRect(DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y, DEF.BUTTON_TIMER_BAR_X + (int) width, DEF.BUTTON_TIMER_BAR_Y + 32);
		spriteDPad.drawAFrame(MainActivityHD.mainCanvas, DEF.FRAME_TIMER_BAR_1, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);
		MainActivityHD.mainCanvas.restore();

	}

	public static void updatetouch()
	{
		if (isKeyReleased(KeyEvent.KEYCODE_BACK)|| isTouchReleaseInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H)) {
			changeState(STATE_IGM, true, false);
		}

		if (isTouchReleaseInRect(DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y, DEF.BUTTON_HINT_W, DEF.BUTTON_HINT_H)) {
			if (Map.countHint > 0) {
				map.searchPair();
				Map.countHint--;
			}
		}

		if (isTouchReleaseInRect(DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y, DEF.BUTTON_CHANGE_TILE_W, DEF.BUTTON_CHANGE_TILE_H)) {
			if (Map.countAutoSort > 0) {
				map.autoSortMap();
				Map.countAutoSort--;
			}

		}

	}
	//set position of dpad

}
