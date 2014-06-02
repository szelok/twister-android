package szelok.app.twister;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DirectMessageFragment extends Fragment {
	private ListView list;
	private DirectMessageListViewAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.direct_message_fragment, container, false);

		list = (ListView) view.findViewById(R.id.direct_message_list);

		adapter = new DirectMessageListViewAdapter(this);
		list.setAdapter(adapter);

		return view;
	}
}
