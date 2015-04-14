package com.xiaxio.petmonsterpop.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.petmonsterpop.BitmapFont;
import com.xiaxio.petmonsterpop.DEF;
import com.xiaxio.petmonsterpop.GameLayer;
import com.xiaxio.petmonsterpop.GameLib;
import com.xiaxio.petmonsterpop.IConstant;
import com.xiaxio.petmonsterpop.Monster;
import com.xiaxio.petmonsterpop.SoundManager;


import android.R.color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;




public class StateCreadit extends Monster implements IConstant 
{	
	//
	
	public static RectF rectF = null;
	public static synchronized void SendMessage(int type) {

		switch (type) {
		case MESSAGE_CTOR:
			rectF = new RectF(DEF.CREADIT_BACKGROUND_X, DEF.CREADIT_BACKGROUND_Y, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W, DEF.CREADIT_BACKGROUND_Y + DEF.CREADIT_BACKGROUND_H);
			DEF.CREADIT_CONTENT_SPACE_H = (int)(50*scaleY);//fontsmall_Yellow.getFontHeight();
			DEF.CREADIT_CONTENT_Y = 3*DEF.CREADIT_CONTENT_SPACE_H/2  + DEF.CREADIT_TITLE_Y;
			break;
		case MESSAGE_UPDATE:
			if (isKeyReleased(KeyEvent.KEYCODE_BACK)|| Monster.isTouchReleaseInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8,  8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
			{
				SoundManager.playSound(SoundManager.SOUND_BACK, 1);
				changeState(STATE_MAINMENU, true, true);
			}	
			
			break;
		case MESSAGE_PAINT:
			Monster.mainPaint.setColor(Color.BLACK);
			Monster.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, Monster.mainPaint);						
			matrix.reset();
			matrix.setScale(SCREEN_WIDTH * 1f / 800, SCREEN_HEIGHT * 1f / 1280);
			mainCanvas.drawBitmap(StateMainMenu.splashBitmap, matrix, mainPaint);
			
			Monster.mainPaint.setStyle(Style.FILL);
			Monster.mainPaint.setARGB(200, 0, 0, 0);
			Monster.mainCanvas.drawRect(0, 0, Monster.SCREEN_WIDTH, Monster.SCREEN_HEIGHT, Monster.mainPaint);
			//FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//FishRescue.mainPaint.setARGB(200, 0, 0, 0);
			//FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//DEF.CREADIT_TITLE_CONTENT_SPACE_H
			//draw text
			mainCanvas.drawText(  "CREADIT",  DEF.CREADIT_TITLE_X, DEF.CREADIT_TITLE_Y, android_FontSmall);
			//TapTapZooCrush.fontbig_Yellow.drawString(TapTapZooCrush.mainCanvas, "CREADIT", DEF.CREADIT_TITLE_X, DEF.CREADIT_TITLE_Y, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText( "Company",  DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 1*DEF.CREADIT_CONTENT_SPACE_H, android_FontSmall);
		//	TapTapZooCrush.fontsmall_Yellow.drawString(TapTapZooCrush.mainCanvas, "Company", DEF.CREADIT_CONTENT_X , DEF.CREADIT_CONTENT_Y + DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText(  "Mega Game",   DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H, android_FontSmall);
		//	TapTapZooCrush.fontsmall_White.drawString(TapTapZooCrush.mainCanvas, "Mega Game", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H,  BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText( "ProGrammer",  DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, android_FontSmall);
		//	TapTapZooCrush.fontsmall_Yellow.drawString(TapTapZooCrush.mainCanvas, "ProGrammer", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText( "Cao_gia",   DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, android_FontSmall);
		//	TapTapZooCrush.fontsmall_White.drawString(TapTapZooCrush.mainCanvas, "Cao_gia", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText( "Toan_stt",  DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 5*DEF.CREADIT_CONTENT_SPACE_H, android_FontSmall);
			//TapTapZooCrush.fontsmall_White.drawString(TapTapZooCrush.mainCanvas, "Toan_stt", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 5*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			
			if (Monster.isTouchDrapInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8,  8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8,  8);
			else
				StateGameplay.spriteDPad.drawAFrame(Monster.mainCanvas, DEF.FRAME_CANCEL_NORMAL, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, 8);
			
			
			
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
