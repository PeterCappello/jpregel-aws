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
package aws.datameer.awstasks.ant.s3.model;

import org.apache.tools.ant.Project;

import com.amazonaws.services.s3.AmazonS3;

public class DeleteBucketCommand extends S3Command {

    private String _name;

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    @Override
    public void execute(Project project, AmazonS3 s3Service) {
        s3Service.deleteBucket(_name);
    }

}
