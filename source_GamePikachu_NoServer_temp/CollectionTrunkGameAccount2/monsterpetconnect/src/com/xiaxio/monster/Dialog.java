package com.xiaxio.monster;

import com.xiaxio.monster.state.StateGameplay;
import com.xiaxio.monster.state.StateMainMenu;

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
	public static int DIALOG_CONFIRM_X = MonsterActivity.SCREEN_WIDTH / 20;
	public static int DIALOG_CONFIRM_Y = MonsterActivity.SCREEN_HEIGHT / 4;
	public static int DIALOG_CONFIRM_W = MonsterActivity.SCREEN_WIDTH - 2 * MonsterActivity.SCREEN_WIDTH /20;
	public static int DIALOG_CONFIRM_H = MonsterActivity.SCREEN_HEIGHT - 2 * MonsterActivity.SCREEN_HEIGHT / 4;

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
	
		MonsterActivity.mainPaint.setStyle(Style.FILL);
		MonsterActivity.mainPaint.setARGB(200, 0, 0, 0);
		c.drawRect(0, 0, MonsterActivity.SCREEN_WIDTH, MonsterActivity.SCREEN_HEIGHT, MonsterActivity.mainPaint);
		MonsterActivity.mainPaint.setStyle(Style.FILL_AND_STROKE);
		MonsterActivity.mainPaint.setColor(Color.argb(170,0, 0, 0));
		c.drawRoundRect(rectF, 25, 25, MonsterActivity.mainPaint);
		//draw text
		//MonsterActivity.fontbig_White.drawString(c, dialogText, MonsterActivity.SCREEN_WIDTH / 2, DIALOG_CONFIRM_Y+ offset , DIALOG_CONFIRM_W - 20, BitmapFont.ALIGN_CENTER);
		StateGameplay.android_FontNormal.setColor(Color.WHITE);
		StateGameplay.mainCanvas.drawText(dialogText, MonsterActivity.SCREEN_WIDTH / 2, DIALOG_CONFIRM_Y+ (int)(150*StateGameplay.scaleY),StateGameplay.android_FontNormal);
		int y = DIALOG_CONFIRM_Y + DIALOG_CONFIRM_H - 3*DEF.DIALOG_BUTTON_CONFIRM_W/2;// PikachuActivity.SCREEN_HEIGHT / 6 + DIALOG_CONFIRM_H - 40 - 20;
		int x1 = DIALOG_CONFIRM_X +DIALOG_CONFIRM_W/4 ;
		int x2 =  DIALOG_CONFIRM_X + 3*DIALOG_CONFIRM_W/4 - DEF.DIALOG_BUTTON_CONFIRM_W ;
		//draw button
		switch (dialogType)
		{
		case DIALOG_TYPE_CONFIRM:

			if (MonsterActivity.isTouchDrapInRect(x1, y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_CANCEL_HIGHTLIGHT, x1, y);
			else
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_CANCEL_NORMAL, x1, y);

			if (MonsterActivity.isTouchDrapInRect(x2, y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
			   
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_OK_HIGHTLIGHT, x2, y);
			else
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_OK_NORMAL, x2, y);
			break;
		case DIALOG_TYPE_NOTICE:
			if (MonsterActivity.isTouchDrapInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_OK_HIGHTLIGHT, x2, y);
			else
				StateGameplay.spriteDPad.drawAFrame(MonsterActivity.mainCanvas, DEF.FRAME_OK_NORMAL, x2, y);
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

			if (MonsterActivity.isTouchReleaseInRect(x1 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//cancel
			{
				hideDialog();
			} else if (MonsterActivity.isTouchReleaseInRect(x2 , y , DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//ok
			{
				Dialog.hideDialog();
				switch (dialogNextState)
				{
				case DIALOG_NEXTSTATE_EXIT:

					MonsterActivity.mainActivity.finish();
					break;
				case DIALOG_BACK_TO_MAINMENU_EXIT:
					MonsterActivity.changeState(MonsterActivity.STATE_MAINMENU, true, true);
					break;
				case DIALOG_RESTART_GAME:
					MonsterActivity.changeState(MonsterActivity.STATE_GAMEPLAY, true, true);
				break;
				default:
					break;
				}

			}
			break;
		case DIALOG_TYPE_NOTICE:
			if (MonsterActivity.isTouchReleaseInRect(x2 - DEF.DIALOG_BUTTON_CONFIRM_H / 2, y - DEF.DIALOG_BUTTON_CONFIRM_H / 2, DEF.DIALOG_BUTTON_CONFIRM_W, DEF.DIALOG_BUTTON_CONFIRM_H))//ok
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
