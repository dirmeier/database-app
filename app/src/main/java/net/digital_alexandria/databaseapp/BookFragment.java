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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * @author Simon Dirmeier {@literal simon.dirmeier@web.de}
 */
public class BookFragment extends Fragment
{
    private Book mBook;
    private EditText mTitleField;
    private EditText mAuthorField;
    private EditText mPublisherField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBook = new Book();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_book, container, false);
        _addTitleListener(v);
        _addAuthorListener(v);
        _addPublisherListener(v);

        return v;
    }

    private void _addPublisherListener(View v)
    {
        mPublisherField = (EditText) v.findViewById(R.id.book_publisher);
        mPublisherField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2)
            {
                mBook.setPublisher(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    private void _addAuthorListener(View v)
    {
        mAuthorField = (EditText) v.findViewById(R.id.book_author);
        mAuthorField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2)
            {
                mBook.setAuthors(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });
    }

    private void _addTitleListener(View v)
    {
        mTitleField = (EditText) v.findViewById(R.id.book_title);
        mTitleField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2)
            {
                mBook.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }
}
