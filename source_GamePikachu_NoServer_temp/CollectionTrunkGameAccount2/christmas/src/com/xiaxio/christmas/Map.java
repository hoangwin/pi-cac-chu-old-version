package com.xiaxio.christmas;


import java.util.ArrayList;
import java.util.Random;

import com.xiaxio.christmas.actor.Actor;
import com.xiaxio.christmas.state.StateGameplay;
import com.xiaxio.christmas.state.StateWinLose;

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

	// số lượng hình là 15 => có 60 thẻ hình, mỗi hình 4 thẻ
	final int CardNo = 15;
	static int[][] CardMatrix; // mảng 2 chi�?u (8x12) lưu giá trị các thẻ hình
								// trong ma trận
	// CardMatrix[0,0]=0: tại vị trí [0,0] là thẻ hình thứ 0
	// CardMatrix[0,1]=-1: tại vị trí [0,1] không có thẻ hình

	int RemainingCount; // Số thẻ hình còn lại trong ma trận chưa được 'ăn'
	static Random random = new Random();
	static boolean CardSelected; // Thẻ hình thứ nhất đã được ch�?n
	static int CardX1; // Vị trí của thẻ hình thứ nhất
	static int CardY1;
	static int CardX2; // Vị trí của thẻ hình thứ nhất
	static int CardY2;
	static int rCount; // Chi�?u dài đư�?ng đi từ thẻ hình thứ 2 đến thẻ hình thứ
						// nhất
	static ArrayList<RectF> arrayListPair = new ArrayList<RectF>();// luu vao 4
																	// ding cho
																	// 2 cap
	static int[] rX; // �?ư�?ng đi từ thẻ hình thứ 2 đến thẻ hình thứ nhất
						// (rX[0],rY[0]) -> ... ->(rX[rCount-1],rX[rCount-1])
	static int[] rY;

	int tCount; // Chi�?u dài đư�?ng đi tạm trong quá trình đệ quy
	int[] tX, tY;
	Direction[] d; // Hướng đi của đư�?ng đi tạm
					// Ví dụ: Up -> Left -> Left -> ...

	public void Init()
	{

		CardMatrix = new int[ROW + 2][COL + 2]; // tạo ma trận các thẻ hình
		// tạo các mảng để tìm đư�?ng trong quá trình đệ quy
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
		mTimerDecrease = 120000 - ChristmasActivity.mcurrentlevel*500;
		mTimerCount = 0;
		if (effect1 == null) {
			effect1 = new Actor();
			effect2 = new Actor();
		}
		effect1.sprite = StateGameplay.spriteTileBoard;
		effect2.sprite = StateGameplay.spriteTileBoard;
		int i, j, k;

		// Mảng này cho biết mỗi hình xuất hiện mấy lần
		int[] CardCount = new int[CardNo];
		for (k = 0; k < CardNo; k++)
			CardCount[k] = 4; // Thiết lập có 4 thẻ hình cho mỗi hình

		Random rnd = new Random(); // tạo đối tượng sinh số ngẫu nhiên

		// đầu tiên các ô của ma trận là trống
		for (i = 0; i <= ROW + 1; i++)
			for (j = 0; j <= COL + 1; j++)
				CardMatrix[i][j] = -1;

		// duyệt qua từng ô trong ma trận, ch�?n ngẫu nhiên thẻ hình cho ô đó
		// không xét các ô sát mép nên i: 1->ROW; j:1-> COL
		for (i = 1; i <= ROW; i++)
			for (j = 1; j <= COL; j++) {
				do {
					k = rnd.nextInt(CardNo);
				} while (CardCount[k] == 0); // Nếu CardCount[k] == 0 nghĩa là
												// hình thứ k đã sử dụng hết
												// các thẻ hình, cần tìm k khác.
				CardMatrix[i][j] = k; // thẻ hình tại ô i, j là hình thứ k
				CardCount[k]--; // hình thứ k đã dùng 1 thẻ
			}
		RemainingCount = COL * ROW; // 60 thẻ hình chưa được 'ăn'
		CardSelected = false; // Thẻ hình thứ nhất chưa được ch�?n
	}

	// đếm xem đư�?ng đi tạm rẽ bao nhiêu lần
	private int CountTurn(Direction[] d, int tCount)
	{
		int count = 0;
		for (int i = 2; i < tCount; i++) // duyệt qua các điểm trong đư�?ng đi
		{
			if (d[i - 1] != d[i])
				count++; // nếu hướng khác nhau nghĩa là có rẽ
		}
		return count;
	}

	// đệ quy quay lui để tìm đư�?ng đi rẽ tối đa 2 lần và ngắn nhất
	// [y,x] là ô hiện tại đang xét
	// direct là hướng đi đến ô [y,x]
	private void FindRoute(int x, int y, Direction direct)
	{
		if (x < 0 || x > COL + 1)
			return;
		if (y < 0 || y > ROW + 1)
			return; // nếu ra kh�?i ma trận, thoát
		if (CardMatrix[y][x] != -1)
			return; // không phải ô trống, thoát (1)

		tX[tCount] = x; // đưa ô [y,x] vào đư�?ng đi
		tY[tCount] = y;
		d[tCount] = direct; // ghi nhận là hướng đi đến ô [y,x]

		// nếu rẽ nhi�?u hơn 2 lần, thoát
		if (CountTurn(d, tCount + 1) > 2)
			return;

		tCount++;
		CardMatrix[y][x] = -2; // đánh dấu ô [y,x] đã đi qua
		// lệnh (1) đảm bảo ô đã đi qua sẽ không đi lại nữa
		if (x == CardX1 && y == CardY1) // nếu đã tìm đến vị trí hình thứ nhất
		{
			// kiểm tra xem đư�?ng đi mới tìm có ngắn hơn đư�?ng đi trong các lần
			// trước?
			if (tCount < rCount) {
				// nếu ngắn hơn, ghi nhớ lại đư�?ng đi này
				rCount = tCount;
				for (int i = 0; i < tCount; i++) {
					rX[i] = tX[i];
					rY[i] = tY[i];
				}
			}
		} else // nếu chưa đi đến được ô hình thứ nhất -> đệ quy để đi đến 4 ô
				// xung quanh
		{
			FindRoute(x - 1, y, Direction.Left);
			FindRoute(x + 1, y, Direction.Right);
			FindRoute(x, y - 1, Direction.Up);
			FindRoute(x, y + 1, Direction.Down);
		}

		// ô [y,x] đã xét xong, quay lui nên loại ô [y,x] ra kh�?i đư�?ng đi
		tCount--;
		// đánh dấu lại là ô [y,x] trống, có thể đi qua lại
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
			CardX1 = (int) arrayListPair.get(temp).left; // Vị trí của thẻ hình
															// thứ nhất
			CardY1 = (int) arrayListPair.get(temp).top;
			CardX2 = (int) arrayListPair.get(temp).right; // Vị trí của thẻ hình
															// thứ nhất
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

		if (CardMatrix[y][x] != -1) // Nếu click vào một thẻ hình
		{

			if (x != CardX1 || y != CardY1) {
				// nếu thẻ hình thứ 2 giống thẻ hình thứ nhất
				if (CardMatrix[y][x] == CardMatrix[CardY1][CardX1]) {
					int temp = CardMatrix[y][x];
					// để dễ dàng trong việc tìm đư�?ng, ta đánh dấu 2 ô này là
					// trống
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = -1;

					rCount = Integer.MAX_VALUE; // ban đầu giả sử không có đư�?ng
												// đi
					tCount = 0;
					FindRoute(x, y, Direction.None); // đệ quy tìm đư�?ng

					// Sau khi tìm đư�?ng khôi phục lại hình ở ô thứ nhất và thứ
					// 2
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = temp;

					if (rCount != Integer.MAX_VALUE) // tìm thấy đư�?ng đi rẽ
														// íthơn 2 lần

					{
						// countDrawPath = 3;
						effect1.sprite.setAnim(effect1, 0, false, false);
						effect2.sprite.setAnim(effect2, 0, false, false);
						StateGameplay.spriteTileBoard.setAnim(0, CardX1 * CELL_WIDTH + BEGIN_X, CardY1 * CELL_HEIGHT + BEGIN_Y, false, false);

						// System.Threading.Thread.Sleep(500); //tạm dừng 0,5 giây

						// CardMatrix[y][x] = -1;
						// CardMatrix[CardY1][CardX1] = -1; // hai thẻ hình dung
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

	public void CardClick(int pointerx, int pointery) // Nhấn chuột tại ô [y,x]
														// trong ma trận
	{
		int x = -0;
		int y = -0;
		if (!ChristmasActivity.isTouchReleaseScreen())
			return;

		x = (pointerx - BEGIN_X) / CELL_WIDTH;
		y = (pointery - BEGIN_Y) / CELL_HEIGHT;
		if (x < 0 || x > COL || y < 0 || y > ROW)
			return;

		if (CardMatrix[y][x] != -1) // Nếu click vào một thẻ hình
		{
			if (!CardSelected) // Nếu thẻ hình thứ nhất chưa ch�?n
			{
				SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
				CardSelected = true; // �?ánh dấu thẻ hình thứ nhất đã ch�?n
				CardX1 = x; // Lưu lại vị trí thẻ hình thứ nhất
				CardY1 = y;
				// DrawGame();//here
			} else // nếu thẻ hình thứ nhất đã ch�?n
			if (x != CardX1 || y != CardY1) {
				// nếu thẻ hình thứ 2 giống thẻ hình thứ nhất
				if (CardMatrix[y][x] == CardMatrix[CardY1][CardX1]) {
					int temp = CardMatrix[y][x];
					// để dễ dàng trong việc tìm đư�?ng, ta đánh dấu 2 ô này là
					// trống
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = -1;

					rCount = Integer.MAX_VALUE; // ban đầu giả sử không có đư�?ng
												// đi
					tCount = 0;
					FindRoute(x, y, Direction.None); // đệ quy tìm đư�?ng

					// Sau khi tìm đư�?ng khôi phục lại hình ở ô thứ nhất và thứ
					// 2
					CardMatrix[y][x] = CardMatrix[CardY1][CardX1] = temp;

					if (rCount != Integer.MAX_VALUE) // tìm thấy đư�?ng đi rẽ
														// íthơn 2 lần

					{
						countFrameDrawPath = 4;
						countFrameDrawAddScrore = 4;
						effect1.sprite.setAnim(effect1, 0, false, false);
						effect2.sprite.setAnim(effect2, 0, false, false);
						StateGameplay.spriteTileBoard.setAnim(0, CardX1 * CELL_WIDTH + BEGIN_X, CardY1 * CELL_HEIGHT + BEGIN_Y, false, false);

						// System.Threading.Thread.Sleep(500); //tạm dừng 0,5
						// giây

						CardMatrix[y][x] = -1;
						CardMatrix[CardY1][CardX1] = -1; // hai thẻ hình này đã được 'ăn'
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

						// Nếu không còn thẻ hình nào, chúc mừng và khởi tạo
						if (RemainingCount == 0) {

							StateWinLose.isWin = true;
							ChristmasActivity.changeState(IConstant.STATE_WINLOSE);
							SoundManager.pausesoundLoop(1);
							SoundManager.playSound(SoundManager.SOUND_WIN, 1);

						} else {
							SoundManager.playSound(SoundManager.SOUND_COMBOL_1, 1);
						}

					} else // nếu không tìm thấy đư�?ng đi
					{
						SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
						CardSelected = true; // hủy ch�?n thẻ hình thứ nhất
						CardX1 = x; // Lưu lại vị trí thẻ hình thứ nhất
						CardY1 = y;
					}
				} else // nếu thẻ hình thứ 2 không giống thẻ hình thứ nhất
				{
					SoundManager.playSound(SoundManager.SOUND_CLICK_CARD, 1);
					CardSelected = true; // hủy ch�?n thẻ hình thứ nhất
					CardX1 = x; // Lưu lại vị trí thẻ hình thứ nhất
					CardY1 = y;
				}
			}
		}
	}

	// vẽ đư�?ng đi nối 2 thẻ hình
	public static void DrawPath(Canvas g)
	{
		if (!effect1.sprite.hasAnimationFinished(effect1._currentAnimation, effect1._currentFrame, effect1._waitDelay)) {

			effect1.sprite.drawAnim(g, effect1, CardX1 * CELL_WIDTH + BEGIN_X, CardY1 * CELL_HEIGHT + BEGIN_Y);
			effect2.sprite.drawAnim(g, effect2, CardX2 * CELL_WIDTH + BEGIN_X, CardY2 * CELL_HEIGHT + BEGIN_Y);
		}
		int i, x1 = 0, y1 = 0, x2, y2;
		if (countFrameDrawPath-- >= 0) {

			ChristmasActivity.mainPaint.setStyle(Style.STROKE);
			ChristmasActivity.mainPaint.setStrokeWidth(5);
			ChristmasActivity.mainPaint.setColor(Color.BLUE);

			x1 = rX[0] * CELL_WIDTH + CELL_WIDTH / 2 + BEGIN_X;
			y1 = rY[0] * CELL_HEIGHT + CELL_HEIGHT / 2 + BEGIN_Y; // (y1, x1) là tâm của thẻ hình thứ 2

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
				y2 = rY[i] * CELL_HEIGHT + CELL_HEIGHT / 2 + BEGIN_Y; // (y1, x1) là tâm của các ô đi qua

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

				g.drawLine(x1, y1, x2, y2, ChristmasActivity.mainPaint); // Vẽ đư�?ng thẳng nối 2 ô
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
			ChristmasActivity.fontbig_White.drawString(g, " + " + mScoreAdd, x1, y1 - (20 - countFrameDrawAddScrore * 2), BitmapFont.ALIGN_CENTER);
		}
	}

	/*
	 * private void FMain_Load(object sender, EventArgs e) { //Khi form load:
	 * Init(); //Khởi tạo các giá trị ban đầu NewGame(); //Chuẩn bị 1 game mới }
	 * 
	 * private void pnGame_Paint(object sender, PaintEventArgs e) { //Khi pnGame
	 * cần vẽ lại (do minimize hoặc bị form khác đè lên) DrawGame(); //Vẽ lại
	 * tình trạng các lá bài }
	 * 
	 * private void pnGame_MouseDown(object sender, MouseEventArgs e) { //Khi
	 * nhấn chuột trên pnGame int x = e.X / CELL_WIDTH; //Tính chỉ số hàng và
	 * cột của lá bài được click int y = e.Y / CELL_HEIGHT; CardClick(x, y);
	 * //Xử lý nhấn chuột }
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

		// duyệt qua từng ô của ma trận để vẽ thẻ hình tại ô đó
		ChristmasActivity.mainPaint.setStyle(Style.STROKE);
		ChristmasActivity.mainPaint.setColor(Color.BLUE);
		int SquareFrame;
		if (ChristmasActivity.mcurrentlevel < 15) {
			SquareFrame = 20;
		}
		else if (ChristmasActivity.mcurrentlevel < 30) {
			SquareFrame = 22;
		} else if (ChristmasActivity.mcurrentlevel < 45) {
			SquareFrame = 24;
		} else {
			SquareFrame = 26;
		}

	
		for (int i = 0; i <= ROW + 1; i++)
			for (int j = 0; j <= COL + 1; j++) {
				RectF r = new RectF(); // tạo hình chữ nhật ứng với ô của ma
										// trận
				r.left = CELL_WIDTH * j + BEGIN_X;
				r.top = CELL_HEIGHT * i + BEGIN_Y; // tính vị trí X, Y của hình
													// chữ nhật
				r.right = CELL_WIDTH * (j + 1) + BEGIN_X;
				r.bottom = CELL_HEIGHT * (i + 1) + BEGIN_Y; // tính chi�?u rộng
															// và cao của hình
															// chữ nhật
				if (CardMatrix[i][j] == -1) { // Nếu ô hiện tại trống
												// g.drawRect(r,
												// PikachuActivity.mainPaint);
												// Xóa đen ô đó

				} else // Nếu ô hiện tại không trống
				{
					
					StateGameplay.spriteTileBoard.drawAFrame(g, CardMatrix[i][j], (int) r.left, (int) r.top);
					if (CardSelected && CardX1 == j && CardY1 == i)
					{
						ChristmasActivity.mainPaint.setStyle(Style.STROKE);
						ChristmasActivity.mainPaint.setColor(Color.RED);//
						ChristmasActivity.mainPaint.setStrokeWidth(ChristmasActivity.SCREEN_WIDTH >700? 6:3);
						 g.drawRect(r, ChristmasActivity.mainPaint); // Vẽ đư�?ng
					}
					// vi�?n đen xung quanh

				}
			}
		DrawPath(g);
	}

}
