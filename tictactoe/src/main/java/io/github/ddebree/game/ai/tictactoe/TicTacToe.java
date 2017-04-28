package io.github.ddebree.game.ai.tictactoe;

import io.github.ddebree.game.ai.core.executor.TurnBasedLoopingGameExecutor;
import io.github.ddebree.game.ai.core.executor.player.PlayerExecutor;
import io.github.ddebree.game.ai.core.player.TwoPlayerKey;
import io.github.ddebree.game.ai.core.strategy.prompt.PromptUserStrategy;

import java.util.Scanner;

public class TicTacToe extends TurnBasedLoopingGameExecutor<State, Move> {

    public static void main(String[] args) {
        new TicTacToe()
                .withStateReader(State::new)
                .withPlayer1Executor(PlayerExecutor.<State, TwoPlayerKey, Move>aPlayerExecutor(TwoPlayerKey.PLAYER_1, "Computer Player")
                        .withStrategy(new BoardMinMax())
                )
                .withPlayer2Executor(PlayerExecutor.<State, TwoPlayerKey, Move>aPlayerExecutor(TwoPlayerKey.PLAYER_2, "User Player")
                        .withStrategy(new PromptUserStrategy<>(new MoveFactory()))
                )
                .withFirstPlayerSelector(() -> {
                    System.out.println("Select turn:\n\n1. Computer 2. User: ");
                    int choice = new Scanner(System.in).nextInt();
                    return choice == 1 ? TwoPlayerKey.PLAYER_1 : TwoPlayerKey.PLAYER_2;}
                )
                .withGameOverTester(new GameOverTester())
                .run();
    }

    @Override
    protected State placeMove(State state, Move userMove, TwoPlayerKey turn) {
        return state.placeAMove(userMove, turn);
    }

}