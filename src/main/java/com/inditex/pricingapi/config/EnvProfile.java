package com.inditex.pricingapi.config;

public class EnvProfile {

    public static String[] setupProfile() {
        String scope = System.getenv("scope");

        String[] profile = {"dev"};
        if (scope != null && scope.equalsIgnoreCase("production")) {
            profile = new String[]{"prod"};
        }

        return profile;
    }
}
