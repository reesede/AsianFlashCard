/**
 * 
 */
package asianFlash;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * This class implements a dialog in which the application's help file is displayed.
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
//	20151228	DEReese				Creation (bug 000051).
//
public class MainHelpDialog extends AbstractHelpDialog 
{

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Main menu bar which set up this dialog.
	 */
	private MainMenuBarPanel	mainMenuBar;

	/**
	 * This class extends the AbstractHelpDialog to display the help file for the main application.
	 * @param theMainMenuBar	Parent main menu bar.
	 */
	public MainHelpDialog(MainMenuBarPanel theMainMenuBar) 
	{
		super("AsianFlashCard Help Text", "/resources/README.txt");
		mainMenuBar = theMainMenuBar;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == super.closeButton)
		{
			setVisible (false);
			dispose ();
			mainMenuBar.enableHelpItem();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		mainMenuBar.enableHelpItem();
	}
}
