import java.util.List;
import java.util.regex.Pattern;

public interface Logic {
  boolean isValidDate(String date);

  boolean isValidTime(String time);

  boolean isAgendaExistsByDate(String date);

  void createAgenda(String date, Agenda agenda) throws Exception;

  List<Agenda> readAllAgendaByDate(String date) throws Exception;

  List<String> readAllAgendaDate() throws Exception;

  void updateAgenda(String date, Agenda agenda) throws Exception;

  boolean deleteAgendaByDate(String date) throws Exception;
}

class LogicImpl implements Logic {
  private static final String DIRECTORY_AGENDA = "agenda";
  private static final String DELIMITER_AGENDA = "/delimiter/";
  private static final String DELIMITER_END_OF_AGENDA = "/delimiter_eoa/";
  private static final String EXTENSION_AGENDA = ".txt";

  private final RepositoryFile repositoryFile = new RepositoryFileImpl();

  @Override
  public boolean isValidDate(String date) {
    Pattern pattern = Pattern.compile(
        "^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(\\d\\d\\d\\d))$"
    );
    return pattern.matcher(date).matches();
  }

  @Override
  public boolean isValidTime(String time) {
    Pattern pattern = Pattern.compile(
        "^((0[0-9]|1[0-9]|2[0-3])\\.(0[0-9]|[1-5][0-9]))$"
    );
    return pattern.matcher(time).matches();
  }

  @Override
  public boolean isAgendaExistsByDate(String date) {
    final String filename = date.concat(EXTENSION_AGENDA);
    return repositoryFile.isExists(DIRECTORY_AGENDA, filename);
  }

  @Override
  public void createAgenda(String date, Agenda agenda) throws Exception {
    String filename = date.concat(EXTENSION_AGENDA);
    String data = agenda.toString(DELIMITER_AGENDA, DELIMITER_END_OF_AGENDA);

    this.repositoryFile.create(DIRECTORY_AGENDA, filename, data);
  }

  @Override
  public List<Agenda> readAllAgendaByDate(String date) throws Exception {
    String filename = date.concat(EXTENSION_AGENDA);
    String data = repositoryFile.read(DIRECTORY_AGENDA, filename);
    if (!data.trim().isEmpty())
      return Agenda.fromString(data, DELIMITER_AGENDA, DELIMITER_END_OF_AGENDA);
    return null;
  }

  @Override
  public List<String> readAllAgendaDate() throws Exception {
    return this.repositoryFile.readAllFiles(DIRECTORY_AGENDA);
  }

  @Override
  public void updateAgenda(String date, Agenda agenda) throws Exception {
    String filename = date.concat(EXTENSION_AGENDA);
    String data = agenda.toString(DELIMITER_AGENDA, DELIMITER_END_OF_AGENDA);

    this.repositoryFile.updateAppend(DIRECTORY_AGENDA, filename, data);
  }

  @Override
  public boolean deleteAgendaByDate(String date) throws Exception {
    final String filename = date.concat(EXTENSION_AGENDA);
    return repositoryFile.delete(DIRECTORY_AGENDA, filename);
  }
}
