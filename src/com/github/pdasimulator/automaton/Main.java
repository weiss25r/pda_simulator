package com.github.pdasimulator.automaton;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        //pda di test a^nb^n
        State q0 = new State("q0", true, false);
        State q1 = new State("q1", false, false);
        State q2 = new State("q2", false, true);

        q0.addTransition('a', 'Z', q0, "ZA");
        q0.addTransition('a', 'A', q0, "AA");
        q0.addTransition('b', 'A', q1, "l");
        q1.addTransition('b', 'A', q1, "l");
        q1.addTransition('l', 'Z', q2, "Z");

        Automaton pda = new Automaton(Set.of(q0, q1, q2), 'Z', "s", 'l');
        System.out.println(pda.run("aaaabbbb"));
    }
}
