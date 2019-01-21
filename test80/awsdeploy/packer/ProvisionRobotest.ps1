#https://github.com/ansible/ansible-modules-extras/issues/2580

#Declare the input parameters that this script will accept
#param (
#  [string]$nameZipTomcat9WithoutZipExtension
#)

Add-Type -AssemblyName System.IO.Compression.FileSystem
function Unzip {
    param([string]$zipfile, [string]$outpath)
    [System.IO.Compression.ZipFile]::ExtractToDirectory($zipfile, $outpath)
}

function Get-EnvironmentVariable([string] $Name, [System.EnvironmentVariableTarget] $Scope) {
    [Environment]::GetEnvironmentVariable($Name, $Scope)
}

# set administrator password
#net user Administrator notsosecret
#wmic useraccount where "name='Administrator'" set PasswordExpires=FALSE

# turn off PowerShell execution policy restrictions
Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope LocalMachine

# Install chocolatey
Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))

# Install Browsers
chocolatey install googlechrome -y --ignore-checksums
chocolatey install firefox -y --version 64.0

# Install ChromeDriver 2.42 in a path accesible for webdrivermagnaer. 
# This step is made for avoid the initial problems when is the webdrivermanager who download the chromedriver (specially in parallel browser-runs) 
# Warning! the default version 2.38 that is downloaded will be modified in the future
chocolatey feature enable -n allowGlobalConfirmation
choco install selenium-chrome-driver
$destination = 'C:\Windows\System32\config\systemprofile\.m2\repository\webdriver\chromedriver\win32\2.45\chromedriver.exe'
New-Item -ItemType File -Path $destination -Force
Copy-Item -Force -Path 'C:\tools\selenium\chromedriver.exe' -Destination $destination 

# Install ChromeDriver 0.22.0 in a path accesible for webdrivermagnaer.
# This step is made for avoid the initial problems when is the webdrivermanager who download the geckodriver (specially in parallel browser-runs)
# Warning! the default version 0.20.1 that is downloaded will be modified in the future
choco install selenium-gecko-driver
$destination = 'C:\Windows\System32\config\systemprofile\.m2\repository\webdriver\geckodriver\win64\0.23.0\geckodriver.exe'
New-Item -ItemType File -Path $destination -Force
Copy-Item -Force -Path 'C:\tools\selenium\geckodriver.exe' -Destination $destination 


#Uninstall tomcat (remove from the final version of the PowerShell script)
#chocolatey uninstall tomcat

#Install tomcat
chocolatey install tomcat --force -params "servicename=Tomcat8"
refreshenv

#Move file tomcat-users.xml that gives roles to tomcat user
$currentScriptDirectory = Get-Location
$fileTomcatUsersOrigin = $currentScriptDirectory.tostring() + '\tomcat-users.xml'
$catalinaHome = Get-EnvironmentVariable -Name 'CATALINA_HOME' -Scope User
$fileTomcatUsersDestination = $catalinaHome + '\conf\tomcat-users.xml'
Copy-Item -Force -Path $fileTomcatUsersOrigin -Destination $fileTomcatUsersDestination

# Install Tools AWS for Windows (https://aws.amazon.com/es/powershell/)
choco install awstools.powershell

# Set Administrator password
$admin = [ADSI]("WinNT://./administrator, user")
$admin.SetPassword("12eple!x")

#Open ports
New-NetFirewallRule -DisplayName "HTTP inbound80" -Enabled:True -Profile Public -Direction Inbound -Action Allow -Protocol TCP -LocalPort 80
New-NetFirewallRule -DisplayName "HTTP inbound8080" -Enabled:True -Profile Public -Direction Inbound -Action Allow -Protocol TCP -LocalPort 8080

# Another way to install Tomcat... manually:
#	# Unzip Tomcat in current directory
#	$nameZipTomcat9WithoutZipExtension = 'apache-tomcat-9.0.5'
#	$currentScriptDirectory = Get-Location
#	Remove-Item -Recurse -Force $nameZipTomcat9WithoutZipExtension
#	$pathFileZipTomcat = $currentScriptDirectory.tostring() + '\' + $nameZipTomcat9WithoutZipExtension + '.zip'
#	Unzip $pathFileZipTomcat $currentScriptDirectory

#	#Set CATALINA_HOME
#	$catalinaHome = $currentScriptDirectory.tostring() + '\' + $nameZipTomcat9WithoutZipExtension
#	$env:CATALINA_HOME = $catalinaHome

#	#Move file tomcat-users.xml that gives roles to tomcat user
#	$fileTomcatUsersOrigin = $currentScriptDirectory.tostring() + '\tomcat-users.xml'
#	$fileTomcatUsersDestination = $catalinaHome + '\conf\tomcat-users.xml'
#	Copy-Item -Force -Path $fileTomcatUsersOrigin -Destination $fileTomcatUsersDestination

#	# Install Tomcat as service
#	$pathServiceBat = $catalinaHome + '\bin\service.bat' 
#	Start-Process $pathServiceBat 'install Tomcat9'

