package org.faustinelli.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by muzietto on 14/06/15.
 */
public class CrimeListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
