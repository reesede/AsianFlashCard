/**
 * 
 */
package cardseteditor;

import java.util.ArrayList;

/**
 * This class contains the information which is to be written to a card set file..
 * @author David E. Reese
 * @version 5.0
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
//	20151223	DEReese				Creation (bug 000051).
//

public class EditableCardSet 
{
	/**
	 * Title for Side 1 of card set.
	 */
	private String	side1Title;
	
	/**
	 * Title for Side 2 of card set.
	 */
	private String	side2Title;

	/**
	 * Title for Side 3 of card set.
	 */
	private String	side3Title;
	
	/**
	 * Font name for Side 1 of card set.
	 */
	private String	side1Font;
	
	/**
	 * Font name for Side 2 of card set.
	 */
	private String	side2Font;
	
	/**
	 * Font name for Side 3 of card set.
	 */
	private String	side3Font;
		
	/**
	 * Font size for Side 1 of card set.
	 */
	private String	side1Size;
	
	/**
	 * Font size for Side 2 of card set.
	 */
	private String	side2Size;
	
	/**
	 * Font size for Side 3 of card set.
	 */
	private String	side3Size;
	
	/**
	 * List of text contained on side 1 cards.
	 */
	private ArrayList<String>	side1Text;
	
	/**
	 * List of text contained on side 2 cards.
	 */
	private ArrayList<String>	side2Text;
	
	/**
	 * List of text contained on side 3 cards.
	 */
	private ArrayList<String>	side3Text;
	
	/**
	 * Default constructor.
	 */
	public EditableCardSet ()
	{
		side1Title = null;
		side2Title = null;
		side3Title = null;
		
		side1Font = null;
		side2Font = null;
		side3Font = null;
		
		side1Size = null;
		side2Size = null;
		side3Size = null;
		
		side1Text = new ArrayList<String> ();
		side2Text = new ArrayList<String> ();
		side3Text = new ArrayList<String> ();
	}

	/**
	 * This method sets the title for a side of the cards in the card set.
	 * @param theSide	Number of the side (1-3).
	 * @param theTitle	Title of the side.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	public void setSideTitle (int theSide, String theTitle) throws Error
	{
		switch (theSide)
		{
			case 1:
				side1Title = theTitle;
				break;
			case 2:
				side2Title = theTitle;
				break;
			case 3:
				side3Title = theTitle;
				break;
			default:
				throw new Error ("EditableCardSet.setSideTitle () detected theSide (" + theSide + ") not in the range 1..3.");
		}
	}
	
	/**
	 * This method returns a string containing the title of a side.
	 * @param theSide	Number of the side (1..3).
	 * @return			Title of the side.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	public String getSideTitle (int theSide) throws Error
	{
		switch (theSide)
		{
			case 1:
				return side1Title;
			case 2:
				return side2Title;
			case 3:
				return side3Title;
			default:
				throw new Error ("EditableCardSet.getSideTitle () detected theSide (" + theSide + ") not in the range 1..3.");
		}
	}
	
	/**
	 * This method sets the font name for a side of the cards in the card set.
	 * @param theSide	Number of the side (1-3).
	 * @param theFont	Font of the side.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	public void setSideFont (int theSide, String theFont) throws Error
	{
		switch (theSide)
		{
			case 1:
				side1Font = theFont;
				break;
			case 2:
				side2Font = theFont;
				break;
			case 3:
				side3Font = theFont;
				break;
			default:
				throw new Error ("EditableCardSet.setSideFont () detected theSide (" + theSide + ") not in the range 1..3.");
		}
	}
	
	/**
	 * This method returns a string containing the font of a side.
	 * @param theSide	Number of the side (1..3).
	 * @return			Font of the side.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	public String getSideFont (int theSide) throws Error
	{
		switch (theSide)
		{
			case 1:
				return side1Font;
			case 2:
				return side2Font;
			case 3:
				return side3Font;
			default:
				throw new Error ("EditableCardSet.getSideFont () detected theSide (" + theSide + ") not in the range 1..3.");
		}
	}
	
	/**
	 * This method sets the font size for a side of the cards in the card set.
	 * @param theSide	Number of the side (1-3).
	 * @param theFont	Font size of the side.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	public void setSideSize (int theSide, String theSize) throws Error
	{
		switch (theSide)
		{
			case 1:
				side1Size = theSize;
				break;
			case 2:
				side2Size = theSize;
				break;
			case 3:
				side3Size = theSize;
				break;
			default:
				throw new Error ("EditableCardSet.setSideSize () detected theSide (" + theSide + ") not in the range 1..3.");
		}
	}
	
	/**
	 * This method returns a string containing the font size of a side.
	 * @param theSide	Number of the side (1..3).
	 * @return			Font of the side.
	 * @throws Error	Thrown if theSide not in the range 1..3.
	 */
	public String getSideSize (int theSide) throws Error
	{
		switch (theSide)
		{
			case 1:
				return side1Size;
			case 2:
				return side2Size;
			case 3:
				return side3Size;
			default:
				throw new Error ("EditableCardSet.getSideSize () detected theSide (" + theSide + ") not in the range 1..3.");
		}
	}
	
	/**
	 * This method appends a new card onto the end of the card set.
	 * @param side1	Text for side 1 of the card.
	 * @param side2	Text for side 2 of the card.
	 * @param side3	Text for side 3 of the card.
	 * @return		Number of cards in the set after data is added.
	 */
	public int appendCard (String side1, String side2, String side3)
	{
		if (side1Text == null)
			side1Text = new ArrayList<String> ();
		if (side2Text == null)
			side2Text = new ArrayList<String> ();
		if (side3Text == null)
			side3Text = new ArrayList<String> ();
		
		side1Text.add(side1);
		side2Text.add(side2);
		side3Text.add(side3);
		
		if ((side1Text.size() != side2Text.size()) || (side1Text.size() != side3Text.size()) || (side2Text.size() != side3Text.size()))
			throw new Error ("EditableCardSet.appendCard () detected ArrayList size mismatches: side1 = " 
					+ side1Text.size() + ", side2 = " + side2Text.size() + ", side3 = " + side3Text.size());

		return side1Text.size();
	}

	/**
	 * This method returns the text for a side of a given card.
	 * @param theCard	Number of the card (>= 1 and <= number of cards in set).
	 * @param theSide	Side of the card (1..3)
	 * @return			Text for the card side.
	 * @throws Error	Thrown if theCard is out of range or theSide is out of range.
	 */
	public String getCardSideText (int theCard, int theSide) throws Error
	{
		// Verify that theCard is in range.
		
		if ((theCard < 1) || (theCard > side1Text.size()))
			throw new Error ("EditableCardSet.getCardSideText () detected theCard (" + theCard + 
					") > number of cards in list (" + side1Text.size() + ")");
		
		switch (theSide)
		{
			case 1:
				return side1Text.get(theCard - 1);
			case 2:
				return side2Text.get(theCard - 1);
			case 3:
				return side3Text.get(theCard - 1);
			default:
				throw new Error ("EditableCardSet.getCardSideText () detected theSide (" + theSide + ") not in range 1..3.");
		}
	}
	
	/**
	 * This method returns the number of cards in the card set.
	 * @return	Number of cards.
	 */
	public int getNumCards ()
	{
		return side1Text.size();
	}	
}
