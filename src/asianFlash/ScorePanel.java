/**
 * 
 */
package asianFlash;

import java.awt.*;
import javax.swing.*;

/**
 * This class implements a panel in which the card test scores are displayed.
 * @author David E. Reese
 * @version 4.1
 *
 */

//Copyright 2012, 2013, 2015 David E. Reese
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
//	20120829	DEReese				Creation.
//	20120830	DEReese				Changed separate operations to change the ScorePanels to a single
//									operation that sets all three simultaneously.
//									Added theScoreBar and code to set the graphical score.
//	20120907	DEReese				Renamed topLabel to correctCountLabel.
//									Renamed bottomLabel to totalCountLabel.
//									Added incorrectCountLabel and unviewedCountLabel. Added topPanel JPanel to
//									hold correctCountLabel, incorrectCountLabel, and totalCountLabel.
//									Added columnCount to determine the size of the JTextfields.
//	20120915	DEReese				Added titleLabel and code to handle it in constructor.
//									Added setTitle () operation.
//									Changed columnCount from 11 to 15 to handle increased string sizes.
//  20121122	DEReese				Added viewedCount to show the number of cards shown (either
//									correct or incorrect) and the code to support it. This includes
//									the viewedCountLabel text component.
//									Added blue tinting to viewedCountLabel, unviewedCountLabel,
//									and totalCountLabel. Added red tinting to incorrectCountLabel.
//									Added green tinting to correctCountLabel.
//	20121202	DEReese				Changed correct and incorrect percentage counts to be based on
//									the number of the cards shown, not the total number of cards.
//	20130704	DEReese				Updated javadoc.
//	20151127	DEReese				Added GPL information.

public class ScorePanel extends JPanel {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Number of columns in JTextFields.
	 */
	private static final int columnCount = 15;
	
	/**
	 * Color for red.
	 */
	private static final int countRGBRed = 1;
	
	/**
	 * Color for green.
	 */
	private static final int countRGBGreen = 200;
	
	/**
	 * Color for blue.
	 */
	private static final int countRGBBlue = 255;
	
	/**
	 * Total count of cards being tested.
	 */
	private int	totalCount;
	
	/**
	 * Number of cards correct.
	 */
	private int correctCount;
	
	/**
	 * Number of cards incorrect.
	 */
	private int incorrectCount;
	
	/**
	 * Number of cards shown (correct and incorrect).
	 */
	private int viewedCount;
	
	/**
	 * Layout of panel.
	 */
	private BorderLayout theLayout;
	
	/**
	 * Text label indicating the title of the score panel.
	 */
	private JTextField titleLabel;
	
	/**
	 * Text label indicating number of correct answers.
	 */
	private JTextField correctCountLabel;

	/**
	 * Text label indicating the number of incorrect answers.
	 */
	private JTextField incorrectCountLabel;
	
	/**
	 * Text label indicating the number of shown cards (sum of correct and incorrect).
	 */
	private JTextField viewedCountLabel;
	
	/**
	 * Text label indicating the number of unviewed answers.
	 */
	private JTextField unviewedCountLabel;
	
	/**
	 * Bottom text label indicating total number of possible answers.
	 */
	private JTextField totalCountLabel;
	
	/**
	 * Bar containing graphical score count.
	 */
	private ScoreBarPanel theScoreBar;
	
	/**
	 * Sub-panel to put at the top of the score panel. This will contain the correct and incorrect counts.
	 */
	private JPanel topPanel;
	
	/**
	 * Layout for the top panel.
	 */
	private GridLayout topLayout;
	
	/**
	 * Default Constructor.
	 */
	public ScorePanel ()
	{
		// Set up the color to be used for the total count, shown count, and unshown count.
		
		Color countColor = new Color (countRGBRed, countRGBGreen, countRGBBlue);
		Color correctColor = Color.green;
		Color incorrectColor = Color.red;
		
		// Set up the layout for the panel.
		
		theLayout = new BorderLayout ();
		setLayout (theLayout);
		
		// Set up the top sub-panel.
		
		topPanel = new JPanel ();
		topLayout = new GridLayout (5, 1);
		topPanel.setLayout(topLayout);
		topPanel.setVisible(true);
		add (topPanel, BorderLayout.NORTH);
		
		// Set the text label to indicate the title of the score bar.
		
		titleLabel = new JTextField ("? Scores");
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
		titleLabel.setColumns(columnCount);
		titleLabel.setHorizontalAlignment(JTextField.CENTER);
		titleLabel.setEditable(false);
		topPanel.add(titleLabel);
		
		// Set the text label to indicate the number of correct answers.
		
		correctCountLabel = new JTextField ("Correct = 0 (??.??%)");
		correctCountLabel.setColumns(columnCount);
		correctCountLabel.setHorizontalAlignment(JTextField.CENTER);
		correctCountLabel.setEditable(false);
		correctCountLabel.setBackground(correctColor);
		topPanel.add (correctCountLabel);
		
		// Set the text label to indicate the number of incorrect answers.
		
		incorrectCountLabel = new JTextField ("Incorrect = 0 (??.??%)");
		incorrectCountLabel.setColumns(columnCount);
		incorrectCountLabel.setHorizontalAlignment(JTextField.CENTER);
		incorrectCountLabel.setEditable(false);
		incorrectCountLabel.setBackground(incorrectColor);
		topPanel.add (incorrectCountLabel);
		
		// Set the text label to indicate the number of shown cards (correct and incorrect).
		
		viewedCountLabel = new JTextField ("Shown = 0 (??.??%)");
		viewedCountLabel.setColumns(columnCount);
		viewedCountLabel.setHorizontalAlignment(JTextField.CENTER);
		viewedCountLabel.setEditable(false);
		viewedCountLabel.setBackground(countColor);
		topPanel.add(viewedCountLabel);
		
		// Set the text label to indicate the number of not done cards.
		
		unviewedCountLabel = new JTextField ("Not Shown = 0 (??.??%)");
		unviewedCountLabel.setColumns(columnCount);
		unviewedCountLabel.setHorizontalAlignment(JTextField.CENTER);
		unviewedCountLabel.setEditable(false);
		unviewedCountLabel.setBackground(countColor);
		topPanel.add (unviewedCountLabel);
		
		// Set the bottom text display label, which will indicate the number of possible answers.
		
		totalCountLabel = new JTextField ("Total Cards = 0");
		totalCountLabel.setColumns(columnCount);
		totalCountLabel.setHorizontalAlignment(JTextField.CENTER);
		totalCountLabel.setEditable(false);
		totalCountLabel.setBackground(countColor);
		add (totalCountLabel, BorderLayout.SOUTH);
		
		// Set up the score bar panel.
		
		theScoreBar = new ScoreBarPanel (this);
		add (theScoreBar, BorderLayout.CENTER);

		// Set up local variables.
		
		totalCount = 0;
		correctCount = 0;
		incorrectCount = 0;
	}
	
