#!/bin/sh
cd ..
echo java -cp build/classes -Djava.security.policy=policy clients.ClusterLaunchEc2 $1
     java -cp build/classes -Djava.security.policy=policy clients.ClusterLaunchEc2 $1