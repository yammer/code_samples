# bulk-revoke-yammer-token
This is a powershell script Sample which will invoke Yammer public API to bulk revoke Yammer legacy token.

## Usage
```
bulk-revoke-yammer-token.ps1 [-CsvPath] <string> [-BearerToken] <string> [<CommonParameters>]
```
This script takes two parameters to run
* CsvPath: The path of the CSV file which contains "user_id" column
* BearerToken: AAD token string which will be used to do authentication

## Yammer API
POST: https://www.yammer.com/api/v1/tokens/deletion 

This API will delete all the yammer bearer tokens for the users which provided in the post data "user_ids".

A curl command example to call this API:
```
curl -v -d '{"user_ids":[1,2,3], "is_3rd_party_only": false}' -H "Content-Type: application/json" -H "Authorization: Bearer <AAD_Token>" -X POST https://www.yammer.com/api/v1/tokens/deletion
```

## Workflow
1. Get all the user ids from the csv file
2. Invoke the post API to revoke tokens for at most 100 users each time


