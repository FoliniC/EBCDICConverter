import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Converter {

	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] bytes = new byte[] { 0x01, 0x10, 0x15 };
		String str = new String(bytes, "IBM-037");

		byte[] result = getEBCDICRawData(str);
		System.out.print("EBCDIC input:");
		for (byte b : result) {
			System.out.print(Integer.toString((b & 0xff) + 0x100, 16).substring(1) + " ");
		}

		System.out.println();
		System.out.println("hex output: " + evaluate(str));
		try {
			System.out.println(toEbcdic("C120C2"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String toEbcdic(String strToConvert) throws IOException {
		String[] test = strToConvert.split("(?<=\\G..)");
		ByteBuffer sb = ByteBuffer.allocate(test.length);
		for (String s : test)
			sb.put((byte) Short.parseShort(s, 16));
		return new String(sb.array(), "CP1047");
	}

	public static String evaluate(String edata) throws UnsupportedEncodingException {
		byte[] ebcdiResult = getEBCDICRawData(edata);

		String hexResult = getHexData(ebcdiResult);
		return hexResult;
	}

	public static byte[] getEBCDICRawData(String edata) throws UnsupportedEncodingException {
		byte[] result = null;

		String ebcdic_encoding = "IBM-037";
		result = edata.getBytes(ebcdic_encoding);

		return result;
	}

	public static String getHexData(byte[] result) {
		String output = asHex(result);
		return output;
	}

	public static String asHex(byte[] buf) {
		char[] HEX_CHARS = "0123456789abcdef".toCharArray();
		char[] chars = new char[2 * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}
}
