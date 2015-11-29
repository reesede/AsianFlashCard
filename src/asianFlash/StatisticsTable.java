/**
 * 
 */
package asianFlash;

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.*;

/**
 * This class defines the statistics table. The main purpose is to allow custom rendering.
 * @author David E. Reese
 * @version 4.1
 */

//Copyright 2013, 2015 David E. Reese
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
//	20130418	DEReese				Creation (bug 000006).
//	20130420	DEReese				Added prepareRenderer() (bug 000006).
//	20130421	DEReese				Changed color code for card sides to LIGHT_GRAY, RED, YELLOW,
//									and GREEN (bug 000012).
//									Used literals for columns in the model and correct marking.
//	20130427	DEReese				Added code to get the font size from the global user parameters
//									and use them for the header and entry font sizes (bug 000018).
//	20130504	DEReese				Added code to handle combined card (fourth) column (bug 000022).
//									Replaced column numbers with literals from StatisticsTableModel.
//									Added code to set initial column sizes (bug 000023).
//									Added code to format total statistics counts at bottom of table (bug 000020).
//	20130512	DEReese				Added regenerateStats () (bug 000024).
//	20130704	DEReese				Updated javadoc.
//	20151127	DEReese				Added GPL information (bug 000047).

public class StatisticsTable extends JTable {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
		
	/**
	 * Preferred width of the column containing the visible side of the card.
	 */
	public static final int visibleColumnPreferredWidth = 200;
	
	/**
	 * Preferred width of the column containing the counts for the left side of the card.
	 */
	public static final int leftColumnPreferredWidth = 75;
	
	/**
	 * Preferred width of the column containing the counts for the right side of the card.
	 */
	public static final int rightColumnPreferredWidth = 75;
	
	/**
	 * Preferred width of the column containing the combined counts for the card.
	 */
	public static final int combinedColumnPreferredWidth = 75;
	
