/**
 * This package contains the classes for the Asian flash card program.
 */
package asianFlash;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * This class implements a flash card test. It includes all routines associated with getting new cards,
 * setting pegs, and keeping scores. Note that the cards themselves are not stored in this class, but are
 * implemented with the InternalFlashCard class. The various parts of the user interface should not access
 * InternalFlashCard objects directly, but should go through the CardTest class.
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
//	20120727	DEReese					Creation as stub.
//	20120824	DEReese					Added separate fonts for each side of the card.
//										Added getNewCard () routine to generate a random new card.
//										getNextCard () will be static.
//	20120825	DEReese					Moved visibleTitle, leftTitle, rightTitle, visibleFont, leftFont,
//										rightFont, and associated operators to AsianFlash as statics.
//										Moved setup of flash card set to InternalFlashCard. Now, CardTest
//										will handle only functions related to testing, not setting up cards.
//	20120826	DEReese					Added setCardViewed () routine. Modified getNewCard () to call it to
//										marked the card as viewed. Added cardsViewed peg to keep track of
//										statistics.
//	20120829	DEReese					Added code to constructor to initialize the leftScorePanel and
//										rightScorePanel. Modified setCurrentCardLeftCorrect () and
//										setCurrentCardLeftCorrect() to modify these panels when the number
//										of correct sides changes.
//	20120830	DEReese					Changed separate operations to change the ScorePanels to a single
//										operation that sets all three simultaneously.
//	20120901	DEReese					Added stub code to set up SetUpTestFrame window.
//										Added stub code to handle setting up test after the SetUpTestFrame
//										window has been terminated.
//										Added theCompletionMode variable and modified getNewCard () to terminate
//										the test based on its value.
//	20120902	DEReese					Added setTestMode (), getTestMode (), setTestCompletionMode (), getTestCompletionMode ()
//										setVisibleSideNumber (), getVisibleSideNumber (), setLeftSideNumber (),
//										getLeftSideNumber (), setRightSideNumber (), getRightSideNumber ().
//										Added non-dummy test set-up code in constructor. Added non-dummy code
//										in finishTestSetUp (), testOK, setTestOK (), getTestOK ().
//	20120903	DEReese					Modified finishTestSetUp () to set the buttons in the main frame's
//										ControlButtonPanel object.
//	20120905	DEReese					Fixed bugs in getNewCardRandom () and getNewCardRadomSkipPassed () where
//										newCard could get set to null, resulting in a NullPointerException.
//	20120906	DEReese					Fixed additional bug in getNewCardRandomSkipPassed () where newCard 
//										still could get set to null and a NullPointerException thrown.
//	20120909	DEReese					Added recalculateCounts ().
//	20120913	DEReese					Corrected side set-up in finishTestSetUp ().
//										Added code to only check left side and right side card correct flags
//										when completion modes call for left and right side only.
//	20120915	DEReese					Added calls to ScorePanel:setTitle() to set the titles of the score
//										panels.
//	20120916	DEReese					Added constructor that contains the side numbers, test mode, and test
//										completion modes as parameters. This will allow the user to reload
//										a saved test.
//	20120920	DEReese					Corrected finishTestSetUp () so that it did not show 2 cards viewed
//										at the beginning of the test.
//	20130406	DEReese					Added visibleFont parameter to call to setTheText() in
//										finishTestSetUp(). This helped to eliminate the "flash"
//										seen due to mismatched fonts (bug 000001).
//	20130411	DEReese					Deleted references to TestCompletionModeEnum (bug_000002).
//	20130413	DEReese					Print out the scores for test when in Exam Mode (bug 000008).
//	20130416	DEReese					Modified getNewCard() to increment the card statistics counts when
//										a new card if found and either or both sides are not marked as correct
//										already. Modified finishTestSetup() to clear these statistics for a
//										new test. Added code to constructor and finishTestSetup() to
//										enable the statistics menu item. Added getCardCount () (bug 000006).
//	20130425	DEReese					Modified both get new card routines to set the left and right correct
//										buttons to correct and incorrect based on the whether the corresponding
//										side is incorrect or correct (bug 000015).
//	20130426	DEReese					Created restartTest() (bug 000010).
//	20130512	DEReese					Added generateCardNumbers () and called it when the test is set up,
//										either initially or on a restart (bug 000024).
//	20130514	DEReese					Added getLeftSideCorrect () and getRightSideCorrect (), checkTestDone ()
//										(bug 000026).
//	20130627	DEReese					Added previousCard private variable. Initialized previousCard to null
//										when tests are created or initialized. Modified getNewCard () so that
//										the same card will not be chosen in succession, unless it is the last
//										card (bug 000027).
//	20140226	DEReese					In restartTest(), disable the left and right correct buttons (bug 000030).
//	20140503	DEReese					Added numCardsInTest instance variable. Added numCards parameter in call
//										to finishTestSetUp (). Added getNumCardsInTest () and setNumCardsInTest ()
//										(bug 000039).
//	20140512	DEReese					Updated places where cardCount was used to use getNumCardsInTest (). This
//										is limited to places where the whole deck does not need to be counted.
//										Modified finishTestSetUp () to set the flag of cards that are
//										in the test (bug 000039).
//	20140525	DEReese					In finishTestSetup (), initialize all cards to not be in the test when
//										statistics are cleared (bug 000039).
//	20140627	DEReese					Corrected infinite loop when tRand not decremented.
//										Corrected null pointer due to uninitialized tCard pointer when
//										number of cards in the test is less than the number of cards (bug 000039).
//	20140628	DEReese					Change cardCount to numCardsInTest when calling routines to recalculate
//										the statistics.
//										Added code to clear the cardInTest flag for each card when a new test
//										is started (bug 000039).
//	20140629	DEReese					Eliminated setting numCardsInTest to 0 when all cards are used. Instead, set
//										it to the number of cards in the card set. This corrected a bug where the
//										score panels were getting negative values (bug 000039).
//	20140704	DEReese					Added code to enable quick keys when the test is ready for
//										execution (bug 000037).
//	20151127	DEReese					Added GPL information (bug 000047).
//


public class CardTest 
{
	/**
	 * Pointer to the current card being displayed.
	 */
	private InternalFlashCard	currentCard = null;
	
	/**
	 * Pointer to the previous card displayed.
	 */
	private InternalFlashCard	previousCard = null;
	
	/**
	 * Title of the side of the card which is visible (i.e., given to the user).
	 */
	private String				visibleTitle;
	
	/**
	 * Title of the left hidden side of the card.
	 */
	private String				leftTitle;
	
	/**
	 * Title of the right hidden side of the card.
	 */
	private String				rightTitle;
	
	/**
	 * Font to be used on the side of the card which is visible (i.e., given to the user).
	 */
	private Font				visibleFont;
	
	/**
	 * Font to be used on the left hidden side of the card.
	 */
	private Font				leftFont;
	
