/**
 * 
 */
package asianFlash;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

/**
 * This class implements a panel in which to display the text for each side of the card.
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
//	20120727	DEReese				Creation as stub.
//	20120823	DEReese				Modified to use JTextPane rather than JTextArea.
//									Renamed theFont to defaultFont.
//									Renamed theLabel to thePane.
//	20120824	DEReese				Added setTheText () with explicit font parameter.
//	20130330	DEReese				Added defaulti18n and code to set i18n to that value.
//									Added instance of setTheText () which sets the i18n property.
//	20130406	DEReese				Moved setting of i18n property in setTheText() to the start of
//									the routine. In setTheText() with a font, set the new font after
//									clearing the text. Both of these operations are designed to
//									minimize the "flash" that was occurring when switching between
//									different text displays (bug 000001).
//	20130407	DEReese				Changes the manner in which the font information is set to use
//									StyledDocument and MutableAttributeSet classes (bug 000001).
//	20130704	DEReese				Updated javadoc.
//	20140311	DEReese				Converted to display of HTML in the JTextPane (bug 000033).
//	20140313	DEReese				Updated javadoc.
//	20151127	DEReese				Added GPL information (bug 000047).
//

public class TextDisplayPanel extends JPanel
{
	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Default font side.
	 */
	public static final float theFontSize = 100;
	
	/**
	 * Default font.
	 */
	private Font	defaultFont;
	
	/**
	 * Default value for i18n parameter.
	 */
	private boolean	defaulti18n = true;
	
	/**
	 * Pane containing text display.
	 */
	private JTextPane	thePane;
	
	/**
	 * Scroll pane containing text display.
	 */
	private JScrollPane theScrollPane;
	
	/**
	 * Layout for panel.
	 */
	private BorderLayout theLayout;
	
	/**
	 * Default constructor. This constructor creates the pane and all sub-elements.
	 */
	public TextDisplayPanel ()
	{
		// Create a new layout and assign it to the panel. Note that, although FlowLayout is the
		// default layout in Java 6, this is being done to insure future compatibility.
		
		theLayout = new BorderLayout ();
		setLayout (theLayout);
		
		// Set up the border surrounding the panel.
		
		this.setBorder(new LineBorder (Color.black));

		// Set up default font information.
		
		defaultFont = getFont();
		defaultFont = defaultFont.deriveFont(TextDisplayPanel.theFontSize);
		setFont (defaultFont);

		// Set up the style for the text pane.
		
		StyledDocument document = new DefaultStyledDocument();
		Style defaultStyle = document.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);

		// Create the text pane and make sure it can not be edited.
		
		thePane = new JTextPane (document);
		thePane.setEditable(false);
		
		thePane.getDocument().putProperty("i18n", defaulti18n);
		
		// Clear the text in the pane.
		
		try
		{
			document.insertString(document.getLength(), "", null);
		}
		catch (BadLocationException theException)
		{
			
		}
		
		// Set up a scroll bar for the text pane.
		
		theScrollPane = new JScrollPane (thePane);
		add(theScrollPane, BorderLayout.CENTER);
		
		// Make the pane visible.
		
		setVisible(true);
	}
		
	/**
	 * This method sets the text in the panel. The text is displayed as HTML in the default font.
	 * @param newText	Text to be displayed.
	 */
	public void setTheText (String newText)
	{
		int theSize = defaultFont.getSize();
		
		thePane.setContentType("text/html");
		thePane.setFont(defaultFont);
		thePane.setText("<FONT size=\"" + theSize + "\">" + "<CENTER>"+newText+"</CENTER></FONT>");
	}

	/**
	 * This method sets the text in the panel. The text is displayed as HTML in the specified font.
	 * @param newText	Text to be displayed.
	 * @param theFont	Font in which to display the text.
	 */
	public void setTheText (String newText, Font theFont)
	{
		int theSize = theFont.getSize();
		thePane.setContentType("text/html");
		thePane.setFont(theFont);
		thePane.setText("<FONT size=\"" + theSize + "\">" + "<CENTER>"+newText+"</CENTER></FONT>");
	}
		
	/**
	 * This method sets the text in the panel. The text is displayed as HTML in the specified font.
	 * @param newText	Text to be displayed.
	 * @param theFont	Font in which to display the text.
	 * @param theI18n	I18N value (currently unused).
	 */
	public void setTheText (String newText, Font theFont, boolean theI18n)
	{
		int theSize = theFont.getSize();
		thePane.setContentType("text/html");
		thePane.setFont(theFont);
		thePane.setText("<FONT size=\"" + theSize + "\">" + "<CENTER>"+newText+"</CENTER></FONT>");
	}
}
