package com.xiaxio.halloween;


import java.util.ArrayList;
import java.util.Random;

import com.xiaxio.halloween.actor.Actor;
import com.xiaxio.halloween.state.StateGameplay;
import com.xiaxio.halloween.state.StateWinLose;

import resolution.DEF;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

public class Map
{
	public static int COL = 10;
	public static int ROW = 6;
	public static int CELL_WIDTH = 73;
	public static int CELL_HEIGHT = 80;
	public static int BEGIN_X = 100;
	public static int BEGIN_Y = -20;
	public static int countFrameDrawPath = -1;
	public static int countFrameDrawAddScrore = -1;
	public static Actor effect1, effect2;
	public static int countHint;// so lan hint con lai
	public static int countAutoSort; // so lan sort con lai
	public static int mAllScore; // so lan sort con lai
	public static int mScoreAdd; // so lan sort con lai
	public static int mAllScoreBonus; // so lan sort con 
	public static long mTimerDecrease; // so lan sort con lai
	public static long mTimerCount; // so lan sort con lai
	public static int mcountAllPaired; // so lan sort con lai
	//	public static long countIsPaired; // so lan sort con lai
	//public static int countPaired = 0;
	public static long[] timerPaired = new long[COL * COL + 1];

	public enum Direction
	{
		Left, Right, Up, Down, None
	}

	
	final int CardNo = 15;
	static int[][] CardMatrix; // máº£ng 2 chiá»�u (8x12) lÆ°u giÃ¡ trá»‹ cÃ¡c tháº» hÃ¬nh
								// trong ma tráº­n
	// CardMatrix[0,0]=0: táº¡i vá»‹ trÃ­ [0,0] lÃ  tháº» hÃ¬nh thá»© 0
	// CardMatrix[0,1]=-1: táº¡i vá»‹ trÃ­ [0,1] khÃ´ng cÃ³ tháº» hÃ¬nh

	int RemainingCount; // Sá»‘ tháº» hÃ¬nh cÃ²n láº¡i trong ma tráº­n chÆ°a Ä‘Æ°á»£c 'Äƒn'
	static Random random = new Random();
	static boolean CardSelected; // Tháº» hÃ¬nh thá»© nháº¥t Ä‘Ã£ Ä‘Æ°á»£c chá»�n
	static int CardX1; // Vá»‹ trÃ­ cá»§a tháº» hÃ¬nh thá»© nháº¥t
	static int CardY1;
	static int CardX2; // Vá»‹ trÃ­ cá»§a tháº» hÃ¬nh thá»© nháº¥t
	static int CardY2;
	static int rCount; // Chiá»�u dÃ i Ä‘Æ°á»�ng Ä‘i tá»« tháº» hÃ¬nh thá»© 2 Ä‘áº¿n tháº» hÃ¬nh thá»©
						// nháº¥t
	static ArrayList<RectF> arrayListPair = new ArrayList<RectF>();// luu vao 4
																	// ding cho
																	// 2 cap
	static int[] rX; // Ä�Æ°á»�ng Ä‘i tá»« tháº» hÃ¬nh thá»© 2 Ä‘áº¿n tháº» hÃ¬nh thá»© nháº¥t
						// (rX[0],rY[0]) -> ... ->(rX[rCount-1],rX[rCount-1])
	static int[] rY;

	int tCount; // Chiá»�u dÃ i Ä‘Æ°á»�ng Ä‘i táº¡m trong quÃ¡ trÃ¬nh Ä‘á»‡ quy
	int[] tX, tY;
	Direction[] d; // HÆ°á»›ng Ä‘i cá»§a Ä‘Æ°á»�ng Ä‘i táº¡m
					// VÃ­ dá»¥: Up -> Left -> Left -> ...

	public void Init()
	{

		CardMatrix = new int[ROW + 2][COL + 2]; // táº¡o ma tráº­n cÃ¡c tháº» hÃ¬nh
		// táº¡o cÃ¡c máº£ng Ä‘á»ƒ tÃ¬m Ä‘Æ°á»�ng trong quÃ¡ trÃ¬nh Ä‘á»‡ quy
		int MAX = (ROW + 2) * (COL + 2);
		rX = new int[MAX];
		rY = new int[MAX];
		tX = new int[MAX];
		tY = new int[MAX];
		d = new Direction[MAX];
		countHint = 3;
		countAutoSort = 1;
		mAllScore = 0;
	}

