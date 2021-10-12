kubectl delete -f ./src/main/resources/k8s/demo-postgres-service.yaml,./src/main/resources/k8s/demo-postgres-deployment.yaml
kubectl delete -f ./src/main/resources/k8s/demo-eureka-service.yaml,./src/main/resources/k8s/demo-eureka-deployment.yaml
kubectl delete -f ./src/main/resources/k8s/demo-gateway-service.yaml,./src/main/resources/k8s/demo-gateway-deployment.yaml
kubectl delete -f ./src/main/resources/k8s/demo-activemq-service.yaml,./src/main/resources/k8s/demo-activemq-deployment.yaml
kubectl delete -f ./src/main/resources/k8s/demo-api-service.yaml,./src/main/resources/k8s/demo-api-deployment.yaml
kubectl delete -f ./src/main/resources/k8s/demo-job-service.yaml,./src/main/resources/k8s/demo-job-deployment.yaml
kubectl delete -f ./src/main/resources/k8s/demo-adminer-service.yaml,./src/main/resources/k8s/demo-adminer-deployment.yaml