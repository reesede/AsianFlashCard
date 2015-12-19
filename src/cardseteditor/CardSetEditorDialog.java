/**
 * 
 */
package cardseteditor;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import asianFlash.AsianFlash;

/**
 * This class contains the dialog which allows the user to edit card sets.
 * @author David E. Reese
 * @version 4.1
 *
 */

//Copyright 2015 David E. Reese
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
//	20151208	DEReese				Creation (bug 000051).
//

public class CardSetEditorDialog extends JFrame implements ActionListener, WindowListener 
{

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Title of window.
	 */
	private static final String windowTitle = "Card Set Editor";
	
	/**
	 * Initial boundaries of the dialog.
	 */
	private static final Rectangle dialogBounds = new Rectangle (300, 100, 800, 600);
	
	/**
	 * Initial boundaries of the main panel.
	 */
	public static final Rectangle mainPanelBounds = new Rectangle (0, 0, dialogBounds.width, 600);
	
	/**
	 * Menu bar boundaries.
	 */
	private static final Rectangle mainMenuBarBounds = new Rectangle (0, 0, dialogBounds.width, 15);
	
	/**
	 * Boundary of previous card button.
	 */
	private static final Rectangle previousCardButtonBounds = new Rectangle (5, 80, 120, 15);
	
	/**
	 * Boundary for display for current number.
	 */
	private static final Rectangle cardNumberTextFieldBounds = new Rectangle (130, 80, 120, 15);
	
	/**
	 * Boundary of next card button.
	 */
	private static final Rectangle nextCardButtonBounds = new Rectangle (255, 80, 120, 15);
	
	/**
	 * Boundary of create card button.
	 */
	private static final Rectangle createCardButtonBounds = new Rectangle (400, 80, 120, 15);
	
	/**
	 * Boundary of delete card button.
	 */
	private static final Rectangle deleteCardButtonBounds = new Rectangle (525, 80, 120, 15);
	
	/**
	 * Main menu bar.
	 */
	private JMenuBar mainMenuBar;
	
	/**
	 * File menu.
	 */
	private JMenu fileMenu;
	
	/**
	 * Help menu.
	 */
	private JMenu helpMenu;
	
	/**
	 * New Card Set menu item.
	 */
	private JMenuItem newCardSetItem;
	
	/**
	 * Open Card Set menu item.
	 */
	private JMenuItem openCardSetItem;
	
	/**
	 * Close Card Set menu item.
	 */
	private JMenuItem closeCardSetItem;
	
	/**
	 * Save Card Set Item.
	 */
	private JMenuItem saveCardSetItem;
	
	/**
	 * Save Card Set As Item.
	 */
	private JMenuItem saveCardSetAsItem;
	
	/**
	 * Quit Card Set Editor Item.
	 */
	private JMenuItem quitEditorItem;
	
	/**
	 * Quit Asian Flash Card Item.
	 */
	private JMenuItem quitItem;
	
	/**
	 * Card Set Editor Help Item.
	 */
	private JMenuItem editorHelpItem;
	
	/**
	 * Button to move to previous card.
	 */
	private JButton previousCardButton;
	
	/**
	 * Button to move to next card;
	 */
	private JButton nextCardButton;
	
	/**
	 * Button to create a new card.
	 */
	private JButton createCardButton;
	
	/**
	 * Button to delete a card.
	 */
	private JButton deleteCardButton;
	
	/**
	 * Text field containing the card number.
	 */
	private JTextField cardNumberTextField;
	
	/**
	 * Array of card sides.
	 */
	private CardEditorCardSidePanel cardSidePanelArray [];
	
	/**
	 * Names of font families.
	 */
	private static String [] fontFamilyNames = null;
	
	/**
	 * Indication that the font families have been discovered.
	 */
	private static Boolean fontFamiliesOK = Boolean.FALSE;
	
	/**
	 * Font family name for side 1.
	 */
	private String side1FontFamily = new String (AsianFlash.defaultSide1FontName);
	
