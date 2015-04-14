package com.game.fruitshooter.v2;

import java.io.FilterReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.game.fruitshooter.v2.actor.Actor;
import com.game.fruitshooter.v2.actor.Bubble;
import com.game.fruitshooter.v2.actor.PointFall;
import com.game.fruitshooter.v2.state.StateGameplay;
import com.game.fruitshooter.v2.state.StateWinLose;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Point;

import android.util.Log;

public class Map
{
	public final static int MAX_COL = 8;
	public final static int MAX_ROW = 13;
	public final static int MAX_ITEM = 8;
	public static int ITEM_WIDTH = 60;
	public static int ITEM_HEIGHT = 60;
	public static int ITEM_SPACE = 60;
	public static Random random = new Random();
	public static Bubble[][] tableArray = new Bubble[MAX_ROW][MAX_COL];
	
	public static ArrayList<Point> listStackCheckSameValue = new ArrayList<Point>();
	// align
	public static int BEGIN_X_0 = 60;
	public static int BEGIN_X_1 = 60;	
	public static int BEGIN_Y = 60;
	//state 
	public final static int STATE_IDE = 0;
	public final static int STATE_FIGHT = 1;
	public final static int STATE_DROP = 2;
	public final static int STATE_WAIT_TO_COMPLETED = 3;	
	public final static int STATE_DETROY = 4;
	public static boolean isChangeIndex = false;
	
	public static int mTopScore = 0; 
	public static int score = 0;

	public static int stateInGamePlay = 0;
	public static long timer = 0;

	public static int stateGame = 0;//0 = waiting begin game;1 =play game;2 waiting Finish;

	public static boolean isSpecialType = false;
	public static int TIME_WAIT_TO_DROP = 0;
	
	public static Actor gunActor =  null;
	public static Actor readyActor =  null;
	public static int GUN_X =  StateGameplay.SCREEN_WIDTH/2;
	public static int GUN_NEXT_X =  StateGameplay.SCREEN_WIDTH/2 - (int)(160*MainActivity.scaleX);
	public static int GUN_Y =  (int) (1140 * StateGameplay.scaleY);
	//public static int GUN_ANGLE =  0;
	
	public static Bubble mCurrentBubble =  null;
	public static Bubble mNextBubble =  null;
	public static Bubble mtempBubble =  null;
	public static Vector<Bubble> arrayListNeighbours = new Vector<Bubble>();
	public static Vector<PointFall> arrayListFall = new Vector<PointFall>();
	static boolean IsBeginGame = false;
	static boolean IsCompleted = false;
	public static boolean IsWin = false; 
	static int effectCount = 0;
	static int bubbleShootCount = 0;
	static int BEGIN_Y_TARGET = 0;
	static int BEGIN_Y_START = 0;
	
