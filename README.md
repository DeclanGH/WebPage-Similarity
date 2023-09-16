# WebPage-Similarity
This program compares a user's wikipedia webpage to 10 other wikipedia pages in our database and tells them the one that has the greatest similarity to theirs. 

CSC365 Assignment 1
===================

Requirements
------------

This assignment asks you to create a similarity-based web-site recommendation system based on Wikipedia pages.

*   The program reads 10 (or more) Wikipedia pages. The urls for these web pages can be maintained in a control file that is read when the program starts. Use a framwwork such as [JSoup](https://jsoup.org/) to extract text bodies etc from html.
*   Establish a similarity metric, that must include information based on custom frequency tables (as will be discussed in class), possibly weighted by or in conjunction with other attributes.
*   Create a user interface that allows a user to indicate one topic or site, and displays two similar ones. The presentation details are up to you. It may be a web/browser-based (for example Spring) or GUI base (Swing, JavaFX, or Android) for the GUI. For Swing, read through the relevant parts of the [Swing tutorial](http://java.sun.com/docs/books/tutorial/uiswing/index.html) first.
*   Use appropriate existing libraries for every component other than the custom similarity tracking.

Test your program thoroughly before submitting, and arrange a demo within 48 hours of submitting. (Demoing before submitting is strongly encouraged.)

* * *

Professor: [Doug Lea](https://gee.cs.oswego.edu/dl/)