	/**
	 * Font to be used on the right hidden side of the card.
	 */
	private Font				rightFont;
	
	/**
	 * Indicates which side of the card (from InternalFlashCard) is to be used as the visible side.
	 */
	private int					visibleSideNumber = 0;
	
	/**
	 * Indicates which side of the card (from InternalFlashCard) is to be used as the left hidden side.
	 */
	private int					leftSideNumber = 0;
	
	/**
	 * Indicates which side of the card (from InternalFlashCard) is to be used as the right hidden side.
	 */
	private int					rightSideNumber = 0;
	
	/**
	 * Number of cards in the test.
	 */
	private int					cardCount;
	
	/**
	 * Number of cards whose left side has been marked correct.
	 */
	private int					leftCorrectCount = 0;
	
	/**
	 * Number of cards whose right side has been marked correct.
	 */
	private int					rightCorrectCount = 0;
	
	/**
	 * Number of cards which have been shown.
	 */
	private int					cardViewedCount = 0;
	
	/**
	 * Mode of the test.
	 */
	private TestModeEnum		theTestMode = TestModeEnum.testModeNull;
		
	/**
	 * Indication if test setup is complete.
	 */
	boolean testOK;
	
	/**
	 * Random number used to generate random cards in those test modes which used randomness.
	 */
	private Random				theRand = new Random (Calendar.getInstance().getTimeInMillis());
	
	/**
	 * Dialog being used to set up test information.
	 */
	private TestSetUpDialog theSetUpDialog;
	
	/**
	 * Number of cards to be used in the test.
	 */
	private int numCardsInTest;
	
	/**
	 * Default Constructor.
	 */
	public CardTest ()
	{
		AsianFlash.theTest = this;
		
			
		// If theFlashCardList is null, then this code has been called by mistake. Generate an error.
		
		if (AsianFlash.theFlashCardList == null)
		{
			new Error ("CardTest (): attempt to start a new test when theFlashCardList == null");
		}
		
		// Clear the pegs associated with each card in the card list.
		
		InternalFlashCard temp = AsianFlash.theFlashCardList;
		while (temp != null)
		{
			temp.setCardViewed(false);
			temp.setSide1Correct(false);
			temp.setSide2Correct(false);
			temp.setSide3Correct(false);
			temp = temp.getNextCard();
		}
		
		// Initialize test variables.
		
		testOK = false;
		currentCard = AsianFlash.theFlashCardList;
		visibleTitle = AsianFlash.side1Title;
		leftTitle = AsianFlash.side2Title;
		rightTitle = AsianFlash.side3Title;
		visibleFont = AsianFlash.side1Font;
		leftFont = AsianFlash.side2Font;
		rightFont = AsianFlash.side3Font;
		visibleSideNumber = 1;
		leftSideNumber = 2;
		rightSideNumber = 3;
		theTestMode = TestModeEnum.testModeShowRepeatedly;

		// Set local statistics pegs.

		cardCount = 0;
		leftCorrectCount = 0;
		rightCorrectCount = 0;
		cardViewedCount = 0;
		numCardsInTest = 0;

		// Call the constructor for the TestSetupDialog to get the test information.
			
		theSetUpDialog = new TestSetUpDialog ();
	}

	/**
	 * This constructor is used when the information required to set up the test has been read from some source other than the user (i.e., a file).
	 * This is designed to be used when the user reloads a saved cardset from a file so that he or she may continue with an existing test. As such,
	 * The number of cards viewed and the correct indications come from the cards themselves and will not be initialized to 0.
	 * @param fileVisibleSide	Indication of the side of the card that is to be visible in the test.
	 * @param fileLeftSide		Indication of the side of the card that is the left hidden side.
	 * @param fileRightSide		Indication of the side of the card that is the right hidden side.
	 * @param fileTestMode		Indication of the test mode to be used.
	 */
	public CardTest (int fileVisibleSide, int fileLeftSide, int fileRightSide, TestModeEnum fileTestMode)
	{
		// Set the various sides, the test mode, and the test completion mode from parameters.
		
		visibleSideNumber = fileVisibleSide;
		leftSideNumber = fileLeftSide;
		rightSideNumber = fileRightSide;
		theTestMode = fileTestMode;
		
		// Derive the titles and fonts to be used from the side indicators.
		
		switch (visibleSideNumber)
		{
			case 1:
				visibleTitle = AsianFlash.side1Title;
				visibleFont = AsianFlash.side1Font;
				break;
			case 2:
				visibleTitle = AsianFlash.side2Title;
				visibleFont = AsianFlash.side2Font;
				break;
			case 3:
				visibleTitle = AsianFlash.side3Title;
				visibleFont = AsianFlash.side3Font;
				break;
			default:
				new Error ("CardTest (): invalid fileVisibleSide (" + fileVisibleSide + ").");
		}
		
		switch (leftSideNumber)
		{
			case 1:
				leftTitle = AsianFlash.side1Title;
				leftFont = AsianFlash.side1Font;
				break;
			case 2:
				leftTitle = AsianFlash.side2Title;
				leftFont = AsianFlash.side2Font;
				break;
			case 3:
				leftTitle = AsianFlash.side3Title;
				leftFont = AsianFlash.side3Font;
				break;
			default:
				new Error ("CardTest (): invalid fileLeftSide (" + fileLeftSide + ").");
		}

		switch (rightSideNumber)
		{
			case 1:
				rightTitle = AsianFlash.side1Title;
				rightFont = AsianFlash.side1Font;
				break;
			case 2:
				rightTitle = AsianFlash.side2Title;
				rightFont = AsianFlash.side2Font;
				break;
			case 3:
				rightTitle = AsianFlash.side3Title;
				rightFont = AsianFlash.side3Font;
				break;
			default:
				new Error ("CardTest (): invalid fileLeftSide (" + fileLeftSide + ").");
		}

		// Get the start of the flash card list, determine the number of cards in the list, and randomize.
		
		currentCard = AsianFlash.theFlashCardList;
		cardCount = determineCardCount();

		// Initialize the card viewed stats.
		
		cardViewedCount = 0;
		leftCorrectCount = 0;
		rightCorrectCount = 0;
		
		while (currentCard != null)
		{
			if (currentCard.getCardViewed() == true)
				cardViewedCount++;
			if (this.getCurrentCardLeftCorrect() == true)
				leftCorrectCount++;
			if (this.getCurrentCardRightCorrect() == true)
				rightCorrectCount++;
			currentCard = currentCard.getNextCard();
		}
		
		currentCard = AsianFlash.theFlashCardList;
		
		// Set the previous card to null.
		
		previousCard = null;
		
		// Randomly get a new card.
		
		currentCard = getNewCard();

		// Set up the score panels.
		
		AsianFlash.leftScorePanel.setTitle(leftTitle);
		AsianFlash.leftScorePanel.setScores(getNumCardsInTest(), leftCorrectCount, cardViewedCount - leftCorrectCount);
		AsianFlash.leftScorePanel.repaint();

		AsianFlash.rightScorePanel.setTitle(rightTitle);
		AsianFlash.rightScorePanel.setScores(getNumCardsInTest(), rightCorrectCount, cardViewedCount - rightCorrectCount);
		AsianFlash.rightScorePanel.repaint();
		
		// Set the titles for the left, right, and center buttons based on values from the test.
		
		AsianFlash.theControlButtonPanel.setCenterButtonTitle(AsianFlash.theTest.getVisibleTitle());
		AsianFlash.theControlButtonPanel.setLeftButtonTitle(AsianFlash.theTest.getLeftTitle());
		AsianFlash.theControlButtonPanel.setLeftCorrectButtonTitle(AsianFlash.theTest.getLeftTitle());
		AsianFlash.theControlButtonPanel.setRightButtonTitle(AsianFlash.theTest.getRightTitle());
		AsianFlash.theControlButtonPanel.setRightCorrectButtonTitle(AsianFlash.theTest.getRightTitle());
		
		// Enable the left, right, center, previous, and next buttons.
		
		AsianFlash.theControlButtonPanel.enableShowLeftButton();
		AsianFlash.theControlButtonPanel.enableShowRightButton();
		AsianFlash.theControlButtonPanel.enableShowCenterButton();
		AsianFlash.theControlButtonPanel.enableNextButton();
		
		// Display the first visible text for the test.
		
		setCardViewed(true);
		String tempString = new String (AsianFlash.theTest.getCurrentCardVisibleString());
		AsianFlash.thePanel.setTheText(tempString);
		AsianFlash.theMainMenuPanel.enableShowStatsItem();
		AsianFlash.theMainMenuPanel.enableRestartTestItem();
		
		// Enabled quick keys.
		
		AsianFlash.globalQuickKeysEnabled = true;
	}
	