	public Map()
	{

	}

	public void NewGame()
	{

		mcountAllPaired = 0;
		mAllScoreBonus = 0;
		countHint = 3;
		countAutoSort = 1;
		mAllScore = 0;
		mTimerDecrease = 120000 - HalloweenActivity.mcurrentlevel*500;
		mTimerCount = 0;
		if (effect1 == null) {
			effect1 = new Actor();
			effect2 = new Actor();
		}
		effect1.sprite = StateGameplay.spriteTileBoard;
		effect2.sprite = StateGameplay.spriteTileBoard;
		int i, j, k;

		// Máº£ng nÃ y cho biáº¿t má»—i hÃ¬nh xuáº¥t hiá»‡n máº¥y láº§n
		int[] CardCount = new int[CardNo];
		for (k = 0; k < CardNo; k++)
			CardCount[k] = 4; // Thiáº¿t láº­p cÃ³ 4 tháº» hÃ¬nh cho má»—i hÃ¬nh

		Random rnd = new Random(); // táº¡o Ä‘á»‘i tÆ°á»£ng sinh sá»‘ ngáº«u nhiÃªn

		// Ä‘áº§u tiÃªn cÃ¡c Ã´ cá»§a ma tráº­n lÃ  trá»‘ng
		for (i = 0; i <= ROW + 1; i++)
			for (j = 0; j <= COL + 1; j++)
				CardMatrix[i][j] = -1;

		// duyá»‡t qua tá»«ng Ã´ trong ma tráº­n, chá»�n ngáº«u nhiÃªn tháº» hÃ¬nh cho Ã´ Ä‘Ã³
		// khÃ´ng xÃ©t cÃ¡c Ã´ sÃ¡t mÃ©p nÃªn i: 1->ROW; j:1-> COL
		for (i = 1; i <= ROW; i++)
			for (j = 1; j <= COL; j++) {
				do {
					k = rnd.nextInt(CardNo);
				} while (CardCount[k] == 0); // Náº¿u CardCount[k] == 0 nghÄ©a lÃ 
												// hÃ¬nh thá»© k Ä‘Ã£ sá»­ dá»¥ng háº¿t
												// cÃ¡c tháº» hÃ¬nh, cáº§n tÃ¬m k khÃ¡c.
				CardMatrix[i][j] = k; // tháº» hÃ¬nh táº¡i Ã´ i, j lÃ  hÃ¬nh thá»© k
				CardCount[k]--; // hÃ¬nh thá»© k Ä‘Ã£ dÃ¹ng 1 tháº»
			}
		RemainingCount = COL * ROW; // 60 tháº» hÃ¬nh chÆ°a Ä‘Æ°á»£c 'Äƒn'
		CardSelected = false; // Tháº» hÃ¬nh thá»© nháº¥t chÆ°a Ä‘Æ°á»£c chá»�n
	}

	// Ä‘áº¿m xem Ä‘Æ°á»�ng Ä‘i táº¡m ráº½ bao nhiÃªu láº§n
	private int CountTurn(Direction[] d, int tCount)
	{
		int count = 0;
		for (int i = 2; i < tCount; i++) // duyá»‡t qua cÃ¡c Ä‘iá»ƒm trong Ä‘Æ°á»�ng Ä‘i
		{
			if (d[i - 1] != d[i])
				count++; // náº¿u hÆ°á»›ng khÃ¡c nhau nghÄ©a lÃ  cÃ³ ráº½
		}
		return count;
	}

