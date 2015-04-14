package com.xiaxio.petpop.state;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.xiaxio.petpop.BitmapFont;
import com.xiaxio.petpop.Dialog;
import com.xiaxio.petpop.GameLayer;
import com.xiaxio.petpop.GameLib;
import com.xiaxio.petpop.GameThread;
import com.xiaxio.petpop.IConstant;
import com.xiaxio.petpop.Map;
import com.xiaxio.petpop.SoundManager;
import com.xiaxio.petpop.PetPop;

import resolution.DEF;

import android.R.color;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.KeyEvent;

public class StateWinLose extends PetPop
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
			

			PetPop.mainActivity.saveGame();
			if(StateGameplay.gameMode ==1)
				if(PetPop.mcurrentlevel >= PetPop.mLevelUnlock)
					PetPop.mLevelUnlock++;
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
			if (PetPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_GAMEPLAY);

			if (isKeyReleased(KeyEvent.KEYCODE_BACK) || PetPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				changeState(STATE_MAINMENU);
			if (PetPop.mcurrentlevel < 15 && StateGameplay.gameMode ==1)
				if (PetPop.isTouchReleaseInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H)) {
					PetPop.mcurrentlevel++;
					changeState(STATE_HINT);
				}

			break;
		case MESSAGE_PAINT:
			StateGameplay.SendMessage(MESSAGE_PAINT);
			if (Dialog.isShowDialog) {
				Dialog.drawDialog(mainCanvas); 
				return;
			}

			PetPop.mainPaint.setStyle(Style.FILL);
			PetPop.mainPaint.setARGB(220, 0, 0, 0);
			PetPop.mainCanvas.drawRect(0, 0, PetPop.SCREEN_WIDTH, PetPop.SCREEN_HEIGHT, PetPop.mainPaint);
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
				PetPop.mainCanvas.drawBitmap(finishEffectNormal, matrix, PetPop.mainPaint);
			else
				PetPop.mainCanvas.drawBitmap(finishEffectClassic, matrix, PetPop.mainPaint);
			//draw text
			if (scale >= 1) {
				int tempHeight = PetPop.fontbig_White.getFontHeight();
				Rect textBounds =  new  Rect();
				android_FontBig.getTextBounds("Maig", 0, "Maig".length(), textBounds);
				mainCanvas.drawText( "YOUR SCORE :",  SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 -  textBounds.height(), android_FontBigBoder);
				mainCanvas.drawText( "YOUR SCORE :",  SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 -  textBounds.height(), android_FontBig);
				mainCanvas.drawText(  ""+ Map.score,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBigBoder);
				mainCanvas.drawText(  ""+ Map.score,  SCREEN_WIDTH / 2,SCREEN_HEIGHT / 2, android_FontBig);
				//Monster.fontbig_White.drawString(Monster.mainCanvas, "YOUR SCORE :", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 - tempHeight / 2, BitmapFont.ALIGN_CENTER);
				//Monster.fontbig_White.drawString(Monster.mainCanvas, ""+ Map.score, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + tempHeight / 2, BitmapFont.ALIGN_CENTER);

				if (!PetPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_RETRY_NORMAL, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);
				else
					StateGameplay.spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_RETRY_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X1, DEF.WINLOSE_BUTTON_Y1);

				if (!PetPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
					StateGameplay.spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_MAINMENU_NORMAL, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				else
					StateGameplay.spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_MAINMENU_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X2, DEF.WINLOSE_BUTTON_Y2);
				if (PetPop.mcurrentlevel < 15 && StateGameplay.gameMode ==1)
					if (!PetPop.isTouchDrapInRect(DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
						StateGameplay.spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_BUTTON_RIGHT_NORMAL, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
					else
						StateGameplay.spriteDPad.drawAFrame(PetPop.mainCanvas, DEF.FRAME_BUTTON_RIGHT_HIGHTLIGHT, DEF.WINLOSE_BUTTON_X3, DEF.WINLOSE_BUTTON_Y3);
			}
			break;
		case MESSAGE_DTOR:
			break;
		}
	}
}