	public static void init()
	{
		Bubble.sprite = StateGameplay.spriteFruit;
		ITEM_WIDTH = StateGameplay.spriteFruit.getFrameWidth(0);
		ITEM_HEIGHT = StateGameplay.spriteFruit.getFrameHeight(0);
		BEGIN_X_0 = (MainActivity.SCREEN_WIDTH - MAX_COL * ITEM_WIDTH) / 2 ;
		BEGIN_X_1 = BEGIN_X_0 - ITEM_WIDTH/2;
		BEGIN_Y = (int) (20 * StateGameplay.SCREEN_HEIGHT * 1.0f / 1280);
		BEGIN_Y_START = BEGIN_Y;
		BEGIN_Y_TARGET = BEGIN_Y;
		if(ITEM_WIDTH < ITEM_HEIGHT)
		{
			ITEM_SPACE = ITEM_WIDTH;
			//ITEM_SPACE -=(int)(10*CandyPop.scaleX);
			Bubble.SPEED_MAX = ITEM_SPACE - 2;			
		}
		else			
		{
			//ITEM_SPACE -=(int)(10*CandyPop.scaleX);
			ITEM_SPACE = ITEM_HEIGHT;
			Bubble.SPEED_MAX = ITEM_SPACE - 2;
		}
		
		//BEGIN_Y = (Diamond.SCREEN_HEIGHT - MAX_ROW * ITEM_HEIGHT) / 2;
		byte[][] levelDataValue= LevelManager.getCurrentLevel(MainActivity.mcurrentlevel);//LevelManager.getMaxLevel()-1
		for (int i = 0; i < MAX_ROW; i++)
			for (int j = 0; j < MAX_COL; j++)
			{				
					tableArray[i][j] = null;
					if(i == MAX_ROW-1 )
						tableArray[i][j] = new Bubble(-1, i, j,Bubble.STATE_IDE);
					else
						tableArray[i][j] = new Bubble(levelDataValue[i][j], i, j,Bubble.STATE_IDE);
					tableArray[i][j].state = Bubble.STATE_IDE;
			}
		readyActor = new Actor(GUN_X,GUN_Y, 0, 0, "READ", "READY");
		StateGameplay.spriteUI.setAnim(readyActor,0, false, false);
		gunActor = new Actor(GUN_X,GUN_Y, 0, 0, "GUN", "GUN");
		Bubble.initBubblesList();
		mCurrentBubble = new Bubble(GUN_X,GUN_Y ,  Bubble.STATE_IDE);
		mCurrentBubble.value = Bubble.nextBubbleIndex();
		
		mNextBubble = new Bubble(GUN_NEXT_X,GUN_Y ,  Bubble.STATE_IDE);
		mNextBubble.value =  Bubble.nextBubbleIndex();
		stateInGamePlay = STATE_IDE;
	
		score = 0;
		timer = 0;
		mTopScore = 0;
		stateGame = 0;
		StateGameplay.spriteFruit.setAnim(gunActor, 2, gunActor.m_x, gunActor.m_y, true, false);
		SoundManager.playSound(SoundManager.SOUND_START, 1);
		IsCompleted = false;
		IsBeginGame = false;
		bubbleShootCount = 0;
	}

	public static void DrawGame(Canvas c)
	{
		StateGameplay.spriteUI.drawAFrame(MainActivity.mainCanvas,3, 0,BEGIN_Y);
		
		for (int i = 0; i < MAX_ROW; i++) {
			for (int j = 0; j < MAX_COL; j++) {

				//test
				int temp_m_x = 0;
				if(i%2==0)
					temp_m_x =  BEGIN_X_0 + j * ITEM_WIDTH;	
				else
					temp_m_x =  BEGIN_X_1 + j * ITEM_WIDTH;

				//tableArray[i][j] = new Bubble(levelDataValue[i][j], i, j,temp_m_x, ,  Bubble.STATE_IDE);
				MainActivity.mainPaint.setStyle(Style.STROKE);
			//	if(!((i%2!=0)&&j==0))
			//		c.drawRect(temp_m_x ,  BEGIN_Y +(i) * ITEM_HEIGHT, temp_m_x + ITEM_WIDTH, BEGIN_Y +(i) * ITEM_HEIGHT +ITEM_HEIGHT , CandyPop.mainPaint);
				//end test				
				if(tableArray[i][j]!= null)
				{
					tableArray[i][j].paint(c);
				}
			}
		}
		//mCurrentBubble.paint(c);
		//mNextBubble.paint(c);
		PaintFall(c);
		
		StateGameplay.spriteFruit.drawAnim(MainActivity.mainCanvas, gunActor.angleDegree, 1, 1, gunActor,GUN_X,GUN_Y);
		if(mCurrentBubble.value>=0)
			StateGameplay.spriteFruit.drawAFrame(MainActivity.mainCanvas,mCurrentBubble.value, mCurrentBubble.angleDegree,1,1,mCurrentBubble.m_x,mCurrentBubble.m_y);
		if(mNextBubble.value>=0)
			StateGameplay.spriteFruit.drawAFrame(MainActivity.mainCanvas,mNextBubble.value,0,1,1,mNextBubble.m_x,mNextBubble.m_y);
		
		
		if(!IsBeginGame)
		{
			StateGameplay.spriteUI.drawAnim(c, readyActor,0,0);
		}
		switch (stateInGamePlay) 
		{
		case STATE_IDE:
			//StateGameplay.spriteFruit.drawAFrame(CandyPop.mainCanvas,value, m_x,m_y);
			//
			
			//StateGameplay.spriteFruit.drawAFrame(CandyPop.mainCanvas,8,GUN_ANGLE,1,1,GUN_X,GUN_Y);
			break;
		case STATE_FIGHT:
		break;			
		case STATE_DROP:
			
			
			break;
		}
	}

