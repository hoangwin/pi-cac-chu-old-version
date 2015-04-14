package com.xiaxio.monster.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.monster.BitmapFont;
import com.xiaxio.monster.GameLayer;
import com.xiaxio.monster.GameLib;
import com.xiaxio.monster.IConstant;
import com.xiaxio.monster.SoundManager;
import com.xiaxio.monster.MonsterActivity;

import resolution.DEF;

import android.R.color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;




public class StateCreadit extends MonsterActivity implements IConstant 
{	
	//
	
	public static RectF rectF = null;
	public static synchronized void SendMessage(int type) {

		switch (type) {
		case MESSAGE_CTOR:
			rectF = new RectF(DEF.CREADIT_BACKGROUND_X, DEF.CREADIT_BACKGROUND_Y, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W, DEF.CREADIT_BACKGROUND_Y + DEF.CREADIT_BACKGROUND_H);
			DEF.CREADIT_CONTENT_SPACE_H =(int)(80*scaleY);// fontsmall_Yellow.getFontHeight();
			DEF.CREADIT_CONTENT_Y = 3*DEF.CREADIT_CONTENT_SPACE_H/2  + DEF.CREADIT_TITLE_Y;
			break;
		case MESSAGE_UPDATE:
			if (isKeyReleased(KeyEvent.KEYCODE_BACK)|| MonsterActivity.isTouchReleaseInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.CREADIT_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
			{
				SoundManager.playSound(SoundManager.SOUND_BACK, 1);
				changeState(STATE_MAINMENU, true, true);
			}	
			
			break;
		case MESSAGE_PAINT:
			MonsterActivity.mainPaint.setColor(Color.BLACK);
			MonsterActivity.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, MonsterActivity.mainPaint);									
			mainCanvas.drawBitmap(StateMainMenu.splashBitmap, 0,0, mainPaint);
			
			MonsterActivity.mainPaint.setStyle(Style.FILL);
			MonsterActivity.mainPaint.setARGB(200, 0, 0, 0);
			MonsterActivity.mainCanvas.drawRect(0, 0, MonsterActivity.SCREEN_WIDTH, MonsterActivity.SCREEN_HEIGHT, MonsterActivity.mainPaint);
			//FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//FishRescue.mainPaint.setARGB(200, 0, 0, 0);
			//FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//DEF.CREADIT_TITLE_CONTENT_SPACE_H
			//draw text
			
			mainCanvas.drawText( "CREADIT", DEF.CREADIT_CONTENT_X, DEF.CREADIT_TITLE_Y, android_FontNormal);
			//MonsterActivity.fontbig_Yellow.drawString(MonsterActivity.mainCanvas, "CREADIT", DEF.CREADIT_TITLE_X, DEF.CREADIT_TITLE_Y, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText("Company", DEF.CREADIT_CONTENT_X,DEF.CREADIT_CONTENT_Y + DEF.CREADIT_CONTENT_SPACE_H, android_FontNormal);
			//MonsterActivity.fontsmall_Yellow.drawString(MonsterActivity.mainCanvas, "Company", DEF.CREADIT_CONTENT_X , DEF.CREADIT_CONTENT_Y + DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText("XiaXio",DEF.CREADIT_CONTENT_X,DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H, android_FontNormal);
			//MonsterActivity.fontsmall_White.drawString(MonsterActivity.mainCanvas, "Mega Free Games", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H,  BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText("ProGrammer",DEF.CREADIT_CONTENT_X,DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, android_FontNormal);
			//MonsterActivity.fontsmall_Yellow.drawString(MonsterActivity.mainCanvas, "ProGrammer", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText("Cao_gia", DEF.CREADIT_CONTENT_X,DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, android_FontNormal);
			//MonsterActivity.fontsmall_White.drawString(MonsterActivity.mainCanvas, "Cao_gia", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			
			mainCanvas.drawText("Toanstt",DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y +5* DEF.CREADIT_CONTENT_SPACE_H, android_FontNormal);
			//MonsterActivity.fontsmall_White.drawString(MonsterActivity.mainCanvas, "Toanstt", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 5*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			
			if (MonsterActivity.isTouchDrapInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.CREADIT_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, DEF.CREADIT_BACKGROUND_Y  + 8);
			else
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, DEF.CREADIT_BACKGROUND_Y  +8);
			
			
			
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
