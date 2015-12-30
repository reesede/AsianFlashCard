/**
 * 
 */
package asianFlash;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

import javax.swing.*;

/**
 * This class implements an abstract dialog in which the program's help files can be displayed.
 * @author David E. Reese
 * @version 5.0
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
//	20130414	DEReese				Creation (bug 000005).
//	20130428	DEReese				Added call to theTextArea.setCaretPosition (0) so that the
//									top of the text window will be displayed in the dialog
//									(bug 000017).
//	20130626	DEReese				Changed code so that the README.txt file is obtained as a resource
//									in the JAR file, rather than as a separate file (bug 000028).
//	20130704	DEReese				Updated javadoc.
//	20141208	DEReese				Added  WindowListener interface and call to enable menu item when
//									the window is closing (bug 000045).
//									Made dialog free from the main frame (bug 000046).
//	20151128	DEReese				Added GPL information (bug 000047).
//									Fixed resizing problem by changing from GridBagLayout to null layout and
//									setting the location of items directly (bug 000048).
//	20151228	DEReese				Changed from having a fixed file name to having the file name passed
//									as a parameter to allow separate dialogs for the general application
//									help and the editor help. Renamed from HelpDialog to AbstractHelpDialog
//									and made into an abstract class (bug 000051).
//

public abstract class AbstractHelpDialog extends JDialog implements ActionListener, WindowListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Horizontal location of frame.
	 */
	private static final int xLoc = 250;
	
	/**
	 * Vertical location of frame.
	 */
	private static final int yLoc = 250;
	
	/**
	 * Boundaries of the dialog.
	 */
	private static final Rectangle dialogBounds = new Rectangle (xLoc, yLoc, 800, 400);
	
	/**
	 * Boundaries of the dialog panel.
	 */
	private static final Rectangle panelBounds = new Rectangle (0,0, dialogBounds.width, dialogBounds.height);

	/**
	 * Boundaries for the text panel containing the help text.
	 */
	private static final Rectangle scrollPaneBounds = new Rectangle (20, 10, dialogBounds.width - 40, dialogBounds.height - 70);

	/**
	 * Boundaries for the close button.
	 */
	private static final Rectangle buttonBounds = new Rectangle (dialogBounds.width / 2 - 50, dialogBounds.height - 50, 100, 15);
	
	/**
	 * Button to close the window.
	 */
	protected JButton closeButton;
	
	/**
	 * Constructor for help dialog class.
	 */

	/**
	 * This method displays the help dialog and displays the given help file in the dialog.
	 * @param dialogTitle	Title of the dialog.
	 * @param helpFileName	Name of the file containing the help text.
	 */
	public AbstractHelpDialog (String dialogTitle, String helpFileName)
	{
		// Call the super class constructor and set the window title.
		
		super ();
		
		setTitle (dialogTitle);
		
		// Set window attributes.
		
		this.setBounds(dialogBounds);
		this.setResizable(true);
		
		// Set up the layout and constraints.
		
		this.setLayout(null);
		
		// Set up the main panel for the dialog.

		JPanel thePanel = new JPanel ();
		thePanel.setBounds(panelBounds);
		thePanel.setLayout(null);
		add(thePanel);
		
		// Set up the "Close" button at the bottom of the help window.
		
		closeButton = new JButton ("Close");
		closeButton.setBounds(buttonBounds);
		closeButton.addActionListener(this);
		thePanel.add(closeButton);
		
		// Set up the text panel.
		
		JTextArea theTextArea = new JTextArea(30, 80);
		theTextArea.setLineWrap(true);
		theTextArea.setEditable(false);
		theTextArea.setWrapStyleWord(true);
		theTextArea.setBounds(scrollPaneBounds);
		
		JScrollPane theScrollPane = new JScrollPane(theTextArea);
		theScrollPane.setEnabled(true);
		theScrollPane.setBounds(scrollPaneBounds);
		thePanel.add(theScrollPane);
		
		// Add the text to the text area from the README file.
		
		BufferedReader	theReader;
		try
		{
			InputStream theStream = getClass().getResourceAsStream(helpFileName);
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
		
		// Make the window visible.
		
		setVisible (true);
		
		addWindowListener (this);
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	}

	@Override
	public void windowOpened(WindowEvent e) 
	{
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		AsianFlash.theMainMenuPanel.enableHelpItem();
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
