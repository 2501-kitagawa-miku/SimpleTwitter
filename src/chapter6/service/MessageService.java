package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;
import chapter6.logging.InitApplication;

public class MessageService {

	/**
	 * ロガーインスタンスの生成
	 */
	Logger log = Logger.getLogger("twitter");

	/**
	 * デフォルトコンストラクタ
	 * アプリケーションの初期化を実施する。
	 */
	public MessageService() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}

	public void insert(Message message) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			new MessageDao().insert(connection, message);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserMessage> select(String userId, String startDate, String finishDate) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		final int LIMIT_NUM = 1000;

		Connection connection = null;
		try {
			connection = getConnection();
			/*
			 * idをnullで初期化
			 * ServletからuserIdの値が渡ってきていたら
			 * 整数型に型変換し、idに代入
			 */
			Integer id = null;
			if(!StringUtils.isEmpty(userId)) {
				id = Integer.parseInt(userId);
			}

			String start = null;
			String finish = null;

			if(!StringUtils.isEmpty(startDate)) {
				start = startDate + " 00:00:00";
			} else {
				start = "2020-01-01 00:00:00";
			}

			if(!StringUtils.isEmpty(finishDate)) {
				finish = finishDate + " 23:59:59";
			} else {
				Date today = new Date();
				SimpleDateFormat fsp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				finish = fsp.format(today);
			}

			/*
			 * messageDao.selectに引数としてInteger型のidを追加
			 * idがnullだったら全件取得する
			 * idがnull以外だったら、その値に対応するユーザーIDの投稿を取得する
			 */
			List<UserMessage> messages = new UserMessageDao().select(connection, LIMIT_NUM, id, start, finish);
			commit(connection);

			return messages;
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void delete(int messageId) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			new MessageDao().delete(connection, messageId);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public Message display(int messageId) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			Message display = new MessageDao().display(connection, messageId);
			commit(connection);
			return display;
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void edit(Message message) {
		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		Connection connection = null;
		try {
			connection = getConnection();
			new MessageDao().edit(connection, message);
			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} catch (Error e) {
			rollback(connection);
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw e;
		} finally {
			close(connection);
		}
	}

}
