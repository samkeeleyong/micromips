package cycle;

public class WriteBackCycle implements MipCycle{

	private Cycle cycle;

	public String processRn() {
		return null;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
}
