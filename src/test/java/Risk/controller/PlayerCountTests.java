package Risk.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerCountTests {

    @Test
    public void illegalyLowPlayerCount(){
        PlayerController pc = new PlayerController();
        int expected = -1;

        int actual = pc.setNumberOfPlayers(2);

        assertEquals(actual, expected);
    }

    @Test
    public void illegalyHighPlayerCount(){
        PlayerController pc = new PlayerController();
        int expected = -1;

        int actual = pc.setNumberOfPlayers(7);

        assertEquals(actual, expected);
    }

    @Test
    public void legalPlayerCount3(){
        PlayerController pc = new PlayerController();
        int expected = 35;

        int actual = pc.setNumberOfPlayers(3);

        assertEquals(actual, expected);
    }

    @Test
    public void legalPlayerCount4(){
        PlayerController pc = new PlayerController();
        int expected = 30;

        int actual = pc.setNumberOfPlayers(4);

        assertEquals(actual, expected);
    }

    @Test
    public void legalPlayerCount5(){
        PlayerController pc = new PlayerController();
        int expected = 25;

        int actual = pc.setNumberOfPlayers(5);

        assertEquals(actual, expected);
    }

    @Test
    public void legalPlayerCount6(){
        PlayerController pc = new PlayerController();
        int expected = 20;

        int actual = pc.setNumberOfPlayers(6);

        assertEquals(actual, expected);
    }



}
