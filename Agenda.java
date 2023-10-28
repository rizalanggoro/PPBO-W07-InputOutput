import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Agenda {
  private final String title;
  private final String date;
  private final String time;

  public Agenda(
      String title,
      String date,
      String time
  ) {
    this.title = title;
    this.date = date;
    this.time = time;
  }

  public static List<Agenda> fromString(String string, String delimiter, String delimiterEOA) {
    String[] agendas = string.split(delimiterEOA);
    final ArrayList<Agenda> result = new ArrayList<>();

    for (String agenda : agendas) {
      String[] data = agenda.split(delimiter, 3);
      result.add(new Agenda(data[0], data[1], data[2]));
    }

    result.sort(Comparator.comparing(Agenda::getTime));

    return result;
  }

  public String getTitle() {
    return this.title;
  }

  public String getDate() {
    return this.date;
  }

  public String getTime() {
    return this.time;
  }

  public String toString(String delimiter, String delimiterEOA) {
    return this.title.concat(delimiter)
        .concat(this.date)
        .concat(delimiter)
        .concat(this.time)
        .concat(delimiterEOA);
  }
}