	/**
	 * This operation finishes test set-up after the SetUpTestFrame window is complete.
	 * @param numCards	Number of cards to be used in the test. If 0, use all cards.
	 */
	public void finishTestSetUp (int numCards)
	{
		// Set up the total number of cards.
		
		numCardsInTest = numCards;
		if (numCards > determineCardCount ())
			numCards = determineCardCount ();
		
		// Get the test titles and fonts.

		switch (visibleSideNumber)
		{
			case 1:
				visibleTitle = new String (AsianFlash.side1Title);
				visibleFont = new Font (AsianFlash.side1Font.getFontName(), AsianFlash.side1Font.getStyle(), AsianFlash.side1Font.getSize());
				leftSideNumber = 2;
				rightSideNumber = 3;
				break;
			case 2:
				visibleTitle = new String (AsianFlash.side2Title);
				visibleFont = new Font (AsianFlash.side2Font.getFontName(), AsianFlash.side2Font.getStyle(), AsianFlash.side2Font.getSize());
				leftSideNumber = 1;
				rightSideNumber = 3;
				break;
			case 3:
				visibleTitle = new String (AsianFlash.side3Title);
				visibleFont = new Font (AsianFlash.side3Font.getFontName(), AsianFlash.side3Font.getStyle(), AsianFlash.side3Font.getSize());
				leftSideNumber = 1;
				rightSideNumber = 2;
				break;
			default:
				new Error ("finishTestSetup (): visibleSideNumber (" + visibleSideNumber + ") out of range");
		}

		// Set up the left side titles and fonts.
		
		switch (leftSideNumber)
		{
			case 1:
				leftTitle = new String (AsianFlash.side1Title);
				leftFont = new Font (AsianFlash.side1Font.getFontName(), AsianFlash.side1Font.getStyle(), AsianFlash.side1Font.getSize());
				break;
			case 2:
				leftTitle = new String (AsianFlash.side2Title);
				leftFont = new Font (AsianFlash.side2Font.getFontName(), AsianFlash.side2Font.getStyle(), AsianFlash.side2Font.getSize());
				break;
			case 3:
				leftTitle = new String (AsianFlash.side3Title);
				leftFont = new Font (AsianFlash.side3Font.getFontName(), AsianFlash.side3Font.getStyle(), AsianFlash.side3Font.getSize());
				break;
			default:
				new Error ("finishTestSetup (): leftSideNumber (" + leftSideNumber + ") out of range");
		}

		switch (rightSideNumber)
		{
			case 1:
				rightTitle = new String (AsianFlash.side1Title);
				rightFont = new Font (AsianFlash.side1Font.getFontName(), AsianFlash.side1Font.getStyle(), AsianFlash.side1Font.getSize());
				break;
			case 2:
				rightTitle = new String (AsianFlash.side2Title);
				rightFont = new Font (AsianFlash.side2Font.getFontName(), AsianFlash.side2Font.getStyle(), AsianFlash.side2Font.getSize());
				break;
			case 3:
				rightTitle = new String (AsianFlash.side3Title);
				rightFont = new Font (AsianFlash.side3Font.getFontName(), AsianFlash.side3Font.getStyle(), AsianFlash.side3Font.getSize());
				break;
			default:
				new Error ("finishTestSetup (): rightSideNumber (" + rightSideNumber + ") out of range");
		}

		// Initialize the statistics for each card and indicate that the card is not in the test.
		
		InternalFlashCard temp = AsianFlash.theFlashCardList;
		while (temp != null)
		{
			temp.clearSide1ViewsBeforeCorrect();
			temp.clearSide2ViewsBeforeCorrect();
			temp.clearSide3ViewsBeforeCorrect();
			temp.setInTest(false);
			temp = temp.getNextCard();
		}
		
		// Give each card in the test a number.
		
		generateCardNumbers ();
		
		// Get the start of the flash card list.
		
		currentCard = AsianFlash.theFlashCardList;
		
		// Initialize the flag which indicates if the cards are in the test.
		
		InternalFlashCard tCard = AsianFlash.theFlashCardList;
		while (tCard != null)
		{
			tCard.setInTest(false);
			tCard = tCard.getNextCard();
		}
		
		// Set the cards in the test. If numCardsInTest is 0, then set all of the cards in the test. Otherwise,
		// randomly determine the cards that will be in the test.
		
		tCard = AsianFlash.theFlashCardList;
		cardCount = determineCardCount();
		if (numCardsInTest == cardCount)
		{
			while (tCard != null)
			{
				tCard.setInTest(true);
				tCard = tCard.getNextCard();
			}
		}
		else
		{
			int	tCardsInTest = numCardsInTest;
			int tCount = determineCardCount ();
			while (tCardsInTest > 0)
			{
				int	tRand = theRand.nextInt (tCount) + 1;
				while (tRand > 0)
				{
					tCard = tCard.getNextCard();
					if (tCard == null)
						tCard = AsianFlash.theFlashCardList;
					tRand--;
				}
				while (tCard.getInTest() == true)
				{
					tCard = tCard.getNextCard();
					if (tCard == null)
						tCard = AsianFlash.theFlashCardList;
				}
				tCard.setInTest(true);
				tCardsInTest--;
			}
		}
		
		// Get the first card to be displayed.
		
		previousCard = null;
		currentCard = getNewCard();

		// Initialize the card viewed stats.
		
		setCardViewed(true);
		AsianFlash.leftScorePanel.setTitle(leftTitle);
		AsianFlash.leftScorePanel.setScores(getNumCardsInTest(), leftCorrectCount, cardViewedCount - leftCorrectCount);
		AsianFlash.leftScorePanel.repaint();

		AsianFlash.rightScorePanel.setTitle(rightTitle);
		AsianFlash.rightScorePanel.setScores(getNumCardsInTest(), rightCorrectCount, cardViewedCount - rightCorrectCount);
		AsianFlash.rightScorePanel.repaint();
		
		// Set the titles for the left, right, and center buttons based on values from the test.
		
		AsianFlash.theControlButtonPanel.setCenterButtonTitle(AsianFlash.theTest.getVisibleTitle());
		AsianFlash.theControlButtonPanel.setLeftButtonTitle(AsianFlash.theTest.getLeftTitle());
		AsianFlash.theControlButtonPanel.setLeftCorrectButtonTitle(AsianFlash.theTest.getLeftTitle());
		AsianFlash.theControlButtonPanel.setRightButtonTitle(AsianFlash.theTest.getRightTitle());
		AsianFlash.theControlButtonPanel.setRightCorrectButtonTitle(AsianFlash.theTest.getRightTitle());
		
		// Enable the left, right, center, previous, and next buttons.
		
		AsianFlash.theControlButtonPanel.enableShowLeftButton();
		AsianFlash.theControlButtonPanel.enableShowRightButton();
		AsianFlash.theControlButtonPanel.enableShowCenterButton();
		AsianFlash.theControlButtonPanel.enableNextButton();
		
		// Display the first visible text for the test.
		
		String tempString = new String (AsianFlash.theTest.getCurrentCardVisibleString());
		AsianFlash.thePanel.setTheText(tempString, visibleFont);
		
		if (theSetUpDialog != null)
			theSetUpDialog = null;
		
		// Enable the statistics and restart test menu items.
		
		AsianFlash.theMainMenuPanel.enableShowStatsItem();
		AsianFlash.theMainMenuPanel.enableRestartTestItem();

		// Enabled quick keys.
		
		AsianFlash.globalQuickKeysEnabled = true;

	}
	
