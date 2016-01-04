/**
 * 
 */
package asianFlash;

import java.awt.Color;

/**
 * This class is used to encapsulate various parameters (font size, directories, etc.) that will
 * be editable by the user and / or stored in user configuration files.
 * @author David E. Reese
 * @version	5.1
 */

//Copyright 2013-2016 David E. Reese
//
//This file is part of AsianFlashCard.
//
//AsianFlashCard is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//AsianFlashCard is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with AsianFlashCard.  If not, see <http://www.gnu.org/licenses/>.

// History:
//	20130427	DEReese					Creation (bug 000018).
//	20130428	DEReese					Added filePath, setFilePath (), and getFilePath () (bug 000016).
//	20130704	DEReese					Updated javadoc.
//	20140704	DEReese					Added quickKeyActions, quickKeyChars, setQuickKeyChar (),
//										getQuickCommand (), getQuickChar (), getQuickCharName () (bug 000037).
//	20140705	DEReese					Changed quick key mapping for next card from the system line separator
//										to line feed (0x0a), which appears to work on both Linux and on
//										windows 7 (bug 000037).
//	20140706	DEReese					Changed getQuickCharName () to use "Enter" for LF (Linefeed) and
//										CR (Carriage Return), to more closely match the name of the key
//										on most keyboards (bug 000037).
//	20141127	DEReese					Added keyWaitDelay, getKeyWaitDelay (), and setKeyWaitDelay ()
//										(bug #000042).
//	20141215	DEReese					Added scratchPadBackgroundColor, scratchPadPenColor, scratchPadPenThickness,
//										and routines to set and get them (bug 000043).
//	20151127	DEReese					Added GPL information (bug 000047).
//	20160104	DEReese					Added outputCharset, outputEncoding, getOutputCharset (), and
//										getOutputEncoding ().
//

public class UserParameters {
	
	/**
	 * Default font size to use in statistics table entries.
	 */
	private static final float defaultStatisticsTableFontSize = (float)16.0;
	
	/**
	 * Default font size to use in statistics table header.
	 */
	private static final float defaultStatisticsTableHeaderFontSize = (float)16.0;
	
	/**
	 * Font size to use in statistics table entries.
	 */
	private float	statisticsTableFontSize;
	
	/**
	 * Font size to use in statistics table header entries.
	 */
	private float	statisticsTableHeaderFontSize;
	
	/**
	 * Path where user starts search for flashcard files.
	 */
	private String	filePath;
	
	/**
	 * Array of quick key actions.
	 */
	private EnumQuickKeyDefs quickKeyActions[];
	
	/**
	 * Array of quick key characters.
	 */
	private char quickKeyChars[];
	
	/**
	 * Delay to wait for debounce of the keyboard, so that only one key value is returned for pushing
	 * a key.
	 */
	private int keyWaitDelay = 500;
	
	/**
	 * Background color for scratchpad.
	 */
	private Color scratchPadBackgroundColor;
	
	/**
	 * Color for scratchpad pen.
	 */
	private Color scratchPadPenColor;
	
	/**
	 * Thickness of scratchpad pen.
	 */
	private int scratchPadPenThickness;
	
	/**
	 * Character set used for editor output.
	 */
	private String outputCharset = "UTF-8";
	
	/**
	 * Encoding used for all file operations.
	 */
	private String outputEncoding = "UTF-8";
	
	/**
	 * Default constructor. This constructor sets the user parameters to their default values.
	 */
	public UserParameters ()
	{
		// Reset all parameters to the defaults.
		
		resetDefaults ();
	}
	
	/**
	 * This operation restores the default values for the user parameters.
	 */
	public void resetDefaults ()
	{
		statisticsTableFontSize = defaultStatisticsTableFontSize;
		statisticsTableHeaderFontSize = defaultStatisticsTableHeaderFontSize;	
		filePath = System.getProperty("user.dir");
		
		// Build the default quick key mapping.
		
		quickKeyActions = EnumQuickKeyDefs.values();
		quickKeyChars = new char[quickKeyActions.length];
		for (int i = 0; i < quickKeyChars.length; i++)
			quickKeyChars[i] = (char)0;
		setQuickKeyChar (EnumQuickKeyDefs.QUICK_KEY_SHOW_LEFT, 'f');
		setQuickKeyChar (EnumQuickKeyDefs.QUICK_KEY_SHOW_RIGHT, 'j');		
		setQuickKeyChar (EnumQuickKeyDefs.QUICK_KEY_SHOW_VISIBLE, ' ');
		setQuickKeyChar (EnumQuickKeyDefs.QUICK_KEY_LEFT_CORRECT, 'a');
		setQuickKeyChar (EnumQuickKeyDefs.QUICK_KEY_RIGHT_CORRECT, ';');
		setQuickKeyChar (EnumQuickKeyDefs.QUICK_KEY_NEXT_CARD, (char)10);
		keyWaitDelay = 500;
		scratchPadBackgroundColor = Color.white;
		scratchPadPenColor = Color.black;
		scratchPadPenThickness = 5;
		outputCharset = "UTF-8";
		outputEncoding = "UTF-8";
	}
	
