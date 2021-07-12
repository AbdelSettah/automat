package de.bpgit.automat.domain;

import de.bpgit.automat.exceptions.KeinPassendesWechselGeldException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abdel
 * Getränkeautomat: Beinhaltet Getränke und Münzen für das Wechselgeld
 * {@link Muenze}
 * {@link Getraenk}
 *
 */
@Data
public class Automat {

    public static final int MAXIMALER_GELDBESTAND_ANZAHL_MUENZEN = 15;
    public static final int MAXIMALER_WARENBESTAND = 20;

    private List<Muenze> geldBestand;
    private List<Getraenk> getraenke;

    public Automat(){
        geldBestand = new ArrayList<>();
    }

    /**
     * @author Abdel
     * Aktualisiert den Bestand an Getränke und Münzen
     * @param getraenkAnfrage: Der Wunsch des Anfragers, siehe @{@link GetraenkAnfrage}
     *
     */
    public void aktualisiereBestand(GetraenkAnfrage getraenkAnfrage) throws KeinPassendesWechselGeldException {
        getGetraenke().remove(getraenkAnfrage.getWunschGetraenk());
        aktualisiereMuenzenBestand(getraenkAnfrage);
    }

    /**
     * @author Abdel
     * Aktualisiert den Münzenbestand
     * @param getraenkAnfrage: Der Wunsch des Anfragers, siehe @{@link GetraenkAnfrage}
     *
     */
    private void aktualisiereMuenzenBestand(GetraenkAnfrage getraenkAnfrage) throws KeinPassendesWechselGeldException {
        aktualisiereMuenzen(getraenkAnfrage.getMuenzenBuendel().getAnzahlZenCentMuenzen(),Muenze.ZEHN_CENT);
        aktualisiereMuenzen(getraenkAnfrage.getMuenzenBuendel().getAnzahlZwanzigCentMuenzen(),Muenze.ZWANZIG_CENT);
        aktualisiereMuenzen(getraenkAnfrage.getMuenzenBuendel().getAnzahlFuenfzigCentMuenzen(),Muenze.FUENFZIG_CENT);
        aktualisiereMuenzen(getraenkAnfrage.getMuenzenBuendel().getAnzahlEinEuroMuenzen(),Muenze.EIN_EURO);
        aktualisiereMuenzen(getraenkAnfrage.getMuenzenBuendel().getAnzahlZweiEuroMuenzen(),Muenze.ZWEI_EURO);
    }

    /**
     * @author Abdel
     * Aktualisiert den Münzenbestand, sollte die entsprechende Münze aus dem Geldbestand nicht gefunden werden, wird
     * {@link KeinPassendesWechselGeldException} aufgeworfen
     * @param anzahlMuenzen: Die Anzahl der zu aktualisierenden Münzen
     * @param muenze: Die Münze, siehe @{@link Muenze}
     */
    private void aktualisiereMuenzen(int anzahlMuenzen,Muenze muenze) throws KeinPassendesWechselGeldException {
        for(int i=0;i<anzahlMuenzen;i++){
            if (getGeldBestand().contains(muenze)) {
                getGeldBestand().remove(muenze);
            } else {
                throw new KeinPassendesWechselGeldException("Es gibt kein passendes Geldstück vorhanden!");
            }
        }
    }

    public void addiereMuenzen(Muenze ... einzahlungen) {
        getGeldBestand().addAll(List.of(einzahlungen));
    }
}