	/**
	 * This operation restarts the test, clearing all statistics, correct sides, etc.
	 */
	public void restartTest ()
	{
		// Initialize the statistics for each card.
		
		InternalFlashCard temp = AsianFlash.theFlashCardList;
		while (temp != null)
		{
			temp.clearStatistics();
			temp = temp.getNextCard();
		}
		
		// Initialize the local viewed and correct statistics.
		
		cardViewedCount = 0;
		leftCorrectCount = 0;
		rightCorrectCount = 0;
		
		// Generate card numbers.
		
		generateCardNumbers ();
		
		// Get the start of the flash card list.
		
		currentCard = AsianFlash.theFlashCardList;
		
		// Get the first card to be displayed.
		
		previousCard = null;
		currentCard = getNewCard();

		// Initialize the card viewed stats.
		
		setCardViewed(true);
		AsianFlash.leftScorePanel.setTitle(leftTitle);
		AsianFlash.leftScorePanel.setScores(getNumCardsInTest(), leftCorrectCount, cardViewedCount - leftCorrectCount);
		AsianFlash.leftScorePanel.repaint();

		AsianFlash.rightScorePanel.setTitle(rightTitle);
		AsianFlash.rightScorePanel.setScores(getNumCardsInTest(), rightCorrectCount, cardViewedCount - rightCorrectCount);
		AsianFlash.rightScorePanel.repaint();
		
		// Set the titles for the left, right, and center buttons based on values from the test.
		
		AsianFlash.theControlButtonPanel.setCenterButtonTitle(AsianFlash.theTest.getVisibleTitle());
		AsianFlash.theControlButtonPanel.setLeftButtonTitle(AsianFlash.theTest.getLeftTitle());
		AsianFlash.theControlButtonPanel.setLeftCorrectButtonTitle(AsianFlash.theTest.getLeftTitle());
		AsianFlash.theControlButtonPanel.setRightButtonTitle(AsianFlash.theTest.getRightTitle());
		AsianFlash.theControlButtonPanel.setRightCorrectButtonTitle(AsianFlash.theTest.getRightTitle());
		
		// Enable the left, right, center, previous, and next buttons.
		
		AsianFlash.theControlButtonPanel.enableShowLeftButton();
		AsianFlash.theControlButtonPanel.enableShowRightButton();
		AsianFlash.theControlButtonPanel.enableShowCenterButton();
		AsianFlash.theControlButtonPanel.enableNextButton();
		
		// Clear the left correct button and right correct button.
		
		AsianFlash.theControlButtonPanel.disableLeftCorrectButton();
		AsianFlash.theControlButtonPanel.disableRightCorrectButton();
		
		// Display the first visible text for the test.
		
		String tempString = new String (AsianFlash.theTest.getCurrentCardVisibleString());
		AsianFlash.thePanel.setTheText(tempString, visibleFont);
		
		// Enable the statistics and restart test menu items.
		
		AsianFlash.theMainMenuPanel.enableShowStatsItem();
		AsianFlash.theMainMenuPanel.enableRestartTestItem();

		// Enabled quick keys.
		
		AsianFlash.globalQuickKeysEnabled = true;
	}
	
	/**
	 * This operation returns the current card in the test.
	 * @return	current card in test.
	 */
	public InternalFlashCard getCurrentCard ()
	{
		return (currentCard);
	}
	
	/**
	 * This operation returns the next card in the list of cards for the test.
	 * @return	next card in test.
	 */
	public InternalFlashCard getNextCard ()
	{
		if (currentCard.getNextCard() == null)
			currentCard = AsianFlash.theFlashCardList;
		else
			currentCard = currentCard.getNextCard();
		return (currentCard);
	}
	
	/**
	 * This operation returns the previous card in the list of cards for the test.
	 * @return	previous card in test.
	 */
	public InternalFlashCard getPreviousCard ()
	{
		InternalFlashCard tempPtr = currentCard;
		
		if (tempPtr.getPreviousCard() == null)
		{
			while (tempPtr.getNextCard() != null)
				tempPtr = tempPtr.getNextCard();
			currentCard = tempPtr;
		}
		else
			currentCard = tempPtr.getPreviousCard();
		
		return (currentCard);
	}
	
