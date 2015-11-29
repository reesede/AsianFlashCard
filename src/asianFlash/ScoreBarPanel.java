/**
 * 
 */
package asianFlash;

import java.awt.*;
import javax.swing.JPanel;

/**
 * This class implements the panel in which card test score bar (red, green, grey) is displayed.
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

// History
//	20120830	DEReese				Creation.
//	20130704	DEReese				Updated javadoc.
//	20151127	DEReese				Added GPL information (bug 000047).

public class ScoreBarPanel extends JPanel {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * ScorePanel that is the parent of this ScoreBarPanel.
	 */
	private ScorePanel theParent;
	
	/**
	 * Color to display in score bar for the correct block.
	 */
	private Color correctColor = Color.green;
	
	/**
	 * Color to display in score bar for the incorrect block.
	 */
	private Color incorrectColor = Color.red;
	
	/**
	 * Color to display in score bar for the unviewed block.
	 */
	private Color unViewedColor = Color.lightGray;
	
	/**
	 * Constructor allowing the parent ScorePanel to be set.
	 * @param parent	ScorePanel in which this ScoreBarPanel will reside.
	 */
	public ScoreBarPanel (ScorePanel parent)
	{
		super ();
		
		// Check if the parent is null. If so, generate an error.
		
		if (parent == null)
			new Error ("ScoreBarPanel (): parent == null");
		
		// Set the parent.
		
		theParent = parent;
	}
	
	/**
	 * This operation paints the score bar. Note that this overrides the JPanel paint () operation.
	 * @param	g	The Graphics structure associated with the score bar panel.
	 */
	public void paint (Graphics g)
	{
		int	totalCount = 0;
		int correctCount = 0;
		int incorrectCount = 0;
		int width = 0;
		int height = 0;
		int correctHeight;
		int incorrectHeight;
		int unViewedHeight;
		
		// If the parent is null, we can not get the data we need, so generate an error.
		
		if (theParent == null)
			new Error ("paint (): theParent == null");
		
		// Determine the size of the score bar.
		
		width = this.getSize().width;
		height = this.getSize().height;
		
		// Get the total card count, correct count, and incorrect count from the parent ScoreBarPanel.
		
		totalCount = theParent.getTotalCount();
		correctCount = theParent.getCorrectCount();
		incorrectCount = theParent.getIncorrectCount();
		
		// If the totalCount still is 0 (i.e., it has not been initialized by the CardTest object, then
		// treat everything as unviewed.
		
		if (totalCount == 0)
		{
			g.setColor(unViewedColor);
			g.fillRect(0, 0, width, height);
		}
		else
		{
			
			// Calculate the height of the correct block, incorrect block, and viewed block, based on the
			// associated counts and the total number of cards. This will allow rectangles that are 
			// proportional to the associated counts to be drawn.
			
			correctHeight = height * correctCount / totalCount;
			incorrectHeight = height * incorrectCount / totalCount;
			unViewedHeight = height - correctHeight - incorrectHeight;
			
			// If there are any correct cards, fill a rectangle to show their ratio to the total.
			
			if (correctHeight > 0)
			{
				g.setColor(correctColor);
				g.fillRect(0, 0, width, correctHeight);
			}

			// If there are any incorrect cards, fill a rectangle to show their ratio to the total.
			
			if (incorrectHeight > 0)
			{
				g.setColor(incorrectColor);
				g.fillRect(0, correctHeight, width, incorrectHeight);
			}

			// If there are any unviewed cards, fill a rectangle to show their ratio to the total.
			
			if (unViewedHeight > 0)
			{
				g.setColor(unViewedColor);
				g.fillRect(0, correctHeight + incorrectHeight, width, unViewedHeight);			
			}
		}
		
	}
}
