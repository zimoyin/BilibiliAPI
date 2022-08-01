package github.zimoyin.core.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Canon on 2017/2/4.
 * 在控制台打印一个二维码
 */
public class ZxingImageQR {

    // static String BLACK = "\033[47m   \033[0m";
    // static String WHITE = "\033[40m   \033[0m";
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";
    static final int[] ALPHANUMERIC_TABLE = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  // 0x00-0x0f
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  // 0x10-0x1f
            36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43,  // 0x20-0x2f
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1,  // 0x30-0x3f
            -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,  // 0x40-0x4f
            25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1,  // 0x50-0x5f
    };
    static String BLACK = "\033[40m  \033[0m";
    static String WHITE = "\033[47m  \033[0m";

    /**
     * 将二维码在控制台输出
     * @param content 内容
     * @return 返回正方形二维码矩阵的高度(宽度)
     */
    public static int print(String content) {
//        String content = "https://github.com/zxing/zxing" ;// 内容
//        if( args.length > 0) {
//            content = args[0];
//        }

        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // TODO 算出最合适的宽高度
        // 现在是使用 zxing 的代码计算的
        int maxWidth = 8;
        try {
            maxWidth += getVersion(content, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = maxWidth; // 图像宽度
        int height = maxWidth; // 图像高度

        BitMatrix bitMatrix = null;// 生成矩阵
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        //去除黑色边框
        //行列最大与最小值
        int[][] a = new int[2][2];//开始于结束位置
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxWidth; j++) {
                //BLACK
                if (bitMatrix.get(i, j)) {
                    //起始位置
                    if (a[0][0] == 0) {
                        a[0][0] = i -1;
                        a[0][1] = j -1;
                    }
                    //结束位置
                    a[1][0] = i +2;
                    a[1][1] = j + 2;
                }
            }
        }

        for (int i = a[0][0]; i < a[1][0]; i++) {
            for (int j = a[0][1]; j < a[1][1]; j++) {
                System.out.print(bitMatrix.get(i, j) ? BLACK : WHITE);
//                System.out.print(bitMatrix.get(i, j) ? "1" : "0");
            }
            System.out.print('\n');
        }
//        for (int i = 0; i < maxWidth; i++) {
//            for (int j = 0; j < maxWidth; j++) {
//                System.out.print(bitMatrix.get(i, j) ? BLACK : WHITE);
//                System.out.print(bitMatrix.get(i, j) ? "1" : "0");
//            }
//            System.out.print('\n');
//        }

        return maxWidth;
    }

    static int getVersion(String content, Map<EncodeHintType, Object> hints) throws WriterException {

        // Determine what character encoding has been specified by the caller, if any
        String encoding = "ISO-8859-1";
        if (hints != null && hints.containsKey(EncodeHintType.CHARACTER_SET)) {
            encoding = hints.get(EncodeHintType.CHARACTER_SET).toString();
        }

        // Pick an encoding mode appropriate for the content. Note that this will not attempt to use
        // multiple modes / segments even if that were more efficient. Twould be nice.
        Mode mode = chooseMode(content, encoding);

        // This will store the header information, like mode and
        // length, as well as "header" segments like an ECI segment.
        BitArray headerBits = new BitArray();

        // Append ECI segment if applicable
        if (mode == Mode.BYTE && !DEFAULT_BYTE_MODE_ENCODING.equals(encoding)) {
            CharacterSetECI eci = CharacterSetECI.getCharacterSetECIByName(encoding);
            if (eci != null) {
                appendECI(eci, headerBits);
            }
        }

        // (With ECI in place,) Write the mode marker
        appendModeInfo(mode, headerBits);

        // Collect data within the main segment, separately, to count its size if needed. Don't add it to
        // main payload yet.
        BitArray dataBits = new BitArray();
        appendBytes(content, mode, dataBits, encoding);


        int provisionalBitsNeeded = calculateBitsNeeded(mode, headerBits, dataBits, Version.getVersionForNumber(1));
        for (int versionNum = 1; versionNum <= 40; versionNum++) {
            Version version = Version.getVersionForNumber(versionNum);
            if (willFit(provisionalBitsNeeded, version, ErrorCorrectionLevel.L)) {
                return version.getVersionNumber() * 4 + 17;
            }
        }
        return 177;
        /*
        double d = Math.sqrt(contentLength * 8 + 209);
        int version = (int)Math.ceil(d) - 20;
        if(version <=0 ) version = 1;
        if(version > 40 ) version = 40;
        return 8 * 4 + 17;
        */
    }

    static boolean willFit(int numInputBits, Version version, ErrorCorrectionLevel ecLevel) {
        // In the following comments, we use numbers of Version 7-H.
        // numBytes = 196
        int numBytes = version.getTotalCodewords();
        // getNumECBytes = 130
        Version.ECBlocks ecBlocks = version.getECBlocksForLevel(ecLevel);
        int numEcBytes = ecBlocks.getTotalECCodewords();
        // getNumDataBytes = 196 - 130 = 66
        int numDataBytes = numBytes - numEcBytes;
        int totalInputBytes = (numInputBits + 7) / 8;
        return numDataBytes >= totalInputBytes;
    }

    static int calculateBitsNeeded(Mode mode,
                                   BitArray headerBits,
                                   BitArray dataBits,
                                   Version version) {
        return headerBits.getSize() + mode.getCharacterCountBits(version) + dataBits.getSize();
    }

    static Mode chooseMode(String content, String encoding) {
        if ("Shift_JIS".equals(encoding) && isOnlyDoubleByteKanji(content)) {
            // Choose Kanji mode if all input are double-byte characters
            return Mode.KANJI;
        }
        boolean hasNumeric = false;
        boolean hasAlphanumeric = false;
        for (int i = 0; i < content.length(); ++i) {
            char c = content.charAt(i);
            if (c >= '0' && c <= '9') {
                hasNumeric = true;
            } else if (getAlphanumericCode(c) != -1) {
                hasAlphanumeric = true;
            } else {
                return Mode.BYTE;
            }
        }
        if (hasAlphanumeric) {
            return Mode.ALPHANUMERIC;
        }
        if (hasNumeric) {
            return Mode.NUMERIC;
        }
        return Mode.BYTE;
    }

    static boolean isOnlyDoubleByteKanji(String content) {
        byte[] bytes;
        try {
            bytes = content.getBytes("Shift_JIS");
        } catch (UnsupportedEncodingException ignored) {
            return false;
        }
        int length = bytes.length;
        if (length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < length; i += 2) {
            int byte1 = bytes[i] & 0xFF;
            if ((byte1 < 0x81 || byte1 > 0x9F) && (byte1 < 0xE0 || byte1 > 0xEB)) {
                return false;
            }
        }
        return true;
    }

    static int getAlphanumericCode(int code) {
        if (code < ALPHANUMERIC_TABLE.length) {
            return ALPHANUMERIC_TABLE[code];
        }
        return -1;
    }

    static void appendBytes(String content,
                            Mode mode,
                            BitArray bits,
                            String encoding) throws WriterException {
        switch (mode) {
            case NUMERIC:
                appendNumericBytes(content, bits);
                break;
            case ALPHANUMERIC:
                appendAlphanumericBytes(content, bits);
                break;
            case BYTE:
                append8BitBytes(content, bits, encoding);
                break;
            case KANJI:
                appendKanjiBytes(content, bits);
                break;
            default:
                throw new WriterException("Invalid mode: " + mode);
        }
    }

    static void appendNumericBytes(CharSequence content, BitArray bits) {
        int length = content.length();
        int i = 0;
        while (i < length) {
            int num1 = content.charAt(i) - '0';
            if (i + 2 < length) {
                // Encode three numeric letters in ten bits.
                int num2 = content.charAt(i + 1) - '0';
                int num3 = content.charAt(i + 2) - '0';
                bits.appendBits(num1 * 100 + num2 * 10 + num3, 10);
                i += 3;
            } else if (i + 1 < length) {
                // Encode two numeric letters in seven bits.
                int num2 = content.charAt(i + 1) - '0';
                bits.appendBits(num1 * 10 + num2, 7);
                i += 2;
            } else {
                // Encode one numeric letter in four bits.
                bits.appendBits(num1, 4);
                i++;
            }
        }
    }

    static void append8BitBytes(String content, BitArray bits, String encoding)
            throws WriterException {
        byte[] bytes;
        try {
            bytes = content.getBytes(encoding);
        } catch (UnsupportedEncodingException uee) {
            throw new WriterException(uee);
        }
        for (byte b : bytes) {
            bits.appendBits(b, 8);
        }
    }

    static void appendKanjiBytes(String content, BitArray bits) throws WriterException {
        byte[] bytes;
        try {
            bytes = content.getBytes("Shift_JIS");
        } catch (UnsupportedEncodingException uee) {
            throw new WriterException(uee);
        }
        int length = bytes.length;
        for (int i = 0; i < length; i += 2) {
            int byte1 = bytes[i] & 0xFF;
            int byte2 = bytes[i + 1] & 0xFF;
            int code = (byte1 << 8) | byte2;
            int subtracted = -1;
            if (code >= 0x8140 && code <= 0x9ffc) {
                subtracted = code - 0x8140;
            } else if (code >= 0xe040 && code <= 0xebbf) {
                subtracted = code - 0xc140;
            }
            if (subtracted == -1) {
                throw new WriterException("Invalid byte sequence");
            }
            int encoded = ((subtracted >> 8) * 0xc0) + (subtracted & 0xff);
            bits.appendBits(encoded, 13);
        }
    }

    static void appendAlphanumericBytes(CharSequence content, BitArray bits) throws WriterException {
        int length = content.length();
        int i = 0;
        while (i < length) {
            int code1 = getAlphanumericCode(content.charAt(i));
            if (code1 == -1) {
                throw new WriterException();
            }
            if (i + 1 < length) {
                int code2 = getAlphanumericCode(content.charAt(i + 1));
                if (code2 == -1) {
                    throw new WriterException();
                }
                // Encode two alphanumeric letters in 11 bits.
                bits.appendBits(code1 * 45 + code2, 11);
                i += 2;
            } else {
                // Encode one alphanumeric letter in six bits.
                bits.appendBits(code1, 6);
                i++;
            }
        }
    }

    static void appendECI(CharacterSetECI eci, BitArray bits) {
        bits.appendBits(Mode.ECI.getBits(), 4);
        // This is correct for values up to 127, which is all we need now.
        bits.appendBits(eci.getValue(), 8);
    }

    static void appendModeInfo(Mode mode, BitArray bits) {
        bits.appendBits(mode.getBits(), 4);
    }
}
