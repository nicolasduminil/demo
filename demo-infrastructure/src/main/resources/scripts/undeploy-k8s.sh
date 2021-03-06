#
# Undeploy the k8s services and cluster if exists
#
if minikube status | grep -q "Running"
then
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-postgres-configmap.yaml,./src/main/resources/k8s/demo-postgres-deployment.yaml,./src/main/resources/k8s/demo-postgres-service.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-eureka-service.yaml,./src/main/resources/k8s/demo-eureka-deployment.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-gateway-service.yaml,./src/main/resources/k8s/demo-gateway-deployment.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-activemq-service.yaml,./src/main/resources/k8s/demo-activemq-deployment.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-api-service.yaml,./src/main/resources/k8s/demo-api-deployment.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-job-service.yaml,./src/main/resources/k8s/demo-job-deployment.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-xa-service.yaml,./src/main/resources/k8s/demo-xa-deployment.yaml
  kubectl delete --ignore-not-found=true -f ./src/main/resources/k8s/demo-adminer-service.yaml,./src/main/resources/k8s/demo-adminer-deployment.yaml
  #
  # Stop minikube and delete cluster
  #
  #minikube stop
  #minikube delete
fi