	// Ä‘á»‡ quy quay lui Ä‘á»ƒ tÃ¬m Ä‘Æ°á»�ng Ä‘i ráº½ tá»‘i Ä‘a 2 láº§n vÃ  ngáº¯n nháº¥t
	// [y,x] lÃ  Ã´ hiá»‡n táº¡i Ä‘ang xÃ©t
	// direct lÃ  hÆ°á»›ng Ä‘i Ä‘áº¿n Ã´ [y,x]
	private void FindRoute(int x, int y, Direction direct)
	{
		if (x < 0 || x > COL + 1)
			return;
		if (y < 0 || y > ROW + 1)
			return; // náº¿u ra khá»�i ma tráº­n, thoÃ¡t
		if (CardMatrix[y][x] != -1)
			return; // khÃ´ng pháº£i Ã´ trá»‘ng, thoÃ¡t (1)

		tX[tCount] = x; // Ä‘Æ°a Ã´ [y,x] vÃ o Ä‘Æ°á»�ng Ä‘i
		tY[tCount] = y;
		d[tCount] = direct; // ghi nháº­n lÃ  hÆ°á»›ng Ä‘i Ä‘áº¿n Ã´ [y,x]

		// náº¿u ráº½ nhiá»�u hÆ¡n 2 láº§n, thoÃ¡t
		if (CountTurn(d, tCount + 1) > 2)
			return;

		tCount++;
		CardMatrix[y][x] = -2; // Ä‘Ã¡nh dáº¥u Ã´ [y,x] Ä‘Ã£ Ä‘i qua
		// lá»‡nh (1) Ä‘áº£m báº£o Ã´ Ä‘Ã£ Ä‘i qua sáº½ khÃ´ng Ä‘i láº¡i ná»¯a
		if (x == CardX1 && y == CardY1) // náº¿u Ä‘Ã£ tÃ¬m Ä‘áº¿n vá»‹ trÃ­ hÃ¬nh thá»© nháº¥t
		{
			// kiá»ƒm tra xem Ä‘Æ°á»�ng Ä‘i má»›i tÃ¬m cÃ³ ngáº¯n hÆ¡n Ä‘Æ°á»�ng Ä‘i trong cÃ¡c láº§n
			// trÆ°á»›c?
			if (tCount < rCount) {
				// náº¿u ngáº¯n hÆ¡n, ghi nhá»› láº¡i Ä‘Æ°á»�ng Ä‘i nÃ y
				rCount = tCount;
				for (int i = 0; i < tCount; i++) {
					rX[i] = tX[i];
					rY[i] = tY[i];
				}
			}
		} else // náº¿u chÆ°a Ä‘i Ä‘áº¿n Ä‘Æ°á»£c Ã´ hÃ¬nh thá»© nháº¥t -> Ä‘á»‡ quy Ä‘á»ƒ Ä‘i Ä‘áº¿n 4 Ã´
				// xung quanh
		{
			FindRoute(x - 1, y, Direction.Left);
			FindRoute(x + 1, y, Direction.Right);
			FindRoute(x, y - 1, Direction.Up);
			FindRoute(x, y + 1, Direction.Down);
		}

		// Ã´ [y,x] Ä‘Ã£ xÃ©t xong, quay lui nÃªn loáº¡i Ã´ [y,x] ra khá»�i Ä‘Æ°á»�ng Ä‘i
		tCount--;
		// Ä‘Ã¡nh dáº¥u láº¡i lÃ  Ã´ [y,x] trá»‘ng, cÃ³ thá»ƒ Ä‘i qua láº¡i
		CardMatrix[y][x] = -1;
	}

	public void searchPair()
	{
		arrayListPair.clear();
		for (int i = 0; i <= ROW + 1; i++) {
			for (int j = 0; j <= COL + 1; j++) {
				for (int x = 0; x <= ROW + 1; x++) {
					for (int y = j; y <= COL + 1; y++) {
						searchPair(j, i, y, x);
					}
				}
			}
		}
		if (arrayListPair.size() > 0) {
			int temp = random.nextInt(arrayListPair.size());
			CardX1 = (int) arrayListPair.get(temp).left; // Vá»‹ trÃ­ cá»§a tháº» hÃ¬nh
															// thá»© nháº¥t
			CardY1 = (int) arrayListPair.get(temp).top;
			CardX2 = (int) arrayListPair.get(temp).right; // Vá»‹ trÃ­ cá»§a tháº» hÃ¬nh
															// thá»© nháº¥t
			CardY2 = (int) arrayListPair.get(temp).bottom;
		}
	}

