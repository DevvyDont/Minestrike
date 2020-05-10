package devvy.me.minestrike.round;

public enum RoundType {

    BUY(BuyRound.class, ActionRound.class),
    ACTION(ActionRound.class, IntermissionRound.class),
    INTERMISSION(IntermissionRound.class, BuyRound.class);

    public Class<? extends Round> CLAZZ;
    public Class<? extends Round> NEXT;

    RoundType(Class<? extends Round> CLAZZ ,Class<? extends Round> NEXT) {
        this.CLAZZ = CLAZZ;
        this.NEXT = NEXT;
    }
}
