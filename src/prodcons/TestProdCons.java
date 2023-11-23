package prodcons;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.function.Consumer;

import prodcons.v2.*;

public class TestProdCons {

	private int nProd;
	private int nCons;
	private int bufSz;
	private int prodTime;
	private int consTime;
	private int minProd;
	private int maxProd;

	public TestProdCons() {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));

			nProd = Integer.parseInt(properties.getProperty("nProd"));
			nCons = Integer.parseInt(properties.getProperty("nCons"));
			bufSz = Integer.parseInt(properties.getProperty("bufSz"));
			prodTime = Integer.parseInt(properties.getProperty("prodTime"));
			consTime = Integer.parseInt(properties.getProperty("consTime"));
			minProd = Integer.parseInt(properties.getProperty("minProd"));
			maxProd = Integer.parseInt(properties.getProperty("maxProd"));
		} catch (IOException e) {

			throw new Error("no file found");
		} catch (NullPointerException e) {
			System.out.println(
			//@formatter:off
				"\n#######################################\n"
				+ "Couldn't read the XML file\n"
				+ "Replacing the value by the default one\n"
				+ "#######################################\n");
				//@formatter:on

			nProd = 15;
			nCons = 10;
			bufSz = 3;
			prodTime = 10;
			consTime = 10;
			minProd = 100;
			maxProd = 500;
		}

	}

	public static void main(String[] args) throws InterruptedException {
		TestProdCons testA = new TestProdCons();
		ArrayList<Productor> prodL = new ArrayList<Productor>();
		ArrayList<Consummer> consL = new ArrayList<Consummer>();

		ProdConsBuffer myProConsBuffer = new ProdConsBuffer(testA.bufSz);
		for (int i = 0; i < testA.nProd; i++) {
			Productor productor = new Productor(myProConsBuffer, testA.minProd, testA.maxProd, testA.prodTime);
			prodL.add(productor);
		}
		for (int i = 0; i < testA.nCons; i++) {
			Consummer consummer = new Consummer(myProConsBuffer, testA.consTime);
			consL.add(consummer);
		}
		for (Iterator<Productor> iterator = prodL.iterator(); iterator.hasNext();) {
			Productor productor = (Productor) iterator.next();
			productor.join();
		}
		myProConsBuffer.finishProducing = true;

		while (myProConsBuffer.nmsg() != 0)
			System.out.println(myProConsBuffer.nmsg() != 0);
		;
		for (Iterator<Consummer> iterator = consL.iterator(); iterator.hasNext();) {
			Consummer consummer = (Consummer) iterator.next();
			consummer.interrupt();
		}
		System.out.println("hey it work!");

	}

}
