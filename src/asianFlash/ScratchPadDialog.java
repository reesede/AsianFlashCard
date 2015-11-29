/**
 * 
 */
package asianFlash;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * This class contains the panel on which the user can draw things.
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
//	20141208	DEReese				Creation (bug 000043).
//	20151127	DEReese				Added GPL information (bug 000047).
//

public class ScratchPadDialog extends JDialog implements ActionListener, ItemListener,
		WindowListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Title of window.
	 */
	private static String windowTitle = new String ("AsianFlashCard Scratch Pad");
	
	/**
	 * Horizontal location of frame.
	 */
	private static final int xLoc = 250;
	
	/**
	 * Vertical location of frame.
	 */
	private static final int yLoc = 250;
	
	/**
	 * Clear button.
	 */
	private JButton	clearButton;
	
	/**
	 * Close button.
	 */
	private JButton closeButton;
	
	/**
	 * Check box to indicate if the scratchpad should be cleared when a new card is displayed.
	 */
	private JCheckBox clearOnNewCardCheckBox;
	
	/**
	 * Scratchpad on which to draw.
	 */
	private ScratchPad drawingPad;
	
	/**
	 * Main constructor.
	 */
	public ScratchPadDialog ()
	{
		// Call the superclass constructor and set the window title.
		
		super();
		setTitle (windowTitle);
		
		// Set window attributes.
		
		this.setLocation(xLoc, yLoc);
		this.setResizable(true);
		
		// Set up the layout.
		
		BorderLayout theMainLayout = new BorderLayout ();
		this.setLayout(theMainLayout);
		
		// Build the drawing panel.
		
		drawingPad = new ScratchPad();
		add (drawingPad, BorderLayout.CENTER);
		AsianFlash.theScratchPad = drawingPad;
				
		// Build panel with close and clear buttons and button to indicate if window should be cleared
		// when the next card is selected.
		
		JPanel buttonPanel = new JPanel ();
		buttonPanel.setLayout(new GridLayout(1,3));
		
		// Build and add the Clear button.
		
		clearButton = new JButton ("Clear");
		clearButton.addActionListener(this);
		buttonPanel.add(clearButton);
		
		// Build and add the check box for determining if to clear on a new card.
		
		clearOnNewCardCheckBox = new JCheckBox ();
		clearOnNewCardCheckBox.setText("Clear when new card displayed");
		if (AsianFlash.clearScratchPadOnNewCard)
			clearOnNewCardCheckBox.setSelected(true);
		else
			clearOnNewCardCheckBox.setSelected(false);
		clearOnNewCardCheckBox.addItemListener(this);
		buttonPanel.add (clearOnNewCardCheckBox);
		
		// Build and add the Close button.
		
		closeButton = new JButton ("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		
		// Add the panel containing the buttons to the scratchpad window.
		
		add(buttonPanel,BorderLayout.SOUTH);
		
		pack ();
		setVisible (true);
		
		addWindowListener (this);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		// If the clear button is clicked, clear the drawing.
		
		if (e.getSource() == clearButton)
		{
			drawingPad.clear();
		}
		
		// If the close button is clicked, close the window.
		
		if (e.getSource() == closeButton)
		{
			setVisible(false);
			dispose();
			AsianFlash.theMainMenuPanel.enableScratchPadItem();
			AsianFlash.theScratchPad = null;
			return;
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		// Toggle the check box indicating if a clear is to be performed when a new card is selected.
		
		if (e.getItemSelectable() == clearOnNewCardCheckBox)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				AsianFlash.clearScratchPadOnNewCard = true;
			}
			if (e.getStateChange() == ItemEvent.DESELECTED)
			{
				AsianFlash.clearScratchPadOnNewCard = false;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) 
	{
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) 
	{
		AsianFlash.theMainMenuPanel.enableScratchPadItem();
		AsianFlash.theScratchPad = null;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) 
	{
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) 
	{
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) 
	{
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) 
	{
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) 
	{
	}

}
