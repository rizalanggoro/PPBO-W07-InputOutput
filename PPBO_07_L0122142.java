// Nama   : Rizal Dwi Anggoro
// NIM    : L0122142
// Github : https://github.com/rizalanggoro/PPBO-W07-InputOutput

import java.util.List;
import java.util.Scanner;

public class PPBO_07_L0122142 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Logic logic = new LogicImpl();

    while (true) {
      int option = printMainMenu(scanner, logic);

      if (option == 1) createAgenda(scanner, logic);
      else if (option == 2) readAgenda(scanner, logic);
      else if (option == 3) deleteAgenda(scanner, logic);
      else {
        System.out.println("\nKeluar program...");
        break;
      }
    }

    scanner.close();
  }

  private static void createAgenda(Scanner scanner, Logic logic) {
    System.out.println("\nBuat Agenda\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format tanggal berikut");
    System.out.println("  <hari>-<bulan>-<tahun>");
    System.out.println("  Contoh:");
    System.out.println("  * 12-12-2023");
    System.out.println("- Gunakan format waktu berikut");
    System.out.println("  <jam>.<menit>");
    System.out.println("  Contoh:");
    System.out.println("  * 12.12");

    System.out.println("\nMasukkan tanggal");
    String date;
    while (true) {
      date = Utils.Input.getString(scanner);
      if (logic.isValidDate(date)) break;
      else System.out.println("  Error: Format tanggal yang Anda masukkan tidak valid!");
    }

    System.out.println("\nMasukkan judul");
    String content = Utils.Input.getString(scanner);

    System.out.println("\nMasukkan waktu");
    String time;
    while (true) {
      time = Utils.Input.getString(scanner);
      if (logic.isValidTime(time)) break;
      else System.out.println("  Error: Format waktu yang Anda masukkan tidak valid!");
    }

    try {
      Agenda agenda = new Agenda(time, content);
      if (logic.isAgendaExistsByDate(date)) {
        logic.updateAgenda(date, agenda);
        System.out.println("  Berhasil menambahkan agenda baru...");
      } else {
        logic.createAgenda(date, agenda);
        System.out.println("  Berhasil membuat agenda baru...");
      }

      Utils.Input.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("  Error: %s\n", e.getMessage());
      Utils.Input.enterToContinue(scanner);
    }
  }

  private static void readAgenda(Scanner scanner, Logic logic) {
    System.out.println("\nLihat Agenda\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format tanggal berikut");
    System.out.println("  <hari>-<bulan>-<tahun>");
    System.out.println("  Contoh:");
    System.out.println("  * 12-12-2023");

    System.out.println("\nMasukkan tanggal");
    String date;
    while (true) {
      date = Utils.Input.getString(scanner);
      if (logic.isValidDate(date)) break;
      else System.out.println("  Error: Format tanggal yang Anda masukkan tidak valid!");
    }

    try {
      if (!logic.isAgendaExistsByDate(date))
        throw new Exception("Agenda tidak ditemukan!");

      List<Agenda> agendas = logic.readAllAgendaByDate(date);

      System.out.printf("\nHari/tanggal: %s\n", Utils.Format.getFormattedDate(date));
      System.out.printf("Anda memiliki %d agenda\n\n", agendas.size());

      Utils.Output.printLine("=", 64);
      System.out.printf(" %-3s | %-5s | %-48s \n", "No", "Waktu", "Agenda");
      Utils.Output.printLine("-", 64);

      int num = 1;
      for (Agenda agenda : agendas) {
        List<String> agendaTitles = Utils.Output.generateWrappedText(agenda.getContent(), 48);
        boolean isNumberPrinted = false;
        for (String title : agendaTitles) {
          if (isNumberPrinted) System.out.printf(" %3s | %5s | %-48s\n", "", "", title);
          else System.out.printf(" %3d | %-5s | %-48s\n", num, agenda.getTime(), title);

          isNumberPrinted = true;
        }
        num++;
      }

      Utils.Output.printLine("=", 64);

      Utils.Input.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("  Error: %s\n", e.getMessage());
      Utils.Input.enterToContinue(scanner);
    }
  }

  private static void deleteAgenda(Scanner scanner, Logic logic) {
    System.out.println("\nHapus Agenda\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format tanggal berikut");
    System.out.println("  <hari>-<bulan>-<tahun>");
    System.out.println("  Contoh:");
    System.out.println("  * 12-12-2023");

    System.out.println("\nMasukkan tanggal");
    String date;
    while (true) {
      date = Utils.Input.getString(scanner);
      if (logic.isValidDate(date)) break;
      else System.out.println("  Error: Format tanggal yang Anda masukkan tidak valid!");
    }

    try {
      final boolean result = logic.deleteAgendaByDate(date);
      if (result)
        System.out.println("  Berhasil menghapus agenda...");

      Utils.Input.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("  Error: %s\n", e.getMessage());
      Utils.Input.enterToContinue(scanner);
    }
  }

  private static void printAllAgendas(Logic logic) {
    try {
      final List<String> dates = logic.readAllAgendaDate();
      if (dates.isEmpty())
        throw new Exception();

      Utils.Output.printLine("=", 64);
      System.out.printf(" %-3s | %-56s \n", "No", "Hari/tanggal");
      Utils.Output.printLine("-", 64);

      int num = 1;
      for (String date : dates)
        System.out.printf(" %3d | %-56s \n", num++, Utils.Format.getFormattedDate(date));

      Utils.Output.printLine("=", 64);
    } catch (Exception e) {
      System.out.println("[Anda belum memiliki agenda]");
    }
  }

  private static int printMainMenu(Scanner scanner, Logic logic) {
    System.out.println("\nAgenda\n");

    printAllAgendas(logic);

    System.out.println("\nOpsi:");
    System.out.println("1. Tambah agenda");
    System.out.println("2. Lihat detail agenda");
    System.out.println("3. Hapus agenda");
    System.out.println("4. Keluar program");

    return Utils.Input.getInt(scanner);
  }
}
