import java.io.*;
import java.net.*;

/**
 * Created by Mave on 2015/4/24 0024.
 * Extra question 2: Design a downloader using threads.
 */

class FileSize {
    int no = 0;
    long fileStart = 0;
    long fileEnd = 0;

    FileSize(int n, long s, long e) {
        no = n;
        fileStart = s;
        fileEnd = e;

    }
}

class FileDefragment {
    FileSize fsize[];

    FileDefragment(int threadNum, String urlStr) {
        try {
            URL url = new URL(urlStr);

            URLConnection urlConnection = url.openConnection();
            long fileLength = urlConnection.getContentLengthLong();
            fsize = new FileSize[threadNum];

            long sLength = fileLength / threadNum;

            for (int i = 0; i < threadNum - 1; i++)
                fsize[i] = new FileSize(i, i * sLength, +(i + 1) * sLength - 1);

            fsize[threadNum - 1] = new FileSize(threadNum, (threadNum - 1) * sLength, fileLength - 1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MTDownload implements Runnable {
    int no = 0;
    long st;
    long end;
    String urlStr;
    String filename;


    MTDownload(int no, long st, long end, String url) {
        this.no = no;
        this.st = st;
        this.end = end;
        this.urlStr = url;
        this.filename = "D:/" + url.substring(url.lastIndexOf("/"), url.length()) + ".tmpdl";
    }


    @Override
    public void run() {
        try {
            RandomAccessFile ras = new RandomAccessFile(filename + no, "rw");
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();

            urlConnection.setRequestProperty("RANGE", "BYTES=" + st + "-" + end);
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();
            byte[] buffer = new byte[512];
            int len = 0;

            while (true) {
                len = is.read(buffer);
                if (len == -1) break;
                ras.write(buffer, 0, len);
            }

            ras.close();
            is.close();

            System.out.println("Thread " + no + " Download OK!!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


public class Ex62_Downloader {
    public static void main(String[] args) {
        String urlStr = "http://www.sit.edu.cn/page/main300/images/2.jpg";
        int ThreadNum = 5;
        FileDefragment fd = new FileDefragment(ThreadNum, urlStr);
        Thread ta[] = new Thread[ThreadNum];

        for (int i = 0; i < ThreadNum; i++) {
            ta[i] = new Thread(new MTDownload(i, fd.fsize[i].fileStart, fd.fsize[i].fileEnd, urlStr));
            ta[i].start();
        }

        for (int i = 0; i < ThreadNum; i++) {
            try {
                ta[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        String filename = "D:/" + urlStr.substring(urlStr.lastIndexOf("/"), urlStr.length());
        int len = 0;
        byte buffer[] = new byte[512];
        try {
            RandomAccessFile ras = new RandomAccessFile(filename, "rw");
            for (int i = 0; i < ThreadNum; i++) {
                RandomAccessFile ras1 = new RandomAccessFile(filename + ".tmpdl" + i, "rw");
                while (true) {
                    len = ras1.read(buffer);
                    if (len == -1) break;
                    ras.write(buffer, 0, len);
                }
                ras1.close();
            }

            ras.close();

            for (int i = 0; i < ThreadNum; i++) {
                File f = new File(filename + ".tmpdl" + i);
                f.delete();
            }

            System.out.println(filename + " -> Download Success!!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
