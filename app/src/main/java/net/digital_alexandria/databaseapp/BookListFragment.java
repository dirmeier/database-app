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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Dirmeier {@literal simon.dirmeier@web.de}
 */
public class BookListFragment extends Fragment
{
    private RecyclerView mBookRecyclerView;
    private BookAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
      container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_book_list,
                                  container,
                                  false);
        mBookRecyclerView = (RecyclerView)
          v.findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(
          new LinearLayoutManager(getActivity()));
        updateUI();

        return v;
    }

    private void updateUI()
    {
        BookList bookList = BookList.get(getActivity());
        List<Book> books = bookList.getBooks();
        mAdapter = new BookAdapter(books);
        mBookRecyclerView.setAdapter(mAdapter);
    }

    public BookAdapter getAdapter()
    {
        return mAdapter;
    }

    private class BookHolder extends RecyclerView.ViewHolder
    {
        private TextView mTitleTextView;
        private TextView mAuthorTextView;
        private TextView mPublisherTextView;

        private Book mBook;

        BookHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_book, parent, false));

            mTitleTextView = itemView.findViewById(R.id.book_title);
            mAuthorTextView = itemView.findViewById(R.id.book_author);
            mPublisherTextView = itemView.findViewById(R.id.book_publisher);
        }

        void bind(Book book)
        {
            mBook = book;
            mTitleTextView.setText(mBook.getTitle());
            mAuthorTextView.setText(mBook.getAuthor());
            mPublisherTextView.setText(mBook.getPublisher());
        }
    }


    private class BookAdapter extends RecyclerView.Adapter<BookHolder>
      implements Filterable
    {
        private List<Book> mBooks;
        private List<Book> mFilteredBooks;

        BookAdapter(List<Book> books)
        {
            this.mBooks = books;
            this.mFilteredBooks = books;
        }

        @Override
        public BookHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new BookHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(BookHolder holder, int position)
        {
            Book book = mFilteredBooks.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount()
        {
            return mFilteredBooks.size();
        }

        @Override
        public Filter getFilter()
        {
            return new BookFilter();
        }

        private class BookFilter extends Filter
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() > 0)
                {
                    List<Book> tempList = new ArrayList<>();

                    for (Book book : mBooks)
                    {
                        if (book.getAuthor().toLowerCase().contains(
                          constraint.toString().toLowerCase()) ||
                            book.getTitle().toLowerCase().contains(
                              constraint.toString().toLowerCase())
                          )
                        {
                            tempList.add(book);
                        }
                    }

                    filterResults.count = tempList.size();
                    filterResults.values = tempList;
                }
                else
                {
                    filterResults.count = mBooks.size();
                    filterResults.values = mBooks;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults results)
            {
                mFilteredBooks = (ArrayList<Book>) results.values;
                notifyDataSetChanged();
            }
        }
    }

}
