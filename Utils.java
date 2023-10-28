import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
  public static class Format {
    public static String getFilenameTxtFromDate(String date) {
      return date.replace("/", "-").concat(".txt");
    }
  }

  public static class Input {
    public static String getString(Scanner scanner) {
      System.out.print("> ");
      return scanner.nextLine();
    }

    public static int getInt(Scanner scanner) {
      System.out.print("> ");
      int input = scanner.nextInt();
      scanner.nextLine();
      return input;
    }

    public static void enterToContinue(Scanner scanner) {
      System.out.println("\n$ Tekan <enter> untuk melanjutkan...");
      String a = scanner.nextLine();
    }
  }

  public static class Output {
    public static void printLine(String ch, int num) {
      for (int a = 0; a < num; a++)
        System.out.print(ch);
      System.out.println();
    }

    public static List<String> generateWrappedText(String text, int max) {
      final ArrayList<String> result = new ArrayList<>();
      while (!text.isEmpty()) {
        String sub;
        if (text.length() > max) {
          sub = text.substring(0, max);
          text = text.substring(max);
        } else {
          sub = text;
          text = "";
        }
        result.add(sub);
      }
      return result;
    }
  }
}
