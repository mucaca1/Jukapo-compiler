package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SupportFunctions {
    public static String readLineByLine(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static boolean parseBoolean(int firstArgument, String logicOperator, int secondArgument) {
        switch (logicOperator){
            case "==":
                if (firstArgument == secondArgument)
                    return true;
                break;
            case "!=":
                if (firstArgument != secondArgument)
                    return true;
                break;
            case "<=":
                if (firstArgument <= secondArgument)
                    return true;
                break;
            case ">=":
                if (firstArgument >= secondArgument)
                    return true;
                break;
            case "<":
                if (firstArgument < secondArgument)
                    return true;
                break;
            case ">":
                if (firstArgument > secondArgument)
                    return true;
                break;
        }
        return false;
    }
}
