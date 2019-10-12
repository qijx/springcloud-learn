/*
 * Filename KeyGenerator.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.id;

import com.rome.openapi.backend.util.date.DateUtil;
import com.rome.openapi.backend.util.message.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class KeyGenerator {
	private static KeyGenerator instance = new KeyGenerator();
	private final ReentrantLock lock = new ReentrantLock();

	protected KeyGenerator() {
	}

	public static KeyGenerator getInstance() {
		return instance;
	}

	public Long getLongPK() {
		this.lock.lock();

		Object var2;
		try {
			Long var1 = UUID.randomUUID().getMostSignificantBits();
			return var1;
		} catch (Exception var6) {
			var6.printStackTrace();
			var2 = null;
		} finally {
			this.lock.unlock();
		}

		return (Long)var2;
	}

	public String getStringPK32() {
		this.lock.lock();

		String pk;
		try {
			Long l = UUID.randomUUID().getMostSignificantBits();
			pk = "" + l;
			String[] pks = pk.split("-");
			if (pks.length > 1) {
				pk = pks[1];
			}

			pk = DateUtil.getYYYYMMDDHHMMSS() + pk;
			if (pk.length() < 31) {
				pk = pk + getRandomIntByLength(31 - pk.length());
			} else if (pk.length() > 31) {
				pk = pk.substring(0, 31);
			}

			String var4 = "8" + pk;
			return var4;
		} catch (Exception var8) {
			var8.printStackTrace();
			pk = null;
		} finally {
			this.lock.unlock();
		}

		return pk;
	}

	public String getStringPK27() {
		this.lock.lock();

		String pk;
		try {
			Long l = UUID.randomUUID().getMostSignificantBits();
			pk = "" + l;
			String[] pks = pk.split("-");
			if (pks.length > 1) {
				pk = pks[1];
			}

			pk = DateUtil.getYYYYMMDD() + pk;
			if (pk.length() < 27) {
				pk = pk + getRandomIntByLength(27 - pk.length());
			} else if (pk.length() > 27) {
				pk = pk.substring(0, 27);
			}

			String var4 = pk;
			return var4;
		} catch (Exception var8) {
			var8.printStackTrace();
			pk = null;
		} finally {
			this.lock.unlock();
		}

		return pk;
	}

	public String getStringPK25() {
		this.lock.lock();

		String pk;
		try {
			Long l = UUID.randomUUID().getMostSignificantBits();
			pk = "" + l;
			String[] pks = pk.split("-");
			if (pks.length > 1) {
				pk = pks[1];
			}

			String day = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
			pk = day + pk;
			if (pk.length() < 25) {
				Random random = new Random();
				int rand = random.nextInt(10);
				pk = StringUtil.leftPad(pk, 25, rand + "");
			} else if (pk.length() > 25) {
				pk = pk.substring(0, 25);
			}

			String var12 = pk;
			return var12;
		} catch (Exception var10) {
			var10.printStackTrace();
			pk = null;
		} finally {
			this.lock.unlock();
		}

		return pk;
	}

	public String getStringPK(int count) {
		this.lock.lock();

		String pk;
		try {
			Long l = UUID.randomUUID().getMostSignificantBits();
			pk = "" + l;
			String[] pks = pk.split("-");
			if (pks.length > 1) {
				pk = pks[1];
			}

			pk = DateUtil.getYYYYMMDDHHMMSS() + pk;
			if (pk.length() < count) {
				pk = pk + getRandomIntByLength(count - pk.length());
			} else if (pk.length() > count) {
				pk = pk.substring(0, count);
			}

			String var5 = pk;
			return var5;
		} catch (Exception var9) {
			var9.printStackTrace();
			pk = null;
		} finally {
			this.lock.unlock();
		}

		return pk;
	}

	public String getStringPK(String righParamt, int count) {
		this.lock.lock();

		String pk;
		try {
			Long l = UUID.randomUUID().getMostSignificantBits();
			pk = "" + l;
			String[] pks = pk.split("-");
			if (pks.length > 1) {
				pk = pks[1];
			}

			if (pk.length() < count) {
				if (count - pk.length() > righParamt.length()) {
					pk = pk + getRandomIntByLength(count - righParamt.length() - pk.length());
				} else if (count - pk.length() <= righParamt.length()) {
					pk = pk.substring(0, count - righParamt.length());
				}
			} else if (pk.length() > count - righParamt.length()) {
				pk = pk.substring(0, count - righParamt.length());
			}

			String var6 = righParamt + pk;
			return var6;
		} catch (Exception var10) {
			var10.printStackTrace();
			pk = null;
		} finally {
			this.lock.unlock();
		}

		return pk;
	}

	private String getUUID32() {
		this.lock.lock();

		String var2;
		try {
			String key = UUID.randomUUID().toString();
			key = key.replace("-", "");
			if (key.length() < 32) {
				key = key + getRandomStringByLength(32 - key.length());
			}

			var2 = key;
			return var2;
		} catch (Exception var6) {
			var6.printStackTrace();
			var2 = null;
		} finally {
			this.lock.unlock();
		}

		return var2;
	}

	public String getUUID() {
		return this.getUUID32();
	}

	public String getUUIDString(int count) {
		this.lock.lock();

		String var3;
		try {
			String key = UUID.randomUUID().toString();
			key = key.replace("-", "");
			if (key.length() < count) {
				key = key + getRandomStringByLength(count - key.length());
			} else if (key.length() > count) {
				key = key.substring(0, count);
			}

			var3 = key;
			return var3;
		} catch (Exception var7) {
			var7.printStackTrace();
			var3 = null;
		} finally {
			this.lock.unlock();
		}

		return var3;
	}

	public String getUUIDRighParamt(String righParamt, int count) {
		this.lock.lock();

		String var4;
		try {
			String key = UUID.randomUUID().toString();
			key = key.replace("-", "");
			if (key.length() < count) {
				if (count - key.length() > righParamt.length()) {
					key = key + getRandomStringByLength(count - righParamt.length() - key.length());
				} else if (count - key.length() <= righParamt.length()) {
					key = key.substring(0, count - righParamt.length());
				}
			} else if (key.length() > count - righParamt.length()) {
				key = key.substring(0, count - righParamt.length());
			}

			var4 = key + righParamt;
			return var4;
		} catch (Exception var8) {
			var8.printStackTrace();
			var4 = null;
		} finally {
			this.lock.unlock();
		}

		return var4;
	}

	public String getUUIDLeft(String leftParamt, int count) {
		this.lock.lock();

		String var4;
		try {
			String key = UUID.randomUUID().toString();
			key = key.replace("-", "");
			if (key.length() < count) {
				if (count - key.length() > leftParamt.length()) {
					key = key + getRandomStringByLength(count - leftParamt.length() - key.length());
				} else if (count - key.length() <= leftParamt.length()) {
					key = key.substring(0, count - leftParamt.length());
				}
			} else if (key.length() > count - leftParamt.length()) {
				key = key.substring(0, count - leftParamt.length());
			}

			var4 = leftParamt + key;
			return var4;
		} catch (Exception var8) {
			var8.printStackTrace();
			var4 = null;
		} finally {
			this.lock.unlock();
		}

		return var4;
	}

	public static String getRandomStringByLength(int length) {
		System.out.println("length=" + length);
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < length; ++i) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}

		return sb.toString();
	}

	public static String getRandomIntByLength(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < length; ++i) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getInstance().getStringPK32());
		System.out.println(getInstance().getStringPK27());
	}
}

