package com.xiaxio.monster.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.monster.BitmapFont;
import com.xiaxio.monster.Dialog;
import com.xiaxio.monster.GameLayer;
import com.xiaxio.monster.GameLib;
import com.xiaxio.monster.GameViewThread;
import com.xiaxio.monster.IConstant;
import com.xiaxio.monster.Map;
import com.xiaxio.monster.SoundManager;
import com.xiaxio.monster.Sprite;
import com.xiaxio.monster.MonsterActivity;

import resolution.DEF;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;

public class StateGameplay extends MonsterActivity
{
	public static Bitmap backgroundImage;	
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
						
			//image/character/character_1.png
			isIngame = true;	
			if(backgroundImage!=null)
				backgroundImage.recycle();
			backgroundImage = null;
			Random a = new Random();			
			int r = a.nextInt(1) + 1; 
			a = null;
			backgroundImage = loadImageFromAsset("image/screen/screen1.jpg");
			backgroundImage = Bitmap.createScaledBitmap(backgroundImage, SCREEN_WIDTH, SCREEN_HEIGHT, true);
			map = new Map();
			map.Init();
			map.NewGame();			
			
			//SoundManager.stopSound(SoundManager.SOUND_TITLE);
			//SoundManager.mMediaPlayer
			SoundManager.playsoundLoop(1,true);
	
			DEF.LABEL_LEVEL_Y =DEF.BUTTON_IGM_Y + 3*DEF.BUTTON_IGM_H/2;//DEF.BUTTON_CHANGE_TILE_Y + DEF.BUTTON_CHANGE_TILE_H + DEF.BUTTON_CHANGE_TILE_H/4 ;			
			DEF.LABEL_SCORE_Y = DEF.BUTTON_IGM_Y + 3*DEF.BUTTON_IGM_H/2;//DEF.LABEL_LEVEL_Y ;//+ fontsmall_Yellow.getFontHeight();
			DEF.BUTTON_TIMER_BAR_Y =  DEF.BUTTON_IGM_W/3;// + DEF.BUTTON_IGM_W /2 + Map.ROW*Map.CELL_HEIGHT;// StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0)) / 2;

			break;
		case MESSAGE_UPDATE:
			//cheat
			if(isKeyReleased(KeyEvent.KEYCODE_9))
			{
				StateWinLose.isWin = true;
				MonsterActivity.changeState(IConstant.STATE_WINLOSE);
										
			}
			//end cheat
			if (Dialog.isShowDialog)
				return;
			updatetouch();
			Map.mTimerCount += GameViewThread.timePrev - GameViewThread.timePrev2;
			if (MonsterActivity.isTouchReleaseScreen()) {
				map.CardClick(MonsterActivity.arraytouchPosX[0], MonsterActivity.arraytouchPosY[0]);
			} else if (Map.mTimerCount > Map.mTimerDecrease) {
				Map.mTimerCount = Map.mTimerDecrease = 0;
				
				//Map.mTimerDecrease = Map.mTimerDecrease - (GameThread.timeCurrent - Map.mTimerCount);
				StateWinLose.isWin = false;
				MonsterActivity.changeState(IConstant.STATE_WINLOSE);
				SoundManager.pausesoundLoop(1);
				SoundManager.playSound(SoundManager.SOUND_LOSE, 1);
			}
			break;
	
		case MESSAGE_PAINT:
			mainCanvas.drawBitmap(backgroundImage, 0,0, mainPaint);
			
		//	bitmapScreenBuffer.eraseColor(Color.TRANSPARENT);			
			//PikachuActivityHD.mainPaint.setStyle(Style.FILL);
		//	PikachuActivityHD.mainPaint.setARGB(130, 0, 0, 0);
		//	PikachuActivityHD.mainCanvas.drawRect(0, 0, PikachuActivityHD.SCREEN_WIDTH, PikachuActivityHD.SCREEN_HEIGHT, PikachuActivityHD.mainPaint);
		
			//	Map.drawMap(canvasScreenBuffer);
			Map.DrawGame(mainCanvas);
			//MonsterActivity.matrix.reset();
			//PikachuActivity.matrix.postScale(SCREEN_WIDTH / 1024f, SCREEN_HEIGHT / 600f);//dua vap file map	
			//mainCanvas.drawBitmap(bitmapScreenBuffer, 0, 0, mainPaint);
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
		//mainPaint.setStyle(Style.FILL);
		//mainPaint.setColor(0xFF2b8fbe);
	//	c.drawRect(0, 0, SCREEN_WIDTH, DEF.BUTTON_TIMER_BAR_H, mainPaint);
		android_FontSmall.setTextAlign(Align.LEFT);
		mainCanvas.drawText( "Level: " + (MonsterActivity.mcurrentlevel +1),  DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, android_FontSmall);
		android_FontSmall.setTextAlign(Align.RIGHT);
		mainCanvas.drawText( "Score: " + Map.mAllScore,  DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		android_FontSmall.setTextAlign(Align.CENTER);
	//	fontsmall_White.drawString(mainCanvas, "Level: " + (TwinsCandy.mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
	//	fontsmall_White.drawString(mainCanvas, "Score: " + Map.mAllScore, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);
		//	if (Dialog.isShowDialog || mCurrentState != STATE_GAMEPLAY)
		//		return;
		//draw pause button
		if (isTouchDrapInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H))
			spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_PAUSE_HIGHTLIGHT, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		else
			spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_PAUSE_NORMAL, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		//hint
		if (Map.countHint > 0)
		{
			if (isTouchDrapInRect(DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y, DEF.BUTTON_HINT_W, DEF.BUTTON_HINT_H))
				spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_HINT_HIGHTLIGHT, DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y);
			else
				spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_HINT, DEF.BUTTON_HINT_X, DEF.BUTTON_HINT_Y);
		}
		
		//mainCanvas.drawText( "(" + Map.countHint + ")",  DEF.BUTTON_HINT_X + DEF.BUTTON_HINT_W/2, DEF.BUTTON_HINT_Y + DEF.BUTTON_HINT_H/2 + (int)(40*scaleY), android_FontNormal);
		
		// change title
		if (Map.countAutoSort > 0)
		{
			if (isTouchDrapInRect(DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y, DEF.BUTTON_CHANGE_TILE_W, DEF.BUTTON_CHANGE_TILE_H))
				spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_SORT_HIGHTLIGHT, DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y);
			else
				spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_BUTTON_CUSTOM_SORT, DEF.BUTTON_CHANGE_TILE_X, DEF.BUTTON_CHANGE_TILE_Y);
		}
		//mainCanvas.drawText( "(" + Map.countAutoSort + ")",  DEF.BUTTON_CHANGE_TILE_X + DEF.BUTTON_CHANGE_TILE_W/2, DEF.BUTTON_CHANGE_TILE_Y + DEF.BUTTON_CHANGE_TILE_H/2 + (int)(40*scaleY), android_FontNormal);
		
		android_FontNormal.setColor(Color.WHITE);
		//timer bar
		spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_TIMER_BAR_0, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);

		double width = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_1);
		width = width - width * (Map.mTimerCount * 1.0) / Map.mTimerDecrease;	
		
		
		MonsterActivity.mainCanvas.save();
		MonsterActivity.mainCanvas.clipRect(DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y, DEF.BUTTON_TIMER_BAR_X + (int) width, DEF.BUTTON_TIMER_BAR_Y + 32);
		spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_TIMER_BAR_1, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);
		MonsterActivity.mainCanvas.restore();

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
