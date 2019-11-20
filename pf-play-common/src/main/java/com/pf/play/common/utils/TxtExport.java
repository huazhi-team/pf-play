package com.pf.play.common.utils;

import com.pf.play.common.utils.model.FileCheckModel;
import com.pf.play.common.utils.model.FileConstant;
import com.pf.play.common.utils.model.FileModel;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author df
 * @Description:生成txt文件
 * @create 2019-08-14 14:50
 **/
public class TxtExport {
    private static String path = "D:/test/";
    private static String filenameTemp;


    public static void main(String[] args) throws Exception {
        //生成数据
        String strCurday = DateUtil.getDayNumber(new Date()) + "";
        String BYHYYHQD_FILE_DAT = FileConstant.BYHYYHQD_FILE_DAT.replace("#", strCurday);
        TxtExport.creatTxtFile(BYHYYHQD_FILE_DAT);
        List<FileModel> dataList = testTempData();
        for (FileModel data : dataList){
            String str = data.getPhone() + "|" + data.getUsid() + "|" + data.getActiveTime() + "|" + data.getHzfId() + "|" + data.getCpId() + "|" + data.getQdId() + "|" + data.getChanpId() + "|" + data.getYwName()
                    + "|" + data.getHylx() + "|" + data.getJrmj();
            TxtExport.writeTxtFile(str);
        }

        //文件的md5值
        String fileMD5 = null;
        String ads = path + BYHYYHQD_FILE_DAT;
        File file = new File(ads);
//        fileMD5 = MD5Util.getFileMD5String(file);
        fileMD5 = DigestUtils.md5Hex(new FileInputStream(ads));

        FileCheckModel fileCheckModel = new FileCheckModel();
        fileCheckModel.setFileName(BYHYYHQD_FILE_DAT);
        fileCheckModel.setFileMd5(fileMD5);
        fileCheckModel.setCurday(strCurday);
        fileCheckModel.setWjscDay(DateUtil.getNowLongTime() + "");

        String BYHYYHQD_CHECK_FILE_DAT = FileConstant.BYHYYHQD_CHECK_FILE_DAT.replace("#", strCurday);
        TxtExport.creatTxtFile(BYHYYHQD_CHECK_FILE_DAT);
        String strCheck = fileCheckModel.getFileName() + "|" + fileCheckModel.getFileMd5() + "|" + fileCheckModel.getCurday() + "|" + fileCheckModel.getWjscDay();
        TxtExport.writeTxtFile(strCheck);


    }


    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
//        filenameTemp = path + name + ".txt";
        filenameTemp = path + name;
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr
     *      新内容
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filenameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    public static List<FileModel> testTempData(){
        List<FileModel> list = new ArrayList<>();
        FileModel bean1 = new FileModel();
        bean1.setPhone("13717505291");
        bean1.setUsid("13717505291");
        bean1.setActiveTime("2019-08-14 16:14:11");//2019-08-14 09:23:25
//        bean1.setActiveTime("2019081415370111");

        FileModel bean2 = new FileModel();
        bean2.setPhone("13717505292");
        bean2.setUsid("13717505292");
        bean2.setActiveTime("2019-08-14 16:14:12");//2019-08-14 09:23:25
//        bean2.setActiveTime("2019081415370112");

        FileModel bean3 = new FileModel();
        bean3.setPhone("13717505293");
        bean3.setUsid("13717505293");
        bean3.setActiveTime("2019-08-14 16:14:13");//2019-08-14 09:23:25
//        bean3.setActiveTime("2019081415370113");

        FileModel bean4 = new FileModel();
        bean4.setPhone("13717505294");
        bean4.setUsid("13717505294");
        bean4.setActiveTime("2019-08-14 16:14:14");//2019-08-14 09:23:25
//        bean4.setActiveTime("2019081415370114");

        FileModel bean5 = new FileModel();
        bean5.setPhone("13717505295");
        bean5.setUsid("13717505295");
        bean5.setActiveTime("2019-08-14 16:14:15");//2019-08-14 09:23:25
//        bean5.setActiveTime("2019081415370115");

        FileModel bean6 = new FileModel();
        bean6.setPhone("13717505296");
        bean6.setUsid("13717505296");
        bean6.setActiveTime("2019-08-14 16:14:16");//2019-08-14 09:23:25
//        bean6.setActiveTime("2019081415370116");

        FileModel bean7 = new FileModel();
        bean7.setPhone("13717505297");
        bean7.setUsid("13717505297");
        bean7.setActiveTime("2019-08-14 16:14:17");//2019-08-14 09:23:25
//        bean7.setActiveTime("2019081415370117");

        FileModel bean8 = new FileModel();
        bean8.setPhone("13717505298");
        bean8.setUsid("13717505298");
        bean8.setActiveTime("2019-08-14 16:14:18");//2019-08-14 09:23:25
//        bean8.setActiveTime("2019081415370118");

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        return list;
    }
}
