package beans;

import java.io.Serializable;
import java.util.Date;

public class UserContribution implements Serializable {
	private static final long serialVersionUID = 1L;

	private String subject;
	private String text;
	private String category;
	private int branch_id;
	private int department_id;
	private String name;
	private Date created_at;
	private int user_id;
	private int contributions_id;

	public int getContributions_id() {
		return contributions_id;
	}
	public void setContributions_id(int contributions_id) {
		this.contributions_id = contributions_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
