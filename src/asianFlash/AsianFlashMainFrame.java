package asianFlash;

import java.awt.*;

import javax.swing.*;

/**
 * This is the main frame which will contain the Asian Flash Card program.
 * @author David E. Reese
 * @version 4.1
 *
 */

//Copyright 2012, 2014, 2015 David E. Reese
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
//	20120824	DEReese				Commented out code for previous button.
//	20120829	DEReese				Added code for left and right score panels.
//	20140704	DEReese				Added code to create a GlobalKeyEventDispatcher object and add
//									it to the current keyboard focus manager (bug #000037).
//	20151127	DEReese				Added GPL information (bug 000047).
//

public class AsianFlashMainFrame extends JFrame {
	
	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Horizontal location of frame.
	 */
	private static final int xLoc = 200;
	
	/**
	 * Vertical location of frame.
	 */
	private static final int yLoc = 200;
	
	/**
	 * Horizontal size of frame.
	 */
	private static final int width = 1000;
	
	/**
	 * Vertical size of frame.
	 */
	private static final int height = 600;
	
	/**
	 * Layout of frame.
	 */
	private BorderLayout theLayout;
	
	/**
	 * Frame constructor. This constructor takes a title for the frame.
	 */
	public AsianFlashMainFrame (String theTitle)
	{
		super (theTitle);
		
		// Set the close operation.
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set window attributes.
		
		this.setSize(width, height);
		this.setLocation(xLoc, yLoc);
		this.setResizable(true);
		
		// Set the layout.
		theLayout = new BorderLayout ();
		this.setLayout(theLayout);
		
		// Set up the main menu bar in the "NORTH" area of the frame.
		
		AsianFlash.theMainMenuPanel = new MainMenuBarPanel ();
		add(AsianFlash.theMainMenuPanel, BorderLayout.NORTH);
		
		// Set up the text display panel in the "CENTER" area of the frame.
		
		AsianFlash.thePanel = new TextDisplayPanel ();
		add (AsianFlash.thePanel, BorderLayout.CENTER);
		
		// Set up the control button panel in the "SOUTH" area of the frame.

		AsianFlash.theControlButtonPanel = new ControlButtonPanel ("?", "?", "?");
		add (AsianFlash.theControlButtonPanel, BorderLayout.SOUTH);
		
		// Set up left score display.
		
		AsianFlash.leftScorePanel = new ScorePanel ();
		add (AsianFlash.leftScorePanel, BorderLayout.WEST);
		
		// Set up right score display.
		
		AsianFlash.rightScorePanel = new ScorePanel ();
		add (AsianFlash.rightScorePanel, BorderLayout.EAST);
		
		// Disable all buttons. The appropriate buttons will be enabled when the test is started.
		
		AsianFlash.theControlButtonPanel.disableLeftCorrectButton();
		AsianFlash.theControlButtonPanel.disableNextButton();
		AsianFlash.theControlButtonPanel.disableRightCorrectButton();
		AsianFlash.theControlButtonPanel.disableShowCenterButton();
		AsianFlash.theControlButtonPanel.disableShowLeftButton();
		AsianFlash.theControlButtonPanel.disableShowRightButton();
		
		// Set up the global key event dispatcher and add it to the current keyboard focus manager.
		
		AsianFlash.theGlobalKeyEventDispatcher = new GlobalKeyEventDispatcher ();
		if (AsianFlash.theGlobalKeyEventDispatcher == null)
			new Error ("FATAL ERROR: Could not create AsianFlash.theGlobalKeyEventDispatcher.");
		KeyboardFocusManager theManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		theManager.addKeyEventDispatcher(AsianFlash.theGlobalKeyEventDispatcher);
		
		// Make visible.
		
		this.setVisible(true);
		
	}
}
