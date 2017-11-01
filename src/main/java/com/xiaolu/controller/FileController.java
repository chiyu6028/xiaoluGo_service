package com.xiaolu.controller;

import com.xiaolu.domain.ProcessInfo;
import com.xiaolu.util.ParamsReqAndResp;
import com.xiaolu.util.PropertiesUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by chinaD on 2017/10/16.
 */
@Controller
public class FileController {

    ParamsReqAndResp paramsReqAndResp = ParamsReqAndResp.getInstance();

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response, HttpServletRequest request) {

        String path = PropertiesUtil.getInstance().getProperties("download_path");
        File file = new File(path, fileName);

        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[2048];

            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            OutputStream outputStream = null;

            try {

                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                outputStream = response.getOutputStream();
                int i = bufferedInputStream.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bufferedInputStream.read(buffer);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /*
    * common-fileupload的 上传方式
    * */
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public void uploadFile(HttpServletResponse response, HttpServletRequest request) {

        final HttpSession session = request.getSession();

        String savePath = PropertiesUtil.getInstance().getProperties("upload_path");
        File file = new File(savePath);
        //1.判断上传目录是否存在，如果不存在则新建。
        if (!file.exists()) {
            file.mkdirs();
        }

        //2.设置上传文件的最大值
        int maxSize = 2 * 1024 * 1024;

        //3.创建工厂对象和文件上传对象；
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        //4.设置进度条
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long l, long l1, int i) {
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setItemNum(i);
                processInfo.setReadSize(l);
                processInfo.setTotalSize(l1);
                processInfo.setShow(l+"/"+l1+"byte");
                processInfo.setRate(Math.round(new Float(l)/new Float(l1)*100));
                session.setAttribute("processInfo",processInfo);
            }
        });

        try {
            request.setCharacterEncoding("utf-8"); // 设置编码
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            //解析上传情况
            List<FileItem> items = upload.parseRequest(request);
            if (items.size() == 0){
                Map errorMap = new HashMap();
                errorMap.put("error","请选择上传文件");
                errorMap.put("flag","-1");
                String err = paramsReqAndResp.getJSONString(errorMap);
                paramsReqAndResp.renderData(response,err);
            }
            for (FileItem item : items) {
                //5.如果是普通输入数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println(name + "=" + value);
                } else {//如果是封装的上传文件
                    String fileName = item.getName();
                    if (fileName != null && !"".equals(fileName)  ){
                        //6.获取上传文件大小。
                        long upLoadFileSize = item.getSize();
                        if (upLoadFileSize > maxSize){
                            Map errorMap = new HashMap();
                            errorMap.put("msg","上传文件大小超过规定");
                            errorMap.put("flag","-1");
                            String err = paramsReqAndResp.getJSONString(errorMap);
                            paramsReqAndResp.renderData(response,err);
                        }else{
                            //7.注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，
                            // 如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                            fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
                            //8.获取item中的上传文件的输入流
                            InputStream in = null;
                            FileOutputStream fileOut = null;
                            try {
                                in = item.getInputStream();
                                //9.创建一个文件输出流
                                fileOut = new FileOutputStream(savePath + "\\" + fileName);
                                //10.创建一个缓冲区
                                byte buffer[] = new byte[1024];
                                //11.定义判断输入流中的数据是否已经读完的标识
                                int len = 0;
                                while ((len = in.read(buffer)) > 0){
                                    fileOut.write(buffer,0,len);
                                }
                                Map successMap = new HashMap();
                                successMap.put("msg","上传文件成功");
                                successMap.put("flag","1");
                                String err = paramsReqAndResp.getJSONString(successMap);
                                paramsReqAndResp.renderData(response,err);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (in != null){
                                    try {
                                        in.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (fileOut != null){
                                    try {
                                        fileOut.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }else{
                        Map errorMap = new HashMap();
                        errorMap.put("error","上传文件名不存在");
                        errorMap.put("flag","-1");
                        String err = paramsReqAndResp.getJSONString(errorMap);
                        paramsReqAndResp.renderData(response,err);
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public @ResponseBody Object process(HttpServletRequest request,HttpServletResponse response){
        return ( ProcessInfo)request.getSession().getAttribute("processInfo");
    }

    /**
     * spring mvc的上传方式
     * */
    //@RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, ModelMap model) {
        System.out.println("开始");
        //String path = request.getSession().getServletContext().getRealPath("upload");
        String path = PropertiesUtil.getInstance().getProperties("upload_path");
        String fileName = file.getOriginalFilename();
        // String fileName = new Date().getTime()+".jpg";
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
