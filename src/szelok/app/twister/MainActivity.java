package szelok.app.twister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private boolean actionWalletUserInited = false;

	private MenuItem updateWalletUserItem = null;
	private MenuItem updateNetworkItem = null;
	private MenuItem updateFollowingUserItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_activity);

		DataModel.INSTANCE.init(this.getApplicationContext());

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("@"
				+ DataModel.INSTANCE.getCurrentWalletUser().getId());

		// setup action bar for tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// actionBar.setDisplayShowTitleEnabled(false);

		Tab tab = actionBar
				.newTab()
				.setText(R.string.post)
				.setTabListener(
						new TabListener<PostFragment>(this, "post",
								PostFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.dm)
				.setTabListener(
						new TabListener<DirectMessageFragment>(this,
								"directMessage", DirectMessageFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText(R.string.following)
				.setTabListener(
						new TabListener<FollowingFragment>(this, "following",
								FollowingFragment.class));
		actionBar.addTab(tab);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		updateWalletUserItem = menu.findItem(R.id.action_update_wallet_user);
		final Spinner updateWalletUser = (Spinner) updateWalletUserItem
				.getActionView();
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		updateWalletUser.setAdapter(adapter);

		updateWalletUser
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (actionWalletUserInited) {
							String walletUser = (String) parent
									.getItemAtPosition(position);
							DataModel.INSTANCE.setCurrentWalletUser(walletUser);
							MainActivity.this.getActionBar().setTitle(
									"@" + walletUser);
							updateWalletUserItem.collapseActionView();
						} else {
							actionWalletUserInited = true;
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						updateWalletUserItem.collapseActionView();

					}
				});

		updateWalletUserItem
				.setOnActionExpandListener(new OnActionExpandListener() {
					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
						adapter.clear();
						adapter.notifyDataSetChanged();
						return true;
					}

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
						if (updateFollowingUserItem.isActionViewExpanded())
							updateFollowingUserItem.collapseActionView();
						if (updateNetworkItem.isActionViewExpanded())
							updateNetworkItem.collapseActionView();

						adapter.addAll(DataModel.INSTANCE.getWalletUsersList()
								.keySet());
						updateWalletUser.setSelection(adapter
								.getPosition(DataModel.INSTANCE
										.getCurrentWalletUser().getId()));
						adapter.notifyDataSetChanged();
						return true;
					}
				});

		updateNetworkItem = menu.findItem(R.id.action_update_network_setting);
		final EditText updateNetwork = (EditText) updateNetworkItem
				.getActionView();
		updateNetwork.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					DataModel.INSTANCE.setServerUrl(updateNetwork.getText()
							.toString());
					updateNetworkItem.collapseActionView();
				}
				return false;
			}
		});

		updateNetworkItem
				.setOnActionExpandListener(new OnActionExpandListener() {
					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
						return true;
					}

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
						if (updateFollowingUserItem.isActionViewExpanded())
							updateFollowingUserItem.collapseActionView();
						if (updateWalletUserItem.isActionViewExpanded())
							updateWalletUserItem.collapseActionView();

						updateNetwork.setText(DataModel.INSTANCE.getServerUrl());
						updateNetwork.requestFocus();
						return true;
					}
				});

		updateFollowingUserItem = menu
				.findItem(R.id.action_update_following_user);
		final FollowingUserSuggestView updateFollowingUserSuggest = (FollowingUserSuggestView) updateFollowingUserItem
				.getActionView();
		updateFollowingUserSuggest
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Map<String, String> itemClicked = (Map<String, String>) parent
								.getItemAtPosition(position);
						DataModel.INSTANCE.follow(itemClicked.get("id"));
						updateFollowingUserItem.collapseActionView();
					}
				});

		updateFollowingUserItem
				.setOnActionExpandListener(new OnActionExpandListener() {
					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
						return true;
					}

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
						if (updateNetworkItem.isActionViewExpanded())
							updateNetworkItem.collapseActionView();
						if (updateWalletUserItem.isActionViewExpanded())
							updateWalletUserItem.collapseActionView();

						updateFollowingUserSuggest.requestFocus();
						((FollowingUserSuggestAdapter) updateFollowingUserSuggest
								.getAdapter()).notifyDataSetInvalidated();
						return true;
					}
				});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_send_twist:
			send_twist();
			return true;
		case R.id.action_send_direct_message:
			send_direct_message();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void send_twist() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.action_send_twist, null);
		final EditText msg = (EditText) layout
				.findViewById(R.id.action_send_twist_msg);

		new AlertDialog.Builder(this)
				.setTitle("New Twist")
				.setView(layout)
				.setPositiveButton("Send",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								DataModel.INSTANCE.sendNewTwist(msg.getText()
										.toString());
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.
							}
						}).show();
	}

	private void send_direct_message() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.action_send_direct_message,
				null);
		final EditText msg = (EditText) layout
				.findViewById(R.id.action_send_direct_message_msg);

		List<String> list = new ArrayList<String>();
		list.addAll(DataModel.INSTANCE.getCurrentWalletUserFollowingUsersList()
				.keySet());
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, list);

		final AutoCompleteTextView to = (AutoCompleteTextView) layout
				.findViewById(R.id.action_send_direct_message_to);
		to.setAdapter(adapter);
		to.setThreshold(1);

		new AlertDialog.Builder(this)
				.setTitle("New Direct Message")
				.setView(layout)
				.setPositiveButton("Send",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								DataModel.INSTANCE.sendNewDirectMessage(to
										.getText().toString(), msg.getText()
										.toString());
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.
							}
						}).show();
	}

	private class TabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
		}
	}
}
