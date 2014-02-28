/*
 * Copyright (C) 2011-2013 Kuropen.
 * 
 * This file is part of the Electricity Warning Crawler, Version 3.
 * 
 * The Electricity Warning Crawler is free software:
 * you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * The Electricity Warning Crawler is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with The Electricity Warning Crawler.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package co.akabe.elecwarn.util;

/**
 * ユーティリティクラス
 *
 * @author Kuropen
 */
public class ElecwarnUtil {

    /**
     * 電力会社管轄区域を表すショートネームを取得する。
     * なお、会社略称は東京・関西を除き採用しなかった。略称が被る可能性のある会社があるためである。
     *
     * @param i 電力会社パラメーターを有するオブジェクト
     * @return 略称名
     */
    public static String getShortName(EPCOInfo i) {
        String key = i.getCompanyKey();
        return getShortName(key);
    }

    /**
     * 電力会社管轄区域を表すロングネームを取得する。
     *
     * @param key Yahoo! APIの会社コード
     * @return 会社名
     * @throws IllegalArgumentException 未知の会社コードが与えられた場合
     */
    public static String getShortName(String key) {
        if (key.contentEquals("hokkaido")) {
            return "北海道";
        }
        if (key.contentEquals("tohoku")) {
            return "東北";
        }
        if (key.contentEquals("tokyo")) {
            return "東電";
        }
        if (key.contentEquals("chubu")) {
            return "中部";
        }
        if (key.contentEquals("kansai")) {
            return "関電";
        }
        if (key.contentEquals("hokuriku")) {
            return "北陸";
        }
        if (key.contentEquals("chugoku")) {
            return "中国";
        }
        if (key.contentEquals("shikoku")) {
            return "四国";
        }
        if (key.contentEquals("kyushu")) {
            return "九州";
        }
        throw new IllegalArgumentException();
    }

    /**
     * 電力会社管轄区域を表すロングネームを取得する。
     *
     * @param i 電力会社パラメーターを有するオブジェクト
     * @return 会社名
     */
    public static String getLongName(EPCOInfo i) {
        String key = i.getCompanyKey();
        return getLongName(key);
    }

    /**
     * 電力会社管轄区域を表すロングネームを取得する。
     *
     * @param key Yahoo! APIの会社コード
     * @return 会社名
     * @throws IllegalArgumentException 未知の会社コードが与えられた場合
     */
    public static String getLongName(String key) {
        if (key.contentEquals("hokkaido")) {
            return "北海道電力";
        }
        if (key.contentEquals("tohoku")) {
            return "東北電力";
        }
        if (key.contentEquals("tokyo")) {
            return "東京電力";
        }
        if (key.contentEquals("chubu")) {
            return "中部電力";
        }
        if (key.contentEquals("kansai")) {
            return "関西電力";
        }
        if (key.contentEquals("hokuriku")) {
            return "北陸電力";
        }
        if (key.contentEquals("chugoku")) {
            return "中国電力";
        }
        if (key.contentEquals("shikoku")) {
            return "四国電力";
        }
        if (key.contentEquals("kyushu")) {
            return "九州電力";
        }
        throw new IllegalArgumentException();
    }

}
