package prodcons.v1;

public class Consummer extends Thread {
	ProdConsBuffer myBuffer;
	int consTime;
	int myID;

	public Consummer(ProdConsBuffer buffer, int consTime) {
		this.myBuffer = buffer;
		this.consTime = consTime;
		this.myID = (int) getId();
		this.start();
	}

	public void run() {
		while (true) {
			try {
				Message msgRead = this.myBuffer.get();
				System.out.print(msgRead.myMessage + " | cons nbr= " + this.myID + "\n");
				sleep(consTime);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
