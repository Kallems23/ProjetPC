package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] mBuffer;
	int totalMsg;
	int nMessage; // number message
	int numIn; // numero de la case a ecrire
	int numOut; // numero de la case a lire
	public boolean finishProducing;
	Semaphore fifo = new Semaphore(0, true);

	public ProdConsBuffer(int bufferSize) {
		this.mBuffer = new Message[bufferSize];
		this.numIn = 0;
		this.numOut = 0;
	}

	private boolean notFull() {
		return nmsg() < this.mBuffer.length;
	}

	private boolean notEmpty() {
		return nmsg() > 0;
	}

	@Override
	// Produce
	public void put(Message m) throws InterruptedException {
		fifo.release();
		synchronized (this) {

			while (!notFull())
				wait();
			mBuffer[numIn] = m;
			numIn = (numIn + 1) % mBuffer.length;
			this.nMessage += 1;
			m.myMessage = "msg nbr = " + totmsg() + " | " + m.myMessage;
			notifyAll();// Car des thread pourrait etre en attente de message
		}
	}

	@Override
	// Consume
	public Message get() throws InterruptedException {

		fifo.acquire();

		Message messageOut = null;
		synchronized (this) {

			while (!notEmpty())
				wait();
			 messageOut = mBuffer[numOut];
			numOut = (numOut + 1) % mBuffer.length;
			this.nMessage -= 1;
			this.totalMsg += 1;
			notifyAll();// Car des thread pourrait etre en attente de place dans le buffer
		}
		return messageOut;

	}

	@Override
	public int nmsg() {
		return this.nMessage;
	}

	@Override
	public int totmsg() {
		return totalMsg;
	}

}
