package com.qy.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

public class FileUtils {
    /**
     * @param filePath
     * @return
     * @serial 获取版本信息
     */
    public static String getVersion(String filePath) {
        File file = new File(filePath);
        RandomAccessFile raf = null;
        byte[] buffer;
        String str;
        try {
            raf = new RandomAccessFile(file, "r");
            buffer = new byte[64];
            raf.read(buffer);
            str = "" + (char) buffer[0] + (char) buffer[1];
            if (!"MZ".equals(str)) {
                return null;
            }

            int peOffset = unpack(new byte[]{buffer[60], buffer[61],
                    buffer[62], buffer[63]});
            if (peOffset < 64) {
                return null;
            }

            raf.seek(peOffset);
            buffer = new byte[24];
            raf.read(buffer);
            str = "" + (char) buffer[0] + (char) buffer[1];
            if (!"PE".equals(str)) {
                return null;
            }
            int machine = unpack(new byte[]{buffer[4], buffer[5]});
			/*if (machine != 332) {
				return null;
			}*/

            int noSections = unpack(new byte[]{buffer[6], buffer[7]});
            int optHdrSize = unpack(new byte[]{buffer[20], buffer[21]});
            raf.seek(raf.getFilePointer() + optHdrSize);
            boolean resFound = false;
            for (int i = 0; i < noSections; i++) {
                buffer = new byte[40];
                raf.read(buffer);
                str = "" + (char) buffer[0] + (char) buffer[1]
                        + (char) buffer[2] + (char) buffer[3]
                        + (char) buffer[4];
                if (".rsrc".equals(str)) {
                    resFound = true;
                    break;
                }
            }
            if (!resFound) {
                return null;
            }

            int infoVirt = unpack(new byte[]{buffer[12], buffer[13],
                    buffer[14], buffer[15]});
            int infoSize = unpack(new byte[]{buffer[16], buffer[17],
                    buffer[18], buffer[19]});
            int infoOff = unpack(new byte[]{buffer[20], buffer[21],
                    buffer[22], buffer[23]});
            raf.seek(infoOff);
            buffer = new byte[infoSize];
            raf.read(buffer);
            int numDirs = unpack(new byte[]{buffer[14], buffer[15]});
            boolean infoFound = false;
            int subOff = 0;
            for (int i = 0; i < numDirs; i++) {
                int type = unpack(new byte[]{buffer[i * 8 + 16],
                        buffer[i * 8 + 17], buffer[i * 8 + 18],
                        buffer[i * 8 + 19]});
                if (type == 16) { // FILEINFO resource
                    infoFound = true;
                    subOff = unpack(new byte[]{buffer[i * 8 + 20],
                            buffer[i * 8 + 21], buffer[i * 8 + 22],
                            buffer[i * 8 + 23]});
                    break;
                }
            }
            if (!infoFound) {
                return null;
            }

            subOff = subOff & 0x7fffffff;
            infoOff = unpack(new byte[]{buffer[subOff + 20],
                    buffer[subOff + 21], buffer[subOff + 22],
                    buffer[subOff + 23]}); // offset of first FILEINFO
            infoOff = infoOff & 0x7fffffff;
            infoOff = unpack(new byte[]{buffer[infoOff + 20],
                    buffer[infoOff + 21], buffer[infoOff + 22],
                    buffer[infoOff + 23]}); // offset to data
            int dataOff = unpack(new byte[]{buffer[infoOff],
                    buffer[infoOff + 1], buffer[infoOff + 2],
                    buffer[infoOff + 3]});
            dataOff = dataOff - infoVirt;

            int version1 = unpack(new byte[]{buffer[dataOff + 48],
                    buffer[dataOff + 48 + 1]});
            int version2 = unpack(new byte[]{buffer[dataOff + 48 + 2],
                    buffer[dataOff + 48 + 3]});
            int version3 = unpack(new byte[]{buffer[dataOff + 48 + 4],
                    buffer[dataOff + 48 + 5]});
            int version4 = unpack(new byte[]{buffer[dataOff + 48 + 6],
                    buffer[dataOff + 48 + 7]});
            System.out.println(version2 + "." + version1 + "." + version4 + "."
                    + version3);
            return version2 + "." + version1 + "." + version4 + "." + version3;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static int unpack(byte[] b) {
        int num = 0;
        for (int i = 0; i < b.length; i++) {
            num = 256 * num + (b[b.length - 1 - i] & 0xff);
        }
        return num;
    }
	
	/*public static String getDllVersion(File item) { 
		RandomAccessFile input = null; 
		try { 
		input = new RandomAccessFile(item,"r"); 
		input.seek(item.length() - 2); 
		byte b1 = input.readByte(); 
		byte b2 = input.readByte(); 
		// find data: 0x00 0x41 (16 byte char 'A') 
		while (!(b1 == (byte) 0 && b2 == (byte) 0x41)) { 
		input.seek(input.getFilePointer() - 3); 
		b1 = input.readByte(); 
		b2 = input.readByte(); 
		} 
		input.seek(input.getFilePointer() - 2); 
		// get"Assembly Version"and the version string 
		StringBuilder[] sbs = new StringBuilder[2]; 
		for (int i = 0; i < sbs.length; i++) { 
		sbs[i] = new StringBuilder(); 
		b1 = input.readByte(); 
		b2 = input.readByte(); 
		while (b2 != (byte) 0) { 
		sbs[i].append((char) b2); 
		b1 = input.readByte(); 
		b2 = input.readByte(); 
		} 
		System.out.println(sbs[i]); 
		} 
		// return the version string 
		if ("Assembly Version".equalsIgnoreCase(sbs[0].toString())) { 
		return sbs[1].toString(); 
		} 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} finally { 
		if (input != null) { 
		try { 
		input.close(); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		} 
		} 
		// don't find version string, return a space 
		return""; 
		} 
		public static String getExeVersion(File item) { 
		return getDllVersion(item); 
		} 
		
		public static void getdll(String filePath) throws IOException {
			 File file = new File(filePath);
		        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		        String s = "F i l e V e r s i o n";
		        byte[] b = s.getBytes();
		        for (int i = 0; i < b.length; i++) {
		            if(b[i] == 32){
		                b[i] = 0;
		            }
		        }
		        String str = new String(b);
		           
		        byte[] data = null;
		           
		        while(reader.ready()){
		            String line = reader.readLine();
		            int index = line.indexOf(str);
		            if(index > 0){
		                int len = index + str.length() + 5;
		                String sb = line.substring(len,len+16);
		                data = sb.getBytes();
		                break;
		            }
		        }
		           
		        int version1 = getVersion(data, 0, 1);
		        int version2 = getVersion(data, 3, 5);
		        int version3 = getVersion(data, 7, 9);
		        int version4 = getVersion(data, 11, 15);
		           
		           
		        String version = version1 + "." + version2 + "." + version3 + "." + version4;
		           
		        System.out.println(version);
		        System.out.println(new String(data));
		}
		
		 public static int getVersion(byte[] data,int start,int end){
		        int version = 0;
		        int len = end-start;
		        byte[] d = new byte[len];
		        for (int i = start,j=0; i < end; i++,j++) {
		            d[j] = data[i];
		        }
		        version = Integer.parseInt(new String(d).trim());
		        return version;
		    }
		 
		 
		 
		 
		 
		 
		 *//**
     * 获取文件版本信息
     * @param filePath
     * @return
     *//*
		    public static String getVersion1(String filePath) {
		        String version = null;
		        RandomAccessFile raf = null;
		        byte[] buffer;
		        String str;
		        try {
		            // r(只读),
		            // rw(读写),rws(读写还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。),rwd(读写还要求对文件内容的每个更新都同步写入到底层存储设备。)
		            raf = new RandomAccessFile(filePath, "r");
		            buffer = new byte[64];
		            raf.read(buffer);
		            str = bytesToStr(buffer, 0, 1);
		            if (!"MZ".equals(str)) return version;
		            int peOffset = unpack(buffer, 60, 61, 62, 63);
		            if (peOffset < 64) return version;
		            raf.seek(peOffset);
		            buffer = new byte[24];
		            raf.read(buffer);
		            str = bytesToStr(buffer, 0, 1);
		            if (!"PE".equals(str)) return version;
		            int machine = unpack(buffer, 4, 5);
		            //if (332 != machine) return version;
		            int noSections = unpack(buffer, 6, 7);
		            int optHdrSize = unpack(buffer, 20, 21);
		            raf.seek(raf.getFilePointer() + optHdrSize);
		            boolean resFound = false;
		            for (int i = 0; i < noSections; i++) {
		                buffer = new byte[40];
		                raf.read(buffer);
		                str = bytesToStr(buffer, 0, 1, 2, 3, 4);
		                if (".rsrc".equals(str)) {
		                    resFound = true;
		                    break;
		                }
		            }
		            if (!resFound) return version;
		            int infoVirt = unpack(buffer, 12, 13, 14, 15);
		            int infoSize = unpack(buffer, 16, 17, 18, 19);
		            int infoOff = unpack(buffer, 20, 21, 22, 23);
		            raf.seek(infoOff);
		            buffer = new byte[infoSize];
		            raf.read(buffer);
		            int numDirs = unpack(buffer, 14, 15);
		            boolean infoFound = false;
		            int subOff = 0;
		            for (int i = 0; i < numDirs; i++) {
		                int type = unpack(buffer, i * 8 + 16, i * 8 + 17, i * 8 + 18,
		                        i * 9 + 19);
		                if (16 == type) {
		                    infoFound = true;
		                    subOff = unpack(buffer, i * 8 + 20, i * 8 + 21, i * 8 + 22,
		                            i * 8 + 23);
		                    break;
		                }
		            }
		            if (!infoFound) return version;
		            subOff = subOff & 0x7fffffff;
		            infoOff = unpack(buffer, subOff + 20, subOff + 21, subOff + 22,
		                    subOff + 23);
		            infoOff = infoOff & 0x7fffffff;
		            infoOff = unpack(buffer, infoOff + 20, infoOff + 21, infoOff + 22,
		                    infoOff + 23);
		            int dataOff = unpack(buffer, infoOff, infoOff + 1, infoOff + 2,
		                    infoOff + 3);
		            dataOff = dataOff - infoVirt;
		            version = "";
		            for (int i = 0; i < buffer.length - 48; i += 2) {
		                int index = dataOff + 48 + i;
		                if (index + 2 > buffer.length) break;
		                version += unpack(buffer, index, index + 1);
		            }
		        } catch (Exception e) {} finally {
		            if (null != raf) try {
		                raf.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return version;
		    }

		    *//**
     * 将字节数组里面多个字节转化为文本
     * @param buffer
     * @param index
     * @return
     *//*
		    public static String bytesToStr(byte[] buffer, int... index) {
		        String result = "";
		        for (int i : index) {
		            result += (char) buffer[i];
		        }
		        return result;
		    }

		    public static int unpack(byte[] buffer, int... index) {
		        int num = 0;
		        for (int i = 0; i < index.length; i++) {
		            num = 256 * num + (buffer[index[index.length - 1 - i]] & 0xff);
		        }
		        return num;
		    }*/


    /**
     * 获取附件文件SIZE
     * @param fileSize
     * @return
     */
    public static String getFileSizeName(int fileSize) {
        String sizeName = "";
        if(NumberUtils.isNullOfEmpty(fileSize)) {
            return sizeName;
        }
        if(fileSize >= 1024*1024){
                DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
                sizeName = (df.format((float)fileSize/(1024*1024)) + " MB");
        }else if(fileSize >= 1024*1024*1024){
            DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
            sizeName = (df.format((float)fileSize/(1024*1024*1024)) + " GB");
        }else{
            DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
            sizeName = (df.format((float)fileSize/1024) + " KB");
        }
        return sizeName;
    }

}
