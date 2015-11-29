/**
 * 
 */
package asianFlash;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.*;

/**
 * This class contains operations required to read the AsianFlash DOM (Document-Object-Model) information from an XML file.
 * @author David E. Reese
 * @version 4.1
 *
 */

//Copyright 2012, 2014, 2015 David E. Reese
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
//	20120904	DEReese					Creation.
//	20120905	DEReese					Added stubs for getSide1FontName (), getSide2FontName (), getSide3FontName (),
//										getSide1Size (), getSide2Size (), getSide3Size (), getSide1Title (),
//										getSide2Title (), and getSide3Title ().
//										Moved from xmlFileProcessing package to asianFlash package to delete
//										xmlFileProcessing package.
//	20120906	DEReese					Added getSideFontName () to handle getting font name information.
//										Added getFontSizeString () to handle getting font size information.
//										Added getTitleString () to handle getting the title name information.
//	20120907	DEReese					Added getFlashCardSet ().
//	20120912	DEReese					Added code to handle embedded linefeeds ("\n") in side strings in the
//										source XML files.
//	20120916	DEReese					Added code to read correct and viewed flags for each card from the
//										XML file.
//										Added loadFullTest flag as parameter to getFlashCardSet(). This will
//										enable parsing of elements for a saved test: test elements plus the
//										flags for each card.
//	20140304	DEReese					Added theFileName parameter to getFlashCardSet () (bug 000033).
//	20140306	DEReese					Added processCard (), processSide (), processBooleanElement (), and
//										processStringElement () (bug 000033).
//	20140312	DEReese					Added processSideTextOnly () and called it in processCard for each
//										side (bug 000033).
//	20140313	DEReese					Added code to processBooleanElement () and processStringElement ()
//										(bug 000033).
//	20151127	DEReese					Added GPL information (bug 000047).
//

public class AsianFlashDOM {

	/**
	 * Factory to create DocumentBuilder.
	 */
	private DocumentBuilderFactory theFactory;
	
	/**
	 * Parser DocumentBuilder.
	 */
	private DocumentBuilder theParser;
	
	/**
	 * Document to be parsed.
	 */
	private Document theDocument;
	
	/**
	 * List of elements.
	 */
	private Element theElementList;
	
	/**
	 * Constructor. The file name given as a parameter is the name of the file to be parsed.
	 * @param theFileName	name of file to be parsed.
	 * @throws	Exception Exception from attempt to create a new DocumentBuilder.
	 */
	public AsianFlashDOM (String theFileName) throws Exception
	{
		if (theFileName == null)
			new Error ("AsianFlashDOM (): theFileName == null");
		parseFile (theFileName);
	}
	
	/**
	 * This method parses a given file.
	 * @param theFileName	Name of the file.
	 * @throws Exception	Exception generated on errors from called methods.
	 */
	private void parseFile (String theFileName) throws Exception
	{
		// Attempt to get the element list from the file. Note that this could throw an exception.
		
		theFactory = DocumentBuilderFactory.newInstance();
		theParser = theFactory.newDocumentBuilder();
		theDocument = theParser.parse(theFileName);
		theElementList = theDocument.getDocumentElement();
	}
	
	/**
	 * This operation attempts to return the font name for a given side from the XML information.
	 * @param theSideName	name of side element (Side1Information, Side2Information, or Side3Information).
	 * @return	String containing the font name or null if it can not be found in the XML information.
	 */
	private String getSideFontName (String theSideName)
	{
		String	tempFontName = null;
		NodeList	theNodeList;
		Element		theElement;
		
		// If the name string is null, generate an error.
		
		if (theSideName == null)
			new Error ("getSideFontName (): theSideName == null");
		
		// Attempt to find the SideInformation element node list.
		
		theNodeList = theElementList.getElementsByTagName("SideInformation");
		if (theNodeList == null)
			return (null);
		
		// From the SideInformation node list, attempt to find the named sub-element.
		
		theElement = (Element)theNodeList.item(0);
		theNodeList = theElement.getElementsByTagName(theSideName);
		if (theNodeList == null)
			return (null);
		
		// From the named element, attempt to find the font attribute.
		
		theElement = (Element)theNodeList.item(0);
		if (theElement == null)
			return (null);
		tempFontName = theElement.getAttribute("font");
		
		return (tempFontName);
	}
	
