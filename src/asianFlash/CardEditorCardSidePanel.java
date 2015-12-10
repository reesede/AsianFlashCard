/**
 * 
 */
package asianFlash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class contains the panel to display and edit a side of a card.
 * @author David E. Reese
 * @version 5.0
 *
 */

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
	private static final Rectangle cardSideLabelBounds = new Rectangle (5, 5, 100, 15);
	
	/**
	 * Boundaries of the label for the side name.
	 */
	private static final Rectangle sideTitleLabelBounds = new Rectangle (5, 30, 80, 15);
	
	/**
	 * Boundaries of the text field for the side name.
	 */
	private static final Rectangle sideTitleTextFieldBounds = new Rectangle (85, 30, 150, 15);
	
	/**
	 * Card side number.
	 */
	private int cardSide;
	
	/**
	 * Title of the side.
	 */
	private JTextField sideTitleTextField;
	
	/**
	 * Constructor allowing card side to be defined.
	 * @param theCardSide	Side of the card (must be 1-3).
	 * @throws Error		Thrown if theCardSide is out of bounds.
	 */
	public CardEditorCardSidePanel (int theCardSide)
	{
		super ();
		
		JLabel theLabel;
		
		if ((theCardSide < 1) || (theCardSide > 3))
			throw new Error ("CardEditorCardSidePanel.CardEditorCardSidePanel () detected theCardSide (" + theCardSide + ") is < 1 or > 3.");
		
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
		
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

}
