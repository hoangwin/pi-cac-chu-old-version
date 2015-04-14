package com.mega.diamond.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import com.mega.diamond.BitmapFont;
import com.mega.diamond.Dialog;
import com.mega.diamond.Diamond;
import com.mega.diamond.GameLayer;
import com.mega.diamond.GameLib;
import com.mega.diamond.GameThread;
import com.mega.diamond.IConstant;
import com.mega.diamond.Map;
import com.mega.diamond.SoundManager;
import com.mega.diamond.Sprite;
import com.mega.diamond.actor.DiamondActor;

import resolution.DEF;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;

public class StateGameplay extends Diamond
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
			// Log.d("aaaaaaaaaaaaaaaaaaa","  "+r);
			backgroundImage = loadImageFromAsset("image/screen/screen" + r + ".jpg");

			//mainPaint.setAntiAlias(true);
			//mainPaint.setFilterBitmap(true);
			//mainPaint.setDither(true);
			backgroundImage = Bitmap.createScaledBitmap(backgroundImage, SCREEN_WIDTH, SCREEN_HEIGHT, true);

			Map.init();
			DEF.LABEL_SCORE_Y = fontsmall_Yellow.getFontHeight();

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
				Diamond.changeState(IConstant.STATE_WINLOSE);

			}
			// end cheat
			if (Dialog.isShowDialog)
				return;
			Map.Update();
			updatetouch();

			break;

		case MESSAGE_PAINT:
			matrix.reset();
			if (backgroundImage != null)
				mainCanvas.drawBitmap(backgroundImage, matrix, mainPaint);
			bitmapScreenBuffer.eraseColor(Color.TRANSPARENT);
			// PikachuActivityHD.mainPaint.setStyle(Style.FILL);
			// PikachuActivityHD.mainPaint.setARGB(130, 0, 0, 0);
			// PikachuActivityHD.mainCanvas.drawRect(0, 0,
			// PikachuActivityHD.SCREEN_WIDTH, PikachuActivityHD.SCREEN_HEIGHT,
			// PikachuActivityHD.mainPaint);

			// Map.drawMap(canvasScreenBuffer);
			Map.DrawGame(canvasScreenBuffer);
			// PikachuActivity.matrix.postScale(SCREEN_WIDTH / 1024f,
			// SCREEN_HEIGHT / 600f);//dua vap file map
			mainPaint.setAlpha(255);
			mainCanvas.drawBitmap(bitmapScreenBuffer, 0, 0, mainPaint);

			// mainPaint.setStyle(Style.FILL);
			// mainPaint.setARGB(150, 0, 0, 0);
			// mainCanvas.drawRect(0, 0, SCREEN_WIDTH, DEF.BUTTON_IGM_H +
			// DEF.BUTTON_IGM_H/4, mainPaint);
			// mainPaint.setAlpha(255);
			drawHUD(mainCanvas);
			break;
		case MESSAGE_DTOR:
			isIngame = false;
			break;
		}
	}

	public static void drawHUD(Canvas c)
	{
		if (gameMode == 1)
			fontsmall_White.drawString(mainCanvas, "Level : " + (mcurrentlevel +1), DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		fontsmall_White.drawString(mainCanvas, "Score : " + Map.score, DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "SCORE : " + Map.mAllScore + "/" + Map.LevelMap[mcurrentlevel][0], DEF.LABEL_LEVEL_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "Failed: " + (Map.tapFail) + "/" + Map.LevelMap[mcurrentlevel][1], DEF.LABEL_SCORE_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);

		if (isTouchDrapInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H))
			spriteDPad.drawAFrame(Diamond.mainCanvas, DEF.FRAME_PAUSE_HIGHTLIGHT, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);
		else
			spriteDPad.drawAFrame(Diamond.mainCanvas, DEF.FRAME_PAUSE_NORMAL, DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y);

		//fontsmall_Yellow.drawString(mainCanvas, "Timer: " + Map.totalTimePlayGame / 1000, DEF.BUTTON_TIMER_BAR_X, DEF.LABEL_LEVEL_Y, BitmapFont.ALIGN_LEFT);
		//fontsmall_Yellow.drawString(mainCanvas, "Level: " + (mcurrentlevel +1), DEF.BUTTON_TIMER_BAR_X, DEF.LABEL_SCORE_Y, BitmapFont.ALIGN_LEFT);

		//timer bar		
		spriteDPad.drawAFrame(Diamond.mainCanvas, DEF.FRAME_TIMER_BAR_0, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);

		double width = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_TIMER_BAR_1);
		
		
		String text = "";
		if (gameMode == 0)
		{
			
			text = "" +(Map.MAX_TIMER - Map.timer) / 6000000 + ":" + (Map.MAX_TIMER - Map.timer) / 1000;
			width = width - width * (Map.timer * 1.0) / Map.MAX_TIMER;
		}
		else
		{
			
			int percent = (Map.score*100 / Map.targetScore);
			if(percent >100)
				percent = 100;
			text = "" + percent + "%";
			width = width - width*(100.0 - percent) / 100;
		}
		Diamond.mainCanvas.save();
		Diamond.mainCanvas.clipRect(DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y, DEF.BUTTON_TIMER_BAR_X + (int) width, DEF.BUTTON_TIMER_BAR_Y + 32);
		spriteDPad.drawAFrame(Diamond.mainCanvas, DEF.FRAME_TIMER_BAR_1, DEF.BUTTON_TIMER_BAR_X, DEF.BUTTON_TIMER_BAR_Y);
		Diamond.mainCanvas.restore();
		fontsmall_White.drawString(mainCanvas,text , SCREEN_WIDTH / 2, DEF.BUTTON_TIMER_BAR_Y, BitmapFont.ALIGN_CENTER);
	}

	public static void updatetouch()
	{
		if (isKeyReleased(KeyEvent.KEYCODE_BACK) || isTouchReleaseInRect(DEF.BUTTON_IGM_X, DEF.BUTTON_IGM_Y, DEF.BUTTON_IGM_W, DEF.BUTTON_IGM_H)) {
			changeState(STATE_IGM, true, false);
		}

	}
	// set position of dpad

}