	/**
	 * This operation returns the font size for a card side, based on information found in the XML SideInformation element in the file.
	 * @param theSideName	Name of the side (Side1Information, Side2Information, or Side3Information).
	 * @return String containing the font size (which can be converted to an integer), or null if there was none in the file.
	 */
	private String getFontSizeString (String theSideName)
	{
		String	tempFontName = null;
		NodeList	theNodeList;
		Element		theElement;
		
		// If the name string is null, generate an error.
		
		if (theSideName == null)
			new Error ("getSideFontName (): theSideName == null");
		
		// Attempt to find the SideInformation element node list.
		
		theNodeList = theElementList.getElementsByTagName("SideInformation");
		if (theNodeList == null)
			return (null);
		
		// From the SideInformation node list, attempt to find the named sub-element.
		
		theElement = (Element)theNodeList.item(0);
		theNodeList = theElement.getElementsByTagName(theSideName);
		if (theNodeList == null)
			return (null);
		
		// From the named element, attempt to find the font attribute.
		
		theElement = (Element)theNodeList.item(0);
		if (theElement == null)
			return (null);
		tempFontName = theElement.getAttribute("size");
		
		return (tempFontName);		
	}
	
	/**
	 * This operation returns the title for a card side, based on information found in the XML SideInformation element in the file.
	 * @param theSideName	Name of the side (Side1Information, Side2Information, or Side3Information).
	 * @return String containing the title, or null if there was none in the file.
	 */
	private String getTitleString (String theSideName)
	{
		String	tempFontName = null;
		NodeList	theNodeList;
		Element		theElement;
		
		// If the name string is null, generate an error.
		
		if (theSideName == null)
			new Error ("getSideFontName (): theSideName == null");
		
		// Attempt to find the SideInformation element node list.
		
		theNodeList = theElementList.getElementsByTagName("SideInformation");
		if (theNodeList == null)
			return (null);
		
		// From the SideInformation node list, attempt to find the named sub-element.
		
		theElement = (Element)theNodeList.item(0);
		theNodeList = theElement.getElementsByTagName(theSideName);
		if (theNodeList == null)
			return (null);
		
		// From the named element, attempt to find the font attribute.
		
		theElement = (Element)theNodeList.item(0);
		if (theElement == null)
			return (null);
		tempFontName = theElement.getAttribute("title");
		
		return (tempFontName);		
	}
	
	/**
	 * This operation returns the side 1 font name contained in the XML file.
	 * @return	Font name contained in the XML file, or null if no font was included.
	 */
	public String getSide1FontName ()
	{
		return (getSideFontName ("Side1Information"));
	}

	/**
	 * This operation returns the side 2 font name contained in the XML file.
	 * @return	Font name contained in the XML file, or null if no font was included.
	 */
	public String getSide2FontName ()
	{
		return (getSideFontName ("Side2Information"));
	}

	/**
	 * This operation returns the side 3 font name contained in the XML file.
	 * @return	Font name contained in the XML file, or null if no font was included.
	 */
	public String getSide3FontName ()
	{
		return (getSideFontName ("Side3Information"));
	}
	
	/**
	 * This operation returns the side 1 font size contained in the XML file.
	 * @return	Size of font contained in XML file, or -1 if no size is included.
	 */
	public int getSide1Size ()
	{
		int		tempSize = -1;
		String	tempString = getFontSizeString ("Side1Information");
		
		if (tempString == null)
			return (tempSize);
		tempSize = new Integer(tempString);

		return (tempSize);
	}
	
