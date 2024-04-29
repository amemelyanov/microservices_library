CREATE TABLE IF NOT EXISTS book
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    author      VARCHAR NOT NULL,
    genre       VARCHAR NOT NULL,
    pages       INT     NOT NULL,
    year        INT     NOT NULL,
    cover_name       VARCHAR
);

COMMENT ON TABLE book IS 'Книги';
COMMENT ON COLUMN book.id IS 'Идентификатор книги';
COMMENT ON COLUMN book.name IS 'Название книги';
COMMENT ON COLUMN book.description IS 'Описание книги';
COMMENT ON COLUMN book.genre IS 'Жанр книги';
COMMENT ON COLUMN book.author IS 'Автор книги';
COMMENT ON COLUMN book.pages IS 'Количество страниц';
COMMENT ON COLUMN book.year IS 'Год выпуска книги';
COMMENT ON COLUMN book.cover_name IS 'Наименование файлы обложки';