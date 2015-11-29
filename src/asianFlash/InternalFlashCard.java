/**
 * 
 */
package asianFlash;

import java.awt.Font;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;


/**
 * This class provides a flash card with three logical sides.
 * @author David E. Reese
 * @version	4.1
 *
 */

//Copyright 2012-2015 David E. Reese
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
//	20120727	DEReese					Creation.
//	20120825	DEReese					Moved functionality for setting up card set from CardTest to here
//										(as static).
//										Added side1Correct, side2Correct, and side3Correct. Added operators
//										to set and get these variables. Modified initializeEmptyCard ()
//										to set them to default values when a new object is constructed.
//	20120826	DEReese					Added cardViewed. Modified initializeEmptyCard () to set it to false,
//										and added routines to get and set it.
//										Added clearStatistics () and clearListStatistics () operations.
//	20120904	DEReese					Added loadNewCardSet (File []) operation to handle files. Eventually,
//										this will replace the loadNewCardSet () operation, which is a dummy
//										routine.
//	20120905	DEReese					Added initGlobalCardSetVariables ().
//										Added code to loadNewCardSet (File []) to get font information from
//										the AsianFlashDOM objects.
//	20120906	DEReese					Added code to loadNewCardSet (File []) to get title information from
//										the AsianFlashDOM objects.
//	20120907	DEReese					Modified addCardToList () to delete setting newCard.next to null. This
//										will allow the operation to append a list of cards to another list of
//										cards.
//										Added code to loadNewCardSet (File []) to get the list of flash cards
//										in a file from the AsianFlashDOM objects.
//										Took out code to exit program when test is complete. This will allow
//										the user to start a new test, load new cards, etc.
//	20120909	DEReese					Removed debug code.
//										Added code to loadNewCardSet () to clear out the cardFileNameSet global
//										variable when loading a new card set and to add each file name to this
//										structure to keep track of which files are being used.
//										Moved most work from loadNewCardSet () to addCardSetFiles () to 
//										consolidate work between loadNewCardSet () and new appendCardSet ().
//										Added appendCardSet () to handle appending cards from one or more
//										files to an existing card set.
//										Added appendCardSet ().
//	20120910	DEReese					Moved default font information to AsianFlash as globals.
//										Added check for null pointer to cardFileNameSet before attempting
//										to clear it.
//	20120915	DEReese					Added code loadNewCardSet () to set the number of total number of cards in the
//										score panels based on the number of cards loaded (the right and wrong
//										counts are initialized to 0).
//	20120916	DEReese					Modified call for AsianFlashDOM:getFlashCardSet() to include
//										flag to indicate if a full test is to be loaded.
//	20130329	DEReese					Added code to allow creation of debug card sets.
//	20130415	DEReese					Added side1ViewsBeforeCorrect, side2ViewsBeforeCorrect, and
//										side3ViewsBeforeCorrect object variables. Modified
//										initializeEmptyFlash() to initialize these variables to 0.
//										Added getSide1ViewsBeforeCorrect(), getSide2ViewsBeforeCorrect(), and
//										getSide3ViewsBeforeCorrect() to read these values.  Added
//										incSide1ViewsBeforeCorrect(), incSide2ViewsBeforeCorrect(), and
//										incSide3ViewsBeforeCorrect() to increment the values.  Added
//										clearSide1ViewsBeforeCorrect(), clearSide2ViewsBeforeCorrect(), and
//										clearSide3ViewsBeforeCorrect() to clear these values. Also modified
//										clearCardStatistics() to clear these values in one shot.
//										(bug 000006).
//	20130415	DEReese					Added javadoc tags for local variables which did not have them.
//	20130412	DEReese					Added cardNumber, setCardNumber (), getCardNumber (), and
//										checkUpdateStatsTable () (bug 000024).
//	20140304	DEReese					Added theFileName to call to getFlashCardSet () (bug 000033).
//	20140308	DEReese					Added cardFileName, setCardFileName (), and getCardFileName ()
//										(bug 000033).
//	20140312	DEReese					Added side1TextOnly, side2TextOnly, side3TextOnly, getSide1TextOnly (),
//										getSide2TextOnly (), getSide3TextOnly (), setSide1TextOnly (),
//										setSide2TextOnly (), setSide3TextOnly () (bug 000033).
//	20140504	DEReese					Added inTest. Modified initializeEmptyFlash () to initialize it.
//										Added getInTest (). Added setInTest () (bug 000039).
//	20151127	DEReese					Added GPL information (bug 000047).
//

