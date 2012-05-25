/**
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aws.datameer.awstasks.ant.emr.model;

import aws.datameer.awstasks.ant.emr.EmrTask;
import aws.datameer.awstasks.aws.emr.EmrCluster;
import aws.datameer.awstasks.aws.emr.EmrCluster.ClusterState;

public class EmrStopCommand implements EmrCommand {

    @Override
    public void execute(EmrCluster cluster) throws Exception {
        if (cluster.getState() == ClusterState.UNCONNECTED) {
            cluster.connectByName();
        }
        cluster.shutdown();
    }

    public static void main(String[] args) {
        EmrTask emrTask = new EmrTask();
        emrTask.setAccessKey("AKIAIUZ4L3YT43JZI6QA");
        emrTask.setAccessSecret("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");
        emrTask.setClusterName("dm-bamboo.emr.test.cluster");
        emrTask.addStopCluster(new EmrStopCommand());
        emrTask.execute();
        // emrTask.setS3Bucket(")
    }

}
