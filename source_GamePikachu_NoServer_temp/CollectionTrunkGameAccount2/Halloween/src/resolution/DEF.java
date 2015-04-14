package resolution;

import com.xiaxio.halloween.HalloweenActivity;

public class DEF
{
	
	//frame
	//---------UI-----------
	public static int FRAME_DIRECT_UP = 0;
	public static int FRAME_DIRECT_DOWN = 1;
	public static int FRAME_DIRECT_LEFT = 2;
	public static int FRAME_DIRECT_RIGHT = 3;	
	public static int FRAME_BUTTON_NORMAL = 4;
	public static int FRAME_BUTTON_HIGHTLIGHT = 5;
	public static int FRAME_BACK_NORMAL = 6;
	public static int FRAME_BACK_HIGHTLIGHT = 7;
	public static int FRAME_NEXT_NORMAL = 8;
	public static int FRAME_NEXT_HIGHTLIGHT = 9;	
	public static int FRAME_OK_NORMAL = 10;
	public static int FRAME_OK_HIGHTLIGHT = 11;
	public static int FRAME_CANCEL_NORMAL = 12;
	public static int FRAME_CANCEL_HIGHTLIGHT = 13;
	public static int FRAME_PAUSE_NORMAL = 14;
	public static int FRAME_PAUSE_HIGHTLIGHT = 15;	
	public static int FRAME_RETRY_NORMAL = 16;
	public static int FRAME_RETRY_HIGHTLIGHT = 17;
	public static int FRAME_MAINMENU_NORMAL = 18;
	public static int FRAME_MAINMENU_HIGHTLIGHT = 19;
	public static int FRAME_OPTION_NORMAL = 20;
	public static int FRAME_OPTION_HIGHTLIGHT = 21;
	public static int FRAME_SOUND_ON_NORMAL = 22;
	public static int FRAME_SOUND_ON_HIGHTLIGHT = 23;
	public static int FRAME_SOUND_OFF_NORMAL = 24;
	public static int FRAME_SOUND_OFF_HIGHTLIGHT = 25;	
	public static int FRAME_BUTTON_LEFT_NORMAL = 26;
	public static int FRAME_BUTTON_LEFT_HIGHTLIGHT = 27;	
	
	public static int FRAME_BUTTON_RIGHT_NORMAL = 28;
	
	public static int FRAME_BUTTON_RIGHT_HIGHTLIGHT = 29;
	public static int FRAME_SELECTLEVEL_NORMAL = 30;
	public static int FRAME_SELECTLEVEL_HIGHTLIGHT = 31;
	public static int FRAME_SELECTLEVEL_LOCK = 32;		
	
	public static int FRAME_START_0 = 33;
	public static int FRAME_START_1 = 34;
	public static int FRAME_START_2 = 35;
	public static int FRAME_START_3 = 36;	
	public static int FRAME_TIMER_BAR_0 = 37;
	public static int FRAME_TIMER_BAR_1 = 38;
	public static int FRAME_BUTTON_CUSTOM_HINT = 39;
	public static int FRAME_BUTTON_CUSTOM_HINT_HIGHTLIGHT = 40;
	public static int FRAME_BUTTON_CUSTOM_SORT = 41;
	public static int FRAME_BUTTON_CUSTOM_SORT_HIGHTLIGHT = 42;
	public static int FRAME_BUTTON_CUSTOM = 43;
	public static int FRAME_BUTTON_CUSTOM_HIGHTLIGHT = 44;
	
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
	public static int LABEL_LEVEL_Y = 2;
	
	public static int LABEL_SCORE_X = 5; 
	public static int LABEL_SCORE_Y = 0;

	public static int BUTTON_TIMER_BAR_X = 100; 
	public static int BUTTON_TIMER_BAR_Y = 0;
	public static int BUTTON_TIMER_BAR_W = 160; 
	public static int BUTTON_TIMER_BAR_H = 10;
	