public class InternalFlashCard {

	/**
	 * Default string for side 1.
	 */
	private static final String	defaultSide1Title = new String ("Uninitialized 1");

	/**
	 * Default string for side 2.
	 */
	private static final String	defaultSide2Title = new String ("Uninitialized 2");
	
	/**
	 * Default string for side 3.
	 */
	private static final String	defaultSide3Title = new String ("Uninitialized 3");
	

	/**
	 * Maximum number of sides to the card.
	 */
	public static final int maxSides = 3;
	
	/**
	 * Flag to indicate whether or not to use a canned debug card set, created by code rather
	 * than by reading a file.
	 */
	public static final boolean debugSet = false;
	
	/**
	 * Text for side 1 of the card.
	 */
	private String		side1;
	
	/**
	 * Text for side 2 of the card.
	 */
	private String		side2;
	
	/**
	 * Text for side 3 of the card.
	 */
	private String		side3;
	
	/**
	 * Pointer to the next card in card set.
	 */
	private InternalFlashCard	next;
	
	/**
	 * Pointer to the previous card in the card set.
	 */
	private InternalFlashCard	previous;
	
	/**
	 * Flag indicating that side 1 of the card has been marked correct.
	 */
	private boolean		side1Correct;
	
	/**
	 * Flag indicating that side 2 of the card has been marked correct.
	 */
	private boolean		side2Correct;
	
	/**
	 * Flag indicating that side 3 of the card has been marked correct.
	 */
	private boolean		side3Correct;
	
	/**
	 * Flag indicating that the card has been viewed previously.
	 */
	private boolean		cardViewed;
	
	/**
	 * Number of times side 1 was viewed before being marked as correct.
	 */
	private int		side1ViewsBeforeCorrect;
	
	/**
	 * Number of times side 2 was viewed before being marked as correct.
	 */
	private int		side2ViewsBeforeCorrect;
	
	/**
	 * Number of times side 3 was viewed before being marked as correct.
	 */
	private int		side3ViewsBeforeCorrect;
	
	/**
	 * Name of file from which the card came.
	 */
	private String	cardFileName;
	
	/**
	 * Number of a card in a list.
	 */
	private int		cardNumber;
	
	/**
	 * Side 1 stripped of all HTML/XML tags.
	 */
	private String	side1TextOnly;
	
	/**
	 * Side 2 stripped of all HTML/XML tags.
	 */
	private String	side2TextOnly;
	
	/**
	 * Side 3 stripped of all HTML/XML tags.
	 */
	private String	side3TextOnly;
	
	/**
	 * Indicates if the card is used in the current test or not.
	 */
	private boolean inTest;
	
	/**
	 * Default Constructor. This constructor creates a InternalFlashCard object with all strings empty.
	 */
	public InternalFlashCard ()
	{
		// The initializeEmptyFlash () operator performs all the necessary work to initialize the card.
		
		initializeEmptyFlash ();
	}
	
	/**
	 * Constructor which assigns each string in the InternalFlashCard object to a given value.
	 * @param string1	First string.
	 * @param string2	Second string.
	 * @param string3	Third string.
	 */
	public InternalFlashCard (String string1, String string2, String string3)
	{
		// The initializeEmptyFlash () operator performs all necessary work to initialize the card.
		
		initializeEmptyFlash ();
		
		// Set up the three strings.
		
		side1 = new String (string1);
		side2 = new String (string2);
		side3 = new String (string3);
	}
	
	/**
	 * Initializes an empty flash card by setting the strings to null, previous to null, and next to null.
	 */
	private void initializeEmptyFlash ()
	{
		side1 = null;
		side2 = null;
		side3 = null;
		next = null;
		previous = null;
		side1Correct = false;
		side2Correct = false;
		side3Correct = false;
		cardViewed = false;
		side1ViewsBeforeCorrect = 0;
		side2ViewsBeforeCorrect = 0;
		side3ViewsBeforeCorrect = 0;
		cardNumber = 0;
		cardFileName = null;
		side1TextOnly = null;
		side2TextOnly = null;
		side3TextOnly = null;
		inTest = false;
	}
	