	/**
	 * This operation sets the total, correct, and incorrect counts.
	 * @param newTotalCount	new total of cards.
	 * @param newCorrectCount new number of correct cards.
	 * @param newIncorrectCount new number of incorrect cards.
	 */
	public void setScores (int newTotalCount, int newCorrectCount, int newIncorrectCount)
	{
		int	unviewedCount;
		
		String	correctPercentString;
		String	incorrectPercentString;
		String  viewedPercentString;
		String	unviewedPercentString;
		
		if ((correctCount + incorrectCount) > totalCount)
			new Error ("setScores (): sum of correctCount (" + correctCount + ") + incorrectCount (" + incorrectCount + ") > totalCount (" + totalCount + ")");
		
		// Set the local counts.
		
		totalCount = newTotalCount;
		correctCount = newCorrectCount;
		incorrectCount = newIncorrectCount;
		viewedCount = correctCount + incorrectCount;
		unviewedCount = totalCount - correctCount - incorrectCount;
		
		// Set the percent strings based on the counts.
		
		if (totalCount == 0)
		{
			correctPercentString = new String (" (??.??%)");
			incorrectPercentString = new String (" (??.??%)");
			viewedPercentString = new String (" (??.??%)");
			unviewedPercentString = new String (" (??.??%)");
		}
		else
		{
			String	percentString = new String ("%)");
			
			if ((correctCount + incorrectCount) > 0)
			{
			  correctPercentString = String.format(" (%3.2f", 100.0 * (double)correctCount / (double)(correctCount + incorrectCount));
			  correctPercentString = correctPercentString.concat(percentString);
			  incorrectPercentString = String.format(" (%3.2f", 100.0 * (double)incorrectCount / (double)(correctCount + incorrectCount));
			  incorrectPercentString = incorrectPercentString.concat(percentString);
			}
			else
			{
				correctPercentString = " (0.00%)";
				incorrectPercentString = " (0.00%)";
			}
			viewedPercentString = String.format(" (%3.2f", 100.0 * (double)viewedCount / (double)totalCount);
			viewedPercentString = viewedPercentString.concat(percentString);
			unviewedPercentString = String.format(" (%3.2f", 100.0 * (double)unviewedCount / (double)totalCount);
			unviewedPercentString = unviewedPercentString.concat(percentString);
		}
		
		// Set the text strings based on the counts.
		
		correctCountLabel.setText("Correct = " + new Integer (correctCount).toString() + correctPercentString);
		incorrectCountLabel.setText("Incorrect = " + new Integer (incorrectCount).toString() + incorrectPercentString);
		viewedCountLabel.setText("Shown = " + new Integer (viewedCount).toString() + viewedPercentString);
		unviewedCountLabel.setText ("Not Shown = " + new Integer (unviewedCount).toString() + unviewedPercentString);
		totalCountLabel.setText("Total = " + new Integer (totalCount).toString());
		theScoreBar.repaint();
	}
	
	/**
	 * This operation returns the total number of cards.
	 * @return	total number of cards.
	 */
	public int getTotalCount ()
	{
		return (totalCount);
	}
	
	/**
	 * This operation returns the total number of cards whose side being monitored by this panel that are correct.
	 * @return	number of cards whose side is correct.
	 */
	public int getCorrectCount ()
	{
		return (correctCount);
	}
	
	/**
	 * This operation returns the total number of cards whose side being monitored by this panel that are incorrect.
	 * @return	number of cards whose side is incorrect.
	 */
	public int getIncorrectCount ()
	{
		return (incorrectCount);
	}
	
	/**
	 * This operation sets the title of the score bar.
	 * @param theTitle	Title of the score bar. Note that the word (" Scores") will be appended to this for display.
	 */
	public void setTitle(String theTitle)
	{
		String	temp;
		
		if (theTitle != null)
		{
			temp = new String (theTitle);
			temp = temp.concat(new String (" Scores"));
			titleLabel.setText(temp);
		}
	}
	
}
