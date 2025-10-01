package com.spring.json.utils;

import com.spring.json.domain.Book;

public class Utils {

    public static Book getBookJavaObject() {
        return Book.builder()
                .isbn("9780-lkw8-4785")
                .title("Land of seven rivers")
                .author("Sanjeev Sanyal")
                .yearPublished("2012")
                .build();
    }
}
