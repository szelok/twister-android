package szelok.app.twister;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.graphics.Bitmap;

public class User implements Comparable<User> {
	public User(String id) {
		this.id = id;
		this.name = id;
	}

	private final String id;
	private int earliestPostId = -1;
	private int latestPostId = -1;
	private int latestPostIdOnServer = -1;
	private String name = "";
	private String bio = "";
	private String location = "";
	private String url = "";
	private Bitmap avatar = null;
	private TreeMap<Post, Post> posts = new TreeMap<Post, Post>();
	private Map<String, TreeMap<DirectMessage, DirectMessage>> directMessages = new HashMap<String, TreeMap<DirectMessage, DirectMessage>>();

	public int getLatestPostId() {
		return latestPostId;
	}

	public void setLatestPostId(int latestPostId) {
		this.latestPostId = latestPostId;
	}

	public TreeMap<Post, Post> getPosts() {
		return posts;
	}

	public TreeMap<DirectMessage, DirectMessage> getDirectMessages(String toUserId) {
		TreeMap<DirectMessage, DirectMessage> t = directMessages.get(toUserId);
		if (t == null) {
			t = new TreeMap<DirectMessage, DirectMessage>();
			directMessages.put(toUserId, t);
		}
		return t;
	}
	
	public int getLatestDirectMessageId(String toUserId) {
		TreeMap<DirectMessage, DirectMessage> directMessages = getDirectMessages(toUserId);
		int result = -1;
		if (directMessages.size() > 0) {
			result = directMessages.firstKey().getId();
		}
		return result;
	}
	 
	public int getLatestPostIdOnServer() {
		return latestPostIdOnServer;
	}

	public void setLatestPostIdOnServer(int latestPostIdOnServer) {
		this.latestPostIdOnServer = latestPostIdOnServer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Bitmap getAvatar() {
		return avatar;
	}

	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}
	
	public int getEarliestPostId() {
		return earliestPostId;
	}

	public void setEarliestPostId(int earliestPostId) {
		this.earliestPostId = earliestPostId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(User another) {
		if (this == another)
			return 0;
		return this.getId().compareToIgnoreCase(another.getId());
	}
}
