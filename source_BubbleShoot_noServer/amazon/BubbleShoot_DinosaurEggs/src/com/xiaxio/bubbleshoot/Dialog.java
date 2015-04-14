package com.xiaxio.bubbleshoot;

import com.xiaxio.bubbleshoot.state.StateGameplay;
import com.xiaxio.bubbleshoot.state.StateMainMenu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Dialog
{
	public static final int DIALOG_TYPE_CONFIRM = 0;
	public static final int DIALOG_TYPE_NOTICE = 1;
	public static final int DIALOG_TYPE_WAITING = 2;

	//
	public static final int DIALOG_NEXTSTATE_EXIT = 0;
	public static final int DIALOG_BACK_TO_MAINMENU_EXIT = 1;
	public static final int DIALOG_RESTART_GAME = 2;
	public static final int DIALOG_NEXTSTATE_CLOSE_LEADERBOARD_DIALOG = 3;
	//
	public static int dialogType = -1;
	public static int dialogNextState = -1;
	public static String dialogText = null;

	//
	public static int DIALOG_CONFIRM_X = 0;
	public static int DIALOG_CONFIRM_Y = MainActivity.SCREEN_HEIGHT / 4;
	public static int DIALOG_CONFIRM_W = MainActivity.SCREEN_WIDTH - 2 * MainActivity.SCREEN_WIDTH /20;
	public static int DIALOG_CONFIRM_H = (int)(653*MainActivity.scaleY);

	public static RectF rectF = null;
	public static boolean isShowDialog = false;

	public static void showDialog(int type, String textStringLabel, String textString, int nextState)
	{
		isShowDialog = true;
		dialogType = type;
		dialogText = textString;
		dialogNextState = nextState;
		rectF = new RectF(DIALOG_CONFIRM_X, DIALOG_CONFIRM_Y, DIALOG_CONFIRM_X + DIALOG_CONFIRM_W, DIALOG_CONFIRM_Y + DIALOG_CONFIRM_H);
	}

	public static void hideDialog()
	{
		isShowDialog = false;
	}

	public static void drawDialog(Canvas c)
	{
		
		//Monster.mainPaint.setStyle(Style.FILL);
		//Monster.mainPaint.setARGB(200, 0, 0, 0);
		//c.drawRect(0, 0, Monster.SCREEN_WIDTH, Monster.SCREEN_HEIGHT, Monster.mainPaint);
		//Monster.mainPaint.setStyle(Style.FILL_AND_STROKE);
		//Monster.mainPaint.setColor(Color.argb(170,128, 194, 32));
		//c.drawRoundRect(rectF, 25, 25, Monster.mainPaint);
		MainActivity.spriteUI.drawAFrame(c, 0, DIALOG_CONFIRM_X, DIALOG_CONFIRM_Y);
		
		
		//draw text
		Rect textBounds =  new  Rect();
		MainActivity.android_FontNormal.getTextBounds("Maig", 0, "Maig".length(), textBounds);		
		MainActivity.mainCanvas.drawText( dialogText , MainActivity.SCREEN_WIDTH / 2,   DIALOG_CONFIRM_Y+ textBounds.height()*4 ,MainActivity.android_FontNormal);
		//Monster.fontbig_White.drawString(c, dialogText, Monster.SCREEN_WIDTH / 2, DIALOG_CONFIRM_Y+ offset , DIALOG_CONFIRM_W - 20, BitmapFont.ALIGN_CENTER);
		int y = DIALOG_CONFIRM_Y + DIALOG_CONFIRM_H - 4*DEF.DIALOG_BUTTON_CONFIRM_W/2;// PikachuActivity.SCREEN_HEIGHT / 6 + DIALOG_CONFIRM_H - 40 - 20;
		int x1 = DIALOG_CONFIRM_X + DIALOG_CONFIRM_W/4 ;
		int x2 =  MainActivity.SCREEN_WIDTH - DIALOG_CONFIRM_X - DIALOG_CONFIRM_W/4 - DEF.DIALOG_BUTTON_CONFIRM_W ;
		//draw button
		switch (dialogType)
		{
		case DIALOG_TYPE_CONFIRM:

			if (MainActivity.isTouchDrapInRect(x1, y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, x1, y);
			else
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, x1, y);

			if (MainActivity.isTouchDrapInRect(x2, y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
			   
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_OK_HIGHTLIGHT, x2, y);
			else
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_OK_NORMAL, x2, y);
			break;
		case DIALOG_TYPE_NOTICE:
			if (MainActivity.isTouchDrapInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_OK_HIGHTLIGHT, x2, y);
			else
				StateGameplay.spriteDPad.drawAFrame(MainActivity.mainCanvas, DEF.FRAME_OK_NORMAL, x2, y);
		
			
			break;
		case	DIALOG_TYPE_WAITING:
			break;
		default:
			break;
		}

		//		fontsmall_White.drawString(mainCanvas, arrayMenuString[i], MENU_POSITION_X, y - 20, BitmapFont.ALIGN_CENTER);		

	}

	public static void updateDialog()
	{
		int y = DIALOG_CONFIRM_Y + DIALOG_CONFIRM_H - 4*DEF.DIALOG_BUTTON_CONFIRM_W/2;// PikachuActivity.SCREEN_HEIGHT / 6 + DIALOG_CONFIRM_H - 40 - 20;
		int x1 = DIALOG_CONFIRM_X + DIALOG_CONFIRM_W/4 ;
		int x2 =  MainActivity.SCREEN_WIDTH - DIALOG_CONFIRM_X - DIALOG_CONFIRM_W/4 - DEF.DIALOG_BUTTON_CONFIRM_W ;
		switch (dialogType)
		{
		case DIALOG_TYPE_CONFIRM:

			if (MainActivity.isTouchReleaseInRect(x1 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//cancel
			{
				SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
				hideDialog();
			} else if (MainActivity.isTouchReleaseInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//ok
			{
				SoundManager.playSound(SoundManager.SOUND_SELECT, 1);
				Dialog.hideDialog();
				switch (dialogNextState)
				{
				case DIALOG_NEXTSTATE_EXIT:

					MainActivity.mainActivity.finish();
					break;
				case DIALOG_BACK_TO_MAINMENU_EXIT:
					MainActivity.mainActivity.saveGame();
					MainActivity.changeState(MainActivity.STATE_MAINMENU, true, true);
					break;
				case DIALOG_RESTART_GAME:
					MainActivity.changeState(MainActivity.STATE_GAMEPLAY, true, true);
				break;
				default:
					break;
				}

			}
			break;
		case DIALOG_TYPE_NOTICE:
			if (MainActivity.isTouchReleaseInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//ok
			{
				switch (dialogNextState)
				{
				case DIALOG_NEXTSTATE_CLOSE_LEADERBOARD_DIALOG:
					hideDialog();
					MainActivity.changeState(MainActivity.mPreState);
					break;
				//case DIALOG_BACK_TO_MAINMENU_EXIT:
				//	break;

				default:
					break;
				}
				Dialog.hideDialog();
			}
			break;
		default:
			break;
		}

	}
}
