package com.example.api.path;

public final class AuthorPaths {
    public static final String AUTHORS = "/authors";
    public static final String LIST_AUTHORS = "/list-authors";
    public static final String AUTHOR_BY_ID = "/authors/{id}";
    public static final String UPDATE_AUTHOR_BY_ID = "/authors/update/{id}";

    public static String authorByIdUrl(Long id) {
        return AUTHOR_BY_ID.replace("{id}", id.toString());
    }

    public static String updateAuthorByIdUrl(Long id) {
        return UPDATE_AUTHOR_BY_ID.replace("{id}", id.toString());
    }
}
