#
# Start minikube in insecure mode
#
minikube start --insecure-registry=localhost
#
# This is an add-on to run the registry into the cluster
#
minikube addons enable registry
#
# Configure the hostname for minikube
#
grep -q "$(minikube ip)" /etc/hosts || echo  -e "$(minikube ip)\tlocalhost" | sudo tee -a /etc/hosts
