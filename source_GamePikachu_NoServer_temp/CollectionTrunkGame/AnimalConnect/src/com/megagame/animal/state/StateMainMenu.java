package com.megagame.animal.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.megagame.animal.BitmapFont;
import com.megagame.animal.Dialog;
import com.megagame.animal.GameLayer;
import com.megagame.animal.GameLib;
import com.megagame.animal.IConstant;
import com.megagame.animal.AnimalActivity;
import com.megagame.animal.SoundManager;

import resolution.DEF;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.KeyEvent;



public class StateMainMenu extends AnimalActivity implements IConstant
{

	public static Bitmap splashBitmap = null;
	public static Bitmap gameTitleBitmap = null;
	public static final int MENU_PLAY = 0;
	public static final int MENU_HOW_PLAY = 1;
	public static final int MENU_OPTION_SOUND = 2;
	public static final int MENU_CREDIT = 3;
	public static final int MENU_EXIT = 4;
	public static int[] arrayMenu = { MENU_PLAY, MENU_HOW_PLAY, MENU_OPTION_SOUND, MENU_CREDIT, MENU_EXIT };
	public static String[] arrayMenuString = { "PLAY", "HOW TO PLAY", "SOUND :", "CREDIT", "EXIT" };
	public static int MENU_BEGIN_X = 0; //will iit in contructor
	public static int MENU_BEGIN_Y = 200;
	public static int MENU_ELEMENT_W = 0; //will iit in contructor
	public static int MENU_ELEMENT_H = 0; //will iit in contructor
	public static int MENU_ELEMENT_SPACE = 20;
	public static int MENU_H = SCREEN_HEIGHT;

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:

			if (splashBitmap == null) 
				splashBitmap = loadImageFromAsset("image/splash.png");
			MENU_ELEMENT_H = StateGameplay.spriteDPad.getFrameHeight(DEF.FRAME_BUTTON_NORMAL);
			MENU_ELEMENT_W = StateGameplay.spriteDPad.getFrameWidth(DEF.FRAME_BUTTON_NORMAL);
			MENU_BEGIN_X = SCREEN_WIDTH - MENU_ELEMENT_W/2 - 50;
			
		
			
			
			MENU_ELEMENT_SPACE = (SCREEN_HEIGHT -((arrayMenu.length + 2)*MENU_ELEMENT_H)) /arrayMenu.length;
			MENU_ELEMENT_SPACE -=5;
			if(MENU_ELEMENT_SPACE < 0 )
				MENU_ELEMENT_SPACE = 0;
			MENU_H = (MENU_ELEMENT_SPACE+ MENU_ELEMENT_H)*arrayMenu.length;
			int temp_y = (SCREEN_HEIGHT - MENU_H)/2;
			if(temp_y < 50)
				temp_y = 50 - temp_y;
			else
				temp_y = 0;
			MENU_BEGIN_Y = (SCREEN_HEIGHT - MENU_H)/2 + MENU_ELEMENT_H/2 + temp_y;
			
			
			
			
			
			
			StateGameplay.isIngame  = false;		
			//SoundManager.playSound(SoundManager.SOUND_TITLE,1);
			SoundManager.playsoundLoop(0, true);
			SoundManager.pausesoundLoop(1);

			break;
		case MESSAGE_UPDATE:

