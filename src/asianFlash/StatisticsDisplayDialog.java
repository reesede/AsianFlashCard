/**
 * 
 */
package asianFlash;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

/**
 * This class contains the panel used to display the statistics for the current test.
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
//	20130416	DEReese				Creation (bug 000006).
//	20130418	DEReese				Changed table model to StatisticsTableModel (bug 000006).
//	20130421	DEReese				Modified to get the titles from each of the non-visible sides and
//									use them as the basis for the column names in the statistics
//									table (bug 000014).
//	20130504	DEReese				Added code to support 4th (combined) column in table (bug 000022).
//	20130511	DEReese				Made class implement WindowListener interface and added stubs for
//									windowOpened (), windowClosing (), windowClosed (), windowDeiconified (),
//									windowActivated (), and windowDeactivated (). Added code to windowClosing ()
//									and windowClosed () to set global statistics table to null. Added code
//									in actionListener () to set global statistics table pointer to null when
//									the close button is clicked (bug 000025).
//	20130512	DEReese				Added code to set global statistics table dialog and statistics table
//									to this and the created table respectively (bug 000025).
//	20130704	DEReese				Updated javadoc.
//	20131227	DEReese				Changed layout from GridBagLayout to BorderLayout. This fixed the problem
//									of the table becoming malformed when the dialog was resized (bug 000019).
//	20140312	DEReese				Changed call from getSide1 () to getSide1TextOnly () so that the HTML
//									tags are not displayed in the table (bug 000033).
//	20140512	DEReese				Changed call to CardTest::getCardCount() to CardTest::getNumCardsInTest () (bug 000039).
//	20140525	DEReese				Added check to make sure the card is in the test before processing and
//									adding it to the table in constructor (bug 000039).
//	20141208	DEReese				Made dialog free from the main frame (bug 000045).
//	20151127	DEReese				Added GPL information (bug 000047).
//

public class StatisticsDisplayDialog extends JDialog implements ActionListener, WindowListener 
{

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	private static String windowTitle = new String ("AsianFlashCard Help Text");

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
	public StatisticsDisplayDialog ()
	{
		// Call the super class constructor and set window title.
		
		super ();
		setTitle (windowTitle);
		
		// Clear the global variables if they are set.
		
		if (AsianFlash.theStatisticsDisplayDialog != null)
		{
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog.dispose ();
			AsianFlash.theStatisticsDisplayDialog = null;
		}
		
		// Set window attributes.
		
		this.setLocation(xLoc, yLoc);
		this.setResizable(true);
		
		// Set up the layout.
		
		BorderLayout theMainLayout = new BorderLayout();
		this.setLayout(theMainLayout);
		
		// Set up the statistics table.
		
		int numCards = AsianFlash.theTest.getNumCardsInTest();
		
		if (numCards < 0)
		{
			new Error ("StatisticsDisplayDialog(): numCards (" + numCards + ") must be greater than or equal to 0");
		}

		// Get the information about the visible side of the cards in the test.
		
		int visibleSide = AsianFlash.theTest.getVisibleSideNumber();
		int leftSide = AsianFlash.theTest.getLeftSideNumber();
		int rightSide = AsianFlash.theTest.getRightSideNumber();
		
		// Create a table. The additional rows are for total counts to be shown at the end of the table.
		
		StatisticsTableModel theModel = new StatisticsTableModel(numCards + StatisticsTableModel.totalStatsRows);
		theModel.setColumnName(StatisticsTableModel.visibleColumn, AsianFlash.theTest.getVisibleTitle());
		theModel.setColumnName(StatisticsTableModel.leftCountColumn, AsianFlash.theTest.getLeftTitle() + " Count");
		theModel.setColumnName(StatisticsTableModel.rightCountColumn, AsianFlash.theTest.getRightTitle() + " Count");
		theModel.setColumnName(StatisticsTableModel.combinedCountColumn, "Card Count");
		
		// Initialize the row index and temporary card pointer, and loop through each card
		// in the card set.
		
		int i = 0;
		int totalLeftGreen = 0;
		int totalLeftYellow = 0;
		int totalLeftRed = 0;
		int totalLeftGray = 0;
		int totalRightGreen = 0;
		int totalRightYellow = 0;
		int totalRightRed = 0;
		int totalRightGray = 0;
		int totalCombinedGreen = 0;
		int totalCombinedYellow = 0;
		int totalCombinedRed = 0;
		int totalCombinedGray = 0;
		
		InternalFlashCard temp = AsianFlash.theFlashCardList;

		while (temp != null)
		{
			// Verify that the row index still is in bounds.
			
			if (i >= numCards)
			{
				new Error ("StatisticsDisplayDialog(): attempt to add statistics for more cards than created in table.");
			}
			
			// Only process the card if it's in the test.
			
			if (temp.getInTest())
			{
				String visibleString = null;
				switch (visibleSide)
				{
					case 1:
						visibleString = temp.getSide1TextOnly();
						break;
					case 2:
						visibleString = temp.getSide2TextOnly();
						break;
					case 3:
						visibleString = temp.getSide3TextOnly();
						break;
					default:
						new Error ("StatisticsDisplayDialog(): attempt to get string from invalid side (" + visibleSide + ").");
				}
			
				int leftCount = 0;
				Boolean leftCorrect = new Boolean(false);
				switch (leftSide)
				{
					case 1:
						leftCount = temp.getSide1ViewsBeforeCorrect();
						leftCorrect = new Boolean(temp.getSide1Correct());
						break;
					case 2:
						leftCount = temp.getSide2ViewsBeforeCorrect();
						leftCorrect = new Boolean(temp.getSide2Correct());
						break;
					case 3:
						leftCount = temp.getSide3ViewsBeforeCorrect();
						leftCorrect = new Boolean(temp.getSide3Correct());
						break;
					default:
						new Error ("StatisticsDisplayDialog(): attempt to get leftCount for invalid side (" + leftSide + ").");
				}
			
				int rightCount = 0;
				Boolean rightCorrect = new Boolean(false);
				switch (rightSide)
				{
					case 1:
						rightCount = temp.getSide1ViewsBeforeCorrect();
						rightCorrect = new Boolean(temp.getSide1Correct());
						break;
					case 2:
						rightCount = temp.getSide2ViewsBeforeCorrect();
						rightCorrect = new Boolean(temp.getSide2Correct());
						break;
					case 3:
						rightCount = temp.getSide3ViewsBeforeCorrect();
						rightCorrect = new Boolean(temp.getSide3Correct());
						break;
					default:
						new Error ("StatisticsDisplayDialog(): attempt to get rightCount for invalid side (" + leftSide + ").");
				}

			
				// Add data for row to the table.
			
				theModel.setValueAt(new String(visibleString), i, StatisticsTableModel.visibleColumn);
				theModel.setValueAt(new Integer (leftCount), i, StatisticsTableModel.leftCountColumn);
				theModel.setValueAt(new Integer (rightCount), i, StatisticsTableModel.rightCountColumn);
				theModel.setMarkedCorrectAt(i, StatisticsTableModel.leftCorrectMarking, leftCorrect);
				theModel.setMarkedCorrectAt(i, StatisticsTableModel.rightCorrectMarking, rightCorrect);
				theModel.setCardNumber(i, temp.getCardNumber());
			
				// Add data for the combined card count and correct indicator.
			
				int combinedCount = leftCount;
				if (rightCount > combinedCount)
					combinedCount = rightCount;
			
				boolean combinedCorrect = leftCorrect && rightCorrect;
			
				theModel.setValueAt(new Integer (combinedCount), i, StatisticsTableModel.combinedCountColumn);
				theModel.setMarkedCorrectAt(i, StatisticsTableModel.combinedCorrectMarking, combinedCorrect);
			
				// Recalculate the total green, yellow, red, and gray counts for the left, right, and combined
				// columns.
			
				if (leftCount == 0)
					totalLeftGray++;
				if ((leftCount == 1) && (leftCorrect == true))
					totalLeftGreen++;
				if ((leftCount > 1) && (leftCorrect == true))
					totalLeftYellow++;
				if ((leftCount > 0) && (leftCorrect == false))
					totalLeftRed++;
			
				if (rightCount == 0)
					totalRightGray++;
				if ((rightCount == 1) && (rightCorrect == true))
					totalRightGreen++;
				if ((rightCount > 1) && (rightCorrect == true))
					totalRightYellow++;
				if ((rightCount > 0) && (rightCorrect == false))
					totalRightRed++;
			
				if (combinedCount == 0)
					totalCombinedGray++;
				if ((combinedCount == 1) && (combinedCorrect == true))
					totalCombinedGreen++;
				if ((combinedCount > 1) && (combinedCorrect == true))
					totalCombinedYellow++;
				if ((combinedCount > 0) && (combinedCorrect == false))
					totalCombinedRed++;
			
				// Increment the index into the table.
				
				i++;
			}
			
			temp = temp.getNextCard();
		}
		
		// Create the strings to contain the total green, yellow, red, and gray statistics.
		
		String totalLeftGreenString = new Integer (totalLeftGreen).toString();
		String totalLeftYellowString = new Integer (totalLeftYellow).toString();
		String totalLeftRedString = new Integer (totalLeftRed).toString();
		String totalLeftGrayString = new Integer (totalLeftGray).toString();
		
		String totalRightGreenString = new Integer (totalRightGreen).toString();
		String totalRightYellowString = new Integer (totalRightYellow).toString();
		String totalRightRedString = new Integer (totalRightRed).toString();
		String totalRightGrayString = new Integer (totalRightGray).toString();
		
		String totalCombinedGreenString = new Integer (totalCombinedGreen).toString();
		String totalCombinedYellowString = new Integer (totalCombinedYellow).toString();
		String totalCombinedRedString = new Integer (totalCombinedRed).toString();
		String totalCombinedGrayString = new Integer (totalCombinedGray).toString();
		
		totalLeftGreenString = totalLeftGreenString.concat(String.format(" (%3.2f", 100.0 * (double)totalLeftGreen / (double)(numCards))) + "%)";
		totalLeftYellowString = totalLeftYellowString.concat(String.format(" (%3.2f", 100.0 * (double)totalLeftYellow / (double)(numCards))) + "%)";
		totalLeftRedString = totalLeftRedString.concat(String.format(" (%3.2f", 100.0 * (double)totalLeftRed / (double)(numCards))) + "%)";
		totalLeftGrayString = totalLeftGrayString.concat(String.format(" (%3.2f", 100.0 * (double)totalLeftGray / (double)(numCards))) + "%)";

		totalRightGreenString = totalRightGreenString.concat(String.format(" (%3.2f", 100.0 * (double)totalRightGreen / (double)(numCards))) + "%)";
		totalRightYellowString = totalRightYellowString.concat(String.format(" (%3.2f", 100.0 * (double)totalRightYellow / (double)(numCards))) + "%)";
		totalRightRedString = totalRightRedString.concat(String.format(" (%3.2f", 100.0 * (double)totalRightRed / (double)(numCards))) + "%)";
		totalRightGrayString = totalRightGrayString.concat(String.format(" (%3.2f", 100.0 * (double)totalRightGray / (double)(numCards))) + "%)";

		totalCombinedGreenString = totalCombinedGreenString.concat(String.format(" (%3.2f", 100.0 * (double)totalCombinedGreen / (double)(numCards))) + "%)";
		totalCombinedYellowString = totalCombinedYellowString.concat(String.format(" (%3.2f", 100.0 * (double)totalCombinedYellow / (double)(numCards))) + "%)";
		totalCombinedRedString = totalCombinedRedString.concat(String.format(" (%3.2f", 100.0 * (double)totalCombinedRed / (double)(numCards))) + "%)";
		totalCombinedGrayString = totalCombinedGrayString.concat(String.format(" (%3.2f", 100.0 * (double)totalCombinedGray / (double)(numCards))) + "%)";

		// Update the rows in the table model for the total scores.
		
		theModel.setValueAt(new String ("Correct on 1 try"), numCards + StatisticsTableModel.greenRowOffset, StatisticsTableModel.visibleColumn);
		theModel.setValueAt(totalLeftGreenString, numCards + StatisticsTableModel.greenRowOffset, StatisticsTableModel.leftCountColumn);
		theModel.setValueAt(totalRightGreenString, numCards + StatisticsTableModel.greenRowOffset, StatisticsTableModel.rightCountColumn);
		theModel.setValueAt(totalCombinedGreenString, numCards + StatisticsTableModel.greenRowOffset, StatisticsTableModel.combinedCountColumn);
		
		theModel.setValueAt(new String ("Correct on > 1 try"), numCards + StatisticsTableModel.yellowRowOffset, StatisticsTableModel.visibleColumn);
		theModel.setValueAt(totalLeftYellowString, numCards + StatisticsTableModel.yellowRowOffset, StatisticsTableModel.leftCountColumn);
		theModel.setValueAt(totalRightYellowString, numCards + StatisticsTableModel.yellowRowOffset, StatisticsTableModel.rightCountColumn);
		theModel.setValueAt(totalCombinedYellowString, numCards + StatisticsTableModel.yellowRowOffset, StatisticsTableModel.combinedCountColumn);
		
		theModel.setValueAt(new String ("Shown but not correct"), numCards + StatisticsTableModel.redRowOffset, StatisticsTableModel.visibleColumn);
		theModel.setValueAt(totalLeftRedString, numCards + StatisticsTableModel.redRowOffset, StatisticsTableModel.leftCountColumn);
		theModel.setValueAt(totalRightRedString, numCards + StatisticsTableModel.redRowOffset, StatisticsTableModel.rightCountColumn);
		theModel.setValueAt(totalCombinedRedString, numCards + StatisticsTableModel.redRowOffset, StatisticsTableModel.combinedCountColumn);
		
		theModel.setValueAt(new String ("Not yet shown"), numCards + StatisticsTableModel.grayRowOffset, StatisticsTableModel.visibleColumn);
		theModel.setValueAt(totalLeftGrayString, numCards + StatisticsTableModel.grayRowOffset, StatisticsTableModel.leftCountColumn);
		theModel.setValueAt(totalRightGrayString, numCards + StatisticsTableModel.grayRowOffset, StatisticsTableModel.rightCountColumn);
		theModel.setValueAt(totalCombinedGrayString, numCards + StatisticsTableModel.grayRowOffset, StatisticsTableModel.combinedCountColumn);
		
		// Create the table.
		
		StatisticsTable statsTable = new StatisticsTable (theModel);
		statsTable.setEnabled(false);
		
		// Initialize global variables.
		
		AsianFlash.theStatisticsDisplayDialog = this;
		AsianFlash.theStatisticsTable = statsTable;
		
		// Create the scroll pane.
		
		JScrollPane theScrollPane = new JScrollPane(statsTable);
		theScrollPane.setEnabled(true);
		
		// Create and set up the table panel.
		
		JPanel tablePanel = new JPanel ();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(theScrollPane, BorderLayout.CENTER);
		
		setLayout (new BorderLayout ());
		add (tablePanel, BorderLayout.CENTER);
		
		
		// Set up the "Close" button at the bottom of the help window.
		
		JPanel buttonPanel = new JPanel ();
		
		closeButton = new JButton ("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		// Make this object the window event listener.
		
		addWindowListener (this);
		
		// Make the window visible.
		
		this.pack();
		setVisible (true);
		
}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == closeButton)
		{
			// Set the global statistics table pointer to null.
			
			AsianFlash.theStatisticsTable = null;
			AsianFlash.theStatisticsDisplayDialog = null;
			setVisible (false);
			dispose ();
			return;
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		
		AsianFlash.theStatisticsTable = null;
		AsianFlash.theStatisticsDisplayDialog = null;
		AsianFlash.theMainMenuPanel.enableShowStatsItem();
		dispose ();
	}

	@Override
	public void windowClosed(WindowEvent e) {

		AsianFlash.theStatisticsTable = null;
		AsianFlash.theStatisticsDisplayDialog = null;
		AsianFlash.theMainMenuPanel.enableShowStatsItem();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}


}
