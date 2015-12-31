/**
 * 
 */
package cardseteditor;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

/**
 * This class implements the editor document object model, and it provides routines for reading and
 * writing card sets from and to files.
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
//	20151223	DEReese				Creation (bug 000051).
//

public class EditorDOM 
{	
	/**
	 * Default constructor.
	 */
	public EditorDOM ()
	{	
	}
	
	/**
	 * This method outputs a given card set to a given file.
	 * @param theFileName	File to which the cardset is to be written.
	 * @param theCardSet	Cardset to write.
	 * @throws Exception	Thrown if an error is detected by a called routine.
	 */
	public void writeCardSet (String theFileName, EditableCardSet theCardSet) throws Exception
	{
		PrintWriter	outputFile = new PrintWriter (theFileName);
		printFlashCardSet (outputFile, theCardSet);
		outputFile.close();
		
	}
	
	/**
	 * This method prints out the flash card set.
	 * @param theWriter		File to which the card set is to be written.
	 * @param theCardSet	Card set to print.
	 * @throws Exception	Thrown if a file error occurs.
	 */
	private void printFlashCardSet (PrintWriter theWriter, EditableCardSet theCardSet) throws Exception
	{
		// Print out the opening tag for the FlashCardSet.
		
		theWriter.println("<FlashCardSet>");
	
		// Print out the side information.
		printSideInformation (theWriter, theCardSet);
		
		// Print out the flash cards.

		printFlashCards (theWriter, theCardSet);
		
		// Print out the closing tag for the FlashCardSet.
		
		theWriter.println("</FlashCardSet>");
	}
	
	/**
	 * This method prints out the general side information for the card set.
	 * @param theWriter		File to which the card set is to be written.
	 * @param theCardSet	Card set to print.
	 * @throws Exception	Thrown if a file error occurs.
	 */
	private void printSideInformation (PrintWriter theWriter, EditableCardSet theCardSet) throws Exception
	{
		String	outString;
		
		// Print out the opening tag.
		
		theWriter.println("  <SideInformation>");
		
		// Print out side 1 information.
		
		outString = "    <Side1Information title=\"" + theCardSet.getSideTitle(1) + "\" font=\"" +
				theCardSet.getSideFont(1) + "\" size=\"" + theCardSet.getSideSize(1) + "\"></Side1Information>";
		theWriter.println(outString);
		
		// Print out side 2 information.
		
		outString = "    <Side2Information title=\"" + theCardSet.getSideTitle(2) + "\" font=\"" +
				theCardSet.getSideFont(2) + "\" size=\"" + theCardSet.getSideSize(2) + "\"></Side2Information>";
		theWriter.println(outString);
		
		// Print out side 3 information.
		
		outString = "    <Side3Information title=\"" + theCardSet.getSideTitle(3) + "\" font=\"" +
				theCardSet.getSideFont(3) + "\" size=\"" + theCardSet.getSideSize(3) + "\"></Side3Information>";
		theWriter.println(outString);
		
		// Print out the closing tag.
		
		theWriter.println("  </SideInformation>");
	}
	
	/**
	 * This method prints out the flash cards in the flashcard set.
	 * @param theWriter
	 * @param theCardSet
	 * @throws Exception
	 */
	private void printFlashCards (PrintWriter theWriter, EditableCardSet theCardSet) throws Exception
	{
		for (int i = 1; i <= theCardSet.getNumCards(); i++)
		{
			theWriter.println("  <FlashCard>");
			theWriter.println("    <Side1>" + theCardSet.getCardSideText(i, 1) + "</Side1>");
			theWriter.println("    <Side2>" + theCardSet.getCardSideText(i, 2) + "</Side2>");
			theWriter.println("    <Side3>" + theCardSet.getCardSideText(i, 3) + "</Side3>");
			theWriter.println("  </FlashCard>");
		}
	}
	
	/**
	 * This method reads the card set file for the editor.
	 * @param theFileName	Name of file to read.
	 * @return				Structure containing the editable information about the card set.
	 * @throws Exception	Thrown if the structure of the file is incorrect.
	 */
	public EditableCardSet readCardSet (String theFileName) throws Exception
	{
		DocumentBuilderFactory	theFactory;
		DocumentBuilder			theDocumentBuilder;
		Document				theDocument;
		Element					flashCardSetElement;
		Element					flashCardElement;
		Element					sideInformationElement;
		Element					theElement;
		EditableCardSet			theCardSet;
		NodeList				theNodeList;
		
		// Get a new document builder factory and document builder.
		
		theFactory = DocumentBuilderFactory.newInstance();
		theDocumentBuilder = theFactory.newDocumentBuilder();
		
		// Parse the file.
		
		theDocument = theDocumentBuilder.parse(theFileName);
		
		// Get the flash card set from the document. This should be the root element in the document.
		
		flashCardSetElement = theDocument.getDocumentElement();
		
		// Create a new card set into which data from the document will be stored.
		
		theCardSet = new EditableCardSet();
		
		// Process the general side information.
		
		theNodeList = flashCardSetElement.getElementsByTagName("SideInformation");
		if (theNodeList.getLength() != 1)
			throw new Exception ("EditableCardSet.readCardSet () detected theNodeList.getLength () for SideInformation returned a value (" + theNodeList.getLength() + ") != 1.");
		sideInformationElement = (Element)theNodeList.item(0);
		
		theNodeList = sideInformationElement.getElementsByTagName("Side1Information");
		if (theNodeList.getLength() != 1)
			throw new Exception ("EditableCardSet.readCardSet () detected theNodeList.getLength () for Side1Information returned a value (" + theNodeList.getLength() + ") != 1.");
		theElement = (Element)theNodeList.item(0);
		theCardSet.setSideTitle(1, theElement.getAttribute("title"));
		theCardSet.setSideFont(1, theElement.getAttribute("font"));
		theCardSet.setSideSize(1, theElement.getAttribute("size"));
		
		theNodeList = sideInformationElement.getElementsByTagName("Side2Information");
		if (theNodeList.getLength() != 1)
			throw new Exception ("EditableCardSet.readCardSet () detected theNodeList.getLength () for Side2Information returned a value (" + theNodeList.getLength() + ") != 1.");
		theElement = (Element)theNodeList.item(0);
		theCardSet.setSideTitle(2, theElement.getAttribute("title"));
		theCardSet.setSideFont(2, theElement.getAttribute("font"));
		theCardSet.setSideSize(2, theElement.getAttribute("size"));
		
		theNodeList = sideInformationElement.getElementsByTagName("Side3Information");
		if (theNodeList.getLength() != 1)
			throw new Exception ("EditableCardSet.readCardSet () detected theNodeList.getLength () for Side3Information returned a value (" + theNodeList.getLength() + ") != 1.");
		theElement = (Element)theNodeList.item(0);
		theCardSet.setSideTitle(3, theElement.getAttribute("title"));
		theCardSet.setSideFont(3, theElement.getAttribute("font"));
		theCardSet.setSideSize(3, theElement.getAttribute("size"));
		
		// Get the flash cards.
		
		theNodeList = flashCardSetElement.getElementsByTagName("FlashCard");

		// Iterate through each card in the list.
		
		for (int i = 0; i < theNodeList.getLength(); i++)
		{
			Transformer transformer;
			StreamResult result;
			DOMSource source;

			// Get the flash card.
			
			flashCardElement = (Element)theNodeList.item(i);
			
			// Get the text for side 1. First, get the node. Then, transform the node to convert it into
			// XML. Then strip out the XML headers. Finally, strip out the side element start and end
			// tags.
			
			NodeList tNodeList = flashCardElement.getElementsByTagName("Side1");
			if (tNodeList.getLength() != 1)
				throw new Exception ("EditableCardSet.readCardSet () detected tNodeList.getLength () for Side1 returned a value (" + tNodeList.getLength() + ") != 1.");

			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			result = new StreamResult(new StringWriter());
			source = new DOMSource(tNodeList.item(0));
			transformer.transform(source, result);

			String side1Text = result.getWriter().toString();
			side1Text=side1Text.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
			side1Text=side1Text.replaceAll("<Side1>", "").trim();
			side1Text=side1Text.replaceAll("</Side1>", "").trim();
			
			// Get the text for side 2. First, get the node. Then, transform the node to convert it into
			// XML. Then strip out the XML headers. Finally, strip out the side element start and end
			// tags.
			
			tNodeList = flashCardElement.getElementsByTagName("Side2");
			if (tNodeList.getLength() != 1)
				throw new Exception ("EditableCardSet.readCardSet () detected tNodeList.getLength () for Side2 returned a value (" + tNodeList.getLength() + ") != 1.");

			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			result = new StreamResult(new StringWriter());
			source = new DOMSource(tNodeList.item(0));
			transformer.transform(source, result);

			String side2Text = result.getWriter().toString();
			side2Text=side2Text.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
			side2Text=side2Text.replaceAll("<Side2>", "").trim();
			side2Text=side2Text.replaceAll("</Side2>", "").trim();
			
			// Get the text for side 3. First, get the node. Then, transform the node to convert it into
			// XML. Then strip out the XML headers. Finally, strip out the side element start and end
			// tags.
			
			tNodeList = flashCardElement.getElementsByTagName("Side3");
			if (tNodeList.getLength() != 1)
				throw new Exception ("EditableCardSet.readCardSet () detected tNodeList.getLength () for Side3 returned a value (" + tNodeList.getLength() + ") != 1.");

			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			result = new StreamResult(new StringWriter());
			source = new DOMSource(tNodeList.item(0));
			transformer.transform(source, result);
			
			String side3Text = result.getWriter().toString();
			side3Text=side3Text.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
			side3Text=side3Text.replaceAll("<Side3>", "").trim();
			side3Text=side3Text.replaceAll("</Side3>", "").trim();
			
			// Add the flashcard information to the flash card list.
			
			theCardSet.appendCard(side1Text, side2Text, side3Text);
		}
		
		return theCardSet;
	}
}
