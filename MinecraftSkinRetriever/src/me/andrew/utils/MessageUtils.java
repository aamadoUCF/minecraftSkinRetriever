package me.andrew.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageUtils 
{

	// Sends pop-up error message.
	public void sendErrorMessage(String message, JFrame frame)
	{
		JOptionPane.showMessageDialog(frame, message);
		
		return;
	}
	
}