	/**
	 * This operation returns the side 2 font size contained in the XML file.
	 * @return	Size of font contained in XML file, or -1 if no size is included.
	 */
	public int getSide2Size ()
	{
		int		tempSize = -1;
		String	tempString = getFontSizeString ("Side2Information");
		
		if (tempString == null)
			return (tempSize);
		tempSize = new Integer(tempString);

		return (tempSize);
	}
	
	/**
	 * This operation returns the side 3 font size contained in the XML file.
	 * @return	Size of font contained in XML file, or -1 if no size is included.
	 */
	public int getSide3Size ()
	{
		int		tempSize = -1;
		String	tempString = getFontSizeString ("Side3Information");
		
		if (tempString == null)
			return (tempSize);
		tempSize = new Integer(tempString);

		return (tempSize);
	}
	
	/**
	 * This operation returns the side 1 title contained in the XML file.
	 * @return	Title contained in XML file, or null if no title is included.
	 */
	public String getSide1Title ()
	{
		return (getTitleString ("Side1Information"));
	}

	/**
	 * This operation returns the side 2 title contained in the XML file.
	 * @return	Title contained in XML file, or null if no title is included.
	 */
	public String getSide2Title ()
	{
		return (getTitleString ("Side2Information"));
	}

	/**
	 * This operation returns the side 3 title contained in the XML file.
	 * @return	Title contained in XML file, or null if no title is included.
	 */
	public String getSide3Title ()
	{
		return (getTitleString ("Side3Information"));
	}

	/**
	 * This operation returns a list of flash cards, built from data contained in the XML file.
	 * @param	loadFullTest	Indicates that full data from a saved test is to be loaded.
	 * @param 	theFileName		Name of the file containing the cards.
	 * @return	Pointer to the first flash card in the list, or null if there was none included in the file.
	 */
	public InternalFlashCard getFlashCardSet (boolean loadFullTest, String theFileName)
	{
		InternalFlashCard	tempCardSet = null;
		NodeList			theNodeList = null;
		Element				theElement = null;
		int					numberOfCards = 0;
				
		// Get all of the FlashCard elements.
		
		theNodeList = theElementList.getElementsByTagName("FlashCard");
		if (theNodeList == null)
			return (null);
		numberOfCards = theNodeList.getLength();
		
		// Process each card in the list.
		
		for (int i = 0; i < numberOfCards; i++)
		{
			InternalFlashCard	tempCard;
			
			// Get the flash card element, process it, and add it to the temporary flash card list.
			
			theElement = (Element)theNodeList.item(i);

			tempCard = processCard(theElement, theFileName, loadFullTest);
			if (tempCardSet == null)
				tempCardSet = tempCard;
			else
				tempCardSet.addNewCardToList(tempCard);
			
		}

		// Return the card list from the file.
		
		return (tempCardSet);
	}
	