	/**
	 * This operation returns a new card to display. This is based on the test mode.
	 * @return	new card to display.
	 */
	public InternalFlashCard getNewCard ()
	{
		InternalFlashCard newCard = null;
		
		// Set the previous card to be the current card.
		
		previousCard = currentCard;
		
		// Based on the test mode, determine if all cards have been viewed or marked correct. If not,
		// get a new card.
		
		switch (theTestMode)
		{
			case testModeShowOnce:
				if (cardViewedCount == getNumCardsInTest())
				{
					// Determine the scores to print in the dialog.
					double leftSideScore = (double)leftCorrectCount / (double)(getNumCardsInTest()) * 100.0;
					double rightSideScore = (double)rightCorrectCount / (double)(getNumCardsInTest()) * 100.0;
					double totalScore = (double)(leftCorrectCount + rightCorrectCount) / (double)(2 * getNumCardsInTest()) * 100.0;
					
					String leftScoreString = new String (String.format(" %3.2f", leftSideScore));
					String rightScoreString = new String (String.format(" %3.2f", rightSideScore));
					String totalScoreString = new String (String.format(" %3.2f", totalScore));

					String outputString = new String ("Exam completed:\n");
					
					outputString = outputString + "     " + AsianFlash.theTest.getLeftTitle() + " : " + leftScoreString + "%\n";
					outputString = outputString + "     " + AsianFlash.theTest.getRightTitle() + " : " + rightScoreString + "%\n";
					outputString = outputString + "     " + "Total :" + totalScoreString + "%";
					
					JOptionPane.showMessageDialog (AsianFlash.mainFrame, outputString);
					return (null);
				}
				newCard = getNewCardNotShown ();
				break;
			case testModeShowRepeatedly:
				if ((leftCorrectCount == getNumCardsInTest()) && (rightCorrectCount == getNumCardsInTest()))
				{
					JOptionPane.showMessageDialog (AsianFlash.mainFrame, "Congratulations - all cards are passed.");
					return (null);
				}
				int totalCorrect = leftCorrectCount;
				if (rightCorrectCount < totalCorrect)
					totalCorrect = rightCorrectCount;
				
				newCard = getNewCardRandom();
				if (totalCorrect < (getNumCardsInTest() - 1))
				{
					while (newCard == previousCard)
					{
						newCard = getNewCardRandom();
					}
				}
			break;
			default:
				break;
		}
		
		// Set the current card for the test and indicate that the card has been viewed.
		
		currentCard = newCard;
		setCardViewed(true);
		
		// Set the card statistics.
		
		switch (leftSideNumber)
		{
			case 1:
				if (!currentCard.getSide1Correct())
					currentCard.incSide1ViewsBeforeCorrect();
				break;
			case 2:
				if (!currentCard.getSide2Correct())
					currentCard.incSide2ViewsBeforeCorrect();
				break;
			case 3:
				if (!currentCard.getSide3Correct())
					currentCard.incSide3ViewsBeforeCorrect();
				break;
			default:
				new Error ("getNewCard (): attempt to increment statistics for invalid left side of card (" + leftSideNumber + ")");
				break;
		}

		switch (rightSideNumber)
		{
			case 1:
				if (!currentCard.getSide1Correct())
					currentCard.incSide1ViewsBeforeCorrect();
				break;
			case 2:
				if (!currentCard.getSide2Correct())
					currentCard.incSide2ViewsBeforeCorrect();
				break;
			case 3:
				if (!currentCard.getSide3Correct())
					currentCard.incSide3ViewsBeforeCorrect();
				break;
			default:
				new Error ("getNewCard (): attempt to increment statistics for invalid right side of card (" + rightSideNumber + ")");
				break;
		}

		// Set the score panel counts.

		AsianFlash.rightScorePanel.setScores(getNumCardsInTest(), rightCorrectCount, cardViewedCount - rightCorrectCount);
		AsianFlash.leftScorePanel.setScores(getNumCardsInTest(), leftCorrectCount, cardViewedCount - leftCorrectCount);
		
		// Return the new card.
		
		return (newCard);
	}
	
	/**
	 * This operation returns a new card when the test mode is random, skipping passed cards. 
	 * This results in a random card being returned. However, only cards which have not been passed
	 * on both sides will be displayed.
	 * @return	new card based on the random algorithm.
	 */
	private InternalFlashCard getNewCardRandom ()
	{
		InternalFlashCard newCard = currentCard;
		int newCount = theRand.nextInt(cardCount) + 1;
		boolean found = false;
		boolean leftCorrect = false;
		boolean rightCorrect = false;
		int i;
		
		if (newCard == null)
			newCard = AsianFlash.theFlashCardList;
		
		// Check to see if the card is passed (both left and right). If not, then we can return it. If it
		// is passed, run through the cards sequentially until the first non-passed card is found and return
		// that.
		
		i = 0;
		while (i < newCount)
		{
			newCard = newCard.getNextCard();
			if (newCard == null)
				newCard = AsianFlash.theFlashCardList;
			
			switch (leftSideNumber)
			{
				case 1:
					leftCorrect = newCard.getSide1Correct();
					break;
				case 2:
					leftCorrect = newCard.getSide2Correct();
					break;
				case 3:
					leftCorrect = newCard.getSide3Correct();
					break;
				default:
					new Error ("getNewCardRandomSkipPassed (): attempt to get invalid left side of card (" + leftSideNumber + ")");
			}
			switch (rightSideNumber)
			{
				case 1:
					rightCorrect = newCard.getSide1Correct();
					break;
				case 2:
					rightCorrect = newCard.getSide2Correct();
					break;
				case 3:
					rightCorrect = newCard.getSide3Correct();
					break;
				default:
					new Error ("getNewCardRandomSkipPassed (): attempt to get invalid right side of card (" + rightSideNumber + ")");
			}

			found = ((leftCorrect == false) || (rightCorrect == false));
			
			if (found && newCard.getInTest())
			{
				i++;
			}
		}
		
		// Find if the left and right sides are correct.
		
		switch (leftSideNumber)
		{
			case 1:
				leftCorrect = newCard.getSide1Correct();
				break;
			case 2:
				leftCorrect = newCard.getSide2Correct();
				break;
			case 3:
				leftCorrect = newCard.getSide3Correct();
				break;
			default:
				new Error ("getNewCardRandomSkipPassed (): attempt to get invalid left side of card (" + leftSideNumber + ")");
		}
		switch (rightSideNumber)
		{
			case 1:
				rightCorrect = newCard.getSide1Correct();
				break;
			case 2:
				rightCorrect = newCard.getSide2Correct();
				break;
			case 3:
				rightCorrect = newCard.getSide3Correct();
				break;
			default:
				new Error ("getNewCardRandomSkipPassed (): attempt to get invalid right side of card (" + rightSideNumber + ")");
		}

		// If the left side is correct already, set the left correct button on the button panel
		// to indicate that the user can mark the side incorrect. Otherwise, set the button to
		// indicate that the user can mark the card correct.
		
		if (leftCorrect == Boolean.TRUE)
		{
			AsianFlash.theControlButtonPanel.setLeftButtonToIncorrect();
			AsianFlash.theControlButtonPanel.setLeftInitCorrect(true);
		}
		else
		{
			AsianFlash.theControlButtonPanel.setLeftButtonToCorrect();
			AsianFlash.theControlButtonPanel.setLeftInitCorrect(false);
		}
		
		// If the right side is correct already, set the right correct button on the button panel
		// to indicate that the user can mark the side incorrect. Otherwise, set the button to
		// indicate that the user can mark the card correct.
		
		if (rightCorrect == Boolean.TRUE)
		{
			AsianFlash.theControlButtonPanel.setRightButtonToIncorrect();
			AsianFlash.theControlButtonPanel.setRightInitCorrect(true);
		}
		else
		{
			AsianFlash.theControlButtonPanel.setRightButtonToCorrect();
			AsianFlash.theControlButtonPanel.setRightInitCorrect(false);
		}
		
		return newCard;
	}
	
