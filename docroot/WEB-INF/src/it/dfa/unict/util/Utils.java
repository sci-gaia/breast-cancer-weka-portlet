package it.dfa.unict.util;

import it.dfa.unict.AppPreferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

public class Utils {

	public static AppPreferences getAppPreferences(String JSONAppPrefs) {
		AppPreferences appPreferences = new AppPreferences();

		if (JSONAppPrefs != null && !JSONAppPrefs.equals(""))
			appPreferences = JSONFactoryUtil.looseDeserialize(JSONAppPrefs,
					AppPreferences.class);

		return appPreferences;
	}

	/**
	 * This method takes as input a filename and will transfer its content
	 * inside a String variable
	 *
	 * @param file
	 *            A complete path to a given file
	 * @return File content into a String
	 * @throws IOException
	 */
	public static String file2String(String file) throws IOException {
		String line;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(Constants.NEW_LINE);
		}
		reader.close();
		return stringBuilder.toString();
	}

	/**
	 * This method will transfer the content of a given String into a given
	 * filename
	 *
	 * @param fileName
	 *            A complete path to a file to write
	 * @param fileContent
	 *            The string content of the file to write
	 * @throws IOException
	 */
	public static void string2File(String fileName, String fileContent)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write(fileContent);
		writer.close();
	}

	public static boolean validateFileName(String fileName) {
		return fileName.matches("^[^.\\\\/:*?\"<>|]?[^\\\\/:*?\"<>|]*")
				&& getValidFileName(fileName).length() > 0;
	}

	public static String getValidFileName(String fileName) {
		String newFileName = fileName.replaceAll(
				"^[.\\\\/:*?\"<>|]?[\\\\/:*?\"<>|]*", "");
		if (newFileName.length() == 0)
			throw new IllegalStateException("File Name " + fileName
					+ " results in a empty fileName!");
		return newFileName;
	}

	public static void mergeTasks(Object obj, Object update)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (!obj.getClass().isAssignableFrom(update.getClass())) {
			return;
		}

		Method[] methods = obj.getClass().getMethods();

		for (Method fromMethod : methods) {
			if (fromMethod.getDeclaringClass().equals(obj.getClass())
					&& fromMethod.getName().startsWith("get")) {

				String fromName = fromMethod.getName();
				String toName = fromName.replace("get", "set");

				Method toMetod = obj.getClass().getMethod(toName,
						fromMethod.getReturnType());
				Object value = fromMethod.invoke(update, (Object[]) null);
				if (value != null) {
					toMetod.invoke(obj, value);
				}
			}
		}
	}

}
