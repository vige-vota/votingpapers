package it.vige.labs.gc;

import static it.vige.labs.gc.bean.votingpapers.Validation.IMAGE_SIZE;
import static java.util.Base64.getEncoder;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles("dev")
public class ImagesTest {

	private Logger logger = getLogger(ImagesTest.class);

	@Test
	public void loadParties() throws IOException {

		BufferedInputStream forzaItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzaitalia.jpg");
		String forzaItaliaStr = new String(getEncoder().encodeToString(forzaItalia.readAllBytes()));
		logger.info("forzaitalia.jpg - " + forzaItaliaStr);
		assertTrue(forzaItaliaStr.length() <= IMAGE_SIZE);

		BufferedInputStream pd = (BufferedInputStream) this.getClass().getResourceAsStream("/parties/pd.jpg");
		String pdStr = new String(getEncoder().encodeToString(pd.readAllBytes()));
		logger.info("pd.jpg - " + pdStr);
		assertTrue(pdStr.length() <= IMAGE_SIZE);

		BufferedInputStream movimento5Stelle = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/movimento5stelle.jpg");
		String movimento5StelleStr = new String(getEncoder().encodeToString(movimento5Stelle.readAllBytes()));
		logger.info("movimento5stelle.jpg - " + movimento5StelleStr);
		assertTrue(movimento5StelleStr.length() <= IMAGE_SIZE);

		BufferedInputStream lega = (BufferedInputStream) this.getClass().getResourceAsStream("/parties/lega.jpg");
		String legaStr = new String(getEncoder().encodeToString(lega.readAllBytes()));
		logger.info("lega.jpg - " + legaStr);
		assertTrue(legaStr.length() <= IMAGE_SIZE);

		BufferedInputStream fratellidItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/fratelliditalia.jpg");
		String fratellidItaliaStr = new String(getEncoder().encodeToString(fratellidItalia.readAllBytes()));
		logger.info("fratelliditalia.jpg - " + fratellidItaliaStr);
		assertTrue(fratellidItaliaStr.length() <= IMAGE_SIZE);

		BufferedInputStream autonomistiPopolari = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/autonomistipopolari.jpg");
		String autonomistiPopolariStr = new String(getEncoder().encodeToString(autonomistiPopolari.readAllBytes()));
		logger.info("autonomistipopolari.jpg - " + autonomistiPopolariStr);
		assertTrue(autonomistiPopolariStr.length() <= IMAGE_SIZE);

		BufferedInputStream cercasiPartito = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/cercasipartito.jpg");
		String cercasiPartitoStr = new String(getEncoder().encodeToString(cercasiPartito.readAllBytes()));
		logger.info("cercasipartito.jpg - " + cercasiPartitoStr);
		assertTrue(cercasiPartitoStr.length() <= IMAGE_SIZE);

		BufferedInputStream futura2018 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/futura2018.jpg");
		String futura2018Str = new String(getEncoder().encodeToString(futura2018.readAllBytes()));
		logger.info("futura2018.jpg - " + futura2018Str);
		assertTrue(futura2018Str.length() <= IMAGE_SIZE);

		BufferedInputStream laltraEuropa = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/laltraeuropa.jpg");
		String laltraEuropaStr = new String(getEncoder().encodeToString(laltraEuropa.readAllBytes()));
		logger.info("laltraeuropa.jpg - " + laltraEuropaStr);
		assertTrue(laltraEuropaStr.length() <= IMAGE_SIZE);

		BufferedInputStream legaNord = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/leganord.jpg");
		String legaNordStr = new String(getEncoder().encodeToString(legaNord.readAllBytes()));
		logger.info("leganord.jpg - " + legaNordStr);
		assertTrue(legaNordStr.length() <= IMAGE_SIZE);

		BufferedInputStream partitoSocialista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitosocialista.jpg");
		String partitoSocialistaStr = new String(getEncoder().encodeToString(partitoSocialista.readAllBytes()));
		logger.info("partitosocialista.jpg - " + partitoSocialistaStr);
		assertTrue(partitoSocialistaStr.length() <= IMAGE_SIZE);

		BufferedInputStream partitoRadicale = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitoradicale.jpg");
		String partitoRadicaleStr = new String(getEncoder().encodeToString(partitoRadicale.readAllBytes()));
		logger.info("partitoradicale.jpg - " + partitoRadicaleStr);
		assertTrue(partitoRadicaleStr.length() <= IMAGE_SIZE);

		BufferedInputStream sceltaEuropea = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/sceltaeuropea.jpg");
		String sceltaEuropeaStr = new String(getEncoder().encodeToString(sceltaEuropea.readAllBytes()));
		logger.info("sceltaeuropea.jpg - " + sceltaEuropeaStr);
		assertTrue(sceltaEuropeaStr.length() <= IMAGE_SIZE);

		BufferedInputStream casapound = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/casapound.jpg");
		String casapoundStr = new String(getEncoder().encodeToString(casapound.readAllBytes()));
		logger.info("casapound.jpg - " + casapoundStr);
		assertTrue(casapoundStr.length() <= IMAGE_SIZE);

		BufferedInputStream sinistraELiberta = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/sinistraeliberta.jpg");
		String sinistraELibertaStr = new String(getEncoder().encodeToString(sinistraELiberta.readAllBytes()));
		logger.info("sinistraeliberta.jpg - " + sinistraELibertaStr);
		assertTrue(sinistraELibertaStr.length() <= IMAGE_SIZE);

		BufferedInputStream liberiEUguali = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/liberieuguali.jpg");
		String liberiEUgualiStr = new String(getEncoder().encodeToString(liberiEUguali.readAllBytes()));
		logger.info("liberieuguali.jpg - " + liberiEUgualiStr);
		assertTrue(liberiEUgualiStr.length() <= IMAGE_SIZE);

		BufferedInputStream forzaRoma = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzaroma.jpg");
		String forzaRomaStr = new String(getEncoder().encodeToString(forzaRoma.readAllBytes()));
		logger.info("forzaroma.jpg - " + forzaRomaStr);
		assertTrue(forzaRomaStr.length() <= IMAGE_SIZE);

		BufferedInputStream forzaLazio = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzalazio.jpg");
		String forzaLazioStr = new String(getEncoder().encodeToString(forzaLazio.readAllBytes()));
		logger.info("forzalazio.jpg - " + forzaLazioStr);
		assertTrue(forzaLazioStr.length() <= IMAGE_SIZE);

		BufferedInputStream piuEuropa = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/+europa.jpg");
		String piuEuropaStr = new String(getEncoder().encodeToString(piuEuropa.readAllBytes()));
		logger.info("+europa.jpg - " + piuEuropaStr);
		assertTrue(piuEuropaStr.length() <= IMAGE_SIZE);

		BufferedInputStream europaVerde = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/europaverde.jpg");
		String europaVerdeStr = new String(getEncoder().encodeToString(europaVerde.readAllBytes()));
		logger.info("europaverde.jpg - " + europaVerdeStr);
		assertTrue(europaVerdeStr.length() <= IMAGE_SIZE);

		BufferedInputStream laSinistra = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/lasinistra.jpg");
		String laSinistraStr = new String(getEncoder().encodeToString(laSinistra.readAllBytes()));
		logger.info("lasinistra.jpg - " + laSinistraStr);
		assertTrue(laSinistraStr.length() <= IMAGE_SIZE);

		BufferedInputStream forzaNuova = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzanuova.jpg");
		String forzaNuovaStr = new String(getEncoder().encodeToString(forzaNuova.readAllBytes()));
		logger.info("forzanuova.jpg - " + forzaNuovaStr);
		assertTrue(forzaNuovaStr.length() <= IMAGE_SIZE);

		BufferedInputStream partitoAnimalista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitoanimalista.jpg");
		String partitoAnimalistaStr = new String(getEncoder().encodeToString(partitoAnimalista.readAllBytes()));
		logger.info("partitoanimalista.jpg - " + partitoAnimalistaStr);
		assertTrue(partitoAnimalistaStr.length() <= IMAGE_SIZE);

		BufferedInputStream partitoComunista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitocomunista.jpg");
		String partitoComunistaStr = new String(getEncoder().encodeToString(partitoComunista.readAllBytes()));
		logger.info("partitocomunista.jpg - " + partitoComunistaStr);
		assertTrue(partitoComunistaStr.length() <= IMAGE_SIZE);

		BufferedInputStream partitoPirata = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitopirata.jpg");
		String partitoPirataStr = new String(getEncoder().encodeToString(partitoPirata.readAllBytes()));
		logger.info("partitopirata.jpg - " + partitoPirataStr);
		assertTrue(partitoPirataStr.length() <= IMAGE_SIZE);

		BufferedInputStream popoloDellaFamiglia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/popolodellafamiglia.jpg");
		String popoloDellaFamigliaStr = new String(getEncoder().encodeToString(popoloDellaFamiglia.readAllBytes()));
		logger.info("popolodellafamiglia.jpg - " + popoloDellaFamigliaStr);
		assertTrue(popoloDellaFamigliaStr.length() <= IMAGE_SIZE);

		BufferedInputStream popolariPerLItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/popolariperlitalia.jpg");
		String popolariPerLItaliaStr = new String(getEncoder().encodeToString(popolariPerLItalia.readAllBytes()));
		logger.info("popolariperlitalia.jpg - " + popolariPerLItaliaStr);
		assertTrue(popolariPerLItaliaStr.length() <= IMAGE_SIZE);

		BufferedInputStream si = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/si.png");
		String siStr = new String(getEncoder().encodeToString(si.readAllBytes()));
		logger.info("si.png - " + siStr);
		assertTrue(siStr.length() <= IMAGE_SIZE);

		BufferedInputStream no = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/no.png");
		String noStr = new String(getEncoder().encodeToString(no.readAllBytes()));
		logger.info("no.png - " + noStr);
		assertTrue(noStr.length() <= IMAGE_SIZE);
	}

