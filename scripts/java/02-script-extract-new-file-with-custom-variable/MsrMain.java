import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsrMain {

	private static String defaultInputFileName = "/Users/victoroliveira/Downloads/MSR/travistorrent_27_10_2016.csv";
	private static String defaultOutputFileName = "/Users/victoroliveira/Downloads/MSR/travistorrent-mined.csv";

	private static List<String> defaultDesiredHeaders = Arrays.asList(new String[] { "row", "gh_project_name",
			"tr_build_number", "tr_status", "tr_tests_failed", "tr_duration", "tr_testduration",
			"gh_test_lines_per_kloc", "gh_test_cases_per_kloc", "gh_tests_added", "gh_test_churn", "gh_src_churn" });

	private final static String CSV_INPUT_SEPARATOR = ",";
	private final static String CSV_OUTPUT_SEPARATOR = ";";

	public static void main(String[] args) {

		String inputFile = defaultInputFileName;
		String outputFile = defaultOutputFileName;
		List<String> desiredHeaders = defaultDesiredHeaders;
		String inputCsvSeparator = CSV_INPUT_SEPARATOR;
		String outputCsvSeparator = CSV_OUTPUT_SEPARATOR;

		if (args != null) {

			List<String> desiredHeadersFromArgs = new ArrayList<String>();

			for (int i = 0; i < args.length; i++) {

				String currentArg = args[i];

				if (i == 0 && currentArg != null) {
					inputFile = currentArg;
				}
				if (i == 1 && currentArg != null) {
					outputFile = currentArg;
				}
				if (i == 2 && currentArg != null) {
					inputCsvSeparator = currentArg;
				}
				if (i == 3 && currentArg != null) {
					outputCsvSeparator = currentArg;
				}

				if (i > 3) {

					desiredHeadersFromArgs.add(currentArg);
				}
			}

			if (!desiredHeadersFromArgs.isEmpty()) {

				desiredHeaders = desiredHeadersFromArgs;
			}
		}

		writeFile(inputFile, outputFile, inputCsvSeparator, outputCsvSeparator, desiredHeaders);
	}

	private static void writeFile(String inputFile, String outputFilePath, String inputCsvSeparator,
			String outputCsvSeparator, List<String> desiredHeaders) {

		System.out.println("Input Path: " + inputFile);
		System.out.println("Output Path: " + outputFilePath);

		try (BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)))) {

			File outputFile = getOutputFile(outputFilePath);

			try (FileOutputStream fos = new FileOutputStream(outputFile)) {

				try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {

					try (BufferedWriter bw = new BufferedWriter(osw)) {

						Map<String, Integer> mapHeaderColumn = new HashMap<String, Integer>();

						int i = 0;
						long totalBuildsInConditional = 0;

						for (String line; (line = br.readLine()) != null; i++) {

							StringBuilder newLineSb = new StringBuilder();

							if (i == 0) {

								writeHeaderLine(inputCsvSeparator, outputCsvSeparator, desiredHeaders, bw,
										mapHeaderColumn, line, newLineSb);

							} else {

								totalBuildsInConditional = writeValueLine(inputCsvSeparator, outputCsvSeparator,
										desiredHeaders, bw, mapHeaderColumn, totalBuildsInConditional, line, newLineSb);
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

	private static long writeValueLine(String inputCsvSeparator, String outputCsvSeparator, List<String> desiredHeaders,
			BufferedWriter bw, Map<String, Integer> mapHeaderColumn, long totalBuildsInConditional, String line,
			StringBuilder newLineSb) throws IOException {

		int firstBracket = line.indexOf('[');
		String originalContentOfBrackets = line.substring(firstBracket + 1, line.indexOf(']', firstBracket));

		String workaroundContentOfBrackets = originalContentOfBrackets.replace(",", "$$");

		line = line.replace(originalContentOfBrackets, workaroundContentOfBrackets);

		String[] values = line.split(inputCsvSeparator);
		StringBuffer conditionalLine = new StringBuffer();

		boolean condition1 = false;
		boolean condition2 = false;

		for (String header : desiredHeaders) {

			int column = mapHeaderColumn.get(header);

			String desiredValue = values[column];

			desiredValue = desiredValue.replace("\"", "");

			if (header.equals("tr_status")) {

				if (desiredValue.equalsIgnoreCase("passed")) {
					condition1 = true;
				}
			}

			if (header.equals("tr_tests_failed")) {

				if (Boolean.valueOf(desiredValue) == true) {
					condition2 = true;
				}
			}

			conditionalLine.append(desiredValue);
			conditionalLine.append(outputCsvSeparator);
		}

		conditionalLine.deleteCharAt(conditionalLine.length() - 1);

		if (condition1 && condition2) {

			newLineSb.append(conditionalLine.toString());

			String newLine = conditionalLine.toString().replace(workaroundContentOfBrackets, originalContentOfBrackets);

			totalBuildsInConditional++;
			System.out.println(newLine);

			bw.write(newLine);
			bw.newLine();
		}

		return totalBuildsInConditional;
	}

	private static void writeHeaderLine(String inputCsvSeparator, String outputCsvSeparator,
			List<String> desiredHeaders, BufferedWriter bw, Map<String, Integer> mapHeaderColumn, String line,
			StringBuilder newLineSb) throws IOException {
		String[] headers;
		headers = line.split(inputCsvSeparator);

		List<String> headersList = Arrays.asList(headers);

		Collections.sort(desiredHeaders, Comparator.comparing(item -> headersList.indexOf(item)));

		for (int column = 0; column < headers.length; column++) {

			String header = headers[column];
			header = header.replace("\"", "");

			if (desiredHeaders.contains(header)) {
				mapHeaderColumn.put(header, column);
			}
		}

		for (String header : desiredHeaders) {
			newLineSb.append(header);
			newLineSb.append(outputCsvSeparator);
		}

		newLineSb.deleteCharAt(newLineSb.length() - 1);

		System.out.println(newLineSb.toString());

		bw.write(newLineSb.toString());
		bw.newLine();
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
