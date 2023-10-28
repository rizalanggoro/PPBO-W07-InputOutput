/*
  File object dari Agenda.
  Agenda memiliki 2 properti, yaitu:
  - time atau waktu
  - content atau isi agenda
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Agenda {
  private final String time;
  private final String content;

  // constructor untuk mengisi properties
  public Agenda(
      String time,
      String content
  ) {
    this.content = content;
    this.time = time;
  }

  // fungsi statis untuk men-generate list agenda berdasarkan string file
  public static List<Agenda> fromString(String string, String delimiter, String delimiterEOA) {
    // delimiter -> untuk memisahkan field: time, content
    // delimiter eoa -> untuk memisahkan setiap baris agenda
    String[] agendas = string.split(delimiterEOA);
    final ArrayList<Agenda> result = new ArrayList<>();

    for (String agenda : agendas) {
      String[] data = agenda.split(delimiter, 2);
      result.add(new Agenda(data[0], data[1]));
    }

    // mengurutkan agenda berdasarkan waktunya
    result.sort(Comparator.comparing(Agenda::getTime));

    return result;
  }

  // getter: content
  public String getContent() {
    return this.content;
  }

  // getter: time
  public String getTime() {
    return this.time;
  }

  // fungsi untuk men-generate string sebelum ditulis ke
  // dalam file txt
  public String toString(String delimiter, String delimiterEOA) {
    return this.time.concat(delimiter)
        .concat(this.content)
        .concat(delimiterEOA);
  }
}
