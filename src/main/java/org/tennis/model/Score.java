package org.tennis.model;

public enum Score {
  ZERO("0"),
  FIFTEEN("15"),
  THIRTY("30"),
  FORTY("40"),
  ADVANTAGE(""),
  WIN("");

  private final String score;

  Score(String score) {
    this.score = score;
  }

  public String getScore() {
    return score;
  }
}
