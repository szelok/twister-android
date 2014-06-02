package szelok.app.twister;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class FollowingUserSuggestView extends AutoCompleteTextView {
	public FollowingUserSuggestView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setAdapter(new FollowingUserSuggestAdapter(context,
				new ArrayList<Map<String, String>>(),
				R.layout.following_user_suggest));
		setThreshold(1);
	}
	
	@Override
	protected CharSequence convertSelectionToString(Object selectedItem) {
		return "";
	}
}