	/**
	 * This method creates a map between a quick key action and a character.
	 * @param theAction	Action as defined in EnumQuickKeyDefs.
	 * @param theChar	Character to be pressed to perform the action.
	 */
	public void setQuickKeyChar (EnumQuickKeyDefs theAction, char theChar)
	{
		for (int i = 0; i < quickKeyActions.length; i++)
		{
			if (quickKeyActions[i] == theAction)
			{
				quickKeyChars[i] = theChar;
				return;
			}
		}
	}
	
	/**
	 * This method returns the quick key command for the given character.
	 * @param theChar	Character to check for quick key mapping.
	 * @return			quick key command, or QUICK_KEY_UNDEFINED if not mapped.
	 */
	public EnumQuickKeyDefs getQuickCommand (char theChar)
	{
		for (int i = 0; i < quickKeyChars.length; i++)
		{
			if (theChar == quickKeyChars[i])
			{
				return quickKeyActions[i];
			}
		}
		return EnumQuickKeyDefs.QUICK_KEY_UNDEFINED;
	}
	
	/**
	 * This method returns the quick key character for a given command.
	 * @param theCommand	Quick key command
	 * @return				Character corresponding to the command or (char)0.
	 */
	public char getQuickChar (EnumQuickKeyDefs theCommand)
	{
		for (int i = 0; i < quickKeyChars.length; i++)
		{
			if (theCommand == quickKeyActions[i])
				return quickKeyChars[i];
		}
		return ((char)0);
	}
	
	/**
	 * This method returns a string containing the character for a given quick key command. If the character
	 * is a non-printing character (such as a line feed or space), it gives the ASCII name of the character.
	 * @param theCommand	quick key command.
	 * @return				String containing the character name.
	 */
	public String getQuickCharName (EnumQuickKeyDefs theCommand)
	{
		char theChar = getQuickChar (theCommand);
		
		switch ((int)theChar)
		{
			case 0:
				return new String ("NULL");
			case 1:
				return new String ("SOH");
			case 2:
				return new String ("STX");
			case 3:
				return new String ("ETX");
			case 4:
				return new String ("EOT");
			case 5:
				return new String ("ENQ");
			case 6:
				return new String ("ACK");
			case 7:
				return new String ("BEL");
			case 8:
				return new String ("BS");
			case 9:
				return new String ("HT");
			case 10:
				return new String ("Enter");			// Really LF.
			case 11:
				return new String ("VT");
			case 12:
				return new String ("FF");
			case 13:
				return new String ("Enter");			// Really CR.
			case 14:
				return new String ("SO");
			case 15:
				return new String ("SI");
			case 16:
				return new String ("DLE");
			case 17:
				return new String ("DC1");
			case 18:
				return new String ("DC2");
			case 19:
				return new String ("DC3");
			case 20:
				return new String ("DC4");
			case 21:
				return new String ("NAK");
			case 22:
				return new String ("SYN");
			case 23:
				return new String ("ETB");
			case 24:
				return new String ("CAN");
			case 25:
				return new String ("EM");
			case 26:
				return new String ("SUB");
			case 27:
				return new String ("ESC");
			case 28:
				return new String ("FS");
			case 29:
				return new String ("GS");
			case 30:
				return new String ("RS");
			case 31:
				return new String ("US");
			case 32:
				return new String ("SPACE");
			case 127:
				return new String ("DEL");
			default:
				return new String (new Character(theChar).toString());
		}
	}
	
	/**
	 * This operation sets the font size to be used in statistics table entries.
	 * @param theVal	New font size to be used (must be > 0.0).
	 */
	public void setStatisticsTableFontSize (float theVal)
	{
		if (theVal <= 0)
			new Error ("setStatisticsTableFontSize (): theVal (" + theVal + ") must be > 0.0");
		
		statisticsTableFontSize = theVal;
	}
	
	/**
	 * This operation returns the current value of the font size used in statistics table entries.
	 * @return	Font size.
	 */
	public float getStatisticsTableFontSize ()
	{
		return statisticsTableFontSize;
	}

	/**
	 * This operation sets the font size to be used in statistics table header entries.
	 * @param theVal	New font size to be used (must be > 0.0).
	 */
	public void setStatisticsTableHeaderFontSize (float theVal)
	{
		if (theVal <= 0)
			new Error ("setStatisticsTableHeaderFontSize (): theVal (" + theVal + ") must be > 0.0");
		
		statisticsTableHeaderFontSize = theVal;
	}
	
