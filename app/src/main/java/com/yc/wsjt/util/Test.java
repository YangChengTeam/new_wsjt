package com.yc.wsjt.util;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by admin on 2017/6/7.
 */

public class Test {
    public static void main(String[] args) throws IOException {
        // Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = "http://www.ttmeiju.com/";

        Logger.e("Fetching %s..." + url);

        Document doc = Jsoup.connect(url).get();
        Elements dayElements = doc.select("weekhot");
        Elements links = doc.select("listname").select("a[href]"); //"a[href]" //带有href属性的a元素
        Elements media = doc.select("listdetail").select("a").select("img").select("[src]");   //利用属性查找元素，比如：[href]


        Logger.e("src---size--->", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                Logger.e("img--->", src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"), trim(src.attr("alt"), 20));
            else
                Logger.e(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        Logger.e("\nLinks: (%d)", links.size());
        for (Element link : links) {
            Logger.e(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}
