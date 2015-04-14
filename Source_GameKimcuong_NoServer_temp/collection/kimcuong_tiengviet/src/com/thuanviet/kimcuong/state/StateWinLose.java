package com.thuanviet.kimcuong.state;

import java.io.IOException;
import java.util.ArrayList;

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


import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;

public class StateWinLose extends Kimcuong
{
	public static RectF rectF;
	public static int MENU_H = SCREEN_HEIGHT;
	public static int m_start = 0;
	public static boolean isWin = false;
	public static Bitmap finishEffectNormal;
	public static Bitmap finishEffectClassic;

	public static synchronized void SendMessage(int type)
	{

		switch (type)
		{
		case MESSAGE_CTOR:
			if (finishEffectNormal == null)
			{
				finishEffectNormal = GameLib.loadImageFromAsset("image/winanim.png");
				finishEffectNormal = Bitmap.createScaledBitmap(finishEffectNormal, SCREEN_WIDTH, SCREEN_HEIGHT, true);
			}
			
			if (finishEffectClassic == null)
			{
				finishEffectClassic = GameLib.loadImageFromAsset("image/completed.png");
				finishEffectClassic = Bitmap.createScaledBitmap(finishEffectClassic, SCREEN_WIDTH, SCREEN_HEIGHT, true);
			}
			

			Kimcuong.mainActivity.saveGame();
			if(StateGameplay.gameMode ==1)
				if(Kimcuong.mcurrentlevel >= Kimcuong.mLevelUnlock)
					Kimcuong.mLevelUnlock++;
			SoundManager.pausesoundLoop(0);
			//if (isWin)
				SoundManager.playSound(SoundManager.SOUND_WIN, 1);
			//else
			//	SoundManager.playSound(SoundManager.SOUND_LOSE, 1);

			DEF.WINLOSE_BUTTON_X2 = SCREEN_WIDTH / 2 - DEF.DIALOG_BUTTON_CONFIRM_H / 2;
			DEF.WINLOSE_BUTTON_X1 = DEF.WINLOSE_BUTTON_X2 - 2 * DEF.DIALOG_BUTTON_CONFIRM_W;
			DEF.WINLOSE_BUTTON_X3 = DEF.WINLOSE_BUTTON_X2 + 2 * DEF.DIALOG_BUTTON_CONFIRM_W;
			DEF.WINLOSE_BUTTON_Y1 = (int) (900 * SCREEN_HEIGHT * 1.0 / 1280);
			DEF.WINLOSE_BUTTON_Y2 = DEF.WINLOSE_BUTTON_Y3 = DEF.WINLOSE_BUTTON_Y1;
			break;
		case MESSAGE_UPDATE:
			if (frameCountCurrentState < 20)
				return;
			if (Kimcuong.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_GAMEPLAY);

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || Kimcuong.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_MAINMENU);
			if (Kimcuong.mcurrentlevel < 15 && StateGameplay.gameMode ==1)
				if (Kimcuong.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					Kimcuong.mcurrentlevel++;
					changeState(STATE_HINT);
				}

			break;
		case MESSAGE_PAINT:
			StateGameplay.SendMessage(MESSAGE_PAINT);
			if (Dialog.isShowDialog) {
				Dialog.drawDialog(mainCanvas); 
				return;
			}

			Kimcuong.mainPaint.setStyle(Style.FILL);
			Kimcuong.mainPaint.setARGB(220, 0, 0, 0);
			Kimcuong.mainCanvas.drawRect(0, 0, Kimcuong.SCREEN_WIDTH, Kimcuong.SCREEN_HEIGHT, Kimcuong.mainPaint);
			//FishRescue.mainPaint.setStyle(Style.FILL_AND_STROKE);
			//FishRescue.mainPaint.setColor(Color.rgb(67, 120, 167));
			//FishRescue.mainCanvas.drawRoundRect(rectF, 25, 25, FishRescue.mainPaint);//WINLOSE_TITLE_CONTENT_SPACE_H
			matrix.reset();
			
			float scale = (1.0f * frameCountCurrentState) / 20;
		
			if (scale > 1)
				scale = 1; 
			matrix.setScale(scale, scale);
			matrix.postTranslate((SCREEN_WIDTH - scale * finishEffectNormal.getWidth()) / 2, (SCREEN_HEIGHT - scale * finishEffectNormal.getHeight()) / 2);
			mainPaint.setAntiAlias(true);
			mainPaint.setFilterBitmap(true); 
			mainPaint.setDither(true);
			if(StateGameplay.gameMode ==0)
				Kimcuong.mainCanvas.drawBitmap(finishEffectNormal, matrix, Kimcuong.mainPaint);
			else
				Kimcuong.mainCanvas.drawBitmap(finishEffectClassic, matrix, Kimcuong.mainPaint);
			//draw text
			if (scale >= 1) {
				int tempHeight = Kimcuong.fontbig_White.getFontHeight();
				Rect textBounds =  new  Rect();
				android_FontBig.getTextBounds("Maig", 0, "Maig".length(), textBounds);
				mainCanvas.drawText( "ĐIỂM :",  SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 -  textBounds.height(), android_FontBig);
				if(StateGameplay.gameMode == 2)					
					mainCanvas.drawText(  ""+ Map.mTopScore,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBig);
				else
					mainCanvas.drawText(  ""+ Map.score,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBig);
				//Kimcuong.fontbig_White.drawString(Kimcuong.mainCanvas, "YOUR SCORE :", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - tempHeight / 2, BitmapFont.ALIGN_CENTER);
				//Kimcuong.fontbig_White.drawString(Kimcuong.mainCanvas, ""+ Map.score, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + tempHeight / 2, BitmapFont.ALIGN_CENTER);

				if (!Kimcuong.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_RETRY_NORMAL, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
				else
					StateGameplay.spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_RETRY_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);

				if (!Kimcuong.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_MAINMENU_NORMAL, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				else
					StateGameplay.spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_MAINMENU_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				if (Kimcuong.mcurrentlevel < 15 && StateGameplay.gameMode ==1)
					if (!Kimcuong.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
						StateGameplay.spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_BUTTON_RIGHT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
					else
						StateGameplay.spriteDPad.drawAFrame(Kimcuong.mainCanvas, DEF.FRAME_BUTTON_RIGHT_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
			}
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
