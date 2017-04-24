package kamisado.logic;

import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;

public class StateHistory {
	
	public List<State> history;
	public int currentState;
	
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
	
	
	public boolean canUndo() {
		return currentState >= 2;
	}
	
	public void undo() {
		if(canUndo())
			currentState -= 2;
	}
	
	public boolean canRedo() {
		return (((history.size()-1) - currentState) >= 2) ;
	}
	
	public void redo() {
		if(canRedo())
			currentState += 2;
	}

}
