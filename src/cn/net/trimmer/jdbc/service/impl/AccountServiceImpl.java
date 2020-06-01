package cn.net.trimmer.jdbc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import cn.net.trimmer.jdbc.dao.AccountDao;
import cn.net.trimmer.jdbc.dao.impl.AccountDaoImpl;
import cn.net.trimmer.jdbc.entity.Account;
import cn.net.trimmer.jdbc.service.AccountService;
import cn.net.trimmer.jdbc.util.JdbcUtils;

/**
 * 转账业务实现
 * 
 * @author wl
 *
 */
public class AccountServiceImpl implements AccountService {

	private AccountDao accDao = new AccountDaoImpl();

	@Override
	public void createAccount(Account acc) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConn();
			// 关闭事务自动提交
			conn.setAutoCommit(false);
			// 执行业务
			accDao.insertAccount(acc);
			// 业务成功,提交事务
			conn.commit();
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();
			try {
				// 业务出错,做数据回滚操作
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}
	}

	@Override
	public void closeAccount(String formNo) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConn();
			// 关闭事务自动提交
			conn.setAutoCommit(false);
			// 执行业务
			accDao.deleteAccountByAccountNo(formNo);
			// 业务成功,提交事务
			conn.commit();
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();
			try {
				// 业务出错,做数据回滚操作
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}
	}

	@Override
	public void updatePassword(String formNo, String password) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConn();
			// 关闭事务自动提交
			conn.setAutoCommit(false);
			
			// 执行业务
			// 1 查询账户信息
			Account account = accDao.selectOne(formNo);
			// 2 修改密码			
			account.setPassword(password);
			accDao.updateAccount(account);

			// 业务成功,提交事务
			conn.commit();
			System.out.println("密码修改成功");
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();
			try {
				// 业务出错,做数据回滚操作
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}
	}

	// 取钱
	@Override
	public void drawMoney(String formNo, double money) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConn();
			// 关闭事务自动提交
			conn.setAutoCommit(false);
			// 执行业务
			Account account = accDao.selectOne(formNo);
			// 计算取钱后的余额
			Double balance = account.getBalance() - money;
			// 存入对象中
			account.setBalance(balance);
			// 修改db数据
			accDao.updateAccount(account);
			// 业务成功,提交事务
			conn.commit();
			System.out.println("取钱成功");
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();
			try {
				// 业务出错,做数据回滚操作
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}
	}

	// 存钱
	@Override
	public void saveMoney(String formNo, double money) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConn();
			// 关闭事务自动提交
			conn.setAutoCommit(false);

			// 执行业务
			// 1 查询账户信息
			Account account = accDao.selectOne(formNo);
			// 2 存钱
			Double balance = account.getBalance() + money;
			account.setBalance(balance);
			accDao.updateAccount(account);

			// 业务成功,提交事务
			conn.commit();
			System.out.println("存钱成功");
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();
			try {
				// 业务出错,做数据回滚操作
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}

	}

	// 转账
	@Override
	public void transferMoney(String formNo, String toNo, double money) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConn();
			// 关闭事务自动提交
			conn.setAutoCommit(false);

			// 执行业务
			// 1 获取原账户信息
			Account formAccount = accDao.selectOne(formNo);
			// 2从原账户中减去所转金额
			Double balance = formAccount.getBalance() - money;
			formAccount.setBalance(balance);
			accDao.updateAccount(formAccount);
			// 3 获取目标账户信息
			Account toAccount = accDao.selectOne(toNo);
			Double balance2 = toAccount.getBalance() + money;
			toAccount.setBalance(balance2);
			// 4给目标账户的余额增加所转金额
			accDao.updateAccount(toAccount);

			// 业务成功,提交事务
			conn.commit();
			System.out.println("转账成功");
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();
			try {
				// 业务出错,做数据回滚操作
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}

	}

	@Override
	public double queryBalanceByNo(String formNo) {
		Connection conn = JdbcUtils.getConn();
		try {
			// 直接查询,不用控制事务
			Account account = accDao.selectOne(formNo);
			return account.getBalance();
		} catch (Exception e) {
			// 出错打印异常
			e.printStackTrace();			
			// 抛运行时异常
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			JdbcUtils.release(null, null, conn);
		}
		
	}

}
