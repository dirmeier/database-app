

package net.digital_alexandria.databaseapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.*;
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

        public BookHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_book, parent, false));

            mTitleTextView = itemView.findViewById(R.id.book_title);
            mAuthorTextView = itemView.findViewById(R.id.book_author);
            mPublisherTextView = itemView.findViewById(R.id.book_publisher);
        }

        public void bind(Book book)
        {
            mBook = book;
            mTitleTextView.setText(mBook.getTitle());
            mAuthorTextView.setText(mBook.getAuthor());
            mPublisherTextView.setText(mBook.getPublisher());
        }
    }


    public class BookAdapter extends RecyclerView.Adapter<BookHolder>
      implements Filterable
    {
        private List<Book> mBooks;
        private List<Book> mFilteredBooks;

        public BookAdapter(List<Book> books)
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
