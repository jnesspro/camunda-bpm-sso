#!/usr/bin/env bash

./make_libs.sh

ver="$(cat build.gradle | grep "version = " | tr -d "'")" && ver="$(echo ${ver:10})"
imageName=postgres
imageRepository=jnesspro/camunda-bpm-sso
imageFullName=${imageRepository}:${imageName}-${ver}

echo 'build docker image...'
docker build -t ${imageName} .

echo 'upload docker image: ${imageFullName} ...'
docker tag ${imageName}:latest ${imageFullName}
docker push ${imageFullName}
