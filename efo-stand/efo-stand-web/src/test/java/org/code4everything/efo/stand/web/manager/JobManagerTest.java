package org.code4everything.efo.stand.web.manager;

import org.junit.Test;

public class JobManagerTest {

    @Test
    public void freeTask() {
        JobManager jobManager = new JobManager();
        jobManager.freeTask();
    }
}