	/**
	 * Font family name for side 2.
	 */
	private String side2FontFamily = new String (AsianFlash.defaultSide2FontName);
	
	/**
	 * Font family name for side 3.
	 */
	private String side3FontFamily = new String (AsianFlash.defaultSide3FontName);
	
	/**
	 * Size of font for side 1.
	 */
	private String side1FontSize = new String (new Integer (AsianFlash.defaultSide1Size).toString());
	
	/**
	 * Size of font for side 2.
	 */
	private String side2FontSize = new String (new Integer (AsianFlash.defaultSide2Size).toString());
	
	/**
	 * Size of font for side 3.
	 */
	private String side3FontSize = new String (new Integer (AsianFlash.defaultSide3Size).toString());

	/**
	 * I18N value for side 1.
	 */
	private String side1I18NValue = new String (new Boolean (AsianFlash.side1i18n).toString());
	
	/**
	 * I18N value for side 2.
	 */
	private String side2I18NValue = new String (new Boolean (AsianFlash.side2i18n).toString());
	
	/**
	 * I18N value for side 3.
	 */
	private String side3I18NValue = new String (new Boolean (AsianFlash.side3i18n).toString());
	
	/**
	 * Indication that initialization is done.
	 */
	private boolean initDone = false;
	
	/**
	 * Flag indicating that the card set has changes that have not been saved.
	 */
	private boolean dirtyFlag;
	
	/**
	 * Flag indicating that a quit editor command is queued up.
	 */
	private boolean quitEditorQueued;
	
	/**
	 * Flag indicating that a quit AsianFlashCard command is queued up.
	 */
	private boolean quitAsianFlashCardQueued;
	
	/**
	 * Number of cards in the card set being edited.
	 */
	private int cardsInSet;
	
	/**
	 * Number of card being edited.
	 */
	private int cardBeingEdited;
	
	/**
	 * List of cards being edited.
	 */
	private ArrayList<CardInfo>	theCardList;
	
	/**
	 * Name of file to which the card set is to be saved.
	 */
	private String saveFileName;
		
	/**
	 * Default constructor, which creates an empty dialog.
	 */
	public CardSetEditorDialog ()
	{
		super ();
		
		createEmptyDialog ();
		initDone = true;
		createEmptyCard(1);
	}
	
