package com.mega.diamonddeluxe;

import java.io.FilterReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.mega.diamonddeluxe.actor.Actor;
import com.mega.diamonddeluxe.actor.DiamondActor;
import com.mega.diamonddeluxe.state.StateGameplay;
import com.mega.diamonddeluxe.state.StateWinLose;

import resolution.DEF;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class Map
{
	public final static int MAX_COL = 7;
	public final static int MAX_ROW = 8;
	public final static int MAX_ITEM = 4;
	public static int ITEM_WIDTH = 60;
	public static int ITEM_HEIGHT = 60;
	public static Random random = new Random();
	public static DiamondActor[][] tableArray = new DiamondActor[MAX_ROW][MAX_COL];
	public static DiamondActor[][] TemptableArray = new DiamondActor[MAX_ROW][MAX_COL];
	public static ArrayList<Point> listStack = new ArrayList<Point>();
	// align
	public static int BEGIN_X = 60;
	public static int BEGIN_Y = 60;
	//state 
	public final static int STATE_IDE = 0;
	public final static int STATE_DROP = 1;
	public final static int STATE_WAIT_DETROY = 2;
	public final static int STATE_DETROY = 3;
	public static int score = 0;
	public static int effectscore = 0;
	public static int effectScore_X = 0;
	public static int effectScore_Y = 0;
	public static int stateInGamePlay = 0;
	public static long timer = 0;
	public static long countCreateSpeial = 0;
	public static long MAX_TIMER = 60000;
	public static int targetScore = 0;//normal mode
	public static int stateGame = 0;//0 = waiting begin game;1 =play game;2 waiting Finish;
	public static String strEffect = "0";//0 = waiting begin game;1 =play game;2 waiting Finish;
	public static int timeEffect = 0;//0 = waiting begin game;1 =play game;2 waiting Finish;
	public static DiamondActor effectActorRow = new DiamondActor();
	public static DiamondActor effectActorCol = new DiamondActor();
	public static boolean isSpecialType = false;

	public static void init()
	{

		DiamondActor.sprite = StateGameplay.spriteFruit;
		ITEM_WIDTH = StateGameplay.spriteFruit.getFrameWidth(0);
		ITEM_HEIGHT = StateGameplay.spriteFruit.getFrameHeight(0);
		DiamondActor.speed = ITEM_HEIGHT;
		BEGIN_X = (Diamond.SCREEN_WIDTH - MAX_COL * ITEM_WIDTH) / 2;
		BEGIN_Y = (int) (144 * StateGameplay.SCREEN_HEIGHT * 1.0f / 1280);
		//BEGIN_Y = (Diamond.SCREEN_HEIGHT - MAX_ROW * ITEM_HEIGHT) / 2;
		for (int i = 0; i < MAX_ROW; i++)
			for (int j = 0; j < MAX_COL; j++) {
				int tempValue = random.nextInt(MAX_ITEM);
				tableArray[i][j] = null;
				tableArray[i][j] = new DiamondActor(tempValue, i, j, BEGIN_X + j * ITEM_WIDTH, (i) * ITEM_HEIGHT - (MAX_ROW + j) * ITEM_HEIGHT, BEGIN_X + j * ITEM_WIDTH, BEGIN_Y + i * ITEM_HEIGHT, DiamondActor.STATE_IDE);
				tableArray[i][j].state = DiamondActor.STATE_DROP;
			}
		stateInGamePlay = STATE_DROP;
		effectScore_X = 0;
		effectscore = 0;
		effectScore_Y = -100;
		score = 0;
		timer = 0;
		stateGame = 0;
		countCreateSpeial = 0;
		Map.targetScore = (3000 + StateGameplay.mcurrentlevel*1000);
		SoundManager.playSound(SoundManager.SOUND_START, 1);
	}

	public static void DrawGame(Canvas c)
	{

		for (int i = 0; i < MAX_ROW; i++) {
			for (int j = 0; j < MAX_COL; j++) {
				//	if((i+j)%2 == 0)
				//		Diamond.mainPaint.setARGB(100, 0, 0, 0);
				//	else				
				tableArray[i][j].paint(c);
			}
		}

		switch (stateInGamePlay)
		{
		case STATE_IDE:
			if(timeEffect++ < 7){
				StateGameplay.fontbig_White.drawString(c, strEffect, effectScore_X, effectScore_Y, BitmapFont.ALIGN_CENTER);
				effectScore_Y -= 2;
			}
			break;
		case STATE_DROP:
			effectActorRow.sprite.drawAnim(c, effectActorRow);
			effectActorCol.sprite.drawAnim(c, effectActorCol);
			StateGameplay.fontbig_White.drawString(c,strEffect, effectScore_X, effectScore_Y, BitmapFont.ALIGN_CENTER);
			effectScore_Y -= 2;
			
			break;
		}
	}

	public static void Update() 
	{ 
		// MAX_COL
		if (stateGame == 1)//dang play game
			Map.timer += GameThread.timeCurrent - GameThread.timePrev;
		if(StateGameplay.gameMode ==0)
		{
			if(timer >= MAX_TIMER) 
				StateGameplay.changeState(StateGameplay.STATE_WINLOSE);
		}
		else
		{
			if(stateInGamePlay == STATE_IDE && score>= targetScore)
				StateGameplay.changeState(StateGameplay.STATE_WINLOSE);
				
		}
		
		switch (stateInGamePlay)
		{
		case STATE_IDE:
			if (GameLib.m_currentNumPointer > 0) {
				if (GameLib.arraytouchState[0] == MotionEvent.ACTION_UP) {
					if ((GameLib.arraytouchPosX[0] - BEGIN_X) >= 0 && (GameLib.arraytouchPosY[0] - BEGIN_Y) >= 0) {
						int cols = (GameLib.arraytouchPosX[0] - BEGIN_X) / ITEM_WIDTH;
						int rows = (GameLib.arraytouchPosY[0] - BEGIN_Y) / ITEM_HEIGHT;
						checkNear(rows, cols);
					}
				}
			}
			break;
		case STATE_DROP:
			if (GameLib.m_currentNumPointer > 0) {
				if (GameLib.arraytouchState[0] == MotionEvent.ACTION_UP) {
					if ((GameLib.arraytouchPosX[0] - BEGIN_X) >= 0 && (GameLib.arraytouchPosY[0] - BEGIN_Y) >= 0) {
						int cols = (GameLib.arraytouchPosX[0] - BEGIN_X) / ITEM_WIDTH;
						int rows = (GameLib.arraytouchPosY[0] - BEGIN_Y) / ITEM_HEIGHT;
						checkNear(rows, cols);
					}
				}
			}
			if(timeEffect++ < 7)
				break;
			boolean completeState = true;			
			for (int i = 0; i < MAX_ROW; i++)
				for (int j = 0; j < MAX_COL; j++) {
					tableArray[i][j].update();
					if (tableArray[i][j].state != DiamondActor.STATE_IDE) {
						completeState = false;
						//	break;
					}
				}
			if (completeState) {
				stateInGamePlay = STATE_IDE;
				if (stateGame == 0) {
					timer = 0;
					stateGame++;
				}
			}
			break;
		}

	}

	public static void checkNear(int row, int col)
	{
		if (row < 0 || row >= MAX_ROW || col < 0 || col >= MAX_COL)
			return;
		for (int i = 0; i < MAX_ROW; i++)
			for (int j = 0; j < MAX_COL; j++) {
				if (TemptableArray[i][j] == null)
					TemptableArray[i][j] = new DiamondActor();
				TemptableArray[i][j].value = tableArray[i][j].value;
				TemptableArray[i][j].state = tableArray[i][j].state;
			}

		listStack.removeAll(listStack);
		listStack.clear();
		listStack.add(new Point(row, col));
		int value = tableArray[row][col].value;
		TemptableArray[row][col].value = -1;
		if (value < 0)
			return;

		int temp_row;
		int temp_col;
		for (int i = 0; i < listStack.size(); i++) {
			// left
			temp_row = listStack.get(i).x - 1;
			temp_col = listStack.get(i).y;
			if (temp_row >= 0) {
				if (TemptableArray[temp_row][temp_col].state == DiamondActor.STATE_IDE && TemptableArray[temp_row][temp_col].value == value) {
					listStack.add(new Point(temp_row, temp_col));
					TemptableArray[temp_row][temp_col].value = -1;

				}
			}
			// right
			temp_row = listStack.get(i).x + 1;
			temp_col = listStack.get(i).y;
			if (temp_row < MAX_ROW) {
				if (TemptableArray[temp_row][temp_col].state == DiamondActor.STATE_IDE && TemptableArray[temp_row][temp_col].value == value) {
					listStack.add(new Point(temp_row, temp_col));
					TemptableArray[temp_row][temp_col].value = -1;
				}
			}
			// top
			temp_row = listStack.get(i).x;
			temp_col = listStack.get(i).y - 1;
			if (temp_col >= 0) {
				if (TemptableArray[temp_row][temp_col].state == DiamondActor.STATE_IDE && TemptableArray[temp_row][temp_col].value == value) {
					listStack.add(new Point(temp_row, temp_col));
					TemptableArray[temp_row][temp_col].value = -1;
				}
			}
			// bottom 
			temp_row = listStack.get(i).x;
			temp_col = listStack.get(i).y + 1;
			if (temp_col < MAX_COL) {
				if (TemptableArray[temp_row][temp_col].state == DiamondActor.STATE_IDE && TemptableArray[temp_row][temp_col].value == value) {
					listStack.add(new Point(temp_row, temp_col));
					TemptableArray[temp_row][temp_col].value = -1;
				}
			}
		}
		if (listStack.size() >= 3) {
			strEffect= "";
			effectscore = listStack.size() * 10;
			isSpecialType= false;
			for (int i = 0; i < listStack.size(); i++) {
				temp_row = listStack.get(i).x;
				temp_col = listStack.get(i).y;
				checkSpecialEffect(temp_row,temp_col);
				tableArray[temp_row][temp_col].value = -1;
			}
			timeEffect = 0;
			if(strEffect.length() < 2){				
				strEffect = "" + effectscore;
			}	
			effectScore_X = BEGIN_X + col * ITEM_WIDTH + ITEM_WIDTH / 2;
			effectScore_Y = BEGIN_Y + row * ITEM_HEIGHT + ITEM_HEIGHT / 2;
			//align text effect
			if(effectScore_X <2*ITEM_WIDTH)
				effectScore_X = 2*ITEM_WIDTH;
			else if(effectScore_X > (StateGameplay.SCREEN_WIDTH- 2 *ITEM_WIDTH))
				effectScore_X = StateGameplay.SCREEN_WIDTH- 2 *ITEM_WIDTH;
			//align text effect			
			explosionAll();			
			score += listStack.size() * 10;
			if(listStack.size() < 5)
				SoundManager.playSound(SoundManager.SOUND_COMBOL_1, 1);
			else if(listStack.size()<8)
				SoundManager.playSound(SoundManager.SOUND_COMBOL_2, 1);
			else if(listStack.size()<11)
				SoundManager.playSound(SoundManager.SOUND_COMBOL_3, 1);
			else 
				SoundManager.playSound(SoundManager.SOUND_COMBOL_4, 1);
		}
		else
		{
			SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
		}
	}

	private static void checkSpecialEffect(int row,int col)
	{
		int value = tableArray[row][col].value;
		int specialType =tableArray[row][col].specialType;
		if(specialType == 0)//cell and row
		{
			explosionCellRow(row,col);
			timeEffect = -2;
			effectscore =effectscore*2;
			strEffect = "Great\n" + effectscore;
		}
		else if(specialType == 1)//double
		{
			SoundManager.playSound(SoundManager.SOUND_SPECIAL_EFFECT_1,1);
			effectscore =effectscore*2;
			strEffect = "Combo x2\n" + effectscore;
		}
		else if(specialType == 2)//boom 
		{
			isSpecialType = true;
			//timeEffect = 0;
			explosionBoom(row,col);
			effectscore = effectscore*2;
			strEffect = "BOOM\n" + effectscore;
		}	
	}

	private static void explosionBoom(int row, int col)
	{
		SoundManager.playSound(SoundManager.SOUND_SPECIAL_EFFECT_3,1);
		int value = tableArray[row][col].value;
		for(int i=0 ;i<MAX_ROW;i++)	
		{	
			for(int j=0 ;j<MAX_COL;j++)
			{
				if(tableArray[i][j].value == value)
					tableArray[i][j].value = -1;
			}
		}
	}

	private static void explosionCellRow(int row, int col)
	{
		effectActorRow.sprite.setAnim(effectActorRow, 2, BEGIN_X, BEGIN_Y + row*ITEM_HEIGHT, false, false);
		effectActorCol.sprite.setAnim(effectActorCol, 3, BEGIN_X + col*ITEM_WIDTH, BEGIN_Y, false, false);
		SoundManager.playSound(SoundManager.SOUND_SPECIAL_EFFECT_3,2);
		for(int i=0 ;i<MAX_COL;i++)
		{
			tableArray[row][i].value = -1;
		}
		for(int i=0 ;i<MAX_ROW;i++)
		{
			tableArray[i][col].value = -1;
		}
		
	}

	public static void explosionAll()
	{
		stateInGamePlay = STATE_DROP;
		for (int col = 0; col < MAX_COL; col++) {
			int cellSpace = 0;
			for (int row = MAX_ROW - 1; row >= 0; row--) {
				if (tableArray[row][col].value == -1) {//here
					if(isSpecialType)//when boom thi doi sprite no di
						DiamondActor.sprite.setAnim(tableArray[row][col], 1, tableArray[row][col].currentX, tableArray[row][col].currentY, false, false);
					else
						DiamondActor.sprite.setAnim(tableArray[row][col], 0, tableArray[row][col].currentX, tableArray[row][col].currentY, false, false);
					cellSpace++;
				}
				if (cellSpace > 0) {
					if (tableArray[row][col].value >= 0) {
						if (row + cellSpace < MAX_ROW) {
							tableArray[row + cellSpace][col].state = DiamondActor.STATE_DROP;
							tableArray[row + cellSpace][col].value = tableArray[row][col].value;
							tableArray[row + cellSpace][col].targetY = BEGIN_Y + (row + cellSpace) * ITEM_HEIGHT;// tableArray[row + cellSpace][col].currentY;
							tableArray[row + cellSpace][col].currentY = tableArray[row][col].currentY;
							tableArray[row + cellSpace][col].specialType = tableArray[row][col].specialType;							
						}
						tableArray[row][col].value = -1;
					}
				}

			}
		}
		//create new id
		for (int col = 0; col < MAX_COL; col++) {
			for (int row = MAX_ROW - 1; row >= 0; row--) {
				if (tableArray[row][col].value < 0) {
					for (int temprow = row; temprow >= 0; temprow--) {
						tableArray[temprow][col].state = DiamondActor.STATE_DROP;
						tableArray[temprow][col].specialType = -1;
						if(timer/5000 >countCreateSpeial)
						{
							countCreateSpeial = timer/5000;
							countCreateSpeial++;
							tableArray[temprow][col].specialType = random.nextInt(3);		
							Log.d("tableArray[temprow][col].specialType :"," " + tableArray[temprow][col].specialType );
						}
						tableArray[temprow][col].value = random.nextInt(MAX_ITEM);
						tableArray[temprow][col].targetX = BEGIN_X + col * ITEM_WIDTH;
						tableArray[temprow][col].targetY = BEGIN_Y + temprow * ITEM_HEIGHT;
						tableArray[temprow][col].currentY = (temprow - row - 1) * ITEM_HEIGHT;
						tableArray[temprow][col].currentX = BEGIN_X + col * ITEM_WIDTH;
					}
				}
			}

		}
	}

	public static void drawArrayLog()
	{
		String s = "";
		for (int i = 0; i < MAX_ROW; i++) {
			s = "";
			for (int j = 0; j < MAX_COL; j++) {

				TemptableArray[i][j].value = tableArray[i][j].value;
			}
		}
	}
}