	@Test
	public void loadCandidates() throws IOException {

		BufferedInputStream candidate1 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate1.jpg");
		String candidate1Str = new String(getEncoder().encodeToString(candidate1.readAllBytes()));
		logger.info("candidate1.jpg - " + candidate1Str);
		assertTrue(candidate1Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate2 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate2.jpg");
		String candidate2Str = new String(getEncoder().encodeToString(candidate2.readAllBytes()));
		logger.info("candidate2.jpg - " + candidate2Str);
		assertTrue(candidate2Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate3 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate3.jpg");
		String candidate3Str = new String(getEncoder().encodeToString(candidate3.readAllBytes()));
		logger.info("candidate3.jpg - " + candidate3Str);
		assertTrue(candidate3Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate4 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate4.jpg");
		String candidate4Str = new String(getEncoder().encodeToString(candidate4.readAllBytes()));
		logger.info("candidate4.jpg - " + candidate4Str);
		assertTrue(candidate4Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate5 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate5.jpg");
		String candidate5Str = new String(getEncoder().encodeToString(candidate5.readAllBytes()));
		logger.info("candidate5.jpg - " + candidate5Str);
		assertTrue(candidate5Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate6 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate6.jpg");
		String candidate6Str = new String(getEncoder().encodeToString(candidate6.readAllBytes()));
		logger.info("candidate6.jpg - " + candidate6Str);
		assertTrue(candidate6Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate7 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate7.jpg");
		String candidate7Str = new String(getEncoder().encodeToString(candidate7.readAllBytes()));
		logger.info("candidate7.jpg - " + candidate7Str);
		assertTrue(candidate7Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate8 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate8.jpg");
		String candidate8Str = new String(getEncoder().encodeToString(candidate8.readAllBytes()));
		logger.info("candidate8.jpg - " + candidate8Str);
		assertTrue(candidate8Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate9 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate9.jpg");
		String candidate9Str = new String(getEncoder().encodeToString(candidate9.readAllBytes()));
		logger.info("candidate9.jpg - " + candidate9Str);
		assertTrue(candidate9Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate10 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate10.jpg");
		String candidate10Str = new String(getEncoder().encodeToString(candidate10.readAllBytes()));
		logger.info("candidate10.jpg - " + candidate10Str);
		assertTrue(candidate10Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate11 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate11.jpg");
		String candidate11Str = new String(getEncoder().encodeToString(candidate11.readAllBytes()));
		logger.info("candidate11.jpg - " + candidate11Str);
		assertTrue(candidate11Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate12 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate12.jpg");
		String candidate12Str = new String(getEncoder().encodeToString(candidate12.readAllBytes()));
		logger.info("candidate12.jpg - " + candidate12Str);
		assertTrue(candidate12Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate13 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate13.jpg");
		String candidate13Str = new String(getEncoder().encodeToString(candidate13.readAllBytes()));
		logger.info("candidate13.jpg - " + candidate13Str);
		assertTrue(candidate13Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate14 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate14.jpg");
		String candidate14Str = new String(getEncoder().encodeToString(candidate14.readAllBytes()));
		logger.info("candidate14.jpg - " + candidate14Str);
		assertTrue(candidate14Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate15 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate15.jpg");
		String candidate15Str = new String(getEncoder().encodeToString(candidate15.readAllBytes()));
		logger.info("candidate15.jpg - " + candidate15Str);
		assertTrue(candidate15Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate16 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate16.jpg");
		String candidate16Str = new String(getEncoder().encodeToString(candidate16.readAllBytes()));
		logger.info("candidate16.jpg - " + candidate16Str);
		assertTrue(candidate16Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate17 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate17.jpg");
		String candidate17Str = new String(getEncoder().encodeToString(candidate17.readAllBytes()));
		logger.info("candidate17.jpg - " + candidate17Str);
		assertTrue(candidate17Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate18 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate18.jpg");
		String candidate18Str = new String(getEncoder().encodeToString(candidate18.readAllBytes()));
		logger.info("candidate18.jpg - " + candidate18Str);
		assertTrue(candidate18Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate19 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate19.jpg");
		String candidate19Str = new String(getEncoder().encodeToString(candidate19.readAllBytes()));
		logger.info("candidate19.jpg - " + candidate19Str);
		assertTrue(candidate19Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate20 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate20.jpg");
		String candidate20Str = new String(getEncoder().encodeToString(candidate20.readAllBytes()));
		logger.info("candidate20.jpg - " + candidate20Str);
		assertTrue(candidate20Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate21 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate21.jpg");
		String candidate21Str = new String(getEncoder().encodeToString(candidate21.readAllBytes()));
		logger.info("candidate21.jpg - " + candidate21Str);
		assertTrue(candidate21Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate22 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate22.jpg");
		String candidate22Str = new String(getEncoder().encodeToString(candidate22.readAllBytes()));
		logger.info("candidate22.jpg - " + candidate22Str);
		assertTrue(candidate22Str.length() <= IMAGE_SIZE);

		BufferedInputStream candidate23 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate23.jpg");
		String candidate23Str = new String(getEncoder().encodeToString(candidate23.readAllBytes()));
		logger.info("candidate23.jpg - " + candidate23Str);
		assertTrue(candidate23Str.length() <= IMAGE_SIZE);
	}

}
