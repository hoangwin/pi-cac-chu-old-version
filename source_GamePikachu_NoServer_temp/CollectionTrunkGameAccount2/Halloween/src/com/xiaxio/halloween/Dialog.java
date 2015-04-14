package com.xiaxio.halloween;

import com.xiaxio.halloween.state.StateGameplay;
import com.xiaxio.halloween.state.StateMainMenu;

import resolution.DEF;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Dialog
{
	public static final int DIALOG_TYPE_CONFIRM = 0;
	public static final int DIALOG_TYPE_NOTICE = 1;
	//public static int DIALOG_TYPE_CONFIRM = -1;

	//
	public static final int DIALOG_NEXTSTATE_EXIT = 0;
	public static final int DIALOG_BACK_TO_MAINMENU_EXIT = 1;
	public static final int DIALOG_RESTART_GAME = 2;
	//
	public static int dialogType = -1;
	public static int dialogNextState = -1;
	public static String dialogText = null;

	//
	public static int DIALOG_CONFIRM_X = HalloweenActivity.SCREEN_WIDTH / 6;
	public static int DIALOG_CONFIRM_Y = HalloweenActivity.SCREEN_HEIGHT / 6;
	public static int DIALOG_CONFIRM_W = HalloweenActivity.SCREEN_WIDTH - 2 * HalloweenActivity.SCREEN_WIDTH / 6;
	public static int DIALOG_CONFIRM_H = HalloweenActivity.SCREEN_HEIGHT - 2 * HalloweenActivity.SCREEN_HEIGHT / 6;

	public static RectF rectF = null;
	public static boolean isShowDialog = false;

	public static void showDialog(int type, String textStringLabel, String textString, int nextState)
	{
		isShowDialog = true;
		dialogType = type;
		dialogText = textString;
		dialogNextState = nextState;
		rectF = new RectF(HalloweenActivity.SCREEN_WIDTH / 6, HalloweenActivity.SCREEN_HEIGHT / 6, HalloweenActivity.SCREEN_WIDTH / 6 + DIALOG_CONFIRM_W, HalloweenActivity.SCREEN_HEIGHT / 6 + DIALOG_CONFIRM_H);
	}

	public static void hideDialog()
	{
		isShowDialog = false;
	}

	public static void drawDialog(Canvas c)
	{
		int offset =0;
		if(HalloweenActivity.SCREEN_WIDTH<600)
		{
			offset = DIALOG_CONFIRM_H/4;
		}
		HalloweenActivity.mainPaint.setStyle(Style.FILL);
		HalloweenActivity.mainPaint.setARGB(200, 0, 0, 0);
		c.drawRect(0, 0, HalloweenActivity.SCREEN_WIDTH, HalloweenActivity.SCREEN_HEIGHT, HalloweenActivity.mainPaint);
		HalloweenActivity.mainPaint.setStyle(Style.FILL_AND_STROKE);
		HalloweenActivity.mainPaint.setColor(Color.argb(170,0, 0, 0));
		c.drawRoundRect(rectF, 25, 25, HalloweenActivity.mainPaint);
		//draw text
		HalloweenActivity.fontbig_White.drawString(c, dialogText, HalloweenActivity.SCREEN_WIDTH / 2, DIALOG_CONFIRM_Y+ offset , DIALOG_CONFIRM_W - 100, BitmapFont.ALIGN_CENTER);
		int y = DIALOG_CONFIRM_Y + DIALOG_CONFIRM_H - 3*DEF.DIALOG_BUTTON_CONFIRM_W/2;// PikachuActivity.SCREEN_HEIGHT / 6 + DIALOG_CONFIRM_H - 40 - 20;
		int x1 = DIALOG_CONFIRM_X +DIALOG_CONFIRM_W/4 ;
		int x2 =  DIALOG_CONFIRM_X + 3*DIALOG_CONFIRM_W/4 - DEF.DIALOG_BUTTON_CONFIRM_W ;
		//draw button
		switch (dialogType)
		{
		case DIALOG_TYPE_CONFIRM:

			if (HalloweenActivity.isTouchDrapInRect(x1, y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, x1, y);
			else
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, x1, y);

			if (HalloweenActivity.isTouchDrapInRect(x2, y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
			   
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_OK_HIGHTLIGHT, x2, y);
			else
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_OK_NORMAL, x2, y);
			break;
		case DIALOG_TYPE_NOTICE:
			if (HalloweenActivity.isTouchDrapInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_OK_HIGHTLIGHT, x2, y);
			else
				StateGameplay.spriteDPad.drawAFrame(HalloweenActivity.mainCanvas, DEF.FRAME_OK_NORMAL, x2, y);
			break;
		default:
			break;
		}

		//		fontsmall_White.drawString(mainCanvas, arrayMenuString[i], MENU_POSITION_X, y - 20, BitmapFont.ALIGN_CENTER);		

	}

	public static void updateDialog()
	{
		int y =DIALOG_CONFIRM_Y + DIALOG_CONFIRM_H - 3*DEF.DIALOG_BUTTON_CONFIRM_H/2;//
		int x1 = DIALOG_CONFIRM_X +DIALOG_CONFIRM_W/4 ;
		int x2 = DIALOG_CONFIRM_X + 3*DIALOG_CONFIRM_W/4 - DEF.DIALOG_BUTTON_CONFIRM_W ;
		switch (dialogType)
		{
		case DIALOG_TYPE_CONFIRM:

			if (HalloweenActivity.isTouchReleaseInRect(x1 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//cancel
			{
				hideDialog();
			} else if (HalloweenActivity.isTouchReleaseInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//ok
			{
				Dialog.hideDialog();
				switch (dialogNextState)
				{
				case DIALOG_NEXTSTATE_EXIT:

					HalloweenActivity.mainActivity.finish();
					break;
				case DIALOG_BACK_TO_MAINMENU_EXIT:
					HalloweenActivity.changeState(HalloweenActivity.STATE_MAINMENU, true, true);
					break;
				case DIALOG_RESTART_GAME:
					HalloweenActivity.changeState(HalloweenActivity.STATE_GAMEPLAY, true, true);
				break;
				default:
					break;
				}

			}
			break;
		case DIALOG_TYPE_NOTICE:
			if (HalloweenActivity.isTouchReleaseInRect(x2 - DEF.DIALOG_BUTTON_CONFIRM_H / 2, y - DEF.DIALOG_BUTTON_CONFIRM_H / 2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//ok
			{
				switch (dialogNextState)
				{
				//case DIALOG_NEXTSTATE_EXIT:
				//	MainActivity.mainActivity.finish();
				//	break;
				//case DIALOG_BACK_TO_MAINMENU_EXIT:
				//	break;

				default:
					break;
				}
			}
			break;
		default:
			break;
		}

	}
}
