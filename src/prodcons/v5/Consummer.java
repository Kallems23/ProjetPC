package prodcons.v5;

import java.util.Random;

public class Consummer extends Thread {
	ProdConsBuffer myBuffer;
	int consTime;

	public Consummer(ProdConsBuffer buffer, int consTime) {
		this.myBuffer = buffer;
		this.consTime = consTime;
		this.start();
	}

	public void run() {
		while (true) {
			try {

				Random r = new Random();
				int get = (int) r.nextInt(0, 2);
				if (get == 1) {
					Message msgRead = this.myBuffer.get();
					System.out.print(msgRead.myMessage + " | cons nbr= " + getId() + "\n");

				} else {
					int nbrMessage = (int) r.nextInt(1, 21);// lit entre 1 et 20 message compris

					Message[] msgReads = this.myBuffer.get(nbrMessage);
					sleep(consTime * nbrMessage);
					for (int i = 0; i < nbrMessage; i++)
						System.out.print(msgReads[i].myMessage + " | cons nbr= " + getId() + "\n");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