	public static void Update() 
	{ 	
		if(!IsBeginGame)
		{
			if(StateGameplay.spriteUI.hasAnimationFinished(readyActor._currentAnimation, readyActor._currentFrame,readyActor._waitDelay))
				IsBeginGame = true;
			return;
			
		}
		if(gunActor._currentAnimation == 3)
			if(StateGameplay.spriteFruit.hasAnimationFinished(gunActor._currentAnimation, gunActor._currentFrame, gunActor._waitDelay))
				StateGameplay.spriteFruit.setAnim(gunActor, 2, gunActor.m_x, gunActor.m_y, true, false);
		switch (stateInGamePlay)
		{
			case STATE_IDE:
				if(GameLib.isTouchDrapInRect(0, 0, GameLib.SCREEN_WIDTH, GameLib.SCREEN_HEIGHT))
				{
					 if(GameLib.arraytouchPosY[0] < gunActor.m_y -ITEM_HEIGHT)
						 getAngle();
				}
				if(GameLib.isTouchReleaseInRect(0, 0, GameLib.SCREEN_WIDTH, GameLib.SCREEN_HEIGHT))
				{
					
					if(GameLib.arraytouchPosY[0] < gunActor.m_y -ITEM_HEIGHT)
					{
						getAngle();
						StateGameplay.spriteFruit.setAnim(gunActor, 3, gunActor.m_x, gunActor.m_y, true, false);
						mCurrentBubble.shootBubble();
						SoundManager.playSound(SoundManager.SOUND_CANON_SHOOT, 1);
						bubbleShootCount++;
						if(bubbleShootCount >=8)
						{
							BEGIN_Y_TARGET = BEGIN_Y + ITEM_HEIGHT;
							bubbleShootCount = 0;
						}
					}
					//getListCanmove();
				}
				
				updateBegin_y();
				updateFall();
				if(mCurrentBubble.state == Bubble.STATE_RUN)
				{
	
						arrayListNeighbours.clear();
						boolean isOK= false;
						isOK = BubbbleUpdateNextStep(mCurrentBubble);
						if(!CheckNextStep())
						{
							isOK = true;
						}					
						if(isOK)
						{
							if(Map.tableArray[Map.mCurrentBubble.currentRow][Map.mCurrentBubble.currentCol].value >0)
							{		
								if(mCurrentBubble.currentRow%2 ==0)
									{
										mCurrentBubble.currentRow++;
										if(mCurrentBubble.currentRow != (MAX_ROW -1))
											if(mCurrentBubble.currentCol <1)
												mCurrentBubble.currentCol = 1;
									}
									else
									{
										mCurrentBubble.currentRow++;
										if(mCurrentBubble.currentRow != (MAX_ROW -1))
											if(mCurrentBubble.currentCol <1)
												mCurrentBubble.currentCol = 0;
									}
							}
	
							mCurrentBubble.setRightPostion();
							Map.tableArray[Map.mCurrentBubble.currentRow][Map.mCurrentBubble.currentCol] = Map.mCurrentBubble;
							mCurrentBubble.state = STATE_IDE;
							
							Explotion();
							
							Map.mCurrentBubble = Map.mNextBubble;
							Map.mCurrentBubble.m_x = Map.GUN_X;
							Map.mNextBubble = new Bubble(GUN_NEXT_X,Map.GUN_Y ,  Bubble.STATE_IDE);
							Map.mNextBubble.value = Bubble.nextBubbleIndex();
						}
				}
				break;
			case STATE_FIGHT:			
				break;
			case STATE_WAIT_TO_COMPLETED:
				if(arrayListFall.size() >0)
				{
					updateFall();
					return;
				}
				
				if(!IsWin)
				{
					for (int i = MAX_ROW -1; i >=0 ; i--)
						for (int j = 0; j < MAX_COL; j++)
						{
							if(tableArray[i][j].value >=0)
							{
								
								 StateGameplay.spriteFruit.setAnim(tableArray[i][j], 0, false, true);
								 tableArray[i][j].value = -1;
								 tableArray[i][j].state = Bubble.STATE_WAIT_DETROY;
								 return;
							}		
							
						}	
				}
				
				if(effectCount++>40)
					MainActivity.changeState(IConstant.STATE_WINLOSE);
				break;
			case STATE_DROP:			
				break;
		}
	}
public static void updateBegin_y()
{
	if(BEGIN_Y_TARGET > BEGIN_Y)
	{
		BEGIN_Y += ITEM_HEIGHT/20 +1;
		if(BEGIN_Y>BEGIN_Y_TARGET)
			BEGIN_Y = BEGIN_Y_TARGET;
		for (int i = MAX_ROW -1; i >=0 ; i--)
			for (int j = 0; j < MAX_COL; j++)
			{
				tableArray[i][j].setRightPostion();
			}	
		if(checkLose())
		 {
			 effectCount = 0;
			 IsWin = false;
			 IsCompleted = true;
			 stateInGamePlay = STATE_WAIT_TO_COMPLETED;
			 return;
		 }
	}
	
}
public static Bubble NextStepBubble = new Bubble(0, 0, 0); //de test truoc khi di chuyen 1 step
public static void cloneBubble(Bubble bubble1, Bubble bubble2)
{
	bubble1.m_x =bubble2.m_x;
	bubble1.m_y =bubble2.m_y;
	bubble1.m_xFloat =bubble2.m_xFloat;
	bubble1.m_yFloat =bubble2.m_yFloat;
	bubble1.speedX = bubble2.speedX;
	bubble1.speedY = bubble2.speedY;
	bubble1.angleDegree = bubble2.angleDegree;
	bubble1.angleRadian = bubble2.angleRadian;
}
public static boolean BubbbleUpdateNextStep(Bubble bubble)
{
	bubble.update();
	if(bubble.currentRow <12)
		{
		 getNeighborsNotEmpty(new Point(bubble.currentCol, bubble.currentRow));
		 if(bubble.currentRow%2==0 && bubble.currentCol ==0)
		 {
			
				 if(arrayListNeighbours.size() >0)				 
					 return true;
					 
		 }else if(bubble.currentRow%2==1 && bubble.currentCol == 0)
		 {
			  
			 if(arrayListNeighbours.size() >0)
			 {
					bubble.currentCol = 1;
					 return true;
			 }
		 }
		 else if(bubble.currentCol == MAX_COL -1)
		 {
			 
			 if(arrayListNeighbours.size() >0)
			 {		
					 return true;
			 }
		 }
	  }	
	 
	arrayListNeighbours.clear();
	
	
	getNeighborsOKSpace(new Point(bubble.currentCol,bubble.currentRow),bubble);	
	if(arrayListNeighbours.size()>0)
	{
		
		if(((bubble.currentRow%2!=0)&&bubble.currentCol==0))
		{		
				return false;
		}		
		return true;
	
	}
	else
	{
		cloneBubble(NextStepBubble,bubble);
		NextStepBubble.speedX = NextStepBubble.speedX/2;
		NextStepBubble.speedY = NextStepBubble.speedY/2;
		NextStepBubble.updateBubbleMove();
		if(NextStepBubble.currentRow <MAX_ROW &&NextStepBubble.currentCol < MAX_COL)
			if(tableArray[NextStepBubble.currentRow][NextStepBubble.currentCol].value >-1)
				if(bubble.currentRow < MAX_ROW && bubble.currentCol < MAX_COL)
					return true;
		
		cloneBubble(NextStepBubble,bubble);
		NextStepBubble.speedX = NextStepBubble.speedX;
		NextStepBubble.speedY = NextStepBubble.speedY;
		NextStepBubble.updateBubbleMove();
		if(NextStepBubble.currentRow <MAX_ROW &&NextStepBubble.currentCol < MAX_COL)
			if(tableArray[NextStepBubble.currentRow][NextStepBubble.currentCol].value >-1)
				if(bubble.currentRow < MAX_ROW && bubble.currentCol < MAX_COL)
					return true;
				
	}
	return false;
}



