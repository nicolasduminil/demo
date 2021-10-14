#
# Start minikube in insecure mode
#
minikube start --network-plugin=cni --enable-default-cni --bootstrapper=kubeadm --insecure-registry=172.19.0.2
#
# Deploy the k8s services
#
kubectl apply -f ./src/main/resources/k8s/demo-postgres-service.yaml,./src/main/resources/k8s/demo-postgres-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-eureka-service.yaml,./src/main/resources/k8s/demo-eureka-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-gateway-service.yaml,./src/main/resources/k8s/demo-gateway-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-activemq-service.yaml,./src/main/resources/k8s/demo-activemq-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-api-service.yaml,./src/main/resources/k8s/demo-api-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-job-service.yaml,./src/main/resources/k8s/demo-job-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-xa-service.yaml,./src/main/resources/k8s/demo-xa-deployment.yaml
kubectl apply -f ./src/main/resources/k8s/demo-adminer-service.yaml,./src/main/resources/k8s/demo-adminer-deployment.yaml
