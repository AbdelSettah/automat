package de.bpgit.automat.testdata;

import de.bpgit.automat.domain.Getraenk;
import de.bpgit.automat.domain.GetraenkTyp;
import de.bpgit.automat.domain.Muenze;

import static de.bpgit.automat.domain.Automat.*;
import static de.bpgit.automat.domain.Getraenk.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataBuilder {

    private static TestDataBuilder INSTANCE;

    private TestDataBuilder() {

    }

    public static TestDataBuilder getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TestDataBuilder();
        }
        return INSTANCE;
    }

    public List<Getraenk> generateGetraenke() {
        List<Getraenk> getraenke = new ArrayList<>();
        int preisInterval=MIN_PREIS;
        for (int i=0;i<MAXIMALER_WARENBESTAND;i++){
            int zufallsNummerGetraenkTyp = new Random().nextInt(2);
            Getraenk getraenk = new Getraenk(preisInterval,(i+1), GetraenkTyp.values()[zufallsNummerGetraenkTyp]);
            getraenke.add(getraenk);
            preisInterval +=10;
        }
        return getraenke;
    }

    public List<Muenze> generiereGeldBestand() {
        List<Muenze> muenzen = new ArrayList<>();
        for(int i=0; i<MAXIMALER_GELDBESTAND_ANZAHL_MUENZEN;i++){
            int zufallsNummerMunze = new Random().nextInt(5);
            Muenze muenze = Muenze.values()[zufallsNummerMunze];
            muenzen.add(muenze);
        }
        return muenzen;
    }
}
