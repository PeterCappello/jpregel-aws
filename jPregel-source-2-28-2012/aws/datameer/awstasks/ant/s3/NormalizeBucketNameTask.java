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
package aws.datameer.awstasks.ant.s3;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import aws.datameer.awstasks.util.S3Util;

public class NormalizeBucketNameTask extends Task {

    private String _name;
    private String _targetProperty;

    public NormalizeBucketNameTask() {
        // default constructor - needed by ant
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setTargetProperty(String targetProperty) {
        _targetProperty = targetProperty;
    }

    public String getTargetProperty() {
        return _targetProperty;
    }

    @Override
    public void execute() throws BuildException {
        String normalizedName = S3Util.normalizeBucketName(getName());
        System.out.println("normalized '" + getName() + "' to '" + normalizedName + "'");
        getProject().setProperty(getTargetProperty(), normalizedName);
    }

}
