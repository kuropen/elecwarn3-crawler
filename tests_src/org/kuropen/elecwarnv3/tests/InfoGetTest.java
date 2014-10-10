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

package org.kuropen.elecwarnv3.tests;

import co.akabe.common.electricusage.ElectricUsageCSVParser;
import co.akabe.common.electricusage.FiveMinDemand;
import co.akabe.common.electricusage.PeakSupply;
import co.akabe.common.electricusage.SupplyDataFormat;
import junit.framework.TestCase;
import org.junit.Test;
import org.kuropen.elecwarnv3.AfterInfoGetTask;
import org.kuropen.elecwarnv3.GetInfoAction;
import org.kuropen.elecwarnv3.GetInfoListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Testing information fetch
 */
public class InfoGetTest extends TestCase {

    private void testForTargetCompany (SupplyDataFormat format, final String targetCompany) throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        GetInfoAction action = new GetInfoAction(format, targetCompany, new GetInfoListener() {
            @Override
            public void onInfoGet(String key, FiveMinDemand demand, PeakSupply supply) {
                assertEquals(targetCompany, key);
                assertTrue(demand.getUsePercentage(supply) > 0);
                signal.countDown();
            }
        });
        action.doAction();
        signal.await();
    }

    @Test
    public void testInfoGetHokkaido () throws Exception {
        testForTargetCompany(ElectricUsageCSVParser.Format_Hokkaido, "hokkaido");
    }

    @Test
    public void testInfoGetTohoku () throws Exception {
        testForTargetCompany(ElectricUsageCSVParser.Format_Tohoku, "tohoku");
    }

    @Test
    public void testInfoGetTokyo () throws Exception {
        testForTargetCompany(ElectricUsageCSVParser.Format_Tokyo, "tokyo");
    }

    @Test
    public void testInfoGetChubu () throws Exception {
        testForTargetCompany(ElectricUsageCSVParser.Format_Chubu, "chubu");
    }

    @Test
    public void testInfoGetKansai () throws Exception {
        testForTargetCompany(ElectricUsageCSVParser.Format_Kansai, "kansai");
    }

    @Test
    public void testInfoGetKyushu () throws Exception {
        testForTargetCompany(ElectricUsageCSVParser.buildKyushuFormat(), "kyushu");
    }

}