	/**
	 * This operation returns a new card that is randomly generated, but has not been viewed
	 * previously.
	 * @return	new card based on the random algorithm that has not been viewed.
	 */
	private InternalFlashCard getNewCardNotShown ()
	{
		InternalFlashCard newCard = currentCard;
		int newCount = theRand.nextInt(cardCount) + 1;
		boolean found = false;
		int i;
		boolean leftCorrect = Boolean.FALSE;
		boolean rightCorrect = Boolean.FALSE;
		
		if (newCard == null)
			newCard = AsianFlash.theFlashCardList;
		
		// Check to see if the card is passed (both left and right). If not, then we can return it. If it
		// is passed, run through the cards sequentially until the first non-passed card is found and return
		// that.
		
		i = 0;
		while (i < newCount)
		{
			newCard = newCard.getNextCard();
			if (newCard == null)
				newCard = AsianFlash.theFlashCardList;
			
			found = !(newCard.getCardViewed());
			
			if (found & newCard.getInTest())
			{
				i++;
			}
		}
		
		// Find if the left and right sides are correct.
		
		switch (leftSideNumber)
		{
			case 1:
				leftCorrect = newCard.getSide1Correct();
				break;
			case 2:
				leftCorrect = newCard.getSide2Correct();
				break;
			case 3:
				leftCorrect = newCard.getSide3Correct();
				break;
			default:
				new Error ("getNewCardRandomSkipPassed (): attempt to get invalid left side of card (" + leftSideNumber + ")");
		}
		switch (rightSideNumber)
		{
			case 1:
				rightCorrect = newCard.getSide1Correct();
				break;
			case 2:
				rightCorrect = newCard.getSide2Correct();
				break;
			case 3:
				rightCorrect = newCard.getSide3Correct();
				break;
			default:
				new Error ("getNewCardRandomSkipPassed (): attempt to get invalid right side of card (" + rightSideNumber + ")");
		}

		// If the left side is correct already, set the left correct button on the button panel
		// to indicate that the user can mark the side incorrect. Otherwise, set the button to
		// indicate that the user can mark the card correct.
		
		if (leftCorrect == Boolean.TRUE)
		{
			AsianFlash.theControlButtonPanel.setLeftButtonToIncorrect();
			AsianFlash.theControlButtonPanel.setLeftInitCorrect(true);
		}
		else
		{
			AsianFlash.theControlButtonPanel.setLeftButtonToCorrect();
			AsianFlash.theControlButtonPanel.setLeftInitCorrect(false);
		}
		
		// If the right side is correct already, set the right correct button on the button panel
		// to indicate that the user can mark the side incorrect. Otherwise, set the button to
		// indicate that the user can mark the card correct.
		
		if (rightCorrect == Boolean.TRUE)
		{
			AsianFlash.theControlButtonPanel.setRightButtonToIncorrect();
			AsianFlash.theControlButtonPanel.setLeftInitCorrect(true);
		}
		else
		{
			AsianFlash.theControlButtonPanel.setRightButtonToCorrect();
			AsianFlash.theControlButtonPanel.setLeftInitCorrect(false);
		}
		
		return newCard;
	}
	
	/**
	 * This operation determines the number of cards in the list of cards.
	 * @return	number of cards.
	 */
	public int determineCardCount ()
	{
		cardCount = 0;
		InternalFlashCard temp = AsianFlash.theFlashCardList;
		
		while (temp != null)
		{
			cardCount++;
			temp = temp.getNextCard();
		}
		return (cardCount);
	}
	
	/**
	 * This method returns the title of the visible (i.e., middle) side of the card.
	 * @return	String containing the title of the visible (middle) side of the card.
	 */
	public String getVisibleTitle ()
	{
		return (new String (visibleTitle));
	}
	
	/**
	 * This method returns the title of the left side of the card.
	 * @return	String containing the title of the left side of the card.
	 */
	public String getLeftTitle ()
	{
		return (new String (leftTitle));
	}

	/**
	 * This method returns the title of the right side of the card.
	 * @return	String containing the title of the right side of the card.
	 */
	public String getRightTitle ()
	{
		return (new String (rightTitle));
	}
	
	/**
	 * This method returns a Font object containing the font information for the visible (middle) side of the card.
	 * @return	Pointer to Font object containing information for the middle side of the card.
	 */
	public Font getVisibleFont ()
	{
		return (new Font (visibleFont.getFontName(), visibleFont.getStyle(), visibleFont.getSize()));
	}

	/**
	 * This method returns a Font object containing the font information for the right side of the card.
	 * @return	Pointer to Font object containing information for the right side of the card.
	 */
	public Font getRightFont ()
	{
		return (new Font (rightFont.getFontName(), rightFont.getStyle(), rightFont.getSize()));
	}

	/**
	 * This method returns a Font object containing the font information for the left side of the card.
	 * @return	Pointer to Font object containing information for the left side of the card.
	 */
	public Font getLeftFont ()
	{
		return (new Font (leftFont.getFontName(), leftFont.getStyle(), leftFont.getSize()));
	}

	/**
	 * This operation returns the string for the visible (testing) side of the card.
	 * @return	String from side of card.
	 */
	public String getCurrentCardVisibleString ()
	{
		String returnString = null;
		
		if (currentCard == null)
			new Error ("getCurrentCardVisibleString (): currentCard == null");
		
		switch (visibleSideNumber)
		{
			case 1:
				returnString = new String (currentCard.getSide1());
				break;
			case 2:
				returnString = new String (currentCard.getSide2());
				break;
			case 3:
				returnString = new String (currentCard.getSide3());
				break;
			default:
				new Error ("getCurrentCardVisibleString (): attempt to get invalid side of card (" + visibleSideNumber + ")");
		}
		return (returnString);
	}
	
