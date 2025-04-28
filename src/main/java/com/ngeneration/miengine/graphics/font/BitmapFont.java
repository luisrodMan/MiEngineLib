package com.ngeneration.miengine.graphics.font;

import java.awt.Dimension;
import java.util.Map;

import com.ngeneration.miengine.graphics.SpriteBatch;
import com.ngeneration.miengine.graphics.Texture;

import lombok.Getter;

@Getter
public class BitmapFont {

	private Texture texture;
	private Map<Integer, Character> chars;
	private Metadata metadata;

	public BitmapFont(Metadata meta, Texture texture, Map<Integer, Character> characters) {
		this.metadata = meta;
		this.texture = texture;
		this.chars = characters;
	}

	public int getLineHeight() {
		return metadata.getLineHeight();
	}

	public int getStringWidth(String text, float size) {
		return getStringWidth(text, 0, text.length(), size);
	}

	public int getStringWidth(String text, int i, int length, float size) {
		float scale = size / metadata.getSize();
		int w = 0;
		Character cData = null;
		for (int j = 0; j < length; j++) {
			int code = (int) text.charAt(i + j);
			cData = chars.get(code);

			// tab size
			if (code == '\t') {
				w += chars.get((int) " ".charAt(0)).width * 4;
				continue;
			}
			if (code == '\r')
				continue;
			else if (code == '\n') {
				continue;
			}

			if (cData == null) {

				// generate new character
				System.out.println("unknown character: [" + (char) code + "]");
				cData = chars.get((int) '*');

			}

			w += cData.xAdvance * scale;
		}
		if (cData != null) {
			w -= (cData.xAdvance - cData.width - cData.xOffset) * scale;
//			w -= chars.get((int) text.charAt(0)).xOffset * scale;
		}
		return w;
	}

	public Dimension getStringBounds(String text, int size) {
		float scale = size / metadata.getSize();
		return new Dimension(getStringWidth(text, 0, text.length(), size), (int) (getLineHeight() * scale));
	}

	public void drawString(SpriteBatch g, float x, float y, String string, float size) {
		int length = string.length();
		float scale = size / metadata.getSize();
		int lineHeight = metadata.getLineHeight();
		y += metadata.getBase() * scale;
		for (int i = 0; i < length; i++) {
			int code = string.charAt(i);

			// tab size
			if (code == '\t') {
				x += chars.get((int) " ".charAt(0)).width * 4;
				continue;
			}

			var charDef = chars.get(code);
			if (charDef == null) {

				// generate new character xdxxdxdxdx
				System.out.println("unknown character: [" + (char) code + "]");
				charDef = chars.get((int) '*');
			}
			g.drawTexture(texture, x + charDef.xOffset * scale, y - (charDef.yOffset + charDef.height) * scale,
					charDef.x, texture.getHeight() - charDef.y - charDef.height, charDef.width, charDef.height,
					charDef.width * scale, charDef.height * scale);

			x += charDef.xAdvance * scale;
		}
	}

}
