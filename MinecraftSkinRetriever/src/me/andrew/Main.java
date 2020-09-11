package me.andrew;

import me.andrew.gui.GUI;

// ----------------------------------
// |								|
// |	Minecraft Skin Retriever	|
// |		  Developed by	 		|
// |		  Andrew Amado			|
// ----------------------------------
//					|
//					|
// 					|

// This program is one of my first real projects. It involves using the Minecraft
// and Mojang APIs to retrieve the mentioned player's skin. An Internet connection
// is needed to successfully run the program. At the end, once the skin is presented,
// you may click download to download that skin to your machine.

// I used the Gson library to help with converting strings to JSON Objects when
// I retrieve the response from the APIs.

// Overall, this was my first time dealing with many new methods and technologies
// such as APIs, HTTP requests, and JSON Objects in Java. It was really cool to learn!

public class Main 
{
	public static void main(String [] args)
	{
		new GUI();
		
		return;
	}
}
