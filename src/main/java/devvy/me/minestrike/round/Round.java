package devvy.me.minestrike.round;

public interface Round {

    public void start();
    public void end();
    public RoundType type();
    public RoundType next();

}
