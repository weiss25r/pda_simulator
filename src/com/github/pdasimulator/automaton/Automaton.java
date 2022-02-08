package com.github.pdasimulator.automaton;

import java.util.*;
import java.util.stream.Collectors;

public class Automaton {
    private final Set<State> states;
    private LinkedList<Character> stack;
    private final String acceptMode;
    private final char emptyStringSymbol;

    public Automaton(Set<State> states, char firstStackSymbol, String acceptMode, char emptyStringSymbol) {
        this.states = new LinkedHashSet<>(states);
        stack = new LinkedList<>();
        stack.push(firstStackSymbol);

        //TODO: matches con regex
        this.acceptMode = acceptMode;
        this.emptyStringSymbol = emptyStringSymbol;
    }

    public boolean run(String input) {
        List<Character> startingStack = new LinkedList<>(this.stack);
        State initialState = states.stream().filter(State::isInitial).findFirst().get();
        boolean result = runRecursive(input, initialState);
        this.stack = new LinkedList<>(startingStack);
        return result;
    }

    private boolean runRecursive(String input, State currentState) {
        //base case
        //input == 0 && there are no epsilon transitions
        char peek = pop();

        //get epsilon-transition
        List<String> epsilonTransitions = currentState
                .getSuccessors()
                .keySet()
                .stream()
                .filter(s -> s.startsWith(Character.toString(emptyStringSymbol) + peek)).toList();

        List<String> otherTransitions = currentState
                .getSuccessors()
                .keySet()
                .stream()
                .filter(s -> input.length() > 0 && (s.startsWith(input.charAt(0) + Character.toString(peek)) ||
                        s.startsWith(input.charAt(0) + Character.toString(emptyStringSymbol)))).toList();

        //base case
        if(epsilonTransitions.isEmpty() && otherTransitions.isEmpty() && input.equals(""))
            return acceptMode.equals("s") ? this.stack.isEmpty() : currentState.isTerminal();

        LinkedList<Character> actualStack = new LinkedList<>(this.stack);
        boolean result = false;

        //gestione delle altre transizioni

        for(String key : otherTransitions) {
            Set<State> nextStates = currentState.getSuccessors().get(key);
            for(State s : nextStates) {

                char[] toPush = key.substring(2).toCharArray();

                //toPush.length is never 0
                if(toPush[0] != emptyStringSymbol) {
                    for(int i = 0; i < toPush.length; i++)
                        stack.push(toPush[i]);
                }

                String newInput = input.length() > 1 ? input.substring(1) : "";
                result = runRecursive(newInput, s);
                this.stack = new LinkedList<>(actualStack);
            }
        }

        if(result) return result;

        for(String key : epsilonTransitions) {
            Set<State> nextStates = currentState.getSuccessors().get(key);
            for(State s : nextStates) {
                actualStack = new LinkedList<>(this.stack);

                char[] toPush = key.substring(2).toCharArray();

                //toPush.length is never 0
                if(toPush[0] != emptyStringSymbol) {
                    for(int i = 0; i < toPush.length; i++)
                        stack.push(toPush[i]);
                }

                result = runRecursive(input, s);
                this.stack = new LinkedList<>(actualStack);
            }
        }
        return result;
    }

    private char pop() {
        return stack.isEmpty() ? emptyStringSymbol : stack.pop();
    }
}
