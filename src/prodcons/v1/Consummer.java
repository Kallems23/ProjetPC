package prodcons.v1;

public class Consummer extends Thread {
	ProdConsBuffer myBuffer;
	int consTime;

	public Consummer(ProdConsBuffer buffer, int consTime) {
		this.myBuffer = buffer;
		this.consTime = consTime;
		this.start();
	}

	public void run() {
		try {
			while (!myBuffer.notEmpty())
				wait();
			Message msgRead = this.myBuffer.get();
			System.out.print(msgRead.myMessage + " | cons nbr= " + getId() + "\n");
			sleep(consTime);
			notifyAll();// Car des thread pourrait etre en attente de place dans le buffer

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