			int temp = -1;
			if (Dialog.isShowDialog) {
				Dialog.updateDialog();
				return;
			} 
			if(isKeyReleased(KeyEvent.KEYCODE_BACK))
			{
				Dialog.showDialog(Dialog.DIALOG_TYPE_CONFIRM, "","DO YOU WANT TO EXIT?",Dialog.DIALOG_NEXTSTATE_EXIT);
				break;
			}
				for (int i = 0; i < arrayMenu.length; i++) {
					int y = MENU_BEGIN_Y + i * (MENU_ELEMENT_H + MENU_ELEMENT_SPACE);
					if (isTouchReleaseInRect(MENU_BEGIN_X - MENU_ELEMENT_W / 2, y - MENU_ELEMENT_H / 2, MENU_ELEMENT_W, MENU_ELEMENT_H)) {
						
						if(i!=MENU_OPTION_SOUND)
							SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
						switch (i)
						{
						case MENU_PLAY:
							changeState(STATE_SELECT_LEVEL);
							break;
						case MENU_OPTION_SOUND:
							isEnableSound = !isEnableSound;
							if(!isEnableSound){
							SoundManager.pausesoundLoop(0);						
							}else
							{
								SoundManager.playsoundLoop(0, true);
							}
							
							//changeState(STATE_OPTION);
							break;							
							
						case MENU_HOW_PLAY:
							changeState(STATE_HOW_TO_PLAY);
							break;
						case MENU_CREDIT:
							changeState(STATE_CREADIT);
							break;										
						case MENU_EXIT:							
							Dialog.showDialog(Dialog.DIALOG_TYPE_CONFIRM, "","DO YOU WANT TO EXIT?",Dialog.DIALOG_NEXTSTATE_EXIT);
							break;
						default:
							break;
						}
					}
				}
			
			break;
		case MESSAGE_PAINT:

			mainCanvas.drawBitmap(splashBitmap, 0, 0, mainPaint);
			bitmapScreenBuffer.eraseColor(Color.TRANSPARENT);

		

			mainPaint.setAlpha(255);
			mainCanvas.drawBitmap(bitmapScreenBuffer, 0, 0, mainPaint);

			
			mainCanvas.drawBitmap(gameTitleBitmap,0, 0, mainPaint);	
			if (Dialog.isShowDialog) {
				Dialog.drawDialog(mainCanvas);
				return;
			} 
			android_FontNormal.setColor(Color.WHITE);
				for (int i = 0; i < arrayMenu.length; i++) {
					int y = MENU_BEGIN_Y + i * (MENU_ELEMENT_H + MENU_ELEMENT_SPACE);
					if (isTouchDrapInRect(MENU_BEGIN_X - MENU_ELEMENT_W / 2, y - MENU_ELEMENT_H / 2, MENU_ELEMENT_W, MENU_ELEMENT_H))
						StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_BUTTON_HIGHTLIGHT, MENU_BEGIN_X, y);
					else
						StateGameplay.spriteDPad.drawAFrame(mainCanvas, DEF.FRAME_BUTTON_NORMAL, MENU_BEGIN_X, y);
					Rect rect = new Rect();
					android_FontNormal.getTextBounds("agHa", 0,3, rect);
					
				if(arrayMenu[i] == MENU_OPTION_SOUND)
				{
					if(isEnableSound)
mainCanvas.drawText( arrayMenuString[i] + "ON", MENU_BEGIN_X, y + rect.height() / 2, android_FontNormal);
//						fontsmall_White.drawString(mainCanvas, arrayMenuString[i] +"ON", MENU_BEGIN_X, y - fontsmall_White.getFontHeight()/2, BitmapFont.ALIGN_CENTER);
					else
					mainCanvas.drawText( arrayMenuString[i] +"OFF", MENU_BEGIN_X, y + rect.height() / 2, android_FontNormal);
//						fontsmall_White.drawString(mainCanvas, arrayMenuString[i] +"OFF", MENU_BEGIN_X, y - fontsmall_White.getFontHeight()/2, BitmapFont.ALIGN_CENTER);
				}
				else
				mainCanvas.drawText( arrayMenuString[i], MENU_BEGIN_X, y + rect.height() / 2, android_FontNormal);
					//fontsmall_White.drawString(mainCanvas, arrayMenuString[i], MENU_BEGIN_X, y - fontsmall_White.getFontHeight()/2, BitmapFont.ALIGN_CENTER);
			}

			//if(System.currentTimeMillis()%1000 > 500)
			//if (fontbig_White != null)
			//	fontbig_White.drawString(mainCanvas, "TOUCH THE SCREEN TO CONTINUE", SCREEN_WIDTH/2, SCREEN_HEIGHT - 150, BitmapFont.ALIGN_CENTER);	

			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
