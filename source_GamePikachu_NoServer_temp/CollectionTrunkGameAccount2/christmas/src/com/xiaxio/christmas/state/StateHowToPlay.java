package com.xiaxio.christmas.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.christmas.BitmapFont;
import com.xiaxio.christmas.ChristmasActivity;
import com.xiaxio.christmas.GameLayer;
import com.xiaxio.christmas.GameLib;
import com.xiaxio.christmas.IConstant;
import com.xiaxio.christmas.Map;
import com.xiaxio.christmas.SoundManager;

import resolution.DEF;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;




public class StateHowToPlay extends ChristmasActivity implements IConstant
{
	public static RectF rectF = null;
	public static int page = 0;
	//public static String  textContent = "You have to find the pairs of the same card and the path between two the characters must have a maximun of three lines";
	public static Bitmap bitmapHowToplay;
	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			rectF = new RectF(DEF.HOWTOPLAY_BACKGROUND_X, DEF.HOWTOPLAY_BACKGROUND_Y, DEF.HOWTOPLAY_BACKGROUND_X + DEF.HOWTOPLAY_BACKGROUND_W, DEF.HOWTOPLAY_BACKGROUND_Y + DEF.HOWTOPLAY_BACKGROUND_H);
			//DEF.HOWTOPLAY_BUTTON_W = PikachuActivity.
			break;
		case MESSAGE_UPDATE:
			
			if (isKeyReleased(KeyEvent.KEYCODE_BACK)|| ChristmasActivity.isTouchReleaseInRect(0,0, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H)) {
				SoundManager.playSound(SoundManager.SOUND_BACK, 1);
				if (StateGameplay.isIngame)
					changeState(STATE_IGM, true, true);
				else
					changeState(STATE_MAINMENU, true, true);
			}
			break;
		case MESSAGE_PAINT: 
			if (!StateGameplay.isIngame) {
				ChristmasActivity.mainPaint.setColor(Color.BLACK);
				ChristmasActivity.mainPaint.setStyle(Style.FILL);
				mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, ChristmasActivity.mainPaint);
				matrix.reset();
				matrix.setScale(SCREEN_WIDTH * 1f / 1280, SCREEN_HEIGHT * 1f / 800);
				//mainCanvas.drawBitmap(StateMainMenu.splashBitmap, matrix, mainPaint);
				mainCanvas.drawBitmap(bitmapHowToplay,matrix,mainPaint); 
			} else {
				StateGameplay.SendMessage(MESSAGE_PAINT);
			}
		//	PikachuActivity.mainPaint.setStyle(Style.FILL);
		// PikachuActivity.mainPaint.setARGB(200, 0, 0, 0);
		//	PikachuActivity.mainCanvas.drawRect(0, 0, PikachuActivity.SCREEN_WIDTH, PikachuActivity.SCREEN_HEIGHT, PikachuActivity.mainPaint);
		//PikachuActivity.mainPaint.setStyle(Style.FILL_AND_STROKE);
		// PikachuActivity.mainPaint.setColor(Color.rgb(67, 120, 167));
		// PikachuActivity.mainCanvas.drawRoundRect(rectF, 25, 25, PikachuActivity.mainPaint);//DEF.HOWTOPLAY_CONTENT_SPACE_H

			//draw text

			//PikachuActivity.fontbig_Yellow.drawString(PikachuActivity.mainCanvas, "HOW TO PLAY", DEF.HOWTOPLAY_TITLE_X, DEF.HOWTOPLAY_TITLE_Y, BitmapFont.ALIGN_CENTER);			
				
			//PikachuActivity.fontsmall_Yellow.drawString(PikachuActivity.mainCanvas, textContent, DEF.HOWTOPLAY_CONTENT_X, DEF.HOWTOPLAY_CONTENT_Y + DEF.HOWTOPLAY_CONTENT_SPACE_H,500, 0);
			
			if (ChristmasActivity.isTouchDrapInRect(0, 0, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(ChristmasActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, 0, 0);
			else
				StateGameplay.spriteDPad.drawAFrame(ChristmasActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, 0, 0);
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
