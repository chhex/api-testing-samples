#!/usr/bin/env bash
curl -vs -H "Content-Type: application/json" -H "Authorization: Bearer ...whatever" -X POST -d "{
    \"textData\": \"$1\"
}"  http://localhost:8080/api/testdata