	static Vector<Bubble> getNeighbors(Point p)
	{

		if(p.y>=MAX_ROW || p.y<0)
			return arrayListNeighbours;
		if(p.x>= MAX_COL || p.x<0)
		{	
			return arrayListNeighbours;
		}
	    if ((p.y % 2) == 0) {
		      if (p.x > 0) {
		    	  arrayListNeighbours.addElement(tableArray[p.y][p.x-1]);
		      }
	
		      if (p.x < 7) {
			    	  arrayListNeighbours.addElement(tableArray[p.y][p.x+1]);
		
			        if (p.y > 0) {
			        	arrayListNeighbours.addElement(tableArray[p.y-1][p.x]);
			        	arrayListNeighbours.addElement(tableArray[p.y-1][p.x+1]);
			        }
		
			        if (p.y < 11) {
			        	arrayListNeighbours.addElement(tableArray[p.y+1][p.x]);
			        	arrayListNeighbours.addElement(tableArray[p.y+1][p.x+1]);
			        }
		      } else {
		        if (p.y > 0) {
		        	arrayListNeighbours.addElement(tableArray[p.y-1][p.x]);
		        }
	
		        if (p.y < 11) {
		        	arrayListNeighbours.addElement(tableArray[p.y+1][p.x]);
		        }
		      }
	    } else {
		      if (p.x < 7) {
		    	  arrayListNeighbours.addElement(tableArray[p.y][p.x+1]);
		      }
	
		      if (p.x > 0) {
		    	  arrayListNeighbours.addElement(tableArray[p.y][p.x-1]);
	
		        if (p.y > 0) {
		        	arrayListNeighbours.addElement(tableArray[p.y-1][p.x]);
		        	arrayListNeighbours.addElement(tableArray[p.y-1][p.x-1]);
		        }
	
		        if (p.y < 11) {
		        	arrayListNeighbours.addElement(tableArray[p.y+1][p.x]);
		        	arrayListNeighbours.addElement(tableArray[p.y+1][p.x-1]);
		        }
		      } else {
		        if (p.y > 0) {
		        	arrayListNeighbours.addElement(tableArray[p.y-1][p.x]);
		        }
	
		        if (p.y < 11) {
		        	arrayListNeighbours.addElement(tableArray[p.y+1][p.x]);
		        }
		      }
	    }
	    return arrayListNeighbours;
	}
	static Vector<Bubble> getNeighborsEmpty(Point p)
	{
		getNeighbors(p);
	  
	    for(int i =0;i<arrayListNeighbours.size();i++)
	    {
	    	if(arrayListNeighbours.get(i).value >= 0)
	    	{
	    		
	    		arrayListNeighbours.remove(i);
	    		i--;
	    	}	    	    	
	    }
	    return arrayListNeighbours;
	}
	static Vector<Bubble> getNeighborsNotEmpty(Point p)
	{
		getNeighbors(p);
	  
	    for(int i =0;i<arrayListNeighbours.size();i++)
	    {
	    	if( arrayListNeighbours.get(i).value <0)
	    	{
	    		
	    		arrayListNeighbours.remove(i);
	    		i--;
	    	}	    	    	
	    }
	    return arrayListNeighbours;
	}	

