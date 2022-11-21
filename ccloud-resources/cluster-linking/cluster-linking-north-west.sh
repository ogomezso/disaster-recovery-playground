#!/bin/bash
source .env

cat <<EOF >> north-west.properties
cluster.link.prefix=north-
bootstrap.servers=$CCLOUD_NORTH_URL
security.protocol=SASL_SSL
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username='$CCLOUD_NORTH_APIKEY' password='$CCLOUD_NORTH_PASSWD';
sasl.mechanism=PLAIN
client.dns.lookup=use_all_dns_ips
session.timeout.ms=45000
EOF

confluent kafka link create north-west-clink \
    --config-file north-west.properties \
    --environment "$ENV" \
    --cluster "$CCLOUD_WEST_CLUSTERID"