	//align how to play
	public static int HOWTOPLAY_BACKGROUND_X =  0;
	public static int HOWTOPLAY_BACKGROUND_Y = 0;
	public static int HOWTOPLAY_BACKGROUND_W = HalloweenActivity.SCREEN_WIDTH ;
	public static int HOWTOPLAY_BACKGROUND_H = HalloweenActivity.SCREEN_HEIGHT;
	public static int HOWTOPLAY_BUTTON_W = 93;
	public static int HOWTOPLAY_BUTTON_H = 93;	
	public static int HOWTOPLAY_TITLE_X = HalloweenActivity.SCREEN_WIDTH/2; 
	public static int HOWTOPLAY_TITLE_Y = HOWTOPLAY_BACKGROUND_Y ;
	public static int HOWTOPLAY_CONTENT_X = HOWTOPLAY_BACKGROUND_X + 200; 
	public static int HOWTOPLAY_CONTENT_Y = HOWTOPLAY_TITLE_Y + 30;
	public static int HOWTOPLAY_CONTENT_SPACE_H = 45;
	public static int HOWTOPLAY_CONTENT_SPACE_W = 45;
	
	// align create dit button
	public static int CREADIT_BACKGROUND_X = HalloweenActivity.SCREEN_WIDTH / 8;
	public static int CREADIT_BACKGROUND_Y = HalloweenActivity.SCREEN_HEIGHT /8;
	public static int CREADIT_BACKGROUND_W = HalloweenActivity.SCREEN_WIDTH - 2 * HalloweenActivity.SCREEN_WIDTH / 8;
	public static int CREADIT_BACKGROUND_H = HalloweenActivity.SCREEN_HEIGHT - 2 * HalloweenActivity.SCREEN_HEIGHT / 8;
	
	public static int CREADIT_TITLE_X = HalloweenActivity.SCREEN_WIDTH/2; 
	public static int CREADIT_TITLE_Y = CREADIT_BACKGROUND_Y + 3;
	public static int CREADIT_CONTENT_X = CREADIT_TITLE_X; 
	public static int CREADIT_CONTENT_Y = 2*CREADIT_TITLE_Y;
	public static int CREADIT_CONTENT_SPACE_H = 40;
	//align OPTION SCREEN
	public static int OPTION_TITLE_X = HalloweenActivity.SCREEN_WIDTH/2; 
	public static int OPTION_TITLE_Y = CREADIT_BACKGROUND_Y ;
	public static int OPTION_CONTENT_X = CREADIT_TITLE_X + 40; 
	public static int OPTION_CONTENT_Y = CREADIT_TITLE_Y + 50;
	public static int OPTION_CONTENT_SPACE_H = 40;
	
	//align select level
	public static int SELECTLEVEL_BACKGROUND_X =  10;
	public static int SELECTLEVEL_BACKGROUND_Y = 10;
	public static int SELECTLEVEL_BACKGROUND_W = HalloweenActivity.SCREEN_WIDTH - 20;
	public static int SELECTLEVEL_BACKGROUND_H = HalloweenActivity.SCREEN_HEIGHT - 20;
	public static int SELECTLEVEL_TITLE_X = HalloweenActivity.SCREEN_WIDTH/2; 
	public static int SELECTLEVEL_TITLE_Y = SELECTLEVEL_BACKGROUND_Y;
	public static int SELECTLEVEL_CONTENT_SPACE_H = 15;
	public static int SELECTLEVEL_CONTENT_SPACE_W = 15;			
	
	//align win lose
	public static int WINLOSE_BACKGROUND_X =  10;
	public static int WINLOSE_BACKGROUND_Y = 10;
	public static int WINLOSE_BACKGROUND_W = HalloweenActivity.SCREEN_WIDTH - 20;
	public static int WINLOSE_BACKGROUND_H = HalloweenActivity.SCREEN_HEIGHT - 20;
	public static int WINLOSE_BUTTON_W = 93;
	public static int WINLOSE_BUTTON_H = 93;	
	public static int WINLOSE_TITLE_X = HalloweenActivity.SCREEN_WIDTH/2; 
	public static int WINLOSE_TITLE_Y = WINLOSE_BACKGROUND_Y ;
	public static int WINLOSE_CONTENT_SPACE_H = 35;
	public static int WINLOSE_CONTENT_SPACE_W = HalloweenActivity.SCREEN_WIDTH/4;
	public static int WINLOSE_CONTENT_X = HalloweenActivity.SCREEN_WIDTH/2; 
	public static int WINLOSE_CONTENT_Y = WINLOSE_TITLE_Y + 10;
	public static int WINLOSE_BUTTON_X1 = 400;
	public static int WINLOSE_BUTTON_Y1 = 400;
	public static int WINLOSE_BUTTON_X2 = 700;
	public static int WINLOSE_BUTTON_Y2 = 400;
	public static int WINLOSE_BUTTON_X3 = 600;
	public static int WINLOSE_BUTTON_Y3 = 400;
		
	

}
