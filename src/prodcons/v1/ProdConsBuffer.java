package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] mBuffer;
	int nMessage; // number message
	int numIn;
	int numOut;

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
	synchronized public void put(Message m) throws InterruptedException {
		while (!notFull())
			wait();
		mBuffer[numIn] = m;
		numIn = (numIn+1)%mBuffer.length;
		this.nMessage +=1;
		notifyAll();// Car des thread pourrait etre en attente de message

	}

	@Override
	// Consume
	synchronized public Message get() throws InterruptedException {
		while (!notEmpty())
			wait();
		Message messageOut = mBuffer[numOut];
		numOut = (numOut+1)%mBuffer.length;
		this.nMessage -=1;
		notifyAll();// Car des thread pourrait etre en attente de place dans le buffer
		return messageOut;
	}

	@Override
	public int nmsg() {
		return this.nMessage;
	}

	@Override
	public int totmsg() {
		// TODO Auto-generated method stub
		return 0;
	}

}
