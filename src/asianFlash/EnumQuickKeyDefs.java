/**
 * 
 */
package asianFlash;

/**
 * This enumeration defines the different possible quick key actions.
 * @author David E. Reese
 * @version	4.1
 *
 */

//Copyright 2012, 2015 David E. Reese
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

//History:
//	20120704	DEReese				Creation (bug #000037).
//	20151127	DEReese				Added GPL information (bug 000047).

public enum EnumQuickKeyDefs {

	/**
	 * Undefined smart key.
	 */
	QUICK_KEY_UNDEFINED,
	
	/**
	 * Show the left side of the card.
	 */
	QUICK_KEY_SHOW_LEFT,
	
	/**
	 * Show the right side of the card.
	 */
	QUICK_KEY_SHOW_RIGHT,
	
	/**
	 * Show the visible side of the card.
	 */
	QUICK_KEY_SHOW_VISIBLE,
	
	/**
	 * Mark the left side of the card correct.
	 */
	QUICK_KEY_LEFT_CORRECT,
	
	/**
	 * Mark the right side of the card correct.
	 */
	QUICK_KEY_RIGHT_CORRECT,
	
	/**
	 * Go to the next card.
	 */
	QUICK_KEY_NEXT_CARD
}
