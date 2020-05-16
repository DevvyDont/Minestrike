package devvy.me.minestrike.round;

public enum RoundType {

    BUY(BuyRound.class, ActionRound.class, 20 * 15),
    ACTION(ActionRound.class, IntermissionRound.class, 20 * 120),
    INTERMISSION(IntermissionRound.class, BuyRound.class, 20 * 7);

    public Class<? extends RoundBase> CLAZZ;
    public Class<? extends RoundBase> NEXT;
    public int DEFAULT_TICK_LENGTH;

    RoundType(Class<? extends RoundBase> CLAZZ, Class<? extends RoundBase> NEXT, int length) {
        this.CLAZZ = CLAZZ;
        this.NEXT = NEXT;
        this.DEFAULT_TICK_LENGTH = length;
    }
}
