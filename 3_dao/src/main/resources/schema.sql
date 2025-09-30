DROP TABLE IF EXISTS "books";
DROP TABLE IF EXISTS "authors";

 CREATE SEQUENCE authors_id_seq;

CREATE TABLE "authors" (
    "id" bigint DEFAULT nextval ('authors_id_seq') NOT NULL,
    "name" text NOT NULL,
    "location" text,
    "age" integer,
    CONSTRAINT "authors_pkey" PRIMARY KEY ("id")
);

CREATE TABLE "books" (
    "isbn" text NOT NULL UNIQUE,
    "title" text,
    "author_id" bigint,
    CONSTRAINT "book_pkey" PRIMARY KEY ("isbn"),
    CONSTRAINT "author_fk" FOREIGN KEY (author_id) REFERENCES authors(id)
);