	public void searchPair(int _CardX1, int _CardY1, int _CardX2, int _CardY2)
	{

		CardX1 = _CardX1;
		CardY1 = _CardY1;

		int x = _CardX2;
		int y = _CardY2;
		if (x < 0 || x > COL || y < 0 || y > ROW)
			return;

		if (CardMatrix[y][x] != -1) // Náº¿u click vÃ o má»™t tháº» hÃ¬nh
		{

			if (x != CardX1 || y != CardY1) {
				// náº¿u tháº» hÃ¬nh thá»© 2 giá»‘ng tháº» hÃ¬nh thá»© nháº¥t
				if (CardMatrix[y][x] == CardMatrix[CardY1][CardX1]) {
					int temp = CardMatrix[y][x];
					// Ä‘á»ƒ dá»… dÃ ng trong viá»‡c tÃ¬m Ä‘Æ°á»�ng, ta Ä‘Ã¡nh dáº¥u 2 Ã´ nÃ y lÃ 
					// trá»‘ng
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = -1;

					rCount = Integer.MAX_VALUE; // ban Ä‘áº§u giáº£ sá»­ khÃ´ng cÃ³ Ä‘Æ°á»�ng
												// Ä‘i
					tCount = 0;
					FindRoute(x, y, Direction.None); // Ä‘á»‡ quy tÃ¬m Ä‘Æ°á»�ng

					// Sau khi tÃ¬m Ä‘Æ°á»�ng khÃ´i phá»¥c láº¡i hÃ¬nh á»Ÿ Ã´ thá»© nháº¥t vÃ  thá»©
					// 2
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = temp;

					if (rCount != Integer.MAX_VALUE) // tÃ¬m tháº¥y Ä‘Æ°á»�ng Ä‘i ráº½
														// Ã­thÆ¡n 2 láº§n

					{
						// countDrawPath = 3;
						effect1.sprite.setAnim(effect1, 0, false, false);
						effect2.sprite.setAnim(effect2, 0, false, false);
						StateGameplay.spriteTileBoard.setAnim(0, CardX1 * CELL_WIDTH + BEGIN_X, CardY1 * CELL_HEIGHT + BEGIN_Y, false, false);

						// System.Threading.Thread.Sleep(500); //táº¡m dá»«ng 0,5 giÃ¢y

						// CardMatrix[y][x] = -1;
						// CardMatrix[CardY1][CardX1] = -1; // hai tháº» hÃ¬nh dung
						CardX2 = x;
						CardY2 = y;
						arrayListPair.add(new RectF(CardX1, CardY1, CardX2, CardY2));
						//	Log.d("PAiR", "x1 : " + CardX1 + ", Y1 : " + CardY1 + ", x2 : " + CardX2 + ", Y2: " + CardY2);

						CardSelected = false;

					}
				}
			}
		}

	}

