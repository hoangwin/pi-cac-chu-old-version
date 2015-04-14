package com.game.fruitshooter.v2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class LevelManager {
	public static byte[] allLevel;
	public static Vector levelList;

	public static void loadAllLevel() {
		try {
			InputStream is = MainActivity.context.getAssets().open("data.dll");
			int size = is.available();
			allLevel = new byte[size];
			is.read(allLevel);
			is.close();		
		} catch (IOException e) {
			// Should never happen.
			throw new RuntimeException(e);
		}
		
		//load xong da ta o day
        String allLevels = new String(allLevel);
        levelList = new Vector();

        int nextLevel = allLevels.indexOf("\n\n");
        if (nextLevel == -1 && allLevels.trim().length() != 0)
        {
                nextLevel = allLevels.length();
        }

        while (nextLevel != -1)
        {
                String currentLevel = allLevels.substring(0, nextLevel).trim();

                levelList.addElement(getLevel(currentLevel));

                allLevels = allLevels.substring(nextLevel).trim();

                if (allLevels.length() == 0)
                {
                        nextLevel = -1;
                }
                else
                {
                        nextLevel = allLevels.indexOf("\n\n");

                        if (nextLevel == -1)
                        {
                                nextLevel = allLevels.length();
                        }
                }
        }

    
		
		
	}
	private static byte[][] getLevel(String data)
    {
            byte[][] temp = new byte[12][8];

            for (int j=0 ; j<12 ; j++)
            {
                    for (int i=0 ; i<8 ; i++)
                    {
                            temp[j][i] = -1;
                    }
            }

            int tempX = 0;
            int tempY = 0;

            for (int i=0 ; i<data.length() ; i++)
            {
                    if (data.charAt(i) >= 48 && data.charAt(i) <= 55)
                    {
                            temp[tempY][tempX] = (byte)(data.charAt(i) - 48);
                            tempX++;
                    }
                    else if (data.charAt(i) == 45)
                    {
                            temp[tempY][tempX] = -1;
                            tempX++;
                    }

                    if (tempX == 8)
                    {
                            tempY++;

                            if (tempY == 12)
                            {
                                    return temp;
                            }

                            tempX = tempY % 2;
                    }
            }

            return temp;
    }
    public static byte[][] getCurrentLevel(int currentLevel)
    {
            if (currentLevel < levelList.size())
            {
                    return (byte[][])levelList.elementAt(currentLevel);
            }

            return null;
    }
    public static int getMaxLevel()
    {

            return levelList.size();
    }


}