	private void createEmptyDialog ()
	{
		// Set the global pointer to the editor window.
		
		AsianFlash.theCardSetEditor = this;
		
		// Set up the frame properties.
		
		setBounds(dialogBounds);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle (windowTitle);
		addWindowListener(this);
		
		// Set up the main panel and scroll pane.
		
		JPanel mainPanel = new JPanel ();
		mainPanel.setLayout(null);
		mainPanel.setBounds(mainPanelBounds);
		mainPanel.setPreferredSize(new Dimension (mainPanelBounds.width-30, mainPanelBounds.height-50));
		
		JScrollPane mainScrollPane = new JScrollPane (mainPanel);
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add (mainScrollPane);
		
		// Add the menu bar.
		
		mainMenuBar = new JMenuBar ();
		mainMenuBar.setBounds(mainMenuBarBounds);
		mainPanel.add(mainMenuBar);
		
		// Set up the file menu.
		
		fileMenu = new JMenu ("File");
		
		newCardSetItem = new JMenuItem ("New");
		newCardSetItem.addActionListener(this);
		
		openCardSetItem = new JMenuItem ("Open...");
		openCardSetItem.addActionListener(this);
		
		closeCardSetItem = new JMenuItem ("Close");
		closeCardSetItem.addActionListener(this);
		
		saveCardSetItem = new JMenuItem ("Save");
		saveCardSetItem.addActionListener(this);
		
		saveCardSetAsItem = new JMenuItem ("Save As...");
		saveCardSetAsItem.addActionListener(this);
		
		quitEditorItem = new JMenuItem ("Quit Card Editor");
		quitEditorItem.addActionListener(this);
		
		quitItem = new JMenuItem ("Quit AsianFlashCard");
		quitItem.addActionListener(this);
		
		fileMenu.add(newCardSetItem);
		fileMenu.add(openCardSetItem);
		fileMenu.addSeparator();
		fileMenu.add(closeCardSetItem);
		fileMenu.addSeparator();
		fileMenu.add(saveCardSetItem);
		fileMenu.add(saveCardSetAsItem);
		fileMenu.addSeparator();
		fileMenu.add(quitEditorItem);
		fileMenu.add(quitItem);
		
		// Set up the Help menu.
		
		helpMenu = new JMenu ("Help");
		
		editorHelpItem = new JMenuItem ("Editor Help");
		editorHelpItem.addActionListener(this);
		
		helpMenu.add(editorHelpItem);
		
		// Add menus to the menu bar.
		
		mainMenuBar.add(fileMenu);
		mainMenuBar.add(helpMenu);
		
		// Set the initial menu enabled states.
		
		newCardSetItem.setEnabled(true);
		openCardSetItem.setEnabled(true);
		closeCardSetItem.setEnabled(true);
		saveCardSetItem.setEnabled(false);
		saveCardSetAsItem.setEnabled(false);
		quitEditorItem.setEnabled(true);
		quitItem.setEnabled(true);
		editorHelpItem.setEnabled(true);
		
		// Set up the previous card button.
		
		previousCardButton = new JButton ("< Prev. Card");
		previousCardButton.setBounds(previousCardButtonBounds);
		previousCardButton.addActionListener(this);
		previousCardButton.setEnabled(false);
		mainPanel.add(previousCardButton);
		
		// Set up the display of the current card.
		
		cardNumberTextField = new JTextField ();
		cardNumberTextField.setBounds(cardNumberTextFieldBounds);
		cardNumberTextField.setEditable(false);
		cardNumberTextField.setHorizontalAlignment(JTextField.CENTER);
		mainPanel.add(cardNumberTextField);
		
		// Set up the next card button.
		
		nextCardButton = new JButton ("Next Card");
		nextCardButton.setBounds(nextCardButtonBounds);
		nextCardButton.addActionListener(this);
		nextCardButton.setEnabled(false);
		mainPanel.add(nextCardButton);
		
		// Set up the create card button.
		
		createCardButton = new JButton ("New Card");
		createCardButton.setBounds(createCardButtonBounds);
		createCardButton.addActionListener(this);
		createCardButton.setEnabled(true);
		mainPanel.add(createCardButton);
		
		// Set up the delete card button.
		
		deleteCardButton = new JButton ("Delete Card");
		deleteCardButton.setBounds(deleteCardButtonBounds);
		deleteCardButton.addActionListener(this);
		deleteCardButton.setEnabled(false);
		mainPanel.add(deleteCardButton);
		
		// Add the editor panels for the three sides of the card.
		
		cardSidePanelArray = new CardEditorCardSidePanel [3];
		int startYLoc = 100;
		
		for (int i = 0; i <= 2; i++)
		{
			cardSidePanelArray[i] = new CardEditorCardSidePanel (i+1);
			cardSidePanelArray[i].setBounds(0, startYLoc, CardEditorCardSidePanel.panelSize.width, CardEditorCardSidePanel.panelSize.height);
			startYLoc += CardEditorCardSidePanel.panelSize.height;
			mainPanel.add(cardSidePanelArray[i]);
		}
		
		boolean checkFonts = false;
		while (!checkFonts)
		{
			synchronized (fontFamiliesOK)
			{
				checkFonts = fontFamiliesOK.booleanValue();
			}
			if (!checkFonts)
			{
				try 
				{
					Thread.sleep(100);
				} catch (InterruptedException e) 
				{
				}
			}
		}
		
		revalidate ();
		repaint ();
		setVisible(true);
		
		// Initialize variables indicating the status of the card set.
		
		dirtyFlag = false;
		cardBeingEdited = 0;
		cardsInSet = 0;
		theCardList = null;
		saveFileName = null;
		quitEditorQueued = false;
		quitAsianFlashCardQueued = false;
		
		// Set up a normal cursor.
		
		AsianFlash.mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * This method returns the array of strings containing the font family names.
	 * @return	String array containing font family names.
	 */
	String [] getFontFamilyNames ()
	{
		return fontFamilyNames;
	}
	
	/**
	 * This method returns the font for a card side.
	 * @param theSide	Side number (1..3).
	 * @return			Font of the size indicated for the side.
	 * @throws Error	Thrown if theSide is not in the range 1..3.
	 */
	Font getFontForSide (int theSide) throws Error
	{
		Font theFont = null;
		
		switch (theSide)
		{
			case 1:
				theFont = new Font (side1FontFamily, Font.PLAIN, new Integer (side1FontSize).intValue());
				break;
			case 2:
				theFont = new Font (side2FontFamily, Font.PLAIN, new Integer (side2FontSize).intValue());
				break;
			case 3:
				theFont = new Font (side3FontFamily, Font.PLAIN, new Integer (side3FontSize).intValue());
				break;
			default:
				throw new Error ("CardSetEditorDialog.getFontForSide () detected theSide (" + theSide + ") not in range 1..3.");
		}
		return theFont;
	}
	
	/**
	 * This method sets the font family name for a given card side.
	 * @param theSide	Side number (1..3).
	 * @param theName	Name of the font family to be assigned to the side.
	 * @throws Error	Thrown if theName is null or theSide is not in the range 1..3.
	 */
	public void setFontFamilyForSide (int theSide, String theName) throws Error
	{
		if (theName == null)
			throw new Error ("CardSetEditorDialog.setFontFamilyForSide () detected theName == null");
		
		switch (theSide)
		{
			case 1:
				side1FontFamily = theName;
				break;
			case 2:
				side2FontFamily = theName;
				break;
			case 3:
				side3FontFamily = theName;
				break;
			default:
				throw new Error ("CardSetEditorDialog.setFontFamilyForSide () detected theSide (" + theSide + ") not in range 1..3.");
		}
	}
	
	/**
	 * This method returns the font family name for a given side.
	 * @param theSide	Side number (1..3).
	 * @return			Name of the font family to be assigned to the side.
	 * @throws Error	Thrown if theSide is not in the range 1..3.
	 */
	public String getFontFamilyForSide (int theSide) throws Error
	{
		switch (theSide)
		{
			case 1:
				return side1FontFamily;
			case 2:
				return side2FontFamily;
			case 3:
				return side3FontFamily;
			default:
				throw new Error ("CardSetEditorDialog.getFontFamilyForSide () detected theSide (" + theSide + ") not in range 1..3.");
		}
	}
	
	/**
	 * This method sets the font size for a given side.
	 * @param theSide	Side number (1..3).
	 * @param theSize	Size of the font (must be >= 1).
	 * @throws Error	Thrown if theSize < 1 or theSide is not in the range 1..3.
	 */
	public void setFontSizeForSide (int theSide, int theSize) throws Error
	{
		if (theSize < 1)
			throw new Error ("CardSetEditorDialog.setFontSizeForSide () detected theSize (" + theSize + ") < 1");
		switch (theSide)
		{
			case 1:
				side1FontSize = new String (new Integer (theSize).toString());
				break;
			case 2:
				side2FontSize = new String (new Integer (theSize).toString());
				break;
			case 3:
				side3FontSize = new String (new Integer (theSize).toString());
				break;
			default:
				throw new Error ("CardSetEditorDialog.setFontSizeForSide () detected theSide (" + theSide + ") not in range 1..3.");
		}
	}
	
	/**
	 * This method gets the size of the font for a given side.
	 * @param theSide	Side number (1..3).
	 * @return			Size of the font for the side.
	 * @throws Error	Thrown if theSide is not in the range 1..3.
	 */
	public int getFontSizeForSize (int theSide) throws Error
	{
		switch (theSide)
		{
			case 1:
				return new Integer(side1FontSize).intValue();
			case 2:
				return new Integer(side2FontSize).intValue();
			case 3:
				return new Integer(side3FontSize).intValue();
		}
		return 0;
	}
	
	/**
	 * This method returns the I18N value (true or false) for a given card side.
	 * @param theSide	Side of the card (1..3).
	 * @return			true if the card side required i18n localization.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	boolean getI18NValueForSide (int theSide) throws Error
	{
		switch (theSide)
		{
			case 1:
				return new Boolean (side1I18NValue).booleanValue();
			case 2:
				return new Boolean (side2I18NValue).booleanValue();
			case 3:
				return new Boolean (side3I18NValue).booleanValue();
			default:
				throw new Error ("CardSetEditorDialog.getI18NValueForSide () detected theSide (" + theSide + ") not in range 1..3.");
		}
	}
	
	/**
	 * This method determines the available font families in the system. It should be run as a separate
	 * thread in the background, since it can take a long time, depending on how many fonts are in the
	 * system.
	 */
	public static void determineFontFamilyNames ()
	{
		// Set the flag indicating that the fonts have been found to false.
		
		synchronized (fontFamiliesOK)
		{
			fontFamiliesOK = Boolean.FALSE;
		}
		
		// Get system font names. These will be used by the CardEditorCardSidePanel.
		
		GraphicsEnvironment theGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();		
		fontFamilyNames = theGraphicsEnvironment.getAvailableFontFamilyNames();				

		// Set the flag indicating that the fonts have been found to true.
		
		synchronized (fontFamiliesOK)
		{
			fontFamiliesOK = Boolean.TRUE;
		}
	}
	
	/**
	 * This method adjusts the status of controls (buttons, menu items, etc.) based on the state of
	 * the cardset being edited.
	 */
	private void adjustControls ()
	{
		// If there is a quit queued up, disabled everything.
		
		if (quitAsianFlashCardQueued || quitEditorQueued)
		{
			newCardSetItem.setEnabled(false);
			openCardSetItem.setEnabled(false);
			closeCardSetItem.setEnabled(false);
			saveCardSetItem.setEnabled(false);
			saveCardSetAsItem.setEnabled(false);
			quitEditorItem.setEnabled(false);
			quitItem.setEnabled(false);
			editorHelpItem.setEnabled(false);
			previousCardButton.setEnabled(false);
			nextCardButton.setEnabled(false);
			createCardButton.setEnabled(false);
			deleteCardButton.setEnabled(false);
			cardSidePanelArray[0].disablePanel();
			cardSidePanelArray[1].disablePanel();
			cardSidePanelArray[2].disablePanel();
			return;
		}
		
		newCardSetItem.setEnabled(true);
		openCardSetItem.setEnabled(true);
		quitEditorItem.setEnabled(true);
		quitItem.setEnabled(true);
		editorHelpItem.setEnabled(true);
		createCardButton.setEnabled(true);
		cardSidePanelArray[0].enablePanel();
		cardSidePanelArray[1].enablePanel();
		cardSidePanelArray[2].enablePanel();
		
		if (dirtyFlag)
			saveCardSetItem.setEnabled(true);
		else
			saveCardSetItem.setEnabled(false);
		saveCardSetAsItem.setEnabled (true);
		
		if (cardsInSet > 1)
			deleteCardButton.setEnabled(true);
		else
			deleteCardButton.setEnabled(false);
		
		if (cardBeingEdited < cardsInSet)
			nextCardButton.setEnabled(true);
		else
			nextCardButton.setEnabled(false);
		
		if (cardBeingEdited > 1)
			previousCardButton.setEnabled(true);
		else
			previousCardButton.setEnabled(false);
	}
	
	/**
	 * This method creates an empty card and displays it in the card panels.
	 * @param	theCardNum	Number of card being created.
	 * @throws	Error	Thrown if theCardNum < 1.
	 */
	private void createEmptyCard (int theCardNum) throws Error
	{
		// Verify that the card number is correct.
		
		if (theCardNum < 1)
			throw new Error ("CardSetEditorDialog.createEmptyCard () detected theCardNum (" + theCardNum + ") < 1.");
		
		// If the card set is null, create it. However, if this is a card other than the first, it should
		// already have been created, so throw an exception.
		
		if (theCardList == null)
		{
			if (theCardNum == 1)
				theCardList = new ArrayList<CardInfo> ();
			else
				throw new Error ("CardSetEditorDialog.createEmptyCard () detected theCardList == null and theCardNum (" + theCardNum + ") > 1.");
		}
		
		// If the new card is not at the end of the card list, throw an exception.
		
		if (theCardNum <= theCardList.size())
			throw new Error ("CardSetEditorDialog.createEmptyCard () detected theCardNum (" + theCardNum + ") <= theCardList.size (" + theCardList.size() + ").");
		
		// Create a new card.
		
		CardInfo tCard = new CardInfo (theCardNum, "", "", "");
		theCardList.add(tCard);
		
		// Display the card sides as blank.
		
		cardSidePanelArray[0].setCardSideDoc(tCard.getCardSide1Doc());
		cardSidePanelArray[1].setCardSideDoc(tCard.getCardSide2Doc());
		cardSidePanelArray[2].setCardSideDoc(tCard.getCardSide3Doc());
		
		// Update the number of cards in the list and set the new card to the one being edited.
		
		cardsInSet++;
		setCurrentCardDisplay (theCardNum);
		
		// Don't mark the card set as dirty if this was the first, empty card.
		
		if (theCardNum > 1)
			dirtyFlag = true;
		
		adjustControls ();
	}
	
	/**
	 * This method sets the display of the current and maximum number of cards in set.
	 * @param theNum	The number of the current card.
	 * @throws Error	Thrown if theNum < 1 or theNum > number of cards in set.
	 */
	private void setCurrentCardDisplay (int theNum) throws Error
	{
		if ((theNum < 1) || (theNum > cardsInSet))
			throw new Error ("CardSetEditorDialog.setCurrentCardDisplay () detected theNum (" + theNum + ") < 1 or > cardsInSet (" + cardsInSet + ").");
		cardBeingEdited = theNum;
		cardNumberTextField.setText("Card # " + theNum + " of " + cardsInSet);
		cardSidePanelArray[0].setCardSideDoc(theCardList.get(theNum - 1).getCardSide1Doc());
		cardSidePanelArray[1].setCardSideDoc(theCardList.get(theNum - 1).getCardSide2Doc());
		cardSidePanelArray[2].setCardSideDoc(theCardList.get(theNum - 1).getCardSide3Doc());
		cardNumberTextField.repaint();
	}
	
	/**
	 * This method sets the dirty flag, indicating that unsaved changes have occurred.
	 */
	public void setDirtyFlag ()
	{
		dirtyFlag = true;
	}
	
	/**
	 * This method performs the activities required to create a new card.
	 */
	private void doCreateCardButton ()
	{
		createEmptyCard (cardsInSet + 1);
		dirtyFlag = true;
	}
	
	/**
	 * This method performs the activities required to delete a card.
	 * @throws	Error	Thrown if an attempt is made to delete the only card (the button should be disabled when only 1 card exists in the set).
	 */
	private void doDeleteCardButton () throws Error
	{
		// Throw an error if an attempt is made to delete the only card in the set.
		
		if (cardsInSet == 1)
			throw new Error ("CardSetEditorDialog.doDeleteCardButton () detected attempt to delete last card in set.");
		
		// Confirm the deletion.
		
		int deleteConfirmResponse = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete"
				+ " card number " + cardBeingEdited + "?", "Delete card number " + cardBeingEdited,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (deleteConfirmResponse == JOptionPane.YES_OPTION)
		{
			// If there are no cards behind this one, just delete the card and set the card being 
			// edited to the new last card. Otherwise, set the new card being edited to the next
			// card and adjust the card numbers of that and all following cards.
			
			if (cardBeingEdited == cardsInSet)
			{
				cardsInSet--;
				theCardList.remove(theCardList.size() - 1);
				setCurrentCardDisplay (cardBeingEdited - 1);
			}
			else
			{
				int tCardNum = cardBeingEdited;
				theCardList.remove(tCardNum - 1);
				for (int i = cardBeingEdited; i < theCardList.size(); i++)
				{
					CardInfo tCard = theCardList.get(i);
					tCard.setTheCardNumber(tCard.getTheCardNumber() - 1);
				}
				cardsInSet--;
				setCurrentCardDisplay (cardBeingEdited);
			}
			dirtyFlag = true;
			adjustControls ();
		}
	}
	
	/**
	 * This method displays the next card in the card list.
	 */
	private void doNextCardButton ()
	{
		cardBeingEdited++;
		setCurrentCardDisplay (cardBeingEdited);
		adjustControls ();
	}
	
	/**
	 * This method displays the previous card in the card list.
	 */
	private void doPreviousCardButton ()
	{
		cardBeingEdited--;
		setCurrentCardDisplay (cardBeingEdited);			
		adjustControls ();
	}
	
	/**
	 * This method performs the "Quit Editor" command: checking if changes need to be saved (and saving
	 * them if necessary) and either quitting the editor immediately or setting up a deferred quit (if
	 * changes need to be performed).
	 */
	private void doQuitEditor ()
	{
		// If there are unsaved edits in the card set, confirm the quit.
		
		if (dirtyFlag)
		{
			int quitConfirmResponse = JOptionPane.showConfirmDialog(null, "Save changes before quitting editor", "Save changes?",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			switch (quitConfirmResponse)
			{
				case JOptionPane.YES_OPTION:
					queueQuitEditor();
					doSaveFile ((this.saveFileName == null) ? true : false);
					return;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					return;
			}
		}
		setVisible (false);
		AsianFlash.theCardSetEditor = null;
		AsianFlash.theMainMenuPanel.enableEditCardSetItem();
		dispose();
	}
	
	/**
	 * This method performs the "Quit" command: checking if changes need to be saved (and saving
	 * them if necessary) and either quitting the editor immediately or setting up a deferred quit (if
	 * changes need to be performed).
	 */
	private void doQuit ()
	{
		// If there are unsaved edits in the card set, confirm the quit.
		
		if (dirtyFlag)
		{
			int quitConfirmResponse = JOptionPane.showConfirmDialog(null, "Save changes before quitting editor", "Save changes?",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			switch (quitConfirmResponse)
			{
				case JOptionPane.YES_OPTION:
					queueQuitAsianFlashCard();
					doSaveFile ((this.saveFileName == null) ? true : false);
					return;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					return;
			}
		}
		System.exit(0);
	}
	
	/**
	 * This method performs a file save operation.
	 * @param getNewFileName	True if a new file name is needed, false if the existing file name should be used.
	 */
	private void doSaveFile (boolean getNewFileName)
	{
	}

	/**
	 * This method queues up a quit operation from the editor window in case of a file save being needed.
	 */
	private void queueQuitEditor ()
	{
		quitEditorQueued = true;
		adjustControls ();
	}
	
	/**
	 * This method queues up a quit operation from the AsianFlashCard program in case of a file save being needed. 
	 */
	private void queueQuitAsianFlashCard ()
	{
		quitAsianFlashCardQueued = true;
		adjustControls ();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub

		if (!initDone)
			return;
		
		Object source = e.getSource();
		
		// Handle the create card button.
		
		if (source == createCardButton)
		{
			doCreateCardButton ();
			return;
		}
		
		// handle the next card button.
		
		if (source == nextCardButton)
		{
			doNextCardButton ();
			return;
		}
		
		// Handle the previous card button.
		
		if (source == previousCardButton)
		{
			doPreviousCardButton ();
			return;
		}
		
		// Handle the delete card button.
		
		if (source == deleteCardButton)
		{
			doDeleteCardButton ();
			return;
		}
		
		// Handle quit editor menu item.
		
		if (source == quitEditorItem)
		{
			doQuitEditor ();
		}
		
		// Handle quit AsianFlashCard program menu item.
		
		if (source == quitItem)
		{
			doQuit ();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (quitAsianFlashCardQueued || quitEditorQueued)
			return;
		
		doQuitEditor ();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) 
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
