import java.util.Arrays;

public class Backend {

    /**
     * Calculates the percentage of polar and apolar amino acids
     * @param protein The polypeptide chain used to calculate
     * @return a float[] with the percentage of polar amino acids, the percentage of apolar amino acids,
     * and the length of the polypeptide chain.
     * @throws Throwable Used to create a custom exception.
     */
    public static float[] calculate(String protein) throws Throwable {
        String[] aminos = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K"
                , "M", "F", "P", "S", "T", "W", "Y", "V"};
        String[] polair = {"R", "N", "D", "C", "Q", "E", "G", "H", "K", "S", "T", "Y"};
        String[] apolair = {"A", "I", "L", "M", "F", "P", "W", "V"};

        float pol = 0f;
        float apol = 0f;
        float length = protein.length();
        for (int i = 0; i < protein.length(); i++) {
            String amino = Character.toString(protein.charAt(i));
            for (String letter : polair) {
                if (amino.equals(letter)) {
                    pol ++;
                }
            }
            for (String letter : apolair) {
                if (amino.equals(letter)) {
                    apol ++;
                }
            }
            if (!(Arrays.toString(aminos).contains(amino))) {
                throw new NotAnAA("Dit is een niet bestaand aminozuur: '" + amino + "'");
            }
        }
        pol = pol / length * 100;
        apol = apol / length * 100;
        float[] re = new float[3];
        re[0] = pol;
        re[1] = apol;
        re[2] = length;
        return re;
    }
}
class NotAnAA extends Exception {

    public NotAnAA() {
        super();
    }

    public NotAnAA(String err) {
        super(err);
    }
}
