package helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomValueGenerator {

    public static List<Double> generateRandomDoubles(int numOfValues, double min, double max){
        List<Double> doubleList = new ArrayList<>();
        for(int i = 0; i < numOfValues; i++){
            doubleList.add(round(ThreadLocalRandom.current().nextDouble(min, max), 3));
        }
        return doubleList;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
