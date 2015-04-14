package com.mega.android.ConnectCute.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.mega.android.ConnectCute.BitmapFont;
import com.mega.android.ConnectCute.DEF;
import com.mega.android.ConnectCute.GameLayer;
import com.mega.android.ConnectCute.GameLib;
import com.mega.android.ConnectCute.IConstant;
import com.mega.android.ConnectCute.ConnectCute;
import com.mega.android.ConnectCute.SoundManager;



import android.R.color;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.KeyEvent;




public class StateCreadit extends ConnectCute implements IConstant 
{	
	//
	
	public static RectF rectF = null;
	public static synchronized void SendMessage(int type) {

		switch (type) {
		case MESSAGE_CTOR:
			rectF = new RectF(DEF.CREADIT_BACKGROUND_X, DEF.CREADIT_BACKGROUND_Y, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W, DEF.CREADIT_BACKGROUND_Y + DEF.CREADIT_BACKGROUND_H);
			DEF.CREADIT_CONTENT_SPACE_H = (int)(60*scaleY);
			DEF.CREADIT_CONTENT_Y = 3*DEF.CREADIT_CONTENT_SPACE_H/2  + DEF.CREADIT_TITLE_Y;
			break;
		case MESSAGE_UPDATE:
			if (isKeyReleased(KeyEvent.KEYCODE_BACK)|| ConnectCute.isTouchReleaseInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.CREADIT_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
			{
				SoundManager.playSound(SoundManager.SOUND_BACK, 1);
				changeState(STATE_MAINMENU, true, true);
			}	
			
			break;
		case MESSAGE_PAINT:
			ConnectCute.mainPaint.setColor(Color.BLACK);
			ConnectCute.mainPaint.setStyle(Style.FILL);
			mainCanvas.drawBitmap(StateMainMenu.splashBitmap, 0,0, mainPaint);
			
			ConnectCute.mainPaint.setStyle(Style.FILL);
			ConnectCute.mainPaint.setARGB(200, 0, 0, 0);
			ConnectCute.mainCanvas.drawRect(0, 0, ConnectCute.SCREEN_WIDTH, ConnectCute.SCREEN_HEIGHT, ConnectCute.mainPaint);
			//FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//FishRescue.mainPaint.setARGB(200, 0, 0, 0);
			//FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//DEF.CREADIT_TITLE_CONTENT_SPACE_H
			//draw text
			//ConnectCute.fontbig_Yellow.drawString(ConnectCute.mainCanvas, "CREADIT", DEF.CREADIT_TITLE_X, DEF.CREADIT_TITLE_Y, BitmapFont.ALIGN_CENTER);
			ConnectCute.mainCanvas.drawText("CREADIT" , DEF.CREADIT_TITLE_X, DEF.CREADIT_TITLE_Y, ConnectCute.android_FontYellow);
			
			//ConnectCute.fontsmall_Yellow.drawString(ConnectCute.mainCanvas, "Company", DEF.CREADIT_CONTENT_X , DEF.CREADIT_CONTENT_Y + DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			ConnectCute.mainCanvas.drawText("Company" ,DEF.CREADIT_CONTENT_X,  DEF.CREADIT_CONTENT_Y + DEF.CREADIT_CONTENT_SPACE_H, ConnectCute.android_FontYellow);
			
			//ConnectCute.fontsmall_White.drawString(ConnectCute.mainCanvas, "CaoGia Entertaiment LTD", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H,  BitmapFont.ALIGN_CENTER);
			ConnectCute.mainCanvas.drawText("MEGA Entertaiment LTD" ,DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 2*DEF.CREADIT_CONTENT_SPACE_H, ConnectCute.android_FontYellow);
			
			//ConnectCute.fontsmall_Yellow.drawString(ConnectCute.mainCanvas, "ProGrammer", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, BitmapFont.ALIGN_CENTER);
			ConnectCute.mainCanvas.drawText("ProGrammer" ,DEF.CREADIT_CONTENT_X,  DEF.CREADIT_CONTENT_Y + 3*DEF.CREADIT_CONTENT_SPACE_H, ConnectCute.android_FontYellow);
			
			//ConnectCute.fontsmall_White.drawString(ConnectCute.mainCanvas, "Caogia", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			ConnectCute.mainCanvas.drawText("Cao gia" ,DEF.CREADIT_CONTENT_X,  DEF.CREADIT_CONTENT_Y + 4*DEF.CREADIT_CONTENT_SPACE_H, ConnectCute.android_FontYellow);
			
			//ConnectCute.fontsmall_White.drawString(ConnectCute.mainCanvas, "Toan_stt", DEF.CREADIT_CONTENT_X, DEF.CREADIT_CONTENT_Y + 5*DEF.CREADIT_CONTENT_SPACE_H, DEF.CREADIT_BACKGROUND_W - 60, BitmapFont.ALIGN_CENTER);
			ConnectCute.mainCanvas.drawText("Toan stt" ,DEF.CREADIT_CONTENT_X,  DEF.CREADIT_CONTENT_Y +5*DEF.CREADIT_CONTENT_SPACE_H, ConnectCute.android_FontYellow);
			
			if (ConnectCute.isTouchDrapInRect(DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W - 8, DEF.CREADIT_BACKGROUND_Y + 8, DEF.BUTTON_CANCEL_CONFIRM_W, DEF.BUTTON_CANCEL_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(ConnectCute.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, DEF.CREADIT_BACKGROUND_Y  + 8);
			else
				StateGameplay.spriteDPad.drawAFrame(ConnectCute.mainCanvas, DEF.FRAME_CANCEL_NORMAL, DEF.CREADIT_BACKGROUND_X + DEF.CREADIT_BACKGROUND_W - DEF.BUTTON_CANCEL_CONFIRM_W-  8, DEF.CREADIT_BACKGROUND_Y  +8);
			
			
			
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
