#
# Stop minikube if running
#
if minikube status | grep -q "Running"
then
  #
  # Stop minikube and delete cluster
  #
  minikube stop
  minikube delete
fi