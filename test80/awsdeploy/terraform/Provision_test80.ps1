#Prerrequisites of the instance:
# 1) Installed PowerShell 3
# 2) Installed Tools AWS for Windows (https://aws.amazon.com/es/powershell/)
# 3) Installed Tomcat8 as a Service
# 4) User tomcat/tomcat with a configuration:
#    <role rolename="admin-gui"/>
#    <role rolename="manager-gui"/>
#    <role rolename="manager-script"/>
#    <user username="tomcat" password="tomcat" roles="admin-gui,manager-gui,manager-script"/>
#
#Declare the input parameters that this script will accept
#$zipFleName is the fully qualified name of the zip file to decompress eg C:\folder1\file.zip
#$UnzipLocation is the full path to the location to decompress the file to eg c:\unzip
#Import-Module -Name AWSPowerShell
#param (
#  [string]$zipFileName, 
#  [string]$UnzipLocation 
#)

#Declare the input parameters that this script will accept
param (
  # param branch_robot: p.e. "branches/RELEASE_1" o "trunk"
  [string]$branch_robot
)

$currentScriptDirectory = Get-Location
$brachWithoutSlash = $branch_robot -replace '/','_'
$pathTest80S3file = 'code/' + 'test80-' + $brachWithoutSlash + '.zip'
$zipTest80LocalFile = 'test80.zip'
$bucketS3Name = 'mng-mangotest-releases'
$accessKeyS3 = 'AKIAJWAHWJ3R2TZYGR3Q'
$secreKeyS3 = 'qp1slaBJw2KnRI5NEXKBVwtcC26CPJxgii8tbuu6'

Add-Type -AssemblyName System.IO.Compression.FileSystem
function Unzip {
    param([string]$zipfile, [string]$outpath)
    [System.IO.Compression.ZipFile]::ExtractToDirectory($zipfile, $outpath)
}

function Zip {
	param([string]$sourceFolder, [string]$zipDestination)
	[System.IO.Compression.ZipFile]::CreateFromDirectory($sourceFolder, $zipDestination)
}

function WaitAndGet-FileFromS3 {
	param ([string] $pathOfFileInS3, [string] $nameOfFileDownloaded, [string] $bucketS3Name, [string] $accessKeyS3, [string] $secreKeyS3, [int] $loops)
	Write-Host "Waiting for existence of file " + $pathOfFileInS3 + " in S3"
	$i=0
	Do {
		$i++	
		try {
			Read-S3Object -BucketName $bucketS3Name -Key $pathOfFileInS3 -File $nameOfFileDownloaded -AccessKey $accessKeyS3 -SecretKey $secreKeyS3 -Region eu-west-1	
			$exception = 0;
			Write-Host "File read OK from S3";
		}
		catch {
			Write-Output "Not available file " + $pathOfFileInS3 + "in S3 yet..."
			Start-Sleep -s 5
			$exception = 1;
		}
	} Until( ($exception -eq 0) -or ($i -gt $loops) )
	
	if ($exception -eq 1) {
		throw "Problem waiting for file " + $pathOfFileInS3 + " in S3"
	}
}

# Ejecutamos la tarea Jenkins que genera el pathTest80S3file (de tipo test80-RELEASE1.zip)
If (Test-Path $zipTest80LocalFile) {
	Remove-Item -Force $zipTest80LocalFile
}

$JenkinsURL = "https://citools.mangodev.net/jenkins"
$JobName = "CI.ROBOTEST.mango.testing"
$JobToken = "robotest"
$FullURL = "$JenkinsURL/view/Testing/job/$JobName/build?token=$JobToken"

$Auth = "jenkins.admin:mango321"
$Bytes = [System.Text.Encoding]::UTF8.GetBytes($Auth)
$Base64bytes = [System.Convert]::ToBase64String($Bytes)
$Headers = @{ "Authorization" = "Basic $Base64bytes"}

$JenkinsParams = @{
"parameter" = @(
@{
name = "BRANCH"
tag = $branch_robot
},
@{
name = "PROJECT"
value = "test80"
},
@{
name = "SKIP_TEST"
value = "true"
}
)
}
$ParamsJSON = ConvertTo-Json -InputObject $JenkinsParams
Invoke-WebRequest -UseBasicParsing $FullURL -Method POST -Headers $Headers -Body @{ json = $ParamsJSON }

# Get del pathTest80S3file (de tipo test80-RELEASE1.zip) from S3 (Wait for X loops) and save with name test80.zip
WaitAndGet-FileFromS3 $pathTest80S3file $zipTest80LocalFile $bucketS3Name $accessKeyS3 $secreKeyS3 15

# Unzip the file test80.zip downloaded from S3
$currentScriptDirectory = Get-Location
$pathZip = $currentScriptDirectory.tostring() + '\' + $zipTest80LocalFile
Unzip $pathZip $currentScriptDirectory

# Exec test80 with Params
Set-Location -Path test80*
java -cp test80.jar com.mng.robotest.test80 -suite SmokeTest -browser chrome -channel desktop -application shop -version V1 -url https://shop.mango.com/preHome.faces -typeAccess Online -tcases MIC001

# Zip Results 
$sourceFolder = $currentScriptDirectory.tostring() + '\' + 'test80-RELEASE-1\output-library'
$zipDestination = $currentScriptDirectory.tostring() + "\reportTest.zip"
If (Test-Path $zipDestination){
	Remove-Item -Force $zipDestination
}
Zip $sourceFolder $zipDestination

# Send results to S3
Set-Location -Path $currentScriptDirectory.tostring()
Write-S3Object -BucketName $bucketS3Name -Key "code/reportTest.zip" -File .\reportTest.zip -AccessKey $accessKeyS3 -SecretKey $secreKeyS3 -Region eu-west-1

