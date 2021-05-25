#!/usr/bin/env bash
curl -vs -H "Content-Type: application/json" -H "Authorization: Bearer ...whatever" -X POST -d "{
    \"id\": \"$1\",
    \"textData\": \"$2\"
}"  http://localhost:8080/api/testdata
