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

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * @author Simon Dirmeier {@literal simon.dirmeier@web.de}
 */
public class BookListActivity extends SingleFragmentActivity implements SearchView.OnQueryTextListener
{
    BookListFragment mFragment;

    @Override
    protected Fragment createFragment()
    {
        mFragment = new BookListFragment();
        return mFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager =
          (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
          (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
          searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        mFragment.getAdapter().getFilter().filter(newText);
        return false;
    }

}
