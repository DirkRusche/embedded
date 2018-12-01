package org.hbrs.embedded;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Spiel {

  private int geber = 0;

  private boolean geschlossen = false;
  private int letzter;

  private Map<Spieler, Hand> spieler;
  private Hand mitte;
  private Stapel stapel;

  public Spiel(List<Spieler> spielerList) {
    this.stapel = new Stapel();

    this.spieler = spielerList.stream().map(
        spieler1 -> new Paar<>(
            spieler1,
            new Hand(stapel.getKarte(), stapel.getKarte(), stapel.getKarte())
        )
    ).collect(Collectors.toMap(Paar::getFirst, Paar::getSecond));
  }

  public void start() {
    
  }
}
