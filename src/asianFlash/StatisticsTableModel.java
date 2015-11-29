/**
 * 
 */
package asianFlash;

import javax.swing.table.AbstractTableModel;

/**
 * This class contains the model for the statistics table.
 * @author reesede
 * @version 4.1
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
//	20130418	DEReese					Creation (bug 000006).
//	20130419	DEReese					Added setMarkedCorrectAt() and getMarkedCorrectAt() (bug 000006).
//	20130421	DEReese					Added getColumnName (), which overrides superclass operation (bug 000013).
//										Added literals for the different columns.
//	20130502	DEReese					Added combinedCountColumn, combinedCorrectMarking, maxMarkingColumns.
//										Increased value of maxColumns to 4. Modified routines to handle the
//										fourth column in the data matrix and the 3rd column in the marking
//										matrix. (bug 000022).
//	20130504	DEReese					Added a fourth column to the columnNames array (bug 000022).
//										Added literal for row containing column header.
// 										Added row offsets for total statistics. Added code to setValueAt()
//										to handle only strings in the total statistics portion of the
//										table (bug 000020).
//	20130512	DEReese					Added cardNumberList, setCardNumber (), getCardNumber (), and
//										getRowForCardNumber (). Added call to fireTableCellUpdated () in
//										setValueAt () to handle updates to the table. Added regenerateStats ()
//										(bug 000024).
//	20130704	DEReese					Updated javadoc.
//	20140314	DEReese					Updated javadoc.
//	20151127	DEReese					Added GPL information (bug 000047).
//

public class StatisticsTableModel extends AbstractTableModel {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Array containing the table data.
	 */
	private Object [][] theData;
	
	/**
	 * Secondary array containing information as to whether or not the cards represented by information
	 * in the theData array are marked correct on the left side and the right side.
	 */
	private Boolean [][] markedCorrect;
	
	/**
	 * Secondary array containing information as the number of the card in the test.
	 */
	private int [] cardNumberList;
	
	/**
	 * Column names for the table. Note that "Title" may be overridden by setColumnName().
	 */
	private String [] columnNames = {"Title", "Left Count", "Right Count", "Card Count"};
	
	/**
	 * Number of rows in the table.
	 */
	private int maxRows = 0;
	
	/**
	 * Number of cards. This should be less than maxRows.
	 */
	private int numCards = 0;
	
	/**
	 * Number of columns in the table.
	 */
	public static final int maxColumns = 4;
	
	/**
	 * Identification of column used for showing visible strings from cards.
	 */
	public static final int visibleColumn = 0;
	
	/**
	 * Identification of column used for showing left hidden side counts.
	 */
	public static final int leftCountColumn = 1;
	
	/**
	 * Identification of column used for showing right hidden side counts.
	 */
	public static final int rightCountColumn = 2;
	
	/**
	 * Identification of column used for showing combined (max) count per card.
	 */
	public static final int combinedCountColumn = 3;
	
	/**
	 * Identification of marking column to use for the left side of the card.
	 */
	public static final int leftCorrectMarking = 0;
	
	/**
	 * Identification of marking column to use for the right side of the card.
	 */
	public static final int rightCorrectMarking = 1;
	
	/**
	 * Identification of marking column to use for the combined correct value of the card.
	 */
	public static final int combinedCorrectMarking = 2;
	
	/**
	 * Maximum number of columns in marking matrix.
	 */
	public static final int maxMarkingColumns = 3;
	
	/**
	 * Row used for table headers.
	 */
	public static final int tableHeaderRow = -1;
	
	/**
	 * Offset for green row at end of the table.
	 */
	public static final int greenRowOffset = 1;
	
	/**
	 * Offset for yellow row at end of the table.
	 */
	public static final int yellowRowOffset = 2;
	
	/**
	 * Offset for red row at end of the table.
	 */
	public static final int redRowOffset = 3;
	
	/**
	 * Offset for gray row at end of the table.
	 */
	public static final int grayRowOffset = 4;
	
	/**
	 * Total rows at end of the table.
	 */
	public static final int totalStatsRows = 5;
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return maxRows;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return maxColumns;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		// Verify the row and column values.
		
		if (rowIndex < 0)
			new Error ("setValueAt(): rowIndex (" + rowIndex + ") must be >= 0");
		if (rowIndex >= maxRows)
			new Error ("setValueAt(): rowIndex (" + rowIndex + ") must be < theRowCount (" + maxRows + ")");
		if (columnIndex < 0)
			new Error ("setValueAt(): columnIndex (" + columnIndex + ") must be >= 0");
		if (columnIndex >= maxColumns)
			new Error ("setValueAt(): columnIndex (" + columnIndex + ") must be < maxColumns (" + maxColumns + ")");
		
		return theData[rowIndex][columnIndex];
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel@setValueAt(Object, int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		// Verify the row and column values.
		
		if (rowIndex < 0)
			new Error ("setValueAt(): rowIndex (" + rowIndex + ") must be >= 0");
		if (rowIndex >= maxRows)
			new Error ("setValueAt(): rowIndex (" + rowIndex + ") must be < theRowCount (" + maxRows + ")");
		if (columnIndex < 0)
			new Error ("setValueAt(): columnIndex (" + columnIndex + ") must be >= 0");
		if (columnIndex >= maxColumns)
			new Error ("setValueAt(): columnIndex (" + columnIndex + ") must be < maxColumns (" + maxColumns + ")");
		
		// Put the data in the correct location, based on the columnIndex. If the rowIndex indicates
		// that the row is part of the total statistics, the object will be a string.
		
		if (rowIndex < numCards)
		{
			switch (columnIndex)
			{
				case 0:
					theData[rowIndex][visibleColumn] = new String ((String)aValue);
					break;
				case 1:
					theData[rowIndex][leftCountColumn] = new Integer ((Integer)aValue);
					break;
				case 2:
					theData[rowIndex][rightCountColumn] = new Integer ((Integer)aValue);
					break;
				case 3:
					theData[rowIndex][combinedCountColumn] = new Integer ((Integer)aValue);
					break;
			}
			
		}
		else
		{
			theData[rowIndex][columnIndex] = new String ((String)aValue);
		}
		
		fireTableCellUpdated (rowIndex, columnIndex);
	}
	
	/**
	 * This operation sets the name of one of the columns in the table.
	 * @param theColumn	Column whose name is to be set.
	 * @param theName	New name of the column.
	 */
	public void setColumnName (int theColumn, String theName)
	{
		// Verify the column information.
		
		if (theColumn < 0)
			new Error ("setColumnName(): theColumn (" + theColumn + ") must be >= 0");
		if (theColumn >= maxColumns)
			new Error ("setColumnName(): theColumn (" + theColumn + ") must be < maxColumns (" + maxColumns + ")");
		
		columnNames[theColumn] = new String (theName);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel@getColumnName (int)
	 */
	@Override
	public String getColumnName (int col)
	{
		// Verify that the column number is valid.
		
		if (col < 0)
			new Error ("setColumnName(): theColumn (" + col + ") must be >= 0");
		if (col >= maxColumns)
			new Error ("setColumnName(): theColumn (" + col + ") must be < maxColumns (" + maxColumns + ")");

		// Return the column name.
		
		return new String (columnNames[col]);
	}
	
	/**
	 * Constructor indicating the number of rows in the table.
	 * @param rowCount	Number of rows to enter into table.
	 */
	public StatisticsTableModel(int rowCount)
	{
		// Verify the row count.
		
		if (rowCount < 0)
			new Error ("StatisticsTableModel(int, int): rowCount (" + rowCount + ") must be >= 0");
		
		// Create the data table.
		
		theData = new Object[rowCount][maxColumns];
		maxRows = rowCount;
		numCards = rowCount - totalStatsRows;
		
		// Create the marked correct table (not displayed).
		
		markedCorrect = new Boolean[rowCount][maxMarkingColumns];
		
		// Initialize the card number list.
		
		cardNumberList = new int[rowCount];
		for (int i = 0; i < rowCount; i++)
			cardNumberList[i] = 0;
	}
	
	/**
	 * This operation sets the indication as to whether or not the left or right side of the card
	 * has been marked correct.
	 * @param rowIndex	Row in table.
	 * @param sideIndex	Side to set (0 = left side, 1 = right side).
	 * @param theValue	Indication as to whether the side has been marked correct or not.
	 */
	public void setMarkedCorrectAt (int rowIndex, int sideIndex, Boolean theValue)
	{
		// Verify the row and column locations.
		
		if (rowIndex < 0)
			new Error ("setMarkedCorrectAt(): rowIndex (" + rowIndex + ") must be >= 0");
		if (rowIndex >= maxRows)
			new Error ("setMarkedCorrectAt(): rowIndex (" + rowIndex + ") must be < theRowCount (" + maxRows + ")");
		if (sideIndex < 0)
			new Error ("setMarkedCorrectAt(): sideIndex (" + sideIndex + ") must be >= 0");
		if (sideIndex >= maxMarkingColumns)
			new Error ("setMarkedCorrectAt(): sideIndex (" + sideIndex + ") must be < maxMarkingColumns");

		// Set the marked correct value based on the row and column.
		
		markedCorrect[rowIndex][sideIndex] = new Boolean (theValue);
	}
	
	/**
	 * This operation returns an indication as to whether or not a side has been marked correct.
	 * @param rowIndex	Row in table.
	 * @param sideIndex	Side of card (0 = left side, 1 = right side). 
	 * @return			Value indicating if the side has been marked correct, or null if undefined.
	 */
	public Boolean getMarkedCorrectAt (int rowIndex, int sideIndex)
	{
		// Verify the row and side locations.
		
		if (rowIndex < 0)
			new Error ("setMarkedCorrectAt(): rowIndex (" + rowIndex + ") must be >= 0");
		if (rowIndex >= maxRows)
			new Error ("setMarkedCorrectAt(): rowIndex (" + rowIndex + ") must be < theRowCount (" + maxRows + ")");
		if (sideIndex < 0)
			new Error ("setMarkedCorrectAt(): sideIndex (" + sideIndex + ") must be >= 0");
		if (sideIndex >= maxMarkingColumns)
			new Error ("setMarkedCorrectAt(): sideIndex (" + sideIndex + ") must be < maxMarkingColumns");

		// Return the selected value. If it is null, return null.
		
		if (markedCorrect[rowIndex][sideIndex] == null)
			return (null);
		return (new Boolean (markedCorrect[rowIndex][sideIndex]));
	}
	
	/**
	 * This operation returns the number of cards in the model table.
	 * @return Number of cards in model table.
	 */
	public int getNumCards ()
	{
		return numCards;
	}
	
	/**
	 * Sets the card number for a given row.
	 * @param rowIndex		Index of the row.
	 * @param newCardNumber	Number of the card. This must be > 0.
	 */
	public void setCardNumber (int rowIndex, int newCardNumber)
	{
		// Verify the row location and new card number.
		
		if (rowIndex < 0)
			new Error ("setCardNumber (): rowIndex (" + rowIndex + ") must be >= 0");
		if (rowIndex >= maxRows)
			new Error ("setCardNumber (): rowIndex (" + rowIndex + ") must be < theRowCount (" + maxRows + ")");
		if (newCardNumber <= 0)
			new Error ("setCardNumber (): newCardNumber (" + newCardNumber + ") must be > 0");
		
		// Set the card number.
		
		cardNumberList[rowIndex] = newCardNumber;
	}

	/**
	 * This operation returns the card number for a given row.
	 * @param rowIndex	The row for which the card number is to be found.
	 * @return	The card number. 0 indicates that the card number never was initialized.
	 */
	public int getCardNumber (int rowIndex)
	{
		// Verify the row locations.
		
		if (rowIndex < 0)
			new Error ("getCardNumber (): rowIndex (" + rowIndex + ") must be >= 0");
		if (rowIndex >= maxRows)
			new Error ("getCardNumber (): rowIndex (" + rowIndex + ") must be < theRowCount (" + maxRows + ")");

		// Return the card number.
		
		return cardNumberList[rowIndex];
	}
	
	/**
	 * This operation returns the number of the row corresponding to a given card number.
	 * @param cardNumber	Number of the card. This must be > 0.
	 * @return	The row corresponding to the card with the given cardNumber. If not found, then -1 is returned.
	 */
	public int getRowForCardNumber (int cardNumber)
	{
		// Verify the card number.
		
		if (cardNumber <= 0)
			new Error ("getRowForCardNumber (): cardNumber (" + cardNumber + ") must be > 0");
		
		// Search for the card number in the cardNumberList and return the corresponding row number if found.
		
		int theRow = 0;
		while (theRow < maxRows)
		{
			if (cardNumberList[theRow] == cardNumber)
			{
				return theRow;
			}
			theRow++;
		}
		
		// If the card number was not found, return -1.
		
		return -1;
	}
	
	/**
	 * This operation regenerates the statistics table entries for a given card number.
	 * @param cardNumber		The card number for which the table entries is to be regenerated.
	 * @param newLeftCount		New left side count.
	 * @param newRightCount		New right side count.
	 * @param newLeftCorrect	New indication if the left side is correct.
	 * @param newRightCorrect	New indication if the right side is correct.
	 */
	public void regenerateStats (int cardNumber, int newLeftCount, int newRightCount, boolean newLeftCorrect, boolean newRightCorrect)
	{
		// Verify the input parameters.
		
		if (cardNumber <= 0)
			new Error ("regenerateStats (): cardNumber (" + cardNumber + ") must be > 0");
		if (newLeftCount < 0)
			new Error ("regenerateStats (): newLeftCount (" + newLeftCount + ") must be >= 0");
		if (newRightCount < 0)
			new Error ("regenerateStats (): newRightCount (" + newRightCount + ") must be >= 0");
		
		// Get the row for the card.
		
		int theRow = getRowForCardNumber (cardNumber);
		if (theRow == -1)
			new Error ("regenerateStats (): could not get row number for cardNumber (" + cardNumber + ").");
	
		// Get pointers to the current values of the counts and markings.
		
		Integer currentLeftCount = (Integer)theData[theRow][leftCountColumn];
		Integer currentRightCount = (Integer)theData[theRow][rightCountColumn];
		Integer currentCardCount = (Integer)theData[theRow][combinedCountColumn];
		Boolean currentLeftMarking = (Boolean)markedCorrect[theRow][leftCorrectMarking];
		Boolean currentRightMarking = (Boolean)markedCorrect[theRow][rightCorrectMarking];
		Boolean currentCardMarking = (Boolean)markedCorrect[theRow][combinedCorrectMarking];
		
		if (currentLeftCount == null)
			new Error ("regenerateStats (): could not get currentLeftCount.");
		if (currentRightCount == null)
			new Error ("regenerateStats (): could not get currentRightCount.");
		if (currentLeftMarking == null)
			new Error ("regenerateStats (): could not get currentLeftMarking.");
		if (currentRightMarking == null)
			new Error ("regenerateStats (): could not get currentRightMarking.");
		
		// Update the counts and markings if needed.
		
		if (currentLeftCount.intValue() != newLeftCount)
			setValueAt (new Integer (newLeftCount), theRow, leftCountColumn);

		if (currentRightCount.intValue() != newRightCount)
			setValueAt (new Integer (newRightCount), theRow, rightCountColumn);
		
		if (currentLeftMarking.booleanValue() != newLeftCorrect)
		{
			setMarkedCorrectAt (theRow, leftCorrectMarking, new Boolean (newLeftCorrect));
			fireTableCellUpdated (theRow, leftCountColumn);
		}
		
		if (currentRightMarking.booleanValue() != newRightCorrect)
		{
			setMarkedCorrectAt (theRow, rightCorrectMarking, new Boolean (newRightCorrect));
			fireTableCellUpdated (theRow, rightCountColumn);
		}
		
		int newCardCount = newLeftCount;
		if (newRightCount > newCardCount)
			newCardCount = newRightCount;
		
		boolean newCardCorrect = newLeftCorrect && newRightCorrect;
		
		if (newCardCount > currentCardCount)
			setValueAt (new Integer (newCardCount), theRow, combinedCountColumn);
		
		if (currentCardMarking.booleanValue() != newCardCorrect)
		{
			setMarkedCorrectAt (theRow, combinedCorrectMarking, new Boolean (newCardCorrect));
			fireTableCellUpdated(theRow, combinedCountColumn);
		}
		
		// Calculate the current and new total counts.
		
		
		int newTotalLeftGreen = 0;
		int newTotalLeftYellow = 0;
		int newTotalLeftRed = 0;
		int newTotalLeftGray = 0;
		int newTotalRightGreen = 0;
		int newTotalRightYellow = 0;
		int newTotalRightRed = 0;
		int newTotalRightGray = 0;
		int newTotalCardGreen = 0;
		int newTotalCardYellow = 0;
		int newTotalCardRed = 0;
		int newTotalCardGray = 0;
		
		for (int i = 0; i < numCards; i++)
		{
			// Check the left side info for the row.
			
			int testVal = ((Integer)(theData[i][leftCountColumn])).intValue();
			boolean testMarking = ((Boolean)(markedCorrect[i][leftCorrectMarking])).booleanValue();
			if ((testVal == 1) && (testMarking == true))
				newTotalLeftGreen++;
			if ((testVal > 1) && (testMarking == true))
				newTotalLeftYellow++;
			if ((testVal > 0) && (testMarking == false))
				newTotalLeftRed++;
			if (testVal == 0)
				newTotalLeftGray++;
			
			// Check the right side info for the row.
			
			testVal = ((Integer)(theData[i][rightCountColumn])).intValue();
			testMarking = ((Boolean)(markedCorrect[i][rightCorrectMarking])).booleanValue();
			if ((testVal == 1) && (testMarking == true))
				newTotalRightGreen++;
			if ((testVal > 1) && (testMarking == true))
				newTotalRightYellow++;
			if ((testVal > 0) && (testMarking == false))
				newTotalRightRed++;
			if (testVal == 0)
				newTotalRightGray++;
			
			// Check the info for the card for the row.
			
			testVal = ((Integer)(theData[i][combinedCountColumn])).intValue();
			testMarking = ((Boolean)(markedCorrect[i][combinedCorrectMarking])).booleanValue();
			if ((testVal == 1) && (testMarking == true))
				newTotalCardGreen++;
			if ((testVal > 1) && (testMarking == true))
				newTotalCardYellow++;
			if ((testVal > 0) && (testMarking == false))
				newTotalCardRed++;
			if (testVal == 0)
				newTotalCardGray++;			
		}
		
		// Create strings based on the new values.
		
		String totalLeftGreenString = new Integer (newTotalLeftGreen).toString();
		String totalLeftYellowString = new Integer (newTotalLeftYellow).toString();
		String totalLeftRedString = new Integer (newTotalLeftRed).toString();
		String totalLeftGrayString = new Integer (newTotalLeftGray).toString();
		
		String totalRightGreenString = new Integer (newTotalRightGreen).toString();
		String totalRightYellowString = new Integer (newTotalRightYellow).toString();
		String totalRightRedString = new Integer (newTotalRightRed).toString();
		String totalRightGrayString = new Integer (newTotalRightGray).toString();
		
		String totalCardGreenString = new Integer (newTotalCardGreen).toString();
		String totalCardYellowString = new Integer (newTotalCardYellow).toString();
		String totalCardRedString = new Integer (newTotalCardRed).toString();
		String totalCardGrayString = new Integer (newTotalCardGray).toString();
		
		totalLeftGreenString = totalLeftGreenString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalLeftGreen / (double)(numCards))) + "%)";
		totalLeftYellowString = totalLeftYellowString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalLeftYellow / (double)(numCards))) + "%)";
		totalLeftRedString = totalLeftRedString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalLeftRed / (double)(numCards))) + "%)";
		totalLeftGrayString = totalLeftGrayString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalLeftGray / (double)(numCards))) + "%)";

		totalRightGreenString = totalRightGreenString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalRightGreen / (double)(numCards))) + "%)";
		totalRightYellowString = totalRightYellowString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalRightYellow / (double)(numCards))) + "%)";
		totalRightRedString = totalRightRedString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalRightRed / (double)(numCards))) + "%)";
		totalRightGrayString = totalRightGrayString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalRightGray / (double)(numCards))) + "%)";

		totalCardGreenString = totalCardGreenString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalCardGreen / (double)(numCards))) + "%)";
		totalCardYellowString = totalCardYellowString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalCardYellow / (double)(numCards))) + "%)";
		totalCardRedString = totalCardRedString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalCardRed / (double)(numCards))) + "%)";
		totalCardGrayString = totalCardGrayString.concat(String.format(" (%3.2f", 100.0 * (double)newTotalCardGray / (double)(numCards))) + "%)";

		// Set the cells in the table.
		
		setValueAt (totalLeftGreenString, numCards + greenRowOffset, leftCountColumn);
		setValueAt (totalRightGreenString, numCards + greenRowOffset, rightCountColumn);
		setValueAt (totalCardGreenString, numCards + greenRowOffset, combinedCountColumn);
		
		setValueAt (totalLeftYellowString, numCards + yellowRowOffset, leftCountColumn);
		setValueAt (totalRightYellowString, numCards + yellowRowOffset, rightCountColumn);
		setValueAt (totalCardYellowString, numCards + yellowRowOffset, combinedCountColumn);
		
		setValueAt (totalLeftRedString, numCards + redRowOffset, leftCountColumn);
		setValueAt (totalRightRedString, numCards + redRowOffset, rightCountColumn);
		setValueAt (totalCardRedString, numCards + redRowOffset, combinedCountColumn);
		
		setValueAt (totalLeftGrayString, numCards + grayRowOffset, leftCountColumn);
		setValueAt (totalRightGrayString, numCards + grayRowOffset, rightCountColumn);
		setValueAt (totalCardGrayString, numCards + grayRowOffset, combinedCountColumn);
	}
}