	static Vector<Bubble> getNeighborsOKSpace(Point p,Bubble bubble)
	{
		 getNeighborsNotEmpty(p);
		 for(int i =0;i<arrayListNeighbours.size();i++)
		 {
				if(space2Point(arrayListNeighbours.get(i).m_x,arrayListNeighbours.get(i).m_y,bubble.m_x,bubble.m_y)>=ITEM_SPACE)
				{
					 
					 Log.d("x1","" +arrayListNeighbours.get(i).m_x);
					 Log.d("y1","" +arrayListNeighbours.get(i).m_y);
					 Log.d("x2","" + mCurrentBubble.m_x);
					 Log.d("y2","" + mCurrentBubble.m_y);
					 Log.d("arrayListNeighbours SPACE","" +space2Point(arrayListNeighbours.get(i).m_x,arrayListNeighbours.get(i).m_y,bubble.m_x,bubble.m_y));	
					
		    		 arrayListNeighbours.remove(i);
		    		 i--;
				}
		}
		return arrayListNeighbours;
	}

		public static void getAngle(){
		int offx = (GUN_X - GameLib.arraytouchPosX[0]);
		int offy = (GUN_Y - GameLib.arraytouchPosY[0]);
		if (offx == 0 && offy == 0)
			return;
		//test
		getLineFrom2Point( gunActor.m_x, gunActor.m_y,GameLib.arraytouchPosX[0],GameLib.arraytouchPosY[0]);
		//entest
		mCurrentBubble.angleRadian = -Math.PI/2 +GameLib.getRadians(0, 0, GUN_X -GameLib.arraytouchPosX[0], GUN_Y - GameLib.arraytouchPosY[0]);//GameLib.getRadians(0, 0, mCurrentBubble.m_x - x, mCurrentBubble.m_y - y);
		mCurrentBubble.angleDegree = GameLib.getDegrees(mCurrentBubble.angleRadian) ;
		gunActor.angleDegree = mCurrentBubble.angleDegree;
		gunActor.angleRadian = mCurrentBubble.angleRadian;
		//mCurrentBubble.angle = GameLib.getDegrees(radian) - 90;// cong tuy theo module. neu dugn huong len +90, nam ngang huong phai +0
	}
	//--------------Phuong Tinh Duong thang
	public static float aParam = 0;
	public static float bParam = 0;
	public static float cParam = 0;
	public static void getLineFrom2Point(int x1,int y1,int x2,int y2)	
	{
		//aX + bY + c =0;
		aParam = -(y2-y1);
		bParam = x2-x1;
		cParam = aParam*x1 + bParam*y1;
		//Log.d("aaaaaaa: ",""+a);
		//Log.d("bbbbbbb: ",""+b);
		//Log.d("ccccccc: ",""+c);
	}
	public static void DrawLineFrom2Point(Canvas canvas)	
	{
		float x = 0;
		float y = 0;
		float width = 10*MainActivity.scaleX;
		MainActivity.mainPaint.setColor(Color.RED);
		//CandyPop.SCREEN_HEIGHT/2
		if(aParam!=0)
		{
			for(y= GUN_Y;y>0;y--)
			{
					x = (-bParam*y+cParam)/aParam;
					//canvas.drawRect(x -width/2, y-width/2, x+width/2, y+width/2, StateGameplay.mainPaint);
					canvas.drawCircle(x, y, (float) (2*Math.PI), MainActivity.mainPaint);//(x -width/2, y-width/2, x+width/2, y+width/2, StateGameplay.mainPaint);
					y -=50*MainActivity.scaleY;
			}
		}
		
		//aX + bY + c =0;
		//a = -(y2-y1);
		//b = x2-x1;
		//c = a*x1 + b*y1;
		
	}
	public static float space2Point(float x1,float y1,float x2,float y2)
	{
		 //AB=can[(3-1)^2+(4-2)^2]
		return  (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	static boolean needcheckNextStep = false;
	static float tempSpeedXNextStep = 0;
	static float tempSpeedYNextStep = 0;
	static float NextStepX = 0;
	static float NextStepY = 0;
	public static boolean CheckNextStep()
	{
		
		
		tempSpeedXNextStep = mCurrentBubble.speedX;
		tempSpeedYNextStep = mCurrentBubble.speedY;
		NextStepX = mCurrentBubble.m_xFloat;
		NextStepY = mCurrentBubble.m_yFloat;		
		boolean a = CheckNextOneStep();
		return a;	  		
	}
	public static boolean CheckNextOneStep()
	{	
		 //needcheckNextStep = false;
		 int currRow =(int)((NextStepY - Map.BEGIN_Y )/ Map.ITEM_HEIGHT);
		 
		 NextStepY = NextStepY+tempSpeedYNextStep;
		 NextStepX = NextStepX+tempSpeedXNextStep;
		 float x = 0;
		 if(currRow<=0)
			 return false;		
		return true;
	}
	
	
	 public static boolean CheckOutBoardX(Bubble bubble)
	  {
		  if(bubble.m_x>=(Map.BEGIN_X_0+  Map.MAX_COL*Map.ITEM_WIDTH))
			  return true;
		   if(bubble.m_x<Map.BEGIN_X_0)
			  return true;
		   return false;
	  }
	 public static boolean CheckOutBoardY(Bubble bubble)
	  {
		  if(bubble.m_y<Map.BEGIN_Y)
			  return true;
		   //if(bubble.m_x<Map.BEGIN_X_0)
		//	  return true;
		   return false;
	  }
	 
	 private static void Explotion() {
		 arrayListNeighbours.clear();
		 arrayListNeighbours.add(mCurrentBubble);
		 int value = mCurrentBubble.value;
		 Bubble bubble;
		 for(int i=0;i<arrayListNeighbours.size() ;i++)
		 { 
			 bubble = arrayListNeighbours.get(i);
			 bubble.isCheck = true;
			 getNeighborsNotEmpty(new Point(bubble.currentCol, bubble.currentRow));
			 for(int j=i+1;j<arrayListNeighbours.size() ;j++)
			 {
				 bubble = arrayListNeighbours.get(j);
				 if(bubble.value!=value || bubble.isCheck)
				 {
					 arrayListNeighbours.remove(j);
					 j--;
				 }
				 
				 
			 }
		}
		 if(arrayListNeighbours.size() >=3)
		 {
			 for(int j=0;j<arrayListNeighbours.size() ;j++)
			 {
				 bubble =  arrayListNeighbours.get(j);
				 bubble.value = -1;
				 StateGameplay.spriteFruit.setAnim(bubble, 0, false, true);
				 bubble.state = Bubble.STATE_WAIT_DETROY;
			 } 
			 
			 getFallBubble();
			 
			 Bubble.initBubblesList();
			 boolean changeValue = true;
			 for(int i = 0;i< Bubble.bubbles.size();i++ )
				 if(mNextBubble.value ==Bubble.bubbles.get(i).x )
					 changeValue = false;
			 if(changeValue)
			 {
				 mNextBubble = new Bubble(GUN_NEXT_X,GUN_Y ,  Bubble.STATE_IDE);
				 mNextBubble.value =  Bubble.nextBubbleIndex();
			 }
			 
			 if(checkWin())
			 {
				 effectCount =0;
				 IsWin = true;
				 IsCompleted = true;
				 stateInGamePlay = STATE_WAIT_TO_COMPLETED;
			 }
			
			 SoundManager.playSound(SoundManager.SOUND_SHOOT_MATCH, 1);
		 }
		 else
		 {
			 SoundManager.playSound(SoundManager.SOUND_SHOOT_BOUND, 1);
			 
			 if(checkLose())
			 {
				 effectCount = 0;
				 IsWin = false;
				 IsCompleted = true;
				 stateInGamePlay = STATE_WAIT_TO_COMPLETED;
				 return;
			 }
		 }
		 for (int i = 0; i < MAX_ROW; i++)
				for (int j = 0; j < MAX_COL; j++)
				{
					tableArray[i][j].isCheck = false;
				}
		 
		 	 
	}
	 public static void getFallBubble()
	 {
		//tinh toan bo nhung thang fall
		 Bubble bubble;
		 arrayListFall.clear();
		 arrayListNeighbours.clear();
		 for(int i=0;i<MAX_COL ;i++)
		 {
			 if(tableArray[0][i].value >=0)
			 {
				 tableArray[0][i].isCheck = false;
				 arrayListNeighbours.add(tableArray[0][i]);
				 
			 }
		 }
		 
		 for(int i=0;i<arrayListNeighbours.size() ;i++)
		 { 
			 bubble = arrayListNeighbours.get(i);
			 bubble.isCheck = true;
			 getNeighborsNotEmpty(new Point(bubble.currentCol, bubble.currentRow));
			 for(int j=i+1;j<arrayListNeighbours.size() ;j++)
			 {
				 bubble = arrayListNeighbours.get(j);
				 if(bubble.isCheck)
				 {
					 arrayListNeighbours.remove(j);
					 j--;
				 }
				 
				 
			 }
		}
		 for (int i = 0; i < MAX_ROW; i++)
				for (int j = 0; j < MAX_COL; j++)
				{
					if(tableArray[i][j].isCheck == false &&tableArray[i][j].value >=0)
					{
					
						 arrayListFall.add(new PointFall(tableArray[i][j].m_x, tableArray[i][j].m_y,tableArray[i][j].value));
						 	tableArray[i][j].value = -1;
					}
					tableArray[i][j].isCheck = false;
				}	
	 }
	 public static void updateFall()
	 {
		 PointFall p;
		 for(int i=0;i<arrayListFall.size();i++)
		 {
			 p = arrayListFall.get(i);
			 p.y += ITEM_HEIGHT/3;
			 if(p.y > MainActivity.SCREEN_HEIGHT + ITEM_HEIGHT)
			 {
				 arrayListFall.remove(i);
				 i--;
			 }
		 }
	 }
	 public static void PaintFall(Canvas c)
	 {
		 PointFall p;
		 for(int i=0;i<arrayListFall.size();i++)
		 {
			 p = arrayListFall.get(i);
			 StateGameplay.spriteFruit.drawAFrame(c,p.value,p.x,p.y);
		 }
	 }
	 public static boolean checkWin()
	 {
		 for (int i = 0; i < MAX_ROW; i++)
			for (int j = 0; j < MAX_COL; j++)
			{
				if(tableArray[i][j].value>=0)
					return false;
			}
		 return true;
	 }
	 public static boolean checkLose()
	 {
		 	int row = (BEGIN_Y - BEGIN_Y_START)/ITEM_HEIGHT;
				 
		
			for (int n = 0; n < MAX_COL; n++)
			{
				if(tableArray[MAX_ROW-1 - row][n].value>=0)
				{
					SoundManager.playSound(SoundManager.SOUND_SPECIAL_EFFECT_LOST, 1);
									
					return true;
				}
			}
		 return false;
	 }
	 //list 
	 //tinh buoc di chuyen truoc
	 /*
	 public static ArrayList<Point> listBubbleCanMove = new ArrayList<Point>();
	 public static Bubble TempBubble = new Bubble();
	 public static boolean istarget = false;
	 public static int lastRow = 0;
	 public static int lastCol = 0;
	 public static void getListCanmove()
	 {		 
		 TempBubble.m_x  =  mCurrentBubble.m_x;
		 TempBubble.m_y  =  mCurrentBubble.m_y;
		 TempBubble.m_xFloat  =  mCurrentBubble.m_xFloat;
		 TempBubble.m_yFloat  =  mCurrentBubble.m_yFloat;
		 TempBubble.speedX  =  mCurrentBubble.speedX;
		 TempBubble.speedY  =  mCurrentBubble.speedY;
		 TempBubble.angleDegree  =  mCurrentBubble.angleDegree;
		 TempBubble.angleRadian  =  mCurrentBubble.angleRadian;
		 TempBubble.getRowAndColFromPostion();
		 lastRow = TempBubble.currentRow;
		 lastCol = TempBubble.currentCol;		 
		 listBubbleCanMove.clear();
		 istarget = false;
		 while (!CheckOutBoardY(TempBubble)) {
			 TempBubble.updateBubbleMove();
			// if( lastRow != TempBubble.currentRow || lastCol != TempBubble.currentCol)
			 {
					if(lastRow >= 0 && lastRow < MAX_ROW && lastCol >=0 && lastCol <MAX_COL)
					{
						arrayListNeighbours.clear();
					//	getNeighborsNotEmpty(new Point(TempBubble.currentCol,TempBubble.currentRow));
						getNeighborsOKSpace(new Point(TempBubble.currentCol,TempBubble.currentRow),TempBubble);
						if(arrayListNeighbours.size() >0  && (tableArray[TempBubble.currentRow][TempBubble.currentCol]==null))// || tableArray[TempBubble.currentRow][TempBubble.currentCol].value <0))
						{
						//	for(int i=0;i<arrayListNeighbours.size())								
								listBubbleCanMove.add(new Point(TempBubble.currentCol,TempBubble.currentRow));
						}
					}
			 }
			
			 lastRow = TempBubble.currentRow;
			 lastCol = TempBubble.currentCol;		 
		}		 
		if(listBubbleCanMove.size()>0)
		{
			istarget = true;
		}
		else
		{
			
		}
		
	 }
	 */
	 }
