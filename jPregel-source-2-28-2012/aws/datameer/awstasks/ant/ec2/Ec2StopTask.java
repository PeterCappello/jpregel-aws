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
package aws.datameer.awstasks.ant.ec2;

import com.xerox.amazonws.ec2.Jec2;

import aws.datameer.awstasks.aws.ec2.InstanceGroup;

public class Ec2StopTask extends AbstractEc2ConnectTask {

    @Override
    protected void execute(Jec2 ec2, InstanceGroup instanceGroup) throws Exception {
        System.out.println("executing " + getClass().getSimpleName() + " with groupName '" + _groupName + "'");
        instanceGroup.shutdown();
    }
}
