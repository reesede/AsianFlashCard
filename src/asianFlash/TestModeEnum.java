/**
 * 
 */
package asianFlash;

/**
 * This enumeration contains the different types of tests that can be run.
 * @author David E. Reese
 * @version	4.1
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
//	20120825	DEReese					Creation.
//	20130411	DEReese					Changed due to simplification of test modes. Now, there
//										are only two: show cards once and show cards repeatedly
//										(bug_000002).
//	20151127	DEReese					Added GPL information (bug 000047).

public enum TestModeEnum {

	/**
	 * This indicates an invalid test mode.
	 */
	testModeNull,
	
	/**
	 * Show each card only once.
	 */
	testModeShowOnce,
	
	/**
	 * Show each card repeatedly.
	 */
	testModeShowRepeatedly
}
