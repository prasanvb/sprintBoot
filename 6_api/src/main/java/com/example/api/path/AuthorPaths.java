package com.example.api.path;

public final class AuthorPaths {
    public static final String AUTHORS = "/authors";
    public static final String LIST_AUTHORS = "/list-authors";
    public static final String AUTHOR_BY_ID = "/authors/{id}";
    public static final String UPDATE_AUTHOR_BY_ID = "/authors/update/{id}";
    public static final String PATCH_AUTHOR_BY_ID = "/authors/patch/{id}";
    public static final String AUTHORS_AGE_LESS_THAN = "/authors/age-less-than/{age}";
    public static final String AUTHORS_AGE_GREATER_THAN = "/authors/age-greater-than/{age}";
    public static final String AUTHORS_BY_NAME = "/authors/search";

    public static String authorByIdUrl(Long id) {
        return AUTHOR_BY_ID.replace("{id}", id.toString());
    }

    public static String updateAuthorByIdUrl(Long id) {
        return UPDATE_AUTHOR_BY_ID.replace("{id}", id.toString());
    }
}
