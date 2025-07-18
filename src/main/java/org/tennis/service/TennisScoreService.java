package org.tennis.service;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tennis.model.Game;
import org.tennis.model.Pair;
import org.tennis.model.Score;

public class TennisScoreService {
  private static final Pattern GAME_PATTERN = Pattern.compile("^[AB]+$");

  private static final Logger log = LogManager.getLogger(TennisScoreService.class);

  public void computeScore(String input) {
    try {
      validateInput(input);
      processScoreDescription(input);
    } catch (InvalidParameterException e) {
      log.error(e.getMessage());
    }
  }

  private void validateInput(String input) {
    if (input == null || input.isBlank()) {
      throw new InvalidParameterException("empty input received");
    }

    if (!GAME_PATTERN.matcher(input).matches()) {
      throw new InvalidParameterException("input " + input + " contain unknown character");
    }
  }

  private void processScoreDescription(String scoreDescription) {
    Game game = new Game(Score.ZERO, Score.ZERO);

    for (char point : scoreDescription.toCharArray()) {
      game = applyPoint(point, game);
      printScore(game);
    }
  }

  private void printScore(Game game) {
    final Score playerAScore = game.playerAScore();
    final Score playerBScore = game.playerBScore();

    if (playerAScore == Score.WIN) {
      log.info("Player A wins the game");
      return;
    }

    if (playerBScore == Score.WIN) {
      log.info("Player B wins the game");
      return;
    }

    if (playerAScore == Score.ADVANTAGE) {
      log.info("Advantage A");
      return;
    }

    if (playerBScore == Score.ADVANTAGE) {
      log.info("Advantage B");
      return;
    }

    if (playerAScore == Score.FORTY && playerBScore == Score.FORTY) {
      log.info("Deuce");
      return;
    }

    log.info("Player A : {} / Player B : {}", playerAScore.getScore(), playerBScore.getScore());
  }

  private Game applyPoint(char point, Game game) {
    if (point == 'A') {
      Pair<Score, Score> updatedScores = updateScore(game.playerAScore(), game.playerBScore());
      return new Game(updatedScores.left(), updatedScores.right());
    } else {
      Pair<Score, Score> updatedScores = updateScore(game.playerBScore(), game.playerAScore());
      return new Game(updatedScores.right(), updatedScores.left());
    }
  }

  private Pair<Score, Score> updateScore(Score winnerScore, Score looserScore) {
    if (looserScore == Score.WIN) {
      throw new InvalidParameterException("game should be over but still getting input");
    }

    return switch (winnerScore) {
      case ZERO -> new Pair<>(Score.FIFTEEN, looserScore);
      case FIFTEEN -> new Pair<>(Score.THIRTY, looserScore);
      case THIRTY -> new Pair<>(Score.FORTY, looserScore);
      case FORTY -> calculateScoreWhenWinnerIsAtForty(looserScore);
      case ADVANTAGE -> new Pair<>(Score.WIN, looserScore);
      case WIN ->
          throw new InvalidParameterException("game should be over but still getting input");
    };
  }

  private Pair<Score, Score> calculateScoreWhenWinnerIsAtForty(Score looserScore) {
    if (looserScore == Score.ADVANTAGE) {
      return new Pair<>(Score.FORTY, Score.FORTY);
    } else if (looserScore == Score.FORTY) {
      return new Pair<>(Score.ADVANTAGE, looserScore);

    } else {
      return new Pair<>(Score.WIN, looserScore);
    }
  }
}
