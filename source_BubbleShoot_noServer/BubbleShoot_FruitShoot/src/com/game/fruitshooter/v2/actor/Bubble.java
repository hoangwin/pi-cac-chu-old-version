package com.game.fruitshooter.v2.actor;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.game.fruitshooter.v2.BitmapFont;
import com.game.fruitshooter.v2.DEF;
import com.game.fruitshooter.v2.GameLib;
import com.game.fruitshooter.v2.MainActivity;
import com.game.fruitshooter.v2.Map;
import com.game.fruitshooter.v2.SoundManager;
import com.game.fruitshooter.v2.Sprite;
import com.game.fruitshooter.v2.state.StateGameplay;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;

public class Bubble extends Actor
{

	//state
	public final static int STATE_IDE = 0;	
	public final static int STATE_RUN = 1;
	public final static int STATE_WAIT_DETROY = 2;
	public final static int STATE_DETROY = 3;
	public int value;

	public int currentRow;
	public int currentCol;	
	public int state;	
	public boolean isCheck = false;
	public static Random rand = new Random();
	public static float SPEED_MAX =80;
	public float speedX = 0;
	public float speedY = 0;	
	public float m_xFloat = 0;
	public float m_yFloat = 0;
	
	public Bubble()
	{
		value = 0;
		m_x = 0;
		m_y = 0;
		currentRow = 0;
		currentCol = 0;

		state = STATE_IDE;
	}

	public Bubble(int _value, int _row, int _col,  int _state)
	{
		value = _value;
		currentRow = _row;
		currentCol = _col;	
		setRightPostion();
		state = _state;
		
	}
	public Bubble(int x, int y,  int _state)
	{
	
		m_x = x;
		m_y = y;	
	
		state = _state;
		
	}

	public void update()
	{
		switch (state)
		{
		case STATE_IDE:
			break;
		case STATE_RUN:
			updateBubbleMove();
			break;
		case STATE_WAIT_DETROY:
			if(StateGameplay.spriteFruit.hasAnimationFinished(_currentAnimation, _currentFrame, _waitDelay))
				state = STATE_IDE;
			break;
		}
	}

	public void paint(Canvas c)
	{

		switch (state)
		{
		case STATE_IDE:
			if(value >=0)
				StateGameplay.spriteFruit.drawAFrame(MainActivity.mainCanvas,value, m_x,m_y);
			break;
		case STATE_RUN:
			if(value >=0)
				StateGameplay.spriteFruit.drawAFrame(MainActivity.mainCanvas,value, m_x,m_y);
			break;
		case STATE_WAIT_DETROY:
			StateGameplay.spriteFruit.drawAnim(c, this,this.m_x,this.m_y);
			break;
		}

	}
	public static ArrayList<Point> bubbles = new ArrayList<Point>(); 
	public static void initBubblesList()
	{
		bubbles.clear();
		for(int i= 0;i<8;i++)
		{
			bubbles.add(new Point(i, 0));
		}
		for (int i = 0; i < Map.MAX_ROW; i++)
			for (int j = 0; j < Map.MAX_COL; j++)
			{		
				int index = Map.tableArray[i][j].value;
				if(index >=0)
					bubbles.get(index).y++;					
			}
		for(int i= 0; i<bubbles.size() ;i++)
		{
			if(bubbles.get(i).y <=0)
			{
				bubbles.remove(i);
				i--;
			}
		}
		
	}
	public static int nextBubbleIndex()
    {
		
		int i = -1;
		if(bubbles.size()>0)
		{
			i=	rand.nextInt(bubbles.size());
			return bubbles.get(i).x;
		}
		return-1;
    }
	  
	public  void shootBubble()
	{
		  m_xFloat =m_x;
		  m_yFloat = m_y;
		  speedX= (float) (SPEED_MAX*Math.sin(angleRadian));		
		  speedY= (float) (SPEED_MAX*Math.cos(angleRadian));
		  speedY = -Math.abs(speedY);
		  state = STATE_RUN;
	}
	public void getRowAndColFromPostion()
	{
		  currentRow =( m_y - Map.BEGIN_Y )/ Map.ITEM_HEIGHT;
		  float x =0;		 
		  if(currentRow%2==0)
		  {
			  x= (m_x - Map.BEGIN_X_0);			  
			  currentCol =  (int) (x/ Map.ITEM_WIDTH);
		  }
		  else
		  {
			  x = (m_x - Map.BEGIN_X_1);
			  currentCol = (int) (x / Map.ITEM_WIDTH);
		  }
		if(currentCol <0)
			currentCol = 0;
		if(currentCol >=Map.MAX_COL)
			currentCol = Map.MAX_COL - 1;
	}
	public void setRightPostion()
	{
		  	
			if(currentRow%2==0)
				m_x =	Map.BEGIN_X_0 + currentCol * Map.ITEM_WIDTH + Map.ITEM_WIDTH/2;	
			else
				m_x =	Map.BEGIN_X_1 + currentCol * Map.ITEM_WIDTH +Map.ITEM_WIDTH/2;
			m_y = Map.BEGIN_Y +(currentRow) * Map.ITEM_HEIGHT + Map.ITEM_HEIGHT/2;
			angleDegree =0;
			angleRadian = 0; 
	}
	 
	public  boolean updateBubbleMove()
	{
		  
		
		 m_xFloat += speedX;
		 m_yFloat += speedY;

		 
		 if(m_xFloat< Map.BEGIN_X_0 +Map.ITEM_WIDTH/2)
		 {
			// float temp =Map.BEGIN_X_0 - m_xFloat; 
			 
			 m_xFloat = Map.BEGIN_X_0 + Map.ITEM_WIDTH/2 + 1;
			 angleDegree += 90;
			 angleRadian += Math.PI/2;
			 speedX = Math.abs(speedX);
		 }
		 if(m_xFloat > (Map.BEGIN_X_0 +Map.MAX_COL*Map.ITEM_WIDTH - Map.ITEM_WIDTH/2))
		 {
			 m_xFloat = Map.BEGIN_X_0 +Map.MAX_COL*Map.ITEM_WIDTH - Map.ITEM_WIDTH/2- 1;
			 angleDegree -= 90;
			 angleRadian-= Math.PI/2;
			 speedX = -Math.abs(speedX);
		 }
		 m_x =(int)m_xFloat;
		 m_y =(int)m_yFloat;
		 getRowAndColFromPostion();				
		
		return false;
	}
}