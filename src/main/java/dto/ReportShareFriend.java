package dto;

import java.util.Date;

public class ReportShareFriend {
	private String fullname;
	private String email;
	private String receiverEmail;
	private Date favoriteDate;
	private Date sentDate;

	public ReportShareFriend(String fullname, String email, String receiverEmail, Date favoriteDate, Date sentDate) {
		this.fullname = fullname;
		this.email = email;
		this.receiverEmail = receiverEmail;
		this.favoriteDate = favoriteDate;
		this.sentDate = sentDate;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public Date getFavoriteDate() {
		return favoriteDate;
	}

	public void setFavoriteDate(Date favoriteDate) {
		this.favoriteDate = favoriteDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

}
