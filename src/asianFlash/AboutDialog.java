/**
 * 
 */
package asianFlash;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;

/**
 * This class implements a dialog in which information about the program (version, author, etc.) is displayed.
 * @author David E. Reese
 * @version 4.1
 *
 */

// Copyright 2013-2015 David E. Reese
//
// This file is part of AsianFlashCard.
//
// AsianFlashCard is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// AsianFlashCard is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with AsianFlashCard.  If not, see <http://www.gnu.org/licenses/>.

//History:
//	20130412	DEReese				Added strings for description, copyright, etc. (bug 000004).
//	20130412	DEReese				Added picture (bug 000004).
//	20130414	DEReese				Added javadoc comment to actionListener().
//									Modified programName to indicate v1.7.
//	20130415	DEReese				Modified programName to indicate v1.8.
//	20130421	DEReese				Modified programName to indicate v1.9.
//	20130425	DEReese				Modified programName to indicate v1.10.
//	20130427	DEReese				Modified programName to indicate v1.11.
//	20130502	DEReese				Modified programName to indicate v1.12.
//	20130511	DEReese				Modified programName to indicate v1.13.
//	20130514	DEReese				Modified programName to indicate v2.0.
//	20130626	DEReese				Modified programName to indicate v2.1.
//									Changed code for displaying picture to get the picture from the
//									resource package. This will allow the picture to be stored directly
//									in the jar file (bug 000028).
//	20130627	DEReese				Modified programName to indicate v2.2.
//	20130704	DEReese				Updated javadoc information.
//	20131227	DEReese				Modified programName to indicate v2.3.
//	20140226	DEReese				Modified programName to indicate v2.4. Modified copyright date to 2014.
//	20140304	DEReese				Modified programName to indicate v2.5.
//	20140313	DEReese				Updated javadoc information.
//	20140318	DEReese				Modified programName to indicate v3.0.
//	20140408	DEReese				Modified programName to indicate v3.1.
//	20140420	DEReese				Modified programName to indicate v3.2.
//	20140502	DEReese				Modified programName to indicate v3.3.
//	20140703	DEReese				Modified programName to indicate v3.4.
//	20141127	DEReese				Modified programName to indicate v3.5.
//	20141202	DEReese				Modified programName to indicate v3.6.
//	20141207	DEReese				Modified programName to indicate v3.7 (unstable).
//	20141208	DEReese				Added call to MainMenuBarPanel::enableAboutItem () in actionPerformed ()
//									to re-enable the "About" menu item. Added WindowListener interface (bug 000045).
//									Made dialog free from main frame (bug 000046).
//	20150219	DEReese				Modified programName to indicate v4.0 (stable) and changed copyright
//									date to add 2015.
//	20151128	DEReese				Added GPL information to file. Deleted all existing (non-free software)
//									copyright information strings. Added JScrollPane and JTextArea and code
//									to read copyright information from the resources/Copyright.txt file
//									(bug 000047).
//									Fixed resizing problem by changing from GridBagLayout to null layout and
//									setting the location of items directly (bug 000048).
//


