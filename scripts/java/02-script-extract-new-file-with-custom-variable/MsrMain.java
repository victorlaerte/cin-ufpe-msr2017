import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsrMain {

	private static String defaultInputFileName = "/Users/victoroliveira/Downloads/MSR/travistorrent-5-3-2016.csv";
	private static String defaultOutputFileName = "/Users/victoroliveira/Downloads/MSR/travistorrent-mined.csv";
	private static List<String> defaultDesiredHeaders = Arrays.asList(new String[] { "row", "gh_project_name",
			"tr_build_number", "tr_status", "tr_tests_failed", "tr_duration", "tr_testduration",
			"gh_test_lines_per_kloc", "gh_test_cases_per_kloc", "gh_tests_added", "gh_test_churn", "gh_src_churn" });

	public static void main(String[] args) {

		String inputFile = defaultInputFileName;
		String outputFile = defaultOutputFileName;
		List<String> desiredHeaders = defaultDesiredHeaders;

		if (args != null) {

			List<String> desiredHeadersFromArgs = new ArrayList<String>();

			for (int i = 0; i < args.length; i++) {

				String currentString = args[0];

				if (i == 0 && currentString != null) {

					inputFile = currentString;
				}

				if (i == 1 && currentString != null) {

					outputFile = currentString;
				}

				if (i > 1) {

					desiredHeadersFromArgs.add(currentString);
				}
			}

			if (!desiredHeadersFromArgs.isEmpty()) {

				desiredHeaders = desiredHeadersFromArgs;
			}
		}

		writeFile(inputFile, outputFile, desiredHeaders);
	}

	private static void writeFile(String inputFile, String outputFilePath, List<String> desiredHeaders) {

		System.out.println("Input Path: " + inputFile);
		System.out.println("Output Path: " + outputFilePath);

		try (BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)))) {

			File outputFile = getOutputFile(outputFilePath);

			try (FileOutputStream fos = new FileOutputStream(outputFile)) {

				try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {

					try (BufferedWriter bw = new BufferedWriter(osw)) {

						String[] headers = null;
						Map<String, Integer> mapHeaderColumn = new HashMap<String, Integer>();

						int i = 0;
						long totalBuildsInConditional = 0;

						for (String line; (line = br.readLine()) != null; i++) {

							StringBuilder newLine = new StringBuilder();

							if (i == 0) {

								headers = line.split(",");

								for (int column = 0; column < headers.length; column++) {

									String header = headers[column];
									header = header.replace("\"", "");

									if (desiredHeaders.contains(header)) {
										mapHeaderColumn.put(header, column);

										newLine.append(header);
										newLine.append(",");
									}
								}

								newLine.deleteCharAt(newLine.length() - 1);

								System.out.println(newLine.toString());

								bw.write(newLine.toString());
								bw.newLine();

							} else {

								String[] values = line.split(",");
								StringBuffer conditionalLine = new StringBuffer();

								boolean condition1 = false;
								boolean condition2 = false;

								for (String header : desiredHeaders) {

									int column = mapHeaderColumn.get(header);

									String desiredValue = values[column];

									if (header.equals("tr_status")) {

										desiredValue = desiredValue.replace("\"", "");

										if (desiredValue.equalsIgnoreCase("passed")) {
											condition1 = true;
										}
									}

									if (header.equals("tr_tests_failed")) {

										desiredValue = desiredValue.replace("\"", "");

										if (Boolean.valueOf(desiredValue) == true) {
											condition2 = true;
										}
									}

									conditionalLine.append(desiredValue);
									conditionalLine.append(",");
								}

								conditionalLine.deleteCharAt(conditionalLine.length() - 1);

								if (condition1 && condition2) {

									newLine.append(conditionalLine.toString());

									totalBuildsInConditional++;
									System.out.println(newLine.toString());

									bw.write(newLine.toString());
									bw.newLine();
								}
							}
						}

						System.out.println("Total of " + totalBuildsInConditional + " found");
					}
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private static File getOutputFile(String outputFilePath) throws IOException {

		if (!outputFilePath.endsWith(".csv")) {
			outputFilePath = outputFilePath + ".csv";
		}

		File file = new File(outputFilePath);

		File parentDir = file.getParentFile();

		parentDir.mkdirs();

		file.createNewFile();

		return file;
	}
}
