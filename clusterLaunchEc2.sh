#!/bin/sh
echo java -cp dist/jpregel-aws.jar -Djava.security.policy=policy clients.ClusterLaunchEc2 $1
     java -cp dist/jpregel-aws.jar -Djava.security.policy=policy clients.ClusterLaunchEc2 $1