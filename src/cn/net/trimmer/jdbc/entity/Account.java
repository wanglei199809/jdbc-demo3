package cn.net.trimmer.jdbc.entity;

import java.util.Date;

/**
 * 账户表
 * 
 * @author wl
 *
 */
public class Account {
	private String accountNo;
	private String accountName;
	private String password;
	private Double balance;
	private Date openDate;

	public Account() {
		super();
	}

	public Account(String accountNo, String accountName, String password, Double balance, Date openDate) {
		super();
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.password = password;
		this.balance = balance;
		this.openDate = openDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", accountName=" + accountName + ", password=" + password
				+ ", balance=" + balance + ", openDate=" + openDate + "]";
	}
}
