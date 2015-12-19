/**
 * 
 */
package cardseteditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import asianFlash.AsianFlash;

/**
 * This class contains the panel to display and edit a side of a card.
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
//	20151208	DEReese				Creation (bug 000051).
//

public class CardEditorCardSidePanel extends JPanel implements ActionListener, DocumentListener, PropertyChangeListener 
		{

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Size of the panel.
	 */
	public static final Dimension panelSize = new Dimension (CardSetEditorDialog.mainPanelBounds.width-30, 150);
	
	/**
	 * Boundaries of display of the side number.
	 */
	private static final Rectangle cardSideLabelBounds = new Rectangle (5, 5, 100, 20);
	
	/**
	 * Boundaries of the label for the side name.
	 */
	private static final Rectangle sideTitleLabelBounds = new Rectangle (5, 30, 100, 20);
	
	/**
	 * Boundaries of the text field for the side name.
	 */
	private static final Rectangle sideTitleTextFieldBounds = new Rectangle (105, 30, 150, 20);
	
	/**
	 * Boundaries of the label for the side font.
	 */
	private static final Rectangle sideFontFamilyLabelBounds = new Rectangle (5, 55, 100, 20);
	
	/**
	 * Boundaries of the JComboBox for the side font.
	 */
	private static final Rectangle sideFontFamilyComboBoxBounds = new Rectangle (105, 55, 150, 20);
	
	/**
	 * Boundaries of the label for the side font size.
	 */
	private static final Rectangle sideFontSizeLabelBounds = new Rectangle (5, 80, 100, 20);
	
	/**
	 * Boundaries of the JComboBox for the side font size.
	 */
	private static final Rectangle sideFontSizeComboBoxBounds = new Rectangle (105, 80, 150, 20);
	
	/**
	 * Boundaries for label of editor panel.
	 */
	private static final Rectangle theTextPaneLabelBounds = new Rectangle (270, 5, panelSize.width-275, 15);
	
	/**
	 * Boundaries for editor panel.
	 */
	private static final Rectangle theTextPaneBounds = new Rectangle (270, 25, panelSize.width-275, panelSize.height-30);
	
	/**
	 * Strings indicating font sizes.
	 */
	private static final String [] fontSizeStrings = {"6", "7", "8", "9", "10", "11", "12", "13", 
		"14", "15", "16", "18", "20", "22", "24", "26", "28", "32", "36", "40", "44", "48", "54", "60", 
		"66", "72", "80", "88", "96"};
	
	/**
	 * Title of the side.
	 */
	private JTextField sideTitleTextField;
	
	/**
	 * Combo box for the side font.
	 */
	private JComboBox<String> sideFontFamilyComboBox;
	
	/**
	 * Combo box for the side font size.
	 */
	private JComboBox<String> sideFontSizeComboBox;
	
	/**
	 * Editor pane for editing the card side.
	 */
	private JTextPane theTextPane;
	
	/**
	 * Card side number.
	 */
	private int cardSide;
	
	/**
	 * Flag to indicate that initialization is complete.
	 */
	private boolean initDone = false;
	
	/**
	 * Document being edited by theTextField.
	 */
	private StyledDocument theDocument;
	
	/**
	 * Constructor which creates an empty (default) panel for a card side.
	 * @param theCardSide	Side of the card (must be 1-3).
	 * @throws Error		Thrown if theCardSide is out of bounds.
	 */
	public CardEditorCardSidePanel (int theCardSide)
	{
		super ();
		
		if ((theCardSide < 1) || (theCardSide > 3))
			throw new Error ("CardEditorCardSidePanel.CardEditorCardSidePanel () detected theCardSide (" + theCardSide + ") is < 1 or > 3.");
		
		createEmptyPanel (theCardSide);
		
		// Indicate that initialization is complete.
		
		initDone = true;
	}
	
	/**
	 * This method creates an empty panel for a given card side.
	 * @param theCardSide	Side of the card (must be 1-3).
	 */
	private void createEmptyPanel (int theCardSide)
	{
		JLabel theLabel;
		
		cardSide = theCardSide;
		
		// Set the panel properties.
		
		setSize(panelSize);
		this.setLayout(null);
		
		// Set a border.
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Add the side information.
		
		theLabel = new JLabel ("SIDE #" + new Integer(theCardSide).toString());
		theLabel.setBounds(cardSideLabelBounds);
		add (theLabel);
		
		// Add the side title text field and label.
		
		theLabel = new JLabel ("Side " + new Integer(cardSide).toString() + " Title:");
		theLabel.setBounds(sideTitleLabelBounds);
		add (theLabel);
		
		sideTitleTextField = new JTextField ("enter Side " + new Integer(cardSide).toString() + " Name");
		sideTitleTextField.setBounds(sideTitleTextFieldBounds);
		sideTitleTextField.setEnabled(true);
		sideTitleTextField.setEditable(true);
		sideTitleTextField.setHorizontalAlignment(JTextField.CENTER);
		add(sideTitleTextField);
		
		// Add the side font combo box and label. Note that the CardSetEditorDialog should set up the
		// font families.
		
		theLabel = new JLabel ("Side " + new Integer (cardSide).toString() + " Font:");
		theLabel.setBounds (sideFontFamilyLabelBounds);
		add (theLabel);
		
		sideFontFamilyComboBox = new JComboBox<String> (AsianFlash.theCardSetEditor.getFontFamilyNames());
		sideFontFamilyComboBox.setBounds(sideFontFamilyComboBoxBounds);
		sideFontFamilyComboBox.addActionListener(this);
		switch (cardSide)
		{
			case 1:
				sideFontFamilyComboBox.setSelectedItem(AsianFlash.defaultSide1FontName);
				break;
			case 2:
				sideFontFamilyComboBox.setSelectedItem(AsianFlash.defaultSide2FontName);
				break;
			case 3:
				sideFontFamilyComboBox.setSelectedItem(AsianFlash.defaultSide3FontName);
				break;
		}
		add (sideFontFamilyComboBox);
		
		// Add the side font size combo box and label.
		
		theLabel = new JLabel ("Side " + new Integer (cardSide).toString() + " Font Size:");
		theLabel.setBounds (sideFontSizeLabelBounds);
		add (theLabel);
		
		sideFontSizeComboBox = new JComboBox<String> (fontSizeStrings);
		sideFontSizeComboBox.setBounds(sideFontSizeComboBoxBounds);
		sideFontSizeComboBox.addActionListener(this);
		switch (cardSide)
		{
			case 1:
				sideFontSizeComboBox.setSelectedItem(new Integer (AsianFlash.defaultSide1Size).toString());
				break;
			case 2:
				sideFontSizeComboBox.setSelectedItem(new Integer (AsianFlash.defaultSide2Size).toString());
				break;
			case 3:
				sideFontSizeComboBox.setSelectedItem(new Integer (AsianFlash.defaultSide3Size).toString());
				break;
		}
		add (sideFontSizeComboBox);
		
		// Add the editor pane and label.
		
		theLabel = new JLabel ("Side " + new Integer (cardSide).toString() + " Text");
		theLabel.setBounds(theTextPaneLabelBounds);
		theLabel.setHorizontalAlignment(JLabel.CENTER);
		add (theLabel);
		
		theTextPane = new JTextPane ();
		theTextPane.setBounds (theTextPaneBounds);
		theTextPane.setPreferredSize(new Dimension(theTextPaneBounds.width, theTextPaneBounds.height));
		theTextPane.setCaretPosition(0);
		theTextPane.setEditable(true);
		theTextPane.setEnabled(true);
		theTextPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		theTextPane.setFont(AsianFlash.theCardSetEditor.getFontForSide(cardSide));
		
		// Set up the style for the document.
		
		StyledDocument tDocument = new DefaultStyledDocument();
		Style defaultStyle = tDocument.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(defaultStyle, StyleConstants.ALIGN_CENTER);
		tDocument.addDocumentListener(this);
		tDocument.putProperty("i18n", AsianFlash.theCardSetEditor.getI18NValueForSide(cardSide));
		theTextPane.setDocument(tDocument);
		theDocument = tDocument;

		// Set up the scroll bar containing the document.
		
		JScrollPane theScrollPane = new JScrollPane (theTextPane);
		theScrollPane.setBounds(theTextPaneBounds);
		theScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		theScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(theScrollPane);
	}
	
	/**
	 * This method returns a pointer to the document from the text pane for the side.
	 * @return	Document for the side being edited.
	 */
	public StyledDocument getTheDocument ()
	{
		return (StyledDocument)theTextPane.getDocument();
	}
	
	/**
	 * This method sets the document containing the side text in the side panel and displays it.
	 * @param theDoc	Document containing text to be displayed in the side.
	 * @throws Error	Thrown if theDoc is null.
	 */
	public void setCardSideDoc (StyledDocument theDoc) throws Error
	{
		if (theDoc == null)
			throw new Error ("CardEditorCardSidePanel.setCardSideDoc () detected theDoc == null");
		
		// If there currently is a document, remove this object as a document listener.
		
		if (theDocument != null)
			theDoc.removeDocumentListener(this);
		
		// Add the document to the text pane.
		
		theTextPane.setDocument(theDoc);
		theDoc.addDocumentListener(this);
		theTextPane.repaint();
		theTextPane.setCaretPosition(theDoc.getLength());
		theDocument = theDoc;
	}
	
	/**
	 * This method returns the styled document containing the text for the side.
	 * @return	Document containing the text for the document.
	 */
	public StyledDocument getCardSideDoc ()
	{
		return theTextPane.getStyledDocument();
	}
	
	/**
	 * This method disables all sub-components of the panel.
	 */
	public void disablePanel ()
	{
		sideTitleTextField.setEnabled(false);
		sideFontFamilyComboBox.setEnabled(false);
		sideFontSizeComboBox.setEnabled(false);
		theTextPane.setEnabled(false);
	}
	
	/**
	 * This method enables all sub-components of the panel.
	 */
	public void enablePanel ()
	{
		sideTitleTextField.setEnabled(true);
		sideFontFamilyComboBox.setEnabled(true);
		sideFontSizeComboBox.setEnabled(true);
		theTextPane.setEnabled(true);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (!initDone)
			return;
		
		Object source = e.getSource();
		
		if (source == this.sideFontFamilyComboBox)
		{
			@SuppressWarnings("unchecked")
			JComboBox<String>	tBox = (JComboBox<String>)source;
			String newValue = (String)tBox.getSelectedItem();
			AsianFlash.theCardSetEditor.setFontFamilyForSide(cardSide, newValue);
			theTextPane.setFont(AsianFlash.theCardSetEditor.getFontForSide(cardSide));
			theTextPane.repaint();
		}
		
		if (source == this.sideFontSizeComboBox)
		{
			@SuppressWarnings("unchecked")
			JComboBox<String>	tBox = (JComboBox<String>)source;
			String newValue = (String)tBox.getSelectedItem();
			AsianFlash.theCardSetEditor.setFontSizeForSide(cardSide, new Integer(newValue).intValue());
			theTextPane.setFont(AsianFlash.theCardSetEditor.getFontForSide(cardSide));
			theTextPane.repaint();
		}
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) 
	{
		if (AsianFlash.theCardSetEditor != null)
			AsianFlash.theCardSetEditor.setDirtyFlag();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) 
	{
		if (AsianFlash.theCardSetEditor != null)
			AsianFlash.theCardSetEditor.setDirtyFlag();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) 
	{
		if (AsianFlash.theCardSetEditor != null)
			AsianFlash.theCardSetEditor.setDirtyFlag();
	}

}
