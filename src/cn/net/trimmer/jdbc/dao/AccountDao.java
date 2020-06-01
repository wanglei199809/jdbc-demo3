package cn.net.trimmer.jdbc.dao;

import cn.net.trimmer.jdbc.entity.Account;

public interface AccountDao {
	//增
	void insertAccount(Account account);
	//删
	void deleteAccountByAccountNo(String no);
	//改
	void updateAccount(Account account);
	//查
	Account selectOne(String no);	
}
