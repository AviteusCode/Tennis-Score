package org.tennis.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;

public class TennisScoreServiceTest {

    TennisScoreService tennisScoreService = new TennisScoreService();

    @AfterEach
    void clean(){
        MapAppender.clear();
    }

    @Test
    public void shouldReturnScore(){
        //given
        String input = "ABABAA";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Player A : 40 / Player B : 30",
                "Player A wins the game"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnScore2(){
        //given
        String input = "ABBBA";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 15 / Player B : 30",
                "Player A : 15 / Player B : 40",
                "Player A : 30 / Player B : 40"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnDeuce(){
        //given
        String input = "ABAABB";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnAdvantage(){
        //given
        String input = "ABABABA";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage A"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldUndoAdvantage(){
        //given
        String input = "ABAABBAB";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage A",
                "Deuce"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldRedoAdvantage(){
        //given
        String input = "ABAABBABA";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage A",
                "Deuce",
                "Advantage A"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldDoOppositeAdvantage(){
        //given
        String input = "ABAABBABB";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage A",
                "Deuce",
                "Advantage B"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnBWinner(){
        //given
        String input = "ABABAA";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 30 / Player B : 30",
                "Player A : 40 / Player B : 30",
                "Player A wins the game"
        );
        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnAWinnerAfterAdvantage(){
        //given
        String input = "ABAABBAA";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 40 / Player B : 15",
                "Player A : 40 / Player B : 30",
                "Deuce",
                "Advantage A",
                "Player A wins the game"
        );

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnAnErrorIfEntryIsNull(){
        //given
        String input = null;
        List<String> expected = List.of("empty input received");

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldReturnAnErrorIfEntryIsEmpty(){
        //given
        String input = "";
        List<String> expected = List.of("empty input received");

        //when
        tennisScoreService.computeScore(input);

        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldStopAndPrintAnErrorAfterVictory(){
        //given
        String input = "ABAAAB";
        List<String> expected = List.of(
                "Player A : 15 / Player B : 0",
                "Player A : 15 / Player B : 15",
                "Player A : 30 / Player B : 15",
                "Player A : 40 / Player B : 15",
                "Player A wins the game",
                "game should be over but still getting input");

        //when
        tennisScoreService.computeScore(input);


        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

    @Test
    public void shouldNotStartIfErrorInEntry(){
        //given
        String input = "ABCABA";
        List<String> expected = List.of("input ABCABA contain unknown character");

        //when
        tennisScoreService.computeScore(input);


        //then
        List<String> result = MapAppender.getEventList();

        Assertions.assertEquals(expected.size(), result.size());

        Assertions.assertTrue(
                result.containsAll(expected)
        );
    }

}
