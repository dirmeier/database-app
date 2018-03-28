/**
 * database-app: a Java implementation of various latent variable models.
 * <p>
 * Copyright (C) 2018 Simon Dirmeier
 * <p>
 * This file is part of database-app.
 * <p>
 * database-app is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * database-app is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with database-app.  If not, see <http://www.gnu.org/licenses/>.
 */


package net.digital_alexandria.databaseapp;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Dirmeier {@literal simon.dirmeier@web.de}
 */
public class BookList
{
    public static BookList mBookList;
    private Map<Long, Book> mBooks;

    public static BookList get(Context context)
    {
        if (mBookList == null)
        {
            mBookList = new BookList(context);
        }
        return mBookList;
    }

    private BookList(Context context)
    {
        mBooks = _get();
    }

    private static Map<Long, Book> _get()
    {
        Book b = new Book(0,
                          "title",
                          "publisher",
                          new Author[]{new Author("s", "a")});
        Map<Long, Book> books = new HashMap<>();
        books.put(0L, b);

        return books;
    }

    public Map<Long, Book> getBooks()
    {
        return mBooks;
    }

    public Book getBook(Long id)
    {
        if (mBooks.containsKey(id))
            return mBooks.get(id);
        return null;
    }

}
