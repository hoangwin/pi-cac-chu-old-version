package com.mobilepuzzle.candypopHD;


public class DEF
{
	//URL
	//type = 1->update or insert
	//type = 2 -> get leader Board
	public static String LEADER_BOARD_UPDATE = "http://caogia.byethost10.com/tapfruit.php?type=update";
	public static String LEADER_BOARD_SELECT = "http://caogia.byethost10.com/tapfruit.php?type=select";
	//frame
	//---------UI DPAD-----------
	public static int FRAME_BUTTON_NORMAL = 0;
	public static int FRAME_BUTTON_HIGHTLIGHT = 1;
	public static int FRAME_NEXT_NORMAL = 2;
	public static int FRAME_NEXT_HIGHTLIGHT = 3;
	public static int FRAME_LEADERBOARD_NORMAL = 4;
	public static int FRAME_LEADERBOARD_HIGHTLIGHT = 5;	
	public static int FRAME_OK_NORMAL = 6;
	public static int FRAME_OK_HIGHTLIGHT = 7;
	public static int FRAME_CANCEL_NORMAL = 8;
	public static int FRAME_CANCEL_HIGHTLIGHT = 9;
	public static int FRAME_PAUSE_NORMAL = 10;
	public static int FRAME_PAUSE_HIGHTLIGHT = 11;	
	public static int FRAME_RETRY_NORMAL = 12;
	public static int FRAME_RETRY_HIGHTLIGHT = 13;
	public static int FRAME_MAINMENU_NORMAL = 14;
	public static int FRAME_MAINMENU_HIGHTLIGHT = 15;
	public static int FRAME_QUIT_NORMAL = 16;
	public static int FRAME_QUIT_HIGHTLIGHT = 17;
	public static int FRAME_SOUND_ON_NORMAL = 18;
	public static int FRAME_SOUND_ON_HIGHTLIGHT = 19;
	public static int FRAME_SOUND_OFF_NORMAL = 20;
	public static int FRAME_SOUND_OFF_HIGHTLIGHT = 21;	
	public static int FRAME_BUTTON_LEFT_NORMAL = 22;
	public static int FRAME_BUTTON_LEFT_HIGHTLIGHT = 23;	
	
	public static int FRAME_BUTTON_RIGHT_NORMAL = 24;
	
	public static int FRAME_BUTTON_RIGHT_HIGHTLIGHT = 25;
	public static int FRAME_SELECTLEVEL_NORMAL = 26;
	public static int FRAME_SELECTLEVEL_HIGHTLIGHT = 27;
	public static int FRAME_SELECTLEVEL_LOCK = 28;		
	
	public static int FRAME_START_0 = 29;
	public static int FRAME_START_1 = 30;
	public static int FRAME_START_2 = 31;
	public static int FRAME_START_3 = 32;	
	public static int FRAME_TIMER_BAR_0 = 33;
	public static int FRAME_TIMER_BAR_1 = 34;	
	public static int FRAME_BUTTON_CUSTOM = 35; 
	public static int FRAME_BUTTON_CUSTOM_HIGHTLIGHT = 36;
	
	//frame animal
	
	public static int FRAME_ROUND_1 = 16;
	public static int FRAME_ROUND_2 = 17;
	public static int FRAME_ROUND_3 = 18;
	public static int FRAME_ROUND_4 = 19;
	public static int FRAME_ROUND_5 = 20;
	public static int FRAME_ROUND_6 = 21;
	
	
	
	//end frame
//	public static int offset = -100;
	public static int DIALOG_BUTTON_CONFIRM_W = 93;
	public static int DIALOG_BUTTON_CONFIRM_H = 93;
	
	public static int DIALOG_ARROW_CONFIRM_W = 93;
	public static int DIALOG_ARROW_CONFIRM_H = 93;	

	
	public static int BUTTON_CANCEL_CONFIRM_W = 93;
	public static int BUTTON_CANCEL_CONFIRM_H = 93;
	
	
	
	//pause button
	public static int BUTTON_IGM_X = 20; 
	public static int BUTTON_IGM_Y = 50;	
	public static int BUTTON_IGM_W = 100; 
	public static int BUTTON_IGM_H = 100;
	
	public static int BUTTON_HINT_X = 20; 
	public static int BUTTON_HINT_Y = 150;
	public static int BUTTON_HINT_W = 100; 
	public static int BUTTON_HINT_H = 100;
	
	public static int BUTTON_CHANGE_TILE_X = 20; 
	public static int BUTTON_CHANGE_TILE_Y = 250;
	public static int BUTTON_CHANGE_TILE_W = 100; 
	public static int BUTTON_CHANGE_TILE_H = 100;
	
