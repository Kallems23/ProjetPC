package prodcons.v6;

import java.util.concurrent.Semaphore;

public class Message {
	String myMessage;
	Semaphore mbloquant;
	int nexemplaires;
	int nexemplairesrestants;

	public Message(String myMessage, int nexemplaires) {
		this.myMessage = myMessage;
		mbloquant = new Semaphore(0);
		this.nexemplaires = nexemplaires;
		nexemplairesrestants = nexemplaires;
	}

	public void read() {
		nexemplairesrestants--;
		if (nexemplairesrestants == 0)
			mbloquant.release(nexemplaires);
		else {
			try {
				mbloquant.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mbloquant.release();
		}

	}
}
