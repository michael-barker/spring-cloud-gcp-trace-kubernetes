#!/usr/bin/env bash

./gradlew clean build jib
kubectl delete deploy spring-cloud-gcp-trace-kubernetes
kubectl create --save-config -f deployment.yml