	public void CardClick(int pointerx, int pointery) // Nháº¥n chuá»™t táº¡i Ã´ [y,x]
														// trong ma tráº­n
	{
		int x = -0;
		int y = -0;
		if (!HalloweenActivity.isTouchReleaseScreen())
			return;

		x = (pointerx - BEGIN_X) / CELL_WIDTH;
		y = (pointery - BEGIN_Y) / CELL_HEIGHT;
		if (x < 0 || x > COL || y < 0 || y > ROW)
			return;

		if (CardMatrix[y][x] != -1) // Náº¿u click vÃ o má»™t tháº» hÃ¬nh
		{
			if (!CardSelected) // Náº¿u tháº» hÃ¬nh thá»© nháº¥t chÆ°a chá»�n
			{
				SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
				CardSelected = true; // Ä�Ã¡nh dáº¥u tháº» hÃ¬nh thá»© nháº¥t Ä‘Ã£ chá»�n
				CardX1 = x; // LÆ°u láº¡i vá»‹ trÃ­ tháº» hÃ¬nh thá»© nháº¥t
				CardY1 = y;
				// DrawGame();//here
			} else // náº¿u tháº» hÃ¬nh thá»© nháº¥t Ä‘Ã£ chá»�n
			if (x != CardX1 || y != CardY1) {
				// náº¿u tháº» hÃ¬nh thá»© 2 giá»‘ng tháº» hÃ¬nh thá»© nháº¥t
				if (CardMatrix[y][x] == CardMatrix[CardY1][CardX1]) {
					int temp = CardMatrix[y][x];
					// Ä‘á»ƒ dá»… dÃ ng trong viá»‡c tÃ¬m Ä‘Æ°á»�ng, ta Ä‘Ã¡nh dáº¥u 2 Ã´ nÃ y lÃ 
					// trá»‘ng
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = -1;

					rCount = Integer.MAX_VALUE; // ban Ä‘áº§u giáº£ sá»­ khÃ´ng cÃ³ Ä‘Æ°á»�ng
												// Ä‘i
					tCount = 0;
					FindRoute(x, y, Direction.None); // Ä‘á»‡ quy tÃ¬m Ä‘Æ°á»�ng

					// Sau khi tÃ¬m Ä‘Æ°á»�ng khÃ´i phá»¥c láº¡i hÃ¬nh á»Ÿ Ã´ thá»© nháº¥t vÃ  thá»©
					// 2
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = temp;

					if (rCount != Integer.MAX_VALUE) // tÃ¬m tháº¥y Ä‘Æ°á»�ng Ä‘i ráº½
														// Ã­thÆ¡n 2 láº§n

					{
						countFrameDrawPath = 4;
						countFrameDrawAddScrore = 4;
						effect1.sprite.setAnim(effect1, 0, false, false);
						effect2.sprite.setAnim(effect2, 0, false, false);
						StateGameplay.spriteTileBoard.setAnim(0, CardX1 * CELL_WIDTH + BEGIN_X, CardY1 * CELL_HEIGHT + BEGIN_Y, false, false);

						// System.Threading.Thread.Sleep(500); //táº¡m dá»«ng 0,5
						// giÃ¢y

						CardMatrix[y][x] = -1;
						CardMatrix[CardY1][CardX1] = -1; // hai tháº» hÃ¬nh nÃ y Ä‘Ã£ Ä‘Æ°á»£c 'Äƒn'
						CardX2 = x;
						CardY2 = y;
						RemainingCount -= 2;
						CardSelected = false;
						mScoreAdd = 10;

						for (int i = mcountAllPaired - 1; i >= 0; i--) {
							if ((GameThread.timeCurrent - timerPaired[i]) < (mcountAllPaired - i) * 1000) {
								mAllScoreBonus += 20;
								mScoreAdd += 20;
							}

							else
								break;
						}
						mAllScore += mScoreAdd;
						timerPaired[mcountAllPaired++] = GameThread.timeCurrent;

						// Náº¿u khÃ´ng cÃ²n tháº» hÃ¬nh nÃ o, chÃºc má»«ng vÃ  khá»Ÿi táº¡o
						if (RemainingCount == 0) {

							StateWinLose.isWin = true;
							HalloweenActivity.changeState(IConstant.STATE_WINLOSE);
							SoundManager.pausesoundLoop(1);
							SoundManager.playSound(SoundManager.SOUND_WIN, 1);

						} else {
							SoundManager.playSound(SoundManager.SOUND_COMBOL_1, 1);
						}

					} else // náº¿u khÃ´ng tÃ¬m tháº¥y Ä‘Æ°á»�ng Ä‘i
					{
						SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
						CardSelected = true; // há»§y chá»�n tháº» hÃ¬nh thá»© nháº¥t
						CardX1 = x; // LÆ°u láº¡i vá»‹ trÃ­ tháº» hÃ¬nh thá»© nháº¥t
						CardY1 = y;
					}
				} else // náº¿u tháº» hÃ¬nh thá»© 2 khÃ´ng giá»‘ng tháº» hÃ¬nh thá»© nháº¥t
				{
					SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
					CardSelected = true; // há»§y chá»�n tháº» hÃ¬nh thá»© nháº¥t
					CardX1 = x; // LÆ°u láº¡i vá»‹ trÃ­ tháº» hÃ¬nh thá»© nháº¥t
					CardY1 = y;
				}
			}
		}
	}

