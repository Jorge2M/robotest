#When executing docker run for an image with Chrome or Firefox please either mount -v /dev/shm:/dev/shm or use the flag --shm-size=2g to use the host's shared memory.
#example create image: docker build -t jorge2m/robotest:latest .
#example container run: docker run -d -p 80:8080 -p 443:443 --privileged -v "%CD%/dockerresults:/robotest/output-library" jorge2m/robotest:latest
#   -> In PowerShell replace %CD% by ${pwd}
FROM jorge2m/chrome-firefox-openjdk17-maven:1695752079

COPY target/robotest.zip robotest.zip
RUN unzip robotest.zip

#WORKAROUND UNTIL SYSOPS WILL FIX COMMUNICATION PROBLEM IN K8S-TOOLS
#COPY chromedriver-linux64.zip chromedriver.zip
#RUN unzip chromedriver.zip && \
#	mkdir -p /root/.cache/selenium/chromedriver/linux64/117.0.5938.149/ && \
#	mv chromedriver/chromedriver /root/.cache/selenium/chromedriver/linux64/117.0.5938.149/
	
COPY docker-entrypoint.sh /robotest/docker-entrypoint.sh

RUN apt-get update && apt-get install -y
#RUN sed 's/TLSv1, TLSv1.1,/ /' etc/java-8-openjdk/security/java.security -i
	
WORKDIR /robotest

EXPOSE 8080
ENTRYPOINT ["bash","/robotest/docker-entrypoint.sh","1920x1080x24"]