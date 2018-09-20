#!/usr/bin/env bash

./gradlew :camunda-webapp-webjar:build
cp camunda-webapp-webjar/build/libs/camunda-webapp-webjar-7.9.0.jar lib/