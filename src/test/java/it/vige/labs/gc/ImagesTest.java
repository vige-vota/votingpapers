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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImagesTest {

	private Logger logger = LoggerFactory.getLogger(ImagesTest.class);

	@Test
	public void loadParties() throws IOException {

		BufferedInputStream forzaItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/pinetina.jpg");
		logger.info("forzaitalia.jpg - " + new String(Base64.getEncoder().encodeToString(forzaItalia.readAllBytes())));
		Assert.assertNotNull(forzaItalia);

		BufferedInputStream pd = (BufferedInputStream) this.getClass().getResourceAsStream("/parties/pd.jpg");
		logger.info("pd.jpg - " + new String(Base64.getEncoder().encodeToString(pd.readAllBytes())));
		Assert.assertNotNull(pd);

		BufferedInputStream movimento5Stelle = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/movimento5stelle.jpg");
		logger.info("movimento5stelle.jpg - " + new String(Base64.getEncoder().encodeToString(movimento5Stelle.readAllBytes())));
		Assert.assertNotNull(movimento5Stelle);

		BufferedInputStream lega = (BufferedInputStream) this.getClass().getResourceAsStream("/parties/lega.jpg");
		logger.info("lega.jpg - " + new String(Base64.getEncoder().encodeToString(lega.readAllBytes())));
		Assert.assertNotNull(lega);

		BufferedInputStream fratellidItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/fratelliditalia.jpg");
		logger.info("fratelliditalia.jpg - " + new String(Base64.getEncoder().encodeToString(fratellidItalia.readAllBytes())));
		Assert.assertNotNull(fratellidItalia);

		BufferedInputStream autonomistiPopolari = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/autonomistipopolari.jpg");
		logger.info("autonomistipopolari.jpg - " + new String(Base64.getEncoder().encodeToString(autonomistiPopolari.readAllBytes())));
		Assert.assertNotNull(autonomistiPopolari);

		BufferedInputStream cercasiPartito = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/cercasipartito.jpg");
		logger.info("cercasipartito.jpg - " + new String(Base64.getEncoder().encodeToString(cercasiPartito.readAllBytes())));
		Assert.assertNotNull(cercasiPartito);

		BufferedInputStream futura2018 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/futura2018.jpg");
		logger.info("futura2018.jpg - " + new String(Base64.getEncoder().encodeToString(futura2018.readAllBytes())));
		Assert.assertNotNull(futura2018);

		BufferedInputStream laltraEuropa = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/laltraeuropa.jpg");
		logger.info("laltraeuropa.jpg - " + new String(Base64.getEncoder().encodeToString(laltraEuropa.readAllBytes())));
		Assert.assertNotNull(laltraEuropa);

		BufferedInputStream legaNord = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/leganord.jpg");
		logger.info("leganord.jpg - " + new String(Base64.getEncoder().encodeToString(legaNord.readAllBytes())));
		Assert.assertNotNull(legaNord);

		BufferedInputStream partitoSocialista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitosocialista.jpg");
		logger.info("partitosocialista.jpg - " + new String(Base64.getEncoder().encodeToString(partitoSocialista.readAllBytes())));
		Assert.assertNotNull(partitoSocialista);

		BufferedInputStream partitoRadicale = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitoradicale.jpg");
		logger.info("partitoradicale.jpg - " + new String(Base64.getEncoder().encodeToString(partitoRadicale.readAllBytes())));
		Assert.assertNotNull(partitoRadicale);

		BufferedInputStream sceltaEuropea = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/sceltaeuropea.jpg");
		logger.info("sceltaeuropea.jpg - " + new String(Base64.getEncoder().encodeToString(sceltaEuropea.readAllBytes())));
		Assert.assertNotNull(sceltaEuropea);

		BufferedInputStream casapound = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/casapound.jpg");
		logger.info("casapound.jpg - " + new String(Base64.getEncoder().encodeToString(casapound.readAllBytes())));
		Assert.assertNotNull(casapound);

		BufferedInputStream sinistraELiberta = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/sinistraeliberta.jpg");
		logger.info("sinistraeliberta.jpg - " + new String(Base64.getEncoder().encodeToString(sinistraELiberta.readAllBytes())));
		Assert.assertNotNull(sinistraELiberta);

		BufferedInputStream liberiEUguali = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/liberieuguali.jpg");
		logger.info("liberieuguali.jpg - " + new String(Base64.getEncoder().encodeToString(liberiEUguali.readAllBytes())));
		Assert.assertNotNull(liberiEUguali);

		BufferedInputStream forzaRoma = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzaroma.jpg");
		logger.info("forzaroma.jpg - " + new String(Base64.getEncoder().encodeToString(forzaRoma.readAllBytes())));
		Assert.assertNotNull(forzaRoma);

		BufferedInputStream forzaLazio = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzalazio.jpg");
		logger.info("forzalazio.jpg - " + new String(Base64.getEncoder().encodeToString(forzaLazio.readAllBytes())));
		Assert.assertNotNull(forzaLazio);

		BufferedInputStream piuEuropa = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/+europa.jpg");
		logger.info("+europa.jpg - " + new String(Base64.getEncoder().encodeToString(piuEuropa.readAllBytes())));
		Assert.assertNotNull(piuEuropa);

		BufferedInputStream europaVerde = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/europaverde.jpg");
		logger.info("europaverde.jpg - " + new String(Base64.getEncoder().encodeToString(europaVerde.readAllBytes())));
		Assert.assertNotNull(europaVerde);

		BufferedInputStream laSinistra = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/lasinistra.jpg");
		logger.info("lasinistra.jpg - " + new String(Base64.getEncoder().encodeToString(laSinistra.readAllBytes())));
		Assert.assertNotNull(laSinistra);

		BufferedInputStream forzaNuova = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/forzanuova.jpg");
		logger.info("forzanuova.jpg - " + new String(Base64.getEncoder().encodeToString(forzaNuova.readAllBytes())));
		Assert.assertNotNull(forzaNuova);

		BufferedInputStream partitoAnimalista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitoanimalista.jpg");
		logger.info("partitoanimalista.jpg - " + new String(Base64.getEncoder().encodeToString(partitoAnimalista.readAllBytes())));
		Assert.assertNotNull(partitoAnimalista);

		BufferedInputStream partitoComunista = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitocomunista.jpg");
		logger.info("partitocomunista.jpg - " + new String(Base64.getEncoder().encodeToString(partitoComunista.readAllBytes())));
		Assert.assertNotNull(partitoComunista);

		BufferedInputStream partitoPirata = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/partitopirata.jpg");
		logger.info("partitopirata.jpg - " + new String(Base64.getEncoder().encodeToString(partitoPirata.readAllBytes())));
		Assert.assertNotNull(partitoPirata);

		BufferedInputStream popoloDellaFamiglia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/popolodellafamiglia.jpg");
		logger.info("popolodellafamiglia.jpg - " + new String(Base64.getEncoder().encodeToString(popoloDellaFamiglia.readAllBytes())));
		Assert.assertNotNull(popoloDellaFamiglia);

		BufferedInputStream popolariPerLItalia = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/parties/popolariperlitalia.jpg");
		logger.info("popolariperlitalia.jpg - " + new String(Base64.getEncoder().encodeToString(popolariPerLItalia.readAllBytes())));
		Assert.assertNotNull(popolariPerLItalia);
	}

	@Test
	public void loadCandidates() throws IOException {

		BufferedInputStream candidate1 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate1.jpg");
		logger.info("candidate1.jpg - " + new String(Base64.getEncoder().encodeToString(candidate1.readAllBytes())));
		Assert.assertNotNull(candidate1);

		BufferedInputStream candidate2 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate2.jpg");
		logger.info("candidate2.jpg - " + new String(Base64.getEncoder().encodeToString(candidate2.readAllBytes())));
		Assert.assertNotNull(candidate2);

		BufferedInputStream candidate3 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate3.jpg");
		logger.info("candidate3.jpg - " + new String(Base64.getEncoder().encodeToString(candidate3.readAllBytes())));
		Assert.assertNotNull(candidate3);

		BufferedInputStream candidate4 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate4.jpg");
		logger.info("candidate4.jpg - " + new String(Base64.getEncoder().encodeToString(candidate4.readAllBytes())));
		Assert.assertNotNull(candidate4);

		BufferedInputStream candidate5 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate5.jpg");
		logger.info("candidate5.jpg - " + new String(Base64.getEncoder().encodeToString(candidate5.readAllBytes())));
		Assert.assertNotNull(candidate5);

		BufferedInputStream candidate6 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate6.jpg");
		logger.info("candidate6.jpg - " + new String(Base64.getEncoder().encodeToString(candidate6.readAllBytes())));
		Assert.assertNotNull(candidate6);

		BufferedInputStream candidate7 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate7.jpg");
		logger.info("candidate7.jpg - " + new String(Base64.getEncoder().encodeToString(candidate7.readAllBytes())));
		Assert.assertNotNull(candidate7);

		BufferedInputStream candidate8 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate8.jpg");
		logger.info("candidate8.jpg - " + new String(Base64.getEncoder().encodeToString(candidate8.readAllBytes())));
		Assert.assertNotNull(candidate8);

		BufferedInputStream candidate9 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate9.jpg");
		logger.info("candidate9.jpg - " + new String(Base64.getEncoder().encodeToString(candidate9.readAllBytes())));
		Assert.assertNotNull(candidate9);

		BufferedInputStream candidate10 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate10.jpg");
		logger.info("candidate10.jpg - " + new String(Base64.getEncoder().encodeToString(candidate10.readAllBytes())));
		Assert.assertNotNull(candidate10);

		BufferedInputStream candidate11 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate11.jpg");
		logger.info("candidate11.jpg - " + new String(Base64.getEncoder().encodeToString(candidate11.readAllBytes())));
		Assert.assertNotNull(candidate11);

		BufferedInputStream candidate12 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate12.jpg");
		logger.info("candidate12.jpg - " + new String(Base64.getEncoder().encodeToString(candidate12.readAllBytes())));
		Assert.assertNotNull(candidate12);

		BufferedInputStream candidate13 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate13.jpg");
		logger.info("candidate13.jpg - " + new String(Base64.getEncoder().encodeToString(candidate13.readAllBytes())));
		Assert.assertNotNull(candidate13);

		BufferedInputStream candidate14 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate14.jpg");
		logger.info("candidate14.jpg - " + new String(Base64.getEncoder().encodeToString(candidate14.readAllBytes())));
		Assert.assertNotNull(candidate14);

		BufferedInputStream candidate15 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate15.jpg");
		logger.info("candidate15.jpg - " + new String(Base64.getEncoder().encodeToString(candidate15.readAllBytes())));
		Assert.assertNotNull(candidate15);

		BufferedInputStream candidate16 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate16.jpg");
		logger.info("candidate16.jpg - " + new String(Base64.getEncoder().encodeToString(candidate16.readAllBytes())));
		Assert.assertNotNull(candidate16);

		BufferedInputStream candidate17 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate17.jpg");
		logger.info("candidate17.jpg - " + new String(Base64.getEncoder().encodeToString(candidate17.readAllBytes())));
		Assert.assertNotNull(candidate17);

		BufferedInputStream candidate18 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate18.jpg");
		logger.info("candidate18.jpg - " + new String(Base64.getEncoder().encodeToString(candidate18.readAllBytes())));
		Assert.assertNotNull(candidate18);

		BufferedInputStream candidate19 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate19.jpg");
		logger.info("candidate19.jpg - " + new String(Base64.getEncoder().encodeToString(candidate19.readAllBytes())));
		Assert.assertNotNull(candidate19);

		BufferedInputStream candidate20 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate20.jpg");
		logger.info("candidate20.jpg - " + new String(Base64.getEncoder().encodeToString(candidate20.readAllBytes())));
		Assert.assertNotNull(candidate20);

		BufferedInputStream candidate21 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate21.jpg");
		logger.info("candidate21.jpg - " + new String(Base64.getEncoder().encodeToString(candidate21.readAllBytes())));
		Assert.assertNotNull(candidate21);

		BufferedInputStream candidate22 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate22.jpg");
		logger.info("candidate22.jpg - " + new String(Base64.getEncoder().encodeToString(candidate22.readAllBytes())));
		Assert.assertNotNull(candidate22);

		BufferedInputStream candidate23 = (BufferedInputStream) this.getClass()
				.getResourceAsStream("/candidates/candidate23.jpg");
		logger.info("candidate23.jpg - " + new String(Base64.getEncoder().encodeToString(candidate23.readAllBytes())));
		Assert.assertNotNull(candidate23);
	}

}
