# Disaster Recovery Playgroung

## Project Goal

Reproduce different architectural patterns for Confluent Cluster Disaster Recovery and their failover and failback procedures.

## Active-Active Platform over Confluent Cloud with Clients hosted on K8s Cluster with automatic restart on config changes

### Architectural Design

![Architectual Design](./assets/K8s%20Active%20-%20Active%20Disaster%20Recovery%20Strategy.svg)

### Scenario Topology

1. 2 Confluent Cloud Clusters following the active-active DR pattern:

   ![Active-Active Patter](./assets/active-active%20pattern.svg)

2. Kubernetes Cluster deployed (connectivity to CCloud cluster required)

3. [Reloader](https://github.com/stakater/Reloader) pod deployed on your. For standard deployment you can execute:

   ```bash
   kubectl apply -f https://raw.githubusercontent.com/stakater/Reloader/master/deployments/kubernetes/reloader.yaml
   ```

4. Create topic "chuck-java-topic" on both DC if auto.create.topics.enable is false

5. Java Clients (producer/consumer) images on a available registry (you can use the ones provided under k8's resources folder)

   * Clients will be deployed with a [grepplabs/kafka-proxy](https://github.com/grepplabs/kafka-proxy) container as sidecar, that will act as a layer 7 kafka protocol aware proxy.

   * ConfigMaps/Secrets need to be annotated to make `reloader` aware of the config changes:

      ```yaml
        annotations:
          reloader.stakater.com/match: "true"
      ```

   * Client application `deployment` will be annotated with config maps that we want to actively listen changes:

      ```yaml
        annotations:
          configmap.reloader.stakater.com/reload: "java-cloud-producer-config,kafka-proxy-config"
      ```

    1. Create 2 configmap using the file K8s-resources/kafka-proxy-configmap.yamlas a template. For each configmap, add the details of DC Cluster and API Key/Secret. This will be the config map the produce refers when it start:

       ```bash
       kubectl apply -f K8s-resources/kafka-proxy-configmap.yaml
       ```

    2. Two cases: Producer using a Proxy or Producer without a Proxy
       * Producer using a Proxy
          1. Configure how the producer will call the proxy (as a sidecar):

             ```bash
             kubectl apply -f K8s-resources/java-cloud-producer-configmap.yaml
             ```

          2. Run the producer:

             ```bash
             kubectl apply -f K8s-resources/java-cloud-producer.yaml
             ```

          3. Send Messages using the producer:

             Get the EXTERNAL-IP where the producer expese its API:

             ```bash
             kubectl get svc -n clients
             ```

             and send the message using this command:

             ```bash
             curl -X POST http://<EXTERNAL-IP>:8080/chuck-says
             ```

       * Producer without a Proxy (TO BE DEFINED)

6. When disaster happens we will change the `kafka-proxy-config-map.yaml` with the values of DC2 Cluster

7. `Reloader` will trigger a `rolling update` on any deployment annotated to listen changes of this CM.

8. As failback procedure we just need to restore the connection data on `kafka proxy config map`to the DC 1 values and after a `rolling update` clients will be again connecting to the original cluster.
