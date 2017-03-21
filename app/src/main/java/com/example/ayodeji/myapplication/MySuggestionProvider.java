package com.example.ayodeji.myapplication;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Ayodeji on 24/03/2017.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.ayodeji.myapplication.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}