	// váº½ Ä‘Æ°á»�ng Ä‘i ná»‘i 2 tháº» hÃ¬nh
	public static void DrawPath(Canvas g)
	{
		if (!effect1.sprite.hasAnimationFinished(effect1._currentAnimation, effect1._currentFrame, effect1._waitDelay)) {

			effect1.sprite.drawAnim(g, effect1, CardX1 * CELL_WIDTH + BEGIN_X, CardY1 * CELL_HEIGHT + BEGIN_Y);
			effect2.sprite.drawAnim(g, effect2, CardX2 * CELL_WIDTH + BEGIN_X, CardY2 * CELL_HEIGHT + BEGIN_Y);
		}
		int i, x1 = 0, y1 = 0, x2, y2;
		if (countFrameDrawPath-- >= 0) {

			HalloweenActivity.mainPaint.setStyle(Style.STROKE);
			HalloweenActivity.mainPaint.setStrokeWidth(5);
			HalloweenActivity.mainPaint.setColor(Color.RED);

			x1 = rX[0] * CELL_WIDTH + CELL_WIDTH / 2 + BEGIN_X;
			y1 = rY[0] * CELL_HEIGHT + CELL_HEIGHT / 2 + BEGIN_Y; // (y1, x1) lÃ  tÃ¢m cá»§a tháº» hÃ¬nh thá»© 2

			//align path
			if (rX[0] == 0)
				x1 = x1 + 2 * CELL_WIDTH / 5;
			else if (rX[0] == COL + 1)
				x1 = x1 - 2 * CELL_WIDTH / 5;
			if (rY[0] == 0)
				y1 = y1 + 2 * CELL_HEIGHT / 5;
			else if (rY[0] == ROW + 1)
				y1 = y1 - 2 * CELL_HEIGHT / 5;
			//align path 

			for (i = 1; i < rCount; i++) {
				x2 = rX[i] * CELL_WIDTH + CELL_WIDTH / 2 + BEGIN_X;
				y2 = rY[i] * CELL_HEIGHT + CELL_HEIGHT / 2 + BEGIN_Y; // (y1, x1) lÃ  tÃ¢m cá»§a cÃ¡c Ã´ Ä‘i qua

				//align path		
				if (rX[i] == 0)
					x2 = x2 + 2 * CELL_WIDTH / 5;
				else if (rX[i] == COL + 1)
					x2 = x2 - 2 * CELL_WIDTH / 5;
				if (rY[i] == 0)
					y2 = y2 + 2 * CELL_HEIGHT / 5;
				else if (rY[i] == ROW + 1)
					y2 = y2 - 2 * CELL_HEIGHT / 5;
				//align path	

				g.drawLine(x1, y1, x2, y2, HalloweenActivity.mainPaint); // Váº½ Ä‘Æ°á»�ng tháº³ng ná»‘i 2 Ã´
				x1 = x2;
				y1 = y2;

			}
		}
		if (countFrameDrawAddScrore-- >= 0) {

			x1 = rX[rCount / 2] * CELL_WIDTH + CELL_WIDTH / 2 + BEGIN_X;
			y1 = rY[rCount / 2] * CELL_HEIGHT + CELL_HEIGHT / 2 + BEGIN_Y;

			Log.d("rcount : ", " " + rCount);
			Log.d("x,y : ", " " + x1 + "," + y1);
			//if(countFrameDrawAddScrore%2 !=0)
			//PikachuActivity.fontbig_Yellow.drawString(g, " + " + mScoreAdd, x1, y1 - 20 - countFrameDrawAddScrore*2, BitmapFont.ALIGN_CENTER);
			//else
			HalloweenActivity.fontbig_White.drawString(g, " + " + mScoreAdd, x1, y1 - (20 - countFrameDrawAddScrore * 2), BitmapFont.ALIGN_CENTER);
		}
	}

	/*
	 * private void FMain_Load(object sender, EventArgs e) { //Khi form load:
	 * Init(); //Khá»Ÿi táº¡o cÃ¡c giÃ¡ trá»‹ ban Ä‘áº§u NewGame(); //Chuáº©n bá»‹ 1 game má»›i }
	 * 
	 * private void pnGame_Paint(object sender, PaintEventArgs e) { //Khi pnGame
	 * cáº§n váº½ láº¡i (do minimize hoáº·c bá»‹ form khÃ¡c Ä‘Ã¨ lÃªn) DrawGame(); //Váº½ láº¡i
	 * tÃ¬nh tráº¡ng cÃ¡c lÃ¡ bÃ i }
	 * 
	 * private void pnGame_MouseDown(object sender, MouseEventArgs e) { //Khi
	 * nháº¥n chuá»™t trÃªn pnGame int x = e.X / CELL_WIDTH; //TÃ­nh chá»‰ sá»‘ hÃ ng vÃ 
	 * cá»™t cá»§a lÃ¡ bÃ i Ä‘Æ°á»£c click int y = e.Y / CELL_HEIGHT; CardClick(x, y);
	 * //Xá»­ lÃ½ nháº¥n chuá»™t }
	 */

