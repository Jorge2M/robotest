#!/bin/bash

#Parameters
#$1 -> screen resolution (f.e. 1920x1080x24)
#$2 -> url cluster slaves
SCREEN_RESOLUTION=$1

if [ -z "$URL_HUB" ]; then
    PARAM_URL_HUB=""
else 
    PARAM_URL_HUB="-urlhub $URL_HUB"
fi

if [ -z "$URL_INIT_SLAVE" ]; then
    PARAM_URL_INIT_SLAVE=""
else 
    PARAM_URL_INIT_SLAVE="-urlinislave $URL_INIT_SLAVE"
fi

if [ -z "$XMS_HUB" ]; then
    PARAM_XMS_HUB=""
else 
    PARAM_XMS_HUB="$XMS_HUB"
fi

if [ -z "$XMX_HUB" ]; then
    PARAM_XMX_HUB=""
else 
    PARAM_XMX_HUB="$XMX_HUB"
fi

echo "none /dev/shm tmpfs defaults,size=2g 0 0" >> /etc/fstab
mount -o remount /dev/shm

echo $SCREEN_RESOLUTION
echo $PARAM_URL_HUB
xvfb-run -s "-screen 0 ${SCREEN_RESOLUTION}" java ${PARAM_XMS_HUB} ${PARAM_XMX_HUB} -cp robotest.jar com.mng.robotest.access.rest.ServerRest -port 8080 -secureport 443 ${PARAM_URL_HUB} ${PARAM_URL_INIT_SLAVE}
#Xvfb :1 -screen 0 ${SCREEN_RESOLUTION} &
#export DISPLAY=:1
#java -cp robotest.jar com.mng.robotest.access.rest.ServerRest -port 8080 -secureport 443 ${PARAM_URL_HUB} ${PARAM_URL_INIT_SLAVE}
#java -cp robotest.jar com.mng.robotest.access.rest.ServerRest -port 8080 -secureport 443 ${PARAM_URL_HUB} ${PARAM_URL_INIT_SLAVE}