package com.xiaxio.petpop.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.petpop.BitmapFont;
import com.xiaxio.petpop.Dialog;
import com.xiaxio.petpop.GameLayer;
import com.xiaxio.petpop.GameLib;
import com.xiaxio.petpop.GameThread;
import com.xiaxio.petpop.IConstant;
import com.xiaxio.petpop.Map;
import com.xiaxio.petpop.SoundManager;
import com.xiaxio.petpop.Sprite;
import com.xiaxio.petpop.PetPop;
import com.xiaxio.petpop.actor.DiamondActor;

import resolution.DEF;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;

public class StateGameplay extends PetPop
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
			DEF.BUTTON_TIMER_BAR_Y = Map.BEGIN_Y + Map.MAX_ROW * Map.ITEM_HEIGHT + Map.ITEM_HEIGHT / 3;

			SoundManager.playsoundLoop(0, true);

			break;
		case MESSAGE_UPDATE:
			// cheat

			if (isKeyReleased(KeyEvent.KEYCODE_9)) {
				StateWinLose.isWin = true;
				PetPop.changeState(IConstant.STATE_WINLOSE);

			}
			// end cheat
			if (Dialog.isShowDialog)
				return;
			Map.Update();
			updatetouch();

			break;

		case MESSAGE_PAINT:		
			mainPaint.setAlpha(255);
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
		//spriteUI.drawAFrame(c, 3, 0, 0);
		android_FontSmall.setTextAlign(Align.LEFT);
		android_FontSmallBoder.setTextAlign(Align.LEFT);
		if (gameMode == 1)
		{
			c.drawText(  "Level : " + (mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y,  android_FontSmallBoder);
			c.drawText(  "Level : " + (mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y,  android_FontSmall);
			c.drawText(  "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmallBoder);
			c.drawText(  "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		}	//fontsmall_White.drawString(mainCanvas, "Level : " + (mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		else if (gameMode == 0) 
		{
			c.drawText(  "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmallBoder);
			c.drawText(  "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		}
		else
		{
			c.drawText(  "Score : " + Map.mTopScore, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmallBoder);
			c.drawText(  "Score : " + Map.mTopScore, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, android_FontSmall);
		}
		android_FontSmall.setTextAlign(Align.CENTER);
		android_FontSmallBoder.setTextAlign(Align.CENTER);
		//fontsmall_White.drawString(mainCanvas, "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "SCORE : " + Map.mAllScore + "/" + Map.LevelMap[mcurrentlevel][0], DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "Failed: " + (Map.tapFail) + "/" + Map.LevelMap[mcurrentlevel][1], DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);

		if (isTouchDrapInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H))
			spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_PAUSE_HIGHTLIGHT, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		else
			spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_PAUSE_NORMAL, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);

		//fontsmall_Yellow.drawString(mainCanvas, "Timer: " + Map.totalTimePlayGame / 1000, DEF.BUTTON_TIMER_BAR_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "Level: " + (mcurrentlevel +1), DEF.BUTTON_TIMER_BAR_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);

		//timer bar		
		spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_TIMER_BAR_0, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);

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
		}
		PetPop.mainCanvas.save();
		PetPop.mainCanvas.clipRect(DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y, DEF.BUTTON_TIMER_BAR_X + (int) width, DEF.BUTTON_TIMER_BAR_Y + 32);
		spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_TIMER_BAR_1, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);
		PetPop.mainCanvas.restore();
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
