#!/bin/bash

#Parameters
#$1 -> screen resolution (f.e. 1920x1080x24)
#$2 -> url cluster slaves
SCREEN_RESOLUTION=$1
URL_CLUSTER=$2

echo "none /dev/shm tmpfs defaults,size=2g 0 0" >> /etc/fstab
mount -o remount /dev/shm
echo $SCREEN_RESOLUTION
echo $URL_CLUSTER
xvfb-run -s "-screen 0 ${SCREEN_RESOLUTION}" java -cp test80.jar com.mng.robotest.test80.access.rest.ServerRest -port 80 -secureport 443