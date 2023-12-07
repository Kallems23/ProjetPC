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

	/**
	 * @return
	 */
	public boolean read() {
		synchronized (this) { // Pour sécurisé l'incrementation
			nexemplairesrestants--;
		}
		if (nexemplairesrestants == 0) {
			mbloquant.release(nexemplaires);
			return true;
		} else {
			try {
				mbloquant.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mbloquant.release();
			return false;
		}

	}
}
