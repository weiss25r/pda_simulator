package com.github.pdasimulator.automaton;

import java.util.*;
import java.util.stream.Collectors;

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
        str.append(new StringBuilder(toPush).reverse());

        if(this.successors.containsKey(str.toString())) {
            this.successors.get(str.toString()).add(nextState);
        }

        else {
            LinkedHashSet<State> newSet = new LinkedHashSet<>();
            newSet.add(nextState);
            this.successors.put(str.toString(), newSet);
        }
    }

    public void deleteTransition(String transition) {
        this.successors.remove(transition);
    }

    //generated automatically by IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return initial == state.initial && terminal == state.terminal && Objects.equals(label, state.label) && Objects.equals(successors, state.successors);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    //TODO: equals, hashCode, toString

    @Override
    public String toString() {
        return this.label + (initial ? ": initial" : terminal ? ": final" : "");
    }

}