	/**
	 * This operation returns the current value of the font size used in statistics table header entries.
	 * @return	Font size.
	 */
	public float getStatisticsTableHeaderFontSize ()
	{
		return statisticsTableHeaderFontSize;
	}
	
	/**
	 * This operation sets the path where the user starts searches for flashcard files.
	 * @param newPath	New path where searches for flashcard files should begin.
	 */
	public void setFilePath (String newPath)
	{
		filePath = new String (newPath);
	}
	
	/**
	 * This operation returns the path where a search for flashcard files begins.
	 * @return	Path where searches for flashcard files should begin.
	 */
	public String getFilePath ()
	{
		return new String (filePath);
	}

	/**
	 * This method processes the quick key characters pressed by the user.
	 * @param theChar	Character pressed by the user.
	 * @return			true if the method handled the character, false if it did not.
	 */
	public boolean processQuickKeys (char theChar)
	{
		// If quick keys are not enabled globally, return an indication that the character was not
		// used.

		if (AsianFlash.globalQuickKeysEnabled == false)
			return false;
		
		EnumQuickKeyDefs theCommand = getQuickCommand (theChar);
		switch (theCommand)
		{
			case QUICK_KEY_LEFT_CORRECT:
				AsianFlash.theControlButtonPanel.leftCorrect ();
				return true;
			case QUICK_KEY_NEXT_CARD:
				AsianFlash.theControlButtonPanel.gotoNextCard ();
				return true;
			case QUICK_KEY_RIGHT_CORRECT:
				AsianFlash.theControlButtonPanel.rightCorrect ();
				return true;
			case QUICK_KEY_SHOW_LEFT:
				AsianFlash.theControlButtonPanel.showLeftSide ();
				return true;
			case QUICK_KEY_SHOW_RIGHT:
				AsianFlash.theControlButtonPanel.showRightSide ();
				return true;
			case QUICK_KEY_SHOW_VISIBLE:
				AsianFlash.theControlButtonPanel.showCenterSide();
				return true;
			case QUICK_KEY_UNDEFINED:
			default:
				break;
		}
		return false;
	}


	/**
	 * This method returns the current value for the delay to wait between allowing quick keys.
	 * @return	Current value of the key wait delay.
	 */
	public int getKeyWaitDelay ()
	{
		return keyWaitDelay;
	}
	
	/**
	 * This method sets the delay to wait between allowing quick keys. If the value of theDelay is
	 * less than or equal to 0, then no action is taken (i.e., the current value of the delay is kept).
	 * @param theDelay
	 */
	public void setKeyWaitDelay (int theDelay)
	{
		if (theDelay > 0)
			keyWaitDelay = theDelay;
	}
	
	/**
	 * This method returns the color of the scratchpad background.
	 * @return	Color of background.
	 */
	public Color getScratchPadBackgroundColor ()
	{
		return scratchPadBackgroundColor;
	}
	
	/**
	 * This method sets the color of the scratchpad background.
	 * @param newColor	Color to which the background is to be set.
	 */
	public void setScratchPadBackgroundColor (Color newColor)
	{
		scratchPadBackgroundColor = newColor;
	}
	
	/**
	 * This method returns the color of the scratchpad pen.
	 * @return	Color of pen.
	 */
	public Color getScratchPadPenColor ()
	{
		return scratchPadPenColor;
	}
	
	/**
	 * This method sets the color of the scratchpad pen.
	 * @param newColor	Color to which the pen is to be set.
	 */
	public void setScratchPadPenColor (Color newColor)
	{
		scratchPadPenColor = newColor;
	}
	
	/**
	 * This method returns the thickness of the scratchpad pen.
	 * @return	Thickness of the pen.
	 */
	public int getScratchPadPenThickness ()
	{
		return scratchPadPenThickness;
	}
	
	/**
	 * This method sets the thickness of the scratchpad pen, if the input thickness is >= 1.
	 * @param newThickness	Thickness of the pen. Must be >= 1.
	 */
	public void setScratchPadPenThickness (int newThickness)
	{
		if (newThickness >= 1)
			scratchPadPenThickness = newThickness;
	}
	
	/**
	 * This method returns the character set to be used when the card set editor outputs data.
	 * @return	String containing the name of the character set.
	 */
	public String getOutputCharset ()
	{
		return outputCharset;
	}
	
	/**
	 * This method returns the encoding to be used when the card set editor outputs data.
	 * @return	String containing the name of the encoding.
	 */
	public String getOutputEncoding ()
	{
		return outputEncoding;
	}
}
