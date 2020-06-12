package devvy.me.minestrike.game;

/**
 * Used to represent a state of the game
 */
public enum GameState {

    WAITING,             // Pre-game/Warmup
    BUY_IN_PROGRESS,     // The buy round is currently going
    ROUND_IN_PROGRESS,   // The action round is still playing out
    TEAM_WAS_ELIMINATED, // The action round is over, but one of the teams were eliminated
    TIME_RAN_OUT,        // When the round ends due to time running out
    BOMB_IS_PLANTED,     // The action round is still going, but the bomb is currently planted
    BOMB_IS_DEFUSED,     // The action round is over, but the bomb was defused
    BOMB_EXPLODED,       // The action round is over, but the bomb exploded


}
