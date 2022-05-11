#!/bin/bash

TAG=$APP_VERSION
IMAGE="robotest-mango"
PRO_ACCOUNT_ID="495248209902"
REPOSITORY_NAME="kamikazes"
REPOSITORY="$PRO_ACCOUNT_ID.dkr.ecr.eu-west-1.amazonaws.com/mango/$REPOSITORY_NAME/$IMAGE"

docker build --build-arg APP_VERSION=$TAG --tag $REPOSITORY_NAME/$IMAGE:latest --tag $REPOSITORY_NAME/$IMAGE:$TAG .
docker tag "$REPOSITORY_NAME/$IMAGE:latest" "$REPOSITORY:latest"
docker tag "$REPOSITORY_NAME/$IMAGE:latest" "$REPOSITORY:$TAG"

echo $(aws --version)
eval $(aws --region eu-west-1 ecr get-login --no-include-email --registry-ids ${PRO_ACCOUNT_ID})
docker push -a "$REPOSITORY"