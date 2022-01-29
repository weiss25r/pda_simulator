package tcsproject.pda;

import java.util.Map.Entry;
import java.util.*;

/**
// Models a PDA state.
//
 */
public class State {
    private final String label;
    private final boolean initial, terminal;
    private Map<String, Set<State>> successors;

    public State(String label, boolean initial, boolean terminal) {
        this.label = label;
        this.initial = initial;
        this.terminal = terminal;

        successors = new LinkedHashMap<>();
    }

    public String getLabel() {
        return label;
    }

    public boolean isInitial() {
        return initial;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public Map<String, Set<State>> getSuccessors() {
        return new HashMap<>(successors);
    }

    public void addTransition(char input, char stackPeek, State nextState, String toPush) {
        StringBuilder str = new StringBuilder();
        str.append(input);
        str.append(stackPeek);
        str.append(toPush);

        if(this.successors.containsKey(str.toString())) {
            this.successors.get(str.toString()).add(nextState);
        }

        else {
            LinkedHashSet<State> newSet = new LinkedHashSet<>();
            newSet.add(nextState);
            this.successors.put(str.toString(), newSet);
        }
    }

}
