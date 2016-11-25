import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsrMain {

	// private static final String DEFAULT_INPUT_FILE_NAME =
	// "/Users/victoroliveira/Downloads/MSR/tr_status_PASSED-tr_tests_failed_TRUE-60947.csv";
	// private static final String DEFAULT_OUTPUT_FILE_NAME =
	// "/Users/victoroliveira/Downloads/MSR/tr_status_PASSED-tr_tests_failed_TRUE-60947-mined-1.csv";
	private static final String DEFAULT_INPUT_FILE_NAME = "/Users/victoroliveira/Downloads/MSR/tr_status_FAILED-tr_tests_failed_TRUE-103350.csv";
	private static final String DEFAULT_OUTPUT_FILE_NAME = "/Users/victoroliveira/Downloads/MSR/tr_status_FAILED-tr_tests_failed_TRUE-103350-mined-1.csv";
	private static final double PERCENT_LIMIT = 0.01;
	private static final String NEW_HEADER_LINE = "row,response_variable,time_spent_in_tests_" + (PERCENT_LIMIT * 10)
			+ "_of_total,gh_test_lines_per_kloc,gh_test_cases_per_kloc,gh_test_churn_" + (PERCENT_LIMIT * 10)
			+ "_of_total,gh_tests_added";

	private static List<String> defaultDesiredHeaders = Arrays.asList(new String[] { "row", "tr_status",
			"tr_tests_failed", "tr_duration", "tr_testduration", "gh_test_lines_per_kloc", "gh_test_cases_per_kloc",
			"gh_tests_added", "gh_test_churn", "gh_src_churn" });

	private final static String CSV_INPUT_SEPARATOR = ",";
	private static List<String> headersList;

	public static void main(String[] args) {

		String inputFile = DEFAULT_INPUT_FILE_NAME;
		String outputFile = DEFAULT_OUTPUT_FILE_NAME;

		if (args != null) {

			for (int i = 0; i < args.length; i++) {

				String currentArg = args[i];

				if (i == 0 && currentArg != null) {
					inputFile = currentArg;
				}
				if (i == 1 && currentArg != null) {
					outputFile = currentArg;
				}
			}

		}

		writeFile(inputFile, outputFile);
	}

	private static void writeFile(String inputFile, String outputFilePath) {

		System.out.println("Input Path: " + inputFile);
		System.out.println("Output Path: " + outputFilePath);

		try (BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)))) {

			File outputFile = getOutputFile(outputFilePath);

			try (FileOutputStream fos = new FileOutputStream(outputFile)) {

				try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {

					try (BufferedWriter bw = new BufferedWriter(osw)) {

						Map<String, Integer> mapHeaderColumn = new HashMap<String, Integer>();

						int i = 0;

						for (String line; (line = br.readLine()) != null; i++) {

							if (i == 0) {

								writeHeaderLine(bw, mapHeaderColumn, line);

							} else {

								writeValueLine(bw, mapHeaderColumn, line);

							}
						}

						System.out.println("Total of " + i + " lines");
					}
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private static void writeValueLine(BufferedWriter bw, Map<String, Integer> mapHeaderColumn, String line)
			throws IOException {

		String[] values = line.split(CSV_INPUT_SEPARATOR);

		Map<String, String> keyValueMap = new HashMap<String, String>();

		for (Map.Entry<String, Integer> entry : mapHeaderColumn.entrySet()) {

			keyValueMap.put(entry.getKey(), values[entry.getValue()]);
		}

		String newLine;

		try {
			newLine = getNewLine(keyValueMap);

			System.out.println(newLine);

			bw.write(newLine);
			bw.newLine();

		} catch (Exception e) {

			System.out.println("LINE ERROR " + line);
			e.printStackTrace();
		}

	}

	private static String getNewLine(Map<String, String> keyValueMap) throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(keyValueMap.get("row"));
		sb.append(CSV_INPUT_SEPARATOR);
		sb.append(getResponseVariable(keyValueMap));
		sb.append(CSV_INPUT_SEPARATOR);
		sb.append(getTime_spent_in_tests_X_percent_of_total(keyValueMap));
		sb.append(CSV_INPUT_SEPARATOR);
		sb.append(keyValueMap.get("gh_test_lines_per_kloc"));
		sb.append(CSV_INPUT_SEPARATOR);
		sb.append(keyValueMap.get("gh_test_cases_per_kloc"));
		sb.append(CSV_INPUT_SEPARATOR);
		sb.append(getGh_test_churn_X_percent_of_total(keyValueMap));
		sb.append(CSV_INPUT_SEPARATOR);
		sb.append(keyValueMap.get("gh_tests_added"));

		return sb.toString();
	}

	private static int getResponseVariable(Map<String, String> keyValueMap) throws Exception {

		StringBuilder sb = new StringBuilder();
		String buildStatus = keyValueMap.get("tr_status");
		String testStatus = keyValueMap.get("tr_tests_failed");

		if (buildStatus.equalsIgnoreCase("passed")) {
			sb.append("P");
		} else if (buildStatus.equalsIgnoreCase("failed")) {
			sb.append("F");
		} else {
			throw new Exception("We don't want this line");
		}

		sb.append("T");

		if (Boolean.valueOf(testStatus) == false) {
			sb.append("F");
		} else {
			throw new Exception("We don't want this line");
		}

		// possible results: BPTF, BFTF
		if (sb.toString().equals("BPTF")) {
			return 1;
		} else if (sb.toString().equals("BFTF")) {
			return 0;
		} else {
			throw new Exception("No such acceptable response variable");
		}
	}

	private static int getGh_test_churn_X_percent_of_total(Map<String, String> keyValueMap) {

		double ghTestChurn = 0;

		try {

			ghTestChurn = Double.valueOf(keyValueMap.get("gh_test_churn"));
		} catch (NumberFormatException e) {
		}

		double ghProductionChurn = Double.valueOf(keyValueMap.get("gh_src_churn"));

		double result = (ghTestChurn / (ghTestChurn + ghProductionChurn));

		if (result > PERCENT_LIMIT) {

			return 1;
		} else {
			return 0;
		}
	}

	private static int getTime_spent_in_tests_X_percent_of_total(Map<String, String> keyValueMap) {

		double trTestDuration = 0;

		try {

			trTestDuration = Double.valueOf(keyValueMap.get("tr_testduration"));
		} catch (NumberFormatException e) {
		}

		double trDuration = Double.valueOf(keyValueMap.get("tr_duration"));

		double result = (trTestDuration / trDuration);

		if (result > PERCENT_LIMIT) {
			return 1;
		} else {
			return 0;
		}
	}

	private static void writeHeaderLine(BufferedWriter bw, Map<String, Integer> mapHeaderColumn, String line)
			throws IOException {

		String[] headers;
		headers = line.split(CSV_INPUT_SEPARATOR);

		headersList = Arrays.asList(headers);

		for (int column = 0; column < headersList.size(); column++) {

			String header = headersList.get(column);

			if (defaultDesiredHeaders.contains(header)) {

				mapHeaderColumn.put(header, column);
			}
		}

		System.out.println(NEW_HEADER_LINE);

		bw.write(NEW_HEADER_LINE);
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
