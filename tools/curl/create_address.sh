#!/usr/bin/env bash
curl -vs -H "Content-Type: application/json" -H "Authorization: Bearer ...whatever" -X POST -d "{
   \"streetName\":\"$1\",
   \"streetNumber\":\"$2\",
   \"postalCode\":\"$3\"}
}"  http://localhost:8080/api/pm/address