	/**
	 * This operation returns the string for the left hidden side of the card.
	 * @return	String from side of card.
	 */
	public String getCurrentCardLeftString ()
	{
		String returnString = null;
		
		switch (leftSideNumber)
		{
			case 1:
				returnString = new String (currentCard.getSide1());
				break;
			case 2:
				returnString = new String (currentCard.getSide2());
				break;
			case 3:
				returnString = new String (currentCard.getSide3());
				break;
			default:
				new Error ("getCurrentCardLeftString (): attempt to get invalid side of card (" + leftSideNumber + ")");
		}
		return (returnString);
	}
	
	/**
	 * This operation returns the string for the right hidden side of the card.
	 * @return	String from side of card.
	 */
	public String getCurrentCardRightString ()
	{
		String returnString = null;
		
		switch (rightSideNumber)
		{
			case 1:
				returnString = new String (currentCard.getSide1());
				break;
			case 2:
				returnString = new String (currentCard.getSide2());
				break;
			case 3:
				returnString = new String (currentCard.getSide3());
				break;
			default:
				new Error ("getCurrentCardRightString (): attempt to get invalid side of card (" + visibleSideNumber + ")");
		}
		return (returnString);
	}
	
	/**
	 * This operation sets the flag indicating if the left hidden side of a card has tested correctly or not.
	 * @param theVal	true indicates the side has tested correctly. false indicates that the side has not.
	 */
	public void setCurrentCardLeftCorrect (boolean theVal)
	{
		boolean	tempLeftFlag = false;
		
		switch (leftSideNumber)
		{
			case 1:
				tempLeftFlag = currentCard.getSide1Correct();
				break;
			case 2:
				tempLeftFlag = currentCard.getSide2Correct();
				break;
			case 3:
				tempLeftFlag = currentCard.getSide3Correct();
				break;
			default:
				new Error ("setCurrentCardLeftCorrect (): attempt to get invalid side of card (" + leftSideNumber + ")");
		}
		
		switch (leftSideNumber)
		{
			case 1:
				currentCard.setSide1Correct(theVal);
				break;
			case 2:
				currentCard.setSide2Correct(theVal);
				break;
			case 3:
				currentCard.setSide3Correct(theVal);
				break;
			default:
				new Error ("setCurrentCardLeftCorrect (): attempt to set invalid side of card (" + leftSideNumber + ")");
		}
		
		if ((tempLeftFlag == false) && (theVal == true))
		{
			leftCorrectCount++;
			AsianFlash.leftScorePanel.setScores(numCardsInTest, leftCorrectCount, cardViewedCount - leftCorrectCount);
			AsianFlash.rightScorePanel.setScores(numCardsInTest, rightCorrectCount, cardViewedCount - rightCorrectCount);
		}
		if ((tempLeftFlag == true) && (theVal == false))
		{
			leftCorrectCount--;
			if (leftCorrectCount < 0)
				leftCorrectCount = 0;
			AsianFlash.leftScorePanel.setScores(numCardsInTest, leftCorrectCount, cardViewedCount - leftCorrectCount);
			AsianFlash.rightScorePanel.setScores(numCardsInTest, rightCorrectCount, cardViewedCount - rightCorrectCount);
		}
	}
	
	
	/**
	 * This operation sets the flag indicating if the right hidden side of a card has tested correctly or not.
	 * @param theVal	true indicates the side has tested correctly. false indicates that the side has not.
	 */
	public void setCurrentCardRightCorrect (boolean theVal)
	{
		boolean	tempRightFlag = false;
		
		switch (rightSideNumber)
		{
			case 1:
				tempRightFlag = currentCard.getSide1Correct();
				break;
			case 2:
				tempRightFlag = currentCard.getSide2Correct();
				break;
			case 3:
				tempRightFlag = currentCard.getSide3Correct();
				break;
			default:
				new Error ("setCurrentCardRightCorrect (): attempt to get invalid side of card (" + rightSideNumber + ")");
		}
		
		switch (rightSideNumber)
		{
			case 1:
				currentCard.setSide1Correct(theVal);
				break;
			case 2:
				currentCard.setSide2Correct(theVal);
				break;
			case 3:
				currentCard.setSide3Correct(theVal);
				break;
			default:
				new Error ("setCurrentCardRightCorrect (): attempt to set invalid side of card (" + rightSideNumber + ")");
		}
		

		if ((tempRightFlag == false) && (theVal == true))
		{
			rightCorrectCount++;
			AsianFlash.rightScorePanel.setScores(numCardsInTest, rightCorrectCount, cardViewedCount - rightCorrectCount);
			AsianFlash.leftScorePanel.setScores(numCardsInTest, leftCorrectCount, cardViewedCount - leftCorrectCount);
		}
		if ((tempRightFlag == true) && (theVal == false))
		{
			rightCorrectCount--;
			if (rightCorrectCount < 0)
				rightCorrectCount = 0;
			AsianFlash.rightScorePanel.setScores(numCardsInTest, rightCorrectCount, cardViewedCount - rightCorrectCount);
			AsianFlash.leftScorePanel.setScores(numCardsInTest, leftCorrectCount, cardViewedCount - leftCorrectCount);
		}		
	}
	
	/**
	 * This operation returns the number of cards whose left side has been marked as correct.
	 * @return	number of correct left side cards.
	 */
	public boolean getCurrentCardLeftCorrect ()
	{
		boolean result = false;
		
		switch (leftSideNumber)
		{
			case 1:
				result = currentCard.getSide1Correct();
				break;
			case 2:
				result = currentCard.getSide2Correct();
				break;
			case 3:
				result = currentCard.getSide3Correct();
				break;
			default:
				new Error ("getCurrentCardLeftCorrect (): attempt to get invalid side of card (" + leftSideNumber + ")");
		}
		return result;
	}

	/**
	 * This operation returns the number of cards whose right side has been marked as correct.
	 * @return	number of correct right side cards.
	 */
	public boolean getCurrentCardRightCorrect ()
	{
		boolean result = false;
		
		switch (rightSideNumber)
		{
			case 1:
				result = currentCard.getSide1Correct();
				break;
			case 2:
				result = currentCard.getSide2Correct();
				break;
			case 3:
				result = currentCard.getSide3Correct();
				break;
			default:
				new Error ("getCurrentCardRightCorrect (): attempt to get invalid side of card (" + rightSideNumber + ")");
		}
		return result;
	}
	
	/**
	 * This operation sets the flag indicating that the current card has been viewed.
	 * @param theVal
	 */
	public void setCardViewed (boolean theVal)
	{
		boolean	tempVal;
		
		// Verify that the current card is not null.
		
		if (currentCard == null)
			new Error ("setCardViewed (): currentCard == null");
		
		// Get the current card-viewed status of the current card.
		
		tempVal = currentCard.getCardViewed();
		
		if ((tempVal == false) && (theVal == true))
		{
			currentCard.setCardViewed(theVal);
			cardViewedCount++;
		}
		if ((tempVal == true) && (theVal == false))
		{
			currentCard.setCardViewed(theVal);
			cardViewedCount--;
			if (cardViewedCount < 0)
				cardViewedCount = 0;
		}	
	}
	
