/**
 * 
 */
package asianFlash;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This class provides the control panel containing the various buttons to handle flashcards.
 * @author 	David E. Reese
 * @version	4.1
 *
 */

//Copyright 2012-2015 David E. Reese
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
//	20120726	DEReese				Creation.
//	20120727	DEReese				Added operators to enable and disable buttons.
//									Added operators to change the titles of buttons.
//	20120824	DEReese				Added code to set separate fonts in each side of the card.
//									Commented out code for previous button (removed button).
//									Moved next button to be on the far right.
//									Changed functionality of next button to call getNewCard () instead of
//									getNextCard (). getNewCard () will generate a random card, while
//									getNextCard () is static.
//	20120825	DEReese				Added code in actionPerformed () to handle leftCorrectButton and rightCorrectButton.
//									Modified actionPerformed () to get the strings to be displayed from CardTest, not InternalFlashCard.
//	20120906	DEReese				In the code in actionPerformed () which processes the nextButton, moved the
//									calls to disableLeftCorrectButton() and disableRightCorrectButton() to
//									a position before the call to the routine to get the next card. This will
//									look a little better when there is a long search.
//	20130425	DEReese				Added setLeftIncorrectTitle () and setRightIncorrectTitle ().
//									Added setLeftButtonToCorrect (), setLeftButtonToIncorrect (),
//									setRightButtonToCorrect (), setRightButtonToIncorrect ().
//									Added leftState, rightState, leftTitle, rightTitle, leftInitCorrect,
//									rightInitCorrect, setLeftInitCorrect (), setRightInitCorrect ().
//									Modified actionListener () to toggle the correct buttons between
//									their correct and incorrect values and to display a dialog the
//									first time a side that was correct initially is changed to
//									incorrect (bug 000015).
//	20130426	DEReese				Corrected strings in call to JOptionPane.showConfirmationDialog() (bug 000015).
//	20130514	DEReese				Added calls to AsianFlash.theTest.checkTestDone () when the left and right
//									correct buttons are clicked. This will disable the next button if
//									the test is done (bug 000026).
//	20140704	DEReese				Moved code from actionPerformed () for each button into separate
//									methods so that they can be called by the routine handling quick
//									keys.
//									Updated buttons to include quick keys (bug 000037).
//	20141201	DEReese				Made buttons unfocusable (bug #000044).
//	20141214	DEReese				Added code to clear the scratchpad if the scratchpad is up and the
//									flag indicating to clear when a new card is displayed is set to true
//									(bug 000043).
//	20151127	DEReese				Added GPL information (bug 000047).
//

