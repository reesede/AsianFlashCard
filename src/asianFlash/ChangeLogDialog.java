/**
 * 
 */
package asianFlash;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author David E. Reese
 * @version	4.1
 *
 */

//Copyright 2014, 2015 David E. Reese
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


//History:
//	20140408	DEReese				Creation (bug 000036).
//	20141208	DEReese				Added WindowListener interface and call to routine in main menu
//									bar to enable the menu item when the window is closing (bug 000045).
//									Made dialog free from the main frame (bug 000046).
//	20151127	DEReese				Added GPL information (bug 000047).

public class ChangeLogDialog extends JDialog implements ActionListener, WindowListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Window title.
	 */
	private static String windowTitle = new String ("AsianFlashCard Change Log");

	/**
	 * Horizontal location of frame.
	 */
	private static final int xLoc = 250;
	
	/**
	 * Vertical location of frame.
	 */
	private static final int yLoc = 250;
	
	/**
	 * Button to close the window.
	 */
	private JButton closeButton;
	
	/**
	 * Constructor for help dialog class.
	 */
	public ChangeLogDialog ()
	{
		// Call the super class constructor and set the window title.
		
		super ();
		setTitle (windowTitle);
		
		// Set window attributes.
		
		this.setLocation(xLoc, yLoc);
		this.setResizable(true);
		
		// Set up the layout and constraints.
		
		GridBagLayout theMainLayout = new GridBagLayout ();
		GridBagConstraints theMainConstraints = new GridBagConstraints ();
		this.setLayout(theMainLayout);
				
		// Set up the "Close" button at the bottom of the help window.
		
		JPanel buttonPanel = new JPanel ();
		
		closeButton = new JButton ("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		
		// Set up the text panel.
		
		JPanel textPanel = new JPanel ();
		
		JTextArea theTextArea = new JTextArea(30, 80);
		theTextArea.setLineWrap(true);
		theTextArea.setEditable(false);
		
		JScrollPane theScrollPane = new JScrollPane(theTextArea);
		theScrollPane.setEnabled(true);
		textPanel.add(theScrollPane);
		
		// Add the text to the text area from the CHANGELOG file.
		
		BufferedReader	theReader;
		try
		{
			InputStream theStream = getClass().getResourceAsStream("/resources/CHANGELOG.txt");
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
		
		// Add the panels.
		
		theMainConstraints.weighty = 10.0;
		theMainConstraints.gridx = 0;
		theMainConstraints.gridy = 0;
		theMainLayout.setConstraints(textPanel, theMainConstraints);
		add(textPanel);
		
		theMainConstraints.weighty = 1.0;
		theMainConstraints.gridx = 0;
		theMainConstraints.gridy = 1;
		theMainLayout.setConstraints(buttonPanel, theMainConstraints);
		add(buttonPanel);
		
		// Make the window visible.
		
		this.pack();
		setVisible (true);
		
		addWindowListener (this);
}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == closeButton)
		{
			setVisible (false);
			dispose ();
			AsianFlash.theMainMenuPanel.enableChangelogItem();
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
		AsianFlash.theMainMenuPanel.enableChangelogItem();
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
