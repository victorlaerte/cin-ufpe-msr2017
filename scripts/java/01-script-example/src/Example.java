
public class Example {

	public static void main(String[] args) {

		if (args != null) {

			for (String argument : args) {
				System.out.println("Variable or argument " + argument);
			}
		}

		System.out.println("Example Script Done");
	}
}