public class ControlButtonPanel extends JPanel implements ActionListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Layout for the panel.
	 */
	private FlowLayout theLayout;
	
	/**
	 * Button to move to next card.
	 */
	private JButton nextButton;
		
	/**
	 * Button to show left hidden side of the card.
	 */
	private JButton showLeftButton;
	
	/**
	 * Button to show the center (visible) of the card.
	 */
	private JButton showCenterButton;
	
	/**
	 * Button to show right hidden side of the card.
	 */
	private JButton showRightButton;
	
	/**
	 * Button to mark left hidden side of the card as correct.
	 */
	private JButton leftCorrectButton;
	
	/**
	 * Button to mark right hidden side of the card as correct.
	 */
	private JButton rightCorrectButton;
	
	/**
	 * State of the left button (mark correct or mark incorrect).
	 */
	private CorrectIncorrectEnum leftState = CorrectIncorrectEnum.CorrectIncorrectEnumUndefined;
	
	/**
	 * State of the right button (mark correct or mark incorrect).
	 */
	private CorrectIncorrectEnum rightState = CorrectIncorrectEnum.CorrectIncorrectEnumUndefined;
	
	/**
	 * String (minus "Correct" or "Incorrect") in left correct button.
	 */
	private String leftTitle = null;
	
	/**
	 * String (minus "Correct" or "Incorrect") in right correct button.
	 */
	private String rightTitle = null;
	
	/**
	 * Set to true if the left side of the card was initially marked correct when first displayed.
	 * This will be set to false the first time the side is marked incorrect and will not be set to
	 * true on subsequent marking of the side correct. This will provide an indication as to whether
	 * or not to display a dialog asking the user to confirm setting the side incorrect.
	 */
	private boolean leftInitCorrect = false;
	
	/**
	 * Set to true if the right side of the card was initially marked correct when first displayed.
	 * This will be set to false the first time the side is marked incorrect and will not be set to
	 * true on subsequent marking of the side correct. This will provide an indication as to whether
	 * or not to display a dialog asking the user to confirm setting the side incorrect.
	 */
	private boolean rightInitCorrect = false;
	
	/**
	 * 
	 * @param visibleTitle		Title of side of the card which is visible initially in a test.
	 * @param test1Title		Title of left hidden side of flash card.
	 * @param test2Title		Title of right hidden side of flash card.
	 */
	public ControlButtonPanel (String visibleTitle, String test1Title, String test2Title)
	{
		// Create a new layout and assign it to the panel. Note that, although FlowLayout is the
		// default layout in Java 6, this is being done to insure future compatibility.
		
		theLayout = new FlowLayout ();
		setLayout (theLayout);
		
		// Set up the border surrounding the panel.
		
		this.setBorder(new LineBorder (Color.black));

		// Create buttons.
		
		nextButton = new JButton ("Next >>" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_NEXT_CARD) + ")");
		nextButton.addActionListener(this);
		nextButton.setFocusable(false);
		
		showLeftButton = new JButton (test1Title);
		showLeftButton.addActionListener (this);
		showLeftButton.setFocusable(false);
		
		showCenterButton = new JButton (visibleTitle);
		showCenterButton.addActionListener(this);
		showCenterButton.setFocusable(false);
		
		showRightButton = new JButton (test2Title);
		showRightButton.addActionListener (this);
		showRightButton.setFocusable(false);
		
		leftCorrectButton = new JButton (new String (test1Title + " Correct"));
		leftCorrectButton.addActionListener (this);
		leftCorrectButton.setFocusable(false);
		
		rightCorrectButton = new JButton (new String (test2Title + " Correct"));
		rightCorrectButton.addActionListener (this);
		rightCorrectButton.setFocusable(false);
		
		// Initially, we will set up so that the center (visible) side of the card is visible. Since we
		// haven't had a chance to check the left and right side, those sides' "Correct" buttons will be
		// disabled.
		
		leftCorrectButton.setEnabled(false);
		rightCorrectButton.setEnabled(false);
		
		// Add the buttons to the panel.
		
		add (leftCorrectButton);
		add (showLeftButton);
		add (showCenterButton);
		add (showRightButton);
		add (rightCorrectButton);
		add (nextButton);
		
		// Set to visible.
		
		setVisible (true);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == showLeftButton)
		{
			showLeftSide ();
			return;
		}
		
		if (source == showRightButton)
		{
			showRightSide ();
			return;
		}

		if (source == showCenterButton)
		{
			showCenterSide ();
			return;
		}
		
		if (source == nextButton)
		{
			gotoNextCard();
			return;
		}
		
		if (source == leftCorrectButton)
		{
			leftCorrect ();
			return;
		}

		if (source == rightCorrectButton)
		{
			rightCorrect ();
			return;
		}
	}

	/**
	 * This method performs the actions when the "Show Left Side" button is clicked.
	 */
	public void showLeftSide ()
	{
		// If the button is disabled, don't do anything. This check is needed because the quick key
		// handler does not know if the button is enabled.
		
		if (showLeftButton.isEnabled() == false)
			return;
		
		// Show the left card side.
		
		String tempString = AsianFlash.theTest.getCurrentCardLeftString();
		AsianFlash.thePanel.setTheText(tempString, AsianFlash.theTest.getLeftFont());
		
		// Enable the left side's correct button, but disable the right side's correct button.
		
		enableLeftCorrectButton ();
		disableRightCorrectButton ();		
	}
	
	/**
	 * This method performs the actions when the "Show Right Side" button is clicked.
	 */
	public void showRightSide ()
	{
		// If the button is disabled, don't do anything. This check is needed because the smart key
		// handler does not know if the button is enabled.
		
		if (showRightButton.isEnabled() == false)
			return;
		
		// Show the right card side.
		
		String tempString = AsianFlash.theTest.getCurrentCardRightString();
		AsianFlash.thePanel.setTheText(tempString, AsianFlash.theTest.getRightFont());
		
		// Enable the right side's correct button, but disable the left side's correct button.
		
		disableLeftCorrectButton ();
		enableRightCorrectButton ();
	}
	
	/**
	 * This method performs the actions when the "Show Center Side" button is clicked.
	 */
	public void showCenterSide ()
	{
		// If the button is disabled, don't do anything. This check is needed because the quick key
		// handler does not know if the button is enabled.
		
		if (showCenterButton.isEnabled() == false)
			return;
		
		// Show the center side of the card.
		
		String tempString = AsianFlash.theTest.getCurrentCardVisibleString();
		AsianFlash.thePanel.setTheText(tempString, AsianFlash.theTest.getVisibleFont());
		
		// Disable both the left correct button and the right correct button.
		
		disableLeftCorrectButton ();
		disableRightCorrectButton ();
	}
	
	/**
	 * This method performs the actions when the "Goto Next Card" button is clicked.
	 */
	public void gotoNextCard ()
	{
		// If the button is disabled, don't do anything. This check is needed because the quick key
		// handler does not know if the button is enabled.
		
		if (nextButton.isEnabled() == false)
			return;
		
		// Disable the correct buttons.
		
		disableLeftCorrectButton ();
		disableRightCorrectButton ();

		// Get the new card and display it.
		
		InternalFlashCard tempCard = AsianFlash.theTest.getNewCard();
		if (tempCard == null)
		{
			new Error ("actionPerformed (): AsianFlash.theTest.getNewCard() returned null pointer.");
		}
		String tempString = AsianFlash.theTest.getCurrentCardVisibleString();
		AsianFlash.thePanel.setTheText(tempString, AsianFlash.theTest.getVisibleFont());	

		// Check to see if the scratchpad is being used. If so, check to see if it should be cleared
		// when a new card is displayed. If this is the case, clear the scratchpad.
		
		if (AsianFlash.theScratchPad != null)
		{
			if (AsianFlash.clearScratchPadOnNewCard)
				AsianFlash.theScratchPad.clear();
		}
	}
	
	/**
	 * This method performs the actions when the "Left Correct" button is clicked.
	 */
	public void leftCorrect ()
	{		
		// If the button is disabled, don't do anything. This check is needed because the quick key
		// handler does not know if the button is enabled.
		
		if (leftCorrectButton.isEnabled() == false)
			return;
		
		// Process based on whether or not the card already is marked correct.
		
		switch (leftState)
		{
			case CorrectIncorrectEnumCorrect:
				
				// If side is marked incorrect, mark it correct.
				
				AsianFlash.theTest.setCurrentCardLeftCorrect(true);
				setLeftButtonToIncorrect();
				break;
			case CorrectIncorrectEnumIncorrect:
				
				// If side is marked as correct and this is a repeat card, ask the user if they are
				// sure that they want to change it back to incorrect. If the side still is on the
				// first time it is visible, then don't check.
				
				if (leftInitCorrect)
				{
					switch (JOptionPane.showConfirmDialog(AsianFlash.mainFrame, "Are you sure you want to mark the left side incorrect?", "Confirmation", JOptionPane.YES_NO_OPTION))
					{
						case JOptionPane.YES_OPTION:
							AsianFlash.theTest.setCurrentCardLeftCorrect(false);
							setLeftButtonToCorrect();
							leftInitCorrect = false;
							break;
						default:
							break;
					}
				}
				else
				{
					AsianFlash.theTest.setCurrentCardLeftCorrect(false);
					setLeftButtonToCorrect();	
				}
				break;
			default:
				new Error ("actionPerformed (): leftState not valid");
		}
		AsianFlash.theTest.checkTestDone();
	}
	
	/**
	 * This method performs the actions when the "Right Correct" button is clicked.
	 */
	public void rightCorrect ()
	{
		// If the button is disabled, don't do anything. This check is needed because the quick key
		// handler does not know if the button is enabled.
		
		if (rightCorrectButton.isEnabled() == false)
			return;
		
		// Process based on whether or not the card already is marked correct.
		
		switch (rightState)
		{
			case CorrectIncorrectEnumCorrect:
				
				// If side is marked incorrect, mark it correct.
				
				AsianFlash.theTest.setCurrentCardRightCorrect(true);
				setRightButtonToIncorrect();
				break;
			case CorrectIncorrectEnumIncorrect:		
				
				// If side is marked as correct and this is a repeat card, ask the user if they are
				// sure that they want to change it back to incorrect. If the side still is on the
				// first time it is visible, then don't check.
				
				if (rightInitCorrect)
				{
					switch (JOptionPane.showConfirmDialog(AsianFlash.mainFrame, "Are you sure you want to mark the right side incorrect?", "Confirmation", JOptionPane.YES_NO_OPTION))
					{
						case JOptionPane.YES_OPTION:
							AsianFlash.theTest.setCurrentCardRightCorrect(false);
							setRightButtonToCorrect();
							rightInitCorrect = false;
							break;
						default:
							break;
					}
				}
				else
				{
					AsianFlash.theTest.setCurrentCardRightCorrect(false);
					setRightButtonToCorrect();	
				}
				break;
			default:
				new Error ("actionPerformed (): rightState not valid");
		}
		AsianFlash.theTest.checkTestDone();
	}
	
	/**
	 * This operation disables the left correct button.
	 */
	public void disableLeftCorrectButton ()
	{
		leftCorrectButton.setEnabled(false);
	}
	
	/**
	 * This operation enables the left correct button.
	 */
	public void enableLeftCorrectButton ()
	{
		leftCorrectButton.setEnabled(true);
	}
	
	/**
	 * This operation disables the right correct button.
	 */
	public void disableRightCorrectButton ()
	{
		rightCorrectButton.setEnabled(false);
	}
	
	/**
	 * This operation enables the right correct button.
	 */
	public void enableRightCorrectButton ()
	{
		rightCorrectButton.setEnabled(true);
	}
	
	/**
	 * This operation disables the show left button.
	 */
	public void disableShowLeftButton ()
	{
		showLeftButton.setEnabled(false);
	}
	
	/**
	 * This operation enables the show left button.
	 */
	public void enableShowLeftButton ()
	{
		showLeftButton.setEnabled(true);
	}
	
	/**
	 * This operation disables the show center button.
	 */
	public void disableShowCenterButton ()
	{
		showCenterButton.setEnabled(false);
	}
	
	/**
	 * This operation enables the show center button.
	 */
	public void enableShowCenterButton ()
	{
		showCenterButton.setEnabled(true);
	}
	
	/**
	 * This operation disables the show right button.
	 */
	public void disableShowRightButton ()
	{
		showRightButton.setEnabled(false);
	}
	
	/**
	 * This operation enables the show right button.
	 */
	public void enableShowRightButton ()
	{
		showRightButton.setEnabled(true);
	}
	
	/**
	 * This operation disables the next button.
	 */
	public void disableNextButton ()
	{
		nextButton.setEnabled(false);
	}
	
	/**
	 * This operation enables the next button.
	 */
	public void enableNextButton ()
	{
		nextButton.setEnabled(true);
	}
	
	/**
	 * This operation sets the title for the show left button.
	 * @param theTitle	String containing language information.
	 */
	public void setLeftButtonTitle (String theTitle)
	{
		showLeftButton.setText(theTitle + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_SHOW_LEFT) + ")");
	}
	
	/**
	 * This operation sets the title for the show center button.
	 * @param theTitle	String containing language information.
	 */
	public void setCenterButtonTitle (String theTitle)
	{
		showCenterButton.setText(theTitle + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_SHOW_VISIBLE) + ")");
	}
	
	/**
	 * This operation sets the title for the show right button.
	 * @param theTitle	String containing language information.
	 */
	public void setRightButtonTitle (String theTitle)
	{
		showRightButton.setText(theTitle + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_SHOW_RIGHT) + ")");
	}
	
	/**
	 * This operation sets the title for the left correct button, when configured to show correct.
	 * @param theTitle	String containing language information.
	 */
	public void setLeftCorrectButtonTitle (String theTitle)
	{
		leftCorrectButton.setText(theTitle + " Correct" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_LEFT_CORRECT) + ")");
		leftTitle = new String (theTitle);
		leftState = CorrectIncorrectEnum.CorrectIncorrectEnumCorrect;
	}
	
	/**
	 * This operation sets the title for the right correct button, when configured to show correct.
	 * @param theTitle	String containing language information.
	 */
	public void setRightCorrectButtonTitle (String theTitle)
	{
		rightCorrectButton.setText(theTitle + " Correct" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_RIGHT_CORRECT) + ")");
		rightTitle = new String (theTitle);
		rightState = CorrectIncorrectEnum.CorrectIncorrectEnumCorrect;
	}
	
	/**
	 * This operation sets the title for the left correct button, when configured to show incorrect.
	 * @param theTitle	String containing language information.
	 */
	public void setLeftIncorrectButtonTitle (String theTitle)
	{
		leftCorrectButton.setText(theTitle + " Incorrect" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_LEFT_CORRECT) + ")");
		leftTitle = new String (theTitle);
		leftState = CorrectIncorrectEnum.CorrectIncorrectEnumIncorrect;
	}
	
	/**
	 * This operation sets the title for the right correct button, when configured to show incorrect.
	 * @param theTitle	String containing language information.
	 */
	public void setRightIncorrectButtonTitle (String theTitle)
	{
		rightCorrectButton.setText(theTitle + " Incorrect" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_RIGHT_CORRECT) + ")");
		rightTitle = new String (theTitle);
		rightState = CorrectIncorrectEnum.CorrectIncorrectEnumIncorrect;
	}
	
	/**
	 * This operation sets the state and title of the left button to indicate marking correct.
	 */
	public void setLeftButtonToCorrect ()
	{
		// If we're already marked correct, just return.
		
		if (leftState == CorrectIncorrectEnum.CorrectIncorrectEnumCorrect)
			return;
		
		// If the leftTitle is null, generate an error.
		
		if (leftTitle == null)
			new Error ("setLeftButtonToCorrect (): leftTitle == null.");
		
		// Set the state and title.
		
		leftCorrectButton.setText(leftTitle + " Correct" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_LEFT_CORRECT) + ")");
		leftState = CorrectIncorrectEnum.CorrectIncorrectEnumCorrect;
	}
	
	/**
	 * This operation sets the state and title of the left button to indicate marking incorrect.
	 */
	public void setLeftButtonToIncorrect ()
	{
		// If we're already marked incorrect, just return.
		
		if (leftState == CorrectIncorrectEnum.CorrectIncorrectEnumIncorrect)
			return;
		
		// If the leftTitle is null, generate an error.
		
		if (leftTitle == null)
			new Error ("setLeftButtonToIncorrect (): leftTitle == null.");
		
		// Set the state and title.
		
		leftCorrectButton.setText(leftTitle + " Incorrect" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_LEFT_CORRECT) + ")");
		leftState = CorrectIncorrectEnum.CorrectIncorrectEnumIncorrect;		
	}
	
	/**
	 * This operation sets the state and title of the right button to indicate marking correct.
	 */
	public void setRightButtonToCorrect ()
	{
		// If we're already marked correct, just return.
		
		if (rightState == CorrectIncorrectEnum.CorrectIncorrectEnumCorrect)
			return;
		
		// If the rightTitle is null, generate an error.
		
		if (rightTitle == null)
			new Error ("setRightButtonToCorrect (): rightTitle == null.");
		
		// Set the state and title.
		
		rightCorrectButton.setText(rightTitle + " Correct" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_RIGHT_CORRECT) + ")");
		rightState = CorrectIncorrectEnum.CorrectIncorrectEnumCorrect;		
	}
	
	/**
	 * This operation sets the state and title of the right button to indicate marking incorrect.
	 */
	public void setRightButtonToIncorrect ()
	{
		// If we're already marked incorrect, just return.
		
		if (rightState == CorrectIncorrectEnum.CorrectIncorrectEnumIncorrect)
			return;
		
		// If the rightTitle is null, generate an error.
		
		if (rightTitle == null)
			new Error ("setRightButtonToIncorrect (): rightTitle == null.");
		
		// Set the state and title.
		
		rightCorrectButton.setText(rightTitle + " Incorrect" + " (" + AsianFlash.userParameterData.getQuickCharName(EnumQuickKeyDefs.QUICK_KEY_RIGHT_CORRECT) + ")");
		rightState = CorrectIncorrectEnum.CorrectIncorrectEnumIncorrect;		
	}
	
	/**
	 * This operation sets the indication as to whether or not the left side was correct when first
	 * displayed.
	 * @param theVal	true indicates that the side was correct. false otherwise.
	 */
	public void setLeftInitCorrect (boolean theVal)
	{
		leftInitCorrect = theVal;
	}
	
	/**
	 * This operation sets the indication as to whether or not the right side was correct when first
	 * displayed.
	 * @param theVal	true indicates that the side was correct. false otherwise.
	 */
	public void setRightInitCorrect (boolean theVal)
	{
		rightInitCorrect = theVal;
	}
}