public class AboutDialog extends JDialog implements ActionListener, WindowListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Title of Window.
	 */
	private static String windowTitle = new String ("About AsianFlashCard");

	/**
	 * File name containing copyright information.
	 */
	private static final String copyrightFileName = "/resources/COPYRIGHT.txt";

	/**
	 * Horizontal location of frame.
	 */
	private static final int xLoc = 450;
	
	/**
	 * Vertical location of frame.
	 */
	private static final int yLoc = 250;
	
	/**
	 * Bound of dialog.
	 */
	private static final Rectangle dialogBounds = new Rectangle (xLoc, yLoc, 400, 490);
	
	/**
	 * Bounds of dialog panel.
	 */
	private static final Rectangle panelBounds = new Rectangle (0, 0, dialogBounds.width, dialogBounds.height);
	
	/**
	 * Bounds for the image.
	 */
	private static final Rectangle imageBounds = new Rectangle (52,10,295,250);
	
	/**
	 * Bounds of text area containing information about the program.
	 */
	private static final Rectangle scrollPaneBounds = new Rectangle (50, 280, 300, 150);
	
	/**
	 * Bounds of the OK button.
	 */
	private static final Rectangle buttonBounds = new Rectangle (170, 440, 60, 15);
	
	/**
	 * Image icon to be displayed in dialog.
	 */
	private ImageIcon		theImage;
	
	/**
	 * Image label to be displayed in dialog.
	 */
	private JLabel			imageLabel;
	
	/**
	 * OK Button.
	 */
	private JButton okButton;
	
	/**
	 * Default constructor. This constructor creates the dialog.
	 */
	public AboutDialog ()
	{
		super ();
		
		// Set up window panel.
		
		JPanel thePanel = new JPanel ();
		thePanel.setBounds(panelBounds);
		add (thePanel);
		
		this.setTitle(windowTitle);
		
		// Set window attributes.
		
		this.setBounds (dialogBounds);
		this.setResizable(true);
		
		// Set the layout.

		this.setLayout(null);
		thePanel.setLayout(null);
		
		// Set up the picture.
		
		theImage = new ImageIcon (getClass().getResource("/resources/AboutDisplay.jpg"));
		
		if (theImage != null)
		{
			imageLabel = new JLabel (theImage);
			imageLabel.setBounds(imageBounds);
			thePanel.add(imageLabel);
		}
		
		//////////////////////////////
		
		// Set up the text panel.
		
		JPanel textPanel = new JPanel ();
		
		JTextArea theTextArea = new JTextArea(20,30);
		theTextArea.setLineWrap(true);
		theTextArea.setEditable(false);
		theTextArea.setWrapStyleWord(true);
		
		JScrollPane theScrollPane = new JScrollPane(theTextArea);
		theScrollPane.setEnabled(true);
		textPanel.setLayout(new BorderLayout ());
		textPanel.add(theScrollPane);
		
		// Add the text to the text area from the README file.
		
		BufferedReader	theReader;
		try
		{
			InputStream theStream = getClass().getResourceAsStream(copyrightFileName);
			theReader = new BufferedReader (new InputStreamReader (theStream));
		}
		catch (Exception e)
		{
			theReader = null;
		}
		
		if (theReader != null)
		{
			String theLine = null;
			boolean done = false;
			
			while (!done)
			{
				try
				{
					theLine = theReader.readLine();
				}
				catch (IOException e)
				{
					System.out.println("DEBUG: IOException found");
					done = true;
				}
				if (theLine == null)
					done = true;
				if (!done)
				{
					theTextArea.append(theLine);
					theTextArea.append("\n");
				}
			}
		}
		else
		{
			theTextArea.setText("Sorry - no file available");
		}
		
		// Set the caret position to 0 so that the top of the text will be shown in the window.
		
		theTextArea.setCaretPosition(0);
		theScrollPane.setBounds(scrollPaneBounds);
		thePanel.add(theScrollPane);
		
		//////////////////////////////
		
		// Set up the OK button in the south panel.
		
		okButton = new JButton ("OK");
		okButton.addActionListener(this);
		okButton.setBounds(buttonBounds);
		thePanel.add(okButton);
		
		setVisible(true);
		
		repaint ();
		
		addWindowListener (this);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == okButton)
		{
			setVisible (false);
			dispose ();
			AsianFlash.theMainMenuPanel.enableAboutItem();
			return;
		}
	}

	@Override
	public void windowOpened(WindowEvent e) 
	{	
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		AsianFlash.theMainMenuPanel.enableAboutItem();
	}

	@Override
	public void windowClosed(WindowEvent e) 
	{
	}

	@Override
	public void windowIconified(WindowEvent e) 
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e) 
	{
	}

	@Override
	public void windowActivated(WindowEvent e) 
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e) 
	{
	}	
}
