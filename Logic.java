/*
  File logika dari program Agenda.
  Terdapat dua class, yaitu interface dan implementation.
 */

import java.util.List;
import java.util.regex.Pattern;

// interface Logic
public interface Logic {
  // utils
  boolean isValidDate(String date);

  boolean isValidTime(String time);

  boolean isAgendaExistsByDate(String date);

  // create
  void createAgenda(String date, Agenda agenda) throws Exception;

  // read
  List<Agenda> readAllAgendaByDate(String date) throws Exception;

  List<String> readAllAgendaDate() throws Exception;

  // update
  void updateAgenda(String date, Agenda agenda) throws Exception;

  // delete
  boolean deleteAgendaByDate(String date) throws Exception;
}

// class implementation dari interface Logic
class LogicImpl implements Logic {
  // constants
  private static final String DIRECTORY_AGENDA = "agenda";
  private static final String DELIMITER_AGENDA = "/delimiter/";
  private static final String DELIMITER_END_OF_AGENDA = "/delimiter_eoa/";
  private static final String EXTENSION_AGENDA = ".txt";

  // deklarasi repository file
  private final RepositoryFile repositoryFile = new RepositoryFileImpl();

  // implementations

  // fungsi untuk memvalidasi tanggal
  @Override
  public boolean isValidDate(String date) {
    Pattern pattern = Pattern.compile(
        "^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(\\d\\d\\d\\d))$"
    );
    return pattern.matcher(date).matches();
  }

  // fungsi untuk memvalidasi waktu
  @Override
  public boolean isValidTime(String time) {
    Pattern pattern = Pattern.compile(
        "^((0[0-9]|1[0-9]|2[0-3])\\.(0[0-9]|[1-5][0-9]))$"
    );
    return pattern.matcher(time).matches();
  }

  // fungsi untuk mengecek, apakah sudah terdapat agenda dengan
  // tanggal `date`
  @Override
  public boolean isAgendaExistsByDate(String date) {
    final String filename = date.concat(EXTENSION_AGENDA);
    return repositoryFile.isExists(DIRECTORY_AGENDA, filename);
  }

  // fungsi untuk membuat agenda baru
  @Override
  public void createAgenda(String date, Agenda agenda) throws Exception {
    String filename = date.concat(EXTENSION_AGENDA);
    String data = agenda.toString(DELIMITER_AGENDA, DELIMITER_END_OF_AGENDA);

    this.repositoryFile.create(DIRECTORY_AGENDA, filename, data);
  }

  // fungsi untuk membaca seluruh agenda yang terdapat pada
  // file `date.txt`
  @Override
  public List<Agenda> readAllAgendaByDate(String date) throws Exception {
    String filename = date.concat(EXTENSION_AGENDA);
    String data = repositoryFile.read(DIRECTORY_AGENDA, filename);
    if (!data.trim().isEmpty())
      return Agenda.fromString(data, DELIMITER_AGENDA, DELIMITER_END_OF_AGENDA);
    return null;
  }

  // fungsi untuk membaca semua tanggal agenda yang ada
  @Override
  public List<String> readAllAgendaDate() throws Exception {
    return this.repositoryFile.readAllFiles(DIRECTORY_AGENDA);
  }

  // fungsi untuk meng-update / menambahkan agenda
  // ke agenda yang sudah ada
  @Override
  public void updateAgenda(String date, Agenda agenda) throws Exception {
    String filename = date.concat(EXTENSION_AGENDA);
    String data = agenda.toString(DELIMITER_AGENDA, DELIMITER_END_OF_AGENDA);

    this.repositoryFile.updateAppend(DIRECTORY_AGENDA, filename, data);
  }

  // fungsi untuk menghapus agenda
  @Override
  public boolean deleteAgendaByDate(String date) throws Exception {
    final String filename = date.concat(EXTENSION_AGENDA);
    return repositoryFile.delete(DIRECTORY_AGENDA, filename);
  }
}
