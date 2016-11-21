import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsrMain2 {

	private static String defaultInputFileName = "/Users/victoroliveira/Downloads/MSR/tr_status_PASSED-tr_tests_failed_FALSE-1775873.csv";
	private static String defaultOutputFileName = "/Users/victoroliveira/Downloads/MSR/tr_status_PASSED-tr_tests_failed_FALSE-1775873-mined.csv";

	private final static String CSV_INPUT_SEPARATOR = ",";
	private static List<String> headersList;

	public static void main(String[] args) {

		String inputFile = defaultInputFileName;
		String outputFile = defaultOutputFileName;
		String inputCsvSeparator = CSV_INPUT_SEPARATOR;

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

		writeFile(inputFile, outputFile, inputCsvSeparator);
	}

	private static void writeFile(String inputFile, String outputFilePath, String inputCsvSeparator) {

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

								writeHeaderLine(inputCsvSeparator, bw, mapHeaderColumn, line);

							} else {

								writeValueLine(inputCsvSeparator, bw, mapHeaderColumn, line);

							}
						}

						System.out.println("Total of " + i + " lines");
					}
				}
			}

		} catch (

		IOException e) {

			e.printStackTrace();
		}
	}

	private static void writeValueLine(String inputCsvSeparator, BufferedWriter bw,
			Map<String, Integer> mapHeaderColumn, String line) throws IOException {

		String[] values = line.split(inputCsvSeparator);

		Map<String, String> keyValueMap = new HashMap<String, String>();

		for (Map.Entry<String, Integer> entry : mapHeaderColumn.entrySet()) {

			keyValueMap.put(entry.getKey(), values[entry.getValue()]);
		}

		double trTestDuration = 0;

		try {

			trTestDuration = Double.valueOf(keyValueMap.get("tr_testduration"));
		} catch (NumberFormatException e) {
		}

		double trDuration = Double.valueOf(keyValueMap.get("tr_duration"));

		double result = (trTestDuration / trDuration);

		System.out.println("tr_testduration/tr_duration = " + trTestDuration + "/" + trDuration + " = " + result);

		DecimalFormat df = new DecimalFormat("#.######");

		line += inputCsvSeparator + df.format(result);

		System.out.println(line);

		bw.write(line);
		bw.newLine();
	}

	private static void writeHeaderLine(String inputCsvSeparator, BufferedWriter bw,
			Map<String, Integer> mapHeaderColumn, String line) throws IOException {

		String[] headers;
		headers = line.split(inputCsvSeparator);

		headersList = Arrays.asList(headers);

		for (int column = 0; column < headersList.size(); column++) {

			String header = headersList.get(column);

			if (header.equalsIgnoreCase("tr_testduration") || header.equalsIgnoreCase("tr_duration")) {

				mapHeaderColumn.put(header, column);
			}
		}

		line += inputCsvSeparator + "tr_testduration/tr_duration";

		System.out.println(line.toString());

		bw.write(line.toString());
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
