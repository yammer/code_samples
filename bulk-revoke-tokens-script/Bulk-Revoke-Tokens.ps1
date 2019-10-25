param (
  [Parameter(Mandatory=$true)][string]$CsvPath,
  [Parameter(Mandatory=$true)][string]$BearerToken,
  [Parameter(Mandatory=$true)][string]$Is3rdPartyAppOnly
)

function RequestDeletion {
  param (
    [string]$Json,
    [string]$BearerToken,
    [string]$Api
  )
  $Headers = @{Authorization=("Bearer {0}" -f $BearerToken)}
  try
  {
    $Response = Invoke-RestMethod -Uri $Api -Method POST -ContentType "application/json" -Body $Json -Headers $Headers
    @{StatusCode=$Response.BaseRequest.StatusCode; Content=$Response.Content} | Format-Table -AutoSize
  } catch {
    @{StatusCode=[int]$_.Exception.Response.StatusCode; Content=$_.ErrorDetails.Message} | Format-Table -AutoSize
  }
}

$BatchSize = 100
$UserIdColName = "user_id"

$CsvObj = Import-Csv -Path $CsvPath

$ThirdPartyAppOnly = false
if($Is3rdPartyAppOnly -eq $true){
  $ThirdPartyAppOnly = true
}

$JsonFormat = '{{"user_ids": [{0}], "is_3rd_party_only": $($ThirdPartyAppOnly)}}'

if ($CsvObj | Get-Member -Name $UserIdColName -MemberType NoteProperty)
{
  $UserIds = $CsvObj.$UserIdColName
  if ($UserIds.Count -gt 0)
  {
    $Api = "https://www.yammer.com/api/v1/tokens/deletion"
    $Batch = New-Object Collections.Generic.List[long]

    For ($i = 0; $i -lt $UserIds.Count; $i++)
    {
      $Batch.Add([long]($UserIds[$i]))

      if ($Batch.Count -ge $BatchSize)
      {
        $Json = (JsonFormat -f ($Batch -join ","))
        RequestDeletion -Json $Json -BearerToken $BearerToken -Api $Api
        $Batch.Clear()
      }
    }

    if ($Batch.Count -gt 0)
    {
      $Json = (JsonFormat -f ($Batch -join ","))
      RequestDeletion -Json $Json -BearerToken $BearerToken -Api $Api
      $Batch.Clear()
    }
  }
}
else 
{
  Write-Error ("Cannot find column {0} in file {1}" -f $UserIdColName, $CsvPath)
  exit 2
}
