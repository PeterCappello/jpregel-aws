#!/bin/sh
echo java -cp dist/jpregel-aws.jar -Djava.security.policy=policy clients.ClusterTerminate
     java -cp dist/jpregel-aws.jar -Djava.security.policy=policy clients.ClusterTerminate