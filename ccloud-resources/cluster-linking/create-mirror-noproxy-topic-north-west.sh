#!/bin/bash
source ../.env

confluent kafka mirror create north-$NP_TOPIC --link north-west-clink --environment $ENV --source-topic $NP_TOPIC --cluster $CCLOUD_WEST_CLUSTERID
