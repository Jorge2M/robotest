#When executing docker run for an image with Chrome or Firefox please either mount -v /dev/shm:/dev/shm or use the flag --shm-size=2g to use the host's shared memory.
#example create image: docker build -t jorge2m/robotest:latest .
#example container run: docker run -d -p 80:8080 -p 443:443 --privileged -v "%CD%/dockerresults:/robotest/output-library" jorge2m/robotest:latest
#   -> In PowerShell replace %CD% by ${pwd}
FROM jorge2m/chrome-firefox-jdk8-maven:latest

COPY target/robotest.zip robotest.zip
RUN unzip robotest.zip
	
COPY docker-entrypoint.sh /robotest/docker-entrypoint.sh

RUN apt-get update && apt-get install -y
RUN sed 's/TLSv1, TLSv1.1,/ /' etc/java-8-openjdk/security/java.security -i
	
WORKDIR /robotest

EXPOSE 8080
ENTRYPOINT ["bash","/robotest/docker-entrypoint.sh","1920x1080x24"]
#CMD echo "none /dev/shm tmpfs defaults,size=2g 0 0" >> /etc/fstab ; mount -o remount /dev/shm ; xvfb-run -s "-screen 0 1024x768x24" java -cp robotest.jar com.mng.robotest.access.rest.ServerRest -port 80 -secureport 443
