package com.ez.sendem.constraint;

import android.content.Intent;

public class AppConstraint {

    public static class THEME_TYPE {
        public static String THEME_LARGE = "Large";
        public static String THEME_MEDIUM = "Medium";
        public static String THEME_SMALL = "Small";
    }

    public static String THEME_DEFAULT = THEME_TYPE.THEME_MEDIUM;

    public static class LANG_TYPE {
        public static String LANG_EN = "en";
        public static String LANG_IN = "in";
    }

    public static String LANG_DEFAULT = LANG_TYPE.LANG_IN;

    public static Intent ServiceIntent;
}