	/**
	 * This operator returns the string associated with side 1 of the InternalFlashCard object.
	 * @return	String associated with side of the card.
	 */
	public String getSide1 ()
	{
		return (new String (side1));
	}
	
	/**
	 * This operator returns the string associated with side 2 of the InternalFlashCard object.
	 * @return	String associated with side of the card.
	 */
	public String getSide2 ()
	{
		return (new String (side2));
	}
	
	/**
	 * This operator returns the string associated with side 3 of the InternalFlashCard object.
	 * @return	String associated with side of the card.
	 */
	public String getSide3 ()
	{
		return (new String (side3));
	}
	
	/**
	 * This operator sets the indication for whether side 1 of the card has tested correctly.
	 * @param theVal	true indicates that the side has been tested correctly. false indicates that the side has not yet been tested correctly.
	 */
	public void setSide1Correct (boolean theVal)
	{
		side1Correct = theVal;
		
		checkUpdateStatsTable ();
	}
		
	/**
	 * This operator sets the indication for whether side 2 of the card has tested correctly.
	 * @param theVal	true indicates that the side has been tested correctly. false indicates that the side has not yet been tested correctly.
	 */
	public void setSide2Correct (boolean theVal)
	{
		side2Correct = theVal;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operator sets the indication for whether side 3 of the card has tested correctly.
	 * @param theVal	true indicates that the side has been tested correctly. false indicates that the side has not yet been tested correctly.
	 */
	public void setSide3Correct (boolean theVal)
	{
		side3Correct = theVal;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operator returns an indication as to whether or not side 1 of the card has tested correctly.
	 * @return	true indicates that the side has tested correctly. false indicates that it has not.
	 */
	public boolean getSide1Correct ()
	{
		return (side1Correct);
	}

	/**
	 * This operator returns an indication as to whether or not side 2 of the card has tested correctly.
	 * @return	true indicates that the side has tested correctly. false indicates that it has not.
	 */
	public boolean getSide2Correct ()
	{
		return (side2Correct);
	}

	/**
	 * This operator returns an indication as to whether or not side 3 of the card has tested correctly.
	 * @return	true indicates that the side has tested correctly. false indicates that it has not.
	 */
	public boolean getSide3Correct ()
	{
		return (side3Correct);
	}
	
	/**
	 * This operation sets the indication as to whether the flash card has been viewed or not.
	 * @param theVal	true indicates that the card has been viewed. false indicates that the card has not been viewed.
	 */
	public void setCardViewed (boolean theVal)
	{
		cardViewed = theVal;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operation returns an indication as to whether the flash card has been viewed or not.
	 * @return	true indicates that the card has been viewed. false indicates that the card has not been viewed.
	 */
	public boolean getCardViewed ()
	{
		return (cardViewed);
	}
	
	/**
	 * This operation clears statistical information and pegs stored for a given card. It sets the card so that
	 * it has not been viewed and no side has been marked as correct.
	 */
	public void clearStatistics ()
	{
		side1Correct = false;
		side2Correct = false;
		side3Correct = false;
		cardViewed = false;
		
		side1ViewsBeforeCorrect = 0;
		side2ViewsBeforeCorrect = 0;
		side3ViewsBeforeCorrect = 0;
	}
	
	/**
	 * This operation clears statistical information and pegs stored for all cards in a list of cards. It sets the
	 * cards so that they have not been viewed and have not been marked correct.
	 */
	public void clearListStatistics ()
	{
		this.clearStatistics();
		if (this.next != null)
		{
			this.next.clearListStatistics();
		}
	}

	/**
	 * This operator returns a pointer to the next FlashCard object in a list after the current object.
	 * @return	Pointer to next card.
	 */
	public InternalFlashCard getNextCard ()
	{
		return (next);
	}
	
	/**
	 * This operator returns a pointer to the previous FlashCard object in a list before the current object.
	 * @return	Pointer to previous card.
	 */
	public InternalFlashCard getPreviousCard ()
	{
		return (previous);
	}
	
	/**
	 * This operator adds a new card to a the end of a list of cards.
	 * @param theNewCard	New card to add to the end of the list.
	 * @throws				NullPointerException when the new card is a null pointer.
	 */
	public void addNewCardToList (InternalFlashCard theNewCard) throws NullPointerException
	{
		InternalFlashCard tempPtr = this;
		
		if (theNewCard == null)
			throw new NullPointerException ("Attempt to add null flashcard to end of list.");
		
		while (tempPtr.next != null)
			tempPtr = tempPtr.next;
		
		tempPtr.next = theNewCard;
//		theNewCard.next = null;
		theNewCard.previous = tempPtr;
	}
	
	/**
	 * This operation initializes all of the global variables that keep track of the flash card list, plus its associated fonts and titles.
	 */
	public static void initGlobalCardSetVariables ()
	{
		AsianFlash.theFlashCardList = null;
		AsianFlash.side1Font = null;
		AsianFlash.side2Font = null;
		AsianFlash.side3Font = null;
		AsianFlash.side1Title = null;
		AsianFlash.side2Title = null;
		AsianFlash.side3Title = null;
	}
	
	/**
	 * This method adds the names of card files to the existing cardset.
	 * @param theFileList	List of file names.
	 */
	private static void addCardSetFiles (File [] theFileList)
	{
		InternalFlashCard	tempCardList = null;		// Temporary storage for card list.
		AsianFlashDOM	theReader = null;			// XML reader for AsianFlash.
		String				theFileName = null;			// File of file to parse.
		
		String				tempTitle = null;			// Temporary side title.
		String				tempFontName = null;		// Temporary font name.
		int					tempSize = -1;				// Temporary font size.
		
		// Attempt to parse each XML file in the file list.
		
		for (int i = 0; i < theFileList.length; i++)
		{
			// Determine the name of each file.
			
			theFileName = theFileList[i].toURI().toString();
			
			// Attempt to parse the file. If an exception occurs (usually signifying an XML error), display
			// a dialog with the exception and continue to the next file.
			try
			{
				theReader = new AsianFlashDOM (theFileName);
			}
			catch (Exception exp)
			{
				JOptionPane.showMessageDialog(AsianFlash.mainFrame, "ERROR: could not parse file: \n\n" + exp.toString());
				continue;
			}
			
			// Add the name of the file to the cardFileNameSet global.
			
			AsianFlash.cardFileNameSet.add(theFileList[i].getPath());
			
			// Attempt to get the side 1 font from the XML information. Note that this is done only once;
			// additional font information in files is ignored.
			
			
			if ((AsianFlash.side1Font == null) && (theReader != null))
			{
				tempFontName = theReader.getSide1FontName();
				tempSize = theReader.getSide1Size();
				if ((tempFontName != null) && (tempSize > 0))
					AsianFlash.side1Font = new Font (tempFontName, AsianFlash.defaultSide1Style, tempSize);		
			}
			
			// Attempt to get the side 2 font from the XML information. Note that this is done only once;
			// additional font information in files is ignored.
			
			
			if ((AsianFlash.side2Font == null) && (theReader != null))
			{
				tempFontName = theReader.getSide2FontName();
				tempSize = theReader.getSide2Size();
				if ((tempFontName != null) && (tempSize > 0))
					AsianFlash.side2Font = new Font (tempFontName, AsianFlash.defaultSide1Style, tempSize);				
			}
			
			// Attempt to get the side 3 font from the XML information. Note that this is done only once;
			// additional font information in files is ignored.
			
			if ((AsianFlash.side3Font == null) && (theReader != null))
			{
				tempFontName = theReader.getSide3FontName();
				tempSize = theReader.getSide3Size();
				if ((tempFontName != null) && (tempSize > 0))
					AsianFlash.side3Font = new Font (tempFontName, AsianFlash.defaultSide1Style, tempSize);				
			}
			
			// Attempt to get the Side 1 title from the XML information. Note that this is done only once;
			// additional title information in the files is ignored.
			
			if ((AsianFlash.side1Title == null) && (theReader != null))
			{
				tempTitle = theReader.getSide1Title();
				if (tempTitle != null)
					AsianFlash.side1Title = new String (tempTitle);
			}
			
			// Attempt to get the Side 2 title from the XML information. Note that this is done only once;
			// additional title information in the files is ignored.
			
			if ((AsianFlash.side2Title == null) && (theReader != null))
			{
				tempTitle = theReader.getSide2Title();
				if (tempTitle != null)
					AsianFlash.side2Title = new String (tempTitle);
			}

			// Attempt to get the Side 3 title from the XML information. Note that this is done only once;
			// additional title information in the files is ignored.
			
			if ((AsianFlash.side3Title == null) && (theReader != null))
			{
				tempTitle = theReader.getSide3Title();
				if (tempTitle != null)
					AsianFlash.side3Title = new String (tempTitle);
			}
			
			tempCardList = theReader.getFlashCardSet(false, theFileName);
			if (tempCardList != null)
			{
				if (AsianFlash.theFlashCardList == null)
					AsianFlash.theFlashCardList = tempCardList;
				else
					AsianFlash.theFlashCardList.addNewCardToList(tempCardList);
			}
		}
		
		// If the font and title globals have not been set by any of the files, set them to the default values.
		
		if (AsianFlash.side1Font == null)
			AsianFlash.side1Font = new Font (AsianFlash.defaultSide1FontName, AsianFlash.defaultSide1Style, AsianFlash.defaultSide1Size);
		if (AsianFlash.side2Font == null)
			AsianFlash.side2Font = new Font (AsianFlash.defaultSide2FontName, AsianFlash.defaultSide2Style, AsianFlash.defaultSide2Size);
		if (AsianFlash.side3Font == null)
			AsianFlash.side3Font = new Font (AsianFlash.defaultSide3FontName, AsianFlash.defaultSide3Style, AsianFlash.defaultSide3Size);
		
		if (AsianFlash.side1Title == null)
			AsianFlash.side1Title = new String (defaultSide1Title);
		if (AsianFlash.side2Title == null)
			AsianFlash.side2Title = new String (defaultSide2Title);
		if (AsianFlash.side3Title == null)
			AsianFlash.side3Title = new String (defaultSide3Title);
		
	}
	
	/**
	 * This operation loads cards from files, creating a new card list. Note that it clears out any previous
	 * card list.
	 * @param theFileList	List of files containing the flash cards to be loaded.
	 */
	public static void loadNewCardSet (File [] theFileList)
	{
		// Clear out any existing card list.
		
		AsianFlash.theFlashCardList = null;
		
		// Check if this is a debug set. If not, load a real set. If so, create a debug card set.
		
		if (!debugSet)
		{
			
			// Clear out the cardFileNameSet global variable, thus emptying the set of file names in use.
			
			if (AsianFlash.cardFileNameSet != null)
				AsianFlash.cardFileNameSet.clear();
			else
				AsianFlash.cardFileNameSet = new HashSet<String>();
			
			// Attempt to parse each XML file in the file list.
			
			addCardSetFiles (theFileList);
			
		}
		else
		{
			// Create a debug cardset here.
			
			InternalFlashCard tempCard = new InternalFlashCard ();
			tempCard.side1 = new String ("\u0F49\u0F72\u0F0B\u0F58\u0F0B");
			tempCard.side2 = new String ("nyi ma");
			tempCard.side3 = new String ("moon");
			
			System.out.println(tempCard.side1);
			
			AsianFlash.side1Font = new Font (Font.SANS_SERIF, AsianFlash.defaultSide1Style, AsianFlash.defaultSide1Size);
			AsianFlash.side2Font = new Font (AsianFlash.defaultSide2FontName, AsianFlash.defaultSide2Style, AsianFlash.defaultSide2Size);
			AsianFlash.side3Font = new Font (AsianFlash.defaultSide3FontName, AsianFlash.defaultSide3Style, AsianFlash.defaultSide3Size);

			AsianFlash.theFlashCardList = tempCard;
			
			AsianFlash.side1Title = new String ("Tibetan");
			AsianFlash.side2Title = new String ("Wylie");
			AsianFlash.side3Title = new String ("English");
		}
		
		if (AsianFlash.theFlashCardList != null)
		{
			int					theCount = 0;
			InternalFlashCard	tempCard = AsianFlash.theFlashCardList;
			
			while (tempCard != null)
			{
				theCount++;
				tempCard = tempCard.getNextCard();
			}
			if (AsianFlash.leftScorePanel != null)
				AsianFlash.leftScorePanel.setScores(theCount, 0, 0);
			if (AsianFlash.rightScorePanel != null)
				AsianFlash.rightScorePanel.setScores(theCount, 0, 0);
		}
		
	}
	
	/**
	 * This operation appends a set of cards from files onto an existing card list.
	 * @param theFileList	List of files containing the flash cards to be appended to the existing list.
	 */
	public static void appendCardSet (File [] theFileList)
	{
		List<File>	internalFileList = new LinkedList<File>();
		File [] newFileList;
		
		// Verify that each file name is not in the list already. If it is, query the user to ask them if
		// they are sure they want to add it. If so, add it.
		
		for (int i = 0; i < theFileList.length; i++)
		{
			if (AsianFlash.cardFileNameSet.contains(theFileList[i].getPath()))
			{
				int result = JOptionPane.showConfirmDialog(AsianFlash.mainFrame, "File: " + theFileList[i].getPath() + " is already included in card set. Do you still want to add this file?");
				switch (result)
				{
					case JOptionPane.YES_OPTION:
						internalFileList.add(theFileList[i]);
						break;
					case JOptionPane.NO_OPTION:
						break;
					case JOptionPane.CANCEL_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
				}
			}
			else
			{
				internalFileList.add(theFileList[i]);
			}			
		}
		newFileList = internalFileList.toArray(new File[0]);
		addCardSetFiles (newFileList);
		if (AsianFlash.theTest != null)
			AsianFlash.theTest.recalculateCounts();
	}
	
	/**
	 * This operation returns the number of times side 1 of the card was viewed before being marked
	 * as correct.
	 * @return	Number of times side was viewed before being marked as correct.
	 */
	public int getSide1ViewsBeforeCorrect ()
	{
		return (side1ViewsBeforeCorrect);
	}
	
	/**
	 * This operation returns the number of times side 2 of the card was viewed before being marked
	 * as correct.
	 * @return	Number of times side was viewed before being marked as correct.
	 */
	public int getSide2ViewsBeforeCorrect ()
	{
		return (side2ViewsBeforeCorrect);
	}
	
	/**
	 * This operation returns the number of times side 3 of the card was viewed before being marked
	 * as correct.
	 * @return	Number of times side was viewed before being marked as correct.
	 */
	public int getSide3ViewsBeforeCorrect ()
	{
		return (side3ViewsBeforeCorrect);
	}
	
	/**
	 * This operation increments the number of times side 1 was viewed before being marked as correct.
	 */
	public void incSide1ViewsBeforeCorrect ()
	{
		side1ViewsBeforeCorrect++;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operation increments the number of times side 2 was viewed before being marked as correct.
	 */
	public void incSide2ViewsBeforeCorrect ()
	{
		side2ViewsBeforeCorrect++;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operation increments the number of times side 3 was viewed before being marked as correct.
	 */
	public void incSide3ViewsBeforeCorrect ()
	{
		side3ViewsBeforeCorrect++;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operation sets the number of times side 1 was viewed before being marked correct to 0.
	 */
	public void clearSide1ViewsBeforeCorrect ()
	{
		side1ViewsBeforeCorrect = 0;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operation sets the number of times side 2 was viewed before being marked correct to 0.
	 */
	public void clearSide2ViewsBeforeCorrect ()
	{
		side2ViewsBeforeCorrect = 0;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * This operation sets the number of times side 3 was viewed before being marked correct to 0.
	 */
	public void clearSide3ViewsBeforeCorrect ()
	{
		side3ViewsBeforeCorrect = 0;
		
		checkUpdateStatsTable ();
	}
	
	/**
	 * Sets the number of the card to a given value, which must be > than 0.
	 * @param newCardNumber	Number for the card.
	 */
	public void setCardNumber (int newCardNumber)
	{
		if (cardNumber <= 0)
			new Error ("setCardNumber (): newCardNumber must be > 0");
		cardNumber = newCardNumber;
	}
	
	/**
	 * Returns the number of the card, which will be >= 0. A value of 0 indicates that the card number has not yet been initialized.
	 * @return	Number of the card.
	 */
	public int getCardNumber ()
	{
		return cardNumber;
	}
	
	/**
	 * This method returns the name of the file which contained the card.
	 * @return	String containing the file name or null if the file name is null.
	 */
	public String getCardFileName ()
	{
		if (cardFileName == null)
			return null;
		else
			return new String (cardFileName);
	}
	
	/**
	 * This method sets the name of the file which contains the card.
	 * @param newName	String containing name of the card's file.
	 */
	public void setCardFileName (String newName)
	{
		if (newName == null)
			cardFileName = null;
		else
			cardFileName = new String (newName);
	}
	
	/**
	 * This method gets the text-only version of side 1.
	 * @return	String containing text-only version of side 1 or null if null.
	 */
	public String getSide1TextOnly ()
	{
		if (side1TextOnly == null)
			return null;
		else
			return new String (side1TextOnly);
	}
	
	/**
	 * This method sets the text-only version of side 1.
	 * @param newTextOnly	String to assign to the text-only version of side 1.
	 */
	public void setSide1TextOnly (String newTextOnly)
	{
		if (newTextOnly == null)
			side1TextOnly = null;
		else
			side1TextOnly = new String (newTextOnly);
	}
	
	/**
	 * This method gets the text-only version of side 2.
	 * @return	String containing text-only version of side 2 or null if null.
	 */
	public String getSide2TextOnly ()
	{
		if (side2TextOnly == null)
			return null;
		else
			return new String (side2TextOnly);
	}
	
	/**
	 * This method sets the text-only version of side 2.
	 * @param newTextOnly	String to assign to the text-only version of side 2.
	 */
	public void setSide2TextOnly (String newTextOnly)
	{
		if (newTextOnly == null)
			side2TextOnly = null;
		else
			side2TextOnly = new String (newTextOnly);
	}
	
	/**
	 * This method gets the text-only version of side 3.
	 * @return	String containing text-only version of side 3 or null if null.
	 */
	public String getSide3TextOnly ()
	{
		if (side3TextOnly == null)
			return null;
		else
			return new String (side3TextOnly);
	}
	
	/**
	 * This method sets the text-only version of side 3.
	 * @param newTextOnly	String to assign to the text-only version of side 3.
	 */
	public void setSide3TextOnly (String newTextOnly)
	{
		if (newTextOnly == null)
			side3TextOnly = null;
		else
			side3TextOnly = new String (newTextOnly);
	}
	
	/**
	 * This operation checks to see if the statistics dialog is displayed. If so, it calls the statistics
	 * table's regenerateStats () operation.
	 */
	private void checkUpdateStatsTable ()
	{
		if (AsianFlash.theStatisticsDisplayDialog == null)
			return;
		if (AsianFlash.theStatisticsTable == null)
			return;

		int leftSide = AsianFlash.theTest.getLeftSideNumber();
		int rightSide = AsianFlash.theTest.getRightSideNumber();
		
		int leftCount = 0;
		int rightCount = 0;
		
		boolean leftCorrect = false;
		boolean rightCorrect = false;
	
		// Get the left side information.
		
		switch (leftSide)
		{
			case 1:
				leftCount = side1ViewsBeforeCorrect;
				leftCorrect = side1Correct;
				break;
			case 2:
				leftCount = side2ViewsBeforeCorrect;
				leftCorrect = side2Correct;
				break;
			case 3:
				leftCount = side3ViewsBeforeCorrect;
				leftCorrect = side3Correct;
				break;
			default:
				new Error ("checkUpdateStatsTable (): leftSide (" + leftSide + ") must be between 1 and 3.");
		}

		// Get the right side information.
		
		switch (rightSide)
		{
			case 1:
				rightCount = side1ViewsBeforeCorrect;
				rightCorrect = side1Correct;
				break;
			case 2:
				rightCount = side2ViewsBeforeCorrect;
				rightCorrect = side2Correct;
				break;
			case 3:
				rightCount = side3ViewsBeforeCorrect;
				rightCorrect = side3Correct;
				break;
			default:
				new Error ("checkUpdateStatsTable (): rightSide (" + rightSide + ") must be between 1 and 3.");
		}
		
		// Update the statistics table.
		
		AsianFlash.theStatisticsTable.regenerateStats(cardNumber, leftCount, rightCount, leftCorrect, rightCorrect);
	}
	
	/**
	 * This method returns the value of the flag indicating if the card is in the current test.
	 * @return	true if the card is in the test; false if the card is not in the current test.
	 */
	public boolean getInTest ()
	{
		return inTest;
	}
	
	/**
	 * This method sets the flag indicating if a card is in the current test.
	 * @param newValue	true if the card is in the current test; false if the card is not in the current test.
	 */
	public void setInTest (boolean newValue)
	{
		inTest = newValue;
	}	
}