	public void autoSortMap()
	{
		ArrayList<Point> maptemp = new ArrayList<Point>();
		int[][] temparray = new int[ROW + 2][COL + 2];
		int count = 0;
		for (int i = 0; i <= ROW + 1; i++) {
			for (int j = 0; j <= COL + 1; j++) {
				if (CardMatrix[i][j] > -1) {
					maptemp.add(new Point(i, j));
					count++;
				}
				temparray[i][j] = -1;
			}
		}

		int tempindex;
		for (int i = 0; i <= ROW + 1; i++) {
			for (int j = 0; j <= COL + 1; j++) {
				if (CardMatrix[i][j] > -1) {
					tempindex = random.nextInt(count);
					temparray[maptemp.get(tempindex).x][maptemp.get(tempindex).y] = CardMatrix[i][j];
					maptemp.remove(tempindex);
					count--;
				}
			}
		}
		CardMatrix = temparray;
		maptemp.clear();
		maptemp = null;
	}

	public static void DrawGame(Canvas g)
	{

		// duyá»‡t qua tá»«ng Ã´ cá»§a ma tráº­n Ä‘á»ƒ váº½ tháº» hÃ¬nh táº¡i Ã´ Ä‘Ã³
		HalloweenActivity.mainPaint.setStyle(Style.STROKE);
		HalloweenActivity.mainPaint.setColor(Color.BLUE);
		int SquareFrame;
		if (HalloweenActivity.mcurrentlevel < 15) {
			SquareFrame = 20;
		}
		else if (HalloweenActivity.mcurrentlevel < 30) {
			SquareFrame = 22;
		} else if (HalloweenActivity.mcurrentlevel < 45) {
			SquareFrame = 24;
		} else {
			SquareFrame = 26;
		}

	
		for (int i = 0; i <= ROW + 1; i++)
			for (int j = 0; j <= COL + 1; j++) {
				RectF r = new RectF(); // táº¡o hÃ¬nh chá»¯ nháº­t á»©ng vá»›i Ã´ cá»§a ma
										// tráº­n
				r.left = CELL_WIDTH * j + BEGIN_X;
				r.top = CELL_HEIGHT * i + BEGIN_Y; // tÃ­nh vá»‹ trÃ­ X, Y cá»§a hÃ¬nh
													// chá»¯ nháº­t
				r.right = CELL_WIDTH * (j + 1) + BEGIN_X;
				r.bottom = CELL_HEIGHT * (i + 1) + BEGIN_Y; // tÃ­nh chiá»�u rá»™ng
															// vÃ  cao cá»§a hÃ¬nh
															// chá»¯ nháº­t
				if (CardMatrix[i][j] == -1) { // Náº¿u Ã´ hiá»‡n táº¡i trá»‘ng
												// g.drawRect(r,
												// PikachuActivity.mainPaint);
												// XÃ³a Ä‘en Ã´ Ä‘Ã³

				} else // Náº¿u Ã´ hiá»‡n táº¡i khÃ´ng trá»‘ng
				{
					if (CardSelected && CardX1 == j && CardY1 == i)
						StateGameplay.spriteTileBoard.drawAFrame(g, SquareFrame + 1, (int) r.left, (int) r.top);
					else
						StateGameplay.spriteTileBoard.drawAFrame(g, SquareFrame, (int) r.left, (int) r.top);
					StateGameplay.spriteTileBoard.drawAFrame(g, CardMatrix[i][j], (int) r.left, (int) r.top);
					HalloweenActivity.mainPaint.setStyle(Style.STROKE);
					// g.drawRect(r, PikachuActivity.mainPaint); // Váº½ Ä‘Æ°á»�ng
					// viá»�n Ä‘en xung quanh

				}
			}
		DrawPath(g);
	}

}
