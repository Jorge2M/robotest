#!/bin/bash

echo "none /dev/shm tmpfs defaults,size=2g 0 0" >> /etc/fstab
mount -o remount /dev/shm

echo $SCREEN_RESOLUTION
echo $PARAM_URL_HUB
##x11vnc -forever -usepw -create &
##java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -cp robotest.jar com.mng.robotest.access.rest.ServerRest -port 8080
x11vnc -forever -usepw -create
