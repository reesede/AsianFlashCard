/**
 * 
 */
package asianFlash;

/**
 *	This enumeration is used to indicate whether the correct/incorrect button on the control
 *	button panel is set to indicate "correct" or "incorrect".
 * @author 	David E. Reese
 * @version 4.1
 */

//Copyright 2013, 2015 David E. Reese
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
//	20130425	DEReese					Creation (bug 000015).
//	20130704	DEReese					Updated javadoc.
//	20151127	DEReese					Added GPL information.
//

public enum CorrectIncorrectEnum {

	/**
	 * Correct/Incorrect status is undefined.
	 */
	CorrectIncorrectEnumUndefined,
	
	/**
	 * Correct/Incorrect status set to indicate that the button says correct.
	 */
	CorrectIncorrectEnumCorrect,
	
	/**
	 * Correct/Incorrect status set to indicate that the button says incorrect.
	 */
	CorrectIncorrectEnumIncorrect
}
