package cardseteditor;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import asianFlash.AsianFlash;

/**
 * This class contains the information about a card that is being edited. This is different from the
 * InternalFlashCard class, which is used for a card in a test.
 * @author David E. Reese
 * @version 5.0
 * @since	5.0
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


//History:
//	20151212	DEReese				Creation (bug 000051).
//

public class CardInfo 
{
	/**
	 * Document for side 1 of the card.
	 */
	private StyledDocument cardSide1Doc;
	
	/**
	 * Document for side 2 of the card.
	 */
	private StyledDocument cardSide2Doc;
	
	/**
	 * Document for side 3 of the card.
	 */
	private StyledDocument cardSide3Doc;
	
	/**
	 * Number of the card.
	 */
	private int theCardNumber;
	
	/**
	 * Default constructor.
	 * @throws Error	Thrown if bad location error was detected.
	 */
	public CardInfo () throws Error
	{
		// Create new default style documents for each side of the card.
		
		cardSide1Doc = new DefaultStyledDocument ();
		cardSide2Doc = new DefaultStyledDocument ();
		cardSide3Doc = new DefaultStyledDocument ();
		
		// Set the style of the card to indicate that text should be centered.
		
		SimpleAttributeSet center = new SimpleAttributeSet ();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		cardSide1Doc.setParagraphAttributes(0, 0, center, false);
		cardSide2Doc.setParagraphAttributes(0, 0, center, false);
		cardSide3Doc.setParagraphAttributes(0, 0, center, false);

		// Set the i18n property.
		
		cardSide1Doc.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(1));
		cardSide2Doc.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(2));
		cardSide3Doc.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(3));

		// Assign the text to the sides of the card.
		
		try 
		{
			cardSide1Doc.insertString(0, "", null);
			cardSide2Doc.insertString(0, "", null);
			cardSide3Doc.insertString(0, "", null);
		} 
		catch (Exception e) 
		{
			throw new Error ("CardInfo.CardInfo () detected bad location.");
		}
		theCardNumber = 0;
	}
	
	/**
	 * Constructor allowing the setting of information about the card.
	 * @param newCardNumber	Number of the card (must be >= 1).
	 * @param side1String	String for side 1 of the card.
	 * @param side2String	String for side 2 of the card.
	 * @param side3String	String for side 3 of the card.
	 * @throws Error		Thrown if newCardNumber < 1 or if bad location error was detected.
	 */
	public CardInfo (int newCardNumber, String side1String, String side2String, String side3String) throws Error
	{
		if (newCardNumber < 1)
			throw new Error ("CardInfo.CardInfo () detected newCardNumber (" + newCardNumber + ") is < 1");
		theCardNumber = newCardNumber;
		
		// Create new default styled documents for each side of the card.
		
		cardSide1Doc = new DefaultStyledDocument ();
		cardSide2Doc = new DefaultStyledDocument ();
		cardSide3Doc = new DefaultStyledDocument ();
		
		// Set the style of the card to indicate that text should be centered.
		
		SimpleAttributeSet center = new SimpleAttributeSet ();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		cardSide1Doc.setParagraphAttributes(0, 0, center, false);
		cardSide2Doc.setParagraphAttributes(0, 0, center, false);
		cardSide3Doc.setParagraphAttributes(0, 0, center, false);
		
		// Set the i18n property.
		
		cardSide1Doc.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(1));
		cardSide2Doc.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(2));
		cardSide3Doc.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(3));
		
		// Assign the text to the sides of the card.
		
		try 
		{
			cardSide1Doc.insertString(0, side1String, null);
			cardSide2Doc.insertString(0, side2String, null);
			cardSide3Doc.insertString(0, side3String, null);
		} 
		catch (Exception e) 
		{
			throw new Error ("CardInfo.CardInfo () detected bad location.");
		}
}
	
	/**
	 * This method sets the text in side 1 of the card. Note that it overwrites any existing text.
	 * @param theString	String containing text to be put in the card side.
	 * @throws Error Thrown if a bad location was detected.
	 */
	public void setCardSide1Text (String theString) throws Error
	{
		String tString = theString;
		if (tString == null)
			tString = "";
		
		try
		{
			cardSide1Doc.remove(0, cardSide1Doc.getLength());
			cardSide1Doc.insertString(0, theString, null);
		}
		catch (Exception e)
		{
			throw new Error ("CardInfo.setCardSide1Text () detected bad location.");
		}
	}
	
	/**
	 * This method returns a string containing the text in card side 1.
	 * @return		String containing text.
	 * @throws Error	Thrown if a bad location was detected.
	 */
	public String getCardSide1Text () throws Error
	{
		String tString = null;
		if (cardSide1Doc == null)
			tString = "";
		else
		{
			try
			{
				tString = cardSide1Doc.getText(0, cardSide1Doc.getLength());
			}
			catch (Exception e)
			{
				throw new Error ("CardInfo.getCardSide1Text () detected bad location.");
			}
		}
		return tString;
	}
	
	/**
	 * This method sets the text in side 2 of the card. Note that it overwrites any existing text.
	 * @param theString	String containing text to be put in the card side.
	 * @throws Error Thrown if a bad location was detected.
	 */
	public void setCardSide2Text (String theString) throws Error
	{
		String tString = theString;
		if (tString == null)
			tString = "";
		
		try
		{
			cardSide2Doc.remove(0, cardSide2Doc.getLength());
			cardSide2Doc.insertString(0, theString, null);
		}
		catch (Exception e)
		{
			throw new Error ("CardInfo.setCardSide2Text () detected bad location.");
		}
	}
	
	/**
	 * This method returns a string containing the text in card side 2.
	 * @return		String containing text.
	 * @throws Error	Thrown if a bad location was detected.
	 */
	public String getCardSide2Text () throws Error
	{
		String tString = null;
		if (cardSide2Doc == null)
			tString = "";
		else
		{
			try
			{
				tString = cardSide2Doc.getText(0, cardSide2Doc.getLength());
			}
			catch (Exception e)
			{
				throw new Error ("CardInfo.getCardSide2Text () detected bad location.");
			}
		}
		return tString;
	}
	
	/**
	 * This method sets the text in side 3 of the card. Note that it overwrites any existing text.
	 * @param theString	String containing text to be put in the card side.
	 * @throws Error Thrown if a bad location was detected.
	 */
	public void setCardSide3Text (String theString) throws Error
	{
		String tString = theString;
		if (tString == null)
			tString = "";
		
		try
		{
			cardSide3Doc.remove(0, cardSide3Doc.getLength());
			cardSide3Doc.insertString(0, theString, null);
		}
		catch (Exception e)
		{
			throw new Error ("CardInfo.setCardSide3Text () detected bad location.");
		}
	}
	
	/**
	 * This method returns a string containing the text in card side 3.
	 * @return		String containing text.
	 * @throws Error	Thrown if a bad location was detected.
	 */
	public String getCardSide3Text () throws Error
	{
		String tString = null;
		if (cardSide3Doc == null)
			tString = "";
		else
		{
			try
			{
				tString = cardSide3Doc.getText(0, cardSide3Doc.getLength());
			}
			catch (Exception e)
			{
				throw new Error ("CardInfo.getCardSide3Text () detected bad location.");
			}
		}
		return tString;
	}
	
	/**
	 * This method sets the document for card side 1.
	 * @param theDoc	Document to be assigned to the text.
	 */
	public void setCardSide1Doc (StyledDocument theDoc)
	{
		cardSide1Doc = theDoc;
	}
	
	/**
	 * This method returns the StyledDocument containing the text for side 1.
	 * @return	Document containing the text.
	 */
	public StyledDocument getCardSide1Doc ()
	{
		return cardSide1Doc;
	}
	
	/**
	 * This method sets the document for card side 2.
	 * @param theDoc	Document to be assigned to the text.
	 */
	public void setCardSide2Doc (StyledDocument theDoc)
	{
		cardSide2Doc = theDoc;
	}
	
	/**
	 * This method returns the StyledDocument containing the text for side 2.
	 * @return	Document containing the text.
	 */
	public StyledDocument getCardSide2Doc ()
	{
		return cardSide2Doc;
	}
	
	/**
	 * This method sets the document for card side 3.
	 * @param theDoc	Document to be assigned to the text.
	 */
	public void setCardSide3Doc (StyledDocument theDoc)
	{
		cardSide3Doc = theDoc;
	}
	
	/**
	 * This method returns the StyledDocument containing the text for side 3.
	 * @return	Document containing the text.
	 */
	public StyledDocument getCardSide3Doc ()
	{
		return cardSide3Doc;
	}

	/**
	 * This method sets the card number for the card.
	 * @param newCardNumber	New value of the card number.
	 * @throws Error	Thrown if newCardNumber < 1.
	 */
	public void setTheCardNumber (int newCardNumber) throws Error
	{
		if (newCardNumber < 1)
			throw new Error ("CardInfo.setTheCardNumber () detected newCardNumber (" + newCardNumber + ") is < 1");
		theCardNumber = newCardNumber;
	}
	
	/**
	 * This method returns the number of the card.
	 * @return	Card number.
	 */
	public int getTheCardNumber ()
	{
		return theCardNumber;
	}
}
