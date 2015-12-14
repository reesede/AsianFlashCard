/**
 * This class contains the information about a card that is being edited. This is different from the
 * InternalFlashCard class, which is used for a card in a test.
 * @author David E. Reese
 * @version 5.0
 *
 */
package asianFlash;

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


//History:
//	20151212	DEReese				Creation (bug 000051).
//

public class CardInfo 
{
	/**
	 * Side 1 text for the card.
	 */
	private String cardSide1Text;
	
	/**
	 * Side 2 text for the card.
	 */
	private String cardSide2Text;
	
	/**
	 * Side 3 text for the card.
	 */
	private String cardSide3Text;
	
	/**
	 * Number of the card.
	 */
	private int theCardNumber;
	
	/**
	 * Default constructor.
	 */
	public CardInfo ()
	{
		cardSide1Text = new String ("");
		cardSide2Text = new String ("");
		cardSide3Text = new String ("");
		theCardNumber = 0;
	}
	
	/**
	 * Constructor allowing the setting of information about the card.
	 * @param newCardNumber	Number of the card (must be >= 1).
	 * @param side1String	String for side 1 of the card.
	 * @param side2String	String for side 2 of the card.
	 * @param side3String	String for side 3 of the card.
	 * @throws Error		Thrown if newCardNumber < 1.
	 */
	public CardInfo (int newCardNumber, String side1String, String side2String, String side3String) throws Error
	{
		if (newCardNumber < 1)
			throw new Error ("CardInfo.CardInfo () detected newCardNumber (" + newCardNumber + ") is < 1");
		theCardNumber = newCardNumber;
		cardSide1Text = side1String;
		cardSide2Text = side2String;
		cardSide3Text = side3String;
	}
	
	public void setCardSide1Text (String theString)
	{
		cardSide1Text = theString;
	}
	
	public String getCardSide1Text ()
	{
		return cardSide1Text;
	}
	
	public void setCardSide2Text (String theString)
	{
		cardSide2Text = theString;
	}
	
	public String getCardSide2Text ()
	{
		return cardSide2Text;
	}
	
	public void setCardSide3Text (String theString)
	{
		cardSide3Text = theString;
	}
	
	public String getCardSide3Text ()
	{
		return cardSide3Text;
	}
	
	public void setTheCardNumber (int newCardNumber)
	{
		if (newCardNumber < 1)
			throw new Error ("CardInfo.setTheCardNumber () detected newCardNumber (" + newCardNumber + ") is < 1");
		theCardNumber = newCardNumber;
	}
	
	public int getTheCardNumber ()
	{
		return theCardNumber;
	}
}
