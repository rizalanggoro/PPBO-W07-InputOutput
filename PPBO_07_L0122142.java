// Nama   : Rizal Dwi Anggoro
// NIM    : L0122142
// Github : https://github.com/rizalanggoro/PPBO-W07-InputOutput

/*
  File utama dari program Agenda yang bertugas untuk
  mengatur UI/tampilan menu yang berinteraksi langsung
  dengan pengguna.
 */

import java.util.List;
import java.util.Scanner;

public class PPBO_07_L0122142 {
  public static void main(String[] args) {
    // deklarasi scanner dan logic
    Scanner scanner = new Scanner(System.in);
    Logic logic = new LogicImpl();

    // menampilkan main menu
    while (true) {
      int option = printMainMenu(scanner, logic);

      // pengkondisian berdasarkan opsi yang dimasukkan
      // oleh user
      if (option == 1) createAgenda(scanner, logic);
      else if (option == 2) readAgenda(scanner, logic);
      else if (option == 3) deleteAgenda(scanner, logic);
      else if (option == 4) {
        System.out.println("\nKeluar program...");
        break;
      } else {
        System.out.println("  Error: Opsi yang Anda masukkan tidak valid!");
        Utils.Input.enterToContinue(scanner);
      }
    }

    scanner.close();
  }

  // fungsi yang menampilkan menu untuk membuat agenda baru
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

    // meminta user untuk memasukkan tanggal agenda
    System.out.println("\nMasukkan tanggal");
    String date;
    while (true) {
      date = Utils.Input.getString(scanner);
      if (logic.isValidDate(date)) break;
      else System.out.println("  Error: Format tanggal yang Anda masukkan tidak valid!");
    }

    // meminta user untuk memasukkan isi/konten agenda
    System.out.println("\nMasukkan konten");
    String content = Utils.Input.getString(scanner);

    // meminta user untuk memasukkan waktu agenda
    System.out.println("\nMasukkan waktu");
    String time;
    while (true) {
      time = Utils.Input.getString(scanner);
      if (logic.isValidTime(time)) break;
      else System.out.println("  Error: Format waktu yang Anda masukkan tidak valid!");
    }

    try {
      // membuat object agenda baru
      Agenda agenda = new Agenda(time, content);

      // validasi agenda berdasarkan tanggal
      if (logic.isAgendaExistsByDate(date)) {
        // jika sudah ada agenda dengan tanggal `date`,
        // maka lakukan update ke agenda yang sudah ada
        logic.updateAgenda(date, agenda);
        System.out.println("  Berhasil menambahkan agenda baru...");
      } else {
        // jika belum, maka buat sebuah agenda baru
        logic.createAgenda(date, agenda);
        System.out.println("  Berhasil membuat agenda baru...");
      }

      Utils.Input.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("  Error: %s\n", e.getMessage());
      Utils.Input.enterToContinue(scanner);
    }
  }

  // fungsi yang menampilkan menu untuk membaca agenda
  // berdasarkan tanggal
  private static void readAgenda(Scanner scanner, Logic logic) {
    System.out.println("\nLihat Agenda\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format tanggal berikut");
    System.out.println("  <hari>-<bulan>-<tahun>");
    System.out.println("  Contoh:");
    System.out.println("  * 12-12-2023");

    // meminta user untuk memasukkan tanggal agenda
    // yang akan dibaca
    System.out.println("\nMasukkan tanggal");
    String date;
    while (true) {
      date = Utils.Input.getString(scanner);
      if (logic.isValidDate(date)) break;
      else System.out.println("  Error: Format tanggal yang Anda masukkan tidak valid!");
    }

    try {
      // jika agenda tidak ditemukan
      if (!logic.isAgendaExistsByDate(date))
        throw new Exception("Agenda tidak ditemukan!");

      List<Agenda> agendas = logic.readAllAgendaByDate(date);

      // jika agenda ditemukan, tetapi tidak ada content
      if (agendas == null)
        throw new Exception("Konten agenda tidak ditemukan!");

      System.out.printf("\nHari/tanggal: %s\n", Utils.Format.getFormattedDate(date));
      System.out.printf("Anda memiliki %d agenda\n\n", agendas.size());

      // menampilkan isi agenda ke console
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

  // fungsi yang menampilkan menu untuk menghapus agenda
  // berdasarkan tanggal
  private static void deleteAgenda(Scanner scanner, Logic logic) {
    System.out.println("\nHapus Agenda\n");

    System.out.println("Petunjuk:");
    System.out.println("- Gunakan format tanggal berikut");
    System.out.println("  <hari>-<bulan>-<tahun>");
    System.out.println("  Contoh:");
    System.out.println("  * 12-12-2023");

    // meminta user untuk memasukkan tanggal
    System.out.println("\nMasukkan tanggal");
    String date;
    while (true) {
      date = Utils.Input.getString(scanner);
      if (logic.isValidDate(date)) break;
      else System.out.println("  Error: Format tanggal yang Anda masukkan tidak valid!");
    }

    try {
      final boolean result = logic.deleteAgendaByDate(date);

      // jika berhasil, tampilkan pesan sukses
      if (result)
        System.out.println("  Berhasil menghapus agenda...");

      Utils.Input.enterToContinue(scanner);
    } catch (Exception e) {
      System.out.printf("  Error: %s\n", e.getMessage());
      Utils.Input.enterToContinue(scanner);
    }
  }

  // fungsi untuk menampilkan daftar semua agenda
  // dalam bentuk tabel
  private static void printAllAgenda(Logic logic) {
    try {
      final List<String> dates = logic.readAllAgendaDate();

      // jika tidak ada, maka lempar sebuah exception
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

  // fungsi untuk menampilkan main menu, sekaligus
  // meminta user untuk memasukkan opsi menu
  private static int printMainMenu(Scanner scanner, Logic logic) {
    System.out.println("\nAgenda\n");

    printAllAgenda(logic);

    System.out.println("\nOpsi:");
    System.out.println("1. Tambah agenda");
    System.out.println("2. Lihat detail agenda");
    System.out.println("3. Hapus agenda");
    System.out.println("4. Keluar program");

    return Utils.Input.getInt(scanner);
  }
}
