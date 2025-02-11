package dto;

import java.util.Date;

public class ReportFavorites {
	private String title;
	private long favoriteCount;
	private Date latestDate;
	private Date oldestate;

	public ReportFavorites() {
	}

	public ReportFavorites(String title, long favoriteCount, Date latestDate, Date oldestate) {
		this.title = title;
		this.favoriteCount = favoriteCount;
		this.latestDate = latestDate;
		this.oldestate = oldestate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Date getLatestDate() {
		return latestDate;
	}

	public void setLatestDate(Date latestDate) {
		this.latestDate = latestDate;
	}

	public Date getOldestate() {
		return oldestate;
	}

	public void setOldestate(Date oldestate) {
		this.oldestate = oldestate;
	}

}
