package dto;

import java.util.Date;

public class ReportFavoriteUser {
	private String id;
	private String email;
	private String fullname;
	private Date favoriteDate;

	public ReportFavoriteUser() {
	}

	public ReportFavoriteUser(String id, String email, String fullname, Date favoriteDate) {
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.favoriteDate = favoriteDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getFavoriteDate() {
		return favoriteDate;
	}

	public void setFavoriteDate(Date favoriteDate) {
		favoriteDate = favoriteDate;
	}

}
