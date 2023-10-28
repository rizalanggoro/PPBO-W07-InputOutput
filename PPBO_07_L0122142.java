import java.util.List;
import java.util.Scanner;

public class PPBO_07_L0122142 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Logic logic = new LogicImpl();

    while (true) {
      int option = printMainMenu(scanner, logic);

      if (option == 1) createAgenda(scanner, logic);
      else if (option == 2) showAgenda(scanner, logic);
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
    String title = Utils.Input.getString(scanner);

    System.out.println("\nMasukkan waktu");
    String time;
    while (true) {
      time = Utils.Input.getString(scanner);
      if (logic.isValidTime(time)) break;
      else System.out.println("  Error: Format waktu yang Anda masukkan tidak valid!");
    }

    try {
      Agenda agenda = new Agenda(title, date, time);
      if (logic.isAgendaExistsByDate(date)) {
        logic.updateAgenda(agenda);
        System.out.println("  Berhasil menambahkan agenda baru...");
      } else {
        logic.createAgenda(new Agenda(title, date, time));
        System.out.println("  Berhasil membuat agenda baru...");
      }

      Utils.Input.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("  Error: %s\n", e.getMessage());
    }
  }

  private static void showAgenda(Scanner scanner, Logic logic) {
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

      System.out.printf("\nTanggal: %s\n", date);
      System.out.printf("Anda memiliki %d agenda\n\n", agendas.size());

      Utils.Output.printLine("=", 64);
      System.out.printf(" %-3s | %-5s | %-48s \n", "No", "Waktu", "Agenda");
      Utils.Output.printLine("-", 64);

      int num = 1;
      for (Agenda agenda : agendas) {
        List<String> agendaTitles = Utils.Output.generateWrappedText(agenda.getTitle(), 48);
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
    }
  }

  private static void printAllAgendas(Logic logic) {
    try {
      final List<String> agendas = logic.readAllDate();
      if (agendas.isEmpty())
        throw new Exception();

      Utils.Output.printLine("=", 32);
      System.out.printf(" %-3s | %-24s \n", "No", "Tanggal");
      Utils.Output.printLine("-", 32);

      int num = 1;
      for (String agenda : agendas)
        System.out.printf(" %3d | %-24s \n", num++, agenda);

      Utils.Output.printLine("=", 32);
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
