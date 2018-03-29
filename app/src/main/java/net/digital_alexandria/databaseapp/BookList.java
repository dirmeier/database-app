/**
 * database-app: a android app for querying my book and CD data bases.
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
import android.util.Log;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Simon Dirmeier {@literal simon.dirmeier@web.de}
 */
class BookList
{
    private static BookList mBookList;
    private List<Book> mBooks;
    private final static String URL_ = "http://simon-dirmeier.net/books/get";

    static BookList get(Context context)
    {
        if (mBookList == null)
        {
            mBookList = new BookList(context);
        }
        return mBookList;
    }

    private BookList(Context context)
    {
        mBooks = new ArrayList<>();
        http();
    }


    public void http()
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Book>> callable = new Callable<List<Book>>()
        {
            @Override
            public List<Book> call()
            {
                return new HttpRequest().request();
            }
        };
        Future<List<Book>> future = executor.submit(callable);
        executor.shutdown();

        try
        {
            mBooks = future.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            Log.e(BookList.class.getSimpleName(), "Error reading books");
        }

    }

    List<Book> getBooks()
    {
        return mBooks;
    }


    private class HttpRequest
    {
        List<Book> request()
        {
            List<Book> books = new ArrayList<>();
            try
            {
                URL url = new URL(URL_);
                HttpURLConnection con = (HttpURLConnection) url
                  .openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");

                BufferedReader in = new BufferedReader(
                  new InputStreamReader(con.getInputStream()));

                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                {
                    content.append(inputLine);
                }

                in.close();
                con.disconnect();

                books = parse(content);
            }
            catch (IOException e)
            {
                Log.e(BookList.class.getSimpleName(), "Http request failed.");
            }


            return books;
        }

        private List<Book> parse(StringBuffer content)
        {

            List<Book> books = new ArrayList<>();
            try
            {
                Object obj = new JSONParser().parse(content.toString());
                JSONArray jo = (JSONArray) obj;

                for (int i = 0; i < jo.size(); i++)
                {
                    JSONArray a = (JSONArray) jo.get(i);
                    books.add(new Book((long) i,
                                       String.valueOf(a.get(2)),
                                       String.valueOf(a.get(3)),
                                       String.valueOf(a.get(1))
                    ));
                }
            }
            catch (ParseException e)
            {
                Log.e(BookList.class.getSimpleName(), "Cannot parse JSON.");
            }


            return books;
        }
    }

}
