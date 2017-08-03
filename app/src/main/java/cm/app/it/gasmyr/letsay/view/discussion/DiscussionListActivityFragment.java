package cm.app.it.gasmyr.letsay.view.discussion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cm.app.it.gasmyr.letsay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscussionListActivityFragment extends Fragment {

    public DiscussionListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discussion_list, container, false);
    }
}
