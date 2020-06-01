package cn.net.trimmer.jdbc.service;

import cn.net.trimmer.jdbc.entity.Account;

/**
 * 有关转账的业务
 * 
 * @author wl
 *
 */
public interface AccountService {
	// 开户
	void createAccount(Account acc);

	// 销户
	void closeAccount(String formNo);

	// 改密码
	void updatePassword(String formNo, String password);

	// 取钱
	void drawMoney(String formNo, double money);

	// 存钱
	void saveMoney(String formNo, double money);

	// 转账
	void transferMoney(String formNo, String toNo, double money);

	// 查余额
	double queryBalanceByNo(String formNo);
}
