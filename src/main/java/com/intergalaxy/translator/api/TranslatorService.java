package com.intergalaxy.translator.api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.intergalaxy.translator.api.Constants.*;

@Service
public class TranslatorService {


    LinkedHashMap<String, String> translatorDB = new LinkedHashMap<>();
    LinkedHashMap<String, Float> wordToCalculatedValueTranslatorDB = new LinkedHashMap<>();
    LinkedHashMap<String, String> wordToRomanTranslatorDB = new LinkedHashMap<>();
    LinkedHashMap<String, Float> romanNumberToDecimalValueTranslatorDB = new LinkedHashMap<>();

    ArrayList<String> result = new ArrayList<>();

    public String validateInput(String input) {
        String[] lines = input.split(System.getProperty("line.separator"));
        for (String line : lines) {
            if (ASSIGNED_PATTERN.matcher(line).matches()) {
                processASSIGNED(line);
            } else if (Constants.VALUE_PATTERN.matcher(line).matches()) {
                processVALUE_PATTERN(line);
            } else if (MUCH_QUESTION_PATTERN.matcher(line).matches()) {
                processMUCH_QUESTION(line);
            } else if (MANY_QUESTION_PATTERN.matcher(line).matches()) {
                processMANY_QUESTION(line);
            } else {
                result.add(I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT);
            }
        }
        return result.toString();
    }

    private void processMANY_QUESTION(String line) {
        try {
            String formated = line.toLowerCase().split("\\sis\\s")[1].replace("how", "").replace("many", "").replace("credits", "").replace("?", "").trim();
            String[] words = formated.trim().split("\\s+");
            String romanNumber = "";
            String resultLine = "";
            float totalCanculatedValue = 1;
            for (int i = 0; i < words.length; i++) {

                if (wordToRomanTranslatorDB.get(words[i]) != null) {
                    romanNumber += wordToRomanTranslatorDB.get(words[i]);
                } else if (wordToCalculatedValueTranslatorDB.get(words[i]) != null) {
                    totalCanculatedValue = totalCanculatedValue * wordToCalculatedValueTranslatorDB.get(words[i]);
                } else {
                    throw new Exception();
                }

                resultLine += words[i] + " ";

            }
            if (!Util.validateRomanNumber(romanNumber.toUpperCase())) {
                throw new Exception();
            }
            int decimalNumber=1;
            if(romanNumber!=""){
                decimalNumber = Util.romanToDecimal(romanNumber.toUpperCase());
            }
            Float totalValue = decimalNumber * totalCanculatedValue;
            result.add(resultLine + "is" + " " + totalValue + " Credits");
        } catch (Exception e) {
            result.add("I have no idea what you are talking about");
        }

    }

    private void processMUCH_QUESTION(String line) {
        try {
            String formated = line.toLowerCase().split("\\sis\\s")[1].replace("how", "").replace("much", "").replace("?", "");
            String[] words = formated.trim().split("\\s+");
            String resultLine = "";
            String romanNumber = "";
            for (int i = 0; i < words.length; i++) {
                resultLine += words[i] + " ";
                if (wordToRomanTranslatorDB.get(words[i]) != null) {
                    romanNumber += wordToRomanTranslatorDB.get(words[i]);
                } else {
                    throw new Exception();
                }


            }
            if (!Util.validateRomanNumber(romanNumber.toUpperCase())) {
                throw new Exception();
            }
            float totalValue = Util.romanToDecimal(romanNumber.toUpperCase());
            result.add(resultLine + "is" + " " + totalValue);
        } catch (Exception e) {
            result.add("I have no idea what you are talking about");
        }

    }


    private void processASSIGNED(String assignedLine) {
        String[] words = assignedLine.toLowerCase().trim().split("\\s+");
        romanNumberToDecimalValueTranslatorDB.put(words[0], (float) Util.romanToDecimal(words[2].toUpperCase()));
        wordToRomanTranslatorDB.put(words[0], words[2]);
    }


    //Ex : glob glob Silver is 34 Credits
    private void processVALUE_PATTERN(String line) {
        String formatted = line.toLowerCase().replaceAll("(is\\s+)|([c|C]redits\\s*)", "").trim();
        String[] words = formatted.trim().split("\\s+");

        String unKnownWord = words[words.length - 2];
        float totalValue = Float.parseFloat(words[words.length - 1]);

        String roman = "";

        for (int i = 0; i < words.length - 2; i++) {
            roman += wordToRomanTranslatorDB.get(words[i]);
        }

        int romanNumber = Util.romanToDecimal(roman.toUpperCase());
        float valueOfUnknownWord = (float) (totalValue / romanNumber);
        wordToCalculatedValueTranslatorDB.put(unKnownWord, valueOfUnknownWord);
    }

    public void reset() {
        translatorDB = new LinkedHashMap<>();
        wordToCalculatedValueTranslatorDB = new LinkedHashMap<>();
        wordToRomanTranslatorDB = new LinkedHashMap<>();
        romanNumberToDecimalValueTranslatorDB = new LinkedHashMap<>();
        result = new ArrayList<>();
    }


}
