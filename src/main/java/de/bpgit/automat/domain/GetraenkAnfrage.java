package de.bpgit.automat.domain;

import lombok.Data;

/**
 * @author Abdel
 * Bündelt das gewünschte Getränk und das Münzenbündel für das Wechselgeld
 * {@link Getraenk}
 * {@link MuenzenBuendel}
 */
@Data
public class GetraenkAnfrage {

    private Getraenk wunschGetraenk;
    private MuenzenBuendel muenzenBuendel;

    public GetraenkAnfrage(Getraenk wunschGetraenk, Muenze... eingeworfen) {
        this.wunschGetraenk = wunschGetraenk;
        this.muenzenBuendel = new MuenzenBuendel(eingeworfen);
    }
}
