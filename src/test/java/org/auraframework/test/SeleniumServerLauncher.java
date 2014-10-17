/*
 * Copyright (C) 2014 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.test;

import java.net.Socket;

import org.apache.log4j.Logger;
import org.openqa.grid.selenium.GridLauncher;

/**
 * Get WebDriver instances for Aura tests.
 */
public class SeleniumServerLauncher {
    private static String browsers = "-browser browserName=chrome,maxInstances=4 -browser browserName=firefox,maxInstances=4";

    public static void main(String args[]) throws Exception {
        final String host = "localhost";
        final int serverPort = Integer.parseInt(args[0]);
        start(host, serverPort);
    }

    public static void start(String host, int serverPort) throws Exception {
        Logger logger = Logger.getLogger(SeleniumServerLauncher.class.getName());

        logger.info("Launching Selenium server on port " + serverPort);
        GridLauncher.main(String.format("-port %s %s", serverPort, browsers).split(" "));
        logger.info("Waiting for server to open port");
        waitForServer(host, serverPort);
    }

    // just check if port is listening
    private static void waitForServer(String host, int port) {
        boolean isUp = false;
        for (int tries = 0; !isUp && tries < 10; tries++) {
            try {
                new Socket(host, port);
                isUp = true;
            } catch (Exception e) {}
            ;
        }
        if (!isUp) { throw new Error(String.format("Failed to open socket to port %d", port)); }
    }
}
