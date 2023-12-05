package prodcons.v5;

import java.util.Iterator;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] mBuffer;
	int totalMsg;
	int nMessage; // number message
	int numIn;
	int numOut;
	int nprio;// flag priority

	public ProdConsBuffer(int bufferSize) {
		this.mBuffer = new Message[bufferSize];
		this.numIn = 0;
		this.numOut = 0;
		this.nprio = 0;
	}

	private boolean notFull() {
		return nmsg() < this.mBuffer.length;
	}

	private boolean notEmpty() {
		return nmsg() > 0;
	}

	@Override
	// Produce
	synchronized public void put(Message m) throws InterruptedException {
		while (!notFull())
			wait();
		mBuffer[numIn] = m;
		numIn = (numIn + 1) % mBuffer.length;
		this.nMessage += 1;
		this.totalMsg += 1;
		m.myMessage = "msg nbr = " + this.totalMsg + " | " + m.myMessage;
		notifyAll();// Car des thread pourrait etre en attente de message

	}

	@Override
	// Consume
	synchronized public Message get() throws InterruptedException {
		while (!notEmpty() && nprio == 0)
			wait();
		Message messageOut = mBuffer[numOut];
		numOut = (numOut + 1) % mBuffer.length;
		this.nMessage -= 1;
		notifyAll();// Car des thread pourrait etre en attente de place dans le buffer
		return messageOut;
	}

	private Message getPrio() throws InterruptedException {
		Message messageOut = mBuffer[numOut];
		numOut = (numOut + 1) % mBuffer.length;
		this.nMessage -= 1;
		return messageOut;
	}

	// Consume k message
	synchronized public Message[] get(int k) throws InterruptedException {
		while (!(nprio == 0))
			wait();
		Message[] msgArray = new Message[k];
		nprio = 1;
		for (int i = 0; i < k; i++) {
			while (!notEmpty())
				wait();
			msgArray[i] = getPrio();
		}
		nprio = 0;
		notifyAll();// Car des thread pourrait etre en attente de place dans le buffer
		return msgArray;
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
