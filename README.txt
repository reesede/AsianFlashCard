
This is the README.txt file for the AsianFlashCard program.

1. TABLE OF CONTENTS
====================
1.	TABLE OF CONTENTS
2.	PROGRAM INFORMATION
3.	LICENSE
4.	GENERAL
5.	REQUIREMENTS
6.	INSTALLATION
7.	PROGRAM INVOCATION
8.	NAVIGATION
9.	MENU ITEMS
10.	FLASHCARD FILE FORMAT
11.	RENDERING PROBLEMS
12.	KNOWN ISSUES


2. PROGRAM INFORMATION
======================

AsianFlashCard.jar
Version 5.1
Copyright 2012-2016, David E. Reese


3. LICENSE
==========

This file is part of AsianFlashCard.

AsianFlashCard is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

AsianFlashCard is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with AsianFlashCard.  If not, see <http://www.gnu.org/licenses/>.


4. GENERAL
==========

Some languages, such as Chinese and Japanese, are difficult to use with
traditional flashcards or flashcard programs, since the characters do not
represent phonetic symbols, but ideographs. For these languages, one really
needs to have "three-sided" flashcards: one side for the ideograph, one
side for the phonetics, and one side for the meaning.

The AsianFlashCard program provides a solution to this problem by allowing
three sides to be displayed.


5. REQUIREMENTS
===============
The AsianFlashCard program requires Java runtime environment JRE-1.7 or later.


6. INSTALLATION
===============
If the program came in a tar file, untar the tarball in any convenient directory.
After installation, you can create flashcard (.alfc) files with the built-in card
set editor tool, or use a text editor that creates flat text files (however, see 
"Character Encoding" in KNOWN ISSUES, below).


7. PROGRAM INVOCATION
=====================

Windows:
	Double-click the AsianFlashCard.bat file to launch.

Linux:
	Run using the following command:

		> ./AsianFlashCard.sh
		
It is not recommended to invoke the Jar file directly, due to character
encoding issues on Windows (see "Character Encoding" in the KNOWN ISSUES
section below).
		
		
8. NAVIGATION
=============
While running a test, buttons provide navigation between each side of the
flash card and to the next card. In addition buttons allow the user to mark
each side correct (if incorrect) or incorrect (if correct already).

Each button has a quick key which can be pressed, causing the same effect as
if the button were pressed.


9. MENU ITEMS
=============

Card Set -> Load New Card Set

	Brings up a dialog to allow the user to load a card set. The dialog
	searchs for files with the ".alfc" extension. Note that multiple files
	can be selected. Note that the titles, fonts, and font sizes from the 
	first file loaded are used, so this potentially can cause issues if
	different languages or fonts are used in different files.

Card Set -> Append Card Set

	Append a file or files onto the existing card set. Note that the titles,
	fonts, and font sizes from the first file loaded are used, so this
	potentially can cause issues if different languages or fonts are
	used in different files.

Card Set -> Clear Card Set

	Clears the current card set (throws them away). This also clears the
	current test if configured.

Card Set -> Quit

	Exit the program.

Test -> New Test

	Starts a new test. This brings up a dialog to allow the user to specify
	how the test is to be executed. Specific options are:
	
	1) Which side of the card is going to be the visible side (the other sides
	are hidden).
	2) Whether to repeat displaying cards until they are marked correct or to
	display each card only once.
	3) The number of cards from the card set to be used in the test.

Test -> Restart Test

	Restart the test. This clears all indications that card sides have
	been viewed and marked correct. All card statistics are reset to
	zero.

Test -> Card Statistics

	Displays a dialog containing the current statistics for the cards in
	the test. There are 3 columns:

	Column 1 - shows the visible sides of the cards in the test.
	Column 2 - shows the number of times the card has been shown, up to
			the point at which the left side was marked correct.
			Note that the counts in Column 2 and in Column 3 can
			be different if one side was marked correct at a
			different time than the other or one side has not yet
			been marked correct.
	Column 3 - shows the number of times the card has been shown, up to
			the point at which the right side was marked correct.
			Note that the counts in Column 2 and in Column 3 can
			be different if one side was marked correct at a
			different time than the other or one side has not yet
			been marked correct.

	The color codes are as follows:

	LT. GRAY	- card has not been displayed yet.
	RED		- the card has been displayed, and the corresponding
			  side has not been marked correct.
	YELLOW		- the card has been displayed, and the corresponding
			  side has been marked correct, but it took more than
			  one display of the card for it to be marked correct.
	GREEN		- the card has been displayed, and the corresponding
			  side was marked correct on the first display.

	The statistics display dialog may be kept up while performing a test,
	and it will be updated automatically. However, if one of the Load New
	Card, Append Card Set, Clear Card Set, Restart Test, or New Test menu
	items is selected, the dialog will be removed and will have to be
	brought up again.

