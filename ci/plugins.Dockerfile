ARG JDK_VERSION=17
FROM maven:3-amazoncorretto-${JDK_VERSION}-debian 

ARG USER_ID=1000
COPY ci/user.sh .
RUN ./user.sh $USER_ID

# For building js and python plugins
RUN apt-get update && apt-get install -y npm python3-docopt poppler-utils curl wget unzip
