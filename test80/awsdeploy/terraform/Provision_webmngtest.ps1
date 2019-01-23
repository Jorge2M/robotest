#Prerrequisites of the instance:
# 1) Installed PowerShell 5
# 2) Installed Tools AWS for Windows (https://aws.amazon.com/es/powershell/)
# 3) Installed Tomcat9 as a Service
# 4) User tomcat/tomcat with a configuration:
#    <role rolename="admin-gui"/>
#    <role rolename="manager-gui"/>
#    <role rolename="manager-script"/>
#    <user username="tomcat" password="tomcat" roles="admin-gui,manager-gui,manager-script"/>
#
#Declare the input parameters that this script will accept
param (
  # param branch_robot: p.e. "origin/develop" o "origin/master"
  [string]$branch_robot
)

$currentScriptDirectory = Get-Location
$brachWithoutSlash = $branch_robot -replace '/','_'
$pathTest80S3file = 'code/' + 'test80-' + $brachWithoutSlash + '.zip'
$pathWebmngtestZipS3file = 'code/' + 'webmngtest-' + $brachWithoutSlash + '.zip'
$bucketS3Name = 'mng-mangotest-releases'
$accessKeyS3 = 'AKIAJWAHWJ3R2TZYGR3Q'
$secreKeyS3 = 'qp1slaBJw2KnRI5NEXKBVwtcC26CPJxgii8tbuu6'
$zipTest80LocalFile = $currentScriptDirectory.tostring() + '\test80.zip'
$zipWebmngtestLocalFile = $currentScriptDirectory.tostring() + '\webmngtest.zip'
$warWebmngtestLocalName = $currentScriptDirectory.tostring() + '\webmngtest.war'

Add-Type -AssemblyName System.IO.Compression.FileSystem
function Unzip {
    param([string]$zipfile, [string]$outpath)
    [System.IO.Compression.ZipFile]::ExtractToDirectory($zipfile, $outpath)
}

function Get-EnvironmentVariable([string] $Name, [System.EnvironmentVariableTarget] $Scope) {
    [Environment]::GetEnvironmentVariable($Name, $Scope)
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

# Open ports
New-NetFirewallRule -DisplayName "HTTP inbound80" -Enabled:True -Profile Public -Direction Inbound -Action Allow -Protocol TCP -LocalPort 80
New-NetFirewallRule -DisplayName "HTTP inbound8080" -Enabled:True -Profile Public -Direction Inbound -Action Allow -Protocol TCP -LocalPort 8080

# Borramos los zips del S3 si existieran
Import-Module "C:\Program Files (x86)\AWS Tools\PowerShell\AWSPowerShell\AWSPowerShell.psd1"
Remove-S3Object -BucketName $bucketS3Name -Key $pathTest80S3file -AccessKey $accessKeyS3 -SecretKey $secreKeyS3 -Region eu-west-1 -force
Remove-S3Object -BucketName $bucketS3Name -Key $pathWebmngtestZipS3file -AccessKey $accessKeyS3 -SecretKey $secreKeyS3 -Region eu-west-1 -force

# Ejecutamos la tarea Jenkins que genera:
# - El pathTest80S3file (de tipo test80-origin_develop.zip) 
# - El pathWebmngtestZipS3file (de tipo de tipo webmngtest-origin_develop.zip)
$JenkinsURL = "https://citools.mangodev.net/jenkins"
$JobName = "CI.ROBOTEST.mango.testing"
$JobToken = "robotest"
$FullURL = "$JenkinsURL/view/Testing/job/$JobName/build?token=$JobToken"

$Auth = "jenkins.admin:j95UM8XSIt"
$Bytes = [System.Text.Encoding]::UTF8.GetBytes($Auth)
$Base64bytes = [System.Convert]::ToBase64String($Bytes)
$Headers = @{ "Authorization" = "Basic $Base64bytes"}

If (Test-Path $zipTest80LocalFile) {
	Remove-Item -Force $zipTest80LocalFile
}

$JenkinsParams = @{
"parameter" = @(
@{
name = "BRANCH"
Branch = $branch_robot
value = $branch_robot
},
@{
name = "SKIP_TEST"
value = "true"
}
)
}
$ParamsJSON = ConvertTo-Json -InputObject $JenkinsParams
Invoke-WebRequest -UseBasicParsing $FullURL -Method POST -Headers $Headers -Body @{ json = $ParamsJSON }

# Get del pathTest80S3file (de tipo test80-RELEASE1.zip) from S3 (Wait for X loops)
# WaitAndGet-FileFromS3 $pathTest80S3file $zipTest80LocalFile $bucketS3Name $accessKeyS3 $secreKeyS3 15

# Get del pathWebmngtestZipS3file (de tipo webmngtest-RELEASE1.zip) from S3 (Wait for X loops)
WaitAndGet-FileFromS3 $pathWebmngtestZipS3file $zipWebmngtestLocalFile $bucketS3Name $accessKeyS3 $secreKeyS3 10

# Unzip $zipS3fileName to obtain the .war
Remove-Item -Force *.war
Unzip $zipWebmngtestLocalFile $currentScriptDirectory.tostring()

#Rename-Item -Path webmngtest*.war $warWebmngtestLocalName
Get-ChildItem -Path $currentScriptDirectory.tostring() -Filter webmngtest*.war |
ForEach-Object {
   Rename-Item -Path $_.FullName -NewName $warMangoTestLocalName
}

# Start Tomcat as a Service and Wait 20 seconds to state "Running"
# Removed start Tomcat as a service because there is a problem: the service is executed in background -> the browsers are executed in background -> the size is 1024x768
$tomcat8Service = Get-Service Tomcat8
Start-Service -InputObject $tomcat8Service
$tomcat8Service.WaitForStatus('Running', '00:00:20')

#$catalinaHome = Get-EnvironmentVariable -Name 'CATALINA_HOME' -Scope User
#$TomcatStartupBat = $catalinaHome + '\bin\tomcat8.exe'
#Start-Process -FilePath $TomcatStartupBat -WindowStyle Maximized 
#& $TomcatStartupBat

# Aunque el Tomcat8 esté en estado "Running", por alguna razón hace falta esperar un tiempo prudencial antes de ejecutar el deploy
#Start-Sleep -s 10

# Deploy webmngtest.war in Tomcat8 (wait via 15 times Loop)
$i=0
Do {
	$i++	
	Write-Host "Try deploy webmngtest in Tomcat"
	try {
		$webRequest = [net.WebRequest]::Create("http://localhost:8080/manager/text/deploy?path=/webmngtest&war=$warWebmngtestLocalName")
		$webRequest.Credentials = New-Object System.Net.NetworkCredential("tomcat","tomcat")	 
	    $response = $webRequest.GetResponse()
		$exception = 0;
		Write-Host "Deploy webmngtest OK"
    }
	catch {
		Write-Output "Problem in deploy. Retry..."
		Start-Sleep -s 1
		$exception = 1;
	}
} Until( (([int]$response.StatusCode -eq 200) -and ($exception -eq 0)) -or ($i -gt 15) )

$ipinfo = Invoke-RestMethod http://ipinfo.io/json
Write-Host "URL to access webmngtest: http://$($ipinfo.ip):8080/webmngtest"