	/**
	 * This method processes the XML for a flash card.
	 * @param theCardElement	XML tree for a flash card.
	 * @param cardFileName	Name of file from which the card was retrieved.
	 * @param loadFullTest		True if loading a saved game; false otherwise.
	 * @return A filled out flash card, or null if an error occurs.
	 */
	private InternalFlashCard processCard (Element theCardElement, String cardFileName, boolean loadFullTest)
	{
		// Return null if the card XML element is null.
		
		if (theCardElement == null)
			return null;
		
		InternalFlashCard tempCard = null;
		boolean				side1Correct = false;
		boolean				side2Correct = false;
		boolean				side3Correct = false;
		boolean				cardViewed = false;
		
		// If the program is loading a saved test, get the directory that contained the original card.
		// This will replace the card directory passed as a parameter.
		
		if (loadFullTest)
		{
			cardFileName = processStringElement (theCardElement, "CardFileName");
		}
		
		// Create a directory name from the card file name.
		
		String	directoryName = new File (cardFileName).getParent() + "/";

		// Get the string to be displayed for each side of the card.
		
		String				side1String = processSide (theCardElement, "Side1", directoryName);
		String				side2String = processSide (theCardElement, "Side2", directoryName);
		String				side3String = processSide (theCardElement, "Side3", directoryName);

		// If the program is loading a saved test, get the side correct and card viewed boolean elements.
		// These elements were initialized to false, so will be valid values even if the program is not
		// loading a full test.
		
		if (loadFullTest)
		{
			side1Correct = processBooleanElement (theCardElement, "Side1Correct");
			side2Correct = processBooleanElement (theCardElement, "Side2Correct");
			side3Correct = processBooleanElement (theCardElement, "Side3Correct");
			cardViewed = processBooleanElement (theCardElement, "CardViewed");
		}
		
		// Build a new flash card with the data collected above and return it to the caller.
		
		tempCard = new InternalFlashCard (side1String, side2String, side3String);
		tempCard.setSide1Correct(side1Correct);
		tempCard.setSide2Correct(side2Correct);
		tempCard.setSide3Correct(side3Correct);
		tempCard.setSide1TextOnly(processSideTextOnly (theCardElement, "Side1", directoryName));
		tempCard.setSide2TextOnly(processSideTextOnly (theCardElement, "Side2", directoryName));
		tempCard.setSide3TextOnly(processSideTextOnly (theCardElement, "Side3", directoryName));
		tempCard.setCardViewed(cardViewed);				
		tempCard.setCardFileName(cardFileName);
		return tempCard;
	}
	
	/**
	 * This method processes a side element.
	 * @param cardElement	Element containing the flash card.
	 * @param sideName		Name of the side element to be processed.
	 * @param directoryName	Name of the directory containing the file containing the card.
	 * @return				String containing HTML for the side. If an error occurs, this will read "Uninitialized".
	 */
	private String processSide (Element cardElement, String sideName, String directoryName)
	{
		if (sideName == null)
			return new String ("Uninitialized");
		
		NodeList	sideNodeList = cardElement.getElementsByTagName(sideName);
		Element		sideElement = null;
		
		// If the sideNodeList contains a number of elements not equal to 1, there has been an error.
		
		if (sideNodeList.getLength() != 1)
		{
			System.out.println ("ERROR: Attempt to find element (" + sideName + ") returned " + sideNodeList.getLength()
					+ " elements (should be 1) in file: " + directoryName);
			return new String ("Uninitialized");
		}
		
		// Get the element containing the side's text.
		
		sideElement = (Element)sideNodeList.item(0);
		if (sideElement == null)
		{
			System.out.println("ERROR: could not get sideElement for element (" + sideName + ")"
					+ " in file: " + directoryName);
			return new String ("Uninitialized");
		}
		
		// Create an XML to string transformer.
		
		Transformer transformer = null;
		
		try
		{
			transformer = TransformerFactory.newInstance().newTransformer();
		} 
		catch (Exception e) 
		{
			System.out.println ("ERROR: Could not create Transformer while attempting to convert XML"
					+ " for element (" + sideName + ") in file: " + directoryName);
			System.out.println ("Exception returned was:");
			System.out.println ();
			e.printStackTrace();
			System.out.println ();
			return new String ("Uninitialized");
		}

		// Create a string writer and transform the XML to a string.
		
		StringWriter writer = new StringWriter();
		try 
		{
			transformer.transform(new DOMSource (sideElement), new StreamResult(writer));
		} 
		catch (TransformerException e) 
		{
			System.out.println ("ERROR: Could not transform XML to string"
					+ " for element (" + sideName + ") in file: " + directoryName);
			System.out.println ("Exception returned was:");
			System.out.println ();
			e.printStackTrace();
			System.out.println ();
			return new String ("Uninitialized");
		}
		String theXML = writer.toString();

		// Clear out any XML tags, <Side> tags, <Side/> tags, and <Side/> tags, and carriage returns.
		
		theXML = theXML.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		theXML = theXML.replace("\n", "");
		theXML = theXML.replace("<" + sideElement.getTagName() + ">", "");
		theXML = theXML.replace("</" + sideElement.getTagName() + ">", "");
		theXML = theXML.replace("<" + sideElement.getTagName() + "/>", "");
		theXML = "<BASE href=\"" + directoryName + "\"/>" + theXML;
		
		// Return the XML.
		
		return theXML;
	}
	
