/**
 * 
 */
package cardseteditor;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import asianFlash.AbstractHelpDialog;

/**
 * This class extends AbstractHelpDialog to implement a dialog in which the card set editor's help file is displayed.
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

// History:
//	20151228	DEReese				Creation (bug 000051).

public class EditorHelpDialog extends AbstractHelpDialog 
{

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parent dialog which set up the EditorHelpDialog.
	 */
	private CardSetEditorDialog	parentDialog;

	/**
	 * This class extends the AbstractHelpDialog to display the help file for the main application.
	 * @param theParentDialog	Parent dialog setting up the help dialog.
	 */
	public EditorHelpDialog(CardSetEditorDialog theParentDialog) 
	{
		super("Card Set Editor Help Text", "/resources/CARDSETEDITORHELP.txt");
		parentDialog = theParentDialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == super.closeButton)
		{
			setVisible (false);
			dispose ();
			parentDialog.enableHelpItem();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		parentDialog.enableHelpItem();
	}
}
