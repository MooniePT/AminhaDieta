$mavenVersion = "3.9.6"
$mavenUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$mavenVersion/apache-maven-$mavenVersion-bin.zip"
$zipFile = "maven.zip"
$extractDir = "clean_maven"

Write-Host "Downloading Maven $mavenVersion..."
Invoke-WebRequest -Uri $mavenUrl -OutFile $zipFile

Write-Host "Extracting Maven..."
Expand-Archive -Path $zipFile -DestinationPath $extractDir -Force

$mvnPath = Join-Path $pwd "$extractDir\apache-maven-$mavenVersion\bin\mvn.cmd"

Write-Host "Running mvn package..."
& $mvnPath package

Write-Host "Cleaning up..."
Remove-Item $zipFile
# Remove-Item $extractDir -Recurse -Force # Optional: keep it for future runs or delete? I'll keep it for now or delete to be clean. Let's delete to be clean as per plan.
Remove-Item $extractDir -Recurse -Force

Write-Host "Done."
