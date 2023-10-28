import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface RepositoryFile {
  boolean isExists(String dir, String filename);

  void create(String dir, String filename, String data) throws Exception;

  String read(String dir, String filename) throws Exception;

  List<String> readAllFiles(String dir) throws Exception;

  void updateAppend(String dir, String filename, String data) throws Exception;

  boolean delete(String dir, String filename) throws Exception;
}

class RepositoryFileImpl implements RepositoryFile {
  @Override
  public boolean isExists(String dir, String filename) {
    File file = new File(dir.concat("/").concat(filename));
    return file.exists();
  }

  @Override
  public void create(String dir, String filename, String data) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    if (!file.getParentFile().isDirectory()) {
      if (!file.getParentFile().mkdirs())
        throw new Exception("Gagal membuat directory: " + dir);
    }

    if (!file.createNewFile())
      throw new Exception("Gagal membuat file: " + filename);

    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(data.concat("\n"));
    fileWriter.close();
  }

  @Override
  public String read(String dir, String filename) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    if (!file.exists())
      throw new Exception("File " + filename + " tidak ditemukan!");

    Scanner scanner = new Scanner(file);
    StringBuilder result = new StringBuilder();
    while (scanner.hasNextLine())
      result.append(scanner.nextLine());
    scanner.close();

    return result.toString();
  }

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

    return result;
  }

  @Override
  public void updateAppend(String dir, String filename, String data) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    if (!file.exists())
      throw new Exception("File " + filename + " tidak ditemukan!");

    FileWriter fileWriter = new FileWriter(file, true);
    fileWriter.write(data.concat("\n"));
    fileWriter.close();
  }

  @Override
  public boolean delete(String dir, String filename) throws Exception {
    File file = new File(dir.concat("/").concat(filename));

    if (!file.exists())
      throw new Exception("File " + filename + " tidak ditemukan!");

    return file.delete();
  }
}
