package prodcons;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.function.Consumer;

import prodcons.v2.*;
import prodcons.v2.ProdConsBuffer;
import prodcons.v3.*;

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

	public static void testV2() throws InterruptedException {
		TestProdCons testA = new TestProdCons();
		ArrayList<prodcons.v2.Productor> prodL = new ArrayList<prodcons.v2.Productor>();
		ArrayList<prodcons.v2.Consummer> consL = new ArrayList<prodcons.v2.Consummer>();

		prodcons.v2.ProdConsBuffer myProConsBuffer = new prodcons.v2.ProdConsBuffer(testA.bufSz);
		for (int i = 0; i < testA.nProd; i++) {
			prodcons.v2.Productor productor = new prodcons.v2.Productor(myProConsBuffer, testA.minProd, testA.maxProd, testA.prodTime);
			prodL.add(productor);
		}
		for (int i = 0; i < testA.nCons; i++) {
			prodcons.v2.Consummer consummer = new prodcons.v2.Consummer(myProConsBuffer, testA.consTime);
			consL.add(consummer);
		}
		for (Iterator<prodcons.v2.Productor> iterator = prodL.iterator(); iterator.hasNext();) {
			prodcons.v2.Productor productor = (prodcons.v2.Productor) iterator.next();
			productor.join();
		}
		myProConsBuffer.finishProducing = true;

		while (myProConsBuffer.nmsg() != 0)
			System.out.println(myProConsBuffer.nmsg() != 0);

		for (Iterator<prodcons.v2.Consummer> iterator = consL.iterator(); iterator.hasNext();) {
			prodcons.v2.Consummer consummer = (prodcons.v2.Consummer) iterator.next();
			consummer.interrupt();
		}
		System.out.println("hey it work!");

	}
	public static void testV3() throws InterruptedException {
		TestProdCons testA = new TestProdCons();
		ArrayList<prodcons.v3.Productor> prodL = new ArrayList<prodcons.v3.Productor>();
		ArrayList<prodcons.v3.Consummer> consL = new ArrayList<prodcons.v3.Consummer>();

		prodcons.v3.ProdConsBuffer myProConsBuffer = new prodcons.v3.ProdConsBuffer(testA.bufSz);
		for (int i = 0; i < testA.nProd; i++) {
			prodcons.v3.Productor productor = new prodcons.v3.Productor(myProConsBuffer, testA.minProd, testA.maxProd, testA.prodTime);
			prodL.add(productor);
		}
		for (int i = 0; i < testA.nCons; i++) {
			prodcons.v3.Consummer consummer = new prodcons.v3.Consummer(myProConsBuffer, testA.consTime);
			consL.add(consummer);
		}
		for (Iterator<prodcons.v3.Productor> iterator = prodL.iterator(); iterator.hasNext();) {
			prodcons.v3.Productor productor = (prodcons.v3.Productor) iterator.next();
			productor.join();
		}
		myProConsBuffer.finishProducing = true;

		while (myProConsBuffer.nmsg() != 0)
			System.out.println(myProConsBuffer.nmsg() != 0);

		for (Iterator<prodcons.v3.Consummer> iterator = consL.iterator(); iterator.hasNext();) {
			prodcons.v3.Consummer consummer = (prodcons.v3.Consummer) iterator.next();
			consummer.interrupt();
		}
		System.out.println("hey it work!");

	}
	public static void main(String[] args) throws InterruptedException {
		//testV2();
		testV3();
	}

}
