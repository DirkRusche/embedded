package org.hbrs.embedded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hbrs.embedded.Spieler.Action;
import org.hbrs.embedded.Spieler.Decision;
import org.hbrs.embedded.common.Paar;
import org.hbrs.embedded.common.Stapel;

public class Spiel {

  private int turn = 0;

  private boolean geschlossen = false;
  private int letzter;

  private Map<Integer, Paar<Spieler, Hand>> spielerMap = new HashMap<>();
  private Hand mitte;
  private Stapel stapel;

  public Spiel(Collection<Spieler> spielerList) {
    this.stapel = new Stapel();

    int x = 0;

    for (Spieler spieler: spielerList) {
      spielerMap.put(x++, new Paar<>(
          spieler,
          new Hand(stapel.getKarte(), stapel.getKarte(), stapel.getKarte())
      ));
    }
  }
  public Spiel(Spieler... spielerList) {
    this(Arrays.asList(spielerList));
  }

  public void start() {

    publish("Spiel beginnt");

    initGame();

    turn++;

    while (!geschlossen || turn != letzter) {
      Spieler spieler = spielerMap.get(turn).getFirst();
      Hand spielerHand = spielerMap.get(turn).getSecond();

      publish(String.format("Spieler %s ist dran", spieler.getName()));

      Action action = spieler.getAction(spielerHand.getKarten(), mitte.getKarten());

      if (action == Action.CLOSE) {
        if (geschlossen) {
          publish(String.format("Spieler %s hat geschoben", spieler.getName()));
        }
        else {
          publish(String.format("Spieler %s hat geschlossen", spieler.getName()));
          geschlossen = true;
          letzter = turn;
        }
      }
      else if (action == Action.CHECK) {
        publish(String.format("Spieler %s hat geschoben", spieler.getName()));
        // do nothing
      }
      else if (action == Action.T11) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte1(),
                mitte.getKarte1()
            )
        );
        mitte.setKarte1(spielerHand.setKarte1(mitte.getKarte1()));
      }
      else if (action == Action.T12) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte1(),
                mitte.getKarte2()
            )
        );
        mitte.setKarte2(spielerHand.setKarte1(mitte.getKarte2()));
      }
      else if (action == Action.T13) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte1(),
                mitte.getKarte3()
            )
        );
        mitte.setKarte3(spielerHand.setKarte1(mitte.getKarte3()));
      }
      else if (action == Action.T21) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte2(),
                mitte.getKarte1()
            )
        );
        mitte.setKarte1(spielerHand.setKarte2(mitte.getKarte1()));
      }
      else if (action == Action.T22) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte2(),
                mitte.getKarte2()
            )
        );
        mitte.setKarte2(spielerHand.setKarte2(mitte.getKarte2()));
      }
      else if (action == Action.T23) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte2(),
                mitte.getKarte3()
            )
        );
        mitte.setKarte3(spielerHand.setKarte2(mitte.getKarte3()));
      }
      else if (action == Action.T31) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte3(),
                mitte.getKarte1()
            )
        );
        mitte.setKarte1(spielerHand.setKarte3(mitte.getKarte1()));
      }
      else if (action == Action.T32) {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte3(),
                mitte.getKarte2()
            )
        );
        mitte.setKarte2(spielerHand.setKarte3(mitte.getKarte2()));
      }
      else {
        publish(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte3(),
                mitte.getKarte3()
            )
        );
        mitte.setKarte3(spielerHand.setKarte3(mitte.getKarte3()));
      }

      turn = (turn + 1) % spielerMap.size();
    }

    publish("Runde vorbei");
    publish("Ermittle Gewinner:");

    float punkte = 0;
    List<Spieler> gewinner = new ArrayList<>();

    for (int i = 0; i < spielerMap.size(); i++) {
      Spieler Stmp = spielerMap.get(i).getFirst();
      Hand Htmp = spielerMap.get(i).getSecond();
      float Ptmp = Helper.getPunkte(Htmp);

      if (Ptmp > punkte) {
        gewinner = new ArrayList<>();
        gewinner.add(Stmp);
        punkte = Ptmp;
      }
      else if (Ptmp == punkte) {
        gewinner.add(Stmp);
      }
    }

    publish(Arrays.toString(gewinner.toArray()));
    publish("Punkte: " + punkte);

  }
  
  private void publish(String string) {
    for (Paar<Spieler, Hand> spielerHandPaar : spielerMap.values()) {
      spielerHandPaar.getFirst().notify(string);
    }
  }

  private void initGame() {
    Spieler spieler = spielerMap.get(turn).getFirst();
    Hand spielerHand = spielerMap.get(turn).getSecond();

    mitte = new Hand(stapel.getKarte(), stapel.getKarte(), stapel.getKarte());

    publish(String.format("Spieler %s ist dran", spieler.getName()));

    Decision entscheidung = spieler.getDecision(spielerHand.getKarten());

    if (entscheidung == Decision.NO) {
      spielerMap.get(turn).setSecond(mitte);
      mitte = spielerHand;
    }
  }
}
