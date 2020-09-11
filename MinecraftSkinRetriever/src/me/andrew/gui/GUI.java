package me.andrew.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.andrew.mc.MinecraftUtils;
import me.andrew.utils.IOUtils;
import me.andrew.utils.MessageUtils;

public class GUI implements ActionListener
{
	private JFrame frame;
	private JPanel userPanel;
	private JPanel imagePanel;
	private JPanel container;
	private JButton button;
	private JButton downloadSkin;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel skinImage;
	
	private MessageUtils msg = new MessageUtils();
	private MinecraftUtils mc = new MinecraftUtils();
	private IOUtils ioUtil = new IOUtils();
	
	BufferedImage skin = null;
	
	
	private final int frameSizeX = 600;
	private final int frameSizeY = 400;
	// Uses Minecraft/Mojang API to retrieve skin
	// https://ourcodeworld.com/articles/read/1293/how-to-retrieve-the-skin-of-a-minecraft-user-from-mojang-using-python-3
	
	public GUI()
	{
		frame = new JFrame();
		container = new JPanel(new GridBagLayout());
		userPanel = new JPanel();
		imagePanel = new JPanel();
		
		userLabel = new JLabel("Player Username");
		
		skinImage = new JLabel();
		skinImage.setBounds(300, 150, 100, 100);
				
		userText = new JTextField();
		userText.addActionListener(this);
		
		container.setLayout(new GridLayout(1, 2));
		
		userPanel.setSize(300, 200);
		userPanel.setBorder(BorderFactory.createTitledBorder("Minecraft Skin Retriever"));
		userPanel.setLayout(null);
		
		userLabel.setBounds(10, 20, 100, 25);
		userLabel.setLocation(10, (int)userPanel.getSize().height / 2);
		
		userText.setBounds(130, 20, 125, 25);
		userText.setLocation((int)(userPanel.getSize().width / 2) - 20, (int)userPanel.getSize().height / 2);
		
		button = new JButton("Retrieve Skin");
		button.setBounds(200, 150, 120, 25);
		button.addActionListener(this);
		button.setLocation((int)userPanel.getSize().width / 2 - 50, (int)userPanel.getSize().height);
		
		userPanel.add(userLabel);
		userPanel.add(button);
		userPanel.add(userText);
		
		imagePanel.setBorder(BorderFactory.createTitledBorder("Skin & Download"));
		imagePanel.setSize(300, 200);
		imagePanel.setLayout(new GridLayout(4, 2));
				
		downloadSkin = new JButton("Download Skin");
		downloadSkin.addActionListener(this);
		
		imagePanel.add(new JLabel());
		imagePanel.add(skinImage);
		imagePanel.add(new JLabel());
		imagePanel.add(downloadSkin);

		container.add(userPanel);
		container.add(imagePanel);
		
		frame.setResizable(false);
		frame.setTitle("Andrew's MC Skin Retriver");
		frame.add(container, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setPreferredSize(new Dimension(frameSizeX, frameSizeY));
		frame.setMaximumSize(new Dimension(frameSizeX, frameSizeY));
		frame.setSize(frameSizeX, frameSizeY);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}	
	
	@Override
	public void actionPerformed(ActionEvent event)
	{	
		// get name from text
		String name = userText.getText();
		
		if (event.getActionCommand().equalsIgnoreCase("Download Skin"))
		{
			System.out.println("Boop!");
			
			if (skin == null)
			{
				// error
				System.out.println("Error");
				msg.sendErrorMessage("Please enter a valid user before trying to download.", frame);
				
				return;
			}
			
			ioUtil.saveSkin(skin, name);
			
			return;
		}
		
						
		if (name.length() == 0)
			return;
							
		System.out.println("Name: ~" + name + "~");
				
		// get skin
		try
		{
			skin = mc.getSkin(name, frame);
		}
		catch(NoSuchElementException e)
		{
			msg.sendErrorMessage("No such user exists.", frame);
		}
		
		if (skin == null)
			return;
		
		skinImage.setIcon(new ImageIcon(skin));		
	}
}
