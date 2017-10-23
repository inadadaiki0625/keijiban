package beans;

import java.io.Serializable;
import java.util.Date;

public class UserComments implements Serializable{
	private static final long serialVersionUID = 1L;

	private String text;
	private int branchId;
	private int departmentId;
	private int userId;
	private int contributionId;
	private int commentId;
	private String name;
	private Date createdAt;


	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getContributionId() {
		return contributionId;
	}
	public void setContributionId(int contributionId) {
		this.contributionId = contributionId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


}
