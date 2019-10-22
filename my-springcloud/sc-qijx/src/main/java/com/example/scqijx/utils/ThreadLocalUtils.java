package com.example.scqijx.utils;


import com.example.scqijx.domain.User;
import org.springframework.core.NamedThreadLocal;

/**
 * 本地变量工具类
 *
 * @author yangk
 * @date 2019/04/29
 * @since 0.0.1
 */
public class ThreadLocalUtils {

	public static final ThreadLocal<User> USER_THREAD_LOCAL = new NamedThreadLocal<>("User Thread Local");

	public static void setUser(User user) {
		USER_THREAD_LOCAL.set(user);
	}

	public static User getUser() {
		return USER_THREAD_LOCAL.get();
	}

	public static void removeUser() {
		USER_THREAD_LOCAL.remove();
	}


}
