/**
 * 
 */
package asianFlash;

import java.awt.Font;
import java.util.*;

import javax.swing.SwingUtilities;

import cardseteditor.CardSetEditorDialog;

/**
 * This is the main class for the Asian Flash Card program. It contains the main () routine and all global
 * objects used by the program.
 * @author David E. Reese
 * @version	5.0
 *
 */
//Copyright 2013-2015 David E. Reese
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
//
//History:
//	20120724	DEReese				Creation.
//	20120825	DEReese				Moved theFlashCardList from CardTest to here as a static variable.
//									Moved visibleTitle, leftTitle, rightTitle, visibleFont, leftFont, 
//									rightFont, and all associated operators from CardTest to here as statics.
//	20120829	DEReese				Added leftScorePanel and rightScorePanel.
//	20120909	DEReese				Added cardFileNameSet.
//	20120910	DEReese				Moved from InternalFlashCard class: defaultSide1FontName, defaultSide2FontName,
//									defaultSide3FontName, defaultSide1Style, defaultSide2Style, defaultSide3Style,
//									defaultSide1Size, defaultSide2Size, defaultSide3Size.
//	20130330	DEReese				Added side1i18n, side2i18n, and side3i18n. Each has default values
//									of true.
//	20130427	DEReese				Added userParameterData object and initialized it with default values (bug 000018).
//	20130511	DEReese				Added theStatisticsTable global variable and initialized it to null (bug 000024).
//	20130512	DEReese				Added theStatisticsDisplayDialog global variable and initialized it to null (bug 000024).
//	20140704	DEReese				Added theGlobalKeyEventDispatcher global variable and initialized it to null. Added
//									globalQuickKeysEnabled and initialized it to false (bug 000037).
//	20141207	DEReese				Changed name of mainMenuPanel to theMainMenuPanel (bug 000043).
//	20141214	DEReese				Added theScratchPad and clearScratchPadOnNewCard (bug 000043).
//	20151127	DEReese				Added GPL information (bug 000047).
//	20151129	DEReese				Surrounded call to AsianFlashMainFrame () constructor with a call to
//									SwingUtilities.invokeLater () (bug 000049).
//	20151208	DEReese				Added theCardSetEditor (bug 000051).
//	20151211	DEReese				Change default font sizes to 48, based on font size availability (bug 000051).
//	20151214	DEReese				Spawned a thread to determine the available fonts. This can take a long
//									time, so it is run in the background (bug 000051).
//
public class AsianFlash {
	
	/**
	 * main window and frame for program.
	 */
	public static AsianFlashMainFrame mainFrame;
	
	/**
	 * Main menu for program.
	 */
	public static MainMenuBarPanel theMainMenuPanel;
	
	/**
	 * Panel for buttons controlling selection of flashcards.
	 */
	public static ControlButtonPanel theControlButtonPanel;
	
	/**
	 * Panel for display of text (in multiple fonts, styles, sizes).
	 */
	public static TextDisplayPanel thePanel;
	
	/**
	 * Panel for display of score for left hidden side of flash cards.
	 */
	public static ScorePanel leftScorePanel;
	
	/**
	 * Panel for display of score for right hidden side of flash cards.
	 */
	public static ScorePanel rightScorePanel;
	
	/**
	 * The test being run.
	 */
	public static CardTest theTest;

	/**
	 * Flash card set being tested.
	 */
	public static InternalFlashCard	theFlashCardList;
	
	/**
	 * Card set editor window being used.
	 */
	public static CardSetEditorDialog theCardSetEditor = null;
	
	/**
	 * Title of side 1 of flash cards.
	 */
	public static String side1Title;
	
	/**
	 * Title of side 2 of flash cards.
	 */
	public static String side2Title;
	
	/**
	 * Title of side 3 of flash cards.
	 */
	public static String side3Title;
	
	/**
	 * Font to be used for side 1 of flash cards.
	 */
	public static Font side1Font;
	
	/**
	 * Font to be used for side 2 of flash cards.
	 */
	public static Font side2Font;
	
	/**
	 * Font to be used for side 3 of flash cards.
	 */
	public static Font side3Font;
	
	/**
	 * Indicates if side 1 should use i18n localization.
	 */
	public static boolean side1i18n = true;
	
	/**
	 * Indicates if side 2 should use i18n localization.
	 */
	public static boolean side2i18n = true;
	
	/**
	 * Indicates if side 3 should use i18n localization.
	 */
	public static boolean side3i18n = true;
	
	/**
	 * Set of file names from which XML data has been retrieved.
	 */
	public static Set<String> cardFileNameSet = new HashSet<String>();
	
	/**
	 * Name of default font for side 1.
	 */
	public static final String	defaultSide1FontName = new String ("Times New Roman");
	
	/**
	 * Name of default font for side 2.
	 */
	public static final String	defaultSide2FontName = new String ("Times New Roman");
	
	/**
	 * Name of default font for side 3.
	 */
	public static final String	defaultSide3FontName = new String ("Times New Roman");
	
	/**
	 * Default style for font used for side 1.
	 */
	public static final int	defaultSide1Style = Font.PLAIN;	// Default style for font used for side 1.
	
	/**
	 * Default style for font used for side 2.
	 */
	public static final int	defaultSide2Style = Font.PLAIN;
	
	/**
	 * Default style for font used for side 3.
	 */
	public static final int	defaultSide3Style = Font.PLAIN;
	
	/**
	 * Default size for font used for side 1.
	 */
	public static final int	defaultSide1Size = 48;
	
	/**
	 * Default size for font used for side 2.
	 */
	public static final int	defaultSide2Size = 48;
	
	/**
	 * Default size for font used for side 3.
	 */
	public static final int	defaultSide3Size = 48;

	/**
	 * Structure containing user parameters.
	 */
	public static UserParameters userParameterData;
	
	/**
	 * Global statistics table.
	 */
	public static StatisticsTable theStatisticsTable = null;
	
	/**
	 * Global statistics dialog.
	 */
	public static StatisticsDisplayDialog theStatisticsDisplayDialog = null;
	
	/**
	 * Global keyboard event dispatcher.
	 */
	public static GlobalKeyEventDispatcher theGlobalKeyEventDispatcher = null;
	
	/**
	 * Indication as to whether quick keys are enabled or not.
	 */
	public static boolean globalQuickKeysEnabled = false;
	
	/**
	 * Indication as to whether the scratchpad is to be cleared on each new card.
	 */
	public static boolean clearScratchPadOnNewCard = true;
	
	/**
	 * Global scratchpad pointer.
	 */
	public static ScratchPad theScratchPad = null;
	
	/**
	 * This is the main routine for the Asian Flash Card program, and is called from the run-time environment.
	 * @param args Currently, the program does not take any arguments, so this is a dummy.
	 */
	public static void main(String[] args) throws InterruptedException
	{	
		// Initialize user parameters.
		
		userParameterData = new UserParameters ();
		
		// Create the main frame.
		
		SwingUtilities.invokeLater(new Runnable ()
		{
			public void run ()
			{
				mainFrame = new AsianFlashMainFrame ("AsianFlash");				
			}
		});
		
		// Start a thread to determine the system fonts. This can take a long time, so it is run in the
		// background at low priority.
		
		Thread	fontThread = new Thread ()
		{
				public void run ()
				{
					
					CardSetEditorDialog.determineFontFamilyNames();
				}
		};
		fontThread.setPriority(Thread.MIN_PRIORITY);
		fontThread.start();
	}

}
