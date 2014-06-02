package szelok.app.twister;

public class Post implements Comparable<Post> {
	private String userId = null;
	private String msg = null;
	private int id = -1;
	private int prevId = -1;
	private int height = -1;
	private long time = 0;
	private String replyUserId = null;
	private int replyUserPostId = -1;
	private Post reTwistPost = null;
	
	public Post() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrevId() {
		return prevId;
	}

	public void setPrevId(int prevId) {
		this.prevId = prevId;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public int getReplyUserPostId() {
		return replyUserPostId;
	}

	public void setReplyUserPostId(int replyUserPostId) {
		this.replyUserPostId = replyUserPostId;
	}
	
	public Post getReTwistPost() {
		return reTwistPost;
	}

	public void setReTwistPost(Post reTwistPost) {
		this.reTwistPost = reTwistPost;
	}
	
	@Override
	public int compareTo(Post another) {
		if (this == another)
			return 0;
		if (this.getTime() < another.getTime())
			return 1;
		else if (this.getTime() > another.getTime())
			return -1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id != other.id)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