	/**
	 * This method returns the text content (without any HTML/XML tags) of a specified sideElement.
	 * @param cardElement	Element containing the flash card.
	 * @param sideName		Name of the side element to be processed.
	 * @param directoryName	Name of the directory containing the file containing the card.
	 * @return				String containing HTML for the side. If an error occurs, this will read "Uninitialized".
	 */
	private String processSideTextOnly (Element cardElement, String sideName, String directoryName)
	{
		if (sideName == null)
			return new String ("Uninitialized");
		
		NodeList	sideNodeList = cardElement.getElementsByTagName(sideName);
		Element		sideElement = null;
		
		// If the sideNodeList contains a number of elements not equal to 1, there has been an error.
		
		if (sideNodeList.getLength() != 1)
		{
			System.out.println ("ERROR: Attempt to find element (" + sideName + ") returned " + sideNodeList.getLength()
					+ " elements (should be 1) in file: " + directoryName);
			return new String ("Uninitialized");
		}
		
		// Get the element containing the side's text.
		
		sideElement = (Element)sideNodeList.item(0);
		if (sideElement == null)
		{
			System.out.println("ERROR: could not get sideElement for element (" + sideName + ")"
					+ " in file: " + directoryName);
			return new String ("Uninitialized");
		}
		
		String	tempString = new String (sideElement.getTextContent());
		return tempString;
	}
	
	/**
	 * This method returns a boolean value  from an element. If the element is not true or false, it
	 * returns false.
	 * @param cardElement	Element containing the card information.
	 * @param elementName	Name of the element.
	 * @return				True if the element is "true", false otherwise.
	 */
	private boolean processBooleanElement (Element cardElement, String elementName)
	{
		// Check for errors.
		
		if (cardElement == null)
			return false;
		if (elementName == null)
			return false;
		
		// Get the list of elements with the tag name.
		
		NodeList elementList = cardElement.getElementsByTagName(elementName);
		if (elementList == null)
			return false;
		
		// If there are less than 1 or more than 1 element in the list, return false.
		
		if (elementList.getLength() != 1)
			return false;
		
		// Get the first (only) entry in the list.
		
		Element	theElement = (Element)(elementList.item(0));
		
		// If the element contains a child, it's not purely text, so return false.
		
		if (theElement.hasChildNodes())
			return false;
		
		// Get the text for the element. Convert it to lower case.
		
		String	theElementString = theElement.getTextContent();
		theElementString = theElementString.toLowerCase();
		
		// If the string is "true" return true. Otherwise, return false.
		
		if (theElementString == "true")
			return true;
		
		return false;
	}
	
	/**
	 * This method returns a string element, or null if an error is detected.
	 * @param cardElement	Element containing the card information.
	 * @param elementName	Name of the element.
	 * @return	String containing the value of the element, or null if an error is detected.
	 */
	private String processStringElement (Element cardElement, String elementName)
	{
		// Check for errors.
		
		if (cardElement == null)
			return null;
		if (elementName == null)
			return null;
		
		// Get the list of elements with the tag name.
		
		NodeList elementList = cardElement.getElementsByTagName(elementName);
		if (elementList == null)
			return null;
		
		// If there are less than 1 or more than 1 element in the list, return false.
		
		if (elementList.getLength() != 1)
			return null;
		
		// Get the first (only) entry in the list.
		
		Element	theElement = (Element)(elementList.item(0));
		
		// If the element contains a child, it's not purely text, so return false.
		
		if (theElement.hasChildNodes())
			return null;
		
		// Get the text for the element and return it.
		
		String	theElementString = theElement.getTextContent();
		return theElementString;
	}
}
