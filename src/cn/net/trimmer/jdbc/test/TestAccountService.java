package cn.net.trimmer.jdbc.test;

import java.util.Date;

import org.junit.Test;

import cn.net.trimmer.jdbc.entity.Account;
import cn.net.trimmer.jdbc.service.AccountService;
import cn.net.trimmer.jdbc.service.impl.AccountServiceImpl;

/**
 * 测试账户业务
 * 
 * @author wl
 *
 */
public class TestAccountService {
	private static AccountService as = new AccountServiceImpl();
	
	// 创建一个小明的账户
	@Test
	private static void testCreateAccount() {
		Account acc = new Account(null, "小明的账户", "123456", 100.0,new Date());
		as.createAccount(acc);
	}
	
	//删除小明的账户
	@Test
	private static void testCloseAccount() {		
		as.closeAccount("");
	}
	
	//改61号账户的密码
	@Test
	private static void testUpdatePassword(String no,String password) {		
		as.updatePassword(no, password);
	}
	
	// 给43号账户存1块钱
	@Test
	private static void testSaveMoney() {
		as.saveMoney("43", 1);
	}
		
	// 从61号账户取1块钱
	@Test
	private static void testDrawMoney() {
		as.drawMoney("61", 1);
	}
	

	// 从43号账户转1块钱到61号账户
	@Test
	private static void testTransferMoney() {
		as.transferMoney("43", "61", 1);
	}
	
	//查61号账户的余额
	@Test
	private static void testQueryBalance() {
		double balance = as.queryBalanceByNo("");
		System.out.println("余额剩余"+balance);
	}
	
}
