package givememoney.table;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Contains all of the properties of a single game state, i.e.:
 *  -Number of players and individual player properties
 *  -Current player turn
 *  -Type of pokergame/turn system
 *  -Time until next action and/or move blinds
 *  etc.
 *
 * This class is intended to be used to save the state of the game so that we can have a
 * compact 'undo' system. PokerTable should have a Game object as currentGame and a stack of
 * Game objects for undo.
 *
 * Alternatively, some other class besides PokerTable (maybe pokergame) could
 * serve as the "master class".
 *
 * Created by Joey on 08/10/2016.
 */

public class Game {
    private int m_numPlayers;
    private List<Player> players;
    private int m_playerTurn;
    private double m_totalPot;
    private double m_lastBet;
    private List<Double> m_sidePots;
    private int m_stack;


    public Game() {

        m_numPlayers = 10;
        players = new ArrayList<Player>(m_numPlayers);
        players.add(new Player(600));
        m_playerTurn = 0;
        System.out.println("successfully created Game object");
    }

    public Game(int numPlayers, int stack) throws IndexOutOfBoundsException {

        if (numPlayers <=0 )
            throw new IndexOutOfBoundsException();

        m_numPlayers = numPlayers;
        m_stack = stack;

        players = new ArrayList<Player>(m_numPlayers);

        for (int i = 0; i < m_numPlayers; i++) {
            players.add(new Player(m_stack, "PLAYER " + Integer.toString(i)));
        }

        m_playerTurn = 0;
        m_totalPot = 0;
        m_sidePots = new ArrayList<Double>(m_numPlayers);
        m_lastBet = -1;

    }


    //---getters-----
    public Player getCurrentPlayer(){ return players.get(m_playerTurn);}
    public int getCurrentPlayerID() { return m_playerTurn;}
    public int getNumPlayers() {return m_numPlayers;}
    public double getLastBet(){return m_lastBet;}
    public List<Player> getPlayerList(){return players;}

    public String[] getStacks() {
        String[] stacks = new String[m_numPlayers];

        for (int i = 0; i < m_numPlayers; i++)
            stacks[i] = Double.toString(players.get(i).getCash());

        return stacks;
    }

    public String[] getBets() {
        String[] bets = new String[m_numPlayers];

        for (int i = 0; i < m_numPlayers; i++)
            bets[i] = Double.toString(players.get(i).getBet());

        return bets;
    }

    public Player.Status[] getStatuses() {
        Player.Status[] statuses = new Player.Status[m_numPlayers];

        for (int i = 0; i < m_numPlayers; i++)
            statuses[i] = players.get(i).getStatus();

        return statuses;
    }

    public String[] getNames() {
        String[] names = new String[m_numPlayers];

        for (int i = 0; i < m_numPlayers; i++)
            names[i] = players.get(i).getName();

        return names;
    }


    //------setters----------
    public void setLastBet(double lastBet){m_lastBet = lastBet;}
    public void clearBets() {
        for (int i = 0; i < m_numPlayers; i++)
            players.get(i).setBet(0);
        m_lastBet = 0;
    }

    //------real functions-------
    public void cycleActivePlayer() {
        if (m_numPlayers == 1)
            return;

        getCurrentPlayer().setStatus(Player.Status.WAITING);

        if (m_playerTurn + 1 >= m_numPlayers) {
            for (int i = 0; i < m_numPlayers; i++) {
                if (m_playerTurn == i) {
                    //game should become inactive
                }
                if (players.get(i).sittingOut()) {
                    players.get(i).setStatus(Player.Status.IDLE);
                }
                if (players.get(i).getStatus() == Player.Status.WAITING) {
                    m_playerTurn = i;
                    getCurrentPlayer().setStatus(Player.Status.ACTIVE);
                    clearBets();
                    return;
                }
            }
        }

        for (int j = m_playerTurn + 1; j < m_numPlayers; j++) {
            if (players.get(j).sittingOut()) {
                players.get(j).setStatus(Player.Status.IDLE);
            }
            if (players.get(j).getStatus() == Player.Status.WAITING) {
                m_playerTurn = j;
                getCurrentPlayer().setStatus(Player.Status.ACTIVE);
                return;
            }
            if (j + 1 >= m_playerTurn) {
                throw new IndexOutOfBoundsException("Noone is playing anymore!");
            }
        }

    }

    public String toString() {
        String returnString =
        "\n-----Current Game Info------\n" +
        " Number of Players: " + m_numPlayers + "\n" +
        " Total Pot: " + m_totalPot + "\n" +
        " Current player turn ID: " + players.get(m_playerTurn).toString() + "\n" +
        " \n\nList of Players: " + players.toString() + "\n" + "------------\n";

        return returnString;
    }

    public void consoleLog() {
        System.out.println(this.toString());
    }



    //adapter
}
