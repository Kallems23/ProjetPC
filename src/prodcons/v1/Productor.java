package prodcons.v1;

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

			while (!myBuffer.notFull())
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			Message newMsg = new Message("prod nbr = " + getId());
			try {
				sleep(this.prodTime);
				myBuffer.put(newMsg);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			notifyAll();// Car des thread pourrait etre en attente de message

		}
	}
}
