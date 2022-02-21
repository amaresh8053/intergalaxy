package com.intergalaxy.translator.api;

import java.util.regex.Pattern;

public final class Constants {

    public static final String ASSIGNED = "ASSIGNED";
    public static final String VALUE = "value";
    public static final String MUCH_QUESTION = "muchQuestion";
    public static final String MANY_QUESTION = "manyQuestion";
    public static final String INVALID_INPUT = "invalid input";
    public static final String I_HAVE_NO_IDEA_WHAT_YOU_ARE_TALKING_ABOUT = "I have no idea what you are talking about";


    //EX pish is X
    public static final Pattern ASSIGNED_PATTERN = Pattern.compile("^([A-Za-z]+) is ([I|V|X|L|C|D|M])$");
    //Ex : glob glob Silver is 34 Credits
    public static final Pattern VALUE_PATTERN = Pattern.compile("^([A-Za-z]+)([A-Za-z\\s]*) is ([0-9]+) ([c|C]redits)$");
    public static final Pattern MUCH_QUESTION_PATTERN = Pattern.compile("^how much is (([A-Za-z\\s])+)\\?$");
    public static final Pattern MANY_QUESTION_PATTERN = Pattern.compile("^how many [c|C]redits is (([A-Za-z\\s])+)\\?$");
    public static final Pattern ROMAN_NUMBER_VALIDATOR_PATTERN = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
}
