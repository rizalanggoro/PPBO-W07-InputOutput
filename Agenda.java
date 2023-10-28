import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Agenda {
  private final String time;
  private final String content;

  public Agenda(
      String time,
      String content
  ) {
    this.content = content;
    this.time = time;
  }

  public static List<Agenda> fromString(String string, String delimiter, String delimiterEOA) {
    String[] agendas = string.split(delimiterEOA);
    final ArrayList<Agenda> result = new ArrayList<>();

    for (String agenda : agendas) {
      String[] data = agenda.split(delimiter, 2);
      result.add(new Agenda(data[0], data[1]));
    }

    result.sort(Comparator.comparing(Agenda::getTime));

    return result;
  }

  public String getContent() {
    return this.content;
  }

  public String getTime() {
    return this.time;
  }

  public String toString(String delimiter, String delimiterEOA) {
    return this.time.concat(delimiter)
        .concat(this.content)
        .concat(delimiterEOA);
  }
}
