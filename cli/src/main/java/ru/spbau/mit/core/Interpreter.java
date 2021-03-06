package ru.spbau.mit.core;

import ru.spbau.mit.commands.Command;
import ru.spbau.mit.commands.CommandFactory;
import ru.spbau.mit.core.exceptions.InterpretException;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that interpret tokens to a commands
 */
public final class Interpreter {
    private Interpreter() {}

    /**
     * Takes a list of tokens from Tokenizer and translates them to commands.
     * It splits tokens to groups called "tasks" by "|".
     * From every task created a command.
     * @param tokens array of independent strings to interpret in commands
     * @return list of commands
     * @throws InterpretException - throws exception if there were no tokens
     * between two pipes.
     */
    public static List<Command> interpret(final List<String> tokens)
            throws InterpretException {
        List<Command> commands = new ArrayList<>();
        for (List<String> task : getTasks(tokens)) {
            commands.add(CommandFactory.getCommand(task));
        }
        return commands;
    }

    /**
     * Splits strings by pipe.
     * @param tokens array of independent strings to interpret in commands
     * @return list of list of strings
     * @throws InterpretException - throws exception if there were no tokens
     * between two pipes.
     */
    private static List<List<String>> getTasks(final List<String> tokens)
            throws InterpretException {
        List<List<String>> tasks = new ArrayList<>();
        List<String> task = new ArrayList<>();

        for (String token : tokens) {
            if (token.equals("|")) {
                if (task.size() == 0) {
                    throw new InterpretException("syntax error near "
                            + "unexpected token '|'");
                }
                tasks.add(task);
                task = new ArrayList<>();
            } else {
                task.add(token);
            }
        }
        if (task.size() != 0) {
            tasks.add(task);
        }

        return tasks;
    }
}
