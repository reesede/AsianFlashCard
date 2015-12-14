/**
 * 
 */
package asianFlash;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class contains the dialog which allows the user to edit card sets.
 * @author David E. Reese
 * @version 4.1
 *
 */

//Copyright 2014-2015 David E. Reese
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
	 * Default constructor, which creates an empty dialog.
	 */
	public CardSetEditorDialog ()
	{
		super ();
		
		createEmptyDialog ();
		initDone = true;
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
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub

		if (!initDone)
			return;
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
		setVisible (false);
		AsianFlash.theCardSetEditor = null;
		AsianFlash.theMainMenuPanel.enableEditCardSetItem();
		dispose();

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
