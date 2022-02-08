package com.github.pdasimulator.automaton;

import java.util.Set;

public class Test2 {

    public static void main(String[] args) {
        //pda di test palindrome pari
        State q0 = new State("q0", true, false);
        State q1 = new State("q1", false, false);

        q0.addTransition('a', 'Z', q0, "AZ");
        q0.addTransition('b', 'Z', q0, "BZ");
        q0.addTransition('a', 'A', q0, "AA");
        q0.addTransition('a', 'A', q1, "l");
        q0.addTransition('b', 'A', q0, "BA");
        q0.addTransition('a', 'B', q0, "AB");
        q0.addTransition('b', 'B', q0, "BB");
        q0.addTransition('b', 'B', q1, "l");

        q1.addTransition('l', 'Z', q1, "l");
        q1.addTransition('a', 'A', q1, "l");
        q1.addTransition('b', 'B', q1, "l");

        Automaton pda = new Automaton(Set.of(q0, q1), 'Z', "s", 'l');
        System.out.println(pda.run("aa"));
    }
}
