package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer{

	Message[] mBuffer;
	int nMessage; //number message
	
	public ProdConsBuffer(int bufferSize) {
		this.mBuffer = new Message[bufferSize];
	}
	
	private boolean notFull() {
		return nmsg() < this.mBuffer.length;
	}
	private boolean notEmpty() {
		return nmsg() > 0;
	}
	@Override
	//Produce
	public void put(Message m) throws InterruptedException { 
		
	}

	@Override
	//Consume
	public Message get() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nmsg() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totmsg() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