	/**
	 * This operation sets the test mode.
	 * @param newTestMode	new test mode value.
	 */
	public void setTestMode (TestModeEnum newTestMode)
	{
		theTestMode = newTestMode;
	}
	
	/**
	 * This operation returns the current test mode.
	 * @return	the test mode value.
	 */
	public TestModeEnum getTestMode ()
	{
		return (theTestMode);
	}
		
	/**
	 * This operation sets the visible side number.
	 * @param newVisibleSide	visible side number.
	 */
	public void setVisibleSideNumber (int newVisibleSide)
	{
		visibleSideNumber = newVisibleSide;
	}
	
	/**
	 * This operation gets the visible side number.
	 * @return	visible side number.
	 */
	public int getVisibleSideNumber ()
	{
		return (visibleSideNumber);
	}
	
	/**
	 * This operation sets the left side number.
	 * @param newLeftSide	left side number value.
	 */
	public void setLeftSideNumber (int newLeftSide)
	{
		leftSideNumber = newLeftSide;
	}
	
	/**
	 * This operation gets the left side number.
	 * @return	left side number value.
	 */
	public int getLeftSideNumber ()
	{
		return (leftSideNumber);
	}

	/**
	 * This operation sets the right side number.
	 * @param newRightSide	right side number value.
	 */
	public void setRightSideNumber (int newRightSide)
	{
		rightSideNumber = newRightSide;
	}
	
	/**
	 * This operation returns the right side number.
	 * @return	right side number value.
	 */
	public int getRightSideNumber ()
	{
		return (rightSideNumber);
	}
	
	/**
	 * This operation sets the test OK flag, which indicates if set-up has completed.
	 * @param newTestOK	flag indicating whether or not the test set-up is complete.
	 */
	public void setTestOK (boolean newTestOK)
	{
		testOK = newTestOK;
	}
	
	/**
	 * This operation returns the test OK flag, which indicates if set-up has completed.
	 * @return	flag indicating whether or not the test set-up is complete.
	 */
	public boolean getTestOK ()
	{
		return (testOK);
	}
	
	/**
	 * This operation recalculates all counts (card count, viewed count, left correct count, right correct count) and updates the score panels.
	 */
	public void recalculateCounts ()
	{
		InternalFlashCard	tempCard = AsianFlash.theFlashCardList;
		boolean				tempCorrect = false;
		
		// Set local statistics pegs.

		cardCount = 0;
		leftCorrectCount = 0;
		rightCorrectCount = 0;
		cardViewedCount = 0;		
		
		while (tempCard != null)
		{
			// Increment the card count.
			
			cardCount++;
			
			// If the card has been viewed, increment the card viewed count.
			
			if (tempCard.getCardViewed() == true)
				cardViewedCount++;
			
			// If the left side has been viewed, update the count.
			
			switch (leftSideNumber)
			{
				case 1:
					tempCorrect = tempCard.getSide1Correct();
					break;
				case 2:
					tempCorrect = tempCard.getSide2Correct();
					break;
				case 3:
					tempCorrect = tempCard.getSide3Correct();
					break;
				default:
					new Error ("recalculateCounts (): invalid leftSideNumber (" + leftSideNumber + ").");
			}
			if (tempCorrect == true)
				leftCorrectCount++;
			
			// If the right side has been viewed, update the count.
			
			switch (rightSideNumber)
			{
				case 1:
					tempCorrect = tempCard.getSide1Correct();
					break;
				case 2:
					tempCorrect = tempCard.getSide2Correct();
					break;
				case 3:
					tempCorrect = tempCard.getSide3Correct();
					break;
				default:
					new Error ("recalculateCounts (): invalid rightSideNumber (" + leftSideNumber + ").");
			}
			if (tempCorrect == true)
				rightCorrectCount++;
			
			tempCard = tempCard.getNextCard();
		}
		
		// Give each card a number.
		
		generateCardNumbers ();

		// Update the score panels.
		
		AsianFlash.rightScorePanel.setScores(getNumCardsInTest(), rightCorrectCount, cardViewedCount - rightCorrectCount);
		AsianFlash.leftScorePanel.setScores(getNumCardsInTest(), leftCorrectCount, cardViewedCount - leftCorrectCount);
	}
	
	/**
	 * This operation returns the number of cards in the card set.
	 * @return	Number of cards in set.
	 */
	public int getCardCount ()
	{
		return (cardCount);
	}
	
	/**
	 * This method returns the number of cards in the test, which is less than or equal to the number of 
	 * cards in the card set.
	 * @return	Number of cards used in the test.
	 */
	public int getNumCardsInTest ()
	{
		if (numCardsInTest == 0)
			return getCardCount ();
		else
			return numCardsInTest;
	}
	
	/**
	 * This method sets the number of cards in the test.
	 * @param numCards	Number of cards in the test. 0 indicates all cards.
	 */
	public void setNumCardsInTest (int numCards)
	{
		if (numCards < 0)
			new Error ("numCards must be >= 0.");
		numCardsInTest = numCards;
	}
	
	/**
	 * This operation generates card numbers for all cards in the card list.
	 */
	public void generateCardNumbers ()
	{
		int	count = 1;
		InternalFlashCard temp = AsianFlash.theFlashCardList;
		
		while (temp != null)
		{
			temp.setCardNumber(count);
			count++;
			temp = temp.getNextCard();
		}
	}

	/**
	 * This operation returns the number of cards with left sides marked correct.
	 * @return	Number of left sides which are correct.
	 */
	public int getLeftCorrectCount ()
	{
		return leftCorrectCount;
	}
	
	/**
	 * This operation returns the number of cards with right sides marked correct.
	 * @return	Number of right sides which are correct.
	 */
	public int getRightCorrectCount ()
	{
		return rightCorrectCount;
	}
	
	/**
	 * This operation checks if the test is done by comparing the number of cards correct or viewed
	 * (depending on the type of test) with the number of cards. If the test is done, it disables the
	 * next button in the control button panel.
	 * @return	True if the test is done and false if the test is not done.
	 */
	public boolean checkTestDone ()
	{
		boolean isDone = false;
		
		// Check to see if the test is done.
		
		switch (theTestMode)
		{
			case testModeShowOnce:
				if (cardViewedCount == cardCount)
					isDone = true;
				break;
			case testModeShowRepeatedly:
				if ((cardCount == leftCorrectCount) && (cardCount == rightCorrectCount))
					isDone = true;
				break;
			default:
				break;
		}
		
		// If the test is done, disable the next button.
		
		if (isDone == true)
			AsianFlash.theControlButtonPanel.disableNextButton();
		return isDone;
	}
	
}


