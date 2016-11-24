import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MsrMain {

	private static String file1Path = "/Users/victoroliveira/Downloads/MSR/tr_status_PASSED-tr_tests_failed_TRUE-60947-mined-copy.csv";
	private static String file2Path = "/Users/victoroliveira/Downloads/MSR/tr_status_FAILED-tr_tests_failed_TRUE-103350-mined.csv";

	public static void main(String[] args) {

		String path1 = file1Path;
		String path2 = file2Path;

		if (args != null) {

			for (int i = 0; i < args.length; i++) {

				String currentArg = args[i];

				if (i == 0 && currentArg != null) {
					path1 = currentArg;
				}
				if (i == 1 && currentArg != null) {
					path2 = currentArg;
				}
			}

		}

		appendFiles(path1, path2);
	}

	private static void appendFiles(String path1, String path2) {

		try (BufferedReader br = new BufferedReader(new FileReader(new File(path2)))) {

			try (FileOutputStream fos = new FileOutputStream(new File(path2), true)) {

				try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {

					try (BufferedWriter bw = new BufferedWriter(osw)) {

						int i = 0;

						for (String line; (line = br.readLine()) != null; i++) {

							if (i > 1) {

								bw.write(line);
								bw.newLine();
							}
						}
					}
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
