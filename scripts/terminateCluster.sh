#!/bin/sh
cd ..
echo java -cp build/classes -Djava.security.policy=policy clients.ClusterTerminate
     java -cp build/classes -Djava.security.policy=policy clients.ClusterTerminate