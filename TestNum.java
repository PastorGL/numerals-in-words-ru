import java.math.BigDecimal;

import Numerals.*;

public class TestNum {

    public static void main(String[] args) {
        Numerals num = new Numerals(Gender.M, new String[] { "", "", "" } );
     System.out.println(num.unwind(new BigDecimal("1234538.48393")));
        System.out.println(num.unwind(new BigDecimal("001.8393")));
        System.out.println(num.unwind(new BigDecimal("234538.4")));
        System.out.println(num.unwind(new BigDecimal("38.1")));
        System.out.println(num.unwind(new BigDecimal("38.100000")));
        System.out.println(num.unwind(new BigDecimal("38.100099")));
        System.out.println(num.unwind(new BigDecimal("38.100009")));
        System.out.println(num.unwind(new BigDecimal("38.001")));
        System.out.println(num.unwind(new BigDecimal("38.01")));
        System.out.println(num.unwind(new BigDecimal("38.01020")));
        System.out.println(num.unwind(new BigDecimal("38.010")));
        System.out.println(num.unwind(new BigDecimal("38.0100")));
        System.out.println(num.unwind(new BigDecimal("5.025")));
        System.out.println(num.unwind(new BigDecimal("5.025001")));
        System.out.println(num.unwind(new BigDecimal("5.025012")));
        System.out.println(num.unwind(new BigDecimal("5.025025")));
        System.out.println(num.unwind(new BigDecimal("5.005025")));

        num = new Numerals(Gender.D, new String[] { "сани", "саней", "саней" } );
        System.out.println(num.unwind(new BigDecimal("1234538.48393")));
        System.out.println(num.unwind(new BigDecimal("1.8393")));
        System.out.println(num.unwind(new BigDecimal("234538.4")));
        System.out.println(num.unwind(new BigDecimal("2.11")));

        num = new Numerals(Gender.M, new String[] { "рубль", "рубля", "рублей" }, Gender.F, new String[] { "копейка", "копейки", "копеек" } );
        System.out.println(num.unwind(new BigDecimal("1234538.48393")));
        System.out.println(num.unwind(new BigDecimal("1.8393")));
        System.out.println(num.unwind(new BigDecimal("234538.4")));
        System.out.println(num.unwind(new BigDecimal("38.10")));
    }
}
