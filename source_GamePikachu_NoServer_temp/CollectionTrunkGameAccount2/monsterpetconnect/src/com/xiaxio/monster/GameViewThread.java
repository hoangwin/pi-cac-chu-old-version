package com.xiaxio.monster;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;




public class GameViewThread extends SurfaceView implements Runnable {
	public static  SurfaceHolder holder;
	Paint paint;
	public GameViewThread(Context context, AttributeSet attrs) {
		super(context, attrs);		
		holder = getHolder();
		paint = new Paint();
	//	this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		// TODO Auto-generated constructor stub
	}

	public static int FRAME_RATE_TARGET = 12;
	public static long FRAME_RATE_CURRENT = 0;
	public static long timeCurrent;
	public static long timePrev = System.currentTimeMillis();
	public static long timePrev2 = System.currentTimeMillis();
	
	
	//test
	/*public static long  FPS = 60;
	public static long  TICKS = 1000/FPS;	 
	public static long  lastUpdateTime;
	 
	public void run1()
	{
		while (ChemFish.running) {
		    long diffTime =  System.currentTimeMillis() - lastUpdateTime;
		    long numOfUpdate = (long) Math.floor(diffTime/TICKS);
		    for(int i = 0;i < numOfUpdate;i++){
		    	GameLib.frameCountCurrentState++;
				ChemFish.SendMessage(ChemFish.mCurrentState,ChemFish.MESSAGE_UPDATE);
				ChemFish.mainView.postInvalidate();					
				ChemFish.UpdateKey();					
				ChemFish.UpdateTouch();
				timePrev2 = timePrev;
				timePrev = timeCurrent;
		    }
		    if(diffTime >= TICKS)
		        draw();
		 
		    lastUpdateTime += TICKS * numOfUpdate;
		    diffTime -= TICKS * numOfUpdate;
		    var sleepTime = TICKS - diffTime;
		    setTimeout(gameLoop,sleepTime);
		}
	}
	*/

	@Override
	protected void onDraw(Canvas canvas) {
	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		// chu y: new pointer 1 -> ACTION_UP
		// pointer 2 thi se la ACTOPN_POINTER_UP
		// chi co ACTION_MOVE la giong nhau
		// no gom thong tin action 8 bit dau va thong tin id = 8 bit sau		
		GameLib.m_currentNumPointer = event.getPointerCount();
		if (GameLib.m_currentNumPointer > GameLib.MAX_TOUCH_POINTER)
			GameLib.m_currentNumPointer = GameLib.MAX_TOUCH_POINTER;
		for (int i = 0; i < GameLib.m_currentNumPointer; i++)
			{					
				// event.get
				GameLib.arraytouchState[i] = (action & MotionEvent.ACTION_MASK);
				GameLib.arraytouchPosX[i] = (int) event.getX(i);
				GameLib.arraytouchPosY[i] = (int) event.getY(i);					
			}
		//Log.d("TOUCH"," " + GameLib.arraytouchState[0]);
		return true;
	}

	public void run() {		
		int miliSeccondInframe =  1000 / FRAME_RATE_TARGET;
			while (MonsterActivity.running) {
				timeCurrent = System.currentTimeMillis();				
				if ((timeCurrent - timePrev) > miliSeccondInframe) {
					FRAME_RATE_CURRENT = 1000/(timeCurrent - timePrev);
					// Log.d("ccc :", " " + (timeCurrent - timePrev));
					if(!holder.getSurface().isValid())
						continue;
					
					GameLib.frameCountCurrentState++;
					GameLib.mainCanvas = holder.lockCanvas(null);
					synchronized (holder)
					{
						if(GameLib.mainCanvas != null && holder != null)
					
						{
							MonsterActivity.SendMessage(MonsterActivity.mCurrentState,MonsterActivity.MESSAGE_PAINT);
							
							MonsterActivity.SendMessage(MonsterActivity.mCurrentState,MonsterActivity.MESSAGE_UPDATE);
							MonsterActivity.UpdateKey();					
							MonsterActivity.UpdateTouch();
							timePrev2 = timePrev;
							timePrev = timeCurrent;
							holder.unlockCanvasAndPost(GameLib.mainCanvas);
						}
					}
					
					
				} else {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
	}
}
