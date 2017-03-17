package kamisado_logic;

import java.util.ArrayList;
import java.util.List;

public class StateHistory {
	
	List<State> history;
	
	public StateHistory() {
		history = new ArrayList<>();
	}
	
	public void addState(State state) {
		history.add(state);
	}

}
