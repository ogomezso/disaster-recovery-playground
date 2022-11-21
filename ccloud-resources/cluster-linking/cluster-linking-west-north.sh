#!/bin/bash
source .env

cat <<EOF >> west-north.properties
cluster.link.prefix=west-
bootstrap.servers=$CCLOUD_WEST_URL
security.protocol=SASL_SSL
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username='$CCLOUD_WEST_APIKEY' password='$CCLOUD_WEST_PASSWD';
sasl.mechanism=PLAIN
client.dns.lookup=use_all_dns_ips
session.timeout.ms=45000
EOF

confluent kafka link create west-north-clink \
    --config-file west-north.properties \
    --environment "$ENV" \
    --cluster "$CCLOUD_NORTH_CLUSTERID"
