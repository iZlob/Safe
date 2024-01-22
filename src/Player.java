
public class Player {
    private int[] _player_variant;

    public Player() {
        _player_variant = new int[4];
    }

    public void setPlayerVariant(String variant) {
        if (variant.length() != 4) throw new RuntimeException("Не корректно введено число!!!");

        for (int i = 0; i < 4; i++) {
            _player_variant[i] = Integer.parseInt(String.valueOf(variant.charAt(i)));
        }
    }

    public int[] getPlayerVariant(){
        return _player_variant;
    }
}