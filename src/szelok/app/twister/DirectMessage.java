package szelok.app.twister;

public class DirectMessage implements Comparable<DirectMessage> {
	private String userId = null;
	private String msg = null;
	private int id = -1;
	private long time = 0;
	private boolean fromMe = true;
	
	public DirectMessage() {
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public boolean getFromMe() {
		return fromMe;
	}

	public void setFromMe(boolean fromMe) {
		this.fromMe = fromMe;
	}
	
	@Override
	public int compareTo(DirectMessage another) {
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
		DirectMessage other = (DirectMessage) obj;
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
