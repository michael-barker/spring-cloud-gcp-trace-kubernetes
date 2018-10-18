#!/usr/bin/env bash

./gradlew clean build jib
kubectl delete svc spring-cloud-gcp-trace-kubernetes-svc
kubectl delete deploy spring-cloud-gcp-trace-kubernetes
kubectl create --save-config -f deployment.yml
kubectl create --save-config -f service.yml
kubectl get svc -w
