/**
 * 
 */
package asianFlash;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.*;

import cardseteditor.CardSetEditorDialog;


/**
 * This class creates a panel containing a menu bar. It also contains all global static variables and constants.
 * @author David E. Reese
 * @version 4.1
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

// History:
//	20120724	DEReese				Creation.
//	20120824	DEReese				Commented out control of previous button.
//	20120825	DEReese				Added loadCardSetItem. Modified actionListener to handle this item and
//									to enable and disable newTestItem depending on whether or not there is
//									a valid card set.
//	20120903	DEReese				Moved code to handle setting buttons in the ControlButtonPanel to the
//									CardTest class in finishTestSetUp ().
//	20120904	DEReese				Added code to bring up a JFileChooser dialog and to call the
//									InternalFlashCard:loadNewCardSet () operation.
//	20120905	DEReese				Added call to InternalFlashCard:initGlobalCardSetVariables () operation
//									before attempting to load the new card set.
//	20120907	DEReese				Renamed fileMenu to cardSetMenu. Added testMenu. Now, commands associated
//									with cards will be in the cardSetMenu while commands associated with tests
//									will be in the testMenu.
//									Added use of "user.dir" (execution path) when setting up JFileChooserDialog.
//	20120909	DEReese				Removed debug code.
//									Added appendCardSetItem to Card Set menu and added code to support it.
//									Added clearCardSetItem to Card Set menu and added code to support it.
//	20120910	DEReese				Added code to actionPerformed () for clearCardSetItem so that it clears
//									out the display.
//									Clear cardFileNameSet instead of setting it to null to prevent a 
//									nullPointerException when attempting to load a card set after it's been
//									cleared.
//	20120915	DEReese				In actionPerformed (), added code for the loadCardSetItem handler to
//									set the global theTest variable to null when a new card set is loaded.
//									Added separators in Card Set Menu to set off the clear and quit commands.
//	20130329	DEReese				Added check for InternalFlashCard.debugSet. If it is set to true, than
//									do not ask for a file name.
//	20130410	DEReese				Fixed copyright date (bug_000003).
//	20130412	DEReese				Commented out aboutString and call to showMessageDialog ().
//									Uncommented out call to AboutDialog() (bug 000004).
//	20130413	DEReese				Added helpItem, which sets up a HelpDialog object.
//									Added helpItem to actionListener (bug 000005).
//	20130416	DEReese				Added statisticsItem to Test menu and to actionListener. Added
//									enableShowStatsItem() and disableShowStatsItem() (bug 000006).
//	20130418	DEReese				Changed statisticsItem text from "Statistics" to "Card Statistics"
//									(bug 000006).
//	20130426	DEReese				Added restartTestItem to Test menu and to actionListener().
//									Modified actionListener() code for loading and clearing a card set
//									so that it disables restartTestItem when there is not a valid test.
//									Added enableRestartTestItem () and disableRestartTestItem ()
//									(bug 000010).
//	20130428	DEReese				Changed start path for loading and appending flashcard files from
//									working directory to path contained in the global userParameterData
//									object. Also, when files are selected, the filePath in the global
//									userParameterData object is updated to the path from which the
//									files were found (bug 000016).
//	20130512	DEReese				Added code to destroy the statistics dialog (if it is open) when the
//									card set is cleared, a new card file is appended, a new cards set is loaded,
//									or the test is restarted, or a new test is started
//									(bug 000025).
//	20140226	DEReese				If the restart test command is run and the statistics dialog is displayed,
//									but "No" is clicked in the confirm dialog, do not destroy the statistics
//									dialog (bug 000032).
//	20140409	DEReese				Added changelogItem and code to support it (bug 000036).
//	20140704	DEReese				Added code to indicate when quick keys are enabled (bug 000037).
//	20141207	DEReese				Added  "Tools" menu and "ScratchPad" item. Renamed from MenuBarPanel
//									to MainMenuBarPanel (bug 000043).
//	20141208	DEReese				Cleaned up actionPerformed () by moving the code to perform each
//									action to separate private routines (bug 000045).
//									Added additional enable and disable item methods and called the disable
//									methods when the windows are set up (bug 000045).
//									Added doScratchPadItem () to handle setting up the scratch pad dialog (bug 000043).
//	20151127	DEReese				Added GPL information (bug 000047).
//	20151128	DEReese				Surrounded calls for long actions with SwingUtilities.invokeLater () calls
//									(bug 000049).
//	20151206	DEReese				Fixed misspelling in comment (no change to functionality).
//	20151208	DEReese				Added editCardSetItem. Added doEditCardSetItem (). Added code in 
//									actionPerformed () to handle editCardSetItem. Added enableEditCardSetItem ()
//									and disableEditCardSetItem () (bug 000051).
//	20151211	DEReese				Put call to constructor for CardSetEditorDialog in SwingUtilities.invokeLater ()
//									and changed the cursor to a rotating cursor, since it can take time for the
//									CardSetEditorDialog to find the system font families (bug 000051).
//

public class MainMenuBarPanel extends JPanel implements ActionListener
{

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
//	private static String aboutString = new String 
//		("Asian Language Flash Card Program v1.4. Copyright 2013, David E. Reese.\n\nThis program is Freeware and the executable may be freely distributed,\nprovided neither the program nor this message are not modified in any manner.");
	
	/**
	 * Menu bar contained in panel.
	 */
	private JMenuBar 	theMenuBar;
	
	/**
	 * Card Set menu contained in menu bar.
	 */
	private JMenu 		cardSetMenu;
	
	/**
	 * Test menu contained in menu bar.
	 */
	private JMenu		testMenu;
	
	/**
	 * Tools menu contained in main menu bar.
	 */
	private JMenu		toolsMenu;
	
	/**
	 * Help menu contained in menu bar.
	 */
	private JMenu		helpMenu;
	
	/**
	 * Load Card Set menu item.
	 */
	private JMenuItem	loadCardSetItem;
	
	/**
	 * Append Card Set menu item.
	 */
	private JMenuItem	appendCardSetItem;
	
	/**
	 * Clear Card Set menu item.
	 */
	private JMenuItem	clearCardSetItem;
	
	/**
	 * New test menu item.
	 */
	private JMenuItem	newTestItem;
	
	/**
	 * Restart Test menu item.
	 */
	private JMenuItem restartTestItem;
	
	/**
	 * Show test statistics item.
	 */
	private JMenuItem	showStatsItem;
	
	/**
	 * Show Scratchpad menu item.
	 */
	private JMenuItem	scratchpadItem;
	
	/**
	 * Edit Card Set menu item.
	 */
	private JMenuItem	editCardSetItem;
	
	/**
	 * Quit item contained in File menu.
	 */
	private JMenuItem	quitItem;
	
	/**
	 * About item contained in help menu.
	 */
	private JMenuItem	aboutItem;
	
	/**
	 * Help item contained in the help menu.
	 */
	private JMenuItem	helpItem;
	
	/**
	 * Change log display item contained in the help menu.
	 */
	private JMenuItem	changelogItem;
	
	/**
	 * Layout for menu bar panel.
	 */
	private GridLayout theLayout;

	/**
	 * Default constructor. This constructor creates the menu bar and menu items.
	 */
	
	public MainMenuBarPanel ()
	{
		super ();
		
		theLayout = new GridLayout (1,4);
		setLayout (theLayout);
		
		// Set up the border surrounding the panel.
		
		this.setBorder(new LineBorder (Color.black));

		// Create the menu bar.
		
		theMenuBar = new JMenuBar ();
		
		// Create the "File" menu and its component items.
		
		cardSetMenu = new JMenu ("Card Set");
		
		// Load Card Set item.
		loadCardSetItem = new JMenuItem ("Load New Card Set");
		loadCardSetItem.setMnemonic(KeyEvent.VK_N);
		loadCardSetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		loadCardSetItem.addActionListener(this);
		
		// Append Card Set item.
		
		appendCardSetItem = new JMenuItem ("Append Card Set");
		appendCardSetItem.setMnemonic(KeyEvent.VK_O);
		appendCardSetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		appendCardSetItem.addActionListener(this);
		appendCardSetItem.setEnabled(false);
		
		// Clear Card Set item.
		
		clearCardSetItem = new JMenuItem ("Clear Card Set");
		clearCardSetItem.addActionListener(this);
		clearCardSetItem.setEnabled(false);
		
		// The "Quit" item can be triggered by selecting the menu item or by entering CTRL-Q.
		
		quitItem = new JMenuItem ("Quit");
		quitItem.setMnemonic(KeyEvent.VK_Q);
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		quitItem.addActionListener(this);
		
		cardSetMenu.add(loadCardSetItem);
		cardSetMenu.add(appendCardSetItem);
		
		cardSetMenu.addSeparator();
		
		cardSetMenu.add(clearCardSetItem);
		cardSetMenu.addSeparator();
		cardSetMenu.add(quitItem);
		
		theMenuBar.add(cardSetMenu);
		
		// Create the "Test" menu and its component items.
		
		testMenu = new JMenu ("Test");
		
		// New test menu item.
		
		newTestItem = new JMenuItem ("New Test");
		newTestItem.addActionListener(this);
		newTestItem.setEnabled(false);
		
		// Show Statistics menu item.
		
		showStatsItem = new JMenuItem ("Card Statistics");
		showStatsItem.addActionListener(this);
		showStatsItem.setEnabled(false);
		
		// Restart Test menu item.
		
		restartTestItem = new JMenuItem ("Restart Test");
		restartTestItem.addActionListener(this);
		restartTestItem.setEnabled(false);
		
		testMenu.add(newTestItem);
		testMenu.addSeparator();
		testMenu.add(restartTestItem);
		testMenu.addSeparator();
		testMenu.add(showStatsItem);
		
		theMenuBar.add(testMenu);
		
		// Create the "Tools" menu and its component items.
		
		toolsMenu = new JMenu ("Tools");
		
		scratchpadItem = new JMenuItem ("Show Scratchpad");
		scratchpadItem.addActionListener(this);
		scratchpadItem.setEnabled(true);
		
		editCardSetItem = new JMenuItem ("Open Card Set Editor");
		editCardSetItem.addActionListener(this);
		editCardSetItem.setEnabled(true);

		toolsMenu.add(scratchpadItem);
		toolsMenu.add(editCardSetItem);
		
		theMenuBar.add(toolsMenu);
		
		// Create the "Help" menu and its component items.
		
		helpMenu = new JMenu ("Help");
		
		helpItem = new JMenuItem ("Help...");
		helpItem.addActionListener(this);
		helpMenu.add(helpItem);
		
		helpMenu.addSeparator();
		
		changelogItem = new JMenuItem ("Display Change Log...");
		changelogItem.addActionListener(this);
		helpMenu.add(changelogItem);
		
		helpMenu.addSeparator();
		
		aboutItem = new JMenuItem ("About...");
		aboutItem.addActionListener(this);
		helpMenu.add(aboutItem);
		
		theMenuBar.add (helpMenu);
		
		// Add the menu bar to this panel.
		
		add(theMenuBar);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		Object source = e.getSource();
		
		// process the load card set menu item.
		
		if (source == loadCardSetItem)
		{
			doLoadCardSetItem (e);
		}
		
		// process the append card set menu item.
		
		if (source == appendCardSetItem)
		{
			doAppendCardSetItem (e);
		}
		
		// Process the clear card set menu item.
		
		if (source == clearCardSetItem)
		{
			doClearCardSetItem (e);
		}
		
		// Process "New Test" menu item.
		
		if (source == newTestItem)
		{
			doNewTestItem (e);
		}
		
		// Process the "Restart Test" item.
		
		if (source == restartTestItem)
		{
			doRestartTestItem (e);
		}
		
		// Process "Statistics" menu item by displaying a StatisticsDisplayDialog object.
		
		if (source == showStatsItem)
		{
			doShowStatsItem (e);
		}
		
		// Process "Quit" menu item by exiting the program.
		
		if (source == quitItem)
		{
			doQuitItem (e);
		}
		
		// Process "Show Scratchpad" menu item by displaying the scratchpad.
		
		if (source == scratchpadItem)
		{
			doScratchPadItem (e);
		}
		
		if (source == editCardSetItem)
		{
			doEditCardSetItem (e);
		}
		
		// Process "Help" menu item by displaying a HelpDialog object.
		
		if (source == helpItem)
		{
			doHelpItem (e);
		}
		
		// Process "Change Log" menu item by displaying the change log information about the program.
		
		if (source == changelogItem)
		{
			doChangeLogItem (e);
		}

		// Process "About" menu item by displaying a dialog containing information about the program.
		
		if (source == aboutItem)
		{
			doAboutItem (e);
		}
	}
	
	/**
	 * This method causes a new card set to be loaded in response to the load card set menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doLoadCardSetItem (ActionEvent e)
	{		
		JFileChooser 		fileNameDialog;				// Dialog for the file name.
		String				executionPath;				// Path from where program was executed.
		FileNameExtensionFilter theFilter;
		File				theFileList [];				// List of file structures.
		int 				result;						// Result from file name chooser.
		
		// If there is no debug set being created, request that the user enter a file name.
		
		if (!InternalFlashCard.debugSet)
		{
			// Save the quick key state in case of a cancel, and then disable quick keys.
			
			boolean savedGlobalQuickKeyEnabled = AsianFlash.globalQuickKeysEnabled;
			AsianFlash.globalQuickKeysEnabled = false;
			
			executionPath = AsianFlash.userParameterData.getFilePath();
			if (executionPath != null)
				fileNameDialog = new JFileChooser (executionPath);
			else
				fileNameDialog = new JFileChooser ();
			theFilter = new FileNameExtensionFilter("AsianFlash Files", "alfc");
			fileNameDialog.setFileFilter(theFilter);
			fileNameDialog.setMultiSelectionEnabled(true);
			result = fileNameDialog.showOpenDialog(AsianFlash.mainFrame);
			
			// If the result from the file chooser dialog is to cancel, than don't do any further operation.
			
			if (result == JFileChooser.CANCEL_OPTION)
			{
				fileNameDialog = null;
				AsianFlash.globalQuickKeysEnabled = savedGlobalQuickKeyEnabled;
				return;
			}
			
			// If the result was OK, than attempt to access the file.
		
			try
			{
				theFileList = fileNameDialog.getSelectedFiles();
				AsianFlash.userParameterData.setFilePath(fileNameDialog.getCurrentDirectory().getPath());
			}
			catch (Exception exp)
			{
				JOptionPane.showMessageDialog(AsianFlash.mainFrame, "ERROR: could not access files: \n\n" + exp.toString());
				fileNameDialog = null;
				return;
			}				
		}
		else // DEBUG CODE
		{
			theFileList = null;
		}
		
		// Initialize the global card set variables, titles, and fonts to null for the new test.
		
		InternalFlashCard.initGlobalCardSetVariables();
		
		// If the statistics display dialog is open, destroy it.
		
		if (AsianFlash.theStatisticsDisplayDialog != null)
		{
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog.dispose();
			AsianFlash.theStatisticsDisplayDialog = null;
		}
		
		// Disable the newTestItem menu item.
		
		newTestItem.setEnabled(false);
		
		// Invalidate the test. The user will have to start a new test when the card set is loaded.
		
		AsianFlash.theTest = null;
		if (AsianFlash.leftScorePanel != null)
			AsianFlash.leftScorePanel.setTitle("?");
		if (AsianFlash.rightScorePanel != null)
			AsianFlash.rightScorePanel.setTitle("?");
		
		// Call the routine to load a new flash card set. If it is successful, the theFlashCardList
		// global variable will be non-null. If not, theFlashCardList will be null.
		
		final File tFileList [] = theFileList;
		
		SwingUtilities.invokeLater(new Runnable ()
		{
			public void run ()
			{
				InternalFlashCard.loadNewCardSet(tFileList);
				
				// If theFlashCardList is non-null, enable the newTestItem menu item.
				
				if (AsianFlash.theFlashCardList != null)
				{
					newTestItem.setEnabled(true);
					restartTestItem.setEnabled(false);
					showStatsItem.setEnabled(false);
					appendCardSetItem.setEnabled(true);
					clearCardSetItem.setEnabled(true);
				}
				
			}
		});
		// Get rid of the file chooser dialog.

		fileNameDialog = null;
	}
	
	/**
	 * This method causes a new card set to be appended to the existing set in response to the append card set menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doAppendCardSetItem (ActionEvent e)
	{
		JFileChooser 		fileNameDialog;				// Dialog for the file name.
		String				executionPath;				// Path from where program was executed.
		FileNameExtensionFilter theFilter;
		File				theFileList [];				// List of file structures.
		int 				result;						// Result from file name chooser.
		
		
		// Save the quick key state in case of a cancel, and then disable quick keys.
		
		boolean savedGlobalQuickKeyEnabled = AsianFlash.globalQuickKeysEnabled;
		AsianFlash.globalQuickKeysEnabled = false;
		
		// Request that the user enter a file name.
		
		executionPath = AsianFlash.userParameterData.getFilePath();
		if (executionPath != null)
			fileNameDialog = new JFileChooser (executionPath);
		else
			fileNameDialog = new JFileChooser ();
		theFilter = new FileNameExtensionFilter("AsianFlash Files", "alfc");
		fileNameDialog.setFileFilter(theFilter);
		fileNameDialog.setMultiSelectionEnabled(true);
		result = fileNameDialog.showOpenDialog(AsianFlash.mainFrame);
		
		// If the result from the file chooser dialog is to cancel, than don't do any further operation.
		
		if (result == JFileChooser.CANCEL_OPTION)
		{
			fileNameDialog = null;
			AsianFlash.globalQuickKeysEnabled = savedGlobalQuickKeyEnabled;
			return;
		}
		
		// If the result was OK, than attempt to access the file.
	
		try
		{
			theFileList = fileNameDialog.getSelectedFiles();
			AsianFlash.userParameterData.setFilePath(fileNameDialog.getCurrentDirectory().getPath());
		}
		catch (Exception exp)
		{
			JOptionPane.showMessageDialog(AsianFlash.mainFrame, "ERROR: could not access files: \n\n" + exp.toString());
			fileNameDialog = null;
			return;
		}
		
		// If the statistics dialog is open, destroy it and enable the menu item that displays the
		// statistics dialog.

		if (AsianFlash.theStatisticsDisplayDialog != null)
		{
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog.dispose();
			AsianFlash.theStatisticsDisplayDialog = null;
			showStatsItem.setEnabled(true);
		}
		
		// Call the routine to load a new flash card set. If it is successful, the theFlashCardList
		// global variable will be non-null. If not, theFlashCardList will be null.
		
		final File tFileList [] = theFileList;

		SwingUtilities.invokeLater(new Runnable ()
		{
			public void run ()
			{
				InternalFlashCard.appendCardSet(tFileList);				
			}
		});
		
		// Get rid of the file chooser dialog.

		fileNameDialog = null;			
	}
	
	/**
	 * This method causes clears the existing set in response to the clear card set menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doClearCardSetItem (ActionEvent e)
	{
		// Save the quick key state in case of a cancel, and then disable quick keys.
		
		boolean savedGlobalQuickKeyEnabled = AsianFlash.globalQuickKeysEnabled;
		AsianFlash.globalQuickKeysEnabled = false;
		
		// Display a dialog and confirm that the user really wants to clear the card set. If not, don't
		// do anything and just return. If yes, continue with the clearance.
		
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the card set?", "Clear Card Set", JOptionPane.YES_NO_OPTION);
		switch (result)
		{
			case JOptionPane.YES_OPTION:
				break;
			case JOptionPane.NO_OPTION:
				AsianFlash.globalQuickKeysEnabled = savedGlobalQuickKeyEnabled;
				return;
			default:
				return;
		}
		
		// Clear the scores.
		
		AsianFlash.rightScorePanel.setScores(0, 0, 0);
		AsianFlash.leftScorePanel.setScores(0, 0, 0);
		
		// Clear out the global variables.
		
		AsianFlash.theTest = null;
		AsianFlash.cardFileNameSet.clear();
		AsianFlash.theFlashCardList = null;
		
		// If the statistics dialog is open, destroy it and the table.
		
		if (AsianFlash.theStatisticsDisplayDialog != null)
		{
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog.dispose();
			AsianFlash.theStatisticsDisplayDialog = null;
		}
		
		// Set the control buttons to uninitiallized values.
		
		AsianFlash.theControlButtonPanel.setCenterButtonTitle("?");
		AsianFlash.theControlButtonPanel.setLeftButtonTitle("?");
		AsianFlash.theControlButtonPanel.setRightButtonTitle("?");
		AsianFlash.theControlButtonPanel.setLeftCorrectButtonTitle("? Correct");
		AsianFlash.theControlButtonPanel.setRightCorrectButtonTitle("? Correct");
		
		// Disable the control buttons.
		
		AsianFlash.theControlButtonPanel.disableLeftCorrectButton();
		AsianFlash.theControlButtonPanel.disableNextButton();
		AsianFlash.theControlButtonPanel.disableRightCorrectButton();
		AsianFlash.theControlButtonPanel.disableShowCenterButton();
		AsianFlash.theControlButtonPanel.disableShowLeftButton();
		AsianFlash.theControlButtonPanel.disableShowRightButton();
		
		// Set menu items to default states.
		
		loadCardSetItem.setEnabled(true);
		appendCardSetItem.setEnabled(false);
		clearCardSetItem.setEnabled(false);
		newTestItem.setEnabled(false);
		restartTestItem.setEnabled(false);
		showStatsItem.setEnabled(false);
		
		// Set up the display panel so that it is showing a blank slate.
		
		AsianFlash.thePanel.setTheText("");
	}
	
	/**
	 * This method causes a new test to be created in response to the new test menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doNewTestItem (ActionEvent e)
	{
		if (AsianFlash.theFlashCardList == null)
			new Error ("actionPerformed (): attempt to start new test when theFlashCardList == null");
		
		// If the statistics dialog is open, destroy it.

		if (AsianFlash.theStatisticsDisplayDialog != null)
		{
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog.dispose();
			AsianFlash.theStatisticsDisplayDialog = null;
		}
		
		// Disable quick keys.
		
		AsianFlash.globalQuickKeysEnabled = false;

		// Create the test.
		
		AsianFlash.theTest = new CardTest ();		
	}
	
	/**
	 * This method causes the current test to be restarted in response to the restart test menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doRestartTestItem (ActionEvent e)
	{
		// Save the quick key state in case of a cancel, and then disable quick keys.
		
		boolean savedGlobalQuickKeyEnabled = AsianFlash.globalQuickKeysEnabled;
		AsianFlash.globalQuickKeysEnabled = false;
		
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to restart the test?", "Restart test?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			SwingUtilities.invokeLater(new Runnable ()
			{
				public void run ()
				{
					AsianFlash.theTest.restartTest();
					
					// If the statistics dialog is open, destroy it and enable the menu item that displays the
					// statistics dialog.

					if (AsianFlash.theStatisticsDisplayDialog != null)
					{
						AsianFlash.theStatisticsTable = null;
						AsianFlash.theStatisticsDisplayDialog.dispose();
						AsianFlash.theStatisticsDisplayDialog = null;
						showStatsItem.setEnabled(true);
					}
				}
			});
		}	
		else
		{
			AsianFlash.globalQuickKeysEnabled = savedGlobalQuickKeyEnabled;
		}
	}
	
	/**
	 * This method causes the statistics window to be shown in response to the show statistics menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doShowStatsItem (ActionEvent e)
	{
		StatisticsDisplayDialog theStatsDialog = new StatisticsDisplayDialog ();
		if (theStatsDialog != null)
			theStatsDialog = null;
		showStatsItem.setEnabled(false);
	}
	
	/**
	 * This method causes the program to terminate in response to the quit menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doQuitItem (ActionEvent e)
	{
		// If the statistics dialog is open, destroy it and enable the menu item that displays the
		// statistics dialog.

		if (AsianFlash.theStatisticsDisplayDialog != null)
		{
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog.dispose();
			AsianFlash.theStatisticsDisplayDialog = null;
		}
		
		System.exit(0);
	}
	
	/**
	 * This method the help dialog in response to the help menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doHelpItem (ActionEvent e)
	{
		HelpDialog theHelpDialog = new HelpDialog ();
		if (theHelpDialog != null)
			theHelpDialog = null;
		disableHelpItem ();
	}
	
	/**
	 * This method the change log dialog in response to the change log menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doChangeLogItem (ActionEvent e)
	{
		ChangeLogDialog theChangeLogDialog;
		
		theChangeLogDialog = new ChangeLogDialog();
		if (theChangeLogDialog != null)
			theChangeLogDialog = null;
		disableChangelogItem ();
	}
	
	/**
	 * This method displays the about dialog in response to the about menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doAboutItem (ActionEvent e)
	{
		AboutDialog theAboutDialog;
		
		theAboutDialog = new AboutDialog();
		if (theAboutDialog != null)
			theAboutDialog = null;
		disableAboutItem ();
	}
	
	/**
	 * This method displays the scratchpad dialog in response to the scratchpad menu item being selected.
	 * @param e	Event that triggered the call.
	 */
	private void doScratchPadItem (ActionEvent e)
	{
		ScratchPadDialog theScratchPadDialog = new ScratchPadDialog ();
		if (theScratchPadDialog != null)
			theScratchPadDialog = null;
		disableScratchPadItem ();
	}
	
	/**
	 * This method opens the card editor dialog.
	 * @param e	Event that triggered the call.
	 */
	private void doEditCardSetItem (ActionEvent e)
	{
		// Set up a revolving cursor.
		
		AsianFlash.mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	
		SwingUtilities.invokeLater(new Runnable ()
		{
			public void run ()
			{
				AsianFlash.theCardSetEditor = new CardSetEditorDialog ();
			}
		});
		disableEditCardSetItem ();
	}
	
	/**
	 * This operation enables the Statistics menu item.
	 */
	public void enableShowStatsItem()
	{
		showStatsItem.setEnabled(true);
	}
	
	/**
	 * This operation disables the Statistics menu item.
	 */
	public void disableShowStatsItem()
	{
		showStatsItem.setEnabled(false);
	}
	
	/**
	 * This operation enables the Restart Test menu item.
	 */
	public void enableRestartTestItem()
	{
		restartTestItem.setEnabled(true);
	}
	
	/**
	 * This operation disables the Restart Test menu item.
	 */
	public void disableRestartTestItem()
	{
		restartTestItem.setEnabled(false);
	}
	
	/**
	 * This method enables the Show Scratchpad menu item.
	 */
	public void enableScratchPadItem()
	{
		scratchpadItem.setEnabled(true);
	}
	
	/**
	 * This method disables the Show Scratchpad menu item.
	 */
	public void disableScratchPadItem()
	{
		scratchpadItem.setEnabled(false);
	}
	
	/**
	 * This method enables the Show Card Editor menu ite.
	 */
	public void enableEditCardSetItem ()
	{
		editCardSetItem.setEnabled(true);
	}
	
	/**
	 * This method disables the Show Card Editor menu ite.
	 */
	public void disableEditCardSetItem ()
	{
		editCardSetItem.setEnabled(false);
	}
	
	/**
	 * This method enables the About menu item.
	 */
	public void enableAboutItem ()
	{
		aboutItem.setEnabled(true);
	}
	
	/**
	 * This method disables the About menu item.
	 */
	public void disableAboutItem ()
	{
		aboutItem.setEnabled(false);
	}
	
	/**
	 * This method enables the Help menu item.
	 */
	public void enableHelpItem ()
	{
		helpItem.setEnabled(true);
	}

	/**
	 * This method disables the Help menu item.
	 */
	public void disableHelpItem ()
	{
		helpItem.setEnabled(false);
	}
	
	/**
	 * This method enables the Change Log menu item.
	 */
	public void enableChangelogItem ()
	{
		changelogItem.setEnabled(true);
	}

	/**
	 * This method disables the Change Log menu item.
	 */
	public void disableChangelogItem ()
	{
		changelogItem.setEnabled(false);
	}
}
