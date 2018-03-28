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


/**
 * @author Simon Dirmeier {@literal mail@simon-dirmeier.net}
 */
public class Book
{
    private  long mId;
    private  String mTitle;
    private  String mPublisher;
    private  Author[] mAuthors;

    public Book()
    {
    }

    public Book(long id, String title, String publisher, Author[] authors)
    {
        setId(id);
        setTitle(title);
        setPublisher(publisher);
        setAuthors(authors);
    }


    public String getTitle()
    {
        return mTitle;
    }

    public String getPublisher()
    {
        return mPublisher;
    }

    public Author[] getAuthors()
    {
        return mAuthors;
    }

    public long id()
    {
        return getId();
    }

    public long getId()
    {
        return mId;
    }

    public void setId(long id)
    {
        mId = id;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public void setPublisher(String publisher)
    {
        mPublisher = publisher;
    }

    public void setAuthors(Author[] authors)
    {
        mAuthors = authors;
    }
}
