package org.faustinelli.android.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by muzietto on 14/06/15.
 */
public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }
}
