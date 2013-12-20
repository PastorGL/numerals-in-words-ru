import java.math.BigDecimal;

import com.epam.gzam.apnt.util.Numerals.*;

public class TestNum {

    public static void main(String[] args) {
        Numerals num = new Numerals(Gender.M, new String[] { "", "", "" } );
        System.out.println(num.unwind(new BigDecimal("1234538.48393")));
        System.out.println(num.unwind(new BigDecimal("1.8393")));
        System.out.println(num.unwind(new BigDecimal("234538.4")));
        System.out.println(num.unwind(new BigDecimal("38.1")));

        num = new Numerals(Gender.D, new String[] { "сани", "саней", "саней" } );
        System.out.println(num.unwind(new BigDecimal("1234538.48393")));
        System.out.println(num.unwind(new BigDecimal("1.8393")));
        System.out.println(num.unwind(new BigDecimal("234538.4")));
        System.out.println(num.unwind(new BigDecimal("2.11")));

        num = new Numerals(Gender.M, new String[] { "рубль", "рубля", "рублей" }, Gender.F, new String[] { "копейка", "копейки", "копеек" } );
        System.out.println(num.unwind(new BigDecimal("1234538.48")));
        System.out.println(num.unwind(new BigDecimal("1.83")));
        System.out.println(num.unwind(new BigDecimal("234538.04")));
        System.out.println(num.unwind(new BigDecimal("38.10")));
    }
}
