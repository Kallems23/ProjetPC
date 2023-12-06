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
		nexemplairesrestants--;
		if (nexemplairesrestants == 0){
			mbloquant.release(nexemplaires+10);
			return true;
		}
		else {
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
