package org.hbrs.embedded;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.hbrs.embedded.Spieler.Action;
import org.hbrs.embedded.Spieler.Decision;
import org.hbrs.embedded.common.Paar;
import org.hbrs.embedded.common.Stapel;

public class Spiel {

  private int turn = 0;

  private boolean geschlossen = false;
  private int letzter;

  private final Map<Integer, Paar<Spieler, Hand>> spielerMap = new HashMap<>();
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
    publish("");

    initGame();

    turn++;

    while (!geschlossen || turn != letzter) {
      Spieler spieler = spielerMap.get(turn).getFirst();
      Hand spielerHand = spielerMap.get(turn).getSecond();

      publishExcept(String.format("Spieler %s ist dran", spieler.getName()), spieler);

      Action action;
      try {
        action = spieler.getAction(spielerHand.getKarten(), mitte.getKarten());
      }
      catch (Exception e) {
        action = Action.CHECK;
      }

      if (action == Action.CLOSE) {
        if (geschlossen) {
          publishExcept(String.format("Spieler %s hat geschoben", spieler.getName()), spieler);
        }
        else {
          publishExcept(String.format("Spieler %s hat geschlossen", spieler.getName()), spieler);
          geschlossen = true;
          letzter = turn;
        }
      }
      else if (action == Action.CHECK) {
        publishExcept(String.format("Spieler %s hat geschoben", spieler.getName()), spieler);
        // do nothing
      }
      else if (action == Action.T11) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte1(),
                mitte.getKarte1()
            ), spieler
        );
        mitte.setKarte1(spielerHand.setKarte1(mitte.getKarte1()));
      }
      else if (action == Action.T12) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte1(),
                mitte.getKarte2()
            ), spieler
        );
        mitte.setKarte2(spielerHand.setKarte1(mitte.getKarte2()));
      }
      else if (action == Action.T13) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte1(),
                mitte.getKarte3()
            ), spieler
        );
        mitte.setKarte3(spielerHand.setKarte1(mitte.getKarte3()));
      }
      else if (action == Action.T21) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte2(),
                mitte.getKarte1()
            ), spieler
        );
        mitte.setKarte1(spielerHand.setKarte2(mitte.getKarte1()));
      }
      else if (action == Action.T22) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte2(),
                mitte.getKarte2()
            ), spieler
        );
        mitte.setKarte2(spielerHand.setKarte2(mitte.getKarte2()));
      }
      else if (action == Action.T23) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte2(),
                mitte.getKarte3()
            ), spieler
        );
        mitte.setKarte3(spielerHand.setKarte2(mitte.getKarte3()));
      }
      else if (action == Action.T31) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte3(),
                mitte.getKarte1()
            ), spieler
        );
        mitte.setKarte1(spielerHand.setKarte3(mitte.getKarte1()));
      }
      else if (action == Action.T32) {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte3(),
                mitte.getKarte2()
            ), spieler
        );
        mitte.setKarte2(spielerHand.setKarte3(mitte.getKarte2()));
      }
      else {
        publishExcept(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                spieler.getName(),
                spielerHand.getKarte3(),
                mitte.getKarte3()
            ), spieler
        );
        mitte.setKarte3(spielerHand.setKarte3(mitte.getKarte3()));
      }

      turn = (turn + 1) % spielerMap.size();
      publish("");
    }

    publish("Runde vorbei");

    getResultsAndPublish();

  }

  private void getResultsAndPublish() {
    publish("Ermittle Punkte");

    Map<Spieler, Float> ergebnisse = new HashMap<>();

    for (int i = 0; i < spielerMap.size(); i++) {
      Spieler spieler = spielerMap.get(i).getFirst();
      Hand hand = spielerMap.get(i).getSecond();
      float punkte = Helper.getPunkte(hand);

      ergebnisse.put(spieler, punkte);
    }

    for (Entry<Spieler, Float> entry : ergebnisse.entrySet()) {
      publish(String.format("Spieler '%s' hat %.1f Punkte", entry.getKey(), entry.getValue()));
    }
  }

  private void publishExcept(String string, Spieler except) {
    for (Paar<Spieler, Hand> spielerHandPaar : spielerMap.values()) {
      if (spielerHandPaar.getFirst().equals(except)) {
        continue;
      }
      spielerHandPaar.getFirst().notify(string);
    }
  }

  private void publish(String string) {
    publishExcept(string, null);
  }

  private void initGame() {
    Spieler spieler = spielerMap.get(turn).getFirst();
    Hand spielerHand = spielerMap.get(turn).getSecond();

    mitte = new Hand(stapel.getKarte(), stapel.getKarte(), stapel.getKarte());

    publish(String.format("Spieler %s beginnt", spieler.getName()));

    Decision entscheidung = spieler.getDecision(spielerHand.getKarten());

    if (entscheidung == Decision.NO) {
      spielerMap.get(turn).setSecond(mitte);
      mitte = spielerHand;
      publishExcept(String.format("Spieler %s hat seine Karten getauscht", spieler), spieler);
    }
    else {
      publishExcept(String.format("Spieler %s hat seine Karten behalten", spieler), spieler);
    }
    publish("");
  }
}
