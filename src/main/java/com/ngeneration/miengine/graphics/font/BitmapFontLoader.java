package com.ngeneration.miengine.graphics.font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ngeneration.miengine.graphics.Texture;
import com.ngeneration.miengine.util.Util;

public class BitmapFontLoader {

	public static BitmapFont create(String texturePath, String fontDef)
			throws NumberFormatException, FileNotFoundException, IOException {
		var texture = new Texture(texturePath);
		int lineIdx = 0;
		String face = null;
		int size = 0;
		Metadata metadata = null;
		Map<Integer, Character> characters = new HashMap<>();
		for (String line : Util.readLines(new File(fontDef))) {
			String[] substrings = line.split("\\s+");
			if (lineIdx == 0) {
				int i = substrings[1].indexOf("\"") + 1;
				face = substrings[1].substring(i, substrings[1].indexOf("\"", i));
				size = Integer.parseInt(substrings[2].split("=")[1]);
			} else if (!line.isBlank()) {
				if (metadata == null) {
					metadata = new Metadata(face, size, Integer.parseInt(substrings[1].split("=")[1]),
							Integer.parseInt(substrings[2].split("=")[1]),
							Integer.parseInt(substrings[5].split("=")[1]));
				} else if (lineIdx > 3) {
					int id = Integer.parseInt(substrings[1].split("=")[1]);
					int x = Integer.parseInt(substrings[2].split("=")[1]);
					int y = Integer.parseInt(substrings[3].split("=")[1]);
					int width = Integer.parseInt(substrings[4].split("=")[1]);
					int height = Integer.parseInt(substrings[5].split("=")[1]);
					int xoffset = Integer.parseInt(substrings[6].split("=")[1]);
					int yoffset = Integer.parseInt(substrings[7].split("=")[1]);
					int xadvance = Integer.parseInt(substrings[8].split("=")[1]);
					characters.put(id, new Character(id, x, y, width, height, xoffset, yoffset, xadvance));
				}
			}
			lineIdx++;
		}
		return new BitmapFont(metadata, texture, characters);
	}

}
