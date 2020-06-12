package devvy.me.minestrike.phase;

public enum PhaseType {

    BUY(BuyPhase.class, ActionPhase.class, 20 * 15),
    ACTION(ActionPhase.class, IntermissionPhase.class, 20 * 120),
    INTERMISSION(IntermissionPhase.class, BuyPhase.class, 20 * 7);

    public Class<? extends PhaseBase> CLAZZ;
    public Class<? extends PhaseBase> NEXT;
    public int DEFAULT_TICK_LENGTH;

    PhaseType(Class<? extends PhaseBase> CLAZZ, Class<? extends PhaseBase> NEXT, int length) {
        this.CLAZZ = CLAZZ;
        this.NEXT = NEXT;
        this.DEFAULT_TICK_LENGTH = length;
    }
}
