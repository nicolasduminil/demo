#
# Start minikube in insecure mode
#
#minikube start --insecure-registry=172.0.0.0/24
#
# This is an add-on to run the registry into the cluster
#
#minikube addons enable registry
#
# Configure the hostname for minikube
#
#grep -q "$(minikube ip)" /etc/hosts || echo  -e "$(minikube ip)\tlocalhost" | sudo tee -a /etc/hosts
#
# Deploy the k8s services
#
kubectl apply -f ./src/main/resources/k8s/demo-postgres-configmap.yaml,./src/main/resources/k8s/demo-postgres-deployment.yaml,./src/main/resources/k8s/demo-postgres-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-eureka-deployment.yaml,./src/main/resources/k8s/demo-eureka-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-gateway-deployment.yaml,./src/main/resources/k8s/demo-gateway-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-activemq-deployment.yaml,./src/main/resources/k8s/demo-activemq-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-api-deployment.yaml,./src/main/resources/k8s/demo-api-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-job-deployment.yaml,./src/main/resources/k8s/demo-job-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-xa-deployment.yaml,./src/main/resources/k8s/demo-xa-service.yaml
kubectl apply -f ./src/main/resources/k8s/demo-adminer-deployment.yaml,./src/main/resources/k8s/demo-adminer-service.yaml
