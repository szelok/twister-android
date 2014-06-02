package szelok.app.twister;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.TreeMap;

import android.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostListViewAdapter extends BaseAdapter implements DataModel.PostsListListener {
	private final static String TAG = PostListViewAdapter.class.getSimpleName();
	
	private final static long ONE_HOUR = 1000 * 60 * 60;
	private final static long ONE_MIN = 1000 * 60;
	private final static long ONE_SEC = 1000;
	
	private static LayoutInflater inflater = null;
	private TreeMap<Post, Post> postsList;
	private Map<String, User> profiles;

	public PostListViewAdapter(Fragment a) {
		inflater = (LayoutInflater) a.getActivity().getLayoutInflater();
		postsList = DataModel.INSTANCE.getCurrentWalletUserPostsList();
		profiles = DataModel.INSTANCE.getProfiles();
		DataModel.INSTANCE.addPostsListListener(this);
	}

	public int getCount() {
		return postsList.size();
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
			vi = inflater.inflate(R.layout.post_list_row, null);

		TextView name = (TextView) vi.findViewById(R.id.post_view_name);
		TextView msg = (TextView) vi.findViewById(R.id.post_view_msg);	
		TextView note = (TextView) vi.findViewById(R.id.post_view_note);
		TextView timestamp = (TextView) vi.findViewById(R.id.post_view_timestamp);
		ImageView image = (ImageView) vi.findViewById(R.id.post_view_image);
					
		
		Iterator<Entry<Post, Post>> it = postsList.entrySet().iterator();
		for (int i = 0; i < position; i++) {
			it.next();
		}			
			
		Post post = it.next().getValue();
		
		if (post.getReTwistPost() != null) {
			note.setText("twisted again by @" + post.getUserId());
			post = post.getReTwistPost();
		} else {
			note.setText(null);
		}

		User user = profiles.get(post.getUserId());
		name.setText(user.getName());
		
		SpannableString ss = new SpannableString(post.getMsg());
		//Pattern p = Pattern.compile("(^#\\w*|^@\\w*|http://\\S*|\\s#\\w*|\\s@\\w*)");
		Pattern p = Pattern.compile("(http://\\S*)");
		Matcher m = p.matcher(post.getMsg());
		while (m.find()) {
		   ss.setSpan(new URLSpan(m.group(1)), m.start(1), m.end(1),
		                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		msg.setText(ss);
        msg.setMovementMethod(LinkMovementMethod.getInstance());
        
		Date now = new Date();
		long diff = now.getTime() - (post.getTime() * ONE_SEC);
		String displayTime = (new Date(post.getTime() * ONE_SEC).toString());
		if (diff <= 0) {
			displayTime = "1 s";
		} else if (diff < ONE_MIN) {
			displayTime = ((long) diff/ONE_SEC) + " s";
		} else if (diff < ONE_HOUR) {
			long t = ((long)diff/ONE_MIN);
			displayTime = t + (t > 1 ? " mins" : "min");
		} else if (diff < 12 * ONE_HOUR) {
			long t = ((long) diff/ONE_HOUR);
			displayTime = t + (t > 1 ? " hr" : "hrs");
		}
		timestamp.setText(displayTime);
		
		image.setImageBitmap(user.getAvatar());
		return vi;
	}
	
	@Override
	public void onPostsListChanged() {
		notifyDataSetChanged();
	}
}