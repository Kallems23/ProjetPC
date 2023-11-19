package prodcons.v1;

public class Consummer extends Thread {
	ProdConsBuffer myBuffer;

	public Consummer(ProdConsBuffer buffer) {
		this.myBuffer = buffer;
		this.start();
	}

	public void run() {
		try {
			Message msgRead = this.myBuffer.get();
			System.out.print("\n" + msgRead.myMessage);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
