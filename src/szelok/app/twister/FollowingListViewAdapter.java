package szelok.app.twister;


import java.util.Iterator;

import java.util.Map.Entry;
import java.util.TreeMap;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FollowingListViewAdapter extends BaseAdapter implements
		DataModel.FollowingUsersListListener {
	private final static String TAG = FollowingListViewAdapter.class
			.getSimpleName();

	private LayoutInflater inflater;
	private TreeMap<String, User> followingUsersList;

	public FollowingListViewAdapter(Fragment a) {
		inflater = (LayoutInflater) a.getActivity().getLayoutInflater();
		followingUsersList = DataModel.INSTANCE
				.getCurrentWalletUserFollowingUsersList();
		DataModel.INSTANCE.addFollowingUsersListListener(this);
	}

	public int getCount() {
		return followingUsersList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.following_list_row, null);

		TextView name = (TextView) vi.findViewById(R.id.following_list_name);
		TextView id = (TextView) vi.findViewById(R.id.following_list_id);
		TextView bio = (TextView) vi.findViewById(R.id.following_list_bio);
		TextView url = (TextView) vi.findViewById(R.id.following_list_url);
		ImageView image = (ImageView) vi
				.findViewById(R.id.following_list_image);

		Iterator<Entry<String, User>> it = followingUsersList.entrySet()
				.iterator();
		for (int i = 0; i < position; i++) {
			it.next();
		}

		User user = it.next().getValue();
		id.setText("@" + user.getId());

		name.setText(user.getName());
		bio.setText(user.getBio());
		url.setText(user.getUrl());
		image.setImageBitmap(user.getAvatar());

		return vi;
	}

	@Override
	public void onFollowingUsersListChanged() {
		notifyDataSetChanged();
	}
}