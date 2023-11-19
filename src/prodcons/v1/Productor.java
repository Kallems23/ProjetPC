package prodcons.v1;

import java.util.Random;

public class Productor extends Thread {
	ProdConsBuffer myBuffer;
	int minProd;
	int maxProd;

	public Productor(ProdConsBuffer buffer,int minProd, int maxProd) {
		this.myBuffer = buffer;
		this.minProd = minProd;
		this.maxProd = maxProd;
		this.start();
	}

	public void run() {
		Random r = new Random();
		int nbrMessage = (int) r.nextInt(this.minProd,this.maxProd) % 100;
		for (int i = 0; i < nbrMessage; i++) {
			Message newMsg = new Message("Hello I am a message");
			try {
				myBuffer.put(newMsg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
