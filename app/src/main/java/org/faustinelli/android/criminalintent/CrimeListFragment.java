package org.faustinelli.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Controller for a fragment used to deal with lists
 * <p/>
 * Created by muzietto on 14/06/15.
 */
public class CrimeListFragment extends ListFragment {
    private ArrayList<Crime> mCrimes;
    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // retrieve hosting activity and override its title
        getActivity().setTitle(R.string.crimes_title);
        // invoke the singleton
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get crime from adapter
        Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
        // start corresponding CrimeActivity providing crime id
        Intent i = new Intent(getActivity(), CrimeActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    // invoked by the fragment manager of CrimeListActivity
    @Override
    public void onResume() {
        super.onResume();
        // gotta update the crime list
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     * adapter that knows how to work with Crime objects
     */
    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), /* no layoutID */ 0, crimes);
        }

        @Override
        public View getView(int position, View listItemView, ViewGroup parent) {
            // no view => inflate one
            if (listItemView == null) {
                listItemView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }

            // configure the list item view for this crime
            Crime c = getItem(position);

            // identify title text in listItemView
            TextView titleTextView =
                    (TextView) listItemView.findViewById(R.id.crime_list_item_titleTextView);
            // set it using the crime title
            titleTextView.setText(c.getTitle());

            // identify date text in listItemView
            TextView dateTextView =
                    (TextView) listItemView.findViewById(R.id.crime_list_item_dateTextView);
            // set it using the crime date
            dateTextView.setText(c.getDate().toString());

            // identify checkbox in listItemView
            CheckBox solvedCheckBox =
                    (CheckBox) listItemView.findViewById(R.id.crime_list_item_solvedCheckBox);
            // set it using the crime solved boolean
            solvedCheckBox.setChecked(c.isSolved());

            // return the configured list item view
            return listItemView;
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragmen_crime_list, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
     switch (item.getItemId()) {
         case R.id.menu_item_new_crime:
             Crime newCrime = new Crime();
             CrimeLab.get(getActivity()).addCrime(newCrime);

             Intent i = new Intent(getActivity(), CrimeActivity.class);
             i.putExtra(CrimeFragment.EXTRA_CRIME_ID, newCrime.getId());
             startActivityForResult(i, 0);
             return true;
         default:
             return super.onOptionsItemSelected(item);
     }
    }

}
