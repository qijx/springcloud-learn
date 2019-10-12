/*
 * Filename Base64Util.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.message;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class Base64Util {
	private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	static final int CHUNK_SIZE = 76;
	static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
	private static final byte[] STANDARD_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
	private static final byte[] URL_SAFE_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
	private static final byte PAD = 61;
	private static final byte[] DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
	private static final int MASK_6BITS = 63;
	private static final int MASK_8BITS = 255;
	private final byte[] encodeTable;
	private final int lineLength;
	private final byte[] lineSeparator;
	private final int decodebyteSize;
	private final int encodeSize;
	private byte[] buffer;
	private int pos;
	private int readPos;
	private int currentLinePos;
	private int modulus;
	private boolean eof;
	private int x;

	public Base64Util() {
		this(false);
	}

	public Base64Util(boolean urlSafe) {
		this(76, CHUNK_SEPARATOR, urlSafe);
	}

	public Base64Util(int lineLength) {
		this(lineLength, CHUNK_SEPARATOR);
	}

	public Base64Util(int lineLength, byte[] lineSeparator) {
		this(lineLength, lineSeparator, false);
	}

	public Base64Util(int lineLength, byte[] lineSeparator, boolean urlSafe) {
		if (lineSeparator == null) {
			lineLength = 0;
			lineSeparator = CHUNK_SEPARATOR;
		}

		this.lineLength = lineLength > 0 ? lineLength / 4 * 4 : 0;
		this.lineSeparator = new byte[lineSeparator.length];
		System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
		if (lineLength > 0) {
			this.encodeSize = 4 + lineSeparator.length;
		} else {
			this.encodeSize = 4;
		}

		this.decodebyteSize = this.encodeSize - 1;
		if (containsBase64Byte(lineSeparator)) {
			String sep = newStringUtf8(lineSeparator);
			throw new IllegalArgumentException("lineSeperator must not contain base64 characters: [" + sep + "]");
		} else {
			this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
		}
	}

	private static String newStringUtf8(byte[] chars) {
		if (chars == null) {
			return null;
		} else {
			try {
				return new String(chars, "UTF-8");
			} catch (UnsupportedEncodingException var2) {
				return null;
			}
		}
	}

	public boolean isUrlSafe() {
		return this.encodeTable == URL_SAFE_ENCODE_TABLE;
	}

	boolean hasData() {
		return this.buffer != null;
	}

	int avail() {
		return this.buffer != null ? this.pos - this.readPos : 0;
	}

	private void resizeBuffer() {
		if (this.buffer == null) {
			this.buffer = new byte[8192];
			this.pos = 0;
			this.readPos = 0;
		} else {
			byte[] b = new byte[this.buffer.length * 2];
			System.arraycopy(this.buffer, 0, b, 0, this.buffer.length);
			this.buffer = b;
		}

	}

	int readResults(byte[] b, int bPos, int bAvail) {
		if (this.buffer != null) {
			int len = Math.min(this.avail(), bAvail);
			if (this.buffer != b) {
				System.arraycopy(this.buffer, this.readPos, b, bPos, len);
				this.readPos += len;
				if (this.readPos >= this.pos) {
					this.buffer = null;
				}
			} else {
				this.buffer = null;
			}

			return len;
		} else {
			return this.eof ? -1 : 0;
		}
	}

	void setInitialBuffer(byte[] out, int outPos, int outAvail) {
		if (out != null && out.length == outAvail) {
			this.buffer = out;
			this.pos = outPos;
			this.readPos = outPos;
		}

	}

	void encode(byte[] in, int inPos, int inAvail) {
		if (!this.eof) {
			if (inAvail < 0) {
				this.eof = true;
				if (this.buffer == null || this.buffer.length - this.pos < this.encodeSize) {
					this.resizeBuffer();
				}

				switch(this.modulus) {
					case 1:
						this.buffer[this.pos++] = this.encodeTable[this.x >> 2 & 63];
						this.buffer[this.pos++] = this.encodeTable[this.x << 4 & 63];
						if (this.encodeTable == STANDARD_ENCODE_TABLE) {
							this.buffer[this.pos++] = 61;
							this.buffer[this.pos++] = 61;
						}
						break;
					case 2:
						this.buffer[this.pos++] = this.encodeTable[this.x >> 10 & 63];
						this.buffer[this.pos++] = this.encodeTable[this.x >> 4 & 63];
						this.buffer[this.pos++] = this.encodeTable[this.x << 2 & 63];
						if (this.encodeTable == STANDARD_ENCODE_TABLE) {
							this.buffer[this.pos++] = 61;
						}
				}

				if (this.lineLength > 0 && this.pos > 0) {
					System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
					this.pos += this.lineSeparator.length;
				}
			} else {
				for(int i = 0; i < inAvail; ++i) {
					if (this.buffer == null || this.buffer.length - this.pos < this.encodeSize) {
						this.resizeBuffer();
					}

					this.modulus = ++this.modulus % 3;
					int b = in[inPos++];
					if (b < 0) {
						b += 256;
					}

					this.x = (this.x << 8) + b;
					if (0 == this.modulus) {
						this.buffer[this.pos++] = this.encodeTable[this.x >> 18 & 63];
						this.buffer[this.pos++] = this.encodeTable[this.x >> 12 & 63];
						this.buffer[this.pos++] = this.encodeTable[this.x >> 6 & 63];
						this.buffer[this.pos++] = this.encodeTable[this.x & 63];
						this.currentLinePos += 4;
						if (this.lineLength > 0 && this.lineLength <= this.currentLinePos) {
							System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
							this.pos += this.lineSeparator.length;
							this.currentLinePos = 0;
						}
					}
				}
			}

		}
	}

	void decodebyte(byte[] in, int inPos, int inAvail) {
		if (!this.eof) {
			if (inAvail < 0) {
				this.eof = true;
			}

			for(int i = 0; i < inAvail; ++i) {
				if (this.buffer == null || this.buffer.length - this.pos < this.decodebyteSize) {
					this.resizeBuffer();
				}

				byte b = in[inPos++];
				if (b == 61) {
					this.eof = true;
					break;
				}

				if (b >= 0 && b < DECODE_TABLE.length) {
					int result = DECODE_TABLE[b];
					if (result >= 0) {
						this.modulus = ++this.modulus % 4;
						this.x = (this.x << 6) + result;
						if (this.modulus == 0) {
							this.buffer[this.pos++] = (byte)(this.x >> 16 & 255);
							this.buffer[this.pos++] = (byte)(this.x >> 8 & 255);
							this.buffer[this.pos++] = (byte)(this.x & 255);
						}
					}
				}
			}

			if (this.eof && this.modulus != 0) {
				this.x <<= 6;
				switch(this.modulus) {
					case 2:
						this.x <<= 6;
						this.buffer[this.pos++] = (byte)(this.x >> 16 & 255);
						break;
					case 3:
						this.buffer[this.pos++] = (byte)(this.x >> 16 & 255);
						this.buffer[this.pos++] = (byte)(this.x >> 8 & 255);
				}
			}

		}
	}

	public static boolean isBase64(byte octet) {
		return octet == 61 || octet >= 0 && octet < DECODE_TABLE.length && DECODE_TABLE[octet] != -1;
	}

	public static boolean isArrayByteBase64(byte[] arrayOctet) {
		for(int i = 0; i < arrayOctet.length; ++i) {
			if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i])) {
				return false;
			}
		}

		return true;
	}

	private static boolean containsBase64Byte(byte[] arrayOctet) {
		for(int i = 0; i < arrayOctet.length; ++i) {
			if (isBase64(arrayOctet[i])) {
				return true;
			}
		}

		return false;
	}

	public static byte[] encodeBase64(byte[] binaryData) {
		return encodeBase64(binaryData, false);
	}

	public static String encodeBase64String(byte[] binaryData) {
		return newStringUtf8(encodeBase64(binaryData, true));
	}

	public static String encode(String data) {
		return newStringUtf8(encodeBase64(data.getBytes(), true));
	}

	public static byte[] encodeBase64URLSafe(byte[] binaryData) {
		return encodeBase64(binaryData, false, true);
	}

	public static String encodeBase64URLSafeString(byte[] binaryData) {
		return newStringUtf8(encodeBase64(binaryData, false, true));
	}

	public static byte[] encodeBase64Chunked(byte[] binaryData) {
		return encodeBase64(binaryData, true);
	}

	public Object decodebyte(Object pObject) throws IllegalArgumentException {
		if (pObject instanceof byte[]) {
			return this.decodebyte((byte[])((byte[])pObject));
		} else if (pObject instanceof String) {
			return this.decodebyte((String)pObject);
		} else {
			throw new IllegalArgumentException("Parameter supplied to Base64 decodebyte is not a byte[] or a String");
		}
	}

	public byte[] decodebyte(String pArray) {
		return this.decodebyte(this.getBytesUtf8(pArray));
	}

	private byte[] getBytesUtf8(String text) {
		if (text == null) {
			return null;
		} else {
			try {
				return text.getBytes("UTF-8");
			} catch (UnsupportedEncodingException var3) {
				return null;
			}
		}
	}

	public byte[] decodebyte(byte[] pArray) {
		this.reset();
		if (pArray != null && pArray.length != 0) {
			long len = (long)(pArray.length * 3 / 4);
			byte[] buf = new byte[(int)len];
			this.setInitialBuffer(buf, 0, buf.length);
			this.decodebyte(pArray, 0, pArray.length);
			this.decodebyte(pArray, 0, -1);
			byte[] result = new byte[this.pos];
			this.readResults(result, 0, result.length);
			return result;
		} else {
			return pArray;
		}
	}

	public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
		return encodeBase64(binaryData, isChunked, false);
	}

	public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe) {
		return encodeBase64(binaryData, isChunked, urlSafe, 2147483647);
	}

	public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
		if (binaryData != null && binaryData.length != 0) {
			long len = getEncodeLength(binaryData, 76, CHUNK_SEPARATOR);
			if (len > (long)maxResultSize) {
				throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len + ") than the specified maxium size of " + maxResultSize);
			} else {
				Base64Util b64 = isChunked ? new Base64Util(urlSafe) : new Base64Util(0, CHUNK_SEPARATOR, urlSafe);
				return b64.encode(binaryData);
			}
		} else {
			return binaryData;
		}
	}

	public static byte[] decodebyteBase64(String base64String) {
		return (new Base64Util()).decodebyte(base64String);
	}

	public static String decode(String data) {
		return new String((new Base64Util()).decodebyte(data));
	}

	public static String decode(String data, String encoding) throws UnsupportedEncodingException {
		return new String((new Base64Util()).decodebyte(data), encoding);
	}

	public static byte[] decodebyteBase64(byte[] base64Data) {
		return (new Base64Util()).decodebyte(base64Data);
	}

	/** @deprecated */
	@Deprecated
	static byte[] discardWhitespace(byte[] data) {
		byte[] groomedData = new byte[data.length];
		int bytesCopied = 0;
		int i = 0;

		while(i < data.length) {
			switch(data[i]) {
				default:
					groomedData[bytesCopied++] = data[i];
				case 9:
				case 10:
				case 13:
				case 32:
					++i;
			}
		}

		byte[] packedData = new byte[bytesCopied];
		System.arraycopy(groomedData, 0, packedData, 0, bytesCopied);
		return packedData;
	}

	private static boolean isWhiteSpace(byte byteToCheck) {
		switch(byteToCheck) {
			case 9:
			case 10:
			case 13:
			case 32:
				return true;
			default:
				return false;
		}
	}

	public Object encode(Object pObject) throws IllegalArgumentException {
		if (!(pObject instanceof byte[])) {
			throw new IllegalArgumentException("Parameter supplied to Base64 encode is not a byte[]");
		} else {
			return this.encode((byte[])((byte[])pObject));
		}
	}

	public String encodeToString(byte[] pArray) {
		return newStringUtf8(this.encode(pArray));
	}

	public byte[] encode(byte[] pArray) {
		this.reset();
		if (pArray != null && pArray.length != 0) {
			long len = getEncodeLength(pArray, this.lineLength, this.lineSeparator);
			byte[] buf = new byte[(int)len];
			this.setInitialBuffer(buf, 0, buf.length);
			this.encode(pArray, 0, pArray.length);
			this.encode(pArray, 0, -1);
			if (this.buffer != buf) {
				this.readResults(buf, 0, buf.length);
			}

			if (this.isUrlSafe() && this.pos < buf.length) {
				byte[] smallerBuf = new byte[this.pos];
				System.arraycopy(buf, 0, smallerBuf, 0, this.pos);
				buf = smallerBuf;
			}

			return buf;
		} else {
			return pArray;
		}
	}

	private static long getEncodeLength(byte[] pArray, int chunkSize, byte[] chunkSeparator) {
		chunkSize = chunkSize / 4 * 4;
		long len = (long)(pArray.length * 4 / 3);
		long mod = len % 4L;
		if (mod != 0L) {
			len += 4L - mod;
		}

		if (chunkSize > 0) {
			boolean lenChunksPerfectly = len % (long)chunkSize == 0L;
			len += len / (long)chunkSize * (long)chunkSeparator.length;
			if (!lenChunksPerfectly) {
				len += (long)chunkSeparator.length;
			}
		}

		return len;
	}

	public static BigInteger decodebyteInteger(byte[] pArray) {
		return new BigInteger(1, decodebyteBase64(pArray));
	}

	public static byte[] encodeInteger(BigInteger bigInt) {
		if (bigInt == null) {
			throw new NullPointerException("encodeInteger called with null parameter");
		} else {
			return encodeBase64(toIntegerBytes(bigInt), false);
		}
	}

	static byte[] toIntegerBytes(BigInteger bigInt) {
		int bitlen = bigInt.bitLength();
		bitlen = bitlen + 7 >> 3 << 3;
		byte[] bigBytes = bigInt.toByteArray();
		if (bigInt.bitLength() % 8 != 0 && bigInt.bitLength() / 8 + 1 == bitlen / 8) {
			return bigBytes;
		} else {
			int startSrc = 0;
			int len = bigBytes.length;
			if (bigInt.bitLength() % 8 == 0) {
				startSrc = 1;
				--len;
			}

			int startDst = bitlen / 8 - len;
			byte[] resizedBytes = new byte[bitlen / 8];
			System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
			return resizedBytes;
		}
	}

	private void reset() {
		this.buffer = null;
		this.pos = 0;
		this.readPos = 0;
		this.currentLinePos = 0;
		this.modulus = 0;
		this.eof = false;
	}
}
