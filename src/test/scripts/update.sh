#!/usr/bin/env bash
curl -H "Content-Type: application/json" -X POST -d "{
    \"id\": \"$1\",
    \"text\": \"$2\"
}"  http://localhost:8080/api/testdata
