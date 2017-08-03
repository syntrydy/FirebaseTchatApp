package cm.app.it.gasmyr.letsay.view.user;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cm.app.it.gasmyr.letsay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserListActivityFragment extends Fragment {

    public UserListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }
}
