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
 * 電力会社の情報を有していることを表すインターフェイス
 *
 * @author Kuropen
 */
public interface EPCOInfo {

    /**
     * 会社検索キー
     *
     * @return
     */
    public String getCompanyKey();

    /**
     * 会社名
     *
     * @return
     */
    public String getCompanyName();

}
