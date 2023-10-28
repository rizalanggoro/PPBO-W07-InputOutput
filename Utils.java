/*
  File utilitas.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Utils {
  public static class Format {
    /*
      fungsi untuk mendapatkan date yang sudah terformat.
      - masukan berformat dd-MM-yyyy
      - keluaran berformat EEEE, dd MMMM yyyy
     */
    public static String getFormattedDate(String dateStr) {
      try {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateStr);
        return dateFormat.format(date);
      } catch (Exception e) {
        return dateStr;
      }
    }
  }

  public static class Input {
    // fungsi untuk mendapatkan masukan string dari user
    public static String getString(Scanner scanner) {
      System.out.print("> ");
      return scanner.nextLine();
    }

    // fungsi untuk mendapatkan masukan int dari user
    public static int getInt(Scanner scanner) {
      int result;
      while (true) {
        try {
          System.out.print("> ");
          result = scanner.nextInt();
          scanner.nextLine();
          break;
        } catch (Exception e) {
          // jika inputan tidak valid int, misal berupa huruf atau lainnya
          // user akan diminta untuk memasukkan ulang input hingga valid
          System.out.println("  Error: Masukkan yang Anda berikan tidak valid!");
          scanner.nextLine();
        }
      }
      return result;
    }

    // fungsi yang meminta user menekan enter untuk melanjutkan
    public static void enterToContinue(Scanner scanner) {
      System.out.println("\n$ Tekan <enter> untuk melanjutkan...");
      String a = scanner.nextLine();
    }
  }

  public static class Output {
    // fungsi untuk mencetak `ch` ke console sebanyak `num`
    public static void printLine(String ch, int num) {
      for (int a = 0; a < num; a++)
        System.out.print(ch);
      System.out.println();
    }

    // fungsi untuk membagi string menjadi beberapa bagian
    // dengan panjang <= max
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
