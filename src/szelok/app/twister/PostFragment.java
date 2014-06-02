package szelok.app.twister;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class PostFragment extends Fragment {
	private final static String TAG = PostFragment.class.getSimpleName();

	private ListView list;
	private PostListViewAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View view = inflater.inflate(R.layout.post_fragment, container, false);

		list = (ListView) view.findViewById(R.id.post_list);

		adapter = new PostListViewAdapter(this);
		list.setAdapter(adapter);

		return view;
	}
}
