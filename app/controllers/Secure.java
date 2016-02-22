package controllers;

import java.util.Map;

import models.User;
import play.cache.Cache;
import play.i18n.Lang;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http;
import utils.MD5;

public class Secure extends Controller {

	public static void login() {
		// 记住操作
		if (request.acceptLanguage() != null) {
			Lang.change(request.acceptLanguage().get(0));
		} else {
			Lang.change("en");
		}
		// System.out.println(".................");
		Http.Cookie remember = request.cookies.get("rememberme");
		if (remember != null && remember.value.indexOf("-") > 0) {
			String sign = remember.value.substring(0,
					remember.value.indexOf("-"));
			String username = remember.value.substring(remember.value
					.indexOf("-") + 1);
			if (Crypto.sign(username).equals(sign)) {
				User user = User.find("username =?", username).first();
				if (user != null) {
					if (user.status == 2) {
						flash.error("Account is locked.");
						login();
					}
					user.login = user.login + 1;
					user.save();
					session.put("userid", user.id);
					session.put("username", user.truename);
					session.put("usermenu", user.role.menu);
					Application.index();
				}
			}
		}
		render();
	}

	public static void authenticate(String username, String password,
			boolean remember, String laug) {
		// System.out.println("---------------------------------");
		Lang.change(laug);
		validation.required(username);
		validation.required(password);
		if (validation.hasErrors()) {
			params.flash();
			flash.error("用户名或者密码不能为空.");
			login();
		}
		User user = User.find("username =? and password =?", username,
				MD5.hash(password)).first();

		if (user != null) {
			if (user.status == 2) {
				flash.error("用户名被锁，不能登录.");
				login();
			}

			Map<String, Http.Header> headers = request.headers;
			Http.Header header = headers.get("x-forwarded-for");
			// System.out.println("url:"+request.url);
			String ip = "";
			if (header != null) {
				ip = header.value();
			} else {
				ip = request.remoteAddress;
			}
			// 检测是否绑定了IP, 并进行验证。
			if (user.useIp == true) {
				if (user.ip_address != null
						&& !user.ip_address.trim().equals("")) {
					boolean b = false;
					String[] ips = user.ip_address.split(",");
					for (String s : ips) {
						if (s.equals(ip)) {
							b = true;
						}
					}
					if (!b) {
						flash.error("IP地址验证错误！");
						login();
					}
				} else {
					flash.error("IP地址验证错误！");
					login();
				}
			}
			user.login = user.login + 1;

			user.save();
			session.put("userid", user.id);
			session.put("username", user.truename);
			session.put("usermenu", user.role.menu);
			if (remember) {
				response.setCookie("rememberme", Crypto.sign(username) + "-"
						+ username, "30d");
			}
			Application.index();
		} else {
			params.flash();
			flash.error("登录失败，用户名或者密码错误.");
			login();
		}
	}

	protected static User connect() {
		return User.findById(Long.parseLong(session.get("userid")));
	}

	public static void logout() {
		String uid = connect().id.toString();
		Cache.delete(uid);
		session.clear();
		response.setCookie("rememberme", "");
		flash.success("secure.logout");
		login();
	}

}
