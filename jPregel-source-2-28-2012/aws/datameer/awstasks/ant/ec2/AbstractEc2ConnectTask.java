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

import org.apache.tools.ant.BuildException;

import com.xerox.amazonws.ec2.Jec2;

import aws.datameer.awstasks.aws.ec2.InstanceGroup;
import aws.datameer.awstasks.aws.ec2.InstanceGroupImpl;

public abstract class AbstractEc2ConnectTask extends AbstractEc2Task {

    
    public final void execute() throws BuildException {
        Jec2 ec2 = new Jec2(_accessKey, _accessSecret);
        InstanceGroup instanceGroup = new InstanceGroupImpl(ec2);
        try {
            instanceGroup.connectTo(_groupName);
            execute(ec2, instanceGroup);
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    protected abstract void execute(Jec2 ec2, InstanceGroup instanceGroup) throws Exception;
}
