package com.khk.mgt.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ColorUtil {
    private static final Random RANDOM = new Random();

    private static final String[] DEFAULT_COLOR = {
            "#017AC9",
            "#D43A3A",
            "#FD7B0A",
            "#EDEE55",
            "#63A92D",
            "#6C4BA4"
    };

    // Merge with default color and if exceed random color generate
    public static List<String> generateColorList(int count) {
        List<String> colorList;
        if (count <= DEFAULT_COLOR.length) {
            colorList = Arrays.asList(
                    Arrays.copyOfRange(DEFAULT_COLOR,0,count)
            );
            return colorList;
        }else {
            colorList = Arrays.asList(DEFAULT_COLOR);
            int generateColorCount = count - DEFAULT_COLOR.length;
            colorList.addAll(generatePastelColors(generateColorCount));
            return colorList;
        }

    }

    // Generate a list of N pastel colors
    public static List<String> generatePastelColors(int count) {
        return java.util.stream.IntStream.range(0, count)
                .mapToObj(i -> generatePastelColor())
                .collect(Collectors.toList());
    }

    // Generate a pastel color (lighter and more pleasing)
    public static String generatePastelColor() {
        int r = 128 + RANDOM.nextInt(128); // 128–255
        int g = 128 + RANDOM.nextInt(128);
        int b = 128 + RANDOM.nextInt(128);
        return String.format("#%02X%02X%02X", r, g, b);
    }

}
