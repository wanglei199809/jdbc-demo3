package cn.net.trimmer.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import cn.net.trimmer.jdbc.dao.AccountDao;
import cn.net.trimmer.jdbc.entity.Account;
import cn.net.trimmer.jdbc.util.JdbcUtils;

public class AccountDaoImpl implements AccountDao {

	@Override
	public void insertAccount(Account account) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = JdbcUtils.getConn();
			String sql = "insert into ACCOUNTS values(seq_account.nextval,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, account.getAccountName());
			pstm.setString(2, account.getPassword());
			pstm.setDouble(3, account.getBalance());
			pstm.setDate(4, new java.sql.Date(account.getOpenDate().getTime()));
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// dao层不关连接
			JdbcUtils.release(null, pstm, null);
		}
	}

	@Override
	public void deleteAccountByAccountNo(String no) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = JdbcUtils.getConn();
			String sql = "delete from ACCOUNTS where accountno = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, no);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// dao层不关连接
			JdbcUtils.release(null, pstm, null);
		}
	}

	@Override
	public void updateAccount(Account account) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = JdbcUtils.getConn();
			String sql = "update ACCOUNTS set accountname = ?, password = ?, balance = ? ,  openDate = ? where accountno = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, account.getAccountName());
			pstm.setString(2, account.getPassword());
			pstm.setDouble(3, account.getBalance());
			pstm.setDate(4, new java.sql.Date(account.getOpenDate().getTime()));
			pstm.setString(5, account.getAccountNo());
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// dao层不关连接
			JdbcUtils.release(null, pstm, null);
		}
	}

	@Override
	public Account selectOne(String no) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Account acc = null;
		try {
			conn = JdbcUtils.getConn();
			String sql = "select accountno,accountname,password,balance,opendate from ACCOUNTS where accountno = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, no);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String accountNo = rs.getString("accountno");
				String accountName = rs.getString("accountname");
				String password = rs.getString("password");
				Double balance = rs.getDouble("balance");
				Date openDate = rs.getDate("opendate");
				acc = new Account(accountNo, accountName, password, balance, openDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// dao层不关连接
			JdbcUtils.release(rs, pstm, null);
		}
		return acc;
	}

}
