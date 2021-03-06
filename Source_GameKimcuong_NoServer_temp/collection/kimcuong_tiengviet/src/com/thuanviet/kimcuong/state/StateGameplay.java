package com.thuanviet.kimcuong.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import com.thuanviet.kimcuong.BitmapFont;
import com.thuanviet.kimcuong.DEF;
import com.thuanviet.kimcuong.Dialog;
import com.thuanviet.kimcuong.GameLayer;
import com.thuanviet.kimcuong.GameLib;

import com.thuanviet.kimcuong.IConstant;
import com.thuanviet.kimcuong.Kimcuong;
import com.thuanviet.kimcuong.Map;
import com.thuanviet.kimcuong.SoundManager;
import com.thuanviet.kimcuong.Sprite;
import com.thuanviet.kimcuong.actor.DiamondActor;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;

public class StateGameplay extends Kimcuong
{

	public static Bitmap backgroundImage;
	public static Sprite spriteDPad = null;
	public static Sprite spriteFruit = null;
	public static boolean isIngame = false;
	public static long timepause;
	public static int gameMode = 0;//normal mode
	

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:

			// image/character/character_1.png
			isIngame = true;
			if (backgroundImage != null)
				backgroundImage.recycle();
			backgroundImage = null;
			Random a = new Random();
			int r = a.nextInt(2) + 1;
			//int r = 1;
			a = null;
			
			backgroundImage = loadImageFromAsset("image/screen/screen1.jpg");
			backgroundImage = Bitmap.createScaledBitmap(backgroundImage, SCREEN_WIDTH, SCREEN_HEIGHT, true);

			Map.init();
			Rect textBounds =  new  Rect();
			android_FontSmall.getTextBounds("Maig", 0, "Maig".length(), textBounds);
			DEF.LABEL_LEVEL_Y = textBounds.height();//fontsmall_Yellow.getFontHeight();
			DEF.LABEL_SCORE_Y = 2*textBounds.height();//fontsmall_Yellow.getFontHeight();

			DEF.BUTTON_TIMER_BAR_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_0);
			DEF.BUTTON_TIMER_BAR_H = DEF.BUTTON_IGM_W + 2;// StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_TIMER_BAR_0);
			DEF.BUTTON_TIMER_BAR_X = (SCREEN_WIDTH - DEF.BUTTON_TIMER_BAR_W) / 2;
			DEF.BUTTON_TIMER_BAR_Y = Map.BEGIN_Y + Map.MAX_ROW * Map.ITEM_HEIGHT + Map.ITEM_HEIGHT / 2;

			SoundManager.playsoundLoop(0, true);

			break;
		case MESSAGE_UPDATE:
			// cheat

			if (isKeyReleased(KeyEvent.KEYCODE_9)) {
				StateWinLose.isWin = true;
				Kimcuong.changeState(IConstant.STATE_WINLOSE);

			}
			// end cheat
			if (Dialog.isShowDialog)
				return;
			Map.Update();
			updatetouch();

			break;

		case MESSAGE_PAINT:			
			if (backgroundImage != null)
				mainCanvas.drawBitmap(backgroundImage, 0,0, mainPaint);
			//bitmapScreenBuffer.eraseColor(Color.TRANSPARENT);
			Map.DrawGame(mainCanvas);
			// PikachuActivity.matrix.postScale(SCREEN_WIDTH / 1024f,
			// SCREEN_HEIGHT / 600f);//dua vap file map
			//mainPaint.setAlpha(255);
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
		android_FontSmall.setTextAlign(Align.LEFT);
		if (gameMode == 1)
		{
			mainCanvas.drawText(  "Màn : " + (mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y,  android_FontSmall);
			mainCanvas.drawText(  "Điểm : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		}	//fontsmall_White.drawString(mainCanvas, "Level : " + (mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		else if (gameMode == 0) 
			mainCanvas.drawText(  "Điểm : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		else
			mainCanvas.drawText(  "Điểm : " + Map.mTopScore, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		android_FontSmall.setTextAlign(Align.CENTER);
		//fontsmall_White.drawString(mainCanvas, "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "SCORE : " + Map.mAllScore + "/" + Map.LevelMap[mcurrentlevel][0], DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "Failed: " + (Map.tapFail) + "/" + Map.LevelMap[mcurrentlevel][1], DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);

		if (isTouchDrapInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H))
			spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_PAUSE_HIGHTLIGHT, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		else
			spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_PAUSE_NORMAL, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);

		//fontsmall_Yellow.drawString(mainCanvas, "Timer: " + Map.totalTimePlayGame / 1000, DEF.BUTTON_TIMER_BAR_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "Level: " + (mcurrentlevel +1), DEF.BUTTON_TIMER_BAR_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);

		//timer bar		
		spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_TIMER_BAR_0, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);

		double width = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_1);
		
		
		String text = "";
		if (gameMode == 0)
		{
			
			text = "" +(Map.MAX_TIMER - Map.timer) / 6000000 + ":" + (Map.MAX_TIMER - Map.timer) / 1000;
			width = width - width * (Map.timer * 1.0) / Map.MAX_TIMER;
		}
		else if (gameMode == 1) 
		{
			
			int percent = (Map.score*100 / Map.targetScore);
			if(percent >100)
				percent = 100;
			text = "" + percent + "%";
			width = width - width*(100.0 - percent) / 100;

		}
		else
		{

			//text = "" +(Map.MAX_TIMER - Map.timer) / 6000000 + ":" + (Map.MAX_TIMER - Map.timer) / 1000;
			text = "Timer";
			width = width * (Map.timer * 1.0) / Map.MAX_TIMER;
			if(width < (int)(20*scaleX))
				width =(int)(20*scaleX);
		}
		Kimcuong.mainCanvas.save();
		Kimcuong.mainCanvas.clipRect(DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y, DEF.BUTTON_TIMER_BAR_X + (int) width, DEF.BUTTON_TIMER_BAR_Y + 32);
		spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_TIMER_BAR_1, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);
		Kimcuong.mainCanvas.restore();
		Rect textBounds =  new  Rect();
		android_FontSmall.getTextBounds("Maig", 0, "Maig".length(), textBounds);
		mainCanvas.drawText( text,  SCREEN_WIDTH / 2, DEF.BUTTON_TIMER_BAR_Y + textBounds.height(), android_FontSmall);
		//fontsmall_White.drawString(mainCanvas,text , SCREEN_WIDTH / 2, DEF.BUTTON_TIMER_BAR_Y, BitmapFont.ALIGN_CENTER);
	}

	public static void updatetouch()
	{
		if (isKeyReleased(KeyEvent.KEYCODE_BACK) || isTouchReleaseInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H)) {
			changeState(STATE_IGM, true, false);
		}

	}
	// set position of dpad

}