	public static int LABEL_LEVEL_X = 5; 
	public static int LABEL_LEVEL_Y = -1;
	
	public static int LABEL_SCORE_X = 5; 
	public static int LABEL_SCORE_Y = 0;

	public static int BUTTON_TIMER_BAR_X = 100; 
	public static int BUTTON_TIMER_BAR_Y = 0;
	public static int BUTTON_TIMER_BAR_W = 160; 
	public static int BUTTON_TIMER_BAR_H = 10;
	
	//align how to play
	public static int HOWTOPLAY_BACKGROUND_X =  0;
	public static int HOWTOPLAY_BACKGROUND_Y = 0;
	public static int HOWTOPLAY_BACKGROUND_W = CandyPop.SCREEN_WIDTH ;
	public static int HOWTOPLAY_BACKGROUND_H = CandyPop.SCREEN_HEIGHT;
	public static int HOWTOPLAY_BUTTON_W = 93;
	public static int HOWTOPLAY_BUTTON_H = 93;	
	public static int HOWTOPLAY_TITLE_X = CandyPop.SCREEN_WIDTH/2; 
	public static int HOWTOPLAY_TITLE_Y = HOWTOPLAY_BACKGROUND_Y ;
	public static int HOWTOPLAY_CONTENT_X = HOWTOPLAY_BACKGROUND_X + 200; 
	public static int HOWTOPLAY_CONTENT_Y = HOWTOPLAY_TITLE_Y + 30;
	public static int HOWTOPLAY_CONTENT_SPACE_H = 45;
	public static int HOWTOPLAY_CONTENT_SPACE_W = 45;
	
	// align create dit button
	public static int CREADIT_BACKGROUND_X = CandyPop.SCREEN_WIDTH / 20;
	public static int CREADIT_BACKGROUND_Y = CandyPop.SCREEN_HEIGHT /6;
	public static int CREADIT_BACKGROUND_W = CandyPop.SCREEN_WIDTH - 2 * CandyPop.SCREEN_WIDTH / 20;
	public static int CREADIT_BACKGROUND_H = CandyPop.SCREEN_HEIGHT - 2 * CandyPop.SCREEN_HEIGHT / 8;
	
	public static int CREADIT_TITLE_X = CandyPop.SCREEN_WIDTH/2; 
	public static int CREADIT_TITLE_Y = CREADIT_BACKGROUND_Y + 3;
	public static int CREADIT_CONTENT_X = CREADIT_TITLE_X; 
	public static int CREADIT_CONTENT_Y = 2*CREADIT_TITLE_Y;
	public static int CREADIT_CONTENT_SPACE_H = 40;
	//align OPTION SCREEN
	public static int OPTION_TITLE_X = CandyPop.SCREEN_WIDTH/2; 
	public static int OPTION_TITLE_Y = CREADIT_BACKGROUND_Y ;
	public static int OPTION_CONTENT_X = CREADIT_TITLE_X + 40; 
	public static int OPTION_CONTENT_Y = CREADIT_TITLE_Y + 50;
	public static int OPTION_CONTENT_SPACE_H = 40;
	
	//align select level
	public static int SELECTLEVEL_BACKGROUND_X =  10;
	public static int SELECTLEVEL_BACKGROUND_Y = 10;
	public static int SELECTLEVEL_BACKGROUND_W = CandyPop.SCREEN_WIDTH - 20;
	public static int SELECTLEVEL_BACKGROUND_H = CandyPop.SCREEN_HEIGHT - 20;
	public static int SELECTLEVEL_TITLE_X = CandyPop.SCREEN_WIDTH/2; 
	public static int SELECTLEVEL_TITLE_Y = SELECTLEVEL_BACKGROUND_Y;
	public static int SELECTLEVEL_CONTENT_SPACE_H = 15;
	public static int SELECTLEVEL_CONTENT_SPACE_W = 15;			
	
	//align win lose
	
	public static int WINLOSE_BUTTON_W = 93;
	public static int WINLOSE_BUTTON_H = 93;	
	public static int WINLOSE_BUTTON_X1 = 400;
	public static int WINLOSE_BUTTON_Y1 = 400;
	public static int WINLOSE_BUTTON_X2 = 700;
	public static int WINLOSE_BUTTON_Y2 = 400;
	public static int WINLOSE_BUTTON_X3 = 600;
	public static int WINLOSE_BUTTON_Y3 = 400;
		
	

}
