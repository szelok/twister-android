package szelok.app.twister;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SimpleAdapter;

public class FollowingUserSuggestAdapter extends SimpleAdapter implements Filterable {
	private static final String TAG = FollowingUserSuggestAdapter.class.getSimpleName();
	
	// Json Result Tags
	private static final String USERNAME = "name";
	private static final String USERID = "id";
	
	// Keys used in Hashmap
	private static final String[] from = { USERNAME, USERID };
	// Ids of views in listview_layout
	private static final int[] to = { R.id.following_user_name, R.id.following_user_id };
	
	private List<Map<String, String>> resultList;

	public FollowingUserSuggestAdapter(Context context, List<Map<String, String>> data,
			int resource) {
		super(context, data, resource, from, to);
		resultList = data;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public Map<String, String> getItem(int index) {
		return resultList.get(index);
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					List<Map<String, String>> results = DataModel.INSTANCE.autocomplete(constraint
							.toString());

					// Assign the data to the FilterResults
					filterResults.values = results;
					filterResults.count = ((results != null) ? results
							.size() : 0);
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				resultList.clear();
				if (results != null && results.count > 0) {
					resultList
							.addAll((List<Map<String, String>>) results.values);
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

	

}