Tools -> Show Scratchpad

	Displays a scratchpad window. By holding down the mouse button in the
	window and moving the cursor, the user may draw or write. If the "Clear
	when new card displayed" check box is checked, the drawing will be cleared
	each time the user selects the "Next" button to move to a new card. If this
	button is not checked, then the drawing will have to be cleared manually.
	The dialog "Close" button closes the dialog; the dialog "Clear" button clears
	the drawing, but keeps the dialog open. Note that closing the dialog also
	clears the drawing.
	
Tools -> Open Card Set Editor

	Displays the card set editor dialog, which allows the user to create and
	edit files containing card sets. For more details, see the card set editor
	help by bringing up the card set editor and selecting the Help->Editor Help
	menu command.

Help -> Help

	Displays this README file.
	
Help -> Display Change Log

	Displays the change log for the program.

Help -> About

	Displays information about this program and a picture of
	Lady Murasaki.


10. FLASHCARD FILE FORMAT
=========================

The card files, by default, have an extension of .alfc, and the load card set
dialog will look for such files.

The file is plain text, but is formatted in XML. THE FILE MUST USE THE UTF-8
CHARACTER SET. 

Below is an example flash card file:

<FlashCardSet>
<SideInformation>
     <Side1Information title="Character" font="Times New Roman" size="100"></Side1Information>
     <Side2Information title="Pinyin" font="Times New Roman" size="50"></Side2Information>
     <Side3Information title="English" font="Times New Roman" size="50"></Side3Information>
</SideInformation>
<FlashCard>
	<Side1>card side 1</Side1>
	<Side2>card side 2</Side2>
	<Side3>card side 3</Side3>
</FlashCard>	
</FlashCardSet>

The <SideInformation> element contains the information about each side of the
flash cards. Each side has a title, which will show up in the buttons when the
card is displayed, a font for the side, and a font size of the side. Note that
only the first <SideInformation> element will be used; any additional such 
elements will be ignored.

The <FlashCard> element contains the flash card, which consists of three sides.
Note that there can be multiple <FlashCard> elements.

Each <Side1>, <Side2>, and <Side3> element contains HTML text. It is possible to
encode HTML fonts, images, etc. in each of these elements. For example, a side could
be:

<Side1><IMG src="n35.jpg"/><BR/>(n35)</Side1>

which would allow the display of a picture in side 1.

Note that, because the card set file is encoded in XML, it always is necessary to
end an element. So, <BR/> or <BR></BR> is required; <BR> would cause an error when
the file is being loaded.

Also note that HTML links do not function in the current version of the program, although
they can be added to card files.

Although HTML is displayed on each card side when the test is being run, only the text
portions are displayed in the statistics dialog. So, in the example above, the statistics
dialog entry would show (n35), without the image. If no text was put into the side but
only the image, the statistics entry would be blank. Therefore, it is important to put
some unique text in each side to show up in the statistics dialog.

The program always assumes that the base for relative paths is the directory containing
the file for the card. If cards come from different directories, the base will be different
for the cards in different directories.

Finally, the sides will be surrounded with <CENTER></CENTER> before being displayed, so that
the cards will be centered in the card window. The user can embed other justifications within
the side HTML, if desired.


11. RENDERING PROBLEMS
======================

Some languages may not render properly on a "standard" software installation (i.e., Tibetan does
not work correctly on a standard Windows installation, even when there was a Tibetan font present).
Solving these problems is beyond the scope of this help file; if a language does not render
correctly, it is suggested to do a Google search for solutions.

Also, see the "Character Encoding" section of KNOWN ISSUES, below.


12. KNOWN ISSUES
================

Character Encoding
------------------
In order to display non-Latin text correctly, any flash card (.alfc) files must be saved in
the UTF-8 character encoding. This usually is not a problem on Linux, but can be a problem
on Windows, since Windows normally saves text files in a variety of formats, depending on
the region of the world (i.e., CP1250 in the USA, CP1252 in Europe, etc.). If the flash card
files are edited with a text editor, it is imperative that that editor save the files in
UTF-8.

For similar reasons, the program should be invoked by executing the AsianFlashCard.sh (on Linux)
or AsianFlashCard.bat (on Windows) scripts, rather than executing the AFC.jar file directly. The
scripts invoke Java with the -Dfile.encoding=UTF-8 option. This ensures that any files created
or modified using the built-in card set editor will save the card files in UTF-8 character set.
Note that this primarily is a problem on Windows. Linux usually uses UTF-8 by default, so the cards
normally will be saved in UTF-8 if the editor is run on Linux.

