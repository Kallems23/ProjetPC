package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] mBuffer;
	int totalMsg;
	int nMessage; // number message
	int numIn;
	int numOut;

	public ProdConsBuffer(int bufferSize) {
		this.mBuffer = new Message[bufferSize];
		this.numIn = 0;
		this.numOut = 0;
	}

	public boolean notFull() {
		return nmsg() < this.mBuffer.length;
	}

	public boolean notEmpty() {
		return nmsg() > 0;
	}

	@Override
	// Produce
	synchronized public void put(Message m) throws InterruptedException {
		mBuffer[numIn] = m;
		numIn = (numIn + 1) % mBuffer.length;
		this.nMessage += 1;
		this.totalMsg += 1;
		m.myMessage = "msg nbr = " + this.totalMsg + " | " + m.myMessage;

	}

	@Override
	// Consume
	synchronized public Message get() throws InterruptedException {
		Message messageOut = mBuffer[numOut];
		numOut = (numOut + 1) % mBuffer.length;
		this.nMessage -= 1;
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
