package com.megagame.animal.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.megagame.animal.BitmapFont;
import com.megagame.animal.GameLayer;
import com.megagame.animal.GameLib;
import com.megagame.animal.IConstant;
import com.megagame.animal.AnimalActivity;
import com.megagame.animal.SoundManager;

import resolution.DEF;

import android.R.color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;




public class StateCreadit extends AnimalActivity implements IConstant 
{	
	//
	
	public static RectF rectF = null;
	public static synchronized void SendMessage(int type) {

		switch (type) {
		case MESSAGE_CTOR:
			rectF = new RectF(DEF.CREADIT_BACKGROUND_X, DEF.CREADIT_BACKGROUND_Y, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W, DEF.CREADIT_BACKGROUND_Y + DEF.CREADIT_BACKGROUND_H);
			DEF.CREADIT_CONTENT_SPACE_H = fontsmall_Yellow.getFontHeight();
			DEF.CREADIT_CONTENT_Y = 3*DEF.CREADIT_CONTENT_SPACE_H/2  + DEF.CREADIT_TITLE_Y;
			break;
		case MESSAGE_UPDATE:
			if (isKeyReleased(KeyEvent.KEYCODE_BACK)|| AnimalActivity.isTouchReleaseInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.CREADIT_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
			{
				SoundManager.playSound(SoundManager.SOUND_BACK, 1);
				changeState(STATE_MAINMENU, true, true);
			}	
			
			break;
		case MESSAGE_PAINT:
			AnimalActivity.mainPaint.setColor(Color.BLACK);
			AnimalActivity.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, AnimalActivity.mainPaint);					
			
			mainCanvas.drawBitmap(StateMainMenu.splashBitmap,0,0, mainPaint);
			
			AnimalActivity.mainPaint.setStyle(Style.FILL);
			AnimalActivity.mainPaint.setARGB(200, 0, 0, 0);
			AnimalActivity.mainCanvas.drawRect(0, 0, AnimalActivity.SCREEN_WIDTH, AnimalActivity.SCREEN_HEIGHT, AnimalActivity.mainPaint);
			AnimalActivity.mainPaint.setStyle(Style.FILL_AND_STROKE);
			AnimalActivity.mainPaint.setColor(Color.rgb(67, 120, 167));
			AnimalActivity.mainCanvas.drawRoundRect(rectF, 25, 25, AnimalActivity.mainPaint);//DEF.CREADIT_TITLE_CONTENT_SPACE_H
			//draw text
			AnimalActivity.fontbig_Yellow.drawString(AnimalActivity.mainCanvas, "CREADIT", DEF.CREADIT_TITLE_X, DEF.CREADIT_TITLE_Y, BitmapFont.ALIGN_CENTER);
			
			AnimalActivity.fontsmall_Yellow.drawString(AnimalActivity.mainCanvas, "Company", DEF.CREADIT_CONTENT_X , DEF.CREADIT_CONTENT_Y + DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			
			AnimalActivity.fontsmall_White.drawString(AnimalActivity.mainCanvas, "Mega Game", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H,  BitmapFont.ALIGN_CENTER);
			
			AnimalActivity.fontsmall_Yellow.drawString(AnimalActivity.mainCanvas, "ProGrammer", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			
			AnimalActivity.fontsmall_White.drawString(AnimalActivity.mainCanvas, "Cao_gia", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			
			AnimalActivity.fontsmall_White.drawString(AnimalActivity.mainCanvas, "Toan_stt", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 5*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			
			if (AnimalActivity.isTouchDrapInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.CREADIT_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, DEF.CREADIT_BACKGROUND_Y  + 8);
			else
				StateGameplay.spriteDPad.drawAFrame(AnimalActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, DEF.CREADIT_BACKGROUND_Y  +8);
			
			
			
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
