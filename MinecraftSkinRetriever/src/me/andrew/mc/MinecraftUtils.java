package me.andrew.mc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.andrew.utils.MessageUtils;

public class MinecraftUtils 
{
	private MessageUtils msg = new MessageUtils();
	
	// Get the JSON response from the URL.
		public String getResponse(URLConnection con, JFrame frame)
		{
			Scanner scanner = null;
			try 
			{
				scanner = new Scanner(con.getInputStream());
			} 
			catch (IOException e) 
			{
				msg.sendErrorMessage("Error: User doesn't exist", frame);
				return null;
			}
			String response = scanner.useDelimiter("\\A").next();
			
			scanner.close();
			
			return response;
		}
		
		// Use the name to get the UUID.
		public String getMinecraftUUID(String name, JFrame frame)
		{
			URLConnection connection = null;
			String response = null;
			
			if (!(Character.isAlphabetic(name.charAt(0))))
			{
				msg.sendErrorMessage("Your first character is not alphanumeric (its a symbol).", frame);
				System.out.println("Spaces caught!");
				return null;
			}
			
			try 
			{
				connection = new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
				response = getResponse(connection, frame);
				
			}
			catch(IOException e)
			{
				msg.sendErrorMessage("Error: " + e.getMessage(), frame);
				return null;
			}
			
			Gson gson = new Gson();
			
			System.out.println("Response:" + response);
			
			// Convert response to JsonObject and print as String.
			JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
			
			String UUID = jsonObject.get("id").getAsString();
			System.out.println("JSON: " + UUID);
					
			
			return UUID;
			
		}
		
		// Decodes base64 value from response in API.
		public JsonObject getUUIDProfile(String uuid, JFrame frame)
		{			
			URLConnection connection = null;
			String response = null;
			try 
			{
				connection = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openConnection();
				response = getResponse(connection, frame);
				
			}
			catch(IOException e)
			{
				msg.sendErrorMessage("Error: " + e.getMessage(), frame);
				return null;
			}
			
			System.out.println("Response UUID: " + response);
			
			// Get JSON value
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
			
			JsonObject propertiesArray = jsonObject.getAsJsonArray("properties").get(0).getAsJsonObject();
			System.out.println("JSON Array: " + propertiesArray);
			
			String value = propertiesArray.get("value").getAsString();
			System.out.println("value: " + value);
			
			//decode value in base64.		
			byte [] decodedValue = Base64.getDecoder().decode(value);
			
			String decodedJson = new String(decodedValue);
			System.out.println("\nDecoded Json:" + decodedJson);
			
			// Return newly made JSON object.		
			return gson.fromJson(decodedJson, JsonObject.class);
		}
		
		// Get skin URL from JSON Object.
		public String getSkinURL(JsonObject input)
		{
			
			JsonObject texturesObj = input.getAsJsonObject("textures").getAsJsonObject("SKIN");
			
			String skinURL = texturesObj.get("url").getAsString();
			
			System.out.println("URL: " + skinURL);
			
			return skinURL;
		}
		
		public BufferedImage getSkin(String username, JFrame frame) 
		{
			String uuid = getMinecraftUUID(username, frame);
			JsonObject jsonProfile;
			String skinURL;
			
			if (uuid == null)
				return null;
			
			jsonProfile = getUUIDProfile(uuid, frame);
			skinURL = getSkinURL(jsonProfile);
			
			URL url;
			BufferedImage image = null;
			try 
			{
				url = new URL(skinURL);
				image = ImageIO.read(url);
			}
			catch (MalformedURLException e) 
			{
				msg.sendErrorMessage("Bad URL (wrong username)", frame);
			}
			catch (IOException e)
			{
				msg.sendErrorMessage("A player with that username doesn't exist.", frame);
			}
			
			return image;
		}
}
