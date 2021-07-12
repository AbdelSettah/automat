package de.bpgit.automat.service;

import de.bpgit.automat.domain.*;
import de.bpgit.automat.exceptions.BetragZuNiedrigException;
import de.bpgit.automat.exceptions.GetraenkNichtVerfuegbarException;
import de.bpgit.automat.exceptions.KeinPassendesWechselGeldException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static de.bpgit.automat.domain.Automat.*;

public class AutomatServiceImpl implements AutomatService {

    private static final Logger logger = LogManager.getLogger(AutomatServiceImpl.class);

    private final Automat automat;
    private final GeldRechner geldRechner;

    public AutomatServiceImpl() {
        this.automat = new Automat();
        this.geldRechner = new GeldRechnerImpl();
    }

    @Override
    public GetraenkAnfrage kaufen(Getraenk auswahl, Muenze... einzahlungen) throws GetraenkNichtVerfuegbarException,
            BetragZuNiedrigException, KeinPassendesWechselGeldException {
        GetraenkAnfrage getraenkAnfrage = new GetraenkAnfrage(auswahl,einzahlungen);
        Getraenk getraenk = automat.getGetraenke().stream().filter(auswahl::equals).findFirst().orElse(null);
        if (getraenk == null){
            throw new GetraenkNichtVerfuegbarException("Ihr geswünschtes Getränk konnte nicht gefunden werden!");
        }
        getraenkAnfrage.setWunschGetraenk(getraenk);
        int einzahlungsbetrag = Arrays.stream(einzahlungen).mapToInt(Muenze::getwert).sum();
        int getraenkPreis = auswahl.getPreis();
        int differenz = einzahlungsbetrag - getraenkPreis;
        if (differenz<0){
            throw new BetragZuNiedrigException("Die eingeworfenen Münzen reichen für den Einkauf nicht aus!");
        }
        MuenzenBuendel muenzenBuendel = geldRechner.rechneWechselGeld(differenz);
        getraenkAnfrage.setMuenzenBuendel(muenzenBuendel);
        automat.aktualisiereBestand(getraenkAnfrage);

        return getraenkAnfrage;
    }

    @Override
    public void wareFuellen(List<Getraenk> getraenke) {
        if (getraenke.size()<=MAXIMALER_WARENBESTAND) {
            automat.setGetraenke(getraenke);
        } else {
            automat.setGetraenke(getraenke.stream().limit(MAXIMALER_WARENBESTAND).collect(Collectors.toList()));
            logger.warn("Der Automcat kann nur mit maximal " +MAXIMALER_WARENBESTAND+" Getränken gefüllt werden!");
        }
    }

    @Override
    public void geldFuellen(List<Muenze> muenzen) {
        if (muenzen.size()<=MAXIMALER_GELDBESTAND_ANZAHL_MUENZEN) {
            automat.setGeldBestand(muenzen);
        } else {
            automat.setGeldBestand(muenzen.stream().limit(MAXIMALER_GELDBESTAND_ANZAHL_MUENZEN).collect(Collectors.toList()));
            logger.warn("Der Automcat kann nur mit maximal " +MAXIMALER_GELDBESTAND_ANZAHL_MUENZEN+" Münzen gefüllt werden!");
        }
    }

    @Override
    public void wareEntleeren() {
        if (automat.getGetraenke().size()>0){
            automat.setGetraenke(Collections.emptyList());
        } else{
            logger.warn("Der Automat hat keine Ware, daher kann keine Entleerung der Ware stattfinden!");
        }
    }

    @Override
    public void geldEntleeren() {
        if (automat.getGeldBestand().size()>0){
            automat.setGeldBestand(Collections.emptyList());
        } else{
            logger.warn("Der Automat hat kein Geld, daher kann keine Entleerung des Geldes stattfinden!");
        }
    }

    @Override
    public Automat getAutomat() {
        return automat;
    }

}
