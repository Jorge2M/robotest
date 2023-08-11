#!/bin/bash

echo "none /dev/shm tmpfs defaults,size=2g 0 0" >> /etc/fstab
mount -o remount /dev/shm

x11vnc -forever -usepw -create
