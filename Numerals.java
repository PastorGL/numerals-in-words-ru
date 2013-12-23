import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * Класс для работы с числами
 * 
 */
public class Numerals {
    private Gender genderWh;

    private String[] pluralsWh;

    private Gender genderFr;

    private String[] pluralsFr;

    public static enum Gender {
        /**
         * Женский род
         */
        F,
        /**
         * Мужской род
         */
        M,
        /**
         * Средний род
         */
        N,
        /**
         * Двойственное число (ножницы, сани)
         */
        D
    };

    private static class Thousand {
        private Gender gender;
        private String[] plurals;

        private Thousand(Gender g, String[] pl) {
            this.gender = g;
            this.plurals = pl;
        }
    }

    private static String WHOLE = "целых";

    private static String ZERO = "ноль";

    private static HashMap<Gender, String[]> ONES = new HashMap<Gender, String[]>() {
        {
            put(Gender.M, new String[] { "", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь",
                    "девять" });
            put(Gender.F, new String[] { "", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь",
                    "девять" });
            put(Gender.N, new String[] { "", "одно", "два", "три", "четыре", "пять", "шесть", "семь", "восемь",
                    "девять" });
            put(Gender.D, new String[] { "", "одни", "двое", "трое", "четверо", "пятеро", "шестеро", "семеро",
                    "восемь", "девять" });
        }
    };

    private static String[] HUNDREDS = { "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот",
            "восемьсот", "девятьсот" };

    private static String[] TEENS = { "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать" };

    private static String[] TENS = { "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят",
            "восемьдесят", "девяносто" };

    private static Thousand[] THOUSANDS = { new Thousand(Gender.F, new String[] { "тысяча", "тысячи", "тысяч" }),
            new Thousand(Gender.M, new String[] { "миллион", "миллиона", "миллионов" }),
            new Thousand(Gender.M, new String[] { "миллиард", "миллиарда", "миллиардов" }),
            new Thousand(Gender.M, new String[] { "триллион", "триллиона", "триллионов" }) };

    private static String[] FRACTIONS = { "десятых", "сотых", "тысячных", "десятитысячных", "стотысячных",
            "миллионных", "десятимиллионных", "стомиллионных", "миллиардных", "десятимиллиардных", "стомиллиардных",
            "триллионных" };

    public Numerals(Gender gender, String[] plurals) {
        this.genderWh = gender;
        this.pluralsWh = plurals;
    }

    public Numerals(Gender genderWh, String[] pluralsWh, Gender genderFr, String[] pluralsFr) {
        this.genderWh = genderWh;
        this.pluralsWh = pluralsWh;
        this.genderFr = genderFr;
        this.pluralsFr = pluralsFr;
    }

    public String unwind(BigDecimal amount) {
        String str = amount.toPlainString();
        if (!str.contains(".")) {
            str += ".0";
        }

        // 1231232.1232 -> [1231232, 1232]
        String[] moi = str.split("\\.");

        String strWh = moi[0];
        String strFr = moi[1];

        // 1231232 -> [1, 231, 232]
        ArrayList<String> wh = new ArrayList<>();
        do {
            int end = strWh.length() - 3;
            if (end < 0) {
                end = 0;
            }
            wh.add(strWh.substring(end));
            strWh = strWh.substring(0, end);
        } while (strWh.length() > 0);
        Collections.reverse(wh);

        // 1232 -> [1, 232]
        ArrayList<String> fr = new ArrayList<>();
        do {
            int end = strFr.length() - 3;
            if (end < 0) {
                end = 0;
            }
            fr.add(strFr.substring(end));
            strFr = strFr.substring(0, end);
        } while (strFr.length() > 0);
        Collections.reverse(fr);

        ArrayList<String> res = new ArrayList<>();

        if (wh.get(0).equals("0")) {
            res.add(ZERO);

            if (genderFr == null) {
                String whole = morph(0, pluralsWh);
                res.add(whole.isEmpty() ? WHOLE : whole);
            } else {
                res.add(pluralsWh[pluralsWh.length - 1]);
            }
        } else {
            do {
                String[] ones;
                Thousand thousand = null;
                int thou = wh.size() - 1;
                if (thou > 0) {
                    thousand = THOUSANDS[thou - 1];
                    ones = ONES.get(thousand.gender);
                } else {
                    ones = ONES.get(genderWh);
                }

                String w = wh.remove(0);

                int n = new Integer(w);

                if (n > 99) {
                    String hundred = w.substring(0, 1);

                    res.add(HUNDREDS[new Integer(hundred) - 1]);
                    w = w.substring(1);
                }

                int m = new Integer(w);

                if (m > 19) {
                    String ten = w.substring(0, 1);

                    res.add(TENS[new Integer(ten) - 2]);

                    w = w.substring(1);

                    m = new Integer(w);

                    res.add(ones[m]);
                } else if (m > 9) {
                    res.add(TEENS[m - 10]);
                } else {
                    res.add(ones[m]);
                }

                if (thousand != null) {
                    if (n != 0) {
                        res.add(morph(n, thousand.plurals));
                    }
                } else if (genderFr == null) {
                    String whole = morph(n, pluralsWh);
                    res.add(whole.isEmpty() ? WHOLE : whole);
                } else {
                    res.add(morph(n, pluralsWh));
                }
            } while (wh.size() > 0);
        }

        if (!fr.get(0).equals("0")) {
            do {
                String[] ones;
                Thousand thousand = null;
                int thou = fr.size() - 1;
                if (thou > 0) {
                    thousand = THOUSANDS[thou - 1];
                    ones = ONES.get(thousand.gender);
                } else {
                    if (genderFr != null) {
                        ones = ONES.get(genderFr);
                    } else {
                        ones = ONES.get(Gender.F);
                    }
                }

                String w = fr.remove(0);

                int n = new Integer(w);

                if (n > 99) {
                    String hundred = w.substring(0, 1);

                    res.add(HUNDREDS[new Integer(hundred) - 1]);
                    w = w.substring(1);
                }

                int m = new Integer(w);

                if (m > 19) {
                    String ten = w.substring(0, 1);

                    res.add(TENS[new Integer(ten) - 2]);

                    w = w.substring(1);

                    m = new Integer(w);

                    res.add(ones[m]);
                } else if (m > 9) {
                    res.add(TEENS[m - 10]);
                } else {
                    res.add(ones[m]);
                }

                if (thousand != null) {
                    if (n != 0) {
                        res.add(morph(n, thousand.plurals));
                    }
                } else if (genderFr != null) {
                    res.add(morph(n, pluralsFr));
                } else {
                    res.add(FRACTIONS[moi[1].length() - 1]);
                }
            } while (fr.size() > 0);
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String s : res) {
            if (!s.isEmpty()) {
                sb.append(sep).append(s);
                sep = " ";
            }
        }

        return sb.toString();
    }

    public static String morph(long count, String... pl) {
        if ((count % 10 == 1) && (count % 100 != 11)) {
            return pl[0];
        }

        if ((count % 10 >= 2) && (count % 10 <= 4) && ((count % 100 < 10) || (count % 100 >= 20))) {
            return pl[1];
        }

        return pl[2];
    }
}
