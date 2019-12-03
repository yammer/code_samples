# Yammer OAuth Token Destroy PowerShell Script
This [PowerShell script](https://github.com/yammer/code_samples/blob/master/bulk-revoke-tokens-script/Bulk-Revoke-Tokens.ps1) will destroy Yammer OAuth tokens. Recommended to use when there are more than 150 users whose Yammer OAuth tokens need to be destroyed. For operations with less than 150 users, please use the Yammer OAuth token Destory API https://aka.ms/Destroy

## Usage
```
Bulk-Revoke-Tokens.ps1 [-CsvPath] <string> [-BearerToken] <string> [-Is3rdPartyAppOnly] <string> [<CommonParameters>]
```
This script takes three parameters to run
* CsvPath: The path of the CSV file which contains "id" column
* BearerToken: Azure Active Directory (AAD) token string which will be used to do authentication
* Is3rdPartyAppOnly: *true* will only delete tokens for third party applications, *false* will delete all the tokens

The script invokes Yammer's Destroy API. For further details, please see https://aka.ms/Destroy
