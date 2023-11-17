package prodcons;

import java.io.IOException;
import java.util.Properties;

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
			properties.loadFromXML(
			TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		} catch (IOException e) {
			throw new Error("no file found");
		}
		nProd = Integer.parseInt(properties.getProperty("nProd"));
		nCons = Integer.parseInt(properties.getProperty("nCons"));
		bufSz = Integer.parseInt(properties.getProperty("bufSz"));
		prodTime = Integer.parseInt(properties.getProperty("prodTime"));
		consTime = Integer.parseInt(properties.getProperty("consTime"));
		minProd = Integer.parseInt(properties.getProperty("minProd"));
		maxProd = Integer.parseInt(properties.getProperty("maxProd"));
	}
	
	public static void main(String[] args) {
		TestProdCons testA = new TestProdCons();
	}

}
