/**
 * 
 */
package asianFlash;

import java.awt.Dimension;
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
	private static final Rectangle dialogBounds = new Rectangle (300, 100, 600, 600);
	
	/**
	 * Initial boundaries of the main panel.
	 */
	public static final Rectangle mainPanelBounds = new Rectangle (0, 0, 600, 600);
	
	/**
	 * Menu bar boundaries.
	 */
	private static final Rectangle mainMenuBarBounds = new Rectangle (0, 0, 600, 15);
	
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
	CardEditorCardSidePanel cardSidePanelArray [];
	
	/**
	 * Default constructor.
	 */
	public CardSetEditorDialog ()
	{
		super ();
		
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
		
		revalidate ();
		repaint ();
		setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
