package prodcons.v2;

import java.util.Random;

public class Productor extends Thread {
	ProdConsBuffer myBuffer;
	int minProd;
	int maxProd;
	int prodTime;
	int myID;

	public Productor(ProdConsBuffer buffer, int minProd, int maxProd, int prodTime) {
		this.myBuffer = buffer;
		this.minProd = minProd;
		this.maxProd = maxProd;
		this.prodTime = prodTime;
		this.myID = (int) getId();
		this.start();
	}

	public void run() {
		Random r = new Random();
		int nbrMessage = (int) r.nextInt(this.minProd, this.maxProd) % 100;
		for (int i = 0; i < nbrMessage; i++) {
			Message newMsg = new Message("prod nbr = " + this.myID);
			try {
				sleep(this.prodTime);
				myBuffer.put(newMsg);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}
}
