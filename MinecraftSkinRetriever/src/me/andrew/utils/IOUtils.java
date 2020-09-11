package me.andrew.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IOUtils 
{
	public void saveSkin(BufferedImage skin, String name)
	{
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
		fileChooser.setSelectedFile(new File(name + "Skin.png"));
				
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			try 
			{
				ImageIO.write(skin, "png", file);
				System.out.println("Did it work?" + skin.toString());
				
			}
			catch (IOException e) 
			{
				System.out.println("No file chosen!");
			}
		}
		
		return;
	}
}
