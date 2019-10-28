# bulk-revoke-yammer-token
This is a powershell script Sample which will invoke Yammer public API to bulk revoke Yammer legacy token.

## Usage
```
Bulk-Revoke-Tokens.ps1 [-CsvPath] <string> [-BearerToken] <string> [-Is3rdPartyAppOnly] <string> [<CommonParameters>]
```
This script takes two parameters to run
* CsvPath: The path of the CSV file which contains "user_id" column
* BearerToken: AAD token string which will be used to do authentication
* Is3rdPartyAppOnly: true will only delete tokens for third party applications, false will delate all the tokens

## Yammer API
POST: https://www.yammer.com/api/v1/tokens/deletion 

This API will delete the yammer bearer tokens for the users which provided in the post data "user_ids". Depending on user's choise, the API will delete all tokens if "false" provided as the value for "Is3rdPartyAppOnly", if user type "true" for "Is3rdPartyAppOnly", only third party application tokens will be deleted.

A curl command example to call this API:
```
curl -v -d '{"user_ids":[1,2,3], "is_3rd_party_only": true}' -H "Content-Type: application/json" -H "Authorization: Bearer <AAD_Token>" -X POST https://www.yammer.com/api/v1/tokens/deletion
```

## Work Flow
1. Get all the user ids from the csv file
2. Invoke the post API to revoke tokens for at most 100 users each time
