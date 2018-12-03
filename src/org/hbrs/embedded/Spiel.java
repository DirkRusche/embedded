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

    initGame();

    turn++;

    while (!geschlossen || turn != letzter) {
      System.out.println(turn + " - " + letzter);

      Spieler spieler = spielerMap.get(turn).getFirst();
      Hand spielerHand = spielerMap.get(turn).getSecond();

      Action action = spieler.getAction(spielerHand.getKarten(), mitte.getKarten());

      if (action == Action.CLOSE) {
        if (geschlossen) {
          System.out.println(String.format("Spieler %s hat geschoben", turn));
        }
        else {
          System.out.println(String.format("Spieler %s hat geschlossen", turn));
          geschlossen = true;
          letzter = turn;
        }
      }
      else if (action == Action.CHECK) {
        System.out.println(String.format("Spieler %s hat geschoben", turn));
        // do nothing
      }
      else if (action == Action.T11) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte1(),
                mitte.getKarte1()
            )
        );
        mitte.setKarte1(spielerHand.setKarte1(mitte.getKarte1()));
      }
      else if (action == Action.T12) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte1(),
                mitte.getKarte2()
            )
        );
        mitte.setKarte2(spielerHand.setKarte1(mitte.getKarte2()));
      }
      else if (action == Action.T13) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte1(),
                mitte.getKarte3()
            )
        );
        mitte.setKarte3(spielerHand.setKarte1(mitte.getKarte3()));
      }
      else if (action == Action.T21) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte2(),
                mitte.getKarte1()
            )
        );
        mitte.setKarte1(spielerHand.setKarte2(mitte.getKarte1()));
      }
      else if (action == Action.T22) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte2(),
                mitte.getKarte2()
            )
        );
        mitte.setKarte2(spielerHand.setKarte2(mitte.getKarte2()));
      }
      else if (action == Action.T23) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte2(),
                mitte.getKarte3()
            )
        );
        mitte.setKarte3(spielerHand.setKarte2(mitte.getKarte3()));
      }
      else if (action == Action.T31) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte3(),
                mitte.getKarte1()
            )
        );
        mitte.setKarte1(spielerHand.setKarte3(mitte.getKarte1()));
      }
      else if (action == Action.T32) {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte3(),
                mitte.getKarte2()
            )
        );
        mitte.setKarte2(spielerHand.setKarte3(mitte.getKarte2()));
      }
      else {
        System.out.println(
            String.format(
                "Spieler %s tauscht seine Karte %s mit %s",
                turn,
                spielerHand.getKarte3(),
                mitte.getKarte3()
            )
        );
        mitte.setKarte3(spielerHand.setKarte3(mitte.getKarte3()));
      }

      turn = (turn + 1) % spielerMap.size();
    }

    System.out.println("Runde vorbei");
    System.out.println("Ermittle Gewinner:");

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

    System.out.println(gewinner.toString());
    System.out.println("Punkte: " + punkte);

  }

  private void initGame() {
    Spieler spieler = spielerMap.get(turn).getFirst();
    Hand spielerHand = spielerMap.get(turn).getSecond();

    mitte = new Hand(stapel.getKarte(), stapel.getKarte(), stapel.getKarte());

    Decision entscheidung = spieler.getDecision(spielerHand.getKarten());

    if (entscheidung == Decision.NO) {
      spielerMap.get(turn).setSecond(mitte);
      mitte = spielerHand;
    }
  }
}
