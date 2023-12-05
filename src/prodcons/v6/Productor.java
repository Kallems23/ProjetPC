package prodcons.v6;

import java.util.Random;

public class Productor extends Thread {
	ProdConsBuffer myBuffer;
	int minProd;
	int maxProd;
	int prodTime;

	public Productor(ProdConsBuffer buffer, int minProd, int maxProd, int prodTime) {
		this.myBuffer = buffer;
		this.minProd = minProd;
		this.maxProd = maxProd;
		this.prodTime = prodTime;
		this.start();
	}

	public void run() {
		Random r = new Random();
		int nbrMessage = (int) r.nextInt(this.minProd, this.maxProd) % 100;
		for (int i = 0; i < nbrMessage; i++) {
			Message newMsg = new Message("prod nbr = " + getId(), 3);
			try {
				sleep(this.prodTime);

				myBuffer.put(newMsg);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}
}