	/**
	 * Default constructor.
	 * @param theModel	Model for the table.
	 */
	public StatisticsTable (StatisticsTableModel theModel)
	{
		super(theModel);
		
		this.getColumnModel().getColumn(0).setPreferredWidth(visibleColumnPreferredWidth);
		this.getColumnModel().getColumn(1).setPreferredWidth(leftColumnPreferredWidth);
		this.getColumnModel().getColumn(2).setPreferredWidth(rightColumnPreferredWidth);
		this.getColumnModel().getColumn(3).setPreferredWidth(combinedColumnPreferredWidth);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel@prepareRenderer(TableCellRenderer, int, int)
	 */
	@Override
	public Component prepareRenderer (TableCellRenderer renderer, int row, int column)
	{
		Component theComponent = super.prepareRenderer(renderer, row, column);
		
		// Verify that the row is valid. Note that -1 means the column names.
		
		if (row < -1)
			new Error ("prepareRenderer(): row (" + row + ") must be >= -1");
		if (column < 0)
			new Error ("prepareRenderer(): column (" + column + ") must be >= 0");
		
		// Set the component to white by default to deal with re-use of components.
		
		theComponent.setBackground(Color.WHITE);
		
		// See if this is the column names. If so, just return.
		
		if (row == StatisticsTableModel.tableHeaderRow)
		{
			// Set up the font size for the component if it already is not equal to the size specified
			// in the global user parameters.
			
			if (theComponent.getFont().getSize2D() != AsianFlash.userParameterData.getStatisticsTableHeaderFontSize())
				theComponent.setFont(theComponent.getFont().deriveFont(AsianFlash.userParameterData.getStatisticsTableHeaderFontSize()));
			return theComponent;
		}
		
		if (row >= ((StatisticsTableModel)this.getModel()).getNumCards())
		{
			if (row == ((StatisticsTableModel)this.getModel()).getNumCards() + StatisticsTableModel.greenRowOffset)
			{
				theComponent.setBackground (Color.GREEN);
				return theComponent;			
			}
			if (row == ((StatisticsTableModel)this.getModel()).getNumCards() + StatisticsTableModel.yellowRowOffset)
			{
				theComponent.setBackground (Color.YELLOW);
				return theComponent;			
			}
			if (row == ((StatisticsTableModel)this.getModel()).getNumCards() + StatisticsTableModel.redRowOffset)
			{
				theComponent.setBackground (Color.RED);
				return theComponent;			
			}
			if (row == ((StatisticsTableModel)this.getModel()).getNumCards() + StatisticsTableModel.grayRowOffset)
			{
				theComponent.setBackground (Color.LIGHT_GRAY);
				return theComponent;			
			}
			return theComponent;
		}

		// Set up the font size for the component if it already is not equal to the size specified
		// in the global user parameters.
		
		if (theComponent.getFont().getSize2D() != AsianFlash.userParameterData.getStatisticsTableFontSize())
			theComponent.setFont(theComponent.getFont().deriveFont(AsianFlash.userParameterData.getStatisticsTableFontSize()));
		
		// If this is the column containing the visible card side, just return.
		
		if (column == StatisticsTableModel.visibleColumn)
			return theComponent;
		
		// Check to see if the object is in column 1, which is the left side of the card. If so,
		// check to see if the left side is marked correct.
				
		if (column == StatisticsTableModel.leftCountColumn)
		{
			if ((((StatisticsTableModel)this.getModel()).getMarkedCorrectAt(row, StatisticsTableModel.leftCorrectMarking).booleanValue() == Boolean.TRUE) &&
					((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.leftCountColumn) == 1))
			{
				theComponent.setBackground (Color.GREEN);
				return theComponent;
			}
			if ((((StatisticsTableModel)this.getModel()).getMarkedCorrectAt(row, StatisticsTableModel.leftCorrectMarking).booleanValue() == Boolean.TRUE) &&
					((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.leftCountColumn) > 1))
			{
				theComponent.setBackground (Color.YELLOW);
				return theComponent;
			}
			if ((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.leftCountColumn) > 0)
			{
				theComponent.setBackground (Color.RED);
				return theComponent;
			}
			if ((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.leftCountColumn) == 0)
			{
				theComponent.setBackground (Color.LIGHT_GRAY);
				return theComponent;
			}
		}
		
		// Check to see if the object is in column 2, which is the right side of the card. If so,
		// check to see if the right side is marked correct.
		
		if (column == StatisticsTableModel.rightCountColumn)
		{
			if ((((StatisticsTableModel)this.getModel()).getMarkedCorrectAt(row, StatisticsTableModel.rightCorrectMarking).booleanValue() == Boolean.TRUE) &&
					((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.rightCountColumn) == 1))
			{
				theComponent.setBackground (Color.GREEN);
				return theComponent;
			}
			if ((((StatisticsTableModel)this.getModel()).getMarkedCorrectAt(row, StatisticsTableModel.rightCorrectMarking).booleanValue() == Boolean.TRUE) &&
					((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.rightCountColumn) > 1))
			{
				theComponent.setBackground (Color.YELLOW);
				return theComponent;
			}
			if ((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.rightCountColumn) > 0)
			{
				theComponent.setBackground (Color.RED);
				return theComponent;
			}
			if ((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.rightCountColumn) == 0)
			{
				theComponent.setBackground (Color.LIGHT_GRAY);
				return theComponent;
			}
		}

		// Check to see if the object is in column 3, which is the combined value of the card. If so,
		// check to see if the card is marked correct.
		
		if (column == StatisticsTableModel.combinedCountColumn)
		{
			if ((((StatisticsTableModel)this.getModel()).getMarkedCorrectAt(row, StatisticsTableModel.combinedCorrectMarking).booleanValue() == Boolean.TRUE) &&
					((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.combinedCountColumn) == 1))
			{
				theComponent.setBackground (Color.GREEN);
				return theComponent;
			}
			if ((((StatisticsTableModel)this.getModel()).getMarkedCorrectAt(row, StatisticsTableModel.combinedCorrectMarking).booleanValue() == Boolean.TRUE) &&
					((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.combinedCountColumn) > 1))
			{
				theComponent.setBackground (Color.YELLOW);
				return theComponent;
			}
			if ((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.combinedCountColumn) > 0)
			{
				theComponent.setBackground (Color.RED);
				return theComponent;
			}
			if ((Integer)((StatisticsTableModel)this.getModel()).getValueAt(row, StatisticsTableModel.combinedCountColumn) == 0)
			{
				theComponent.setBackground (Color.LIGHT_GRAY);
				return theComponent;
			}
		}

		return theComponent;
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
		
		// Get the model.
		
		StatisticsTableModel theModel = (StatisticsTableModel)(this.getModel());
		if (theModel == null)
			new Error ("regenerateStats (): model was null.");
		
		// Call the model routine to regenerate the stats.
		
		theModel.regenerateStats(cardNumber, newLeftCount, newRightCount, newLeftCorrect, newRightCorrect);
		
		}
 }
