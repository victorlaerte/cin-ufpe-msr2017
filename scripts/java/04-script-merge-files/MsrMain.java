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

		mergeFiles();
	}

	private static void mergeFiles() {

		try (BufferedReader br = new BufferedReader(new FileReader(new File(file2Path)))) {

			try (FileOutputStream fos = new FileOutputStream(new File(file1Path), true)) {

				try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {

					try (BufferedWriter bw = new BufferedWriter(osw)) {

						int i = 0;

						for (String line; (line = br.readLine()) != null; i++) {

							if (i > 1) {

								bw.write(line);
								bw.newLine();
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
}
