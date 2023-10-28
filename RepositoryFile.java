/*
  File repository file.
  Berguna untuk create, read, update, and delete.
  Terdiri dari interface dan implementation.
 */

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// interface RepositoryFile
public interface RepositoryFile {
  // utils
  boolean isExists(String dir, String filename);

  // create
  void create(String dir, String filename, String data) throws Exception;

  // read
  String read(String dir, String filename) throws Exception;

  List<String> readAllFiles(String dir) throws Exception;

  // update
  void updateAppend(String dir, String filename, String data) throws Exception;

  // delete
  boolean delete(String dir, String filename) throws Exception;
}

// class implementation dari interface RepositoryFile
class RepositoryFileImpl implements RepositoryFile {
  // fungsi untuk mengecek apakah sudah terdapat
  // file dengan nama `filename` pada directory
  // `dir`
  @Override
  public boolean isExists(String dir, String filename) {
    File file = new File(dir.concat("/").concat(filename));
    return file.exists();
  }

  // fungsi untuk membuat sebuah file baru
  @Override
  public void create(String dir, String filename, String data) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    // jika folder belum ada, maka buat folder `dir` terlebih dahulu
    if (!file.getParentFile().isDirectory()) {
      if (!file.getParentFile().mkdirs())
        throw new Exception("Gagal membuat directory: " + dir);
    }

    // jika file belum ada, maka buat file terlebih dahulu
    if (!file.createNewFile())
      throw new Exception("Gagal membuat file: " + filename);

    // menulis file yang telah dibuat
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(data.concat("\n"));
    fileWriter.close();
  }

  // fungsi untuk membaca file `filename` pada directory `dir`
  @Override
  public String read(String dir, String filename) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    // jika file tidak ditemukan, lempar exception
    if (!file.exists())
      throw new Exception("File " + filename + " tidak ditemukan!");

    // jika file ditemukan, baca setiap baris file
    // menggunakan scanner
    Scanner scanner = new Scanner(file);
    StringBuilder result = new StringBuilder();
    while (scanner.hasNextLine())
      result.append(scanner.nextLine());
    scanner.close();

    // mengembalikan file yang telah dibaca
    // dalam bentuk string
    return result.toString();
  }

  // fungsi untuk membaca semua file yang ada pada directory `dir`
  @Override
  public List<String> readAllFiles(String dir) throws Exception {
    File file = new File(dir);
    if (!file.isDirectory())
      throw new Exception(dir + " bukan merupakan directory!");

    ArrayList<String> result = new ArrayList<>();
    File[] files = file.listFiles();

    if (files != null) {
      for (File file1 : files) {
        if (file1.isFile())
          result.add(file1.getName().substring(
              0, file1.getName().lastIndexOf(".")
          ));
      }
    }

    // mengembalikan semua daftar file yang ada pada
    // directory `dir`, hanya nama dan tanpa
    // ektensi dalam bentuk array
    return result;
  }

  // fungsi untuk menulis file, dalam mode append
  @Override
  public void updateAppend(String dir, String filename, String data) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    if (!file.exists())
      throw new Exception("File " + filename + " tidak ditemukan!");

    // menulis file yang telah ada dalam mode append
    // atau menambahkan pada bagian akhir file
    FileWriter fileWriter = new FileWriter(file, true);
    fileWriter.write(data.concat("\n"));
    fileWriter.close();
  }

  // fungsi untuk menghapus file
  @Override
  public boolean delete(String dir, String filename) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    if (!file.exists())
      throw new Exception("File " + filename + " tidak ditemukan!");

    return file.delete();
  }
}
