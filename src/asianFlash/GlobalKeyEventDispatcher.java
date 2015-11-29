/**
 * 
 */
package asianFlash;

import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * This class is the Key Event Dispatcher for the program. It will intercept and process key entry globally
 * for the entire program.
 * @author David E. Reese
 * @version	4.1
 *
 */

//Copyright 2014, 2015 David E. Reese
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
//	20140703	DEReese				Creation (bug #000037).
//	20141127	DEReese				Modified functionality so that the program will ignore any character
//									pressed for a given amount of time, instead of trying to debounce the
//									character. Modified dispatchKeyEvent () and actionPerformed (). Deleted
//									restartTimer (). Moved keyWaitDelay to UserParameters class and
//									modified constructor to retrieve the keyWaitDelay from the global
//									instance of that class. (Bug #000042).
//	20151127	DEReese				Added GPL information (bug 000047).

public class GlobalKeyEventDispatcher implements KeyEventDispatcher, ActionListener {
	
	/**
	 * Last key pressed.
	 */
	private char	theTypedChar = (char)0;
	
	/**
	 * Debounce timer.
	 */
	private javax.swing.Timer theTimer;
	
	/**
	 * Flag indicating if the debounce timer is active or not.
	 */
	private boolean timerActive;
	
	/**
	 * Lock for debounce timer.
	 */
	private Object lock = new Object();
	
	/**
	 * Default constructor.
	 */
	public GlobalKeyEventDispatcher()
	{
		// Create a new timer and make sure it's stopped.
		
		theTimer = new javax.swing.Timer(AsianFlash.userParameterData.getKeyWaitDelay (), this);
		theTimer.stop();
		timerActive = false;
	}
	
	/**
	 * This method starts the debounce timer.
	 */
	private void startTimer ()
	{
		synchronized (lock)
		{
			theTimer.start();
			timerActive = true;
		}
	}
	
	/**
	 * This method stops the debounce timer.
	 */
	private void stopTimer ()
	{
		synchronized (lock)
		{
			theTimer.stop ();
			timerActive = false;
		}
	}
	
	/**
	 * This method returns an indication as to whether or not the debounce timer is active.
	 * @return	true if the debounce timer is active; false if the debounce timer is not active.
	 */
	private boolean isTimerActive ()
	{
		boolean temp;
		
		synchronized (lock)
		{
			temp = timerActive;
		}
		return temp;
	}

	/* (non-Javadoc)
	 * @see java.awt.KeyEventDispatcher#dispatchKeyEvent(java.awt.event.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) 
	{
		
		if (e.getID() == KeyEvent.KEY_TYPED)
		{
			if (!isTimerActive())
			{
				theTypedChar = e.getKeyChar();
				if (AsianFlash.globalQuickKeysEnabled)
				{
					startTimer();
					AsianFlash.userParameterData.processQuickKeys(theTypedChar);
				}
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// If the event is from the debounce timer, stop the timer and process the character.
		
		if (e.getSource() == theTimer)
		{
			// Stop the timer.
			
			stopTimer ();
			
		}
	}

}
