# Distater Recovery Playgroung

## Project Goal

Reproduce different architectural patterns for Confluent Cluster Disaster Recovery and their failover and failback procedures.

## Active-Active Platform over Confluent Cloud with Clients hosted on K8s Cluster with automatic restart on config changes

### Architectural Design

![Architectual Design](./assets/K8s%20Active%20-%20Active%20Disaster%20Recovery%20Strategy.svg)

### Scenario Topology:

1. 2 Confluent Cloud Clusters following the active-active DR pattern:

![Active-Active Patter](./assets/active-active%20pattern.png)
2. Kubernetes Cluster deployed (connectivity to CCloud cluster required)
3. [Reloader](https://github.com/stakater/Reloader) pod deployed on your. For standard deployment you can execute: 
```bash
kubectl apply -f https://raw.githubusercontent.com/stakater/Reloader/master/deployments/kubernetes/reloader.yaml
```
4. Java Clients (producer/consumer) images on a available registry (you can use the ones provided under k8's resources folder)
5. Clients will be deployed wit a [grepplabs/kafka-proxy](https://github.com/grepplabs/kafka-proxy) container as sidecar, that will act as a layer 7 kafka protocol aware proxy.
6. ConfigMaps/Secrets need to be annotated to make `reloader` aware of the config changes:
```yaml
  annotations:
    reloader.stakater.com/match: "true"
```
7. Client application `deployment` will be annotated with config maps that we want to actively listen changes:
```yaml
  annotations:
    configmap.reloader.stakater.com/reload: "java-cloud-producer-config,kafka-proxy-config"
```
8. When disaster happens we will change the `kafka-proxy-config-map.yaml` with the values of DC2 Cluster
9. `Reloader` will trigger a `rolling update` on any deployment annotated to listen changes of this CM.
10. As failback procedure we just need to restore the connection data on `kafka proxy config map`to the DC 1 values and after a `rolling update` clients will be again connecting to the original cluster. 