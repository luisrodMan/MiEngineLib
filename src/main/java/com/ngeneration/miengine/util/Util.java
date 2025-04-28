package com.ngeneration.miengine.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Util {

	public static String[] sliptToLines(String data) {
		return data.split("\r?\n");
	}

	public static String readText(File file) throws FileNotFoundException, IOException {
		return readText(new FileInputStream(file));
	}
	
	public static List<String> readLines(File file) throws FileNotFoundException, IOException {
		return readLines(new FileInputStream(file));
	}

	public static List<String> readLines(InputStream inputStream) throws IOException {
		BufferedInputStream stream = new BufferedInputStream(inputStream);
		List<String> lines = new LinkedList<>();
		var read = new InputStreamReader(stream);
		try (BufferedReader reader = new BufferedReader(read)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}

	public static String readText(InputStream inputStream) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedInputStream stream = new BufferedInputStream(inputStream);
		var read = new InputStreamReader(stream);
		try (BufferedReader reader = new BufferedReader(read)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!buffer.isEmpty())
					buffer.append(System.lineSeparator());
				buffer.append(line);
			}
		}
		return buffer.toString();
	}

	public static void write(File file, String string) throws IOException {
		try (var writer = new FileWriter(file)) {
			writer.write(string);
		}
	}

}
