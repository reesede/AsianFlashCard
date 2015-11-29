/**
 * 
 */
package asianFlash;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.*;

/**
 * This class implements a dialog used for setting up a new test.
 * @author David E. Reese
 * @version 4.1
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
//	20120902	DEReese					Creation.
//	20120903	DEReese					Added code to set up and handle buttons to determine the visible side.
//	20120913	DEReese					Added code to change the radio button names for the correction modes when
//										the visible side button changes.
//	20130411	DEReese					Deleted setup for TestCompletionModeEnum. Now, the test is based
//										only on TestModeEnum. Changed code to use buttons for testModeShowRepeatedly
//										and testModeShowOnce (bug_000002).
//	20130413	DEReese					Modified test mode radio button strings (bug 000007).
//	20130426	DEReese					Added javadoc comments.
//	20130704	DEReese					Updated javadoc.
//	20140628	DEReese					Added numCardsPanel and theNumField and code to set them up.
//										Add propertyChangeListener interface and propertyChanged ()
//										function (bug 000039).
//	20151127	DEReese					Added GPL information (bug 000047).
//	20151128	DEReese					Surrounded call to finishTestSetup () with a call to 
//										SwingUtilities.invokeLater () (bug 000049).
//

public class TestSetUpDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Horizontal location of frame.
	 */
	private static final int xLoc = 250;
	
	/**
	 * Vertical location of frame.
	 */
	private static final int yLoc = 250;
	
	/**
	 * Horizontal size of frame.
	 */
	private static final int width = 700;
	
	/**
	 * Vertical size of frame.
	 */
	private static final int height = 400;
	
	/**
	 * Layout of frame.
	 */
	private BorderLayout theLayout;
	
	/**
	 * Center panel.
	 */
	private JPanel centerPanel;
	
	/**
	 * Bottom (south) panel.
	 */
	private JPanel southPanel;
	
	/**
	 * Panel to contain visible side radio buttons.
	 */
	private JPanel visibleSidePanel;
	
	/** 
	 * Panel to contain test mode.
	 */
	private JPanel testModePanel;
	
	/**
	 * OK button.
	 */
	private JButton okButton;
	
	/**
	 * Cancel button.
	 */
	private JButton cancelButton;
	
	/**
	 * Button group for visible side radio buttons.
	 */
	private ButtonGroup visibleSideButtonGroup;
	
	/**
	 * Button group for test mode radio buttons.
	 */
	private ButtonGroup testModeButtonGroup;
	
	/**
	 * Radio button for "show repeatedly" test mode.
	 */
	private JRadioButton testModeShowRepeatedlyButton;
	
	/**
	 * Radio button for "show once" test mode.
	 */
	private JRadioButton testModeShowOnceButton;
	
	/**
	 * Radio button for side 1 being visible.
	 */
	private JRadioButton visibleSide1Button;
	
	/**
	 * Radio button for side 2 being visible.
	 */
	private JRadioButton visibleSide2Button;
	
	/**
	 * Radio button for side 3 being visible.
	 */
	private JRadioButton visibleSide3Button;
	
	/**
	 * Panel holding the input for the number of cards to include in a test.
	 */
	
	private JPanel numCardsPanel;
	
	/**
	 * Field for entering the number of cards to include in the test.
	 */
	private JFormattedTextField theNumField;
	
	/**
	 * Maximum number of cards in card set.
	 */
	private int maxNumberOfCards;
	
	/**
	 * Current indication of the number of cards to use.
	 */
	private int currentCardCount;
	
	/**
	 * Maximum size of the number of digits in the number field.
	 */
	private static final int	maxSizeNumField = 5;

	/**
	 * Temporary visible side.
	 */
	private int tempVisibleSide;
	
	/**
	 * Temporary test mode.
	 */
	private TestModeEnum tempTestMode;
	
	/**
	 * Default constructor.
	 */
	public TestSetUpDialog ()
	{
		super (AsianFlash.mainFrame, "Test Set-up");
		
		// Set window attributes.
		
		this.setSize(width, height);
		this.setLocation(xLoc, yLoc);
		this.setResizable(true);
		
		// Set the layout.
		theLayout = new BorderLayout ();
		this.setLayout(theLayout);
		
		// Set up the center panel and the south panel.
		
		southPanel = new JPanel ();
		centerPanel = new JPanel ();
		centerPanel.setSize(width, height);
		add (centerPanel, BorderLayout.CENTER);
		add (southPanel, BorderLayout.SOUTH);
		
		// Set up the cancel button.
		
		cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(this);
		southPanel.add(cancelButton, BorderLayout.SOUTH);
		
		// Set up the OK button.
		
		okButton = new JButton ("OK");
		okButton.addActionListener(this);
		southPanel.add(okButton, BorderLayout.SOUTH);
		
		// Set up the visible side radio button group.
		
		visibleSidePanel = new JPanel ();
		
		visibleSidePanel.add(new JLabel ("Visible Side:"));
		
		visibleSideButtonGroup = new ButtonGroup ();
		
		visibleSide1Button = new JRadioButton (AsianFlash.side1Title);
		visibleSide1Button.setActionCommand("Side1");
		visibleSide1Button.addActionListener(this);
		visibleSidePanel.add(visibleSide1Button);
		visibleSideButtonGroup.add(visibleSide1Button);
		
		visibleSide2Button = new JRadioButton (AsianFlash.side2Title);
		visibleSide2Button.setActionCommand("Side2");
		visibleSide2Button.addActionListener(this);
		visibleSidePanel.add(visibleSide2Button);
		visibleSideButtonGroup.add(visibleSide2Button);
		
		visibleSide3Button = new JRadioButton (AsianFlash.side3Title);
		visibleSide3Button.setActionCommand("Side3");
		visibleSide3Button.addActionListener(this);
		visibleSidePanel.add(visibleSide3Button);
		visibleSideButtonGroup.add(visibleSide3Button);
		
		switch (AsianFlash.theTest.getVisibleSideNumber())
		{
			case 1:
				visibleSide1Button.setSelected(true);
				tempVisibleSide = 1;
				break;
			case 2:
				visibleSide2Button.setSelected(true);
				tempVisibleSide = 2;
				break;
			case 3:
				visibleSide3Button.setSelected(true);
				tempVisibleSide = 3;
				break;
			default:
				new Error ("TestSetUpDialog (): visible side number (" + AsianFlash.theTest.getVisibleSideNumber() + ") out of range.");
		}
		
		centerPanel.add(visibleSidePanel);
		
		// Set up the test mode radio button group.
		
		testModePanel = new JPanel ();
		
		testModePanel.add(new JLabel ("Test Mode:"));
		
		testModeButtonGroup = new ButtonGroup ();
		
		testModeShowRepeatedlyButton = new JRadioButton ("Practice Mode (Show until all cards are passed)");
		testModeShowRepeatedlyButton.setActionCommand("ShowUntilPassed");
		testModeShowRepeatedlyButton.addActionListener(this);
		testModePanel.add(testModeShowRepeatedlyButton);
		testModeButtonGroup.add(testModeShowRepeatedlyButton);
		
		testModeShowOnceButton = new JRadioButton ("Exam Mode (Show each card once only)");
		testModeShowOnceButton.setActionCommand("Random");
		testModeShowOnceButton.addActionListener(this);
		testModePanel.add(testModeShowOnceButton);
		testModeButtonGroup.add(testModeShowOnceButton);
		
		switch (AsianFlash.theTest.getTestMode())
		{
			case testModeShowRepeatedly:
				testModeShowRepeatedlyButton.setSelected(true);
				tempTestMode = TestModeEnum.testModeShowRepeatedly;
				break;
			case testModeShowOnce:
				testModeShowOnceButton.setSelected(true);
				tempTestMode = TestModeEnum.testModeShowOnce;
				break;
			default:
				new Error ("TestSetUpDialog (): test mode (" + AsianFlash.theTest.getTestMode() + ") out of range.");
		}
		
		centerPanel.add(testModePanel);
		
		// Find the number of cards in the test.
		
		InternalFlashCard	tCard = AsianFlash.theFlashCardList;
		int					tCount = 0;
		while (tCard != null)
		{
			tCount++;
			tCard = tCard.getNextCard();
		}
		// Set up the panel to include the number of cards to include in the test.
		
		numCardsPanel = new JPanel ();
		
		// Set up the label to indicate that the number of cards in the test can be set.
		
		JLabel numCardsLabel = new JLabel ("Number of cards in test (0-" + tCount + "): ");
		
		// Set up the format of the number of cards (it must be an integer).
		
		NumberFormat numCardsFormat= NumberFormat.getIntegerInstance();
		numCardsFormat.setMinimumIntegerDigits(1);
		numCardsFormat.setMaximumIntegerDigits(maxSizeNumField);
		
		// Set up the text area to include the number of cards in the test.
		
		theNumField = new JFormattedTextField (numCardsFormat);
		theNumField.setColumns(maxSizeNumField);
		theNumField.setValue(tCount);
		theNumField.addPropertyChangeListener(this);
		maxNumberOfCards = tCount;
		currentCardCount = tCount;
		
		numCardsPanel.add(numCardsLabel);
		numCardsPanel.add(theNumField);
		
		centerPanel.add(numCardsPanel);
		
		// Make the dialog visible.
		
		setVisible (true);
	}

	public void actionPerformed (ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == cancelButton)
		{
			AsianFlash.theTest.setTestOK(false);
			setVisible (false);
			dispose ();
		}
		
		if (source == okButton)
		{
			switch (tempVisibleSide)
			{
				case 1:
					AsianFlash.theTest.setVisibleSideNumber(1);
					AsianFlash.theTest.setLeftSideNumber(2);
					AsianFlash.theTest.setRightSideNumber(3);
					break;
				case 2:
					AsianFlash.theTest.setVisibleSideNumber(2);
					AsianFlash.theTest.setLeftSideNumber(1);
					AsianFlash.theTest.setRightSideNumber(3);
					break;
				case 3:
					AsianFlash.theTest.setVisibleSideNumber(3);
					AsianFlash.theTest.setLeftSideNumber(1);
					AsianFlash.theTest.setRightSideNumber(2);
					break;
			}
			AsianFlash.theTest.setTestMode(tempTestMode);

			AsianFlash.theTest.setTestOK(true);
			
			SwingUtilities.invokeLater(new Runnable ()
			{
				public void run ()
				{
					AsianFlash.theTest.finishTestSetUp(currentCardCount);
					setVisible (false);
					dispose ();					
				}
			});
		}
		
		if (source == visibleSide1Button)
		{
			tempVisibleSide = 1;
		}
		if (source == visibleSide2Button)
		{
			tempVisibleSide = 2;
		}
		if (source == visibleSide3Button)
		{
			tempVisibleSide = 3;
		}
		
		if (source == testModeShowRepeatedlyButton)
		{
			tempTestMode = TestModeEnum.testModeShowRepeatedly;
		}
		if (source == testModeShowOnceButton)
		{
			tempTestMode = TestModeEnum.testModeShowOnce;
		}
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
		Object source = evt.getSource();
		if (source == theNumField)
		{
			int tValue = ((Number)theNumField.getValue()).intValue();
			if ((tValue >= 1) && (tValue <= maxNumberOfCards))
			{
				currentCardCount = tValue;
			}
			else
			{
				theNumField.setValue(currentCardCount);
			}
		}
	}
}
