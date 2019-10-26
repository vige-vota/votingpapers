package it.vige.labs.gc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import it.vige.labs.gc.votingpapers.Validation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ImagesTest {

	private Logger logger = LoggerFactory.getLogger(ImagesTest.class);

	@Test
	public void loadParties() throws IOException {

		BufferedInputStream forzaItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzaitalia.jpg");
		String forzaItaliaStr = new String(Base64.getEncoder().encodeToString(forzaItalia.readAllBytes()));
		logger.info("forzaitalia.jpg - " + forzaItaliaStr);
		Assert.assertTrue(forzaItaliaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream pd = (BufferedInputStream) this.getClass().getResourceAsStream("/parties/pd.jpg");
		String pdStr = new String(Base64.getEncoder().encodeToString(pd.readAllBytes()));
		logger.info("pd.jpg - " + pdStr);
		Assert.assertTrue(pdStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream movimento5Stelle = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/movimento5stelle.jpg");
		String movimento5StelleStr = new String(Base64.getEncoder().encodeToString(movimento5Stelle.readAllBytes()));
		logger.info("movimento5stelle.jpg - " + movimento5StelleStr);
		Assert.assertTrue(movimento5StelleStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream lega = (BufferedInputStream) this.getClass().getResourceAsStream("/parties/lega.jpg");
		String legaStr = new String(Base64.getEncoder().encodeToString(lega.readAllBytes()));
		logger.info("lega.jpg - " + legaStr);
		Assert.assertTrue(legaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream fratellidItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/fratelliditalia.jpg");
		String fratellidItaliaStr = new String(Base64.getEncoder().encodeToString(fratellidItalia.readAllBytes()));
		logger.info("fratelliditalia.jpg - " + fratellidItaliaStr);
		Assert.assertTrue(fratellidItaliaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream autonomistiPopolari = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/autonomistipopolari.jpg");
		String autonomistiPopolariStr = new String(
				Base64.getEncoder().encodeToString(autonomistiPopolari.readAllBytes()));
		logger.info("autonomistipopolari.jpg - " + autonomistiPopolariStr);
		Assert.assertTrue(autonomistiPopolariStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream cercasiPartito = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/cercasipartito.jpg");
		String cercasiPartitoStr = new String(Base64.getEncoder().encodeToString(cercasiPartito.readAllBytes()));
		logger.info("cercasipartito.jpg - " + cercasiPartitoStr);
		Assert.assertTrue(cercasiPartitoStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream futura2018 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/futura2018.jpg");
		String futura2018Str = new String(Base64.getEncoder().encodeToString(futura2018.readAllBytes()));
		logger.info("futura2018.jpg - " + futura2018Str);
		Assert.assertTrue(futura2018Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream laltraEuropa = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/laltraeuropa.jpg");
		String laltraEuropaStr = new String(Base64.getEncoder().encodeToString(laltraEuropa.readAllBytes()));
		logger.info("laltraeuropa.jpg - " + laltraEuropaStr);
		Assert.assertTrue(laltraEuropaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream legaNord = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/leganord.jpg");
		String legaNordStr = new String(Base64.getEncoder().encodeToString(legaNord.readAllBytes()));
		logger.info("leganord.jpg - " + legaNordStr);
		Assert.assertTrue(legaNordStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream partitoSocialista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitosocialista.jpg");
		String partitoSocialistaStr = new String(Base64.getEncoder().encodeToString(partitoSocialista.readAllBytes()));
		logger.info("partitosocialista.jpg - " + partitoSocialistaStr);
		Assert.assertTrue(partitoSocialistaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream partitoRadicale = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitoradicale.jpg");
		String partitoRadicaleStr = new String(Base64.getEncoder().encodeToString(partitoRadicale.readAllBytes()));
		logger.info("partitoradicale.jpg - " + partitoRadicaleStr);
		Assert.assertTrue(partitoRadicaleStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream sceltaEuropea = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/sceltaeuropea.jpg");
		String sceltaEuropeaStr = new String(Base64.getEncoder().encodeToString(sceltaEuropea.readAllBytes()));
		logger.info("sceltaeuropea.jpg - " + sceltaEuropeaStr);
		Assert.assertTrue(sceltaEuropeaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream casapound = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/casapound.jpg");
		String casapoundStr = new String(Base64.getEncoder().encodeToString(casapound.readAllBytes()));
		logger.info("casapound.jpg - " + casapoundStr);
		Assert.assertTrue(casapoundStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream sinistraELiberta = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/sinistraeliberta.jpg");
		String sinistraELibertaStr = new String(Base64.getEncoder().encodeToString(sinistraELiberta.readAllBytes()));
		logger.info("sinistraeliberta.jpg - " + sinistraELibertaStr);
		Assert.assertTrue(sinistraELibertaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream liberiEUguali = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/liberieuguali.jpg");
		String liberiEUgualiStr = new String(Base64.getEncoder().encodeToString(liberiEUguali.readAllBytes()));
		logger.info("liberieuguali.jpg - " + liberiEUgualiStr);
		Assert.assertTrue(liberiEUgualiStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream forzaRoma = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzaroma.jpg");
		String forzaRomaStr = new String(Base64.getEncoder().encodeToString(forzaRoma.readAllBytes()));
		logger.info("forzaroma.jpg - " + forzaRomaStr);
		Assert.assertTrue(forzaRomaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream forzaLazio = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzalazio.jpg");
		String forzaLazioStr = new String(Base64.getEncoder().encodeToString(forzaLazio.readAllBytes()));
		logger.info("forzalazio.jpg - " + forzaLazioStr);
		Assert.assertTrue(forzaLazioStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream piuEuropa = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/+europa.jpg");
		String piuEuropaStr = new String(Base64.getEncoder().encodeToString(piuEuropa.readAllBytes()));
		logger.info("+europa.jpg - " + piuEuropaStr);
		Assert.assertTrue(piuEuropaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream europaVerde = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/europaverde.jpg");
		String europaVerdeStr = new String(Base64.getEncoder().encodeToString(europaVerde.readAllBytes()));
		logger.info("europaverde.jpg - " + europaVerdeStr);
		Assert.assertTrue(europaVerdeStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream laSinistra = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/lasinistra.jpg");
		String laSinistraStr = new String(Base64.getEncoder().encodeToString(laSinistra.readAllBytes()));
		logger.info("lasinistra.jpg - " + laSinistraStr);
		Assert.assertTrue(laSinistraStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream forzaNuova = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzanuova.jpg");
		String forzaNuovaStr = new String(Base64.getEncoder().encodeToString(forzaNuova.readAllBytes()));
		logger.info("forzanuova.jpg - " + forzaNuovaStr);
		Assert.assertTrue(forzaNuovaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream partitoAnimalista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitoanimalista.jpg");
		String partitoAnimalistaStr = new String(Base64.getEncoder().encodeToString(partitoAnimalista.readAllBytes()));
		logger.info("partitoanimalista.jpg - " + partitoAnimalistaStr);
		Assert.assertTrue(partitoAnimalistaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream partitoComunista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitocomunista.jpg");
		String partitoComunistaStr = new String(Base64.getEncoder().encodeToString(partitoComunista.readAllBytes()));
		logger.info("partitocomunista.jpg - " + partitoComunistaStr);
		Assert.assertTrue(partitoComunistaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream partitoPirata = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitopirata.jpg");
		String partitoPirataStr = new String(Base64.getEncoder().encodeToString(partitoPirata.readAllBytes()));
		logger.info("partitopirata.jpg - " + partitoPirataStr);
		Assert.assertTrue(partitoPirataStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream popoloDellaFamiglia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/popolodellafamiglia.jpg");
		String popoloDellaFamigliaStr = new String(
				Base64.getEncoder().encodeToString(popoloDellaFamiglia.readAllBytes()));
		logger.info("popolodellafamiglia.jpg - " + popoloDellaFamigliaStr);
		Assert.assertTrue(popoloDellaFamigliaStr.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream popolariPerLItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/popolariperlitalia.jpg");
		String popolariPerLItaliaStr = new String(
				Base64.getEncoder().encodeToString(popolariPerLItalia.readAllBytes()));
		logger.info("popolariperlitalia.jpg - " + popolariPerLItaliaStr);
		Assert.assertTrue(popolariPerLItaliaStr.length() <= Validation.IMAGE_SIZE);
	}

	@Test
	public void loadCandidates() throws IOException {

		BufferedInputStream candidate1 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate1.jpg");
		String candidate1Str = new String(Base64.getEncoder().encodeToString(candidate1.readAllBytes()));
		logger.info("candidate1.jpg - " + candidate1Str);
		Assert.assertTrue(candidate1Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate2 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate2.jpg");
		String candidate2Str = new String(Base64.getEncoder().encodeToString(candidate2.readAllBytes()));
		logger.info("candidate2.jpg - " + candidate2Str);
		Assert.assertTrue(candidate2Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate3 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate3.jpg");
		String candidate3Str = new String(Base64.getEncoder().encodeToString(candidate3.readAllBytes()));
		logger.info("candidate3.jpg - " + candidate3Str);
		Assert.assertTrue(candidate3Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate4 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate4.jpg");
		String candidate4Str = new String(Base64.getEncoder().encodeToString(candidate4.readAllBytes()));
		logger.info("candidate4.jpg - " + candidate4Str);
		Assert.assertTrue(candidate4Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate5 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate5.jpg");
		String candidate5Str = new String(Base64.getEncoder().encodeToString(candidate5.readAllBytes()));
		logger.info("candidate5.jpg - " + candidate5Str);
		Assert.assertTrue(candidate5Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate6 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate6.jpg");
		String candidate6Str = new String(Base64.getEncoder().encodeToString(candidate6.readAllBytes()));
		logger.info("candidate6.jpg - " + candidate6Str);
		Assert.assertTrue(candidate6Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate7 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate7.jpg");
		String candidate7Str = new String(Base64.getEncoder().encodeToString(candidate7.readAllBytes()));
		logger.info("candidate7.jpg - " + candidate7Str);
		Assert.assertTrue(candidate7Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate8 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate8.jpg");
		String candidate8Str = new String(Base64.getEncoder().encodeToString(candidate8.readAllBytes()));
		logger.info("candidate8.jpg - " + candidate8Str);
		Assert.assertTrue(candidate8Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate9 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate9.jpg");
		String candidate9Str = new String(Base64.getEncoder().encodeToString(candidate9.readAllBytes()));
		logger.info("candidate9.jpg - " + candidate9Str);
		Assert.assertTrue(candidate9Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate10 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate10.jpg");
		String candidate10Str = new String(Base64.getEncoder().encodeToString(candidate10.readAllBytes()));
		logger.info("candidate10.jpg - " + candidate10Str);
		Assert.assertTrue(candidate10Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate11 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate11.jpg");
		String candidate11Str = new String(Base64.getEncoder().encodeToString(candidate11.readAllBytes()));
		logger.info("candidate11.jpg - " + candidate11Str);
		Assert.assertTrue(candidate11Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate12 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate12.jpg");
		String candidate12Str = new String(Base64.getEncoder().encodeToString(candidate12.readAllBytes()));
		logger.info("candidate12.jpg - " + candidate12Str);
		Assert.assertTrue(candidate12Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate13 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate13.jpg");
		String candidate13Str = new String(Base64.getEncoder().encodeToString(candidate13.readAllBytes()));
		logger.info("candidate13.jpg - " + candidate13Str);
		Assert.assertTrue(candidate13Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate14 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate14.jpg");
		String candidate14Str = new String(Base64.getEncoder().encodeToString(candidate14.readAllBytes()));
		logger.info("candidate14.jpg - " + candidate14Str);
		Assert.assertTrue(candidate14Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate15 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate15.jpg");
		String candidate15Str = new String(Base64.getEncoder().encodeToString(candidate15.readAllBytes()));
		logger.info("candidate15.jpg - " + candidate15Str);
		Assert.assertTrue(candidate15Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate16 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate16.jpg");
		String candidate16Str = new String(Base64.getEncoder().encodeToString(candidate16.readAllBytes()));
		logger.info("candidate16.jpg - " + candidate16Str);
		Assert.assertTrue(candidate16Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate17 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate17.jpg");
		String candidate17Str = new String(Base64.getEncoder().encodeToString(candidate17.readAllBytes()));
		logger.info("candidate17.jpg - " + candidate17Str);
		Assert.assertTrue(candidate17Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate18 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate18.jpg");
		String candidate18Str = new String(Base64.getEncoder().encodeToString(candidate18.readAllBytes()));
		logger.info("candidate18.jpg - " + candidate18Str);
		Assert.assertTrue(candidate18Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate19 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate19.jpg");
		String candidate19Str = new String(Base64.getEncoder().encodeToString(candidate19.readAllBytes()));
		logger.info("candidate19.jpg - " + candidate19Str);
		Assert.assertTrue(candidate19Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate20 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate20.jpg");
		String candidate20Str = new String(Base64.getEncoder().encodeToString(candidate20.readAllBytes()));
		logger.info("candidate20.jpg - " + candidate20Str);
		Assert.assertTrue(candidate20Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate21 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate21.jpg");
		String candidate21Str = new String(Base64.getEncoder().encodeToString(candidate21.readAllBytes()));
		logger.info("candidate21.jpg - " + candidate21Str);
		Assert.assertTrue(candidate21Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate22 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate22.jpg");
		String candidate22Str = new String(Base64.getEncoder().encodeToString(candidate22.readAllBytes()));
		logger.info("candidate22.jpg - " + candidate22Str);
		Assert.assertTrue(candidate22Str.length() <= Validation.IMAGE_SIZE);

		BufferedInputStream candidate23 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate23.jpg");
		String candidate23Str = new String(Base64.getEncoder().encodeToString(candidate23.readAllBytes()));
		logger.info("candidate23.jpg - " + candidate23Str);
		Assert.assertTrue(candidate23Str.length() <= Validation.IMAGE_SIZE);
	}

}
