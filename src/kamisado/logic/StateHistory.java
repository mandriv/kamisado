package kamisado.logic;

import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;

public class StateHistory {
	
	List<State> history;
	int currentState;
	
	public StateHistory() {
		history = new ArrayList<>();
		currentState = -1;
	}
	
	public void addState(State state) {
		//head of history
		if (currentState + 1 == history.size()) {
			history.add(state);
		} else {
			history.subList(currentState+1, history.size()).clear();
			history.add(state);
		}
		currentState++;
	}
	
	public State getCurrentState() {
		return history.get(currentState);
	}
	
	public State getPreviousState() throws InvalidActivityException {
		if(currentState > 0) {
			return history.get(--currentState);
		} else {
			throw new InvalidActivityException("Can't get any further back in history!");
		}

		
	}
	
	public State getNextState() throws InvalidActivityException {
		if(currentState < history.size()-1) {
			return history.get(++currentState);
		} else {
			throw new InvalidActivityException("Can't predict future");
		}
	}

}
