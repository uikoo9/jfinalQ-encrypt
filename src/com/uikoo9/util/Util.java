package com.uikoo9.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util
{
  public static boolean isEmpty(String s)
  {
    return (s == null) || (s.trim().equals(""));
  }

  public static String changeFileExt(String fileName, String ext)
  {
    if (isEmpty(fileName)) return null;

    int dot = fileName.lastIndexOf('.');
    if ((dot == -1) || (dot + 1 == fileName.length())) return null;

    String fileNameStr = fileName.substring(0, dot + 1);
    return fileNameStr + ext;
  }

  public static String format(Date date, String format)
  {
    return date == null ? null : new SimpleDateFormat(format).format(date);
  }

  public static String dateStr()
  {
    return format(new Date(), "yyyyMMddHHmmss");
  }
}