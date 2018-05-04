package com.fileconverting.demo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

	public String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}
}
