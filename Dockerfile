#When executing docker run for an image with Chrome or Firefox please either mount -v /dev/shm:/dev/shm or use the flag --shm-size=2g to use the host's shared memory.
#example create image: docker build -t jorge2m/robotest:latest .
#example container run: docker run -d -p 80:8080 -p 443:443 --privileged -v "%CD%/dockerresults:/robotest/output-library" jorge2m/robotest:latest
#   -> In PowerShell replace %CD% by ${pwd}
# docker run -d -p 80:8080 -p 443:443 --privileged -m=4g -v "${PWD}\dockerresults:/robotest/output-library" jorge2m/robotest:latest

FROM jorge2m/chrome-firefox-openjdk17-maven:1717773795

COPY target/robotest.zip robotest.zip
RUN unzip robotest.zip

COPY docker-entrypoint.sh /robotest/docker-entrypoint.sh

#DEVELOP ONLY - INIT
#RUN apt-get update && \
#    DEBIAN_FRONTEND=noninteractive apt-get install -y \
#    xauth \
#    x11-apps
    
#ENV DISPLAY=:0
#DEVELOP ONLY - FIN

# Set time zone
ENV TZ=Europe/Madrid
RUN apt-get update \
    && apt-get install -y tzdata fonts-noto-cjk fonts-noto-cjk-extra \
    && rm -rf /var/lib/apt/lists/* \
    && ln -snf /usr/share/zoneinfo/Europe/Madrid /etc/localtime \
    && echo Europe/Madrid > /etc/timezone	
	
WORKDIR /robotest

EXPOSE 8080
ENTRYPOINT ["bash","/robotest/docker-entrypoint.sh","1920x1080x24"]