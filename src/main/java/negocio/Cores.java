package negocio;

public enum Cores {
    BLACK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PURPLE,
    CYAN,
    WHITE;
    public static Cores fromString(String corAnsi) {
        if (corAnsi != null) {
            try {
                return Cores.valueOf(corAnsi.toUpperCase());
            } catch (IllegalArgumentException e) {
                return WHITE;
            }
        }
        return WHITE;
    }
}
