/**
 * 
 */
package asianFlash;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * This class contains the area on which the user can draw things.
 * @author David E. Reese
 * @version 4.1
 *
 */

//Copyright 2013-2015 David E. Reese
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
//	20141211	DEReese				Creation (bug 000043).
//	20151127	DEReese				Added GPL information (bug 000047).
//

public class ScratchPad extends JComponent implements MouseListener, MouseMotionListener {

	/**
	 * Version ID.
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Default width of scratchpad.
	 */
	private static final int sizeX = 600;
	
	/**
	 * Default height of scratchpad.
	 */
	private static final int sizeY = 200;
	
	/**
	 * Image containing any drawing done by the user.
	 */
	private Image image;
	
	/**
	 * Graphics containing any drawing done by the user.
	 */
	private Graphics2D theGraphics;
	
	/**
	 * Current mouse X-coordinate.
	 */
	private int	currentX;
	
	/**
	 * Current mouse Y-coordinate.
	 */
	private int currentY;
	
	/**
	 * Old mouse X-coordinate.
	 */
	private int oldX;
	
	/**
	 * Old mouse Y-coordinate.
	 */
	private int oldY;
	
	/**
	 * Previous bounds of scratchpad.
	 */
	private Rectangle oldBounds = null;
	
	/**
	 * Default constructor.
	 */
	public ScratchPad ()
	{
		this.setPreferredSize(new Dimension(sizeX,sizeY));
		setDoubleBuffered(false);
		setVisible(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);

		Rectangle theBounds = this.getBounds();

		if (image == null)
		{
			image = createImage (theBounds.width, theBounds.height);
			theGraphics = (Graphics2D)image.getGraphics();
			theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			oldBounds = theBounds;
			clear();
		}
		else
		{
			oldBounds = theBounds;
		}
		double	minX = oldBounds.getMinX();
		double  minY = oldBounds.getMinY();
		double  sizeX = oldBounds.getWidth();
		double  sizeY = oldBounds.getHeight();
		g.drawImage(image, (int)minX, (int)minY, (int)sizeX, (int)sizeY, null);
	}
	
	/**
	 * This method clears the drawing.
	 */
	public void clear()
	{		
		theGraphics.setPaint(AsianFlash.userParameterData.getScratchPadBackgroundColor());
		theGraphics.fillRect(0, 0, getSize().width, getSize().height);
		theGraphics.setPaint(AsianFlash.userParameterData.getScratchPadPenColor());
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		oldX = e.getX();
		oldY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		currentX = e.getX();
		currentY = e.getY();
		if (theGraphics != null)
		{
			theGraphics.setStroke(new BasicStroke(AsianFlash.userParameterData.getScratchPadPenThickness()));
			theGraphics.drawLine(oldX, oldY, currentX, currentY);
		}
		repaint();
		oldX = currentX;
		oldY = currentY;
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
	}
	
}
