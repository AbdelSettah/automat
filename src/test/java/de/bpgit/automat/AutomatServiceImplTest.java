package de.bpgit.automat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import de.bpgit.automat.domain.*;
import de.bpgit.automat.exceptions.BetragZuNiedrigException;
import de.bpgit.automat.exceptions.GetraenkNichtVerfuegbarException;
import de.bpgit.automat.exceptions.KeinPassendesWechselGeldException;
import de.bpgit.automat.service.AutomatService;
import de.bpgit.automat.service.AutomatServiceImpl;
import de.bpgit.automat.testdata.TestDataBuilder;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class AutomatServiceImplTest {

	private final AutomatService automatService = new AutomatServiceImpl();

	@BeforeEach
	void automatBefuellen(){
		List<Getraenk> testGetraenke = TestDataBuilder.getInstance().generateGetraenke();
		List<Muenze> testGeldBestand = TestDataBuilder.getInstance().generiereGeldBestand();
		automatService.wareFuellen(testGetraenke);
		automatService.geldFuellen(testGeldBestand);
	}

	@AfterEach
	void automatEntleeren(){
		automatService.wareEntleeren();
		automatService.geldEntleeren();
	}

	@Test
	void whenKaufGebeGetraenkUndWechselGeldZurueck() throws GetraenkNichtVerfuegbarException, BetragZuNiedrigException, KeinPassendesWechselGeldException {
		int testPreis = 120;
		Getraenk wunschGetraenk = automatService.getAutomat().getGetraenke().stream().filter(g->g.getPreis()==testPreis).findAny().orElseThrow();
		GetraenkAnfrage getraenkAnfrage = automatService.kaufen(wunschGetraenk,Muenze.EIN_EURO,Muenze.FUENFZIG_CENT);
		Getraenk getraenk = getraenkAnfrage.getWunschGetraenk();
		assertThat(automatService.getAutomat().getGetraenke(), not(hasItem(getraenk)));
		assertThat(wunschGetraenk,is(getraenk));
		MuenzenBuendel muenzenBuendel = getraenkAnfrage.getMuenzenBuendel();
		assertThat(muenzenBuendel.getAnzahlZwanzigCentMuenzen(),is(equalTo(1)));
		assertThat(muenzenBuendel.getAnzahlZenCentMuenzen(),is(equalTo(1)));
	}

	@Test
	void whenKaufExakterbetragGebeGetraenkUndKeinWechselGeldZurueck() throws GetraenkNichtVerfuegbarException, BetragZuNiedrigException, KeinPassendesWechselGeldException {
		int testPreis = 150;
		Getraenk wunschGetraenk = automatService.getAutomat().getGetraenke().stream().filter(g->g.getPreis()==testPreis).findAny().orElseThrow();
		GetraenkAnfrage getraenkAnfrage = automatService.kaufen(wunschGetraenk,Muenze.EIN_EURO,Muenze.FUENFZIG_CENT);
		assertThat(getraenkAnfrage.getMuenzenBuendel().getAnzahlZenCentMuenzen(),is(equalTo(0)));
		assertThat(getraenkAnfrage.getMuenzenBuendel().getAnzahlZwanzigCentMuenzen(),is(equalTo(0)));
		assertThat(getraenkAnfrage.getMuenzenBuendel().getAnzahlFuenfzigCentMuenzen(),is(equalTo(0)));
		assertThat(getraenkAnfrage.getMuenzenBuendel().getAnzahlEinEuroMuenzen(),is(equalTo(0)));
		assertThat(getraenkAnfrage.getMuenzenBuendel().getAnzahlZweiEuroMuenzen(),is(equalTo(0)));
	}

	@Test
	void whenKaufWareNichtVorhandenWerfeExceptionAuf() throws GetraenkNichtVerfuegbarException, BetragZuNiedrigException, KeinPassendesWechselGeldException {
		int testPreis = 150;
		Getraenk wunschGetraenk = automatService.getAutomat().getGetraenke().stream().filter(g->g.getPreis()==testPreis).findAny().orElseThrow();
		automatService.kaufen(wunschGetraenk,Muenze.EIN_EURO,Muenze.FUENFZIG_CENT);
		Assertions.assertThrows(GetraenkNichtVerfuegbarException.class, () -> automatService.kaufen(wunschGetraenk,Muenze.EIN_EURO,Muenze.FUENFZIG_CENT));
	}

	@Test
	void whenKaufBetragZuNiedrigWerfeExceptionAuf() {
		int testPreis = 160;
		Getraenk wunschGetraenk = automatService.getAutomat().getGetraenke().stream().filter(g->g.getPreis()==testPreis).findAny().orElseThrow();
		Assertions.assertThrows(BetragZuNiedrigException.class, () -> automatService.kaufen(wunschGetraenk,Muenze.EIN_EURO,Muenze.FUENFZIG_CENT));
	}

	@Test
	void whenKaufKeinPassendesWechselGeldWerfeExceptionAuf() {
		automatService.geldEntleeren();
		automatService.geldFuellen(Arrays.asList(Muenze.EIN_EURO,Muenze.FUENFZIG_CENT));
		int testPreis = 190;
		Getraenk getraenkZukaufen = automatService.getAutomat().getGetraenke().stream().filter(g->g.getPreis()==testPreis).findAny().orElseThrow();
		Assertions.assertThrows(KeinPassendesWechselGeldException.class, () -> automatService.kaufen(getraenkZukaufen,Muenze.ZWEI_EURO));
	}

	@Test
	void wareEntleeren() {
		automatService.wareEntleeren();
		assertThat(automatService.getAutomat().getGetraenke().size(),is(equalTo(0)));
	}

	@Test
	void geldEntleeren() {
		automatService.geldEntleeren();
		assertThat(automatService.getAutomat().getGeldBestand().size(),is(equalTo(0)));
	}

}
