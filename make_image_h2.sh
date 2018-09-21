#!/usr/bin/env bash

ver="$(cat build.gradle | grep "version = " | tr -d "'")" && ver="$(echo ${ver:10})"
imageName=h2
imageRepository=jnesspro/camunda-bpm-sso
imageFullName=${imageRepository}:${imageName}-${ver}

echo 'build docker image...'
docker build -t ${imageName} .

echo 'upload docker image...'
docker tag ${imageName}:latest ${imageFullName}
docker push ${imageFullName}
